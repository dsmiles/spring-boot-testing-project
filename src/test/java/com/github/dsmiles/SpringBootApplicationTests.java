package com.github.dsmiles;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the Spring Boot application.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("Verify the application context loads successfully")
    void contextLoads() {
        ResponseEntity<Object> actuatorResult =
            this.testRestTemplate.getForEntity("/actuator/health", Object.class);

        assertEquals(HttpStatus.OK, actuatorResult.getStatusCode());
    }
}
