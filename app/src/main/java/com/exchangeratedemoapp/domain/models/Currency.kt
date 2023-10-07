package com.exchangeratedemoapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val currency: String,
    val baseCurrency: String,
    val rate: Double,
    var isFavorite: Boolean = false
) : Serializable