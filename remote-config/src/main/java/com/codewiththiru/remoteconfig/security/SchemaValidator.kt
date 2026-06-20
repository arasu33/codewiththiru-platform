package com.codewiththiru.remoteconfig.security

interface SchemaValidator {
    fun isValidSchema(payload: String): Boolean
}

class DefaultSchemaValidator : SchemaValidator {
    override fun isValidSchema(payload: String): Boolean {
        return payload.trim().startsWith("{") && payload.trim().endsWith("}")
    }
}
