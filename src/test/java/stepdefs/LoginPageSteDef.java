package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.LoginPage;
import utils.ExcelUtils;

import java.util.LinkedHashMap;

public class LoginPageSteDef {

    LoginPage loginPage;
    LinkedHashMap<String, String> data;

    public LoginPageSteDef() {
        loginPage = new LoginPage();
        data = ExcelUtils.getData();
    }

    @Given("LoginPage is loaded")
    public void login_page_loaded() {
        loginPage.verify_page_is_loaded();
    }

    @Then("Enter invalid {string} and {string} and verify message {string}")
    public void enter_invalid_login(String username, String password, String message){
        loginPage.invalid_login(username, password, message);
    }

    @Then("Enter valid credentials and login")
    public void enter_valid_login(){
        loginPage.login_to_application(data.get("userName"), data.get("password"));
    }
}
