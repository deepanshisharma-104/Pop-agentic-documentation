// Copyright 2024, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library - Android only

@file:Suppress("NOTHING_TO_INLINE")
@file:OptIn(InternalScrimApi::class)

package com.pop.components.ds_components.scrim

import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.ColorFilter
import android.graphics.RenderEffect as AndroidRenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.graphics.BlendMode as ComposeBlendMode
import androidx.compose.ui.graphics.ColorFilter as ComposeColorFilter
import androidx.compose.ui.graphics.Shader as ComposeShader
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.asAndroidColorFilter
import kotlin.math.ceil
import kotlin.math.hypot
import kotlin.math.roundToInt

/**
 * Sealed interface representing supported blend modes for platform operations.
 */
@InternalScrimApi
internal sealed interface ScrimBlendMode {
    data object Clear : ScrimBlendMode
    data object Src : ScrimBlendMode
    data object Dst : ScrimBlendMode
    data object SrcOver : ScrimBlendMode
    data object DstOver : ScrimBlendMode
    data object SrcIn : ScrimBlendMode
    data object DstIn : ScrimBlendMode
    data object SrcOut : ScrimBlendMode
    data object DstOut : ScrimBlendMode
    data object SrcAtop : ScrimBlendMode
    data object DstAtop : ScrimBlendMode
    data object Xor : ScrimBlendMode
    data object Plus : ScrimBlendMode
    data object Modulate : ScrimBlendMode
    data object Screen : ScrimBlendMode
    data object Overlay : ScrimBlendMode
    data object Darken : ScrimBlendMode
    data object Lighten : ScrimBlendMode
    data object ColorDodge : ScrimBlendMode
    data object ColorBurn : ScrimBlendMode
    data object Hardlight : ScrimBlendMode
    data object Softlight : ScrimBlendMode
    data object Difference : ScrimBlendMode
    data object Exclusion : ScrimBlendMode
    data object Multiply : ScrimBlendMode
    data object Hue : ScrimBlendMode
    data object Saturation : ScrimBlendMode
    data object Color : ScrimBlendMode
    data object Luminosity : ScrimBlendMode

    companion object {
        fun from(blendMode: ComposeBlendMode): ScrimBlendMode = when (blendMode) {
            ComposeBlendMode.Clear -> Clear
            ComposeBlendMode.Src -> Src
            ComposeBlendMode.Dst -> Dst
            ComposeBlendMode.SrcOver -> SrcOver
            ComposeBlendMode.DstOver -> DstOver
            ComposeBlendMode.SrcIn -> SrcIn
            ComposeBlendMode.DstIn -> DstIn
            ComposeBlendMode.SrcOut -> SrcOut
            ComposeBlendMode.DstOut -> DstOut
            ComposeBlendMode.SrcAtop -> SrcAtop
            ComposeBlendMode.DstAtop -> DstAtop
            ComposeBlendMode.Xor -> Xor
            ComposeBlendMode.Plus -> Plus
            ComposeBlendMode.Modulate -> Modulate
            ComposeBlendMode.Screen -> Screen
            ComposeBlendMode.Overlay -> Overlay
            ComposeBlendMode.Darken -> Darken
            ComposeBlendMode.Lighten -> Lighten
            ComposeBlendMode.ColorDodge -> ColorDodge
            ComposeBlendMode.ColorBurn -> ColorBurn
            ComposeBlendMode.Hardlight -> Hardlight
            ComposeBlendMode.Softlight -> Softlight
            ComposeBlendMode.Difference -> Difference
            ComposeBlendMode.Exclusion -> Exclusion
            ComposeBlendMode.Multiply -> Multiply
            ComposeBlendMode.Hue -> Hue
            ComposeBlendMode.Saturation -> Saturation
            ComposeBlendMode.Color -> Color
            ComposeBlendMode.Luminosity -> Luminosity
            else -> SrcOver
        }
    }
}

@InternalScrimApi
internal fun ComposeBlendMode.toScrimBlendMode(): ScrimBlendMode = ScrimBlendMode.from(this)

@RequiresApi(Build.VERSION_CODES.Q)
@InternalScrimApi
internal fun ScrimBlendMode.toAndroidBlendMode(): BlendMode = when (this) {
    ScrimBlendMode.Clear -> BlendMode.CLEAR
    ScrimBlendMode.Src -> BlendMode.SRC
    ScrimBlendMode.Dst -> BlendMode.DST
    ScrimBlendMode.SrcOver -> BlendMode.SRC_OVER
    ScrimBlendMode.DstOver -> BlendMode.DST_OVER
    ScrimBlendMode.SrcIn -> BlendMode.SRC_IN
    ScrimBlendMode.DstIn -> BlendMode.DST_IN
    ScrimBlendMode.SrcOut -> BlendMode.SRC_OUT
    ScrimBlendMode.DstOut -> BlendMode.DST_OUT
    ScrimBlendMode.SrcAtop -> BlendMode.SRC_ATOP
    ScrimBlendMode.DstAtop -> BlendMode.DST_ATOP
    ScrimBlendMode.Xor -> BlendMode.XOR
    ScrimBlendMode.Plus -> BlendMode.PLUS
    ScrimBlendMode.Modulate -> BlendMode.MODULATE
    ScrimBlendMode.Screen -> BlendMode.SCREEN
    ScrimBlendMode.Overlay -> BlendMode.OVERLAY
    ScrimBlendMode.Darken -> BlendMode.DARKEN
    ScrimBlendMode.Lighten -> BlendMode.LIGHTEN
    ScrimBlendMode.ColorDodge -> BlendMode.COLOR_DODGE
    ScrimBlendMode.ColorBurn -> BlendMode.COLOR_BURN
    ScrimBlendMode.Hardlight -> BlendMode.HARD_LIGHT
    ScrimBlendMode.Softlight -> BlendMode.SOFT_LIGHT
    ScrimBlendMode.Difference -> BlendMode.DIFFERENCE
    ScrimBlendMode.Exclusion -> BlendMode.EXCLUSION
    ScrimBlendMode.Multiply -> BlendMode.MULTIPLY
    ScrimBlendMode.Hue -> BlendMode.HUE
    ScrimBlendMode.Saturation -> BlendMode.SATURATION
    ScrimBlendMode.Color -> BlendMode.COLOR
    ScrimBlendMode.Luminosity -> BlendMode.LUMINOSITY
}

internal typealias PlatformRenderEffect = AndroidRenderEffect

internal typealias PlatformColorFilter = ColorFilter

@RequiresApi(31)
@InternalScrimApi
internal fun createShaderImageFilter(shader: ComposeShader, crop: Rect? = null): PlatformRenderEffect {
    // Android RenderEffect.createShaderEffect doesn't support crop
    return AndroidRenderEffect.createShaderEffect(shader)
}

@RequiresApi(31)
@InternalScrimApi
internal fun createBlendImageFilter(
    blendMode: ScrimBlendMode,
    background: PlatformRenderEffect,
    foreground: PlatformRenderEffect,
    crop: Rect? = null,
): PlatformRenderEffect = AndroidRenderEffect.createBlendModeEffect(background, foreground, blendMode.toAndroidBlendMode())

@RequiresApi(31)
@InternalScrimApi
internal fun createColorFilterImageFilter(
    colorFilter: PlatformColorFilter,
    input: PlatformRenderEffect? = null,
    crop: Rect? = null,
): PlatformRenderEffect = when {
    input != null -> AndroidRenderEffect.createColorFilterEffect(colorFilter, input)
    else -> AndroidRenderEffect.createColorFilterEffect(colorFilter)
}

@RequiresApi(31)
@InternalScrimApi
internal fun createBlurImageFilter(
    sigmaX: Float,
    sigmaY: Float,
    tileMode: TileMode,
    input: PlatformRenderEffect? = null,
    crop: Rect? = null,
): PlatformRenderEffect {
    val tileModeAndroid = when (tileMode) {
        TileMode.Clamp -> Shader.TileMode.CLAMP
        TileMode.Repeated -> Shader.TileMode.REPEAT
        TileMode.Mirror -> Shader.TileMode.MIRROR
        TileMode.Decal -> Shader.TileMode.DECAL
        else -> Shader.TileMode.CLAMP
    }
    val blurEffect = AndroidRenderEffect.createBlurEffect(sigmaX, sigmaY, tileModeAndroid)
    return if (input != null) {
        AndroidRenderEffect.createChainEffect(blurEffect, input)
    } else {
        blurEffect
    }
}

@RequiresApi(31)
@InternalScrimApi
internal fun createOffsetImageFilter(
    offsetX: Float,
    offsetY: Float,
    input: PlatformRenderEffect? = null,
    crop: Rect? = null,
): PlatformRenderEffect = when {
    input != null -> AndroidRenderEffect.createOffsetEffect(offsetX, offsetY, input)
    else -> AndroidRenderEffect.createOffsetEffect(offsetX, offsetY)
}

@RequiresApi(31)
@InternalScrimApi
internal inline fun PlatformRenderEffect.then(other: PlatformRenderEffect): PlatformRenderEffect {
    return AndroidRenderEffect.createChainEffect(other, this)
}

@RequiresApi(31)
@InternalScrimApi
internal fun PlatformRenderEffect.blendForeground(
    foreground: PlatformRenderEffect,
    blendMode: ScrimBlendMode,
    offset: Offset = Offset.Unspecified,
): PlatformRenderEffect = createBlendImageFilter(
    blendMode = blendMode,
    background = this,
    foreground = when {
        offset.isUnspecified -> foreground
        offset == Offset.Zero -> foreground
        else -> createOffsetImageFilter(offset.x, offset.y, foreground)
    },
)

@InternalScrimApi
internal fun ComposeColorFilter.toPlatformColorFilter(): PlatformColorFilter = asAndroidColorFilter()

@RequiresApi(Build.VERSION_CODES.Q)
@InternalScrimApi
internal fun createBlendColorFilter(
    color: Int,
    blendMode: ScrimBlendMode,
): PlatformColorFilter {
    return BlendModeColorFilter(color, blendMode.toAndroidBlendMode())
}

internal fun calculateLength(
    start: Offset,
    end: Offset,
    size: Size,
): Float {
    val (startX, startY) = start
    val endX = end.x.coerceAtMost(size.width)
    val endY = end.y.coerceAtMost(size.height)
    return hypot(endX - startX, endY - startY)
}

internal fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}

internal fun ceil(size: Size): Size =
    Size(
        width = ceil(size.width),
        height = ceil(size.height),
    )

internal fun Offset.round(): Offset = Offset(x.roundToInt().toFloat(), y.roundToInt().toFloat())

/**
 * Extension function to convert TileMode to Android TileMode.
 */
@RequiresApi(Build.VERSION_CODES.S)
@InternalScrimApi
internal fun TileMode.toAndroidTileModeInternal(): Shader.TileMode = when (this) {
    TileMode.Clamp -> Shader.TileMode.CLAMP
    TileMode.Repeated -> Shader.TileMode.REPEAT
    TileMode.Mirror -> Shader.TileMode.MIRROR
    TileMode.Decal -> Shader.TileMode.DECAL
    else -> Shader.TileMode.CLAMP
}

