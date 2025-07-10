package stepdefs;

import config.PropertiesReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import runners.CustomCucumberTestNGTests;
import utils.DriverFactory;
import utils.LoggerWrapper;
import utils.ReusableMethods;

public class CucumberHooks {

    LoggerWrapper logger = new LoggerWrapper(CucumberHooks.class);
    PropertiesReader propertiesReader = new PropertiesReader();
        ReusableMethods reusableMethods = new ReusableMethods(CucumberHooks.class);

    @Before
    public void openBrowser(Scenario scenario) {
        String methodName = scenario.getName();
        logger.info(String.format("************************ %s Method Starts ************************", methodName));
        ThreadContext.put("testName", methodName);
        String browser = CustomCucumberTestNGTests.browser.get();
        DriverFactory.setBrowser(browser);
        WebDriver driver = DriverFactory.getDriver();
        reusableMethods.openURL(propertiesReader.getProperty("url"), driver);
    }

    @After
    public void tearDown(Scenario scenario){
        if (scenario.isFailed()) {
            reusableMethods.attachScreenshot(DriverFactory.getDriver(), "Failed");
        }
        DriverFactory.quitDriver();
        String methodName = scenario.getName();
        logger.info(String.format("************************ %s Method Ends ************************", methodName));
    }

}
