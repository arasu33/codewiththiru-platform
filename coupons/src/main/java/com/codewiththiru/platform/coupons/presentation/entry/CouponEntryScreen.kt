package com.codewiththiru.platform.coupons.presentation.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.coupons.domain.model.CouponReward
import com.codewiththiru.platform.coupons.presentation.entry.components.CouponErrorDialog
import com.codewiththiru.platform.coupons.presentation.entry.components.CouponInputField
import com.codewiththiru.platform.coupons.presentation.entry.components.CouponSuccessDialog
import com.codewiththiru.platform.coupons.presentation.state.CouponEffect
import com.codewiththiru.platform.coupons.presentation.state.CouponIntent
import com.codewiththiru.platform.coupons.presentation.viewmodel.CouponViewModel

@Suppress("LongMethod", "FunctionNaming", "CyclomaticComplexMethod")
@Composable
fun CouponEntryScreen(
    viewModel: CouponViewModel,
    onNavigateBack: () -> Unit,
    onLaunchReward: (CouponReward) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var successReward by remember { mutableStateOf<CouponReward?>(null) }
    var errorReason by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is CouponEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
                is CouponEffect.NavigateBack -> onNavigateBack()
                is CouponEffect.ShowSuccessDialog -> successReward = effect.reward
                is CouponEffect.ShowErrorDialog -> errorReason = effect.reason
                is CouponEffect.LaunchRewardScreen -> onLaunchReward(effect.reward)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier,
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Redeem a Code",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            CouponInputField(
                code = state.inputCode,
                onCodeChange = { viewModel.processIntent(CouponIntent.UpdateInput(it)) },
                onSubmit = { viewModel.processIntent(CouponIntent.SubmitCoupon()) },
                isLoading = state.isLoading || state.isRedeeming,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.processIntent(CouponIntent.SubmitCoupon()) },
                enabled = state.inputCode.isNotBlank() && !state.isLoading && !state.isRedeeming,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .semantics { role = Role.Button },
            ) {
                if (state.isLoading || state.isRedeeming) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp,
                    )
                } else {
                    Text("Redeem")
                }
            }

            // Accessibility Live Region for state announcements
            if (state.isLoading || state.isRedeeming) {
                Text(
                    text = if (state.isRedeeming) "Redeeming coupon..." else "Validating coupon...",
                    modifier =
                        Modifier
                            .semantics { liveRegion = LiveRegionMode.Polite }
                            .padding(top = 16.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }

    successReward?.let { reward ->
        CouponSuccessDialog(
            reward = reward,
            onDismiss = {
                successReward = null
                viewModel.processIntent(CouponIntent.DismissSuccess)
            },
        )
    }

    errorReason?.let { reason ->
        CouponErrorDialog(
            reason = reason,
            onDismiss = {
                errorReason = null
                viewModel.processIntent(CouponIntent.DismissError)
            },
        )
    }
}
