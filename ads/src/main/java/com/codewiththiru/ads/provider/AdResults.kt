package com.codewiththiru.ads.provider

/**
 * Result of an ad load operation.
 */
sealed class AdLoadResult {
    data class Success(val adType: String, val loadTimeMs: Long) : AdLoadResult()
    data class Failure(val adType: String, val error: Throwable) : AdLoadResult()
}

/**
 * Result of an ad show operation.
 */
sealed class AdShowResult {
    data class Success(val adType: String) : AdShowResult()
    data class Failure(val adType: String, val error: Throwable) : AdShowResult()
    data class Dismissed(val adType: String) : AdShowResult()
}
