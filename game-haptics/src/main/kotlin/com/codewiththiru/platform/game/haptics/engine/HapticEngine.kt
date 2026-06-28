package com.codewiththiru.platform.game.haptics.engine

import com.codewiththiru.platform.game.haptics.api.HapticRequest
import com.codewiththiru.platform.game.haptics.api.HapticResponse

/**
 * The engine translates high-level requests into concrete patterns, factoring in policies.
 */
interface HapticEngine {
    /**
     * Submits a request to the engine. Returns the response/outcome.
     */
    suspend fun processRequest(request: HapticRequest): HapticResponse

    /**
     * Cancels all current vibrations and clears the queue.
     */
    suspend fun cancelAll()
}
