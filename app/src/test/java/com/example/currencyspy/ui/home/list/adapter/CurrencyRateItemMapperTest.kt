package com.example.currencyspy.ui.home.list.adapter

import com.example.domain.CurrencyRate
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.time.LocalDate

class CurrencyRateItemMapperTest {
    private val currencyRateItemMapper = CurrencyRateItemMapper()

    @Test
    fun `maps currency rates to items`() {
        val today = LocalDate.now()
        val currencyRates = listOf(
            CurrencyRate(today, "PLN", 1.0, "EUR"),
            CurrencyRate(today, "USD", 2.0, "EUR"),
            CurrencyRate(today.minusDays(1), "PLN", 2.0, "EUR"),
            CurrencyRate(today.minusDays(1), "USD", 3.0, "EUR"),
        )

        currencyRateItemMapper.map(currencyRates) shouldBe listOf(
            CurrencyRateItem.Header(today),
            CurrencyRateItem.Rate(CurrencyRate(today, "PLN", 1.0, "EUR")),
            CurrencyRateItem.Rate(CurrencyRate(today, "USD", 2.0, "EUR")),
            CurrencyRateItem.Header(today.minusDays(1)),
            CurrencyRateItem.Rate(CurrencyRate(today.minusDays(1), "PLN", 2.0, "EUR")),
            CurrencyRateItem.Rate(CurrencyRate(today.minusDays(1), "USD", 3.0, "EUR")),
        )
    }
}