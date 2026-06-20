package com.codewiththiru.ai.generation

interface QuizGenerator {
    suspend fun generateQuiz(topic: String, difficulty: Int): String
}
