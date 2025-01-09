package com.epam.ai.demo.restassured;

import com.epam.ai.demo.config.ConfigManager;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class APIRequestImplementationTests {

    private static final String BASE_URL = "http://example.com";
    private static final String ENDPOINT = "/v1/pets";
    private static final String REQUEST_BODY = "{ \"name\": \"Buddy\", \"type\": \"dog\", \"age\": 3 }";

    @BeforeEach
    public void setUp() {
        // Setup code if needed
    }

    @Test
    public void main_ShouldThrowRuntimeException_WhenBaseURLNotFound() {
        try (MockedStatic<ConfigManager> mockedConfigManager = Mockito.mockStatic(ConfigManager.class)) {
            mockedConfigManager.when(() -> ConfigManager.getProperty("pet_store_base_url")).thenReturn(null);

            RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
                APIRequestImplementation.main(new String[]{});
            });

            Assertions.assertEquals("Base URL not found in config properties.", exception.getMessage());
        }
    }

    @Test
    public void main_ShouldCreateRequestAndPerformGetAndPost_WhenBaseURLIsFound() {
        try (MockedStatic<ConfigManager> mockedConfigManager = Mockito.mockStatic(ConfigManager.class);
             MockedStatic<APIRequest> mockedAPIRequest = Mockito.mockStatic(APIRequest.class)) {

            mockedConfigManager.when(() -> ConfigManager.getProperty("pet_store_base_url")).thenReturn(BASE_URL);

            // Mocking the APIRequest methods
            mockedAPIRequest.when(() -> APIRequest.createRequest(BASE_URL)).thenReturn(null);
            mockedAPIRequest.when(() -> APIRequest.createRequest(ENDPOINT, REQUEST_BODY)).thenReturn(null);

            Response mockGetResponse = Mockito.mock(Response.class);
            Mockito.when(mockGetResponse.getStatusCode()).thenReturn(200);
            Mockito.when(mockGetResponse.getBody().asString()).thenReturn("GET Response Body");

            Response mockPostResponse = Mockito.mock(Response.class);
            Mockito.when(mockPostResponse.getStatusCode()).thenReturn(201);
            Mockito.when(mockPostResponse.getBody().asString()).thenReturn("POST Response Body");

            mockedAPIRequest.when(() -> APIRequest.getRequestSpecification().when().get(ENDPOINT)).thenReturn(mockGetResponse);
            mockedAPIRequest.when(() -> APIRequest.getRequestSpecification().when().post(ENDPOINT)).thenReturn(mockPostResponse);

            // Call the main method
            APIRequestImplementation.main(new String[]{});

            // Verify the interactions
            mockedAPIRequest.verify(() -> APIRequest.createRequest(BASE_URL));
            mockedAPIRequest.verify(() -> APIRequest.createRequest(ENDPOINT, REQUEST_BODY));
            mockedAPIRequest.verify(() -> APIRequest.getRequestSpecification().when().get(ENDPOINT));
            mockedAPIRequest.verify(() -> APIRequest.getRequestSpecification().when().post(ENDPOINT));
        }
    }
}
