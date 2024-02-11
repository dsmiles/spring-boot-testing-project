package com.github.dsmiles.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository interface for accessing customer data stored in a relational database.
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Retrieves the earliest joined customer.
     *
     * @return the earliest joined customer
     */
    @Query(value =
        "SELECT * " +
            "FROM customer " +
            "ORDER BY joined_at ASC " +
            "LIMIT 1", nativeQuery = true)
    Customer getEarlyBird();
}
