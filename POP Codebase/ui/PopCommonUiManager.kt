package com.pop.components.ui

import com.pop.components.ui.config.BottomBarEvent
import com.pop.components.coachmarks.CoachMarkConfig
import com.pop.components.coachmarks.CoachMarkEvent
import com.pop.components.ui.config.LoaderViewConfig
import com.pop.components.ui.config.ToastConfig
import com.pop.components.ui.config.ToastType
import com.pop.components.ui.service.BottomBarService
import com.pop.components.coachmarks.service.CoachMarkService
import com.pop.components.ui.service.LoaderService
import com.pop.components.ui.service.ToastService
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

/**
 * Manager for UI feedback components (Toast, Loader, and CoachMark).
 * Provides a unified API for displaying toast messages, managing loader views, and showing coach marks.
 */
class PopCommonUiManager @Inject constructor(
    private val toastService: ToastService,
    private val loaderService: LoaderService,
    private val bottomBarService: BottomBarService,
    private val coachMarkService: CoachMarkService,
) {

    // ========== Toast Methods ==========

    /**
     * Observable flow for toast events.
     * Consumers can collect from this flow to show toast messages.
     */
    val toastEvent: SharedFlow<ToastConfig> = toastService.toastEvent

    /**
     * Shows a toast message.
     *
     * @param config ToastConfig containing the toast message details
     */
    fun showToast(config: ToastConfig) {
        toastService.showToast(config)
    }

    /**
     * Convenience method to show a toast with a simple message.
     *
     * @param message The message text to display
     * @param type The type of toast (default: MESSAGE)
     */
    fun showToast(
        message: String,
        type: ToastType = ToastType.MESSAGE
    ) {
        toastService.showToast(message, type)
    }

    // ========== Loader Methods ==========

    /**
     * Observable flow for loader events.
     * Consumers can collect from this flow to show/hide loader views.
     */
    val loaderEvent: SharedFlow<LoaderViewConfig> = loaderService.loaderEvent

    /**
     * Toggles the loader view.
     *
     * @param config LoaderViewConfig containing the loader configuration
     */
    fun toggleLoader(config: LoaderViewConfig) {
        loaderService.toggleLoader(config)
    }

    // ========== Bottom Bar Methods ==========

    /**
     * Observable flow for bottom bar events.
     * Consumers can collect from this flow to show/hide loader views.
     */
    val bottomBarEvent: SharedFlow<BottomBarEvent> = bottomBarService.bottomBarEvent

    /**
     * Emits Bottom Bar Event
     *
     * @param bottomBarEvent BottomBarEvent for specifying event type
     */
    fun emitBottomBarEvent(bottomBarEvent: BottomBarEvent) {
        bottomBarService.emitBottomBarEvent(bottomBarEvent)
    }

    // ========== Coach Mark Methods ==========

    /**
     * Observable flow for coach mark events.
     * Consumers can collect from this flow to show/hide coach marks.
     */
    val coachMarkEvent: SharedFlow<CoachMarkEvent> = coachMarkService.coachMarkEvent

    /**
     * Shows coach marks with the given configuration.
     *
     * @param config CoachMarkConfig containing the coach mark steps
     */
    fun showCoachMarks(config: CoachMarkConfig) {
        coachMarkService.showCoachMarks(config)
    }

    /**
     * Dismisses any currently showing coach marks.
     */
    fun dismissCoachMarks() {
        coachMarkService.dismissCoachMarks()
    }
}

