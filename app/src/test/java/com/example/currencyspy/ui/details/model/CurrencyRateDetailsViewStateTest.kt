package com.example.currencyspy.ui.details.model

import com.example.domain.CurrencyRate
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

class CurrencyRateDetailsViewStateTest {

    @Test
    fun `maps currency rate domain model to currency rate details view state`() {
        val today = LocalDate.now()
        val currencyRate = CurrencyRate(
            date = LocalDate.now(),
            currencyCode = "PLN",
            rate = 1.0,
            baseCurrencyCode = "EUR"
        )

        val expectedViewState = CurrencyRateDetailsViewState(
            formattedDate = today.toString(),
            baseCurrencyCode = "EUR",
            formattedRate = "1.0 PLN"
        )

        assertEquals(expectedViewState, currencyRate.asRateDetailsViewState())
    }
}