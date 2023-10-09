package com.exchangeratedemoapp.data.usecase

import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.domain.models.ExchangeRate
import com.exchangeratedemoapp.domain.remote.api.models.ExchangeRatesDto
import com.exchangeratedemoapp.domain.remote.api.models.base.ApiResult
import com.exchangeratedemoapp.domain.remote.api.repositories.CurrenciesRepository
import com.exchangeratedemoapp.domain.remote.api.repositories.FavoriteCurrenciesDatabaseRepository
import com.exchangeratedemoapp.domain.utils.base.Constants
import kotlinx.coroutines.flow.flow

class CurrenciesUseCase(private val currenciesRepository: CurrenciesRepository, private val favoriteCurrenciesDatabaseRepository: FavoriteCurrenciesDatabaseRepository) {

    suspend operator fun invoke(base: String, symbols: List<String>) = flow {
        val result = currenciesRepository.getExchangeRates(base, symbols)
        when (result?.isSuccessful) {
            true -> {
                val rate = toExchangeRate(result.body())
                val favorites = getAllFavoriteCurrenciesList()
                rate.rates?.map {
                    it.apply {
                        isFavorite = favorites.any { favoriteCurrency ->
                            favoriteCurrency.key == key && favoriteCurrency.baseCurrency == baseCurrency
                        }
                    }
                }
                emit(ApiResult.Success(rate))
            }

            false -> emit(ApiResult.Error(result.message()))
            null -> Unit
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

    fun getAllFavoriteCurrencies() = favoriteCurrenciesDatabaseRepository.getAllFavoriteCurrencies()

    private suspend fun getAllFavoriteCurrenciesList() = favoriteCurrenciesDatabaseRepository.getAllFavoriteCurrenciesList()

    suspend fun insertFavoriteCurrency(currency: Currency) {
        favoriteCurrenciesDatabaseRepository.insertFavoriteCurrency(currency)
    }

    suspend fun deleteFavoriteCurrency(currency: Currency) {
        favoriteCurrenciesDatabaseRepository.deleteFavoriteCurrency(currency)
    }
}