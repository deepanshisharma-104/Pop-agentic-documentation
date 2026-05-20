package com.pop.components.animation.config

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing

/**
 * Sealed class representing different easing functions for animations.
 * 
 * This allows for type-safe configuration of animation easing while maintaining
 * compatibility with Compose animation easing types.
 */
sealed class AnimationEasing {
    /**
     * EaseOutCubic easing - decelerates smoothly.
     * Approximates @android:anim/decelerate_interpolator used in XML animations.
     */
    object EaseOutCubic : AnimationEasing() {
        fun toComposeEasing(): Easing = androidx.compose.animation.core.EaseOutCubic
    }
    
    /**
     * AccelerateDecelerate easing - accelerates then decelerates.
     * Equivalent to @android:anim/accelerate_decelerate_interpolator used in XML animations.
     */
    object AccelerateDecelerate : AnimationEasing() {
        fun toComposeEasing(): Easing = FastOutSlowInEasing
    }
    
    /**
     * Cubic Bezier easing with custom control points.
     * Allows for custom easing curves including overshoot effects.
     * 
     * @param controlX1 First control point X coordinate (0.0 to 1.0)
     * @param controlY1 First control point Y coordinate
     * @param controlX2 Second control point X coordinate (0.0 to 1.0)
     * @param controlY2 Second control point Y coordinate (can exceed 1.0 for overshoot)
     */
    data class CubicBezier(
        val controlX1: Float,
        val controlY1: Float,
        val controlX2: Float,
        val controlY2: Float
    ) : AnimationEasing() {
        fun toComposeEasing(): Easing = CubicBezierEasing(
            controlX1, controlY1, controlX2, controlY2
        )
    }
}

