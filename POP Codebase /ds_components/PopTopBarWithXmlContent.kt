package com.pop.components.ds_components

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.alpha
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.WindowCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.pop.components.compose_components.PopDivider
import com.pop.components.ds_components.scrim.ExperimentalScrimApi
import com.pop.components.ds_components.scrim.blurModifier
import com.pop.components.ds_components.scrim.rememberScrimState
import com.pop.components.ds_components.scrim.scrimSource
import com.pop.components.models.MerchantImageSize
import com.pop.components.models.PopTopBarConfig
import com.pop.components.models.StatusBarTheme
import com.pop.components.models.isValid
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopTheme
import com.pop.components.theme.SurfaceColor
import kotlin.math.abs

/**
 * A composable that wraps XML content with PopTopBar using CoordinatorLayout.
 * This provides perfectly smooth, hardware-accelerated collapsing behavior
 * using Android's native Material Design components.
 *
 * @param T The ViewBinding type for your XML layout
 * @param modifier Modifier for the root container
 * @param topBarConfig Configuration for the PopTopBar
 * @param maxScrollDistance Optional custom max scroll distance
 * @param centerRightSlot Optional composable slot for title bar center-right position
 * @param scrollBehaviorKey Optional key to reset scroll behavior when changed
 * @param bottomFixedContent Optional fixed content at the bottom
 * @param scrollController Optional controller for programmatic scroll control
 * @param bindingInflater Function to inflate the binding
 * @param onBindingReady Callback when binding is ready
 */
@OptIn(ExperimentalScrimApi::class)
@Composable
fun <T : ViewBinding> PopTopBarWithXmlContent(
    modifier: Modifier = Modifier,
    topBarConfig: PopTopBarConfig,
    maxScrollDistance: Dp? = null,
    centerRightSlot: (@Composable () -> Unit)? = null,
    scrollBehaviorKey: Any? = null,
    bottomFixedContent: (@Composable () -> Unit)? = null,
    scrollController: PopTopBarScrollController? = null,
    bindingInflater: (LayoutInflater) -> T,
    onBindingReady: (T) -> Unit = {}
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val scrimState = rememberScrimState()
    val hasMerchantImage = topBarConfig.merchantImage != null && topBarConfig.merchantImage.isValid()
    
    // Track collapse progress (0f = expanded, 1f = collapsed)
    var collapseProgress by remember { mutableFloatStateOf(0f) }

    // Calculate heights
    val statusBarHeight = statusBarInsetHeight()
    val appBarHeight = LocalPopAppBarHeight.current
    val collapsedHeight = statusBarHeight + appBarHeight
    
    val expandedHeight = remember(
        topBarConfig.merchantImageSize,
        maxScrollDistance,
        topBarConfig.useAspectRatioBasedHeight,
        configuration.screenWidthDp,
        hasMerchantImage
    ) {
        if (!hasMerchantImage) {
            collapsedHeight + 40.dp
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

    // Configure status bar
    LaunchedEffect(topBarConfig.statusBarTheme) {
        val window = (context as? Activity)?.window ?: return@LaunchedEffect
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        when (topBarConfig.statusBarTheme) {
            StatusBarTheme.Light -> {
                window.statusBarColor = android.graphics.Color.TRANSPARENT
                windowInsetsController.isAppearanceLightStatusBars = true
            }
            StatusBarTheme.Dark -> {
                window.statusBarColor = android.graphics.Color.TRANSPARENT
                windowInsetsController.isAppearanceLightStatusBars = false
            }
            StatusBarTheme.Transparent -> {
                window.statusBarColor = android.graphics.Color.TRANSPARENT
            }
        }
    }

    var bottomFixedContentHeightPx by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // CoordinatorLayout with native collapsing toolbar
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                val coordinator = CoordinatorLayout(ctx)
                coordinator.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                coordinator.fitsSystemWindows = false

                // AppBarLayout (collapsible top bar)
                val appBarLayout = AppBarLayout(ctx).apply {
                    layoutParams = CoordinatorLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        with(density) { expandedHeight.toPx().toInt() }
                    )
                    fitsSystemWindows = false
                    
                    // Track collapse progress
                    addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBar, verticalOffset ->
                        val maxScrollRange = appBar.totalScrollRange.toFloat()
                        if (maxScrollRange > 0f) {
                            collapseProgress = abs(verticalOffset) / maxScrollRange
                        }
                    })
                }

                if (hasMerchantImage) {
                    // CollapsingToolbarLayout for merchant image
                    val collapsingToolbar = CollapsingToolbarLayout(ctx).apply {
                        layoutParams = AppBarLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        ).apply {
                            scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                                         AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                        }
                        setCollapsedTitleTextColor(android.graphics.Color.TRANSPARENT)
                        setExpandedTitleColor(android.graphics.Color.TRANSPARENT)
                        
                        // Set scrim color for collapsed state
                        setContentScrimColor(android.graphics.Color.parseColor("#121212"))
                        scrimAnimationDuration = 300
                        scrimVisibleHeightTrigger = with(density) { (collapsedHeight.toPx() * 1.2f).toInt() }
                    }

                    // Merchant image using ComposeView
                    val merchantImageView = ComposeView(ctx).apply {
                        layoutParams = CollapsingToolbarLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        ).apply {
                            collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                            parallaxMultiplier = 0.25f
                        }
                        setContent {
                            PopTheme {
                                topBarConfig.merchantImage?.let { visualElement ->
                                    val merchantModifier = if (topBarConfig.onMerchantImageClick != null) {
                                        Modifier
                                            .fillMaxSize()
                                            .clickable { topBarConfig.onMerchantImageClick.invoke() }
                                    } else {
                                        Modifier.fillMaxSize()
                                    }
                                    
                                    PopVisualElement(
                                        visualElement = visualElement,
                                        modifier = merchantModifier,
                                        contentScale = ContentScale.Crop,
                                        contentDescription = "Merchant background"
                                    )
                                }
                            }
                        }
                    }
                    collapsingToolbar.addView(merchantImageView)

                    // App bar with Compose (pinned at top when collapsed)
                    val appBarComposeView = ComposeView(ctx).apply {
                        layoutParams = CollapsingToolbarLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            with(density) { collapsedHeight.toPx().toInt() }
                        ).apply {
                            collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                        }
                        setContent {
                            PopTheme {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = statusBarHeight)
                                ) {
                                    // Title bar at bottom with optional center right slot
                                    topBarConfig.titleBarConfig?.let { titleConfig ->
                                        PopTitleBar(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .align(Alignment.BottomCenter),
                                            config = titleConfig,
                                            rightSlot = centerRightSlot
                                        )
                                    }
                                }
                            }
                        }
                    }
                    collapsingToolbar.addView(appBarComposeView)
                    appBarLayout.addView(collapsingToolbar)
                    
                    // Divider at bottom of AppBarLayout (always at the bottom)
                    if (topBarConfig.showDivider) {
                        val dividerView = ComposeView(ctx).apply {
                            layoutParams = AppBarLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            setContent {
                                PopTheme {
                                    PopDivider(
                                        modifier = Modifier.fillMaxWidth(),
                                        color = PopColor.Grey.Grey900
                                    )
                                }
                            }
                        }
                        appBarLayout.addView(dividerView)
                    }
                } else {
                    // No merchant image - simple app bar
                    val appBarComposeView = ComposeView(ctx).apply {
                        layoutParams = AppBarLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        ).apply {
                            scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or
                                         AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                        }
                        setContent {
                            PopTheme {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(top = statusBarHeight + 24.dp)
                                ) {
                                    topBarConfig.titleBarConfig?.let { titleConfig ->
                                        PopTitleBar(
                                            modifier = Modifier.fillMaxWidth(),
                                            config = titleConfig,
                                            rightSlot = centerRightSlot
                                        )
                                    }
                                }
                            }
                        }
                    }
                    appBarLayout.addView(appBarComposeView)
                }

                coordinator.addView(appBarLayout)

                // XML content with scrolling behavior
                val contentContainer = FrameLayout(ctx).apply {
                    layoutParams = CoordinatorLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    ).apply {
                        // Apply AppBarLayout scrolling behavior
                        behavior = AppBarLayout.ScrollingViewBehavior()
                    }
                    
                    // Add bottom padding for fixed content if needed
                    if (bottomFixedContentHeightPx > 0f) {
                        setPadding(0, 0, 0, bottomFixedContentHeightPx.toInt())
                    }
                }

                // Inflate XML binding and add to container
                val binding = bindingInflater(LayoutInflater.from(ctx))
                binding.root.layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                contentContainer.addView(binding.root)
                coordinator.addView(contentContainer)

                onBindingReady(binding)
                coordinator
            },
            update = { coordinator ->
                // Update bottom padding if fixed content height changes
                val contentContainer = coordinator.getChildAt(1) as? FrameLayout
                contentContainer?.setPadding(0, 0, 0, bottomFixedContentHeightPx.toInt())
            }
        )

        // Blur overlay when content scrolls below collapsed top bar
        if (hasMerchantImage) {
            // Early gradient: builds from the very start of the collapse so the merchant image
            // darkens gradually — mirrors how PopAppBarManager builds its scrim with p².
            val earlyGradientAlpha by remember {
                derivedStateOf {
                    val p = collapseProgress.coerceIn(0f, 1f)
                    (p * p * 0.55f).coerceIn(0f, 0.55f)
                }
            }

            // Fine-grained blur ramp: start at 50 % collapse so there is a longer window for
            // the gradient to build up before the full-screen blur kicks in.
            val blurIntensity by remember {
                derivedStateOf {
                    if (collapseProgress >= 0.5f) {
                        ((collapseProgress - 0.5f) / 0.5f).coerceIn(0f, 1f)
                    } else 0f
                }
            }
            
            val isScrollingContentBelow by remember {
                derivedStateOf { collapseProgress >= 1f }
            }

            // Early gradient overlay — visible from the first scroll event, fades in slowly.
            if (collapseProgress > 0.02f && !isScrollingContentBelow) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(collapsedHeight)
                        .align(Alignment.TopCenter)
                        .zIndex(149f)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = earlyGradientAlpha * 0.55f),
                                    Color.Black.copy(alpha = earlyGradientAlpha)
                                )
                            )
                        )
                )
            }

            // Scrim / blur overlay — uses squared blurIntensity so it builds up gradually
            // (slow start → accelerates) rather than jumping to half-opacity on first frame.
            if (blurIntensity > 0f || isScrollingContentBelow) {
                val scrimAlpha = blurIntensity * blurIntensity
                val gradientOpacity = when {
                    isScrollingContentBelow -> 0.9f
                    else -> (scrimAlpha * 0.85f).coerceIn(0f, 0.85f)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(collapsedHeight)
                        .align(Alignment.TopCenter)
                        .zIndex(150f)
                        .then(
                            if (isScrollingContentBelow) {
                                Modifier.blurModifier(
                                    scrimState = scrimState,
                                    blurRadius = 60.dp,
                                    backgroundColor = SurfaceColor.Primary
                                )
                            } else {
                                Modifier
                                    .alpha(scrimAlpha)
                                    .background(
                                        Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Black.copy(alpha = gradientOpacity.coerceIn(0f, 0.95f)),
                                                Color.Black.copy(alpha = (gradientOpacity * 0.95f).coerceIn(0f, 0.95f))
                                            )
                                        )
                                    )
                            }
                        )
                )
            }
        }

        // Content area for scrim source
        Box(
            modifier = Modifier
                .fillMaxSize()
                .scrimSource(state = scrimState, zIndex = 200f)
        )

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
