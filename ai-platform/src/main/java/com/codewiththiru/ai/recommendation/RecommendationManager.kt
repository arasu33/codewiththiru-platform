package com.codewiththiru.ai.recommendation

import kotlinx.coroutines.flow.StateFlow

interface RecommendationManager {
    suspend fun getRecommendations(context: RecommendationContext): List<RecommendationModel>
}
