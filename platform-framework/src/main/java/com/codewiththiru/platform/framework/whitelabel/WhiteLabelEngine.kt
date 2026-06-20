package com.codewiththiru.platform.framework.whitelabel

data class BrandConfiguration(
    val brandId: String,
    val primaryColor: String,
    val secondaryColor: String,
    val logoResourceUrl: String
)

interface BrandManager {
    fun applyBrand(config: BrandConfiguration)
}

interface ThemeManager {
    fun applyTheme(themeOverrides: Map<String, String>)
}

class WhiteLabelManager(
    private val brandManager: BrandManager,
    private val themeManager: ThemeManager
) {
    fun applyWhiteLabel(config: BrandConfiguration, themeOverrides: Map<String, String>) {
        brandManager.applyBrand(config)
        themeManager.applyTheme(themeOverrides)
    }
}
