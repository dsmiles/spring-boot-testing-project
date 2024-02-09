package com.github.dsmiles.pricing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// Register the Mockito JUnit Jupiter extension
@ExtendWith(MockitoExtension.class)
class PricingServiceInjectMockTest {

    // Instruct Mockito to mock this object
    @Mock
    private ProductVerifier mockedProductVerifier;

    // Instruct Mockito to inject mock dependencies into the fields of the tested object
    @InjectMocks
    private PricingService classUnderTest;

    @Test
    void shouldReturnCheapPriceWhenProductIsInStockOfCompetitor() {
        //Specify what boolean value to return for this test
        when(mockedProductVerifier.isCurrentlyInStockOfCompetitor("AirPods"))
                .thenReturn(true);

        assertEquals(new BigDecimal("99.99"), classUnderTest.calculatePrice("AirPods"));
    }

    @Test
    void shouldReturnHigherPriceWhenProductIsInStockOfCompetitor() {
        //Specify what boolean value to return for this test
        when(mockedProductVerifier.isCurrentlyInStockOfCompetitor("AirPods"))
                .thenReturn(false);

        assertEquals(new BigDecimal("149.99"), classUnderTest.calculatePrice("AirPods"));
    }
}