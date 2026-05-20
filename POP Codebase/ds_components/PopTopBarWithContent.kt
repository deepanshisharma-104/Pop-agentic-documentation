package com.pop.components.ds_components

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.layoutId
import androidx.core.view.WindowCompat
import com.pop.components.compose_components.PopDivider
import com.pop.components.ds_components.scrim.ScrimState
import com.pop.compose_components.R as ComposeR
import com.pop.components.models.MerchantImageSize
import com.pop.components.models.PopTopBarConfig
import com.pop.components.models.StatusBarTheme
import com.pop.components.models.isValid
import com.pop.components.theme.PopColor
import com.pop.components.theme.SurfaceColor

/**
 * CompositionLocal for the ScrimState used by PopTopBar blur effect.
 * Can be accessed within PopTopBarWithContent's content to apply matching blur effect to sticky headers.
 */
val LocalPopScrimState = compositionLocalOf<ScrimState?> { null }

/**
 * CompositionLocal indicating whether content is scrolling behind the collapsed top bar.
 * When true, blur effects should be applied to sticky headers to match the top bar's blur.
 */
val LocalIsScrollingContentBelow = compositionLocalOf { false }

/**
 * CompositionLocal for a SEPARATE ScrimState used specifically for sticky header blur.
 * 
 * This is different from [LocalPopScrimState] because:
 * - PopTopBar blur uses the main scrimState (content is captured at the parent level)
 * - Sticky header blur needs a separate state where items are marked as scrimSource
 *   at the SIBLING level (not ancestor), avoiding circular draw dependencies.
 * 
 * Usage: LazyColumn items should be wrapped with `Modifier.scrimSource(LocalStickyHeaderScrimState.current)`
 * and PopStickyHeader will automatically use this state for blur.
 */
val LocalStickyHeaderScrimState = compositionLocalOf<ScrimState?> { null }

/**
 * CompositionLocal for reporting sticky header height (in px) to the top-level container.
 * This is used to size empty states to the viewport below the sticky header.
 */
val LocalStickyHeaderHeightPx = compositionLocalOf { mutableStateOf(0f) }

/**
 * CompositionLocal providing the available content height in Dp.
 * 
 * This is the visible viewport height after accounting for:
 * - Top bar (expanded or collapsed state)
 * - Bottom fixed content (if any)
 * - Navigation bars padding
 * 
 * Use this for properly sizing and centering empty states in LazyColumn items:
 * ```kotlin
 * val availableHeight = LocalContentAvailableHeight.current
 * item {
 *     Box(
 *         modifier = Modifier
 *             .fillMaxWidth()
 *             .height(availableHeight),
 *         contentAlignment = Alignment.Center
 *     ) {
 *         EmptyStateComponent(...)
 *     }
 * }
 * ```
 */
val LocalContentAvailableHeight = compositionLocalOf { 0.dp }

/**
 * CompositionLocal providing the current top bar height in Dp.
 *
 * This value animates from the expanded height down to the collapsed height
 * (statusBar + appBar) as the top bar collapses. It equals the top bar's
 * bottom edge in screen coordinates (since topBar.top is always 0).
 *
 * Used by [PopStickyHeader] to keep the header pinned just below the top bar
 * throughout the collapsing animation, preventing it from sliding behind the bar.
 */
val LocalCurrentTopBarHeight = compositionLocalOf { 0.dp }

/**
 * CompositionLocal providing the current top Y of the MotionLayout content area in Dp,
 * derived directly from [motionProgress].
 *
 * Unlike [lazyColumnTopY] (which comes from [onGloballyPositioned] and can lag the animation),
 * this value changes in lockstep with [LocalCurrentTopBarHeight] on every frame, giving
 * [PopStickyHeader] a jitter-free source for computing its top spacer.
 *
 * Default is -1.dp (sentinel = "not inside PopTopBarWithContent").
 */
val LocalContentAreaTopDp = compositionLocalOf { (-1).dp }

/**
 * Adds a self-sizing compensating spacer as the first item in a LazyColumn that lives inside
 * [PopTopBarWithContent].
 *
 * **Why this is needed:**
 * When the top bar finishes collapsing, [PopTopBarWithContent] switches its MotionLayout `end`
 * constraint so the content area extends to Y=0 (allowing items to scroll behind the opaque
 * collapsed bar). That switch is an instant layout jump of `collapsedHeight` upward. Without
 * compensation, users feel a sudden pull at the moment of collapse.
 *
 * **How it works:**
 * The spacer height is `max(currentTopBarHeight − contentAreaTopDp, 0)`.
 * - During the collapse animation `contentAreaTopDp == currentTopBarHeight`, so the spacer is
 *   **0** — no interference with the animation.
 * - The instant the constraint switches `contentAreaTopDp` drops to `0.dp`, making the spacer
 *   exactly `collapsedHeight` — absorbing the layout jump so the first visible item stays put.
 * - As the user scrolls afterwards the spacer scrolls off naturally, carrying items behind the
 *   topbar exactly as if the user scrolled them there.
 *
 * **Usage:** call this as the very first item in any LazyColumn inside [PopTopBarWithContent]
 * that does **not** already start with a [PopStickyHeader] (which provides the same mechanism
 * internally).
 *
 * ```kotlin
 * LazyColumn(state = listState) {
 *     topBarCollapseSpacer()
 *     item { MyContent() }
 * }
 * ```
 */
fun LazyListScope.topBarCollapseSpacer() {
    item {
        val currentTopBarHeight = LocalCurrentTopBarHeight.current
        val contentAreaTopDp = LocalContentAreaTopDp.current
        val spacing = if (contentAreaTopDp >= 0.dp) {
            maxOf(currentTopBarHeight - contentAreaTopDp, 0.dp)
        } else 0.dp
        // Keep a 1px anchor spacer even when computed spacing is 0 so index 0 remains
        // stable in LazyListState across the constraint switch. This avoids the visible
        // pull/jump when content item index 1 is currently the first visible row.
        val minAnchorSpacing = with(LocalDensity.current) { 1f.toDp() }
        val effectiveSpacing = maxOf(spacing, minAnchorSpacing)
        Spacer(modifier = Modifier.height(effectiveSpacing))
    }
}

/**
 * Grid variant of [topBarCollapseSpacer] for LazyVerticalGrid/LazyHorizontalGrid.
 * Keeps the same anchor-spacing behavior used by list content.
 */
fun LazyGridScope.topBarCollapseSpacer() {
    item(span = { GridItemSpan(maxLineSpan) }) {
        val currentTopBarHeight = LocalCurrentTopBarHeight.current
        val contentAreaTopDp = LocalContentAreaTopDp.current
        val spacing = if (contentAreaTopDp >= 0.dp) {
            maxOf(currentTopBarHeight - contentAreaTopDp, 0.dp)
        } else 0.dp
        val minAnchorSpacing = with(LocalDensity.current) { 1f.toDp() }
        val effectiveSpacing = maxOf(spacing, minAnchorSpacing)
        Spacer(modifier = Modifier.height(effectiveSpacing))
    }
}

/**
 * Wrapper composable that handles PopTopBar with scrollable content using MotionLayout.
 *
 * This composable uses Compose MotionLayout for smooth, physics-based collapsing animations.
 * The scroll position is observed and mapped to a progress value (0-1) that drives all animations.
 * 
 * Features:
 * - Smooth hardware-accelerated animations via MotionLayout
 * - Automatically calculates max scroll distance based on merchant image size
 * - Manages content padding (starts below top bar, scrolls behind when collapsed)
 * - Blur effect when collapsed
 * - Works with both Compose (LazyColumn) and XML (RecyclerView) content
 * 
 * Usage in Compose:
 * ```kotlin
 * PopTopBarWithContent(
 *     topBarConfig = PopTopBarConfig(...),
 *     merchantImageSize = MerchantImageSize.Large
 * ) {
 *     // Your content here
 * }
 * ```
 * 
 * Usage in XML:
 * Add a ComposeView in your XML layout:
 * ```xml
 * <androidx.compose.ui.platform.ComposeView
 *     android:id="@+id/composeView"
 *     android:layout_width="match_parent"
 *     android:layout_height="match_parent" />
 * ```
 * 
 * Then in your Fragment/Activity:
 * ```kotlin
 * binding.composeView.setContent {
 *     PopTopBarWithContent(
 *         topBarConfig = PopTopBarConfig(...),
 *         merchantImageSize = MerchantImageSize.Large
 *     ) {
 *         // Your content here - can also embed XML views using AndroidView
 *     }
 * }
 * ```
 * 
 * @param modifier Modifier for the root container
 * @param topBarConfig Configuration for the PopTopBar
 * @param merchantImageSize Size of the merchant image (used to calculate scroll distance)
 * @param maxScrollDistance Optional custom max scroll distance (overrides merchantImageSize calculation)
 * @param centerRightSlot Optional composable slot for title bar center-right position
 * @param scrollBehaviorKey Optional key to reset scroll behavior (e.g., current screen/destination). When this changes, scroll resets to expanded state.
 * @param lazyListState Optional: if content is LazyColumn, pass its state to accurately detect if scrolling is needed
 * @param lazyGridState Optional: if content is LazyVerticalGrid, pass its state (same behavior as lazyListState)
 * @param xmlContentScrollable Optional: if content is XML layout, pass scrollability detection result. When true, content can scroll; when false, content fits in viewport. When null, falls back to height-based calculation.
 * @param xmlContentAtTop Optional: if content is XML layout, pass whether content is currently at top. Used to determine if top bar should expand when scrolling up. When null, defaults to false (safe fallback).
 * @param xmlScrollPosition Optional: if content is XML layout, pass the current scroll position in pixels. Used to drive MotionLayout progress for smooth animations.
 * @param bottomFixedContent Optional fixed content at the bottom (e.g., sticky buttons). This content is excluded from the scrollable viewport calculation.
 * @param applyNavigationBarsPaddingToContent When true, applies navigation bar inset padding to the content container.
 * @param scrollController Optional controller for programmatic scroll control (e.g., collapse on search focus)
 * @param content The scrollable content composable
 *
 * Note: For app bar navigation icon click handling, use [ConfigureAppBar] composable in your screen.
 * The app bar is rendered at activity level, so navigation callbacks should be set via PopAppBarManager.
 */
@OptIn(ExperimentalMotionApi::class)
@Composable
fun PopTopBarWithContent(
    modifier: Modifier = Modifier,
    topBarConfig: PopTopBarConfig,
    maxScrollDistance: Dp? = null,
    centerRightSlot: (@Composable () -> Unit)? = null,
    scrollBehaviorKey: Any? = null,
    lazyListState: LazyListState? = null,
    lazyGridState: LazyGridState? = null,
    xmlContentScrollable: Boolean? = null,
    xmlContentAtTop: Boolean? = null,
    xmlScrollPosition: Int? = null, // NEW: XML scroll position for MotionLayout progress
    bottomFixedContent: (@Composable () -> Unit)? = null,
    applyNavigationBarsPaddingToContent: Boolean = true,
    scrollController: PopTopBarScrollController? = null,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    // Check if merchant image exists
    val hasMerchantImage = topBarConfig.merchantImage != null && topBarConfig.merchantImage.isValid()
    val hasBodyText = !topBarConfig.titleBarConfig?.bodyText.isNullOrBlank()
    val statusBarHeight = statusBarInsetHeight()
    val appBarHeight = LocalPopAppBarHeight.current
    
    // Measured height of PopTitleBar in the no-merch case.
    // Pre-initialized with an estimate from the config to avoid a visible flash on the first frame.
    var noMerchTitleBarHeightPx by remember(topBarConfig.titleBarConfig) {
        val paddingDp = topBarConfig.titleBarConfig?.paddingDp ?: 16
        val titleLineHeight = 28  // headingLarge approximate line height
        val bodyExtra = if (hasBodyText) {
            (topBarConfig.titleBarConfig?.titleBodyGapDp ?: 6) + 18  // labelXSmall line height
        } else 0
        mutableFloatStateOf(with(density) { ((paddingDp * 2) + titleLineHeight + bodyExtra).dp.toPx() })
    }

    // Calculate max scroll distance based on merchant image size or use provided value
    val calculatedMaxScrollDistance = remember(
        topBarConfig.merchantImageSize,
        maxScrollDistance, 
        topBarConfig.useAspectRatioBasedHeight,
        configuration.screenWidthDp,
        hasMerchantImage,
        hasBodyText,
        statusBarHeight,
        appBarHeight,
        noMerchTitleBarHeightPx,
        topBarConfig.titleBarConfig
    ) {
        if (!hasMerchantImage) {
            // No merch image: height = status bar + app bar + title bar,
            // minus the title bar's top padding so that padding overlaps the app bar
            // zone — keeping the title text flush against the app bar with no gap.
            val titleTopPaddingDp = (topBarConfig.titleBarConfig?.paddingDp ?: 0).dp
            statusBarHeight + appBarHeight + with(density) { noMerchTitleBarHeightPx.toDp() } - titleTopPaddingDp
        } else {
            maxScrollDistance ?: if (topBarConfig.useAspectRatioBasedHeight) {
                val screenWidthDp = configuration.screenWidthDp.dp
                val aspectRatio = when (topBarConfig.merchantImageSize) {
                    MerchantImageSize.Small -> 2.77f
                    MerchantImageSize.Medium -> 1.52f
                    MerchantImageSize.Large -> 1.23f
                    MerchantImageSize.XLarge -> 0.79f
                }
                screenWidthDp / aspectRatio
            } else {
                when (topBarConfig.merchantImageSize) {
                    MerchantImageSize.XLarge -> 500.dp
                    MerchantImageSize.Large -> 320.dp
                    MerchantImageSize.Medium -> 260.dp
                    MerchantImageSize.Small -> 200.dp
                }
            }
        }
    }
    
    val maxScrollDistancePx = with(density) { calculatedMaxScrollDistance.toPx() }
    val collapsedHeightPx = with(density) { (statusBarHeight + appBarHeight).toPx() }
    val maxHeightOffsetPx = maxScrollDistancePx - collapsedHeightPx

    // Create scroll behavior state for compatibility with PopTopBarScrollController
    val scrollBehavior = remember(scrollBehaviorKey) { 
        PopTopBarScrollBehaviorState() 
    }
    
    // Update max values in scroll behavior
    LaunchedEffect(maxScrollDistancePx, maxHeightOffsetPx) {
        scrollBehavior.updateMaxScrollDistance(maxScrollDistancePx)
        scrollBehavior.updateMaxHeightOffset(maxHeightOffsetPx)
    }

    // Connect scroll controller
    LaunchedEffect(scrollController, scrollBehavior) {
        scrollController?.scrollBehaviorState = scrollBehavior
    }

    // Track content measurements
    var screenHeightPx by remember { mutableFloatStateOf(0f) }
    var contentHeightPx by remember { mutableFloatStateOf(0f) }
    var bottomFixedContentHeightPx by remember { mutableFloatStateOf(0f) }
    val stickyHeaderHeightPxState = remember { mutableStateOf(0f) }

    // Track scroll offset in pixels (primary state for scroll tracking)
    // This is updated directly by the nestedScrollConnection
    var currentScrollOffsetPx by remember(scrollBehaviorKey) { mutableFloatStateOf(0f) }

    // Sync scroll offset from scrollBehavior to currentScrollOffsetPx when controller is used
    LaunchedEffect(scrollBehavior.scrollOffset) {
        if (scrollBehavior.scrollOffset != currentScrollOffsetPx) {
            currentScrollOffsetPx = scrollBehavior.scrollOffset
        }
    }

    // Keep collapsed state sticky when forceCollapsed is enabled
    LaunchedEffect(scrollBehavior.forceCollapsed, maxHeightOffsetPx, hasMerchantImage) {
        if (scrollBehavior.forceCollapsed && hasMerchantImage && maxHeightOffsetPx > 0f) {
            currentScrollOffsetPx = maxHeightOffsetPx
            scrollBehavior.updateScrollOffset(maxHeightOffsetPx)
        }
    }

    // Reset scroll position when scrollBehaviorKey changes (tab switch)
    // This ensures a clean state when switching between tabs
    LaunchedEffect(scrollBehaviorKey) {
        currentScrollOffsetPx = 0f
        scrollBehavior.updateScrollOffset(0f)
        scrollBehavior.clearForceCollapse()
    }

    // MotionLayout progress derived from scroll offset (0 = expanded, 1 = collapsed)
    // Key by scrollBehaviorKey to ensure fresh state when switching tabs
    // Note: derivedStateOf automatically tracks currentScrollOffsetPx changes
    val motionProgress by remember(scrollBehaviorKey, maxHeightOffsetPx) {
        derivedStateOf {
            if (maxHeightOffsetPx > 0f) {
                (currentScrollOffsetPx / maxHeightOffsetPx).coerceIn(0f, 1f)
            } else 0f
        }
    }

    // Calculate viewport height for content measurement and centering
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
            } else if (applyNavigationBarsPaddingToContent) {
                systemInsets.getBottom(this).toFloat()
            } else {
                0f
            }
            val fixedStatusBarPx = 44.dp.toPx()
            val expandedTopBarHeightPx = (maxScrollDistancePx - fixedStatusBarPx + topInsetPx).coerceAtLeast(0f)
            (resolvedScreenHeightPx - expandedTopBarHeightPx - bottomInsetPx).coerceAtLeast(0f)
        }
    }

    // Cached scrollability state with hysteresis to prevent flickering
    // Key by scrollBehaviorKey to reset when switching tabs/screens
    var cachedContentNeedsScrolling by remember(scrollBehaviorKey) { mutableStateOf(false) }
    
    // Calculate if content needs scrolling - uses hysteresis to prevent flickering
    // Recalculates dynamically but with different thresholds for entering/exiting scrollable state
    val contentNeedsScrolling by remember(scrollBehaviorKey, lazyListState, lazyGridState, xmlContentScrollable, contentHeightPx, viewportHeightPx) {
        derivedStateOf {
            if (lazyListState != null) {
                val info = lazyListState.layoutInfo
                val first = info.visibleItemsInfo.firstOrNull()
                val last = info.visibleItemsInfo.lastOrNull()
                
                val needsScroll = if (first == null || last == null) {
                    false
                } else {
                    val allVisible = first.index == 0 && last.index == info.totalItemsCount - 1
                    if (!allVisible) {
                        // Not all items visible = definitely needs scrolling
                        true
                    } else {
                        // All items visible - check if content exceeds viewport with hysteresis
                        val totalContentSize = info.visibleItemsInfo.sumOf { it.size }.toFloat()
                        
                        // Use hysteresis: different thresholds for becoming scrollable vs non-scrollable
                        // This prevents flickering when content size is near the threshold
                        if (cachedContentNeedsScrolling) {
                            // Currently scrollable - need significant reduction to become non-scrollable
                            totalContentSize > viewportHeightPx - 50
                        } else {
                            // Currently not scrollable - need to exceed threshold to become scrollable
                            totalContentSize > viewportHeightPx + 50
                        }
                    }
                }
                
                cachedContentNeedsScrolling = needsScroll
                needsScroll
            } else if (lazyGridState != null) {
                val info = lazyGridState.layoutInfo
                val first = info.visibleItemsInfo.firstOrNull()
                val last = info.visibleItemsInfo.lastOrNull()
                
                val needsScroll = if (first == null || last == null) {
                    false
                } else {
                    val allVisible = first.index == 0 && last.index == info.totalItemsCount - 1
                    if (!allVisible) {
                        true
                    } else {
                        val totalContentSize = info.visibleItemsInfo.sumOf { it.size.height }.toFloat()
                        if (cachedContentNeedsScrolling) {
                            totalContentSize > viewportHeightPx - 50
                        } else {
                            totalContentSize > viewportHeightPx + 50
                        }
                    }
                }
                cachedContentNeedsScrolling = needsScroll
                needsScroll
            } else {
                // XML content: use threshold with hysteresis
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

    // Note: For LazyListState, scroll is handled via nestedScrollConnection
    // which intercepts scroll events to collapse/expand top bar first

    // Track whether nested scroll is active
    // Nested scroll is active when: (lazyListState != null || (xmlScrollPosition != null && lazyListState == null))
    // AND contentNeedsScrolling && hasMerchantImage
    val isNestedScrollActive = remember(contentNeedsScrolling, hasMerchantImage, lazyListState, lazyGridState, xmlScrollPosition) {
        (contentNeedsScrolling || scrollBehavior.forceCollapsed) && hasMerchantImage && 
        (lazyListState != null || lazyGridState != null || (xmlScrollPosition != null && lazyListState == null && lazyGridState == null))
    }

    // Update scroll offset from XML scroll position
    // Note: When nested scroll is active, the nested scroll connection handles collapse/expand automatically,
    // so we should NOT update from xmlScrollPosition to avoid conflicts and jerky behavior.
    // xmlScrollPosition updates are only needed for pure XML/RecyclerView content where nested scroll can't be used.
    LaunchedEffect(xmlScrollPosition, maxHeightOffsetPx, scrollBehavior.forceCollapsed, isNestedScrollActive) {
        // Only update from xmlScrollPosition if:
        // 1. xmlScrollPosition is provided
        // 2. There's a merchant image
        // 3. Not force collapsed
        // 4. Nested scroll is NOT active (for pure XML/RecyclerView mode)
        //    When nested scroll IS active (LazyGrid), the nested scroll connection handles everything
        if (xmlScrollPosition != null && hasMerchantImage && !scrollBehavior.forceCollapsed && !isNestedScrollActive) {
            // Only update for pure XML/RecyclerView mode (no nested scroll)
            // For LazyGrid with nested scroll, the nested scroll connection handles updates
            val newOffset = xmlScrollPosition.toFloat().coerceIn(0f, maxHeightOffsetPx)
            currentScrollOffsetPx = newOffset
            scrollBehavior.updateScrollOffset(newOffset)
        }
    }

    // Reset scroll offset when no merchant image (completely disable collapse)
    LaunchedEffect(hasMerchantImage) {
        if (!hasMerchantImage) {
            currentScrollOffsetPx = 0f
            scrollBehavior.updateScrollOffset(0f)
            scrollBehavior.clearForceCollapse()
        }
    }

    // Reset scroll offset when content becomes non-scrollable
    // This ensures merchant image renders correctly when content shrinks (e.g., accordions collapse)
    LaunchedEffect(contentNeedsScrolling, hasMerchantImage, scrollBehavior.forceCollapsed) {
        if (!contentNeedsScrolling && !scrollBehavior.forceCollapsed && hasMerchantImage && currentScrollOffsetPx > 0f) {
            currentScrollOffsetPx = 0f
            scrollBehavior.updateScrollOffset(0f)
        }
    }

    // Nested scroll connection for ALL scrollable content
    // This intercepts scroll to collapse/expand top bar BEFORE content scrolls
    // Uses a local tracking variable to avoid state read/write issues during scroll
    val nestedScrollConnection = remember(maxHeightOffsetPx, contentNeedsScrolling, hasMerchantImage, lazyListState, lazyGridState, xmlContentAtTop, scrollBehavior.forceCollapsed) {
        object : NestedScrollConnection {
            // Local tracking to avoid reading state during scroll
            private var trackingScrollOffset = 0f
            
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Only enable scroll behavior when:
                // 1. There's a merchant image AND
                // 2. Either content needs scrolling OR we're in force-collapsed mode
                if (!hasMerchantImage || (!contentNeedsScrolling && !scrollBehavior.forceCollapsed)) {
                    return Offset.Zero
                }

                // Sync with state at start of gesture
                trackingScrollOffset = currentScrollOffsetPx

                val delta = available.y
                if (delta < 0) {
                    // Scrolling DOWN - collapse top bar FIRST before content scrolls
                    if (trackingScrollOffset < maxHeightOffsetPx) {
                        val scrollDelta = -delta // Convert to positive (scroll amount)
                        val newScrollPx = (trackingScrollOffset + scrollDelta).coerceIn(0f, maxHeightOffsetPx)
                        val consumed = newScrollPx - trackingScrollOffset
                        
                        if (consumed > 0.5f) { // Minimum threshold to avoid micro-updates
                            val wasNotFullyCollapsed = trackingScrollOffset < maxHeightOffsetPx
                            val reachedFullyCollapsed = newScrollPx >= maxHeightOffsetPx
                            trackingScrollOffset = newScrollPx
                            currentScrollOffsetPx = newScrollPx
                            scrollBehavior.updateScrollOffset(newScrollPx)
                            // Important boundary handling:
                            // if this event is the one that reaches full collapse, consume the
                            // entire available delta so the list does not receive leftover pixels
                            // in the same frame (that leftover was causing the pull/jump).
                            return if (wasNotFullyCollapsed && reachedFullyCollapsed) {
                                Offset(0f, delta)
                            } else {
                                Offset(0f, -consumed)
                            }
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
                if (!hasMerchantImage || (!contentNeedsScrolling && !scrollBehavior.forceCollapsed)) {
                    return Offset.Zero
                }

                val delta = available.y
                if (delta > 0 && trackingScrollOffset > 0f) {
                    // Scrolling UP - expand top bar if content is at top (has leftover scroll)
                    val isAtTop = when {
                        lazyListState != null -> {
                            val firstVisible = lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()
                            firstVisible?.index == 0 && firstVisible.offset >= 0
                        }
                        lazyGridState != null -> {
                            val firstVisible = lazyGridState.layoutInfo.visibleItemsInfo.firstOrNull()
                            firstVisible?.index == 0 && firstVisible.offset.y >= 0
                        }
                        else -> xmlContentAtTop ?: (delta > 5f)
                    }
                    
                    if (isAtTop) {
                        if (scrollBehavior.forceCollapsed) {
                            scrollBehavior.clearForceCollapse()
                        }
                        val newScrollPx = (trackingScrollOffset - delta).coerceAtLeast(0f)
                        val consumedAmount = trackingScrollOffset - newScrollPx
                        
                        if (consumedAmount > 0.5f) { // Minimum threshold
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

    // Handle status bar appearance
    LaunchedEffect(topBarConfig.statusBarTheme) {
        val window = (context as? Activity)?.window ?: return@LaunchedEffect
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        when (topBarConfig.statusBarTheme) {
            StatusBarTheme.Light -> {
                window.statusBarColor = Color.Transparent.hashCode()
                windowInsetsController.isAppearanceLightStatusBars = true
            }
            StatusBarTheme.Dark -> {
                window.statusBarColor = Color.Transparent.hashCode()
                windowInsetsController.isAppearanceLightStatusBars = false
            }
            StatusBarTheme.Transparent -> {
                window.statusBarColor = Color.Transparent.hashCode()
            }
        }
    }

    // Calculate derived animation values from progress
    val config = scrollBehavior.config
    
    // Title bar alpha (fades between titleBarHideStart and blurStart)
    // Title bar alpha (fades between titleBarHideStart and blurStart)
    // Key by scrollBehaviorKey to ensure proper reset on tab switch
    // Note: derivedStateOf automatically tracks motionProgress changes
    val titleBarAlpha by remember(scrollBehaviorKey) {
        derivedStateOf {
            when {
                motionProgress < config.titleBarHideStart -> 1f
                motionProgress >= config.blurStart -> 0f
                else -> {
                    val fadeRange = config.blurStart - config.titleBarHideStart
                    if (fadeRange > 0f) {
                        1f - ((motionProgress - config.titleBarHideStart) / fadeRange).coerceIn(0f, 1f)
                    } else 0f
                }
            }
        }
    }

    // Merchant image alpha (fades after merchantImageHideStart)
    // Key by scrollBehaviorKey to ensure proper reset on tab switch
    // Note: derivedStateOf automatically tracks motionProgress changes
    val merchantImageAlpha by remember(scrollBehaviorKey) {
        derivedStateOf {
            if (motionProgress < config.merchantImageHideStart) 1f
            else {
                val fadeProgress = ((motionProgress - config.merchantImageHideStart) / (1f - config.merchantImageHideStart)).coerceIn(0f, 1f)
                // Squared ease-in: barely visible change at first, accelerates toward the end —
                // mirrors how the app bar scrim builds up (scrimProgress * scrimProgress).
                1f - (fadeProgress * fadeProgress)
            }
        }
    }

    // Blur intensity (starts at blurStart)
    // Key by scrollBehaviorKey to ensure proper reset on tab switch
    // Note: derivedStateOf automatically tracks motionProgress changes
    val blurIntensity by remember(scrollBehaviorKey) {
        derivedStateOf {
            if (motionProgress >= config.blurStart) {
                ((motionProgress - config.blurStart) / (1f - config.blurStart)).coerceIn(0f, 1f)
            } else 0f
        }
    }

    // Whether content is scrolling behind collapsed top bar
    // Key by scrollBehaviorKey to ensure proper reset on tab switch
    // Note: derivedStateOf automatically tracks motionProgress changes
    val isScrollingContentBelow by remember(scrollBehaviorKey) {
        derivedStateOf { motionProgress >= 1f }
    }

    // Current top bar height based on progress
    val currentTopBarHeight = with(density) {
        val expandedPx = maxScrollDistancePx
        val collapsedPx = collapsedHeightPx
        val currentPx = expandedPx - (motionProgress * (expandedPx - collapsedPx))
        currentPx.toDp()
    }

    // Content area's top Y exposed to content via LocalContentAreaTopDp.
    // During animation the content area is pinned to topBar.bottom (= currentTopBarHeight).
    // Once fully collapsed and scrolling begins, it moves to Y=0 so items can scroll behind
    // the opaque collapsed bar.
    val contentAreaTopDp = if (isScrollingContentBelow) 0.dp else currentTopBarHeight

    // Calculate available content height (for proper centering of empty states)
    // This is the visible viewport height when top bar is in EXPANDED state
    val availableContentHeight = with(density) {
        // Use a minimum sticky header height of 100dp if not yet measured
        // This prevents empty states from being too large initially
        val minStickyHeaderPx = 100.dp.toPx()
        val effectiveStickyHeaderPx = if (stickyHeaderHeightPxState.value > 0f) {
            stickyHeaderHeightPxState.value
    } else {
            minStickyHeaderPx
        }
        val adjustedPx = (viewportHeightPx - effectiveStickyHeaderPx).coerceAtLeast(0f)
        adjustedPx.toDp()
    }

    // Create MotionLayout constraint sets
    val expandedConstraints = remember(calculatedMaxScrollDistance) {
        ConstraintSet {
            val topBar = createRefFor("topBar")
            val contentArea = createRefFor("content")

            constrain(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.value(calculatedMaxScrollDistance)
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

    // Used during the collapse animation: content stays pinned to topBar.bottom at all
    // times so it physically tracks the topbar and never enters the behind-topbar region.
    val collapsedConstraints = remember(density, statusBarHeight) {
        ConstraintSet {
            val topBar = createRefFor("topBar")
            val contentArea = createRefFor("content")
            val collapsedHeight = with(density) { statusBarHeight + appBarHeight }

            constrain(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.value(collapsedHeight)
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

    // Switched in once the topbar is fully collapsed (isScrollingContentBelow = true).
    // Content area moves to Y=0 so the LazyColumn's viewport covers the full screen and
    // items can scroll naturally behind the opaque collapsed bar.  The swap is visually
    // seamless because the topbar scrim is fully opaque at that moment.
    val collapsedConstraintsScrolling = remember(density, statusBarHeight) {
        ConstraintSet {
            val topBar = createRefFor("topBar")
            val contentArea = createRefFor("content")
            val collapsedHeight = with(density) { statusBarHeight + appBarHeight }

            constrain(topBar) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.value(collapsedHeight)
            }

            constrain(contentArea) {
                top.linkTo(parent.top)
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
            .background(SurfaceColor.Primary)
                .onGloballyPositioned { coordinates ->
                    val newScreenHeightPx = coordinates.size.height.toFloat()
                    if (screenHeightPx != newScreenHeightPx) {
                        screenHeightPx = newScreenHeightPx
                    }
                }
    ) {
        MotionLayout(
            start = expandedConstraints,
            // While collapsing: content is pinned to topBar.bottom so it physically tracks
            // the topbar.  Once fully collapsed (isScrollingContentBelow), switch to the
            // scrolling variant so the content area covers the full screen and items can
            // scroll behind the opaque bar.
            end = if (isScrollingContentBelow) collapsedConstraintsScrolling else collapsedConstraints,
            progress = if (hasMerchantImage && (contentNeedsScrolling || scrollBehavior.forceCollapsed)) motionProgress else 0f,
            modifier = Modifier.fillMaxSize()
        ) {
            // Top bar area - draws above content via zIndex so blur overlay is visible
            Box(
                modifier = Modifier
                    .layoutId("topBar")
                    .fillMaxWidth()
                    .zIndex(1f)
            ) {
                // Merchant image background
                if (hasMerchantImage && !isScrollingContentBelow) {
                    topBarConfig.merchantImage?.let { visualElement ->
                        PopVisualElement(
                            visualElement = visualElement,
                            modifier = Modifier
                                .fillMaxSize()
                                .alpha(merchantImageAlpha)
                                .then(
                                    if (topBarConfig.onMerchantImageClick != null) {
                                        Modifier.clickable(onClick = topBarConfig.onMerchantImageClick)
                                    } else Modifier
                                ),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Merchant background"
                        )
                    }
                }

                // Early gradient overlay — builds up from the very start of the collapse so the
                // image darkens gradually before the scrim kicks in.  Uses a squared progress curve
                // (slow-start → accelerates) that mirrors how PopAppBarManager builds its scrim.
                if (hasMerchantImage && motionProgress > 0.02f && !isScrollingContentBelow) {
                    val earlyProgress = (motionProgress / config.blurStart.coerceAtLeast(0.01f)).coerceIn(0f, 1f)
                    val overlayAlpha = earlyProgress * earlyProgress * 0.55f
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = overlayAlpha * 0.55f),
                                        Color.Black.copy(alpha = overlayAlpha)
                                    )
                                )
                            )
                    )
                }

                // Scrim overlay when collapsed — fades in with a squared alpha curve so it builds
                // up gradually, matching PopAppBarManager's slow-start scrim behaviour.
                if ((blurIntensity > 0f || isScrollingContentBelow) && hasMerchantImage) {
                    Image(
                        painter = painterResource(ComposeR.drawable.top95_percent_block_background),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .alpha(if (isScrollingContentBelow) 1f else blurIntensity * blurIntensity)
                    )
                }

                // Title bar at bottom of top bar area
                if (hasMerchantImage && titleBarAlpha > 0f && !isScrollingContentBelow) {
                    topBarConfig.titleBarConfig?.let { titleConfig ->
                        PopTitleBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .alpha(titleBarAlpha),
                            config = titleConfig,
                            rightSlot = centerRightSlot
                        )
                    }
                } else if (!hasMerchantImage) {
                    // Background: fills the topBar area (sized to status bar + app bar + title bar).
                    Image(
                        painter = painterResource(ComposeR.drawable.top95_percent_block_background),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.matchParentSize()
                    )
                    // Title pinned to the bottom of the topBar. onSizeChanged measures the actual
                    // rendered height so calculatedMaxScrollDistance stays exact.
                    topBarConfig.titleBarConfig?.let { titleConfig ->
                        PopTitleBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .onSizeChanged { size ->
                                    val h = size.height.toFloat()
                                    if (noMerchTitleBarHeightPx != h) noMerchTitleBarHeightPx = h
                                },
                            config = titleConfig,
                            rightSlot = centerRightSlot
                        )
                    }
                }

                // Divider at bottom
                if (topBarConfig.showDivider) {
                    PopDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        color = PopColor.Grey.Grey900
                    )
                }
            }

            // Content area
            Box(
                modifier = Modifier
                    .layoutId("content")
                    .fillMaxSize()
                    .then(
                        // Apply nested scroll for Compose LazyList content OR when xmlScrollPosition is provided (for LazyGrid)
                        // For XML content (xmlScrollPosition != null), the RecyclerView scrolls naturally
                        // and we track its position via xmlScrollPosition - no nestedScrollConnection needed
                        // For LazyGrid (xmlScrollPosition provided but lazyListState is null), we need nested scroll
                        if ((contentNeedsScrolling || scrollBehavior.forceCollapsed) && hasMerchantImage && (lazyListState != null || lazyGridState != null || (xmlScrollPosition != null && lazyListState == null && lazyGridState == null))) {
                            Modifier.nestedScroll(nestedScrollConnection)
                        } else Modifier
                    )
                    .then(
                        if (bottomFixedContentHeightPx > 0f) {
                            Modifier.padding(bottom = with(density) { bottomFixedContentHeightPx.toDp() })
                        } else Modifier
                    )
                    .then(
                        if (bottomFixedContent == null && applyNavigationBarsPaddingToContent) {
                            Modifier.navigationBarsPadding()
                        } else Modifier
                    )
            ) {
                CompositionLocalProvider(
                    LocalIsScrollingContentBelow provides isScrollingContentBelow,
                    LocalContentAvailableHeight provides availableContentHeight,
                    LocalStickyHeaderHeightPx provides stickyHeaderHeightPxState,
                    LocalCurrentTopBarHeight provides currentTopBarHeight,
                    LocalContentAreaTopDp provides contentAreaTopDp
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

        // Bottom fixed content overlay
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
