package com.pop.components.animation.strategy

import android.content.res.Resources
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.Easing
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavOptions
import com.pop.components.animation.config.AnimationConfig
import com.pop.components.animation.config.AnimationEasing
import com.pop.components.animation.config.SlideLeftScaleAnimationConfig
import com.pop.compose_components.R

/**
 * Strategy implementation for Slide Left Scale animations.
 * 
 * This animation type provides a smooth slide transition with scaling where:
 * - New screen slides in from the right (+50dp) with fade in (opacity 0% to 100%) and scale (95% to 100%)
 * - Current screen slides out to the left (-50dp) with fade out (opacity 100% to 0%) and scale (100% to 95%)
 * 
 * Animation steps:
 * Step 1: New screen drawn above current screen at x-offset +50dp, opacity 0%, scale 95%
 * Step 2: Current screen slides out to x-offset -50dp, opacity 0%, scale 95%
 * Step 3: New screen slides in to x-offset 0px, opacity 100%, scale 100%
 * 
 * Based on POP Design System specifications.
 */
internal object SlideLeftScaleAnimationStrategy : AnimationStrategy {
    
    /**
     * Converts a dp value to pixels using system resources density.
     * This works in non-composable contexts.
     * 
     * @param dp The value in density-independent pixels to convert
     * @return The value in pixels, calculated from the provided dp value
     */
    private fun dpToPx(dp: Float): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }
    
    override fun createNavOptions(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_left_scale_slide_in_right)
            .setExitAnim(R.anim.slide_left_scale_slide_out_left)
            .setPopEnterAnim(R.anim.slide_left_scale_slide_in_left)
            .setPopExitAnim(R.anim.slide_left_scale_slide_out_right)
            .build()
    }

    override fun applyAnimations(transaction: FragmentTransaction): FragmentTransaction {
        return transaction.setCustomAnimations(
            R.anim.slide_left_scale_slide_in_right,  // enter
            R.anim.slide_left_scale_slide_out_left,  // exit
            R.anim.slide_left_scale_slide_in_left,   // popEnter
            R.anim.slide_left_scale_slide_out_right  // popExit
        )
    }

    override fun createEnterTransition(config: AnimationConfig): EnterTransition {
        require(config is SlideLeftScaleAnimationConfig) {
            "Expected SlideLeftScaleAnimationConfig but got ${config::class.simpleName}"
        }
        val offsetPx = dpToPx(config.slideOffsetDp)
        
        return slideInHorizontally(
            initialOffsetX = { offsetPx },
            animationSpec = tween(
                durationMillis = config.enterDurationMs,
                easing = config.enterEasing.toComposeEasing()
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = config.enterDurationMs,
                easing = config.enterEasing.toComposeEasing()
            )
        ) + scaleIn(
            initialScale = config.initialScale,
            animationSpec = tween(
                durationMillis = config.enterDurationMs,
                easing = config.enterEasing.toComposeEasing()
            )
        )
    }

    override fun createExitTransition(config: AnimationConfig): ExitTransition {
        require(config is SlideLeftScaleAnimationConfig) {
            "Expected SlideLeftScaleAnimationConfig but got ${config::class.simpleName}"
        }
        val offsetPx = dpToPx(config.slideOffsetDp)
        
        return slideOutHorizontally(
            targetOffsetX = { -offsetPx },
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        ) + fadeOut(
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        ) + scaleOut(
            targetScale = config.initialScale,
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        )
    }

    override fun createPopEnterTransition(config: AnimationConfig): EnterTransition {
        require(config is SlideLeftScaleAnimationConfig) {
            "Expected SlideLeftScaleAnimationConfig but got ${config::class.simpleName}"
        }
        val offsetPx = dpToPx(config.slideOffsetDp)
        
        return slideInHorizontally(
            initialOffsetX = { -offsetPx },
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        ) + scaleIn(
            initialScale = config.initialScale,
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        )
    }

    override fun createPopExitTransition(config: AnimationConfig): ExitTransition {
        require(config is SlideLeftScaleAnimationConfig) {
            "Expected SlideLeftScaleAnimationConfig but got ${config::class.simpleName}"
        }
        val offsetPx = dpToPx(config.slideOffsetDp)
        
        return slideOutHorizontally(
            targetOffsetX = { offsetPx },
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        ) + fadeOut(
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        ) + scaleOut(
            targetScale = config.initialScale,
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        )
    }
    
    /**
     * Helper function to convert AnimationEasing to Compose Easing.
     */
    private fun AnimationEasing.toComposeEasing(): Easing {
        return when (this) {
            is AnimationEasing.EaseOutCubic -> this.toComposeEasing()
            is AnimationEasing.AccelerateDecelerate -> this.toComposeEasing()
            is AnimationEasing.CubicBezier -> this.toComposeEasing()
        }
    }
}

