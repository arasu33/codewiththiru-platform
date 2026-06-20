package com.codewiththiru.security.integrity

class SecurityRiskEvaluator(
    private val rootDetector: RootDetector,
    private val emulatorDetector: EmulatorDetector,
    private val tamperDetector: TamperDetector
) {
    fun evaluateRisk(): Double {
        var risk = 0.0
        if (rootDetector.isDeviceRooted()) risk += 0.5
        if (emulatorDetector.isEmulator()) risk += 0.3
        if (tamperDetector.isAppTampered()) risk += 0.8
        return risk.coerceAtMost(1.0)
    }
}
