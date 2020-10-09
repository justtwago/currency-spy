package com.example.networking.currency.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDate

@JsonClass(generateAdapter = true)
data class HistoricalRatesResponse(
    @Json(name = "base")
    val baseCurrency: String,
    @Json(name = "date")
    val date: LocalDate,
    @Json(name = "rates")
    val rates: Map<String, Double>
)