package com.exchangeratedemoapp.domain.remote.api.repositories

import com.exchangeratedemoapp.domain.models.Currency
import kotlinx.coroutines.flow.Flow

interface FavoriteCurrenciesDatabaseRepository {
    fun getAllFavoriteCurrencies(): Flow<List<Currency>>
    suspend fun getAllFavoriteCurrenciesList(): List<Currency>
    suspend fun insertFavoriteCurrency(currency: Currency)
    suspend fun deleteFavoriteCurrency(currency: Currency)
}