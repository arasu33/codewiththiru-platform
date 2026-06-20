package com.codewiththiru.platform.feedback.provider

interface FeedbackDataRedactor {
    fun redact(content: String): String
}

class DefaultFeedbackDataRedactor : FeedbackDataRedactor {
    override fun redact(content: String): String {
        var redacted = content
        // Basic email redaction
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        redacted = redacted.replace(emailRegex, "[REDACTED_EMAIL]")
        
        // Basic phone number redaction (e.g., +1-234-567-8900)
        val phoneRegex = Regex("\\+?\\d{1,3}[-.\\s]?\\(?\\d{1,4}\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}")
        @Suppress("MagicNumber")
        redacted = redacted.replace(phoneRegex) { matchResult ->
            if (matchResult.value.length >= 7) "[REDACTED_PHONE]" else matchResult.value
        }
        
        return redacted
    }
}
