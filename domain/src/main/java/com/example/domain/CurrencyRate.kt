package com.example.domain

import java.time.LocalDate

data class CurrencyRate(
    val date: LocalDate,
    val currencyCode: String,
    val rate: Double,
    val baseCurrencyCode: String
)