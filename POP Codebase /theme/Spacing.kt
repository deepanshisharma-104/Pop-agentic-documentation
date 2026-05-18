package com.pop.components.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class FlashSpacing {
    val scale000: Dp = 0.dp
    val scale050: Dp = 1.dp
    val scale100: Dp = 2.dp
    val scale200: Dp = 4.dp
    val scale300: Dp = 8.dp
    val scale350: Dp = 10.dp
    val scale400: Dp = 16.dp
    val scale450: Dp = 18.dp
    val scale500: Dp = 24.dp
    val scale600: Dp = 32.dp
    val scale650: Dp = 36.dp
    val scale700: Dp = 44.dp
    val scale800: Dp = 56.dp
    val scale900: Dp = 64.dp
    val scale950: Dp = 86.dp
}

object FlashThemeSpacing {
    private val spacing = FlashSpacing()

    val scale000: Dp = spacing.scale000
    val scale050: Dp = spacing.scale050
    val scale100: Dp = spacing.scale100
    val scale200: Dp = spacing.scale200
    val scale300: Dp = spacing.scale300
    val scale350: Dp = spacing.scale350
    val scale400: Dp = spacing.scale400
    val scale450: Dp = spacing.scale450
    val scale500: Dp = spacing.scale500
    val scale600: Dp = spacing.scale600
    val scale650: Dp = spacing.scale650
    val scale700: Dp = spacing.scale700
    val scale800: Dp = spacing.scale800
    val scale900: Dp = spacing.scale900
    val scale950: Dp = spacing.scale950
}

val LocalFlashSpacing = staticCompositionLocalOf { FlashThemeSpacing }

object Radius {
    val ExtraSmall: Dp = FlashThemeSpacing.scale200
    val Small: Dp = FlashThemeSpacing.scale350
    val Default: Dp = FlashThemeSpacing.scale400
    val Full: Dp = FlashThemeSpacing.scale600
    val Rounding10: Dp = 10.dp
    val Rounding16: Dp = 16.dp
    val Rounding24: Dp = 24.dp
    val Rounding30: Dp = 30.dp
}

object Height {
    val ExtraSmall: Dp = FlashThemeSpacing.scale600
    val Small: Dp = FlashThemeSpacing.scale650
    val Medium: Dp = FlashThemeSpacing.scale700
    val Large: Dp = FlashThemeSpacing.scale800
    val ExtraLarge: Dp = FlashThemeSpacing.scale950
    val IconMedium: Dp = FlashThemeSpacing.scale450
    val IconSmall: Dp = FlashThemeSpacing.scale800
    val H32: Dp = 32.dp
    val H36: Dp = 36.dp
    val H44: Dp = 44.dp
    val H56: Dp = 56.dp
}

object Padding {
    val None: Dp = FlashThemeSpacing.scale000
    val Default: Dp = FlashThemeSpacing.scale400
    val Large: Dp = FlashThemeSpacing.scale600
}

object Stroke {
    val WeightLarge: Dp = FlashThemeSpacing.scale100
    val WeightMedium: Dp = FlashThemeSpacing.scale050
}

object Gap {
    val None: Dp = FlashThemeSpacing.scale000
    val ExtraExtraSmall: Dp = FlashThemeSpacing.scale100
    val ExtraSmall: Dp = FlashThemeSpacing.scale200
    val Small: Dp = FlashThemeSpacing.scale300
    val Medium: Dp = FlashThemeSpacing.scale400
    val Large: Dp = FlashThemeSpacing.scale600
}

object Spacing {
    val Scale0: Dp = 0.dp
    val Scale2: Dp = 2.dp
    val Scale4: Dp = 4.dp
    val Scale8: Dp = 8.dp
    val Scale16: Dp = 16.dp
    val Scale24: Dp = 24.dp
    val Scale32: Dp = 32.dp
    val Scale64: Dp = 64.dp
} 