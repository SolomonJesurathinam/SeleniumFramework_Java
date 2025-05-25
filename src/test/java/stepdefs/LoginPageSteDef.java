package stepdefs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.LoginPage;

public class LoginPageSteDef {

    LoginPage loginPage;

    public LoginPageSteDef() {
        loginPage = new LoginPage();
    }

    @Given("LoginPage is loaded")
    public void login_page_loaded() {
        loginPage.verify_page_is_loaded();
    }

    @Then("Enter invalid {string} and {string} and verify message {string}")
    public void enter_invalid_login(String username, String password, String message){
        loginPage.invalid_login(username, password, message);
    }
}
