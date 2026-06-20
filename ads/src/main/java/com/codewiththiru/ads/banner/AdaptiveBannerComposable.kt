package com.codewiththiru.ads.banner

import android.app.Activity
import android.util.DisplayMetrics
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
fun AdaptiveBannerComposable(
    adUnitId: String,
    controller: BannerController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var adView by remember { mutableStateOf<AdView?>(null) }

    // Calculate adaptive ad size based on window metrics
    val adSize = remember(context) {
        val displayMetrics = context.resources.displayMetrics
        val widthPixels = displayMetrics.widthPixels
        val density = displayMetrics.density
        val adWidth = (widthPixels / density).toInt()
        
        AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .semantics { contentDescription = "Advertisement" },
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.wrapContentHeight(),
            factory = { ctx ->
                var adLoadStartTime = 0L
                AdView(ctx).apply {
                    setAdSize(adSize)
                    this.adUnitId = adUnitId

                    adListener = object : AdListener() {
                        override fun onAdLoaded() {
                            val loadTime = System.currentTimeMillis() - adLoadStartTime
                            controller.onLoaded(loadTime)
                        }

                        override fun onAdFailedToLoad(error: LoadAdError) {
                            controller.onFailed(error.code, error.message)
                        }

                        override fun onAdImpression() {
                            controller.onImpression()
                        }

                        override fun onAdClicked() {
                            controller.onClicked()
                        }
                    }

                    controller.onLoading()
                    adLoadStartTime = System.currentTimeMillis()
                    loadAd(AdRequest.Builder().build())
                }.also { adView = it }
            },
            update = { view ->
                // Usually we'd want to handle configuration changes by reloading the ad 
                // if the width changed significantly, but for basic adaptive banner this works.
            }
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            adView?.destroy()
        }
    }
}
