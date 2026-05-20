package com.pop.components.utils

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Creates a linear gradient brush with support for custom angles.
 * 
 * This class extends [ShaderBrush] to provide linear gradients that can be rotated
 * to any angle, similar to CSS linear gradients.
 * 
 * ## Usage Examples:
 * 
 * ### Basic horizontal gradient (left to right):
 * ```kotlin
 * Brush.linearGradientAngle(
 *     colors = listOf(Color.Red, Color.Blue),
 *     angleInDegrees = 90f, // 90° = right
 *     useAsCssAngle = true
 * )
 * ```
 * 
 * ### Vertical gradient (top to bottom):
 * ```kotlin
 * Brush.linearGradientAngle(
 *     colors = listOf(Color.Red, Color.Blue),
 *     angleInDegrees = 180f, // 180° = down
 *     useAsCssAngle = true
 * )
 * ```
 * 
 * ### Diagonal gradient (top-left to bottom-right):
 * ```kotlin
 * Brush.linearGradientAngle(
 *     colors = listOf(Color.Red, Color.Blue),
 *     angleInDegrees = 135f, // 135° = diagonal
 *     useAsCssAngle = true
 * )
 * ```
 * 
 * ### With custom color stops:
 * ```kotlin
 * Brush.linearGradientAngle(
 *     colors = listOf(Color.Red, Color.Yellow, Color.Blue),
 *     stops = listOf(0.0f, 0.5f, 1.0f), // Red at 0%, Yellow at 50%, Blue at 100%
 *     angleInDegrees = 45f,
 *     useAsCssAngle = true
 * )
 * ```
 * 
 * ### Using color-stop pairs:
 * ```kotlin
 * Brush.linearGradientAngle(
 *     0.0f to Color.Red,    // Red at 0%
 *     0.5f to Color.Yellow, // Yellow at 50%
 *     1.0f to Color.Blue,   // Blue at 100%
 *     angleInDegrees = 90f,
 *     useAsCssAngle = true
 * )
 * ```
 * 
 * @param colors List of colors to use in the gradient. Must have at least 2 colors.
 * @param stops Optional list of stop positions (0.0 to 1.0) for each color.
 *              If null, colors are evenly distributed.
 *              Must have the same size as [colors] if provided.
 * @param tileMode How to handle areas outside the gradient bounds.
 *                 - [TileMode.Clamp]: Repeat edge colors (default)
 *                 - [TileMode.Repeated]: Repeat the gradient pattern
 *                 - [TileMode.Mirror]: Mirror the gradient pattern
 * @param angleInDegrees The angle of the gradient in degrees.
 *                       - When [useAsCssAngle] = true: 0° = up, 90° = right, 180° = down, 270° = left
 *                       - When [useAsCssAngle] = false: Uses cartesian coordinates (0° = right, 90° = up)
 * @param useAsCssAngle If true, uses CSS-style angle (0° = up, clockwise).
 *                       If false, uses cartesian angle (0° = right, counter-clockwise).
 *                       Default: false (cartesian)
 */
@Immutable
class LinearGradient(
    private val colors: List<Color>,
    private val stops: List<Float>? = null,
    private val tileMode: TileMode = TileMode.Clamp,
    angleInDegrees: Float = 0f,
    useAsCssAngle: Boolean = false
) : ShaderBrush() {

    private val normalizedAngle: Float = if (useAsCssAngle) {
        ((90 - angleInDegrees) % 360 + 360) % 360
    } else {
        (angleInDegrees % 360 + 360) % 360
    }
    private val angleInRadians: Float = Math.toRadians(normalizedAngle.toDouble()).toFloat()

    override fun createShader(size: Size): Shader {
        val (from, to) = getGradientCoordinates(size = size)

        return LinearGradientShader(
            colors = colors,
            colorStops = stops,
            from = from,
            to = to,
            tileMode = tileMode
        )
    }

    private fun getGradientCoordinates(size: Size): Pair<Offset, Offset> {
        val diagonal = sqrt(size.width.pow(2) + size.height.pow(2))
        val angleBetweenDiagonalAndWidth = acos(size.width / diagonal)
        val angleBetweenDiagonalAndGradientLine =
            if ((normalizedAngle > 90 && normalizedAngle < 180) ||
                (normalizedAngle > 270 && normalizedAngle < 360)
            ) {
                PI.toFloat() - angleInRadians - angleBetweenDiagonalAndWidth
            } else {
                angleInRadians - angleBetweenDiagonalAndWidth
            }
        val halfGradientLine = abs(cos(angleBetweenDiagonalAndGradientLine) * diagonal) / 2

        val horizontalOffset = halfGradientLine * cos(angleInRadians)
        val verticalOffset = halfGradientLine * sin(angleInRadians)

        val start = size.center + Offset(-horizontalOffset, verticalOffset)
        val end = size.center + Offset(horizontalOffset, -verticalOffset)

        return start to end
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LinearGradient) return false

        if (colors != other.colors) return false
        if (stops != other.stops) return false
        if (normalizedAngle != other.normalizedAngle) return false
        if (tileMode != other.tileMode) return false

        return true
    }

    override fun hashCode(): Int {
        var result = colors.hashCode()
        result = 31 * result + (stops?.hashCode() ?: 0)
        result = 31 * result + normalizedAngle.hashCode()
        result = 31 * result + tileMode.hashCode()
        return result
    }

    override fun toString(): String {
        return "LinearGradient(colors=$colors, stops=$stops, angle=$normalizedAngle, tileMode=$tileMode)"
    }
}

/**
 * Creates a linear gradient brush with custom angle support.
 * 
 * ## Angle Reference (when useAsCssAngle = true):
 * - **0°** = Up (top to bottom, but starting from top)
 * - **90°** = Right (left to right)
 * - **180°** = Down (top to bottom)
 * - **270°** = Left (right to left)
 * - **45°** = Diagonal (top-left to bottom-right)
 * - **135°** = Diagonal (top-right to bottom-left)
 * 
 * ## Common Use Cases:
 * 
 * ### Top to Bottom gradient:
 * ```kotlin
 * Brush.linearGradientAngle(
 *     colors = listOf(Color(0xFFFFFFFF), Color(0xFF999999)),
 *     angleInDegrees = 180f,
 *     useAsCssAngle = true
 * )
 * ```
 * 
 * ### Left to Right gradient:
 * ```kotlin
 * Brush.linearGradientAngle(
 *     colors = listOf(Color(0xFFFFFFFF), Color(0xFF999999)),
 *     angleInDegrees = 90f,
 *     useAsCssAngle = true
 * )
 * ```
 * 
 * ### Diagonal gradient (top-left to bottom-right):
 * ```kotlin
 * Brush.linearGradientAngle(
 *     colors = listOf(Color(0xFFFFFFFF), Color(0xFF999999)),
 *     angleInDegrees = 135f,
 *     useAsCssAngle = true
 * )
 * ```
 * 
 * @param colors List of colors for the gradient. Must have at least 2 colors.
 *               Colors are evenly distributed if [stops] is null.
 * @param stops Optional list of stop positions (0.0 to 1.0) for each color.
 *              - 0.0f = start of gradient
 *              - 1.0f = end of gradient
 *              - If null, colors are evenly spaced
 *              - Must match the size of [colors] if provided
 * @param tileMode How to handle areas outside gradient bounds:
 *                 - [TileMode.Clamp]: Extend edge colors (default, most common)
 *                 - [TileMode.Repeated]: Repeat the gradient pattern
 *                 - [TileMode.Mirror]: Mirror the gradient pattern
 * @param angleInDegrees Rotation angle in degrees.
 *                       - When [useAsCssAngle] = true: CSS-style (0° = up, clockwise)
 *                       - When [useAsCssAngle] = false: Cartesian (0° = right, counter-clockwise)
 * @param useAsCssAngle If true, angle follows CSS convention (0° = up, 90° = right).
 *                       If false, uses cartesian coordinates (0° = right, 90° = up).
 *                       **Recommended: true** for most design system use cases.
 * @return A [Brush] that can be used with [Modifier.background] or [Modifier.border]
 */
@Stable
fun Brush.Companion.linearGradientAngle(
    colors: List<Color>,
    stops: List<Float>? = null,
    tileMode: TileMode = TileMode.Clamp,
    angleInDegrees: Float = 0f,
    useAsCssAngle: Boolean = false
): Brush = LinearGradient(
    colors = colors,
    stops = stops,
    tileMode = tileMode,
    angleInDegrees = angleInDegrees,
    useAsCssAngle = useAsCssAngle,
)

/**
 * Creates a linear gradient brush with custom angle support using color-stop pairs.
 * 
 * This overload allows you to specify colors and their positions as pairs,
 * which can be more readable for complex gradients.
 * 
 * ## Usage Example:
 * ```kotlin
 * Brush.linearGradientAngle(
 *     0.0f to Color.Red,      // Red at 0% (start)
 *     0.3f to Color.Orange,   // Orange at 30%
 *     0.7f to Color.Yellow,   // Yellow at 70%
 *     1.0f to Color.Green,     // Green at 100% (end)
 *     angleInDegrees = 90f,   // Left to right
 *     useAsCssAngle = true
 * )
 * ```
 * 
 * ## Output:
 * - Creates a horizontal gradient (left to right) with:
 *   - Red at the left edge (0%)
 *   - Orange at 30% of the width
 *   - Yellow at 70% of the width
 *   - Green at the right edge (100%)
 * 
 * @param colorStops Variable number of pairs where:
 *                   - First value (Float): Stop position from 0.0f to 1.0f
 *                   - Second value (Color): Color at that position
 *                   - Must have at least 2 pairs
 *                   - Stop positions should generally be in ascending order
 * @param tileMode How to handle areas outside gradient bounds:
 *                 - [TileMode.Clamp]: Extend edge colors (default)
 *                 - [TileMode.Repeated]: Repeat the gradient pattern
 *                 - [TileMode.Mirror]: Mirror the gradient pattern
 * @param angleInDegrees Rotation angle in degrees.
 *                       - When [useAsCssAngle] = true: CSS-style (0° = up, clockwise)
 *                       - When [useAsCssAngle] = false: Cartesian (0° = right, counter-clockwise)
 * @param useAsCssAngle If true, angle follows CSS convention (0° = up, 90° = right).
 *                       If false, uses cartesian coordinates (0° = right, 90° = up).
 *                       **Recommended: true** for most design system use cases.
 * @return A [Brush] that can be used with [Modifier.background] or [Modifier.border]
 */
@Stable
fun Brush.Companion.linearGradientAngle(
    vararg colorStops: Pair<Float, Color>,
    tileMode: TileMode = TileMode.Clamp,
    angleInDegrees: Float = 0f,
    useAsCssAngle: Boolean = false
): Brush = LinearGradient(
    colors = List(colorStops.size) { i -> colorStops[i].second },
    stops = List(colorStops.size) { i -> colorStops[i].first },
    tileMode = tileMode,
    angleInDegrees = angleInDegrees,
    useAsCssAngle = useAsCssAngle,
)

