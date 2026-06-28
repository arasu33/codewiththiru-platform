package com.codewiththiru.platform.game.engine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

interface GameTimer {
    val timeFlow: StateFlow<Long>

    fun start(
        initialSeconds: Long = 0,
        countdown: Boolean = false,
        maxSeconds: Long? = null,
    )

    fun pause()

    fun resume()

    fun reset()

    fun destroy()
}

class DefaultGameTimer(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : GameTimer {
    private val _timeFlow = MutableStateFlow(0L)
    override val timeFlow: StateFlow<Long> = _timeFlow.asStateFlow()

    private var job: Job? = null
    private var isRunning = false
    private var isCountdown = false
    private var limitSeconds: Long? = null
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    override fun start(
        initialSeconds: Long,
        countdown: Boolean,
        maxSeconds: Long?,
    ) {
        job?.cancel()
        _timeFlow.value = initialSeconds
        isCountdown = countdown
        limitSeconds = maxSeconds
        isRunning = true

        job =
            scope.launch {
                while (isActive && isRunning) {
                    delay(1000)
                    if (isRunning) {
                        if (isCountdown) {
                            if (_timeFlow.value > 0) {
                                _timeFlow.value -= 1
                            } else {
                                isRunning = false
                            }
                        } else {
                            val limit = limitSeconds
                            if (limit == null || _timeFlow.value < limit) {
                                _timeFlow.value += 1
                            } else {
                                isRunning = false
                            }
                        }
                    }
                }
            }
    }

    override fun pause() {
        isRunning = false
    }

    override fun resume() {
        if (!isRunning) {
            isRunning = true
        }
    }

    override fun reset() {
        job?.cancel()
        _timeFlow.value = 0L
        isRunning = false
    }

    override fun destroy() {
        reset()
        scope.cancel()
    }
}
