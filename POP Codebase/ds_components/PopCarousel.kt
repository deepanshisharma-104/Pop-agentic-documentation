package com.pop.components.ds_components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pop.components.theme.BorderColor
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextColor
import com.pop.components.utils.horizontalGradientFade
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

// Constants for carousel styling
private const val PAGINATION_SHADOW_ELEVATION_DP = 9f
private const val PAGINATION_SHADOW_ALPHA = 0.42f
private const val PROGRESS_MIN_WIDTH_FRACTION = 0.01f

/**
 * PopCarouselItem - A composable wrapper for carousel items
 *
 * Provides a consistent container for carousel items with optional styling.
 * Can wrap any composable content.
 *
 * @param modifier Modifier for the item container
 * @param onClick Optional click handler for the item
 * @param content Composable content for the item
 *
 * @sample
 * ```
 * PopCarouselItem(
 *     onClick = { /* handle click */ }
 * ) {
 *     Text("Carousel Item")
 * }
 * ```
 */
@Composable
fun PopCarouselItem(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .then(
                if (onClick != null) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            )
    ) {
        content()
    }
}

/**
 * PopCarousel - A horizontally scrollable carousel component with pagination indicators.
 *
 * Displays a horizontal pager with customizable items, optional header, and pagination dots.
 * Supports auto-scroll functionality with configurable timing and behavior.
 *
 * Figma link: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=6130-2609
 *
 * @param modifier Modifier for the carousel container
 * @param itemCount Total number of items in the carousel
 * @param headerConfig Optional header configuration (title, body, CTA)
 * @param paginationConfig Pagination display configuration
 * @param carouselConfig Carousel behavior and appearance configuration
 * @param autoScrollConfig Optional auto-scroll configuration for automatic page advancement
 * @param onPageChanged Callback when current page changes
 * @param itemContent Composable content for each carousel item. Can use [PopCarouselItem] wrapper
 *                    or any other composable directly.
 *
 * @sample Using PopCarouselItem wrapper:
 * ```
 * PopCarousel(
 *     itemCount = 5,
 *     carouselConfig = PopCarouselConfig(itemWidthFraction = 0.8f)
 * ) { page ->
 *     PopCarouselItem(
 *         onClick = { /* handle click */ }
 *     ) {
 *         MyBannerItem(data = items[page])
 *     }
 * }
 * ```
 *
 * @sample Using any composable directly:
 * ```
 * PopCarousel(
 *     itemCount = 5,
 *     carouselConfig = PopCarouselConfig(itemWidthFraction = 0.8f)
 * ) { page ->
 *     MyBannerItem(data = items[page])
 * }
 * ```
 */
@Composable
fun PopCarousel(
    modifier: Modifier = Modifier,
    itemCount: Int,
    headerConfig: PopCarouselHeaderConfig? = null,
    paginationConfig: PopCarouselPaginationConfig = PopCarouselPaginationConfig(),
    carouselConfig: PopCarouselConfig = PopCarouselConfig(),
    autoScrollConfig: PopCarouselAutoScrollConfig? = null,
    onPageChanged: ((Int) -> Unit)? = null,
    itemContent: @Composable (page: Int) -> Unit
) {
    when (carouselConfig.type) {
        PopCarouselType.Pager -> {
            PopCarouselPager(
                modifier = modifier,
                itemCount = itemCount,
                headerConfig = headerConfig,
                paginationConfig = paginationConfig,
                carouselConfig = carouselConfig,
                autoScrollConfig = autoScrollConfig,
                onPageChanged = onPageChanged,
                itemContent = itemContent
            )
        }
        PopCarouselType.List -> {
            PopCarouselList(
                modifier = modifier,
                itemCount = itemCount,
                headerConfig = headerConfig,
                paginationConfig = paginationConfig,
                carouselConfig = carouselConfig,
                onPageChanged = onPageChanged,
                itemContent = itemContent
            )
        }
    }
}

/**
 * PopCarousel with list of composables
 *
 * Alternative API that accepts a list of composables directly instead of using a lambda.
 *
 * @param modifier Modifier for the carousel container
 * @param items List of composables to display in the carousel
 * @param headerConfig Optional header configuration
 * @param paginationConfig Pagination display configuration
 * @param carouselConfig Carousel behavior and appearance configuration
 * @param autoScrollConfig Optional auto-scroll configuration
 * @param onPageChanged Callback when current page changes
 *
 * @sample
 * ```
 * PopCarousel(
 *     items = listOf(
 *         { MyBannerItem(data = item1) },
 *         { MyBannerItem(data = item2) },
 *         { MyBannerItem(data = item3) }
 *     )
 * )
 * ```
 */
@Composable
fun PopCarousel(
    modifier: Modifier = Modifier,
    items: List<@Composable () -> Unit>,
    headerConfig: PopCarouselHeaderConfig? = null,
    paginationConfig: PopCarouselPaginationConfig = PopCarouselPaginationConfig(),
    carouselConfig: PopCarouselConfig = PopCarouselConfig(),
    autoScrollConfig: PopCarouselAutoScrollConfig? = null,
    onPageChanged: ((Int) -> Unit)? = null
) {
    PopCarousel(
        modifier = modifier,
        itemCount = items.size,
        headerConfig = headerConfig,
        paginationConfig = paginationConfig,
        carouselConfig = carouselConfig,
        autoScrollConfig = autoScrollConfig,
        onPageChanged = onPageChanged
    ) { page ->
        items[page]()
    }
}

/**
 * Pager-based carousel implementation
 */
@Composable
private fun PopCarouselPager(
    modifier: Modifier = Modifier,
    itemCount: Int,
    headerConfig: PopCarouselHeaderConfig? = null,
    paginationConfig: PopCarouselPaginationConfig = PopCarouselPaginationConfig(),
    carouselConfig: PopCarouselConfig = PopCarouselConfig(),
    autoScrollConfig: PopCarouselAutoScrollConfig? = null,
    onPageChanged: ((Int) -> Unit)? = null,
    itemContent: @Composable (page: Int) -> Unit
) {
    // Validate initialPage against itemCount
    if (itemCount > 0) {
        carouselConfig.validateInitialPage(itemCount)
    }
    
    val pagerState = rememberPagerState(
        initialPage = carouselConfig.initialPage.coerceIn(0, (itemCount - 1).coerceAtLeast(0)),
        pageCount = { itemCount }
    )

    // Track if user is dragging
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    
    // Track if auto-scroll is currently paused due to user interaction
    var isAutoScrollPaused by remember { mutableStateOf(false) }
    
    // Resume auto-scroll after user stops interacting
    LaunchedEffect(isDragged) {
        if (isDragged) {
            isAutoScrollPaused = true
        } else if (isAutoScrollPaused && autoScrollConfig?.resumeAfterInteraction == true) {
            delay(autoScrollConfig.resumeDelayMillis)
            isAutoScrollPaused = false
        }
    }

    // Auto-scroll logic
    // Use a continuous loop that only restarts when config changes, not on every page change
    LaunchedEffect(
        autoScrollConfig?.enabled,
        isAutoScrollPaused,
        itemCount
    ) {
        if (autoScrollConfig?.enabled == true && itemCount > 1) {
            while (true) {
                // Wait for the delay period
                delay(autoScrollConfig.delayMillis)
                
                // Check if paused or disabled during the delay
                if (isAutoScrollPaused || autoScrollConfig.enabled != true) {
                    continue
                }
                
                // Calculate next page
                val currentPage = pagerState.currentPage
                val nextPage = if (currentPage < itemCount - 1) {
                    currentPage + 1
                } else if (autoScrollConfig.loop) {
                    0
                } else {
                    // Stop at last page if not looping
                    break
                }
                
                // Animate to next page
                try {
                    pagerState.animateScrollToPage(
                        page = nextPage,
                        animationSpec = tween(
                            durationMillis = autoScrollConfig.animationDurationMillis,
                            easing = LinearEasing
                        )
                    )
                } catch (e: Exception) {
                    // Handle case where pager state might be invalid (e.g., itemCount changed)
                    break
                }
            }
        }
    }

    // Track page changes
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collectLatest { page ->
            onPageChanged?.invoke(page)
        }
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header section
        headerConfig?.let { config ->
            PopCarouselHeader(
                config = config,
                modifier = Modifier.padding(horizontal = PopSpacing.Spacing0)
            )
            Spacer(modifier = Modifier.height(PopSpacing.Spacing16))
        }

        // Carousel content with pager
        PopCarouselContent(
            pagerState = pagerState,
            itemCount = itemCount,
            config = carouselConfig,
            itemContent = itemContent
        )

        // Pagination
        if (paginationConfig.showPagination && itemCount > 1) {
            Spacer(modifier = Modifier.height(PopSpacing.Spacing8))
            
            // Use progress pagination if auto-scroll is enabled and showing progress
            if (autoScrollConfig?.enabled == true && autoScrollConfig.showProgressIndicator) {
                PopCarouselPaginationWithProgress(
                    pageCount = itemCount,
                    currentPage = pagerState.currentPage,
                    config = paginationConfig,
                    autoScrollDelayMillis = autoScrollConfig.delayMillis,
                    isPaused = isAutoScrollPaused || isDragged,
                    modifier = Modifier.padding(vertical = PopSpacing.Spacing4)
                )
            } else {
                PopCarouselPagination(
                    pageCount = itemCount,
                    currentPage = pagerState.currentPage,
                    config = paginationConfig,
                    modifier = Modifier.padding(vertical = PopSpacing.Spacing4)
                )
            }
        }
    }
}

/**
 * List-based carousel implementation (LazyRow)
 */
@Composable
private fun PopCarouselList(
    modifier: Modifier = Modifier,
    itemCount: Int,
    headerConfig: PopCarouselHeaderConfig? = null,
    paginationConfig: PopCarouselPaginationConfig = PopCarouselPaginationConfig(),
    carouselConfig: PopCarouselConfig = PopCarouselConfig(),
    onPageChanged: ((Int) -> Unit)? = null,
    itemContent: @Composable (page: Int) -> Unit
) {
    // Validate initialPage against itemCount
    if (itemCount > 0) {
        carouselConfig.validateInitialPage(itemCount)
    }
    
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = carouselConfig.initialPage.coerceIn(0, (itemCount - 1).coerceAtLeast(0))
    )
    
    // Calculate current page based on scroll position
    val currentPage = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) {
                0
            } else {
                // Get the most visible item (closest to center)
                val centerOffset = layoutInfo.viewportSize.width / 2
                visibleItems.minByOrNull { 
                    kotlin.math.abs(it.offset + it.size / 2 - centerOffset)
                }?.index ?: 0
            }
        }
    }

    // Track page changes
    LaunchedEffect(currentPage.value) {
        onPageChanged?.invoke(currentPage.value)
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header section
        headerConfig?.let { config ->
            PopCarouselHeader(
                config = config,
                modifier = Modifier.padding(horizontal = PopSpacing.Spacing0)
            )
            Spacer(modifier = Modifier.height(PopSpacing.Spacing16))
        }

        // Carousel content with LazyRow
        PopCarouselListContent(
            listState = listState,
            itemCount = itemCount,
            config = carouselConfig,
            itemContent = itemContent
        )

        // Pagination
        if (paginationConfig.showPagination && itemCount > 1) {
            Spacer(modifier = Modifier.height(PopSpacing.Spacing8))
            PopCarouselPagination(
                pageCount = itemCount,
                currentPage = currentPage.value,
                config = paginationConfig,
                modifier = Modifier.padding(vertical = PopSpacing.Spacing4)
            )
        }
    }
}

/**
 * Header configuration for PopCarousel
 *
 * @param title Main title text (required if header is shown)
 * @param body Optional body/subtitle text
 * @param titleLeadingIcon Optional composable for icon before title
 * @param titleTrailingIcon Optional composable for icon after title
 * @param bodyLeadingIcon Optional composable for icon before body
 * @param bodyTrailingIcon Optional composable for icon after body
 * @param trailingContent Optional trailing content (CTA button, slot, etc.)
 */
data class PopCarouselHeaderConfig(
    val title: String,
    val body: String? = null,
    val titleLeadingIcon: (@Composable () -> Unit)? = null,
    val titleTrailingIcon: (@Composable () -> Unit)? = null,
    val bodyLeadingIcon: (@Composable () -> Unit)? = null,
    val bodyTrailingIcon: (@Composable () -> Unit)? = null,
    val trailingContent: (@Composable () -> Unit)? = null
)

/**
 * Pagination configuration for PopCarousel
 *
 * @param showPagination Whether to show pagination indicators
 * @param maxVisibleDots Maximum number of dots to show (always 5 per Figma)
 * @param dotSize Size of pagination dots (normal size)
 * @param dotOverflowSize Size of overflow indicator dots (smaller)
 * @param dotSpacing Spacing between dots
 * @param selectedColor Color for selected dot
 * @param unselectedColor Color for unselected dots
 * @param showGlow Whether to show glow effect on selected dot
 */
data class PopCarouselPaginationConfig(
    val showPagination: Boolean = true,
    val maxVisibleDots: Int = 5, // Always 5 per Figma design
    val dotSize: Dp = 4.dp,
    val dotOverflowSize: Dp = 2.dp, // Smaller size for overflow indicators
    val dotSpacing: Dp = 6.dp,
    val selectedColor: Color = BorderColor.PrimaryInvert,
    val unselectedColor: Color = BorderColor.PrimaryInvertTransparent10,
    val showGlow: Boolean = true
)

/**
 * Carousel display type
 */
enum class PopCarouselType {
    /**
     * Pager-based carousel with snap-to-page behavior
     * Best for: Discrete pages, pagination indicators, auto-scroll
     */
    Pager,
    
    /**
     * Horizontal list with free scrolling
     * Best for: Continuous scrolling, no pagination needed
     */
    List
}

/**
 * Carousel behavior and appearance configuration
 *
 * @param type Carousel display type (Pager or List)
 * @param itemWidthFraction Fraction of screen width for each item (0.0-1.0)
 *                          Used when itemWidthProvider is null (uniform width for all items)
 * @param itemWidthProvider Optional function to provide dynamic width for each item (0.0-1.0)
 *                         Takes page index and returns width fraction. Overrides itemWidthFraction when provided.
 *                         1.0 = full width, 0.5 = half width, etc.
 * @param itemSpacing Spacing between carousel items
 * @param showOverflowGradient Whether to show fade gradient at edges
 * @param overflowGradientWidth Width of the overflow gradient
 * @param initialPage Initial page to display (only used for Pager)
 * @param contentPadding Padding around the carousel content
 */
data class PopCarouselConfig(
    val type: PopCarouselType = PopCarouselType.Pager,
    val itemWidthFraction: Float = 0.86f,
    val itemWidthProvider: ((Int) -> Float)? = null,
    val itemSpacing: Dp = 12.dp,
    val showOverflowGradient: Boolean = false,
    val overflowGradientWidth: Dp = 12.dp,
    val initialPage: Int = 0,
    val contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp)
) {
    init {
        require(itemWidthFraction in 0f..1f) {
            "itemWidthFraction must be between 0.0 and 1.0, got $itemWidthFraction"
        }
        require(initialPage >= 0) {
            "initialPage must be non-negative, got $initialPage"
        }
        // Note: initialPage validation against itemCount happens at usage time
        // since itemCount is not available in the config
    }
    
    /**
     * Gets the width fraction for a specific item
     * Validates and coerces the value to be within [0f, 1f]
     */
    fun getItemWidth(page: Int): Float {
        val width = itemWidthProvider?.invoke(page)?.coerceIn(0f, 1f) 
            ?: itemWidthFraction.coerceIn(0f, 1f)
        return width
    }
    
    /**
     * Validates initialPage against itemCount
     * Should be called when itemCount is known
     */
    fun validateInitialPage(itemCount: Int) {
        require(initialPage < itemCount) {
            "initialPage ($initialPage) must be less than itemCount ($itemCount)"
        }
    }
}

/**
 * Auto-scroll configuration for PopCarousel
 *
 * @param enabled Whether auto-scroll is enabled
 * @param delayMillis Time in milliseconds to wait before advancing to next page
 * @param loop Whether to loop back to the first page after the last
 * @param animationDurationMillis Duration of the scroll animation in milliseconds
 * @param pauseOnInteraction Whether to pause auto-scroll when user interacts (always true)
 * @param resumeAfterInteraction Whether to resume auto-scroll after user stops interacting
 * @param resumeDelayMillis Delay before resuming auto-scroll after user interaction
 * @param showProgressIndicator Whether to show progress indicator on pagination dot
 */
data class PopCarouselAutoScrollConfig(
    val enabled: Boolean = true,
    val delayMillis: Long = 5000L,
    val loop: Boolean = true,
    val animationDurationMillis: Int = 500,
    val pauseOnInteraction: Boolean = true,
    val resumeAfterInteraction: Boolean = true,
    val resumeDelayMillis: Long = 3000L,
    val showProgressIndicator: Boolean = true
)

/**
 * Internal header composable
 */
@Composable
private fun PopCarouselHeader(
    config: PopCarouselHeaderConfig,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    
    // Track title text overflow
    var titleOverflows by remember { mutableStateOf(false) }
    var titleOverflowPx by remember { mutableStateOf(0f) }
    var titleAvailableWidth by remember { mutableStateOf(0f) }
    
    // Track body text overflow
    var bodyOverflows by remember { mutableStateOf(false) }
    var bodyOverflowPx by remember { mutableStateOf(0f) }
    var bodyAvailableWidth by remember { mutableStateOf(0f) }

    // Calculate title text width
    val titleTextWidth = remember(config.title) {
        textMeasurer.measure(
            text = androidx.compose.ui.text.AnnotatedString(config.title),
            style = PopTypography.headingSmall,
            softWrap = false,
            maxLines = 1
        ).size.width.toFloat()
    }

    // Calculate body text width if provided
    val bodyTextWidth = remember(config.body) {
        if (config.body != null) {
            textMeasurer.measure(
                text = androidx.compose.ui.text.AnnotatedString(config.body),
                style = PopTypography.labelXSmall,
                softWrap = false,
                maxLines = 1
            ).size.width.toFloat()
        } else {
            0f
        }
    }

    // Update overflow states when available width changes
    LaunchedEffect(titleTextWidth, titleAvailableWidth) {
        val titleWidth: Float = titleTextWidth
        val titleAvailable: Float = titleAvailableWidth
        titleOverflows = titleWidth > titleAvailable
        titleOverflowPx = (titleWidth - titleAvailable).coerceAtLeast(0f)
    }

    LaunchedEffect(bodyTextWidth, bodyAvailableWidth) {
        val bodyWidth: Float = bodyTextWidth
        val bodyAvailable: Float = bodyAvailableWidth
        if (config.body != null && bodyWidth > 0f) {
            bodyOverflows = bodyWidth > bodyAvailable
            bodyOverflowPx = (bodyWidth - bodyAvailable).coerceAtLeast(0f)
        } else {
            bodyOverflows = false
            bodyOverflowPx = 0f
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Title + Body column
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            // Title row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        // Account for icon spacing
                        val iconSpacing = with(density) { PopSpacing.Spacing6.toPx() }
                        val hasLeadingIcon = config.titleLeadingIcon != null
                        val hasTrailingIcon = config.titleTrailingIcon != null
                        val iconSpace = (if (hasLeadingIcon) iconSpacing else 0f) + 
                                      (if (hasTrailingIcon) iconSpacing else 0f)
                        titleAvailableWidth = with(density) { coordinates.size.width.toDp().toPx() } - iconSpace
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing6)
            ) {
                config.titleLeadingIcon?.invoke()

                PopMarqueeText(
                    modifier = Modifier.weight(1f, fill = false),
                    text = config.title,
                    style = PopTypography.headingSmall,
                    color = TextColor.Primary,
                    shouldAnimate = titleOverflows,
                    textOverflows = titleOverflows,
                    maxLines = 1,
                    gradientWidth = 12.dp,
                    showLeftGradient = false,
                    showRightGradient = titleOverflows,
                    overflowDistancePx = if (titleOverflowPx > 0f) titleOverflowPx else null
                )

                config.titleTrailingIcon?.invoke()
            }

            // Body row (if provided)
            config.body?.let { bodyText ->
                Spacer(modifier = Modifier.height(PopSpacing.Spacing4))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned { coordinates ->
                            // Account for icon spacing
                            val iconSpacing = with(density) { PopSpacing.Spacing6.toPx() }
                            val hasLeadingIcon = config.bodyLeadingIcon != null
                            val hasTrailingIcon = config.bodyTrailingIcon != null
                            val iconSpace = (if (hasLeadingIcon) iconSpacing else 0f) + 
                                          (if (hasTrailingIcon) iconSpacing else 0f)
                            bodyAvailableWidth = with(density) { coordinates.size.width.toDp().toPx() } - iconSpace
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing6)
                ) {
                    config.bodyLeadingIcon?.invoke()

                    PopMarqueeText(
                        modifier = Modifier.weight(1f, fill = false),
                        text = bodyText,
                        style = PopTypography.labelXSmall,
                        color = TextColor.Tertiary,
                        shouldAnimate = bodyOverflows,
                        textOverflows = bodyOverflows,
                        maxLines = 1,
                        gradientWidth = 12.dp,
                        showLeftGradient = false,
                        showRightGradient = bodyOverflows,
                        overflowDistancePx = if (bodyOverflowPx > 0f) bodyOverflowPx else null
                    )

                    config.bodyTrailingIcon?.invoke()
                }
            }
        }

        // Trailing content (CTA, slot, etc.)
        config.trailingContent?.invoke()
    }
}

/**
 * Internal carousel content with HorizontalPager
 */
@Composable
private fun PopCarouselContent(
    pagerState: PagerState,
    itemCount: Int,
    config: PopCarouselConfig,
    itemContent: @Composable (page: Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    
    // Calculate padding based on first item width (for centering)
    val firstItemWidth = screenWidth * config.getItemWidth(0)
    val horizontalPadding = (screenWidth - firstItemWidth) / 2

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (config.showOverflowGradient) {
                    Modifier.horizontalGradientFade(
                        gradientWidth = config.overflowGradientWidth,
                        shouldShowLeftGradient = pagerState.currentPage > 0,
                        shouldShowRightGradient = pagerState.currentPage < itemCount - 1
                    )
                } else {
                    Modifier
                }
            )
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            pageSpacing = config.itemSpacing,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val itemWidth = screenWidth * config.getItemWidth(page)
            Box(
                modifier = Modifier.width(itemWidth)
            ) {
                itemContent(page)
            }
        }
    }
}

/**
 * Internal carousel content with LazyRow
 */
@Composable
private fun PopCarouselListContent(
    listState: LazyListState,
    itemCount: Int,
    config: PopCarouselConfig,
    itemContent: @Composable (page: Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    
    // Calculate padding based on first item width (for centering)
    val firstItemWidth = screenWidth * config.getItemWidth(0)
    val horizontalPadding = (screenWidth - firstItemWidth) / 2

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (config.showOverflowGradient) {
                    Modifier.horizontalGradientFade(
                        gradientWidth = config.overflowGradientWidth,
                        shouldShowLeftGradient = listState.firstVisibleItemIndex > 0,
                        shouldShowRightGradient = listState.firstVisibleItemIndex < itemCount - 1
                    )
                } else {
                    Modifier
                }
            )
    ) {
        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = horizontalPadding),
            horizontalArrangement = Arrangement.spacedBy(config.itemSpacing),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(itemCount) { page ->
                val itemWidth = screenWidth * config.getItemWidth(page)
                Box(
                    modifier = Modifier.width(itemWidth)
                ) {
                    itemContent(page)
                }
            }
        }
    }
}

/**
 * Shared pagination structure composable
 * Handles the layout and logic for pagination indicators
 */
@Composable
private fun PopCarouselPaginationStructure(
    pageCount: Int,
    currentPage: Int,
    config: PopCarouselPaginationConfig,
    modifier: Modifier = Modifier,
    dotContent: @Composable (pageIndex: Int, isSelected: Boolean, isOverflow: Boolean) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(config.dotSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val maxDots = config.maxVisibleDots // Always 5
        
        if (pageCount <= maxDots) {
            // Show all items if total is 5 or less
            repeat(pageCount) { index ->
                val isSelected = index == currentPage
                dotContent(index, isSelected, false)
            }
        } else {
            // More than 5 items: center current item, show overflow indicators
            val paginationRange = calculatePaginationRange(currentPage, pageCount, maxDots)
            
            // First indicator: overflow if needed
            if (paginationRange.showFirstOverflow) {
                dotContent(-1, false, true)
            }
            
            // Middle indicators (the actual pages)
            for (pageIndex in paginationRange.startIndex..paginationRange.endIndex) {
                val isSelected = pageIndex == currentPage
                dotContent(pageIndex, isSelected, false)
            }
            
            // Last indicator: overflow if needed
            if (paginationRange.showLastOverflow) {
                dotContent(-1, false, true)
            }
        }
    }
}

/**
 * Internal pagination composable with discrete dots
 * Implements Figma design: max 5 indicators, current always centered, overflow indicators smaller
 */
@Composable
private fun PopCarouselPagination(
    pageCount: Int,
    currentPage: Int,
    config: PopCarouselPaginationConfig,
    modifier: Modifier = Modifier
) {
    PopCarouselPaginationStructure(
        pageCount = pageCount,
        currentPage = currentPage,
        config = config,
        modifier = modifier
    ) { pageIndex, isSelected, isOverflow ->
        PopCarouselPaginationDot(
            isSelected = isSelected,
            size = if (isOverflow) config.dotOverflowSize else config.dotSize,
            selectedColor = config.selectedColor,
            unselectedColor = config.unselectedColor,
            showGlow = config.showGlow && isSelected && !isOverflow
        )
    }
}

/**
 * Calculates which page indices to show in pagination, keeping current page centered
 * Returns: (startIndex, endIndex, showFirstOverflow, showLastOverflow)
 * 
 * Logic per Figma:
 * - Always show max 5 indicators total (including overflow indicators)
 * - Current page is always at center position (index 2 of 5)
 * - Overflow indicators are smaller and shown at edges when needed
 * - When at actual first/last page, overflow indicator becomes normal size
 * 
 * Examples:
 * - Total 10, current 1: [0, 1, 2, 3, overflow] - last is smaller (4 pages + 1 overflow)
 * - Total 10, current 8: [overflow, 6, 7, 8, 9] - first is smaller (1 overflow + 4 pages)
 * - Total 10, current 0: [0, 1, 2, 3, 4] - all normal (at first page, no overflow)
 * - Total 10, current 9: [5, 6, 7, 8, 9] - all normal (at last page, no overflow)
 */
private fun calculatePaginationRange(
    currentPage: Int,
    totalPages: Int,
    maxDots: Int
): PaginationRange {
    // Determine if we need overflow indicators
    // Overflow indicators are smaller unless they represent the actual first/last page AND that page is active
    // If current is near start (<= 2), show overflow at end (always, since we're not at last page)
    // If current is near end (>= totalPages - 3), show overflow at start (always, since we're not at first page)
    val nearStart = currentPage <= 2
    val nearEnd = currentPage >= totalPages - 3
    
    // Show overflow at end if we're near start (we're not at the last page)
    val showLastOverflow = nearStart && currentPage < totalPages - 1
    // Show overflow at start if we're near end (we're not at the first page)
    val showFirstOverflow = nearEnd && currentPage > 0
    
    // Calculate how many actual page indicators we can show
    val overflowCount = (if (showFirstOverflow) 1 else 0) + (if (showLastOverflow) 1 else 0)
    val actualPageIndicators = maxDots - overflowCount
    
    // Center current page among actual page indicators
    val centerInActual = (actualPageIndicators - 1) / 2
    val beforeCenter = centerInActual
    val afterCenter = actualPageIndicators - centerInActual - 1
    
    var startIndex = currentPage - beforeCenter
    var endIndex = currentPage + afterCenter
    
    // Adjust if too close to boundaries
    if (startIndex < 0) {
        val adjustment = -startIndex
        startIndex = 0
        endIndex = kotlin.comparisons.minOf(endIndex + adjustment, totalPages - 1)
    }
    if (endIndex >= totalPages) {
        val adjustment = endIndex - (totalPages - 1)
        endIndex = totalPages - 1
        startIndex = kotlin.comparisons.maxOf(0, startIndex - adjustment)
    }
    
    // Ensure we show the correct number of pages
    val actualPagesShown = endIndex - startIndex + 1
    if (actualPagesShown < actualPageIndicators) {
        // Need to expand to show more pages
        val needed = actualPageIndicators - actualPagesShown
        if (startIndex == 0 && endIndex < totalPages - 1) {
            // Expand to the right
            endIndex = kotlin.comparisons.minOf(endIndex + needed, totalPages - 1)
        } else if (endIndex == totalPages - 1 && startIndex > 0) {
            // Expand to the left
            startIndex = kotlin.comparisons.maxOf(0, startIndex - needed)
        }
    }
    
    // Re-evaluate overflow after boundary adjustments
    // Overflow indicators are shown when:
    // - First overflow: when we're near end and not at first page (startIndex > 0 means there are pages before)
    // - Last overflow: when we're near start and not at last page (endIndex < totalPages - 1 means there are pages after)
    val finalShowFirstOverflow = showFirstOverflow && startIndex > 0
    val finalShowLastOverflow = showLastOverflow && endIndex < totalPages - 1
    
    return PaginationRange(startIndex, endIndex, finalShowFirstOverflow, finalShowLastOverflow)
}

/**
 * Helper data class for pagination range calculation
 */
private data class PaginationRange(
    val startIndex: Int,
    val endIndex: Int,
    val showFirstOverflow: Boolean,
    val showLastOverflow: Boolean
)

/**
 * Individual pagination dot
 */
@Composable
private fun PopCarouselPaginationDot(
    isSelected: Boolean,
    size: Dp,
    selectedColor: Color,
    unselectedColor: Color,
    showGlow: Boolean
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .then(
                if (showGlow) {
                    Modifier.shadow(
                        elevation = PAGINATION_SHADOW_ELEVATION_DP.dp,
                        shape = CircleShape,
                        ambientColor = Color.White.copy(alpha = PAGINATION_SHADOW_ALPHA),
                        spotColor = Color.White.copy(alpha = PAGINATION_SHADOW_ALPHA)
                    )
                } else {
                    Modifier
                }
            )
            .background(
                color = if (isSelected) selectedColor else unselectedColor,
                shape = CircleShape
            )
    )
}

/**
 * Pagination with progress indicator for auto-scroll
 * Uses same centered logic as regular pagination
 */
@Composable
private fun PopCarouselPaginationWithProgress(
    pageCount: Int,
    currentPage: Int,
    config: PopCarouselPaginationConfig,
    autoScrollDelayMillis: Long,
    isPaused: Boolean,
    modifier: Modifier = Modifier
) {
    PopCarouselPaginationStructure(
        pageCount = pageCount,
        currentPage = currentPage,
        config = config,
        modifier = modifier
    ) { pageIndex, isSelected, isOverflow ->
        if (isOverflow) {
            // Overflow indicators use regular dots
            PopCarouselPaginationDot(
                isSelected = false,
                size = config.dotOverflowSize,
                selectedColor = config.selectedColor,
                unselectedColor = config.unselectedColor,
                showGlow = false
            )
        } else {
            // Regular page indicators use progress dots
            PopCarouselPaginationDotWithProgressInternal(
                isSelected = isSelected,
                isPaused = isPaused && isSelected,
                autoScrollDelayMillis = autoScrollDelayMillis,
                size = config.dotSize,
                progressWidth = 24.dp,
                selectedColor = config.selectedColor,
                unselectedColor = config.unselectedColor,
                showGlow = config.showGlow
            )
        }
    }
}

/**
 * Individual pagination dot with progress indicator
 */
@Composable
private fun PopCarouselPaginationDotWithProgressInternal(
    isSelected: Boolean,
    isPaused: Boolean,
    autoScrollDelayMillis: Long,
    size: Dp,
    progressWidth: Dp,
    selectedColor: Color,
    unselectedColor: Color,
    showGlow: Boolean
) {
    val progress = remember { Animatable(0f) }

    // Animate progress when selected and not paused
    LaunchedEffect(isSelected, isPaused) {
        if (isSelected && !isPaused) {
            progress.snapTo(0f)
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = autoScrollDelayMillis.toInt(),
                    easing = LinearEasing
                )
            )
        } else if (!isSelected) {
            progress.snapTo(0f)
        }
        // When paused, keep current progress (don't reset)
    }

    Box(
        modifier = Modifier
            .then(
                if (isSelected) {
                    Modifier
                        .width(progressWidth)
                        .height(size)
                } else {
                    Modifier.size(size)
                }
            )
            .clip(CircleShape)
            .background(
                color = unselectedColor,
                shape = CircleShape
            )
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.value.coerceAtLeast(PROGRESS_MIN_WIDTH_FRACTION))
                    .height(size)
                    .then(
                        if (showGlow) {
                            Modifier.shadow(
                                elevation = PAGINATION_SHADOW_ELEVATION_DP.dp,
                                shape = CircleShape,
                                ambientColor = Color.White.copy(alpha = PAGINATION_SHADOW_ALPHA),
                                spotColor = Color.White.copy(alpha = PAGINATION_SHADOW_ALPHA)
                            )
                        } else {
                            Modifier
                        }
                    )
                    .background(
                        color = selectedColor,
                        shape = CircleShape
                    )
            )
        }
    }
}

/**
 * Progress-based pagination dot with animation (for auto-play)
 */
@Composable
fun PopCarouselPaginationDotWithProgress(
    isSelected: Boolean,
    isPlaying: Boolean,
    autoPlayDuration: Long,
    size: Dp,
    progressWidth: Dp = 24.dp,
    selectedColor: Color,
    unselectedColor: Color,
    onProgressComplete: () -> Unit
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(isSelected, isPlaying) {
        if (isSelected && isPlaying) {
            progress.snapTo(0f)
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = autoPlayDuration.toInt(),
                    easing = LinearEasing
                )
            )
            onProgressComplete()
        } else {
            progress.snapTo(0f)
        }
    }

    Box(
        modifier = Modifier
            .then(
                if (isSelected && isPlaying) {
                    Modifier
                        .width(progressWidth)
                        .height(size)
                } else {
                    Modifier.size(size)
                }
            )
            .clip(CircleShape)
            .background(
                color = unselectedColor,
                shape = CircleShape
            )
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(if (isPlaying) progress.value else 1f)
                    .height(size)
                    .shadow(
                        elevation = PAGINATION_SHADOW_ELEVATION_DP.dp,
                        shape = CircleShape,
                        ambientColor = Color.White.copy(alpha = PAGINATION_SHADOW_ALPHA),
                        spotColor = Color.White.copy(alpha = PAGINATION_SHADOW_ALPHA)
                    )
                    .background(
                        color = selectedColor,
                        shape = CircleShape
                    )
            )
        }
    }
}

// ============================================================================
// Previews
// ============================================================================

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PopCarouselPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        PopCarousel(
            itemCount = 5,
            headerConfig = PopCarouselHeaderConfig(
                title = "Title"
            ),
            carouselConfig = PopCarouselConfig(
                itemWidthFraction = 0.86f
            )
        ) { page ->
            // Sample banner item
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        color = Color(0xFF1F1F1F),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Banner ${page + 1}",
                    style = PopTypography.labelLarge,
                    color = TextColor.Primary
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PopCarouselWithAutoScrollPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        PopCarousel(
            itemCount = 5,
            headerConfig = PopCarouselHeaderConfig(
                title = "Auto-Scroll Carousel"
            ),
            carouselConfig = PopCarouselConfig(
                itemWidthFraction = 0.86f
            ),
            autoScrollConfig = PopCarouselAutoScrollConfig(
                enabled = true,
                delayMillis = 3000L,
                loop = true,
                showProgressIndicator = true
            )
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        color = Color(0xFF1F1F1F),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Slide ${page + 1}",
                    style = PopTypography.labelLarge,
                    color = TextColor.Primary
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PopCarouselWithBodyPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        PopCarousel(
            itemCount = 3,
            headerConfig = PopCarouselHeaderConfig(
                title = "Featured Offers",
                body = "Check out our latest deals"
            ),
            paginationConfig = PopCarouselPaginationConfig(
                showPagination = true
            )
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(
                        color = Color(0xFF1F1F1F),
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Item ${page + 1}",
                    style = PopTypography.labelLarge,
                    color = TextColor.Primary
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PopCarouselPaginationOnlyPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PopCarouselPagination(
            pageCount = 5,
            currentPage = 0,
            config = PopCarouselPaginationConfig()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PopCarouselPagination(
            pageCount = 5,
            currentPage = 2,
            config = PopCarouselPaginationConfig()
        )
        Spacer(modifier = Modifier.height(16.dp))
        PopCarouselPagination(
            pageCount = 5,
            currentPage = 4,
            config = PopCarouselPaginationConfig()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PopCarouselPaginationWithProgressPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Progress Pagination (Auto-scroll)",
            style = PopTypography.labelXSmall,
            color = TextColor.Tertiary
        )
        Spacer(modifier = Modifier.height(8.dp))
        PopCarouselPaginationWithProgress(
            pageCount = 5,
            currentPage = 0,
            config = PopCarouselPaginationConfig(),
            autoScrollDelayMillis = 3000L,
            isPaused = false
        )
    }
}

















