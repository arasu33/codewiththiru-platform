package com.codewiththiru.ai.rag

data class KnowledgeSource(
    val id: String,
    val content: String,
    val metadata: Map<String, String>
)
