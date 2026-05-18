package com.pop.components.ds_components

import androidx.annotation.DrawableRes
import com.pop.compose_components.R

/**
 * Gradient-only scrim styles for top bars and sticky headers.
 *
 * All gradient types are top-to-bottom and based on #0D0D0D.
 */
sealed class PopTopBarScrimType {
    /**
     * 0% -> 40%: 100% alpha
     * 40% -> 100%: 100% to 0% alpha
     */
    object TopGradient100 : PopTopBarScrimType()

    /**
     * 0% -> 40%: 80% alpha
     * 40% -> 100%: 80% to 0% alpha
     */
    object TopGradient80 : PopTopBarScrimType()

    /**
     * Solid #0D0D0D with 80% alpha (no gradient).
     */
    object Solid90 : PopTopBarScrimType()
}

@DrawableRes
fun PopTopBarScrimType.drawableResId(): Int {
    return when (this) {
        PopTopBarScrimType.TopGradient100 -> R.drawable.top_gradient_100_100_0
        PopTopBarScrimType.TopGradient80 -> R.drawable.top_80_to_0_gradient
        PopTopBarScrimType.Solid90 -> R.drawable.top95_percent_block_background
    }
}

