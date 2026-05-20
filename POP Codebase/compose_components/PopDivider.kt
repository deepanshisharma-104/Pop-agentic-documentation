package com.pop.components.compose_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pop.components.theme.PopColors
import com.pop.components.theme.PopStroke

/**
 * Pop Design System - Divider Component
 *
 * A unified divider component that supports both horizontal and vertical orientations,
 * with solid and dashed styles matching Figma specifications.
 *
 * Based on: [Figma Design System](https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=278-13798)
 */

/**
 * Divider style options
 */
enum class PopDividerStyle {
    /** Solid line divider (full width) */
    Solid,
    
    /** Dashed line divider */
    Dashed,
    
    /** Dotted line divider with circular dots */
    Dotted
}

/**
 * Divider orientation
 */
enum class PopDividerOrientation {
    /** Horizontal divider (default) */
    Horizontal,
    
    /** Vertical divider */
    Vertical
}

/**
 * Pop Divider - A unified divider component with horizontal/vertical and solid/dashed/dotted support
 *
 * @param modifier Modifier to be applied to the divider
 * @param orientation Orientation of the divider - [PopDividerOrientation.Horizontal] or [PopDividerOrientation.Vertical]
 * @param style Style of the divider - [PopDividerStyle.Solid], [PopDividerStyle.Dashed], or [PopDividerStyle.Dotted]
 * @param color Color of the divider (defaults to PopColors.Neutral.N5)
 * @param thickness Thickness of the divider (defaults to PopStroke.Default - 0.5dp)
 * @param dashLength Length of each dash segment (only used for dashed style, defaults to 8dp)
 * @param gapLength Length of gap between dashes (only used for dashed style, defaults to 4dp)
 * @param dotDiameter Diameter of each dot (only used for dotted style, defaults to 2.5dp)
 * @param dotGap Gap between dots (only used for dotted style, defaults to 3dp)
 *
 * Example:
 * ```
 * // Horizontal solid divider
 * PopDivider()
 *
 * // Horizontal dashed divider
 * PopDivider(style = PopDividerStyle.Dashed)
 *
 * // Horizontal dotted divider (circular dots)
 * PopDivider(style = PopDividerStyle.Dotted)
 *
 * // Vertical solid divider
 * PopDivider(orientation = PopDividerOrientation.Vertical)
 *
 * // Custom color and thickness
 * PopDivider(
 *     color = PopColors.Neutral.N7,
 *     thickness = 1.dp
 * )
 * ```
 */
@Composable
fun PopDivider(
    modifier: Modifier = Modifier,
    orientation: PopDividerOrientation = PopDividerOrientation.Horizontal,
    style: PopDividerStyle = PopDividerStyle.Solid,
    color: Color = PopColors.Neutral.N5,
    thickness: Dp = PopStroke.Default,
    dashLength: Dp = 8.dp,
    gapLength: Dp = 4.dp,
    dotDiameter: Dp = 2.5.dp,
    dotGap: Dp = 3.dp
) {
    val density = LocalDensity.current
    
    when (style) {
        PopDividerStyle.Solid -> {
            // Use Material 3 Divider for solid style (optimized)
            when (orientation) {
                PopDividerOrientation.Horizontal -> {
                    Divider(
                        modifier = modifier,
                        color = color,
                        thickness = thickness
                    )
                }
                PopDividerOrientation.Vertical -> {
                    Box(
                        modifier = modifier
                            .width(thickness)
                            .fillMaxHeight()
                    ) {
                        Divider(
                            modifier = Modifier
                                .width(thickness)
                                .fillMaxHeight(),
                            color = color,
                            thickness = thickness
                        )
                    }
                }
            }
        }
        PopDividerStyle.Dashed -> {
            // Custom Canvas-based divider for dashed style
            val thicknessPx = with(density) { thickness.toPx() }
            val dashPx = with(density) { dashLength.toPx() }
            val gapPx = with(density) { gapLength.toPx() }
            
            val safeDashPx = if (dashPx > 0) dashPx else 0.1f
            val safeGapPx = if (gapPx > 0) gapPx else 0.1f
            
            val pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(safeDashPx, safeGapPx),
                phase = 0f
            )
            
            when (orientation) {
                PopDividerOrientation.Horizontal -> {
                    Canvas(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(thickness)
                    ) {
                        drawLine(
                            color = color,
                            start = Offset(x = 0f, y = size.height / 2f),
                            end = Offset(x = size.width, y = size.height / 2f),
                            strokeWidth = thicknessPx,
                            pathEffect = pathEffect
                        )
                    }
                }
                PopDividerOrientation.Vertical -> {
                    Canvas(
                        modifier = modifier
                            .width(thickness)
                            .fillMaxHeight()
                    ) {
                        drawLine(
                            color = color,
                            start = Offset(x = size.width / 2f, y = 0f),
                            end = Offset(x = size.width / 2f, y = size.height),
                            strokeWidth = thicknessPx,
                            pathEffect = pathEffect
                        )
                    }
                }
            }
        }
        PopDividerStyle.Dotted -> {
            // Custom Canvas-based divider for dotted style with circular dots
            val dotDiameterPx = with(density) { dotDiameter.toPx() }
            val dotGapPx = with(density) { dotGap.toPx() }
            val dotRadius = dotDiameterPx / 2f
            
            when (orientation) {
                PopDividerOrientation.Horizontal -> {
                    Canvas(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(dotDiameter)
                    ) {
                        val centerY = size.height / 2f
                        val availableWidth = size.width
                        
                        // Calculate how many dots can fit
                        val dotCount = if (dotDiameterPx + dotGapPx > 0) {
                            ((availableWidth + dotGapPx) / (dotDiameterPx + dotGapPx)).toInt().coerceAtLeast(1)
                        } else {
                            1
                        }
                        
                        // Calculate actual spacing to distribute dots evenly
                        val actualSpacing = if (dotCount > 1) {
                            (availableWidth - (dotCount * dotDiameterPx)) / (dotCount - 1)
                        } else {
                            0f
                        }
                        
                        var currentX = dotRadius
                        repeat(dotCount) {
                            drawCircle(
                                color = color,
                                radius = dotRadius,
                                center = Offset(x = currentX, y = centerY)
                            )
                            if (it < dotCount - 1) {
                                currentX += dotDiameterPx + actualSpacing
                            }
                        }
                    }
                }
                PopDividerOrientation.Vertical -> {
                    Canvas(
                        modifier = modifier
                            .width(dotDiameter)
                            .fillMaxHeight()
                    ) {
                        val centerX = size.width / 2f
                        val availableHeight = size.height
                        
                        // Calculate how many dots can fit
                        val dotCount = if (dotDiameterPx + dotGapPx > 0) {
                            ((availableHeight + dotGapPx) / (dotDiameterPx + dotGapPx)).toInt().coerceAtLeast(1)
                        } else {
                            1
                        }
                        
                        // Calculate actual spacing to distribute dots evenly
                        val actualSpacing = if (dotCount > 1) {
                            (availableHeight - (dotCount * dotDiameterPx)) / (dotCount - 1)
                        } else {
                            0f
                        }
                        
                        var currentY = dotRadius
                        repeat(dotCount) {
                            drawCircle(
                                color = color,
                                radius = dotRadius,
                                center = Offset(x = centerX, y = currentY)
                            )
                            if (it < dotCount - 1) {
                                currentY += dotDiameterPx + actualSpacing
                            }
                        }
                    }
                }
            }
        }
    }
}


