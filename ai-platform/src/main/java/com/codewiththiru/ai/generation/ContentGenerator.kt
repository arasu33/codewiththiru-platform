package com.codewiththiru.ai.generation

interface ContentGenerator {
    suspend fun generateContent(topic: String): String
}
