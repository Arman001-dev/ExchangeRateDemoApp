package com.exchangeratedemoapp.data.model

data class ExchangeRatesDto(
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
    val success: String,
    val timestamp: String,
)