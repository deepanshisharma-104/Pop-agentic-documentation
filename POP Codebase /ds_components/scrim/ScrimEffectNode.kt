// Copyright 2024, Christopher Banes and the Haze project contributors
// SPDX-License-Identifier: Apache-2.0
// 
// Standalone implementation adapted from haze library - Android only

@file:OptIn(ExperimentalScrimApi::class, InternalScrimApi::class)

package com.pop.components.ds_components.scrim

import androidx.collection.MutableObjectLongMap
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.snapshots.Snapshot
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.geometry.isUnspecified
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.GraphicsContext
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.GlobalPositionAwareModifierNode
import androidx.compose.ui.node.LayoutAwareModifierNode
import androidx.compose.ui.node.ObserverModifierNode
import androidx.compose.ui.node.TraversableNode
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.node.findNearestAncestor
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.node.observeReads
import androidx.compose.ui.node.requireDensity
import androidx.compose.ui.node.requireGraphicsContext
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.toIntSize
import androidx.compose.ui.unit.toSize
import kotlinx.coroutines.CoroutineScope
import kotlin.math.max
import kotlin.math.min

internal enum class ScrimTraversableNodeKeys {
    Effect,
    Source,
}

/**
 * The [Modifier.Node] implementation used by [scrimEffect].
 */
@ExperimentalScrimApi
class ScrimEffectNode(
    var state: ScrimState? = null,
    var block: (ScrimEffectScope.() -> Unit)? = null,
) : Modifier.Node(),
    CompositionLocalConsumerModifierNode,
    GlobalPositionAwareModifierNode,
    LayoutAwareModifierNode,
    ObserverModifierNode,
    DrawModifierNode,
    TraversableNode,
    ScrimEffectScope {

    override val traverseKey: Any
        get() = ScrimTraversableNodeKeys.Effect

    override val shouldAutoInvalidate: Boolean = false

    internal var dirtyTracker = Bitmask()

    override var inputScale: ScrimInputScale = ScrimInputScale.Default
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "inputScale changed. Current: $field. New: $value" }
                field = value
                dirtyTracker += DirtyFields.InputScale
            }
        }

    private var _positionOnScreen: Offset = Offset.Unspecified
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "positionOnScreen changed. Current: $field. New: $value" }
                dirtyTracker += DirtyFields.ScreenPosition
                field = value
            }
        }

    val positionOnScreen: Offset get() = _positionOnScreen

    internal var rootBoundsOnScreen: Rect = Rect.Zero
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "rootBoundsOnScreen changed. Current: $field. New: $value" }
                dirtyTracker += DirtyFields.ScreenPosition
                field = value
            }
        }

    private val areaOffsets = MutableObjectLongMap<ScrimArea>()

    private var _size: Size = Size.Unspecified
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "size changed. Current: $field. New: $value" }
                dirtyTracker += DirtyFields.Size
                field = value
            }
        }

    val size: Size get() = _size

    private var _layerSize: Size = Size.Unspecified
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "layerSize changed. Current: $field. New: $value" }
                dirtyTracker += DirtyFields.LayerSize
                field = value
            }
        }

    val layerSize: Size
        get() = _layerSize

    private var _layerOffset: Offset = Offset.Zero
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "layerOffset changed. Current: $field. New: $value" }
                dirtyTracker += DirtyFields.LayerOffset
                field = value
            }
        }

    val layerOffset: Offset
        get() = _layerOffset

    internal var windowId: Any? = null

    internal val visualEffectContext: VisualEffectContext by lazy(LazyThreadSafetyMode.NONE) {
        ScrimEffectNodeVisualEffectContext(this)
    }

    private var _areas: List<ScrimArea> = emptyList()
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "backgroundAreas changed. Current $field. New: $value" }
                dirtyTracker += DirtyFields.Areas

                // Remove the pre-draw listener from the current areas
                for (area in field) {
                    area.preDrawListeners -= areaPreDrawListener
                }
                // Add the pre-draw listener to all of the new areas
                for (area in value) {
                    area.preDrawListeners += areaPreDrawListener
                }
                field = value
            }
        }

    val areas: List<ScrimArea> get() = _areas

    private val contentDrawArea by lazy { ScrimArea() }

    override var canDrawArea: ((ScrimArea) -> Boolean)? = null
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "canDrawArea changed. Current $field. New: $value" }
                field = value
            }
        }

    override var visualEffect: VisualEffect = VisualEffect.Empty
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "visualEffect changed. Current $field. New: $value" }
                // detach old VisualEffect
                field.detach()
                field = value
                // attach new VisualEffect
                value.attach(visualEffectContext)
            }
        }

    override var drawContentBehind: Boolean = false
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "drawContentBehind changed. Current $field. New: $value" }
                dirtyTracker += DirtyFields.DrawContentBehind
                field = value
            }
        }

    override var clipToAreasBounds: Boolean? = null
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "clipToAreasBounds changed. Current $field. New: $value" }
                dirtyTracker += DirtyFields.ClipToAreas
                field = value
            }
        }

    override var expandLayerBounds: Boolean? = null
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "expandLayer changed. Current $field. New: $value" }
                dirtyTracker += DirtyFields.ExpandLayer
                field = value
            }
        }

    override var forceInvalidateOnPreDraw: Boolean = false
        set(value) {
            if (value != field) {
                ScrimLogger.d(TAG) { "forceInvalidateOnPreDraw changed. Current $field. New: $value" }
                dirtyTracker += DirtyFields.ForcePreDraw
                field = value
            }
        }

    private val areaPreDrawListener by lazy(LazyThreadSafetyMode.NONE) {
        OnPreDrawListener(::invalidateDraw)
    }

    internal fun update() {
        onObservedReadsChanged()
    }

    override fun onAttach() {
        visualEffect.attach(visualEffectContext)
        update()
    }

    override fun onDetach() {
        visualEffect.detach()
    }

    override fun onObservedReadsChanged() {
        observeReads(::updateEffect)
    }

    override fun onPlaced(coordinates: LayoutCoordinates) {
        Snapshot.withoutReadObservation {
            if (positionOnScreen.isUnspecified) {
                onPositioned(coordinates, "onPlaced")
            }
        }
    }

    override fun onGloballyPositioned(coordinates: LayoutCoordinates) {
        onPositioned(coordinates, "onGloballyPositioned")
    }

    private fun onPositioned(coordinates: LayoutCoordinates, source: String) {
        if (!isAttached) {
            return
        }

        _positionOnScreen = coordinates.positionForHaze()
        _size = coordinates.size.toSize()
        windowId = getWindowId()

        val rootLayoutCoords = coordinates.findRootCoordinates()
        rootBoundsOnScreen = Rect(
            offset = rootLayoutCoords.positionForHaze(),
            size = rootLayoutCoords.size.toSize(),
        )

        ScrimLogger.d(TAG) {
            "$source: positionOnScreen=$positionOnScreen, size=$size"
        }

        updateEffect()
    }

    override fun ContentDrawScope.draw() {
        try {
            ScrimLogger.d(TAG) { "-> start draw()" }

            if (!isAttached) {
                return
            }

            for (area in areas) {
                require(!area.isContentDrawing) {
                    "Modifier.scrimEffect nodes can not draw Modifier.scrimSource nodes. " +
                        "This should not happen if you are providing correct values for zIndex on Modifier.scrimSource. " +
                        "Alternatively you can use can `canDrawArea` to to filter out parent areas."
                }
            }

            if (this@ScrimEffectNode.size.isSpecified && this@ScrimEffectNode.layerSize.isSpecified) {
                if (state != null) {
                    if (areas.isNotEmpty()) {
                        // Background blurring
                        with(visualEffect) {
                            draw(visualEffectContext)
                        }
                    }
                    // Draw the content over the background
                    drawContentSafely()
                } else {
                    // Content (foreground) blurring
                    val contentLayer = contentDrawArea.contentLayer
                        ?.takeUnless { it.isReleased }
                        ?: requireGraphicsContext().createGraphicsLayer().also {
                            contentDrawArea.contentLayer = it
                            ScrimLogger.d(TAG) { "Updated contentLayer in content ScrimArea" }
                        }
                    // Record the this node's content into the layer
                    contentLayer.record(size.toIntSize()) {
                        this@draw.drawContentSafely()
                    }
                    if (drawContentBehind || with(visualEffect) { shouldDrawContentBehind(visualEffectContext) }) {
                        drawLayer(contentLayer)
                    }
                    with(visualEffect) {
                        draw(visualEffectContext)
                    }
                }
            } else {
                ScrimLogger.d(TAG) { "-> State not valid, so no need to draw effect." }
                drawContentSafely()
            }
        } finally {
            onPostDraw()
            ScrimLogger.d(TAG) { "-> end draw()" }
        }
    }

    private fun updateEffect(): Unit = trace("ScrimEffectNode-updateEffect") {
        // Allow the current VisualEffect to update from CompositionLocals/state
        visualEffect.update(visualEffectContext)
        windowId = getWindowId()

        // Invalidate if any of the effects triggered an invalidation
        block?.invoke(this)

        val backgroundBlurring = state != null

        _areas.forEach { area ->
            // Remove our pre draw listener from the current areas
            area.preDrawListeners -= areaPreDrawListener
        }

        _areas = if (backgroundBlurring) {
            val ancestorSourceNode =
                (findNearestAncestor(ScrimTraversableNodeKeys.Source) as? ScrimSourceNode)
                    ?.takeIf { it.state == this.state }

            state?.areas.orEmpty()
                .also {
                    ScrimLogger.d(TAG) { "Background Areas observing: $it" }
                }
                .asSequence()
                .filter { area ->
                    val filter = canDrawArea
                    when {
                        filter != null -> filter(area)
                        ancestorSourceNode != null -> area.zIndex < ancestorSourceNode.zIndex
                        else -> true
                    }.also { included ->
                        ScrimLogger.d(TAG) { "Background Area: $area. Included=$included" }
                    }
                }
                .toMutableList()
                .apply { sortBy(ScrimArea::zIndex) }
        } else {
            contentDrawArea.size = size
            contentDrawArea.positionOnScreen = positionOnScreen
            contentDrawArea.windowId = windowId
            listOf(contentDrawArea)
        }

        updateAreaOffsets()

        if (shouldUsePreDrawListener()) {
            for (area in areas) {
                area.preDrawListeners += areaPreDrawListener
            }
        }

        if (backgroundBlurring && areas.isNotEmpty() && size.isSpecified && positionOnScreen.isSpecified) {
            // Clip the expanded layer bounds
            val clippedLayerBounds = Rect(positionOnScreen, size)
                .letIf(shouldExpandLayer()) { visualEffect.calculateLayerBounds(it, requireDensity()) }
                .letIf(shouldClipToAreaBounds()) { rect ->
                    // Calculate the dimensions which covers all areas...
                    var left = Float.POSITIVE_INFINITY
                    var top = Float.POSITIVE_INFINITY
                    var right = Float.NEGATIVE_INFINITY
                    var bottom = Float.NEGATIVE_INFINITY
                    for (area in areas) {
                        val bounds = area.bounds ?: continue
                        left = min(left, bounds.left)
                        top = min(top, bounds.top)
                        right = max(right, bounds.right)
                        bottom = max(bottom, bounds.bottom)
                    }
                    rect.intersect(left, top, right, bottom)
                }
                .intersect(rootBoundsOnScreen)

            _layerSize = Size(
                width = clippedLayerBounds.width.coerceAtLeast(0f),
                height = clippedLayerBounds.height.coerceAtLeast(0f),
            )
            _layerOffset = positionOnScreen - clippedLayerBounds.topLeft
        } else if (!backgroundBlurring && size.isSpecified && !visualEffect.shouldClip() && shouldExpandLayer()) {
            val rect = size.toRect()
            val expanded = visualEffect.calculateLayerBounds(rect, requireDensity())
            _layerSize = expanded.size
            _layerOffset = rect.topLeft - expanded.topLeft
        } else {
            _layerSize = size
            _layerOffset = Offset.Zero
        }

        invalidateIfNeeded()
    }

    private fun onPostDraw() {
        dirtyTracker = Bitmask()
    }

    private fun invalidateIfNeeded() {
        val invalidateRequired =
            dirtyTracker.any(DirtyFields.InvalidateFlags) ||
                visualEffect.requireInvalidation()

        ScrimLogger.d(TAG) {
            "invalidateRequired=$invalidateRequired. " +
                "Dirty params=${DirtyFields.stringify(dirtyTracker)}"
        }

        if (invalidateRequired) {
            invalidateDraw()
        }
    }

    private fun updateAreaOffsets() {
        // Calculate new offsets and detect changes
        val hasAreaOffsetsChanged = when {
            areaOffsets.size != areas.size -> true
            else -> {
                areas.any { area ->
                    val newOffset = positionOnScreen - area.positionOnScreen
                    !areaOffsets.contains(area) || areaOffsets[area] != newOffset.packedValue
                }
            }
        }

        if (hasAreaOffsetsChanged) {
            ScrimLogger.d(TAG) { "areaOffsets changed" }
            dirtyTracker += DirtyFields.AreaOffsets

            areaOffsets.clear()
            areas.forEach { area ->
                val offset = positionOnScreen - area.positionOnScreen
                areaOffsets[area] = offset.packedValue
            }
        }
    }

    internal fun requirePlatformContext(): Any? {
        // On Android, return the Context from LocalContext
        return try {
            currentValueOf(LocalContext)
        } catch (_: Exception) {
            null
        }
    }

    internal companion object {
        const val TAG = "ScrimEffect"
    }
}

/**
 * Android-specific: Returns whether to invalidate on pre-draw for older Android versions.
 * For Android, this is typically false unless targeting very old versions.
 */
internal fun invalidateOnScrimAreaPreDraw(): Boolean = false

internal fun ScrimEffectNode.shouldClipToAreaBounds(): Boolean {
    clipToAreasBounds?.let { return it }
    return visualEffect.preferClipToAreaBounds()
}

internal fun ScrimEffectNode.shouldExpandLayer(): Boolean {
    expandLayerBounds?.let { return it }
    return true
}

internal fun ScrimEffectNode.shouldUsePreDrawListener(): Boolean {
    if (forceInvalidateOnPreDraw) return true
    if (invalidateOnScrimAreaPreDraw()) return true
    if (areas.any { it.windowId != windowId }) return true
    return false
}

@Suppress("ConstPropertyName", "ktlint:standard:property-naming")
internal object DirtyFields {
    const val InputScale: Int = 0b1
    const val ScreenPosition: Int = InputScale shl 1
    const val AreaOffsets: Int = ScreenPosition shl 1
    const val Size: Int = AreaOffsets shl 1
    const val Areas: Int = Size shl 1
    const val LayerSize: Int = Areas shl 1
    const val LayerOffset: Int = LayerSize shl 1
    const val DrawContentBehind: Int = LayerOffset shl 1
    const val ClipToAreas: Int = DrawContentBehind shl 1
    const val ExpandLayer: Int = ClipToAreas shl 1
    const val ForcePreDraw: Int = ExpandLayer shl 1

    const val InvalidateFlags: Int =
        InputScale or
            Size or
            ScreenPosition or
            LayerSize or
            LayerOffset or
            Areas or
            DrawContentBehind or
            ClipToAreas or
            ExpandLayer or
            ForcePreDraw

    fun stringify(dirtyTracker: Bitmask): String {
        val params = buildList {
            if (InputScale in dirtyTracker) add("InputScale")
            if (ScreenPosition in dirtyTracker) add("ScreenPosition")
            if (AreaOffsets in dirtyTracker) add("AreaOffsets")
            if (Size in dirtyTracker) add("Size")
            if (LayerSize in dirtyTracker) add("LayerSize")
            if (LayerOffset in dirtyTracker) add("LayerOffset")
            if (Areas in dirtyTracker) add("Areas")
            if (DrawContentBehind in dirtyTracker) add("DrawContentBehind")
            if (ClipToAreas in dirtyTracker) add("ClipToAreas")
            if (ExpandLayer in dirtyTracker) add("ExpandLayer")
            if (ForcePreDraw in dirtyTracker) add("ForcePreDraw")
        }
        return params.joinToString(separator = ", ", prefix = "[", postfix = "]")
    }
}

/**
 * Internal implementation of [VisualEffectContext] that wraps a [ScrimEffectNode].
 */
@OptIn(ExperimentalScrimApi::class, InternalScrimApi::class)
internal class ScrimEffectNodeVisualEffectContext(
    internal val node: ScrimEffectNode,
) : VisualEffectContext {

    override val positionOnScreen: Offset get() = node.positionOnScreen
    override val size: Size get() = node.size
    override val layerSize: Size get() = node.layerSize
    override val layerOffset: Offset get() = node.layerOffset
    override val rootBoundsOnScreen: Rect get() = node.rootBoundsOnScreen

    override val inputScale: ScrimInputScale get() = node.inputScale
    override val windowId: Any? get() = node.windowId
    override val areas: List<ScrimArea> get() = node.areas
    override val state: ScrimState? get() = node.state

    override val visualEffect: VisualEffect get() = node.visualEffect

    override val coroutineScope: CoroutineScope get() = node.coroutineScope
    override fun requireDensity(): Density = node.requireDensity()
    override fun <T> currentValueOf(local: CompositionLocal<T>): T = node.currentValueOf(local)
    override fun requireGraphicsContext(): GraphicsContext = node.requireGraphicsContext()
    override fun invalidateDraw() = node.invalidateDraw()
    override fun requirePlatformContext(): Any? = node.requirePlatformContext()
}

