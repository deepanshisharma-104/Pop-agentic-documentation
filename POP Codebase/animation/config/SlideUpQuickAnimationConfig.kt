package com.pop.components.animation.config

/**
 * Slide Up Quick animation configuration.
 * Uses percentage-based offset for slide-in (scales with screen size) and dp-based for slide-out.
 *
 * @param screenId Unique identifier for the owner of childFragmentManager.
 *                 - `null` = activity-level navigation (parent is activity, uses activity's screenId)
 *                 - Non-null = fragment child navigation (parent is fragment with that screenId)
 * @param slideInOffsetPercent Vertical slide-in offset as percentage of screen height (default: 0.25f = 25%).
 *                             New screen starts this percentage of screen height below its final position.
 * @param slideOutOffsetDp Vertical slide-out offset in dp (default: 30dp).
 *                         Current screen slides up by this amount when exiting.
 * @param enterDurationMs Duration in milliseconds for enter animations
 * @param exitDurationMs Duration in milliseconds for exit/pop animations
 * @param enterEasing Easing function for enter animations
 * @param exitEasing Easing function for exit/pop animations
 */
data class SlideUpQuickAnimationConfig(
    override val screenId: String? = null,
    val slideInOffsetPercent: Float,
    val slideOutOffsetDp: Float,
    override val enterDurationMs: Int,
    override val exitDurationMs: Int,
    override val enterEasing: AnimationEasing,
    override val exitEasing: AnimationEasing
) : AnimationConfig() {
    companion object {
        val Default = SlideUpQuickAnimationConfig(
            screenId = null,
            slideInOffsetPercent = 0.25f,
            slideOutOffsetDp = 30f,
            enterDurationMs = 300,
            exitDurationMs = 300,
            enterEasing = AnimationEasing.CubicBezier(0.22f, 0.0f, 0.0f, 1.10f), // Matches slide_up_bezier.xml
            exitEasing = AnimationEasing.EaseOutCubic // Matches decelerate_interpolator
        )
    }
}

