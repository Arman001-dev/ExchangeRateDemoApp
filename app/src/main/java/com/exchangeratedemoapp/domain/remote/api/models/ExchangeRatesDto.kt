package com.exchangeratedemoapp.domain.remote.api.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExchangeRatesDto(
    @SerializedName("base")
    val base: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("rates")
    val rates: Map<String, Double>?,
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("timestamp")
    val timestamp: Long?,
) : Serializable