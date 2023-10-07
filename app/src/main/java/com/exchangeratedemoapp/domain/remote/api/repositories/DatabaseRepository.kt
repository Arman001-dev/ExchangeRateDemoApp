package com.exchangeratedemoapp.domain.remote.api.repositories

import com.exchangeratedemoapp.domain.models.Currency
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun getAllRates(): Flow<List<Currency>>
    suspend fun insertFavoriteRate(currency: Currency)
    suspend fun deleteFavoriteRate(currency: Currency)
}