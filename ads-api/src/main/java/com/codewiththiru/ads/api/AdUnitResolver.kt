package com.codewiththiru.ads.api

import com.codewiththiru.ads.config.AdsConfig

/**
 * Interface responsible for resolving Ad Unit IDs based on the environment and ad type.
 */
interface AdUnitResolver {
    /**
     * Resolves the Ad Unit ID for the given [adType].
     *
     * @param environment The current Ads environment.
     * @param config The current remote config.
     * @param adType The requested ad type.
     * @return The resolved Ad Unit ID.
     */
    fun resolve(
        environment: AdsEnvironment,
        config: AdsConfig,
        adType: AdType,
    ): String
}
