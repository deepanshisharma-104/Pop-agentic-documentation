package com.pop.components.xml_components

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var fillColor: Int = Color.RED
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val radius = min(width, height) / 2f
        val cx = width / 2f
        val cy = height / 2f

        paint.color = fillColor
        canvas.drawCircle(cx, cy, radius, paint)
    }

    fun setFillColor(color: Int) {
        fillColor = color
        invalidate() // Redraw with new color
    }
}
