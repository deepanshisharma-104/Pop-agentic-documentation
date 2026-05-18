@file:OptIn(InternalScrimApi::class)

package com.pop.components.ds_components.scrim

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// This file uses the standalone scrim infrastructure defined in the other files in this directory.

/**
 * Returns a modifier that applies a basic blur effect to the composable.
 *
 * This modifier blurs content from elsewhere in the UI that's marked with
 * `scrimSource(state = scrimState)`.
 *
 * Example usage:
 * ```
 * val scrimState = rememberScrimState()
 *
 * TopAppBar(
 *     modifier = blurModifier(
 *         scrimState = scrimState,
 *         blurRadius = 20.dp,
 *         backgroundColor = MaterialTheme.colorScheme.surface
 *     )
 * )
 * ```
 *
 * @param scrimState The [ScrimState] instance shared with the source content
 * @param blurRadius The radius of the blur effect. Larger values produce a stronger blur. Defaults to 20dp.
 * @param backgroundColor Color to draw behind the blurred content. Should be opaque so the original
 * content is not visible. Defaults to transparent (no background color).
 * @return A modifier that applies the blur effect
 */
@ExperimentalScrimApi
fun Modifier.blurModifier(
    scrimState: ScrimState,
    blurRadius: Dp = 20.dp,
    backgroundColor: Color = Color.Unspecified,
): Modifier = scrimEffect(state = scrimState) {
    blurEffect {
        this.blurRadius = blurRadius
        this.backgroundColor = backgroundColor
    }
}

/**
 * Returns a modifier that applies a blur effect to content inside a scrimSource.
 *
 * Unlike [blurModifier], this modifier bypasses the ancestor filtering logic that
 * normally prevents a blur effect from capturing content from its ancestor scrimSource.
 * This is specifically designed for use cases like sticky headers that need to blur
 * content from their containing scrimSource.
 *
 * **When to use this:**
 * - When applying blur to a composable that is a CHILD of a scrimSource
 * - For sticky headers that need to blur content scrolling behind them
 * - When the regular [blurModifier] doesn't show any blur effect
 *
 * **When NOT to use this:**
 * - For composables that are siblings or above the scrimSource (use [blurModifier] instead)
 * - For PopTopBar or similar overlay components that are not children of scrimSource
 *
 * Example usage:
 * ```
 * val scrimState = rememberScrimState()
 *
 * Box(modifier = Modifier.scrimSource(state = scrimState)) {
 *     LazyColumn {
 *         stickyHeader {
 *             Box(
 *                 modifier = Modifier.blurModifierInsideSource(
 *                     scrimState = scrimState,
 *                     blurRadius = 60.dp,
 *                     backgroundColor = SurfaceColor.Primary
 *                 )
 *             ) {
 *                 // Sticky header content
 *             }
 *         }
 *         items(list) { /* ... */ }
 *     }
 * }
 * ```
 *
 * @param scrimState The [ScrimState] instance shared with the source content
 * @param blurRadius The radius of the blur effect. Larger values produce a stronger blur. Defaults to 20dp.
 * @param backgroundColor Color to draw behind the blurred content. Should be opaque so the original
 * content is not visible. Defaults to transparent (no background color).
 * @return A modifier that applies the blur effect
 */
@ExperimentalScrimApi
fun Modifier.blurModifierInsideSource(
    scrimState: ScrimState,
    blurRadius: Dp = 20.dp,
    backgroundColor: Color = Color.Unspecified,
): Modifier = scrimEffect(state = scrimState) {
    // Bypass ancestor filtering - include all areas from the ScrimState
    // EXCEPT areas that are currently drawing (to avoid circular dependency)
    // This allows child composables to blur content from their ancestor scrimSource
    canDrawArea = { area -> !area.isContentDrawing }
    blurEffect {
        this.blurRadius = blurRadius
        this.backgroundColor = backgroundColor
    }
}

/**
 * Returns a modifier that applies a blur effect with input scaling for performance optimization.
 *
 * Input scaling renders the effect at a lower resolution before scaling it back up,
 * which can improve performance at the cost of some quality.
 *
 * Example usage:
 * ```
 * val scrimState = rememberScrimState()
 *
 * TopAppBar(
 *     modifier = blurModifierWithInputScale(
 *         scrimState = scrimState,
 *         blurRadius = 20.dp,
 *         backgroundColor = MaterialTheme.colorScheme.surface
 *     )
 * )
 * ```
 *
 * @param scrimState The [ScrimState] instance shared with the source content
 * @param blurRadius The radius of the blur effect. Larger values produce a stronger blur. Defaults to 20dp.
 * @param backgroundColor Color to draw behind the blurred content. Should be opaque so the original
 * content is not visible. Defaults to transparent (no background color).
 * @param inputScale The input scale configuration. Defaults to [ScrimInputScale.Auto] which
 * automatically determines the optimal scale factor based on blur radius and other settings.
 * @return A modifier that applies the blur effect with input scaling
 */
@ExperimentalScrimApi
fun Modifier.blurModifierWithInputScale(
    scrimState: ScrimState,
    blurRadius: Dp = 20.dp,
    backgroundColor: Color = Color.Unspecified,
    inputScale: ScrimInputScale = ScrimInputScale.Auto,
): Modifier = scrimEffect(state = scrimState) {
    this.inputScale = inputScale
    blurEffect {
        this.blurRadius = blurRadius
        this.backgroundColor = backgroundColor
    }
}

/**
 * Returns a modifier that applies a progressive (gradient) blur effect.
 *
 * Progressive blur creates a gradient blur effect where the blur intensity varies across the composable.
 * This is useful for creating smooth fade effects (e.g., blurring content behind a top app bar).
 *
 * Example usage:
 * ```
 * val scrimState = rememberScrimState()
 *
 * TopAppBar(
 *     modifier = progressiveBlurModifier(
 *         scrimState = scrimState,
 *         startIntensity = 1f,
 *         endIntensity = 0f,
 *         blurRadius = 20.dp
 *     )
 * )
 * ```
 *
 * @param scrimState The [ScrimState] instance shared with the source content
 * @param startIntensity The intensity of the blur at the start position (top by default),
 * in the range `0f`..`1f`. Defaults to 1f (full blur).
 * @param endIntensity The intensity of the blur at the end position (bottom by default),
 * in the range `0f`..`1f`. Defaults to 0f (no blur).
 * @param blurRadius The radius of the blur effect. Larger values produce a stronger blur. Defaults to 20dp.
 * @param backgroundColor Color to draw behind the blurred content. Should be opaque so the original
 * content is not visible. Defaults to transparent (no background color).
 * @return A modifier that applies the progressive blur effect
 */
@ExperimentalScrimApi
fun Modifier.progressiveBlurModifier(
    scrimState: ScrimState,
    startIntensity: Float = 1f,
    endIntensity: Float = 0f,
    blurRadius: Dp = 20.dp,
    backgroundColor: Color = Color.Unspecified,
): Modifier = scrimEffect(state = scrimState) {
    blurEffect {
        this.blurRadius = blurRadius
        this.backgroundColor = backgroundColor
        this.progressive = ScrimProgressive.Companion.verticalGradient(
            startIntensity = startIntensity,
            endIntensity = endIntensity,
        )
    }
}

/**
 * Returns a modifier that applies a progressive blur effect with input scaling.
 *
 * This combines progressive blur (gradient blur intensity) with input scaling for performance optimization.
 *
 * Example usage:
 * ```
 * val scrimState = rememberScrimState()
 *
 * TopAppBar(
 *     modifier = progressiveBlurModifierWithInputScale(
 *         scrimState = scrimState,
 *         startIntensity = 1f,
 *         endIntensity = 0f,
 *         blurRadius = 20.dp
 *     )
 * )
 * ```
 *
 * @param scrimState The [ScrimState] instance shared with the source content
 * @param startIntensity The intensity of the blur at the start position (top by default),
 * in the range `0f`..`1f`. Defaults to 1f (full blur).
 * @param endIntensity The intensity of the blur at the end position (bottom by default),
 * in the range `0f`..`1f`. Defaults to 0f (no blur).
 * @param blurRadius The radius of the blur effect. Larger values produce a stronger blur. Defaults to 20dp.
 * @param backgroundColor Color to draw behind the blurred content. Should be opaque so the original
 * content is not visible. Defaults to transparent (no background color).
 * @param inputScale The input scale configuration. Defaults to [ScrimInputScale.Auto] which
 * automatically determines the optimal scale factor based on blur radius and other settings.
 * @return A modifier that applies the progressive blur effect with input scaling
 */
@ExperimentalScrimApi
fun Modifier.progressiveBlurModifierWithInputScale(
    scrimState: ScrimState,
    startIntensity: Float = 1f,
    endIntensity: Float = 0f,
    blurRadius: Dp = 20.dp,
    backgroundColor: Color = Color.Unspecified,
    inputScale: ScrimInputScale = ScrimInputScale.Auto,
): Modifier = scrimEffect(state = scrimState) {
    this.inputScale = inputScale
    blurEffect {
        this.blurRadius = blurRadius
        this.backgroundColor = backgroundColor
        this.progressive = ScrimProgressive.verticalGradient(
            startIntensity = startIntensity,
            endIntensity = endIntensity,
        )
    }
}

/**
 * Returns a modifier that applies a masked blur effect.
 *
 * Masked blur uses a [Brush] as an alpha mask to fade the blur effect. This is a more performant
 * alternative to progressive blur, but may provide a less refined visual result.
 *
 * Example usage:
 * ```
 * val scrimState = rememberScrimState()
 *
 * TopAppBar(
 *     modifier = maskedBlurModifier(
 *         scrimState = scrimState,
 *         mask = Brush.verticalGradient(
 *             colors = listOf(Color.Black, Color.Transparent)
 *         ),
 *         blurRadius = 20.dp
 *     )
 * )
 * ```
 *
 * @param scrimState The [ScrimState] instance shared with the source content
 * @param mask The brush used as an alpha mask for the blur effect. The alpha values of the brush
 * determine the opacity of the blur. Defaults to a vertical gradient from opaque to transparent.
 * @param blurRadius The radius of the blur effect. Larger values produce a stronger blur. Defaults to 20dp.
 * @param backgroundColor Color to draw behind the blurred content. Should be opaque so the original
 * content is not visible. Defaults to transparent (no background color).
 * @return A modifier that applies the masked blur effect
 */
@ExperimentalScrimApi
fun Modifier.maskedBlurModifier(
    scrimState: ScrimState,
    mask: Brush = Brush.verticalGradient(
        colors = listOf(Color.Black, Color.Transparent),
    ),
    blurRadius: Dp = 20.dp,
    backgroundColor: Color = Color.Unspecified,
): Modifier = scrimEffect(state = scrimState) {
    blurEffect {
        this.blurRadius = blurRadius
        this.backgroundColor = backgroundColor
        this.mask = mask
    }
}

/**
 * Returns a modifier that applies a masked blur effect with input scaling.
 *
 * This combines masked blur (using a brush as an alpha mask) with input scaling for performance optimization.
 *
 * Example usage:
 * ```
 * val scrimState = rememberScrimState()
 *
 * TopAppBar(
 *     modifier = maskedBlurModifierWithInputScale(
 *         scrimState = scrimState,
 *         mask = Brush.verticalGradient(
 *             colors = listOf(Color.Black, Color.Transparent)
 *         ),
 *         blurRadius = 20.dp
 *     )
 * )
 * ```
 *
 * @param scrimState The [ScrimState] instance shared with the source content
 * @param mask The brush used as an alpha mask for the blur effect. The alpha values of the brush
 * determine the opacity of the blur. Defaults to a vertical gradient from opaque to transparent.
 * @param blurRadius The radius of the blur effect. Larger values produce a stronger blur. Defaults to 20dp.
 * @param backgroundColor Color to draw behind the blurred content. Should be opaque so the original
 * content is not visible. Defaults to transparent (no background color).
 * @param inputScale The input scale configuration. Defaults to [ScrimInputScale.Auto] which
 * automatically determines the optimal scale factor based on blur radius and other settings.
 * @return A modifier that applies the masked blur effect with input scaling
 */
@ExperimentalScrimApi
fun Modifier.maskedBlurModifierWithInputScale(
    scrimState: ScrimState,
    mask: Brush = Brush.verticalGradient(
        colors = listOf(Color.Black, Color.Transparent),
    ),
    blurRadius: Dp = 20.dp,
    backgroundColor: Color = Color.Unspecified,
    inputScale: ScrimInputScale = ScrimInputScale.Auto,
): Modifier = scrimEffect(state = scrimState) {
    this.inputScale = inputScale
    blurEffect {
        this.blurRadius = blurRadius
        this.backgroundColor = backgroundColor
        this.mask = mask
    }
}

