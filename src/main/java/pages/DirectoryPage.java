package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.support.PageFactory;
import pages.locators.DirectoryPageLocators;
import utils.AssertionUtils;
import utils.ReusableMethods;

public class DirectoryPage extends BasePage {

    ReusableMethods reusableMethods;
    DirectoryPageLocators dpLocators;

    public DirectoryPage(){
        reusableMethods = new ReusableMethods(this);
        dpLocators = new DirectoryPageLocators();
        PageFactory.initElements(driver, dpLocators);
    }

    @Step("Verify Directory Page is loaded")
    public DirectoryPage verify_page_is_loaded(){
        reusableMethods.waitForElementToBeVisible(driver, 30, dpLocators.page_load_verify,"Directory Page");
        return this;
    }

    @Step("Search and verify Employees")
    public DirectoryPage search_and_verify_employee(String searchText, String selectText, String expectedVerify){
        reusableMethods.dynamicDropdown_text_actions(driver,dpLocators.txt_empName,searchText,selectText,"Select Employee",
                dpLocators.dropFirstEle, dpLocators.lst_empDropdown);
        reusableMethods.clickElement(dpLocators.btn_search,driver,"Search");
        reusableMethods.waitForElementToBeVisible(driver, 5, dpLocators.spinner, "Loading Spinner");
        reusableMethods.waitForElementToBeInVisible(driver, 30, dpLocators.spinner, "Loading Spinner");
        String records = reusableMethods.getTextOfElement(dpLocators.msg_records,driver,"Message Records");
        AssertionUtils.assertEquals(records,expectedVerify, "Records Count");
        return this;
    }

    @Step("Reset Search")
    public DirectoryPage resetSearch(){
        reusableMethods.clickElement(dpLocators.btn_reset,driver,"Reset");
        reusableMethods.waitForElementToBeVisible(driver, 5, dpLocators.spinner, "Loading Spinner");
        reusableMethods.waitForElementToBeInVisible(driver, 30, dpLocators.spinner, "Loading Spinner");
        return this;
    }

    @Step("Search and verify Job Title")
    public DirectoryPage search_and_Verify_jobTitle_NoRecords(String selectText, String noRecordsExp){
        reusableMethods.dynamicDropdown(driver,dpLocators.drop_jobTitle,dpLocators.dropList_generic_verify,selectText,"Job Title",dpLocators.lst_drop_generic);
        reusableMethods.clickElement(dpLocators.btn_search,driver,"Search");
        reusableMethods.waitForElementToBeVisible(driver, 5, dpLocators.spinner, "Loading Spinner");
        reusableMethods.waitForElementToBeInVisible(driver, 30, dpLocators.spinner, "Loading Spinner");
        String records = reusableMethods.getTextOfElement(dpLocators.msg_records,driver,"Message Records");
        if(records.equalsIgnoreCase("No Records Found")){
            reusableMethods.waitForElementToBeVisible(driver, 10, dpLocators.popup_noRecords,"No Records Popup");
            String actual = reusableMethods.getTextOfElement(dpLocators.popup_noRecords,driver,"No Records popup");
            AssertionUtils.assertEquals(actual,noRecordsExp,"No Records comparing");
        }
        AssertionUtils.assertEquals(records,noRecordsExp, "Records Count");
        return this;
    }

    @Step("Search and verify location")
    public DirectoryPage search_and_verify_location(String selectText, String expectedVerify){
        reusableMethods.dynamicDropdown(driver,dpLocators.drop_location,dpLocators.dropList_generic_verify,selectText,"Location",dpLocators.lst_drop_generic);
        reusableMethods.clickElement(dpLocators.btn_search,driver,"Search");
        reusableMethods.waitForElementToBeVisible(driver, 5, dpLocators.spinner, "Loading Spinner");
        reusableMethods.waitForElementToBeInVisible(driver, 30, dpLocators.spinner, "Loading Spinner");
        String records = reusableMethods.getTextOfElement(dpLocators.msg_records,driver,"Message Records");
        if(records.equalsIgnoreCase("No Records Found")){
            reusableMethods.waitForElementToBeVisible(driver, 10, dpLocators.popup_noRecords,"No Records Popup");
            String actual = reusableMethods.getTextOfElement(dpLocators.popup_noRecords,driver,"No Records popup");
            AssertionUtils.assertEquals(actual,expectedVerify,"No Records comparing");
        }
        AssertionUtils.assertEquals(records,expectedVerify, "Records Count");
        return this;
    }
}
