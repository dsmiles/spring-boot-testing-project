package com.github.dsmiles;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for configuring web security.
 */
@Configuration
public class WebSecurityConfig {

    /**
     * Configures HTTP security for the application.
     *
     * @param http the HttpSecurity object to configure
     * @return the SecurityFilterChain configured for the application
     * @throws Exception if an error occurs while configuring security
     */
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
            // Allow or deny access to the resources as required
            .requestMatchers(HttpMethod.GET, "/api/customers").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/customers/*").authenticated()
            .requestMatchers(HttpMethod.GET, "/dashboard").permitAll()
            .requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic();

        return http.build();
    }
}
