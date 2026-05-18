// Copyright 2025, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library - Android only

@file:OptIn(ExperimentalScrimApi::class, InternalScrimApi::class)

package com.pop.components.ds_components.scrim

import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.takeOrElse
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.unit.roundToIntSize
import kotlin.math.max

/**
 * Creates a scaled content layer and draws it with the given block.
 */
internal fun DrawScope.createAndDrawScaledContentLayer(
    context: VisualEffectContext,
    releaseLayerOnExit: Boolean = true,
    block: DrawScope.(GraphicsLayer) -> Unit,
) {
    val graphicsContext = context.requireGraphicsContext()

    val effect = context.visualEffect
    val scaleFactor = effect.calculateInputScaleFactor(context.inputScale)
    val clip = effect.shouldClip()

    val layer = createScaledContentLayer(
        context = context,
        scaleFactor = scaleFactor,
        layerSize = context.layerSize,
        layerOffset = context.layerOffset,
        backgroundColor = (effect as? BlurVisualEffect)?.backgroundColor ?: Color.Transparent,
    )

    if (layer != null) {
        layer.clip = clip

        drawScaledContent(
            offset = -context.layerOffset,
            scaledSize = size * scaleFactor,
            clip = clip,
        ) {
            block(layer)
        }

        if (releaseLayerOnExit) {
            graphicsContext.releaseGraphicsLayer(layer)
        }
    }
}

internal fun DrawScope.createScaledContentLayer(
    context: VisualEffectContext,
    backgroundColor: Color,
    scaleFactor: Float,
    layerSize: Size,
    layerOffset: Offset,
): GraphicsLayer? {
    val scaledLayerSize = (layerSize * scaleFactor).roundToIntSize()

    if (scaledLayerSize.width <= 0 || scaledLayerSize.height <= 0) {
        // If we have a 0px dimension we can't do anything so just return
        return null
    }

    // Now we need to draw `contentNode` into each of an 'effect' graphic layers.
    // The RenderEffect applied will provide the blurring effect.
    val graphicsContext = context.requireGraphicsContext()
    val layer = graphicsContext.createGraphicsLayer()

    layer.record(size = scaledLayerSize) {
        if (backgroundColor.isSpecified) {
            drawRect(backgroundColor)
        }

        scale(scale = scaleFactor, pivot = Offset.Zero) {
            translate(layerOffset - context.positionOnScreen) {
                for (area in context.areas) {
                    val position = Snapshot.withoutReadObservation {
                        area.positionOnScreen.takeOrElse { Offset.Zero }
                    }
                    translate(position) {
                        // Draw the content into our effect layer. We do want to observe this via snapshot
                        // state
                        val areaLayer = area.contentLayer
                            ?.takeUnless { it.isReleased }
                            ?.takeUnless { it.size.width <= 0 || it.size.height <= 0 }

                        if (areaLayer != null) {
                            ScrimLogger.d("Blur") { "Drawing ScrimArea GraphicsLayer: $areaLayer" }
                            drawLayer(areaLayer)
                        } else {
                            ScrimLogger.d("Blur") { "ScrimArea GraphicsLayer is not valid" }
                        }
                    }
                }
            }
        }
    }

    return layer
}

internal fun DrawScope.drawScaledContent(
    offset: Offset,
    scaledSize: Size,
    clip: Boolean = true,
    block: DrawScope.() -> Unit,
) {
    val scaleFactor = max(size.width / scaledSize.width, size.height / scaledSize.height)
    optionalClipRect(enabled = clip) {
        translate(offset) {
            scale(scale = scaleFactor, pivot = Offset.Zero) {
                block()
            }
        }
    }
}

private inline fun DrawScope.optionalClipRect(
    enabled: Boolean,
    left: Float = 0.0f,
    top: Float = 0.0f,
    right: Float = size.width,
    bottom: Float = size.height,
    clipOp: ClipOp = ClipOp.Intersect,
    block: DrawScope.() -> Unit,
) = withTransform(
    transformBlock = {
        if (enabled) {
            clipRect(left, top, right, bottom, clipOp)
        }
    },
    drawBlock = block,
)

