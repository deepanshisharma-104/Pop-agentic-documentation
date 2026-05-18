package com.pop.components.ds_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.pop.components.models.AppBarIconSlots
import com.pop.components.models.PopAppBarConfig
import com.pop.components.theme.IconName

/**
 * CompositionLocal to access the PopAppBarManager from anywhere in the composition tree
 */
val LocalPopAppBarManager = compositionLocalOf<PopAppBarManager?> { null }

/**
 * CompositionLocal to provide the PopAppBar height for sticky components
 * This allows components to stick below the app bar correctly
 */
val LocalPopAppBarHeight = compositionLocalOf<Dp> { 56.dp } // Default app bar height (without status bar)
val GlobalPopAppBarContentHeight: Dp = 56.dp

/**
 * Manager for the global PopAppBar that persists at activity level
 * Handles app bar state and provides functions to update it with animations
 */
class PopAppBarManager {
    // Use mutableStateOf so Compose can observe changes
    // Initialize with default config so app bar is visible by default
    var config: PopAppBarConfig by mutableStateOf(PopAppBarConfig.default())
        private set
    
    // App bar visibility control
    var isAppBarVisible: Boolean by mutableStateOf(true)
        private set
    
    // Right slot composable content
    private var _rightSlot: (@Composable () -> Unit)? by mutableStateOf(null)
    // Left slot composable content
    private var _leftSlot: (@Composable () -> Unit)? by mutableStateOf(null)
    private var iconSlotsOwnerKey: Any? = null
    private var rightSlotOwnerKey: Any? = null
    private var leftSlotOwnerKey: Any? = null
    private var navigationOwnerKey: Any? = null
    private var scrollScrimOwnerKey: Any? = null
    var isScrollScrimVisible: Boolean by mutableStateOf(false)
        private set
    
    // ============================================
    // SEPARATE UPDATE METHODS (Recommended)
    // These preserve other parts of the config
    // ============================================
    
    /**
     * Update ONLY the navigation icon configuration.
     * Preserves existing icon slots and right slot.
     * 
     * @param navigationIcon Icon to display (null to hide)
     * @param onNavigationClick Callback when icon is clicked
     * @param showNavigationIcon Whether to show the icon
     * @param navigationIconTint Optional tint color for navigation icon (null uses default contentColor)
     * @param ownerKey Optional key to identify the owner of this navigation config (for ownership-based updates)
     */
    fun updateNavigation(
        navigationIcon: IconName? = config.navigationIcon,
        onNavigationClick: (() -> Unit)? = config.onNavigationClick,
        showNavigationIcon: Boolean = config.showNavigationIcon,
        navigationIconTint: Color? = config.navigationIconTint,
        ownerKey: Any? = null
    ) {
        config = config.copy(
            navigationIcon = navigationIcon,
            onNavigationClick = onNavigationClick,
            showNavigationIcon = showNavigationIcon,
            navigationIconTint = navigationIconTint
        )
        if (ownerKey != null) {
            navigationOwnerKey = ownerKey
        }
    }
    
    /**
     * Check if the given owner key matches the current navigation owner.
     * Used to determine if a screen should be allowed to update navigation.
     * 
     * @param ownerKey The key to check against the current owner
     * @return true if the key matches or if there's no current owner
     */
    fun isNavigationOwner(ownerKey: Any?): Boolean {
        return ownerKey == null || navigationOwnerKey == null || ownerKey == navigationOwnerKey
    }
    
    /**
     * Clear navigation configuration only if the caller is the current owner.
     * Prevents disposed screens from clearing navigation set by newly visible screens.
     * 
     * @param ownerKey The key of the caller attempting to clear navigation
     */
    fun clearNavigationIfOwner(ownerKey: Any?) {
        if (ownerKey == null || ownerKey == navigationOwnerKey) {
            // Only clear if we're the owner - don't reset navigation entirely,
            // just clear the owner so the next screen can take over
            navigationOwnerKey = null
        }
    }
    
    /**
     * Update ONLY the icon slots (right side icons).
     * Preserves existing navigation config and right slot.
     * 
     * @param iconSlots The new icon slots configuration
     */
    fun updateIconSlots(iconSlots: AppBarIconSlots, ownerKey: Any? = null) {
        config = config.copy(iconSlots = iconSlots)
        iconSlotsOwnerKey = ownerKey
    }
    
    /**
     * Clear ONLY the icon slots.
     * Preserves existing navigation config.
     */
    fun clearIconSlots(ownerKey: Any? = null) {
        if (ownerKey == null || ownerKey == iconSlotsOwnerKey) {
            config = config.copy(iconSlots = AppBarIconSlots())
            iconSlotsOwnerKey = null
            // Fixed: Removed navigationIcon = null and showNavigationIcon = false
            // Navigation config should be preserved
        }
    }
    
    /**
     * Update ONLY the right slot (custom composable).
     * Preserves existing navigation config and icon slots.
     * 
     * @param rightSlot The composable to display, or null to clear
     */
    fun updateRightSlot(ownerKey: Any? = null, rightSlot: (@Composable () -> Unit)?) {
        _rightSlot = rightSlot
        rightSlotOwnerKey = ownerKey
    }
    
    /**
     * Clear ONLY the right slot.
     * Preserves navigation config and icon slots.
     */
    fun clearRightSlot(ownerKey: Any? = null) {
        if (ownerKey == null || ownerKey == rightSlotOwnerKey) {
            _rightSlot = null
            rightSlotOwnerKey = null
        }
    }
    
    /**
     * Update ONLY the left slot (custom composable).
     * Preserves existing navigation config, icon slots, and right slot.
     * 
     * @param leftSlot The composable to display, or null to clear
     */
    fun updateLeftSlot(ownerKey: Any? = null, leftSlot: (@Composable () -> Unit)?) {
        _leftSlot = leftSlot
        leftSlotOwnerKey = ownerKey
    }
    
    /**
     * Clear ONLY the left slot.
     * Preserves navigation config, icon slots, and right slot.
     */
    fun clearLeftSlot(ownerKey: Any? = null) {
        if (ownerKey == null || ownerKey == leftSlotOwnerKey) {
            _leftSlot = null
            leftSlotOwnerKey = null
        }
    }

    /**
     * Update app bar scroll scrim visibility.
     * Used by app-bar-only screens to indicate content has started scrolling.
     */
    fun updateScrollScrimVisibility(isVisible: Boolean, ownerKey: Any? = null) {
        if (ownerKey == null || scrollScrimOwnerKey == null || ownerKey == scrollScrimOwnerKey) {
            isScrollScrimVisible = isVisible
            if (ownerKey != null) {
                scrollScrimOwnerKey = ownerKey
            }
        }
    }

    /**
     * Clear scroll scrim only if caller owns it.
     */
    fun clearScrollScrimIfOwner(ownerKey: Any? = null) {
        if (ownerKey == null || ownerKey == scrollScrimOwnerKey) {
            isScrollScrimVisible = false
            scrollScrimOwnerKey = null
        }
    }
    
    // ============================================
    // FULL UPDATE METHOD (Use sparingly)
    // This replaces the entire config
    // ============================================
    
    /**
     * Update the ENTIRE app bar configuration.
     * ⚠️ This replaces all config including navigation.
     * Prefer using updateNavigation(), updateIconSlots(), etc.
     * 
     * @param newConfig The new app bar configuration
     * @param rightSlot Optional composable slot for custom content on the right
     * @param leftSlot Optional composable slot for custom content on the left
     */
    fun updateAppBar(
        newConfig: PopAppBarConfig,
        rightSlot: (@Composable () -> Unit)? = null,
        leftSlot: (@Composable () -> Unit)? = null
    ) {
        config = newConfig
        _rightSlot = rightSlot
        _leftSlot = leftSlot
        isAppBarVisible = true // Auto-show app bar when updating
    }
    
    // ============================================
    // VISIBILITY CONTROL
    // ============================================
    
    /**
     * Hide the entire app bar.
     * Use for full-screen experiences (video player, image viewer, etc.)
     */
    fun hideAppBar() {
        isAppBarVisible = false
    }
    
    /**
     * Show the app bar.
     */
    fun showAppBar() {
        isAppBarVisible = true
    }
    
    /**
     * Toggle app bar visibility.
     */
    fun toggleAppBar() {
        isAppBarVisible = !isAppBarVisible
    }
    
    // ============================================
    // NAVIGATION ICON VISIBILITY SHORTCUTS
    // ============================================
    
    /**
     * Hide navigation icon only.
     * Updates current config with showNavigationIcon = false.
     */
    fun hideNavigationIcon() {
        config = config.copy(showNavigationIcon = false)
    }
    
    /**
     * Show navigation icon only.
     * Updates current config with showNavigationIcon = true.
     */
    fun showNavigationIcon() {
        config = config.copy(showNavigationIcon = true)
    }
    
    // ============================================
    // GETTERS
    // ============================================
    
    /**
     * Get current app bar configuration.
     */
    fun getCurrentConfig(): PopAppBarConfig = config
    
    /**
     * Get the right slot composable.
     */
    @Composable
    fun getRightSlot(): (@Composable () -> Unit)? = _rightSlot
    
    /**
     * Get the left slot composable.
     */
    @Composable
    fun getLeftSlot(): (@Composable () -> Unit)? = _leftSlot
}

/**
 * Global PopAppBar composable that should be placed at activity level
 * This bar persists across screen navigation and only its content changes with dissolve animation
 * 
 * @param manager The PopAppBarManager instance
 * @param modifier Modifier for the app bar container
 */
@Composable
fun GlobalPopAppBar(
    manager: PopAppBarManager,
    modifier: Modifier = Modifier
) {
    // Observe the manager's config state and visibility
    val config = manager.config
    val isVisible = manager.isAppBarVisible
    val rightSlot = manager.getRightSlot()
    val leftSlot = manager.getLeftSlot()
    val isScrollScrimVisible = manager.isScrollScrimVisible

    val scrimProgress by animateFloatAsState(
        targetValue = if (isScrollScrimVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 520),
        label = "AppBarScrollScrimProgress"
    )
    // Slow-start alpha curve so the scrim builds up more gradually.
    val scrimAlpha = scrimProgress * scrimProgress
    val hasAppBarBackground = isScrollScrimVisible || config.backgroundColor != Color.Transparent
    
    // Animate entire app bar visibility (for rare full-screen cases)
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(tween(200)) { -it } +
                fadeIn(tween(200)),
        exit = slideOutVertically(tween(200)) { -it } +
               fadeOut(tween(200)),
        modifier = modifier
    ) {
        // Position absolutely at the top with high z-index
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(1000f) // High z-index to ensure it's above PopTopBar and other content
        ) {
            // Block click-through to underlying content in app bar region.
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = {}
                    )
            )
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .alpha(scrimAlpha)
            ) {
                Image(
                    painter = painterResource(id = config.scrollScrimType.drawableResId()),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.matchParentSize()
                )
            }

            // Static app bar - no animation on config changes
            // Icons animate individually via AnimatedVisibility in PopAppBar
            PopAppBar(
                config = config.copy(backgroundColor = Color.Transparent),
                rightSlot = rightSlot,
                leftSlot = leftSlot,
                modifier = Modifier.fillMaxWidth().align(Alignment.Center)
            )

            if (config.showDivider && hasAppBarBackground) {
                val dividerAlpha = if (isScrollScrimVisible) scrimAlpha else 1f
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .alpha(dividerAlpha)
                        .background(Color(0xFF262626))
                )
            }
        }
    }
}

/**
 * Composable to provide PopAppBarManager to the composition tree
 * Should be called at activity level
 * 
 * @param manager The PopAppBarManager instance
 * @param appBarHeight The height of the app bar (default: 48.dp, app bar only; use 92.dp if including status bar)
 * @param content The content to provide the manager to
 */
@Composable
fun ProvidePopAppBarManager(
    manager: PopAppBarManager,
    appBarHeight: Dp = 56.dp,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalPopAppBarManager provides manager,
        LocalPopAppBarHeight provides appBarHeight
    ) {
        content()
    }
}

/**
 * Extension function to get PopAppBarManager from composition local
 * Use this in fragments/screens to update the app bar
 * 
 * Example:
 * ```kotlin
 * val appBarManager = getPopAppBarManager()
 * appBarManager?.updateAppBar(
 *     PopAppBarConfig(
 *         title = "New Screen",
 *         onNavigationClick = { navigateBack() }
 *     )
 * )
 * ```
 */
@Composable
fun getPopAppBarManager(): PopAppBarManager? {
    return LocalPopAppBarManager.current
}

@Composable
fun statusBarInsetHeight(): Dp {
    val inset = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding()
    return if (inset < 44.dp) 44.dp else inset
}
