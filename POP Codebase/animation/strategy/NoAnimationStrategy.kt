package com.pop.components.animation.strategy

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavOptions
import com.pop.components.animation.config.AnimationConfig

/**
 * Strategy implementation for no animations (instant transitions).
 * 
 * This strategy disables all screen transition animations by:
 * - Setting all animation resource IDs to 0 for NavOptions and FragmentTransaction
 * - Using fade transitions with 0ms duration for Compose Navigation
 * 
 * Use cases:
 * - Programmatic navigation where animations are not desired
 * - NO_ANIMATION animation type
 * - MASK_REVEAL animation type (which uses special handling via animation extensions
 *   rather than standard transitions, so this strategy provides the base instant transitions)
 * 
 * Note: For MASK_REVEAL, the actual reveal effect is implemented separately using
 * View clipping with custom outline providers, not through standard Compose transitions.
 */
internal object NoAnimationStrategy : AnimationStrategy {
    override fun createNavOptions(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(0)
            .setExitAnim(0)
            .setPopEnterAnim(0)
            .setPopExitAnim(0)
            .build()
    }

    override fun applyAnimations(transaction: FragmentTransaction): FragmentTransaction {
        return transaction.setCustomAnimations(0, 0, 0, 0)
    }

    override fun createEnterTransition(config: AnimationConfig): EnterTransition {
        return fadeIn(animationSpec = tween(durationMillis = 0))
    }

    override fun createExitTransition(config: AnimationConfig): ExitTransition {
        return fadeOut(animationSpec = tween(durationMillis = 0))
    }

    override fun createPopEnterTransition(config: AnimationConfig): EnterTransition {
        return fadeIn(animationSpec = tween(durationMillis = 0))
    }

    override fun createPopExitTransition(config: AnimationConfig): ExitTransition {
        return fadeOut(animationSpec = tween(durationMillis = 0))
    }
}

