package com.codewiththiru.platform.designsystem.components

import android.os.Build
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer

/**
 * A custom modifier that shrinks the component slightly when pressed,
 * simulating physical spring physics, driven by the provided InteractionSource.
 */
fun Modifier.bounceClick(
    interactionSource: androidx.compose.foundation.interaction.InteractionSource,
    enabled: Boolean = true,
): Modifier =
    composed {
        val isPressed by interactionSource.collectIsPressedAsState()

        val scale by animateFloatAsState(
            targetValue = if (isPressed && enabled) 0.95f else 1f,
            animationSpec =
                spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            label = "BounceClickScale",
        )

        this.graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
    }

/**
 * Applies a premium glassmorphic effect.
 * Uses native RenderEffect blur on Android 12+, otherwise falls back to a translucent tint.
 */
fun Modifier.glassmorphic(
    shape: Shape,
    tint: Color = Color.White.copy(alpha = 0.2f),
    blurRadius: Float = 50f,
): Modifier =
    composed {
        this
            .clip(shape)
            .graphicsLayer {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    renderEffect =
                        android.graphics.RenderEffect
                            .createBlurEffect(
                                blurRadius,
                                blurRadius,
                                android.graphics.Shader.TileMode.DECAL,
                            ).let {
                                it.asComposeRenderEffect()
                            }
                }
            }.drawWithContent {
                drawContent()
                drawRect(color = tint)
            }
    }
