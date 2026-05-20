package com.pop.components.coachmarks

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

/**
 * Helper class for initializing and managing coach marks.
 * 
 * Usage:
 * ```kotlin
 * class MyActivity : FragmentActivity() {
 *     private lateinit var coachMarkHelper: CoachMarkHelper
 *     
 *     override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         coachMarkHelper = CoachMarkHelper(activity = this, popUIManager = popUIManager)
 *     }
 *     
 *     private fun showOnboarding() {
 *         coachMarkHelper.show(
 *             steps = CoachMarkSteps.homeOnboardingSteps(),
 *             onComplete = { /* Mark as completed */ },
 *             onSkip = { /* Handle skip */ }
 *         )
 *     }
 * }
 * ```
 */
class CoachMarkHelper(
    private val activity: FragmentActivity,
    private val popUIManager: com.pop.components.ui.PopCommonUiManager
) {
    
    /**
     * Shows a coach mark flow with the given steps.
     * 
     * @param steps List of coach mark steps to display
     * @param onComplete Callback invoked when all steps are completed
     * @param onSkip Callback invoked when user skips the flow
     * @param onDismiss Callback invoked when flow is dismissed
     */
    fun show(
        steps: List<CoachMarkStep>,
        onComplete: (() -> Unit)? = null,
        onSkip: (() -> Unit)? = null,
        onDismiss: (() -> Unit)? = null
    ) {
        activity.lifecycleScope.launch {
            popUIManager.showCoachMarks(
                CoachMarkConfig(
                    steps = steps,
                    onComplete = onComplete ?: {},
                    onSkip = onSkip ?: {},
                    onDismiss = onDismiss ?: {}
                )
            )
        }
    }
    
    /**
     * Dismisses any currently showing coach marks.
     */
    fun dismiss() {
        activity.lifecycleScope.launch {
            popUIManager.dismissCoachMarks()
        }
    }
    
    /**
     * Checks if coach marks are currently showing.
     */
    suspend fun isShowing(): Boolean {
        // You can add state tracking here if needed
        return false
    }
}
