package com.codewiththiru.platform.core.string

import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Standard utility for encoding and decoding URLs using standard UTF-8 charset.
 */
object UrlEncoder {
    /**
     * Percent-encodes a string value for URL parameters.
     *
     * @param url The string value to encode.
     * @return Percent-encoded string.
     */
    fun encode(url: String): String {
        return URLEncoder.encode(url, StandardCharsets.UTF_8)
    }

    /**
     * Decodes a percent-encoded URL string.
     *
     * @param encodedUrl The encoded string parameter.
     * @return Decoded plain string.
     */
    fun decode(encodedUrl: String): String {
        return URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8)
    }
}
