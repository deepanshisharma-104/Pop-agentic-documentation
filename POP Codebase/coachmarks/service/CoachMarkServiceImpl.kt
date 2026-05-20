package com.pop.components.coachmarks.service

import com.pop.components.coachmarks.CoachMarkConfig
import com.pop.components.coachmarks.CoachMarkEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

/**
 * Implementation of CoachMarkService.
 * Uses SharedFlow for coach mark events.
 */
class CoachMarkServiceImpl @Inject constructor() : CoachMarkService {
    
    /**
     * Private mutable SharedFlow for coach mark events.
     * Configuration:
     * - replay = 0: New subscribers don't receive past events
     * - extraBufferCapacity = 1: Buffers one event if no collectors are active
     */
    private val _coachMarkEvent = MutableSharedFlow<CoachMarkEvent>(
        extraBufferCapacity = 1,
        replay = 0
    )
    
    override val coachMarkEvent: SharedFlow<CoachMarkEvent> = _coachMarkEvent.asSharedFlow()
    
    override fun showCoachMarks(config: CoachMarkConfig) {
        _coachMarkEvent.tryEmit(CoachMarkEvent.Show(config))
    }
    
    override fun dismissCoachMarks() {
        _coachMarkEvent.tryEmit(CoachMarkEvent.Dismiss)
    }
}
