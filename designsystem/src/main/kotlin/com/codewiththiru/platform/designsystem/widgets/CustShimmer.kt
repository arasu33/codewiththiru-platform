@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.platform.InspectorInfo
import kotlinx.coroutines.launch

/**
 * Applies an animated shimmer background effect over the current component.
 *
 * @param color The base color of the shimmer effect. If [Color.Unspecified], it falls back to a light gray.
 * @param durationMillis The duration of one full shimmer translation cycle in milliseconds.
 */
fun Modifier.custShimmer(
    color: Color = Color.Unspecified,
    durationMillis: Int = 1000,
): Modifier = this.then(CustShimmerElement(color, durationMillis))

private data class CustShimmerElement(
    val color: Color,
    val durationMillis: Int,
) : ModifierNodeElement<CustShimmerNode>() {
    override fun create(): CustShimmerNode = CustShimmerNode(color, durationMillis)

    override fun update(node: CustShimmerNode) {
        node.color = color
        node.durationMillis = durationMillis
    }

    override fun InspectorInfo.inspectableProperties() {
        name = "custShimmer"
        properties["color"] = color
        properties["durationMillis"] = durationMillis
    }
}

private class CustShimmerNode(
    var color: Color,
    var durationMillis: Int,
) : Modifier.Node(),
    DrawModifierNode {
    private val translateAnimation = AnimationState(0f)

    override fun onAttach() {
        super.onAttach()
        coroutineScope.launch {
            while (true) {
                translateAnimation.animateTo(
                    targetValue = 1000f,
                    animationSpec =
                        infiniteRepeatable(
                            animation =
                                tween(
                                    durationMillis = durationMillis,
                                    easing = FastOutSlowInEasing,
                                ),
                            repeatMode = RepeatMode.Restart,
                        ),
                )
            }
        }
    }

    override fun ContentDrawScope.draw() {
        val baseColor = if (color == Color.Unspecified) Color.LightGray else color
        val shimmerColors =
            listOf(
                baseColor.copy(alpha = 0.6f),
                baseColor.copy(alpha = 0.2f),
                baseColor.copy(alpha = 0.6f),
            )

        drawContent()

        drawRect(
            brush =
                Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset.Zero,
                    end = Offset(x = translateAnimation.value, y = translateAnimation.value),
                ),
        )
    }
}

/**
 * A dedicated Box that implements a generic shimmer loading state.
 *
 * @param modifier Optional modifier for structural layout constraint mapping.
 * @param shape Shape applied to the background box constraints.
 * @param color Color used to tint the Box background and its overlapping shimmer highlight.
 */
@Composable
fun CustShimmerBox(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    color: Color = Color.Unspecified,
) {
    val resolvedColor =
        if (color == Color.Unspecified) {
            MaterialTheme.colorScheme.surfaceVariant
        } else {
            color
        }

    Box(
        modifier =
            modifier
                .background(color = resolvedColor, shape = shape)
                .custShimmer(color = resolvedColor),
    )
}
