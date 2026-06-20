package com.codewiththiru.security.keystore

data class SecretRotationPolicy(
    val maxAgeDays: Int = 90,
    val requiresManualIntervention: Boolean = false
)
