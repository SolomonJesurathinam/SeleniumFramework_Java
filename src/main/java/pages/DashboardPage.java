package pages;

import org.openqa.selenium.support.PageFactory;
import pages.locators.DashboardPageLocators;
import utils.ReusableMethods;

public class DashboardPage extends BasePage{

    ReusableMethods reusableMethods;
    DashboardPageLocators dbpLocators;

    public DashboardPage(){
        reusableMethods = new ReusableMethods();
        dbpLocators = new DashboardPageLocators();
        PageFactory.initElements(driver,dbpLocators);
    }

    public DashboardPage verify_page_is_loaded(){
        reusableMethods.waitForElementToBeVisible(driver, 30, dbpLocators.load_dashboard, "Dashboard Name");
        return this;
    }

    public DashboardPage navigate_to_directory(){
        reusableMethods.clickElement(dbpLocators.lnk_directory, driver, "Directory Link");
        return this;
    }

    public DashboardPage navigate_to_admin(){
        reusableMethods.clickElement(dbpLocators.lnk_admin, driver, "Admin Link");
        return this;
    }

    public DashboardPage logout(){
        reusableMethods.clickElement(dbpLocators.userProfile, driver, "User Profile");
        reusableMethods.clickElement(dbpLocators.lnk_logout, driver, "Logout");
        return this;
    }

}
