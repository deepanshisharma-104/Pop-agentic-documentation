package com.pop.components.ds_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

// Animation constants
private const val BACKGROUND_TRANSITION_MIDPOINT = 0.5f
private const val TOP_CONTENT_FADE_THRESHOLD = 0.45f
private const val MIN_BACKGROUND_OPACITY = 0.1f
private const val TOP_CONTENT_SPEED_MULTIPLIER = 0.5f

// Infinite scroll constants
private const val INFINITE_SCROLL_START_PAGE = Int.MAX_VALUE / 2

// Auto-scroll constants
private const val AUTO_SCROLL_CHECK_INTERVAL_MS = 100L
private const val AUTO_SCROLL_ANIMATION_DELAY_MS = 500L

/**
 * Generic Hero Carousel component with complex opacity transitions.
 * 
 * Features:
 * - Background images that fade but don't shift position
 * - Top content that fades in/out faster than center content and moves at 0.5x speed
 * - Center content that slides and fades based on scroll position
 * - Bottom content that stays at full opacity and switches instantly with page changes
 * - Bidirectional swiping support
 *
 * ## Opacity Transition Timing
 * 
 * The component uses coordinated opacity transitions across layers:
 * 
 * - **Background**: Current background fades from 1.0 to [MIN_BACKGROUND_OPACITY] (0.1) at offset [BACKGROUND_TRANSITION_MIDPOINT] (0.5),
 *   then stays at minimum until new background fully takes over. Adjacent background appears at minimum opacity
 *   when current reaches minimum, ensuring no black background is ever visible.
 * 
 * - **Center Content**: Fades out completely by offset [BACKGROUND_TRANSITION_MIDPOINT] (0.5), matching when
 *   background reaches minimum opacity. New center content starts fading in only after background exceeds minimum.
 * 
 * - **Top Content**: Fades out/in faster than center content using [TOP_CONTENT_FADE_THRESHOLD] (0.45),
 *   creating a subtle parallax effect. Moves at 0.5x speed relative to center content.
 * 
 * - **Bottom Content**: Switches instantly with page changes (no fade transition).
 *
 * ## Infinite Scroll
 * 
 * When [infiniteScroll] is enabled, the carousel allows continuous scrolling in both directions:
 * - Swiping right past the last item wraps to the first item
 * - Swiping left before the first item wraps to the last item
 * 
 * The [onPageChanged] callback always reports the actual item index (0 to items.size - 1),
 * regardless of the internal virtual page number used for infinite scrolling.
 * 
 * Note: Infinite scroll requires at least 2 items to function properly. With a single item,
 * infinite scroll has no effect.
 *
 * ## Auto-Scroll
 * 
 * When [autoScroll] is enabled, the carousel automatically advances to the next page at regular
 * intervals specified by [autoScrollInterval]. Auto-scroll behavior:
 * 
 * - Automatically pauses when the user is actively swiping (detected via [PagerState.isScrollInProgress])
 * - Works seamlessly with infinite scroll - will continuously loop through items
 * - In normal mode, wraps to the first item after reaching the last item
 * - Resumes automatically after user interaction completes
 * 
 * Default interval is 3 seconds, but can be customized via [autoScrollInterval] parameter.
 *
 * ## Edge Cases
 * 
 * - **Empty List**: Component returns early and renders nothing if [items] is empty.
 * 
 * - **Invalid initialPage**: If [initialPage] is out of bounds (negative or >= items.size),
 *   it is automatically coerced to a valid range (0 to items.size - 1).
 * 
 * - **Dynamic List Changes**: If the items list size changes after initialization, the component
 *   handles it gracefully. However, if the current page becomes invalid (e.g., list shrinks),
 *   the page will be automatically adjusted to remain within bounds. Note: Dynamic list changes
 *   with infinite scroll enabled may cause the carousel to jump to maintain a valid position.
 *
 * ## Page Change Callbacks
 * 
 * [onPageChanged] is invoked whenever the current page changes, including:
 * - User-initiated swipes
 * - Programmatic page changes (if supported by PagerState)
 * 
 * The callback fires after the page change is complete, not during the transition.
 *
 * ## Accessibility
 * 
 * For accessibility support, follow these recommendations:
 * 
 * - **Background Images**: Always provide meaningful content descriptions in [backgroundImageContent].
 *   Use `contentDescription` parameter in `Image` composables to describe the visual content.
 *   Example: `contentDescription = "Product showcase: ${item.title}"`
 * 
 * - **Page Indicators**: Consider adding visual or semantic page indicators (e.g., dots showing current page)
 *   in your [bottomContent] or [topContent] implementation to help users understand their position
 *   in the carousel.
 * 
 * - **Content Descriptions**: Ensure all interactive elements in [centerContent] and [bottomContent]
 *   have appropriate content descriptions and semantic labels.
 * 
 * - **Screen Reader Support**: The carousel supports standard swipe gestures. Screen readers will
 *   announce page changes when [onPageChanged] is used to update accessible labels.
 *
 * @param items List of items to display in the carousel. Must not be empty.
 * @param modifier Modifier to be applied to the carousel container
 * @param backgroundImageContent Composable for background image (receives item, opacity). Should provide
 *        content descriptions for accessibility.
 * @param topContent Optional composable for top content (receives item, opacity for fade effect).
 *        If null, top content layer is not rendered.
 * @param centerContent Composable for center content (receives item, opacity for fade effect)
 * @param bottomContent Composable for bottom content (receives item, always at full opacity).
 *        Switches instantly with page changes (no fade transition).
 * @param initialPage Initial page index to display. Automatically coerced to valid range if out of bounds.
 * @param infiniteScroll If true, enables infinite scrolling where swiping past the last item wraps to the first,
 *        and swiping before the first item wraps to the last. Default is false.
 * @param autoScroll If true, enables automatic page advancement at regular intervals. Default is false.
 *        Auto-scroll pauses when the user is actively dragging and restarts the timer when user manually changes pages.
 *        Note: With a single item, auto-scroll will immediately loop back to the same item.
 * @param autoScrollInterval The time interval between automatic page changes. Default is 8 seconds.
 *        The timer restarts whenever the user manually changes the page or finishes dragging.
 * @param onPageChanged Callback invoked when the current page changes (after transition completes).
 *        Fires for both user-initiated and programmatic page changes. Reports the actual item index (0 to items.size - 1),
 *        even when infinite scroll is enabled.
 */
@Composable
fun <T> PopHeroCarousel(
    modifier: Modifier = Modifier,
    items: List<T>,
    backgroundImageContent: @Composable (item: T, opacity: Float) -> Unit,
    topContent: (@Composable (item: T, opacity: Float) -> Unit)? = null,
    centerContent: @Composable (item: T, opacity: Float) -> Unit,
    bottomContent: @Composable (item: T) -> Unit,
    initialPage: Int = 0,
    infiniteScroll: Boolean = false,
    autoScroll: Boolean = false,
    autoScrollInterval: Duration = 8.seconds,
    onPageChanged: ((Int) -> Unit)? = null
) {
    if (items.isEmpty()) return

    // Helper function to convert virtual page to actual item index
    val getItemIndex: (Int) -> Int = if (infiniteScroll) {
        { page -> page.mod(items.size) }
    } else {
        { page -> page }
    }

    // Calculate initial page for pager state
    val pagerInitialPage = if (infiniteScroll) {
        // Start at a multiple of items.size near the middle to allow scrolling in both directions
        // This ensures that (pagerInitialPage % items.size) == initialPage
        val validInitialPage = initialPage.coerceIn(0, items.size - 1)
        val basePage = (INFINITE_SCROLL_START_PAGE / items.size) * items.size
        basePage + validInitialPage
    } else {
        initialPage.coerceIn(0, items.size - 1)
    }

    val pagerState = rememberPagerState(
        initialPage = pagerInitialPage,
        pageCount = { if (infiniteScroll) Int.MAX_VALUE else items.size }
    )

    // Separate pager state for top content (slower animation)
    val topContentPagerState = rememberPagerState(
        initialPage = pagerInitialPage,
        pageCount = { if (infiniteScroll) Int.MAX_VALUE else items.size }
    )

    // Handle dynamic list size changes - ensure current page remains valid (only for non-infinite scroll)
    LaunchedEffect(items.size, infiniteScroll) {
        if (!infiniteScroll) {
            val currentPage = pagerState.currentPage
            if (items.isNotEmpty() && currentPage >= items.size) {
                // Current page is invalid, reset to last valid page
                val validPage = (items.size - 1).coerceAtLeast(0)
                pagerState.animateScrollToPage(validPage)
                topContentPagerState.animateScrollToPage(validPage)
            }
        }
    }

    // Sync top content pager page changes with main pager (but with slower visual offset)
    LaunchedEffect(pagerState, items.size, infiniteScroll) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { targetPage ->
                // For infinite scroll, always sync. For normal scroll, validate bounds.
                val shouldSync = if (infiniteScroll) {
                    topContentPagerState.currentPage != targetPage
                } else {
                    targetPage in 0 until items.size && topContentPagerState.currentPage != targetPage
                }
                
                if (shouldSync) {
                    // When main pager changes page, animate top content pager to same page
                    // Using animateScrollToPage prevents race conditions during rapid swiping
                    // The visual offset will be handled separately via graphicsLayer
                    topContentPagerState.animateScrollToPage(targetPage)
                }
            }
    }

    // Track user-initiated page changes to restart auto-scroll timer
    var autoScrollResetKey by remember { mutableIntStateOf(0) }
    var isAutoScrolling by remember { mutableStateOf(false) }

    // Track page changes and report actual item index
    LaunchedEffect(pagerState, infiniteScroll) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { page ->
                val actualItemIndex = getItemIndex(page)
                // Only invoke callback if item index is valid
                if (actualItemIndex in items.indices) {
                    onPageChanged?.invoke(actualItemIndex)
                }
            }
    }

    // Track when user finishes dragging to restart auto-scroll timer
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.isScrollInProgress }
            .distinctUntilChanged()
            .collect { isScrolling ->
                if (!isScrolling && !isAutoScrolling) {
                    // User finished dragging (and it wasn't due to auto-scroll), restart auto-scroll timer
                    autoScrollResetKey++
                }
            }
    }

    // Auto-scroll functionality
    LaunchedEffect(autoScroll, autoScrollInterval, infiniteScroll, items.size, autoScrollResetKey) {
        if (!autoScroll) return@LaunchedEffect
        
        while (true) {
            // Wait for the interval, checking periodically if user starts dragging
            val intervalMs = autoScrollInterval.inWholeMilliseconds
            val startTime = System.currentTimeMillis()
            
            // Wait in small increments to check if user starts dragging
            while (System.currentTimeMillis() - startTime < intervalMs) {
                if (pagerState.isScrollInProgress) {
                    // User started dragging, wait until they finish by restarting the effect
                    // The autoScrollResetKey will be updated when dragging stops, restarting this effect
                    return@LaunchedEffect
                }
                delay(AUTO_SCROLL_CHECK_INTERVAL_MS) // Check periodically to detect user interaction
            }
            
            // Only auto-scroll if user is not dragging
            if (!pagerState.isScrollInProgress) {
                isAutoScrolling = true
                val currentPage = pagerState.currentPage
                val nextPage = if (infiniteScroll) {
                    // For infinite scroll, just advance to next page (wraps automatically)
                    currentPage + 1
                } else {
                    // For normal scroll, advance to next page or wrap to first if at the end
                    val currentItemIndex = getItemIndex(currentPage)
                    val nextItemIndex = (currentItemIndex + 1) % items.size
                    nextItemIndex
                }
                
                // Animate to next page
                pagerState.animateScrollToPage(nextPage)
                
                // Reset flag after animation completes
                // Note: This delay approximates the animation duration. For more precise timing,
                // consider using the actual animation duration from PagerState if available.
                delay(AUTO_SCROLL_ANIMATION_DELAY_MS)
                isAutoScrolling = false
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Background layer - non-scrollable, handles opacity transitions
        BackgroundLayer(
            items = items,
            pagerState = pagerState,
            backgroundImageContent = backgroundImageContent,
            getItemIndex = getItemIndex
        )

        // Top content layer - non-swipeable HorizontalPager with slower animation
        topContent?.let {
            TopContentLayer(
                items = items,
                mainPagerState = pagerState,
                topContentPagerState = topContentPagerState,
                topContent = it,
                getItemIndex = getItemIndex
            )
        }

        // Center content layer - scrollable with HorizontalPager
        CenterContentLayer(
            items = items,
            pagerState = pagerState,
            centerContent = centerContent,
            getItemIndex = getItemIndex
        )

        // Bottom content layer - non-scrollable, switches based on current page
        BottomContentLayer(
            items = items,
            pagerState = pagerState,
            bottomContent = bottomContent,
            getItemIndex = getItemIndex
        )
    }
}

/**
 * Background layer that displays background images with opacity transitions.
 * Background images don't shift position but fade in/out based on scroll offset.
 */
@Composable
private fun <T> BackgroundLayer(
    items: List<T>,
    pagerState: PagerState,
    backgroundImageContent: @Composable (item: T, opacity: Float) -> Unit,
    getItemIndex: (Int) -> Int
) {
    val currentPage = pagerState.currentPage
    val pageOffset = pagerState.currentPageOffsetFraction
    val currentItemIndex = getItemIndex(currentPage)

    // Calculate opacity for current background
    val currentBgOpacity = calculateBackgroundOpacity(pageOffset, isCurrent = true)

    // Calculate opacity for next/previous background based on swipe direction
    val adjacentPage = if (pageOffset > 0) {
        // Swiping right (positive offset), show next page background
        currentPage + 1
    } else if (pageOffset < 0) {
        // Swiping left (negative offset), show previous page background
        currentPage - 1
    } else {
        // No swipe, no adjacent page
        -1
    }

    val adjacentItemIndex = if (adjacentPage >= 0) getItemIndex(adjacentPage) else -1
    val adjacentBgOpacity = if (adjacentItemIndex in items.indices) {
        // Function handles absolute value internally, works for both swipe directions
        calculateBackgroundOpacity(pageOffset, isCurrent = false)
    } else {
        0f
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Adjacent (next/previous) background - rendered behind current (lower z-index)
        // Always render when swiping to ensure no black background
        val adjacentOpacity = adjacentBgOpacity.coerceIn(0f, 1f)
        if (adjacentItemIndex in items.indices && adjacentOpacity > 0f) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Opacity is applied by the backgroundImageContent composable itself via the opacity parameter
                backgroundImageContent(items[adjacentItemIndex], adjacentOpacity)
            }
        }

        // Current background - rendered on top (higher z-index)
        val currentOpacity = currentBgOpacity.coerceIn(0f, 1f)
        if (currentItemIndex in items.indices && currentOpacity > 0f) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Opacity is applied by the backgroundImageContent composable itself via the opacity parameter
                backgroundImageContent(items[currentItemIndex], currentOpacity)
            }
        }
    }
}

/**
 * Center content layer using HorizontalPager with opacity transitions.
 * Content is clipped to bounds and fades out as it moves to the start (left edge).
 */
@Composable
private fun <T> CenterContentLayer(
    items: List<T>,
    pagerState: PagerState,
    centerContent: @Composable (item: T, opacity: Float) -> Unit,
    getItemIndex: (Int) -> Int
) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds(),
        // Prevent content from scrolling beyond viewport bounds
        contentPadding = PaddingValues(horizontal = 0.dp)
    ) { page ->
        val itemIndex = getItemIndex(page)
        if (itemIndex in items.indices) {
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            
            // Calculate opacity for center content
            // At BACKGROUND_TRANSITION_MIDPOINT (when both backgrounds are at MIN_BACKGROUND_OPACITY), both center contents should be at 0 opacity
            // New center content should start appearing only when new background opacity > MIN_BACKGROUND_OPACITY
            // From BACKGROUND_TRANSITION_MIDPOINT to 0, new center content fades from 0 to 1.0 (matching new background MIN_BACKGROUND_OPACITY to 1.0f)
            // Old center content stays at 0 opacity once it reaches BACKGROUND_TRANSITION_MIDPOINT offset
            val centerOpacity = calculateContentOpacity(pageOffset, BACKGROUND_TRANSITION_MIDPOINT)

            // Center content - clip to bounds to prevent going outside viewport
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds()
                    .graphicsLayer { alpha = centerOpacity }
            ) {
                centerContent(items[itemIndex], centerOpacity)
            }
        }
    }
}

/**
 * Top content layer using HorizontalPager with slower animation speed.
 * This pager is non-swipeable and synced with the main pager at 0.5x speed.
 * The slower movement is achieved by applying a visual offset transformation.
 */
@Composable
private fun <T> TopContentLayer(
    items: List<T>,
    mainPagerState: PagerState,
    topContentPagerState: PagerState,
    topContent: @Composable (item: T, opacity: Float) -> Unit,
    getItemIndex: (Int) -> Int
) {
    val windowInfo = LocalWindowInfo.current
    val screenWidthPx = remember(windowInfo.containerSize.width) {
        windowInfo.containerSize.width.toFloat()
    }
    
    // Calculate slower offset from main pager (0.5x speed) for visual transformation
    val mainPageOffset = mainPagerState.currentPageOffsetFraction
    val slowOffset = mainPageOffset * TOP_CONTENT_SPEED_MULTIPLIER
    
    HorizontalPager(
        state = topContentPagerState,
        modifier = Modifier
            .fillMaxSize()
            .clipToBounds()
            .graphicsLayer {
                // Apply visual offset transformation for slower sliding effect
                // This creates the illusion of slower movement by translating the entire pager
                translationX = -slowOffset * screenWidthPx
            },
        contentPadding = PaddingValues(horizontal = 0.dp),
        userScrollEnabled = false // Disable user scrolling - make it non-swipeable
    ) { page ->
        val itemIndex = getItemIndex(page)
        if (itemIndex in items.indices) {
            // Use main pager state for opacity calculation to stay in sync with center content
            val mainCurrentPage = mainPagerState.currentPage
            val mainPageOffset = mainPagerState.currentPageOffsetFraction
            val pageOffset = (mainCurrentPage - page) + mainPageOffset
            
            // Calculate opacity for top content - fades quicker than center content
            // Top content fades out/in faster: uses TOP_CONTENT_FADE_THRESHOLD instead of BACKGROUND_TRANSITION_MIDPOINT
            // This makes it fade slightly quicker than center content
            val topOpacity = calculateContentOpacity(pageOffset, TOP_CONTENT_FADE_THRESHOLD)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clipToBounds()
                    .graphicsLayer { 
                        alpha = topOpacity
                    },
                contentAlignment = Alignment.TopCenter
            ) {
                topContent(items[itemIndex], topOpacity)
            }
        }
    }
}

/**
 * Bottom content layer that displays content for the current page.
 * Content stays at full opacity and switches when page changes.
 */
@Composable
private fun <T> BottomContentLayer(
    items: List<T>,
    pagerState: PagerState,
    bottomContent: @Composable (item: T) -> Unit,
    getItemIndex: (Int) -> Int
) {
    val currentPage = pagerState.currentPage
    val currentItemIndex = getItemIndex(currentPage)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        if (currentItemIndex in items.indices) {
            // Bottom content always at full opacity
            bottomContent(items[currentItemIndex])
        }
    }
}

/**
 * Calculates content opacity based on page offset and fade threshold.
 * 
 * This function handles the fade in/out logic for center and top content layers.
 * Content fades out as it moves away from the current page, and fades in as it approaches.
 * 
 * @param pageOffset The offset of the page relative to the current page (can be negative for pages coming from right)
 * @param fadeThreshold The offset threshold at which content should be fully faded out (e.g., BACKGROUND_TRANSITION_MIDPOINT or TOP_CONTENT_FADE_THRESHOLD)
 * @return Opacity value between 0f and 1f
 */
private fun calculateContentOpacity(pageOffset: Float, fadeThreshold: Float): Float {
    val absOffset = abs(pageOffset)
    
    return when {
        pageOffset >= 0f -> {
            // Current/old page: fade out to 0 by fadeThreshold, then stay at 0
            if (absOffset <= fadeThreshold) {
                // Fade from 1f to 0f as offset goes from 0 to fadeThreshold
                lerp(1f, 0f, absOffset / fadeThreshold)
            } else {
                // Stay at 0f after fadeThreshold
                0f
            }
        }
        else -> {
            // New page coming from right (pageOffset is negative, absOffset decreases from 1 to 0)
            // Content should only start appearing when absOffset < fadeThreshold
            if (absOffset >= fadeThreshold) {
                // Stay at 0f until absOffset reaches fadeThreshold
                0f
            } else {
                // Start fading in when absOffset < fadeThreshold
                // Fade from 0f to 1f as absOffset goes from fadeThreshold to 0
                val progress = (fadeThreshold - absOffset) / fadeThreshold
                lerp(0f, 1f, progress)
            }
        }
    }
}

/**
 * Calculates background opacity based on scroll offset.
 * 
 * Background opacity behavior:
 * - Current background: 
 *   - Opacity 1f at offset 0
 *   - Reduces to MIN_BACKGROUND_OPACITY as center content opacity reaches 0 (around BACKGROUND_TRANSITION_MIDPOINT)
 *   - Stays at MIN_BACKGROUND_OPACITY until new background fully takes over, then fades to 0f
 * - Adjacent background (next/previous):
 *   - Starts showing at MIN_BACKGROUND_OPACITY opacity when current background reaches MIN_BACKGROUND_OPACITY (around BACKGROUND_TRANSITION_MIDPOINT)
 *   - Maintains MIN_BACKGROUND_OPACITY minimum to prevent black background
 *   - Increases to 1f as offset reaches 1.0
 * 
 * Key requirement: Background should never be black - when current reaches MIN_BACKGROUND_OPACITY, 
 * adjacent should already be at MIN_BACKGROUND_OPACITY, ensuring continuous coverage.
 * 
 * @param pageOffset Current page offset fraction (0f to 1f for right swipe, 0f to -1f for left swipe)
 * @param isCurrent Whether this is the current background (true) or adjacent background (false)
 * @return Opacity value between 0f and 1f
 */
private fun calculateBackgroundOpacity(pageOffset: Float, isCurrent: Boolean): Float {
    val absOffset = abs(pageOffset)

    return if (isCurrent) {
        // Current background: reduces from 1f to MIN_BACKGROUND_OPACITY, stays at MIN_BACKGROUND_OPACITY while new image increases, then fades to 0f
        when {
            absOffset <= BACKGROUND_TRANSITION_MIDPOINT -> {
                // Fade from 1f to MIN_BACKGROUND_OPACITY (0 -> BACKGROUND_TRANSITION_MIDPOINT)
                lerp(1f, MIN_BACKGROUND_OPACITY, absOffset / BACKGROUND_TRANSITION_MIDPOINT)
            }
            absOffset < 1f -> {
                // Stay at MIN_BACKGROUND_OPACITY while new background increases from MIN_BACKGROUND_OPACITY to 1f
                MIN_BACKGROUND_OPACITY
            }
            else -> {
                // Only fade to 0 when transition is complete and page changes
                0f
            }
        }
    } else {
        // Adjacent background: appears at MIN_BACKGROUND_OPACITY when current reaches MIN_BACKGROUND_OPACITY, then increases to 1f
        // Key: Both images should be at MIN_BACKGROUND_OPACITY at the same time (at BACKGROUND_TRANSITION_MIDPOINT), then new increases
        // Critical: Start showing immediately (at very small offset > 0) to ensure continuous coverage
        when {
            absOffset <= 0f -> {
                // Not swiping, don't show
                0f
            }
            absOffset <= BACKGROUND_TRANSITION_MIDPOINT -> {
                // Fade in from 0f to MIN_BACKGROUND_OPACITY as current fades from 1f to MIN_BACKGROUND_OPACITY
                // At BACKGROUND_TRANSITION_MIDPOINT, both are at MIN_BACKGROUND_OPACITY simultaneously
                // Start showing immediately when swiping starts (even at tiny offset) to prevent any gap
                val fadeInProgress = (absOffset / BACKGROUND_TRANSITION_MIDPOINT).coerceIn(0f, 1f)
                lerp(0f, MIN_BACKGROUND_OPACITY, fadeInProgress)
            }
            else -> {
                // Increase from MIN_BACKGROUND_OPACITY to 1f as transition completes (BACKGROUND_TRANSITION_MIDPOINT -> 1.0)
                // Current stays at MIN_BACKGROUND_OPACITY during this period, ensuring continuous coverage
                val progress = ((absOffset - BACKGROUND_TRANSITION_MIDPOINT) / BACKGROUND_TRANSITION_MIDPOINT).coerceIn(0f, 1f)
                lerp(MIN_BACKGROUND_OPACITY, 1f, progress)
            }
        }
    }.coerceIn(0f, 1f)
}

