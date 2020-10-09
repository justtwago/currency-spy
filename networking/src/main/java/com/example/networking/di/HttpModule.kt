package com.example.networking.di

import com.example.networking.BuildConfig
import com.example.networking.currency.CurrencyRatesService
import com.example.networking.adapter.LocalDateJsonAdapter
import com.example.networking.interceptor.AuthenticationInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@InstallIn(ApplicationComponent::class)
@Module
internal object HttpModule {

    @Provides
    @Reusable
    internal fun currencyRatesService(retrofit: Retrofit): CurrencyRatesService =
        retrofit.create(CurrencyRatesService::class.java)

    @Provides
    internal fun retrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(moshiConverterFactory)
        .build()

    @Provides
    internal fun okHttpClient(authenticationInterceptor: AuthenticationInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(authenticationInterceptor)
            .enableDebugLogging()
            .build()

    private fun OkHttpClient.Builder.enableDebugLogging(): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return this
    }

    @Provides
    internal fun moshiConverterFactory(moshi: Moshi) = MoshiConverterFactory.create(moshi)

    @Provides
    internal fun moshi(
        localDateJsonAdapter: LocalDateJsonAdapter
    ) = Moshi.Builder()
        .add(localDateJsonAdapter)
        .build()
}