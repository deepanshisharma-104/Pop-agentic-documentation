package com.pop.components.xml_components

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.pop.compose_components.R
import com.google.android.material.button.MaterialButton

class PopButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.materialButtonStyle
) : MaterialButton(context, attrs, defStyleAttr) {

    enum class Variant { PRIMARY, SECONDARY, TERTIARY }
    enum class Size { SMALL, MEDIUM, LARGE }

    var variant: Variant = Variant.PRIMARY
        set(value) {
            field = value
            updateBackground()
            updateTextColor()
        }

    var size: Size = Size.LARGE
        set(value) {
            field = value
            updateSize()
        }

    private var originalText: CharSequence? = null
    private var loadingDrawable: LoadingDrawable? = null
    private var rotationAnimator: ValueAnimator? = null

    var isLoading: Boolean = false
        set(value) {
            field = value
            updateLoadingState()
        }

    var ifEnabled: Boolean = true
        set(value) {
            field = value
            updateBackground()
            updateTextColor()
        }

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.FlashButtonView,
            0, 0
        ).apply {
            try {
                val variantIndex = getInt(R.styleable.FlashButtonView_flashButtonVariant, 0)
                variant = Variant.values().getOrElse(variantIndex) { Variant.PRIMARY }

                val sizeIndex = getInt(R.styleable.FlashButtonView_flashButtonSize, 1)
                size = Size.values().getOrElse(sizeIndex) { Size.MEDIUM }

                val initialLoading =
                    getBoolean(R.styleable.FlashButtonView_flashButtonLoading, false)

                // Handle new text size in dp attribute
                if (hasValue(R.styleable.FlashButtonView_flashButtonTextSizeDp)) {
                    val textSizeDp =
                        getDimension(R.styleable.FlashButtonView_flashButtonTextSizeDp, -1f)
                    if (textSizeDp > 0) {
                        setTextSizeDp(textSizeDp / resources.displayMetrics.density) // convert px to dp
                    }
                }

                // Handle new text style attribute
                if (hasValue(R.styleable.FlashButtonView_flashButtonTextStyle)) {
                    val style = getInt(R.styleable.FlashButtonView_flashButtonTextStyle, 0)
                    setTextBold(style == 1)
                }



                // Initialize originalText first, then set loading state
                post {
                    updateBackgroundForEnabledState(isEnabled)
                    updateTextColorForEnabledState(isEnabled)

                    if (originalText == null) {
                        originalText = text
                    }
                    if (initialLoading) {
                        isLoading = true
                    }
                }
            } finally {
                recycle()
            }
        }

        setTextAppearance(R.style.TextAppearance_Flash_Heading_H4)
        stateListAnimator = null
        cornerRadius = resources.getDimensionPixelSize(R.dimen.height_32)

        updateBackground()
        updateTextColor()
        updateSize()
        setupLoadingDrawable()
    }

    private fun updateBackground() {
        backgroundTintList = if (ifEnabled) {
            when (variant) {
                Variant.PRIMARY -> ContextCompat.getColorStateList(
                    context,
                    R.color.button_primary_background
                )

                Variant.SECONDARY -> ContextCompat.getColorStateList(
                    context,
                    R.color.button_secondary_background
                )

                Variant.TERTIARY -> ContextCompat.getColorStateList(
                    context,
                    R.color.button_tertiary_background
                )
            }
        } else {
            when (variant) {
                Variant.PRIMARY -> ContextCompat.getColorStateList(context, R.color.orange_4)
                Variant.SECONDARY -> ContextCompat.getColorStateList(
                    context,
                    R.color.neutral_21_white
                )

                Variant.TERTIARY -> ContextCompat.getColorStateList(
                    context,
                    R.color.neutral_7_white
                )
            }
        }
    }

    private fun updateTextColor() {
        setTextColor(
            if (ifEnabled) {
                when (variant) {
                    Variant.PRIMARY -> ContextCompat.getColorStateList(
                        context,
                        R.color.button_primary_text
                    )

                    Variant.SECONDARY -> ContextCompat.getColorStateList(
                        context,
                        R.color.button_secondary_text
                    )

                    Variant.TERTIARY -> ContextCompat.getColorStateList(
                        context,
                        R.color.button_tertiary_text
                    )
                }
            } else {
                when (variant) {
                    Variant.PRIMARY -> ContextCompat.getColorStateList(context, R.color.orange_8)
                    Variant.SECONDARY -> ContextCompat.getColorStateList(
                        context,
                        R.color.neutral_10
                    )

                    Variant.TERTIARY -> ContextCompat.getColorStateList(
                        context,
                        R.color.neutral_7_white
                    )
                }
            }

        )
    }

    private fun updateSize() {
        val heightRes = when (size) {
            Size.SMALL -> R.dimen.button_height_small
            Size.MEDIUM -> R.dimen.button_height_medium
            Size.LARGE -> R.dimen.button_height_large
        }
        val heightPx = resources.getDimensionPixelSize(heightRes)
        minimumHeight = heightPx
        height = heightPx
        isAllCaps = false
        insetTop = 0
        insetBottom = 0
        val paddingHorizontal = resources.getDimensionPixelSize(
            when (size) {
                Size.SMALL -> R.dimen.button_padding_default
                Size.MEDIUM, Size.LARGE -> R.dimen.button_padding_default
            }
        )
        setPadding(paddingHorizontal, 0, paddingHorizontal, 0)

        val textSizeSp = when (size) {
            Size.SMALL -> 13f
            Size.MEDIUM -> 15f
            Size.LARGE -> 15f
        }
        setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp)

        // ✅ Enable auto-shrink if text doesn't fit
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
            this,
            10, // minimum size it can shrink to (SP)
            textSizeSp.toInt(), // maximum = your fixed size
            1,  // step size in SP
            TypedValue.COMPLEX_UNIT_SP
        )
        // Ensure text stays in one line and shrinks instead of wrapping
        maxLines = 1
        isSingleLine = true
        ellipsize = null
    }

    private fun setupLoadingDrawable() {
        val size = (textSize * 1.2f).toInt()
        loadingDrawable = LoadingDrawable(currentTextColor, size)
    }

    private class LoadingDrawable(private val color: Int, private val size: Int) : Drawable() {
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = this@LoadingDrawable.color
            style = Paint.Style.STROKE
            strokeWidth = 3f
            strokeCap = Paint.Cap.ROUND
        }
        private var rotation = 0f

        init {
            setBounds(0, 0, size, size)
        }

        override fun draw(canvas: Canvas) {
            val centerX = bounds.centerX().toFloat()
            val centerY = bounds.centerY().toFloat()
            val radius = (size / 2f) - paint.strokeWidth

            canvas.save()
            canvas.rotate(rotation, centerX, centerY)

            // Draw loading arc
            val rect = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)
            canvas.drawArc(rect, 0f, 270f, false, paint)

            canvas.restore()
        }

        override fun setAlpha(alpha: Int) {
            paint.alpha = alpha
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            paint.colorFilter = colorFilter
        }

        override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

        fun setRotation(degrees: Float) {
            rotation = degrees
            invalidateSelf()
        }
    }

    private fun updateLoadingState() {
        if (isLoading) {
            if (originalText == null) {
                originalText = text
            }
            text = ""
            isClickable = false

            // Start rotation animation
            startLoadingAnimation()
        } else {
            text = originalText ?: ""
            isClickable = true

            // Stop rotation animation
            stopLoadingAnimation()
        }

        // Trigger redraw to show/hide spinner
        invalidate()
    }

    private fun startLoadingAnimation() {
        rotationAnimator?.cancel()

        rotationAnimator = ValueAnimator.ofFloat(0f, 360f).apply {
            setDuration(1200)
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            interpolator = LinearInterpolator()

            addUpdateListener { animator ->
                val rotation = animator.animatedValue as Float
                loadingDrawable?.setRotation(rotation)
            }
        }
        rotationAnimator?.start()
    }

    private fun stopLoadingAnimation() {
        rotationAnimator?.cancel()
        rotationAnimator = null
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw loading spinner centered when in loading state
        if (isLoading && loadingDrawable != null) {
            val centerX = width / 2
            val centerY = height / 2
            val drawableSize = loadingDrawable!!.intrinsicWidth

            loadingDrawable!!.setBounds(
                centerX - drawableSize / 2,
                centerY - drawableSize / 2,
                centerX + drawableSize / 2,
                centerY + drawableSize / 2
            )
            loadingDrawable!!.draw(canvas)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopLoadingAnimation()
    }

    fun setSpannableText(spannable: Spannable) {
        text = spannable
    }

    /**
     * Sets the text size in dp (density-independent pixels).
     * @param dp The text size in dp.
     */
    fun setTextSizeDp(dp: Float) {
        val scaledSize = dp * resources.displayMetrics.density
        textSize = scaledSize
    }

    /**
     * Sets the text style to normal or bold.
     * @param isBold If true, sets text to bold; otherwise, normal.
     */
    fun setTextBold(isBold: Boolean) {
        paint.isFakeBoldText = isBold
        invalidate()
    }

    fun enable(enabled: Boolean) {
        super.setEnabled(enabled)
        alpha = if (enabled) 1f else 0.3f  // dims entire button
        isClickable = enabled
        isFocusable = enabled
        invalidate()
    }

    private fun updateBackgroundForEnabledState(enabled: Boolean) {
        val safeVariant = variant ?: Variant.PRIMARY
        backgroundTintList = if (enabled) {
            when (safeVariant) {
                Variant.PRIMARY -> ContextCompat.getColorStateList(
                    context,
                    R.color.button_primary_background
                )

                Variant.SECONDARY -> ContextCompat.getColorStateList(
                    context,
                    R.color.button_secondary_background
                )

                Variant.TERTIARY -> ContextCompat.getColorStateList(
                    context,
                    R.color.button_tertiary_background
                )
            }
        } else {
            when (safeVariant) {
                Variant.PRIMARY -> ContextCompat.getColorStateList(
                    context,
                    R.color.surfaces_primary_accent_disabled
                )

                Variant.SECONDARY -> ContextCompat.getColorStateList(
                    context,
                    R.color.surfaces_secondary_disabled
                )

                Variant.TERTIARY -> ContextCompat.getColorStateList(
                    context,
                    R.color.surfaces_tertiary_disabled
                )
            }
        }
    }


    private fun updateTextColorForEnabledState(enabled: Boolean) {
        val safeVariant = variant ?: Variant.PRIMARY
        setTextColor(
            if (enabled) {
                when (safeVariant) {
                    Variant.PRIMARY -> ContextCompat.getColorStateList(
                        context,
                        R.color.button_primary_text
                    )

                    Variant.SECONDARY -> ContextCompat.getColorStateList(
                        context,
                        R.color.button_secondary_text
                    )

                    Variant.TERTIARY -> ContextCompat.getColorStateList(
                        context,
                        R.color.button_tertiary_text
                    )
                }
            } else {
                when (safeVariant) {
                    Variant.PRIMARY -> ContextCompat.getColorStateList(
                        context,
                        R.color.text_primary_accent_disabled
                    )

                    Variant.SECONDARY -> ContextCompat.getColorStateList(
                        context,
                        R.color.text_secondary_disabled
                    )

                    Variant.TERTIARY -> ContextCompat.getColorStateList(
                        context,
                        R.color.text_tertiary_disabled
                    )
                }
            }
        )
    }


    fun setCustomBackgroundColour(@ColorRes colourId:Int) {
        val safeVariant = variant ?: Variant.PRIMARY
        backgroundTintList = ContextCompat.getColorStateList(
            context,
            colourId
        )
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        updateBackgroundForEnabledState(enabled)
        updateTextColorForEnabledState(enabled)
    }


}