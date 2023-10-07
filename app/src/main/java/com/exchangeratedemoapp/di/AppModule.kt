package com.exchangeratedemoapp.di

import android.content.Context
import androidx.room.Room
import com.exchangeratedemoapp.data.local.database.CurrencyDao
import com.exchangeratedemoapp.data.local.database.CurrencyDatabase
import com.exchangeratedemoapp.data.remote.api.net.ExchangeRatesApiService
import com.exchangeratedemoapp.data.remote.api.repositories.ApiRequestRepositoryImpl
import com.exchangeratedemoapp.data.remote.api.repositories.DatabaseRepositoryImpl
import com.exchangeratedemoapp.domain.remote.api.repositories.ApiRequestRepository
import com.exchangeratedemoapp.domain.remote.api.repositories.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCurrencyDatabase(@ApplicationContext appContext: Context): CurrencyDatabase {
        return Room.databaseBuilder(
            appContext,
            CurrencyDatabase::class.java,
            "currency"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCurrencyDao(currencyDatabase: CurrencyDatabase): CurrencyDao {
        return currencyDatabase.getCurrencyDao()
    }

    @Provides
    @Singleton
    fun provideApiRequestRepository(api: ExchangeRatesApiService): ApiRequestRepository {
        return ApiRequestRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(dao: CurrencyDao): DatabaseRepository {
        return DatabaseRepositoryImpl(dao)
    }
}