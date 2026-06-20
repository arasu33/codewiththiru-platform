package com.codewiththiru.ai.api

sealed class AiResult<out T> {
    data class Success<T>(val data: T) : AiResult<T>()
    data class Failure(val error: Throwable) : AiResult<Nothing>()
}
