package com.codewiththiru.platform.android.deviceinfo

interface CustDeviceInfoProvider {
    val manufacturer: String
    val brand: String
    val model: String
    val device: String
    val sdkVersion: Int
    val androidVersion: String
    val isEmulator: Boolean
    val isTablet: Boolean
    val formFactor: CustDeviceFormFactor
}
