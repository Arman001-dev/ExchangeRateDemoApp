package com.exchangeratedemoapp.data.usecase

import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.domain.remote.api.repositories.FavoriteCurrenciesDatabaseRepository

class FavoritesUseCase(private val favoriteCurrenciesDatabaseRepository: FavoriteCurrenciesDatabaseRepository) {

    fun getAllFavoriteCurrencies() = favoriteCurrenciesDatabaseRepository.getAllFavoriteCurrencies()

    suspend fun insertFavoriteCurrency(currency: Currency) {
        favoriteCurrenciesDatabaseRepository.insertFavoriteCurrency(currency)
    }

    suspend fun deleteFavoriteCurrency(currency: Currency) {
        favoriteCurrenciesDatabaseRepository.deleteFavoriteCurrency(currency)
    }
}