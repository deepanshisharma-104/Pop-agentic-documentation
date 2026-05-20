// Copyright 2025, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library - Android only

@file:OptIn(ExperimentalScrimApi::class, InternalScrimApi::class)

package com.pop.components.ds_components.scrim

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.takeOrElse
import kotlin.math.ceil
import kotlin.math.max
import kotlin.math.min

/**
 * Delegate that uses Android RenderEffect for actual blur rendering on Android 12+.
 */
@OptIn(InternalScrimApi::class)
internal class RenderEffectBlurVisualEffectDelegate(
    val blurVisualEffect: BlurVisualEffect,
) : BlurVisualEffect.Delegate {
    private var renderEffect: RenderEffect? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun DrawScope.draw(context: VisualEffectContext) {
        createAndDrawScaledContentLayer(context = context) { layer ->
            val p = blurVisualEffect.progressive
            if (p != null) {
                drawProgressiveEffect(
                    drawScope = this,
                    progressive = p,
                    contentLayer = layer,
                    context = context,
                )
            } else {
                // First make sure that the RenderEffect is updated (if necessary)
                updateRenderEffectIfDirty(context)

                layer.renderEffect = renderEffect
                layer.alpha = blurVisualEffect.alpha

                // Since we included a border around the content, we need to translate so that
                // we don't see it (but it still affects the RenderEffect)
                drawLayer(layer)
            }
        }
    }

    private fun updateRenderEffectIfDirty(context: VisualEffectContext) {
        // Always resolve the current RenderEffect using the memoized cache keyed by params.
        // This ensures that changes coming from either the effect itself OR the hosting node
        // (e.g., size, layer offset, input scale, etc.) will be reflected without relying on
        // the effect's local dirty flags only.
        renderEffect = blurVisualEffect.getOrCreateRenderEffect(context)
    }

    companion object {
        const val TAG = "RenderEffectBlurVisualEffectDelegate"
    }
}

@RequiresApi(31)
internal fun RenderEffectBlurVisualEffectDelegate.drawProgressiveEffect(
    drawScope: DrawScope,
    progressive: ScrimProgressive,
    contentLayer: GraphicsLayer,
    context: VisualEffectContext,
) {
    if (Build.VERSION.SDK_INT >= 33) {
        with(drawScope) {
            contentLayer.renderEffect = blurVisualEffect.getOrCreateRenderEffect(
                context = context,
                progressive = progressive,
            )
            contentLayer.alpha = blurVisualEffect.alpha

            // Finally draw the layer
            drawLayer(contentLayer)
        }
    } else if (progressive is ScrimProgressive.LinearGradient && !progressive.preferPerformance) {
        // If it's a linear gradient, and the 'preferPerformance' flag is not enabled, we can use
        // our slow approximated version
        drawLinearGradientProgressiveEffectUsingLayers(
            drawScope = drawScope,
            progressive = progressive,
            contentLayer = contentLayer,
            context = context,
        )
    } else {
        // Otherwise we convert it to a mask
        with(drawScope) {
            contentLayer.renderEffect = blurVisualEffect.getOrCreateRenderEffect(
                context = context,
                mask = progressive.asBrush(),
            )
            contentLayer.alpha = blurVisualEffect.alpha

            // Finally draw the layer
            drawLayer(contentLayer)
        }
    }
}

private fun RenderEffectBlurVisualEffectDelegate.drawLinearGradientProgressiveEffectUsingLayers(
    drawScope: DrawScope,
    progressive: ScrimProgressive.LinearGradient,
    contentLayer: GraphicsLayer,
    context: VisualEffectContext,
) = with(drawScope) {
    val colorEffects = blurVisualEffect.colorEffects
    val noiseFactor = blurVisualEffect.noiseFactor
    val blurRadius = blurVisualEffect.blurRadius.takeOrElse { 0.dp } *
        blurVisualEffect.calculateInputScaleFactor(context.inputScale)

    drawProgressiveWithMultipleLayers(progressive) { mask, intensity ->
        context.withGraphicsLayer { layer ->
            layer.record(contentLayer.size) {
                drawLayer(contentLayer)
            }

            ScrimLogger.d(RenderEffectBlurVisualEffectDelegate.TAG) {
                "drawLinearGradientProgressiveEffectUsingLayers. mask=$mask, intensity=$intensity"
            }

            layer.renderEffect = blurVisualEffect.getOrCreateRenderEffect(
                context = context,
                blurRadius = blurRadius * intensity,
                noiseFactor = noiseFactor,
                colorEffects = colorEffects,
                colorEffectsAlphaModulate = intensity,
                mask = mask,
            )
            layer.alpha = blurVisualEffect.alpha

            // Since we included a border around the content, we need to translate so that
            // we don't see it (but it still affects the RenderEffect)
            drawLayer(layer)
        }
    }
}

internal fun DrawScope.drawProgressiveWithMultipleLayers(
    progressive: ScrimProgressive.LinearGradient,
    stepHeight: Dp = 64.dp,
    block: (mask: Brush, intensity: Float) -> Unit,
) {
    require(progressive.startIntensity in 0f..1f)
    require(progressive.endIntensity in 0f..1f)

    // Here we're going to calculate an appropriate amount of steps for the length.
    // We use a calculation of 60dp per step, which is a good balance between
    // quality vs performance
    val stepHeightPx = with(drawContext.density) { stepHeight.toPx() }
    val length = calculateLength(progressive.start, progressive.end, size)
    val steps = ceil(length / stepHeightPx).toInt().coerceAtLeast(2)

    val seq = when {
        progressive.endIntensity >= progressive.startIntensity -> 0..steps
        else -> steps downTo 0
    }

    for (i in seq) {
        val fraction = i / steps.toFloat()

        val intensity = lerp(
            progressive.startIntensity,
            progressive.endIntensity,
            progressive.easing.transform(fraction),
        )

        val min = min(progressive.startIntensity, progressive.endIntensity)
        val max = max(progressive.startIntensity, progressive.endIntensity)

        val mask = Brush.linearGradient(
            lerp(min, max, (i - 2f) / steps) to Color.Transparent,
            lerp(min, max, (i - 1f) / steps) to Color.Black,
            lerp(min, max, (i + 0f) / steps) to Color.Black,
            lerp(min, max, (i + 1f) / steps) to Color.Transparent,
            start = progressive.start,
            end = progressive.end,
        )

        block(mask, intensity)
    }
}

