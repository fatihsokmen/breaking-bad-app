package com.github.fatihsokmen.breakingbad.data

sealed class Result<T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error<T>(val throwable: Throwable) : Result<T>()

    fun onSuccess(successHandler: (T) -> Unit): Result<T> = this.also {
        if (this is Success) successHandler(data)
    }

    fun onError(errorHandler: (t: Throwable) -> Unit): Result<T> = this.also {
        if (this is Error) errorHandler(throwable)
    }

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }
}