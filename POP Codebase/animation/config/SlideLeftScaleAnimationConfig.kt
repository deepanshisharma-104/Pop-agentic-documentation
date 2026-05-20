package com.pop.components.animation.config

/**
 * Slide Left Scale animation configuration.
 * Combines horizontal slide with scale animation for a more dynamic transition.
 *
 * @param screenId Unique identifier for the owner of childFragmentManager.
 *                 - `null` = activity-level navigation (parent is activity, uses activity's screenId)
 *                 - Non-null = fragment child navigation (parent is fragment with that screenId)
 * @param slideOffsetDp Horizontal slide offset in dp (default: 50dp).
 *                      Positive values slide new screen from right, negative from left.
 * @param initialScale Initial scale factor (default: 0.97f = 97% scale).
 *                     New screen starts at this scale and animates to 100%.
 * @param enterDurationMs Duration in milliseconds for enter animations
 * @param exitDurationMs Duration in milliseconds for exit/pop animations
 * @param enterEasing Easing function for enter animations
 * @param exitEasing Easing function for exit/pop animations
 */
data class SlideLeftScaleAnimationConfig(
    override val screenId: String? = null,
    val slideOffsetDp: Float,
    val initialScale: Float,
    override val enterDurationMs: Int,
    override val exitDurationMs: Int,
    override val enterEasing: AnimationEasing,
    override val exitEasing: AnimationEasing
) : AnimationConfig() {
    companion object {
        val Default = SlideLeftScaleAnimationConfig(
            screenId = null,
            slideOffsetDp = 50f,
            initialScale = 0.97f,
            enterDurationMs = 300,
            exitDurationMs = 300,
            enterEasing = AnimationEasing.CubicBezier(0.47f, 0.0f, 0.23f, 1.38f),
            exitEasing = AnimationEasing.AccelerateDecelerate
        )
    }
}

