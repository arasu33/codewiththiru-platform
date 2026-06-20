package com.codewiththiru.platform.gamification.api

import kotlinx.coroutines.flow.Flow

/**
 * Central entry point for Gamification operations.
 * Wraps individual domain managers (XP, Achievements, etc.).
 */
public interface GamificationManager {
    /** Connects/Syncs the local state with remote backend. */
    public suspend fun initialize(userId: String)

    /** Observes the global state continuously. */
    public fun observeState(): Flow<GamificationState>

    /** Observes critical milestone events (Level Ups, Badges). */
    public fun observeEvents(): Flow<GamificationEvent>

    /** Force triggers an offline sync via WorkManager/Repository. */
    public suspend fun syncNow(): GamificationResult<Unit>
}
