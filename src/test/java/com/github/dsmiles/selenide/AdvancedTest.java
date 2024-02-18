package com.github.dsmiles.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains a basic Selenide test that accesses a dashboard page and submits a form.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdvancedTest {

    private static final Logger LOG = LoggerFactory.getLogger(AdvancedTest.class);

    private static ChromeOptions chromeOptions;

    static BrowserWebDriverContainer<?> webDriverContainer =
        new BrowserWebDriverContainer<>()
            .withLogConsumer(new Slf4jLogConsumer(LOG))
            .withCapabilities(chromeOptions);

    /**
     * Configures Chrome driver settings before running the tests.
     *
     * @param environment the environment properties
     */
    @BeforeAll
    static void configureChromeDriver(@Autowired Environment environment) {
        // Retrieve the server port from the environment
        Integer port = environment.getProperty("local.server.port", Integer.class);

        // Expose the server port to Testcontainers
        Testcontainers.exposeHostPorts(port);

        // Configure Chrome options for WebDriver
        chromeOptions = new ChromeOptions()
            .addArguments("--no-sandbox")
            .addArguments("--disable-dev-shm-usage")
            .addArguments("--remote-allow-origins=*");

        // Start the WebDriver container
        webDriverContainer.start();

        // Set the base URL and reports folder for Selenide configuration
        Configuration.baseUrl = String.format("http://host.testcontainers.internal:%d", port);
        Configuration.reportsFolder = "target/selenide-screenshots";
    }

    /**
     * Closes the WebDriver after all tests have been executed.
     */
    @AfterAll
    static void cleanUp() {
        if (hasWebDriverStarted()) {
            getWebDriver().manage().deleteAllCookies();
            WebDriverRunner.closeWebDriver();
        }
    }

    @Test
    void shouldDisplayBook() {

        // Create a new RemoteWebDriver instance using the Selenium address and Chrome options
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(
            webDriverContainer.getSeleniumAddress(),
            chromeOptions,
            false
        );

        // Set the implicit wait timeout for the WebDriver instance
        remoteWebDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Set the WebDriver instance for Selenide
        WebDriverRunner.setWebDriver(remoteWebDriver);

        // Open the dashboard page
        Selenide.open("/dashboard");

        // Verify that the page title is "Dashboard"
        assertEquals("Dashboard", Selenide.title());

        // Find the last and first name and set the values
        $(By.id("lname")).val("Mike");
        $(By.id("fname")).val("Duke");

        // Submit the form for processing
        $(By.id("submit")).click();

        // Take a screenshot after form submission
        String filename = screenshot("advanced-selenide-test-post-submit");

        // Print the filename of the screenshot to the console
        System.out.println("Screenshot filename: " + filename);

        // Log the filename of the screenshot at DEBUG and INFO levels
        LOG.debug("Screenshot filename: {}", filename);
        LOG.info("Screenshot filename: {}", filename);
    }
}
