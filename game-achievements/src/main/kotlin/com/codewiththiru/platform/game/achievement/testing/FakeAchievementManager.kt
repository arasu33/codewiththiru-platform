package com.codewiththiru.platform.game.achievement.testing

import com.codewiththiru.platform.game.achievement.api.AchievementDefinition
import com.codewiththiru.platform.game.achievement.api.AchievementProgress
import com.codewiththiru.platform.game.achievement.manager.AchievementManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeAchievementManager : AchievementManager {
    private val definitions = mutableListOf<AchievementDefinition>()
    private val progresses = mutableMapOf<String, AchievementProgress>()
    private val allProgressFlow = MutableStateFlow<List<AchievementProgress>>(emptyList())
    private val progressFlows = mutableMapOf<String, MutableStateFlow<AchievementProgress?>>()

    override fun getDefinitions(): List<AchievementDefinition> = definitions

    override suspend fun getProgress(achievementId: String): AchievementProgress? {
        return progresses[achievementId]
    }

    override fun observeAllProgress(): StateFlow<List<AchievementProgress>> = allProgressFlow.asStateFlow()

    override fun observeProgress(achievementId: String): StateFlow<AchievementProgress?> {
        if (!progressFlows.containsKey(achievementId)) {
            progressFlows[achievementId] = MutableStateFlow(progresses[achievementId])
        }
        return progressFlows[achievementId]!!.asStateFlow()
    }

    override suspend fun claimReward(achievementId: String): Boolean {
        // Fake implementation
        return true
    }

    fun addDefinition(def: AchievementDefinition) {
        definitions.add(def)
    }

    fun updateProgress(progress: AchievementProgress) {
        progresses[progress.achievementId] = progress
        progressFlows[progress.achievementId]?.value = progress
        allProgressFlow.value = progresses.values.toList()
    }
}
