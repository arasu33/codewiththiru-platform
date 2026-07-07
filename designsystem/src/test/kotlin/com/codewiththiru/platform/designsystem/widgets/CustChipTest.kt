package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
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
class CustChipTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun assistChip_rendersAndClicks() {
        var clicked = false
        composeTestRule.setContent {
            CustTheme {
                CustAssistChip(
                    label = "Assist",
                    onClick = { clicked = true },
                )
            }
        }
        composeTestRule.onNodeWithText("Assist").assertIsDisplayed().performClick()
        assertTrue(clicked)
    }

    @Test
    fun filterChip_rendersAndClicks() {
        var clicked = false
        composeTestRule.setContent {
            CustTheme {
                CustFilterChip(
                    label = "Filter",
                    selected = true,
                    onClick = { clicked = true },
                )
            }
        }
        composeTestRule.onNodeWithText("Filter").assertIsDisplayed().performClick()
        assertTrue(clicked)
    }

    @Test
    @Config(qualifiers = "ar-ldrtl")
    fun filterChip_rtlSupport() {
        composeTestRule.setContent {
            CustTheme {
                CustFilterChip(
                    label = "RTL Filter",
                    selected = false,
                    onClick = {},
                    modifier = Modifier.testTag("rtlFilter"),
                )
            }
        }
        composeTestRule.onNodeWithTag("rtlFilter").assertIsDisplayed()
        composeTestRule.onNodeWithText("RTL Filter").assertIsDisplayed()
    }
}
