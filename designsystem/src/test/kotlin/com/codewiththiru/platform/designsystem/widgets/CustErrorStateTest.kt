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
class CustErrorStateTest {
    @Test
    fun errorState_rendersTitleAndMessage() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustErrorState(
                        title = "Error",
                        message = "Something went wrong",
                    )
                }
            }
            onNodeWithText("Error").assertIsDisplayed()
            onNodeWithText("Something went wrong").assertIsDisplayed()
        }

    @Test
    fun errorState_rendersErrorCode() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustErrorState(
                        title = "Error",
                        errorCode = "404",
                    )
                }
            }
            onNodeWithText("Code: 404").assertIsDisplayed()
        }

    @Test
    fun errorState_triggersRetry() =
        runComposeUiTest {
            var retried = false
            setContent {
                CustTheme {
                    CustErrorState(
                        title = "Error",
                        onRetry = { retried = true },
                        retryText = "Try Again",
                    )
                }
            }
            onNodeWithText("Try Again").performClick()
            assertTrue(retried)
        }

    @Test
    fun errorState_accessibilitySemantics() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustErrorState(
                        title = "Failed",
                        message = "Network error",
                        errorCode = "101",
                    )
                }
            }
            // It merges into "Failed. Network error. Error code: 101"
            onNodeWithContentDescription("Failed. Network error. Error code: 101").assertIsDisplayed()
        }

    @Test
    @Config(qualifiers = "sw600dp")
    fun errorState_tabletSupport() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustErrorState(
                        title = "Tablet Error",
                        modifier = Modifier.testTag("errorStateTablet"),
                    )
                }
            }
            onNodeWithTag("errorStateTablet").assertIsDisplayed()
        }
}
