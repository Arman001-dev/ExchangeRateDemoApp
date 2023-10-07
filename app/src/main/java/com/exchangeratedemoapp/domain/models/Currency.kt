package com.exchangeratedemoapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "currency")
data class Currency(
    val currency: String,
    val baseCurrency: String,
    val rate: Double,
    var isFavorite: Boolean = false,
    @PrimaryKey
    val key: String = currency + baseCurrency,
) : Serializable