package com.codewiththiru.ai.generation

interface QuestionGenerator {
    suspend fun generateQuestion(topic: String): String
}
