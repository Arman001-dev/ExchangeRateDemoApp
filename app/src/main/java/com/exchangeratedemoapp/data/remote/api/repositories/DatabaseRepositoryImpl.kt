package com.exchangeratedemoapp.data.remote.api.repositories

import com.exchangeratedemoapp.data.local.database.CurrencyDao
import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.domain.remote.api.repositories.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DatabaseRepositoryImpl(private val dao: CurrencyDao) : DatabaseRepository {
    override fun getAllRates(): Flow<List<Currency>> = flow {
        dao.getAllRates()
    }

    override suspend fun insertFavoriteRate(currency: Currency) = dao.insertRate(currency = currency)

    override suspend fun deleteFavoriteRate(currency: Currency) = dao.deleteRate(currency = currency)
}