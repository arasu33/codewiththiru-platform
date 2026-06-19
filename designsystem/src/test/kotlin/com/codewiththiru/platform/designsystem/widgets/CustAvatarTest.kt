package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.codewiththiru.platform.designsystem.theme.CustTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], instrumentedPackages = ["androidx.loader.content"])
class CustAvatarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun avatar_rendersInitials() {
        composeTestRule.setContent {
            CustTheme {
                CustAvatar(
                    initials = "John Doe",
                    contentDescription = "Profile Avatar"
                )
            }
        }
        // It should take the first two letters and uppercase them
        composeTestRule.onNodeWithText("JO").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Profile Avatar").assertIsDisplayed()
    }

    @Test
    fun avatar_rendersPlaceholder() {
        composeTestRule.setContent {
            CustTheme {
                CustAvatar(
                    modifier = Modifier.testTag("placeholderAvatar"),
                    contentDescription = "Empty Avatar"
                )
            }
        }
        composeTestRule.onNodeWithTag("placeholderAvatar").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Empty Avatar").assertIsDisplayed()
    }

    @Test
    @Config(qualifiers = "sw600dp")
    fun avatar_tabletSupport() {
        composeTestRule.setContent {
            CustTheme {
                CustAvatar(
                    initials = "TB",
                    size = AvatarSize.Large,
                    modifier = Modifier.testTag("tabletAvatar")
                )
            }
        }
        composeTestRule.onNodeWithTag("tabletAvatar").assertIsDisplayed()
        composeTestRule.onNodeWithText("TB").assertIsDisplayed()
    }
}
