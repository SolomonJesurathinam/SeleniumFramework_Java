package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.AllureHelper;
import utils.LoggerWrapper;

public class CustomCucumberTestNGTests extends AbstractTestNGCucumberTests {

    AllureHelper allureHelper = new AllureHelper();
    LoggerWrapper logger = new LoggerWrapper(CustomCucumberTestNGTests.class);

    @BeforeSuite
    public void cleanAllureResults_folder(){
        allureHelper.cleanAllureResults();
    }

    @BeforeTest
    public void beforeTestConfiguration(ITestContext testContext) {
        String testName = testContext.getName();
        logger.info(String.format("************************ %s Test Starts ************************", testName));
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


}
