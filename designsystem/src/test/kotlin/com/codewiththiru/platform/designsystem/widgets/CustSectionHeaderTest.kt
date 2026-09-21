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
import com.codewiththiru.platform.designsystem.components.CustButton
import com.codewiththiru.platform.designsystem.components.CustText
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
@OptIn(ExperimentalTestApi::class)
class CustSectionHeaderTest {
    @Test
    fun sectionHeader_rendersTitleAndSubtitle() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustSectionHeader(
                        title = "Settings",
                        subtitle = "Account preferences",
                    )
                }
            }
            onNodeWithText("Settings").assertIsDisplayed()
            onNodeWithText("Account preferences").assertIsDisplayed()
        }

    @Test
    fun sectionHeader_rendersAction() =
        runComposeUiTest {
            var clicked = false
            setContent {
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
            onNodeWithText("Edit").performClick()
            assertTrue(clicked)
        }

    @Test
    fun sectionHeader_accessibilitySemantics() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustSectionHeader(
                        title = "Settings",
                        subtitle = "Version 1.0",
                    )
                }
            }
            onNodeWithContentDescription("Settings. Version 1.0").assertIsDisplayed()
        }

    @Test
    @Config(qualifiers = "ar-ldrtl")
    fun sectionHeader_rtlSupport() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustSectionHeader(
                        title = "RTL Title",
                        modifier = Modifier.testTag("rtlHeader"),
                    )
                }
            }
            onNodeWithTag("rtlHeader").assertIsDisplayed()
            onNodeWithText("RTL Title").assertIsDisplayed()
        }
}
