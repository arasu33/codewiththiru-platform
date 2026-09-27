package com.codewiththiru.notifications.security

import com.codewiththiru.notifications.api.NotificationPayload

object NotificationSecurityValidator {
    @Volatile
    var allowedSchemes: Set<String> = setOf("codewiththiru", "https")

    fun isPayloadValid(payload: NotificationPayload): Boolean {
        if (payload.id.isBlank()) return false
        if (payload.title.isBlank() && payload.body.isBlank()) return false

        // Deep link validation
        payload.deepLink?.let { link ->
            val hasValidScheme = allowedSchemes.any { scheme -> link.startsWith("$scheme://") }
            if (!hasValidScheme) {
                return false
            }
            if (link.contains("javascript:")) return false
        }

        return true
    }

    fun detectFraud(payload: NotificationPayload): Boolean {
        // Advanced signature and spam detection logic could go here.
        // For now, check for explicitly malicious injected scripts in the body
        if (payload.body.contains("<script>") || payload.title.contains("eval(")) {
            return true // Is fraudulent
        }
        return false
    }
}
