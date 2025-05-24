package pages;

import base.BasePage;
import org.openqa.selenium.support.PageFactory;
import pages.locators.LoginPageLocators;
import utils.AssertionUtils;
import utils.ReusableMethods;

public class LoginPage extends BasePage {

    ReusableMethods reusableMethods;
    LoginPageLocators lpLoators;

    public LoginPage() {
        reusableMethods = new ReusableMethods(this);
        lpLoators = new LoginPageLocators();
        PageFactory.initElements(driver, lpLoators);
    }

    public LoginPage verify_page_is_loaded() {
        reusableMethods.waitForElementToBeVisible(driver, 30, lpLoators.pageLoad, "Login Page Load");
        return this;
    }

    public LoginPage login_to_application(String username, String password) {
        reusableMethods.enterText(username, lpLoators.txt_userName, driver, "UserName field");
        reusableMethods.enterText(password, lpLoators.txt_password, driver, "Password field");
        reusableMethods.clickElement(lpLoators.btn_login, driver, "Login Button");
        return this;
    }

    public LoginPage invalid_login(String username, String password, String expected) {
        reusableMethods.enterText(username, lpLoators.txt_userName, driver, "UserName field");
        reusableMethods.enterText(password, lpLoators.txt_password, driver, "Password field");
        reusableMethods.clickElement(lpLoators.btn_login, driver, "Login Button");
        reusableMethods.waitForElementToBeVisible(driver, 15, lpLoators.msg_Error, "Login Error");
        String error = reusableMethods.getTextOfElement(lpLoators.msg_Error, driver, "Login error");
        AssertionUtils.assertEquals(error,expected,"Verify invalid login");
        return this;
    }

}
