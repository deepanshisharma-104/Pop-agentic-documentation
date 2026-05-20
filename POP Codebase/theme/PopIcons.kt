package com.pop.components.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Design System Icons based on Figma specifications.
 * This provides standardized icon sizes and utilities for the icon system.
 *
 * Icons are available in two styles:
 * - Outline: Thin-lined, monochrome icons
 * - Filled: Solid, filled icons
 *
 * Standard icon size: 24x24dp
 */
object PopIcons {
    // Standard icon sizes
    val sizeSmall: Dp = 16.dp      // 16x16dp
    val sizeMedium: Dp = 24.dp     // 24x24dp (standard)
    val sizeLarge: Dp = 32.dp      // 32x32dp
    val sizeXLarge: Dp = 48.dp     // 48x48dp

    // Icon button sizes (for interactive icon buttons)
    val buttonSizeSmall: Dp = 32.dp   // 32x32dp
    val buttonSizeMedium: Dp = 40.dp  // 40x40dp
    val buttonSizeLarge: Dp = 48.dp   // 48x48dp

    /**
     * Standard icon size used throughout the design system
     */
    val defaultSize: Dp = sizeMedium
}

/**
 * Icon style variants available in the design system
 */
enum class IconStyle {
    /** Outline style: Thin-lined, monochrome icons */
    Outline,

    /** Filled style: Solid, filled icons */
    Filled
}

/**
 * Icon size presets for consistent usage
 */
enum class IconSize {
    Small,      // 16dp
    Medium,     // 24dp (default)
    Large,      // 32dp
    XLarge      // 48dp
}

/**
 * Extension function to get Dp value from IconSize enum
 */
fun IconSize.toDp(): Dp = when (this) {
    IconSize.Small -> PopIcons.sizeSmall
    IconSize.Medium -> PopIcons.sizeMedium
    IconSize.Large -> PopIcons.sizeLarge
    IconSize.XLarge -> PopIcons.sizeXLarge
}

data class PopIconConfig(
    val iconName: IconName,
    val style: IconStyle,
    val size: IconSize,
    val tint: Color,
    val contentDescription: String? = null,
)

