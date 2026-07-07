package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.codewiththiru.platform.designsystem.components.CustButton
import com.codewiththiru.platform.designsystem.components.CustText
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
class CustEmptyStateTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emptyState_rendersTitleAndMessage() {
        composeTestRule.setContent {
            CustTheme {
                CustEmptyState(
                    title = "No Data",
                    message = "Nothing to see here",
                )
            }
        }
        composeTestRule.onNodeWithText("No Data").assertIsDisplayed()
        composeTestRule.onNodeWithText("Nothing to see here").assertIsDisplayed()
    }

    @Test
    fun emptyState_rendersActionButton() {
        var clicked = false
        composeTestRule.setContent {
            CustTheme {
                CustEmptyState(
                    title = "Empty",
                    actionButton = {
                        CustButton(onClick = { clicked = true }) {
                            CustText(text = "Action")
                        }
                    },
                )
            }
        }
        composeTestRule.onNodeWithText("Action").performClick()
        assertTrue(clicked)
    }

    @Test
    @Config(qualifiers = "ar-ldrtl")
    fun emptyState_rtlSupport() {
        composeTestRule.setContent {
            CustTheme {
                CustEmptyState(
                    title = "RTL Empty",
                    modifier = Modifier.testTag("emptyStateRtl"),
                )
            }
        }
        composeTestRule.onNodeWithTag("emptyStateRtl").assertIsDisplayed()
        composeTestRule.onNodeWithText("RTL Empty").assertIsDisplayed()
    }
}
