package com.github.dsmiles.testcontainers;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class contains JUnit tests that utilize Testcontainers to manage a custom Docker container,
 * specifically a Keycloak container, for integration testing.
 */
@Testcontainers
public class KeycloakTest {

    /**
     * The Keycloak container instance managed by Testcontainers for testing.
     * This container is used to run Keycloak for integration tests. It waits
     * for the Keycloak application to finish starting.
     */
    @Container
    static GenericContainer<?> keycloakContainer =
        new GenericContainer<>(DockerImageName.parse("jboss/keycloak:14.0.0"))
            .withExposedPorts(8090)
            .withStartupTimeout(Duration.ofSeconds(30))
            .waitingFor(Wait.forHttp("/auth").forStatusCode(200));

    @Test
    @DisplayName("Check the Keyclock IdP container can be started")
    void shouldStartCustomDockerContainer() {
        assertTrue(keycloakContainer.isRunning(), "Keycloak container failed to start");
    }

    /**
     * Stops the Keycloak container after all tests have been executed.
     */
    @AfterAll
    static void tearDown() {
        if (keycloakContainer != null) {
            keycloakContainer.stop();
        }
    }
}