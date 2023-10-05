package com.exchangeratedemoapp.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangeratedemoapp.data.usecase.ExchangeRatesUseCase
import com.exchangeratedemoapp.domain.remote.api.models.ExchangeRatesDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(private val exchangeRatesUseCase: ExchangeRatesUseCase) : ViewModel() {
    init {
        fetchExchangeRates()
    }

    private val _exchangeRates: MutableStateFlow<ExchangeRatesDto?> = MutableStateFlow(null)
    val exchangeRates: StateFlow<ExchangeRatesDto?> = _exchangeRates.asStateFlow()

    fun fetchExchangeRates() {
        viewModelScope.launch {
            exchangeRatesUseCase.invoke("EUR", "AED,AMD,BBD,BYN")
        }
    }
}