package com.exchangeratedemoapp.domain.models

// TODO: Need to refactor after connecting api
data class Currency(val currency: String, val foreignCurrency: String, val rate: String, val isFavorite: Boolean)