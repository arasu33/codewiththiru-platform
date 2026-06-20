package com.codewiththiru.platform.gamification.anticheat

import com.codewiththiru.platform.gamification.api.GamificationState

public interface IntegrityValidator {
    public fun validateStateHash(state: GamificationState, providedHash: String): Boolean
    public fun generateStateHash(state: GamificationState): String
}

public interface RewardFraudDetector {
    public fun isSuspiciousTransaction(userId: String, amount: Long, reason: String): Boolean
}

public interface ProgressValidator {
    public fun isValidXpGain(oldXp: Long, newXp: Long, timestampDelta: Long): Boolean
}

/**
 * Core engine to detect and prevent gamification manipulation.
 */
public class AntiCheatEngine(
    private val integrityValidator: IntegrityValidator,
    private val fraudDetector: RewardFraudDetector,
    private val progressValidator: ProgressValidator
) {
    public fun verifyStateIntegrity(state: GamificationState, storedHash: String): Boolean {
        return integrityValidator.validateStateHash(state, storedHash)
    }

    public fun verifyXpGain(oldXp: Long, newXp: Long, timestampDelta: Long): Boolean {
        // Example check: max 5000 XP per second
        val xpDelta = newXp - oldXp
        if (xpDelta < 0) return false
        if (timestampDelta <= 0 && xpDelta > 0) return false 
        
        return progressValidator.isValidXpGain(oldXp, newXp, timestampDelta)
    }

    public fun verifyEconomyTransaction(userId: String, amount: Long, reason: String): Boolean {
        return !fraudDetector.isSuspiciousTransaction(userId, amount, reason)
    }
}
