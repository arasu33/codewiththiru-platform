package com.codewiththiru.ads.consent

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Implementation of [ConsentManager] using Google's User Messaging Platform (UMP) SDK.
 * Supports EEA, UK, and GDPR requirements.
 */
class UMPConsentManager(
    private val context: Context,
    private val activityProvider: () -> Activity? // Required for displaying forms
) : ConsentManager {

    private val consentInformation: ConsentInformation = UserMessagingPlatform.getConsentInformation(context)

    private val _consentState = MutableStateFlow(ConsentState.Unknown)
    override val consentState: StateFlow<ConsentState> = _consentState.asStateFlow()

    override suspend fun requestConsent() {
        val params = ConsentRequestParameters.Builder().build()

        val activity = activityProvider() ?: return // Can't request without activity

        suspendCancellableCoroutine { continuation ->
            consentInformation.requestConsentInfoUpdate(
                activity,
                params,
                {
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                        activity
                    ) { loadAndShowError ->
                        if (loadAndShowError != null) {
                            // Consent gathering failed
                            _consentState.value = ConsentState.Unknown
                        } else {
                            updateConsentState()
                        }
                        continuation.resume(Unit)
                    }
                },
                { requestConsentError ->
                    _consentState.value = ConsentState.Unknown
                    continuation.resume(Unit)
                }
            )
        }
    }

    override suspend fun refreshConsent() {
        requestConsent() // For UMP, refresh is essentially requesting an update again
    }

    override fun resetConsent() {
        consentInformation.reset()
        _consentState.value = ConsentState.Unknown
    }

    override suspend fun persistConsent(state: ConsentState) {
        // UMP automatically persists consent using SharedPreferences internally
        _consentState.value = state
    }

    private fun updateConsentState() {
        _consentState.value = when (consentInformation.consentStatus) {
            ConsentInformation.ConsentStatus.REQUIRED -> ConsentState.Required
            ConsentInformation.ConsentStatus.OBTAINED -> ConsentState.Granted
            ConsentInformation.ConsentStatus.NOT_REQUIRED -> ConsentState.NotRequired
            else -> ConsentState.Unknown
        }
    }
}
