package com.codewiththiru.platform.developer.ai

interface AIDeveloperAssistant {
    suspend fun processQuery(query: String): String
}

class DefaultAIDeveloperAssistant(
    private val codeReviewAssistant: CodeReviewAssistant,
    private val architectureAssistant: ArchitectureAssistant
) : AIDeveloperAssistant {
    override suspend fun processQuery(query: String): String {
        return "AI Assistant Response"
    }
}
