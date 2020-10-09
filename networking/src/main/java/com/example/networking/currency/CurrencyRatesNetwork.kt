package com.example.networking.currency

import com.example.domain.CurrencyRate
import java.time.LocalDate
import javax.inject.Inject

interface CurrencyRatesNetwork {
    suspend fun getCurrencyRates(date: LocalDate): CallResult<List<CurrencyRate>>
}

internal class FixerCurrencyRatesNetwork @Inject constructor(
    private val currencyRatesService: CurrencyRatesService
) : CurrencyRatesNetwork {

    override suspend fun getCurrencyRates(date: LocalDate): CallResult<List<CurrencyRate>> {
        return executeCall {
            val response = currencyRatesService.getCurrencyRates(date)
            response.rates.map {
                CurrencyRate(
                    date = response.date,
                    baseCurrencyCode = response.baseCurrency,
                    currencyCode = it.key,
                    rate = it.value
                )
            }
        }
    }
}