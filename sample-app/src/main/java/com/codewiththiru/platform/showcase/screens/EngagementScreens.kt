package com.codewiththiru.platform.showcase.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FeedbackScreen() {
    var feedbackText by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Feedback Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        androidx.compose.material3.OutlinedTextField(
            value = feedbackText,
            onValueChange = { feedbackText = it },
            label = { Text("Your Feedback") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { submitted = true }) {
            Text("Submit Feedback")
        }
        if (submitted) Text("Feedback Submitted Successfully!")
    }
}

@Composable
fun RatingScreen() {
    var rating by remember { mutableStateOf(0) }
    
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Rating Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Text("Current Rating: $rating Stars")
        Row {
            (1..5).forEach { star ->
                Button(onClick = { rating = star }) {
                    Text("$star")
                }
            }
        }
    }
}

@Composable
fun UpdatesScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Updates Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Button(onClick = { /* Check Updates */ }) {
            Text("Check for Flexible Update")
        }
    }
}

@Composable
fun MoreAppsScreen() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("More Apps Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Text("1. Sudoku Nexus\n2. Chess Academy\n3. Learn Kotlin")
    }
}
