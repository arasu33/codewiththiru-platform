package com.codewiththiru.ai.platform.content

interface ContentGenerator {
    suspend fun generateDailyChallenge(): String
}

interface QuestionGenerator {
    suspend fun generateQuestion(topic: String): String
}

interface LessonGenerator {
    suspend fun generateLesson(topic: String): String
}

interface ExplanationGenerator {
    suspend fun explainConcept(concept: String): String
}
