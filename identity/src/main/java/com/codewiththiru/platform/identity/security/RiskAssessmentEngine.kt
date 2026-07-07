package com.codewiththiru.platform.identity.security

interface RiskAssessmentEngine {
    suspend fun assessRisk(userId: String, action: String): Float // 0.0 to 1.0

    suspend fun recordSuspiciousActivity(
        userId: String,
        action: String,
    )
}
