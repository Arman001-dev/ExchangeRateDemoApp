package com.exchangeratedemoapp.di

import com.exchangeratedemoapp.data.remote.api.net.ExchangeRatesApiService
import com.exchangeratedemoapp.data.remote.api.repositories.CurrenciesRepositoryImpl
import com.exchangeratedemoapp.data.usecase.CurrenciesUseCase
import com.exchangeratedemoapp.domain.remote.api.repositories.CurrenciesRepository
import com.exchangeratedemoapp.domain.remote.api.repositories.FavoriteCurrenciesDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrenciesModule {

    @Provides
    @Singleton
    fun provideCurrenciesRepository(api: ExchangeRatesApiService): CurrenciesRepository {
        return CurrenciesRepositoryImpl(api)
    }

    @Provides
    fun provideExchangeRatesService(retrofit: Retrofit): ExchangeRatesApiService {
        return retrofit.create(ExchangeRatesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrenciesUseCase(currenciesRepository: CurrenciesRepository, favoriteCurrenciesDatabaseRepository: FavoriteCurrenciesDatabaseRepository): CurrenciesUseCase {
        return CurrenciesUseCase(currenciesRepository, favoriteCurrenciesDatabaseRepository)
    }
}