package com.codewiththiru.platform.developer.dashboard

interface DeveloperDashboard {
    suspend fun getHealthScores(): List<DeveloperHealthScore>
    suspend fun getInsights(): List<DeveloperInsight>
}

class DefaultDeveloperDashboard : DeveloperDashboard {
    override suspend fun getHealthScores(): List<DeveloperHealthScore> {
        return listOf(DeveloperHealthScore("dev_plat", ":developer-platform", 100))
    }

    override suspend fun getInsights(): List<DeveloperInsight> {
        return emptyList()
    }
}
