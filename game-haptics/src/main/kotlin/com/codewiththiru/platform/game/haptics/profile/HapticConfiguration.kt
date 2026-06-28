package com.codewiththiru.platform.game.haptics.profile

import kotlinx.serialization.Serializable

/**
 * Defines the configuration for haptics.
 */
@Serializable
data class HapticConfiguration(
    val isEnabled: Boolean = true,
    val intensityScale: Float = 1.0f,
    val isAccessibilityAware: Boolean = true,
)

/**
 * A predefined profile of configurations (e.g. "Soft", "Medium", "Strong").
 */
@Serializable
data class HapticProfile(
    val id: String,
    val name: String,
    val configuration: HapticConfiguration,
)
