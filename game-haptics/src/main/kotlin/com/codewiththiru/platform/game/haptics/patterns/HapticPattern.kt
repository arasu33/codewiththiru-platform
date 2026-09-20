package com.codewiththiru.platform.game.haptics.patterns

/**
 * Represents a rich vibration waveform or primitive.
 */
data class HapticPattern(
    val timings: LongArray,
    val amplitudes: IntArray? = null,
    val repeatIndex: Int = -1,
) {
    /**
     * Scales the amplitudes of this pattern based on a multiplier.
     */
    fun scale(factor: Float): HapticPattern {
        if (amplitudes == null) return this

        val scaledAmplitudes =
            amplitudes
                .map { amp ->
                    when (amp) {
                        -1 -> -1 // -1 means DEFAULT_AMPLITUDE
                        0 -> 0 // 0 means OFF
                        else -> (amp * factor).toInt().coerceIn(0, 255)
                    }
                }.toIntArray()

        return this.copy(amplitudes = scaledAmplitudes)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HapticPattern

        if (!timings.contentEquals(other.timings)) return false
        if (amplitudes != null) {
            if (other.amplitudes == null) return false
            if (!amplitudes.contentEquals(other.amplitudes)) return false
        } else if (other.amplitudes != null) {
            return false
        }
        if (repeatIndex != other.repeatIndex) return false

        return true
    }

    override fun hashCode(): Int {
        var result = timings.contentHashCode()
        result = 31 * result + (amplitudes?.contentHashCode() ?: 0)
        result = 31 * result + repeatIndex
        return result
    }
}
