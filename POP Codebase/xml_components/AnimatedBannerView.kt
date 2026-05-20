package com.pop.components.xml_components

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.PathInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener

class AnimatedBannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var contentView: View? = null
    private var bannerAnimator: AnimatorSet? = null
    private var bannerHideRunnable: Runnable? = null
    private var preDrawListener: ViewTreeObserver.OnPreDrawListener? = null

    init {
        clipChildren = false
        clipToPadding = false
        visibility = GONE
    }

    /**
     * Sets the content view for this banner. This can be any View or ViewGroup.
     * The view will be added to this FrameLayout with MATCH_PARENT width and WRAP_CONTENT height.
     */
    private fun setContentView(view: View) {
        // Remove existing content
        contentView?.let { removeView(it) }

        contentView = view.apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        }

        addView(contentView)
    }

    /**
     * Sets the content view from a layout resource ID.
     */
    private fun setContentView(layoutResId: Int) {
        val view = inflate(context, layoutResId, null)
        setContentView(view)
    }

    /**
     * Sets an ImageView as the content view and loads an image using Glide.
     * This is similar to the older implementation for image-based banners.
     *
     * @param imageUrl The URL of the image to load. If null or empty, uses the default placeholder.
     * @param visibleDuration How long the banner should remain visible (in milliseconds)
     * @param animationDuration How long the animation should take (in milliseconds)
     */
    fun showImage(
        imageUrl: String? = null,
        visibleDuration: Long = 3000L,
        animationDuration: Long = 800L
    ) {
        val imageView = ImageView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
            adjustViewBounds = true
        }

        setContentView(imageView)

        cancelPendingOperations()

        Glide.with(imageView).clear(imageView)

        alpha = 0f
        translationY = 0f
        visibility = VISIBLE
        imageView.visibility = INVISIBLE

        if (imageUrl.isNullOrEmpty()) {
            imageView.visibility = VISIBLE
            waitForLayoutThenAnimate(visibleDuration, animationDuration)
            return
        }

        Glide.with(imageView)
            .load(imageUrl)
            .dontAnimate()
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable?>, isFirstResource: Boolean): Boolean {
                    if (!isAttachedToWindow) return false
                    post {
                        if (isAttachedToWindow) {
                            imageView.visibility = VISIBLE
                            waitForLayoutThenAnimate(visibleDuration, animationDuration)
                        }
                    }
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: com.bumptech.glide.request.target.Target<Drawable?>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    if (!isAttachedToWindow) return false
                    post {
                        if (isAttachedToWindow) {
                            imageView.visibility = VISIBLE
                            waitForLayoutThenAnimate(visibleDuration, animationDuration)
                        }
                    }
                    return false
                }
            })
            .into(imageView)
    }

    /**
     * Shows the banner with the provided content view.
     *
     * @param contentView The view to display in the banner
     * @param visibleDuration How long the banner should remain visible (in milliseconds)
     * @param animationDuration How long the animation should take (in milliseconds)
     */
    fun show(
        contentView: View,
        visibleDuration: Long = 3000L,
        animationDuration: Long = 800L
    ) {
        setContentView(contentView)
        showInternal(visibleDuration, animationDuration)
    }

    /**
     * Shows the banner with the provided layout resource.
     *
     * @param layoutResId The layout resource ID to inflate and display
     * @param visibleDuration How long the banner should remain visible (in milliseconds)
     * @param animationDuration How long the animation should take (in milliseconds)
     */
    fun show(
        @LayoutRes
        layoutResId: Int,
        visibleDuration: Long = 3000L,
        animationDuration: Long = 800L
    ) {
        setContentView(layoutResId)
        showInternal(visibleDuration, animationDuration)
    }

    private fun showInternal(
        visibleDuration: Long,
        animationDuration: Long
    ) {
        val content = contentView ?: return

        // cancel previous
        cancelPendingOperations()

        alpha = 0f
        translationY = 0f
        visibility = VISIBLE
        content.visibility = VISIBLE

        waitForLayoutThenAnimate(visibleDuration, animationDuration)
    }

    /**
     * Ensures we only start the animation *after* this view has a non-zero width/height.
     */
    private fun waitForLayoutThenAnimate(
        visibleDuration: Long,
        animationDuration: Long
    ) {
        if (!isAttachedToWindow) return

        if (width > 0 && height > 0) {
            startEnterAnimation(visibleDuration, animationDuration)
            return
        }

        // Remove any existing listener first
        removePreDrawListener()

        val listener = object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                if (!isAttachedToWindow) {
                    removePreDrawListener()
                    return false
                }

                if (width > 0 && height > 0) {
                    removePreDrawListener()
                    startEnterAnimation(visibleDuration, animationDuration)
                    return true
                }
                // keep waiting
                return false
            }
        }

        preDrawListener = listener
        try {
            viewTreeObserver.addOnPreDrawListener(listener)
        } catch (_: IllegalStateException) {
            // ViewTreeObserver is not alive, view might be detached
            preDrawListener = null
        }
    }

    private fun removePreDrawListener() {
        preDrawListener?.let { listener ->
            try {
                viewTreeObserver.removeOnPreDrawListener(listener)
            } catch (_: IllegalStateException) {
                // ViewTreeObserver is not alive, ignore
            }
            preDrawListener = null
        }
    }

    @SuppressLint("Recycle")
    private fun startEnterAnimation(
        visibleDuration: Long,
        animationDuration: Long
    ) {
        val banner = this

        // now width/height are valid (thanks to waitForLayoutThenAnimate)
        val bannerHeight = banner.height.toFloat()

        // Phase 1: start state
        val startTranslationY = -0.5f * bannerHeight
        banner.translationY = startTranslationY
        banner.alpha = 0f
        visibility = VISIBLE

        // circular reveal params
        val cx = banner.width / 2
        val cy = banner.height / 2

        val startRadius = dpToPx(40f) // 80dp diameter
        val finalRadius = hypot(
            banner.width / 2f,
            banner.height / 2f
        )

        val animators = mutableListOf<Animator>()

        val reveal = ViewAnimationUtils.createCircularReveal(
            banner,
            cx,
            cy,
            startRadius,
            finalRadius
        )
        animators += reveal

        val translation = ObjectAnimator.ofFloat(
            banner,
            TRANSLATION_Y,
            startTranslationY,
            0f
        )

        val alpha = ObjectAnimator.ofFloat(
            banner,
            ALPHA,
            0f,
            1f
        )

        animators += translation
        animators += alpha

        val interpolator = PathInterpolator(0.2f, 0f, 0f, 1f)
        // Avoid AnimatorSet#setDuration with RenderNodeAnimator children (circular reveal),
        // which can crash on newer Android versions when total duration is queried mid-run.
        animators.forEach { animator ->
            animator.duration = animationDuration
            animator.interpolator = interpolator
        }

        AnimatorSet().apply {
            playTogether(animators)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    if (isAttachedToWindow) {
                        val hideRunnable = Runnable { hide(animationDuration) }
                        bannerHideRunnable = hideRunnable
                        postDelayed(hideRunnable, visibleDuration)
                    }
                }

                override fun onAnimationCancel(animation: Animator) {
                    bannerAnimator = null
                }
            })
            bannerAnimator = this
            start()
        }
    }

    @SuppressLint("Recycle")
    fun hide(animationDuration: Long) {
        removeCallbacks(bannerHideRunnable)
        bannerHideRunnable = null

        if (visibility != VISIBLE) return

        bannerAnimator?.cancel()
        bannerAnimator = null

        // Validate dimensions before animating
        if (width == 0 || height == 0) {
            visibility = GONE
            translationY = 0f
            alpha = 1f
            return
        }

        val banner = this
        val bannerWidth = banner.width.toFloat()
        val bannerHeight = banner.height.toFloat()

        val animators = mutableListOf<Animator>()

        // slide up (reverse of entry)
        val endTranslationY = -0.5f * bannerHeight
        val translation = ObjectAnimator.ofFloat(
            banner,
            TRANSLATION_Y,
            banner.translationY,
            endTranslationY
        )
        animators += translation

        // fade out
        val alpha = ObjectAnimator.ofFloat(
            banner,
            ALPHA,
            banner.alpha,
            0f
        )
        animators += alpha

        // reverse circular reveal
        val cx = (bannerWidth / 2f).toInt()
        val cy = (bannerHeight / 2f).toInt()

        val startRadius = hypot(bannerWidth / 2f, bannerHeight / 2f)
        val endRadius = dpToPx(40f)

        val conceal = ViewAnimationUtils.createCircularReveal(
            banner,
            cx,
            cy,
            startRadius,
            endRadius
        )
        animators += conceal

        val interpolator = PathInterpolator(0.2f, 0f, 0f, 1f)
        // Avoid AnimatorSet#setDuration with RenderNodeAnimator children (circular reveal),
        // which can crash on newer Android versions when total duration is queried mid-run.
        animators.forEach { animator ->
            animator.duration = animationDuration
            animator.interpolator = interpolator
        }

        AnimatorSet().apply {
            playTogether(animators)
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    visibility = GONE
                    translationY = 0f
                    banner.alpha = 1f
                    bannerAnimator = null
                }

                override fun onAnimationCancel(animation: Animator) {
                    visibility = GONE
                    translationY = 0f
                    banner.alpha = 1f
                    bannerAnimator = null
                }
            })
            bannerAnimator = this
            start()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // Clean up all resources
        removeCallbacks(bannerHideRunnable)
        bannerHideRunnable = null
        bannerAnimator?.cancel()
        bannerAnimator = null
        removePreDrawListener()

        // Clear Glide if content view is an ImageView
        contentView?.let { view ->
            if (view is ImageView) {
                Glide.with(view).clear(view)
            }
        }
    }

    /**
     * Cancels any pending operations (animations and delayed runnables).
     */
    private fun cancelPendingOperations() {
        removeCallbacks(bannerHideRunnable)
        bannerHideRunnable = null
        bannerAnimator?.cancel()
        bannerAnimator = null
    }

    /**
     * Cancels any running or pending banner animation and hides the banner immediately.
     * Call this when the activity goes to background (e.g. from [android.app.Activity.onPause])
     * to avoid RenderNodeAnimator crashes when the app returns to foreground.
     */
    fun cancelAndHide() {
        cancelPendingOperations()
        removePreDrawListener()
        visibility = GONE
        translationY = 0f
        alpha = 1f
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // Set padding based on status bar height when view is attached
        post {
            updatePaddingForStatusBar()
        }
    }

    /**
     * Updates the padding of this view to account for the status bar height.
     * This ensures the banner appears below the status bar.
     */
    private fun updatePaddingForStatusBar() {
        val statusBarHeight = getStatusBarHeight()
        setPadding(0, statusBarHeight, 0, 0)
    }

    /**
     * Gets the status bar height using multiple fallback methods.
     *
     * @return The height of the status bar in pixels, or 0 if unable to determine.
     */
    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    private fun getStatusBarHeight(): Int {
        // 1️⃣ Try WindowInsets from root view (most reliable)
        rootView?.let { root ->
            val insets = ViewCompat.getRootWindowInsets(root)
            val insetHeight = insets?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: 0
            if (insetHeight > 0) return insetHeight
        }

        // 2️⃣ Try WindowInsets from activity's decor view
        val activity = context as? android.app.Activity
        activity?.window?.decorView?.let { decorView ->
            val insets = ViewCompat.getRootWindowInsets(decorView)
            val insetHeight = insets?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: 0
            if (insetHeight > 0) return insetHeight
        }

        // 3️⃣ Fallback to resource-based height (works on all devices)
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            val resourceHeight = resources.getDimensionPixelSize(resourceId)
            if (resourceHeight > 0) return resourceHeight
        }

        // 4️⃣ Final fallback using visible display frame (works in full-screen cases)
        activity?.window?.decorView?.let { decorView ->
            val rect = Rect()
            decorView.getWindowVisibleDisplayFrame(rect)
            val frameTop = rect.top.takeIf { it > 0 } ?: 0
            if (frameTop > 0) return frameTop
        }

        return 0
    }

    // helpers

    private fun dpToPx(dp: Float): Float = dp * resources.displayMetrics.density

    private fun hypot(a: Float, b: Float): Float =
        kotlin.math.hypot(a.toDouble(), b.toDouble()).toFloat()
}