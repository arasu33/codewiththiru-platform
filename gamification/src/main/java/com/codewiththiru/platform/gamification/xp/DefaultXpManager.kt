package com.codewiththiru.platform.gamification.xp

import com.codewiththiru.platform.gamification.repository.GamificationRepository
import kotlinx.coroutines.flow.first

public class DefaultXpManager(
    private val repository: GamificationRepository,
    private val policy: XpPolicy = XpPolicy()
) : XpManager {

    override suspend fun awardXp(userId: String, event: XpEvent): Long {
        val currentState = repository.observeLocalState(userId).first()
        val xpToAward = (event.baseAmount * policy.baseMultiplier).toLong()
        
        val newState = currentState.copy(
            totalXp = currentState.totalXp + xpToAward,
            lastSyncTimestamp = System.currentTimeMillis()
        )
        
        repository.commitState(newState)
        return xpToAward
    }

    override suspend fun getTotalXp(userId: String): Long {
        return repository.observeLocalState(userId).first().totalXp
    }
}
