package com.exchangeratedemoapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.exchangeratedemoapp.domain.models.Currency

@Database(entities = [Currency::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun getCurrencyDao(): CurrencyDao
}