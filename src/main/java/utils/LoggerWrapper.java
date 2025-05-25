package utils;

import com.aventstack.chaintest.plugins.ChainTestListener;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.ByteArrayInputStream;

public class LoggerWrapper {

    private final Logger logger;

    public LoggerWrapper(Class<?> clazz){
        this.logger = LogManager.getLogger(clazz);
    }

    public void info(String message){
        logger.info(message);
        ChainTestListener.log("[INFO] " + message);
        logToAllure("INFO", message);
    }

    public void warn(String message) {
        logger.warn(message);
        ChainTestListener.log("[WARN] " + message);
        logToAllure("WARN", message);
    }

    public void error(String message) {
        logger.error(message);
        ChainTestListener.log("[ERROR] " + message);
        logToAllure("ERROR", message);
    }

    public void debug(String message) {
        logger.debug(message);
        ChainTestListener.log("[DEBUG] " + message);
        logToAllure("DEBUG", message);
    }

    private void logToAllure(String level, String message) {
        Allure.addAttachment("[" + level + "] " + message,
                new ByteArrayInputStream(message.getBytes()));
    }

}
