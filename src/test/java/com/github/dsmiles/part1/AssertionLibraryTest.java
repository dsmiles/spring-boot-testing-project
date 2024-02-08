package com.github.dsmiles.part1;

import com.github.dsmiles.models.HelloWorld;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.api.Assertions;
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

public class AssertionLibraryTest {

    @Test
    void helloWorldJUnit5() {
        assertEquals("hello world!", "HELLO WORLD!".toLowerCase());
    }

    @Test
    void helloWorldMockito() {
        HelloWorld mockedHello = Mockito.mock(HelloWorld.class);

        // My HelloWorld class actually returns different text
        Mockito.when(mockedHello.getText()).thenReturn("Hello World!");

        assertEquals("Hello World!", mockedHello.getText());
    }

    @Test
    void helloWorldAssertJ() {
        Assertions.assertThat("hello world!")
            .isEqualToIgnoringCase("HELLO WORLD!");
    }

    @Test
    void helloWorldHamcrest() {
        assertThat("hello world!", equalToIgnoringCase("HELLO WORLD!"));
    }

    @Test
    void helloWorldJsonAssert() throws Exception {
        String result = "{name: 'fred', age: 42}";

        // It is recommended that you leave strictMode off, so your tests will be less
        // brittle. Turn it on if you need to enforce a particular order for arrays, or
        // if you want to ensure that the actual JSON does not have any fields beyond
        // what's expected.
        JSONAssert.assertEquals("{name: 'fred'}", result, false);
    }

    @Test
    void helloWorldJsonPath() {
        String result = "{\"age\":\"42\", \"name\": \"fred\", \"tags\":[\"java\", \"jdk\"]}";

        // Using JUnit 5 Assertions
        assertEquals(2, JsonPath.parse(result).read("$.tags.length()", Long.class));
        assertEquals("fred", JsonPath.parse(result).read("$.name", String.class));
    }

    @Test
    void helloWorldXmlUnit() {
        Source expected = Input.fromString("<invoices></invoices>").build();
        Source actual = Input.fromString("<customers></customers>").build();

        DifferenceEngine diff = new DOMDifferenceEngine();

        diff.addDifferenceListener((comparison, outcome) ->
                Assertions.fail("XML documents are not similar: " + comparison));

        assertThrows(AssertionError.class, () -> diff.compare(expected, actual));
    }
}
