package utils;

import config.PropertiesReader;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class ExcelUtils {

    LoggerWrapper logger;
    PropertiesReader propertiesReader;
    public static ThreadLocal<LinkedHashMap<String, String>> testData = new ThreadLocal<>();

    public ExcelUtils(){
        logger = new LoggerWrapper(ExcelUtils.class);
        propertiesReader = new PropertiesReader();
    }

    private LinkedHashMap<String, String> get_excel_data_by_id(String excelPath, String sheetName,String testID) {
        logger.info(String.format("Retrieving data for %s",testID));
        try(Workbook workbook = WorkbookFactory.create(new FileInputStream(excelPath))){
            Sheet sheet = workbook.getSheet(sheetName);
            int rows = sheet.getLastRowNum();
            int cols = sheet.getRow(0).getLastCellNum();

            LinkedHashMap<String, String> data = new LinkedHashMap<>();

            for(int row=1; row<=rows; row++){
                Cell filter = sheet.getRow(row).getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String filter_value = excel_cell_formatter(filter);

                if(filter_value.equalsIgnoreCase(testID)){
                    for(int col=0; col<cols; col++){
                        Cell header = sheet.getRow(0).getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        Cell value = sheet.getRow(row).getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        String colHeader = excel_cell_formatter(header);
                        String data_value = excel_cell_formatter(value);
                        if(data_value !=null && !data_value.trim().isEmpty()){
                            data.put(colHeader, data_value);
                        }
                    }
                }
            }
            logger.info(String.format("Retrieved data %s for %s",data,testID));
            return data;
        } catch (IOException e){
            AssertionUtils.assertFail("Unable to get test details due to "+e.getMessage());
        }
        return null;
    }

    public LinkedHashMap<String, String> get_excel_data_from_testid(String testID){
        String path = propertiesReader.getProperty("dataPath");
        String sheetName = propertiesReader.getProperty("sheetName");
        return get_excel_data_by_id(path,sheetName,testID);
    }

    private String excel_cell_formatter(Cell cell){
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    public LinkedList<String> get_test_id(String excelPath, String sheetName, String moduleName){
        logger.info(String.format("Retrieving test id for %s",moduleName));
        try(Workbook workbook = WorkbookFactory.create(new FileInputStream(excelPath))){
            Sheet sheet = workbook.getSheet(sheetName);
            int rows = sheet.getLastRowNum();

            LinkedList<String> testids = new LinkedList<>();

            for(int row=1; row<=rows; row++){
                Cell module = sheet.getRow(row).getCell(2,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String moduleValue = excel_cell_formatter(module);
                Cell execution = sheet.getRow(row).getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String execution_value = excel_cell_formatter(execution);

                if(moduleValue.equalsIgnoreCase(moduleName) && execution_value.equalsIgnoreCase("Y")){
                    Cell testid = sheet.getRow(row).getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String testid_value = excel_cell_formatter(testid);
                    testids.add(testid_value);
                }
            }
            logger.info(String.format("Retrieved test id's  %s for %s",testids,moduleName));
            return testids;
        }catch (IOException e){
            AssertionUtils.assertFail("Unable to get test id's due to "+e.getMessage());
        }
        return null;
    }

    public List<String> get_test_id(String excelPath, String sheetName){
        logger.info("Retrieving test id for all 'Y' execution");
        try(Workbook workbook = WorkbookFactory.create(new FileInputStream(excelPath))){
            Sheet sheet = workbook.getSheet(sheetName);
            int rows = sheet.getLastRowNum();

            List<String> testids = new LinkedList<>();

            for(int row=1; row<=rows; row++){
                Cell execution = sheet.getRow(row).getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String execution_value = excel_cell_formatter(execution);

                if(execution_value.equalsIgnoreCase("Y")){
                    Cell testid = sheet.getRow(row).getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String testid_value = excel_cell_formatter(testid);
                    testids.add(testid_value);
                }
            }
            logger.info(String.format("Retrieved test id's %s for executions flagged 'Y'",testids));
            return testids;
        }catch (IOException e){
            AssertionUtils.assertFail("Unable to get test id's due to "+e.getMessage());
        }
        return null;
    }

    public static void setData(LinkedHashMap<String, String> data){
        testData.set(data);
    }

    public static LinkedHashMap<String, String> getData(){
        return testData.get();
    }
}
