package com.codewiththiru.platform.identity.api

data class IdentityConfig(
    val environment: IdentityEnvironment = IdentityEnvironment.PRODUCTION,
    val allowOfflineSessions: Boolean = true,
    val sessionTimeoutMinutes: Long = 60 * 24 * 7, // 7 days
    val maxDevicesPerUser: Int = 5,
    val requireBiometricsForSensitiveActions: Boolean = false,
)

enum class IdentityEnvironment {
    DEVELOPMENT,
    STAGING,
    PRODUCTION,
}
