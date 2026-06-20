package com.codewiththiru.ai.safety

interface AiSafetyManager {
    suspend fun validatePrompt(prompt: String): Boolean
    suspend fun filterContent(content: String): String
}
