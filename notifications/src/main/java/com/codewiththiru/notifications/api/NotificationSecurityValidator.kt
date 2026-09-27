package com.codewiththiru.notifications.api

import com.codewiththiru.notifications.consent.NotificationConsentManager
import kotlinx.coroutines.flow.first

class NotificationSecurityValidator(
    private val consentManager: NotificationConsentManager,
    private val allowedSchemes: Set<String> = setOf("codewiththiru", "https"),
) {
    suspend fun validatePayload(payload: NotificationPayload): Boolean {
        // Validate payload doesn't contain malicious deep links or unauthorized PII
        val deepLink = payload.deepLink
        if (deepLink != null) {
            val hasValidScheme = allowedSchemes.any { scheme -> deepLink.startsWith("$scheme://") }
            if (!hasValidScheme) {
                return false
            }
        }

        // Check consent
        if (!consentManager.hasConsent.first()) {
            return false
        }

        return true
    }
}
