package com.example.networking.interceptor

import com.example.networking.BuildConfig
import io.mockk.*
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import org.junit.Before
import org.junit.Test

class AuthenticationInterceptorTest {
    private lateinit var authenticationInterceptor: AuthenticationInterceptor

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        authenticationInterceptor = AuthenticationInterceptor()
    }

    @Test
    fun `adds authentication query for each fixer API call`() {
        val mockedChain = mockk<Interceptor.Chain>()
        val mockedRequest = mockk<Request>()
        val mockedHttpUrl = mockk<HttpUrl>()

        every { mockedChain.request() } returns mockedRequest

        every {
            mockedRequest.url()
                .newBuilder()
                .addQueryParameter("access_key", BuildConfig.API_KEY)
                .build()
        } returns mockedHttpUrl

        every {
            mockedRequest.newBuilder()
                .url(mockedHttpUrl)
                .build()
        } returns mockedRequest

        every { mockedChain.proceed(mockedRequest) } returns mockk()

        authenticationInterceptor.intercept(mockedChain)

        verifyOrder {
            mockedRequest.url()
                .newBuilder()
                .addQueryParameter("access_key", BuildConfig.API_KEY)
                .build()

            mockedRequest.newBuilder()
                .url(mockedHttpUrl)
                .build()

            mockedChain.proceed(mockedRequest)
        }
    }
}