package com.codewiththiru.notifications.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InboxScreen(modifier: Modifier = Modifier) {
    // Phase 19.9 Inbox Screen Scaffold
    Column(modifier = modifier.fillMaxSize()) {
        Text("Notification Inbox")
        LazyColumn {
            // Items mapped from NotificationInbox
        }
    }
}
