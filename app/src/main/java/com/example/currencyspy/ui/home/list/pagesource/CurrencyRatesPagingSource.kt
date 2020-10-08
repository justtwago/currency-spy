package com.example.currencyspy.ui.home.list.pagesource

import androidx.paging.PagingSource
import com.example.domain.CurrencyRate
import java.time.LocalDate
import javax.inject.Inject

typealias Day = Int

class CurrencyRatesPagingSource @Inject constructor(
    private val currencyRateSource: MockedCurrencyRatesSource
) : PagingSource<LocalDate, CurrencyRate>() {
    override suspend fun load(params: LoadParams<LocalDate>): LoadResult<LocalDate, CurrencyRate> {
        val today = LocalDate.now()
        val nextPageStartDate = params.key ?: today
        val rates = currencyRateSource.fetchRates(
            nextPageStartDate,
            nextPageStartDate.plusDays(params.loadSize.toLong())
        )

        return LoadResult.Page(
            data = rates,
            prevKey = if (nextPageStartDate == today) null else nextPageStartDate.minusDays(params.loadSize.toLong() + 1),
            nextKey = nextPageStartDate.plusDays(params.loadSize.toLong() + 1)
        )
    }
}