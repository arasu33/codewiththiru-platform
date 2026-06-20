package com.codewiththiru.remoteconfig.security

class ConfigThreatAnalyzer(
    private val schemaValidator: SchemaValidator,
    private val signatureValidator: SignatureValidator,
    private val integrityValidator: ConfigIntegrityValidator
) {
    fun analyzeThreatLevel(
        payload: String, 
        signature: String?, 
        expectedHash: String?
    ): ConfigRiskLevel {
        if (!schemaValidator.isValidSchema(payload)) return ConfigRiskLevel.CRITICAL
        
        if (signature != null && !signatureValidator.isValidSignature(payload, signature)) {
            return ConfigRiskLevel.HIGH
        }
        
        if (expectedHash != null && !integrityValidator.isIntegrityValid(payload, expectedHash)) {
            return ConfigRiskLevel.HIGH
        }
        
        if (payload.contains("eval(") || payload.contains("javascript:")) {
            return ConfigRiskLevel.CRITICAL
        }
        
        return ConfigRiskLevel.LOW
    }
}
