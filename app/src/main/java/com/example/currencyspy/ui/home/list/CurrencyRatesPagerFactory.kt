package com.example.currencyspy.ui.home.list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.domain.CurrencyRate
import java.time.LocalDate
import javax.inject.Inject

private const val CURRENCY_RATES_PAGE_SIZE: Day = 25

class CurrencyRatesPagerFactory @Inject constructor(
    private val currencyRatesPagingSource: CurrencyRatesPagingSource
) {
    fun create(): Pager<LocalDate, CurrencyRate> = Pager(
        config = PagingConfig(pageSize = CURRENCY_RATES_PAGE_SIZE),
        pagingSourceFactory = { currencyRatesPagingSource }
    )
}