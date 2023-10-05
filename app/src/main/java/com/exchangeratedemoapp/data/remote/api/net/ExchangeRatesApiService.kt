package com.exchangeratedemoapp.data.remote.api.net

import com.exchangeratedemoapp.domain.remote.api.models.ExchangeRatesDto
import com.exchangeratedemoapp.domain.utils.base.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ExchangeRatesApiService {
    @GET("latest?")
    suspend fun getExchangeRates(
        @Header("apikey")
        apiKey: String = Constants.EXCHANGE_RATE_API_KEY,
        @Query("symbols") symbols: String,
        @Query("base") base: String
    ): Response<ExchangeRatesDto>
}