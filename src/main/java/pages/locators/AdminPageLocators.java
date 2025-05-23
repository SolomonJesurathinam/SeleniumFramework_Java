package pages.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class AdminPageLocators {

    @FindBy(xpath = "//span[@class='oxd-topbar-header-breadcrumb']")
    public WebElement page_load;

    @FindBy(xpath = "//label[text()='User Role']/../../div[2]")
    public WebElement drop_userRole;

    @FindBy(xpath = "//div[@role='listbox']")
    public WebElement drop_section_verify;

    @FindBy(xpath = "//div[@class='oxd-select-option']")
    public List<WebElement> generic_drop_values;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement btn_search;

    @FindBy(xpath = "//button[@type='reset']")
    public WebElement btn_reset;

    @FindBy(xpath = "//div[@class='oxd-loading-spinner']")
    public WebElement spinner;

    @FindBy(xpath = "(//span[@class='oxd-text oxd-text--span'])[1]")
    public WebElement msg_records;
}
