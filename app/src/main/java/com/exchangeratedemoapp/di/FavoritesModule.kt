package com.exchangeratedemoapp.di

import com.exchangeratedemoapp.data.usecase.FavoritesUseCase
import com.exchangeratedemoapp.domain.remote.api.repositories.FavoriteCurrenciesDatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FavoritesModule {
    @Provides
    @Singleton
    fun provideFavoritesUseCase(favoriteCurrenciesDatabaseRepository: FavoriteCurrenciesDatabaseRepository): FavoritesUseCase {
        return FavoritesUseCase(favoriteCurrenciesDatabaseRepository)
    }
}