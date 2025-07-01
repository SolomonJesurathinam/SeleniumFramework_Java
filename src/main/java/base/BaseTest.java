package base;

import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.AllureHelper;
import utils.DriverFactory;
import utils.LoggerWrapper;
import config.PropertiesReader;
import utils.ReusableMethods;
import java.lang.reflect.Method;

public class BaseTest {

    PropertiesReader propertiesReader;
    protected WebDriver driver;
    ReusableMethods reusableMethods;
    LoggerWrapper logger;
    AllureHelper allureHelper;

    public BaseTest() {
        propertiesReader = new PropertiesReader();
        reusableMethods = new ReusableMethods(this);
        logger = new LoggerWrapper(BaseTest.class);
        allureHelper = new AllureHelper();
    }

    @BeforeSuite
    public void cleanAllureResults_folder(){
        allureHelper.cleanAllureResults();
    }

    @BeforeTest
    public void beforeTestConfiguration(ITestContext testContext) {
        String testName = testContext.getName();
        logger.info(String.format("************************ %s Test Starts ************************", testName));
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void openBrowser(Method method, @Optional("") String xmlBrowser) {
        String methodName = method.getName();
        logger.info(String.format("************************ %s Method Starts *************************", methodName));
        DriverFactory.setBrowser(returnBrowser(xmlBrowser));
        driver = DriverFactory.getDriver();
        reusableMethods.openURL(propertiesReader.getProperty("url"), driver);
    }

    @AfterMethod
    public void tearDown(ITestResult result, Method method) {
        if (ITestResult.FAILURE == result.getStatus()) {
            reusableMethods.attachScreenshot(DriverFactory.getDriver(), "Failed");
        }
        DriverFactory.quitDriver();
        String methodName = method.getName();
        logger.info(String.format("************************ %s Method Ends ************************", methodName));
    }

    @AfterTest
    public void afterTestConfiguration(ITestContext testContext) {
        String testName = testContext.getName();
        logger.info(String.format("************************ %s Test Ends ************************", testName));
    }

    @AfterSuite
    public void generateReports(){
        allureHelper.generateAllureReports();
    }

    private String returnBrowser(String xmlBrowser) {
        String browser;
        if (!xmlBrowser.isEmpty()) {
            browser = xmlBrowser;
        } else {
            browser = propertiesReader.getProperty("browser");
        }
        return browser;
    }

}
