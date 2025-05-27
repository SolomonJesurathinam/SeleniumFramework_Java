package stepdefs;

import io.cucumber.java.en.Then;
import pages.DashboardPage;

public class DashboardStepDef {

    DashboardPage dashboardPage;

    public DashboardStepDef(){
        dashboardPage = new DashboardPage();
    }

    @Then("Verify Dashboard page is loaded")
    public void verify_dashboard_is_loaded(){
        dashboardPage.verify_page_is_loaded();
    }

    @Then("Navigate to Directory Page")
    public void navigate_to_directory(){
        dashboardPage.navigate_to_directory();
    }

    @Then("Navigate to Admin Page")
    public void navigate_to_admin(){
        dashboardPage.navigate_to_admin();
    }

    @Then("Logout of application")
    public void logout(){
        dashboardPage.logout();
    }
}
