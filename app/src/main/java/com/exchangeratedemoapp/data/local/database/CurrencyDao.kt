package com.exchangeratedemoapp.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exchangeratedemoapp.domain.models.Currency
import kotlinx.coroutines.flow.Flow


@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRate(currency: Currency)

    @Query("SELECT * FROM currency")
    fun getAllRates(): Flow<List<Currency>>

    @Query("SELECT * FROM currency")
    fun getAllRatesList(): List<Currency>

    @Delete
    suspend fun deleteRate(currency: Currency)
}