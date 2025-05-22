package pages.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageLocators {

    @FindBy(xpath = "//div[@class='login_logo']")
    public WebElement pageLoad;

    @FindBy(id = "user-name")
    public WebElement txt_userName;

    @FindBy(id = "password")
    public WebElement txt_password;

    @FindBy(id = "login-button")
    public WebElement btn_login;

    @FindBy(xpath = "//h3[@data-test='error']")
    public WebElement msg_Error;
}
