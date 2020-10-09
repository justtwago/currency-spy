package com.example.currencyspy.ui.home.list.pagesource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.currencyspy.ui.home.list.adapter.CurrencyRateItem
import java.time.LocalDate
import javax.inject.Inject

private const val CURRENCY_RATES_PAGE_SIZE: Day = 1

class CurrencyRatesPagerFactory @Inject constructor(
    private val currencyRateItemsPagingSource: CurrencyRateItemsPagingSource
) {
    fun create(): Pager<LocalDate, CurrencyRateItem> = Pager(
        config = PagingConfig(pageSize = CURRENCY_RATES_PAGE_SIZE),
        pagingSourceFactory = { currencyRateItemsPagingSource }
    )
}