package com.codewiththiru.ai.learning

interface LearningCoach {
    suspend fun provideStudyAdvice(userId: String): String
}
