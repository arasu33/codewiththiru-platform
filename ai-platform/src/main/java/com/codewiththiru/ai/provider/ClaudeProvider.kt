package com.codewiththiru.ai.provider

import com.codewiththiru.ai.api.AiResult

class ClaudeProvider : AiProvider {
    override val name: String = "claude"
    
    override suspend fun generateText(prompt: String): AiResult<String> {
        return AiResult.Success("Response from Claude")
    }

    override suspend fun generateEmbeddings(text: String): AiResult<List<Float>> {
        return AiResult.Success(emptyList())
    }
}
