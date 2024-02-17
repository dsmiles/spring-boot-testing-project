package com.github.dsmiles.integration;

import com.github.dsmiles.customer.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
    "spring.security.user.name=john",
    "spring.security.user.password=woodley93"
})
public class ReusedBasicIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldGetCustomerById() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("john", "woodley93");

        HttpEntity<Customer> request = new HttpEntity<>(headers);

        ResponseEntity<Customer> result = this.testRestTemplate
            .exchange("/api/customers/1", HttpMethod.GET, request, Customer.class);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
