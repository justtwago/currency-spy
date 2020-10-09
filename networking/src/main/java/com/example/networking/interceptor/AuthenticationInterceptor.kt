package com.example.networking.interceptor

import com.example.networking.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AuthenticationInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val urlWithAccessToken = request.url()
            .newBuilder()
            .addQueryParameter("access_key", BuildConfig.API_KEY)
            .build()

        val newRequest = request.newBuilder()
            .url(urlWithAccessToken)
            .build()

        return chain.proceed(newRequest)
    }
}
