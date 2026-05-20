package com.pop.components.animation.strategy

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavOptions
import com.pop.components.animation.config.AnimationConfig
import com.pop.components.animation.config.SplitScreenAnimationConfig
import com.pop.compose_components.R

/**
 * Strategy implementation for Split Screen animations.
 * 
 * This animation type provides a split screen transition where:
 * - Current screen splits: upper half slides up, lower half slides down (handled by MotionLayout)
 * - New screen appears instantly (matching XML: split_screen_enter.xml with duration 0)
 * 
 * The Compose transitions are instant (duration 0) because the actual split effect is
 * implemented using MotionLayout transitions in POPMainActivity, which:
 * - Captures the current window as bitmaps
 * - Splits the bitmap at the anchor point
 * - Animates the split halves using ImageViews
 * - Reveals the new screen underneath
 * 
 * This strategy provides the basic NavOptions and FragmentTransaction animations,
 * but the visual split effect is handled separately by MotionLayout.
 * 
 * Based on POP Design System specifications.
 */
internal object SplitScreenAnimationStrategy : AnimationStrategy {
    
    override fun createNavOptions(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.split_screen_enter)
            .setExitAnim(R.anim.split_screen_exit)
            .setPopEnterAnim(R.anim.split_screen_pop_enter)
            .setPopExitAnim(R.anim.split_screen_pop_exit)
            .build()
    }

    override fun applyAnimations(transaction: FragmentTransaction): FragmentTransaction {
        return transaction.setCustomAnimations(
            R.anim.split_screen_enter,   // enter
            R.anim.split_screen_exit,     // exit (basic fade, split applied via MotionLayout)
            R.anim.split_screen_pop_enter, // popEnter
            R.anim.split_screen_pop_exit   // popExit
        )
    }

    override fun createEnterTransition(config: AnimationConfig): EnterTransition {
        require(config is SplitScreenAnimationConfig) {
            "Expected SplitScreenAnimationConfig but got ${config::class.simpleName}"
        }
        // Instant transition matching XML: split_screen_enter.xml (duration 0)
        return fadeIn(
            animationSpec = tween(durationMillis = 0)
        )
    }

    override fun createExitTransition(config: AnimationConfig): ExitTransition {
        require(config is SplitScreenAnimationConfig) {
            "Expected SplitScreenAnimationConfig but got ${config::class.simpleName}"
        }
        // Instant transition matching XML: split_screen_exit.xml (duration 0, fade to 0%)
        // The split effect (upper half slides up, lower half slides down) is handled by MotionLayout transitions.
        return fadeOut(
            animationSpec = tween(durationMillis = 0)
        )
    }

    override fun createPopEnterTransition(config: AnimationConfig): EnterTransition {
        require(config is SplitScreenAnimationConfig) {
            "Expected SplitScreenAnimationConfig but got ${config::class.simpleName}"
        }
        // Instant transition matching XML: split_screen_pop_enter.xml (duration 0)
        return fadeIn(
            animationSpec = tween(durationMillis = 0)
        )
    }

    override fun createPopExitTransition(config: AnimationConfig): ExitTransition {
        require(config is SplitScreenAnimationConfig) {
            "Expected SplitScreenAnimationConfig but got ${config::class.simpleName}"
        }
        // Instant transition matching XML: split_screen_pop_exit.xml (duration 0, fade to 0%)
        // The split effect is handled by MotionLayout transitions.
        return fadeOut(
            animationSpec = tween(durationMillis = 0)
        )
    }
}

