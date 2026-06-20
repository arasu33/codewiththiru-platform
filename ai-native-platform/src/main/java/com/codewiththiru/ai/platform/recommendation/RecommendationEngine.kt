package com.codewiththiru.ai.platform.recommendation

data class RecommendationModel(val id: String, val score: Float)
data class RecommendationPolicy(val minScore: Float, val maxItems: Int)

interface RecommendationEngine {
    suspend fun getRecommendations(userId: String, policy: RecommendationPolicy): List<RecommendationModel>
}
