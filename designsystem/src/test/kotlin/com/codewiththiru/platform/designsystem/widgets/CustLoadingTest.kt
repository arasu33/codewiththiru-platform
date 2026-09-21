package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
@OptIn(ExperimentalTestApi::class)
class CustLoadingTest {
    @Test
    fun loading_rendersCircularWithoutMessage() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustLoading(
                        type = LoadingType.Circular,
                        modifier = Modifier.testTag("circularLoading"),
                    )
                }
            }
            onNodeWithTag("circularLoading").assertIsDisplayed()
        }

    @Test
    fun loading_rendersCircularWithMessage() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustLoading(
                        type = LoadingType.Circular,
                        message = "Please wait",
                        modifier = Modifier.testTag("circularLoadingMessage"),
                    )
                }
            }
            onNodeWithTag("circularLoadingMessage").assertIsDisplayed()
            onNodeWithText("Please wait").assertIsDisplayed()
        }

    @Test
    fun loading_rendersLinearWithMessage() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustLoading(
                        type = LoadingType.Linear,
                        message = "Syncing...",
                        modifier = Modifier.testTag("linearLoadingMessage"),
                    )
                }
            }
            onNodeWithTag("linearLoadingMessage").assertIsDisplayed()
            onNodeWithText("Syncing...").assertIsDisplayed()
        }
}
