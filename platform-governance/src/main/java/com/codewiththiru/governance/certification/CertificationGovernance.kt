package com.codewiththiru.governance.certification

interface CertificationManager {
    suspend fun certifyModule(moduleName: String): CertificationReport
    suspend fun certifyPlatform(): List<CertificationReport>
}

data class CertificationRule(
    val governanceArea: String,
    val passed: Boolean,
    val score: Float
)

enum class CertificationLevel {
    NONE, BRONZE, SILVER, GOLD, PLATINUM
}

data class CertificationReport(
    val moduleName: String,
    val level: CertificationLevel,
    val rulesEvaluated: List<CertificationRule>,
    val isEnterpriseReady: Boolean
)
