package com.codewiththiru.platform.identity.api

sealed interface IdentityResult<out T> {
    data class Success<T>(val data: T) : IdentityResult<T>
    data class Failure(val error: IdentityException) : IdentityResult<Nothing>
}
