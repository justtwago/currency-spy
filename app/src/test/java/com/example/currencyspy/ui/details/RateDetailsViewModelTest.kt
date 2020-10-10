package com.example.currencyspy.ui.details

import com.example.currencyspy.ui.details.model.CurrencyRateDetailsViewState
import com.example.currencyspy.ui.util.InstantTaskExecutionTest
import io.mockk.MockKAnnotations
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RateDetailsViewModelTest : InstantTaskExecutionTest() {

    private lateinit var viewModel: RateDetailsViewModel

    @Before
    override fun setup() {
        super.setup()
        MockKAnnotations.init(this)

        viewModel = RateDetailsViewModel()
    }

    @Test
    fun `updates currency rate details`() {
        val currencyRate = CurrencyRateDetailsViewState(
            formattedDate = "10-10-2020",
            formattedRate = "3.48 PLN",
            baseCurrencyCode = "EUR"
        )
        viewModel.updateCurrencyRate(currencyRate)

        assertEquals(currencyRate, viewModel.currencyRateLiveData.value)
    }

}