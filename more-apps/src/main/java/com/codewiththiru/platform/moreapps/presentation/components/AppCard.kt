package com.codewiththiru.platform.moreapps.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.moreapps.domain.model.InstallStatus
import com.codewiththiru.platform.moreapps.domain.model.MoreAppModel
import com.codewiththiru.platform.moreapps.presentation.integration.AppActionType
import com.codewiththiru.platform.moreapps.presentation.integration.MoreAppsImageProvider

@Composable
fun AppCard(
    app: MoreAppModel,
    imageProvider: MoreAppsImageProvider,
    onActionClick: (AppActionType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val stateDescription =
        when (app.installStatus) {
            InstallStatus.Installed -> "Installed"
            InstallStatus.UpdateAvailable -> "Update Available"
            InstallStatus.NotInstalled -> "Not Installed"
            InstallStatus.Unknown -> ""
        }

    val ratingText = if (app.rating != null) "Rating ${app.rating}" else ""
    val fullDescription =
        listOf(app.title, ratingText, stateDescription)
            .filter { it.isNotEmpty() }
            .joinToString(", ")

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onActionClick(AppActionType.View) }
                .semantics(mergeDescendants = true) {
                    contentDescription = fullDescription
                },
    ) {
        Row(
            modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            imageProvider.LoadImage(
                url = app.iconUrl,
                contentDescription = null, // Handled by merged semantics on parent
                modifier = Modifier.size(64.dp),
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = app.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                if (app.rating != null) {
                    Text(
                        text = "★ ${app.rating}",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Text(
                    text = app.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = {
                        val action =
                            when (app.installStatus) {
                                InstallStatus.Installed -> AppActionType.Open
                                else -> AppActionType.Install
                            }
                        onActionClick(action)
                    },
                ) {
                    Text(
                        text =
                            when (app.installStatus) {
                                InstallStatus.Installed -> "Open"
                                InstallStatus.UpdateAvailable -> "Update"
                                else -> "Install"
                            },
                    )
                }

                Text(
                    text = "Share",
                    style = MaterialTheme.typography.labelSmall,
                    modifier =
                        Modifier
                            .clickable { onActionClick(AppActionType.Share) }
                            .padding(4.dp),
                )
            }
        }
    }
}
