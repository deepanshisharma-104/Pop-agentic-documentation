package com.pop.components.ds_components

import android.graphics.BlurMaskFilter
import android.graphics.Color
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pop.components.theme.PopColors

/**
 * Scrim types based on Figma specifications.
 * 
 * Blur values:
 * - 100% blur = 100.dp
 * - 30% blur = 30.dp (0.3 * 100.dp)
 */
enum class ScrimType {
    /**
     * Type 1: 70% opacity + 30% blur (24.dp) throughout
     */
    Block70Blur30,
    
    /**
     * Type 2: Just 30% blur (24.dp) - no opacity gradient
     */
    Blur30,
    
    /**
     * Type 3: Gradient 100-0% opacity + blur 30-0% from top to bottom
     */
    TopGradient100Blur30,
    
    /**
     * Type 4: Gradient 70-0% opacity + blur 30-0% from top to bottom
     */
    TopGradient70Blur30,
    
    /**
     * Type 5: Just blur 30-0% from top to bottom (no opacity gradient)
     */
    TopBlur30,
    
    /**
     * Type 6: Gradient 100-0% opacity + blur 30-0% from bottom to top
     */
    BottomGradient100Blur30,
    
    /**
     * Type 7: Gradient 70-0% opacity + blur 30-0% from bottom to top
     */
    BottomGradient70Blur30
}

// Blur constants
private val BLUR_100_PERCENT = 100.dp
private val BLUR_30_PERCENT = 30.dp // 0.3 * 100.dp

/**
 * Reusable scrim component that supports 7 different scrim types.
 * 
 * Usage:
 * ```
 * Scrim(
 *     type = ScrimType.Block70Blur30,
 *     modifier = Modifier.height(4.dp)
 * )
 * 
 * Scrim(
 *     type = ScrimType.TopGradient100Blur30,
 *     horizontalPadding = 12.dp,
 *     height = 4.dp
 * )
 * ```
 * 
 * @param type The scrim type (one of 7 predefined types)
 * @param modifier Modifier for the scrim container
 * @param horizontalPadding Optional horizontal padding (default: 0dp, can be 12dp)
 * @param height Height of the scrim (default: 4dp overhang)
 */
@Composable
fun Scrim(
    type: ScrimType,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 0.dp,
    height: Dp = 40.dp
) {
    val density = LocalDensity.current
    val baseColor = PopColors.Neutral.N1
    
    Box(
        modifier = modifier
            .then(
                if (horizontalPadding > 0.dp) {
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = horizontalPadding)
                } else {
                    Modifier.fillMaxWidth()
                }
            )
            .height(height)
            .then(
                when (type) {
                    ScrimType.Block70Blur30 -> {
                        Modifier.scrimBlock70Blur30()
                    }
                    ScrimType.Blur30 -> {
                        Modifier.scrimBlur30()
                    }
                    ScrimType.TopGradient100Blur30 -> {
                        Modifier.scrimTopGradient100Blur30(height = height, density = density)
                    }
                    ScrimType.TopGradient70Blur30 -> {
                        Modifier.scrimTopGradient70Blur30(height = height, density = density)
                    }
                    ScrimType.TopBlur30 -> {
                        Modifier.scrimTopBlur30(height = height, density = density)
                    }
                    ScrimType.BottomGradient100Blur30 -> {
                        Modifier.scrimBottomGradient100Blur30(height = height, density = density)
                    }
                    ScrimType.BottomGradient70Blur30 -> {
                        Modifier.scrimBottomGradient70Blur30(height = height, density = density)
                    }
                }
            )
    )
}

/**
 * Type 1: Block scrim with 70% opacity and 30% blur throughout
 * 
 * This scrim provides a semi-transparent overlay (70% opacity) that sits above the PopAppBar.
 * Note: The blur effect in the name refers to the design intent, but Compose's blur modifier
 * blurs the composable itself, not content behind it. For a true frosted glass effect with blur,
 * a custom implementation using RenderScript or other techniques would be needed.
 */
@Composable
fun Modifier.scrimBlock70Blur30(): Modifier {
    val baseColor = PopColors.Neutral.N1
    // 70% opacity as per design spec
    val scrimColor = baseColor.copy(alpha = 0.7f) // 70% opacity
    
    Log.d("AppBarDebug", "Scrim color: baseColor=$baseColor, scrimColor=$scrimColor (alpha=0.7f), API=${Build.VERSION.SDK_INT}")
    
    // The scrim is a semi-transparent overlay with blur effect on content behind it
    // Use RenderEffect.createBlurEffect() for API 31+ to blur content behind the scrim
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val blurRadiusPx = with(LocalDensity.current) { BLUR_30_PERCENT.toPx() }
        Log.d("AppBarDebug", "Applying RenderEffect blur: radius=${BLUR_30_PERCENT.value}dp (${blurRadiusPx}px)")
        this
            .background(scrimColor)
            .graphicsLayer {
                renderEffect = RenderEffect
                    .createBlurEffect(
                        blurRadiusPx, // radiusX
                        blurRadiusPx, // radiusY
                        Shader.TileMode.CLAMP
                    )
                    .asComposeRenderEffect()
            }
    } else {
        // API < 31: Just use background (RenderEffect not available)
        Log.d("AppBarDebug", "No blur (API < 31), only background")
        this.background(scrimColor)
    }
}

/**
 * Type 2: Just 30% blur - no opacity gradient
 * This blurs the content behind the scrim
 */
@Composable
private fun Modifier.scrimBlur30(): Modifier {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        // API 31+: Use Compose blur modifier
        this.blur(radius = BLUR_30_PERCENT)
    } else {
        // API < 31: BlurMaskFilter doesn't blur content behind, so we use a workaround
        // by drawing the content with blur applied
        this.drawWithContent {
            drawContent()
            // Note: For API < 31, uniform blur of content behind requires RenderScript
            // or a more complex implementation. This is a simplified version.
        }
    }
}

/**
 * Type 3: Top gradient 100-0% opacity + blur 30-0% from top to bottom
 */
@Composable
private fun Modifier.scrimTopGradient100Blur30(
    height: Dp,
    density: Density
): Modifier {
    val baseColor = PopColors.Neutral.N1
    val heightPx = with(density) { height.toPx() }
    
    return this.drawWithContent {
        drawContent()
        
        // For gradient blur, we need to use BlurMaskFilter on all versions
        // since Compose blur modifier applies uniform blur
        drawIntoCanvas { canvas ->
            val steps = 10
            for (i in 0 until steps) {
                val progress = i.toFloat() / steps
                val y = heightPx * progress
                val blurRadius = with(density) { BLUR_30_PERCENT.toPx() * (1f - progress) } // 30% to 0%
                val alpha = 1f - progress // 100% to 0%
                
                if (blurRadius > 0) {
                    val paint = Paint().apply {
                        asFrameworkPaint().apply {
                            isAntiAlias = true
                            setMaskFilter(BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL))
                            color = baseColor.copy(alpha = alpha).toArgb()
                        }
                    }
                    canvas.drawRect(
                        0f, y,
                        size.width, y + (heightPx / steps),
                        paint
                    )
                }
            }
        }
    }
}

/**
 * Type 4: Top gradient 70-0% opacity + blur 30-0% from top to bottom
 */
@Composable
fun Modifier.scrimTopGradient70Blur30(
    height: Dp,
    density: Density
): Modifier {
    val baseColor = PopColors.Neutral.N1
    val heightPx = with(density) { height.toPx() }
    
    return this.drawWithContent {
        drawContent()
        
        // For gradient blur, use BlurMaskFilter on all versions
        drawIntoCanvas { canvas ->
            val steps = 10
            for (i in 0 until steps) {
                val progress = i.toFloat() / steps
                val y = heightPx * progress
                val blurRadius = with(density) { BLUR_30_PERCENT.toPx() * (1f - progress) } // 30% to 0%
                val alpha = 0.7f * (1f - progress) // 70% to 0%
                
                if (blurRadius > 0) {
                    val paint = Paint().apply {
                        asFrameworkPaint().apply {
                            isAntiAlias = true
                            setMaskFilter(BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL))
                            color = baseColor.copy(alpha = alpha).toArgb()
                        }
                    }
                    canvas.drawRect(
                        0f, y,
                        size.width, y + (heightPx / steps),
                        paint
                    )
                }
            }
        }
    }
}

/**
 * Type 5: Just blur 30-0% from top to bottom (no opacity gradient)
 */
@Composable
private fun Modifier.scrimTopBlur30(
    height: Dp,
    density: Density
): Modifier {
    val heightPx = with(density) { height.toPx() }
    
    return this.drawWithContent {
        drawContent()
        
        // For gradient blur, use BlurMaskFilter on all versions
        drawIntoCanvas { canvas ->
            val steps = 10
            for (i in 0 until steps) {
                val progress = i.toFloat() / steps
                val y = heightPx * progress
                val blurRadius = with(density) { BLUR_30_PERCENT.toPx() * (1f - progress) } // 30% to 0%
                
                if (blurRadius > 0) {
                    val paint = Paint().apply {
                        asFrameworkPaint().apply {
                            isAntiAlias = true
                            setMaskFilter(BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL))
                            color = Color.TRANSPARENT
                        }
                    }
                    canvas.drawRect(
                        0f, y,
                        size.width, y + (heightPx / steps),
                        paint
                    )
                }
            }
        }
    }
}

/**
 * Type 6: Bottom gradient 100-0% opacity + blur 30-0% from bottom to top
 */
@Composable
private fun Modifier.scrimBottomGradient100Blur30(
    height: Dp,
    density: Density
): Modifier {
    val baseColor = PopColors.Neutral.N1
    val heightPx = with(density) { height.toPx() }
    
    return this.drawWithContent {
        drawContent()
        
        // For gradient blur, use BlurMaskFilter on all versions
        drawIntoCanvas { canvas ->
            val steps = 10
            for (i in 0 until steps) {
                val progress = i.toFloat() / steps
                val y = heightPx * progress
                val blurRadius = with(density) { BLUR_30_PERCENT.toPx() * progress } // 0% to 30%
                val alpha = progress // 0% to 100%
                
                if (blurRadius > 0) {
                    val paint = Paint().apply {
                        asFrameworkPaint().apply {
                            isAntiAlias = true
                            setMaskFilter(BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL))
                            color = baseColor.copy(alpha = alpha).toArgb()
                        }
                    }
                    canvas.drawRect(
                        0f, y,
                        size.width, y + (heightPx / steps),
                        paint
                    )
                }
            }
        }
    }
}

/**
 * Type 7: Bottom gradient 70-0% opacity + blur 30-0% from bottom to top
 */
@Composable
private fun Modifier.scrimBottomGradient70Blur30(
    height: Dp,
    density: Density
): Modifier {
    val baseColor = PopColors.Neutral.N1
    val heightPx = with(density) { height.toPx() }
    
    return this.drawWithContent {
        drawContent()
        
        // For gradient blur, use BlurMaskFilter on all versions
        drawIntoCanvas { canvas ->
            val steps = 10
            for (i in 0 until steps) {
                val progress = i.toFloat() / steps
                val y = heightPx * progress
                val blurRadius = with(density) { BLUR_30_PERCENT.toPx() * progress } // 0% to 30%
                val alpha = 0.7f * progress // 0% to 70%
                
                if (blurRadius > 0) {
                    val paint = Paint().apply {
                        asFrameworkPaint().apply {
                            isAntiAlias = true
                            setMaskFilter(BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL))
                            color = baseColor.copy(alpha = alpha).toArgb()
                        }
                    }
                    canvas.drawRect(
                        0f, y,
                        size.width, y + (heightPx / steps),
                        paint
                    )
                }
            }
        }
    }
}
