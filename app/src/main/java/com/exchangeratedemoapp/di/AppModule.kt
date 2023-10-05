package com.exchangeratedemoapp.di

import com.exchangeratedemoapp.data.remote.api.net.ExchangeRatesApiService
import com.exchangeratedemoapp.data.remote.api.repositories.ExchangeRatesRepositoryImpl
import com.exchangeratedemoapp.domain.remote.api.repositories.ExchangeRatesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

}