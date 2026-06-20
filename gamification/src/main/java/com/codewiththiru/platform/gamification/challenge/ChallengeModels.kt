package com.codewiththiru.platform.gamification.challenge

public enum class ChallengeType {
    DAILY, WEEKLY, MONTHLY
}

public data class Challenge(
    val id: String,
    val type: ChallengeType,
    val title: String,
    val requiredCount: Int,
    val rewardXp: Long
)

public data class Mission(
    val id: String,
    val description: String,
    val target: Int
)

public data class Quest(
    val id: String,
    val title: String,
    val missions: List<Mission>,
    val rewardGems: Long
)

public interface ChallengeManager {
    public fun getActiveChallenges(): List<Challenge>
    public suspend fun reportProgress(userId: String, challengeId: String, amount: Int)
}

public interface QuestManager {
    public fun getActiveQuests(): List<Quest>
    public suspend fun reportMissionProgress(userId: String, missionId: String, amount: Int)
}
