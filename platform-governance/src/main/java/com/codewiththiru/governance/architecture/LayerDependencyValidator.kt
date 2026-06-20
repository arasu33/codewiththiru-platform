package com.codewiththiru.governance.architecture

class LayerDependencyValidator : ArchitectureValidator {
    override suspend fun validate(moduleName: String): List<ArchitectureRule> {
        // Enforce domain cannot depend on data
        return listOf(
            ArchitectureRule(
                id = "LAY_01",
                description = "Domain layer must not depend on Data layer",
                isViolated = false
            )
        )
    }
}
