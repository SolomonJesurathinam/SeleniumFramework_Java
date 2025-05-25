package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverFactory {

    static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final LoggerWrapper logger = new LoggerWrapper(DriverFactory.class);

    public static void setBrowser(String browser){
        if(browser.equalsIgnoreCase("chrome")){
            setChromeDriver();
        }
        else if(browser.equalsIgnoreCase("edge")){
            setEdgeDriver();
        }
        else if(browser.equalsIgnoreCase("firefox")){
            setFireFoxDriver();
        }else{
            logger.warn(String.format("Unsupported %s is given",browser));
            throw new IllegalArgumentException("Unsupported "+browser+" is given");
        }
        getDriver().manage().window().maximize();
    }

    private static void setChromeDriver(){
        ChromeOptions options = new ChromeOptions();
        logger.info("Opening Chrome Browser");
        try{
            driverThreadLocal.set(new ChromeDriver(options));
            logger.info("Opened Chrome Browser successfully");
        }catch(Exception e){
            logger.error(String.format("Error opening Chrome browser - %s",e.getMessage()));
        }
    }

    private static void setEdgeDriver(){
        EdgeOptions options = new EdgeOptions();
        logger.info("Opening Edge Browser");
        try{
            driverThreadLocal.set(new EdgeDriver(options));
            logger.info("Opened Edge Browser successfully");
        }catch(Exception e){
            logger.error(String.format("Error opening Edge browser - %s",e.getMessage()));
        }
    }

    private static void setFireFoxDriver(){
        FirefoxOptions options = new FirefoxOptions();
        logger.info("Opening Firefox Browser");
        try{
            driverThreadLocal.set(new FirefoxDriver(options));
            logger.info("Opened Firefox Browser successfully");
        }catch(Exception e){
            logger.error(String.format("Error opening Firefox browser - %s",e.getMessage()));
        }
    }

    public static WebDriver getDriver(){
        return driverThreadLocal.get();
    }

    public static void quitDriver(){
        logger.info("Quitting driver");
        try{
            getDriver().quit();
            logger.info("Driver closed successfully");
        }catch (Exception e){
            logger.error(String.format("Error quitting driver - %s",e.getMessage()));
        }
    }
}
