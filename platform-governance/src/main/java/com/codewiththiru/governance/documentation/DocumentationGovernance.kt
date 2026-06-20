package com.codewiththiru.governance.documentation

interface DocumentationValidator {
    suspend fun validate(moduleName: String): DocumentationAudit
}

data class DocumentationPolicy(
    val requireReadme: Boolean = true,
    val requireApiGuide: Boolean = true,
    val requireArchitectureDoc: Boolean = true,
    val minKDocCoverage: Float = 0.8f
)

data class DocumentationCoverage(
    val publicApisWithDocs: Int,
    val totalPublicApis: Int
) {
    val coveragePercentage: Float get() = if (totalPublicApis > 0) publicApisWithDocs.toFloat() / totalPublicApis else 1.0f
}

data class DocumentationAudit(
    val moduleName: String,
    val hasReadme: Boolean,
    val hasApiGuide: Boolean,
    val hasArchitectureDoc: Boolean,
    val coverage: DocumentationCoverage,
    val completenessScore: Float,
    val passed: Boolean
)
