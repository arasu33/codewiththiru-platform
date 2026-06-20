package com.codewiththiru.governance.api

interface GovernanceRepository {
    suspend fun saveResult(result: GovernanceResult)
    suspend fun getResultsForModule(moduleName: String): List<GovernanceResult>
    suspend fun getAllResults(): List<GovernanceResult>
}
