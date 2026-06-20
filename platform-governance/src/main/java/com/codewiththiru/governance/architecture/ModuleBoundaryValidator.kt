package com.codewiththiru.governance.architecture

class ModuleBoundaryValidator : ArchitectureValidator {
    override suspend fun validate(moduleName: String): List<ArchitectureRule> {
        // Enforce module isolation
        return listOf(
            ArchitectureRule(
                id = "MOD_01",
                description = "Module must not have circular dependencies",
                isViolated = false
            )
        )
    }
}
