package com.exchangeratedemoapp.presentation.screens.currencies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangeratedemoapp.data.usecase.ExchangeRatesUseCase
import com.exchangeratedemoapp.domain.models.CurrenciesEnum
import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.domain.remote.api.models.base.ApiResult
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
class CurrenciesViewModel @Inject constructor(private val exchangeRatesUseCase: ExchangeRatesUseCase) : ViewModel() {

    private val _currentCurrency: MutableStateFlow<CurrenciesEnum?> = MutableStateFlow(CurrenciesEnum.EUR)
    val currentCurrency: StateFlow<CurrenciesEnum?> = _currentCurrency.asStateFlow()

    private val _exchangeRates: MutableStateFlow<List<Currency>?> = MutableStateFlow(emptyList())
    val exchangeRates: StateFlow<List<Currency>?> = _exchangeRates.asStateFlow()

    fun setCurrency(currency: CurrenciesEnum?) {
        _currentCurrency.update { currency }
    }

    fun getExchangeRates(base: CurrenciesEnum) {
        viewModelScope.launch {
            exchangeRatesUseCase.invoke(base.label, CurrenciesEnum.values().toMutableList().apply { remove(base) }.map { it.label }).collectLatest { result ->
                when (result) {
                    is ApiResult.Success -> {
                        _exchangeRates.update { result.data.rates }
                    }

                    is ApiResult.Error -> Log.d(Constants.EXCHANGE_RATE_TAG, "Error:${result.message}")
                }
            }
        }
    }

//    fun getAllFavoriteRates() {
//        viewModelScope.launch {
//            exchangeRatesUseCase.getAllFavoriteRates().collectLatest { favoriteCurrencies ->
//                Log.d(Constants.EXCHANGE_RATE_TAG, "CurrenciesViewModel favorites:$favoriteCurrencies")
//                val list = exchangeRates.value?.toMutableList()?.map {
//                    it.copy(
//                        isFavorite = favoriteCurrencies.find { favoriteCurrency ->
//                            it.key == favoriteCurrency.key
//                        } != null
//                    )
//                }
//                _exchangeRates.update { currencies ->
//                    list
//                }
//            }
//        }
//    }

    fun setFavoriteRate(currency: Currency) {
        viewModelScope.launch {
            exchangeRatesUseCase.insertFavoriteRate(currency)
        }
    }

    fun deleteFavoriteRate(currency: Currency) {
        viewModelScope.launch {
            exchangeRatesUseCase.deleteFavoriteRate(currency)
        }
    }
}