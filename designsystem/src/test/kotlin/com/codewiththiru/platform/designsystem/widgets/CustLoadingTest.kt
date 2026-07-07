package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
class CustLoadingTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loading_rendersCircularWithoutMessage() {
        composeTestRule.setContent {
            CustTheme {
                CustLoading(
                    type = LoadingType.Circular,
                    modifier = Modifier.testTag("circularLoading"),
                )
            }
        }
        composeTestRule.onNodeWithTag("circularLoading").assertIsDisplayed()
    }

    @Test
    fun loading_rendersCircularWithMessage() {
        composeTestRule.setContent {
            CustTheme {
                CustLoading(
                    type = LoadingType.Circular,
                    message = "Please wait",
                    modifier = Modifier.testTag("circularLoadingMessage"),
                )
            }
        }
        composeTestRule.onNodeWithTag("circularLoadingMessage").assertIsDisplayed()
        composeTestRule.onNodeWithText("Please wait").assertIsDisplayed()
    }

    @Test
    fun loading_rendersLinearWithMessage() {
        composeTestRule.setContent {
            CustTheme {
                CustLoading(
                    type = LoadingType.Linear,
                    message = "Syncing...",
                    modifier = Modifier.testTag("linearLoadingMessage"),
                )
            }
        }
        composeTestRule.onNodeWithTag("linearLoadingMessage").assertIsDisplayed()
        composeTestRule.onNodeWithText("Syncing...").assertIsDisplayed()
    }
}
