package com.codewiththiru.ai.platform.personalization

data class UserProfile(val id: String, val persona: String)
data class BehaviorProfile(val userId: String, val activeHours: List<Int>)
data class RecommendationProfile(val userId: String, val preferredTopics: List<String>)

interface PersonalizationEngine {
    suspend fun getPersonalizedContent(userId: String): String
    suspend fun updateBehavior(behaviorProfile: BehaviorProfile)
}
