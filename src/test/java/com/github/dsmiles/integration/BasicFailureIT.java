package com.github.dsmiles.integration;

import com.github.dsmiles.customer.CustomerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * Configures the test to start with a random port and a fully running servlet environment
 * Specifies additional properties for the Spring application context
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
    "spring.security.user.name=duke",
    "spring.security.user.password=duke42"
})
class BasicFailureIT {

    @Autowired  // Injects a TestRestTemplate capable of making HTTP requests to the application
    private TestRestTemplate testRestTemplate;

    @MockBean   // Mocks the CustomerRepository to simulate database interactions
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Test fetching customers when database is down")
    void shouldFailFetchingCustomersWhenDatabaseIsDown() {

        // Mocks the behavior of the customerRepository to throw a RuntimeException when findAll() is called
        when(customerRepository.findAll()).thenThrow(new RuntimeException("Database is down :("));

        // Sets up HTTP headers with basic authentication credentials
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("duke", "duke42");

        // Creates an HTTP request entity with the configured headers
        HttpEntity<String> request = new HttpEntity<>(headers);

        // Executes an HTTP GET request to the endpoint and captures the response
        ResponseEntity<String> result = this.testRestTemplate
            .exchange("/api/customers", HttpMethod.GET, request, String.class);

        // Asserts that the HTTP status code of the response is HttpStatus.INTERNAL_SERVER_ERROR (500)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}
