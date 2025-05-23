package pages.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPageLocators {

    @FindBy(xpath = "//h6[text()='Dashboard']")
    public WebElement load_dashboard;

    @FindBy(xpath = "//span[text()='Directory']/..")
    public WebElement lnk_directory;

    @FindBy(xpath = "//span[text()='Admin']/..")
    public WebElement lnk_admin;

    @FindBy(xpath = "//span[@class='oxd-userdropdown-tab']")
    public WebElement userProfile;

    @FindBy(xpath = "//a[text()='Logout']")
    public WebElement lnk_logout;
}
