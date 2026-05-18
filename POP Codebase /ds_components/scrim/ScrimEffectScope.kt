// Copyright 2023, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library

package com.pop.components.ds_components.scrim

/**
 * A receiver scope for configuring scrim effects.
 */
@ExperimentalScrimApi
interface ScrimEffectScope {
    /**
     * The visual effect implementation used by this node.
     */
    var visualEffect: VisualEffect

    /**
     * The input scale factor configuration.
     */
    @ExperimentalScrimApi
    var inputScale: ScrimInputScale

    /**
     * A block which controls whether this effect should draw the given [ScrimArea].
     */
    @ExperimentalScrimApi
    var canDrawArea: ((ScrimArea) -> Boolean)?

    /**
     * Whether to draw the content behind the blurred effect for foreground blurring.
     */
    var drawContentBehind: Boolean

    /**
     * Whether the drawn effect should be clipped to the total bounds which cover all of the areas.
     */
    var clipToAreasBounds: Boolean?

    /**
     * Whether the layer should be expanded on all edges.
     */
    var expandLayerBounds: Boolean?

    /**
     * Force draw invalidation from pre-draw events of contributing [ScrimArea]s.
     */
    var forceInvalidateOnPreDraw: Boolean
}

