package com.github.dsmiles.integration;

import com.github.dsmiles.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
    "spring.security.user.name=duke",
    "spring.security.user.password=duke42"
})
class BasicFailureIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void shouldFailFetchingCustomersWhenDatabaseIsDown() {

        when(customerRepository.findAll()).thenThrow(new RuntimeException("Database is down :("));

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("duke", "duke42");

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> result = this.testRestTemplate
            .exchange("/api/customers", HttpMethod.GET, request, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }
}
