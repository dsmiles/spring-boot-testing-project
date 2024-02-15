package com.github.dsmiles.testcontainers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains JUnit tests that utilize Testcontainers to manage a PostgreSQL
 * database container for integration testing.
 */
@Testcontainers
class PostgreSqlTest {

    /**
     * The PostgreSQLContainer instance managed by Testcontainers for testing.
     */
    @Container
    static PostgreSQLContainer<?> postgreSQLContainer =
        new PostgreSQLContainer<>(DockerImageName.parse("postgres:13"))
            .withDatabaseName("test")
            .withUsername("adminuser")
            .withPassword("superSecret");

    @Test
    @DisplayName("Check PostgreSQL database container can be started")
    void shouldStartPostgreSQLDatabase() {
        assertTrue(postgreSQLContainer.isRunning());
    }

    /**
     * Stops the PostgreSQL container after all tests have been executed.
     */
    @AfterAll
    static void tearDown() {
        if (postgreSQLContainer != null) {
            postgreSQLContainer.stop();
        }
    }
}
