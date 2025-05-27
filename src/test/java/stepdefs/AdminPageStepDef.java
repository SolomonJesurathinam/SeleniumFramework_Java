package stepdefs;

import io.cucumber.java.en.Then;
import pages.AdminPage;

public class AdminPageStepDef {

    AdminPage adminPage;

    public AdminPageStepDef(){
        adminPage = new AdminPage();
    }

    @Then("Verify Admin Page is loaded")
    public void admin_page_load(){
        adminPage.verify_page_is_loaded();
    }

    @Then("Search and verify User Role {string} with results {string}")
    public void search_verify_userRole(String selectText, String expectedVerify){
        adminPage.search_and_verify_userRole(selectText, expectedVerify);
    }
}
