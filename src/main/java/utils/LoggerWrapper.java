package utils;

import com.aventstack.chaintest.plugins.ChainTestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerWrapper {

    private final Logger logger;

    public LoggerWrapper(Class<?> clazz){
        this.logger = LogManager.getLogger(clazz);
    }

    public void info(String message){
        logger.info(message);
        ChainTestListener.log("[INFO] " + message);
    }

    public void warn(String message) {
        logger.warn(message);
        ChainTestListener.log("[WARN] " + message);
    }

    public void error(String message) {
        logger.error(message);
        ChainTestListener.log("[ERROR] " + message);
    }

    public void debug(String message) {
        logger.debug(message);
        ChainTestListener.log("[DEBUG] " + message);
    }


}
