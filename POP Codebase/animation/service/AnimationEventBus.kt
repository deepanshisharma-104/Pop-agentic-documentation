package com.pop.components.animation.service

import com.pop.components.animation.event.AnimationEvent
import kotlinx.coroutines.flow.SharedFlow

/**
 * Service interface for animation event bus.
 * Used for broadcasting animation-related events (navigation, go back, etc.).
 */
interface AnimationEventBus {
    /**
     * Observable flow for animation events.
     * Consumers can collect from this flow to handle navigation and other events.
     */
    val animationEventBus: SharedFlow<AnimationEvent>

    /**
     * Emits an animation event to the animation event bus.
     *
     * @param event The AnimationEvent to emit
     */
    fun emitEvent(event: AnimationEvent)
}

