package com.codewiththiru.platform.updates.internal

import android.app.Activity
import com.codewiththiru.platform.updates.api.UpdateType
import com.google.android.gms.tasks.Tasks
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PlayStoreUpdateSourceTest {

    private val mockAppUpdateManager = mockk<AppUpdateManager>(relaxed = true)
    private val mockActivity = mockk<Activity>()

    @Test
    fun `checkForUpdate returns success when task completes`() = runTest {
        val mockInfo = mockk<AppUpdateInfo> {
            every { updateAvailability() } returns UpdateAvailability.UPDATE_AVAILABLE
            every { availableVersionCode() } returns 42
            every { clientVersionStalenessDays() } returns 3
            every { isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) } returns true
            every { isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) } returns false
        }

        every { mockAppUpdateManager.appUpdateInfo } returns Tasks.forResult(mockInfo)

        val source = PlayStoreUpdateSource(mockAppUpdateManager)
        val result = source.checkForUpdate()

        assertTrue(result.isSuccess)
        val info = result.getOrNull()!!
        assertTrue(info.isUpdateAvailable)
        assertEquals(42, info.availableVersionCode)
        assertEquals(3, info.clientStalenessDays)
        assertTrue(info.isFlexibleAllowed)
    }

    @Test
    fun `startUpdate invokes appUpdateManager`() = runTest {
        val mockInfo = mockk<AppUpdateInfo>()
        every {
            mockAppUpdateManager.startUpdateFlowForResult(
                mockInfo,
                AppUpdateType.FLEXIBLE,
                mockActivity,
                PlayStoreUpdateSource.PLAY_CORE_UPDATE_REQUEST_CODE
            )
        } returns true

        val source = PlayStoreUpdateSource(mockAppUpdateManager)
        val availabilityInfo = com.codewiththiru.platform.updates.api.UpdateAvailabilityInfo(
            isUpdateAvailable = true,
            availableVersionCode = 2,
            clientStalenessDays = null,
            isFlexibleAllowed = true,
            isImmediateAllowed = false,
            rawPayload = mockInfo
        )

        val result = source.startUpdate(mockActivity, availabilityInfo, UpdateType.Flexible)

        assertTrue(result.isSuccess)
        verify {
            mockAppUpdateManager.startUpdateFlowForResult(
                mockInfo,
                AppUpdateType.FLEXIBLE,
                mockActivity,
                PlayStoreUpdateSource.PLAY_CORE_UPDATE_REQUEST_CODE
            )
        }
    }
}
