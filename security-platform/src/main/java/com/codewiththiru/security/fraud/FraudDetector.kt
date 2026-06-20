package com.codewiththiru.security.fraud

interface FraudDetector {
    fun analyzeSignals(signals: List<FraudSignal>): RiskScore
}
