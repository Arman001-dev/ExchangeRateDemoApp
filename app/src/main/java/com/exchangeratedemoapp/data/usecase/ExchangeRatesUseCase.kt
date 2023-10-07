package com.exchangeratedemoapp.data.usecase

import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.domain.models.ExchangeRate
import com.exchangeratedemoapp.domain.remote.api.models.ExchangeRatesDto
import com.exchangeratedemoapp.domain.remote.api.models.base.ApiResult
import com.exchangeratedemoapp.domain.remote.api.repositories.ApiRequestRepository
import com.exchangeratedemoapp.domain.remote.api.repositories.DatabaseRepository
import com.exchangeratedemoapp.domain.utils.base.Constants
import kotlinx.coroutines.flow.flow

class ExchangeRatesUseCase(private val apiRequestRepository: ApiRequestRepository, private val databaseRepository: DatabaseRepository) {

    suspend operator fun invoke(base: String, symbols: List<String>) = flow {
        val result = apiRequestRepository.getExchangeRates(base, symbols)
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

    suspend fun insertFavoriteRate(currency: Currency) {
        databaseRepository.insertFavoriteRate(currency)
    }

    suspend fun deleteFavoriteRate(currency: Currency) {
        databaseRepository.deleteFavoriteRate(currency)
    }
}