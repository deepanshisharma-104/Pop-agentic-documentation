// Copyright 2025, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library

package com.pop.components.ds_components.scrim

import androidx.compose.runtime.CompositionLocal
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.GraphicsContext
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.unit.Density
import kotlinx.coroutines.CoroutineScope

/**
 * A visual effect that can be applied to content behind or in front of a composable.
 */
@ExperimentalScrimApi
interface VisualEffect {
    /**
     * Draws the effect.
     */
    fun DrawScope.draw(context: VisualEffectContext)

    /**
     * Called when this effect is attached to a context.
     */
    fun attach(context: VisualEffectContext): Unit = Unit

    /**
     * Called when the effect should update its state from composition locals or other sources.
     */
    fun update(context: VisualEffectContext): Unit = Unit

    /**
     * Called when this effect is detached from its context.
     */
    fun detach(): Unit = Unit

    /**
     * Returns whether the content should be drawn behind the effect for foreground blurring.
     */
    fun DrawScope.shouldDrawContentBehind(context: VisualEffectContext): Boolean = false

    /**
     * Returns whether the effect output should be clipped to the node bounds.
     */
    fun shouldClip(): Boolean = false

    /**
     * Calculates the input scale factor based on the given scale configuration.
     */
    fun calculateInputScaleFactor(scale: ScrimInputScale): Float = when (scale) {
        is ScrimInputScale.None -> 1f
        is ScrimInputScale.Fixed -> scale.scale
        ScrimInputScale.Auto -> 1f
    }

    /**
     * Returns whether the effect requires draw invalidation.
     */
    fun requireInvalidation(): Boolean = false

    /**
     * Returns whether the effect prefers to clip to area bounds.
     */
    fun preferClipToAreaBounds(): Boolean = false

    /**
     * Calculates the layer bounds required for this effect.
     */
    fun calculateLayerBounds(rect: Rect, density: Density): Rect = rect

    companion object {
        /**
         * An empty and no-op visual effect that does nothing.
         */
        val Empty: VisualEffect get() = EmptyVisualEffect
    }
}

@OptIn(ExperimentalScrimApi::class)
private object EmptyVisualEffect : VisualEffect {
    override fun DrawScope.draw(context: VisualEffectContext) {
        // No-op
    }
}

/**
 * Context provided to [VisualEffect] implementations during their lifecycle.
 */
@ExperimentalScrimApi
interface VisualEffectContext {
    /**
     * The position of the effect node on screen.
     */
    val positionOnScreen: Offset

    /**
     * The size of the effect node.
     */
    val size: Size

    /**
     * The size of the graphics layer used for rendering the effect.
     */
    val layerSize: Size

    /**
     * The offset of the graphics layer relative to the effect node's position.
     */
    val layerOffset: Offset

    /**
     * The bounds of the root layout coordinates on screen.
     */
    val rootBoundsOnScreen: Rect

    /**
     * The input scale factor configuration for this effect.
     */
    val inputScale: ScrimInputScale

    /**
     * An identifier for the window containing this effect.
     */
    val windowId: Any?

    /**
     * The list of [ScrimArea]s that this effect should process.
     */
    val areas: List<ScrimArea>

    /**
     * The [ScrimState] associated with this effect, if any.
     */
    val state: ScrimState?

    /**
     * The [VisualEffect] currently attached to this context.
     */
    val visualEffect: VisualEffect

    /**
     * Returns the current [Density] for pixel-to-dp conversions.
     */
    fun requireDensity(): Density

    /**
     * Returns the current value of the given [CompositionLocal].
     */
    fun <T> currentValueOf(local: CompositionLocal<T>): T

    /**
     * Returns the [GraphicsContext] for creating and managing graphics layers.
     */
    fun requireGraphicsContext(): GraphicsContext

    /**
     * CoroutineScope to launch coroutines from.
     */
    val coroutineScope: CoroutineScope

    /**
     * Requests a redraw of the effect.
     */
    fun invalidateDraw()
    
    /**
     * Returns the platform-specific context required for certain rendering operations.
     * On Android, this returns the Android Context.
     */
    @InternalScrimApi
    fun requirePlatformContext(): Any?
}

/**
 * Provides a [GraphicsLayer] for temporary use within the [block], automatically
 * releasing it when done.
 */
@ExperimentalScrimApi
inline fun <R> VisualEffectContext.withGraphicsLayer(block: (GraphicsLayer) -> R): R {
    val graphicsContext = requireGraphicsContext()
    val layer = graphicsContext.createGraphicsLayer()
    return try {
        block(layer)
    } finally {
        graphicsContext.releaseGraphicsLayer(layer)
    }
}

