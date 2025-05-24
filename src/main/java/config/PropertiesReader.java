package config;

import utils.LoggerWrapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReader {

//    private final Logger logger = LogManager.getLogger(PropertiesReader.class);
    private final LoggerWrapper logger = new LoggerWrapper(PropertiesReader.class);

    public String getProperty(String getProp){
        Properties properties = new Properties();
        String propPath = System.getProperty("user.dir")+"/src/test/resources/app.properties";
        try{
            logger.info("Loading Properties File");
            properties.load(new FileInputStream(propPath));
            logger.info("Loaded Properties File successfully");
        } catch (IOException e) {
            String errorMsg = "Property File is not available in the "+propPath+" location";
            logger.error(String.format("Error Loading Properties File %s", errorMsg));
            throw new RuntimeException(errorMsg);
        }
        String value = properties.getProperty(getProp);
        logger.info(String.format("Returning %s for %s property", value, getProp));
        return value;
    }
}
