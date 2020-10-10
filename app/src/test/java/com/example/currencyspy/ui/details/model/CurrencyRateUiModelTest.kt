package com.example.currencyspy.ui.details.model

import com.example.domain.CurrencyRate
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class CurrencyRateUiModelTest {

    @Test
    fun `maps currency rate domain model to currency rate details view state`() {
        val today = LocalDate.now()
        val currencyRate = CurrencyRate(
            date = LocalDate.now(),
            currencyCode = "PLN",
            rate = 1.0,
            baseCurrencyCode = "EUR"
        )

        val expectedRate = CurrencyRateUiModel(
            formattedDate = today.toString(),
            baseCurrencyCode = "EUR",
            currencyCode = "PLN",
            rate = 1.0
        )

        assertEquals(expectedRate, currencyRate.asUiModel())
    }
}