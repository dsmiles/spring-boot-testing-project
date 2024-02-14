package com.github.dsmiles.plugins;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Run all Integration tests with:
 * mvn failsafe:integration-test failsafe:verify
 */
public class BasicIntegrationIT {

    @Test
    @DisplayName("Integration test run by Failsafe plugin")
    void test() {
        System.out.println("Running an integration test with Failsafe plugin");
    }
}
