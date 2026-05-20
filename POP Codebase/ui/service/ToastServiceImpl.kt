package com.pop.components.ui.service

import com.pop.components.ui.config.ToastConfig
import com.pop.components.ui.config.ToastType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

/**
 * Implementation of ToastService.
 * Uses SharedFlow for one-time toast events.
 */
class ToastServiceImpl @Inject constructor() : ToastService {

    /**
     * Private mutable SharedFlow for toast events.
     * Configuration:
     * - replay = 0: New subscribers don't receive past events (one-time events only)
     * - extraBufferCapacity = 1: Buffers one event if no collectors are active
     */
    private val _toastEvent = MutableSharedFlow<ToastConfig>(
        extraBufferCapacity = 1,
        replay = 0
    )

    override val toastEvent: SharedFlow<ToastConfig> = _toastEvent.asSharedFlow()

    override fun showToast(config: ToastConfig) {
        _toastEvent.tryEmit(config)
    }

    override fun showToast(message: String, type: ToastType) {
        showToast(ToastConfig(title = message, type = type))
    }
}

