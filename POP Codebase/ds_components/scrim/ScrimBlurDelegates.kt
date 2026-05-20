// Copyright 2025, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library

@file:OptIn(ExperimentalScrimApi::class, InternalScrimApi::class)

package com.pop.components.ds_components.scrim

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isFinite
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.layer.CompositingStrategy
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.roundToIntSize
import androidx.compose.ui.unit.takeOrElse

/**
 * A delegate that draws a scrim (color overlay) instead of actual blur.
 * Used as a fallback when blur is disabled or not supported.
 */
@OptIn(InternalScrimApi::class)
internal class ScrimBlurVisualEffectDelegate(
    val blurVisualEffect: BlurVisualEffect,
) : BlurVisualEffect.Delegate {
    override fun DrawScope.draw(context: VisualEffectContext) {
        val scrimTint = blurVisualEffect.fallbackTint.takeIf { it.isSpecified }
            ?: blurVisualEffect.colorEffects.firstOrNull()
                ?.boostForFallback(blurVisualEffect.blurRadius.takeOrElse { 0.dp })
            ?: return

        withAlpha(alpha = blurVisualEffect.alpha, context = context) {
            drawScrim(
                colorEffect = scrimTint,
                context = context,
                mask = blurVisualEffect.mask ?: blurVisualEffect.progressive?.asBrush(),
            )
        }
    }
}

/**
 * Helper function to boost a color effect for fallback scrim.
 */
private fun ScrimColorEffect.boostForFallback(blurRadius: Dp): ScrimColorEffect = when (this) {
    is ScrimColorEffect.TintColor -> {
        // For color, we can boost the alpha
        val resolved = blurRadius.takeOrElse { ScrimBlurDefaults.blurRadius }
        val boosted = color.boostAlphaForBlurRadius(resolved)
        copy(color = boosted)
    }
    else -> this
}

/**
 * Boost alpha based on blur radius for scrim fallback.
 */
private fun Color.boostAlphaForBlurRadius(blurRadius: Dp): Color {
    // We treat a blur radius of 72.dp as near 'opaque', and linearly boost using that
    val factor = 1 + (blurRadius.value / 72)
    return copy(alpha = (alpha * factor).coerceAtMost(1f))
}

/**
 * Draws a color effect with optional mask.
 */
@OptIn(InternalScrimApi::class)
private fun DrawScope.drawScrim(
    colorEffect: ScrimColorEffect,
    context: VisualEffectContext,
    offset: Offset = Offset.Zero,
    expandedSize: Size = this.size,
    mask: Brush? = null,
) {
    when (colorEffect) {
        is ScrimColorEffect.TintBrush -> {
            if (mask != null) {
                context.withGraphicsLayer { layer ->
                    layer.compositingStrategy = CompositingStrategy.Offscreen
                    layer.record(size = size.roundToIntSize()) {
                        drawRect(brush = colorEffect.brush, blendMode = colorEffect.blendMode)
                        drawRect(brush = mask, blendMode = BlendMode.DstIn)
                    }
                    translate(offset) {
                        drawLayer(layer)
                    }
                }
            } else {
                drawRect(
                    brush = colorEffect.brush,
                    topLeft = offset,
                    size = size,
                    blendMode = colorEffect.blendMode,
                )
            }
        }
        is ScrimColorEffect.TintColor -> {
            if (mask != null) {
                // When we have a mask, combine the tint color with the mask
                context.withGraphicsLayer { layer ->
                    layer.compositingStrategy = CompositingStrategy.Offscreen
                    layer.record(size = size.roundToIntSize()) {
                        drawRect(color = colorEffect.color, blendMode = colorEffect.blendMode)
                        drawRect(brush = mask, blendMode = BlendMode.DstIn)
                    }
                    translate(offset) {
                        drawLayer(layer)
                    }
                }
            } else {
                drawRect(
                    color = colorEffect.color,
                    size = expandedSize,
                    blendMode = colorEffect.blendMode,
                )
            }
        }
        is ScrimColorEffect.ColorFilter -> {
            if (mask != null) {
                context.withGraphicsLayer { layer ->
                    layer.compositingStrategy = CompositingStrategy.Offscreen
                    layer.record(size = size.roundToIntSize()) {
                        drawRect(color = Color.White, colorFilter = colorEffect.colorFilter)
                        drawRect(brush = mask, blendMode = BlendMode.DstIn)
                    }
                    translate(offset) {
                        val canvas = drawContext.canvas
                        val paint = Paint().apply { blendMode = colorEffect.blendMode }
                        val bounds = Rect(Offset.Zero, size)
                        canvas.saveLayer(bounds, paint)
                        drawLayer(layer)
                        canvas.restore()
                    }
                }
            } else {
                drawRect(
                    color = Color.White,
                    size = expandedSize,
                    colorFilter = colorEffect.colorFilter,
                    blendMode = colorEffect.blendMode,
                )
            }
        }
        else -> {
            // Unspecified - do nothing
        }
    }
}

/**
 * Draw with alpha modulation.
 */
@OptIn(ExperimentalScrimApi::class)
private inline fun DrawScope.withAlpha(
    alpha: Float,
    context: VisualEffectContext,
    crossinline block: DrawScope.() -> Unit,
) {
    if (alpha < 1f) {
        context.withGraphicsLayer { layer ->
            layer.alpha = alpha
            layer.record(size = size.roundToIntSize()) { block() }
            drawLayer(layer)
        }
    } else {
        block()
    }
}

/**
 * Translate helper.
 */
inline fun DrawScope.translate(
    offset: Offset,
    block: DrawScope.() -> Unit,
) {
    if (offset.isFinite && offset != Offset.Zero) {
        translate(offset.x, offset.y, block)
    } else {
        block()
    }
}

/**
 * Convert ScrimProgressive to Brush.
 */
internal fun ScrimProgressive.asBrush(numStops: Int = 20): Brush = when (this) {
    is ScrimProgressive.LinearGradient -> asBrush(numStops)
    is ScrimProgressive.RadialGradient -> asBrush(numStops)
    is ScrimProgressive.Brush -> brush
}

private fun ScrimProgressive.LinearGradient.asBrush(numStops: Int = 20): Brush {
    val startIntensity = startIntensity.coerceIn(0f, 1f)
    val endIntensity = endIntensity.coerceIn(0f, 1f)
    
    return Brush.linearGradient(
        colors = List(numStops) { i ->
            val x = i * 1f / (numStops - 1)
            Color.Magenta.copy(alpha = lerp(startIntensity, endIntensity, easing.transform(x)))
        },
        start = start,
        end = end,
    )
}

private fun ScrimProgressive.RadialGradient.asBrush(numStops: Int = 20): Brush {
    val centerIntensity = centerIntensity.coerceIn(0f, 1f)
    val radiusIntensity = radiusIntensity.coerceIn(0f, 1f)
    
    return Brush.radialGradient(
        colors = List(numStops) { i ->
            val x = i * 1f / (numStops - 1)
            Color.Magenta.copy(alpha = lerp(centerIntensity, radiusIntensity, easing.transform(x)))
        },
        center = center,
        radius = radius,
    )
}

