package base;

import api.clients.AuthenticationClient;
import api.utils.RestAssuredConfiguration;
import config.PropertiesReader;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public class BaseAPI {

    public String token;

    @BeforeSuite
    public void setupSuite() {
        PropertiesReader propertiesReader = new PropertiesReader();
        String baseUrl = propertiesReader.getProperty("baseUrl");
        RestAssuredConfiguration.setup(baseUrl);
    }

    @BeforeClass
    public void setup() {
        token = AuthenticationClient.getToken();
    }

}
