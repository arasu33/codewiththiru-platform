package com.codewiththiru.platform.identity.security

data class BiometricPolicy(
    val requireForLogin: Boolean = false,
    val requireForPayments: Boolean = true,
    val requireForProfileEdits: Boolean = false,
    val timeoutMinutes: Long = 15,
)
