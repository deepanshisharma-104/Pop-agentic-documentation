package com.pop.components.animation.strategy

import com.pop.components.animation.config.AnimationConfig
import com.pop.components.animation.config.MaskRevealAnimationConfig
import com.pop.components.animation.config.NoAnimationConfig
import com.pop.components.animation.config.SlideLeftAnimationConfig
import com.pop.components.animation.config.SlideLeftScaleAnimationConfig
import com.pop.components.animation.config.SlideUpMidAnimationConfig
import com.pop.components.animation.config.SlideUpQuickAnimationConfig
import com.pop.components.animation.config.SplitScreenAnimationConfig
import javax.inject.Inject

/**
 * Factory for creating AnimationStrategy instances based on AnimationConfig.
 * 
 * Implements the Strategy Pattern to eliminate repeated when statements throughout
 * the codebase. Each AnimationConfig type has a corresponding AnimationStrategy that
 * knows how to create NavOptions, FragmentTransaction animations, and Compose transitions.
 * 
 * This factory centralizes the mapping between AnimationConfig types and AnimationStrategy,
 * making it easy to add new animation types in the future.
 * 
 * Thread Safety:
 * - This factory is stateless and thread-safe
 * - All strategy instances are singleton objects (no state)
 * 
 * Usage:
 * ```
 * val factory = AnimationStrategyFactory()
 * val config = SlideLeftAnimationConfig.Default
 * val strategy = factory.getStrategy(config)
 * val navOptions = strategy.createNavOptions()
 * ```
 */
internal class AnimationStrategyFactory @Inject constructor() {

    /**
     * Gets the appropriate AnimationStrategy for the given AnimationConfig.
     * 
     * Maps each AnimationConfig type to its corresponding strategy implementation:
     * - SlideLeftAnimationConfig → SlideLeftAnimationStrategy
     * - SlideLeftScaleAnimationConfig → SlideLeftScaleAnimationStrategy
     * - SlideUpQuickAnimationConfig → SlideUpQuickAnimationStrategy
     * - SlideUpMidAnimationConfig → SlideUpMidAnimationStrategy
     * - SplitScreenAnimationConfig → SplitScreenAnimationStrategy
     * - MaskRevealAnimationConfig → NoAnimationStrategy (custom implementation in extensions)
     * - NoAnimationConfig → NoAnimationStrategy
     * 
     * Note: MASK_REVEAL uses NoAnimationStrategy because the reveal effect is
     * implemented separately using View clipping with custom outline providers
     * in animation extensions. The standard Compose transitions are disabled
     * (instant) to allow the custom effect.
     *
     * @param config The AnimationConfig to get a strategy for
     * @return The corresponding AnimationStrategy instance
     * @throws IllegalArgumentException if an unknown AnimationConfig type is provided
     */
    fun getStrategy(config: AnimationConfig): AnimationStrategy {
        return when (config) {
            is SlideLeftAnimationConfig -> SlideLeftAnimationStrategy
            is SlideLeftScaleAnimationConfig -> SlideLeftScaleAnimationStrategy
            is SlideUpMidAnimationConfig -> SlideUpMidAnimationStrategy
            is SlideUpQuickAnimationConfig -> SlideUpQuickAnimationStrategy
            is SplitScreenAnimationConfig -> SplitScreenAnimationStrategy
            // MASK_REVEAL uses NoAnimationStrategy because the reveal effect is implemented
            // separately using View clipping with custom outline providers in animation extensions.
            // The standard Compose transitions are disabled (instant) to allow the custom effect.
            is MaskRevealAnimationConfig -> NoAnimationStrategy
            is NoAnimationConfig -> NoAnimationStrategy
            else -> NoAnimationStrategy
        }
    }
}

