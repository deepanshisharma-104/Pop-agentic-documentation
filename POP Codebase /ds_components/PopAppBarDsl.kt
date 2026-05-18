package com.pop.components.ds_components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pop.components.models.AppBarIconSlot
import com.pop.components.models.AppBarIconSlots
import com.pop.components.models.IconAnimationType
import com.pop.components.theme.IconName

/**
 * DSL helper for configuring complete app bar (navigation + icon slots) with minimal boilerplate.
 * 
 * Use this when you need to configure BOTH navigation icon callback AND icon slots.
 * 
 * **Lifecycle Awareness:**
 * This composable only updates the app bar when the screen's lifecycle is at least STARTED.
 * This prevents disposed screens from overwriting navigation set by newly visible screens
 * during navigation transitions (e.g., pop animations).
 * 
 * **Usage Example:**
 * ```kotlin
 * @Composable
 * fun MyScreen(onBack: () -> Unit) {
 *     ConfigureAppBar(
 *         navigationIcon = Icons.ChevronLeft,
 *         onNavigationClick = onBack,
 *         showNavigationIcon = true
 *     ) {
 *         slot1(Icons.Share05) { shareContent() }
 *         slot2(Icons.Filter) { showFilters() }
 *     }
 * }
 * 
 * // For home screen (no navigation icon):
 * @Composable
 * fun HomeScreen() {
 *     ConfigureAppBar(
 *         showNavigationIcon = false  // Hide navigation icon
 *     ) {
 *         slot1(Icons.Search) { search() }
 *     }
 * }
 * ```
 * 
 * @param navigationIcon Icon to display on the left (default: null)
 * @param onNavigationClick Callback when navigation icon is clicked
 * @param showNavigationIcon Whether to show the navigation icon (default: false)
 * @param navigationIconTint Optional tint color for navigation icon (null uses default contentColor)
 * @param key Optional key to trigger reconfiguration when changed
 * @param block DSL block for configuring icon slots (optional)
 */
@Composable
fun ConfigureAppBar(
    navigationIcon: IconName? = null,
    onNavigationClick: (() -> Unit)? = null,
    showNavigationIcon: Boolean = false,
    navigationIconTint: Color? = null,
    key: Any? = Unit,
    block: (AppBarIconSlotsBuilder.() -> Unit)? = null
) {
    val appBarManager = getPopAppBarManager()
    val ownerKey = remember { Any() }
    val lifecycleOwner = LocalLifecycleOwner.current

    fun applyConfigIfActive() {
        // Only update navigation if lifecycle is at least STARTED
        // This prevents screens being disposed during pop animations from
        // overwriting navigation set by newly visible screens (e.g., Home)
        val isLifecycleActive = lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        if (isLifecycleActive) {
            // Update navigation with owner key for ownership tracking
            appBarManager?.updateNavigation(
                navigationIcon = navigationIcon,
                onNavigationClick = onNavigationClick,
                showNavigationIcon = showNavigationIcon,
                navigationIconTint = navigationIconTint,
                ownerKey = ownerKey
            )
        }

        // Update icon slots if provided (also only when lifecycle is active)
        if (block != null && isLifecycleActive) {
            val builder = AppBarIconSlotsBuilder()
            builder.block()
            appBarManager?.updateIconSlots(builder.build(), ownerKey = ownerKey)
        }
    }

    LaunchedEffect(key) {
        applyConfigIfActive()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> applyConfigIfActive()
                Lifecycle.Event.ON_STOP -> {
                    appBarManager?.clearIconSlots(ownerKey = ownerKey)
                    appBarManager?.clearNavigationIfOwner(ownerKey = ownerKey)
                }
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            appBarManager?.clearIconSlots(ownerKey = ownerKey)
            // Clear navigation ownership only if we're still the owner
            // This prevents disposed screens from affecting navigation set by other screens
            appBarManager?.clearNavigationIfOwner(ownerKey = ownerKey)
        }
    }
}

/**
 * DSL helper for configuring app bar icon slots with minimal boilerplate.
 * 
 * Use this when you ONLY need to configure icon slots (not navigation).
 * 
 * This composable provides a clean, declarative API for setting up app bar icons
 * without requiring developers to manually construct PopAppBarConfig and IconSlots.
 * 
 * **Features:**
 * - Automatic cleanup on disposal
 * - Type-safe icon configuration
 * - Default dissolve animation
 * - Optional key for re-triggering updates
 * - Lifecycle awareness to prevent race conditions during navigation
 * 
 * **Usage Example:**
 * ```kotlin
 * @Composable
 * fun MyScreen() {
 *     ConfigureAppBarIcons {
 *         slot1(Icons.Share05) { shareContent() }
 *         slot2(Icons.Filter) { showFilters() }
 *         slot3(Icons.Check) { confirmSelection() }
 *     }
 *     
 *     // Rest of your screen UI
 * }
 * ```
 * 
 * @param key Optional key to trigger reconfiguration when changed
 * @param block DSL block for configuring icon slots
 */
@Composable
fun ConfigureAppBarIcons(
    key: Any? = Unit,
    block: AppBarIconSlotsBuilder.() -> Unit
) {
    val appBarManager = getPopAppBarManager()
    val ownerKey = remember { Any() }
    val lifecycleOwner = LocalLifecycleOwner.current

    fun applyIconSlotsIfActive() {
        // Only update if lifecycle is at least STARTED
        val isLifecycleActive = lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)

        if (isLifecycleActive) {
            val builder = AppBarIconSlotsBuilder()
            builder.block()
            // Use updateIconSlots - preserves navigation config!
            appBarManager?.updateIconSlots(builder.build(), ownerKey = ownerKey)
        }
    }

    LaunchedEffect(key) {
        applyIconSlotsIfActive()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> applyIconSlotsIfActive()
                Lifecycle.Event.ON_STOP -> {
                    appBarManager?.clearIconSlots(ownerKey = ownerKey)
                }
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            appBarManager?.clearIconSlots(ownerKey = ownerKey)
        }
    }
}

/**
 * DSL helper for configuring app bar right slot (custom composable) with minimal boilerplate.
 * 
 * Use this when you need to add a custom composable to the right side of the app bar
 * (after the icon slots). This is useful for custom buttons, badges, or any other
 * composable content that doesn't fit into the standard icon slots.
 * 
 * **Features:**
 * - Automatic cleanup on disposal
 * - Optional key for re-triggering updates
 * - Preserves existing navigation config and icon slots
 * - Lifecycle awareness to prevent race conditions during navigation
 * 
 * **Usage Example:**
 * ```kotlin
 * @Composable
 * fun MyScreen() {
 *     ConfigureAppBarRightSlot {
 *         Text(
 *             text = "Custom",
 *             modifier = Modifier.padding(8.dp),
 *             color = TextColors.Primary.Default
 *         )
 *     }
 *     
 *     // Or with a button:
 *     ConfigureAppBarRightSlot {
 *         Button(onClick = { /* ... */ }) {
 *             Text("Custom Action")
 *         }
 *     }
 *     
 *     // Rest of your screen UI
 * }
 * ```
 * 
 * @param key Optional key to trigger reconfiguration when changed
 * @param content The composable content to display in the right slot
 */
@Composable
fun ConfigureAppBarRightSlot(
    key: Any? = Unit,
    content: @Composable () -> Unit
) {
    val appBarManager = getPopAppBarManager()
    val ownerKey = remember { Any() }
    val lifecycleOwner = LocalLifecycleOwner.current

    fun applyRightSlotIfActive() {
        // Only update if lifecycle is at least STARTED
        val isLifecycleActive = lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)

        if (isLifecycleActive) {
            appBarManager?.updateRightSlot(ownerKey = ownerKey, rightSlot = content)
        }
    }

    LaunchedEffect(key) {
        applyRightSlotIfActive()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> applyRightSlotIfActive()
                Lifecycle.Event.ON_STOP -> {
                    appBarManager?.clearRightSlot(ownerKey = ownerKey)
                }
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            appBarManager?.clearRightSlot(ownerKey = ownerKey)
        }
    }
}

/**
 * DSL helper for configuring app bar left slot (custom composable) with minimal boilerplate.
 * 
 * Use this when you need to add a custom composable to the left side of the app bar
 * (before the navigation icon). This is useful for custom logos, avatars, or any other
 * composable content that should appear at the leftmost position.
 * 
 * **Features:**
 * - Automatic cleanup on disposal
 * - Optional key for re-triggering updates
 * - Preserves existing navigation config, icon slots, and right slot
 * - Lifecycle awareness to prevent race conditions during navigation
 * 
 * **Usage Example:**
 * ```kotlin
 * @Composable
 * fun MyScreen() {
 *     ConfigureAppBarLeftSlot {
 *         Image(
 *             painter = painterResource(id = R.drawable.logo),
 *             contentDescription = "Logo",
 *             modifier = Modifier.size(32.dp)
 *         )
 *     }
 *     
 *     // Or with a custom avatar:
 *     ConfigureAppBarLeftSlot {
 *         PopAvatar(
 *             imageUrl = user.avatarUrl,
 *             size = 32.dp
 *         )
 *     }
 *     
 *     // Rest of your screen UI
 * }
 * ```
 * 
 * @param key Optional key to trigger reconfiguration when changed
 * @param content The composable content to display in the left slot
 */
@Composable
fun ConfigureAppBarLeftSlot(
    key: Any? = Unit,
    content: @Composable () -> Unit
) {
    val appBarManager = getPopAppBarManager()
    val ownerKey = remember { Any() }
    val lifecycleOwner = LocalLifecycleOwner.current

    fun applyLeftSlotIfActive() {
        // Only update if lifecycle is at least STARTED
        val isLifecycleActive = lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)

        if (isLifecycleActive) {
            appBarManager?.updateLeftSlot(ownerKey = ownerKey, leftSlot = content)
        }
    }

    LaunchedEffect(key) {
        applyLeftSlotIfActive()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> applyLeftSlotIfActive()
                Lifecycle.Event.ON_STOP -> {
                    appBarManager?.clearLeftSlot(ownerKey = ownerKey)
                }
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            appBarManager?.clearLeftSlot(ownerKey = ownerKey)
        }
    }
}

/**
 * DSL Builder for app bar icon slots.
 * Provides a clean, type-safe API for configuring 1-4 icon slots.
 */
class AppBarIconSlotsBuilder {
    private var slot1: AppBarIconSlot? = null
    private var slot2: AppBarIconSlot? = null
    private var slot3: AppBarIconSlot? = null
    private var slot4: AppBarIconSlot? = null
    
    /**
     * Configure icon slot 1 (leftmost icon)
     * 
     * @param icon The icon to display
     * @param contentDescription Accessibility description
     * @param animationType Animation type (default: Dissolve)
     * @param iconTint Optional tint color for the icon (null uses default contentColor)
     * @param onClick Click handler for the icon
     */
    fun slot1(
        icon: IconName,
        contentDescription: String = "",
        animationType: IconAnimationType = IconAnimationType.Dissolve,
        iconTint: Color? = null,
        onClick: () -> Unit
    ) {
        slot1 = AppBarIconSlot(
            icon = icon,
            onClick = onClick,
            contentDescription = contentDescription,
            animationType = animationType,
            iconTint = iconTint
        )
    }
    
    /**
     * Configure icon slot 2
     * 
     * @param icon The icon to display
     * @param contentDescription Accessibility description
     * @param animationType Animation type (default: Dissolve)
     * @param iconTint Optional tint color for the icon (null uses default contentColor)
     * @param onClick Click handler for the icon
     */
    fun slot2(
        icon: IconName,
        contentDescription: String = "",
        animationType: IconAnimationType = IconAnimationType.Dissolve,
        iconTint: Color? = null,
        onClick: () -> Unit
    ) {
        slot2 = AppBarIconSlot(
            icon = icon,
            onClick = onClick,
            contentDescription = contentDescription,
            animationType = animationType,
            iconTint = iconTint
        )
    }
    
    /**
     * Configure icon slot 3
     * 
     * @param icon The icon to display
     * @param contentDescription Accessibility description
     * @param animationType Animation type (default: Dissolve)
     * @param iconTint Optional tint color for the icon (null uses default contentColor)
     * @param onClick Click handler for the icon
     */
    fun slot3(
        icon: IconName,
        contentDescription: String = "",
        animationType: IconAnimationType = IconAnimationType.Dissolve,
        iconTint: Color? = null,
        onClick: () -> Unit
    ) {
        slot3 = AppBarIconSlot(
            icon = icon,
            onClick = onClick,
            contentDescription = contentDescription,
            animationType = animationType,
            iconTint = iconTint
        )
    }
    
    /**
     * Configure icon slot 4 (rightmost icon)
     * 
     * @param icon The icon to display
     * @param contentDescription Accessibility description
     * @param animationType Animation type (default: Dissolve)
     * @param iconTint Optional tint color for the icon (null uses default contentColor)
     * @param onClick Click handler for the icon
     */
    fun slot4(
        icon: IconName,
        contentDescription: String = "",
        animationType: IconAnimationType = IconAnimationType.Dissolve,
        iconTint: Color? = null,
        onClick: () -> Unit
    ) {
        slot4 = AppBarIconSlot(
            icon = icon,
            onClick = onClick,
            contentDescription = contentDescription,
            animationType = animationType,
            iconTint = iconTint
        )
    }

    /**
     * Build the final AppBarIconSlots configuration
     */
    internal fun build(): AppBarIconSlots {
        return AppBarIconSlots(
            slot1 = slot1,
            slot2 = slot2,
            slot3 = slot3,
            slot4 = slot4
        )
    }
}

/**
 * Simplified helper for common use cases - just need icons and clicks
 * 
 * **Usage Example:**
 * ```kotlin
 * SetAppBarIcons(
 *     icon1 = Icons.Share05 to { shareContent() },
 *     icon2 = Icons.Filter to { showFilters() },
 *     icon3 = Icons.Check to { confirmSelection() }
 * )
 * ```
 * 
 * @param icon1 Pair of icon and click handler for slot 1
 * @param icon2 Pair of icon and click handler for slot 2
 * @param icon3 Pair of icon and click handler for slot 3
 * @param icon4 Pair of icon and click handler for slot 4
 */
@Composable
fun SetAppBarIcons(
    icon1: Pair<IconName, () -> Unit>? = null,
    icon2: Pair<IconName, () -> Unit>? = null,
    icon3: Pair<IconName, () -> Unit>? = null,
    icon4: Pair<IconName, () -> Unit>? = null
) {
    ConfigureAppBarIcons {
        icon1?.let { (icon, onClick) -> slot1(icon, onClick = onClick) }
        icon2?.let { (icon, onClick) -> slot2(icon, onClick = onClick) }
        icon3?.let { (icon, onClick) -> slot3(icon, onClick = onClick) }
        icon4?.let { (icon, onClick) -> slot4(icon, onClick = onClick) }
    }
}

/**
 * Extension to hide all app bar icons (useful for screens that don't need any).
 * Preserves navigation config.
 */
@Composable
fun HideAppBarIcons() {
    val appBarManager = getPopAppBarManager()
    LaunchedEffect(Unit) {
        appBarManager?.clearIconSlots()
    }
}

/**
 * Convenience helper to hide the navigation icon temporarily
 * 
 * @param key Optional key to trigger when to hide/show
 */
@Composable
fun HideNavigationIcon(key: Any? = Unit) {
    val appBarManager = getPopAppBarManager()
    LaunchedEffect(key) {
        appBarManager?.hideNavigationIcon()
    }
    
    DisposableEffect(Unit) {
        onDispose {
            appBarManager?.showNavigationIcon()
        }
    }
}

/**
 * Convenience helper to show the navigation icon (useful if it was hidden globally)
 */
@Composable
fun ShowNavigationIcon() {
    val appBarManager = getPopAppBarManager()
    LaunchedEffect(Unit) {
        appBarManager?.showNavigationIcon()
    }
}

/**
 * Helper to hide navigation icon for screens that don't need it (e.g., home screen).
 * This is a convenience wrapper around ConfigureAppBar with showNavigationIcon = false.
 * 
 * **Usage Example:**
 * ```kotlin
 * @Composable
 * fun HomeScreen() {
 *     HideAppBarNavigation()  // Hide navigation icon
 *     
 *     ConfigureAppBarIcons {
 *         slot1(Icons.Search) { search() }
 *     }
 *     
 *     // Rest of your screen
 * }
 * ```
 * 
 * @param key Optional key to trigger when to hide/show
 */
@Composable
fun HideAppBarNavigation(key: Any? = Unit) {
    val appBarManager = getPopAppBarManager()
    LaunchedEffect(key) {
        appBarManager?.updateNavigation(
            showNavigationIcon = false,
            navigationIcon = null,
            onNavigationClick = null
        )
    }
}

/**
 * App-bar-only scroll scrim helper.
 *
 * Use this for screens that have global app bar but do not use PopTopBarWithContent.
 * It toggles a top scrim (status bar to app bar bottom) when content starts scrolling.
 */
@Composable
fun ConfigureAppBarScrollScrim(
    key: Any? = Unit,
    lazyListState: LazyListState? = null,
    scrollState: ScrollState? = null,
    scrollOffsetPx: Int? = null
) {
    val appBarManager = getPopAppBarManager()
    val ownerKey = remember { Any() }
    val lifecycleOwner = LocalLifecycleOwner.current

    val isScrolled by remember(lazyListState, scrollState, scrollOffsetPx) {
        derivedStateOf {
            when {
                lazyListState != null ->
                    lazyListState.firstVisibleItemIndex > 0 || lazyListState.firstVisibleItemScrollOffset > 60
                scrollState != null -> scrollState.value > 60
                scrollOffsetPx != null -> scrollOffsetPx > 60
                else -> false
            }
        }
    }

    fun applyIfActive() {
        val isLifecycleActive = lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        if (isLifecycleActive) {
            appBarManager?.updateScrollScrimVisibility(isVisible = isScrolled, ownerKey = ownerKey)
        }
    }

    LaunchedEffect(key, isScrolled) {
        applyIfActive()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> applyIfActive()
                Lifecycle.Event.ON_STOP -> appBarManager?.clearScrollScrimIfOwner(ownerKey = ownerKey)
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            appBarManager?.clearScrollScrimIfOwner(ownerKey = ownerKey)
        }
    }
}
