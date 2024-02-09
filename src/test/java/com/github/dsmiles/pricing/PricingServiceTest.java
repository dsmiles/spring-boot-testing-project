package com.github.dsmiles.pricing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// Register the Mockito JUnit Jupiter extension
@ExtendWith(MockitoExtension.class)
class PricingServiceTest {

    @Mock   // Instruct Mockito to mock this object
    private ProductVerifier mockedProductVerifier;

    @Test
    void shouldReturnCheapPriceWhenProductIsInStockOfCompetitor() {
        //Specify what boolean value to return for this test
        when(mockedProductVerifier.isCurrentlyInStockOfCompetitor("AirPods"))
                .thenReturn(true);

        PricingService classUnderTest = new PricingService(mockedProductVerifier);

        assertEquals(new BigDecimal("99.99"), classUnderTest.calculatePrice("AirPods"));
    }

    @Test
    void shouldReturnHigherPriceWhenProductIsInStockOfCompetitor() {
        //Specify what boolean value to return for this test
        when(mockedProductVerifier.isCurrentlyInStockOfCompetitor("AirPods"))
                .thenReturn(false);

        PricingService classUnderTest = new PricingService(mockedProductVerifier);

        assertEquals(new BigDecimal("149.99"), classUnderTest.calculatePrice("AirPods"));
    }
}
