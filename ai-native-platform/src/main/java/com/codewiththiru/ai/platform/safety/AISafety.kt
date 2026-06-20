package com.codewiththiru.ai.platform.safety

interface PromptValidator {
    suspend fun validatePrompt(prompt: String): Boolean
}

interface ContentModerator {
    suspend fun moderateOutput(output: String): String
}

interface AIComplianceValidator {
    suspend fun checkCompliance(output: String): Boolean
}

class AISafetyManager(
    private val promptValidator: PromptValidator,
    private val contentModerator: ContentModerator,
    private val complianceValidator: AIComplianceValidator
)
