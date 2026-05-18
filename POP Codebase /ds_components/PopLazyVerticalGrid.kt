package com.pop.components.ds_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pop.components.theme.FlashGrid.MobileGrid.horizontalPadding
import kotlin.math.max

/**
 * State for sticky header overlay in [PopLazyVerticalGrid].
 * Tracks whether overlay should be visible and the header content to display.
 */
@Stable
class StickyHeaderGridState {
    internal var headerContent: (@Composable () -> Unit)? by mutableStateOf(null)
    internal var showOverlay by mutableStateOf(false)
    internal var headerHeightPx by mutableStateOf(0f)
}

/**
 * CompositionLocal for [StickyHeaderGridState] used by [PopStickyHeaderGrid].
 */
val LocalStickyHeaderGridState = compositionLocalOf<StickyHeaderGridState?> { null }

/**
 * CompositionLocal providing the horizontal padding applied to PopLazyVerticalGrid items.
 */
val LocalLazyGridHorizontalPadding = compositionLocalOf { 0.dp }

/**
 * A LazyVerticalGrid wrapper with optional sticky header support.
 *
 * ## Features:
 * - **Sticky header**: When [PopStickyHeaderGrid] is used via [raw], the header sticks at the top
 *   when content scrolls (overlay approach, since LazyGridScope has no native stickyHeader).
 * - **Same pattern as PopLazyColumn**: Use [raw] for direct LazyGridScope access to add
 *   [PopStickyHeaderGrid] and items.
 *
 * ## Architecture:
 * ```
 * PopLazyVerticalGrid (Box with overlay)
 * ├── LazyVerticalGrid
 * │   ├── raw.PopStickyHeaderGrid (full-width)
 * │   ├── raw.itemsIndexed(...)
 * │   └── raw.item(...)
 * └── Overlay (when header scrolled out)
 * ```
 *
 * ## Usage (reference: BillPayCategoryPageFragmentV2):
 * ```kotlin
 * var lazyGridTopY by remember { mutableFloatStateOf(0f) }
 * val topBarHeight = statusBarHeight + appBarHeight
 *
 * PopLazyVerticalGrid(
 *     modifier = Modifier.onGloballyPositioned { lazyGridTopY = it.positionInWindow().y },
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
 *     raw.itemsIndexed(products) { index, product -> ProductCard(...) }
 * }
 * ```
 *
 * @param modifier Modifier for the container
 * @param state LazyGridState for scroll control
 * @param columns Grid column configuration
 * @param contentPadding Padding around the entire content
 * @param reverseLayout Whether to reverse the layout direction
 * @param verticalArrangement Vertical arrangement of grid items
 * @param horizontalArrangement Horizontal arrangement of grid items
 * @param userScrollEnabled Whether user scrolling is enabled
 * @param content Content lambda using [PopLazyGridScope]
 */
@Composable
fun PopLazyVerticalGrid(
    modifier: Modifier = Modifier,
    state: androidx.compose.foundation.lazy.grid.LazyGridState = rememberLazyGridState(),
    columns: GridCells,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
    userScrollEnabled: Boolean = true,
    content: PopLazyGridScope.() -> Unit
) {
    val stickyState = remember { StickyHeaderGridState() }

    LaunchedEffect(state) {
        snapshotFlow {
            val layoutInfo = state.layoutInfo
            val firstVisible = layoutInfo.visibleItemsInfo.firstOrNull()
            when {
                firstVisible == null -> false
                firstVisible.index > 0 -> true
                else -> firstVisible.offset.y < -10
            }
        }.collect { showOverlay ->
            stickyState.showOverlay = showOverlay
        }
    }

    val layoutDirection = LocalLayoutDirection.current
    CompositionLocalProvider(
        LocalStickyHeaderGridState provides stickyState,
        LocalLazyGridHorizontalPadding provides contentPadding.calculateLeftPadding(layoutDirection)
    ) {
        Box(modifier = modifier) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                state = state,
                columns = columns,
                contentPadding = contentPadding,
                reverseLayout = reverseLayout,
                verticalArrangement = verticalArrangement,
                horizontalArrangement = horizontalArrangement,
                userScrollEnabled = userScrollEnabled
            ) {
                PopLazyGridScopeImpl(delegate = this).content()
            }

            if (stickyState.showOverlay && stickyState.headerContent != null) {
                PopStickyHeaderGridOverlay(
                    stickyState = stickyState,
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }
        }
    }
}

/**
 * Overlay composable for sticky header when content has scrolled.
 */
@Composable
private fun PopStickyHeaderGridOverlay(
    stickyState: StickyHeaderGridState,
    modifier: Modifier = Modifier
) {
    val isScrollingContentBelow = LocalIsScrollingContentBelow.current
    val currentTopBarHeight = LocalCurrentTopBarHeight.current
    val contentAreaTopDp = LocalContentAreaTopDp.current
    val appBarHeight = LocalPopAppBarHeight.current
    val statusBarHeight = statusBarInsetHeight()
    val staticThreshold = statusBarHeight + appBarHeight
    val effectiveStickyThreshold = if (currentTopBarHeight > 0.dp) {
        maxOf(staticThreshold, currentTopBarHeight)
    } else {
        staticThreshold
    }
    // Same formula as PopStickyHeaderContent: when inside PopTopBarWithContent, content area top
    // moves from maxScrollDistance (expanded) to 0 (collapsed). Spacer = threshold - contentAreaTop
    // so header sticks just below the top bar throughout the collapse animation.
    val overlayTopSpacer = if (contentAreaTopDp >= 0.dp) {
        maxOf(effectiveStickyThreshold - contentAreaTopDp, 0.dp)
    } else {
        effectiveStickyThreshold
    }

    val horizontalPadding = LocalLazyGridHorizontalPadding.current
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(overlayTopSpacer))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPadding)
                .onGloballyPositioned { coordinates ->
                    stickyState.headerHeightPx = coordinates.size.height.toFloat()
                }
        ) {
            if (isScrollingContentBelow) {
                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(com.pop.compose_components.R.drawable.top95_percent_block_background),
                    contentDescription = null,
                    contentScale = androidx.compose.ui.layout.ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize()
                )
            }
            stickyState.headerContent?.invoke()
        }
    }
}

/**
 * Custom scope for [PopLazyVerticalGrid] providing direct access to [LazyGridScope].
 *
 * Use [raw] to add [PopStickyHeaderGrid], items, itemsIndexed, etc.
 * Same pattern as [PopLazyListScope] in [PopLazyColumn].
 */
@Stable
interface PopLazyGridScope {
    /**
     * Direct access to the underlying [LazyGridScope].
     * Use for adding [PopStickyHeaderGrid], items, itemsIndexed, etc.
     */
    val raw: LazyGridScope
}

@Composable
private fun PopLazyGridContent(
    scope: PopLazyGridScope,
    content: PopLazyGridScope.() -> Unit
) {
    content(scope)
}

@Stable
private class PopLazyGridScopeImpl(
    private val delegate: LazyGridScope
) : PopLazyGridScope {
    override val raw: LazyGridScope = delegate
}
