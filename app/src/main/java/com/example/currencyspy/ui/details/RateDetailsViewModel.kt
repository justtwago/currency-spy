package com.example.currencyspy.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyspy.ui.details.model.CurrencyRateDetailsViewState

class RateDetailsViewModel @ViewModelInject constructor() : ViewModel() {
    private val _currencyRateLiveData = MutableLiveData<CurrencyRateDetailsViewState>()
    val currencyRateLiveData: LiveData<CurrencyRateDetailsViewState> = _currencyRateLiveData

    fun updateCurrencyRate(currencyRate: CurrencyRateDetailsViewState) {
        _currencyRateLiveData.value = currencyRate
    }
}