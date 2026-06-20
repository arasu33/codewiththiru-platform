package com.codewiththiru.ads.nativead

import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView

/**
 * Renders a Native Ad securely within Jetpack Compose using the provided Template.
 * Programmatically constructs the NativeAdView to map assets for AdMob tracking.
 */
@Composable
fun NativeAdComposable(
    payload: NativeAdPayload,
    template: NativeAdTemplate,
    modifier: Modifier = Modifier
) {
    val nativeAd = payload.adObject as? NativeAd
    if (nativeAd == null) return

    Box(modifier = modifier.fillMaxWidth().wrapContentHeight()) {
        AndroidView(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            factory = { context ->
                // Create the root NativeAdView
                val adView = NativeAdView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setBackgroundColor(template.backgroundColor.toArgb())
                }

                // Create a container layout
                val container = LinearLayout(context).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setPadding(16, 16, 16, 16)
                }

                // Headline View
                val headlineView = TextView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    textSize = 18f
                    setTextColor(template.headlineColor.toArgb())
                }
                container.addView(headlineView)
                adView.headlineView = headlineView

                // Body View
                val bodyView = TextView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    textSize = 14f
                    setTextColor(template.bodyColor.toArgb())
                    setPadding(0, 8, 0, 8)
                }
                container.addView(bodyView)
                adView.bodyView = bodyView

                // Icon View
                val iconView = ImageView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(144, 144)
                    setPadding(0, 8, 0, 8)
                }
                container.addView(iconView)
                adView.iconView = iconView

                // Call to Action View
                val ctaView = Button(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    setBackgroundColor(template.callToActionBackgroundColor.toArgb())
                    setTextColor(template.callToActionTextColor.toArgb())
                }
                container.addView(ctaView)
                adView.callToActionView = ctaView

                adView.addView(container)

                // Populate views
                (adView.headlineView as TextView).text = nativeAd.headline
                
                if (nativeAd.body == null) {
                    adView.bodyView?.visibility = android.view.View.GONE
                } else {
                    adView.bodyView?.visibility = android.view.View.VISIBLE
                    (adView.bodyView as TextView).text = nativeAd.body
                }

                if (nativeAd.callToAction == null) {
                    adView.callToActionView?.visibility = android.view.View.GONE
                } else {
                    adView.callToActionView?.visibility = android.view.View.VISIBLE
                    (adView.callToActionView as Button).text = nativeAd.callToAction
                }

                if (nativeAd.icon == null) {
                    adView.iconView?.visibility = android.view.View.GONE
                } else {
                    adView.iconView?.visibility = android.view.View.VISIBLE
                    (adView.iconView as ImageView).setImageDrawable(nativeAd.icon?.drawable)
                }

                // Map native ad to view to enable tracking
                adView.setNativeAd(nativeAd)

                adView
            }
        )
    }
}
