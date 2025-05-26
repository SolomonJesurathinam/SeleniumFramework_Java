package tests;

import base.BaseTest;
import io.qameta.allure.Step;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.DashboardPage;
import pages.DirectoryPage;
import pages.LoginPage;

public class TestExample extends BaseTest {

//    @Test
    public void verify_directory_case(){
        LoginPage loginPage = new LoginPage();
        loginPage.verify_page_is_loaded()
                .login_to_application("Admin", "admin123");

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.verify_page_is_loaded()
                .navigate_to_directory();

        DirectoryPage directoryPage = new DirectoryPage();
        directoryPage.verify_page_is_loaded()
                .search_and_verify_employee("Admin", "Admin Admin123","(1) Record Found")
                .resetSearch()
                .search_and_Verify_jobTitle_NoRecords("Account Assistant","No Records Found")
                .resetSearch()
                .search_and_verify_location("Texas R&D","(4) Records Found");

        dashboardPage.logout();
    }

    @Test()
    public void invalidTest(){
        LoginPage loginPage = new LoginPage();
        loginPage.verify_page_is_loaded()
                .invalid_login("test", "test","Invalid credentials");
    }

//    @Test
    public void verify_admin_case(){
        LoginPage loginPage = new LoginPage();
        loginPage.verify_page_is_loaded()
                .login_to_application("Admin", "admin123");

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.verify_page_is_loaded()
                .navigate_to_admin();

        AdminPage adminPage = new AdminPage();
        adminPage.verify_page_is_loaded()
                .search_and_verify_userRole("Admin","(15) Records Found");

        dashboardPage.logout();
    }
}
