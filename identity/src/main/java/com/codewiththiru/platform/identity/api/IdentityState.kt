package com.codewiththiru.platform.identity.api

sealed interface IdentityState {
    object Unauthenticated : IdentityState
    object Authenticating : IdentityState
    data class Authenticated(val userId: String, val isAnonymous: Boolean) : IdentityState
    data class Error(val exception: IdentityException) : IdentityState
}
