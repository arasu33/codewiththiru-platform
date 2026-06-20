package com.codewiththiru.ai.provider

import com.codewiththiru.ai.api.AiResult

class CompositeAiProvider(
    private val providers: List<AiProvider>
) : AiProvider {
    override val name: String = "composite"
    
    override suspend fun generateText(prompt: String): AiResult<String> {
        for (provider in providers) {
            val result = provider.generateText(prompt)
            if (result is AiResult.Success) {
                return result
            }
        }
        return AiResult.Failure(Exception("All providers failed"))
    }

    override suspend fun generateEmbeddings(text: String): AiResult<List<Float>> {
        for (provider in providers) {
            val result = provider.generateEmbeddings(text)
            if (result is AiResult.Success) {
                return result
            }
        }
        return AiResult.Failure(Exception("All providers failed"))
    }
}
