// Copyright 2025, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library

package com.pop.components.ds_components.scrim

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.layer.GraphicsLayer
import kotlin.RequiresOptIn

/**
 * A state holder that manages rendering targets for blur effects.
 * 
 * This state must be shared between:
 * - Source content marked with `scrimSource(state = scrimState)`
 * - Effect composables using the blur modifier functions
 */
@ExperimentalScrimApi
@Stable
class ScrimState {
    private val _areas = mutableStateListOf<ScrimArea>()
    val areas: List<ScrimArea> get() = _areas

    internal fun addArea(area: ScrimArea) {
        _areas += area
    }

    internal fun removeArea(area: ScrimArea) {
        _areas -= area
    }
}

/**
 * Represents an area of content that can be used as a source for blur effects.
 */
@Stable
class ScrimArea {
    var positionOnScreen: Offset by mutableStateOf(Offset.Unspecified)
        internal set

    var size: Size by mutableStateOf(Size.Unspecified)
        internal set

    var zIndex: Float by mutableFloatStateOf(0f)
        internal set

    var key: Any? = null
        internal set

    var windowId: Any? = null
        internal set

    internal val preDrawListeners = mutableStateSetOf<OnPreDrawListener>()

    /**
     * The content [GraphicsLayer].
     */
    var contentLayer: GraphicsLayer? by mutableStateOf(null)
        internal set

    internal val bounds: Rect?
        get() = when {
            size.isSpecified && positionOnScreen.isSpecified -> Rect(positionOnScreen, size)
            else -> null
        }

    internal var contentDrawing: Boolean = false

    @InternalScrimApi
    val isContentDrawing: Boolean
        get() = contentDrawing

    @OptIn(InternalScrimApi::class)
    override fun toString(): String = buildString {
        append("ScrimArea(")
        append("positionOnScreen=$positionOnScreen, ")
        append("size=$size, ")
        append("zIndex=$zIndex, ")
        append("contentLayer=$contentLayer, ")
        append("isContentDrawing=$isContentDrawing")
        append(")")
    }
}

internal fun ScrimArea.reset() {
    positionOnScreen = Offset.Unspecified
    size = Size.Unspecified
    contentDrawing = false
}

internal fun interface OnPreDrawListener {
    operator fun invoke()
}

/**
 * Creates and remembers a [ScrimState] instance.
 */
@Composable
@ExperimentalScrimApi
fun rememberScrimState(): ScrimState = remember { ScrimState() }

/**
 * Annotation for experimental Scrim API.
 */
@RequiresOptIn(message = "Experimental Scrim API", level = RequiresOptIn.Level.WARNING)
annotation class ExperimentalScrimApi

/**
 * Annotation for internal Scrim API.
 */
@RequiresOptIn(message = "Internal Scrim API", level = RequiresOptIn.Level.ERROR)
annotation class InternalScrimApi

