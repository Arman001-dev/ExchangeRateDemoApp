package com.exchangeratedemoapp.data.remote.api.repositories

import com.exchangeratedemoapp.data.remote.api.net.ExchangeRatesApiService
import com.exchangeratedemoapp.domain.remote.api.repositories.ExchangeRatesRepository
import com.exchangeratedemoapp.domain.utils.base.Constants

class ExchangeRatesRepositoryImpl(private val api: ExchangeRatesApiService) : ExchangeRatesRepository {
    override suspend fun getExchangeRates(base: String, symbols: List<String>) = api.getExchangeRates(symbols = symbols.joinToString(Constants.SEMICOLON), base = base)
}