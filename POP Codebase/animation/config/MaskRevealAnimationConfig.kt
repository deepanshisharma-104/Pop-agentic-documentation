package com.pop.components.animation.config

/**
 * Mask Reveal animation configuration.
 * Creates a reveal effect where the new screen is revealed using a circular/elliptical mask
 * that expands from a vertical origin point. The effect is implemented using View clipping
 * with custom outline providers in animation extensions.
 *
 * @param screenId Unique identifier for the owner of childFragmentManager.
 *                 - `null` = activity-level navigation (parent is activity, uses activity's screenId)
 *                 - Non-null = fragment child navigation (parent is fragment with that screenId)
 * @param verticalOriginFraction Vertical origin point as fraction of screen height (default: 0.55f = 55% from top).
 *                               The reveal animation starts expanding from this point.
 * @param animationId Unique identifier for this animation instance (required).
 *                   Used for bitmap caching and retrieval, and for matching events in event bus.
 *                   Must be a unique UUID for each animation instance.
 * @param useBitmapCaching Whether to cache bitmaps for this animation (default: true).
 *                        Set to false to disable caching for this animation instance.
 * @param enterDurationMs Duration in milliseconds for enter animations
 * @param exitDurationMs Duration in milliseconds for exit/pop animations
 * @param enterEasing Easing function for enter animations
 * @param exitEasing Easing function for exit/pop animations
 */
data class MaskRevealAnimationConfig(
    override val screenId: String? = null,
    val verticalOriginFraction: Float,
    val animationId: String,
    val useBitmapCaching: Boolean = true,
    override val enterDurationMs: Int,
    override val exitDurationMs: Int,
    override val enterEasing: AnimationEasing,
    override val exitEasing: AnimationEasing
) : AnimationConfig() {
    companion object {
        /**
         * Default configuration for Mask Reveal animation.
         * Note: When using this default, you must copy it and provide a unique animationId:
         * ```
         * val config = MaskRevealAnimationConfig.Default.copy(animationId = UUID.randomUUID().toString())
         * ```
         */
        val Default = MaskRevealAnimationConfig(
            screenId = null,
            verticalOriginFraction = 0.55f,
            animationId = "", // Empty - must be replaced with UUID.randomUUID().toString()
            useBitmapCaching = true,
            enterDurationMs = 300,
            exitDurationMs = 300,
            enterEasing = AnimationEasing.EaseOutCubic,
            exitEasing = AnimationEasing.EaseOutCubic
        )
    }
}

