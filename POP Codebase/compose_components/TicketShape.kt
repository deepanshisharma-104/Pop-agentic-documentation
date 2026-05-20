package com.pop.components.compose_components

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.SquircleShape

/**
 * Ticket shape with semicircular cutouts on the right edge.
 * Used for creating ticket/perforation effect on list items.
 *
 * @param cornerRadius Radius for the rounded corners of the main shape
 * @param cutoutRadius Radius of the semicircular cutouts on the right edge
 * @param topCutoutOffset Offset from top for the top cutout (default: 10.dp)
 * @param bottomCutoutOffset Offset from bottom for the bottom cutout (default: 10.dp)
 * @param rightEdgeOffset Offset from right edge where cutouts are positioned (default: 94.dp)
 * @param cornerSmoothing Optional squircle smoothing (0f-1f). When null, uses rounded corners.
 */
class TicketShape(
    private val cornerRadius: Dp,
    private val cutoutRadius: Dp = 10.dp,
    private val topCutoutOffset: Dp = 10.dp,
    private val bottomCutoutOffset: Dp = 10.dp,
    private val rightEdgeOffset: Dp = 94.dp,
    private val cornerSmoothing: Float? = null
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = getPath(size, density))
    }

    private fun getPath(size: Size, density: Density): Path {
        val roundedRectPath = if (cornerSmoothing != null) {
            val outline = SquircleShape(cornerRadius = cornerRadius, smoothing = cornerSmoothing)
                .createOutline(size, LayoutDirection.Ltr, density)
            when (outline) {
                is Outline.Generic -> outline.path
                else -> Path().apply {
                    val roundedRect = RoundRect(size.toRect(), CornerRadius(cornerRadius.value))
                    addRoundRect(roundedRect)
                }
            }
        } else {
            val roundedRect = RoundRect(size.toRect(), CornerRadius(cornerRadius.value))
            Path().apply { addRoundRect(roundedRect) }
        }
        return Path.combine(
            operation = PathOperation.Intersect,
            path1 = roundedRectPath,
            path2 = getTicketPath(size, density)
        )
    }

    private fun getTicketPath(size: Size, density: Density): Path {
        val middleX = 2 * size.width / 3f
        val circleRadiusInPx = with(density) { cutoutRadius.toPx() }

        return Path().apply {
            reset()

            // Start top-left
            moveTo(0f, 0f)

            // Top edge → before notch
            lineTo(middleX - circleRadiusInPx, 0f)

            // Top notch (cut-in)
            arcTo(
                rect = Rect(
                    left = middleX - circleRadiusInPx,
                    top = -circleRadiusInPx,
                    right = middleX + circleRadiusInPx,
                    bottom = circleRadiusInPx
                ),
                startAngleDegrees = 180f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )

            // Top edge → right
            lineTo(size.width, 0f)

            // Right edge
            lineTo(size.width, size.height)

            // Bottom edge → after notch
            lineTo(middleX + circleRadiusInPx, size.height)

            // Bottom notch (cut-in)
            arcTo(
                rect = Rect(
                    left = middleX - circleRadiusInPx,
                    top = size.height - circleRadiusInPx,
                    right = middleX + circleRadiusInPx,
                    bottom = size.height + circleRadiusInPx
                ),
                startAngleDegrees = 0f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )

            // Bottom edge → left
            lineTo(0f, size.height)

            // Left edge
            close()
        }
    }
}
