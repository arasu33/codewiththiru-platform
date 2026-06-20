package com.codewiththiru.governance.analytics

interface AnalyticsGovernanceManager {
    suspend fun validateSchema(moduleName: String): Boolean
}

interface AnalyticsSchemaValidator {
    suspend fun validateEventCatalog(catalogJson: String): Boolean
}
