package com.codewiththiru.platform.gamification.xp

/**
 * Defines how XP is calculated and bounded.
 */
public data class XpPolicy(
    val baseMultiplier: Float = 1.0f,
    val maxXpPerDay: Long = 50_000L,
    val prestigeEnabled: Boolean = false
)

/**
 * Action that grants XP.
 */
public sealed class XpEvent(public val baseAmount: Long) {
    public data class LessonCompleted(val score: Int) : XpEvent(100L + score)
    public data class DailyLogin(val streakDays: Int) : XpEvent(50L + (streakDays * 10L))
    public data class CustomAction(val amount: Long, val reason: String) : XpEvent(amount)
}

/**
 * Manages the awarding of XP and tracking daily limits.
 */
public interface XpManager {
    public suspend fun awardXp(userId: String, event: XpEvent): Long
    public suspend fun getTotalXp(userId: String): Long
}
