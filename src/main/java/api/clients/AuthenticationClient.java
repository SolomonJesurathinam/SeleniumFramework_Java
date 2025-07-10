package api.clients;

import api.constants.ApiEndpoints;
import api.utils.ApiUtils;
import config.PropertiesReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.LinkedHashMap;
import java.util.Map;

public class AuthenticationClient {

    public static ThreadLocal<String> threadLocalToken = new ThreadLocal<>();
    static PropertiesReader propertiesReader = new PropertiesReader();
    static ApiUtils apiUtils = new ApiUtils(AuthenticationClient.class);

    public static String getToken(){
        if(threadLocalToken.get() == null){
            threadLocalToken.set(login("apiTest1@gmail.com","apiTestPassword@2580123"));
        }
        return threadLocalToken.get();
    }

    private static String login(String username, String password){
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("email",username);
        data.put("password",password);

        Response response = RestAssured.given()
                .baseUri(propertiesReader.getProperty("baseUrl"))
                .contentType("application/json")
                .body(apiUtils.mapToJson(data))
                .post(ApiEndpoints.LOGIN.getPath());

        response.then().statusCode(200);
        return response.jsonPath().getString("data.token");
    }
}
