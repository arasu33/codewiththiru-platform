package com.codewiththiru.ai.rag

interface EmbeddingProvider {
    suspend fun getEmbedding(text: String): List<Float>
}
