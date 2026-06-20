package com.codewiththiru.ai.rag

interface RagManager {
    suspend fun query(queryText: String): String
    suspend fun indexDocument(source: KnowledgeSource)
}
