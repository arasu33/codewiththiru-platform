package com.codewiththiru.ai.rag

interface VectorStore {
    suspend fun store(id: String, embedding: List<Float>, metadata: Map<String, String>)
    suspend fun search(embedding: List<Float>, limit: Int): List<KnowledgeSource>
}
