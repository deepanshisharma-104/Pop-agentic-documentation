package com.pop.components.ui.service

import com.pop.components.ui.config.BottomBarEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class BottomBarServiceImpl : BottomBarService {

    private val _bottomBarEvent = MutableSharedFlow<BottomBarEvent>(
        extraBufferCapacity = 1,
        replay = 0
    )

    override val bottomBarEvent: SharedFlow<BottomBarEvent> = _bottomBarEvent.asSharedFlow()

    override fun emitBottomBarEvent(bottomBarEvent: BottomBarEvent) {
        _bottomBarEvent.tryEmit(bottomBarEvent)
    }

}