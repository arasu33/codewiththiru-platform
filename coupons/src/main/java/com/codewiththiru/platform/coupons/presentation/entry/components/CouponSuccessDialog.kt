package com.codewiththiru.platform.coupons.presentation.entry.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.codewiththiru.platform.coupons.domain.model.CouponReward

@Suppress("FunctionNaming")
@Composable
fun CouponSuccessDialog(
    reward: CouponReward,
    onDismiss: () -> Unit,
) {
    // In a real app, you'd map CouponReward to user-friendly text
    val rewardText =
        when (reward) {
            is CouponReward.CoinsReward -> "You received ${reward.amount} coins!"
            is CouponReward.DiscountReward -> "Discount applied successfully!"
            is CouponReward.PremiumUnlock -> "Premium unlocked for ${reward.durationDays} days!"
            is CouponReward.TrialExtension -> "Trial extended by ${reward.extraDays} days!"
        }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Success!") },
        text = { Text(rewardText) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Awesome")
            }
        },
    )
}
