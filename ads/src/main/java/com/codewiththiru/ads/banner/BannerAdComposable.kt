package com.codewiththiru.ads.banner

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.viewinterop.AndroidView
import com.codewiththiru.ads.api.AdUnitResolver
import com.codewiththiru.ads.api.AdType
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
fun BannerAdComposable(
    adUnitId: String,
    controller: BannerController,
    modifier: Modifier = Modifier
) {
    var adView by remember { mutableStateOf<AdView?>(null) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .semantics { contentDescription = "Advertisement" },
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.wrapContentHeight(),
            factory = { context ->
                var adLoadStartTime = 0L
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
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

                    // Actually load ad
                    controller.onLoading()
                    adLoadStartTime = System.currentTimeMillis()
                    loadAd(AdRequest.Builder().build())
                }.also { adView = it }
            },
            update = { view ->
                // Update if needed
            }
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            adView?.destroy()
        }
    }
}
