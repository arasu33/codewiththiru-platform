package com.codewiththiru.ai.provider

import com.codewiththiru.ai.api.AiResult

class OllamaProvider : AiProvider {
    override val name: String = "ollama"
    
    override suspend fun generateText(prompt: String): AiResult<String> {
        return AiResult.Success("Response from Ollama")
    }

    override suspend fun generateEmbeddings(text: String): AiResult<List<Float>> {
        return AiResult.Success(emptyList())
    }
}
