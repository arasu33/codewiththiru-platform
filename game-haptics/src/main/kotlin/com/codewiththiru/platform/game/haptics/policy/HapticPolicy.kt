package com.codewiththiru.platform.game.haptics.policy

import com.codewiththiru.platform.game.haptics.api.HapticPatternType

/**
 * Governs global rules for haptic playback.
 */
interface HapticPolicy {
    /**
     * Determines if a specific pattern type should be suppressed based on OS level policies
     * (e.g. Battery Saver, Do Not Disturb).
     */
    fun isSuppressed(type: HapticPatternType): Boolean
}
