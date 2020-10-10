package com.example.currencyspy.ui.home.list

import androidx.paging.PagingSource
import com.example.currencyspy.ui.home.list.adapter.CurrencyRateItem
import com.example.currencyspy.ui.home.list.adapter.CurrencyRateItemMapper
import com.example.currencyspy.ui.home.list.paging.CurrencyRateItemsPagingSource
import com.example.domain.CurrencyRate
import com.example.networking.currency.CallResult
import com.example.networking.currency.CurrencyRatesNetwork
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import java.time.LocalDate

class CurrencyRateItemsPagingSourceTest {
    @RelaxedMockK private lateinit var mockedCurrencyRatesNetwork: CurrencyRatesNetwork
    @RelaxedMockK private lateinit var mockedCurrencyRateItemMapper: CurrencyRateItemMapper
    private lateinit var pagingSource: CurrencyRateItemsPagingSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        pagingSource = CurrencyRateItemsPagingSource.Factory(
            currencyRatesNetwork = mockedCurrencyRatesNetwork,
            currencyRateItemMapper = mockedCurrencyRateItemMapper
        ).create()
    }

    @Test
    fun `loads first page with today currency rate`() = runBlockingTest {
        coEvery {
            mockedCurrencyRatesNetwork.getCurrencyRates(today)
        } returns CallResult.Success(currencyRates)

        coEvery { mockedCurrencyRateItemMapper.map(currencyRates) } returns listOf(
            CurrencyRateItem.Header(date = currencyRates.first().date),
            CurrencyRateItem.Rate(currencyRate = currencyRates.first())
        )

        val loadParams = PagingSource.LoadParams.Refresh<LocalDate>(
            key = null,
            loadSize = 1,
            placeholdersEnabled = false
        )

        val loadedPage = pagingSource.load(loadParams)

        val expectedPage = PagingSource.LoadResult.Page(
            data = listOf(
                CurrencyRateItem.Header(date = currencyRates.first().date),
                CurrencyRateItem.Rate(currencyRate = currencyRates.first())
            ),
            prevKey = null,
            nextKey = today.minusDays(1)
        )

        assertEquals(expectedPage, loadedPage)
    }

    @Test
    fun `loads next page with today currency rate`() = runBlockingTest {
        coEvery {
            mockedCurrencyRatesNetwork.getCurrencyRates(today.minusDays(1))
        } returns CallResult.Success(currencyRates)

        coEvery { mockedCurrencyRateItemMapper.map(currencyRates) } returns listOf(
            CurrencyRateItem.Header(date = currencyRates.first().date),
            CurrencyRateItem.Rate(currencyRate = currencyRates.first())
        )

        val loadParams = PagingSource.LoadParams.Append<LocalDate>(
            key = today.minusDays(1),
            loadSize = 1,
            placeholdersEnabled = false
        )

        val loadedPage = pagingSource.load(loadParams)

        val expectedPage = PagingSource.LoadResult.Page(
            data = listOf(
                CurrencyRateItem.Header(date = currencyRates.first().date),
                CurrencyRateItem.Rate(currencyRate = currencyRates.first())
            ),
            prevKey = today,
            nextKey = today.minusDays(2)
        )

        assertEquals(expectedPage, loadedPage)
    }

    @Test
    fun `returns error when currency rates are not fetched`() = runBlockingTest {
        val exception = Exception()

        coEvery {
            mockedCurrencyRatesNetwork.getCurrencyRates(today)
        } returns CallResult.Error(exception)

        val loadParams = PagingSource.LoadParams.Append<LocalDate>(
            key = today,
            loadSize = 1,
            placeholdersEnabled = false
        )

        val loadedPage = pagingSource.load(loadParams)
        val expectedError = PagingSource.LoadResult.Error<LocalDate, CurrencyRateItem>(exception)

        assertEquals(expectedError, loadedPage)
    }

    private val today get() = LocalDate.now()
    private val currencyRates
        get() = listOf(
            CurrencyRate(
                date = LocalDate.now(),
                currencyCode = "PLN",
                rate = 1.0,
                baseCurrencyCode = "EUR"
            )
        )
}