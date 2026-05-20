package com.pop.components.animation.event

import com.pop.components.animation.config.MaskRevealAnimationConfig
import com.pop.components.animation.config.SplitScreenAnimationConfig

/**
 * Base sealed class for all animation-related events.
 * 
 * This sealed class serves as the base for all events that can be emitted
 * through the AnimationManager event bus. Events are used to coordinate
 * complex animations (Split Screen, Mask Reveal) between Activity and Fragments.
 * 
 * Event Flow:
 * 1. Fragment emits entry/exit animation events to Activity
 * 2. Activity performs the visual animation
 * 3. Activity emits Navigate/GoBack events back to Fragment
 * 4. Fragment executes the actual navigation operation
 * 
 * For slide animations, events are not used - navigation happens directly.
 */
sealed class AnimationEvent {
    /**
     * Navigation event emitted by Activity after animation completes.
     * 
     * This event signals to the Fragment that it should execute the pending navigation
     * operation. The Fragment looks up the pending navigation by [animationId] and
     * performs the actual fragment transaction, NavController navigation, etc.
     * 
     * Flow:
     * 1. Fragment calls extension function (e.g., navigateChildWithSplitScreen)
     * 2. Extension stores PendingNavigation with animationId
     * 3. Extension emits InvokeSplitScreenEntryAnimation to Activity
     * 4. Activity performs animation, then emits Navigate(animationId, targetScreenId)
     * 5. Fragment receives Navigate event and executes pending navigation
     * 
     * @param animationId Unique identifier matching the animationId from the config.
     *                    Used to look up the corresponding PendingNavigation in the Fragment.
     * @param targetScreenId Optional screenId of the target fragment. If provided, only the
     *                       fragment with matching screenId will process this event.
     *                       If null, all fragments will attempt to process the event.
     */
    data class Navigate(
        val animationId: String,
        val targetScreenId: String? = null
    ) : AnimationEvent()

    /**
     * Go back event emitted by Activity after exit animation completes.
     * 
     * This event signals to the Fragment that it should execute the pending back navigation
     * operation. The Fragment looks up the pending back operation by [animationId] and
     * performs the actual back navigation (popBackStack, etc.).
     * 
     * Flow:
     * 1. User presses back, Fragment calls handleChildBackWithAnimation
     * 2. Fragment stores PendingBackOperation with animationId
     * 3. Fragment emits InvokeSplitScreenExitAnimation to Activity
     * 4. Activity performs exit animation, then emits GoBack(animationId, targetScreenId)
     * 5. Fragment receives GoBack event and executes pending back navigation
     * 
     * @param animationId Unique identifier matching the animationId from the config.
     *                    Used to look up the corresponding PendingBackOperation in the Fragment.
     * @param targetScreenId Optional screenId of the target fragment. If provided, only the
     *                       fragment with matching screenId will process this event.
     *                       If null, all fragments will attempt to process the event.
     */
    data class GoBack(
        val animationId: String,
        val targetScreenId: String? = null
    ): AnimationEvent()

    /**
     * Invoke Split Screen Entry Animation Event.
     * 
     * Emitted by Fragment to request Activity to perform the split screen entry animation.
     * The Activity will:
     * 1. Capture the current screen and split it into upper/lower halves
     * 2. Perform the MotionLayout transition animation
     * 3. Emit Navigate event back to Fragment to trigger actual navigation
     * 
     * This event is only used for Split Screen animations. Slide animations
     * are handled directly without events.
     * 
     * @param splitScreenAnimationConfig The configuration for the split screen animation,
     *                                   containing splitAnchorPercent, durations, easing, etc.
     */
    data class InvokeSplitScreenEntryAnimation(val splitScreenAnimationConfig: SplitScreenAnimationConfig): AnimationEvent()

    /**
     * Invoke Split Screen Exit Animation Event.
     * 
     * Emitted by Fragment to request Activity to perform the split screen exit animation
     * when navigating back. The Activity will:
     * 1. Restore the previously captured screen halves
     * 2. Perform the reverse MotionLayout transition animation
     * 3. Emit GoBack event back to Fragment to trigger actual back navigation
     * 
     * This event is only used for Split Screen animations. Slide animations
     * are handled directly without events.
     * 
     * @param splitScreenAnimationConfig The configuration for the split screen exit animation.
     *                                   Should match the config used for the entry animation.
     */
    data class InvokeSplitScreenExitAnimation(val splitScreenAnimationConfig: SplitScreenAnimationConfig): AnimationEvent()

    /**
     * Invoke Mask Reveal Entry Animation Event.
     * 
     * Emitted by Fragment to request Activity to perform the mask reveal entry animation.
     * The Activity will:
     * 1. Capture the current screen
     * 2. Apply a circular/elliptical mask reveal effect using View clipping
     * 3. Emit Navigate event back to Fragment to trigger actual navigation
     * 
     * This event is only used for Mask Reveal animations. Slide animations
     * are handled directly without events.
     * 
     * @param maskRevealAnimationConfig The configuration for the mask reveal animation,
     *                                  containing verticalOriginFraction, durations, easing, etc.
     */
    data class InvokeMaskRevealEntryAnimation(val maskRevealAnimationConfig: MaskRevealAnimationConfig): AnimationEvent()

    /**
     * Invoke Mask Reveal Exit Animation Event.
     * 
     * Emitted by Fragment to request Activity to perform the mask reveal exit animation
     * when navigating back. The Activity will:
     * 1. Restore the previously captured screen
     * 2. Perform the reverse mask reveal effect
     * 3. Emit GoBack event back to Fragment to trigger actual back navigation
     * 
     * This event is only used for Mask Reveal animations. Slide animations
     * are handled directly without events.
     * 
     * @param maskRevealAnimationConfig The configuration for the mask reveal exit animation.
     *                                   Should match the config used for the entry animation.
     */
    data class InvokeMaskRevealExitAnimation(val maskRevealAnimationConfig: MaskRevealAnimationConfig): AnimationEvent()

    /**
     * Change Window Scale Event.
     * 
     * Emitted to request a change in the window scale. This is used for special
     * UI effects that require scaling the entire window.
     * 
     * @param scale The scale factor to apply (1.0 = normal size, >1.0 = larger, <1.0 = smaller)
     */
    data class ChangeWindowScale(val scale: Float): AnimationEvent()
}

