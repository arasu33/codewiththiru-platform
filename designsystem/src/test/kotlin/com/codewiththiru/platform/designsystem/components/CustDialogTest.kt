package com.codewiththiru.platform.designsystem.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
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
class CustDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dialog_rendersTitleMessageAndButtons() {
        composeTestRule.setContent {
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

        composeTestRule.onNodeWithText("Dialog Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dialog Message").assertIsDisplayed()
        composeTestRule.onNodeWithText("Confirm").assertIsDisplayed()
        composeTestRule.onNodeWithText("Dismiss").assertIsDisplayed()
    }

    @Test
    fun dialog_triggersCallbacks() {
        var confirmClicked = false
        var dismissClicked = false

        composeTestRule.setContent {
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

        composeTestRule.onNodeWithText("Confirm").performClick()
        assertTrue(confirmClicked)

        composeTestRule.onNodeWithText("Dismiss").performClick()
        assertTrue(dismissClicked)
    }

    @Test
    @Config(qualifiers = "ar-ldrtl")
    fun dialog_rtlSupport() {
        composeTestRule.setContent {
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
        composeTestRule.onNodeWithText("RTL Title").assertIsDisplayed()
    }

    @Test
    @Config(qualifiers = "sw600dp")
    fun dialog_tabletSupport() {
        composeTestRule.setContent {
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
        composeTestRule.onNodeWithText("Tablet").assertIsDisplayed()
    }

    @Test
    @Config(qualifiers = "land")
    fun dialog_landscapeSupport() {
        composeTestRule.setContent {
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
        composeTestRule.onNodeWithText("Landscape").assertIsDisplayed()
    }

    @Test
    fun dialog_largeFontSupport() {
        composeTestRule.setContent {
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
        composeTestRule.onNodeWithText("Large Font").assertIsDisplayed()
    }
}
