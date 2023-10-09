package com.exchangeratedemoapp.data.remote.api.repositories

import com.exchangeratedemoapp.data.remote.api.net.ExchangeRatesApiService
import com.exchangeratedemoapp.data.remote.api.net.exchangeRateHttpResponse
import com.exchangeratedemoapp.domain.remote.api.repositories.CurrenciesRepository
import com.exchangeratedemoapp.domain.utils.base.Constants

class CurrenciesRepositoryImpl(private val api: ExchangeRatesApiService) : CurrenciesRepository {
    override suspend fun getExchangeRates(base: String, symbols: List<String>) = exchangeRateHttpResponse {
        api.getExchangeRates(symbols = symbols.joinToString(Constants.SEMICOLON), base = base)
    }
}