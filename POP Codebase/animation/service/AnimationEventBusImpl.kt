package com.pop.components.animation.service

import com.pop.components.animation.event.AnimationEvent
import timber.log.Timber
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import java.util.concurrent.atomic.AtomicLong

/**
 * Implementation of AnimationEventBus.
 * Uses SharedFlow for one-time events (navigation, go back, etc.).
 */
internal class AnimationEventBusImpl @Inject constructor() : AnimationEventBus {

    companion object {
        /**
         * Buffer capacity for events when no collectors are active.
         * Set to 1 to handle rapid emit-collect cycles without blocking.
         * This allows one event to be buffered if emitted before a collector is ready.
         */
        private const val EVENT_BUFFER_CAPACITY = 1
    }

    /**
     * Private mutable SharedFlow for animation events.
     * Configuration:
     * - replay = 0: New subscribers don't receive past events (one-time events only)
     * - extraBufferCapacity = EVENT_BUFFER_CAPACITY: Buffers one event if no collectors are active
     */
    private val _animationEventBus = MutableSharedFlow<AnimationEvent>(
        extraBufferCapacity = EVENT_BUFFER_CAPACITY,
        replay = 0
    )

    override val animationEventBus: SharedFlow<AnimationEvent> = _animationEventBus.asSharedFlow()

    // Performance monitoring
    private val eventEmissionCount = AtomicLong(0)
    private val eventEmissionStartTime = AtomicLong(System.currentTimeMillis())

    override fun emitEvent(event: AnimationEvent) {
        val startTime = System.currentTimeMillis()
        val success = _animationEventBus.tryEmit(event)
        val emissionTime = System.currentTimeMillis() - startTime
        
        val count = eventEmissionCount.incrementAndGet()
        
        // Log performance metrics every 50 events or if emission failed
        if (count % 50 == 0L || !success) {
            val totalTime = System.currentTimeMillis() - eventEmissionStartTime.get()
            val avgTime = if (count > 0) totalTime / count else 0
            
            if (!success) {
                Timber.tag("AnimationEventBus").e(
                    "Failed to emit event: ${event::class.simpleName} (animationId: ${getAnimationId(event)}). " +
                    "Buffer may be full. Emission time: ${emissionTime}ms"
                )
            } else {
                Timber.tag("AnimationEventBus").d(
                    "Performance: Total events: $count, Avg time: ${avgTime}ms, Last emission: ${emissionTime}ms"
                )
            }
        }
    }

    private fun getAnimationId(event: AnimationEvent): String {
        return when (event) {
            is AnimationEvent.Navigate -> event.animationId
            is AnimationEvent.GoBack -> event.animationId
            is AnimationEvent.InvokeSplitScreenEntryAnimation -> event.splitScreenAnimationConfig.animationId
            is AnimationEvent.InvokeSplitScreenExitAnimation -> event.splitScreenAnimationConfig.animationId
            is AnimationEvent.InvokeMaskRevealEntryAnimation -> event.maskRevealAnimationConfig.animationId
            is AnimationEvent.InvokeMaskRevealExitAnimation -> event.maskRevealAnimationConfig.animationId
            is AnimationEvent.ChangeWindowScale -> "window-scale"
        }
    }
}

