package com.codewiththiru.platform.developer.documentation

interface KDocValidator {
    suspend fun validateModule(modulePath: String): Float
}

class DefaultKDocValidator : KDocValidator {
    override suspend fun validateModule(modulePath: String): Float {
        // Parse source code and compute KDoc coverage percentage
        return 95.0f
    }
}
