// Copyright 2023, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
//
// Standalone implementation adapted from haze library - Android only

@file:OptIn(ExperimentalScrimApi::class, InternalScrimApi::class)

package com.pop.components.ds_components.scrim

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.RenderEffect as AndroidRenderEffect
import android.graphics.Shader.TileMode.REPEAT
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.collection.LruCache
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.takeOrElse
import androidx.compose.ui.util.fastFold
import kotlin.math.abs
import androidx.core.graphics.createBitmap

// Note: You'll need to provide a noise texture bitmap or handle this differently
// For now, we'll create a placeholder. In production, you'd load this from resources
private var noiseTexture: Bitmap? = null

internal fun Context.getNoiseTexture(): Bitmap {
    val cached = noiseTexture
    if (cached != null && !cached.isRecycled) {
        return cached
    }

    // TODO: Load noise texture from resources
    // For now, create a simple placeholder bitmap
    // In production, you'd use: BitmapFactory.decodeResource(resources, R.drawable.haze_noise)
    return createBitmap(64, 64).also {
        // Fill with noise pattern (simplified - you should use actual noise texture)
        it.eraseColor(0x80808080.toInt())
        noiseTexture = it
    }
}

@RequiresApi(31)
@InternalScrimApi
internal fun createNoiseEffect(
    context: Any?, // PlatformContext - on Android this is Context
    noiseFactor: Float,
    mask: Shader?,
    scale: Float,
): PlatformRenderEffect {
    val androidContext = context as? Context ?: return AndroidRenderEffect.createOffsetEffect(0f, 0f)

    // Apply scaling through the shader matrix so we can reuse the decoded bitmap.
    val normalizedScale = if (scale > 0f) scale else 1f
    val noiseShader = BitmapShader(androidContext.getNoiseTexture(), REPEAT, REPEAT).apply {
        if (abs(normalizedScale - 1f) >= 0.001f) {
            val matrix = Matrix().apply {
                val reciprocal = 1f / normalizedScale
                setScale(reciprocal, reciprocal)
            }
            setLocalMatrix(matrix)
        }
    }

    val noiseAlpha = noiseFactor.coerceIn(0f, 1f)
    val baseNoiseEffect = AndroidRenderEffect.createShaderEffect(noiseShader)
    val noiseEffect = if (noiseAlpha < 1f) {
        val matrix = ColorMatrix().apply { setScale(1f, 1f, 1f, noiseAlpha) }
        AndroidRenderEffect.createColorFilterEffect(ColorMatrixColorFilter(matrix), baseNoiseEffect)
    } else {
        baseNoiseEffect
    }

    return when {
        mask != null -> {
            // If we have a mask, we need to apply it to the noise bitmap shader via a blend mode
            createBlendImageFilter(
                blendMode = ScrimBlendMode.SrcIn,
                background = createShaderImageFilter(mask),
                foreground = noiseEffect,
            )
        }
        else -> noiseEffect
    }
}

@RequiresApi(31)
@InternalScrimApi
internal fun createBlurRenderEffect(
    blurRadiusPx: Float,
    params: RenderEffectParams,
): PlatformRenderEffect {
    if (blurRadiusPx <= 0f) {
        return AndroidRenderEffect.createOffsetEffect(0f, 0f)
    }

    return try {
        // On Android we use the native blur effect directly for better performance
        AndroidRenderEffect.createBlurEffect(
            blurRadiusPx,
            blurRadiusPx,
            params.blurTileMode.toAndroidTileModeInternal(),
        )
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException(
            "Error whilst creating blur effect. " +
                    "This is likely because this device does not support a blur radius of ${params.blurRadius}dp",
            e,
        )
    }
}

/**
 * Parameters for creating a render effect.
 */
@InternalScrimApi
internal data class RenderEffectParams(
    val blurRadius: Dp,
    val noiseFactor: Float,
    val scale: Float,
    val contentSize: Size,
    val contentOffset: Offset,
    val colorEffects: List<ScrimColorEffect> = emptyList(),
    val colorEffectsAlphaModulate: Float = 1f,
    val mask: Brush? = null,
    val progressive: ScrimProgressive? = null,
    val blurTileMode: TileMode,
)

private val renderEffectCache by lazy(mode = LazyThreadSafetyMode.NONE) {
    LruCache<RenderEffectParams, RenderEffect>(maxSize = 50)
}

@OptIn(ExperimentalScrimApi::class)
internal fun BlurVisualEffect.getOrCreateRenderEffect(
    context: VisualEffectContext,
    inputScale: Float = calculateInputScaleFactor(context.inputScale),
    blurRadius: Dp = this.blurRadius.takeOrElse { 0.dp },
    noiseFactor: Float = this.noiseFactor,
    colorEffects: List<ScrimColorEffect> = this.colorEffects,
    colorEffectsAlphaModulate: Float = 1f,
    contentSize: Size = context.size,
    contentOffset: Offset = context.layerOffset,
    mask: Brush? = this.mask,
    progressive: ScrimProgressive? = null,
    blurTileMode: TileMode = calculateBlurTileMode(),
): RenderEffect? {
    trace("BlurVisualEffect-getOrCreateRenderEffect") { }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        getOrCreateRenderEffectInternal(
            context = context,
            params = RenderEffectParams(
                blurRadius = blurRadius,
                noiseFactor = noiseFactor,
                scale = inputScale,
                colorEffects = colorEffects,
                colorEffectsAlphaModulate = colorEffectsAlphaModulate,
                contentSize = contentSize,
                contentOffset = contentOffset,
                mask = mask,
                progressive = progressive,
                blurTileMode = blurTileMode,
            ),
        )
    } else {
        null
    }
}

@RequiresApi(Build.VERSION_CODES.S)
private fun getOrCreateRenderEffectInternal(context: VisualEffectContext, params: RenderEffectParams): RenderEffect {
    ScrimLogger.d(BlurVisualEffect.TAG) { "getOrCreateRenderEffect: $params" }
    val cached = renderEffectCache[params]
    if (cached != null) {
        ScrimLogger.d(BlurVisualEffect.TAG) { "getOrCreateRenderEffect. Returning cached: $params" }
        return cached
    }

    ScrimLogger.d(BlurVisualEffect.TAG) { "getOrCreateRenderEffect. Creating: $params" }
    return createRenderEffect(
        context = context,
        density = context.requireDensity(),
        params = params,
    ).also { effect ->
        renderEffectCache.put(params, effect)
    }
}

@RequiresApi(31)
@OptIn(InternalScrimApi::class)
private fun createRenderEffect(
    context: VisualEffectContext,
    density: Density,
    params: RenderEffectParams,
): RenderEffect {
    val blurRadius = params.blurRadius * params.scale
    require(blurRadius >= 0.dp) { "blurRadius needs to be equal or greater than 0.dp" }
    val size = ceil(params.contentSize * params.scale)
    val offset = (params.contentOffset * params.scale).round()

    val blurRadiusPx = with(density) { blurRadius.toPx() }
    val progressiveShader = params.progressive?.asBrush()?.toShader(size)

    val blur = if (progressiveShader != null) {
        // If we've been provided with a progressive/gradient blur shader, we need to use
        // our custom blur via a runtime shader (requires runtime shader support)
        createGradientBlurRenderEffect(blurRadiusPx, size, offset, progressiveShader)
    } else {
        // Platform-specific blur creation
        createBlurRenderEffect(blurRadiusPx, params)
    }

    val noise = createNoiseEffect(
        context = context.requirePlatformContext(),
        noiseFactor = params.noiseFactor,
        mask = progressiveShader,
        scale = params.scale,
    )

    return blur
        .blendForeground(foreground = noise, blendMode = ScrimBlendMode.Softlight)
        .withTints(params.colorEffects, size, offset, params.colorEffectsAlphaModulate, progressiveShader)
        .withMask(params.mask, size, offset)
        .asComposeRenderEffect()
}

private fun PlatformRenderEffect.withTints(
    effects: List<ScrimColorEffect>,
    size: Size,
    offset: Offset,
    alphaModulate: Float = 1f,
    mask: Shader? = null,
): PlatformRenderEffect = effects.fastFold(this) { acc, effect ->
    acc.withColorEffect(effect, size, offset, alphaModulate, mask)
}

private fun PlatformRenderEffect.withColorEffect(
    effect: ScrimColorEffect,
    size: Size,
    offset: Offset,
    alphaModulate: Float = 1f,
    mask: Shader? = null,
): PlatformRenderEffect {
    if (!effect.isSpecified) return this

    return when (effect) {
        is ScrimColorEffect.TintBrush -> withBrushTint(effect, size, offset, alphaModulate, mask)
        is ScrimColorEffect.TintColor -> withColorTint(effect, offset, alphaModulate, mask)
        is ScrimColorEffect.ColorFilter -> withColorFilter(effect, size, offset, mask)
        else -> this
    }
}

@RequiresApi(Build.VERSION_CODES.S)
private fun PlatformRenderEffect.withBrushTint(
    effect: ScrimColorEffect.TintBrush,
    size: Size,
    offset: Offset,
    alphaModulate: Float,
    mask: Shader?,
): PlatformRenderEffect {
    val tintBrush = effect.brush.toShader(size) ?: return this

    val brushEffect = if (alphaModulate >= 1f) {
        createShaderImageFilter(tintBrush)
    } else {
        // If we need to modulate the alpha, wrap it in a ColorFilter
        createColorFilterImageFilter(
            colorFilter = createBlendColorFilter(
                color = Color.Black.copy(alpha = alphaModulate).toArgb(),
                blendMode = ScrimBlendMode.SrcIn,
            ),
            input = createShaderImageFilter(tintBrush),
        )
    }

    return applyMaskAndBlend(
        baseEffect = brushEffect,
        blendMode = effect.blendMode,
        mask = mask,
        offset = offset,
    )
}

@RequiresApi(Build.VERSION_CODES.S)
private fun PlatformRenderEffect.withColorTint(
    effect: ScrimColorEffect.TintColor,
    offset: Offset,
    alphaModulate: Float,
    mask: Shader?,
): PlatformRenderEffect {
    val tintColor = when {
        alphaModulate < 1f -> effect.color.copy(alpha = effect.color.alpha * alphaModulate)
        else -> effect.color
    }

    if (tintColor.alpha < 0.005f) return this

    val colorEffect = createBlendColorFilter(tintColor.toArgb(), effect.blendMode.toScrimBlendMode())

    val effectWithMask = if (mask != null) {
        createColorFilterImageFilter(
            colorFilter = createBlendColorFilter(tintColor.toArgb(), ScrimBlendMode.SrcIn),
            input = createShaderImageFilter(mask),
        )
    } else {
        createColorFilterImageFilter(
            colorFilter = colorEffect,
            input = this,
        )
    }

    return if (mask != null) {
        blendForeground(
            foreground = effectWithMask,
            blendMode = effect.blendMode.toScrimBlendMode(),
            offset = offset,
        )
    } else {
        effectWithMask
    }
}

@RequiresApi(Build.VERSION_CODES.S)
private fun PlatformRenderEffect.withColorFilter(
    effect: ScrimColorEffect.ColorFilter,
    size: Size,
    offset: Offset,
    mask: Shader?,
): PlatformRenderEffect {
    val filterEffect = createColorFilterImageFilter(
        colorFilter = effect.colorFilter.toPlatformColorFilter(),
        input = this,
    )

    return applyMaskAndBlend(
        baseEffect = filterEffect,
        blendMode = effect.blendMode,
        mask = mask,
        offset = offset,
    )
}

@RequiresApi(Build.VERSION_CODES.S)
private fun PlatformRenderEffect.applyMaskAndBlend(
    baseEffect: PlatformRenderEffect,
    blendMode: BlendMode,
    mask: Shader?,
    offset: Offset,
): PlatformRenderEffect {
    val effectWithMask = if (mask != null) {
        createBlendImageFilter(
            blendMode = ScrimBlendMode.SrcIn,
            background = createShaderImageFilter(mask),
            foreground = baseEffect,
        )
    } else {
        baseEffect
    }

    return blendForeground(
        foreground = effectWithMask,
        blendMode = blendMode.toScrimBlendMode(),
        offset = offset,
    )
}

@RequiresApi(Build.VERSION_CODES.S)
private fun PlatformRenderEffect.withMask(
    brush: Brush?,
    size: Size,
    offset: Offset,
    blendMode: ScrimBlendMode = ScrimBlendMode.DstIn,
): PlatformRenderEffect {
    val shader = brush?.toShader(size) ?: return this
    return blendForeground(
        foreground = createShaderImageFilter(shader),
        blendMode = blendMode,
        offset = offset,
    )
}

@RequiresApi(33)
private fun createGradientBlurRenderEffect(
    blurRadiusPx: Float,
    size: Size,
    offset: Offset,
    mask: Shader,
): PlatformRenderEffect {
    // For gradient blur with runtime shader, we would need to implement the SKSL shaders
    // For now, fall back to regular blur with mask
    // Note: Runtime shader support requires Android 13+ (API 33+)
    val blur = createBlurRenderEffect(
        blurRadiusPx,
        RenderEffectParams(
            blurRadius = blurRadiusPx.dp,
            noiseFactor = 0f,
            scale = 1f,
            contentSize = size,
            contentOffset = offset,
            blurTileMode = TileMode.Clamp,
        ),
    )
    return blur.withMask(null, size, offset)
}

private fun Brush.toShader(size: Size): Shader? = when (this) {
    is ShaderBrush -> createShader(size)
    else -> null
}

internal fun BlurVisualEffect.calculateBlurTileMode(): TileMode = when (blurredEdgeTreatment) {
    BlurredEdgeTreatment.Unbounded -> TileMode.Decal
    else -> TileMode.Clamp
}

