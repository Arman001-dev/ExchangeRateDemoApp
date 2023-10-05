package com.exchangeratedemoapp.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exchangeratedemoapp.data.model.ExchangeRatesDto
import com.exchangeratedemoapp.data.repositories.CurrenciesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(private val repo: CurrenciesRepository) : ViewModel() {
    init {
        fetchExchangeRates()
    }

    private val _exchangeRates: MutableStateFlow<ExchangeRatesDto?> = MutableStateFlow(null)
    val exchangeRates: StateFlow<ExchangeRatesDto?> = _exchangeRates.asStateFlow()
    fun fetchExchangeRates() {
        viewModelScope.launch {
            val response = repo.getExchangeRates("EUR", "AED,AMD,BBD,BYN")
                .onEach { it ->
                    _exchangeRates.update { it }

                }
        }
    }
}