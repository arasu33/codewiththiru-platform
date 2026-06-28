package com.codewiththiru.platform.game.statistics.testing

import com.codewiththiru.platform.game.statistics.api.StatisticsMetric
import com.codewiththiru.platform.game.statistics.api.StatisticsProfile
import com.codewiththiru.platform.game.statistics.api.StatisticsProfileScope
import com.codewiththiru.platform.game.statistics.manager.StatisticsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * An in-memory fake for StatisticsManager, useful for unit tests.
 */
class FakeStatisticsManager : StatisticsManager {
    private val profiles = mutableMapOf<String, StatisticsProfile>()
    private val profileFlows = mutableMapOf<String, MutableStateFlow<StatisticsProfile>>()

    override suspend fun getLifetimeProfile(gameId: String): StatisticsProfile {
        return getProfile(gameId, StatisticsProfileScope.LIFETIME)
    }

    override suspend fun getProfile(
        gameId: String,
        scope: StatisticsProfileScope,
    ): StatisticsProfile {
        val key = "${gameId}_${scope.name}"
        return profiles[key] ?: StatisticsProfile(
            gameId = gameId,
            scope = scope,
            startTimeMs = 0L,
            endTimeMs = null,
        )
    }

    override fun observeLifetimeProfile(gameId: String): StateFlow<StatisticsProfile> {
        return observeProfile(gameId, StatisticsProfileScope.LIFETIME)
    }

    override fun observeProfile(
        gameId: String,
        scope: StatisticsProfileScope,
    ): StateFlow<StatisticsProfile> {
        val key = "${gameId}_${scope.name}"
        if (!profileFlows.containsKey(key)) {
            val initial = profiles[key] ?: StatisticsProfile(gameId, scope, 0L, null)
            profileFlows[key] = MutableStateFlow(initial)
        }
        return profileFlows[key]!!.asStateFlow()
    }

    override suspend fun resetStatistics(
        gameId: String,
        scope: StatisticsProfileScope,
    ) {
        val key = "${gameId}_${scope.name}"
        val emptyProfile = StatisticsProfile(gameId, scope, 0L, null)
        profiles[key] = emptyProfile
        profileFlows[key]?.value = emptyProfile
    }

    fun updateMetrics(
        gameId: String,
        scope: StatisticsProfileScope,
        metrics: Map<StatisticsMetric, Double>,
    ) {
        val key = "${gameId}_${scope.name}"
        val current = profiles[key] ?: StatisticsProfile(gameId, scope, 0L, null)
        val updated = current.copy(metrics = metrics)
        profiles[key] = updated
        profileFlows[key]?.value = updated
    }
}
