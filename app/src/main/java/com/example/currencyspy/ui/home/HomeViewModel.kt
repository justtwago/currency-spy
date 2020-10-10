package com.example.currencyspy.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.currencyspy.ui.home.list.adapter.CurrencyRateItem
import com.example.currencyspy.ui.home.list.paging.CurrencyRatesPagerFactory
import kotlinx.coroutines.flow.Flow

class HomeViewModel @ViewModelInject constructor(
    private val currencyRatesPagerFactory: CurrencyRatesPagerFactory
) : ViewModel() {
    val currencyRates: Flow<PagingData<CurrencyRateItem>> = currencyRatesPagerFactory
        .create()
        .flow
        .cachedIn(viewModelScope)

    fun refreshRates() {
        currencyRatesPagerFactory.invalidate()
    }
}