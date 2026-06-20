package com.codewiththiru.governance.dependencies

interface DependencyManager {
    suspend fun auditDependencies(moduleName: String): DependencyAudit
}

data class DependencyPolicy(
    val allowedLicenses: List<String> = listOf("Apache-2.0", "MIT", "BSD"),
    val blockDuplicateDependencies: Boolean = true,
    val maxVulnerabilitiesAllowed: Int = 0
)

data class DependencyRiskScore(
    val vulnerabilityScore: Int,
    val licenseRiskScore: Int,
    val duplicationPenalty: Int
)

data class DependencyAudit(
    val moduleName: String,
    val totalDependencies: Int,
    val riskScore: DependencyRiskScore,
    val violations: List<String>,
    val passed: Boolean
)
