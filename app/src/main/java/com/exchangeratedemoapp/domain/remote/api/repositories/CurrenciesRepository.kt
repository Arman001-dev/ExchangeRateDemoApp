package com.exchangeratedemoapp.domain.remote.api.repositories

import com.exchangeratedemoapp.domain.remote.api.models.ExchangeRatesDto
import retrofit2.Response

interface CurrenciesRepository {
    suspend fun getExchangeRates(base: String, symbols: List<String>): Response<ExchangeRatesDto>?
}