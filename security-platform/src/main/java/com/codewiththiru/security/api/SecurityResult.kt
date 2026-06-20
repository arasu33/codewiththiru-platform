package com.codewiththiru.security.api

sealed class SecurityResult<out T> {
    data class Success<T>(val data: T) : SecurityResult<T>()
    data class Failure(val error: Throwable) : SecurityResult<Nothing>()
}
