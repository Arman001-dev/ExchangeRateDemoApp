package com.exchangeratedemoapp.data.remote.api.repositories

import com.exchangeratedemoapp.data.remote.api.net.ExchangeRatesApiService
import com.exchangeratedemoapp.domain.remote.api.repositories.ApiRequestRepository
import com.exchangeratedemoapp.domain.utils.base.Constants

class ApiRequestRepositoryImpl(private val api: ExchangeRatesApiService) : ApiRequestRepository {
    override suspend fun getExchangeRates(base: String, symbols: List<String>) = api.getExchangeRates(symbols = symbols.joinToString(Constants.SEMICOLON), base = base)
}