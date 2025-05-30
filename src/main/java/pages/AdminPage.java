package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pages.locators.AdminPageLocators;
import utils.AssertionUtils;
import utils.ReusableMethods;

public class AdminPage extends BasePage {

    ReusableMethods reusableMethods;
    AdminPageLocators adPLocators;

    public AdminPage(){
        reusableMethods = new ReusableMethods(this);
        adPLocators = new AdminPageLocators();
        PageFactory.initElements(driver,adPLocators);
    }

    @Step("Verify Admin Page is loaded")
    public AdminPage verify_page_is_loaded(){
        reusableMethods.waitForElementToBeVisible(driver,30, adPLocators.page_load, "Admin Page");
        return this;
    }

    @Step("Search and verify User Role")
    public AdminPage search_and_verify_userRole(String selectText, String expectedVerify){
        reusableMethods.dynamicDropdown(driver,adPLocators.drop_userRole, adPLocators.drop_section_verify,selectText,"User Role",adPLocators.generic_drop_values);
        reusableMethods.clickElement(adPLocators.btn_search,driver,"Search");
        reusableMethods.waitForElementToBeVisible(driver, 5, adPLocators.spinner, "Loading Spinner");
        reusableMethods.waitForElementToBeInVisible(driver, 30, adPLocators.spinner, "Loading Spinner");
        String records = reusableMethods.getTextOfElement(adPLocators.msg_records,driver,"Message Records");
        AssertionUtils.assertEquals(records,expectedVerify, "Records Count");
        return this;
    }

    @Step("Add new Location")
    public void add_new_location(String name, String city, String state, String zip, String address, String country, String successMsg){
        select_location();
        reusableMethods.clickElement(adPLocators.lnk_add, driver, "Add Location button");
        reusableMethods.waitForElementToBeVisible(driver, 15, adPLocators.txt_addLocation_check, "Add New Location");
        reusableMethods.enterText(name, adPLocators.input_name, driver, "Name");
        reusableMethods.enterText(city,adPLocators.input_city, driver, "City");
        reusableMethods.enterText(state, adPLocators.input_state, driver, "State");
        reusableMethods.enterText(zip, adPLocators.input_zipcode, driver, "Zipcode");
        reusableMethods.dynamicDropdown(driver, adPLocators.drop_country, adPLocators.drop_section_verify, country,"Country dropdown", adPLocators.generic_drop_values);
        reusableMethods.enterText(address, adPLocators.txtarea_address, driver, "Address");
        reusableMethods.clickElement(adPLocators.btn_save, driver, "Save");
        reusableMethods.waitForElementToBeVisible(driver, 15, adPLocators.popup_noRecords,"Popup Message");
        String message = reusableMethods.getTextOfElement(adPLocators.popup_noRecords,driver,"Popup Message");
        AssertionUtils.assertEquals(message, successMsg,"Add New Location");
    }

    private void select_location(){
        reusableMethods.clickElement(adPLocators.lnk_organization, driver, "Organization");
        reusableMethods.clickElement(adPLocators.lnk_locations, driver, "Locations");
        reusableMethods.waitForElementToBeVisible(driver, 15, adPLocators.txt_location_check, "Location Section");
    }

    @Step("Delete all locations")
    public void delete_all_locations(String deleteMsg){
        select_location();
        deleteAllRecords(deleteMsg);
    }

    private void deleteAllRecords(String deleteMsg){
        String msg = reusableMethods.getTextOfElement(adPLocators.msg_records,driver,"Records Message");
        if(msg.equalsIgnoreCase("No Records Found")){
            AssertionUtils.assertTrue(true,"All records are deleted");
            return;
        }

        if(!adPLocators.delete_location.isEmpty()){
            WebElement firstRecord = adPLocators.delete_location.get(0);
            reusableMethods.clickElement(firstRecord, driver, "Delete location");
            reusableMethods.clickElement(adPLocators.btn_delete_confirm, driver, "Delete location confirm Yes");
            String popupMsg = reusableMethods.getTextOfElement(adPLocators.popup_noRecords, driver, "Popup message");
            AssertionUtils.assertEquals(popupMsg, deleteMsg,"Delete Message");

            //recursion call
            deleteAllRecords(deleteMsg);
        }else{
            AssertionUtils.assertTrue(true,"All records are deleted");
        }
    }
}
