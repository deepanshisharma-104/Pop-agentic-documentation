package com.pop.components.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * PopDot size variants based on design system specs.
 */
enum class PopDotSize {
    /** Larger dot size */
    Large,

    /** Medium dot size */
    Med
}

/**
 * Convert PopDotSize to dp.
 *
 * Defaults align with typical indicator dots; update if Figma specifies
 * different dimensions for this variant.
 */
fun PopDotSize.toDotDp(): Dp = when (this) {
    PopDotSize.Large -> 8.dp
    PopDotSize.Med -> 6.dp
}

