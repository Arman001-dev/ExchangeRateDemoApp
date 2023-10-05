package com.exchangeratedemoapp.domain.remote.api.repositories

import com.exchangeratedemoapp.domain.remote.api.models.ExchangeRatesDto
import com.exchangeratedemoapp.domain.remote.api.models.base.ApiResult
import kotlinx.coroutines.flow.Flow

interface ExchangeRatesRepository {
    suspend fun getExchangeRates(symbols: String, base: String): Flow<ApiResult<ExchangeRatesDto?>>
}