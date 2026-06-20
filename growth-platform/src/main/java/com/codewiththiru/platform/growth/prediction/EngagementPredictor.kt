package com.codewiththiru.platform.growth.prediction

interface EngagementPredictor {
    suspend fun predictNextAction(userId: String): String?
}

class DefaultEngagementPredictor : EngagementPredictor {
    override suspend fun predictNextAction(userId: String): String? {
        // ML Model dummy
        return "open_course"
    }
}
