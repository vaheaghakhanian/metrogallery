package com.ramonapp.pixabay.domain.model

sealed class DataResult<out T> {
    data class Success<T>(val data: T?) : DataResult<T>()
    data class Failure(val statusCode: Int?, val e: Exception) : DataResult<Nothing>()
}