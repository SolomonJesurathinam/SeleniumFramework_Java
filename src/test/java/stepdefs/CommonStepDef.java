package stepdefs;

import io.cucumber.java.en.Given;
import utils.ExcelUtils;

public class CommonStepDef {

    ExcelUtils excelUtils;

    public CommonStepDef(){
        excelUtils = new ExcelUtils();
    }

    @Given("Load TestCase data for {string}")
    public void testData(String testCaseID){
        ExcelUtils.setData(excelUtils.get_excel_data_from_testid(testCaseID));
    }
}
