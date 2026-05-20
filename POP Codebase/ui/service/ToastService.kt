package com.pop.components.ui.service

import com.pop.components.ui.config.ToastConfig
import com.pop.components.ui.config.ToastType
import kotlinx.coroutines.flow.SharedFlow

/**
 * Service interface for showing toast messages.
 */
interface ToastService {
    /**
     * Observable flow for toast events.
     * Consumers can collect from this flow to show toast messages.
     */
    val toastEvent: SharedFlow<ToastConfig>

    /**
     * Shows a toast message.
     *
     * @param config ToastConfig containing the toast message details
     */
    fun showToast(config: ToastConfig)

    /**
     * Convenience method to show a toast with a simple message.
     *
     * @param message The message text to display
     * @param type The type of toast (default: MESSAGE)
     */
    fun showToast(message: String, type: ToastType = ToastType.MESSAGE)
}

