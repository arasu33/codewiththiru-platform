package com.codewiththiru.platform.game.audio

import com.codewiththiru.platform.game.audio.profile.AudioConfiguration
import org.junit.Assert.assertEquals
import org.junit.Test

class AudioTests {
    @Test
    fun `test AudioConfiguration computes correct final volume`() {
        val config =
            AudioConfiguration(
                masterVolume = 0.5f,
                musicVolume = 0.8f,
                isMuted = false,
            )

        // 0.5 * 0.8 = 0.4
        val finalVolume = config.computeFinalVolume(config.musicVolume)
        assertEquals(0.4f, finalVolume, 0.001f)
    }

    @Test
    fun `test AudioConfiguration honors mute`() {
        val config =
            AudioConfiguration(
                masterVolume = 1.0f,
                effectsVolume = 1.0f,
                isMuted = true,
            )

        val finalVolume = config.computeFinalVolume(config.effectsVolume)
        assertEquals(0.0f, finalVolume, 0.001f)
    }
}
