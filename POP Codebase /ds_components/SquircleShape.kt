package com.pop.components.ds_components

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

/**
 * A Shape implementation that creates a "Squircle" or Smooth Rounded Rectangle.
 * 
 * This shape uses continuous Bezier curves to create a smoother transition between the 
 * straight edges and the corners, matching Figma's "Corner Smoothing" algorithm.
 * 
 * The algorithm extends the curve start/end points and positions control points to create
 * a smooth, continuous transition that starts earlier and extends further into the edges
 * compared to standard rounded corners.
 *
 * @param cornerRadius The radius of the corner. If null, defaults to 50% of min dimension (full squircle).
 * @param smoothing The smoothing factor (0.0 - 1.0). Default 1.0 provides full squircle smoothing that matches Figma's corner smoothing.
 */
class SquircleShape(
    private val cornerRadius: Dp? = null,
    private val smoothing: Float = 0.6f
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = createSmoothPath(size, density))
    }

    private fun createSmoothPath(size: Size, density: Density): Path {
        val path = Path()
        val width = size.width
        val height = size.height
        
        if (width <= 0 || height <= 0) return path

        // Determine effective radius
        val maxRadius = width.coerceAtMost(height) / 2f
        val r = if (cornerRadius != null) {
            with(density) { cornerRadius.toPx() }.coerceAtMost(maxRadius)
        } else {
            maxRadius
        }

        // Figma's corner smoothing is based on a superellipse (squircle) formula
        // The curve must start much earlier to create a smooth transition from straight edges
        val p = smoothing.coerceIn(0f, 1f)
        
        // For proper Figma-style corner smoothing, the curve needs to extend significantly
        // into the edges. The anchor points should be much further from the corner to
        // create the smooth, early-starting curve transition.
        val baseExtension = 1.8f  // Larger extension for earlier curve start
        val baseCpOffset = 0.08f  // Control points closer to corner for smoother transition
        
        // Interpolate between standard rounded corner (p=0) and full squircle (p=1)
        // At p=0: extension = 1.0 (standard rounded corner)
        // At p=1: extension = baseExtension (curve starts much earlier)
        val extensionFactor = 1f + (p * (baseExtension - 1f))
        val anchorLen = r * extensionFactor
        
        // Control points positioned very close to the corner create the smooth transition
        // Lower cpOffset ratio means control points are closer to corner = smoother curve
        val cpOffsetRatio = baseCpOffset + (p * 0.03f) // 0.08 to 0.11 range
        val cpOffset = r * cpOffsetRatio
        
        // Clamp to bounds to ensure we don't exceed the shape dimensions
        val safeAnchor = anchorLen.coerceAtMost(width / 2f).coerceAtMost(height / 2f)
        // Scale cpOffset proportionally if anchor was clamped
        val safeCpOffset = if (safeAnchor < anchorLen && anchorLen > 0f) {
            cpOffset * (safeAnchor / anchorLen)
        } else {
            cpOffset
        }
        
        val left = 0f
        val top = 0f
        val right = width
        val bottom = height
        
        // Start from top-left, moving clockwise
        path.moveTo(left, top + safeAnchor)
        
        // Top-Left Corner - squircle bezier curve
        path.cubicTo(
            left, top + safeCpOffset,      // CP1: vertical control point
            left + safeCpOffset, top,      // CP2: horizontal control point
            left + safeAnchor, top         // End point (extends well into edge)
        )
        
        // Top edge
        path.lineTo(right - safeAnchor, top)
        
        // Top-Right Corner
        path.cubicTo(
            right - safeCpOffset, top,     // CP1: horizontal control point
            right, top + safeCpOffset,     // CP2: vertical control point
            right, top + safeAnchor        // End point
        )
        
        // Right edge
        path.lineTo(right, bottom - safeAnchor)
        
        // Bottom-Right Corner
        path.cubicTo(
            right, bottom - safeCpOffset,  // CP1: vertical control point
            right - safeCpOffset, bottom,  // CP2: horizontal control point
            right - safeAnchor, bottom     // End point
        )
        
        // Bottom edge
        path.lineTo(left + safeAnchor, bottom)
        
        // Bottom-Left Corner
        path.cubicTo(
            left + safeCpOffset, bottom,   // CP1: horizontal control point
            left, bottom - safeCpOffset,   // CP2: vertical control point
            left, bottom - safeAnchor      // End point
        )
        
        path.close()
        return path
    }
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SquircleShape) return false
        if (cornerRadius != other.cornerRadius) return false
        if (smoothing != other.smoothing) return false
        return true
    }

    override fun hashCode(): Int {
        var result = cornerRadius?.hashCode() ?: 0
        result = 31 * result + smoothing.hashCode()
        return result
    }
}
