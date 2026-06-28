package com.codewiththiru.platform.game.challenges

import com.codewiththiru.platform.game.challenges.api.ChallengeStatus
import com.codewiththiru.platform.game.challenges.progress.ChallengeProgress
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ChallengeTests {
    @Test
    fun `test condition is met when target reached`() {
        val progress =
            ChallengeProgress(
                challengeId = "daily_1",
                status = ChallengeStatus.ACTIVE,
                currentValues = mapOf("games_won" to 5L),
                activationTimeMs = 0L,
            )

        assertTrue(progress.isConditionMet("games_won", 5L))
        assertTrue(progress.isConditionMet("games_won", 3L))
    }

    @Test
    fun `test condition is not met when target unreached`() {
        val progress =
            ChallengeProgress(
                challengeId = "daily_1",
                status = ChallengeStatus.ACTIVE,
                currentValues = mapOf("games_won" to 2L),
                activationTimeMs = 0L,
            )

        assertFalse(progress.isConditionMet("games_won", 5L))

        // Uninitialized stat defaults to 0
        assertFalse(progress.isConditionMet("perfect_games", 1L))
    }
}
