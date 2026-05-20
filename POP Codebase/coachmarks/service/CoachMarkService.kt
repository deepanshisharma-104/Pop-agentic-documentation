package com.pop.components.coachmarks.service

import com.pop.components.coachmarks.CoachMarkConfig
import com.pop.components.coachmarks.CoachMarkEvent
import kotlinx.coroutines.flow.SharedFlow

/**
 * Service interface for managing coach mark display.
 */
interface CoachMarkService {
    /**
     * Observable flow for coach mark events.
     * Consumers can collect from this flow to show/hide coach marks.
     */
    val coachMarkEvent: SharedFlow<CoachMarkEvent>
    
    /**
     * Shows coach marks with the given configuration.
     *
     * @param config CoachMarkConfig containing the coach mark steps
     */
    fun showCoachMarks(config: CoachMarkConfig)
    
    /**
     * Dismisses any currently showing coach marks.
     */
    fun dismissCoachMarks()
}
