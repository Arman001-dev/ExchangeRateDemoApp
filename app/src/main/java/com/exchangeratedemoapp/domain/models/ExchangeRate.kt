package com.exchangeratedemoapp.domain.models

import java.io.Serializable

data class ExchangeRate(
    val base: String,
    val date: String,
    val rates: List<Currency>?,
) : Serializable
