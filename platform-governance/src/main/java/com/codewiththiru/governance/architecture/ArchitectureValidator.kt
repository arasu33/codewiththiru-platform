package com.codewiththiru.governance.architecture

interface ArchitectureValidator {
    suspend fun validate(moduleName: String): List<ArchitectureRule>
}

data class ArchitectureRule(
    val id: String,
    val description: String,
    val isViolated: Boolean,
    val violationDetails: String? = null
)
