package com.pop.components.coachmarks.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.pop.components.coachmarks.HighlightStyle

/**
 * Overlay that dims the screen and highlights a specific area.
 * Creates a gradient-like dimming effect similar to the progress bar in CommonUIManager.
 *
 * @param targetRect The rectangle area to highlight (not dimmed)
 * @param highlightStyle Style of highlighting (BORDER, FILLED_SQUIRCLE, or CIRCLE)
 * @param cornerRadiusDp Corner radius in dp (null = use default based on style)
 * @param dimOpacity Opacity of the dimming overlay (0.0 to 1.0)
 * @param onTapOutside Callback when user taps outside the highlighted area
 */
@Composable
fun CoachMarkOverlay(
    targetRect: Rect?,
    highlightStyle: HighlightStyle,
    cornerRadiusDp: Float? = null,
    dimOpacity: Float = 0.7f,
    onTapOutside: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    // Check if tap is outside the target area
                    if (targetRect == null || !targetRect.contains(offset)) {
                        onTapOutside?.invoke()
                    }
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw dimming overlay with cutout for highlighted area
            if (targetRect != null) {
                // Create path for the entire screen
                val screenPath = Path().apply {
                    addRect(
                        Rect(
                            offset = Offset.Zero,
                            size = Size(size.width, size.height)
                        )
                    )
                }
                
                // Calculate corner radius based on style and optional override
                val cornerRadiusPx = when {
                    cornerRadiusDp != null -> cornerRadiusDp.dp.toPx()
                    highlightStyle == HighlightStyle.CIRCLE -> minOf(targetRect.width, targetRect.height) / 2f
                    highlightStyle == HighlightStyle.FILLED_SQUIRCLE -> 20.dp.toPx()
                    else -> 8.dp.toPx() // BORDER default
                }
                
                // Create path for the cutout (highlighted area)
                val cutoutPath = Path().apply {
                    addRoundRect(
                        androidx.compose.ui.geometry.RoundRect(
                            rect = targetRect,
                            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
                        )
                    )
                }
                
                // Subtract the cutout from the screen to create a hole
                val finalPath = Path().apply {
                    op(screenPath, cutoutPath, androidx.compose.ui.graphics.PathOperation.Difference)
                }
                
                // Draw the dimming overlay
                drawPath(
                    path = finalPath,
                    color = Color.Black.copy(alpha = dimOpacity)
                )
                
                // Draw highlight border around the target area using the same corner radius
                drawRoundRect(
                    color = Color.White,
                    topLeft = targetRect.topLeft,
                    size = targetRect.size,
                    cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx),
                    style = Stroke(width = 1.dp.toPx())
                )
            } else {
                // No target rect, just dim the entire screen
                drawRect(
                    color = Color.Black.copy(alpha = dimOpacity)
                )
            }
        }
    }
}
