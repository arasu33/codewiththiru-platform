package com.codewiththiru.platform.showcase.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BillingScreen() {
    var subscribed by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Billing Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Subscription Status: ${if (subscribed) "ACTIVE" else "INACTIVE"}")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { subscribed = true }) {
            Text("Purchase Premium ($9.99/mo)")
        }
        Button(onClick = { subscribed = false }) {
            Text("Cancel Subscription")
        }
    }
}

@Composable
fun CouponsScreen() {
    var couponCode by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Coupons Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        androidx.compose.material3.OutlinedTextField(
            value = couponCode,
            onValueChange = { couponCode = it },
            label = { Text("Enter Code (e.g. FREE100)") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { 
            result = if (couponCode == "FREE100") "Coupon Valid: 100% OFF" else "Invalid Coupon"
        }) {
            Text("Validate Coupon")
        }
        if (result.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(result)
        }
    }
}

@Composable
fun GamificationScreen() {
    var xp by remember { mutableStateOf(0) }
    val level = (xp / 100) + 1

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Gamification Showcase", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Current Level: $level")
        Text("Current XP: $xp")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { xp += 25 }) {
            Text("Complete Task (+25 XP)")
        }
    }
}
