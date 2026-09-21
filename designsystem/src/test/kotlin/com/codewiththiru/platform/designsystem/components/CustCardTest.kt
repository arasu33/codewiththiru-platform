package com.codewiththiru.platform.designsystem.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
@OptIn(ExperimentalTestApi::class)
class CustCardTest {
    @Test
    fun card_isDisplayed() =
        runComposeUiTest {
            setContent {
                CustTheme {
                    CustCard(modifier = Modifier.testTag("cardTag")) {
                        CustText("Content")
                    }
                }
            }

            onNodeWithTag("cardTag").assertIsDisplayed()
        }

    @Test
    fun clickableCard_respondsToClicks() =
        runComposeUiTest {
            var clicked = 0
            setContent {
                CustTheme {
                    CustCard(
                        onClick = { clicked++ },
                        modifier = Modifier.testTag("cardTag"),
                    ) {
                        CustText("Clickable Card")
                    }
                }
            }

            onNodeWithTag("cardTag").assertIsDisplayed().performClick()
            assertEquals(1, clicked)
        }
}
