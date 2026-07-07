package com.codewiththiru.platform.moreapps.presentation.viewmodel

import com.codewiththiru.platform.moreapps.data.repository.FakeMoreAppsRepository
import com.codewiththiru.platform.moreapps.domain.model.InstallStatus
import com.codewiththiru.platform.moreapps.domain.model.MoreAppModel
import com.codewiththiru.platform.moreapps.domain.model.MoreAppsResult
import com.codewiththiru.platform.moreapps.presentation.config.CachePolicy
import com.codewiththiru.platform.moreapps.presentation.config.MoreAppsConfig
import com.codewiththiru.platform.moreapps.presentation.config.MoreAppsDisplayType
import com.codewiththiru.platform.moreapps.presentation.config.MoreAppsEmptyStateConfig
import com.codewiththiru.platform.moreapps.presentation.integration.AppActionType
import com.codewiththiru.platform.moreapps.presentation.integration.AppInstallResolver
import com.codewiththiru.platform.moreapps.presentation.integration.MoreAppsAnalyticsProvider
import com.codewiththiru.platform.moreapps.presentation.state.MoreAppsEffect
import com.codewiththiru.platform.moreapps.presentation.state.MoreAppsUiState
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("ktlint:standard:max-line-length")
class MoreAppsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: FakeMoreAppsRepository
    private lateinit var viewModel: MoreAppsViewModel

    private val dummyConfig =
        MoreAppsConfig(
            displayType = MoreAppsDisplayType.Grid,
            emptyStateConfig = MoreAppsEmptyStateConfig("Empty", "No apps", "Retry"),
            cachePolicy = CachePolicy.CacheFirst,
        )

    private val stubInstallResolver =
        object : AppInstallResolver {
            override fun isInstalled(packageName: String) = packageName == "com.test.installed"

            override fun isUpdateAvailable(packageName: String) = false
        }

    private val dummyAnalytics =
        object : MoreAppsAnalyticsProvider {
            override fun logScreenView(screenName: String) {}

            override fun logAppAction(
                app: MoreAppModel,
                action: AppActionType,
                position: Int,
                section: String,
            ) {}

            override fun logSearchPerformed(query: String) {}

            override fun logCategorySelected(
                category: com.codewiththiru.platform.moreapps.domain.model.MoreAppsCategory,
            ) {}
        }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeMoreAppsRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadApps returns Success state with resolved install status`() =
        runTest {
            val apps =
                listOf(
                    MoreAppModel(
                        id = "1",
                        packageName = "com.test.installed",
                        title = "App 1",
                        description = "Desc 1",
                        iconUrl = "",
                    ),
                    MoreAppModel(
                        id = "2",
                        packageName = "com.test.notinstalled",
                        title = "App 2",
                        description = "Desc 2",
                        iconUrl = "",
                    ),
                )
            repository.appsResult = MoreAppsResult.Success(apps)

            viewModel = MoreAppsViewModel(repository, stubInstallResolver, dummyAnalytics, dummyConfig)
            testDispatcher.scheduler.advanceUntilIdle()

            val state = viewModel.uiState.value
            assertTrue(state is MoreAppsUiState.Success)
            val successState = state as MoreAppsUiState.Success
            assertEquals(2, successState.apps.size)
            assertEquals(InstallStatus.Installed, successState.apps[0].installStatus)
            assertEquals(InstallStatus.NotInstalled, successState.apps[1].installStatus)
        }

    @Test
    fun `onAppAction triggers OpenStore effect for Install action`() =
        runTest {
            val app =
                MoreAppModel(
                    id = "1",
                    packageName = "com.test.installed",
                    title = "App 1",
                    description = "Desc 1",
                    iconUrl = "",
                )
            repository.appsResult = MoreAppsResult.Success(listOf(app))
            viewModel = MoreAppsViewModel(repository, stubInstallResolver, dummyAnalytics, dummyConfig)

            // Launch collection
            val job =
                launch {
                    val effect = viewModel.effect.first()
                    assertTrue(effect is MoreAppsEffect.OpenStore)
                    assertEquals(app, (effect as MoreAppsEffect.OpenStore).app)
                }

            viewModel.onAppAction(app, AppActionType.Install, 0)
            testDispatcher.scheduler.advanceUntilIdle()
            job.cancel()
        }
}
