package com.pop.components.coachmarks.utils

import android.graphics.Rect as AndroidRect
import android.view.View
import androidx.compose.ui.geometry.Rect

/**
 * Utility functions for working with coach marks.
 */
object CoachMarkUtils {
    
    /**
     * Get the bounds of a View as a Compose Rect for use in coach marks.
     * This is useful for XML views that need to be highlighted.
     *
     * @param view The view to get bounds for
     * @return Rect representing the view's position on screen, or null if view not laid out
     */
    fun getViewBounds(view: View): Rect? {
        if (!view.isLaidOut) return null
        
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        
        return Rect(
            left = location[0].toFloat(),
            top = location[1].toFloat(),
            right = (location[0] + view.width).toFloat(),
            bottom = (location[1] + view.height).toFloat()
        )
    }
    
    /**
     * Get bounds for a view with optional padding.
     * Useful when you want to highlight an area slightly larger than the view itself.
     *
     * @param view The view to get bounds for
     * @param paddingPx Padding in pixels to add around the view
     * @return Rect with padding applied, or null if view not laid out
     */
    fun getViewBoundsWithPadding(view: View, paddingPx: Float): Rect? {
        val bounds = getViewBounds(view) ?: return null
        
        return Rect(
            left = bounds.left - paddingPx,
            top = bounds.top - paddingPx,
            right = bounds.right + paddingPx,
            bottom = bounds.bottom + paddingPx
        )
    }
    
    /**
     * Convert Android Rect to Compose Rect.
     *
     * @param androidRect Android framework Rect
     * @return Compose UI Rect
     */
    fun androidRectToComposeRect(androidRect: AndroidRect): Rect {
        return Rect(
            left = androidRect.left.toFloat(),
            top = androidRect.top.toFloat(),
            right = androidRect.right.toFloat(),
            bottom = androidRect.bottom.toFloat()
        )
    }
    
    /**
     * Create a Rect from center point and size.
     * Useful when you know the center position of an element.
     *
     * @param centerX Center X coordinate
     * @param centerY Center Y coordinate
     * @param width Width of the rect
     * @param height Height of the rect
     * @return Rect centered at the given point
     */
    fun createCenteredRect(
        centerX: Float,
        centerY: Float,
        width: Float,
        height: Float
    ): Rect {
        val halfWidth = width / 2
        val halfHeight = height / 2
        
        return Rect(
            left = centerX - halfWidth,
            top = centerY - halfHeight,
            right = centerX + halfWidth,
            bottom = centerY + halfHeight
        )
    }
    
    /**
     * Wait for view to be laid out, then execute callback with view bounds.
     * Useful when you need to get bounds immediately after view creation.
     *
     * @param view The view to observe
     * @param callback Callback to execute with view bounds
     */
    fun waitForLayout(view: View, callback: (Rect) -> Unit) {
        if (view.isLaidOut) {
            getViewBounds(view)?.let(callback)
            return
        }
        
        view.post {
            getViewBounds(view)?.let(callback)
        }
    }
    
    /**
     * Check if a rect is within screen bounds.
     * Useful for validating coach mark target positions.
     *
     * @param rect The rect to check
     * @param screenWidth Screen width in pixels
     * @param screenHeight Screen height in pixels
     * @return true if rect is within screen bounds
     */
    fun isRectInScreen(
        rect: Rect,
        screenWidth: Float,
        screenHeight: Float
    ): Boolean {
        return rect.left >= 0 &&
                rect.top >= 0 &&
                rect.right <= screenWidth &&
                rect.bottom <= screenHeight
    }
    
    /**
     * Adjust rect to fit within screen bounds.
     * Useful for ensuring coach mark targets are visible.
     *
     * @param rect The rect to adjust
     * @param screenWidth Screen width in pixels
     * @param screenHeight Screen height in pixels
     * @return Adjusted rect that fits within screen
     */
    fun constrainRectToScreen(
        rect: Rect,
        screenWidth: Float,
        screenHeight: Float
    ): Rect {
        return Rect(
            left = rect.left.coerceAtLeast(0f),
            top = rect.top.coerceAtLeast(0f),
            right = rect.right.coerceAtMost(screenWidth),
            bottom = rect.bottom.coerceAtMost(screenHeight)
        )
    }
    
    /**
     * Expand rect by a percentage.
     * Useful for highlighting an area larger than the actual view.
     *
     * @param rect The rect to expand
     * @param percentage Percentage to expand (e.g., 0.1 for 10% larger)
     * @return Expanded rect
     */
    fun expandRect(rect: Rect, percentage: Float): Rect {
        val expandX = rect.width * percentage / 2
        val expandY = rect.height * percentage / 2
        
        return Rect(
            left = rect.left - expandX,
            top = rect.top - expandY,
            right = rect.right + expandX,
            bottom = rect.bottom + expandY
        )
    }
    
    /**
     * Combine multiple rects into one bounding rect.
     * Useful for highlighting multiple views as one area.
     *
     * @param rects List of rects to combine
     * @return Single rect that encompasses all input rects
     */
    fun combineRects(rects: List<Rect>): Rect? {
        if (rects.isEmpty()) return null
        
        var minLeft = Float.MAX_VALUE
        var minTop = Float.MAX_VALUE
        var maxRight = Float.MIN_VALUE
        var maxBottom = Float.MIN_VALUE
        
        rects.forEach { rect ->
            minLeft = minOf(minLeft, rect.left)
            minTop = minOf(minTop, rect.top)
            maxRight = maxOf(maxRight, rect.right)
            maxBottom = maxOf(maxBottom, rect.bottom)
        }
        
        return Rect(
            left = minLeft,
            top = minTop,
            right = maxRight,
            bottom = maxBottom
        )
    }
}

/**
 * Extension function to get coach mark bounds from a View.
 */
fun View.toCoachMarkRect(): Rect? {
    return CoachMarkUtils.getViewBounds(this)
}

/**
 * Extension function to get coach mark bounds with padding from a View.
 */
fun View.toCoachMarkRect(paddingPx: Float): Rect? {
    return CoachMarkUtils.getViewBoundsWithPadding(this, paddingPx)
}
