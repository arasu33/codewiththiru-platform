package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
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
class CustChipTest {
    @Test
    fun assistChip_rendersAndClicks() =
        runComposeUiTest {
            var clicked = false
            setContent {
                CustTheme {
                    CustAssistChip(
                        label = "Assist",
                        onClick = { clicked = true },
                    )
                }
            }
            onNodeWithText("Assist").assertIsDisplayed().performClick()
            assertTrue(clicked)
        }

    @Test
    fun filterChip_rendersAndClicks() =
        runComposeUiTest {
            var clicked = false
            setContent {
                CustTheme {
                    CustFilterChip(
                        label = "Filter",
                        selected = true,
                        onClick = { clicked = true },
                    )
                }
            }
            onNodeWithText("Filter").assertIsDisplayed().performClick()
            assertTrue(clicked)
        }

    @Test
    @Config(qualifiers = "ar-ldrtl")
    fun filterChip_rtlSupport() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustFilterChip(
                        label = "RTL Filter",
                        selected = false,
                        onClick = {},
                        modifier = Modifier.testTag("rtlFilter"),
                    )
                }
            }
            onNodeWithTag("rtlFilter").assertIsDisplayed()
            onNodeWithText("RTL Filter").assertIsDisplayed()
        }
}
