package com.codewiththiru.platform.game.haptics.capabilities

/**
 * Describes the hardware capabilities of the device running the app.
 */
interface HapticCapabilities {
    /** Does the device have any vibrator hardware? */
    fun hasVibrator(): Boolean

    /** Can the device independently control the amplitude (strength) of vibrations? */
    fun hasAmplitudeControl(): Boolean

    /** Does the device support modern rich haptics primitives? */
    fun hasPrimitiveSupport(): Boolean
}
