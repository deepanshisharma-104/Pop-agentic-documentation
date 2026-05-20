package com.pop.components.xml_components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.pop.compose_components.R

/**
 * Pop Design System - Divider View (XML)
 *
 * A View component for using Pop dividers in XML layouts.
 * Supports both horizontal and vertical orientations with solid and dashed styles.
 *
 * Usage in XML:
 * ```xml
 * <com.pop.components.xml_components.PopDividerView
 *     android:layout_width="match_parent"
 *     android:layout_height="wrap_content"
 *     app:dividerOrientation="horizontal"
 *     app:dividerStyle="solid" />
 * ```
 */
class PopDividerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    /**
     * Divider orientation
     */
    enum class Orientation {
        HORIZONTAL,
        VERTICAL
    }

    /**
     * Divider style
     */
    enum class Style {
        SOLID,
        DASHED
    }

    private var orientation: Orientation = Orientation.HORIZONTAL
    private var style: Style = Style.SOLID

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PopDividerView,
            0,
            0
        ).apply {
            try {
                val orientationValue = getInt(
                    R.styleable.PopDividerView_dividerOrientation,
                    0
                )
                orientation = Orientation.values()[orientationValue]

                val styleValue = getInt(
                    R.styleable.PopDividerView_dividerStyle,
                    0
                )
                style = Style.values()[styleValue]
            } finally {
                recycle()
            }
        }

        updateBackground()
    }

    /**
     * Set the divider orientation
     */
    fun setOrientation(orientation: Orientation) {
        this.orientation = orientation
        updateBackground()
        requestLayout()
    }

    /**
     * Set the divider style
     */
    fun setStyle(style: Style) {
        this.style = style
        updateBackground()
        invalidate()
    }

    private fun updateBackground() {
        val drawableRes = when {
            orientation == Orientation.HORIZONTAL && style == Style.SOLID ->
                R.drawable.pop_divider_solid_horizontal
            orientation == Orientation.HORIZONTAL && style == Style.DASHED ->
                R.drawable.pop_divider_dashed_horizontal
            orientation == Orientation.VERTICAL && style == Style.SOLID ->
                R.drawable.pop_divider_solid_vertical
            orientation == Orientation.VERTICAL && style == Style.DASHED ->
                R.drawable.pop_divider_dashed_vertical
            else -> R.drawable.pop_divider_solid_horizontal
        }
        setBackgroundResource(drawableRes)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val strokeWidth = resources.getDimensionPixelSize(R.dimen.pop_stroke_default)
        
        when (orientation) {
            Orientation.HORIZONTAL -> {
                val width = MeasureSpec.getSize(widthMeasureSpec)
                setMeasuredDimension(width, strokeWidth)
            }
            Orientation.VERTICAL -> {
                val height = MeasureSpec.getSize(heightMeasureSpec)
                setMeasuredDimension(strokeWidth, height)
            }
        }
    }
}

