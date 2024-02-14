package com.github.dsmiles.plugins;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Run all Unit tests with:
 * mvn test
 */
public class BasicUnitTest {

    @Test
    @DisplayName("Integration test run by Surefire plugin")
    void test() {
        System.out.println("Running a unit test with the Maven Surefire Plugin");
    }
}
