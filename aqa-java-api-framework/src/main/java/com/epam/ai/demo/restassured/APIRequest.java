package com.epam.ai.demo.restassured;
import com.epam.ai.demo.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIRequest {

    private static RequestSpecification requestSpecification = RestAssured.given();
    private static Response response;
    private APIRequest() {
    }

    public static RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    public static RequestSpecification createRequest(String baseURL) {
        requestSpecification = new RequestSpecBuilder().setBaseUri(baseURL).setContentType(ContentType.JSON)
                .build().log().all();
        return requestSpecification;
    }

    public static RequestSpecification createRequest(String endpoint, String body) {
        requestSpecification = new RequestSpecBuilder().setBaseUri(ConfigManager.getProperty("pet_store_base_url"))
                .setBasePath(endpoint).setContentType(ContentType.JSON).setBody(body)
                .build().log().all();
        return requestSpecification;
    }
}
