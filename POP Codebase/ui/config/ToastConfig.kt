package com.pop.components.ui.config

import com.pop.components.theme.PopIconConfig

/**
 * Configuration data class for toast messages.
 *
 * @param title The title/message text to display
 * @param startIcon Optional icon to display at the start of the toast
 * @param showCloseIcon Whether to show a close icon on the toast
 * @param endCta Optional end call-to-action configuration
 * @param animDuration Duration in milliseconds for the toast animation
 * @param toastDuration How long the toast should be displayed (SHORT or LONG)
 * @param type The type of toast (MESSAGE, INFO, SUCCESS, ERROR)
 */
data class ToastConfig(
    val title: String,
    val startIcon: PopIconConfig? = null,
    val showCloseIcon: Boolean = false,
    val endCta: EndCtaConfig? = null,
    val animDuration: Long = 300L,
    val toastDuration: ToastDuration = ToastDuration.LONG,
    val type: ToastType = ToastType.MESSAGE,
)
