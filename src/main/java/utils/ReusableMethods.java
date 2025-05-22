package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ReusableMethods {

    SeleniumExecutor se = new SeleniumExecutor();

    public void openURL(String url, WebDriver driver) {
        se.runWithHandling(() -> {
            driver.get(url);
        }, String.format("Opening url - %s ", url), 1, driver, null);
    }

    public void clickElement(WebElement element, WebDriver driver, String eleName) {
        se.runWithHandling(() -> {
            waitForElementToBeVisible(driver, 30, element, eleName);
            element.click();
        }, String.format("Clicking element - %s ", eleName), 3, driver, element);
    }

    public String getTextOfElement(WebElement element, WebDriver driver, String eleName) {
        return se.runWithReturnHandling(() -> {
            return element.getText();
        }, String.format("Getting text from - %s ", eleName), 3, driver, element);
    }

    public String waitUntilTextContains(WebElement element, String expected, WebDriver driver, String eleName, int timeSec) {
        return se.waitUntil(() -> {
            String text = element.getText();
            return text.contains(expected) ? text : null;
        }, String.format("Waiting for text in %s", eleName), timeSec, driver);
    }

    public void waitForElementToBeVisible(WebDriver driver, int timeSec, WebElement element, String eleName) {
        se.runWithHandling(() -> {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeSec));
            wait.until(ExpectedConditions.visibilityOf(element));
        }, String.format("Wait for %s to be visible ", element), 1, driver, element);
    }

    public void enterText(String text, WebElement element, WebDriver driver, String eleName) {
        se.runWithHandling(() -> {
            waitForElementToBeVisible(driver, 30, element, eleName);
            element.clear();
            element.sendKeys(text);
        }, String.format("Entering %s in %s ", text, eleName), 3, driver, element);
    }

}
