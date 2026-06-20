package com.codewiththiru.ai.recommendation

data class RecommendationPolicy(
    val maxResults: Int,
    val minimumScoreThreshold: Double,
    val allowExploration: Boolean
)
