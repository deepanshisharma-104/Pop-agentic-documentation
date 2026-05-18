// Copyright 2025, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library

package com.pop.components.ds_components.scrim

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import kotlin.jvm.JvmInline

/**
 * Parameters for applying a progressive blur effect.
 */
@Immutable
sealed interface ScrimProgressive {
    /**
     * A linear gradient effect.
     *
     * @param easing The easing function to use when applying the effect. Defaults to a
     * linear easing effect.
     * @param start Starting position of the gradient. Defaults to [Offset.Zero] which
     * represents the top-left of the drawing area.
     * @param startIntensity The intensity of the blur effect at the start, in the range `0f`..`1f`.
     * @param end Ending position of the gradient. Defaults to [Offset.Infinite] which
     * represents the bottom-right of the drawing area.
     * @param endIntensity The intensity of the blur effect at the end, in the range `0f`..`1f`
     * @param preferPerformance Whether to prefer performance (when true), or quality (when false).
     * When true, may fall back to mask-based implementation on some platforms.
     */
    data class LinearGradient(
        val easing: Easing = EaseIn,
        val start: Offset = Offset.Zero,
        val startIntensity: Float = 0f,
        val end: Offset = Offset.Infinite,
        val endIntensity: Float = 1f,
        val preferPerformance: Boolean = false,
    ) : ScrimProgressive

    /**
     * A radial gradient effect.
     *
     * @param easing The easing function to use when applying the effect. Defaults to a
     * linear easing effect.
     * @param center Center position of the radial gradient circle. If this is set to
     * [Offset.Unspecified] then the center of the drawing area is used as the center for
     * the radial gradient.
     * @param centerIntensity The intensity of the blur effect at the [center], in the range `0f`..`1f`.
     * @param radius Radius for the radial gradient. Defaults to positive infinity to indicate
     * the largest radius that can fit within the bounds of the drawing area.
     * @param radiusIntensity The intensity of the blur effect at the [radius], in the range `0f`..`1f`
     */
    data class RadialGradient(
        val easing: Easing = EaseIn,
        val center: Offset = Offset.Unspecified,
        val centerIntensity: Float = 1f,
        val radius: Float = Float.POSITIVE_INFINITY,
        val radiusIntensity: Float = 0f,
    ) : ScrimProgressive

    /**
     * A progressive effect which is derived by using the provided [Brush] as an alpha mask.
     *
     * This allows custom effects driven from a brush. The RGB values from the brush's pixels will
     * be ignored, only the alpha values are used.
     */
    @JvmInline
    value class Brush(val brush: androidx.compose.ui.graphics.Brush) : ScrimProgressive

    companion object {
        /**
         * A vertical gradient effect.
         *
         * @param easing The easing function to use when applying the effect. Defaults to a
         * linear easing effect.
         * @param startY Starting Y position of the vertical gradient. Defaults to 0 which
         * represents the top of the drawing area.
         * @param startIntensity The intensity of the blur effect at the start, in the range `0f`..`1f`.
         * @param endY Ending Y position of the vertical gradient. Defaults to
         * [Float.POSITIVE_INFINITY] which represents the bottom of the drawing area.
         * @param endIntensity The intensity of the blur effect at the end, in the range `0f`..`1f`.
         * @param preferPerformance Whether to prefer performance (when true), or quality (when false).
         */
        fun verticalGradient(
            easing: Easing = EaseIn,
            startY: Float = 0f,
            startIntensity: Float = 0f,
            endY: Float = Float.POSITIVE_INFINITY,
            endIntensity: Float = 1f,
            preferPerformance: Boolean = false,
        ): LinearGradient = LinearGradient(
            easing = easing,
            start = Offset(0f, startY),
            startIntensity = startIntensity,
            end = Offset(0f, endY),
            endIntensity = endIntensity,
            preferPerformance = preferPerformance,
        )

        /**
         * A horizontal gradient effect.
         *
         * @param easing The easing function to use when applying the effect. Defaults to a
         * linear easing effect.
         * @param startX Starting X position of the horizontal gradient. Defaults to 0 which
         * represents the left of the drawing area
         * @param startIntensity The intensity of the blur effect at the start, in the range `0f`..`1f`
         * @param endX Ending X position of the horizontal gradient. Defaults to
         * [Float.POSITIVE_INFINITY] which represents the right of the drawing area.
         * @param endIntensity The intensity of the blur effect at the end, in the range `0f`..`1f`.
         * @param preferPerformance Whether to prefer performance (when true), or quality (when false).
         */
        fun horizontalGradient(
            easing: Easing = EaseIn,
            startX: Float = 0f,
            startIntensity: Float = 0f,
            endX: Float = Float.POSITIVE_INFINITY,
            endIntensity: Float = 1f,
            preferPerformance: Boolean = false,
        ): LinearGradient = LinearGradient(
            easing = easing,
            start = Offset(startX, 0f),
            startIntensity = startIntensity,
            end = Offset(endX, 0f),
            endIntensity = endIntensity,
            preferPerformance = preferPerformance,
        )

        /**
         * Helper function for building a [ScrimProgressive.Brush] with a [Shader]. The block is
         * provided with the size of the content, allowing you to setup the shader as required.
         */
        inline fun forShader(
            crossinline block: (Size) -> Shader,
        ): Brush = Brush(
            object : ShaderBrush() {
                override fun createShader(size: Size): Shader = block(size)
            },
        )
    }
}

