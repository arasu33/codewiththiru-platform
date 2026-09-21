package com.codewiththiru.platform.coupons.presentation.entry

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.runComposeUiTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
@OptIn(ExperimentalTestApi::class)
class CouponScreenAccessibilityTest {
    @Test
    fun testInputFieldHasContentDescription() =
        runComposeUiTest {
            // We'd pass a stub ViewModel here. For architectural verification,
            // asserting the semantic properties of the standalone components is enough.

            setContent {
                com.codewiththiru.platform.coupons.presentation.entry.components.CouponInputField(
                    code = "",
                    onCodeChange = {},
                    onSubmit = {},
                    isLoading = false,
                )
            }

            onNodeWithContentDescription("Enter promotional code").assertExists()
        }
}
