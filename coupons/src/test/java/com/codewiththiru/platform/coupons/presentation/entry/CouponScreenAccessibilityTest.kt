package com.codewiththiru.platform.coupons.presentation.entry

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class CouponScreenAccessibilityTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testInputFieldHasContentDescription() {
        // We'd pass a stub ViewModel here. For architectural verification,
        // asserting the semantic properties of the standalone components is enough.

        composeTestRule.setContent {
            com.codewiththiru.platform.coupons.presentation.entry.components.CouponInputField(
                code = "",
                onCodeChange = {},
                onSubmit = {},
                isLoading = false,
            )
        }

        composeTestRule.onNodeWithContentDescription("Enter promotional code").assertExists()
    }
}
