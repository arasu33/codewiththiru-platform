@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.components.CustText

/**
 * A standardized presentation component mapping null or empty data payloads to user-friendly graphics.
 *
 * @param title Core semantic header explicitly communicating the missing state natively.
 * @param modifier Applied structure properties bridging parent elements.
 * @param message Clarifying textual guidance.
 * @param illustration Optional graphic mapped above the text nodes.
 * @param actionButton Optional call-to-action block recovering workflows.
 */
@Composable
fun CustEmptyState(
    title: String,
    modifier: Modifier = Modifier,
    message: String? = null,
    illustration: @Composable (() -> Unit)? = null,
    actionButton: @Composable (() -> Unit)? = null,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(24.dp)
                .semantics(mergeDescendants = true) {},
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (illustration != null) {
            illustration()
            Spacer(modifier = Modifier.height(CustEmptyStateDefaults.illustrationSpacing))
        }

        CustText(
            text = title,
            style = CustEmptyStateDefaults.titleStyle,
            color = CustEmptyStateDefaults.titleColor,
            textAlign = TextAlign.Center,
        )

        if (message != null) {
            Spacer(modifier = Modifier.height(CustEmptyStateDefaults.titleSpacing))
            CustText(
                text = message,
                style = CustEmptyStateDefaults.messageStyle,
                color = CustEmptyStateDefaults.messageColor,
                textAlign = TextAlign.Center,
            )
        }

        if (actionButton != null) {
            Spacer(modifier = Modifier.height(CustEmptyStateDefaults.actionSpacing))
            actionButton()
        }
    }
}
