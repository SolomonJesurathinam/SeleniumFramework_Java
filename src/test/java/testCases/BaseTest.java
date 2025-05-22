package testCases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverFactory;
import utils.PropertiesReader;
import utils.ReusableMethods;

public class BaseTest {

    PropertiesReader propertiesReader;
    protected WebDriver driver;
    ReusableMethods reusableMethods;

    BaseTest(){
        propertiesReader = new PropertiesReader();
        reusableMethods = new ReusableMethods();
    }

    @BeforeMethod
    public void openBrowser(){
        DriverFactory.setBrowser(propertiesReader.getProperty("browser"));
        driver = DriverFactory.getDriver();
        reusableMethods.openURL(propertiesReader.getProperty("url"),driver);
    }

    //@AfterMethod
    public void tearDown(){
        DriverFactory.quitDriver();
    }
}
