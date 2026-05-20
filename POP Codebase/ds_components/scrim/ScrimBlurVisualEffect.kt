// Copyright 2025, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library - Android only

@file:OptIn(InternalScrimApi::class)

package com.pop.components.ds_components.scrim

import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.takeOrElse

/**
 * A [VisualEffect] implementation that applies blur effects to content.
 *
 * This effect supports various blur-related features including:
 * - Configurable blur radius
 * - Progressive (gradient) blur
 * - Background color
 * - Alpha masking
 */
@ExperimentalScrimApi
class BlurVisualEffect : VisualEffect {

    @OptIn(InternalScrimApi::class)
    internal var dirtyTracker: Bitmask by mutableStateOf(Bitmask())
        private set

    internal var delegate: Delegate = ScrimBlurVisualEffectDelegate(this)
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "delegate changed. Current $field. New: $value" }
                // attach new delegate
                value.attach()
                // detach old delegate
                field.detach()
                field = value
            }
        }

    @OptIn(InternalScrimApi::class)
    override fun update(context: VisualEffectContext) {
        if (dirtyTracker.any(BlurDirtyFields.InvalidateFlags)) {
            context.invalidateDraw()
        }
    }

    override fun DrawScope.draw(context: VisualEffectContext) {
        updateDelegate(context, this)

        try {
            with(delegate) { draw(context) }
        } finally {
            resetDirtyTracker()
        }
    }

    override fun DrawScope.shouldDrawContentBehind(context: VisualEffectContext): Boolean {
        updateDelegate(context, this)
        return delegate is ScrimBlurVisualEffectDelegate
    }

    override fun shouldClip(): Boolean = blurredEdgeTreatment.shape != null

    @OptIn(InternalScrimApi::class)
    private fun resetDirtyTracker() {
        dirtyTracker = Bitmask()
    }

    private var _blurEnabled: Boolean? = null

    /**
     * Whether the blur effect is enabled or not.
     *
     * When set to `false` a scrim effect will be used. When set to `true`, and running on a platform
     * which does not support blurring, a scrim effect will be used.
     *
     * Defaults to [ScrimBlurDefaults.blurEnabled].
     */
    var blurEnabled: Boolean
        get() = _blurEnabled ?: ScrimBlurDefaults.blurEnabled()
        set(value) {
            if (_blurEnabled == null || value != _blurEnabled) {
                ScrimLogger.d(TAG) { "blurEnabled changed. Current: $_blurEnabled. New: $value" }
                _blurEnabled = value
                dirtyTracker += BlurDirtyFields.BlurEnabled
            }
        }

    /**
     * Radius of the blur.
     */
    var blurRadius: Dp = Dp.Unspecified
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "blurRadius changed. Current: $field. New: $value" }
                field = value
                dirtyTracker += BlurDirtyFields.BlurRadius
            }
        }

    /**
     * Amount of noise applied to the content, in the range `0f` to `1f`.
     */
    var noiseFactor: Float = -1f
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "noiseFactor changed. Current: $field. New: $value" }
                field = value
                dirtyTracker += BlurDirtyFields.NoiseFactor
            }
        }

    /**
     * Optional alpha mask which allows effects such as fading via a
     * [Brush.verticalGradient] or similar. This is only applied when [progressive] is null.
     */
    var mask: Brush? = null
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "mask changed. Current: $field. New: $value" }
                field = value
                dirtyTracker += BlurDirtyFields.Mask
            }
        }

    /**
     * Color to draw behind the blurred content. Ideally should be opaque
     * so that the original content is not visible behind.
     */
    var backgroundColor: Color = Color.Unspecified
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "backgroundColor changed. Current: $field. New: $value" }
                field = value
                dirtyTracker += BlurDirtyFields.BackgroundColor
            }
        }

    /**
     * The color effects to apply to the blurred content.
     */
    var colorEffects: List<ScrimColorEffect> = emptyList()
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "colorEffects changed. Current: $field. New: $value" }
                field = value
                dirtyTracker += BlurDirtyFields.ColorEffects
            }
        }

    /**
     * The color effect to use when using the fallback scrim functionality.
     */
    var fallbackTint: ScrimColorEffect = ScrimColorEffect.Unspecified
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "fallbackTint changed. Current: $field. New: $value" }
                field = value
                dirtyTracker += BlurDirtyFields.FallbackColorEffect
            }
        }

    /**
     * The opacity that the overall effect will drawn with, in the range of 0..1.
     */
    var alpha: Float = 1f
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "alpha changed. Current: $field. New: $value" }
                field = value
                dirtyTracker += BlurDirtyFields.Alpha
            }
        }

    /**
     * Parameters for enabling a progressive (or gradient) blur effect, or null for a uniform
     * blurring effect. Defaults to null.
     */
    var progressive: ScrimProgressive? = null
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "progressive changed. Current: $field. New: $value" }
                field = value
                dirtyTracker += BlurDirtyFields.Progressive
            }
        }

    /**
     * The [BlurredEdgeTreatment] to use when blurring content.
     */
    var blurredEdgeTreatment: BlurredEdgeTreatment = ScrimBlurDefaults.blurredEdgeTreatment
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "blurredEdgeTreatment changed. Current: $field. New: $value" }
                field = value
                dirtyTracker += BlurDirtyFields.BlurredEdgeTreatment
            }
        }

    override fun calculateInputScaleFactor(scale: ScrimInputScale): Float = when (scale) {
        is ScrimInputScale.None -> 1f
        is ScrimInputScale.Fixed -> scale.scale
        ScrimInputScale.Auto -> {
            val blurRadius = blurRadius.takeOrElse { 0.dp }
            when {
                // For small blurRadius values, input scaling is very noticeable therefore we turn it off
                blurRadius < 7.dp -> 1f
                // For progressive and masks, we need to keep enough resolution for the lowest intensity.
                // 0.5f is about right.
                progressive != null -> 0.5f
                mask != null -> 0.5f
                // Otherwise we use 1/3
                else -> 0.3334f
            }
        }
    }

    override fun requireInvalidation(): Boolean = dirtyTracker.any(BlurDirtyFields.InvalidateFlags)

    override fun preferClipToAreaBounds(): Boolean {
        return backgroundColor.isSpecified && backgroundColor.alpha < 0.9f
    }

    override fun calculateLayerBounds(rect: Rect, density: Density): Rect {
        val blurRadiusPx = with(density) {
            blurRadius.takeOrElse { 0.dp }.toPx()
        }
        return when {
            blurRadiusPx >= 1f -> rect.inflate(blurRadiusPx)
            else -> rect
        }
    }

    internal interface Delegate {
        fun attach() = Unit
        fun DrawScope.draw(context: VisualEffectContext)
        fun detach() = Unit
    }


    internal companion object {
        const val TAG = "BlurVisualEffect"
    }
}

/**
 * Android-specific: Updates the blur delegate based on platform capabilities.
 * Uses RenderEffect on Android 12+ (API 31+), falls back to scrim otherwise.
 */
@OptIn(ExperimentalScrimApi::class)
internal fun BlurVisualEffect.updateDelegate(context: VisualEffectContext, drawScope: DrawScope) {
    val canUseRenderEffect = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
        drawScope.drawContext.canvas.nativeCanvas.isHardwareAccelerated

    val blurEnabled = this.blurEnabled

    if (blurEnabled && canUseRenderEffect) {
        // Use actual blur rendering on Android 12+
        if (delegate !is RenderEffectBlurVisualEffectDelegate) {
            delegate = RenderEffectBlurVisualEffectDelegate(this)
        }
        return
    }

    // Fallback to scrim for older Android versions or when blur is disabled
    if (delegate !is ScrimBlurVisualEffectDelegate) {
        delegate = ScrimBlurVisualEffectDelegate(this)
    }
}

