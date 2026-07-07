package com.codewiththiru.platform.designsystem.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
class CustButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun button_isClickable_whenEnabled() {
        var clicked = 0
        composeTestRule.setContent {
            CustTheme {
                CustButton(
                    onClick = { clicked++ },
                    modifier = Modifier.testTag("buttonTag"),
                ) {
                    CustText("Click Me")
                }
            }
        }

        composeTestRule
            .onNodeWithTag("buttonTag")
            .assertIsDisplayed()
            .assertIsEnabled()
            .performClick()
        assertEquals(1, clicked)
    }

    @Test
    fun button_notClickable_whenDisabled() {
        var clicked = 0
        composeTestRule.setContent {
            CustTheme {
                CustButton(
                    onClick = { clicked++ },
                    enabled = false,
                    modifier = Modifier.testTag("buttonTag"),
                ) {
                    CustText("Click Me")
                }
            }
        }

        composeTestRule.onNodeWithTag("buttonTag").assertIsDisplayed().assertIsNotEnabled()
    }

    @Test
    fun button_notClickable_whenLoading() {
        var clicked = 0
        composeTestRule.setContent {
            CustTheme {
                CustButton(
                    onClick = { clicked++ },
                    loading = true,
                    modifier = Modifier.testTag("buttonTag"),
                ) {
                    CustText("Loading")
                }
            }
        }

        val button = composeTestRule.onNodeWithTag("buttonTag")
        button.assertIsDisplayed().assertIsEnabled().performClick()

        // Clicks should be intercepted during loading
        assertEquals(0, clicked)
    }
}
