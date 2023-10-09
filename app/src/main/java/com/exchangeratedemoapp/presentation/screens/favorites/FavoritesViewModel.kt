package com.exchangeratedemoapp.presentation.screens.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangeratedemoapp.data.usecase.FavoritesUseCase
import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.domain.utils.base.Constants
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

    private val _favoriteCurrencies: MutableStateFlow<List<Currency>?> = MutableStateFlow(emptyList())
    val favoriteCurrencies: StateFlow<List<Currency>?> = _favoriteCurrencies.asStateFlow()

    fun getFavoriteCurrencies() {
        viewModelScope.launch {
            favoritesUseCase.getAllFavoriteCurrencies().collectLatest { favorites ->
                Log.d(Constants.EXCHANGE_RATE_TAG, "FavoriteViewModel favorites:$favorites")
                _favoriteCurrencies.update {
                    favorites.toMutableList().map {
                        it.apply {
                            isFavorite = true
                        }
                    }
                }
            }
        }
    }

    fun insertFavoriteCurrency(currency: Currency) {
        viewModelScope.launch {
            favoritesUseCase.insertFavoriteCurrency(currency)
        }
    }

    fun deleteFavoriteCurrency(currency: Currency) {
        viewModelScope.launch {
            favoritesUseCase.deleteFavoriteCurrency(currency)
        }
    }
}