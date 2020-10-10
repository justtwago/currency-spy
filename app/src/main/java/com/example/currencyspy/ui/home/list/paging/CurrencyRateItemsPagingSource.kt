package com.example.currencyspy.ui.home.list.paging

import androidx.paging.PagingSource
import com.example.currencyspy.ui.home.list.adapter.CurrencyRateItem
import com.example.currencyspy.ui.home.list.adapter.CurrencyRateItemMapper
import com.example.networking.currency.CallResult
import com.example.networking.currency.CurrencyRatesNetwork
import java.time.LocalDate
import javax.inject.Inject

typealias Day = Int

class CurrencyRateItemsPagingSource private constructor(
    private val currencyRatesNetwork: CurrencyRatesNetwork,
    private val currencyRateItemMapper: CurrencyRateItemMapper
) : PagingSource<LocalDate, CurrencyRateItem>() {

    override suspend fun load(
        params: LoadParams<LocalDate>
    ): LoadResult<LocalDate, CurrencyRateItem> {
        val today = LocalDate.now()
        val nextPageStartDate = params.key ?: today
        return when (val response = currencyRatesNetwork.getCurrencyRates(nextPageStartDate)) {
            is CallResult.Success -> LoadResult.Page(
                data = currencyRateItemMapper.map(currencyRates = response.body),
                prevKey = if (nextPageStartDate == today) null else nextPageStartDate.plusDays(1),
                nextKey = nextPageStartDate.minusDays(1)
            )
            is CallResult.Error -> LoadResult.Error(response.exception)
        }
    }

    class Factory @Inject constructor(
        private val currencyRatesNetwork: CurrencyRatesNetwork,
        private val currencyRateItemMapper: CurrencyRateItemMapper
    ) {
        fun create() = CurrencyRateItemsPagingSource(
            currencyRatesNetwork = currencyRatesNetwork,
            currencyRateItemMapper = currencyRateItemMapper
        )
    }
}