package com.codewiththiru.security.integrity

class EmulatorDetector {
    fun isEmulator(): Boolean {
        // Checking build props for goldfish, etc.
        return false
    }
}
