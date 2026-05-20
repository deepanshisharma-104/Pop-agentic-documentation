package com.pop.components.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.node.DelegatingNode

data class FlashColor(
    val surface: SurfaceColors,
    val text: TextColors
)

data class FlashDimensions(
    val radius: Radius,
    val height: Height,
    val padding: Padding,
    val stroke: Stroke,
    val gap: Gap,
    val grid: FlashGrid
)

val LocalFlashColors = staticCompositionLocalOf {
    FlashColor(
        surface = SurfaceColors,
        text = TextColors
    )
}

val LocalFlashGrid = staticCompositionLocalOf { FlashGrid }

val LocalFlashDimensions = staticCompositionLocalOf {
    FlashDimensions(
        radius = Radius,
        height = Height,
        padding = Padding,
        stroke = Stroke,
        gap = Gap,
        grid = FlashGrid
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = PopColors.Orange.O9,
    secondary = PopColors.Neutral.N12,
    tertiary = PopColors.Misc.OnTop.Default,
    background = SurfaceColor.Primary,
    surface = SurfaceColor.Primary,
    error = PopColors.Red.R9,
    onPrimary = PopColors.Neutral.N1,
    onSecondary = PopColors.Neutral.N1,
    onTertiary = PopColors.Neutral.N12,
    onBackground = PopColors.Neutral.N12,
    onSurface = PopColors.Neutral.N12,
    onError = PopColors.Neutral.N1
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PopTheme(
    content: @Composable () -> Unit
) {
    val colors = FlashColor(
        surface = SurfaceColors,
        text = TextColors
    )

    val dimensions = FlashDimensions(
        radius = Radius,
        height = Height,
        padding = Padding,
        stroke = Stroke,
        gap = Gap,
        grid = FlashGrid
    )

    CompositionLocalProvider(
        LocalFlashColors provides colors,
        LocalFlashDimensions provides dimensions,
        LocalFlashGrid provides FlashGrid,
        LocalFlashTypography provides FlashTypography,
        LocalOverscrollConfiguration provides null,
        // Provide LocalIndication BEFORE MaterialTheme so it's available when Material3 initializes
        LocalIndication provides NoRippleIndicationNodeFactory
    ) {
        MaterialTheme(
            colorScheme = DarkColorScheme,
            typography = MaterialTypography,
            content = {
                // Also provide LocalIndication AFTER MaterialTheme to override Material3's default
                CompositionLocalProvider(
                    LocalIndication provides NoRippleIndicationNodeFactory
                ) {
                    content()
                }
            }
        )
    }
}

object FlashTheme {
    val colors: FlashColor
        @Composable
        get() = LocalFlashColors.current

    val dimensions: FlashDimensions
        @Composable
        get() = LocalFlashDimensions.current

    val grid: FlashGrid
        @Composable
        get() = LocalFlashGrid.current

    val type: FlashTypography
        @Composable
        get() = LocalFlashTypography.current
}

/**
 * No-op IndicationNodeFactory that provides no ripple effect.
 * Used to replace PlatformRipple (old Indication API) with IndicationNodeFactory (new API).
 */
object NoRippleIndicationNodeFactory : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatingNode {
        return object : DelegatingNode() {}
    }

    override fun hashCode(): Int = -1
    override fun equals(other: Any?) = other === this
} 