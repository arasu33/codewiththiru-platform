package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
class CustErrorStateTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun errorState_rendersTitleAndMessage() {
        composeTestRule.setContent {
            CustTheme {
                CustErrorState(
                    title = "Error",
                    message = "Something went wrong"
                )
            }
        }
        composeTestRule.onNodeWithText("Error").assertIsDisplayed()
        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
    }

    @Test
    fun errorState_rendersErrorCode() {
        composeTestRule.setContent {
            CustTheme {
                CustErrorState(
                    title = "Error",
                    errorCode = "404"
                )
            }
        }
        composeTestRule.onNodeWithText("Code: 404").assertIsDisplayed()
    }

    @Test
    fun errorState_triggersRetry() {
        var retried = false
        composeTestRule.setContent {
            CustTheme {
                CustErrorState(
                    title = "Error",
                    onRetry = { retried = true },
                    retryText = "Try Again"
                )
            }
        }
        composeTestRule.onNodeWithText("Try Again").performClick()
        assertTrue(retried)
    }

    @Test
    fun errorState_accessibilitySemantics() {
        composeTestRule.setContent {
            CustTheme {
                CustErrorState(
                    title = "Failed",
                    message = "Network error",
                    errorCode = "101"
                )
            }
        }
        // It merges into "Failed. Network error. Error code: 101"
        composeTestRule.onNodeWithContentDescription("Failed. Network error. Error code: 101").assertIsDisplayed()
    }

    @Test
    @Config(qualifiers = "sw600dp")
    fun errorState_tabletSupport() {
        composeTestRule.setContent {
            CustTheme {
                CustErrorState(
                    title = "Tablet Error",
                    modifier = Modifier.testTag("errorStateTablet")
                )
            }
        }
        composeTestRule.onNodeWithTag("errorStateTablet").assertIsDisplayed()
    }
}
