package tests;

import base.BaseTest;
import config.PropertiesReader;
import dataproviders.ExcelDataProvider;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.DashboardPage;
import pages.DirectoryPage;
import pages.LoginPage;
import utils.ExcelUtils;
import java.util.LinkedHashMap;

public class TestExample extends BaseTest {

//    @Test(dataProvider = "Directory", dataProviderClass = ExcelDataProvider.class)
    public void verify_directory_case(String testID){
        ExcelUtils excelUtils = new ExcelUtils();
        final LinkedHashMap<String, String> data = excelUtils.get_excel_data_from_testid(testID);

        LoginPage loginPage = new LoginPage();
        loginPage.verify_page_is_loaded()
                .login_to_application(data.get("userName"), data.get("password"));

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.verify_page_is_loaded()
                .navigate_to_directory();

        DirectoryPage directoryPage = new DirectoryPage();
        directoryPage.verify_page_is_loaded()
                .search_and_verify_employee(data.get("employee"), data.get("employee_select"),data.get("empolyee_verify"))
                .resetSearch()
                .search_and_Verify_jobTitle_NoRecords(data.get("jobTitle"),data.get("jobtitle_verify"))
                .resetSearch()
                .search_and_verify_location(data.get("location"),data.get("location_verify"));

        dashboardPage.logout();
    }

//    @Test(dataProvider = "Login", dataProviderClass = ExcelDataProvider.class)
    public void invalidTest(String testID){
        ExcelUtils excelUtils = new ExcelUtils();
        final LinkedHashMap<String, String> data = excelUtils.get_excel_data_from_testid(testID);

        LoginPage loginPage = new LoginPage();
        loginPage.verify_page_is_loaded()
                .invalid_login(data.get("userName"), data.get("password"),data.get("login_error"));
    }

    @Test(dataProvider = "Admin", dataProviderClass = ExcelDataProvider.class)
    public void admin_add_new_location(String testID){
        ExcelUtils excelUtils = new ExcelUtils();
        final LinkedHashMap<String, String> data = excelUtils.get_excel_data_from_testid(testID);

        LoginPage loginPage = new LoginPage();
        loginPage.verify_page_is_loaded()
                .login_to_application(data.get("userName"), data.get("password"));

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.verify_page_is_loaded()
                .navigate_to_admin();

        AdminPage adminPage = new AdminPage();
        adminPage.verify_page_is_loaded()
                .add_new_location(data.get("name"), data.get("city"), data.get("state"), data.get("zipcode"),
                        data.get("address"), data.get("country"), "Successfully Saved");

        dashboardPage.logout();
    }

//    @Test
    public void admin_delete_location(){
        ExcelUtils excelUtils = new ExcelUtils();
        final LinkedHashMap<String, String> data = excelUtils.get_excel_data_from_testid("TC001");

        LoginPage loginPage = new LoginPage();
        loginPage.verify_page_is_loaded()
                .login_to_application(data.get("userName"), data.get("password"));

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.verify_page_is_loaded()
                .navigate_to_admin();

        AdminPage adminPage = new AdminPage();
        adminPage.verify_page_is_loaded()
                .delete_all_locations("Successfully Deleted");

        dashboardPage.logout();
    }
}
