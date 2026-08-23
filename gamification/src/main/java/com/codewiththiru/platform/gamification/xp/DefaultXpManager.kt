package com.codewiththiru.platform.gamification.xp

import com.codewiththiru.platform.gamification.repository.GamificationRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.withLock

public class DefaultXpManager(
    private val repository: GamificationRepository,
    private val policy: XpPolicy = XpPolicy()
) : XpManager {

    private val mutex = kotlinx.coroutines.sync.Mutex()

    override suspend fun awardXp(userId: String, event: XpEvent): Long {
        return mutex.withLock {
            val currentState = repository.observeLocalState(userId).first()
            val xpToAward = (event.baseAmount * policy.baseMultiplier).toLong()
            
            val newState = currentState.copy(
                totalXp = currentState.totalXp + xpToAward,
                lastSyncTimestamp = System.currentTimeMillis()
            )
            
            repository.commitState(newState)
            xpToAward
        }
    }

    override suspend fun getTotalXp(userId: String): Long {
        return repository.observeLocalState(userId).first().totalXp
    }
}
