package com.example.currencyspy.ui.details

import com.example.currencyspy.ui.details.model.CurrencyRateUiModel
import com.example.currencyspy.ui.util.InstantTaskExecutionTest
import com.example.currencyspy.utils.ClipboardManager
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RateDetailsViewModelTest : InstantTaskExecutionTest() {

    @RelaxedMockK private lateinit var mockedClipboardManager: ClipboardManager
    private lateinit var viewModel: RateDetailsViewModel

    @Before
    override fun setup() {
        super.setup()
        MockKAnnotations.init(this)

        viewModel = RateDetailsViewModel(
            clipboardManager = mockedClipboardManager
        )
    }

    @Test
    fun `updates currency rate details`() {
        val currencyRate = CurrencyRateUiModel(
            formattedDate = "10-10-2020",
            currencyCode = "PLN",
            rate = 3.84,
            baseCurrencyCode = "EUR"
        )
        viewModel.updateCurrencyRate(currencyRate)

        assertEquals(currencyRate, viewModel.currencyRateLiveData.value)
    }

    @Test
    fun `copy currency rate to clipboard`() {
        val currencyRate = CurrencyRateUiModel(
            formattedDate = "10-10-2020",
            currencyCode = "PLN",
            rate = 3.84,
            baseCurrencyCode = "EUR"
        )
        viewModel.updateCurrencyRate(currencyRate)
        viewModel.copyRate()

        verify {
            mockedClipboardManager.copyText("3.84")
        }
    }

}