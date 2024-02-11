package com.github.dsmiles.customer;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Service class for managing customer-related operations.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Constructs a new CustomerService with the provided CustomerRepository.
     *
     * @param customerRepository the repository for accessing customer data
     */
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id the ID of the customer to retrieve
     * @return the customer with the specified ID
     * @throws CustomerNotFoundException if no customer with the specified ID is found
     */
    public Customer getCustomerById(Long id) {
        return customerRepository
            .findById(id)
            .orElseThrow(CustomerNotFoundException::new);
    }
}
