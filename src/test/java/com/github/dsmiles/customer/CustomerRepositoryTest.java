package com.github.dsmiles.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the {@link CustomerRepository} class.
 */
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Verify Spring application context loads successfully")
    void contextLoads() {
        assertNotNull(customerRepository);
        assertNotNull(testEntityManager);
    }

    @Test
    @DisplayName("Save and retrieve JPA entity")
    void shouldSaveAndRetrieveJpaEntity() {
        // Given
        Customer customer = new Customer();
        customer.setName("mike");
        customer.setJoinedAt(ZonedDateTime.now());

        // When
        Customer result = testEntityManager.persistFlushFind(customer);

        // Then
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("Update JPA entity")
    void shouldUpdateJpaEntity() {
        // Given
        Customer customer = new Customer();
        customer.setName("john");
        customer.setJoinedAt(ZonedDateTime.now());
        Customer savedCustomer = testEntityManager.persistFlushFind(customer);

        // When
        savedCustomer.setName("updated name");
        Customer updatedCustomer = testEntityManager.persistFlushFind(savedCustomer);

        // Then
        assertEquals("updated name", updatedCustomer.getName());
    }

    @Test
    @DisplayName("Delete JPA entity")
    void shouldDeleteJpaEntity() {
        // Given
        Customer customer = new Customer();
        customer.setName("jane");
        customer.setJoinedAt(ZonedDateTime.now());
        Customer savedCustomer = testEntityManager.persistFlushFind(customer);

        // When
        testEntityManager.remove(savedCustomer);

        // Then
        assertNull(testEntityManager.find(Customer.class, savedCustomer.getId()));
    }
}
