package com.example.currencyspy.ui.home.list.adapter

import com.example.domain.CurrencyRate
import java.time.LocalDate

sealed class CurrencyRateItem {
    data class Header(val date: LocalDate) : CurrencyRateItem()
    data class Rate(val currencyRate: CurrencyRate) : CurrencyRateItem()
}