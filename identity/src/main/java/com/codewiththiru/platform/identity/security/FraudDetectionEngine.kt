package com.codewiththiru.platform.identity.security

interface FraudDetectionEngine {
    suspend fun analyzeLoginAttempt(
        userId: String,
        ipAddress: String,
        deviceId: String,
    ): FraudRisk
}

enum class FraudRisk {
    LOW,
    MEDIUM,
    HIGH,
    BLOCKED,
}
