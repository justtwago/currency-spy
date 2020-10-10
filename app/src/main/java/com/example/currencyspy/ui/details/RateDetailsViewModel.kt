package com.example.currencyspy.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyspy.ui.details.model.CurrencyRateUiModel
import com.example.currencyspy.utils.ClipboardManager

class RateDetailsViewModel @ViewModelInject constructor(
    private val clipboardManager: ClipboardManager
) : ViewModel() {
    private val _currencyRateLiveData = MutableLiveData<CurrencyRateUiModel>()
    val currencyRateLiveData: LiveData<CurrencyRateUiModel> = _currencyRateLiveData

    fun updateCurrencyRate(currencyRate: CurrencyRateUiModel) {
        _currencyRateLiveData.value = currencyRate
    }

    fun copyRate() {
        val rateText = currencyRateLiveData.value?.rate?.toString() ?: return
        clipboardManager.copyText(rateText)
    }
}