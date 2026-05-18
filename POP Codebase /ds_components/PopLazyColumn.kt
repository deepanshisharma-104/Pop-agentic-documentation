package com.pop.components.ds_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * CompositionLocal providing the horizontal padding applied to PopLazyColumn items.
 * This allows child composables (like PopStickyHeader) to be aware of the padding context.
 */
val LocalLazyColumnHorizontalPadding = compositionLocalOf { 0.dp }

/**
 * A LazyColumn wrapper with per-item horizontal padding.
 * 
 * ## Features:
 * - **Per-item horizontal padding**: Padding is applied to individual items, NOT the container.
 *   This allows sticky headers to naturally extend full-width.
 * - **Minimal recomposition**: Uses stable wrappers and efficient composition
 * 
 * ## Architecture:
 * ```
 * PopLazyColumn (NO horizontal padding on container)
 * ├── StickyHeader (full-width, no wrapper)
 * ├── Item (wrapped with padding)
 * ├── Item (wrapped with padding)
 * └── Item (wrapped with padding)
 * ```
 * 
 * @param modifier Modifier for the LazyColumn container
 * @param state LazyListState for scroll control
 * @param contentPadding Padding around the entire content (top/bottom margins, etc.)
 * @param horizontalPadding Horizontal padding applied to each item (NOT the container).
 *                          Sticky headers ignore this and extend full-width.
 * @param reverseLayout Whether to reverse the layout direction
 * @param verticalArrangement Vertical arrangement of items
 * @param horizontalAlignment Horizontal alignment of items
 * @param userScrollEnabled Whether user scrolling is enabled
 * @param content Content lambda using [PopLazyListScope]
 */
@Composable
fun PopLazyColumn(
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    horizontalPadding: Dp = 0.dp,
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical =
        if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    userScrollEnabled: Boolean = true,
    content: PopLazyListScope.() -> Unit
) {
    CompositionLocalProvider(LocalLazyColumnHorizontalPadding provides horizontalPadding) {
        LazyColumn(
            modifier = modifier,
            state = state,
            contentPadding = contentPadding,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            userScrollEnabled = userScrollEnabled
        ) {
            PopLazyListScopeImpl(
                delegate = this,
                horizontalPadding = horizontalPadding
            ).content()
        }
    }
}

/**
 * Custom scope for [PopLazyColumn] that provides auto-wrapping of items
 * with horizontal padding.
 * 
 * Sticky headers added via [raw].PopStickyHeader are NOT wrapped,
 * allowing them to extend full-width.
 */
@Stable
interface PopLazyListScope {
    fun item(
        key: Any? = null,
        contentType: Any? = null,
        content: @Composable LazyItemScope.() -> Unit
    )

    fun items(
        count: Int,
        key: ((index: Int) -> Any)? = null,
        contentType: (index: Int) -> Any? = { null },
        itemContent: @Composable LazyItemScope.(index: Int) -> Unit
    )

    /**
     * Direct access to the underlying [LazyListScope].
     * 
     * Use this for:
     * - Adding [PopStickyHeader]
     * - Adding items that should NOT be wrapped with horizontal padding
     */
    val raw: LazyListScope
}

/**
 * Internal implementation of [PopLazyListScope].
 * Marked as @Stable to minimize recomposition.
 */
@Stable
private class PopLazyListScopeImpl(
    private val delegate: LazyListScope,
    private val horizontalPadding: Dp
) : PopLazyListScope {
    
    override val raw: LazyListScope = delegate
    
    override fun item(
        key: Any?,
        contentType: Any?,
        content: @Composable LazyItemScope.() -> Unit
    ) {
        delegate.item(key = key, contentType = contentType) {
            ItemWrapper(horizontalPadding = horizontalPadding) {
                content()
            }
        }
    }
    
    override fun items(
        count: Int,
        key: ((index: Int) -> Any)?,
        contentType: (index: Int) -> Any?,
        itemContent: @Composable LazyItemScope.(index: Int) -> Unit
    ) {
        delegate.items(
            count = count,
            key = key,
            contentType = contentType
        ) { index ->
            ItemWrapper(horizontalPadding = horizontalPadding) {
                itemContent(index)
            }
        }
    }
}

@Composable
private fun ItemWrapper(
    horizontalPadding: Dp,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    ) {
        content()
    }
}
