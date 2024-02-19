package com.github.dsmiles.integration;

import com.github.dsmiles.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

/**
 * Configures the test to start with a random port and a fully running servlet environment
 * Specifies additional properties for the Spring application context
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
    "spring.security.user.name=john",
    "spring.security.user.password=woodley93"
    })
public class BasicIT {

    @Autowired  // Injects a TestRestTemplate capable of making HTTP requests to the application
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Test fetching list of customers")
    void shouldFetchListOfCustomers() {

        // Sets up HTTP headers with basic authentication credentials
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("john", "woodley93");

        // Creates an HTTP request entity with the configured headers
        HttpEntity<List<Customer>> request = new HttpEntity<>(headers);

        // Executes an HTTP GET request to the endpoint and captures the response
        ResponseEntity<List<Customer>> result = this.testRestTemplate
            .exchange("/api/customers", HttpMethod.GET, request, new ParameterizedTypeReference<List<Customer>>() {
            });

        // Asserts that the HTTP status code of the response is HttpStatus.OK (200)
        assertEquals(HttpStatus.OK, result.getStatusCode());
     }
}
