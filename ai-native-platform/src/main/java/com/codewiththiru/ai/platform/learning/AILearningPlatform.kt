package com.codewiththiru.ai.platform.learning

interface AITutor {
    suspend fun askQuestion(topic: String, question: String): String
}

interface LearningCoach {
    suspend fun getMotivation(userId: String): String
}

interface StudyPlanner {
    suspend fun createPlan(userId: String, goal: String): String
}

interface QuizGenerator {
    suspend fun generateQuiz(topic: String, difficulty: Int): String
}
