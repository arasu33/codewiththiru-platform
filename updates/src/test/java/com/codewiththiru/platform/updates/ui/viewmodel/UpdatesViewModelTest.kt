package com.codewiththiru.platform.updates.ui.viewmodel

import android.app.Activity
import com.codewiththiru.platform.updates.api.UpdateEffect
import com.codewiththiru.platform.updates.api.UpdateManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdatesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val mockManager = mockk<UpdateManager>(relaxed = true)
    private val mockActivity = mockk<Activity>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `checkForUpdates emits effect from manager`() = runTest(testDispatcher) {
        val viewModel = UpdatesViewModel(mockManager)

        coEvery { mockManager.checkAndPrompt(mockActivity, 10) } returns UpdateEffect.LaunchFlexibleUpdate

        val effects = mutableListOf<UpdateEffect>()
        val job = launch {
            viewModel.updateEffect.collect { effects.add(it) }
        }

        viewModel.checkForUpdates(mockActivity, 10)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(1, effects.size)
        assertEquals(UpdateEffect.LaunchFlexibleUpdate, effects.first())
        job.cancel()
    }

    @Test
    fun `onUpdateDeferred calls manager recordUserDeferral`() = runTest(testDispatcher) {
        val viewModel = UpdatesViewModel(mockManager)

        viewModel.onUpdateDeferred()
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { mockManager.recordUserDeferral() }
    }
}
