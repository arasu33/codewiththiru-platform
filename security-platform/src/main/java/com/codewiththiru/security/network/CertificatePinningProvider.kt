package com.codewiththiru.security.network

interface CertificatePinningProvider {
    fun addPin(hostname: String, pin: String)
    fun checkPins()
}
