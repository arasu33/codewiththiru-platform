package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
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
class CustSectionHeaderTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun sectionHeader_rendersTitleAndSubtitle() {
        composeTestRule.setContent {
            CustTheme {
                CustSectionHeader(
                    title = "Settings",
                    subtitle = "Account preferences",
                )
            }
        }
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
        composeTestRule.onNodeWithText("Account preferences").assertIsDisplayed()
    }

    @Test
    fun sectionHeader_rendersAction() {
        var clicked = false
        composeTestRule.setContent {
            CustTheme {
                CustSectionHeader(
                    title = "Settings",
                    action = {
                        CustButton(onClick = { clicked = true }) {
                            CustText("Edit")
                        }
                    },
                )
            }
        }
        composeTestRule.onNodeWithText("Edit").performClick()
        assertTrue(clicked)
    }

    @Test
    fun sectionHeader_accessibilitySemantics() {
        composeTestRule.setContent {
            CustTheme {
                CustSectionHeader(
                    title = "Settings",
                    subtitle = "Version 1.0",
                )
            }
        }
        composeTestRule.onNodeWithContentDescription("Settings. Version 1.0").assertIsDisplayed()
    }

    @Test
    @Config(qualifiers = "ar-ldrtl")
    fun sectionHeader_rtlSupport() {
        composeTestRule.setContent {
            CustTheme {
                CustSectionHeader(
                    title = "RTL Title",
                    modifier = Modifier.testTag("rtlHeader"),
                )
            }
        }
        composeTestRule.onNodeWithTag("rtlHeader").assertIsDisplayed()
        composeTestRule.onNodeWithText("RTL Title").assertIsDisplayed()
    }
}
