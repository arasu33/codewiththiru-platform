package com.codewiththiru.platform.about.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.codewiththiru.platform.about.AboutDefaults
import com.codewiththiru.platform.about.model.AboutConfig
import com.codewiththiru.platform.about.model.AppInfo
import com.codewiththiru.platform.about.model.DeveloperInfo
import com.codewiththiru.platform.about.model.DeviceInfo
import com.codewiththiru.platform.about.model.DiagnosticsConfig
import com.codewiththiru.platform.about.model.LegalInfo
import com.codewiththiru.platform.about.model.SocialLink
import com.codewiththiru.platform.about.state.AboutUiState
import com.codewiththiru.platform.designsystem.theme.CustTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
class AboutScreenScreenshotTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
    fun captureFullAboutScreen() {
        val config =
            AboutConfig
                .Builder()
                .setAppInfo(
                    AppInfo(
                        appName = "Test App",
                        versionName = "1.0.0",
                        versionCode = 1L,
                        buildType = "Release",
                        packageName = "com.test.app",
                    ),
                ).setDeveloperInfo(
                    DeveloperInfo(
                        developerName = "CodeWithThiru",
                        websiteUrl = "https://example.com",
                        socialLinks =
                            listOf(
                                SocialLink("GitHub", "https://github.com/codewiththiru"),
                            ),
                    ),
                ).setDeviceInfo(
                    DeviceInfo(
                        osVersion = "14",
                        apiLevel = 34,
                        manufacturer = "Google",
                        model = "Pixel 5",
                    ),
                ).setLegalInfo(
                    LegalInfo(
                        privacyPolicyUrl = "https://example.com/privacy",
                        termsOfServiceUrl = "https://example.com/terms",
                        showOssLicenses = true,
                    ),
                ).setDiagnosticsConfig(DiagnosticsConfig(includeDeviceInfo = true))
                .setVisibility(AboutDefaults.visibility())
                .build()

        composeTestRule.setContent {
            CustTheme {
                AboutScreen(
                    uiState = AboutUiState.Success(config),
                    eventListener = AboutDefaults.eventListener(),
                    licenseProvider = null,
                )
            }
        }

        composeTestRule.onRoot().captureRoboImage()
    }
}
