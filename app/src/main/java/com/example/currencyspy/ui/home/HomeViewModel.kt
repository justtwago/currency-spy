package com.example.currencyspy.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.currencyspy.ui.home.list.adapter.CurrencyRateItem
import com.example.currencyspy.ui.home.list.paging.CurrencyRatesPagerFactory

class HomeViewModel @ViewModelInject constructor(
    private val currencyRatesPagerFactory: CurrencyRatesPagerFactory
) : ViewModel() {

    val currencyRates: LiveData<PagingData<CurrencyRateItem>> = currencyRatesPagerFactory
        .create()
        .flow
        .cachedIn(viewModelScope)
        .asLiveData()

    fun refreshRates() {
        currencyRatesPagerFactory.invalidate()
    }
}