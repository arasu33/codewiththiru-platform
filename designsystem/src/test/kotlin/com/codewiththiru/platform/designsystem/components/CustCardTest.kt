package com.codewiththiru.platform.designsystem.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
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
class CustCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun card_isDisplayed() {
        composeTestRule.setContent {
            CustTheme {
                CustCard(modifier = Modifier.testTag("cardTag")) {
                    CustText("Content")
                }
            }
        }

        composeTestRule.onNodeWithTag("cardTag").assertIsDisplayed()
    }

    @Test
    fun clickableCard_respondsToClicks() {
        var clicked = 0
        composeTestRule.setContent {
            CustTheme {
                CustCard(
                    onClick = { clicked++ },
                    modifier = Modifier.testTag("cardTag")
                ) {
                    CustText("Clickable Card")
                }
            }
        }

        composeTestRule.onNodeWithTag("cardTag").assertIsDisplayed().performClick()
        assertEquals(1, clicked)
    }
}
