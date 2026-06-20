package com.codewiththiru.governance.ai

interface AIGovernanceManager {
    suspend fun auditAI(moduleName: String): AIAudit
}

data class AIPolicy(
    val blockHallucinationRisk: Boolean = true,
    val requireSafetyFilters: Boolean = true,
    val enforcePromptGovernance: Boolean = true,
    val preventPIILeakage: Boolean = true
)

data class AIAudit(
    val moduleName: String,
    val safetyScore: Float,
    val complianceViolations: List<String>,
    val passed: Boolean
)
