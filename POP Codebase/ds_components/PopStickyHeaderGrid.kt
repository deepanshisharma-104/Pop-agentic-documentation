package com.pop.components.ds_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.layout.onGloballyPositioned
import com.pop.compose_components.R as ComposeR
import kotlin.math.max

/**
 * Reusable sticky header for LazyVerticalGrid that sticks at a specific screen position.
 *
 * Since LazyGridScope has no native stickyHeader (unlike LazyColumn), this uses an overlay
 * approach: the header is added as a full-width first item, and when it scrolls out of view,
 * an overlay is shown at the top. Requires [PopLazyVerticalGrid] as parent.
 *
 * Usage (same pattern as raw.PopStickyHeader in PopLazyColumn / BillPayCategoryPageFragmentV2):
 * ```kotlin
 * PopLazyVerticalGrid(
 *     columns = GridCells.Fixed(2),
 *     state = gridState,
 *     contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
 * ) {
 *     raw.PopStickyHeaderGrid(
 *         stickyThreshold = topBarHeight,
 *         lazyGridTopY = lazyGridTopY
 *     ) {
 *         SortAndFilterButtons(...)
 *     }
 *     itemsIndexed(products) { index, product -> ... }
 * }
 * ```
 *
 * @param stickyThreshold Distance from screen top where header should stick.
 *                        If null, uses status bar + app bar height from LocalPopAppBarHeight.
 * @param lazyGridTopY The LazyVerticalGrid's top position in window (track via onGloballyPositioned).
 * @param content The content to make sticky (shown as first grid item and in overlay when scrolled).
 */
fun LazyGridScope.PopStickyHeaderGrid(
    stickyThreshold: Dp? = null,
    lazyGridTopY: Float,
    content: @Composable () -> Unit
) {

    // Add full-width header as first grid item (spans all columns)
    item(
        span = { GridItemSpan(maxLineSpan) },
        content = {
            PopStickyHeaderGridItemContent(
                stickyThreshold = stickyThreshold,
                lazyGridTopY = lazyGridTopY,
                content = content
            )
        }
    )
}

/**
 * Internal composable for the header item content.
 * Reports height to parent for overlay sizing.
 */
@Composable
private fun PopStickyHeaderGridItemContent(
    stickyThreshold: Dp?,
    lazyGridTopY: Float,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val stickyState = LocalStickyHeaderGridState.current

    // Register header content for overlay (PopLazyVerticalGrid shows this when scrolled)
    SideEffect {
        stickyState?.headerContent = content
    }

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

    val contentAreaTopDp = LocalContentAreaTopDp.current
    val stickyHeaderTopSpacing = if (contentAreaTopDp >= 0.dp) {
        maxOf(effectiveStickyThreshold - contentAreaTopDp, 0.dp)
    } else {
        with(density) {
            val offsetPx = effectiveStickyThreshold.toPx() - lazyGridTopY
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
                    onClick = { },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
        ) {
            Image(
                painter = painterResource(ComposeR.drawable.top95_percent_block_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
            content()
        }
    }
}
