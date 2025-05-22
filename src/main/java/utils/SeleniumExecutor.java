package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class SeleniumExecutor {

    private final Logger logger = LogManager.getLogger(SeleniumExecutor.class);

    /**
     * A functional interface representing an action that can throw an exception.
     * Used for executing void-returning Selenium actions like clicks, navigation, etc.
     */
    @FunctionalInterface
    public interface SeleniumAction {
        void execute() throws Exception;
    }

    /**
     * A functional interface representing a function that returns a value and may throw an exception.
     * Used for Selenium operations that return results, like getting element text, values, etc.
     *
     * @param <T> The type of the return value.
     */
    @FunctionalInterface
    public interface SeleniumFunction<T> {
        T apply() throws Exception;
    }


    /**
     * A list of Selenium-related exceptions that are considered retryable.
     * If any of these exceptions occur, the framework will attempt to retry the operation
     * or perform recovery before failing.
     */
    private final List<Class<? extends Exception>> retryableExceptions = Arrays.asList(
            NoSuchElementException.class,
            TimeoutException.class,
            ElementClickInterceptedException.class,
            StaleElementReferenceException.class,
            ElementNotInteractableException.class
    );

    /**
     * Executes a Selenium action with automatic retry and recovery logic.
     *
     * @param action      The Selenium action to execute (e.g., click, navigation).
     * @param description A textual description of the action, used for logging.
     * @param maxRetries  Maximum number of retry attempts if recoverable exceptions occur.
     * @param driver      The WebDriver instance, used during recovery.
     * @param element     The WebElement involved in the action, passed to recovery logic.
     *
     * This method:
     * - Logs the start of the action.
     * - Executes the action with retries in case of transient or known recoverable exceptions.
     * - Attempts recovery using the provided driver and element.
     * - Logs success or fails with a RuntimeException if recovery is not possible or retries are exhausted.
     */
    public void runWithHandling(SeleniumAction action, String description, int maxRetries, WebDriver driver, WebElement element) {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                logger.info(description);
                action.execute();
                logger.info("{} is successful",description);
                return;
            } catch (Exception e) {
                attempt++;
                logger.warn("[ {} ] - Attempt {} : {}",description,attempt,e.getClass().getSimpleName());

                if (!canRecoverOrRetry(e)) {
                    logger.error("Unrecoverable exception. Aborting: {}",description);
                    throw new RuntimeException("Unrecoverable exception: " + description, e);
                }

                // Try recovery
                attemptRecovery(e, driver, element);

                if (attempt >= maxRetries) {
                    logger.error("Max retries reached. Failing: {}",description);
                    throw new RuntimeException("Max retries reached. Failing: " + description,e);
                } else {
                    waitBeforeRetry();
                }
            }
        }
    }


    /**
     * Executes a Selenium function that returns a result, with retry and recovery logic.
     *
     * @param function    A Selenium function that returns a value (e.g., getText, getAttribute).
     * @param description A textual description of the function, used for logging.
     * @param maxRetries  Maximum number of retry attempts on failure.
     * @param driver      The WebDriver instance, used during recovery.
     * @param element     The WebElement involved in the operation, passed to recovery logic.
     * @return The result returned by the function, or throws a RuntimeException on failure.
     *
     * This method:
     * - Logs and executes the provided function.
     * - Handles recoverable exceptions by retrying and logging each attempt.
     * - Performs recovery if necessary and retries the function.
     * - Returns the final successful result or throws if retries are exhausted or exception is unrecoverable.
     */
    public <T> T runWithReturnHandling(SeleniumFunction<T> function, String description, int maxRetries, WebDriver driver, WebElement element) {
        int attempt = 0;
        while (attempt < maxRetries) {
            try {
                logger.info(description);
                T result = function.apply();
                if(result instanceof WebElement){
                    logger.info("{} executed successfully and returned a WebElement.", description);
                }else{
                    logger.info("{} returned {} successfully", description, result);
                }
                return result;
            } catch (Exception e) {
                attempt++;
                logger.warn("[ {} ] - Attempt {} : {}",description,attempt,e.getClass().getSimpleName());

                if (!canRecoverOrRetry(e)) {
                    logger.error("Unrecoverable exception. Aborting: {}",description);
                    throw new RuntimeException("Unrecoverable exception: " + description, e);
                }

                attemptRecovery(e, driver, element);

                if (attempt >= maxRetries) {
                    logger.error("Max retries reached. Failing: {}",description);
                    throw new RuntimeException("Max retries reached. Failing: " + description,e);
                } else {
                    waitBeforeRetry();
                }
            }
        }
        return null;
    }


    /**
     * Waits for a condition to be met using a flexible polling mechanism.
     *
     * @param condition   A Supplier representing the condition to wait for. Can return a non-null object or Boolean true.
     * @param description A textual description of the condition, used for logging.
     * @param timeOutinSec Maximum number of seconds to wait before timing out.
     * @param driver      The WebDriver instance used by WebDriverWait.
     * @param <T>         The type of the value expected from the condition (e.g., Boolean, WebElement, String).
     * @return The value returned by the condition if successful, or throws a TimeoutException on failure.
     *
     * This method:
     * - Logs the start of the wait.
     * - Uses WebDriverWait to poll the condition until it evaluates to a non-null value or true.
     * - Ignores exceptions during polling to support robust wait conditions.
     * - Logs success or timeout and throws if the condition is not met within the specified duration.
     */
    public <T> T waitUntil(Supplier<T> condition, String description, int timeOutinSec, WebDriver driver){
        logger.info("Waiting for condition: {}", description);
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutinSec));
            T result = wait.until(new ExpectedCondition<T>() {
                @Override
                public T apply(WebDriver input) {
                    try{
                        T value = condition.get();
                        if(value instanceof Boolean){
                            return (Boolean) value ? value : null;
                        }
                        return value!=null ? value : null;
                    } catch (Exception e){
                        logger.debug("Ignoring exception during wait: {}", e.getClass().getSimpleName());
                        return null;
                    }
                }
            });
            logger.info("✅ Condition met: {}", description);
            return result;
        } catch (TimeoutException e) {
            logger.error("Timeout waiting for condition: {}", description);
            throw new RuntimeException("Timeout waiting for condition: " + description, e);
        }
    }

    /**
     * Determines whether the given exception is considered recoverable and eligible for a retry.
     *
     * @param e The exception thrown during Selenium action or function execution.
     * @return True if the exception is in the list of retryable exceptions; otherwise, false.
     *
     * This method:
     * - Checks if the thrown exception type matches any known retryable Selenium exceptions.
     * - Helps prevent unnecessary retries on unrecoverable issues (like null pointers, assertion failures).
     */
    private boolean canRecoverOrRetry(Exception e) {
        return retryableExceptions.stream().anyMatch(type -> type.isAssignableFrom(e.getClass()));
    }

    /**
     * Attempts recovery based on the exception type.
     *
     * @param e       The exception that occurred during the Selenium operation.
     * @param driver  The WebDriver instance for executing JavaScript recovery steps.
     * @param element The WebElement involved in the original action (if applicable).
     *
     * This method:
     * - Tries to scroll the element into view for clickability or interactability issues.
     * - Introduces a delay for stale or missing elements to allow the DOM to stabilize.
     * - Logs recovery actions or failures during the process.
     * - Provides basic fault-tolerance to transient UI issues.
     */
    private void attemptRecovery(Exception e, WebDriver driver, WebElement element) {
        logger.info("Attempting recovery for exception: {}", e.getClass().getSimpleName());
        if (e instanceof ElementClickInterceptedException || e instanceof ElementNotInteractableException) {
            try {
                logger.info("Recovery: Scrolling element into view...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            } catch (Exception recoveryError) {
                logger.warn("Scroll recovery failed.");
            }
        } else if (e instanceof StaleElementReferenceException || e instanceof NoSuchElementException) {
            logger.info("Recovery: StaleElement - wait and try again");
            try {
                Thread.sleep(1000);
            } catch (Exception recoveryError) {
                logger.warn("Wait recovery failed.");
            }
        } else {
            logger.info("No recovery logic defined for: {}",e.getClass().getSimpleName());
        }
    }

    /**
     * Waits briefly before retrying a failed Selenium operation.
     *
     * This method:
     * - Pauses execution for 1 second (1000 milliseconds).
     * - Helps in cases where a short delay might resolve a transient issue (e.g., rendering or animations).
     * - Catches and logs interruption, preserving thread state if interrupted.
     */
    private void waitBeforeRetry() {
        int waitTime = 1000;
        logger.info("Waiting {}ms before retry...", waitTime);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ig) {
            Thread.currentThread().interrupt();
            logger.warn("Interrupted during retry wait", ig);
        }
    }

}
