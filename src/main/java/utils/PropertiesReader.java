package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

    private final Logger logger = LogManager.getLogger(PropertiesReader.class);

    public String getProperty(String getProp){
        Properties properties = new Properties();
        String propPath = System.getProperty("user.dir")+"/src/test/resources/app.properties";
        try{
            logger.info("Loading Properties File");
            properties.load(new FileInputStream(propPath));
            logger.info("Loaded Properties File successfully");
        } catch (IOException e) {
            String errorMsg = "Property File is not available in the "+propPath+" location";
            logger.error("Error Loading Properties File {}", errorMsg);
            throw new RuntimeException(errorMsg);
        }
        String value = properties.getProperty(getProp);
        logger.info("Returning {} for {} property", value, getProp);
        return value;
    }
}
