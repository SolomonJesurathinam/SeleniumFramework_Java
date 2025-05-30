package dataproviders;

import config.PropertiesReader;
import org.testng.annotations.DataProvider;
import utils.ExcelUtils;
import utils.LoggerWrapper;
import java.util.LinkedList;

public class ExcelDataProvider {

    LoggerWrapper logger = new LoggerWrapper(ExcelDataProvider.class);

    public Object[][] get_testIds_from_module(String moduleName){
        logger.info("Converting test id's to Object array");
        PropertiesReader propertiesReader = new PropertiesReader();
        String path = propertiesReader.getProperty("dataPath");
        String sheetName = propertiesReader.getProperty("sheetName");

        ExcelUtils excelUtils = new ExcelUtils();
        LinkedList<String> testids =excelUtils.get_test_id(path,sheetName,moduleName);

        Object[][] data = new Object[testids.size()][1];
        for(int i=0; i< testids.size(); i++){
            data[i][0] = testids.get(i);
        }
        logger.info(String.format("Converted test id's to Object array - %s for %s",data.toString(),moduleName));
        return data;
    }

    @DataProvider(name = "Login")
    public Object[][] loginData(){
        return get_testIds_from_module("Login");
    }

    @DataProvider(name = "Directory",parallel = true)
    public Object[][] directoryData(){
        return get_testIds_from_module("Directory");
    }

    @DataProvider(name = "Admin", parallel = true)
    public Object[][] adminData(){
        return get_testIds_from_module("Admin");
    }
}
