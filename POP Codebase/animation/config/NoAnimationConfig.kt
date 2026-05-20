package com.pop.components.animation.config

/**
 * No Animation configuration.
 * Disables all screen transition animations, creating instant transitions.
 * All duration values should typically be 0ms for true instant transitions.
 *
 * @param screenId Unique identifier for the owner of childFragmentManager.
 *                 - `null` = activity-level navigation (parent is activity, uses activity's screenId)
 *                 - Non-null = fragment child navigation (parent is fragment with that screenId)
 * @param enterDurationMs Duration in milliseconds for enter animations (typically 0)
 * @param exitDurationMs Duration in milliseconds for exit/pop animations (typically 0)
 * @param enterEasing Easing function for enter animations (unused when duration is 0)
 * @param exitEasing Easing function for exit/pop animations (unused when duration is 0)
 */
data class NoAnimationConfig(
    override val screenId: String? = null,
    override val enterDurationMs: Int,
    override val exitDurationMs: Int,
    override val enterEasing: AnimationEasing,
    override val exitEasing: AnimationEasing
) : AnimationConfig() {
    companion object {
        val Default = NoAnimationConfig(
            screenId = null,
            enterDurationMs = 0,
            exitDurationMs = 0,
            enterEasing = AnimationEasing.EaseOutCubic,
            exitEasing = AnimationEasing.EaseOutCubic
        )
    }
}

