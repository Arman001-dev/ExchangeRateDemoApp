package com.exchangeratedemoapp.di

import com.exchangeratedemoapp.data.remote.api.ExchangeRatesService
import com.exchangeratedemoapp.data.repositories.CurrenciesRepository
import com.exchangeratedemoapp.data.repositories.CurrenciesRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCurrenciesRepository(api: ExchangeRatesService): CurrenciesRepository {
        return CurrenciesRepositoryImpl(api)
    }
}