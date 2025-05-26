package pages;

import base.BasePage;
import io.qameta.allure.Step;
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

}
