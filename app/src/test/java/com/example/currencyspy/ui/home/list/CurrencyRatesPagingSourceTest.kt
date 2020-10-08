package com.example.currencyspy.ui.home.list

import androidx.paging.PagingSource
import com.example.currencyspy.ui.home.list.pagesource.CurrencyRatesPagingSource
import com.example.currencyspy.ui.home.list.pagesource.MockedCurrencyRatesSource
import com.example.domain.CurrencyRate
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class CurrencyRatesPagingSourceTest {
    @RelaxedMockK private lateinit var currencyRateSource: MockedCurrencyRatesSource
    private lateinit var pagingSource: CurrencyRatesPagingSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        pagingSource = CurrencyRatesPagingSource(
            currencyRateSource = currencyRateSource
        )
    }

    @Test
    fun `loads first page with today currency rate`() = runBlockingTest {
        every {
            currencyRateSource.fetchRates(today, today.plusDays(25))
        } returns currencyRates

        val loadParams = PagingSource.LoadParams.Refresh<LocalDate>(
            key = null,
            loadSize = 25,
            placeholdersEnabled = false
        )

        val loadResult = pagingSource.load(loadParams)

        loadResult shouldBe PagingSource.LoadResult.Page(
            data = currencyRates,
            prevKey = null,
            nextKey = today.plusDays(26)
        )
    }

    @Test
    fun `loads next page with today currency rate`() = runBlockingTest {
        every {
            currencyRateSource.fetchRates(today.plusDays(26), today.plusDays(51))
        } returns currencyRates

        val loadParams = PagingSource.LoadParams.Append<LocalDate>(
            key = today.plusDays(26),
            loadSize = 25,
            placeholdersEnabled = false
        )

        val loadResult = pagingSource.load(loadParams)

        loadResult shouldBe PagingSource.LoadResult.Page(
            data = currencyRates,
            prevKey = today,
            nextKey = today.plusDays(52)
        )
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