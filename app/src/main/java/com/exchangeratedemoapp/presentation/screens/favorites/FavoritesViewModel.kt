package com.exchangeratedemoapp.presentation.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangeratedemoapp.data.usecase.FavoritesUseCase
import com.exchangeratedemoapp.domain.models.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val favoritesUseCase: FavoritesUseCase) : ViewModel() {

    private val _favoriteRates: MutableStateFlow<List<Currency>?> = MutableStateFlow(emptyList())
    val favoriteRates: StateFlow<List<Currency>?> = _favoriteRates.asStateFlow()
    fun getFavoriteRates() {
        viewModelScope.launch {
            favoritesUseCase.getAllFavoriteRate().collectLatest {
                _favoriteRates.update { it }
            }
        }
    }

    fun setFavoriteRate(currency: Currency) {
        viewModelScope.launch {
            favoritesUseCase.insertFavoriteRate(currency)
        }
    }

    fun deleteFavoriteRate(currency: Currency) {
        viewModelScope.launch {
            favoritesUseCase.deleteFavoriteRate(currency)
        }
    }
}