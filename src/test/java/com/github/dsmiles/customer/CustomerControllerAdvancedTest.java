package com.github.dsmiles.customer;

import com.github.dsmiles.WebSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

// Must manually import the security config when using a SecurityFilterChain bean
@Import(WebSecurityConfig.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerAdvancedTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Forbid unauthenticated users access to protected resource")
    void shouldForbidAnonymousUsersFetchingCustomersById() throws Exception {
        mockMvc
            .perform(get("/api/customers/42"))  // Look at WebSecurityConfig
            .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Allow authenticated users access to protected resource")
    @WithMockUser
    void shouldAllowAuthenticatedUsersToFetchCustomersById() throws Exception {
        ZoneId zoneId = ZoneId.of("Europe/London");
        ZonedDateTime zdt = ZonedDateTime.of(2024, 2, 10, 16, 35, 0, 0, zoneId);
        Customer customer = new Customer(42L, "fred", zdt);

        when(customerService.getCustomerById(42L)).thenReturn(customer);

        mockMvc
            .perform(get("/api/customers/42"))  // Look at WebSecurityConfig
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(42))
            .andExpect(jsonPath("$.name").value("fred"))
            .andExpect(jsonPath("$.joinedAt").value("2024-02-10T16:35:00Z"));
    }
}
