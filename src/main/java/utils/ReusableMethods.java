package utils;

import com.aventstack.chaintest.plugins.ChainTestListener;
import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class ReusableMethods {

    SeleniumExecutor se;

    public ReusableMethods(Object callerObject) {
        se = new SeleniumExecutor(callerObject.getClass());
    }

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

    public void clickElementJS(WebElement element, WebDriver driver, String eleName) {
        se.runWithHandling(() -> {
            waitForElementToBeVisible(driver, 30, element, eleName);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", element);
        }, String.format("Clicking element using JS - %s", eleName), 3, driver, element);
    }


    public String getTextOfElement(WebElement element, WebDriver driver, String eleName) {
        return se.runWithReturnHandling(() -> {
            waitForElementToBeVisible(driver, 30, element, eleName);
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

    public void enterText_slowly(String text, WebElement element, WebDriver driver, String eleName) {
        se.runWithHandling(() -> {
            waitForElementToBeVisible(driver, 30, element, eleName);
            element.clear();
            char[] chars = text.toCharArray();
            for (char c : chars) {
                element.sendKeys(String.valueOf(c));
                Thread.sleep(200);
            }

        }, String.format("Entering %s in %s ", text, eleName), 3, driver, element);
    }

    public void dynamicDropdown_text(WebDriver driver, WebElement enterTextEle, String searchText, String selectText, String eleName,
                                     WebElement dropFirstEle, List<WebElement> dropList) {
        se.runWithHandling(() -> {
            waitForElementToBeVisible(driver, 30, enterTextEle, eleName);
            enterText_slowly(searchText, enterTextEle, driver, eleName);
            waitForElementToBeVisible(driver, 15, dropFirstEle, "Drop First element");
            waitUntilTextDoesntContains(dropFirstEle, "Searching...", driver, "Drop First element", 15);
            for (WebElement drop : dropList) {
                String text = getTextOfElement(drop, driver, eleName);
                if (text.equals(selectText)) {
                    clickElementJS(drop, driver, text);
                    break;
                }
            }
        }, String.format("Select %s from dynamic dropdown %s ", selectText, eleName), 3, driver, enterTextEle);
    }


    public void waitUntilTextDoesntContains(WebElement element, String expected, WebDriver driver, String eleName, int timeSec) {
        se.waitUntil(() -> {
            String text = element.getText();
            return !text.contains(expected) ? true : null;
        }, String.format("Waiting for text doesn't contains %s", expected), timeSec, driver);
    }

    public void waitForElementToBeInVisible(WebDriver driver, int timeSec, WebElement element, String eleName) {
        se.runWithHandling(() -> {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeSec));
            wait.until(ExpectedConditions.invisibilityOf(element));
        }, String.format("Wait for %s to be invisible ", element), 1, driver, element);
    }

    public void dynamicDropdown(WebDriver driver, WebElement clickEle, WebElement dropSectionCheck, String selectText, String eleName, List<WebElement> dropList) {
        se.runWithHandling(() -> {
            waitForElementToBeVisible(driver, 30, clickEle, eleName);
            clickElement(clickEle, driver, eleName);
            waitForElementToBeVisible(driver, 10, dropSectionCheck, "Dropdown list");

            Optional<WebElement> option = dropList.stream().filter(webElement ->
                webElement.getText().trim().equalsIgnoreCase(selectText)).findFirst();

            if(option.isPresent()){
                clickElement(option.get(),driver, "Dropdown Option");
            }else{
                throw new RuntimeException("Element with text '" + selectText + "' not found in drop list.");
            }

        }, String.format("Selecting %s from dynamic dropdown %s ", selectText, eleName), 3, driver, clickEle);
    }

    public void dynamicDropdown_text_actions(WebDriver driver, WebElement enterTextEle, String searchText, String selectText, String eleName,
                                             WebElement dropFirstEle, List<WebElement> dropList) {
        se.runWithHandling(() -> {
            waitForElementToBeVisible(driver, 30, enterTextEle, eleName);
            enterText_slowly(searchText, enterTextEle, driver, eleName);
            waitForElementToBeVisible(driver, 15, dropFirstEle, "Drop First element");
            waitUntilTextDoesntContains(dropFirstEle, "Searching...", driver, "Drop First element", 15);
            for (WebElement drop : dropList) {
                String text = getTextOfElement(drop, driver, eleName);
                if (text.equals(selectText)) {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(drop).click().perform();
                    break;
                }
            }
        }, String.format("Select %s from dynamic dropdown %s ", selectText, eleName), 3, driver, enterTextEle);
    }

    public void attachScreenshot(WebDriver driver, String imageName){
        se.runWithHandling(()->{
            TakesScreenshot ts = (TakesScreenshot) driver;
            String img = ts.getScreenshotAs(OutputType.BASE64);
            byte[] decodedImg = Base64.getDecoder().decode(img);
            ChainTestListener.embed(img,"image/png");
            Allure.addAttachment(imageName,new ByteArrayInputStream(decodedImg));
        },"Taking screenshot",1,driver,null);
    }
}
