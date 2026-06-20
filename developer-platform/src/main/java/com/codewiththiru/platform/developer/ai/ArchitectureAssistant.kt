package com.codewiththiru.platform.developer.ai

interface ArchitectureAssistant {
    suspend fun suggestRefactoring(code: String): String
}

class DefaultArchitectureAssistant : ArchitectureAssistant {
    override suspend fun suggestRefactoring(code: String): String {
        return "Consider extracting an interface."
    }
}
