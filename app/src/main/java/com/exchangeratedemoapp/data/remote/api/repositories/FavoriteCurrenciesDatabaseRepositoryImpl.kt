package com.exchangeratedemoapp.data.remote.api.repositories

import com.exchangeratedemoapp.data.local.database.CurrencyDao
import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.domain.remote.api.repositories.FavoriteCurrenciesDatabaseRepository
import kotlinx.coroutines.flow.Flow

class FavoriteCurrenciesDatabaseRepositoryImpl(private val dao: CurrencyDao) : FavoriteCurrenciesDatabaseRepository {

    override fun getAllFavoriteCurrencies(): Flow<List<Currency>> = dao.getAllRates()

    override suspend fun getAllFavoriteCurrenciesList(): List<Currency> = dao.getAllRatesList()

    override suspend fun insertFavoriteCurrency(currency: Currency) = dao.insertRate(currency = currency)

    override suspend fun deleteFavoriteCurrency(currency: Currency) = dao.deleteRate(currency = currency)
}