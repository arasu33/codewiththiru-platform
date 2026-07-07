package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.codewiththiru.platform.designsystem.components.CustText
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
class CustBadgeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun badge_rendersExactCount() {
        composeTestRule.setContent {
            CustTheme {
                CustBadge(count = 5) {
                    CustText("Item")
                }
            }
        }
        composeTestRule.onNodeWithText("5").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("5 notifications").assertIsDisplayed()
    }

    @Test
    fun badge_rendersMaxCountPlus() {
        composeTestRule.setContent {
            CustTheme {
                CustBadge(count = 105, maxCount = 99) {
                    CustText("Item")
                }
            }
        }
        composeTestRule.onNodeWithText("99+").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("105 notifications").assertIsDisplayed()
    }
}
