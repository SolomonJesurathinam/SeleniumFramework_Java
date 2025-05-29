package utils;

import config.PropertiesReader;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

public class ExcelUtils {

    LoggerWrapper logger;

    public ExcelUtils(){
        logger = new LoggerWrapper(ExcelUtils.class);
    }

    public LinkedHashMap<String, String> get_excel_data_by_id(String excelPath, String testID, String sheetName) {
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

    public String excel_cell_formatter(Cell cell){
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }

    public void get_test_id(String excelPath, String sheetName, String moduleName){
        Workbook workbook = WorkbookFactory.create(new FileInputStream(excelPath));
        Sheet sheet = workbook.getSheet(sheetName);
        int rows = sheet.getLastRowNum();


    }

    public static void main(String[] args) throws Throwable{
        ExcelUtils ex = new ExcelUtils();
        PropertiesReader prop = new PropertiesReader();
        System.out.println(ex.get_excel_data_by_id(prop.getProperty("dataPath"),"TC002",prop.getProperty("sheetName")));
    }
}
