package com.codewiththiru.platform.coupons.security

import com.codewiththiru.platform.coupons.config.CouponCooldownPolicy
import com.codewiththiru.platform.coupons.provider.CouponClock
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

class CouponCooldownEngine(
    private val clock: CouponClock,
    private val policy: CouponCooldownPolicy
) {
    // Maps a user or device identifier to their attempt timestamps
    private val attempts = ConcurrentHashMap<String, CopyOnWriteArrayList<Long>>()

    suspend fun recordAttempt(identifier: String) {
        val list = attempts.getOrPut(identifier) { CopyOnWriteArrayList() }
        list.add(clock.currentTimeMillis())
        cleanupOldAttempts(identifier)
    }

    suspend fun isCooldownActive(identifier: String): Boolean {
        cleanupOldAttempts(identifier)
        val list = attempts[identifier] ?: return false
        return list.size >= policy.maxAttempts
    }

    private fun cleanupOldAttempts(identifier: String) {
        val list = attempts[identifier] ?: return
        val now = clock.currentTimeMillis()
        val cooldownMillis = policy.cooldownMinutes * 60 * 1000L
        
        list.removeIf { attemptTime ->
            now - attemptTime > cooldownMillis
        }
    }
}
