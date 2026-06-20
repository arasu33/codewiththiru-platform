package com.codewiththiru.platform.developer.dashboard

interface EngineeringDashboard {
    suspend fun getGlobalHealthScore(): Int
}

class DefaultEngineeringDashboard : EngineeringDashboard {
    override suspend fun getGlobalHealthScore(): Int {
        return 98
    }
}
