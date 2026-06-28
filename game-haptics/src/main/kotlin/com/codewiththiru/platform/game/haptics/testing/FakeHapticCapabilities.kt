package com.codewiththiru.platform.game.haptics.testing

import com.codewiththiru.platform.game.haptics.capabilities.HapticCapabilities
import com.codewiththiru.platform.game.haptics.patterns.HapticPattern
import com.codewiththiru.platform.game.haptics.provider.HapticProvider

class FakeHapticCapabilities(
    private val hasVib: Boolean = true,
    private val hasAmp: Boolean = true,
    private val hasPrim: Boolean = true,
) : HapticCapabilities {
    override fun hasVibrator(): Boolean = hasVib

    override fun hasAmplitudeControl(): Boolean = hasAmp

    override fun hasPrimitiveSupport(): Boolean = hasPrim
}

class FakeHapticProvider(
    override val capabilities: HapticCapabilities = FakeHapticCapabilities(),
) : HapticProvider {
    var lastPlayedPattern: HapticPattern? = null
    var isCancelled: Boolean = false

    override suspend fun vibrate(pattern: HapticPattern) {
        lastPlayedPattern = pattern
        isCancelled = false
    }

    override suspend fun cancel() {
        isCancelled = true
        lastPlayedPattern = null
    }
}
