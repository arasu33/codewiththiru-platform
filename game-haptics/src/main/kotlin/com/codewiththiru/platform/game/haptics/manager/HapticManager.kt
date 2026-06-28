package com.codewiththiru.platform.game.haptics.manager

import com.codewiththiru.platform.game.haptics.api.HapticPatternType
import com.codewiththiru.platform.game.haptics.api.HapticResponse
import com.codewiththiru.platform.game.haptics.patterns.HapticPattern
import com.codewiththiru.platform.game.haptics.profile.HapticConfiguration
import kotlinx.coroutines.flow.StateFlow

/**
 * Top level orchestrator for all game haptics.
 */
interface HapticManager {
    /**
     * Exposes the reactive state of the current haptic configuration.
     */
    val configuration: StateFlow<HapticConfiguration>

    /**
     * Globally enables or disables haptics.
     */
    fun setHapticsEnabled(enabled: Boolean)

    /**
     * Scales the global intensity of all vibrations (e.g. 0.5f for half strength).
     */
    fun setIntensityScale(scale: Float)

    /**
     * Performs a predefined haptic pattern type (like Selection, Success, Error).
     */
    suspend fun performHapticFeedback(type: HapticPatternType): HapticResponse

    /**
     * Submits a completely custom vibration pattern to the engine.
     */
    suspend fun performCustomPattern(pattern: HapticPattern): HapticResponse

    /**
     * Immediately halts any currently vibrating patterns.
     */
    suspend fun cancel()
}
