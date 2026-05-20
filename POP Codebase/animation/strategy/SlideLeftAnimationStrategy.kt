package com.pop.components.animation.strategy

import android.content.res.Resources
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.core.Easing
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavOptions
import com.pop.components.animation.config.AnimationConfig
import com.pop.components.animation.config.AnimationEasing
import com.pop.components.animation.config.SlideLeftAnimationConfig
import com.pop.compose_components.R

/**
 * Strategy implementation for Slide Left animations.
 * 
 * This animation type provides a smooth slide transition where:
 * - New screen slides in from the right edge
 * - Old screen slides out to the left edge
 * - No scaling is applied (pure horizontal translation)
 * 
 * Based on POP Design System specifications.
 */
internal object SlideLeftAnimationStrategy : AnimationStrategy {
    
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
            .setEnterAnim(R.anim.slide_left_slide_in_right)
            .setExitAnim(R.anim.slide_left_slide_out_left)
            .setPopEnterAnim(R.anim.slide_left_slide_in_left)
            .setPopExitAnim(R.anim.slide_left_slide_out_right)
            .build()
    }

    override fun applyAnimations(transaction: FragmentTransaction): FragmentTransaction {
        return transaction.setCustomAnimations(
            R.anim.slide_left_slide_in_right,  // enter
            R.anim.slide_left_slide_out_left,  // exit
            R.anim.slide_left_slide_in_left,   // popEnter
            R.anim.slide_left_slide_out_right  // popExit
        )
    }

    override fun createEnterTransition(config: AnimationConfig): EnterTransition {
        require(config is SlideLeftAnimationConfig) {
            "Expected SlideLeftAnimationConfig but got ${config::class.simpleName}"
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
        )
    }

    override fun createExitTransition(config: AnimationConfig): ExitTransition {
        require(config is SlideLeftAnimationConfig) {
            "Expected SlideLeftAnimationConfig but got ${config::class.simpleName}"
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
        )
    }

    override fun createPopEnterTransition(config: AnimationConfig): EnterTransition {
        require(config is SlideLeftAnimationConfig) {
            "Expected SlideLeftAnimationConfig but got ${config::class.simpleName}"
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
        )
    }

    override fun createPopExitTransition(config: AnimationConfig): ExitTransition {
        require(config is SlideLeftAnimationConfig) {
            "Expected SlideLeftAnimationConfig but got ${config::class.simpleName}"
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

