package com.pop.components.animation.config

/**
 * Split Screen animation configuration.
 * Creates a split effect where the current screen splits (upper half up, lower half down) and the new screen appears.
 * The actual split effect is implemented using MotionLayout transitions in POPMainActivity.
 *
 * @param screenId Unique identifier for the owner of childFragmentManager.
 *                 - `null` = activity-level navigation (parent is activity, uses activity's screenId)
 *                 - Non-null = fragment child navigation (parent is fragment with that screenId)
 * @param splitAnchorPercent Split point as percentage of screen height (default: 0.5f = 50%).
 *                           Determines where the screen splits horizontally.
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
data class SplitScreenAnimationConfig(
    override val screenId: String? = null,
    val splitAnchorPercent: Float,
    val animationId: String,
    val useBitmapCaching: Boolean = true,
    val fromScanAndPay: Boolean = false,
    override val enterDurationMs: Int,
    override val exitDurationMs: Int,
    override val enterEasing: AnimationEasing,
    override val exitEasing: AnimationEasing
) : AnimationConfig() {
    companion object {
        /**
         * Default configuration for Split Screen animation.
         * Note: When using this default, you must copy it and provide a unique animationId:
         * ```
         * val config = SplitScreenAnimationConfig.Default.copy(animationId = UUID.randomUUID().toString())
         * ```
         */
        val Default = SplitScreenAnimationConfig(
            screenId = null,
            splitAnchorPercent = 0.5f,
            animationId = "", // Empty - must be replaced with UUID.randomUUID().toString()
            useBitmapCaching = true,
            enterDurationMs = 275,
            exitDurationMs = 250,
            enterEasing = AnimationEasing.EaseOutCubic,
            exitEasing = AnimationEasing.EaseOutCubic
        )
    }
}

