package com.codewiththiru.ads.api

/**
 * Defines the environment in which the Ads SDK is operating.
 *
 * Rules:
 * - Debug, Internal, QA, Beta MUST ALWAYS USE TEST ADS.
 * - Production MUST ALWAYS USE REAL ADS.
 */
enum class AdsEnvironment {
    Debug,
    Internal,
    QA,
    Beta,
    Production;

    val isTestEnvironment: Boolean
        get() = this != Production
}
