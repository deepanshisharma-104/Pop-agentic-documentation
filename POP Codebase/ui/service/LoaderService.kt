package com.pop.components.ui.service

import com.pop.components.ui.config.LoaderViewConfig
import kotlinx.coroutines.flow.SharedFlow

/**
 * Service interface for managing loader views.
 */
interface LoaderService {
    /**
     * Observable flow for loader events.
     */
    val loaderEvent: SharedFlow<LoaderViewConfig>

    /**
     * Toggles the loader view.
     *
     * @param config LoaderViewConfig containing the loader configuration
     */
    fun toggleLoader(config: LoaderViewConfig)
}

