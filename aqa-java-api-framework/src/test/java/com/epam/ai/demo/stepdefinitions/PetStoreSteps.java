package com.epam.ai.demo.stepdefinitions;
import com.epam.ai.demo.config.ConfigManager;
import com.epam.ai.demo.models.Pet;
import com.epam.ai.demo.restassured.APIRequest;
import com.epam.ai.demo.restassured.APIResponse;
import com.epam.ai.demo.restassured.HttpMethod;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class PetStoreSteps {
    private Response response;
    ObjectMapper mapper = new ObjectMapper();
    private Pet pet;

   /* @Given("I have the necessary details of the pet")
    public void i_have_the_necessary_details_of_the_pet() {
        RestAssured.baseURI = BASE_URL;
        pet = new Pet();
        pet.setId(1);
        pet.setName("Tommy");
        pet.setStatus("available");
    }*/

    @When("I send a POST request to the {string} endpoint with the pet's details in JSON format")
    public void i_send_a_post_request_to_the_endpoint_with_the_pet_s_details_in_json_format(String endpoint) throws JsonProcessingException {
        APIRequest.createRequest(endpoint, mapper.writeValueAsString(pet));
        response = APIResponse.sendRequest(HttpMethod.POST);
        System.out.println("S Code: "+response.getStatusCode());
    }

    @Then("the pet should be added to the store's inventory")
    public void the_pet_should_be_added_to_the_store_s_inventory() {
        System.out.println("Status code: "+ response.statusCode());
        System.out.println("Body: "+ response.body().asString());
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.asString().contains("available"));
    }

    @Given("I have the details of the pet {string},{string},{string}")
    public void iHaveTheDetailsOfThePet(String id, String name, String status) {
        pet = new Pet();
        pet.setId(Integer.parseInt(id));
        pet.setName(name);
        pet.setStatus(status);
    }

    @Given("I have the details of the pet {string},{string},{string} exists")
public void i_have_the_details_of_the_pet_exists(String id, String name, String status) {
    pet = new Pet();
    pet.setId(Integer.parseInt(id));
    pet.setName(name);
    pet.setStatus(status);
    System.out.println("Pet details: ID=" + id + ", Name=" + name + ", Status=" + status);
}
 
@When("User retrieves the pet details with ID {string}")
public void user_retrieves_the_pet_details_with_id(String id) {
// Create the endpoint URL
String endpoint = ConfigManager.getProperty("pet_store_base_url")+"pet/" + id;
 
// Create the request using APIRequest utility class
APIRequest.createRequest(endpoint); // No body needed for GET request

// Send the GET request using APIResponse utility class
response = APIResponse.sendRequest(HttpMethod.GET);

// Log the response status code
System.out.println("Status Code: " + response.getStatusCode());
}
@Then("The API should return a 200 OK response with the pet object that matches the {string},{string},{string}")
public void the_api_should_return_a_200_ok_response_with_the_pet_object_that_matches(String id, String name, String status) {
    Assert.assertEquals(200, response.getStatusCode());
    Assert.assertEquals(Integer.parseInt(id), response.jsonPath().getInt("id"));
    Assert.assertEquals(name, response.jsonPath().getString("name"));
    Assert.assertEquals(status, response.jsonPath().getString("status"));
    System.out.println("Response Body: " + response.getBody().asString());
}
@When("I send a PUT request to the {string} endpoint with the pet's updated details in JSON format")
public void i_send_a_put_request_to_the_endpoint_with_the_pet_s_updated_details_in_json_format(String endpoint) throws JsonProcessingException {
    APIRequest.createRequest(endpoint, mapper.writeValueAsString(pet));
    response = APIResponse.sendRequest(HttpMethod.PUT);
    System.out.println("Status Code: " + response.getStatusCode());
}
@Then("The API should return a 200 OK response with the updated pet object that matches the {string},{string},{string}")
public void the_api_should_return_a_200_ok_response_with_the_updated_pet_object_that_matches(String id, String name, String status) {
    Assert.assertEquals(200, response.getStatusCode());
    Pet retrievedPet = response.getBody().as(Pet.class);
    Assert.assertEquals(Long.parseLong(id), retrievedPet.getId());
    Assert.assertEquals(name, retrievedPet.getName());
    Assert.assertEquals(status, retrievedPet.getStatus());
}
}
