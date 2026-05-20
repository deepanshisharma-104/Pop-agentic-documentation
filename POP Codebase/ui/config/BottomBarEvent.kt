package com.pop.components.ui.config

sealed class BottomBarEvent {
    data object Expand : BottomBarEvent()
    data object Collapse : BottomBarEvent()
    data object ScrollToTop : BottomBarEvent()
}