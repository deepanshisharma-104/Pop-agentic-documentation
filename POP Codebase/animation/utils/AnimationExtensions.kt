package com.pop.components.animation.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnPreDraw

fun Activity.rootContentView(): View? {
    val content = window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
    return content?.getChildAt(0)
}

fun View.changeViewScale(scale: Float) {
    pivotX = width / 2f
    pivotY = height.toFloat()
    scaleX = scale
    scaleY = scale
}

fun View.scaleDownView(
    scale: Float = 0.95f, duration: Int
) {
    // Optional: improve animation performance
    setLayerType(View.LAYER_TYPE_HARDWARE, null)
    pivotX = width / 2f
    pivotY = height / 2f
    animate().scaleX(scale).scaleY(scale).setDuration(duration.toLong()).setInterpolator(android.view.animation.AccelerateDecelerateInterpolator()).withEndAction {
        // Keep hardware layer only if you want
        setLayerType(View.LAYER_TYPE_NONE, null)
    }.start()
}

fun View.resetViewScale(
    duration: Int
) {
    setLayerType(View.LAYER_TYPE_HARDWARE, null)
    pivotX = width / 2f
    pivotY = height / 2f
    animate().scaleX(1f).scaleY(1f).setDuration(duration.toLong()).setInterpolator(android.view.animation.AccelerateDecelerateInterpolator()).withEndAction {
        setLayerType(View.LAYER_TYPE_NONE, null)
    }.start()
}

fun View.startMaskAnimation(
    maskRevealAnimator: Animator?,
    animationDuration: Int,
    verticalOrigin: Float,
    isEnter: Boolean,
    updateMaskRevealAnimator: (Animator?) -> Unit,
    progressCallback: ((Float) -> Unit)? = null
) {
    val view = this
    if (!view.isAttachedToWindow) return

    maskRevealAnimator?.cancel()
    updateMaskRevealAnimator(null)

    if (view.width == 0 || view.height == 0) {
        view.post {
            if (view.width > 0 && view.height > 0 && view.isAttachedToWindow) {
                startMaskAnimation(maskRevealAnimator, animationDuration, verticalOrigin, isEnter, updateMaskRevealAnimator, progressCallback)
            }
        }
        return
    }

    // Setup clipping
    val provider = GrowingRoundedRectOutlineProvider(view, verticalOrigin)
    view.outlineProvider = provider
    view.clipToOutline = true

    val startValue = if (isEnter) 0f else 1f
    val endValue = if (isEnter) 1f else 0f

    val animator = ValueAnimator.ofFloat(startValue, endValue).apply {
        duration = animationDuration.toLong()
        interpolator = LinearInterpolator()

        addUpdateListener { va ->
            val fraction = va.animatedValue as Float
            provider.fraction = fraction
            progressCallback?.invoke(fraction)   // 🔥 Callback here
        }

        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                view.clipToOutline = false
                view.outlineProvider = null
                updateMaskRevealAnimator(null)
            }

            override fun onAnimationCancel(animation: Animator) {
                view.clipToOutline = false
                view.outlineProvider = null
                updateMaskRevealAnimator(null)
            }
        })
    }

    updateMaskRevealAnimator(animator)
    animator.start()
}

fun Dialog.rootContentView(): View? {
    val content = window?.decorView?.findViewById<ViewGroup>(android.R.id.content)
    return content?.getChildAt(0)
}

fun Dialog.scaleDownScreen(
    scale: Float = 0.95f,
    duration: Long = 400L,
    onScaleProgress: ((Float) -> Unit)? = null
) {
    val root = rootContentView() ?: return

    // Ensure we have width/height for proper pivot
    if (root.width == 0 || root.height == 0) {
        root.doOnPreDraw { root ->
            root.runScaleDownAnimation(scale, duration, onScaleProgress)
        }
    } else {
        root.runScaleDownAnimation(scale, duration, onScaleProgress)
    }
}

fun Dialog.resetScreenScale(
    duration: Long = 400L,
    onScaleProgress: ((Float) -> Unit)? = null,
    onScaleComplete: (() -> Unit)? = null
) {
    val root = rootContentView() ?: return

    // If already reset, fire callbacks immediately
    if (root.scaleX == 1f) {
        onScaleProgress?.invoke(1f)
        onScaleComplete?.invoke()
        return
    }

    if (root.width == 0 || root.height == 0) {
        root.doOnPreDraw {
            root.runResetScaleAnimation(duration, onScaleProgress, onScaleComplete)
        }
    } else {
        root.runResetScaleAnimation(duration, onScaleProgress, onScaleComplete)
    }
}

fun View.runScaleDownAnimation(
    targetScale: Float,
    duration: Long,
    onScaleProgress: ((Float) -> Unit)?,
    startScale: Float = 1.0f
) {
    val root = this
    root.setLayerType(View.LAYER_TYPE_HARDWARE, null)

    // Bottom-center pivot: scales from left, right, and bottom
    root.pivotX = root.width / 2f
    root.pivotY = root.height.toFloat()

    val animator = ValueAnimator.ofFloat(startScale, targetScale).apply {
        this.duration = duration
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener { va ->
            val s = va.animatedValue as Float
            root.scaleX = s
            root.scaleY = s
            onScaleProgress?.invoke(s)
        }
        doOnEnd {
            root.setLayerType(View.LAYER_TYPE_NONE, null)
        }
    }
    animator.start()
}

fun View.runResetScaleAnimation(
    duration: Long,
    onScaleProgress: ((Float) -> Unit)?,
    onScaleComplete: (() -> Unit)?
) {
    val root = this
    root.setLayerType(View.LAYER_TYPE_HARDWARE, null)

    root.pivotX = root.width / 2f
    root.pivotY = root.height.toFloat()

    val startScale = root.scaleX // assume X == Y
    val endScale = 1f

    val animator = ValueAnimator.ofFloat(startScale, endScale).apply {
        this.duration = duration
        interpolator = AccelerateDecelerateInterpolator()
        addUpdateListener { va ->
            val s = va.animatedValue as Float
            root.scaleX = s
            root.scaleY = s
            onScaleProgress?.invoke(s) // send current scale to parent
        }
        doOnEnd {
            root.setLayerType(View.LAYER_TYPE_NONE, null)
            // Ensure final state & notify completion
            onScaleProgress?.invoke(endScale)
            onScaleComplete?.invoke()
        }
    }
    animator.start()
}
