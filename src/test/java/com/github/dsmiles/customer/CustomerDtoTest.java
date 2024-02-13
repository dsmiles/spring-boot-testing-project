package com.github.dsmiles.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * JUnit test class for testing the serialization of {@link CustomerDto} objects using Jackson.
 * This test class focuses on verifying that the serialization produces the expected JSON content,
 * including custom attribute names and date format.
 */
@JsonTest
public class CustomerDtoTest {

    /**
     * Autowired JacksonTester for serializing CustomerDto objects to JSON format.
     */
    @Autowired
    private JacksonTester<CustomerDto> json;

    /**
     * Test method to verify that a CustomerDto object serializes correctly with custom attribute names and date format.
     * @throws Exception if there is an error during the serialization process.
     */
    @Test
    @DisplayName("Serialize CustomerDto with Custom Attribute Names and Date Format")
    void shouldSerialiseWithCustomAttributeNamesAndDateFormat() throws Exception {
        // Given a Customer object with a specific name and ZonedDateTime
        ZoneId zoneId = ZoneId.of("Europe/London");
        ZonedDateTime zdt = ZonedDateTime.of(2024, 2, 13, 20, 25, 0, 0, zoneId);
        Customer customer = new Customer("john", zdt);

        // Create a CustomerDto object using the Customer
        CustomerDto customDto = new CustomerDto(customer);

        // Serialize the CustomerDto object into JSON format using JacksonTester
        JsonContent<CustomerDto> result = json.write(customDto);

        // Assertions to verify the JSON content (.@ is root level)
        assertThat(result).hasJsonPathStringValue("@.member_since");
        assertThat(result).hasJsonPathStringValue("@.customerName");
        assertThat(result).extractingJsonPathStringValue("@.member_since").isEqualTo("02-13 20:25");
    }
}
