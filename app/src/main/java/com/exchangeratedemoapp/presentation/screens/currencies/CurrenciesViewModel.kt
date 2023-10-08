package com.exchangeratedemoapp.presentation.screens.currencies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangeratedemoapp.data.usecase.ExchangeRatesUseCase
import com.exchangeratedemoapp.domain.models.CurrenciesEnum
import com.exchangeratedemoapp.domain.models.Currency
import com.exchangeratedemoapp.domain.models.FiltersOptionEnum
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

    private val _ratesFilter: MutableStateFlow<FiltersOptionEnum?> = MutableStateFlow(null)
    val ratesFilter: StateFlow<FiltersOptionEnum?> = _ratesFilter.asStateFlow()

    fun setRatesFilter(filter: String?) {
        _ratesFilter.update { FiltersOptionEnum.from(filter) }
    }

    fun setCurrency(currency: CurrenciesEnum?) {
        _currentCurrency.update { currency }
    }

    private fun setFilteredRates(exchangeRate: List<Currency>) {

        when (_ratesFilter.value) {
            FiltersOptionEnum.CODE_A_Z -> {
                Log.d("before", "$exchangeRate")
                _exchangeRates.update {
                    exchangeRate.sortedBy { it.currency }
                }
                Log.d("beforeAfter", "$exchangeRate")
                Log.d("beforeValue", "${_exchangeRates.value}")
            }

            FiltersOptionEnum.CODE_Z_A -> {
                _exchangeRates.update {
                    exchangeRate.sortedByDescending { it.currency }
                }
            }

            FiltersOptionEnum.QUOTE_ASC -> {
                _exchangeRates.update {
                    exchangeRate.sortedBy { it.rate }
                }
            }

            FiltersOptionEnum.QUOTE_DESC -> {
                _exchangeRates.update {
                    exchangeRate.sortedByDescending { it.rate }
                }
            }

            else -> {
                _exchangeRates.update { exchangeRate }
            }
        }
    }

    fun getExchangeRates(base: CurrenciesEnum) {
        viewModelScope.launch {
            exchangeRatesUseCase.invoke(base.label, CurrenciesEnum.values().toMutableList().apply { remove(base) }.map { it.label }).collectLatest { result ->
                when (result) {
                    is ApiResult.Success -> {
                        Log.d(Constants.EXCHANGE_RATE_TAG, "Success:${result.data}")
                        result.data.rates?.let { setFilteredRates(it) }
                    }

                    is ApiResult.Error -> Log.d(Constants.EXCHANGE_RATE_TAG, "Error:${result.message}")
                }
            }
        }
    }

    fun setFavoriteRate(currency: Currency) {
        viewModelScope.launch {
            exchangeRatesUseCase.insertFavoriteRate(currency)
        }
    }

    fun deleteFavoriteRate(currency: Currency){
        viewModelScope.launch {
            exchangeRatesUseCase.deleteFavoriteRate(currency)
        }
    }
}