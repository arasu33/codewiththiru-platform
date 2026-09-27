@file:Suppress("FunctionNaming", "LongParameterList")

package com.codewiththiru.consent.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Standard privacy consent banner suitable for displaying at the bottom of the screen.
 * Fully customizable title, description, and button action labels.
 */
@Composable
fun ConsentBanner(
    onAcceptAll: () -> Unit,
    onDeclineOptional: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = "Your Privacy Choices",
    description: String =
        "We use essential data to provide core services, and optional analytics to improve your experience.",
    acceptButtonText: String = "Accept All",
    declineButtonText: String = "Decline Optional",
) {
    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(16.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                OutlinedButton(
                    onClick = onDeclineOptional,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(declineButtonText)
                }
                Button(
                    onClick = onAcceptAll,
                    modifier = Modifier.weight(1f),
                ) {
                    Text(acceptButtonText)
                }
            }
        }
    }
}

/**
 * Backward-compatible overload matching legacy parameter ordering.
 */
@Composable
fun ConsentBanner(
    title: String,
    description: String,
    onAcceptAll: () -> Unit,
    onDeclineOptional: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConsentBanner(
        onAcceptAll = onAcceptAll,
        onDeclineOptional = onDeclineOptional,
        modifier = modifier,
        title = title,
        description = description,
    )
}
