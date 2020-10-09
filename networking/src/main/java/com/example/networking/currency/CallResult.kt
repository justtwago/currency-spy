package com.example.networking.currency

sealed class CallResult<T> {
    data class Success<T>(val body: T) : CallResult<T>()
    data class Error<T>(val exception: Exception) : CallResult<T>()
}

suspend fun <T : Any> executeCall(call: suspend () -> T): CallResult<T> {
    return try {
        CallResult.Success(body = call())
    } catch (e: Exception) {
        CallResult.Error(e)
    }
}