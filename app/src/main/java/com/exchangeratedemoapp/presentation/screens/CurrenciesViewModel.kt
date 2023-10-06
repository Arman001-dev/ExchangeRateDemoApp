package com.exchangeratedemoapp.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangeratedemoapp.data.usecase.ExchangeRatesUseCase
import com.exchangeratedemoapp.domain.models.CurrenciesEnum
import com.exchangeratedemoapp.domain.models.ExchangeRate
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

    init {
        getExchangeRates(CurrenciesEnum.EUR.label, listOf(CurrenciesEnum.AED.label, CurrenciesEnum.AMD.label, CurrenciesEnum.RUB.label, CurrenciesEnum.USD.label))
    }

    private val _currency: MutableStateFlow<CurrenciesEnum?> = MutableStateFlow(CurrenciesEnum.EUR)
    val currency: StateFlow<CurrenciesEnum?> = _currency.asStateFlow()

    private val _exchangeRates: MutableStateFlow<ExchangeRate?> = MutableStateFlow(null)
    val exchangeRates: StateFlow<ExchangeRate?> = _exchangeRates.asStateFlow()

    fun setCurrency(currency: CurrenciesEnum?) {
        _currency.update { currency }
    }

    fun getExchangeRates(base: String, symbols: List<String>) {
        viewModelScope.launch {
            exchangeRatesUseCase.invoke(base, symbols).collectLatest { result ->
                when (result) {
                    is ApiResult.Success -> {
                        Log.d(Constants.EXCHANGE_RATE_TAG, "Success:${result.data}")
                        _exchangeRates.update { result.data }
                    }

                    is ApiResult.Error -> Log.d(Constants.EXCHANGE_RATE_TAG, "Error:${result.message}")
                }
            }
        }
    }
}