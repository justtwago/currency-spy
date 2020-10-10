package com.example.networking.currency

import com.example.domain.CurrencyRate
import com.example.networking.currency.model.HistoricalRatesResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import java.time.LocalDate

class FixerCurrencyRatesNetworkTest {

    @RelaxedMockK private lateinit var mockedCurrencyRatesService: CurrencyRatesService
    private lateinit var fixerCurrencyRatesNetwork: FixerCurrencyRatesNetwork

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        fixerCurrencyRatesNetwork = FixerCurrencyRatesNetwork(
            currencyRatesService = mockedCurrencyRatesService
        )
    }

    @Test
    fun `fetches today currency rates`() = runBlockingTest {
        val today = LocalDate.now()
        coEvery { mockedCurrencyRatesService.getCurrencyRates(today) } returns HistoricalRatesResponse(
            baseCurrency = "EUR",
            date = today,
            rates = mapOf(
                "PLN" to 3.8,
                "USD" to 1.4,
            )
        )

        val response = fixerCurrencyRatesNetwork.getCurrencyRates(today)

        val expectedResponse = CallResult.Success(
            listOf(
                CurrencyRate(
                    date = today,
                    currencyCode = "PLN",
                    rate = 3.8,
                    baseCurrencyCode = "EUR"
                ),
                CurrencyRate(
                    date = today,
                    currencyCode = "USD",
                    rate = 1.4,
                    baseCurrencyCode = "EUR"
                ),
            )
        )

        assertEquals(expectedResponse, response)
    }

    @Test
    fun `returns error when fetching today currency rates is not successful`() = runBlockingTest {
        val exception = Exception()
        val today = LocalDate.now()
        coEvery { mockedCurrencyRatesService.getCurrencyRates(today) } throws exception

        val response = fixerCurrencyRatesNetwork.getCurrencyRates(today)

        assertEquals(CallResult.Error<List<CurrencyRate>>(exception), response)
    }

}