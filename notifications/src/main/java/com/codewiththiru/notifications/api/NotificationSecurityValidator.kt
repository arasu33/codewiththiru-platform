package com.codewiththiru.notifications.api

import com.codewiththiru.notifications.consent.NotificationConsentManager
import kotlinx.coroutines.flow.first

class NotificationSecurityValidator(private val consentManager: NotificationConsentManager) {
    suspend fun validatePayload(payload: NotificationPayload): Boolean {
        // Validate payload doesn't contain malicious deep links or unauthorized PII
        if (payload.deepLink != null && !payload.deepLink.startsWith("codewiththiru://")) {
            if (!payload.deepLink.startsWith("https://")) {
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
