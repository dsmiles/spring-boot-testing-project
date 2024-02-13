package com.github.dsmiles.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for advanced functionality of the {@link CustomerRepository} class.
 */
@DataJpaTest
public class CustomerRepositoryAdvancedTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Return Customer That Joined the Earliest")
    void shouldReturnCustomerThatJoinedTheEarliest() {
        // Given
        customerRepository.deleteAll();

        Customer customerOne = new Customer("duke", ZonedDateTime.now());
        Customer customerTwo = new Customer("anna", ZonedDateTime.now().minusMinutes(42));
        Customer customerThree = new Customer("mike", ZonedDateTime.now().minusDays(42));

        customerRepository.saveAll(List.of(customerOne, customerTwo, customerThree));

        // When
        Customer result = customerRepository.getEarlyBird();

        // Then
        assertEquals("mike", result.getName());
    }

    @Test
    @DisplayName("Return Correct Number of Customers")
    void shouldReturnCorrectNumberOfCustomers() {
        // Given
        customerRepository.deleteAll();

        Customer customerOne = new Customer("John", ZonedDateTime.now());
        Customer customerTwo = new Customer("Jane", ZonedDateTime.now());

        customerRepository.saveAll(List.of(customerOne, customerTwo));

        // When
        List<Customer> customers = customerRepository.findAll();

        // Then
        assertEquals(2, customers.size());
    }

    @Test
    @DisplayName("Find Customer by Name")
    void shouldFindCustomerByName() {
        // Given
        customerRepository.deleteAll();

        Customer customer = new Customer("John", ZonedDateTime.now());
        customerRepository.save(customer);

        // When
        Customer foundCustomer = customerRepository.findByName("John");

        // Then
        assertNotNull(foundCustomer);
        assertEquals("John", foundCustomer.getName());
    }
}
