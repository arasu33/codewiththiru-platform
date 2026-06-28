package com.codewiththiru.platform.game.sync.network

import kotlinx.coroutines.flow.StateFlow

/**
 * Monitors device connectivity to govern when Sync Queues can drain.
 */
interface NetworkMonitor {
    /**
     * True if the device currently has an active internet connection.
     */
    val isConnected: StateFlow<Boolean>

    /**
     * True if the network is metered (e.g. Cellular data).
     * Useful for halting large backup syncs.
     */
    val isMetered: StateFlow<Boolean>
}
