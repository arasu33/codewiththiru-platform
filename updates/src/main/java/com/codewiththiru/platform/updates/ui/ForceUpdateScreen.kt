@file:Suppress("ktlint:standard:no-wildcard-imports", "ktlint:standard:max-line-length")

package com.codewiththiru.platform.updates.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ForceUpdateScreen(
    onUpdateClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    Surface(
        modifier =
            modifier
                .fillMaxSize()
                .semantics {
                    isContainer = true
                    liveRegion = LiveRegionMode.Polite
                },
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .semantics { isTraversalGroup = true },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Update Required",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .focusRequester(focusRequester)
                        .semantics { heading() },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "A critical update is required to continue using the app. Please update to the latest version to ensure security and stability.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onUpdateClicked,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 48.dp),
            ) {
                Text("Update Now")
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
