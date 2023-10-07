package com.exchangeratedemoapp.di

import com.exchangeratedemoapp.data.remote.api.net.ExchangeRatesApiService
import com.exchangeratedemoapp.data.usecase.ExchangeRatesUseCase
import com.exchangeratedemoapp.domain.remote.api.repositories.ApiRequestRepository
import com.exchangeratedemoapp.domain.remote.api.repositories.DatabaseRepository
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
    fun provideExchangeRatesUseCase(apiRequestRepository: ApiRequestRepository, databaseRepository: DatabaseRepository): ExchangeRatesUseCase {
        return ExchangeRatesUseCase(apiRequestRepository, databaseRepository)
    }
}