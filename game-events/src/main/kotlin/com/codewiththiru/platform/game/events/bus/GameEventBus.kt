package com.codewiththiru.platform.game.events.bus

import com.codewiththiru.platform.game.events.api.PlatformEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * The core router powered by Kotlin Coroutines SharedFlow.
 */
interface GameEventBus {
    suspend fun emit(event: PlatformEvent)

    fun observe(): Flow<PlatformEvent>
}

class DefaultGameEventBus : GameEventBus {
    // extraBufferCapacity and onBufferOverflow can be configured as needed
    private val _events = MutableSharedFlow<PlatformEvent>(extraBufferCapacity = 64)
    val events = _events.asSharedFlow()

    override suspend fun emit(event: PlatformEvent) {
        _events.emit(event)
    }

    override fun observe(): Flow<PlatformEvent> {
        return events
    }
}
