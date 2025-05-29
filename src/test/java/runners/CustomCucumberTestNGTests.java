package runners;

import config.PropertiesReader;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.AllureHelper;
import utils.LoggerWrapper;

public class CustomCucumberTestNGTests extends AbstractTestNGCucumberTests {

    AllureHelper allureHelper = new AllureHelper();
    LoggerWrapper logger = new LoggerWrapper(CustomCucumberTestNGTests.class);
    public final static ThreadLocal<String> browser = new ThreadLocal<>();
    PropertiesReader propertiesReader = new PropertiesReader();

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
    public void test(@Optional("") String xmlBrowser){
        CustomCucumberTestNGTests.browser.set(returnBrowser(xmlBrowser));
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
