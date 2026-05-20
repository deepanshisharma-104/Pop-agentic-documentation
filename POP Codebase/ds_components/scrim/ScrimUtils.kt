// Copyright 2024, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library - Android only

package com.pop.components.ds_components.scrim

import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.platform.LocalView
import timber.log.Timber
import kotlin.jvm.JvmInline

/**
 * Bitmask utility for tracking dirty fields.
 */
@JvmInline
@InternalScrimApi
internal value class Bitmask(private val value: Int = 0) {
    internal operator fun plus(flag: Int): Bitmask = Bitmask(value or flag)
    internal operator fun minus(flag: Int): Bitmask = Bitmask(value and flag.inv())
    internal operator fun contains(flag: Int): Boolean = (flag and value) == flag
    internal fun any(flag: Int): Boolean = (flag and value) != 0
    internal fun isEmpty(): Boolean = value == 0
}

/**
 * Logger for scrim infrastructure.
 */
internal object ScrimLogger {
    /**
     * Whether to print debug log statements. Do not enable in release builds.
     */
    var enabled: Boolean = false

    fun d(tag: String, message: () -> String) {
        d(tag = tag, throwable = null, message = message)
    }

    fun d(tag: String, throwable: Throwable?, message: () -> String) {
        if (enabled) {
            Snapshot.withoutReadObservation {
                Timber.tag(tag).d(buildString {
                    append(message())
                    if (throwable != null) {
                        append(". Throwable: ")
                        append(throwable)
                    }
                })
            }
        }
    }
}

/**
 * Android-specific: We use positionOnScreen on Android to support dialogs, popup windows, etc.
 */
internal fun LayoutCoordinates.positionForHaze(): Offset = positionOnScreen()

/**
 * Android-specific: Get the window ID from LocalView.
 */
internal fun CompositionLocalConsumerModifierNode.getWindowId(): Any? {
    return currentValueOf(LocalView).windowId
}

internal inline fun <T> T.letIf(condition: Boolean, block: (T) -> T): T {
    return if (condition) block(this) else this
}

internal fun ContentDrawScope.drawContentSafely() {
    try {
        drawContent()
    } catch (e: Exception) {
        val message = e.message.orEmpty()
        // Handle known issues gracefully
        if ("mViewFlags" in message || "LayoutNode" in message) {
            ScrimLogger.d("ContentDrawScope", e) { "Error whilst drawing content" }
        } else {
            throw e
        }
    }
}

internal inline fun trace(tag: String, block: () -> Unit): Unit = block()

