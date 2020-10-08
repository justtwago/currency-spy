package com.example.currencyspy.ui.home.list

import com.example.domain.CurrencyRate
import java.time.LocalDate
import java.time.temporal.ChronoUnit.DAYS
import javax.inject.Inject
import kotlin.random.Random.Default.nextDouble

class MockedCurrencyRatesSource @Inject constructor() {

    fun fetchRates(startDate: LocalDate, endDate: LocalDate): List<CurrencyRate> {
        return (0 until DAYS.between(startDate, endDate).toInt()).map {
            CurrencyRate(
                date = LocalDate.now(),
                "USD",
                rate = nextDouble(),
                baseCurrencyCode = "EUR"
            )
        }
    }
}