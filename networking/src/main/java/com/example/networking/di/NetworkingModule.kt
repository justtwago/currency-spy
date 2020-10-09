package com.example.networking.di

import com.example.networking.currency.CurrencyRatesNetwork
import com.example.networking.currency.FixerCurrencyRatesNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module(includes = [HttpModule::class])
internal interface NetworkingModule {

    @Binds
    fun currencyRatesNetwork(currencyRatesNetwork: FixerCurrencyRatesNetwork): CurrencyRatesNetwork
}