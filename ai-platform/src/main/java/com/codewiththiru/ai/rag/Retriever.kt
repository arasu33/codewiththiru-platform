package com.codewiththiru.ai.rag

class Retriever(
    private val embeddingProvider: EmbeddingProvider,
    private val vectorStore: VectorStore
) {
    suspend fun retrieveContext(query: String): List<KnowledgeSource> {
        val embedding = embeddingProvider.getEmbedding(query)
        return vectorStore.search(embedding, 5)
    }
}
