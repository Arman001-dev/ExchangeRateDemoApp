package com.exchangeratedemoapp.data.remote.api.net

import com.exchangeratedemoapp.ExchangeRatesApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T> exchangeRateHttpResponse(
    apiFunction: suspend () -> Response<T>
): Response<T>? {
    return if (ExchangeRatesApplication.networkStateFlow.value) {
        withContext(Dispatchers.IO) {
            apiFunction()
        }
    } else null
}