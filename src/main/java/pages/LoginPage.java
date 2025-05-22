package pages;

import org.openqa.selenium.support.PageFactory;
import pages.locators.LoginPageLocators;
import utils.AssertionUtils;
import utils.ReusableMethods;

public class LoginPage extends BasePage {

    ReusableMethods reusableMethods;
    LoginPageLocators lpLoators;

    public LoginPage() {
        reusableMethods = new ReusableMethods();
        lpLoators = new LoginPageLocators();
        PageFactory.initElements(driver, lpLoators);
    }

    public void verify_page_is_loaded() {
        reusableMethods.waitForElementToBeVisible(driver, 30, lpLoators.pageLoad, "Login Page Load");
    }

    public void login_to_application(String username, String password) {
        reusableMethods.enterText(username, lpLoators.txt_userName, driver, "UserName field");
        reusableMethods.enterText(password, lpLoators.txt_password, driver, "Password field");
        reusableMethods.clickElement(lpLoators.btn_login, driver, "Login Button");
    }

    public void invalid_login(String username, String password) {
        reusableMethods.enterText(username, lpLoators.txt_userName, driver, "UserName field");
        reusableMethods.enterText(password, lpLoators.txt_password, driver, "Password field");
        reusableMethods.clickElement(lpLoators.btn_login, driver, "Login Button");
        reusableMethods.waitForElementToBeVisible(driver, 15, lpLoators.msg_Error, "Login Error");
        String error = reusableMethods.getTextOfElement(lpLoators.msg_Error, driver, "Login error");
        AssertionUtils.assertEquals(error,"Epic sadface: Sorry, this user has been locked out.","Verify invalid login");
    }

}
