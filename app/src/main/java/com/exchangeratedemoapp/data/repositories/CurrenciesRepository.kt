package com.exchangeratedemoapp.data.repositories

import com.exchangeratedemoapp.data.model.ExchangeRatesDto
import com.exchangeratedemoapp.data.remote.api.ExchangeRatesService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CurrenciesRepository {
    suspend fun getExchangeRates(symbols: String, base: String): Flow<ExchangeRatesDto?>
}

class CurrenciesRepositoryImpl(private val api: ExchangeRatesService) : CurrenciesRepository {
    override suspend fun getExchangeRates(symbols: String, base: String): Flow<ExchangeRatesDto?> = flow {
        val response = api.getExchangeRates(symbols, base).body()
        emit(response)
    }
}