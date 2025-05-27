package stepdefs;

import io.cucumber.java.en.Then;
import pages.DirectoryPage;

public class DirectoryPageStepDef {

    DirectoryPage directoryPage;

    public DirectoryPageStepDef(){
        directoryPage = new DirectoryPage();
    }

    @Then("Verify Directory Page is loaded")
    public void verify_directory_loaded(){
        directoryPage.verify_page_is_loaded();
    }

    @Then("Search and verify Employee {string} and {string} with results {string}")
    public void search_verify_employee(String searchText, String selectText, String expectedVerify){
        directoryPage.search_and_verify_employee(searchText, selectText, expectedVerify)
                .resetSearch();
    }

    @Then("Search and verify Jobtitle {string} with results {string}")
    public void search_verify_jobTitle(String selectText, String message){
        directoryPage.search_and_Verify_jobTitle_NoRecords(selectText, message)
                .resetSearch();
    }

    @Then("Search and verify Location {string} with results {string}")
    public void search_location(String selectText, String message){
        directoryPage.search_and_verify_location(selectText, message)
                .resetSearch();
    }
}
