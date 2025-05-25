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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
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
    @Parameters({"browser"})
    public void openBrowser(Method method, @Optional("") String xmlBrowser){
        String methodName = method.getName();
        logger.info(String.format("************************ %s Method Starts ************************",methodName));
        DriverFactory.setBrowser(returnBrowser(xmlBrowser));
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

    @AfterSuite
    public void generateAllureReports(){
        try{
            String allureExecutable = resolveAllureExecutable();
            if(allureExecutable == null){
                System.err.println("Allure CLI not found in environment PATH.");
                return;
            }

            File resultsDir = new File("target/allure-results");
            if (!resultsDir.exists() || !resultsDir.isDirectory()) {
                System.err.println("Allure results directory does not exist: " + resultsDir.getAbsolutePath());
                return;
            }

            File outputDir = new File("target/allure-report");
            ProcessBuilder pb = new ProcessBuilder(
                    allureExecutable,
                    "generate",
                    "--single-file",
                    resultsDir.getAbsolutePath(),
                    "-o",
                    outputDir.getAbsolutePath()
            );

            pb.redirectErrorStream(true);
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[Allure] " + line);
                }
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Allure report generated at: " + outputDir.getAbsolutePath());
            } else {
                System.err.println("Failed to generate Allure report. Exit code: " + exitCode);
            }
        }catch (Exception e) {
            System.err.println("Error generating Allure report: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String resolveAllureExecutable() {
        try{
            String path = System.getenv("PATH");
            if(path != null){
                String[] paths = path.split(File.pathSeparator);
                for(String dir : paths){
                    File allureFile = new File(dir, isWindows() ? "allure.bat" : "allure");
                    if(allureFile.exists() && allureFile.canExecute()){
                        return allureFile.getAbsolutePath();
                    }
                }
            }
        }catch (Exception e){
            System.err.println("Could not resolve allure binary: " + e.getMessage());
        }
        return null;
    }

    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }

    private String returnBrowser(String xmlBrowser){
        String browser;
        if(!xmlBrowser.isEmpty()){
            browser = xmlBrowser;
        }else{
            browser = propertiesReader.getProperty("browser");
        }
        return browser;
    }
}
