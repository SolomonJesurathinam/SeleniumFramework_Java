package stepdefs;

import io.cucumber.java.en.Then;
import pages.AdminPage;
import utils.ExcelUtils;

import java.util.LinkedHashMap;

public class AdminPageStepDef {

    AdminPage adminPage;
    LinkedHashMap<String, String> data;

    public AdminPageStepDef(){
        adminPage = new AdminPage();
        data = ExcelUtils.getData();
    }

    @Then("Verify Admin Page is loaded")
    public void admin_page_load(){
        adminPage.verify_page_is_loaded();
    }

    @Then("Add new Location")
    public void add_new_location(){
        adminPage.add_new_location(data.get("name"), data.get("city"), data.get("state"), data.get("zipcode"),
                data.get("address"), data.get("country"), "Successfully Saved");
    }

    @Then("Delete all locations")
    public void delete_all_locations(){
        adminPage.delete_all_locations("Successfully Deleted");
    }
}
