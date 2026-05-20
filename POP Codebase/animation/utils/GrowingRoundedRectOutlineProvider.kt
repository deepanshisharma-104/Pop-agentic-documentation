package com.pop.components.animation.utils

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import kotlin.math.min

class GrowingRoundedRectOutlineProvider(
    private val view: View,
    private val verticalOriginFraction: Float = 0.5f // 0 = top, 1 = bottom
) : ViewOutlineProvider() {

    var fraction: Float = 0f
        set(value) {
            field = value
            view.invalidateOutline()
        }

    override fun getOutline(v: View, outline: Outline) {
        val w = v.width
        val h = v.height
        if (w == 0 || h == 0 || fraction <= 0f) {
            outline.setEmpty()
            return
        }

        // Horizontal origin is always center
        val centerX = w / 2

        // Vertical origin based on [0, 1] parameter
        val centerY = (h * verticalOriginFraction).toInt()

        // Grow rect from this origin
        val halfWidth = (w * fraction / 2f).toInt()
        val halfHeight = (h * fraction / 2f).toInt()

        val left = centerX - halfWidth
        val top = centerY - halfHeight
        val right = centerX + halfWidth
        val bottom = centerY + halfHeight

        // Corner radius grows with fraction
        val maxRadius = min(w, h) / 2f
        val radius = maxRadius * fraction

        outline.setRoundRect(left, top, right, bottom, radius)
    }
}