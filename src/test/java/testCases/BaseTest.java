package testCases;

import com.aventstack.chaintest.generator.ChainTestSimpleGenerator;
import com.aventstack.chaintest.generator.FileGenerator;
import com.aventstack.chaintest.plugins.ChainTestListener;
import com.aventstack.chaintest.service.ChainPluginService;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.DriverFactory;
import utils.PropertiesReader;
import utils.ReusableMethods;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseTest {

    PropertiesReader propertiesReader;
    protected WebDriver driver;
    ReusableMethods reusableMethods;

    BaseTest(){
        propertiesReader = new PropertiesReader();
        reusableMethods = new ReusableMethods(this);
    }

    @BeforeMethod
    public void openBrowser(){
        DriverFactory.setBrowser(propertiesReader.getProperty("browser"));
        driver = DriverFactory.getDriver();
        reusableMethods.openURL(propertiesReader.getProperty("url"),driver);
    }

    @AfterMethod
    public void tearDown(ITestResult result){
        if(ITestResult.FAILURE == result.getStatus()){
            TakesScreenshot ts = (TakesScreenshot) DriverFactory.getDriver();
            ChainTestListener.embed(ts.getScreenshotAs(OutputType.BASE64),"image/png");
        }
        DriverFactory.quitDriver();
    }
}
