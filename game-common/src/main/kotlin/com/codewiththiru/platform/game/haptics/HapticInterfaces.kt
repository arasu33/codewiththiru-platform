package com.codewiththiru.platform.game.haptics

@Deprecated("Use HapticPatternType from :game-haptics module instead")
enum class HapticFeedbackType {
    SELECTION,
    SUCCESS,
    FAILURE,
    WARNING,
}

@Deprecated("Use HapticPattern from :game-haptics module instead")
data class HapticPattern(
    val timings: LongArray,
    val amplitudes: IntArray? = null,
    val repeatIndex: Int = -1,
) {
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

@Deprecated("Use HapticManager from :game-haptics module instead")
interface HapticManager {
    var isHapticsEnabled: Boolean
    var isAccessibilityAware: Boolean

    fun performFeedback(type: HapticFeedbackType)

    fun performPattern(pattern: HapticPattern)

    fun cancel()
}
