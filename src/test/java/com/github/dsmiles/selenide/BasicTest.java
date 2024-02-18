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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.screenshot;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains a basic Selenide test that accesses a dashboard page and submits a form.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BasicTest {

    /**
     * Configures Chrome driver settings before running the tests.
     *
     * @param environment the environment properties
     */
    @BeforeAll
    static void configureChromeDriver(@Autowired Environment environment) {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments(
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--headless",
            "--disable-gpu",
            "--disable-extensions",
            "--remote-allow-origins=*");

        Configuration.browserCapabilities = chromeOptions;
        Configuration.reportsFolder = "target/selenide-screenshots";

        Integer port = environment.getProperty("local.server.port", Integer.class);
        Configuration.baseUrl = "http://localhost:" + port;
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
    @DisplayName("Verify accessing dashboard and submit form")
    void shouldAccessDashboardAndSubmitForm() {

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
        String filename = screenshot("basic-selenide-test-post-submit");

        // Print the filename of the screenshot to the console
        System.out.println("Screenshot filename: " + filename);
    }
}
