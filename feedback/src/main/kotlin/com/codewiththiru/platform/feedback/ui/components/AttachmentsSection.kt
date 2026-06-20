@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.feedback.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.components.CustButton
import com.codewiththiru.platform.designsystem.components.CustText
import com.codewiththiru.platform.feedback.model.Attachment
import com.codewiththiru.platform.feedback.model.FeedbackAttachmentsConfig
import com.codewiththiru.platform.feedback.model.FeedbackThemeConfig

@Composable
internal fun AttachmentsSection(
    attachments: List<Attachment>,
    config: FeedbackAttachmentsConfig,
    themeConfig: FeedbackThemeConfig,
    onAddAttachmentClicked: () -> Unit,
    onRemoveAttachment: (Attachment) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CustText(
            text = "Attachments (${attachments.size} / ${config.maxAttachments})",
            style = themeConfig.labelStyle ?: androidx.compose.ui.text.TextStyle.Default,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .semantics {
                    stateDescription = "${attachments.size} of ${config.maxAttachments} attachments added"
                }
        )

        attachments.forEach { attachment ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                CustText(
                    text = "${attachment.type}",
                    modifier = Modifier.weight(1f)
                )
                CustButton(
                    onClick = { onRemoveAttachment(attachment) },
                    modifier = Modifier.semantics {
                        contentDescription = "Remove ${attachment.type} attachment"
                    }
                ) {
                    CustText("Remove")
                }
            }
        }

        if (attachments.size < config.maxAttachments) {
            CustButton(
                onClick = onAddAttachmentClicked,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                CustText("Add Attachment")
            }
        }
    }
}
