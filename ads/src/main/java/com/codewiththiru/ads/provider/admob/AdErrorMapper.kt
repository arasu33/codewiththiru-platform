package com.codewiththiru.ads.provider.admob

import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.LoadAdError

/**
 * Platform-agnostic exception for ad-related errors.
 */
class AdsException(
    val errorCode: Int,
    message: String,
    val domain: String = "Unknown",
    cause: Throwable? = null
) : Exception(message, cause)

/**
 * Maps AdMob specific errors to platform-agnostic exceptions.
 */
object AdErrorMapper {

    fun mapLoadError(error: LoadAdError): AdsException {
        val cause = error.cause?.let { Exception(it.message) }
        return AdsException(
            errorCode = error.code,
            message = error.message,
            domain = error.domain,
            cause = cause
        )
    }

    fun mapShowError(error: AdError): AdsException {
        val cause = error.cause?.let { Exception(it.message) }
        return AdsException(
            errorCode = error.code,
            message = error.message,
            domain = error.domain,
            cause = cause
        )
    }
}
