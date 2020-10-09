package com.example.currencyspy.ui.home.list

import androidx.paging.PagingSource
import com.example.currencyspy.ui.home.list.pagesource.CurrencyRatesPagingSource
import com.example.domain.CurrencyRate
import com.example.networking.currency.CallResult
import com.example.networking.currency.CurrencyRatesNetwork
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import java.time.LocalDate

class CurrencyRatesPagingSourceTest {
    @RelaxedMockK private lateinit var mockedCurrencyRatesNetwork: CurrencyRatesNetwork
    private lateinit var pagingSource: CurrencyRatesPagingSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        pagingSource = CurrencyRatesPagingSource(
            currencyRatesNetwork = mockedCurrencyRatesNetwork
        )
    }

    @Test
    fun `loads first page with today currency rate`() = runBlockingTest {
        coEvery {
            mockedCurrencyRatesNetwork.getCurrencyRates(today)
        } returns CallResult.Success(currencyRates)

        val loadParams = PagingSource.LoadParams.Refresh<LocalDate>(
            key = null,
            loadSize = 1,
            placeholdersEnabled = false
        )

        val loadResult = pagingSource.load(loadParams)

        loadResult shouldBe PagingSource.LoadResult.Page(
            data = currencyRates,
            prevKey = null,
            nextKey = today.minusDays(1)
        )
    }

    @Test
    fun `loads next page with today currency rate`() = runBlockingTest {
        coEvery {
            mockedCurrencyRatesNetwork.getCurrencyRates(today.minusDays(1))
        } returns CallResult.Success(currencyRates)

        val loadParams = PagingSource.LoadParams.Append<LocalDate>(
            key = today.minusDays(1),
            loadSize = 1,
            placeholdersEnabled = false
        )

        val loadResult = pagingSource.load(loadParams)

        loadResult shouldBe PagingSource.LoadResult.Page(
            data = currencyRates,
            prevKey = today,
            nextKey = today.minusDays(2)
        )
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

        val loadResult = pagingSource.load(loadParams)

        loadResult shouldBe PagingSource.LoadResult.Error(exception)
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