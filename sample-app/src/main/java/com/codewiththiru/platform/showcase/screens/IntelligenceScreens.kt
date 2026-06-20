package com.codewiththiru.platform.showcase.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AIScreen() {
    var query by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("AI Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        androidx.compose.material3.OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Ask the Platform AI") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { response = "AI: I am the autonomous foundation. How can I help?" }) {
            Text("Send Query")
        }
        if (response.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(response)
        }
    }
}

@Composable
fun DeveloperToolsScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Developer Tools", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Reset Caches */ }) { Text("Reset Remote Config Cache") }
        Button(onClick = { /* Export Logs */ }) { Text("Export Application Logs") }
        Button(onClick = { /* Clear DB */ }) { Text("Clear Local Database") }
    }
}

@Composable
fun HealthCenterScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Platform Health Center", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Analytics: PASS")
        Text("Ads: PASS")
        Text("Remote Config: PASS")
        Text("Security: PASS")
        Text("AI Native: PASS")
        Spacer(modifier = Modifier.height(16.dp))
        Text("PLATFORM SCORE: 100/100", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
    }
}
