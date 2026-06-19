package com.codewiththiru.platform.core.string

/**
 * Standard utility to handle HTML escaping, unescaping, and tag stripping.
 */
object HtmlUtils {
    private val HTML_TAG_REGEX = Regex("<[^>]*>")
    private val ENTITY_REGEX = Regex("&[a-zA-Z0-9#x]+;")

    private val ENTITY_MAP =
        mapOf(
            "&amp;" to "&",
            "&lt;" to "<",
            "&gt;" to ">",
            "&quot;" to "\"",
            "&apos;" to "'",
            "&#x27;" to "'",
            "&#x2F;" to "/",
            "&#39;" to "'",
        )

    /**
     * Removes all HTML tags from the given [text] string.
     *
     * @param text The input text to strip HTML tags from.
     * @return String without HTML tags.
     */
    fun stripHtml(text: String?): String {
        if (text.isNullOrEmpty()) return ""
        return text.replace(HTML_TAG_REGEX, "")
    }

    /**
     * Escapes critical HTML markup characters into their equivalent character entities.
     *
     * @param text The input text to escape.
     * @return HTML-escaped string.
     */
    @Suppress("MagicNumber")
    fun escapeHtml(text: String?): String {
        if (text.isNullOrEmpty()) return ""
        return buildString(text.length * 11 / 10) {
            for (char in text) {
                when (char) {
                    '&' -> append("&amp;")
                    '<' -> append("&lt;")
                    '>' -> append("&gt;")
                    '"' -> append("&quot;")
                    '\'' -> append("&#x27;")
                    '/' -> append("&#x2F;")
                    else -> append(char)
                }
            }
        }
    }

    /**
     * Unescapes HTML character entities back to standard characters.
     *
     * @param text The HTML-escaped text.
     * @return Decoded plain string.
     */
    fun unescapeHtml(text: String?): String {
        if (text.isNullOrEmpty()) return ""
        return ENTITY_REGEX.replace(text) { result ->
            ENTITY_MAP[result.value] ?: result.value
        }
    }
}
