package com.codewiththiru.ai.learning

interface LearningPathEngine {
    suspend fun generateOptimalPath(userId: String, goal: String): List<String>
}
