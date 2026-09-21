package com.codewiththiru.platform.designsystem.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.text.AnnotatedString
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
@OptIn(ExperimentalTestApi::class)
class CustTextTest {
    @Test
    fun stringText_isDisplayed() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustText(
                        text = "Hello String",
                        modifier = Modifier.testTag("textTag"),
                    )
                }
            }

            onNodeWithTag("textTag").assertIsDisplayed()
            onNodeWithText("Hello String").assertIsDisplayed()
        }

    @Test
    fun annotatedStringText_isDisplayed() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustText(
                        annotatedString = AnnotatedString("Hello Annotated"),
                        modifier = Modifier.testTag("textTag"),
                    )
                }
            }

            onNodeWithTag("textTag").assertIsDisplayed()
            onNodeWithText("Hello Annotated").assertIsDisplayed()
        }
}
