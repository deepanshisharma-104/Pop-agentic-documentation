package com.pop.components.utils

import android.graphics.BlurMaskFilter
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Matrix
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pop.components.theme.BorderColor
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopRadius
import com.pop.components.theme.SurfaceColor
import com.pop.components.ds_components.SquircleShape
import android.graphics.LinearGradient
import android.graphics.Path as AndroidPath
import android.graphics.RadialGradient
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.hypot

/**
 * Extension to set background using PopGradient.
 * Handles Solid, Linear (with angle), and Radial gradients.
 */
@Stable
fun Modifier.popBackground(
    gradient: PopGradient?,
    shape: Shape = RectangleShape
): Modifier {
    if (gradient == null) return this

    return when (gradient) {
        is PopGradient.Solid -> this.background(color = gradient.color, shape = shape)
        is PopGradient.Linear -> this.background(brush = gradient.toBrush(), shape = shape)
        is PopGradient.Radial -> this.background(brush = gradient.toBrush(), shape = shape)
    }
}

/**
 * Extension to set border using PopGradient.
 */
@Stable
fun Modifier.popBorder(
    width: Dp,
    gradient: PopGradient?,
    shape: Shape = RectangleShape
): Modifier {
    if (gradient == null) return this

    return when (gradient) {
        is PopGradient.Solid -> this.border(width = width, color = gradient.color, shape = shape)
        is PopGradient.Linear -> this.border(width = width, brush = gradient.toBrush(), shape = shape)
        is PopGradient.Radial -> this.border(width = width, brush = gradient.toBrush(), shape = shape)
    }
}

/**
 * Helper to convert PopGradient to Brush.
 * Returns null for Solid (handled separately usually, or wrap in SolidColor brush if needed).
 * But here we return Brush for Linear/Radial and handle Solid separately in modifiers to prefer Color over Brush (slightly more performant).
 */
@Stable
fun PopGradient.toBrush(): Brush {
    return when (this) {
        is PopGradient.Solid -> androidx.compose.ui.graphics.SolidColor(this.color)
        is PopGradient.Linear -> Brush.linearGradientAngle(
            colors = this.colors,
            stops = this.stops,
            angleInDegrees = this.angleInDegrees,
            useAsCssAngle = this.useAsCssAngle,
            tileMode = this.tileMode
        )

        is PopGradient.Radial -> {
            // Capture the radial gradient instance to use inside the anonymous object
            val radialGradient = this
            object : ShaderBrush() {
                override fun createShader(size: Size): Shader {
                    val geometry = radialGradient.resolveGeometry(size)
                    if (kotlin.math.abs(geometry.radiusX - geometry.radiusY) < 0.001f) {
                        return RadialGradientShader(
                            colors = radialGradient.colors,
                            colorStops = radialGradient.stops,
                            center = geometry.center,
                            radius = geometry.radiusX,
                            tileMode = radialGradient.tileMode
                        )
                    }

                    val androidShader = RadialGradient(
                        0f,
                        0f,
                        1f,
                        radialGradient.colors.map { it.toArgb() }.toIntArray(),
                        radialGradient.colorStopsArray(),
                        radialGradient.toAndroidTileMode()
                    )
                    val matrix = Matrix().apply {
                        setScale(geometry.radiusX, geometry.radiusY)
                        postTranslate(geometry.center.x, geometry.center.y)
                    }
                    androidShader.setLocalMatrix(matrix)
                    return androidShader
                }
            }
        }
    }
}

private data class RadialGeometry(
    val center: Offset,
    val radiusX: Float,
    val radiusY: Float
)

private fun PopGradient.Radial.resolveGeometry(size: Size): RadialGeometry {
    val center = resolveCenter(size)
    val baseRadius = resolveBaseRadius(size)

    var radiusX = baseRadius * radiusMultiplierX
    var radiusY = baseRadius * radiusMultiplierY

    if (constrainRadiusToBoundsX) {
        val maxXFromCenter = boundDistanceFromCenter(
            centerCoordinate = center.x,
            totalSize = size.width,
            mode = constrainRadiusBoundsModeX
        )
        radiusX = radiusX.coerceAtMost(maxXFromCenter)
    }
    if (constrainRadiusToBoundsY) {
        val maxYFromCenter = boundDistanceFromCenter(
            centerCoordinate = center.y,
            totalSize = size.height,
            mode = constrainRadiusBoundsModeY
        )
        radiusY = radiusY.coerceAtMost(maxYFromCenter)
    }

    return RadialGeometry(
        center = center,
        radiusX = radiusX.coerceAtLeast(0.001f),
        radiusY = radiusY.coerceAtLeast(0.001f)
    )
}

private fun boundDistanceFromCenter(
    centerCoordinate: Float,
    totalSize: Float,
    mode: PopGradient.RadiusBoundsMode
): Float {
    val distanceToStart = centerCoordinate.coerceAtLeast(0f)
    val distanceToEnd = (totalSize - centerCoordinate).coerceAtLeast(0f)
    return when (mode) {
        PopGradient.RadiusBoundsMode.NearestEdge -> minOf(distanceToStart, distanceToEnd)
        PopGradient.RadiusBoundsMode.FarthestEdge -> maxOf(distanceToStart, distanceToEnd)
    }
}

private fun PopGradient.Radial.resolveCenter(size: Size): Offset {
    return when {
        alignment != null -> {
            when (alignment) {
                Alignment.TopStart -> Offset(size.width * 0.3f, size.height * 0.3f)
                Alignment.TopCenter -> Offset(size.width / 2f, 0f)
                Alignment.TopEnd -> Offset(size.width * 0.7f, size.height * 0.3f)
                Alignment.CenterStart -> Offset(size.width * 0.3f, size.height / 2f)
                Alignment.Center -> Offset(size.width / 2f, size.height / 2f)
                Alignment.CenterEnd -> Offset(size.width * 0.7f, size.height / 2f)
                Alignment.BottomStart -> Offset(size.width * 0.3f, size.height * 0.7f)
                Alignment.BottomCenter -> Offset(size.width / 2f, size.height * 0.7f)
                Alignment.BottomEnd -> Offset(size.width * 0.7f, size.height * 0.7f)
                else -> Offset(size.width / 2f, size.height / 2f)
            }
        }

        center != Offset.Unspecified -> center
        colors.size == 3 &&
            colors[0] == Color(0xFF26D526) &&
            colors[1] == Color(0xFF059505) &&
            colors[2] == Color(0xFF015001) -> {
            Offset(size.width * 0.5014f, size.height * (-0.2857f))
        }
        colors.size == 3 &&
            colors[0] == Color(0xFFF27373) &&
            colors[1] == Color(0xFFE22626) &&
            colors[2] == Color(0xFF8A0F0F) -> {
            Offset(size.width * 0.5014f, size.height * (-0.2857f))
        }
        colors.size == 2 &&
            colors[0] == Color(0xFF1D1D1D) &&
            colors[1] == Color(0xFF121212) &&
            (center.x.isNaN() || center.y.isNaN()) -> {
            Offset(size.width * 0.3035f, size.height * 0.2105f)
        }

        else -> Offset(size.width / 2f, size.height / 2f)
    }
}

private fun PopGradient.Radial.resolveBaseRadius(size: Size): Float {
    if (radius != Float.POSITIVE_INFINITY) return radius

    return when {
        colors.size == 2 &&
            colors[0] == Color(0xFF1D1D1D) &&
            colors[1] == Color(0xFF121212) &&
            (center.x.isNaN() || center.y.isNaN()) -> {
            kotlin.math.max(size.width, size.height) * 1.0417f / 2f
        }
        (colors.size == 3 && colors[0] == Color(0xFF26D526)) ||
            (colors.size == 3 && colors[0] == Color(0xFFF27373)) -> {
            size.height * 1.5982f / 2f
        }
        else -> kotlin.math.max(size.width, size.height) / 2f * radiusMultiplier
    }
}

private fun PopGradient.Radial.colorStopsArray(): FloatArray? {
    return stops?.toFloatArray() ?: colors.indices
        .map { it / (colors.size - 1).coerceAtLeast(1).toFloat() }
        .toFloatArray()
}

private fun PopGradient.Radial.toAndroidTileMode(): android.graphics.Shader.TileMode {
    return when (tileMode) {
        androidx.compose.ui.graphics.TileMode.Clamp -> android.graphics.Shader.TileMode.CLAMP
        androidx.compose.ui.graphics.TileMode.Repeated -> android.graphics.Shader.TileMode.REPEAT
        androidx.compose.ui.graphics.TileMode.Mirror -> android.graphics.Shader.TileMode.MIRROR
        else -> android.graphics.Shader.TileMode.CLAMP
    }
}

/**
 * Applies a horizontal gradient fade effect to the edges of a composable.
 *
 * This modifier creates a gradient mask that fades the content at the left and/or right edges.
 * It uses offscreen compositing to support BlendMode.DstIn for the gradient mask.
 *
 * @param gradientWidth The width of the gradient fade at the edges
 * @param shouldShowLeftGradient Whether to show the fade on the left edge
 * @param shouldShowRightGradient Whether to show the fade on the right edge
 * @param leftGradientAlpha The alpha value for the left gradient (0f to 1f).
 *                          Used for smooth fade-in animations. When 1f, the gradient is fully visible.
 *                          When 0f, the left edge is fully opaque (no gradient).
 *
 * @return A Modifier that applies the gradient fade effect
 *
 * @sample
 * ```
 * Box(
 *     modifier = Modifier
 *         .fillMaxWidth()
 *         .horizontalGradientFade(
 *             gradientWidth = 56.dp,
 *             shouldShowLeftGradient = true,
 *             shouldShowRightGradient = true,
 *             leftGradientAlpha = 1f
 *         )
 * ) {
 *     Text("Content with gradient fade")
 * }
 * ```
 */
fun Modifier.horizontalGradientFade(
    gradientWidth: Dp,
    shouldShowLeftGradient: Boolean,
    shouldShowRightGradient: Boolean,
    leftGradientAlpha: Float = 1f
): Modifier {
    return this
        .graphicsLayer {
            // Use offscreen compositing to support BlendMode.DstIn for the gradient mask
            compositingStrategy = CompositingStrategy.Offscreen
        }
        .drawWithContent {
            drawContent()

            val gradientWidthPx = gradientWidth.toPx()
            val width = size.width

            // Calculate gradient stops for the alpha mask
            // Use animated alpha for smooth fade-in of left gradient
            val startAlpha = if (shouldShowLeftGradient) (1f - leftGradientAlpha) else 1f
            val startStop = if (shouldShowLeftGradient) gradientWidthPx / width else 0f

            val endAlpha = if (shouldShowRightGradient) 0f else 1f
            val endStop = if (shouldShowRightGradient) (width - gradientWidthPx) / width else 1f

            val colorStops = listOf(
                0f to Color.Black.copy(alpha = startAlpha),
                startStop.coerceIn(0f, 1f) to Color.Black,
                endStop.coerceIn(0f, 1f) to Color.Black,
                1f to Color.Black.copy(alpha = endAlpha)
            ).toTypedArray()

            drawRect(
                brush = Brush.horizontalGradient(
                    colorStops = colorStops
                ),
                blendMode = BlendMode.DstIn
            )
        }
}

/**
 * Applies a vertical gradient equivalent to `top_gradient_100_0.xml`, but reversed:
 * 0% alpha at top and 100% alpha at bottom.
 *
 * A subtle noise overlay is added to reduce visible banding at the fade edge.
 */
@Stable
fun Modifier.bottomPrimaryGradientWithDither(
    baseColor: Color = SurfaceColor.Primary
): Modifier {
    return this
        .background(
            brush = Brush.verticalGradient(
                colorStops = arrayOf(
                    0f to baseColor.copy(alpha = 0f),
                    0.5f to baseColor.copy(alpha = 1f),
                    1f to baseColor.copy(alpha = 1f)
                )
            )
        )
        .noiseOverlay(
            config = NoiseConfig(
                intensity = 0.03f,
                scale = 3.0f,
                blendMode = BlendMode.Softlight
            )
        )
}

/**
 * Noise effect configuration for texture overlays
 */
data class NoiseConfig(
    /**
     * Intensity of the noise effect (0.0 to 1.0)
     * 0.0 = no noise, 1.0 = full noise
     */
    val intensity: Float = 0.1f,

    /**
     * Scale of the noise pattern (higher = finer grain)
     */
    val scale: Float = 4.0f,

    /**
     * Blend mode for the noise overlay
     */
    val blendMode: BlendMode = BlendMode.Overlay
)

/**
 * Applies a noise texture overlay to a composable.
 *
 * For Android 13+ (API 33+), uses AGSL RuntimeShader for efficient noise generation.
 * For older versions, uses a canvas-based procedural noise fallback.
 *
 * @param config Noise configuration (intensity, scale, blend mode)
 *
 * Usage:
 * ```kotlin
 * Box(modifier = Modifier.noiseOverlay(NoiseConfig(intensity = 0.1f))) {
 *     // Your content here
 * }
 * ```
 */
fun Modifier.noiseOverlay(
    config: NoiseConfig = NoiseConfig()
): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val noiseBrush = remember(size, config) {
        if (size.width == 0 || size.height == 0) {
            null
        } else {
            generateNoisePattern(Size(size.width.toFloat(), size.height.toFloat()), config)
        }
    }

    this
        .onSizeChanged { size = it }
        .drawWithContent {
            drawContent()

            // Draw noise overlay with specified blend mode
            noiseBrush?.let { brush ->
                drawRect(
                    brush = brush,
                    blendMode = config.blendMode
                )
            }
        }
}

/**
 * Generates a noise pattern using procedural noise
 * Uses a hash-based noise function for consistent, tileable noise
 */
private fun generateNoisePattern(size: Size, config: NoiseConfig): Brush {
    // Helper function to get fractional part
    fun fract(x: Float): Float = x - floor(x)

    // Simple hash function for pseudo-random values
    fun hash(p: Float): Float {
        val h = p * 0.01f
        return fract(sin(h * 12.9898f) * 43758.5453f)
    }

    // 2D noise function using hash
    fun noise(p: Offset): Float {
        val i = Offset(floor(p.x), floor(p.y))
        val f = Offset(fract(p.x), fract(p.y))

        val a = hash(i.x + i.y * 57.0f)
        val b = hash(i.x + 1.0f + i.y * 57.0f)
        val c = hash(i.x + (i.y + 1.0f) * 57.0f)
        val d = hash(i.x + 1.0f + (i.y + 1.0f) * 57.0f)

        val u = f.x * f.x * (3.0f - 2.0f * f.x)
        val v = f.y * f.y * (3.0f - 2.0f * f.y)

        return a + (b - a) * u + (c - a) * v + (a - b - c + d) * u * v
    }

    return object : ShaderBrush() {
        override fun createShader(size: Size): Shader {
            val baseTileSize = (kotlin.math.min(size.width, size.height) / config.scale)
                .roundToInt()
                .coerceIn(8, 64)
            val bitmap = Bitmap.createBitmap(baseTileSize, baseTileSize, Bitmap.Config.ARGB_8888)

            for (y in 0 until baseTileSize) {
                for (x in 0 until baseTileSize) {
                    val n = noise(Offset(x / config.scale, y / config.scale))
                    val alpha = (n * 255f * config.intensity).roundToInt().coerceIn(0, 255)
                    bitmap.setPixel(x, y, android.graphics.Color.argb(alpha, 255, 255, 255))
                }
            }

            return BitmapShader(
                bitmap,
                android.graphics.Shader.TileMode.REPEAT,
                android.graphics.Shader.TileMode.REPEAT
            )
        }
    }
}

/**
 * Predefined noise configurations for common use cases
 */
object NoisePresets {
    /**
     * Subtle noise for buttons and surfaces
     */
    val Subtle = NoiseConfig(
        intensity = 0.05f,
        scale = 4.0f,
        blendMode = BlendMode.Overlay
    )

    /**
     * Medium noise for emphasis
     */
    val Medium = NoiseConfig(
        intensity = 0.1f,
        scale = 4.0f,
        blendMode = BlendMode.Overlay
    )

    /**
     * Strong noise effect
     */
    val Strong = NoiseConfig(
        intensity = 0.2f,
        scale = 4.0f,
        blendMode = BlendMode.Overlay
    )

    /**
     * Brand button noise - visible grain without overpowering
     */
    val BrandButton = NoiseConfig(
        intensity = 0.12f,
        scale = 3.2f,
        blendMode = BlendMode.Overlay
    )
}

/**
 * Converts PopGradient to Android native Shader for use with native Paint.
 *
 * @param gradient The PopGradient to convert
 * @param size The size of the area where the gradient will be drawn
 * @return Android native Shader, or null if gradient is Solid (use color instead)
 */
private fun PopGradient.toNativeShader(size: Size): android.graphics.Shader? {
    return when (this) {
        is PopGradient.Solid -> null // Use color instead
        is PopGradient.Linear -> {
            // Calculate gradient coordinates based on angle
            val angleInRadians = Math.toRadians(
                if (useAsCssAngle) {
                    ((90 - angleInDegrees) % 360 + 360) % 360
                } else {
                    (angleInDegrees % 360 + 360) % 360
                }.toDouble()
            ).toFloat()

            // Calculate start and end points
            val diagonal = kotlin.math.sqrt(size.width * size.width + size.height * size.height)
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val halfGradientLine = diagonal / 2f

            val startX = centerX - halfGradientLine * cos(angleInRadians)
            val startY = centerY + halfGradientLine * sin(angleInRadians)
            val endX = centerX + halfGradientLine * cos(angleInRadians)
            val endY = centerY - halfGradientLine * sin(angleInRadians)

            // Convert colors to int array
            val colorInts = colors.map { it.toArgb() }.toIntArray()

            // Convert stops or create evenly spaced stops
            val colorStops = stops?.toFloatArray() ?: colors.indices.map { it / (colors.size - 1).coerceAtLeast(1).toFloat() }.toFloatArray()

            // Convert TileMode
            val androidTileMode = when (tileMode) {
                androidx.compose.ui.graphics.TileMode.Clamp -> android.graphics.Shader.TileMode.CLAMP
                androidx.compose.ui.graphics.TileMode.Repeated -> android.graphics.Shader.TileMode.REPEAT
                androidx.compose.ui.graphics.TileMode.Mirror -> android.graphics.Shader.TileMode.MIRROR
                else -> android.graphics.Shader.TileMode.CLAMP
            }

            LinearGradient(
                startX, startY,
                endX, endY,
                colorInts,
                colorStops,
                androidTileMode
            )
        }

        is PopGradient.Radial -> {
            val geometry = resolveGeometry(size)
            val androidTileMode = toAndroidTileMode()
            val colorInts = colors.map { it.toArgb() }.toIntArray()
            val colorStops = colorStopsArray()

            if (kotlin.math.abs(geometry.radiusX - geometry.radiusY) < 0.001f) {
                RadialGradient(
                    geometry.center.x,
                    geometry.center.y,
                    geometry.radiusX,
                    colorInts,
                    colorStops,
                    androidTileMode
                )
            } else {
                RadialGradient(
                    0f,
                    0f,
                    1f,
                    colorInts,
                    colorStops,
                    androidTileMode
                ).also { shader ->
                    val matrix = Matrix().apply {
                        setScale(geometry.radiusX, geometry.radiusY)
                        postTranslate(geometry.center.x, geometry.center.y)
                    }
                    shader.setLocalMatrix(matrix)
                }
            }
        }
    }
}

/**
 * Creates an Android Path for a SquircleShape with the given parameters.
 * This recreates the squircle path algorithm directly as an Android Path.
 *
 * @param width The width of the shape
 * @param height The height of the shape
 * @param cornerRadius The corner radius in pixels, or null for full squircle
 * @param smoothing The smoothing factor (0.0 - 1.0)
 * @param offsetX The X offset to apply to the path
 * @param offsetY The Y offset to apply to the path
 * @return An Android Path representing the squircle shape
 */
private fun createSquircleAndroidPath(
    width: Float,
    height: Float,
    cornerRadius: Dp?,
    smoothing: Float,
    density: Density,
    offsetX: Float = 0f,
    offsetY: Float = 0f
): AndroidPath {
    val androidPath = AndroidPath()

    if (width <= 0 || height <= 0) return androidPath

    // Determine effective radius (same logic as SquircleShape)
    val maxRadius = width.coerceAtMost(height) / 2f
    val r = if (cornerRadius != null) {
        with(density) { cornerRadius.toPx() }.coerceAtMost(maxRadius)
    } else {
        maxRadius
    }

    // Squircle algorithm parameters (same as SquircleShape)
    val p = smoothing.coerceIn(0f, 1f)
    val baseExtension = 1.8f
    val baseCpOffset = 0.08f

    val extensionFactor = 1f + (p * (baseExtension - 1f))
    val anchorLen = r * extensionFactor

    val cpOffsetRatio = baseCpOffset + (p * 0.03f)
    val cpOffset = r * cpOffsetRatio

    val safeAnchor = anchorLen.coerceAtMost(width / 2f).coerceAtMost(height / 2f)
    val safeCpOffset = if (safeAnchor < anchorLen && anchorLen > 0f) {
        cpOffset * (safeAnchor / anchorLen)
    } else {
        cpOffset
    }

    val left = offsetX
    val top = offsetY
    val right = offsetX + width
    val bottom = offsetY + height

    // Build the squircle path (same algorithm as SquircleShape)
    androidPath.moveTo(left, top + safeAnchor)

    // Top-Left Corner
    androidPath.cubicTo(
        left, top + safeCpOffset,
        left + safeCpOffset, top,
        left + safeAnchor, top
    )

    // Top edge
    androidPath.lineTo(right - safeAnchor, top)

    // Top-Right Corner
    androidPath.cubicTo(
        right - safeCpOffset, top,
        right, top + safeCpOffset,
        right, top + safeAnchor
    )

    // Right edge
    androidPath.lineTo(right, bottom - safeAnchor)

    // Bottom-Right Corner
    androidPath.cubicTo(
        right, bottom - safeCpOffset,
        right - safeCpOffset, bottom,
        right - safeAnchor, bottom
    )

    // Bottom edge
    androidPath.lineTo(left + safeAnchor, bottom)

    // Bottom-Left Corner
    androidPath.cubicTo(
        left + safeCpOffset, bottom,
        left, bottom - safeCpOffset,
        left, bottom - safeAnchor
    )

    androidPath.close()
    return androidPath
}

/**
 * Applies a glow effect around a composable, similar to CSS box-shadow.
 * Implements: box-shadow: 0 0 32px 2px rgba(255, 255, 255, 0.25)
 *
 * Supports both solid colors and gradients for the glow effect.
 *
 * @param blurRadius The blur radius of the glow effect (default: 32.dp)
 * @param spreadRadius The spread radius of the glow effect (default: 2.dp)
 * @param color The color of the glow effect (default: white with 25% opacity)
 *              Used when [gradient] is null.
 * @param gradient The gradient for the glow effect. If provided, takes precedence over [color].
 * @param shape The shape to apply the glow to
 *
 * @sample
 * ```
 * // Solid color glow
 * Box(
 *     modifier = Modifier
 *         .size(100.dp)
 *         .glowEffect(blurRadius = 32.dp, spreadRadius = 2.dp)
 * ) {
 *     // Content
 * }
 *
 * // Gradient glow
 * Box(
 *     modifier = Modifier
 *         .size(100.dp)
 *         .glowEffect(
 *             blurRadius = 32.dp,
 *             spreadRadius = 2.dp,
 *             gradient = PopGradient.Linear(
 *                 colors = listOf(Color.Red, Color.Blue),
 *                 angleInDegrees = 90f
 *             )
 *         )
 * ) {
 *     // Content
 * }
 * ```
 */
@Stable
fun Modifier.glowEffect(
    blurRadius: Dp = 32.dp,
    spreadRadius: Dp = 2.dp,
    color: Color = Color(0x40FFFFFF), // rgba(255, 255, 255, 0.25) = 25% opacity white
    gradient: PopGradient? = null,
    shape: Shape = RectangleShape,
    blurStyle: BlurMaskFilter.Blur = BlurMaskFilter.Blur.NORMAL,
    enableDitheredFalloff: Boolean = false,
    ditherStrength: Float = 1f,
): Modifier {
    return this
        .graphicsLayer {
            clip = false
        }
        .drawWithContent {
            val blurRadiusPx = blurRadius.toPx()
            val spreadRadiusPx = spreadRadius.toPx()

            // Calculate the glow bounds (larger than content to account for spread)
            val glowWidth = size.width + spreadRadiusPx * 2
            val glowHeight = size.height + spreadRadiusPx * 2
            val glowOffsetX = -spreadRadiusPx
            val glowOffsetY = -spreadRadiusPx
            val glowSize = Size(glowWidth, glowHeight)

            // Draw the glow effect using native canvas with blur mask filter
            drawIntoCanvas { canvas ->
                val paint = android.graphics.Paint().apply {
                    maskFilter = BlurMaskFilter(
                        blurRadiusPx,
                        blurStyle
                    )
                    isAntiAlias = true
                    style = android.graphics.Paint.Style.FILL
                }

                // Apply color or gradient to paint
                if (gradient != null) {
                    val nativeShader = gradient.toNativeShader(glowSize)
                    if (nativeShader != null) {
                        paint.shader = nativeShader
                    } else {
                        // Fall back to solid color for PopGradient.Solid
                        paint.color = when (gradient) {
                            is PopGradient.Solid -> gradient.color.toArgb()
                            else -> color.toArgb()
                        }
                    }
                } else {
                    // Use solid color
                    paint.color = color.toArgb()
                }

                // Get the shape outline for the glow size
                val glowOutline = shape.createOutline(
                    size = glowSize,
                    layoutDirection = layoutDirection,
                    density = this@drawWithContent
                )

                // Draw the shape with glow using native canvas
                when (glowOutline) {
                    is androidx.compose.ui.graphics.Outline.Rounded -> {
                        val cornerRadius = glowOutline.roundRect.topLeftCornerRadius.x
                        val rect = android.graphics.RectF(
                            glowOffsetX,
                            glowOffsetY,
                            glowOffsetX + glowWidth,
                            glowOffsetY + glowHeight
                        )
                        canvas.nativeCanvas.drawRoundRect(
                            rect,
                            cornerRadius,
                            cornerRadius,
                            paint
                        )
                    }

                    is androidx.compose.ui.graphics.Outline.Rectangle -> {
                        val rect = android.graphics.RectF(
                            glowOffsetX,
                            glowOffsetY,
                            glowOffsetX + glowWidth,
                            glowOffsetY + glowHeight
                        )
                        canvas.nativeCanvas.drawRect(rect, paint)
                    }

                    is androidx.compose.ui.graphics.Outline.Generic -> {
                        // Check if the shape is a SquircleShape
                        if (shape is SquircleShape) {
                            // For SquircleShape, recreate the path directly as Android Path
                            // Use reflection to access the private fields of SquircleShape
                            val cornerRadius = try {
                                val cornerRadiusField = shape::class.java.getDeclaredField("cornerRadius")
                                cornerRadiusField.isAccessible = true
                                cornerRadiusField.get(shape) as? Dp
                            } catch (e: Exception) {
                                null
                            }

                            val smoothing = try {
                                val smoothingField = shape::class.java.getDeclaredField("smoothing")
                                smoothingField.isAccessible = true
                                smoothingField.get(shape) as? Float ?: 0.6f
                            } catch (e: Exception) {
                                0.6f
                            }

                            // Create the squircle path directly for the glow size
                            val androidPath = createSquircleAndroidPath(
                                width = glowWidth,
                                height = glowHeight,
                                cornerRadius = cornerRadius,
                                smoothing = smoothing,
                                density = this@drawWithContent,
                                offsetX = glowOffsetX,
                                offsetY = glowOffsetY
                            )
                            canvas.nativeCanvas.drawPath(androidPath, paint)
                        } else {
                            // For other generic shapes, check if it's a circle
                            val bounds = glowOutline.path.getBounds()
                            val isCircle = glowWidth == glowHeight &&
                                    bounds.width == bounds.height &&
                                    bounds.left == 0f && bounds.top == 0f

                            if (isCircle) {
                                // Draw a circle for circular buttons
                                val centerX = glowOffsetX + glowWidth / 2
                                val centerY = glowOffsetY + glowHeight / 2
                                val radius = glowWidth / 2
                                canvas.nativeCanvas.drawCircle(centerX, centerY, radius, paint)
                            } else {
                                // For other shapes, use the path bounds to draw a rounded rect
                                val cornerRadius = if (glowWidth == glowHeight) glowWidth / 2 else 0f
                                val rect = android.graphics.RectF(
                                    glowOffsetX + bounds.left,
                                    glowOffsetY + bounds.top,
                                    glowOffsetX + bounds.right,
                                    glowOffsetY + bounds.bottom
                                )
                                canvas.nativeCanvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint)
                            }
                        }
                    }
                }

                if (enableDitheredFalloff) {
                    val fallbackColor = when {
                        gradient is PopGradient.Solid -> gradient.color
                        else -> color
                    }
                    val dotColorInt = fallbackColor.toArgb()
                    val dotBaseAlpha = ((dotColorInt ushr 24) and 0xFF)

                    // Sparse stippled ring outside the shape:
                    // density and alpha both decay with distance for a true dithered dissolve.
                    val ringMaxDist = (blurRadiusPx * 1.45f).coerceAtLeast(1f)
                    // Finer sampling than before so dithering looks smooth, not blocky.
                    val stepPx = (blurRadiusPx / 6.0f).coerceIn(0.9f, 1.8f)
                    val jitterPx = stepPx * 0.28f

                    val isRounded = glowOutline is androidx.compose.ui.graphics.Outline.Rounded
                    val cornerRadius = if (isRounded) {
                        (glowOutline as androidx.compose.ui.graphics.Outline.Rounded)
                            .roundRect.topLeftCornerRadius.x
                    } else 0f

                    val left = glowOffsetX
                    val top = glowOffsetY
                    val right = glowOffsetX + glowWidth
                    val bottom = glowOffsetY + glowHeight

                    val centerX = (left + right) / 2f
                    val centerY = (top + bottom) / 2f
                    val halfW = (right - left) / 2f
                    val halfH = (bottom - top) / 2f

                    fun hash(ix: Int, iy: Int, seed: Float): Float {
                        val v = sin((ix * 12.9898f) + (iy * 78.233f) + seed) * 43758.5453f
                        return v - floor(v)
                    }

                    fun outsideDistanceRoundedRect(x: Float, y: Float): Float {
                        if (!isRounded) {
                            val dx = maxOf(left - x, 0f, x - right)
                            val dy = maxOf(top - y, 0f, y - bottom)
                            return hypot(dx, dy)
                        }

                        val r = cornerRadius.coerceAtLeast(0f).coerceAtMost(minOf(halfW, halfH))
                        val px = kotlin.math.abs(x - centerX)
                        val py = kotlin.math.abs(y - centerY)
                        val qx = px - (halfW - r)
                        val qy = py - (halfH - r)
                        val qxPos = maxOf(qx, 0f)
                        val qyPos = maxOf(qy, 0f)
                        val sdf = hypot(qxPos, qyPos) + minOf(maxOf(qx, qy), 0f) - r
                        return maxOf(sdf, 0f)
                    }

                    val dotPaint = android.graphics.Paint().apply {
                        isAntiAlias = true
                        isDither = true
                        style = android.graphics.Paint.Style.FILL
                        setColor(dotColorInt)
                        // Slight blur softens each stipple point into a smooth grain.
                        maskFilter = BlurMaskFilter(
                            (stepPx * 0.85f).coerceAtLeast(0.6f),
                            BlurMaskFilter.Blur.NORMAL
                        )
                    }

                    var y = top - ringMaxDist
                    var row = 0
                    while (y <= bottom + ringMaxDist) {
                        var x = left - ringMaxDist
                        var col = 0
                        while (x <= right + ringMaxDist) {
                            val jx = (hash(col, row, 19.13f) - 0.5f) * 2f * jitterPx
                            val jy = (hash(col, row, 73.31f) - 0.5f) * 2f * jitterPx
                            val px = x + jx
                            val py = y + jy

                            val dist = outsideDistanceRoundedRect(px, py)
                            if (dist in 0f..ringMaxDist) {
                                val t = (dist / ringMaxDist).coerceIn(0f, 1f)
                                val density =
                                    ((1f - t) * (1f - t) * 0.85f + 0.03f) * ditherStrength
                                if (hash(col, row, 137.77f) < density.coerceIn(0f, 1f)) {
                                    val alphaMul = ((1f - t) * (1f - t) * (1f - t))
                                    val alpha =
                                        (dotBaseAlpha * alphaMul * 0.42f).toInt().coerceIn(0, 255)
                                    if (alpha > 0) {
                                        dotPaint.alpha = alpha
                                        val radius = (stepPx * (0.30f + (1f - t) * 0.16f))
                                            .coerceAtLeast(0.45f)
                                        canvas.nativeCanvas.drawCircle(px, py, radius, dotPaint)
                                    }
                                }
                            }

                            x += stepPx
                            col++
                        }
                        y += stepPx
                        row++
                    }
                }
            }

            // Draw the actual content on top
            drawContent()
        }
}

/**
 * Applies a clean, gradual glow effect around a composable without
 * tinting the interior area or adding dark halo artifacts.
 *
 * This implementation uses native shadow layers with a transparent fill,
 * so only the outer glow is drawn.
 */
@Stable
fun Modifier.intenseGlowEffect(
    glowRadius: Dp = 10.dp,
    spread: Dp = 3.dp,
    color: Color = Color.White,
    layers: Int = 2,
    shape: Shape = RectangleShape
): Modifier {
    return this.drawBehind {
        val glowRadiusPx = glowRadius.toPx()
        val spreadPx = spread.toPx()

        // Get shape outline for corner radius
        val shapeOutline = shape.createOutline(
            size = size,
            layoutDirection = layoutDirection,
            density = this
        )

        val cornerRadiusPx = when (shapeOutline) {
            is androidx.compose.ui.graphics.Outline.Rounded ->
                shapeOutline.roundRect.topLeftCornerRadius.x

            is androidx.compose.ui.graphics.Outline.Generic ->
                if (size.width == size.height) size.width / 2 else size.height / 2

            else -> 0f
        }

        val alphaSteps = when (layers.coerceIn(1, 3)) {
            1 -> listOf(0.4f)
            2 -> listOf(0.6f, 0.35f)
            else -> listOf(0.8f, 0.45f, 0.25f)
        }

        drawIntoCanvas { canvas ->
            alphaSteps.forEachIndexed { index, alpha ->
                val blur = glowRadiusPx * (1f + (index * 0.6f))
                val glowColor = color.copy(alpha = alpha)

                val paint = android.graphics.Paint().apply {
                    isAntiAlias = true
                    // Use transparent fill; draw only the shadow/glow
                    setColor(android.graphics.Color.TRANSPARENT)
                    setShadowLayer(
                        blur.coerceAtLeast(1f),
                        0f,
                        0f,
                        glowColor.toArgb()
                    )
                }

                val rectLeft = -spreadPx
                val rectTop = -spreadPx
                val rectRight = size.width + spreadPx
                val rectBottom = size.height + spreadPx

                if (size.width == size.height && shapeOutline is androidx.compose.ui.graphics.Outline.Generic) {
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val radius = size.width / 2 + spreadPx
                    canvas.nativeCanvas.drawCircle(centerX, centerY, radius, paint)
                } else {
                    canvas.nativeCanvas.drawRoundRect(
                        rectLeft,
                        rectTop,
                        rectRight,
                        rectBottom,
                        cornerRadiusPx,
                        cornerRadiusPx,
                        paint
                    )
                }
            }
        }
    }
}

/**
 * Applies a card-style background with enclosed border styling.
 *
 * This modifier creates:
 * - Outer radial gradient background with rounded corners
 * - Border with specified color
 * - Inner padding (content area)
 *
 * The gradient used is: radial-gradient(104.17% 104.17% at 30.35% 21.05%, #1D1D1D 0%, #121212 100%)
 * This gradient is defined in [GradientPreset.CardBackground].
 *
 * @param borderColor The color for the border (default: PopColor.Grey.Grey700)
 * @param cornerRadius The corner radius for the outer shape (default: PopRadius.Medium)
 * @param innerPadding The padding between outer border and inner content area (default: 12.dp)
 *
 * @sample
 * ```
 * Box(
 *     modifier = Modifier
 *         .backgroundWithEnclosedBorder()
 * ) {
 *     // Your content here
 * }
 * ```
 */
@Stable
fun Modifier.backgroundWithEnclosedBorder(
    borderColor: Color = BorderColor.Secondary,
    cornerRadius: Dp = PopRadius.Medium,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    shape: Shape? = null
): Modifier {
    return this
        .popBackground(
            gradient = GradientPreset.CardBackground.gradient,
            shape = shape ?: RoundedCornerShape(cornerRadius)
        )
        .border(
            color = borderColor,
            shape = shape ?: RoundedCornerShape(cornerRadius),
            width = 0.5.dp
        )
        .padding(innerPadding)
}

@Stable
fun Modifier.backgroundSquircleWithEnclosedBorder(
    borderColor: Color = BorderColor.Secondary,
    cornerRadius: Dp = PopRadius.Medium,
    innerPadding: PaddingValues = PaddingValues(0.dp)
): Modifier {
    return this
        .popBackground(
            gradient = GradientPreset.CardBackground.gradient,
            shape = SquircleShape(cornerRadius)
        )
        .border(
            color = borderColor,
            shape = SquircleShape(cornerRadius),
            width = 0.5.dp
        )
        .padding(innerPadding)
}

@Stable
fun Modifier.backgroundWithoutBorder(
    borderColor: Color = BorderColor.Secondary,
    cornerRadius: Dp = PopRadius.Medium,
    shape: Shape? = null,
    innerPadding: PaddingValues = PaddingValues(0.dp)
): Modifier {
    return this
        .popBackground(
            gradient = GradientPreset.CardBackground.gradient,
            shape = shape ?: RoundedCornerShape(cornerRadius)
        )
        .padding(innerPadding)
}

/**
 * Applies a card-style border with enclosed area but no inner background.
 *
 * This modifier creates:
 * - Outer dark gray background with rounded corners
 * - Inner padding (content area)
 * - No inner background (transparent, content shows through)
 *
 * The dashed orange border shown in Figma is just a visual indicator for where content goes.
 *
 * @param outerBackgroundColor The color for the outer background (default: dark gray)
 * @param cornerRadius The corner radius for the outer shape (default: PopRadius.Large)
 * @param innerPadding The padding between outer border and inner content area (default: 16.dp)
 *
 * @sample
 * ```
 * Box(
 *     modifier = Modifier
 *         .borderEnclosedNoBg()
 * ) {
 *     // Your content here
 * }
 * ```
 */
@Stable
fun Modifier.borderEnclosedNoBg(
    borderColor: Color = BorderColor.Tertiary, // Dark charcoal gray
    cornerRadius: Dp = PopRadius.Medium,
    shape: Shape? = null,
    innerPadding: PaddingValues = PaddingValues(0.dp)
): Modifier {
    return this
        .border(
            width = 0.5.dp,
            color = borderColor,
            shape = shape ?: RoundedCornerShape(cornerRadius)
        )
        .padding(innerPadding)
}

fun Modifier.rippleClickable(
    speed: SweepSpeed = SweepSpeed.Fast,
    enabled: Boolean = true,
    onClick: () -> Unit
): Modifier = composed {

    val scope = rememberCoroutineScope()
    val motion = speed.motionSpec()

    val centerX = remember { Animatable(0f) }
    val centerY = remember { Animatable(0f) }

    var size by remember { mutableStateOf(IntSize.Zero) }
    var isAnimating by remember { mutableStateOf(false) }

    this
        .onSizeChanged { size = it }

        .pointerInput(enabled, speed) {
            if (!enabled) return@pointerInput

            detectTapGestures(
                onPress = {
                    if (size.width == 0 || size.height == 0) return@detectTapGestures

                    val w = size.width.toFloat()
                    val h = size.height.toFloat()

                    scope.launch {
                        isAnimating = true

                        // START: bottom-end (outside)
                        centerX.snapTo(w * 1.2f)
                        centerY.snapTo(h * 1.2f)

                        // PHASE 1: bottom-end → center
                        launch {
                            centerX.animateTo(
                                targetValue = w * 0.5f,
                                animationSpec = tween(
                                    durationMillis = motion.phase1Duration,
                                    easing = motion.phase1Easing
                                )
                            )
                        }
                        launch {
                            centerY.animateTo(
                                targetValue = h * 0.5f,
                                animationSpec = tween(
                                    durationMillis = motion.phase1Duration,
                                    easing = motion.phase1Easing
                                )
                            )
                        }.join()

                        // PHASE 2: center → top-start
                        launch {
                            centerX.animateTo(
                                targetValue = -w * 0.2f,
                                animationSpec = tween(
                                    durationMillis = motion.phase2Duration,
                                    easing = motion.phase2Easing
                                )
                            )
                        }
                        launch {
                            centerY.animateTo(
                                targetValue = -h * 0.2f,
                                animationSpec = tween(
                                    durationMillis = motion.phase2Duration,
                                    easing = motion.phase2Easing
                                )
                            )
                        }.join()

                        isAnimating = false
                    }

                    tryAwaitRelease()
                    onClick()
                }
            )
        }

        .drawWithContent {

            drawContent()

            if (!isAnimating) return@drawWithContent

            val w = size.width.toFloat()
            val h = size.height.toFloat()
            if (w == 0f || h == 0f) return@drawWithContent

            val radius = hypot(w, h) * 1.1f

            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.08f),
                        Color.Transparent
                    ),
                    center = Offset(centerX.value, centerY.value),
                    radius = radius
                )
            )
        }
}

enum class SweepSpeed {
    Slow,
    Fast
}

private data class SweepMotionSpec(
    val phase1Duration: Int,
    val phase2Duration: Int,
    val phase1Easing: Easing,
    val phase2Easing: Easing
)

private fun SweepSpeed.motionSpec(): SweepMotionSpec = when (this) {
    SweepSpeed.Slow -> SweepMotionSpec(
        phase1Duration = 420,
        phase2Duration = 480,
        phase1Easing = FastOutSlowInEasing,
        phase2Easing = LinearOutSlowInEasing
    )

    SweepSpeed.Fast -> SweepMotionSpec(
        phase1Duration = 260,
        phase2Duration = 300,
        phase1Easing = FastOutLinearInEasing,
        phase2Easing = FastOutLinearInEasing
    )
}