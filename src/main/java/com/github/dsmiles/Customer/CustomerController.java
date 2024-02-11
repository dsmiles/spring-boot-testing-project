package com.github.dsmiles.Customer;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling customer-related requests.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Constructs a new CustomerController with the provided CustomerService.
     *
     * @param customerService the service for managing customer data
     */
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    @GetMapping
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param id the ID of the customer to retrieve
     * @return the customer with the specified ID
     */
    @GetMapping("/{id}")
    public Customer getById(@PathVariable("id") Long id) {
        return customerService.getCustomerById(id);
    }
}
