package pages;

import base.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.support.PageFactory;
import pages.locators.DashboardPageLocators;
import utils.ReusableMethods;

public class DashboardPage extends BasePage {

    ReusableMethods reusableMethods;
    DashboardPageLocators dbpLocators;

    public DashboardPage(){
        reusableMethods = new ReusableMethods(this);
        dbpLocators = new DashboardPageLocators();
        PageFactory.initElements(driver,dbpLocators);
    }

    @Step("Verify Dashboard Page is loaded")
    public DashboardPage verify_page_is_loaded(){
        reusableMethods.waitForElementToBeVisible(driver, 30, dbpLocators.load_dashboard, "Dashboard Name");
        return this;
    }

    @Step("Navigate to Directory")
    public DashboardPage navigate_to_directory(){
        reusableMethods.clickElement(dbpLocators.lnk_directory, driver, "Directory Link");
        return this;
    }

    @Step("Navigate to Admin")
    public DashboardPage navigate_to_admin(){
        reusableMethods.clickElement(dbpLocators.lnk_admin, driver, "Admin Link");
        return this;
    }

    @Step("Logout of application")
    public DashboardPage logout(){
        reusableMethods.clickElement(dbpLocators.userProfile, driver, "User Profile");
        reusableMethods.clickElement(dbpLocators.lnk_logout, driver, "Logout");
        return this;
    }

}
