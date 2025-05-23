package pages.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPageLocators {

    @FindBy(xpath = "//img[@alt='company-branding']")
    public WebElement pageLoad;

    @FindBy(name = "username")
    public WebElement txt_userName;

    @FindBy(name = "password")
    public WebElement txt_password;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement btn_login;

    @FindBy(xpath = "//div[@role='alert']//p")
    public WebElement msg_Error;
}
