package com.pop.components.ds_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import com.pop.compose_components.R as ComposeR
import kotlin.math.max

/**
 * Reusable sticky header for LazyColumn that sticks at a specific screen position.
 * 
 * Uses LazyColumn's built-in stickyHeader with a spacer to position
 * the header at a specific distance from the screen top (e.g., below an app bar).
 * A drawable background is shown when content is scrolling behind the collapsed top bar.
 * 
 * @param stickyThreshold Distance from screen top where header should stick. 
 *                        If null, uses status bar + app bar height from LocalPopAppBarHeight
 * @param lazyColumnTopY The LazyColumn's top position in window (should be tracked by parent)
 * @param lazyListState Optional LazyListState to check if content needs scrolling.
 *                      If provided, header will only be sticky when content actually needs scrolling.
 * @param viewportHeightPx Optional viewport height in pixels. If provided with lazyListState,
 *                          used to determine if content needs scrolling.
 * @param bottomPaddingBufferPx Bottom padding buffer in pixels to subtract from viewport
 *                               when determining if content needs scrolling.
 * @param content The content to make sticky
 */
fun LazyListScope.PopStickyHeader(
    stickyThreshold: Dp? = null,
    lazyColumnTopY: Float,
    lazyListState: LazyListState? = null,
    viewportHeightPx: Float = 0f,
    bottomPaddingBufferPx: Float = 0f,
    content: @Composable () -> Unit
) {
    // Check if content needs scrolling at LazyListScope level (non-composable context)
    // Content needs scrolling if: total content height + 100dp bottom padding > viewport
    val contentNeedsScrolling = if (lazyListState != null) {
        val layoutInfo = lazyListState.layoutInfo
        
        // Quick check: If all items are visible, check if they fit in viewport with bottom padding
        val allItemsVisible = layoutInfo.visibleItemsInfo.isNotEmpty() &&
                layoutInfo.visibleItemsInfo.firstOrNull()?.index == 0 &&
                layoutInfo.visibleItemsInfo.lastOrNull()?.index == (layoutInfo.totalItemsCount - 1)
        
        if (allItemsVisible && viewportHeightPx > 0f && bottomPaddingBufferPx > 0f) {
            // All items are visible, check if they fit in viewport with bottom padding
            val visibleHeight = layoutInfo.visibleItemsInfo.sumOf { it.size }.toFloat()
            // Content with bottom padding: visible height + 100dp bottom padding
            val totalContentHeightWithPadding = visibleHeight + bottomPaddingBufferPx
            // Add a small threshold to account for measurement inaccuracies
            val threshold = 10f // 10px threshold
            // Content needs scrolling if: content height + bottom padding > viewport
            // Also check if user has scrolled (for mid-length content where calculation might be slightly off)
            val firstVisibleItem = layoutInfo.visibleItemsInfo.firstOrNull()
            val hasScrolled = firstVisibleItem != null && (
                firstVisibleItem.index > 0 || // Scrolled past first item
                firstVisibleItem.offset < 0   // First item is scrolled up
            )
            val needsScrolling = (totalContentHeightWithPadding > (viewportHeightPx + threshold) && viewportHeightPx > 0f) || hasScrolled
            needsScrolling
        } else if (!allItemsVisible && viewportHeightPx > 0f && bottomPaddingBufferPx > 0f && layoutInfo.totalItemsCount > 0) {
            // Not all items visible - calculate total content height
            val visibleHeight = layoutInfo.visibleItemsInfo.sumOf { it.size }.toFloat()
            
            // For non-visible items, estimate height based on visible items
            val nonVisibleCount = layoutInfo.totalItemsCount - layoutInfo.visibleItemsInfo.size
            val estimatedNonVisibleHeight = if (layoutInfo.visibleItemsInfo.isNotEmpty() && nonVisibleCount > 0) {
                val avgItemHeight = layoutInfo.visibleItemsInfo.map { it.size }.average().toFloat()
                nonVisibleCount * avgItemHeight
            } else {
                0f
            }
            
            val totalContentHeightPx = visibleHeight + estimatedNonVisibleHeight
            // Content with bottom padding: total content height + 100dp bottom padding
            val totalContentHeightWithPadding = totalContentHeightPx + bottomPaddingBufferPx
            val threshold = 10f
            // Content needs scrolling if: content height + bottom padding > viewport
            // Also check if user has scrolled (for mid-length content where calculation might be slightly off)
            val firstVisibleItem = layoutInfo.visibleItemsInfo.firstOrNull()
            val hasScrolled = firstVisibleItem != null && (
                firstVisibleItem.index > 0 || // Scrolled past first item
                firstVisibleItem.offset < 0   // First item is scrolled up
            )
            val needsScrolling = (totalContentHeightWithPadding > (viewportHeightPx + threshold) && viewportHeightPx > 0f) || hasScrolled
            needsScrolling
        } else {
            // Fallback: Check if list can actually scroll OR if user has scrolled
            // This handles mid-length content where viewport calculation might be inaccurate
            val firstVisibleItem = layoutInfo.visibleItemsInfo.firstOrNull()
            val hasScrolled = firstVisibleItem != null && (
                firstVisibleItem.index > 0 || // Scrolled past first item
                firstVisibleItem.offset < 0   // First item is scrolled up (negative offset means scrolled)
            )
            val canScroll = lazyListState.canScrollForward || lazyListState.canScrollBackward
            // Content needs scrolling if it can scroll OR if user has already scrolled
            // This ensures sticky header works when top bar is collapsed and content is scrolling
            val needsScrolling = canScroll || hasScrolled
            needsScrolling
        }
    } else {
        // If no lazyListState provided, always use sticky behavior (backward compatible)
        true
    }
    
    // Conditionally use stickyHeader or regular item based on scroll detection
    if (contentNeedsScrolling) {
        stickyHeader {
            PopStickyHeaderContent(
                stickyThreshold = stickyThreshold,
                lazyColumnTopY = lazyColumnTopY,
                content = content
            )
        }
    } else {
        // When content doesn't need scrolling, use regular item to prevent scrolling behind
        item {
            content()
        }
    }
}

/**
 * Internal composable that handles the sticky header positioning logic.
 * Shows a drawable background when content is scrolling behind the collapsed top bar.
 */
@Composable
private fun PopStickyHeaderContent(
    stickyThreshold: Dp?,
    lazyColumnTopY: Float,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current

    val isScrollingContentBelow = LocalIsScrollingContentBelow.current

    val currentTopBarHeight = LocalCurrentTopBarHeight.current
    val appBarHeight = LocalPopAppBarHeight.current
    val statusBarHeight = statusBarInsetHeight()
    val staticThreshold = stickyThreshold ?: (statusBarHeight + appBarHeight)
    val effectiveStickyThreshold = if (currentTopBarHeight > 0.dp) {
        maxOf(staticThreshold, currentTopBarHeight)
    } else {
        staticThreshold
    }

    // Use the animation-derived content area top when available (inside PopTopBarWithContent).
    // This is computed from the same motionProgress that drives the MotionLayout, so it changes
    // in perfect lockstep with currentTopBarHeight — no async lag, no jitter.
    // Fall back to lazyColumnTopY (onGloballyPositioned) only when outside PopTopBarWithContent.
    val contentAreaTopDp = LocalContentAreaTopDp.current
    val stickyHeaderTopSpacing = if (contentAreaTopDp >= 0.dp) {
        maxOf(effectiveStickyThreshold - contentAreaTopDp, 0.dp)
    } else {
        with(density) {
            val offsetPx = effectiveStickyThreshold.toPx() - lazyColumnTopY
            max(offsetPx, 0f).toDp()
        }
    }

    val stickyHeaderHeightPxState = LocalStickyHeaderHeightPx.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(100f)
    ) {
        if (stickyHeaderTopSpacing > 0.dp) {
            Spacer(modifier = Modifier.height(stickyHeaderTopSpacing))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    val newHeight = coordinates.size.height.toFloat()
                    if (stickyHeaderHeightPxState.value != newHeight) {
                        stickyHeaderHeightPxState.value = newHeight
                    }
                }
                .clickable(
                    onClick = { /* Intentionally empty - just blocks pass-through */ },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
        ) {
            // Background is always present so it never flickers in/out due to
            // timing differences in isScrollingContentBelow state updates.
            Image(
                painter = painterResource(ComposeR.drawable.top95_percent_block_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
            content()
        }

        // Divider shown when the header is stuck below the collapsed topbar,
        // matching the 0.5dp line that appears under the PopAppBar background.
        if (isScrollingContentBelow) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(Color(0xFF262626))
            )
        }
    }
}

