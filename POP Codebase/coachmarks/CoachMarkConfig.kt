package com.pop.components.coachmarks

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

/**
 * Configuration for displaying a series of coach marks (onboarding tooltips).
 *
 * @param steps List of coach mark steps to display in sequence
 * @param onComplete Callback when all coach marks are completed
 * @param onSkip Callback when user skips the coach marks
 * @param onDismiss Callback when coach marks are dismissed (optional)
 */
data class CoachMarkConfig(
    val steps: List<CoachMarkStep>,
    val onComplete: () -> Unit = {},
    val onSkip: () -> Unit = {},
    val onDismiss: () -> Unit = {},
    /**
     * Called whenever the user taps Next, Skip, or Done on a step.
     * [stepId] is the [CoachMarkStep.id] of the active step.
     * [cta] is "Next", "Skip", or "Done".
     */
    val onStepAction: (stepId: String, cta: String) -> Unit = { _, _ -> },
)

/**
 * Configuration for a single coach mark step.
 *
 * @param id Unique identifier for this step
 * @param title Title text for the coach mark dialog
 * @param subtitle Optional subtitle text for the coach mark dialog
 * @param targetViewKey Optional key to identify the target view to highlight
 * @param targetRect Manual target rectangle (used if targetViewKey is not set)
 * @param highlightStyle Style of highlighting (BORDER or FILLED_SQUIRCLE)
 * @param dialogPosition Preferred position for the dialog (TOP or BOTTOM)
 * @param dialogAlignment Preferred horizontal alignment (CENTER, START, END, or AUTO)
 * @param scrollToTarget Whether to scroll to the target if it's not visible
 * @param scrollOffset Offset to apply when scrolling to target
 * @param dimOpacity Opacity of the dimming overlay (0.0 to 1.0)
 * @param onStepShown Callback when this step is shown
 */
data class CoachMarkStep(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val targetViewKey: String? = null,
    val targetRect: Rect? = null,
    val highlightStyle: HighlightStyle = HighlightStyle.BORDER,
    val cornerRadiusDp: Float? = null,
    val dialogPosition: DialogPosition = DialogPosition.AUTO,
    val dialogAlignment: DialogAlignment = DialogAlignment.AUTO,
    val scrollToTarget: Boolean = false,
    val scrollOffset: Float = 0f,
    val dimOpacity: Float = 0.7f,
    val onStepShown: () -> Unit = {},
    /** Optional suspend action to scroll the target into view before showing this step. */
    val scrollAction: (suspend () -> Unit)? = null,
)

/**
 * Style of highlighting for the target component.
 */
enum class HighlightStyle {
    /**
     * White border around the component (1dp stroke)
     */
    BORDER,
    
    /**
     * Squircle (rounded rectangle) shape with white border for larger components
     */
    FILLED_SQUIRCLE,
    
    /**
     * Perfect circular highlight (for circular buttons like FABs)
     */
    CIRCLE
}

/**
 * Position of the coach mark dialog relative to the target.
 */
enum class DialogPosition {
    /**
     * Position the dialog above the target (if space available)
     */
    TOP,
    
    /**
     * Position the dialog below the target (if space available)
     */
    BOTTOM,
    
    /**
     * Automatically choose the best position based on available space
     */
    AUTO
}

/**
 * Horizontal alignment of the coach mark dialog relative to the target.
 */
enum class DialogAlignment {
    /**
     * Center-align the dialog with the target
     */
    CENTER,
    
    /**
     * Align the dialog to the start (left) of the target
     */
    START,
    
    /**
     * Align the dialog to the end (right) of the target
     */
    END,
    
    /**
     * Automatically choose the best alignment based on available space
     */
    AUTO
}

/**
 * Event to show coach marks.
 */
sealed class CoachMarkEvent {
    /**
     * Show coach marks with the given configuration.
     */
    data class Show(val config: CoachMarkConfig) : CoachMarkEvent()
    
    /**
     * Dismiss any currently showing coach marks.
     */
    data object Dismiss : CoachMarkEvent()
}

/**
 * Internal state for managing coach mark display.
 */
data class CoachMarkState(
    val config: CoachMarkConfig? = null,
    val currentStepIndex: Int = 0,
    val isShowing: Boolean = false,
) {
    val currentStep: CoachMarkStep?
        get() = config?.steps?.getOrNull(currentStepIndex)
    
    val totalSteps: Int
        get() = config?.steps?.size ?: 0
    
    val isLastStep: Boolean
        get() = currentStepIndex == totalSteps - 1
    
    val canGoNext: Boolean
        get() = currentStepIndex < totalSteps - 1
}
