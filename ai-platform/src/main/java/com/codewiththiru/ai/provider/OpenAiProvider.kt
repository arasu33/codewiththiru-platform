package com.codewiththiru.ai.provider

import com.codewiththiru.ai.api.AiResult

class OpenAiProvider : AiProvider {
    override val name: String = "openai"
    
    override suspend fun generateText(prompt: String): AiResult<String> {
        return AiResult.Success("Response from OpenAI")
    }

    override suspend fun generateEmbeddings(text: String): AiResult<List<Float>> {
        return AiResult.Success(emptyList())
    }
}
