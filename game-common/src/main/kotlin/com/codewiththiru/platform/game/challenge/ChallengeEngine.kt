package com.codewiththiru.platform.game.challenge

import com.codewiththiru.platform.game.reward.Reward
import kotlinx.serialization.Serializable

@Deprecated("Use ChallengeType from :game-challenges module instead")
@Serializable
enum class ChallengeType {
    DAILY,
    WEEKLY,
    MONTHLY,
    SEASONAL,
}

@Deprecated("Use ChallengeDefinition and ChallengeProgress from :game-challenges module instead")
@Serializable
data class GameChallenge(
    val id: String,
    val title: String,
    val type: ChallengeType,
    val target: Int,
    val currentProgress: Int = 0,
    val reward: Reward,
    val isCompleted: Boolean = false,
)

@Deprecated("Use ChallengeEngine from :game-challenges module instead")
interface ChallengeEngine {
    fun getActiveChallenges(): List<GameChallenge>

    fun trackProgress(challengeId: String, delta: Int): Boolean // returns true if newly completed

    fun registerChallenges(challenges: List<GameChallenge>)
}

class DefaultChallengeEngine(
    private val saveStorage: com.codewiththiru.platform.game.save.GameStorage,
    private val serializer: com.codewiththiru.platform.game.save.StateSerializer,
    private val onChallengeCompleted: (GameChallenge) -> Unit = {},
) : ChallengeEngine {
    private val challengesKey = "game_active_challenges"

    override fun registerChallenges(challenges: List<GameChallenge>) {
        saveList(challenges)
    }

    override fun getActiveChallenges(): List<GameChallenge> {
        val serialized = saveStorage.getString(challengesKey) ?: return emptyList()
        return try {
            serializer.deserialize(serialized, kotlin.reflect.typeOf<List<GameChallenge>>())
        } catch (e: kotlin.coroutines.cancellation.CancellationException) {
            throw e
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun trackProgress(
        challengeId: String,
        delta: Int,
    ): Boolean {
        val list = getActiveChallenges().toMutableList()
        val index = list.indexOfFirst { it.id == challengeId }
        if (index == -1) return false

        val challenge = list[index]
        if (challenge.isCompleted) return false

        val newProgress = minOf(challenge.target, challenge.currentProgress + delta)
        val newlyCompleted = newProgress >= challenge.target

        val updated =
            challenge.copy(
                currentProgress = newProgress,
                isCompleted = newlyCompleted,
            )
        list[index] = updated
        saveList(list)

        if (newlyCompleted) {
            onChallengeCompleted(updated)
        }
        return newlyCompleted
    }

    private fun saveList(list: List<GameChallenge>) {
        saveStorage.putString(
            challengesKey,
            serializer.serialize(list, kotlin.reflect.typeOf<List<GameChallenge>>()),
        )
    }
}
