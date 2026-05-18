// Copyright 2025, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library - Android only

package com.pop.components.ds_components.scrim

import android.os.Build
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Default values for the blur visual effect.
 */
@Suppress("ktlint:standard:property-naming")
internal object ScrimBlurDefaults {
    /**
     * Default blur radius.
     */
    val blurRadius: Dp = 20.dp

    /**
     * Noise factor.
     */
    const val noiseFactor: Float = 0.15f

    /**
     * Default alpha used for the tint color.
     */
    const val tintAlpha: Float = 0.7f

    /**
     * Default value for blurred edge treatment.
     */
    val blurredEdgeTreatment: BlurredEdgeTreatment =
        BlurredEdgeTreatment.Rectangle

    /**
     * Default builder for the 'tint' color. Transforms the provided [color].
     */
    fun tint(color: Color): ScrimColorEffect = ScrimColorEffect.tint(
        color = when {
            color.isSpecified -> color.copy(alpha = color.alpha * tintAlpha)
            else -> color
        },
    )

    /**
     * Default values for blurEnabled. This function only returns `true` on
     * platforms where we know blurring works reliably.
     */
    fun blurEnabled(): Boolean = isBlurEnabledByDefault()
}

/**
 * Android-specific: Returns whether blur is enabled by default.
 * On Android, blur is enabled on API 31+ (Android 12+) using RenderEffect.
 */
internal fun isBlurEnabledByDefault(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}

