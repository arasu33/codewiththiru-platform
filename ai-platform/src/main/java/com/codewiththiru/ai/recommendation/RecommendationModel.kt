package com.codewiththiru.ai.recommendation

data class RecommendationModel(
    val id: String,
    val title: String,
    val score: Double,
    val type: Type
) {
    enum class Type {
        LESSON,
        GAME,
        PRODUCT,
        CONTENT
    }
}
