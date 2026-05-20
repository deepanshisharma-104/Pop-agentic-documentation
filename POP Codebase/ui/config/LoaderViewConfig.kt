package com.pop.components.ui.config

import androidx.compose.ui.graphics.Color
import com.pop.components.theme.PopGradient

data class LoaderViewConfig(
    val show: Boolean,
    val barGradient: PopGradient? = null,
    val glowColor: Color? = null,
    val title: String? = null,
    val body: String? = null,
    val showScrim: Boolean = true,
    val enableSweepAnimation: Boolean = true,
    val hideDelayMillis: Long = 2000L,
    val useEntryStyleTextAnimation: Boolean = false,
) {
    fun getGradient(): PopGradient? = barGradient
}

