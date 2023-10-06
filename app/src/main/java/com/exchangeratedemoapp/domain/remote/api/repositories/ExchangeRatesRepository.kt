package com.exchangeratedemoapp.domain.remote.api.repositories

import com.exchangeratedemoapp.domain.remote.api.models.ExchangeRatesDto
import com.exchangeratedemoapp.domain.remote.api.models.base.ApiResult
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ExchangeRatesRepository {
    suspend fun getExchangeRates(base: String, symbols:List<String>): Response<ExchangeRatesDto>
}