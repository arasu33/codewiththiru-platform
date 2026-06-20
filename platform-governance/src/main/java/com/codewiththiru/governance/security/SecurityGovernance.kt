package com.codewiththiru.governance.security

interface SecurityGovernanceManager {
    suspend fun evaluateSecurity(moduleName: String): SecurityCertification
}

data class SecurityPolicy(
    val owaspTop10Validation: Boolean = true,
    val masvsValidation: Boolean = true,
    val enforceEncryptedSharedPreferences: Boolean = true
)

data class SecurityComplianceRule(
    val id: String,
    val description: String,
    val passed: Boolean
)

data class SecurityCertification(
    val moduleName: String,
    val isCertified: Boolean,
    val passedRules: List<SecurityComplianceRule>,
    val failedRules: List<SecurityComplianceRule>
)
