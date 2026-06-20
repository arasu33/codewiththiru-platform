package com.codewiththiru.governance.quality

interface QualityGateManager {
    suspend fun evaluateQuality(moduleName: String): QualityAudit
}

data class CodeQualityRule(
    val id: String,
    val name: String,
    val maxAllowedViolations: Int,
    val currentViolations: Int
) {
    val isPassing: Boolean get() = currentViolations <= maxAllowedViolations
}

data class QualityScore(
    val detektScore: Int,
    val ktlintScore: Int,
    val complexityScore: Int,
    val technicalDebtRatio: Float
)

data class QualityAudit(
    val moduleName: String,
    val score: QualityScore,
    val rules: List<CodeQualityRule>,
    val passed: Boolean
)
