package com.example.currencyspy.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.currencyspy.ui.home.list.pagesource.CurrencyRatesPagerFactory
import com.example.domain.CurrencyRate

class HomeViewModel @ViewModelInject constructor(
    currencyRatesPagerFactory: CurrencyRatesPagerFactory
) : ViewModel() {

    val currencyRates: LiveData<PagingData<CurrencyRate>> = currencyRatesPagerFactory
        .create()
        .flow
        .cachedIn(viewModelScope)
        .asLiveData()
}