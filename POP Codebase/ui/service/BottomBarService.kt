package com.pop.components.ui.service

import com.pop.components.ui.config.BottomBarEvent
import kotlinx.coroutines.flow.SharedFlow

interface BottomBarService {

    val bottomBarEvent: SharedFlow<BottomBarEvent>

    fun emitBottomBarEvent(bottomBarEvent: BottomBarEvent)
}