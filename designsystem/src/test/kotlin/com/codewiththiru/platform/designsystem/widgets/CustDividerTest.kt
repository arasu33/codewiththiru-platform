package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
class CustDividerTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun horizontalDivider_renders() {
        composeTestRule.setContent {
            CustTheme {
                CustHorizontalDivider(modifier = Modifier.testTag("hDivider"))
            }
        }
        composeTestRule.onNodeWithTag("hDivider").assertIsDisplayed()
    }

    @Test
    fun verticalDivider_renders() {
        composeTestRule.setContent {
            CustTheme {
                CustVerticalDivider(modifier = Modifier.testTag("vDivider"))
            }
        }
        composeTestRule.onNodeWithTag("vDivider").assertIsDisplayed()
    }
}
