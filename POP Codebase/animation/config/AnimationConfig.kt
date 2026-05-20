package com.pop.components.animation.config

/**
 * Base class containing common configuration parameters for screen transition animations.
 *
 * This base class contains properties that are common to all animation types:
 * - Screen ID for tracking navigation history
 * - Duration settings (enter/exit)
 * - Easing functions (enter/exit)
 *
 * Each specific animation type extends this class with its own specific properties.
 *
 * @param screenId Unique identifier for the owner of childFragmentManager.
 *                 - `null` = activity-level navigation (parent is activity, uses activity's screenId)
 *                 - Non-null = fragment child navigation (parent is fragment with that screenId)
 */
abstract class AnimationConfig {
    abstract val screenId: String?
    abstract val enterDurationMs: Int
    abstract val exitDurationMs: Int
    abstract val enterEasing: AnimationEasing
    abstract val exitEasing: AnimationEasing
}
