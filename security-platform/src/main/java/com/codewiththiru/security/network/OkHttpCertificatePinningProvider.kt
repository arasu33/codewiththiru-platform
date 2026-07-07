package com.codewiththiru.security.network

import okhttp3.CertificatePinner
import okhttp3.OkHttpClient

class OkHttpCertificatePinningProvider : CertificatePinningProvider {
    
    private val pins = mutableMapOf<String, MutableList<String>>()

    override fun addPin(hostname: String, pin: String) {
        val hostnamePins = pins.getOrPut(hostname) { mutableListOf() }
        if (!hostnamePins.contains(pin)) {
            hostnamePins.add(pin)
        }
    }

    override fun checkPins() {
        // Implementation for checking pins manually if needed, usually handled by OkHttp interceptor
    }

    /**
     * Builds and attaches the CertificatePinner to the provided OkHttpClient.Builder
     */
    fun attachToClient(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        val pinnerBuilder = CertificatePinner.Builder()
        
        for ((hostname, hostPins) in pins) {
            for (pin in hostPins) {
                pinnerBuilder.add(hostname, pin)
            }
        }
        
        return builder.certificatePinner(pinnerBuilder.build())
    }
}
