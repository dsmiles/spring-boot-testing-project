package com.github.dsmiles.Customer;

import com.github.dsmiles.WebSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

// Must manually import the security config when using a SecurityFilterChain bean
@Import(WebSecurityConfig.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Check MockMvc has been injected")
    void shouldCreateMockMvc() {
        assertNotNull(mockMvc);
    }

    @Test
    @DisplayName("Check 200 status OK returned on GET")
    void shouldGet200() throws Exception {
        mockMvc
            .perform(get("/api/customers"))     // Look at WebSecurityConfig
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Check a list of customers returned")
    void shouldReturnListOfCustomers() throws Exception {
        ZoneId zoneId = ZoneId.of("Europe/London");
        ZonedDateTime zdt = ZonedDateTime.of(2024, 2, 10, 16, 35, 0, 0, zoneId);
        Customer customer = new Customer(42L, "fred", zdt);

        when(customerService.getAllCustomers()).thenReturn(List.of(customer));

        mockMvc
            .perform(get("/api/customers"))     // Look at WebSecurityConfig
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].id").value(42))
            .andExpect(jsonPath("$[0].name").value("fred"))
            .andExpect(jsonPath("$[0].joinedAt").value("2024-02-10T16:35:00Z"));
    }

    @Test
    @DisplayName("Check list of customers returned")
    void shouldReturnListOfCustomersAlternate() throws Exception {
        ZoneId zoneId = ZoneId.of("Europe/London");
        ZonedDateTime zdt = ZonedDateTime.of(2024, 2, 10, 16, 35, 0, 0, zoneId);

        Customer customer = new Customer();
        customer.setId(42L);
        customer.setName("fred");
        customer.setJoinedAt(zdt);

        when(customerService.getAllCustomers()).thenReturn(List.of(customer));

        mockMvc
            .perform(get("/api/customers"))     // Look at WebSecurityConfig
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].id").value(42))
            .andExpect(jsonPath("$[0].name").value("fred"))
            .andExpect(jsonPath("$[0].joinedAt").value("2024-02-10T16:35:00Z"));
    }
}
