package com.github.dsmiles.Customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    void shouldGet200() throws Exception {
        this
            .mockMvc
            .perform(get("/api/customers"))
            .andExpect(status().isOk());
    }

    @Test
    void shouldReturnListOfCustomers() throws Exception {
        ZoneId zoneId = ZoneId.of("Europe/London");
        ZonedDateTime zdt = ZonedDateTime.of(2024, 2, 10, 16, 35, 0, 0, zoneId);
        Customer customer = new Customer(42L, "fred", zdt);

        when(customerService.getAllCustomers()).thenReturn(List.of(customer));

        this
            .mockMvc
            .perform(get("/api/customers"))
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].id").value(42))
            .andExpect(jsonPath("$[0].name").value("fred"))
            .andExpect(jsonPath("$[0].joinedAt").value("2024-02-10T16:35:00Z"));
    }

    @Test
    void shouldReturnListOfCustomersAlternate() throws Exception {
        ZoneId zoneId = ZoneId.of("Europe/London");
        ZonedDateTime zdt = ZonedDateTime.of(2024, 2, 10, 16, 35, 0, 0, zoneId);

        Customer customer = new Customer();
        customer.setId(42L);
        customer.setName("fred");
        customer.setJoinedAt(zdt);

        when(customerService.getAllCustomers()).thenReturn(List.of(customer));

        this
            .mockMvc
            .perform(get("/api/customers"))
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].id").value(42))
            .andExpect(jsonPath("$[0].name").value("fred"))
            .andExpect(jsonPath("$[0].joinedAt").value("2024-02-10T16:35:00Z"));
    }
}
