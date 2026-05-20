package com.pop.components.ds_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.layoutId
import com.pop.components.ds_components.scrim.ExperimentalScrimApi
import com.pop.components.ds_components.scrim.blurModifier
import com.pop.components.ds_components.scrim.rememberScrimState
import com.pop.components.ds_components.scrim.scrimSource
import com.pop.components.theme.SurfaceColor
import kotlin.math.roundToInt

/**
 * CompositionLocal for the current motion progress (0f = expanded, 1f = collapsed).
 * Top bar content can use this to adapt layout when collapsing.
 */
val LocalTopBarMotionProgress = compositionLocalOf { 0f }

/**
 * Wrapper composable that provides the same scroll-collapse behavior as [PopTopBarWithContent],
 * but accepts an arbitrary [topBarContent] composable instead of [PopTopBarConfig].
 *
 * Use this when you need a custom top bar of any size and content (e.g. custom header,
 * image, multiple rows, or non-DS layout). The top bar content keeps its full height
 * ([topBarExpandedHeight]) and scrolls up and out of view as the user scrolls (the visible
 * window shrinks to [topBarCollapsedHeight]); content is not squished. Use [LocalTopBarMotionProgress]
 * to adapt (e.g. fade or hide secondary content when collapsed).
 *
 * Features:
 * - Same MotionLayout-based collapse/expand as [PopTopBarWithContent]
 * - Nested scroll: scroll collapses top bar first, then content
 * - Same CompositionLocals: [LocalPopScrimState], [LocalStickyHeaderScrimState],
 *   [LocalContentAvailableHeight], etc., for use in content
 * - Optional [bottomFixedContent] and [scrollController]
 *
 * @param modifier Modifier for the root container
 * @param topBarContent Composable for the top bar (any size/content). Receives a slot that
 *        fills the animating top bar area; use [LocalTopBarMotionProgress] to adapt if needed.
 * @param topBarExpandedHeight Height of the top bar when fully expanded (required for animation).
 * @param topBarCollapsedHeight Height when fully collapsed (default: 92.dp = status + app bar).
 * @param scrollBehaviorKey Optional key to reset scroll when it changes (e.g. screen/tab).
 * @param lazyListState Optional LazyListState when content is a LazyColumn (for scroll detection).
 * @param xmlContentScrollable Optional: when content is XML, whether it can scroll.
 * @param xmlContentAtTop Optional: when content is XML, whether content is at top.
 * @param xmlScrollPosition Optional: when content is XML, current scroll position in px.
 * @param bottomFixedContent Optional fixed content at the bottom (e.g. buttons).
 * @param scrollController Optional controller for programmatic collapse/expand.
 * @param content The scrollable content below the top bar.
 */
@OptIn(ExperimentalScrimApi::class, ExperimentalMotionApi::class)
@Composable
fun PopTopBarWithCustomContent(
    modifier: Modifier = Modifier,
    topBarContent: @Composable () -> Unit,
    topBarExpandedHeight: Dp,
    topBarCollapsedHeight: Dp = 92.dp, // status bar + app bar
    scrollBehaviorKey: Any? = null,
    lazyListState: LazyListState? = null,
    xmlContentScrollable: Boolean? = null,
    xmlContentAtTop: Boolean? = null,
    xmlScrollPosition: Int? = null,
    bottomFixedContent: (@Composable () -> Unit)? = null,
    scrollController: PopTopBarScrollController? = null,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    val scrimState = rememberScrimState()
    val stickyHeaderScrimState = rememberScrimState()

    val maxScrollDistancePx = with(density) { topBarExpandedHeight.toPx() }
    val collapsedHeightPx = with(density) { topBarCollapsedHeight.toPx() }
    val maxHeightOffsetPx = (maxScrollDistancePx - collapsedHeightPx).coerceAtLeast(0f)
    val canCollapse = maxHeightOffsetPx > 0f

    val scrollBehavior = remember(scrollBehaviorKey) {
        PopTopBarScrollBehaviorState()
    }

    LaunchedEffect(maxScrollDistancePx, maxHeightOffsetPx) {
        scrollBehavior.updateMaxScrollDistance(maxScrollDistancePx)
        scrollBehavior.updateMaxHeightOffset(maxHeightOffsetPx)
    }

    LaunchedEffect(scrollController, scrollBehavior) {
        scrollController?.scrollBehaviorState = scrollBehavior
    }

    var screenHeightPx by remember { mutableFloatStateOf(0f) }
    var contentHeightPx by remember { mutableFloatStateOf(0f) }
    var bottomFixedContentHeightPx by remember { mutableFloatStateOf(0f) }
    val stickyHeaderHeightPxState = remember { mutableStateOf(0f) }

    var currentScrollOffsetPx by remember(scrollBehaviorKey) { mutableFloatStateOf(0f) }

    LaunchedEffect(scrollBehavior.scrollOffset) {
        if (scrollBehavior.scrollOffset != currentScrollOffsetPx) {
            currentScrollOffsetPx = scrollBehavior.scrollOffset
        }
    }

    LaunchedEffect(scrollBehavior.forceCollapsed, maxHeightOffsetPx, canCollapse) {
        if (scrollBehavior.forceCollapsed && canCollapse && maxHeightOffsetPx > 0f) {
            currentScrollOffsetPx = maxHeightOffsetPx
            scrollBehavior.updateScrollOffset(maxHeightOffsetPx)
        }
    }

    LaunchedEffect(scrollBehaviorKey) {
        currentScrollOffsetPx = 0f
        scrollBehavior.updateScrollOffset(0f)
        scrollBehavior.clearForceCollapse()
    }

    val motionProgress by remember(scrollBehaviorKey, maxHeightOffsetPx) {
        derivedStateOf {
            if (maxHeightOffsetPx > 0f) {
                (currentScrollOffsetPx / maxHeightOffsetPx).coerceIn(0f, 1f)
            } else 0f
        }
    }

    val systemInsets = WindowInsets.systemBars.union(WindowInsets.displayCutout)
    val viewportHeightPx = remember(
        screenHeightPx,
        bottomFixedContentHeightPx,
        maxScrollDistancePx,
        density,
        systemInsets
    ) {
        with(density) {
            val resolvedScreenHeightPx = if (screenHeightPx > 0f) {
                screenHeightPx
            } else {
                configuration.screenHeightDp.dp.toPx()
            }
            val topInsetPx = systemInsets.getTop(this).toFloat()
            val bottomInsetPx = if (bottomFixedContentHeightPx > 0f) {
                bottomFixedContentHeightPx
            } else {
                systemInsets.getBottom(this).toFloat()
            }
            val expandedTopBarHeightPx = (maxScrollDistancePx + topInsetPx).coerceAtLeast(0f)
            (resolvedScreenHeightPx - expandedTopBarHeightPx - bottomInsetPx).coerceAtLeast(0f)
        }
    }

    var cachedContentNeedsScrolling by remember(scrollBehaviorKey) { mutableStateOf(false) }

    val contentNeedsScrolling by remember(
        scrollBehaviorKey,
        lazyListState,
        xmlContentScrollable,
        contentHeightPx,
        viewportHeightPx
    ) {
        derivedStateOf {
            if (lazyListState != null) {
                val info = lazyListState.layoutInfo
                val first = info.visibleItemsInfo.firstOrNull()
                val last = info.visibleItemsInfo.lastOrNull()
                val needsScroll = when {
                    first == null || last == null -> false
                    else -> {
                        val allVisible = first.index == 0 && last.index == info.totalItemsCount - 1
                        if (!allVisible) true
                        else {
                            val totalContentSize = info.visibleItemsInfo.sumOf { it.size }.toFloat()
                            if (cachedContentNeedsScrolling) {
                                totalContentSize > viewportHeightPx - 50
                            } else {
                                totalContentSize > viewportHeightPx + 50
                            }
                        }
                    }
                }
                cachedContentNeedsScrolling = needsScroll
                needsScroll
            } else {
                val needsScroll = if (cachedContentNeedsScrolling) {
                    contentHeightPx > viewportHeightPx - 50f
                } else {
                    contentHeightPx > viewportHeightPx + 50f
                }
                cachedContentNeedsScrolling = needsScroll
                xmlContentScrollable ?: needsScroll
            }
        }
    }

    val isNestedScrollActive = remember(
        contentNeedsScrolling,
        canCollapse,
        lazyListState,
        xmlScrollPosition
    ) {
        (contentNeedsScrolling || scrollBehavior.forceCollapsed) && canCollapse &&
            (lazyListState != null || (xmlScrollPosition != null && lazyListState == null))
    }

    LaunchedEffect(xmlScrollPosition, maxHeightOffsetPx, scrollBehavior.forceCollapsed, isNestedScrollActive) {
        if (xmlScrollPosition != null && canCollapse && !scrollBehavior.forceCollapsed && !isNestedScrollActive) {
            val newOffset = xmlScrollPosition.toFloat().coerceIn(0f, maxHeightOffsetPx)
            currentScrollOffsetPx = newOffset
            scrollBehavior.updateScrollOffset(newOffset)
        }
    }

    LaunchedEffect(canCollapse) {
        if (!canCollapse) {
            currentScrollOffsetPx = 0f
            scrollBehavior.updateScrollOffset(0f)
            scrollBehavior.clearForceCollapse()
        }
    }

    LaunchedEffect(contentNeedsScrolling, canCollapse, scrollBehavior.forceCollapsed) {
        if (!contentNeedsScrolling && !scrollBehavior.forceCollapsed && canCollapse && currentScrollOffsetPx > 0f) {
            currentScrollOffsetPx = 0f
            scrollBehavior.updateScrollOffset(0f)
        }
    }

    val nestedScrollConnection = remember(
        maxHeightOffsetPx,
        contentNeedsScrolling,
        canCollapse,
        lazyListState,
        xmlContentAtTop,
        scrollBehavior.forceCollapsed
    ) {
        object : NestedScrollConnection {
            private var trackingScrollOffset = 0f

            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (!canCollapse || (!contentNeedsScrolling && !scrollBehavior.forceCollapsed)) {
                    return Offset.Zero
                }
                trackingScrollOffset = currentScrollOffsetPx
                val delta = available.y
                if (delta < 0) {
                    if (trackingScrollOffset < maxHeightOffsetPx) {
                        val scrollDelta = -delta
                        val newScrollPx = (trackingScrollOffset + scrollDelta).coerceIn(0f, maxHeightOffsetPx)
                        val consumed = newScrollPx - trackingScrollOffset
                        if (consumed > 0.5f) {
                            trackingScrollOffset = newScrollPx
                            currentScrollOffsetPx = newScrollPx
                            scrollBehavior.updateScrollOffset(newScrollPx)
                            return Offset(0f, -consumed)
                        }
                    }
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (!canCollapse || (!contentNeedsScrolling && !scrollBehavior.forceCollapsed)) {
                    return Offset.Zero
                }
                val delta = available.y
                if (delta > 0 && trackingScrollOffset > 0f) {
                    val isAtTop = if (lazyListState != null) {
                        val firstVisible = lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()
                        firstVisible?.index == 0 && firstVisible.offset >= 0
                    } else {
                        xmlContentAtTop ?: (delta > 5f)
                    }
                    if (isAtTop) {
                        if (scrollBehavior.forceCollapsed) {
                            scrollBehavior.clearForceCollapse()
                        }
                        val newScrollPx = (trackingScrollOffset - delta).coerceAtLeast(0f)
                        val consumedAmount = trackingScrollOffset - newScrollPx
                        if (consumedAmount > 0.5f) {
                            trackingScrollOffset = newScrollPx
                            currentScrollOffsetPx = newScrollPx
                            scrollBehavior.updateScrollOffset(newScrollPx)
                            return Offset(0f, consumedAmount)
                        }
                    }
                }
                return Offset.Zero
            }
        }
    }

    val isScrollingContentBelow by remember(scrollBehaviorKey) {
        derivedStateOf { motionProgress >= 1f }
    }

    // Scrim/blur ramp: start showing app-bar color overlay then blur as content scrolls away
    val scrimStart = 0.2f
    val scrimAlpha by remember(scrollBehaviorKey) {
        derivedStateOf {
            if (motionProgress <= scrimStart) 0f
            else ((motionProgress - scrimStart) / (1f - scrimStart)).coerceIn(0f, 1f)
        }
    }

    val currentTopBarHeight = with(density) {
        val expandedPx = maxScrollDistancePx
        val collapsedPx = collapsedHeightPx
        val currentPx = expandedPx - (motionProgress * (expandedPx - collapsedPx))
        currentPx.toDp()
    }

    val availableContentHeight = with(density) {
        val minStickyHeaderPx = 100.dp.toPx()
        val effectiveStickyHeaderPx = if (stickyHeaderHeightPxState.value > 0f) {
            stickyHeaderHeightPxState.value
        } else {
            minStickyHeaderPx
        }
        val adjustedPx = (viewportHeightPx - effectiveStickyHeaderPx).coerceAtLeast(0f)
        adjustedPx.toDp()
    }

    val expandedConstraints = remember(topBarExpandedHeight) {
        ConstraintSet {
            val topBar = createRefFor("topBar")
            val contentArea = createRefFor("content")
            constrain(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.value(topBarExpandedHeight)
            }
            constrain(contentArea) {
                top.linkTo(topBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        }
    }

    val collapsedConstraints = remember(topBarCollapsedHeight) {
        ConstraintSet {
            val topBar = createRefFor("topBar")
            val contentArea = createRefFor("content")
            constrain(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.value(topBarCollapsedHeight)
            }
            constrain(contentArea) {
                top.linkTo(topBar.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .onGloballyPositioned { coordinates ->
                val newScreenHeightPx = coordinates.size.height.toFloat()
                if (screenHeightPx != newScreenHeightPx) {
                    screenHeightPx = newScreenHeightPx
                }
            }
    ) {
        MotionLayout(
            start = expandedConstraints,
            end = collapsedConstraints,
            progress = if (canCollapse && (contentNeedsScrolling || scrollBehavior.forceCollapsed)) motionProgress else 0f,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .layoutId("topBar")
                    .fillMaxWidth()
                    .clipToBounds()
            ) {
                // Measure content at full expanded height (ignore parent's shrinking height) so
                // image/Rive never get squished; then place with offset so it scrolls away.
                CompositionLocalProvider(LocalTopBarMotionProgress provides motionProgress) {
                    Layout(
                        content = {
                            Box(Modifier.fillMaxSize()) {
                                topBarContent()
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { measurables, constraints ->
                        val expandedPx = maxScrollDistancePx.roundToInt().coerceAtLeast(1)
                        val relaxedConstraints = constraints.copy(maxHeight = expandedPx)
                        val placeable = measurables.first().measure(relaxedConstraints)
                        layout(constraints.maxWidth, constraints.maxHeight) {
                            placeable.place(0, -currentScrollOffsetPx.toInt())
                        }
                    }
                }
                // Gradual color scrim: app bar color fades in as content scrolls away
                if (scrimAlpha > 0f) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer { alpha = scrimAlpha }
                            .background(SurfaceColor.Primary)
                    )
                }
                // When fully collapsed (app bar height), show blur of content scrolling behind
                if (isScrollingContentBelow) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .blurModifier(
                                scrimState = scrimState,
                                blurRadius = 60.dp,
                                backgroundColor = SurfaceColor.Primary
                            )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .layoutId("content")
                    .fillMaxSize()
                    .then(
                        if ((contentNeedsScrolling || scrollBehavior.forceCollapsed) && canCollapse &&
                            (lazyListState != null || (xmlScrollPosition != null && lazyListState == null))
                        ) {
                            Modifier.nestedScroll(nestedScrollConnection)
                        } else Modifier
                    )
                    .then(
                        if (bottomFixedContentHeightPx > 0f) {
                            Modifier.padding(bottom = with(density) { bottomFixedContentHeightPx.toDp() })
                        } else Modifier
                    )
                    .then(
                        if (bottomFixedContent == null) {
                            Modifier.navigationBarsPadding()
                        } else Modifier
                    )
                    .scrimSource(state = scrimState, zIndex = 200f)
            ) {
                CompositionLocalProvider(
                    LocalPopScrimState provides scrimState,
                    LocalIsScrollingContentBelow provides isScrollingContentBelow,
                    LocalStickyHeaderScrimState provides stickyHeaderScrimState,
                    LocalContentAvailableHeight provides availableContentHeight,
                    LocalStickyHeaderHeightPx provides stickyHeaderHeightPxState
                ) {
                    Box(
                        modifier = Modifier.onGloballyPositioned { coordinates ->
                            val newHeight = coordinates.size.height.toFloat()
                            if (contentHeightPx != newHeight) {
                                contentHeightPx = newHeight
                            }
                        }
                    ) {
                        content()
                    }
                }
            }
        }

        bottomFixedContent?.let { bottomContent ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .onGloballyPositioned { coordinates ->
                        val newHeight = coordinates.size.height.toFloat()
                        if (bottomFixedContentHeightPx != newHeight) {
                            bottomFixedContentHeightPx = newHeight
                        }
                    }
            ) {
                bottomContent()
            }
        }
    }
}
