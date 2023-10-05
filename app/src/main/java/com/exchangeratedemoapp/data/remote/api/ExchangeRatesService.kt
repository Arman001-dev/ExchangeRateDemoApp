package com.exchangeratedemoapp.data.remote.api

import com.exchangeratedemoapp.data.model.ExchangeRatesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRatesService {
    @GET("latest?")
    suspend fun getExchangeRates(
        @Query("symbols") symbols: String,
        @Query("base") base: String
    ): Response<ExchangeRatesDto>
}