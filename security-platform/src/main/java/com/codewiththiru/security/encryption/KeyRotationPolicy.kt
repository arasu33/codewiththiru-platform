package com.codewiththiru.security.encryption

data class KeyRotationPolicy(
    val rotationIntervalDays: Int = 30,
    val enableAutomaticRotation: Boolean = true
)
