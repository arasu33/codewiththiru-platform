package com.codewiththiru.remoteconfig.provider

import kotlin.math.pow

class CircuitBreakerPolicy(
    private val maxFailures: Int = 5,
    private val initialBackoffMs: Long = 5000L,
    private val maxBackoffMs: Long = 30 * 60 * 1000L // 30 minutes
) {
    private var currentFailures = 0
    private var lastFailureTime = 0L

    suspend fun <T> execute(block: suspend () -> Result<T>): Result<T> {
        if (isOpen()) {
            val waitTime = calculateBackoff()
            val timeSinceLastFailure = System.currentTimeMillis() - lastFailureTime
            if (timeSinceLastFailure < waitTime) {
                return Result.failure(Exception("Circuit breaker open. Try again later. Wait time: ${waitTime}ms"))
            }
        }

        val result = block()
        if (result.isSuccess) {
            reset()
        } else {
            recordFailure()
        }
        return result
    }

    private fun isOpen(): Boolean = currentFailures >= maxFailures

    private fun recordFailure() {
        currentFailures++
        lastFailureTime = System.currentTimeMillis()
    }

    private fun reset() {
        currentFailures = 0
        lastFailureTime = 0L
    }

    private fun calculateBackoff(): Long {
        if (currentFailures == 0) return 0L
        val exponentialFactor = 2.0.pow(currentFailures - 1).toLong()
        return (initialBackoffMs * exponentialFactor).coerceAtMost(maxBackoffMs)
    }
}
