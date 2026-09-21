package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
@OptIn(ExperimentalTestApi::class)
class CustInfoRowTest {
    @Test
    fun infoRow_rendersLabelAndValue() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustInfoRow(
                        label = "Version",
                        value = "1.0.0",
                    )
                }
            }
            onNodeWithText("Version").assertIsDisplayed()
            onNodeWithText("1.0.0").assertIsDisplayed()
        }

    @Test
    fun infoRow_isClickable() =
        runComposeUiTest {
            var clicked = false
            setContent {
                CustTheme {
                    CustInfoRow(
                        label = "Privacy Policy",
                        onClick = { clicked = true },
                    )
                }
            }
            onNodeWithText("Privacy Policy").performClick()
            assertTrue(clicked)
        }

    @Test
    fun infoRow_accessibilitySemantics() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustInfoRow(
                        label = "Developer",
                        value = "Google",
                    )
                }
            }
            // Tests the mergeDescendants and contentDescription builder
            onNodeWithContentDescription("Developer. Google").assertIsDisplayed()
        }

    @Test
    @Config(qualifiers = "ar-ldrtl-sw600dp")
    fun infoRow_tabletRtlSupport() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustInfoRow(
                        label = "Settings",
                        modifier = Modifier.testTag("infoRowTabletRtl"),
                    )
                }
            }
            onNodeWithTag("infoRowTabletRtl").assertIsDisplayed()
            onNodeWithText("Settings").assertIsDisplayed()
        }
}
