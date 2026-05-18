package com.pop.components.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Pop Design System - Corner Radius & Stroke Tokens
 *
 * Standardized corner radius and stroke values matching Figma specifications.
 * All values use 100% corner smoothing (iOS-style continuous curves).
 *
 * Based on: [Figma Design System](https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=29-78)
 */

// ============================================================================
// Spacing Tokens
// ============================================================================

/**
 * Pop Design System - Spacing Tokens
 *
 * Standardized spacing values matching Figma specifications.
 * Use these tokens for consistent spacing throughout the application.
 */
object PopSpacing {
    /**
     * 0dp - No spacing
     */
    val Spacing0: Dp = 0.dp

    /**
     * 2dp
     */
    val Spacing2: Dp = 2.dp

    /**
     * 4dp
     */
    val Spacing4: Dp = 4.dp

    /**
     * 6dp
     */
    val Spacing6: Dp = 6.dp

    /**
     * 8dp
     */
    val Spacing8: Dp = 8.dp

    /**
     * 10dp
     */
    val Spacing10: Dp = 10.dp

    /**
     * 12dp
     */
    val Spacing12: Dp = 12.dp

    /**
     * 16dp
     */
    val Spacing16: Dp = 16.dp

    /**
     * 20dp
     */
    val Spacing20: Dp = 20.dp

    /**
     * 24dp
     */
    val Spacing24: Dp = 24.dp

    /**
     * 28dp
     */
    val Spacing28: Dp = 28.dp

    /**
     * 32dp
     */
    val Spacing32: Dp = 32.dp

    /**
     * 36dp
     */
    val Spacing36: Dp = 36.dp

    /**
     * 44dp
     */
    val Spacing44: Dp = 44.dp

    /**
     * 48dp
     */
    val Spacing48: Dp = 48.dp

    /**
     * 56dp
     */
    val Spacing56: Dp = 56.dp

    /**
     * 120dp
     */
    val Spacing120: Dp = 120.dp
}

// ============================================================================
// Corner Radius Tokens
// ============================================================================

/**
 * Pop Design System - Corner Radius Tokens
 *
 * Standardized corner radius values matching Figma specifications.
 * All values use 100% corner smoothing (iOS-style continuous curves).
 */
object PopRadius {
    /**
     * 0px - Sharp corners, no rounding
     *
     * Use for:
     - Rectangular components that should have sharp edges
     - Elements that need to align perfectly with other sharp edges
     */
    val None: Dp = 0.dp

    /**
     * 4px - Extra-small rounding
     *
     * Use for:
     - Minimal rounding for subtle softening
     - Small badges or tags
     - Tiny UI elements
     */
    val ExtraSmall: Dp = 4.dp

    /**
     * 8px - Small rounding
     *
     * Use for:
     - Small components like chips
     - Compact buttons
     - Small cards or containers
     */
    val Small: Dp = 8.dp

    /**
     * 12px - Medium rounding (standard)
     *
     * Use for:
     - Standard buttons
     - Input fields
     - Standard cards
     - Most common component radius
     */
    val Medium: Dp = 12.dp

    /**
     * 16px - Large rounding
     *
     * Use for:
     - Large cards
     - Bottom sheets (top corners)
     - Prominent containers
     - Modals and dialogs
     */
    val Large: Dp = 16.dp

    /**
     * 20px - Extra-large rounding
     *
     * Use for:
     - Extra large components
     - Prominent surfaces
     - Special containers
     */
    val XLarge: Dp = 20.dp

    /**
     * 999px - Fully rounded (pill/capsule shape)
     *
     * Use for:
     - Pill-shaped buttons
     - Rounded tabs
     - Fully rounded containers
     - Circular-like shapes that aren't perfect circles
     *
     * Note: In Compose, you can also use `RoundedCornerShape(percent = 50)`
     * for pill shapes, which is equivalent to this value.
     */
    val XLLarge: Dp = 999.dp
}

// ============================================================================
// Stroke Tokens
// ============================================================================

/**
 * Pop Design System - Stroke Tokens
 *
 * Standardized stroke width values matching Figma specifications.
 */
object PopStroke {
    /**
     * 0.5px - Standard stroke width
     *
     * This is the standard stroke width for all borders, dividers, and outlines
     * in the Pop Design System.
     *
     * Use for:
     - Component borders
     - Input field outlines
     - Card borders
     - Divider lines
     - All stroke-based UI elements
     *
     * Note: 0.5dp is the Android equivalent of 0.5px in Figma.
     * This provides a subtle, refined border appearance.
     */
    val Default: Dp = 0.5.dp
}

// ============================================================================
// Shape Utilities
// ============================================================================

/**
 * Pop Design System - Shape Utilities
 *
 * Utility functions for creating shapes using Pop design tokens.
 * These helpers make it easy to apply standardized corner radii and strokes.
 */
object PopShapes {
    /**
     * Creates a [RoundedCornerShape] with the specified Pop radius value.
     *
     * @param radius The Pop radius token to use
     * @return A RoundedCornerShape with the specified radius
     *
     * Example:
     * ```
     * Button(
     *     onClick = { },
     *     shape = PopShapes.rounded(PopRadius.Medium)
     * )
     * ```
     */
    fun rounded(radius: Dp): Shape = RoundedCornerShape(radius)

    /**
     * Creates a [RoundedCornerShape] with different radii for each corner.
     *
     * @param topStart Top-start corner radius
     * @param topEnd Top-end corner radius
     * @param bottomStart Bottom-start corner radius
     * @param bottomEnd Bottom-end corner radius
     * @return A RoundedCornerShape with the specified corner radii
     *
     * Example:
     * ```
     * BottomSheet(
     *     shape = PopShapes.rounded(
     *         topStart = PopRadius.Large,
     *         topEnd = PopRadius.Large,
     *         bottomStart = PopRadius.None,
     *         bottomEnd = PopRadius.None
     *     )
     * )
     * ```
     */
    fun rounded(
        topStart: Dp = PopRadius.None,
        topEnd: Dp = PopRadius.None,
        bottomStart: Dp = PopRadius.None,
        bottomEnd: Dp = PopRadius.None
    ): Shape = RoundedCornerShape(
        topStart = topStart,
        topEnd = topEnd,
        bottomStart = bottomStart,
        bottomEnd = bottomEnd
    )

    /**
     * Creates a pill/capsule shape (fully rounded).
     *
     * This is equivalent to using [PopRadius.XLLarge] but uses
     * Compose's percentage-based rounding for better performance.
     *
     * @return A RoundedCornerShape with 50% rounding (pill shape)
     *
     * Example:
     * ```
     * Button(
     *     onClick = { },
     *     shape = PopShapes.pill()
     * )
     * ```
     */
    fun pill(): Shape = RoundedCornerShape(percent = 50)

    /**
     * Creates a shape with only top corners rounded.
     * Useful for bottom sheets, cards at the top of lists, etc.
     *
     * @param radius The radius to apply to top corners
     * @return A RoundedCornerShape with rounded top corners only
     *
     * Example:
     * ```
     * BottomSheet(
     *     shape = PopShapes.topRounded(PopRadius.Large)
     * )
     * ```
     */
    fun topRounded(radius: Dp = PopRadius.Large): Shape = RoundedCornerShape(
        topStart = radius,
        topEnd = radius,
        bottomStart = PopRadius.None,
        bottomEnd = PopRadius.None
    )

    /**
     * Creates a shape with only bottom corners rounded.
     * Useful for cards at the bottom of lists, etc.
     *
     * @param radius The radius to apply to bottom corners
     * @return A RoundedCornerShape with rounded bottom corners only
     *
     * Example:
     * ```
     * Card(
     *     shape = PopShapes.bottomRounded(PopRadius.Large)
     * )
     * ```
     */
    fun bottomRounded(radius: Dp = PopRadius.Large): Shape = RoundedCornerShape(
        topStart = PopRadius.None,
        topEnd = PopRadius.None,
        bottomStart = radius,
        bottomEnd = radius
    )

    /**
     * Creates a shape with only left corners rounded.
     *
     * @param radius The radius to apply to left corners
     * @return A RoundedCornerShape with rounded left corners only
     */
    fun leftRounded(radius: Dp = PopRadius.Medium): Shape = RoundedCornerShape(
        topStart = radius,
        topEnd = PopRadius.None,
        bottomStart = radius,
        bottomEnd = PopRadius.None
    )

    /**
     * Creates a shape with only right corners rounded.
     *
     * @param radius The radius to apply to right corners
     * @return A RoundedCornerShape with rounded right corners only
     */
    fun rightRounded(radius: Dp = PopRadius.Medium): Shape = RoundedCornerShape(
        topStart = PopRadius.None,
        topEnd = radius,
        bottomStart = PopRadius.None,
        bottomEnd = radius
    )
}

// ============================================================================
// Modifier Extensions
// ============================================================================

/**
 * Adds a border using Pop design tokens.
 *
 * @param color The color of the border
 * @param width The stroke width (defaults to PopStroke.Default)
 * @param shape The shape of the border (defaults to no rounding)
 * @return Modifier with border applied
 *
 * Example:
 * ```
 * Box(
 *     modifier = Modifier
 *         .popBorder(PopColors.Neutral.N5)
 * )
 * ```
 */
fun Modifier.popBorder(
    color: Color,
    width: Dp = PopStroke.Default,
    shape: Shape = RoundedCornerShape(0.dp)
): Modifier = this.border(
    width = width,
    color = color,
    shape = shape
)

/**
 * Adds a border with a specific corner radius using Pop radius tokens.
 *
 * @param color The color of the border
 * @param radius The Pop radius value to use for the border shape
 * @param width The stroke width (defaults to PopStroke.Default)
 * @return Modifier with border applied
 *
 * Example:
 * ```
 * Box(
 *     modifier = Modifier
 *         .popBorder(PopColors.Neutral.N5, PopRadius.Medium)
 * )
 * ```
 */
fun Modifier.popBorder(
    color: Color,
    radius: Dp,
    width: Dp = PopStroke.Default
): Modifier = this.border(
    width = width,
    color = color,
    shape = RoundedCornerShape(radius)
)

/**
 * Adds a border with a pill/capsule shape.
 *
 * @param color The color of the border
 * @param width The stroke width (defaults to PopStroke.Default)
 * @return Modifier with pill-shaped border applied
 *
 * Example:
 * ```
 * Button(
 *     onClick = { },
 *     modifier = Modifier.popBorderPill(PopColors.Neutral.N5)
 * )
 * ```
 */
fun Modifier.popBorderPill(
    color: Color,
    width: Dp = PopStroke.Default
): Modifier = this.border(
    width = width,
    color = color,
    shape = PopShapes.pill()
)

/**
 * Adds a border with rounded top corners only (useful for bottom sheets).
 *
 * @param color The color of the border
 * @param radius The Pop radius value for top corners (defaults to Large)
 * @param width The stroke width (defaults to PopStroke.Default)
 * @return Modifier with top-rounded border applied
 *
 * Example:
 * ```
 * BottomSheet(
 *     modifier = Modifier.popBorderTopRounded(PopColors.Neutral.N5)
 * )
 * ```
 */
fun Modifier.popBorderTopRounded(
    color: Color,
    radius: Dp = PopRadius.Large,
    width: Dp = PopStroke.Default
): Modifier = this.border(
    width = width,
    color = color,
    shape = PopShapes.topRounded(radius)
)

/**
 * Adds a border with rounded bottom corners only.
 *
 * @param color The color of the border
 * @param radius The Pop radius value for bottom corners (defaults to Large)
 * @param width The stroke width (defaults to PopStroke.Default)
 * @return Modifier with bottom-rounded border applied
 *
 * Example:
 * ```
 * Card(
 *     modifier = Modifier.popBorderBottomRounded(PopColors.Neutral.N5)
 * )
 * ```
 */
fun Modifier.popBorderBottomRounded(
    color: Color,
    radius: Dp = PopRadius.Large,
    width: Dp = PopStroke.Default
): Modifier = this.border(
    width = width,
    color = color,
    shape = PopShapes.bottomRounded(radius)
)

