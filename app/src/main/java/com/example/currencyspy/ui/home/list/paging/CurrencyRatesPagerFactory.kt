package com.example.currencyspy.ui.home.list.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.currencyspy.ui.home.list.adapter.CurrencyRateItem
import java.time.LocalDate
import javax.inject.Inject

private const val CURRENCY_RATES_PAGE_SIZE: Day = 1

class CurrencyRatesPagerFactory @Inject constructor(
    private val pagingSourceFactory: CurrencyRateItemsPagingSource.Factory
) {
    private var pagingSource: CurrencyRateItemsPagingSource? = null

    fun create(): Pager<LocalDate, CurrencyRateItem> = Pager(
        config = PagingConfig(pageSize = CURRENCY_RATES_PAGE_SIZE),
        pagingSourceFactory = {
            pagingSourceFactory.create().also { pagingSource = it }
        }
    )

    fun invalidate() {
        pagingSource?.invalidate()
    }
}