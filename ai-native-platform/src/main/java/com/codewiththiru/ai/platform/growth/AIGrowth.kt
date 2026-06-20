package com.codewiththiru.ai.platform.growth

interface GrowthOptimizer {
    suspend fun optimizeRetention(userId: String): Map<String, String>
}

interface MonetizationOptimizer {
    suspend fun optimizePaywall(userId: String): Map<String, String>
    suspend fun optimizeAdPlacement(userId: String): Map<String, String>
}

interface EngagementOptimizer {
    suspend fun optimizeNotifications(userId: String): Map<String, String>
}
