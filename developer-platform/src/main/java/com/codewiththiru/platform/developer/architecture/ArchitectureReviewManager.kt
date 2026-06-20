package com.codewiththiru.platform.developer.architecture

interface ArchitectureReviewManager {
    suspend fun analyzeModule(modulePath: String): ArchitectureReport
}

class DefaultArchitectureReviewManager : ArchitectureReviewManager {
    private val rules = listOf(
        ArchitectureReviewRule("clean_arch", "No data models in UI layer", true)
    )

    override suspend fun analyzeModule(modulePath: String): ArchitectureReport {
        return ArchitectureReport(
            moduleName = modulePath,
            score = ArchitectureScore(modulePath, 100),
            violations = emptyList()
        )
    }
}
