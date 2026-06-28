package com.codewiththiru.platform.game.events

import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.game.events.api.PlatformEvent
import com.codewiththiru.platform.game.events.priority.EventPriority

sealed class GameEvent : PlatformEvent {
    override val timestamp: Long = System.currentTimeMillis()
    override val priority: EventPriority = EventPriority.NORMAL

    data class GameStarted(
        val gameId: String,
        val gameMode: String,
        val difficulty: String,
    ) : GameEvent() {
        override fun toAnalyticsEvent() =
            AnalyticsEvent(
                "game_started",
                mapOf("game_id" to gameId, "game_mode" to gameMode, "difficulty" to difficulty),
                timestamp,
            )
    }

    data class GamePaused(
        val gameId: String,
        val durationSeconds: Long,
    ) : GameEvent() {
        override fun toAnalyticsEvent() =
            AnalyticsEvent(
                "game_paused",
                mapOf("game_id" to gameId, "duration_seconds" to durationSeconds),
                timestamp,
            )
    }

    data class GameResumed(
        val gameId: String,
    ) : GameEvent() {
        override fun toAnalyticsEvent() =
            AnalyticsEvent(
                "game_resumed",
                mapOf("game_id" to gameId),
                timestamp,
            )
    }

    data class GameCompleted(
        val gameId: String,
        val result: String,
        val score: Long,
        val moves: Int,
        val timeSpentSeconds: Long,
    ) : GameEvent() {
        override fun toAnalyticsEvent() =
            AnalyticsEvent(
                "game_completed",
                mapOf(
                    "game_id" to gameId,
                    "result" to result,
                    "score" to score,
                    "moves" to moves,
                    "time_spent_seconds" to timeSpentSeconds,
                ),
                timestamp,
            )
    }

    data class HintUsed(
        val gameId: String,
        val hintType: String,
        val remainingHints: Int,
    ) : GameEvent() {
        override fun toAnalyticsEvent() =
            AnalyticsEvent(
                "hint_used",
                mapOf("game_id" to gameId, "hint_type" to hintType, "remaining_hints" to remainingHints),
                timestamp,
            )
    }

    data class UndoUsed(
        val gameId: String,
        val remainingUndos: Int,
    ) : GameEvent() {
        override fun toAnalyticsEvent() =
            AnalyticsEvent(
                "undo_used",
                mapOf("game_id" to gameId, "remaining_undos" to remainingUndos),
                timestamp,
            )
    }

    data class AchievementUnlocked(
        val achievementId: String,
        val isSecret: Boolean,
    ) : GameEvent() {
        override fun toAnalyticsEvent() =
            AnalyticsEvent(
                "achievement_unlocked",
                mapOf("achievement_id" to achievementId, "is_secret" to isSecret),
                timestamp,
            )
    }

    data class RewardClaimed(
        val rewardId: String,
        val rewardType: String,
        val amount: Long,
    ) : GameEvent() {
        override fun toAnalyticsEvent() =
            AnalyticsEvent(
                "reward_claimed",
                mapOf("reward_id" to rewardId, "reward_type" to rewardType, "amount" to amount),
                timestamp,
            )
    }
}
