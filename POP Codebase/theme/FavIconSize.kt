package com.pop.components.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Fav icon size variants available in the design system.
 * Based on Figma design specifications.
 */
enum class FavIconSize {
    /** Large size: 22dp icon */
    Large,
    
    /** Medium size: 18dp icon */
    Med
}

/**
 * Extension function to get icon Dp value from FavIconSize enum
 */
fun FavIconSize.toIconDp(): Dp = when (this) {
    FavIconSize.Large -> 22.dp
    FavIconSize.Med -> 18.dp
}

