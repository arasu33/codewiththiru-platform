package com.codewiththiru.platform.designsystem.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.text.AnnotatedString
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
class CustTextTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun stringText_isDisplayed() {
        composeTestRule.setContent {
            CustTheme {
                CustText(
                    text = "Hello String",
                    modifier = Modifier.testTag("textTag"),
                )
            }
        }

        composeTestRule.onNodeWithTag("textTag").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hello String").assertIsDisplayed()
    }

    @Test
    fun annotatedStringText_isDisplayed() {
        composeTestRule.setContent {
            CustTheme {
                CustText(
                    annotatedString = AnnotatedString("Hello Annotated"),
                    modifier = Modifier.testTag("textTag"),
                )
            }
        }

        composeTestRule.onNodeWithTag("textTag").assertIsDisplayed()
        composeTestRule.onNodeWithText("Hello Annotated").assertIsDisplayed()
    }
}
