package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.codewiththiru.platform.designsystem.components.CustText
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
@OptIn(ExperimentalTestApi::class)
class CustBadgeTest {
    @Test
    fun badge_rendersExactCount() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustBadge(count = 5) {
                        CustText("Item")
                    }
                }
            }
            onNodeWithText("5").assertIsDisplayed()
            onNodeWithContentDescription("5 notifications").assertIsDisplayed()
        }

    @Test
    fun badge_rendersMaxCountPlus() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustBadge(count = 105, maxCount = 99) {
                        CustText("Item")
                    }
                }
            }
            onNodeWithText("99+").assertIsDisplayed()
            onNodeWithContentDescription("105 notifications").assertIsDisplayed()
        }
}
