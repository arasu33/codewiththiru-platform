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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.components.CustButton
import com.codewiththiru.platform.designsystem.components.CustText

/**
 * A rigorous error block mapping fatal or non-fatal boundaries securely to the UI layer.
 *
 * Integrates explicitly structured TalkBack logic that merges the error code, title,
 * and descriptive message into a cohesive sequence.
 *
 * @param title Primary categorization string bounding the fault.
 * @param modifier Structure configuration traversing layouts.
 * @param message Verbose localized sequence explaining the fault explicitly.
 * @param errorCode Secondary identifier for internal telemetry.
 * @param onRetry Optional closure executing retry workflows visually bound to a button.
 * @param retryText Overridable textual constraint for the action layer.
 */
@Composable
fun CustErrorState(
    title: String,
    modifier: Modifier = Modifier,
    message: String? = null,
    errorCode: String? = null,
    icon: @Composable (() -> Unit)? = null,
    onRetry: (() -> Unit)? = null,
    retryText: String = "Retry",
) {
    val errorSemanticText =
        buildString {
            append(title)
            message?.let { append(". $it") }
            errorCode?.let { append(". Error code: $it") }
        }

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(24.dp)
                .semantics(mergeDescendants = true) {
                    contentDescription = errorSemanticText
                },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (icon != null) {
            icon()
            Spacer(modifier = Modifier.height(CustErrorStateDefaults.iconSpacing))
        }

        CustText(
            text = title,
            style = CustErrorStateDefaults.titleStyle,
            color = CustErrorStateDefaults.titleColor,
            textAlign = TextAlign.Center,
        )

        if (message != null) {
            Spacer(modifier = Modifier.height(CustErrorStateDefaults.titleSpacing))
            CustText(
                text = message,
                style = CustErrorStateDefaults.messageStyle,
                color = CustErrorStateDefaults.messageColor,
                textAlign = TextAlign.Center,
            )
        }

        if (errorCode != null) {
            Spacer(modifier = Modifier.height(CustErrorStateDefaults.errorCodeSpacing))
            CustText(
                text = "Code: $errorCode",
                style = CustErrorStateDefaults.errorCodeStyle,
                color = CustErrorStateDefaults.errorCodeColor,
                textAlign = TextAlign.Center,
            )
        }

        if (onRetry != null) {
            Spacer(modifier = Modifier.height(CustErrorStateDefaults.retrySpacing))
            CustButton(onClick = onRetry) {
                CustText(text = retryText)
            }
        }
    }
}
