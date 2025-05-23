package pages.locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DirectoryPageLocators {

    @FindBy(xpath = "//h6[text()='Directory']")
    public WebElement page_load_verify;

    @FindBy(xpath = "//input[@placeholder='Type for hints...']")
    public WebElement txt_empName;

    @FindBy(xpath = "//div[@class='oxd-autocomplete-option']")
    public List<WebElement> lst_empDropdown;

    @FindBy(xpath = "//label[text()='Job Title']/../../div[2]/div/div")
    public WebElement drop_jobTitle;

    @FindBy(xpath = "//div[@class='oxd-autocomplete-option']")
    public WebElement dropFirstEle;

    @FindBy(xpath = "//div[@class='oxd-select-option']")
    public List<WebElement> lst_drop_generic;

    @FindBy(xpath = "//div[@role='listbox']")
    public WebElement dropList_generic_verify;

    @FindBy(xpath = "//div[@id='oxd-toaster_1']//p[2]")
    public WebElement popup_noRecords;

    @FindBy(xpath = "//label[text()='Location']/../../div[2]/div/div")
    public WebElement drop_location;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement btn_search;

    @FindBy(xpath = "//button[@type='reset']")
    public WebElement btn_reset;

    @FindBy(xpath = "//span[@class='oxd-text oxd-text--span']")
    public WebElement msg_records;

    @FindBy(xpath = "//div[@class='oxd-loading-spinner']")
    public WebElement spinner;
}
