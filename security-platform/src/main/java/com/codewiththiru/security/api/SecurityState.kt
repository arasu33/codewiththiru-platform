package com.codewiththiru.security.api

sealed class SecurityState {
    object Uninitialized : SecurityState()
    object Initializing : SecurityState()
    object Secure : SecurityState()
    data class Compromised(val reason: String) : SecurityState()
}
