package com.pop.components.ui.service

import com.pop.components.ui.config.LoaderViewConfig
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

/**
 * Implementation of LoaderService.
 */
class LoaderServiceImpl @Inject constructor() : LoaderService {

    private val _loaderEvent = MutableSharedFlow<LoaderViewConfig>(
        extraBufferCapacity = 1,
        replay = 0
    )

    override val loaderEvent: SharedFlow<LoaderViewConfig> = _loaderEvent.asSharedFlow()

    override fun toggleLoader(config: LoaderViewConfig) {
        _loaderEvent.tryEmit(config)
    }
}

