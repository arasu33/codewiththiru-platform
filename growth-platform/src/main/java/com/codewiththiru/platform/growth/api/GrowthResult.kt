package com.codewiththiru.platform.growth.api

sealed class GrowthResult<out T> {
    data class Success<out T>(val data: T) : GrowthResult<T>()
    data class Failure(val error: GrowthException) : GrowthResult<Nothing>()
}
