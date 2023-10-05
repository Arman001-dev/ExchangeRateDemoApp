package com.exchangeratedemoapp.di

import com.exchangeratedemoapp.data.remote.api.net.ExchangeRatesApiService
import com.exchangeratedemoapp.data.remote.api.repositories.ExchangeRatesRepositoryImpl
import com.exchangeratedemoapp.data.usecase.ExchangeRatesUseCase
import com.exchangeratedemoapp.domain.remote.api.repositories.ExchangeRatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExchangeRatesModule {

    @Provides
    fun provideExchangeRatesService(retrofit: Retrofit): ExchangeRatesApiService {
        return retrofit.create(ExchangeRatesApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideExchangeRatesRepository(api: ExchangeRatesApiService): ExchangeRatesRepository {
        return ExchangeRatesRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideExchangeRatesUseCase(exchangeRatesRepository: ExchangeRatesRepository): ExchangeRatesUseCase {
        return ExchangeRatesUseCase(exchangeRatesRepository)
    }
}