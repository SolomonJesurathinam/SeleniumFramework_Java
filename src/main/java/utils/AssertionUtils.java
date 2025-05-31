package utils;

import io.restassured.response.Response;
import org.testng.Assert;

public class AssertionUtils {

    private static final LoggerWrapper logger = new LoggerWrapper(AssertionUtils.class);

    /**
     * Hard assert equality with logging.
     */
    public static <T> void assertEquals(T actual, T expected, String description) {
        logger.info(String.format("Verifying: %s", description));
        try {
            Assert.assertEquals(actual, expected);
            logger.info(String.format("Assertion passed: %s | Expected: %s | Actual: %s", description, expected, actual));
        } catch (AssertionError e) {
            logger.error(String.format("Assertion failed: %s | Expected: %s | Actual: %s", description, expected, actual));
            throw e;
        }
    }

    /**
     * Hard assert condition is true.
     */
    public static void assertTrue(boolean condition, String description) {
        logger.info(String.format("Checking condition: %s", description));
        try {
            Assert.assertTrue(condition);
            logger.info(String.format("Condition is true: %s", description));
        } catch (AssertionError e) {
            logger.error(String.format("Condition failed: %s", description));
            throw e;
        }
    }

    /**
     * Assertion Failure.
     */
    public static void assertFail(String description) {
        logger.info(String.format("Assertion Failed: %s", description));
        Assert.fail(description);
    }

    // ================================================================
    //                     API-Specific Assertion Methods
    // ================================================================

    public static void assertStatusCode(Response response, int expectedStatusCode){
        int actualStatusCode = response.getStatusCode();
        String description = "Status code verification";
        
    }

}
