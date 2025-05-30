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

    @FindBy(xpath = "//span[text()='Organization ']")
    public WebElement lnk_organization;

    @FindBy(xpath = "//a[text()='Locations']")
    public WebElement lnk_locations;

    @FindBy(xpath = "//h5[text()='Locations']")
    public WebElement txt_location_check;

    @FindBy(xpath = "//button[text()=' Add ']")
    public WebElement lnk_add;

    @FindBy(xpath = "//h6[text()='Add Location']")
    public WebElement txt_addLocation_check;

    @FindBy(xpath = "(//form[@class='oxd-form']//input)[1]")
    public WebElement input_name;

    @FindBy(xpath = "(//form[@class='oxd-form']//input)[2]")
    public WebElement input_city;

    @FindBy(xpath = "(//form[@class='oxd-form']//input)[3]")
    public WebElement input_state;

    @FindBy(xpath = "(//form[@class='oxd-form']//input)[4]")
    public WebElement input_zipcode;

    @FindBy(xpath = "(//form[@class='oxd-form']//input)[5]")
    public WebElement input_phone;

    @FindBy(xpath = "(//form[@class='oxd-form']//textarea)[1]")
    public WebElement txtarea_address;

    @FindBy(xpath = "//label[text()='Country']/../../div[2]/div/div")
    public WebElement drop_country;

    @FindBy(xpath = "//button[text()=' Save ']")
    public WebElement btn_save;

    @FindBy(xpath = "//div[@id='oxd-toaster_1']//p[2]")
    public WebElement popup_noRecords;

    @FindBy(xpath = "//div/button[@class='oxd-icon-button oxd-table-cell-action-space'][1]")
    public List<WebElement> delete_location;

    @FindBy(xpath = "//button[text()=' Yes, Delete ']")
    public WebElement btn_delete_confirm;
}
