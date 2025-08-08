package com.example.network_api.models

open class NetworkApiResult<T> {
    data class Loading<T>(val data: T? = null) : NetworkApiResult<T>()
    data class Success<T>(val data: T) : NetworkApiResult<T>()
    data class Error<T>(val error: String, val code: Int? = null) : NetworkApiResult<T>()
}