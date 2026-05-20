package com.pop.components.ds_components

import android.app.Activity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.pop.components.compose_components.PopDivider
import com.pop.components.ds_components.scrim.ScrimState
import com.pop.components.ds_components.scrim.blurModifier
import com.pop.components.ds_components.scrim.rememberScrimState
import com.pop.components.models.MerchantImageSize
import com.pop.components.models.PopTopBarConfig
import com.pop.components.models.StatusBarTheme
import com.pop.components.models.isValid
import com.pop.components.theme.PopColor
import com.pop.components.theme.SurfaceColor

/**
 * PopTopBar component - A complete top bar with app bar, optional merchant image, and title bar
 * Supports scroll-aware collapsing behavior with animations
 *
 * Figma: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=5435-4761&m=dev
 *        https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=1591-15413&m=dev
 *        https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=2569-3452&m=dev (initial)
 *        https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=2569-3441&m=dev (collapsing)
 *        https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=2569-3419&m=dev (collapsed with blur)
 *
 * Features:
 * - Status bar handling (Light, Dark, Transparent)
 * - App bar with navigation and actions
 * - Optional merchant image background (Large, Medium, Small sizes)
 * - PopTitleBar at the bottom
 * - Scroll-aware collapsing with animations
 * - Title bar fade out on scroll
 * - Blur effect when collapsed
 * - Extends to status bar when merchant image is present
 *
 * @param modifier Modifier for the component
 * @param config Configuration for the top bar
 * @param scrollState Optional ScrollState for Column/verticalScroll - if provided, enables scroll-aware collapsing
 * @param lazyListState Optional LazyListState for LazyColumn - if provided, enables scroll-aware collapsing
 * @param scrollBehavior Optional scroll behavior state (alternative to scrollState/lazyListState)
 * @param scrollBehaviorConfig Optional scroll behavior configuration (used when scrollState or lazyListState is provided)
 * @param contentNeedsScrolling Whether content actually needs scrolling. If false, top bar will not collapse.
 * @param centerRightSlot Optional composable slot for title bar center-right position
 */
@OptIn(com.pop.components.ds_components.scrim.ExperimentalScrimApi::class)
@Composable
fun PopTopBar(
    modifier: Modifier = Modifier,
    config: PopTopBarConfig = PopTopBarConfig.default(),
    scrollState: ScrollState? = null,
    lazyListState: LazyListState? = null,
    scrollBehavior: PopTopBarScrollBehaviorState? = null,
    scrollBehaviorConfig: PopTopBarScrollBehaviorConfig = PopTopBarScrollBehaviorConfig(),
    contentNeedsScrolling: Boolean = true,
    centerRightSlot: (@Composable () -> Unit)? = null,
    scrimState: ScrimState? = null,
    isContentScrollingBehind: Boolean = false // External indicator for blur when no merchant image
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    // Calculate merchant image height based on aspect ratio or fixed height
    val merchantImageHeight = remember(
        config.merchantImage,
        config.merchantImageSize,
        config.useAspectRatioBasedHeight,
        configuration.screenWidthDp
    ) {
        if (config.merchantImage != null && config.merchantImage.isValid()) {
            if (config.useAspectRatioBasedHeight) {
                // New behavior: Calculate height based on screen width and aspect ratio
                // Aspect ratios: height:width
                // Small: 1:2.77, so height = width / 2.77
                // Medium: 1:1.52, so height = width / 1.52
                // Large: 1:1.23, so height = width / 1.23
                val screenWidthDp = configuration.screenWidthDp.dp
                val aspectRatio = when (config.merchantImageSize) {
                    MerchantImageSize.Small -> 2.77f
                    MerchantImageSize.Medium -> 1.52f
                    MerchantImageSize.Large -> 1.23f
                    MerchantImageSize.XLarge -> 0.79f
                }
                screenWidthDp / aspectRatio
            } else {
                // Legacy behavior: Fixed heights
                when (config.merchantImageSize) {
                    MerchantImageSize.XLarge -> 500.dp
                    MerchantImageSize.Large -> 320.dp
                    MerchantImageSize.Medium -> 260.dp
                    MerchantImageSize.Small -> 200.dp
                }
            }
        } else {
            null
        }
    }

    // Calculate max scroll distance based on merchant image height
    val maxScrollDistance = remember(merchantImageHeight) {
        merchantImageHeight ?: run {
            // No merchant image - collapse title bar only
            with(density) { 72.dp } // Approximate title bar height
        }
    }

    // Always create scroll behavior for nested scroll support
    val internalScrollBehavior = rememberPopTopBarScrollBehavior(
        config = remember(scrollBehaviorConfig, maxScrollDistance) {
            scrollBehaviorConfig.copy(maxScrollDistance = maxScrollDistance)
        }
    )

    // Use provided scrollBehavior or internal one
    val effectiveScrollBehavior = scrollBehavior ?: internalScrollBehavior

    // Connect scroll states to behavior if provided
    // Only connect if content needs scrolling - prevents scroll offset updates when content fits
    if (contentNeedsScrolling) {
        if (scrollState != null) {
            effectiveScrollBehavior.connectToScrollState(scrollState, maxScrollDistance)
        }

        if (lazyListState != null) {
            effectiveScrollBehavior.connectToLazyListState(lazyListState, maxScrollDistance)
        }
    }

    // Calculate total height based on merchant image height (aspect ratio or fixed)
    // When there's no merchant image, include title bar height
    val totalHeight = remember(merchantImageHeight, config.titleBarConfig) {
        merchantImageHeight ?: run {
            // No merchant image: status bar (44dp) + reduced app bar space (28dp) + title bar (~60dp with padding)
            // The app bar space is reduced from 48dp to 28dp to minimize gap between app bar and title bar
            // (previously achieved via -20dp offset which caused layout issues)
            // Title bar height is approximately 60dp (12dp padding top + ~36dp content + 12dp padding bottom)
            if (config.titleBarConfig != null) {
                with(density) { (44.dp + 28.dp + 60.dp) } // Status bar + App bar space + Title bar = 132dp
            } else {
                with(density) { (44.dp + 28.dp) } // Status bar + App bar space only = 72dp
            }
        }
    }

    // Calculate collapsed height (status bar + app bar only)
    // App bar is now at activity level, but we reserve space for it
    val collapsedHeight = remember {
        with(density) { (44.dp + 48.dp) } // Status bar + App bar (reserved space)
    }

    val hasMerchantImage = config.merchantImage != null && config.merchantImage.isValid()

    // Get scroll progress values from behavior
    // If content doesn't need scrolling OR there's no merchant image, keep all values at their initial state (no collapse, no fade)
    val titleBarAlpha by remember(effectiveScrollBehavior, contentNeedsScrolling, hasMerchantImage) {
        derivedStateOf {
            if (!contentNeedsScrolling || !hasMerchantImage) {
                1f // Keep title bar fully visible
            } else {
                effectiveScrollBehavior?.titleBarAlpha ?: 1f
            }
        }
    }

    val isTitleBarVisible by remember(effectiveScrollBehavior, contentNeedsScrolling, hasMerchantImage) {
        derivedStateOf {
            if (!contentNeedsScrolling || !hasMerchantImage) {
                true // Keep title bar visible
            } else {
                effectiveScrollBehavior?.isTitleBarVisible ?: true
            }
        }
    }

    val titleBarOffsetPx by remember(effectiveScrollBehavior, contentNeedsScrolling, hasMerchantImage) {
        derivedStateOf {
            if (!contentNeedsScrolling || !hasMerchantImage) {
                0f // Keep title bar at original position
            } else {
                effectiveScrollBehavior?.titleBarOffsetPx ?: 0f
            }
        }
    }

    val merchantImageOffsetPx by remember(effectiveScrollBehavior, contentNeedsScrolling, hasMerchantImage) {
        derivedStateOf {
            if (!contentNeedsScrolling || !hasMerchantImage) {
                0f // Keep merchant image at original position
            } else {
                effectiveScrollBehavior?.merchantImageOffsetPx ?: 0f
            }
        }
    }

    val merchantImageAlpha by remember(effectiveScrollBehavior, contentNeedsScrolling, hasMerchantImage) {
        derivedStateOf {
            if (!contentNeedsScrolling || !hasMerchantImage) {
                1f // Keep merchant image fully visible
            } else {
                effectiveScrollBehavior?.merchantImageAlpha ?: 1f
            }
        }
    }

    val blurIntensity by remember(effectiveScrollBehavior, contentNeedsScrolling, hasMerchantImage) {
        derivedStateOf {
            if (!contentNeedsScrolling || !hasMerchantImage) {
                0f // No blur effect
            } else {
                effectiveScrollBehavior?.blurIntensity ?: 0f
            }
        }
    }

    val heightOffsetPx by remember(effectiveScrollBehavior, contentNeedsScrolling, hasMerchantImage) {
        derivedStateOf {
            // If content doesn't need scrolling OR there's no merchant image, don't collapse (keep heightOffsetPx at 0)
            val offset = if (!contentNeedsScrolling || !hasMerchantImage) {
                0f
            } else {
                effectiveScrollBehavior?.heightOffsetPx ?: 0f
            }
            offset
        }
    }

    val animatedTitleBarAlpha by animateFloatAsState(
        targetValue = titleBarAlpha,
        animationSpec = tween(durationMillis = 200),
        label = "TitleBarAlpha"
    )

    val animatedBlurIntensity by animateFloatAsState(
        targetValue = blurIntensity,
        animationSpec = tween(durationMillis = 150),
        label = "BlurIntensity"
    )

    // Calculate max height offset (when top bar reaches collapsed height)
    val maxHeightOffsetPx = remember(totalHeight, collapsedHeight) {
        totalHeight?.let { maxHeight ->
            val maxHeightPx = with(density) { maxHeight.toPx() }
            val collapsedHeightPx = with(density) { collapsedHeight.toPx() }
            maxHeightPx - collapsedHeightPx
        } ?: 0f
    }

    // Update max height offset in scroll behavior
    LaunchedEffect(maxHeightOffsetPx, effectiveScrollBehavior) {
        effectiveScrollBehavior?.updateMaxHeightOffset(maxHeightOffsetPx)
    }

    // Calculate current height based on scroll
    // If content doesn't need scrolling OR there's no merchant image, always use max height (no collapse)
    val currentHeight = remember(totalHeight, heightOffsetPx, collapsedHeight, contentNeedsScrolling, hasMerchantImage) {
        val height = if (!contentNeedsScrolling || !hasMerchantImage) {
            // When content doesn't need scrolling or no merchant image, always use max height
            totalHeight ?: collapsedHeight
        } else {
            totalHeight?.let { maxHeight ->
                val maxHeightPx = with(density) { maxHeight.toPx() }
                val collapsedHeightPx = with(density) { collapsedHeight.toPx() }
                val currentHeightPx = maxHeightPx - heightOffsetPx.coerceIn(0f, maxHeightOffsetPx)
                with(density) { currentHeightPx.toDp() }
            } ?: collapsedHeight
        }
        height
    }

    // Check if user is scrolling content below after top bar reached collapsed height
    val isScrollingContentBelow by remember(effectiveScrollBehavior, contentNeedsScrolling, hasMerchantImage, isContentScrollingBehind) {
        derivedStateOf {
            // If content doesn't need scrolling, never consider content as scrolling below
            if (!contentNeedsScrolling && !isContentScrollingBehind) {
                false
            } else if (!hasMerchantImage) {
                // When no merchant image, use the external indicator for blur
                isContentScrollingBehind
            } else {
                effectiveScrollBehavior?.isScrollingContentBelow ?: false
            }
        }
    }

    // Calculate merchant image offset (moves up as we scroll)
    val merchantImageOffset = remember(merchantImageOffsetPx) {
        with(density) { (-merchantImageOffsetPx).toDp() }
    }

    // Calculate title bar offset (moves up as we scroll)
    val titleBarOffset = remember(titleBarOffsetPx) {
        with(density) { (-titleBarOffsetPx).toDp() }
    }

    // Handle status bar appearance
    LaunchedEffect(config.statusBarTheme) {
        val window = (context as? Activity)?.window ?: return@LaunchedEffect
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        when (config.statusBarTheme) {
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
                // Keep current appearance
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(currentHeight)
            .background(Color.Transparent) // Ensure transparent background
    ) {
        // Merchant visual element background (if present) - extends to status bar
        // Moves up and fades out as we scroll
        // Completely hide when user scrolls content below after top bar reached collapsed height
        if (!isScrollingContentBelow) {
            config.merchantImage?.let { visualElement ->
                val merchantModifier = Modifier
                    .fillMaxWidth()
                    .height(totalHeight ?: 0.dp)
                    .offset(y = merchantImageOffset)
                    .alpha(merchantImageAlpha)
                    .then(
                        if (config.onMerchantImageClick != null) {
                            Modifier.clickable(onClick = config.onMerchantImageClick)
                        } else {
                            Modifier
                        }
                    )
                
                PopVisualElement(
                    visualElement = visualElement,
                    modifier = merchantModifier,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Merchant background"
                )
            }
        }


        // Linear gradient overlay with blur when collapsed
        // Stage 3: Title bar hides, background becomes blur with gradient
        // Stage 4: Content scrolls with solid gradient background
        // Show gradient when blur is active or when content is scrolling behind
        val shouldShowBlur = animatedBlurIntensity > 0f
        // Use the derived state value that respects contentNeedsScrolling

        // Show gradient when:
        // 1. Blur is active (stage 3+) OR
        // 2. Content is scrolling behind (stage 4)
        if (shouldShowBlur || isScrollingContentBelow) {
            // Calculate gradient opacity based on blur intensity and scroll state
            val gradientOpacity = when {
                isScrollingContentBelow -> {
                    // When content is scrolling behind, show a more visible gradient
                    // Start at 0.7 (70%) and increase with blur intensity
                    0.7f + (animatedBlurIntensity * 0.2f) // 70% to 90% opacity
                }
                shouldShowBlur -> {
                    // When blur is active but content not yet behind, more visible gradient
                    0.5f + (animatedBlurIntensity * 0.3f) // 50% to 80% opacity
                }
                else -> 0.3f // Base gradient
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(currentHeight)
            )
        }

        // Layout structure differs based on whether merchant image exists
        if (hasMerchantImage) {
            // With merchant image: original layout with status bar, app bar, and title bar at bottom
            // Reserve space for app bar (which is now at activity level)
            // App bar height: 48dp (8dp padding top + 24dp icon + 8dp padding bottom + 8dp padding)

            // Reserve space for app bar (48dp) - app bar is rendered at activity level
            Spacer(modifier = Modifier.height(48.dp))

            // Title bar - moves up and fades out as scroll progresses
            // Stage 3: Title bar should be fully hidden when blur starts
            // Stage 4: Title bar remains hidden when content scrolls
            // Completely hide when user scrolls content below after top bar reached collapsed height
            if (!isScrollingContentBelow) {
                config.titleBarConfig?.let { titleConfig ->
                    // Use isTitleBarVisible to determine if title bar should be shown
                    if (isTitleBarVisible) {
                        PopTitleBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .offset(y = titleBarOffset)
                                .alpha(animatedTitleBarAlpha),
                            config = titleConfig,
                            rightSlot = centerRightSlot
                        )
                    }
                }
            }

            // Thin horizontal divider at the bottom of the top bar
            // Always visible (both when expanded and collapsed) unless showDivider is false
            if (config.showDivider) {
                PopDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    color = PopColor.Grey.Grey900
                )
            }
        } else {
            // Without merchant image: Column layout with status bar, app bar space, title bar, and divider
            // Use Box + Column to ensure proper background fill and divider positioning
            // When content is scrolling behind (blur is active), use transparent background so blur shows through
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Status bar spacer (44dp height) - only when no merchant image
                    Spacer(modifier = Modifier.height(44.dp))

                    // Reduced space for app bar (28dp instead of 48dp) - app bar is rendered at activity level
                    // This gives the same visual result as the previous 48dp + (-20dp offset) approach
                    // but without the layout inconsistencies caused by negative offset
                    Spacer(modifier = Modifier.height(28.dp))

                    // Title bar - always visible when no merchant image (no collapse behavior)
                    config.titleBarConfig?.let { titleConfig ->
                        PopTitleBar(
                            modifier = Modifier.fillMaxWidth(),
                            config = titleConfig,
                            rightSlot = centerRightSlot
                        )
                    }
                }

                // Thin horizontal divider at the bottom of the top bar
                if (config.showDivider) {
                    PopDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        color = PopColor.Grey.Grey900
                    )
                }
            }
        }
    }
}
