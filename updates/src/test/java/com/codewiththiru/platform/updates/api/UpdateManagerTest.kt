package com.codewiththiru.platform.updates.api

import android.app.Activity
import com.codewiththiru.platform.updates.internal.DefaultUpdatePolicy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import io.mockk.mockk

class UpdateManagerTest {

    private val mockActivity = mockk<Activity>(relaxed = true)

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
        override fun now(): Long = 100000000000L
    }

    private val mockSource = object : UpdateSource {
        var availabilityInfo = UpdateAvailabilityInfo(
            isUpdateAvailable = true,
            availableVersionCode = 20,
            clientStalenessDays = 0,
            isFlexibleAllowed = true,
            isImmediateAllowed = true
        )
        var startedUpdateType: UpdateType? = null

        override suspend fun checkForUpdate(): Result<UpdateAvailabilityInfo> = Result.success(availabilityInfo)

        override suspend fun startUpdate(activity: Activity, availabilityInfo: UpdateAvailabilityInfo, updateType: UpdateType): Result<Unit> {
            startedUpdateType = updateType
            return Result.success(Unit)
        }
    }

    @Test
    fun `checkAndPrompt triggers flexible update when eligible`() = runTest {
        val config = UpdateConfig(minRequiredVersionCode = 10)
        val policy = DefaultUpdatePolicy(config, mockStorage, mockClock)
        
        val manager = UpdateManager(mockSource, policy, mockStorage, mockClock)
        
        val effect = manager.checkAndPrompt(mockActivity, currentVersionCode = 15)
        
        assertEquals(UpdateEffect.LaunchFlexibleUpdate, effect)
        assertEquals(UpdateType.Flexible, mockSource.startedUpdateType)
    }

    @Test
    fun `checkAndPrompt triggers force update when required`() = runTest {
        val config = UpdateConfig(minRequiredVersionCode = 25) // Requires 25, current is 15
        val policy = DefaultUpdatePolicy(config, mockStorage, mockClock)
        
        val manager = UpdateManager(mockSource, policy, mockStorage, mockClock)
        
        val effect = manager.checkAndPrompt(mockActivity, currentVersionCode = 15)
        
        assertEquals(UpdateEffect.ShowForceUpdate, effect)
        assertEquals(UpdateType.Force, mockSource.startedUpdateType)
    }

    @Test
    fun `checkAndPrompt shows whats new if update not available and not shown yet`() = runTest {
        val config = UpdateConfig(minRequiredVersionCode = 10)
        val policy = DefaultUpdatePolicy(config, mockStorage, mockClock)
        
        mockSource.availabilityInfo = UpdateAvailabilityInfo(false, 15, 0, false, false)
        mockStorage.lastShownVersion = 10 // Haven't shown for 15 yet
        
        val manager = UpdateManager(mockSource, policy, mockStorage, mockClock)
        
        val effect = manager.checkAndPrompt(mockActivity, currentVersionCode = 15)
        
        assertEquals(UpdateEffect.ShowWhatsNew, effect)
        assertEquals(15, mockStorage.lastShownVersion)
    }
}
