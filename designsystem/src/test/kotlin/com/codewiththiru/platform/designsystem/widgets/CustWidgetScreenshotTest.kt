package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import com.codewiththiru.platform.designsystem.theme.CustTheme
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
@OptIn(ExperimentalTestApi::class)
class CustWidgetScreenshotTest {
    @Test
    @Config(qualifiers = RobolectricDeviceQualifiers.Pixel5)
    fun captureLoadingState() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustLoading(message = "Loading Baseline...")
                }
            }
            onRoot().captureRoboImage()
        }
}
