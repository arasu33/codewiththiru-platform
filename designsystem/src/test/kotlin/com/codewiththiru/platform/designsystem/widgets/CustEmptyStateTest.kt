package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
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
class CustEmptyStateTest {
    @Test
    fun emptyState_rendersTitleAndMessage() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustEmptyState(
                        title = "No Data",
                        message = "Nothing to see here",
                    )
                }
            }
            onNodeWithText("No Data").assertIsDisplayed()
            onNodeWithText("Nothing to see here").assertIsDisplayed()
        }

    @Test
    fun emptyState_rendersActionButton() =
        runComposeUiTest {
            var clicked = false
            setContent {
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
            onNodeWithText("Action").performClick()
            assertTrue(clicked)
        }

    @Test
    @Config(qualifiers = "ar-ldrtl")
    fun emptyState_rtlSupport() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustEmptyState(
                        title = "RTL Empty",
                        modifier = Modifier.testTag("emptyStateRtl"),
                    )
                }
            }
            onNodeWithTag("emptyStateRtl").assertIsDisplayed()
            onNodeWithText("RTL Empty").assertIsDisplayed()
        }
}
