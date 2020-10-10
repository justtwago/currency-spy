package com.example.currencyspy.ui.home

import com.example.currencyspy.ui.home.list.paging.CurrencyRatesPagerFactory
import com.example.currencyspy.ui.util.InstantTaskExecutionTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class HomeViewModelTest : InstantTaskExecutionTest() {
    @RelaxedMockK private lateinit var currencyRatesPagerFactory: CurrencyRatesPagerFactory
    private lateinit var viewModel: HomeViewModel

    @Before
    override fun setup() {
        super.setup()
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(
            currencyRatesPagerFactory = currencyRatesPagerFactory
        )
    }

    @Test
    fun `fetches currency rates on view model init`() {
        verify { currencyRatesPagerFactory.create() }
    }

    @Test
    fun `invalidates currency rates`() {
        viewModel.refreshRates()

        verify { currencyRatesPagerFactory.invalidate() }
    }
}