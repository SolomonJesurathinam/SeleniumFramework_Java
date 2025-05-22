package testCases;

import org.testng.annotations.Test;
import pages.LoginPage;

public class TestExample extends BaseTest{

    @Test
    public void test1(){
        LoginPage loginPage = new LoginPage();
        loginPage.verify_page_is_loaded();
        loginPage.login_to_application("standard_user", "secret_sauce");
    }

    @Test
    public void invalidTest(){
        LoginPage loginPage = new LoginPage();
        loginPage.verify_page_is_loaded();
        loginPage.invalid_login("locked_out_user", "secret_sauce");
    }
}
