package com.example.currencyspy.ui.details.model

import android.os.Parcelable
import com.example.domain.CurrencyRate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrencyRateDetailsViewState(
    val formattedDate: String,
    val formattedRate: String,
    val baseCurrencyCode: String
) : Parcelable

fun CurrencyRate.asRateDetailsViewState() = CurrencyRateDetailsViewState(
    baseCurrencyCode = baseCurrencyCode,
    formattedRate = "$rate $currencyCode",
    formattedDate = date.toString()
)