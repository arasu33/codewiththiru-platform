package com.codewiththiru.platform.growth.funnel

interface FunnelManager {
    suspend fun startFunnel(funnelId: String)
    suspend fun advanceFunnel(funnelId: String, stepId: String)
    suspend fun getFunnelDropOff(funnelId: String): Double
}

class DefaultFunnelManager : FunnelManager {
    override suspend fun startFunnel(funnelId: String) {
        // Mark funnel start
    }

    override suspend fun advanceFunnel(funnelId: String, stepId: String) {
        // Mark funnel progress
    }

    override suspend fun getFunnelDropOff(funnelId: String): Double {
        return 0.15 // 15% dummy dropoff
    }
}
