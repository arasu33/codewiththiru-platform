package com.codewiththiru.ai.recommendation

data class RecommendationContext(
    val userId: String,
    val currentActivity: String,
    val userPreferences: Map<String, String>
)
