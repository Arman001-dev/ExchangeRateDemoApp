package com.exchangeratedemoapp.presentation.screens.currencies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangeratedemoapp.data.usecase.CurrenciesUseCase
import com.exchangeratedemoapp.domain.models.CurrenciesEnum
import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.domain.models.FiltersOptionEnum
import com.exchangeratedemoapp.domain.remote.api.models.base.ApiResult
import com.exchangeratedemoapp.domain.utils.base.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(private val currenciesUseCase: CurrenciesUseCase) : ViewModel() {

    private val _currencies: MutableStateFlow<List<Currency>?> = MutableStateFlow(emptyList())
    val currencies: StateFlow<List<Currency>?> = _currencies.asStateFlow()

    private val _ratesFilter: MutableStateFlow<FiltersOptionEnum?> = MutableStateFlow(null)
    val ratesFilter: StateFlow<FiltersOptionEnum?> = _ratesFilter.asStateFlow()

    fun setRatesFilter(filter: String?) {
        _ratesFilter.update { FiltersOptionEnum.from(filter) }
    }

    fun getCurrencies(base: CurrenciesEnum) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                currenciesUseCase.invoke(base.label, CurrenciesEnum.values().toMutableList().apply { remove(base) }.map { it.label }).collectLatest { result ->
                    when (result) {
                        is ApiResult.Success -> {
                            _currencies.update { result.data.rates }
                        }

                        is ApiResult.Error -> Log.d(Constants.EXCHANGE_RATE_TAG, "Error:${result.message}")
                    }
                }
            }
        }
    }

    fun getAllFavoriteCurrencies() {
        viewModelScope.launch {
            currenciesUseCase.getAllFavoriteCurrencies().collectLatest { favoriteCurrencies ->
                val list = _currencies.value?.toMutableList()?.map {
                    it.copy(
                        isFavorite = favoriteCurrencies.any { favoriteCurrency ->
                            it.key == favoriteCurrency.key
                        }
                    )
                }
                _currencies.update {
                    list
                }
            }
        }
    }

    fun insertFavoriteCurrency(currency: Currency) {
        viewModelScope.launch {
            currenciesUseCase.insertFavoriteCurrency(currency)
        }
    }

    fun deleteFavoriteCurrency(currency: Currency) {
        viewModelScope.launch {
            currenciesUseCase.deleteFavoriteCurrency(currency)
        }
    }
}