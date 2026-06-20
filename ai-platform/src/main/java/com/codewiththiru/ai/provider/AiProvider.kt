package com.codewiththiru.ai.provider

import com.codewiththiru.ai.api.AiResult

interface AiProvider {
    val name: String
    
    suspend fun generateText(prompt: String): AiResult<String>
    suspend fun generateEmbeddings(text: String): AiResult<List<Float>>
}
