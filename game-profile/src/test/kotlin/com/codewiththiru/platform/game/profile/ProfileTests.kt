package com.codewiththiru.platform.game.profile

import com.codewiththiru.platform.game.profile.level.PlayerLevel
import com.codewiththiru.platform.game.profile.testing.TestExperienceEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ProfileTests {
    private val engine = TestExperienceEngine()

    @Test
    fun `test experience engine levels up and calculates remainder overflow`() {
        val initial = PlayerLevel(currentLevel = 1, currentXp = 0)

        // Level 1 needs 1000 XP. Give 1500 XP.
        // Expect Level 2, with 500 XP remaining.
        val (updated, didLevelUp) = engine.applyXp(initial, 1500L)

        assertTrue(didLevelUp)
        assertEquals(2, updated.currentLevel)
        assertEquals(500L, updated.currentXp)
        assertEquals(2000L, updated.xpToNextLevel) // Level 2 requires 2000
    }

    @Test
    fun `test experience engine does not level up on insufficient xp`() {
        val initial = PlayerLevel(currentLevel = 1, currentXp = 0)

        val (updated, didLevelUp) = engine.applyXp(initial, 900L)

        assertFalse(didLevelUp)
        assertEquals(1, updated.currentLevel)
        assertEquals(900L, updated.currentXp)
        assertEquals(1000L, updated.xpToNextLevel)
    }

    @Test
    fun `test experience engine jumps multiple levels`() {
        val initial = PlayerLevel(currentLevel = 1, currentXp = 0)

        // Level 1 needs 1000, Level 2 needs 2000, Level 3 needs 3000.
        // Total needed to reach Level 4 = 1000 + 2000 + 3000 = 6000.
        // Grant 6500. Should hit Level 4, with 500 remaining.
        val (updated, didLevelUp) = engine.applyXp(initial, 6500L)

        assertTrue(didLevelUp)
        assertEquals(4, updated.currentLevel)
        assertEquals(500L, updated.currentXp)
    }
}
