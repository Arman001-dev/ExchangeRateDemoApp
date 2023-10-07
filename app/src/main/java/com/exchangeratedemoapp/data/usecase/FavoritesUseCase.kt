package com.exchangeratedemoapp.data.usecase

import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.domain.remote.api.repositories.DatabaseRepository
import kotlinx.coroutines.flow.flow

class FavoritesUseCase(private val databaseRepository: DatabaseRepository) {

    fun getAllFavoriteRate() = databaseRepository.getAllRates()

    suspend fun insertFavoriteRate(currency: Currency) {
        databaseRepository.insertFavoriteRate(currency)
    }

    suspend fun deleteFavoriteRate(currency: Currency) {
        databaseRepository.deleteFavoriteRate(currency)
    }
}