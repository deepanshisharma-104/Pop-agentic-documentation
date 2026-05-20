package com.pop.components.animation.strategy

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavOptions
import com.pop.components.animation.config.AnimationConfig

/**
 * Strategy interface for creating animations.
 * Each animation type implements this interface to provide its specific animation behavior.
 */
internal interface AnimationStrategy {
    /**
     * Creates NavOptions with animations for forward navigation.
     *
     * @return NavOptions configured with the appropriate animations
     */
    fun createNavOptions(): NavOptions

    /**
     * Applies animations to a FragmentTransaction.
     *
     * @param transaction The FragmentTransaction to apply animations to
     * @return The same FragmentTransaction instance for method chaining
     */
    fun applyAnimations(transaction: FragmentTransaction): FragmentTransaction

    /**
     * Creates enter transition for Compose Navigation.
     *
     * @param config AnimationConfig with custom values
     * @return EnterTransition configured with the appropriate animations
     */
    fun createEnterTransition(config: AnimationConfig): EnterTransition

    /**
     * Creates exit transition for Compose Navigation.
     *
     * @param config AnimationConfig with custom values
     * @return ExitTransition configured with the appropriate animations
     */
    fun createExitTransition(config: AnimationConfig): ExitTransition

    /**
     * Creates pop enter transition for Compose Navigation.
     *
     * @param config AnimationConfig with custom values
     * @return EnterTransition configured with the appropriate pop animations
     */
    fun createPopEnterTransition(config: AnimationConfig): EnterTransition

    /**
     * Creates pop exit transition for Compose Navigation.
     *
     * @param config AnimationConfig with custom values
     * @return ExitTransition configured with the appropriate pop animations
     */
    fun createPopExitTransition(config: AnimationConfig): ExitTransition
}

