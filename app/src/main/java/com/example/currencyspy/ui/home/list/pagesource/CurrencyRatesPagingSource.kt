package com.example.currencyspy.ui.home.list.pagesource

import androidx.paging.PagingSource
import com.example.domain.CurrencyRate
import com.example.networking.currency.CallResult
import com.example.networking.currency.CurrencyRatesNetwork
import java.time.LocalDate
import javax.inject.Inject

typealias Day = Int

class CurrencyRatesPagingSource @Inject constructor(
    private val currencyRatesNetwork: CurrencyRatesNetwork
) : PagingSource<LocalDate, CurrencyRate>() {
    override suspend fun load(params: LoadParams<LocalDate>): LoadResult<LocalDate, CurrencyRate> {
        val today = LocalDate.now()
        val nextPageStartDate = params.key ?: today
        return when (val response = currencyRatesNetwork.getCurrencyRates(nextPageStartDate)) {
            is CallResult.Success -> LoadResult.Page(
                data = response.body,
                prevKey = if (nextPageStartDate == today) null else nextPageStartDate.plusDays(1),
                nextKey = nextPageStartDate.minusDays(1)
            )
            is CallResult.Error -> LoadResult.Error(response.exception)
        }
    }
}