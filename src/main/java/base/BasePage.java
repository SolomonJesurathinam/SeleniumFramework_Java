package base;

import org.openqa.selenium.WebDriver;
import utils.DriverFactory;

public class BasePage {

    public WebDriver driver;

    public BasePage() {
        this.driver = DriverFactory.getDriver();
    }

}
