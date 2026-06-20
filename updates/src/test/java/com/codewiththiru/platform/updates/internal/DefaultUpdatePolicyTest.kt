package com.codewiththiru.platform.updates.internal

import com.codewiththiru.platform.updates.api.UpdateClock
import com.codewiththiru.platform.updates.api.UpdateConfig
import com.codewiththiru.platform.updates.api.UpdateEligibilityResult
import com.codewiththiru.platform.updates.api.UpdateStorageProvider
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultUpdatePolicyTest {

    private val mockStorage = object : UpdateStorageProvider {
        var lastPrompt: Long = 0L
        var lastDefer: Long = 0L
        var lastShownVersion: Int = 0
        
        override suspend fun getLastPromptDate(): Long = lastPrompt
        override suspend fun setLastPromptDate(timestamp: Long) { lastPrompt = timestamp }
        override suspend fun getLastDeferDate(): Long = lastDefer
        override suspend fun setLastDeferDate(timestamp: Long) { lastDefer = timestamp }
        override suspend fun getLastShownReleaseNotesVersion(): Int = lastShownVersion
        override suspend fun setLastShownReleaseNotesVersion(versionCode: Int) { lastShownVersion = versionCode }
    }

    private val mockClock = object : UpdateClock {
        var currentTime: Long = 0L
        override fun now(): Long = currentTime
    }

    private val config = UpdateConfig(
        minRequiredVersionCode = 10,
        flexibleUpdateCooldownDays = 3,
        isForceUpdateEnabled = false
    )

    private val policy = DefaultUpdatePolicy(config, mockStorage, mockClock)

    @Test
    fun `evaluate returns NoUpdateAvailable when available is not greater than current`() = runTest {
        val result = policy.evaluate(currentVersion = 15, availableVersion = 15, isForceUpdateRequired = false)
        assertEquals(UpdateEligibilityResult.NoUpdateAvailable, result)
    }

    @Test
    fun `evaluate returns ForceUpdateRequired when current is less than min required`() = runTest {
        val result = policy.evaluate(currentVersion = 5, availableVersion = 15, isForceUpdateRequired = false)
        assertEquals(UpdateEligibilityResult.ForceUpdateRequired, result)
    }

    @Test
    fun `evaluate returns ForceUpdateRequired when forced by remote flag`() = runTest {
        val result = policy.evaluate(currentVersion = 15, availableVersion = 16, isForceUpdateRequired = true)
        assertEquals(UpdateEligibilityResult.ForceUpdateRequired, result)
    }

    @Test
    fun `evaluate returns CooldownActive if deferred recently`() = runTest {
        mockStorage.lastDefer = 0L
        mockClock.currentTime = 1 * 24 * 60 * 60 * 1000L // 1 day later
        
        val result = policy.evaluate(currentVersion = 15, availableVersion = 16, isForceUpdateRequired = false)
        assertEquals(UpdateEligibilityResult.CooldownActive, result)
    }

    @Test
    fun `evaluate returns Eligible if cooldown passed`() = runTest {
        mockStorage.lastDefer = 0L
        mockClock.currentTime = 4 * 24 * 60 * 60 * 1000L // 4 days later (cooldown is 3)
        
        val result = policy.evaluate(currentVersion = 15, availableVersion = 16, isForceUpdateRequired = false)
        assertEquals(UpdateEligibilityResult.Eligible, result)
    }

    @Test
    fun `evaluate returns AlreadyShown if prompted recently`() = runTest {
        // Assume defer cooldown is passed, but prompt cooldown is active
        mockStorage.lastDefer = 0L
        mockStorage.lastPrompt = 4 * 24 * 60 * 60 * 1000L // prompted today
        mockClock.currentTime = 4 * 24 * 60 * 60 * 1000L + 1000L // 1 sec later
        
        val result = policy.evaluate(currentVersion = 15, availableVersion = 16, isForceUpdateRequired = false)
        assertEquals(UpdateEligibilityResult.AlreadyShown, result)
    }
}
