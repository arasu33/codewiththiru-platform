package com.codewiththiru.platform.designsystem.components

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.unit.LayoutDirection
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
@OptIn(ExperimentalTestApi::class)
class CustTextFieldTest {
    @Test
    fun textField_focusState() =
        runComposeUiTest {
            val focusRequester = FocusRequester()
            setContent {
                CustTheme {
                    CustTextField(
                        value = "",
                        onValueChange = {},
                        modifier =
                            Modifier
                                .testTag("textField")
                                .focusRequester(focusRequester),
                    )
                }
            }

            val node = onNodeWithTag("textField")
            node.assertIsNotFocused()

            runOnIdle {
                focusRequester.requestFocus()
            }

            node.assertIsFocused()
        }

    @Test
    fun textField_errorStateDisplaysErrorText() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustTextField(
                        value = "",
                        onValueChange = {},
                        errorText = "Invalid input",
                    )
                }
            }

            onNodeWithText("Invalid input").assertIsDisplayed()
        }

    @Test
    fun passwordField_togglesVisibility() =
        runComposeUiTest {
            var value by mutableStateOf("secret123")
            setContent {
                CustTheme {
                    CustPasswordTextField(
                        value = value,
                        onValueChange = { value = it },
                        modifier = Modifier.testTag("passwordField"),
                    )
                }
            }

            // Initially obscured (visual transformation obscures it, but we can check the icon semantic)
            onNodeWithContentDescription("Show password").assertIsDisplayed()

            // Toggle
            onNodeWithContentDescription("Show password").performClick()

            // Should now show hide password
            onNodeWithContentDescription("Hide password").assertIsDisplayed()
        }

    @Test
    fun textField_rtlSupport() =
        runComposeUiTest {
            setContent {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    CustTheme {
                        CustTextField(
                            value = "RTL Test",
                            onValueChange = {},
                            modifier = Modifier.testTag("rtlTextField"),
                        )
                    }
                }
            }

            onNodeWithTag("rtlTextField").assertIsDisplayed()
            onNodeWithText("RTL Test").assertIsDisplayed()
        }

    @Test
    fun textField_maxLengthRestrictsInput() =
        runComposeUiTest {
            var value by mutableStateOf("")
            setContent {
                CustTheme {
                    CustTextField(
                        value = value,
                        onValueChange = { value = it },
                        modifier = Modifier.testTag("textField"),
                        maxLength = 5,
                    )
                }
            }

            val node = onNodeWithTag("textField")

            // Type exactly max length
            node.performTextInput("12345")
            runOnIdle {
                assertEquals("12345", value)
            }

            // Try to add one more character (should be ignored)
            node.performTextInput("6")
            runOnIdle {
                assertEquals("12345", value)
            }
        }
}
