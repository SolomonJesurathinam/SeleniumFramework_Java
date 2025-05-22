package pages;

import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class BasePage {

    WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
    }

}
