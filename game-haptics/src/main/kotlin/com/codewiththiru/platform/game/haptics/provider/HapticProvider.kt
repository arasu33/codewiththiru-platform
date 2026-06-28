package com.codewiththiru.platform.game.haptics.provider

import com.codewiththiru.platform.game.haptics.capabilities.HapticCapabilities
import com.codewiththiru.platform.game.haptics.patterns.HapticPattern

/**
 * The hardware-agnostic contract for firing vibrations.
 * Application layer must implement this via Android's Vibrator or iOS CoreHaptics.
 */
interface HapticProvider {
    val capabilities: HapticCapabilities

    /**
     * Executes the requested raw pattern on the device hardware.
     */
    suspend fun vibrate(pattern: HapticPattern)

    /**
     * Cancels any currently playing vibrations.
     */
    suspend fun cancel()
}
