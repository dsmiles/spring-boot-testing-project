package com.github.dsmiles.assertions;

import com.github.dsmiles.models.HelloWorld;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.DOMDifferenceEngine;
import org.xmlunit.diff.DifferenceEngine;

import javax.xml.transform.Source;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
@DisplayName("Demonstrate different assertion libraries")
public class AssertionLibraryTest {

    @Test
    @DisplayName("JUnit 5 Assertion")
    void helloWorldJUnit5() {
        assertEquals("hello world!", "HELLO WORLD!".toLowerCase());
    }

    @Test
    @DisplayName("Mockito Assertion")
    void helloWorldMockito() {
        HelloWorld mockedHello = Mockito.mock(HelloWorld.class);

        // My HelloWorld class actually returns different text
        Mockito.when(mockedHello.getText()).thenReturn("Hello World!");

        // Verify the mock replaced the returned text string
        assertEquals("Hello World!", mockedHello.getText());

        // Verify that getText() has been called at least once
        Mockito.verify(mockedHello).getText();
    }

    @Test
    @DisplayName("AssertJ Assertion")
    void helloWorldAssertJ() {
        Assertions.assertThat("hello world!")
            .isEqualToIgnoringCase("HELLO WORLD!");
    }

    @Test
    @DisplayName("Hamcrest Assertion")
    void helloWorldHamcrest() {
        assertThat("hello world!", equalToIgnoringCase("HELLO WORLD!"));
    }

    @Test
    @DisplayName("JsonAssert Assertion")
    void helloWorldJsonAssert() throws Exception {
        String result = "{name: 'fred', age: 42}";

        // It is recommended that you leave strictMode off, so your tests will be less
        // brittle. Turn it on if you need to enforce a particular order for arrays, or
        // if you want to ensure that the actual JSON does not have any fields beyond
        // what's expected.
        JSONAssert.assertEquals("{name: 'fred'}", result, false);
    }

    @Test
    @DisplayName("JsonPath Assertion")
    void helloWorldJsonPath() {
        String result = "{\"age\":\"42\", \"name\": \"fred\", \"tags\":[\"java\", \"jdk\"]}";

        // Using JUnit 5 Assertions
        assertEquals(2, JsonPath.parse(result).read("$.tags.length()", Long.class));
        assertEquals("fred", JsonPath.parse(result).read("$.name", String.class));
    }

    @Test
    @DisplayName("XmlUnit Assertion")
    void helloWorldXmlUnit() {
        Source expected = Input.fromString("<invoices></invoices>").build();
        Source actual = Input.fromString("<customers></customers>").build();

        DifferenceEngine diff = new DOMDifferenceEngine();

        diff.addDifferenceListener((comparison, outcome) ->
                Assertions.fail("XML documents are not similar: " + comparison));

        assertThrows(AssertionError.class, () -> diff.compare(expected, actual));
    }
}
