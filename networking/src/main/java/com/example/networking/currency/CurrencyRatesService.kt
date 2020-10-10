package com.example.networking.currency

import com.example.networking.currency.model.HistoricalRatesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import java.time.LocalDate

internal interface CurrencyRatesService {

    @GET("{date}")
    suspend fun getCurrencyRates(@Path("date") date: LocalDate): HistoricalRatesResponse
}