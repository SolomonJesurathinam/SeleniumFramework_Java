package base;

import com.aventstack.chaintest.plugins.ChainTestListener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
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

    public BaseTest(){
        propertiesReader = new PropertiesReader();
        reusableMethods = new ReusableMethods(this);
        logger = new LoggerWrapper(BaseTest.class);
    }

    @BeforeTest
    public void beforeTestConfiguration(ITestContext testContext){
        String testName = testContext.getName();
        logger.info(String.format("************************ %s Test Starts ************************",testName));
    }

    @BeforeMethod
    public void openBrowser(Method method){
        String methodName = method.getName();
        logger.info(String.format("************************ %s Method Starts ************************",methodName));
        DriverFactory.setBrowser(propertiesReader.getProperty("browser"));
        driver = DriverFactory.getDriver();
        reusableMethods.openURL(propertiesReader.getProperty("url"),driver);
    }

    @AfterMethod
    public void tearDown(ITestResult result, Method method){
        if(ITestResult.FAILURE == result.getStatus()){
            TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
            ChainTestListener.embed(ts.getScreenshotAs(OutputType.BASE64),"image/png");
        }
        DriverFactory.quitDriver();
        String methodName = method.getName();
        logger.info(String.format("************************ %s Method Ends ************************",methodName));
    }

    @AfterTest
    public void afterTestConfiguration(ITestContext testContext){
        String testName = testContext.getName();
        logger.info(String.format("************************ %s Test Ends ************************",testName));
    }
}
