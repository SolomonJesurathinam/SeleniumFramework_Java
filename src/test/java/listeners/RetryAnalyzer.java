package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import utils.LoggerWrapper;
import config.PropertiesReader;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retrycount = 0;
    private static final int maxRetryCount;
    LoggerWrapper logger = new LoggerWrapper(RetryAnalyzer.class);

    static {
        PropertiesReader propertiesReader = new PropertiesReader();
        String maxcount = propertiesReader.getProperty("maxretryCount");
        maxRetryCount = maxcount != null ? Integer.parseInt(maxcount) : 0;
    }

    @Override
    public boolean retry(ITestResult iTestResult) {
        logger.info(String.format("Checking if the test needs to be retried - %s",iTestResult.getStatus()));
        if(retrycount < maxRetryCount){
            retrycount++;
            logger.info(String.format("Retrying %s %s time",iTestResult.getMethod().getMethodName(),String.valueOf(retrycount)));
            return true;
        }
        logger.info(String.format("Retry completed or not needed for %s ",iTestResult.getMethod().getMethodName()));
        return false;
    }
}
