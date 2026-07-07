package com.codewiththiru.platform.game.challenges.testing

import com.codewiththiru.platform.game.challenges.api.ChallengeType
import com.codewiththiru.platform.game.challenges.definition.ChallengeDefinition
import com.codewiththiru.platform.game.challenges.manager.ChallengeManager
import com.codewiththiru.platform.game.challenges.progress.ChallengeProgress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeChallengeManager : ChallengeManager {
    private val _activeChallenges = MutableStateFlow<List<ChallengeProgress>>(emptyList())
    override val activeChallenges: StateFlow<List<ChallengeProgress>> = _activeChallenges.asStateFlow()

    var lastTrackedStatistic: String? = null
    var lastTrackedDelta: Long = 0L

    override fun getDefinition(challengeId: String): ChallengeDefinition? {
        // Return dummy
        return ChallengeDefinition(
            id = challengeId,
            type = ChallengeType.DAILY,
            conditions = emptyList(),
            rewardIds = emptyList(),
        )
    }

    override suspend fun trackStatistic(
        statisticKey: String,
        delta: Long,
    ) {
        lastTrackedStatistic = statisticKey
        lastTrackedDelta = delta
    }

    override suspend fun claimReward(challengeId: String): Boolean = true

    override suspend fun checkRotations(currentTimeMs: Long) {
        // No-op for fake
    }
}
