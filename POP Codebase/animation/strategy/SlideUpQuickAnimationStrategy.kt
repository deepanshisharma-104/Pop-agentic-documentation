package com.pop.components.animation.strategy

import android.content.res.Resources
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.Easing
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavOptions
import com.pop.components.animation.config.AnimationConfig
import com.pop.components.animation.config.AnimationEasing
import com.pop.components.animation.config.SlideUpQuickAnimationConfig
import com.pop.compose_components.R

/**
 * Strategy implementation for Slide Up Quick animations.
 * 
 * This animation type provides a smooth slide-up transition where:
 * - New screen slides in from +25% (below) with fade in (opacity 0% to 100%) using slide_up_bezier easing
 * - Current screen slides up to -30dp with fade out (opacity 100% to 0%) using decelerate_interpolator
 * 
 * Animation steps:
 * Step 1: Current screen at opacity 100%, y offset 0px
 * Step 2: New screen drawn above current screen at opacity 0%, y offset +25% (below)
 * Step 3: Current screen slides up to -30dp with opacity 0%, 
 *         New screen slides up to 0px with opacity 100%
 * 
 * Based on POP Design System specifications.
 * Matches XML animations: slide_up_quick_slide_in_up.xml, slide_up_quick_slide_out_up.xml, etc.
 */
internal object SlideUpQuickAnimationStrategy : AnimationStrategy {
    
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
            .setEnterAnim(R.anim.slide_up_quick_slide_in_up)
            .setExitAnim(R.anim.slide_up_quick_slide_out_up)
            .setPopEnterAnim(R.anim.slide_up_quick_slide_in_down)
            .setPopExitAnim(R.anim.slide_up_quick_slide_out_down)
            .build()
    }

    override fun applyAnimations(transaction: FragmentTransaction): FragmentTransaction {
        return transaction.setCustomAnimations(
            R.anim.slide_up_quick_slide_in_up,   // enter
            R.anim.slide_up_quick_slide_out_up,  // exit
            R.anim.slide_up_quick_slide_in_down, // popEnter
            R.anim.slide_up_quick_slide_out_down // popExit
        )
    }

    override fun createEnterTransition(config: AnimationConfig): EnterTransition {
        require(config is SlideUpQuickAnimationConfig) {
            "Expected SlideUpQuickAnimationConfig but got ${config::class.simpleName}"
        }
        return slideInVertically(
            initialOffsetY = { (it * config.slideInOffsetPercent).toInt() },
            animationSpec = tween(
                durationMillis = config.enterDurationMs,
                easing = config.enterEasing.toComposeEasing()
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = config.enterDurationMs,
                easing = config.enterEasing.toComposeEasing()
            )
        )
    }

    override fun createExitTransition(config: AnimationConfig): ExitTransition {
        require(config is SlideUpQuickAnimationConfig) {
            "Expected SlideUpQuickAnimationConfig but got ${config::class.simpleName}"
        }
        val offsetPx = -dpToPx(config.slideOutOffsetDp)
        
        return slideOutVertically(
            targetOffsetY = { offsetPx },
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        ) + fadeOut(
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        )
    }

    override fun createPopEnterTransition(config: AnimationConfig): EnterTransition {
        require(config is SlideUpQuickAnimationConfig) {
            "Expected SlideUpQuickAnimationConfig but got ${config::class.simpleName}"
        }
        val offsetPx = -dpToPx(config.slideOutOffsetDp)
        
        return slideInVertically(
            initialOffsetY = { offsetPx },
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        )
    }

    override fun createPopExitTransition(config: AnimationConfig): ExitTransition {
        require(config is SlideUpQuickAnimationConfig) {
            "Expected SlideUpQuickAnimationConfig but got ${config::class.simpleName}"
        }
        return slideOutVertically(
            targetOffsetY = { (it * config.slideInOffsetPercent).toInt() },
            animationSpec = tween(
                durationMillis = config.exitDurationMs,
                easing = config.exitEasing.toComposeEasing()
            )
        ) + fadeOut(
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

