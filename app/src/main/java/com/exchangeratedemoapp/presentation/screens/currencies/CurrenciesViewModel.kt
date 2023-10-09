package com.exchangeratedemoapp.presentation.screens.currencies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangeratedemoapp.data.usecase.CurrenciesUseCase
import com.exchangeratedemoapp.domain.models.CurrenciesEnum
import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.domain.models.FilterOptionsEnum
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

    private val _currenciesFilter: MutableStateFlow<FilterOptionsEnum?> = MutableStateFlow(null)
    val currenciesFilter: StateFlow<FilterOptionsEnum?> = _currenciesFilter.asStateFlow()

    fun setCurrenciesFilter(filter: String?) {
        _currenciesFilter.update { FilterOptionsEnum.from(filter) }
    }

    private fun setFilteredRates(exchangeRate: List<Currency>) {
        when (_currenciesFilter.value) {
            FilterOptionsEnum.CODE_A_Z -> {
                _currencies.update {
                    exchangeRate.sortedBy { it.currency }
                }
            }

            FilterOptionsEnum.CODE_Z_A -> {
                _currencies.update {
                    exchangeRate.sortedByDescending { it.currency }
                }
            }

            FilterOptionsEnum.QUOTE_ASC -> {
                _currencies.update {
                    exchangeRate.sortedBy { it.rate }
                }
            }

            FilterOptionsEnum.QUOTE_DESC -> {
                _currencies.update {
                    exchangeRate.sortedByDescending { it.rate }
                }
            }

            else -> {
                _currencies.update { exchangeRate }
            }
        }
    }

    fun getCurrencies(base: CurrenciesEnum) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                currenciesUseCase.invoke(base.label, CurrenciesEnum.values().toMutableList().apply { remove(base) }.map { it.label }).collectLatest { result ->
                    when (result) {
                        is ApiResult.Success -> {
//                            _currencies.update { result.data.rates }
                            result.data.rates?.let { setFilteredRates(it) }
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