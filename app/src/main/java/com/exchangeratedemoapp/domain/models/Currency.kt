package com.exchangeratedemoapp.domain.models

import java.io.Serializable

data class Currency(
    val currency: String,
    val baseCurrency: String,
    val rate: Double,
    val isFavorite: Boolean = false
) : Serializable