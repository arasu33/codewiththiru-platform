package com.codewiththiru.platform.growth.viral

interface ViralLoopEngine {
    suspend fun recordInvite(inviterId: String, inviteeId: String)
    suspend fun calculateKFactor(): ViralCoefficient
}

class DefaultViralLoopEngine : ViralLoopEngine {
    private var totalUsers = 1000.0
    private var totalInvitesSent = 2500.0
    private var totalInvitesAccepted = 500.0

    override suspend fun recordInvite(inviterId: String, inviteeId: String) {
        totalInvitesSent++
        totalInvitesAccepted++
        totalUsers++
    }

    override suspend fun calculateKFactor(): ViralCoefficient {
        val invitesPerUser = totalInvitesSent / totalUsers
        val conversionRate = totalInvitesAccepted / totalInvitesSent
        val kFactor = invitesPerUser * conversionRate
        return ViralCoefficient(kFactor, invitesPerUser, conversionRate)
    }
}
