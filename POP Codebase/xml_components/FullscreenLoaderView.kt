package com.pop.components.xml_components

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.PathInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopShapes
import com.pop.components.utils.glowEffect
import com.pop.components.utils.popBackground
import com.pop.components.utils.statusBarHeight
import com.pop.compose_components.R
import androidx.core.view.isGone
import androidx.core.view.isVisible

class FullscreenLoaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    // Matches SlideLeftScaleAnimationConfig.Default enterEasing CubicBezier(0.47, 0.0, 0.23, 1.38)
    private val loaderEnterInterpolator = PathInterpolator(0.47f, 0f, 0.23f, 1.38f)
    // Symmetric PathInterpolator so reverse sweep has matching left/right feel.
    private val loaderSweepInterpolator = PathInterpolator(0.47f, 0f, 0.53f, 1f)
    // Matches SlideLeftScaleAnimationConfig.Default exitEasing AccelerateDecelerate
    private val loaderExitInterpolator = PathInterpolator(0.47f, 0f, 0.23f, 1.38f)

    // Root dim/background view shown behind the loader content.
    private val dim: View
    // Holds the bar + text content block that fades in/out.
    private val loaderContainer: View
    // Full-width track container used to compute animation travel.
    private val loaderBarContainer: View
    // Static background track (dimmer bar behind the sweep).
    private val loaderBarBackground: View
    // Animated foreground sweep rendered via Compose (gradient + optional glow).
    private val loaderBarSweep: ComposeView
    // Primary loader title text.
    private val message: TextView
    // Secondary title text layer used for cross-slide text transitions.
    private val messageAlt: TextView
    // Secondary loader body text.
    private val bodyText: TextView

    // Foreground sweep animator. Null when no sweep is running.
    private var sweepAnimator: ValueAnimator? = null
    // Last rendered title/body to detect when text animation is needed.
    private var currentText: String? = null
    private var currentBodyText: String? = null
    private var activeMessageView: TextView
    // Last rendered gradient/glow so Compose content is updated only when needed.
    private var currentGradient: PopGradient? = null
    private var currentGlowColor: Color? = null

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_fullscreen_loader, this, true)

        visibility = GONE

        dim = findViewById(R.id.loaderDim)
        loaderContainer = findViewById(R.id.loaderContainer)
        loaderBarContainer = findViewById(R.id.loaderBarContainer)
        loaderBarBackground = findViewById(R.id.loaderBarBackground)
        loaderBarSweep = findViewById(R.id.loaderBarSweep)
        message = findViewById(R.id.loaderMessage)
        messageAlt = findViewById(R.id.loaderMessageAlt)
        bodyText = findViewById(R.id.loaderBodyText)
        activeMessageView = message
        
        // Initialize ComposeView with default content
        loaderBarSweep.setContent {
            LoaderBarSweepContent(gradient = currentGradient, glowColor = currentGlowColor)
        }
    }

    fun show(
        barGradient: PopGradient? = null,
        glowColor: Color? = null,
        text: String? = null,
        bodyText: String? = null,
        enableSweepAnimation: Boolean = true,
        showScrim: Boolean = true,
        useEntryStyleTextAnimation: Boolean = false,
    ) {
        // Keep a local alias to avoid shadowing the `bodyText` view field.
        val bodyTextValue = bodyText
        // If visible, we only update text/colors/animation state instead of replaying entry transition.
        val isAlreadyVisible = isVisible

        // Compose content only needs re-mount when glow value actually changes.
        val glowChanged = glowColor != currentGlowColor
        currentGlowColor = glowColor
        updateBarGradient(barGradient, forceContentUpdate = glowChanged)
        
        if (isAlreadyVisible) {
            // Already showing - update text with animation if it changed
            val textChanged = currentText != text
            val bodyTextChanged = currentBodyText != bodyTextValue
            
            if (textChanged) {
                animateTextChange(text, useEntryStyleTextAnimation)
            } else {
                updateText(text)
            }
            
            if (bodyTextChanged) {
                animateBodyTextChange(bodyTextValue, useEntryStyleTextAnimation)
            } else {
                updateBodyText(bodyTextValue)
            }
            
            post {
                // Avoid restarting the sweep unnecessarily, which causes visible jumps.
                val isCurrentlyAnimating = sweepAnimator?.isRunning == true
                if (enableSweepAnimation && !isCurrentlyAnimating) {
                    updateSweepAnimation(true)
                } else if (!enableSweepAnimation && isCurrentlyAnimating) {
                    updateSweepAnimation(false)
                }
                // If the sweep is already in the desired state, leave it running untouched
            }
            return
        }
        
        // Set alpha=0 before VISIBLE to prevent a 1-frame flash at wrong position/opacity
        loaderContainer.alpha = 0f
        loaderBarSweep.alpha = 0f

        // First time showing - animate in
        visibility = VISIBLE

        // Set text content before animation begins
        if (text != null) {
            activeMessageView = message
            message.text = text
            message.visibility = VISIBLE
            message.alpha = 0f
            message.translationX = 0f
            messageAlt.visibility = GONE
            messageAlt.alpha = 1f
            messageAlt.translationX = 0f
            currentText = text
        } else {
            message.visibility = GONE
            messageAlt.visibility = GONE
            activeMessageView = message
            currentText = null
        }

        if (bodyTextValue != null) {
            this.bodyText.text = bodyTextValue
            this.bodyText.visibility = VISIBLE
            this.bodyText.alpha = 0f
            this.bodyText.translationX = 0f
            currentBodyText = bodyTextValue
        } else {
            this.bodyText.visibility = GONE
            currentBodyText = null
        }

        post {
            // Place content below status bar so the top-aligned loader doesn't overlap system UI.
            loaderContainer.translationY = statusBarHeight().toFloat()
            dim.alpha = 0f

            dim.animate()
                .alpha(1f)
                .setDuration(200)
                .setInterpolator(loaderEnterInterpolator)
                .start()

            if (enableSweepAnimation) {
                // Kick off sweep immediately instead of waiting for container fade-in.
                updateSweepAnimation(true)
            }

            loaderContainer.animate()
                .alpha(1f)
                .setDuration(200)
                .setInterpolator(loaderEnterInterpolator)
                .withEndAction {
                    message.alpha = 1f
                    messageAlt.alpha = 1f
                    this.bodyText.alpha = 1f
                    if (!enableSweepAnimation) {
                        updateSweepAnimation(false)
                    }
                }
                .start()

            if (!enableSweepAnimation) {
                val containerWidth = loaderBarContainer.width.toFloat()
                val glowPaddingPx = 9f * resources.displayMetrics.density
                loaderBarSweep.layoutParams = loaderBarSweep.layoutParams.also {
                    it.width = (containerWidth + (2f * glowPaddingPx)).toInt()
                }
                // Negative offset keeps glow from being clipped at the left boundary.
                loaderBarSweep.translationX = -glowPaddingPx
                loaderBarSweep.animate()
                    .alpha(1f)
                    .setDuration(450)
                    .setInterpolator(loaderEnterInterpolator)
                    .start()
            }

            if (text != null) {
                activeMessageView.animate()
                    .alpha(1f)
                    .setDuration(200)
                    .setInterpolator(loaderEnterInterpolator)
                    .start()
            }
            if (bodyTextValue != null) {
                this.bodyText.animate()
                    .alpha(1f)
                    .setDuration(200)
                    .setInterpolator(loaderEnterInterpolator)
                    .start()
            }
        }
    }
    
    private fun updateBarGradient(gradient: PopGradient?, forceContentUpdate: Boolean = false) {
        // Track/background bar is rebuilt each call because track color/corner shape is fixed and cheap.
        val bgDrawable = GradientDrawable().apply {
            val trackColor = ContextCompat.getColor(context, R.color.surface_secondary_transparent_60)
            setColor(trackColor)
            cornerRadius = resources.getDimension(R.dimen.pop_radius_xl_large)
        }
        loaderBarBackground.background = bgDrawable

        // Null input means "use default loader orange gradient".
        val newGradient = gradient ?: getDefaultGradient()
        // setContent() is expensive; only refresh Compose tree when appearance actually changes.
        if (newGradient != currentGradient || forceContentUpdate) {
            currentGradient = newGradient
            loaderBarSweep.setContent {
                LoaderBarSweepContent(gradient = currentGradient, glowColor = currentGlowColor)
            }
        } else {
            currentGradient = newGradient
        }
    }
    
    /**
     * Gets the default gradient (orange) when no gradient is provided.
     */
    private fun getDefaultGradient(): PopGradient {
        val defaultColor = ContextCompat.getColor(context, R.color.orange_9)
        val color = Color(defaultColor)

        val brightColor = Color(
            red = (color.red * 1.2f).coerceIn(0f, 1f),
            green = (color.green * 1.1f).coerceIn(0f, 1f),
            blue = (color.blue * 1.0f).coerceIn(0f, 1f),
            alpha = color.alpha
        )

        // Centre-bright gradient — looks warm on the small dot, and rich on the static full bar
        return PopGradient.Linear(
            colors = listOf(color, brightColor, color),
            stops = listOf(0f, 0.5f, 1f),
            angleInDegrees = 90f
        )
    }
    
    /**
     * Compose function to render the loader bar sweep with gradient.
     */
    @Composable
    private fun LoaderBarSweepContent(gradient: PopGradient?, glowColor: Color?) {
        if (gradient != null) {
            // Outer box fills the full 22dp ComposeView height (4dp bar + 9dp glow on each side)
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                // Reserve explicit horizontal room so left/right glow caps are not clipped.
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Inner box is the actual 4dp bar.
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(3.dp)
                            .then(
                                if (glowColor != null) Modifier.glowEffect(
                                    blurRadius = 9.dp,
                                    spreadRadius = 0.dp,
                                    color = glowColor,
                                    shape = PopShapes.pill(),
                                    blurStyle = BlurMaskFilter.Blur.OUTER,
                                    // Kept false intentionally: true caused frame drops during sweep animation.
                                    enableDitheredFalloff = false
                                ) else Modifier
                            )
                            .popBackground(
                                gradient = gradient,
                                shape = PopShapes.pill()
                            )
                    )
                }
            }
        }
    }
    
    private fun updateText(text: String?) {
        if (text != null) {
            activeMessageView.text = text
            activeMessageView.visibility = VISIBLE
            activeMessageView.translationX = 0f
            activeMessageView.alpha = 1f
            inactiveMessageView().visibility = GONE
            currentText = text
        } else {
            message.visibility = GONE
            messageAlt.visibility = GONE
            activeMessageView = message
            currentText = null
        }
    }
    
    private fun updateBodyText(body: String?) {
        if (body != null) {
            bodyText.text = body
            bodyText.visibility = VISIBLE
            currentBodyText = body
        } else {
            bodyText.visibility = GONE
            currentBodyText = null
        }
    }
    
    private fun animateTextChange(newText: String?, useEntryStyleAnimation: Boolean = false) {
        val slideDistance = 30f * resources.displayMetrics.density
        val outgoingView = activeMessageView
        val incomingView = inactiveMessageView()
        outgoingView.animate().cancel()
        incomingView.animate().cancel()
        
        if (newText == null) {
            // Slide current text left and hide
            outgoingView.animate()
                .translationX(-slideDistance)
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(loaderExitInterpolator)
                .withEndAction {
                    outgoingView.visibility = GONE
                    outgoingView.translationX = 0f
                    outgoingView.alpha = 1f
                    incomingView.visibility = GONE
                    incomingView.translationX = 0f
                    incomingView.alpha = 1f
                    activeMessageView = message
                    currentText = null
                }
                .start()
            return
        }
        
        if (useEntryStyleAnimation) {
            outgoingView.animate()
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(loaderExitInterpolator)
                .withEndAction {
                    outgoingView.visibility = GONE
                    incomingView.text = newText
                    incomingView.visibility = VISIBLE
                    incomingView.translationX = 0f
                    incomingView.alpha = 0f
                    incomingView.animate()
                        .alpha(1f)
                        .setDuration(200)
                        .setInterpolator(loaderEnterInterpolator)
                        .start()
                    outgoingView.translationX = 0f
                    outgoingView.alpha = 1f
                    activeMessageView = incomingView
                    currentText = newText
                }
                .start()
            return
        }

        incomingView.text = newText
        incomingView.visibility = VISIBLE
        incomingView.translationX = slideDistance
        incomingView.alpha = 0.1f

        // Simultaneous cross-slide: outgoing left+fade, incoming right->center+fade.
        outgoingView.animate()
            .translationX(-slideDistance)
            .alpha(0f)
            .setDuration(200)
            .setInterpolator(loaderExitInterpolator)
            .withEndAction {
                outgoingView.visibility = GONE
                outgoingView.translationX = 0f
                outgoingView.alpha = 1f
            }
            .start()

        incomingView.animate()
            .translationX(0f)
            .alpha(1f)
            .setDuration(200)
            .setInterpolator(loaderEnterInterpolator)
            .withEndAction {
                activeMessageView = incomingView
                currentText = newText
            }
            .start()
    }

    private fun inactiveMessageView(): TextView {
        return if (activeMessageView === message) messageAlt else message
    }

    private fun lerp(start: Float, end: Float, fraction: Float): Float {
        return start + ((end - start) * fraction)
    }
    
    private fun animateBodyTextChange(newBodyText: String?, useEntryStyleAnimation: Boolean = false) {
        val slideDistance = 20f * resources.displayMetrics.density
        
        if (newBodyText == null) {
            // Slide current body text left and hide
            bodyText.animate()
                .translationX(-slideDistance)
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(loaderExitInterpolator)
                .withEndAction {
                    bodyText.visibility = GONE
                    bodyText.translationX = 0f
                    currentBodyText = null
                }
                .start()
            return
        }
        
        if (useEntryStyleAnimation) {
            bodyText.animate()
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(loaderExitInterpolator)
                .withEndAction {
                    bodyText.text = newBodyText
                    bodyText.translationX = 0f
                    bodyText.alpha = 0f
                    bodyText.animate()
                        .alpha(1f)
                        .setDuration(200)
                        .setInterpolator(loaderEnterInterpolator)
                        .start()
                    currentBodyText = newBodyText
                }
                .start()
            return
        }

        // Slide current body text left
        bodyText.animate()
            .translationX(-slideDistance)
            .alpha(0f)
            .setDuration(200)
            .setInterpolator(loaderExitInterpolator)
            .withEndAction {
                // Update body text and slide in from right with a soft fade-in.
                bodyText.text = newBodyText
                bodyText.translationX = slideDistance // Start from right
                bodyText.alpha = 0f
                
                // Slide + fade in from right to left.
                bodyText.animate()
                    .translationX(0f)
                    .alpha(1f)
                    .setDuration(200)
                    .setInterpolator(loaderEnterInterpolator)
                    .start()
                
                currentBodyText = newBodyText
            }
            .start()
    }
    
    private fun updateSweepAnimation(
        enableSweepAnimation: Boolean,
        startFromRight: Boolean = false
    ) {
        // Always stop any previous sweep before applying new geometry/state.
        sweepAnimator?.cancel()
        sweepAnimator = null

        val containerWidth = loaderBarContainer.width.toFloat()
        // Horizontal extra room reserved for glow halo.
        val glowPaddingPx = 12f * resources.displayMetrics.density

        if (enableSweepAnimation) {
            // Foreground core is 1.5x container, creating a long bright segment.
            val sweepCoreWidth = containerWidth * 1.5f
            val sweepLayoutParams = loaderBarSweep.layoutParams
            sweepLayoutParams.width = (sweepCoreWidth + (2f * glowPaddingPx)).toInt()
            loaderBarSweep.layoutParams = sweepLayoutParams

            // Preserve the original sweep path.
            val fromX = (if (startFromRight) containerWidth else -sweepCoreWidth) - glowPaddingPx
            val toX = (if (startFromRight) -sweepCoreWidth else containerWidth) - glowPaddingPx

            val startLoopSweep: () -> Unit = {
                // ValueAnimator writes translationX directly on every frame, avoiding
                // the legacy ViewAnimation snap while preserving the original motion style.
                sweepAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
                    // Faster than before, but same visual motion path.
                    duration = 1700
                    repeatCount = ValueAnimator.INFINITE
                    // REVERSE preserves the original back-and-forth movement.
                    repeatMode = ValueAnimator.REVERSE
                    // Keep animator timeline linear; apply easing exactly once below.
                    interpolator = LinearInterpolator()
                    addUpdateListener { anim ->
                        val animatedFraction = anim.animatedValue as Float
                        val easedFraction = loaderSweepInterpolator
                            .getInterpolation(animatedFraction)
                            .coerceIn(0f, 1f)
                        loaderBarSweep.translationX = lerp(fromX, toX, easedFraction)
                    }
                    start()
                }
            }

            if (startFromRight) {
                // No fade-in: every pass must look identical from the very first one.
                loaderBarSweep.alpha = 1f
                val handoffStartX = loaderBarSweep.translationX
                // Keep the exact same first sweep direction/path, but bridge the handoff
                // to avoid a visible jump from the expansion end state.
                if (kotlin.math.abs(handoffStartX - fromX) > 1f) {
                    val handoffAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
                        duration = 130
                        // Keep timeline linear; apply easing exactly once below.
                        interpolator = LinearInterpolator()
                        var wasCancelled = false
                        addUpdateListener { anim ->
                            val animatedFraction = anim.animatedValue as Float
                            val easedFraction = loaderSweepInterpolator
                                .getInterpolation(animatedFraction)
                                .coerceIn(0f, 1f)
                            loaderBarSweep.translationX = lerp(handoffStartX, fromX, easedFraction)
                        }
                        addListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationCancel(animation: android.animation.Animator) {
                                wasCancelled = true
                            }

                            override fun onAnimationEnd(animation: android.animation.Animator) {
                                if (!wasCancelled) {
                                    startLoopSweep()
                                }
                            }
                        })
                    }
                    sweepAnimator = handoffAnimator
                    handoffAnimator.start()
                } else {
                    loaderBarSweep.translationX = fromX
                    startLoopSweep()
                }
            } else {
                // Fade in on gradient-update calls where the sweep restarts from the left.
                loaderBarSweep.translationX = fromX
                loaderBarSweep.alpha = 0f
                loaderBarSweep.animate()
                    .alpha(1f)
                    .setDuration(450)
                    .setInterpolator(loaderEnterInterpolator)
                    .start()
                startLoopSweep()
            }
        } else {
            // Static mode: foreground fills full track width.
            val sweepLayoutParams = loaderBarSweep.layoutParams
            sweepLayoutParams.width = (containerWidth + (2f * glowPaddingPx)).toInt()
            loaderBarSweep.layoutParams = sweepLayoutParams
            loaderBarSweep.translationX = -glowPaddingPx
        }
    }

    fun hide() {
        if (isGone) {
            return
        }

        // Stop sweep immediately to avoid running animation on a disappearing view.
        sweepAnimator?.cancel()
        sweepAnimator = null

        post {
            dim.animate()
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(loaderExitInterpolator)
                .start()

            message.animate()
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(loaderExitInterpolator)
                .start()

            messageAlt.animate()
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(loaderExitInterpolator)
                .start()

            this.bodyText.animate()
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(loaderExitInterpolator)
                .start()

            loaderContainer.animate()
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(loaderExitInterpolator)
                .withEndAction {
                    visibility = GONE
                }
                .start()
        }
    }

    override fun onDetachedFromWindow() {
        // Mirror hide() teardown to avoid animator leaks if the view detaches abruptly.
        sweepAnimator?.cancel()
        sweepAnimator = null

        dim.animate().cancel()
        loaderContainer.animate().cancel()
        loaderBarSweep.animate().cancel()
        message.animate().cancel()
        messageAlt.animate().cancel()
        bodyText.animate().cancel()

        super.onDetachedFromWindow()
    }
}
