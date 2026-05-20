// Copyright 2025, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library

package com.pop.components.ds_components.scrim

import kotlin.jvm.JvmInline

/**
 * Configuration for input scaling, which allows rendering the effect at a lower resolution
 * before scaling it back up for performance optimization.
 * 
 * Input scaling can improve performance at the cost of some quality.
 */
@ExperimentalScrimApi
sealed interface ScrimInputScale {
    /**
     * No input scaling. This is functionally the same as `Fixed(1.0f)`
     */
    data object None : ScrimInputScale

    /**
     * Automatic input scaling. The system will attempt to use an appropriate input scale
     * depending on the other settings which have been set.
     */
    data object Auto : ScrimInputScale

    /**
     * An input scale which uses a fixed scale factor.
     *
     * @param scale The scale factor, in the range 0 < x <= 1.
     */
    @JvmInline
    value class Fixed(val scale: Float) : ScrimInputScale {
        init {
            require(scale > 0f && scale <= 1f) {
                "scale needs to be in the range 0 < x <= 1f"
            }
        }
    }

    companion object {
        /**
         * The default [ScrimInputScale] value. Currently this resolves to [ScrimInputScale.None].
         */
        @ExperimentalScrimApi
        val Default: ScrimInputScale get() = None
    }
}

