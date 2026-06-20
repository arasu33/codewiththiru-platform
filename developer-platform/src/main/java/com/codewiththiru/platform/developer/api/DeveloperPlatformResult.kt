package com.codewiththiru.platform.developer.api

sealed class DeveloperPlatformResult<out T> {
    data class Success<out T>(val data: T) : DeveloperPlatformResult<T>()
    data class Failure(val error: Throwable) : DeveloperPlatformResult<Nothing>()
}
