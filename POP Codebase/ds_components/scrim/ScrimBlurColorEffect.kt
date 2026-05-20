// Copyright 2024, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library

package com.pop.components.ds_components.scrim

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified

/**
 * Describes a color effect applied by the blur effect.
 */
@Stable
sealed interface ScrimColorEffect {
    /**
     * The blend mode to use when applying the effect.
     */
    val blendMode: BlendMode

    /**
     * Whether this effect is specified (not [Unspecified]).
     */
    val isSpecified: Boolean

    /**
     * A color filter effect.
     */
    @Immutable
    data class ColorFilter(
        val colorFilter: androidx.compose.ui.graphics.ColorFilter,
        override val blendMode: BlendMode = DefaultBlendMode,
    ) : ScrimColorEffect {
        override val isSpecified: Boolean get() = true
    }

    /**
     * A color-based tint effect.
     */
    @Immutable
    data class TintColor(
        val color: Color,
        override val blendMode: BlendMode = DefaultBlendMode,
    ) : ScrimColorEffect {
        override val isSpecified: Boolean get() = color.isSpecified
    }

    /**
     * A brush-based tint effect.
     */
    @Immutable
    data class TintBrush(
        val brush: Brush,
        override val blendMode: BlendMode = DefaultBlendMode,
    ) : ScrimColorEffect {
        override val isSpecified: Boolean = true
    }

    /**
     * An unspecified color effect. When used, no effect will be applied.
     */
    object Unspecified : ScrimColorEffect {
        override val blendMode: BlendMode = BlendMode.SrcOver
        override val isSpecified: Boolean = false
    }

    @Suppress("NOTHING_TO_INLINE")
    companion object {
        /**
         * Default blend mode for effects.
         */
        val DefaultBlendMode: BlendMode = BlendMode.SrcOver

        /**
         * Creates a color filter effect.
         */
        inline fun colorFilter(
            colorFilter: androidx.compose.ui.graphics.ColorFilter,
            blendMode: BlendMode = DefaultBlendMode,
        ): ScrimColorEffect = ColorFilter(colorFilter, blendMode)

        /**
         * Creates a color-based tint effect.
         */
        inline fun tint(
            color: Color,
            blendMode: BlendMode = DefaultBlendMode,
        ): ScrimColorEffect = TintColor(color, blendMode)

        /**
         * Creates a brush-based tint effect.
         */
        inline fun tint(
            brush: Brush,
            blendMode: BlendMode = DefaultBlendMode,
        ): ScrimColorEffect = TintBrush(brush, blendMode)
    }
}

internal inline fun Float.takeOrElse(block: () -> Float): Float =
    if (this in 0f..1f) this else block()

