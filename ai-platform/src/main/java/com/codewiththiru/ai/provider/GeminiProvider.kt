package com.codewiththiru.ai.provider

import com.codewiththiru.ai.api.AiResult

class GeminiProvider : AiProvider {
    override val name: String = "gemini"
    
    override suspend fun generateText(prompt: String): AiResult<String> {
        return AiResult.Success("Response from Gemini")
    }

    override suspend fun generateEmbeddings(text: String): AiResult<List<Float>> {
        return AiResult.Success(emptyList())
    }
}
