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
class CustInfoRowTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun infoRow_rendersLabelAndValue() {
        composeTestRule.setContent {
            CustTheme {
                CustInfoRow(
                    label = "Version",
                    value = "1.0.0",
                )
            }
        }
        composeTestRule.onNodeWithText("Version").assertIsDisplayed()
        composeTestRule.onNodeWithText("1.0.0").assertIsDisplayed()
    }

    @Test
    fun infoRow_isClickable() {
        var clicked = false
        composeTestRule.setContent {
            CustTheme {
                CustInfoRow(
                    label = "Privacy Policy",
                    onClick = { clicked = true },
                )
            }
        }
        composeTestRule.onNodeWithText("Privacy Policy").performClick()
        assertTrue(clicked)
    }

    @Test
    fun infoRow_accessibilitySemantics() {
        composeTestRule.setContent {
            CustTheme {
                CustInfoRow(
                    label = "Developer",
                    value = "Google",
                )
            }
        }
        // Tests the mergeDescendants and contentDescription builder
        composeTestRule.onNodeWithContentDescription("Developer. Google").assertIsDisplayed()
    }

    @Test
    @Config(qualifiers = "ar-ldrtl-sw600dp")
    fun infoRow_tabletRtlSupport() {
        composeTestRule.setContent {
            CustTheme {
                CustInfoRow(
                    label = "Settings",
                    modifier = Modifier.testTag("infoRowTabletRtl"),
                )
            }
        }
        composeTestRule.onNodeWithTag("infoRowTabletRtl").assertIsDisplayed()
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()
    }
}
