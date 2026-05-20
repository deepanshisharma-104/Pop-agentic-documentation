package com.pop.components.shimmering

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ComposeShader
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.Rect
import android.graphics.Shader
import android.os.Build
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.pop.compose_components.R
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class ShimmerLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    private var maskOffsetX = 0
    private var maskRect: Rect? = null
    private var gradientTexturePaint: Paint? = null
    private var maskAnimator: ValueAnimator? = null

    private var localMaskBitmap: Bitmap? = null
    private var maskBitmap: Bitmap? = null
    private var canvasForShimmerMask: Canvas? = null

    private var isAnimationReversed = false
    private var isAnimationStarted = false
    private var autoStart = false
    private var shimmerAnimationDuration = 0
    private var shimmerColor = 0
    private var shimmerAngle = 0
    private var maskWidth = 0f
    private var gradientCenterColorWidth = 0f

    private var startAnimationPreDrawListener: ViewTreeObserver.OnPreDrawListener? = null

    init {
        setWillNotDraw(false)

        val a = context.getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.ShimmerLayout,
            0, 0
        )

        try {
            shimmerAngle =
                a.getInteger(R.styleable.ShimmerLayout_shimmer_angle, DEFAULT_ANGLE.toInt())
            shimmerAnimationDuration = a.getInteger(
                R.styleable.ShimmerLayout_shimmer_animation_duration,
                DEFAULT_ANIMATION_DURATION
            )
            shimmerColor =
                a.getColor(R.styleable.ShimmerLayout_shimmer_color, getColor(R.color.neutral_8))
            autoStart = a.getBoolean(R.styleable.ShimmerLayout_shimmer_auto_start, false)
            maskWidth = a.getFloat(R.styleable.ShimmerLayout_shimmer_mask_width, 0.5f)
            gradientCenterColorWidth =
                a.getFloat(R.styleable.ShimmerLayout_shimmer_gradient_center_color_width, 0.1f)
            isAnimationReversed =
                a.getBoolean(R.styleable.ShimmerLayout_shimmer_reverse_animation, false)
            val layoutResId = a.getResourceId(R.styleable.ShimmerLayout_layout, -1)
            if (layoutResId != -1) {
                inflate(context, layoutResId, this)
            } else {
                // Done this until we get a default shimmer layout. Post we can inflate the default layout for shimmer.
            }

        } finally {
            a.recycle()
        }

        setMaskWidth(maskWidth)
        setGradientCenterColorWidth(gradientCenterColorWidth)
        setShimmerAngle(shimmerAngle)
        if (autoStart && getVisibility() == VISIBLE) {
            startShimmerAnimation()
        }
    }

    override fun onDetachedFromWindow() {
        resetShimmering()
        super.onDetachedFromWindow()
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (!isAnimationStarted || getWidth() <= 0 || getHeight() <= 0) {
            super.dispatchDraw(canvas)
        } else {
            dispatchDrawShimmer(canvas)
        }
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == VISIBLE) {
            if (autoStart) {
                startShimmerAnimation()
            }
        } else {
            stopShimmerAnimation()
        }
    }

    fun startShimmerAnimation() {
        if (isAnimationStarted) {
            return
        }

        if (getWidth() == 0) {
            startAnimationPreDrawListener = object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    getViewTreeObserver().removeOnPreDrawListener(this)
                    startShimmerAnimation()

                    return true
                }
            }

            getViewTreeObserver().addOnPreDrawListener(startAnimationPreDrawListener)

            return
        }

        val animator = this.shimmerAnimation
        animator.start()
        isAnimationStarted = true
    }

    fun stopShimmerAnimation() {
        if (startAnimationPreDrawListener != null) {
            getViewTreeObserver().removeOnPreDrawListener(startAnimationPreDrawListener)
        }

        resetShimmering()
    }

    fun setShimmerColor(shimmerColor: Int) {
        this.shimmerColor = shimmerColor
        resetIfStarted()
    }

    fun setShimmerAnimationDuration(durationMillis: Int) {
        this.shimmerAnimationDuration = durationMillis
        resetIfStarted()
    }

    fun setAnimationReversed(animationReversed: Boolean) {
        this.isAnimationReversed = animationReversed
        resetIfStarted()
    }

    /**
     * Set the angle of the shimmer effect in clockwise direction in degrees.
     * The angle must be between {@value #MIN_ANGLE_VALUE} and {@value #MAX_ANGLE_VALUE}.
     *
     * @param angle The angle to be set
     */
    fun setShimmerAngle(angle: Int) {
        require(!(angle < MIN_ANGLE_VALUE || MAX_ANGLE_VALUE < angle)) {
            String.format(
                "shimmerAngle value must be between %d and %d",
                MIN_ANGLE_VALUE,
                MAX_ANGLE_VALUE
            )
        }
        this.shimmerAngle = angle
        resetIfStarted()
    }

    /**
     * Sets the width of the shimmer line to a value higher than 0 to less or equal to 1.
     * 1 means the width of the shimmer line is equal to half of the width of the ShimmerLayout.
     * The default value is 0.5.
     *
     * @param maskWidth The width of the shimmer line.
     */
    fun setMaskWidth(maskWidth: Float) {
        require(!(maskWidth <= MIN_MASK_WIDTH_VALUE || MAX_MASK_WIDTH_VALUE < maskWidth)) {
            String.format(
                "maskWidth value must be higher than %d and less or equal to %d",
                MIN_MASK_WIDTH_VALUE, MAX_MASK_WIDTH_VALUE
            )
        }

        this.maskWidth = maskWidth
        resetIfStarted()
    }

    /**
     * Sets the width of the center gradient color to a value higher than 0 to less than 1.
     * 0.99 means that the whole shimmer line will have this color with a little transparent edges.
     * The default value is 0.1.
     *
     * @param gradientCenterColorWidth The width of the center gradient color.
     */
    fun setGradientCenterColorWidth(gradientCenterColorWidth: Float) {
        require(
            !(gradientCenterColorWidth <= MIN_GRADIENT_CENTER_COLOR_WIDTH_VALUE
                    || MAX_GRADIENT_CENTER_COLOR_WIDTH_VALUE <= gradientCenterColorWidth)
        ) {
            String.format(
                "gradientCenterColorWidth value must be higher than %d and less than %d",
                MIN_GRADIENT_CENTER_COLOR_WIDTH_VALUE, MAX_GRADIENT_CENTER_COLOR_WIDTH_VALUE
            )
        }

        this.gradientCenterColorWidth = gradientCenterColorWidth
        resetIfStarted()
    }

    private fun resetIfStarted() {
        if (isAnimationStarted) {
            resetShimmering()
            startShimmerAnimation()
        }
    }

    private fun dispatchDrawShimmer(canvas: Canvas) {
        super.dispatchDraw(canvas)

        localMaskBitmap = getMaskBitmap()
        if (localMaskBitmap == null) {
            return
        }

        if (canvasForShimmerMask == null) {
            canvasForShimmerMask = Canvas(localMaskBitmap!!)
        }

        canvasForShimmerMask!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        canvasForShimmerMask!!.save()
        canvasForShimmerMask!!.translate(-maskOffsetX.toFloat(), 0f)

        super.dispatchDraw(canvasForShimmerMask!!)

        canvasForShimmerMask!!.restore()

        drawShimmer(canvas)

        localMaskBitmap = null
    }

    private fun drawShimmer(destinationCanvas: Canvas) {
        createShimmerPaint()

        destinationCanvas.save()

        destinationCanvas.translate(maskOffsetX.toFloat(), 0f)
        destinationCanvas.drawRect(
            maskRect!!.left.toFloat(),
            0f,
            maskRect!!.width().toFloat(),
            maskRect!!.height().toFloat(),
            gradientTexturePaint!!
        )

        destinationCanvas.restore()
    }

    private fun resetShimmering() {
        if (maskAnimator != null) {
            maskAnimator!!.end()
            maskAnimator!!.removeAllUpdateListeners()
        }

        maskAnimator = null
        gradientTexturePaint = null
        isAnimationStarted = false

        releaseBitMaps()
    }

    private fun releaseBitMaps() {
        canvasForShimmerMask = null

        if (maskBitmap != null) {
            maskBitmap!!.recycle()
            maskBitmap = null
        }
    }

    private fun getMaskBitmap(): Bitmap? {
        if (maskBitmap == null) {
            maskBitmap = createBitmap(maskRect!!.width(), getHeight())
        }

        return maskBitmap
    }

    private fun createShimmerPaint() {
        if (gradientTexturePaint != null) {
            return
        }

        val edgeColor = reduceColorAlphaValueToZero(shimmerColor)
        val shimmerLineWidth = getWidth() / 2 * maskWidth
        val yPosition = (if (0 <= shimmerAngle) getHeight() else 0).toFloat()

        val gradient = LinearGradient(
            0f, yPosition,
            cos(Math.toRadians(shimmerAngle.toDouble())).toFloat() * shimmerLineWidth,
            yPosition + sin(Math.toRadians(shimmerAngle.toDouble())).toFloat() * shimmerLineWidth,
            intArrayOf(edgeColor, shimmerColor, shimmerColor, edgeColor),
            this.gradientColorDistribution,
            Shader.TileMode.CLAMP
        )

        val maskBitmapShader =
            BitmapShader(localMaskBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val composeShader = ComposeShader(gradient, maskBitmapShader, PorterDuff.Mode.DST_IN)

        gradientTexturePaint = Paint()
        gradientTexturePaint!!.setAntiAlias(true)
        gradientTexturePaint!!.setDither(true)
        gradientTexturePaint!!.setFilterBitmap(true)
        gradientTexturePaint!!.setShader(composeShader)
    }

    private val shimmerAnimation: Animator
        get() {
            if (maskAnimator != null) {
                return maskAnimator!!
            }

            if (maskRect == null) {
                maskRect = calculateBitmapMaskRect()
            }

            val animationToX = getWidth()
            val animationFromX: Int

            if (getWidth() > maskRect!!.width()) {
                animationFromX = -animationToX
            } else {
                animationFromX = -maskRect!!.width()
            }

            val shimmerBitmapWidth = maskRect!!.width()
            val shimmerAnimationFullLength = animationToX - animationFromX

            maskAnimator = if (isAnimationReversed)
                ValueAnimator.ofInt(shimmerAnimationFullLength, 0)
            else
                ValueAnimator.ofInt(0, shimmerAnimationFullLength)
            maskAnimator!!.setDuration(shimmerAnimationDuration.toLong())
            maskAnimator!!.setRepeatCount(ObjectAnimator.INFINITE)

            maskAnimator!!.addUpdateListener(object :
                ValueAnimator.AnimatorUpdateListener {
                override fun onAnimationUpdate(animation: ValueAnimator) {
                    maskOffsetX = animationFromX + animation.getAnimatedValue() as Int

                    if (maskOffsetX + shimmerBitmapWidth >= 0) {
                        invalidate()
                    }
                }
            })

            return maskAnimator!!
        }

    private fun createBitmap(width: Int, height: Int): Bitmap? {
        try {
            return Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
        } catch (e: OutOfMemoryError) {
            System.gc()

            return null
        }
    }

    private fun getColor(id: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().getColor(id)
        } else {
            return getResources().getColor(id)
        }
    }

    private fun reduceColorAlphaValueToZero(actualColor: Int): Int {
        return Color.argb(
            0,
            Color.red(actualColor),
            Color.green(actualColor),
            Color.blue(actualColor)
        )
    }

    private fun calculateBitmapMaskRect(): Rect {
        return Rect(0, 0, calculateMaskWidth(), getHeight())
    }

    private fun calculateMaskWidth(): Int {
        val shimmerLineBottomWidth =
            (getWidth() / 2 * maskWidth) / cos(Math.toRadians(abs(shimmerAngle).toDouble()))
        val shimmerLineRemainingTopWidth =
            getHeight() * tan(Math.toRadians(abs(shimmerAngle).toDouble()))

        return (shimmerLineBottomWidth + shimmerLineRemainingTopWidth).toInt()
    }

    private val gradientColorDistribution: FloatArray
        get() {
            val colorDistribution = FloatArray(4)

            colorDistribution[0] = 0f
            colorDistribution[3] = 1f

            colorDistribution[1] = 0.5f - gradientCenterColorWidth / 2f
            colorDistribution[2] = 0.5f + gradientCenterColorWidth / 2f

            return colorDistribution
        }

    companion object {
        private const val DEFAULT_ANIMATION_DURATION = 1500

        private const val DEFAULT_ANGLE: Byte = 20

        private val MIN_ANGLE_VALUE: Byte = -45
        private const val MAX_ANGLE_VALUE: Byte = 45
        private const val MIN_MASK_WIDTH_VALUE: Byte = 0
        private const val MAX_MASK_WIDTH_VALUE: Byte = 1

        private const val MIN_GRADIENT_CENTER_COLOR_WIDTH_VALUE: Byte = 0
        private const val MAX_GRADIENT_CENTER_COLOR_WIDTH_VALUE: Byte = 1
    }
}