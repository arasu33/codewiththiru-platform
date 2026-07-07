package com.codewiththiru.platform.identity.security

data class TrustedDevice(
    val deviceId: String,
    val deviceName: String,
    val osVersion: String,
    val addedAt: Long,
    val lastSeenAt: Long,
)
