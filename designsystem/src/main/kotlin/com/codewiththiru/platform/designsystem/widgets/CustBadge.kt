@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.components.CustText

/**
 * A notification badge wrapper that manages visual thresholds for dense integer states.
 * 
 * Generates an automatic `"99+"` textual truncation bounding if the active count exceeds [maxCount].
 * Crucially, TalkBack strings bypass this truncation via explicit semantic merging (e.g. `"105 notifications"`).
 *
 * @param count Current notification payload count.
 * @param modifier Applied structure properties bridging parent elements.
 * @param maxCount Threshold limit before the badge visualizes string overflows.
 * @param content Target structure receiving the badge container mappings.
 */
@Composable
fun CustBadge(
    count: Int,
    modifier: Modifier = Modifier,
    maxCount: Int = 99,
    content: @Composable () -> Unit
) {
    val displayCount = if (count > maxCount) "$maxCount+" else count.toString()
    val description = "$count notifications"

    BadgedBox(
        modifier = modifier.semantics {
            contentDescription = description
        },
        badge = {
            if (count > 0) {
                Badge(
                    containerColor = CustBadgeDefaults.containerColor,
                    contentColor = CustBadgeDefaults.contentColor
                ) {
                    CustText(
                        text = displayCount,
                        style = CustBadgeDefaults.textStyle
                    )
                }
            }
        }
    ) {
        content()
    }
}
