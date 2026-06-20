package com.codewiththiru.platform.growth.retention

interface RetentionManager {
    suspend fun trackAppLaunch()
    suspend fun getRetention(day: Int): RetentionMetric
}

class DefaultRetentionManager : RetentionManager {
    override suspend fun trackAppLaunch() {
        // Log launch for retention analytics
    }

    override suspend fun getRetention(day: Int): RetentionMetric {
        // Dummy data
        return RetentionMetric(day, 50, 100)
    }
}
