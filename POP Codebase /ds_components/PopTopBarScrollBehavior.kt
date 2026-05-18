package com.pop.components.ds_components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import androidx.compose.runtime.snapshotFlow

/**
 * Scroll behavior configuration for PopTopBar
 * 
 * These values control at what scroll progress (0-1) different animation stages occur.
 */
data class PopTopBarScrollBehaviorConfig(
    /** Maximum scroll distance before fully collapsed */
    val maxScrollDistance: Dp = 320.dp,
    /** Progress at which title bar starts moving up (0f to 1f) */
    val titleBarMoveStart: Float = 0f,
    /** Progress at which title bar starts fading out (0f to 1f) */
    val titleBarHideStart: Float = 0.5f,
    /** Progress at which blur effect starts (0f to 1f) */
    val blurStart: Float = 0.7f,
    /** Progress at which merchant image starts fading (0f to 1f) */
    val merchantImageHideStart: Float = 0.35f
)

/**
 * Scroll behavior state for PopTopBar.
 * 
 * This class tracks scroll offset and provides derived animation values.
 * With MotionLayout, the scrollOffset is updated from scroll observation,
 * and all animation values are derived from the scroll progress (0-1).
 * 
 * The state is designed to work with:
 * - Compose LazyColumn: scroll position observed via LazyListState
 * - XML RecyclerView: scroll position reported via callback
 */
class PopTopBarScrollBehaviorState(
    val config: PopTopBarScrollBehaviorConfig = PopTopBarScrollBehaviorConfig()
) {
    /** Current scroll offset in pixels */
    var scrollOffset: Float by mutableFloatStateOf(0f)
        internal set

    var maxScrollDistancePx: Float = 0f
        private set
    
    /** Maximum height offset that can be applied (when top bar is fully collapsed) */
    var maxHeightOffsetPx: Float = 0f
        internal set

    /** Scroll progress from 0f (expanded) to 1f (collapsed) */
    val scrollProgress: Float
        get() = if (maxScrollDistancePx > 0f) {
            (scrollOffset / maxScrollDistancePx).coerceIn(0f, 1f)
        } else {
            0f
        }

    /**
     * Whether the top bar should stay collapsed until user scrolls up at top.
     */
    var forceCollapsed: Boolean by mutableStateOf(false)
        private set

    /** Whether title bar should be visible (alpha > 0) */
    val isTitleBarVisible: Boolean
        get() = titleBarAlpha > 0f

    /** Title bar vertical offset (moves up as scroll progresses) */
    val titleBarOffsetPx: Float
        get() {
            if (scrollProgress < config.titleBarMoveStart) return 0f
            val moveProgress = if (config.titleBarHideStart > config.titleBarMoveStart) {
                ((scrollProgress - config.titleBarMoveStart) / (config.titleBarHideStart - config.titleBarMoveStart)).coerceIn(0f, 1f)
            } else {
                1f
            }
            return maxScrollDistancePx * moveProgress * 0.7f
        }
    
    /** Title bar alpha (fades out as scroll progresses) */
    val titleBarAlpha: Float
        get() {
            if (scrollProgress < config.titleBarHideStart) {
                return 1f
            } else if (scrollProgress >= config.blurStart) {
                return 0f
            } else {
                val fadeRange = config.blurStart - config.titleBarHideStart
                if (fadeRange > 0f) {
                    val fadeProgress = ((scrollProgress - config.titleBarHideStart) / fadeRange).coerceIn(0f, 1f)
                    return 1f - fadeProgress
                } else {
                    return 0f
                }
            }
        }
    
    /** Merchant image offset (moves up as scroll progresses) */
    val merchantImageOffsetPx: Float
        get() {
            if (scrollProgress < config.titleBarMoveStart) return 0f
            val moveProgress = if (config.merchantImageHideStart > config.titleBarMoveStart) {
                ((scrollProgress - config.titleBarMoveStart) / (config.merchantImageHideStart - config.titleBarMoveStart)).coerceIn(0f, 1f)
            } else {
                1f
            }
            return maxScrollDistancePx * moveProgress
        }
    
    /** Merchant image alpha (fades out as scroll progresses) */
    val merchantImageAlpha: Float
        get() {
            if (scrollProgress < config.merchantImageHideStart) {
                return 1f
            } else {
                val fadeProgress = ((scrollProgress - config.merchantImageHideStart) / (1f - config.merchantImageHideStart)).coerceIn(0f, 1f)
                return 1f - (fadeProgress * fadeProgress)
            }
        }

    /** Blur intensity from 0f (no blur) to 1f (full blur) */
    val blurIntensity: Float
        get() = if (scrollProgress >= config.blurStart) {
            ((scrollProgress - config.blurStart) / (1f - config.blurStart)).coerceIn(0f, 1f)
        } else {
            0f
        }

    /** Current height offset for collapsing animation (in pixels) */
    val heightOffsetPx: Float
        get() = scrollOffset.coerceIn(0f, maxHeightOffsetPx)
    
    /** Whether the top bar has reached its collapsed height and user is scrolling content below */
    val isScrollingContentBelow: Boolean
        get() = scrollOffset > maxHeightOffsetPx

    internal fun updateMaxScrollDistance(maxDistancePx: Float) {
        maxScrollDistancePx = maxDistancePx
    }
    
    internal fun updateMaxHeightOffset(maxHeightOffsetPx: Float) {
        this.maxHeightOffsetPx = maxHeightOffsetPx
    }

    internal fun updateScrollOffset(offset: Float) {
        scrollOffset = offset.coerceAtLeast(0f)
    }

    internal fun requestForceCollapse() {
        forceCollapsed = true
    }

    internal fun clearForceCollapse() {
        forceCollapsed = false
    }
}

/**
 * Creates and remembers a PopTopBarScrollBehaviorState
 */
@Composable
fun rememberPopTopBarScrollBehavior(
    config: PopTopBarScrollBehaviorConfig = PopTopBarScrollBehaviorConfig()
): PopTopBarScrollBehaviorState {
    return remember { PopTopBarScrollBehaviorState(config) }
}

/**
 * Connects a ScrollState to PopTopBarScrollBehaviorState.
 * This tracks scroll offset and updates the behavior state.
 * 
 * @deprecated Use LazyListState observation in PopTopBarWithContent instead.
 * This is kept for backward compatibility with standalone PopTopBar usage.
 */
@Composable
fun PopTopBarScrollBehaviorState.connectToScrollState(
    scrollState: ScrollState,
    maxScrollDistance: Dp
) {
    val density = LocalDensity.current
    val maxDistancePx = remember(maxScrollDistance) {
        with(density) { maxScrollDistance.toPx() }
    }
    
    LaunchedEffect(maxDistancePx) {
        updateMaxScrollDistance(maxDistancePx)
    }
    
    LaunchedEffect(scrollState, maxDistancePx) {
        snapshotFlow { scrollState.value }
            .collect { scrollPosition ->
                val clampedOffset = scrollPosition.toFloat().coerceIn(0f, maxDistancePx)
                updateScrollOffset(clampedOffset)
            }
    }
}

/**
 * Connects a LazyListState to PopTopBarScrollBehaviorState.
 * This tracks scroll offset and updates the behavior state.
 * 
 * @deprecated Use LazyListState observation in PopTopBarWithContent instead.
 * This is kept for backward compatibility with standalone PopTopBar usage.
 */
@Composable
fun PopTopBarScrollBehaviorState.connectToLazyListState(
    lazyListState: LazyListState,
    maxScrollDistance: Dp
) {
    val density = LocalDensity.current
    val maxDistancePx = remember(maxScrollDistance) {
        with(density) { maxScrollDistance.toPx() }
    }
    
    LaunchedEffect(maxDistancePx) {
        updateMaxScrollDistance(maxDistancePx)
    }
    
    LaunchedEffect(lazyListState, maxDistancePx) {
        snapshotFlow { 
            val firstVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()
            when {
                firstVisibleItem != null && firstVisibleItem.index == 0 -> {
                    val offsetPx = (-firstVisibleItem.offset).toFloat()
                    offsetPx.coerceIn(0f, maxDistancePx)
                }
                firstVisibleItem != null && firstVisibleItem.index > 0 -> {
                    maxDistancePx
                }
                else -> {
                    0f
                }
            }
        }
            .distinctUntilChanged()
            .collect { offset: Float ->
                updateScrollOffset(offset)
            }
    }
}

/**
 * No-op nested scroll connection that doesn't consume any scroll events.
 * Used when content doesn't need scrolling to prevent top bar from collapsing.
 */
internal val NoOpNestedScrollConnection = object : NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset = Offset.Zero
    override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset = Offset.Zero
    override suspend fun onPreFling(available: androidx.compose.ui.unit.Velocity): androidx.compose.ui.unit.Velocity = androidx.compose.ui.unit.Velocity.Zero
    override suspend fun onPostFling(consumed: androidx.compose.ui.unit.Velocity, available: androidx.compose.ui.unit.Velocity): androidx.compose.ui.unit.Velocity = androidx.compose.ui.unit.Velocity.Zero
}

/**
 * Controller for programmatically controlling PopTopBar scroll behavior.
 * 
 * This controller allows external components to trigger collapse/expand of the top bar,
 * which is useful for scenarios like:
 * - Collapsing the top bar when a search field gets focus
 * - Expanding the top bar when navigating back to a screen
 * - Programmatic control based on user actions
 * 
 * Usage:
 * ```kotlin
 * val scrollController = rememberPopTopBarScrollController()
 * 
 * PopTopBarWithContent(
 *     scrollController = scrollController,
 *     ...
 * ) {
 *     SearchBar(
 *         onFocusChanged = { isFocused ->
 *             if (isFocused) {
 *                 scrollController.collapseToMinimum()
 *             }
 *         }
 *     )
 * }
 * ```
 */
class PopTopBarScrollController {
    internal var scrollBehaviorState: PopTopBarScrollBehaviorState? = null
    
    /**
     * Whether the top bar is currently collapsed to its minimum height
     */
    val isCollapsed: Boolean
        get() = scrollBehaviorState?.let { state ->
            state.scrollOffset >= state.maxHeightOffsetPx && state.maxHeightOffsetPx > 0f
        } ?: false
    
    /**
     * Whether the top bar is currently fully expanded
     */
    val isExpanded: Boolean
        get() = scrollBehaviorState?.scrollOffset == 0f
    
    /**
     * Current scroll progress from 0f (expanded) to 1f (collapsed)
     */
    val scrollProgress: Float
        get() = scrollBehaviorState?.scrollProgress ?: 0f
    
    /**
     * Programmatically collapse the top bar to its minimum height.
     */
    fun collapseToMinimum() {
        scrollBehaviorState?.let { state ->
            if (state.maxHeightOffsetPx > 0f) {
                state.requestForceCollapse()
                state.updateScrollOffset(state.maxHeightOffsetPx)
            }
        }
    }
    
    /**
     * Programmatically expand the top bar to its maximum height.
     */
    fun expand() {
        scrollBehaviorState?.let { state ->
            state.clearForceCollapse()
            state.updateScrollOffset(0f)
        }
    }
    
    /**
     * Set the scroll offset to a specific value.
     * @param offset The scroll offset in pixels. 0 = fully expanded, maxHeightOffsetPx = fully collapsed
     */
    fun setScrollOffset(offset: Float) {
        scrollBehaviorState?.updateScrollOffset(offset)
    }
}

/**
 * Creates and remembers a [PopTopBarScrollController].
 * 
 * Use this controller to programmatically control the PopTopBar scroll behavior,
 * such as collapsing when a search field gets focus.
 * 
 * @return A remembered instance of [PopTopBarScrollController]
 */
@Composable
fun rememberPopTopBarScrollController(): PopTopBarScrollController {
    return remember { PopTopBarScrollController() }
}
