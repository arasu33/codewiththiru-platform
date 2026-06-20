package com.codewiththiru.platform.showcase.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SecurityScreen() {
    var rootStatus by remember { mutableStateOf("Checking...") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Security Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Root Status: $rootStatus")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { rootStatus = "Clean (No Root Detected)" }) {
            Text("Run Security Scan")
        }
    }
}

@Composable
fun IdentityScreen() {
    var loggedIn by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Identity Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Status: ${if (loggedIn) "Authenticated (User ID: 1001)" else "Anonymous"}")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { loggedIn = !loggedIn }) {
            Text(if (loggedIn) "Log Out" else "Log In")
        }
    }
}

@Composable
fun ObservabilityScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Observability Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("No recent crashes detected.")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { throw RuntimeException("Test Crash from Observability Screen") }) {
            Text("Force Test Crash")
        }
    }
}

@Composable
fun NotificationsScreen() {
    var notificationSent by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Notifications Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { notificationSent = true }) {
            Text("Trigger Local Notification")
        }
        if (notificationSent) {
            Text("Notification triggered successfully!")
        }
    }
}
