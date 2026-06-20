package com.codewiththiru.platform.gamification.config

/**
 * Configuration options for the entire gamification platform.
 */
public data class GamificationConfig(
    val isEnabled: Boolean = true,
    val offlineModeAllowed: Boolean = true,
    val antiCheatStrictness: AntiCheatLevel = AntiCheatLevel.MODERATE,
    val maxOfflineSyncQueueSize: Int = 100,
    val enableAnalytics: Boolean = true
)

public enum class AntiCheatLevel {
    DISABLED, // Trust the client unconditionally
    MODERATE, // Standard offline checks, signature verification
    STRICT    // Server-authoritative only (disables offline progression)
}
