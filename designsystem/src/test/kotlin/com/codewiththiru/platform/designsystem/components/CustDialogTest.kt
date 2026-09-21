package com.codewiththiru.platform.designsystem.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
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
class CustDialogTest {
    @Test
    fun dialog_rendersTitleMessageAndButtons() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustAlertDialog(
                        title = "Dialog Title",
                        message = "Dialog Message",
                        confirmButtonText = "Confirm",
                        dismissButtonText = "Dismiss",
                        onConfirm = {},
                        onDismiss = {},
                    )
                }
            }

            onNodeWithText("Dialog Title").assertIsDisplayed()
            onNodeWithText("Dialog Message").assertIsDisplayed()
            onNodeWithText("Confirm").assertIsDisplayed()
            onNodeWithText("Dismiss").assertIsDisplayed()
        }

    @Test
    fun dialog_triggersCallbacks() =
        runComposeUiTest {
            var confirmClicked = false
            var dismissClicked = false

            setContent {
                CustTheme {
                    CustAlertDialog(
                        title = "Title",
                        message = "Message",
                        confirmButtonText = "Confirm",
                        dismissButtonText = "Dismiss",
                        onConfirm = { confirmClicked = true },
                        onDismiss = { dismissClicked = true },
                    )
                }
            }

            onNodeWithText("Confirm").performClick()
            assertTrue(confirmClicked)

            onNodeWithText("Dismiss").performClick()
            assertTrue(dismissClicked)
        }

    @Test
    @Config(qualifiers = "ar-ldrtl")
    fun dialog_rtlSupport() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustAlertDialog(
                        title = "RTL Title",
                        message = "RTL Message",
                        confirmButtonText = "Confirm",
                        onConfirm = {},
                        onDismiss = {},
                        modifier = Modifier.testTag("dialog"),
                    )
                }
            }
            onNodeWithText("RTL Title").assertIsDisplayed()
        }

    @Test
    @Config(qualifiers = "sw600dp")
    fun dialog_tabletSupport() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustAlertDialog(
                        title = "Tablet",
                        message = "Message",
                        confirmButtonText = "Confirm",
                        onConfirm = {},
                        onDismiss = {},
                    )
                }
            }
            onNodeWithText("Tablet").assertIsDisplayed()
        }

    @Test
    @Config(qualifiers = "land")
    fun dialog_landscapeSupport() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustAlertDialog(
                        title = "Landscape",
                        message = "Message",
                        confirmButtonText = "Confirm",
                        onConfirm = {},
                        onDismiss = {},
                    )
                }
            }
            onNodeWithText("Landscape").assertIsDisplayed()
        }

    @Test
    fun dialog_largeFontSupport() =
        runComposeUiTest {
            setContent {
                val currentDensity = androidx.compose.ui.platform.LocalDensity.current
                val customDensity =
                    androidx.compose.ui.unit.Density(
                        density = currentDensity.density,
                        fontScale = 1.5f,
                    )
                androidx.compose.runtime.CompositionLocalProvider(
                    androidx.compose.ui.platform.LocalDensity provides customDensity,
                ) {
                    CustTheme {
                        CustAlertDialog(
                            title = "Large Font",
                            message = "Message",
                            confirmButtonText = "Confirm",
                            onConfirm = {},
                            onDismiss = {},
                        )
                    }
                }
            }
            onNodeWithText("Large Font").assertIsDisplayed()
        }
}
