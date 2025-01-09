package com.epam.ai.demo.restassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class APIResponse {

    private static Response response;
    private APIResponse() {

    }

    public static Response getResponse() {
        return response;
    }



    public static Response sendRequest(HttpMethod requestType) {
        if (APIRequest.getRequestSpecification() == null) {
            //testContext.getLogger().log(LogLevel.ERROR, "Please create a valid request by APIRequst.createRequest()");
            return response;
        }

        switch (requestType) {
            case GET:
                //testContext.getLogger().log(LogLevel.INFO, "Creating a get request for : " + apiRequest.getQueryableRequestSpecification().getBaseUri());
                response = RestAssured.given().when().spec(APIRequest.getRequestSpecification()).get();
                break;
            case POST:
                System.out.println("Request spec is: "+APIRequest.getRequestSpecification());
                response = RestAssured.given().when().spec(APIRequest.getRequestSpecification()).post();
                break;
            case DELETE:
                response = RestAssured.given().when().spec(APIRequest.getRequestSpecification()).delete();
                break;
            case PUT:
                response = RestAssured.given().when().spec(APIRequest.getRequestSpecification()).put();
                break;
            case PATCH:
                response = RestAssured.given().when().spec(APIRequest.getRequestSpecification()).patch();
                break;
            case OPTIONS:
                response = RestAssured.given().when().spec(APIRequest.getRequestSpecification()).options();
                break;
            default:
                //testContext.getLogger().log(LogLevel.INFO, "Please give the correct action like GET,PUT,POST :");
                break;
        }
        return response;
    }

}
