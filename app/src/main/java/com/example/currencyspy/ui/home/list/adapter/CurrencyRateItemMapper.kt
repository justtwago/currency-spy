package com.example.currencyspy.ui.home.list.adapter

import com.example.domain.CurrencyRate
import java.util.*
import javax.inject.Inject

class CurrencyRateItemMapper @Inject constructor() {

    fun map(currencyRates: List<CurrencyRate>): List<CurrencyRateItem> {
        val groupedCurrencyRates = currencyRates.groupByTo(LinkedHashMap(), { it.date })
        val currencyRateItems = mutableListOf<CurrencyRateItem>()
        groupedCurrencyRates.forEach {
            currencyRateItems += CurrencyRateItem.Header(date = it.key)
            currencyRateItems += it.value.map { currencyRate ->
                CurrencyRateItem.Rate(currencyRate = currencyRate)
            }
        }
        return currencyRateItems
    }
}