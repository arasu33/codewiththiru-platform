package com.codewiththiru.platform.game.animation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Standard durations and easing configurations for game transitions.
 */
object MotionTokens {
    const val DURATION_SHORT_MS = 150L
    const val DURATION_NORMAL_MS = 300L
    const val DURATION_LONG_MS = 500L

    // Easing specs
    val EasingStandard = floatArrayOf(0.4f, 0.0f, 0.2f, 1.0f)
    val EasingAccelerate = floatArrayOf(0.4f, 0.0f, 1.0f, 1.0f)
    val EasingDecelerate = floatArrayOf(0.0f, 0.0f, 0.2f, 1.0f)
}

interface AnimationCoordinator {
    val activeAnimations: StateFlow<Set<String>>
    val isAnyAnimationRunning: StateFlow<Boolean>

    fun onAnimationStarted(animationId: String)

    fun onAnimationFinished(animationId: String)

    fun forceClear()
}

class DefaultAnimationCoordinator : AnimationCoordinator {
    private val _activeAnimations = MutableStateFlow<Set<String>>(emptySet())
    override val activeAnimations: StateFlow<Set<String>> = _activeAnimations.asStateFlow()

    private val _isAnyAnimationRunning = MutableStateFlow(false)
    override val isAnyAnimationRunning: StateFlow<Boolean> = _isAnyAnimationRunning.asStateFlow()

    override fun onAnimationStarted(animationId: String) {
        _activeAnimations.update { it + animationId }
        _isAnyAnimationRunning.value = true
    }

    override fun onAnimationFinished(animationId: String) {
        var newSize = 0
        _activeAnimations.update {
            val updated = it - animationId
            newSize = updated.size
            updated
        }
        if (newSize == 0) {
            _isAnyAnimationRunning.value = false
        }
    }

    override fun forceClear() {
        _activeAnimations.value = emptySet()
        _isAnyAnimationRunning.value = false
    }
}
