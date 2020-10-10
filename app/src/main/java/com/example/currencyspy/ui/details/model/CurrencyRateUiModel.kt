package com.example.currencyspy.ui.details.model

import android.os.Parcelable
import com.example.domain.CurrencyRate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrencyRateUiModel(
    val formattedDate: String,
    val currencyCode: String,
    val rate: Double,
    val baseCurrencyCode: String
) : Parcelable

fun CurrencyRate.asUiModel() = CurrencyRateUiModel(
    baseCurrencyCode = baseCurrencyCode,
    rate = rate,
    currencyCode = currencyCode,
    formattedDate = date.toString()
)