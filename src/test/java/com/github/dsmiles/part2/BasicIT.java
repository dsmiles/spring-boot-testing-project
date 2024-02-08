package com.github.dsmiles.part2;

import org.junit.jupiter.api.Test;

/**
 * Run all Integration tests with:
 * mvn failsafe:integration-test failsafe:verify
 */
public class BasicIT {

    @Test
    void test() {
        System.out.println("running an integration test with Failsafe plugin");
    }
}
