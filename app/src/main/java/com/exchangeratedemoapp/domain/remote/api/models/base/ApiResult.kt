package com.exchangeratedemoapp.domain.remote.api.models.base

sealed interface ApiResult<T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Error<T>(val message: String) : ApiResult<T>
}