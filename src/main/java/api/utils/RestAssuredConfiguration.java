package api.utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import lombok.Getter;

public class RestAssuredConfiguration {

    @Getter
    private static RequestSpecification requestSpec;
    @Getter
    private static ResponseSpecification responseSpec;

    public static void setup(String baseUri){
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setContentType(ContentType.JSON)
                .addHeader("Accept","application/json")
                .log(LogDetail.ALL)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();

        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;
    }
}
