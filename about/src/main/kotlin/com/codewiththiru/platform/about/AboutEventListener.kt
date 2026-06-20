package com.codewiththiru.platform.about

import com.codewiththiru.platform.about.model.AppInfo
import com.codewiththiru.platform.about.model.DeveloperInfo
import com.codewiththiru.platform.about.model.SocialLink

/**
 * Unified listener interface defining explicit action boundaries driven by the About UI.
 */
interface AboutEventListener {
    fun onShareApp(appInfo: AppInfo)
    fun onCopyDiagnostics(payload: String)
    fun onContactDeveloper(developerInfo: DeveloperInfo)
    fun onSocialLinkClick(link: SocialLink)
    fun onLegalLinkClick(url: String)
}
