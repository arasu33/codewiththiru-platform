package com.codewiththiru.platform.game.haptics

import com.codewiththiru.platform.game.haptics.patterns.HapticPattern
import org.junit.Assert.assertArrayEquals
import org.junit.Test

class HapticTests {
    @Test
    fun `test HapticPattern scales amplitude correctly`() {
        val original =
            HapticPattern(
                timings = longArrayOf(0, 100, 50, 100),
                amplitudes = intArrayOf(0, 200, 0, 150),
            )

        // Scale by 0.5 (half intensity)
        val scaled = original.scale(0.5f)

        assertArrayEquals(longArrayOf(0, 100, 50, 100), scaled.timings)
        assertArrayEquals(intArrayOf(0, 100, 0, 75), scaled.amplitudes)
    }

    @Test
    fun `test HapticPattern scaling honors default unscalable amplitudes`() {
        // -1 represents DEFAULT_AMPLITUDE, which shouldn't be scaled mathematically
        val original =
            HapticPattern(
                timings = longArrayOf(0, 100),
                amplitudes = intArrayOf(0, -1),
            )

        val scaled = original.scale(0.5f)

        assertArrayEquals(intArrayOf(0, -1), scaled.amplitudes)
    }
}
