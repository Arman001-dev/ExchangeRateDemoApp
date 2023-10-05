package com.exchangeratedemoapp.data.remote.api.repositories

import com.exchangeratedemoapp.data.remote.api.net.ExchangeRatesApiService
import com.exchangeratedemoapp.domain.remote.api.models.base.ApiResult
import com.exchangeratedemoapp.domain.remote.api.repositories.ExchangeRatesRepository
import kotlinx.coroutines.flow.flow

class ExchangeRatesRepositoryImpl(private val api: ExchangeRatesApiService) : ExchangeRatesRepository {
    override suspend fun getExchangeRates(symbols: String, base: String) = flow {
        val response = api.getExchangeRates(symbols = symbols, base = base)
        when (response.isSuccessful) {
            true -> emit(ApiResult.Success(response.body()))
            false -> emit(ApiResult.Error(response.message()))
        }
    }
}