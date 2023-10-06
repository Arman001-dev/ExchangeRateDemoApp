package com.exchangeratedemoapp.data.usecase

import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.domain.models.ExchangeRate
import com.exchangeratedemoapp.domain.remote.api.models.ExchangeRatesDto
import com.exchangeratedemoapp.domain.remote.api.models.base.ApiResult
import com.exchangeratedemoapp.domain.remote.api.repositories.ExchangeRatesRepository
import com.exchangeratedemoapp.domain.utils.base.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flow

class ExchangeRatesUseCase(private val repository: ExchangeRatesRepository) {

    private val scope = CoroutineScope(Job() + Dispatchers.Default)

    suspend operator fun invoke(base: String, symbols: List<String>) = flow {
        val result = repository.getExchangeRates(base, symbols)
        when (result.isSuccessful) {
            true -> emit(ApiResult.Success(toExchangeRate(result.body())))
            false -> emit(ApiResult.Error(result.message()))
        }
    }

    private fun toExchangeRate(exchangeRatesDto: ExchangeRatesDto?): ExchangeRate {
        return ExchangeRate(
            base = exchangeRatesDto?.base ?: Constants.EMPTY_STRING,
            date = exchangeRatesDto?.date ?: Constants.EMPTY_STRING,
            rates = exchangeRatesDto?.rates?.toList()?.map {
                Currency(
                    currency = it.first,
                    baseCurrency = exchangeRatesDto.base ?: Constants.EMPTY_STRING,
                    rate = it.second,
                    isFavorite = false
                )
            }
        )
    }
}