package com.codewiththiru.governance.api

import kotlinx.coroutines.flow.StateFlow

interface GovernanceManager {
    val state: StateFlow<GovernanceState>
    fun initialize(config: GovernanceConfig)
    suspend fun evaluateModule(moduleName: String): GovernanceResult
    suspend fun generateGovernanceReport(): String
}

data class GovernanceConfig(
    val strictMode: Boolean = true,
    val failOnWarnings: Boolean = false,
    val targetCoverage: Float = 0.95f
)

sealed class GovernanceState {
    object Idle : GovernanceState()
    data class Evaluating(val moduleName: String) : GovernanceState()
    data class Completed(val results: List<GovernanceResult>) : GovernanceState()
    data class Error(val exception: Exception) : GovernanceState()
}

sealed class GovernanceResult {
    data class Compliant(val moduleName: String, val score: Float) : GovernanceResult()
    data class NonCompliant(val moduleName: String, val violations: List<String>) : GovernanceResult()
}
