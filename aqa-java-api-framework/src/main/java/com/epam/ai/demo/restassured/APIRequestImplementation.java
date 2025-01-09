package com.epam.ai.demo.restassured;

import com.epam.ai.demo.config.ConfigManager;
import io.restassured.response.Response;

public class APIRequestImplementation {

    public static void main(String[] args) {
        // Example: Base URL from config properties
        String baseURL = ConfigManager.getProperty("pet_store_base_url");

        if (baseURL == null) {
            throw new RuntimeException("Base URL not found in config properties.");
        }

        // Create a request using the base URL
        APIRequest.createRequest(baseURL);

        // Example endpoint and request body
        String endpoint = "/v1/pets";
        String requestBody = "{ \"name\": \"Buddy\", \"type\": \"dog\", \"age\": 3 }";

        // Create a request with an endpoint and body
        APIRequest.createRequest(endpoint, requestBody);

        // Perform a GET request
        Response getResponse = APIRequest.getRequestSpecification()
                .when()
                .get(endpoint);

        // Print GET response details
        System.out.println("GET Response Status Code: " + getResponse.getStatusCode());
        System.out.println("GET Response Body: " + getResponse.getBody().asString());

        // Perform a POST request
        Response postResponse = APIRequest.getRequestSpecification()
                .when()
                .post(endpoint);

        // Print POST response details
        System.out.println("POST Response Status Code: " + postResponse.getStatusCode());
        System.out.println("POST Response Body: " + postResponse.getBody().asString());
    }
}
