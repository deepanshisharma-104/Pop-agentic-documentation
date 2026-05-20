package com.pop.components.animation

import android.graphics.Bitmap
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavOptions
import com.pop.components.animation.config.AnimationConfig
import com.pop.components.animation.config.AnimationConfigStackManager
import com.pop.components.animation.event.AnimationEvent
import com.pop.components.animation.repository.AnimationBitmapRepository
import com.pop.components.animation.serialization.AnimationConfigSerializer
import com.pop.components.animation.service.AnimationEventBus
import com.pop.components.animation.strategy.AnimationStrategyFactory
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

/**
 * Manager for animation-related operations.
 * 
 * This is the central coordinator for all screen transition animations in the app.
 * It provides a unified API for creating animations, managing animation configuration
 * stacks, handling animation events, and caching animation bitmaps.
 * 
 * Responsibilities:
 * - Creating NavOptions, FragmentTransaction animations, and Compose transitions
 * - Managing stacks of animation configurations per screenId (for back navigation)
 * - Coordinating complex animations (Split Screen, Mask Reveal) via event bus
 * - Caching animation bitmaps for performance
 * - Serializing/deserializing animation state
 * 
 * Usage:
 * ```
 * // Inject via Hilt
 * @Inject lateinit var animationManager: AnimationManager
 * 
 * // Create NavOptions for navigation
 * val config = SlideLeftAnimationConfig.Default
 * val navOptions = animationManager.createNavOptions(config)
 * navController.navigate(resId, navOptions)
 * 
 * // Add config to stack for back navigation tracking
 * animationManager.addNavigationAnimationConfigToStack(screenId, config)
 * 
 * // Emit event for complex animations
 * animationManager.emitEvent(AnimationEvent.InvokeSplitScreenEntryAnimation(config))
 * ```
 * 
 * Thread Safety:
 * - All methods are thread-safe
 * - Config stack operations use ConcurrentHashMap internally
 * - Event bus uses SharedFlow (thread-safe)
 * - Bitmap repository uses Room database (thread-safe)
 * 
 * For Toast and Loader functionality, use PopCommonUiManager instead.
 * 
 * @param animationStrategyFactory Factory for creating animation strategies
 * @param bitmapRepository Repository for caching animation bitmaps
 * @param configStackManager Manager for animation configuration stacks
 * @param eventBus Event bus for coordinating animations between Activity and Fragments
 * @param serializer Serializer for saving/restoring animation state
 */
class AnimationManager @Inject internal constructor(
    private val animationStrategyFactory: AnimationStrategyFactory,
    private val bitmapRepository: AnimationBitmapRepository,
    private val configStackManager: AnimationConfigStackManager,
    private val eventBus: AnimationEventBus,
    private val serializer: AnimationConfigSerializer
) {

    // ========== Animation Creation Methods ==========

    /**
     * Creates NavOptions with animations for the specified animation config.
     * 
     * This method is used for NavGraph-based navigation (AndroidX Navigation Component).
     * The returned NavOptions contains enter/exit animations that will be applied
     * when navigating to a destination.
     * 
     * Example usage:
     * ```
     * val config = SlideLeftAnimationConfig.Default
     * val navOptions = animationManager.createNavOptions(config)
     * navController.navigate(R.id.destination, navOptions)
     * ```
     *
     * @param config The animation configuration to use
     * @return NavOptions configured with the appropriate enter/exit animations for forward navigation
     *         and pop enter/pop exit animations for back navigation
     */
    fun createNavOptions(config: AnimationConfig): NavOptions {
        val strategy = animationStrategyFactory.getStrategy(config)
        return strategy.createNavOptions()
    }

    /**
     * Applies animations to a FragmentTransaction for the specified animation config.
     * 
     * This method is used for Fragment-based navigation (childFragmentManager).
     * The animations are applied directly to the transaction and will be executed
     * when the transaction is committed.
     * 
     * Example usage:
     * ```
     * val config = SlideLeftAnimationConfig.Default
     * val transaction = childFragmentManager.beginTransaction()
     * animationManager.applyAnimations(transaction, config)
     *     .replace(containerId, fragment)
     *     .addToBackStack(null)
     *     .commit()
     * ```
     *
     * @param transaction The FragmentTransaction to apply animations to. Must not be committed yet.
     * @param config The animation configuration to use
     * @return The same FragmentTransaction instance for method chaining
     */
    fun applyAnimations(
        transaction: FragmentTransaction,
        config: AnimationConfig
    ): FragmentTransaction {
        val strategy = animationStrategyFactory.getStrategy(config)
        return strategy.applyAnimations(transaction)
    }

    /**
     * Creates enter transition for Compose Navigation.
     * 
     * This method is used for Jetpack Compose Navigation (NavHost).
     * The returned transition is applied when a new screen enters during forward navigation.
     * 
     * Example usage:
     * ```
     * val config = SlideLeftAnimationConfig.Default
     * NavHost(
     *     navController = navController,
     *     startDestination = "home",
     *     enterTransition = { animationManager.createEnterTransition(config) }
     * )
     * ```
     *
     * @param config The animation configuration to use
     * @return EnterTransition configured with the appropriate animations for the new screen entering
     */
    fun createEnterTransition(config: AnimationConfig): EnterTransition {
        val strategy = animationStrategyFactory.getStrategy(config)
        return strategy.createEnterTransition(config)
    }

    /**
     * Creates exit transition for Compose Navigation.
     * 
     * This method is used for Jetpack Compose Navigation (NavHost).
     * The returned transition is applied when the current screen exits during forward navigation.
     * 
     * Example usage:
     * ```
     * val config = SlideLeftAnimationConfig.Default
     * NavHost(
     *     navController = navController,
     *     startDestination = "home",
     *     exitTransition = { animationManager.createExitTransition(config) }
     * )
     * ```
     *
     * @param config The animation configuration to use
     * @return ExitTransition configured with the appropriate animations for the current screen exiting
     */
    fun createExitTransition(config: AnimationConfig): ExitTransition {
        val strategy = animationStrategyFactory.getStrategy(config)
        return strategy.createExitTransition(config)
    }

    /**
     * Creates pop enter transition for Compose Navigation.
     * 
     * This method is used for Jetpack Compose Navigation (NavHost).
     * The returned transition is applied when a screen re-enters during back navigation
     * (i.e., when popping the back stack).
     * 
     * Example usage:
     * ```
     * val config = SlideLeftAnimationConfig.Default
     * NavHost(
     *     navController = navController,
     *     startDestination = "home",
     *     popEnterTransition = { animationManager.createPopEnterTransition(config) }
     * )
     * ```
     *
     * @param config The animation configuration to use
     * @return EnterTransition configured with the appropriate reverse animations for the screen re-entering
     */
    fun createPopEnterTransition(config: AnimationConfig): EnterTransition {
        val strategy = animationStrategyFactory.getStrategy(config)
        return strategy.createPopEnterTransition(config)
    }

    /**
     * Creates pop exit transition for Compose Navigation.
     * 
     * This method is used for Jetpack Compose Navigation (NavHost).
     * The returned transition is applied when the current screen exits during back navigation
     * (i.e., when popping the back stack).
     * 
     * Example usage:
     * ```
     * val config = SlideLeftAnimationConfig.Default
     * NavHost(
     *     navController = navController,
     *     startDestination = "home",
     *     popExitTransition = { animationManager.createPopExitTransition(config) }
     * )
     * ```
     *
     * @param config The animation configuration to use
     * @return ExitTransition configured with the appropriate reverse animations for the current screen exiting
     */
    fun createPopExitTransition(config: AnimationConfig): ExitTransition {
        val strategy = animationStrategyFactory.getStrategy(config)
        return strategy.createPopExitTransition(config)
    }

    // ========== Animation Config Stack Management ==========

    /**
     * Adds an animation configuration to the stack for tracking navigation history.
     * 
     * This method is called after navigation to track which animation was used,
     * enabling proper reverse animations when navigating back.
     * 
     * The `screenId` is extracted directly from `animationConfig.screenId`.
     * The config must have `screenId` set before calling this method.
     * 
     * **When to call:**
     * - Slide animations: Immediately after navigation in extension functions
     * - Split Screen/Mask Reveal: After animation starts in Activity's animation handler
     * 
     * Example usage:
     * ```
     * // For child navigation
     * val config = SlideLeftAnimationConfig(screenId = fragment.screenId, ...)
     * animationManager.addNavigationAnimationConfigToStack(config)
     * 
     * // For activity-level navigation
     * val config = SlideLeftAnimationConfig(screenId = activityScreenId, ...)
     * animationManager.addNavigationAnimationConfigToStack(config)
     * ```
     *
     * @param animationConfig The animation configuration to add to the stack. Must have `screenId` set.
     * @return true if the config was added successfully, false if screenId is blank
     * @throws IllegalArgumentException if `animationConfig.screenId` is null
     */
    fun addNavigationAnimationConfigToStack(
        animationConfig: AnimationConfig
    ): Boolean {
        val screenId = animationConfig.screenId ?: throw IllegalArgumentException(
            "AnimationConfig.screenId must be set. Got null for ${animationConfig::class.simpleName}"
        )
        return configStackManager.addNavigationAnimationConfigToStack(screenId, animationConfig)
    }

    /**
     * Gets the last animation configuration from the stack without removing it.
     * 
     * This method is used to check which animation was used for the last navigation,
     * typically before handling back navigation. The config remains in the stack
     * until explicitly removed.
     * 
     * Example usage:
     * ```
     * val lastConfig = animationManager.getLastNavigationAnimationConfigFromStack(screenId)
     * if (lastConfig is SplitScreenAnimationConfig) {
     *     // Handle split screen back animation
     * }
     * ```
     *
     * @param ownerId Unique identifier for the owner (fragment's screenId or activity's screenId)
     * @return The last animation config that was added to the stack, or null if stack is empty or doesn't exist
     */
    fun getLastNavigationAnimationConfigFromStack(ownerId: String): AnimationConfig? {
        return configStackManager.getLastNavigationAnimationConfigFromStack(ownerId)
    }

    /**
     * Removes and returns the last animation configuration from the stack.
     * 
     * This method is called during back navigation to pop the last config from the stack.
     * The config is removed in LIFO (Last In, First Out) order.
     * 
     * **When to call:**
     * - For slide animations: Called immediately in `handleChildBackWithAnimation()` before back navigation
     * - For Split Screen/Mask Reveal: Called in Activity's exit animation handler after animation completes
     * 
     * Example usage:
     * ```
     * // Get last config to determine animation type
     * val lastConfig = animationManager.getLastNavigationAnimationConfigFromStack(screenId)
     * 
     * // Remove it from stack
     * animationManager.removeLastNavigationAnimationConfigFromStack(screenId)
     * 
     * // Perform back navigation
     * navController.popBackStack()
     * ```
     *
     * @param ownerId Unique identifier for the owner (fragment's screenId or activity's screenId)
     * @return The removed animation config, or null if stack is empty or doesn't exist
     */
    fun removeLastNavigationAnimationConfigFromStack(ownerId: String): AnimationConfig? {
        return configStackManager.removeLastNavigationAnimationConfigFromStack(ownerId)
    }

    /**
     * Removes a specific animation configuration from the stack.
     * 
     * This method removes a particular config instance from the stack, rather than
     * just the last one. Useful when you need to remove a specific config that
     * may not be at the top of the stack.
     * 
     * **Note:** This uses `equals()` to find the config, so the exact same instance
     * or an equal instance must be provided.
     * 
     * Example usage:
     * ```
     * // Remove a specific config (e.g., after animation failure)
     * animationManager.removeNavigationAnimationConfigFromStack(screenId, config)
     * ```
     *
     * @param ownerId Unique identifier for the owner (fragment's screenId or activity's screenId)
     * @param animationConfig The exact animation configuration instance to remove from the stack
     * @return true if the config was found and removed, false otherwise
     */
    fun removeNavigationAnimationConfigFromStack(ownerId: String, animationConfig: AnimationConfig): Boolean {
        return configStackManager.removeNavigationAnimationConfigFromStack(ownerId, animationConfig)
    }

    /**
     * Checks if the animation configuration stack for the specified owner is empty.
     * 
     * Useful for determining if there are any tracked animations for a given screenId,
     * which can help decide whether back navigation should use animations or not.
     * 
     * Example usage:
     * ```
     * if (!animationManager.isNavigationAnimationConfigStackEmpty(screenId)) {
     *     // There are tracked animations, handle back with animation
     *     handleChildBackWithAnimation()
     * } else {
     *     // No tracked animations, use default back behavior
     *     super.onBackPressed()
     * }
     * ```
     *
     * @param ownerId Unique identifier for the owner (fragment's screenId or activity's screenId)
     * @return true if the stack is empty or doesn't exist, false if it has entries
     */
    fun isNavigationAnimationConfigStackEmpty(ownerId: String): Boolean {
        return configStackManager.isNavigationAnimationConfigStackEmpty(ownerId)
    }

    /**
     * Gets the size of the animation configuration stack for the specified owner.
     * 
     * Useful for debugging, logging, or understanding navigation depth.
     * 
     * Example usage:
     * ```
     * val stackSize = animationManager.getStackSize(screenId)
     * Log.d("Navigation", "Stack size: $stackSize")
     * ```
     *
     * @param ownerId Unique identifier for the owner (fragment's screenId or activity's screenId)
     * @return The number of animation configs in the stack, or 0 if the stack doesn't exist or is empty
     */
    fun getStackSize(ownerId: String): Int {
        return configStackManager.getStackSize(ownerId)
    }

    /**
     * Checks if the animation configuration stack exists and has entries for the specified owner.
     * 
     * This is a convenience method that is the inverse of [isNavigationAnimationConfigStackEmpty].
     * 
     * Example usage:
     * ```
     * if (animationManager.hasAnimationStack(screenId)) {
     *     // Stack exists and has entries
     * }
     * ```
     *
     * @param ownerId Unique identifier for the owner (fragment's screenId or activity's screenId)
     * @return true if the stack exists and has at least one entry, false otherwise
     */
    fun hasAnimationStack(ownerId: String): Boolean {
        return !configStackManager.isNavigationAnimationConfigStackEmpty(ownerId)
    }

    /**
     * Logs the current state of the animation configuration stack for the specified owner.
     * 
     * This method is useful for debugging to see all configs currently in the stack.
     * The log output follows the pattern: `[screenId] STACK_STATE | Size: X | Configs: [...]`
     * 
     * Example usage:
     * ```
     * // Log stack state for debugging
     * animationManager.logStackState(screenId)
     * ```
     *
     * @param screenId Unique identifier for the owner (fragment's screenId or activity's screenId)
     */
    fun logStackState(screenId: String) {
        configStackManager.logStackState(screenId)
    }

    /**
     * Removes the entire animation configuration stack for the specified owner.
     * 
     * This method clears all animation configs for a given screenId, typically called
     * when a fragment is detached to prevent memory leaks and ensure clean state.
     * 
     * **When to call:**
     * - In `BaseFragment.onDetach()` to clean up when fragment is removed/replaced
     * - When you want to reset navigation history for a specific screenId
     * 
     * **Note:** This is automatically called in `BaseFragment.onDetach()`, so fragments
     * extending `BaseFragment` don't need to call this manually.
     * 
     * Example usage:
     * ```
     * override fun onDetach() {
     *     super.onDetach()
     *     animationManager.removeNavigationAnimationConfigStack(screenId)
     * }
     * ```
     *
     * @param ownerId Unique identifier for the owner (fragment's screenId or activity's screenId)
     */
    fun removeNavigationAnimationConfigStack(ownerId: String) {
        configStackManager.removeNavigationAnimationConfigStack(ownerId)
    }

    // ========== Serialization Methods ==========

    /**
     * Serializes the navigation animation configuration stacks to a JSON string.
     * 
     * Useful for saving animation state across process death or for debugging purposes.
     * Returns null if there are no stacks to serialize.
     * 
     * Example usage:
     * ```
     * val json = animationManager.serializeNavigationAnimationConfigStackMap()
     * if (json != null) {
     *     // Save to SharedPreferences or other storage
     * }
     * ```
     *
     * @return JSON string representation, or null if stacks are empty
     */
    fun serializeNavigationAnimationConfigStackMap(): String? {
        val stacks = configStackManager.getAllStacks()
        return serializer.serialize(stacks)
    }

    /**
     * Deserializes a JSON string and populates the navigation animation configuration stacks.
     * 
     * Restores animation configuration stacks from a previously serialized JSON string.
     * Useful for restoring animation state after process death.
     * 
     * Example usage:
     * ```
     * val savedJson = // ... retrieve from storage
     * try {
     *     animationManager.deserializeNavigationAnimationConfigStackMap(savedJson)
     * } catch (e: JsonParseException) {
     *     // Handle invalid JSON
     * }
     * ```
     *
     * @param jsonString JSON string representation of the stacks
     * @throws JsonParseException if the JSON string is invalid or contains unknown animation config types
     */
    fun deserializeNavigationAnimationConfigStackMap(jsonString: String) {
        val stacks = serializer.deserialize(jsonString)
        configStackManager.setAllStacks(stacks)
    }

    // ========== Event Bus Methods ==========

    /**
     * Observable flow for animation events.
     * 
     * Fragments can collect from this flow to receive animation events from the Activity.
     * Used for coordinating Split Screen and Mask Reveal animations.
     * 
     * Example usage:
     * ```
     * lifecycleScope.launch {
     *     animationManager.animationEventBus.collect { event ->
     *         when (event) {
     *             is AnimationEvent.Navigate -> {
     *                 // Execute pending navigation
     *             }
     *             is AnimationEvent.GoBack -> {
     *                 // Execute pending back navigation
     *             }
     *             // ... other events
     *         }
     *     }
     * }
     * ```
     * 
     * Note: BaseFragment already sets up a collector for this flow.
     * Fragments should use the extension functions instead of collecting directly.
     * 
     * @see AnimationEvent for available event types
     */
    val animationEventBus: SharedFlow<AnimationEvent> = eventBus.animationEventBus

    /**
     * Emits an animation event to the animation event bus.
     * 
     * This method is used to coordinate complex animations (Split Screen, Mask Reveal)
     * between Activity and Fragments. Events flow as follows:
     * 
     * Entry Animation Flow:
     * 1. Fragment calls extension function (e.g., navigateChildWithSplitScreen)
     * 2. Extension emits InvokeSplitScreenEntryAnimation event
     * 3. Activity receives event, performs animation
     * 4. Activity emits Navigate event back to Fragment
     * 5. Fragment executes actual navigation
     * 
     * Exit Animation Flow:
     * 1. User presses back, Fragment emits InvokeSplitScreenExitAnimation event
     * 2. Activity receives event, performs exit animation
     * 3. Activity emits GoBack event back to Fragment
     * 4. Fragment executes actual back navigation
     * 
     * Note: Slide animations don't use events - they're applied directly.
     * 
     * @param event The AnimationEvent to emit. Can be Navigate, GoBack,
     *              InvokeSplitScreenEntryAnimation, InvokeSplitScreenExitAnimation,
     *              InvokeMaskRevealEntryAnimation, InvokeMaskRevealExitAnimation,
     *              or ChangeWindowScale.
     * 
     * @see AnimationEvent for available event types
     */
    fun emitEvent(event: AnimationEvent) {
        eventBus.emitEvent(event)
    }

    // ========== Bitmap Repository Methods ==========

    /**
     * Stores split screen bitmaps in the database.
     * 
     * Used for caching split screen animation bitmaps to improve performance on repeated navigations.
     * The bitmaps are compressed and stored with the animation ID for later retrieval.
     * 
     * @param animationId Unique identifier for this animation instance (from SplitScreenAnimationConfig)
     * @param upperBitmap The upper half of the split screen bitmap
     * @param lowerBitmap The lower half of the split screen bitmap
     * @param splitAnchorPercent The split point percentage used (for validation/retrieval)
     * @return true if storage was successful, false otherwise
     * 
     * Note: This is a suspend function and should be called from a coroutine context.
     * Thread-safe database operations are handled internally.
     */
    suspend fun storeSplitScreenBitmaps(
        animationId: String,
        upperBitmap: Bitmap,
        lowerBitmap: Bitmap,
        splitAnchorPercent: Float
    ): Boolean {
        return bitmapRepository.storeSplitScreenBitmaps(animationId, upperBitmap, lowerBitmap, splitAnchorPercent)
    }

    /**
     * Retrieves split screen bitmaps from the database.
     *
     * Retrieves previously stored split screen bitmaps for a given animation ID.
     * Returns null if no bitmaps are found for the given ID.
     * 
     * @param animationId Unique identifier for the animation instance
     * @return Pair of (upperBitmap, lowerBitmap) if found, null otherwise
     * 
     * Note: This is a suspend function and should be called from a coroutine context.
     */
    suspend fun getSplitScreenBitmaps(animationId: String): Pair<Bitmap, Bitmap>? {
        return bitmapRepository.getSplitScreenBitmaps(animationId)
    }

    /**
     * Stores mask reveal bitmap in the database.
     *
     * Used for caching mask reveal animation bitmaps to improve performance on repeated navigations.
     * The bitmap is compressed and stored with the animation ID and vertical origin for later retrieval.
     * 
     * @param animationId Unique identifier for this animation instance (from MaskRevealAnimationConfig)
     * @param bitmap The full screen bitmap to cache
     * @param verticalOriginFraction The vertical origin fraction used (for validation/retrieval)
     * @return true if storage was successful, false otherwise
     * 
     * Note: This is a suspend function and should be called from a coroutine context.
     * Thread-safe database operations are handled internally.
     */
    suspend fun storeMaskRevealBitmap(
        animationId: String,
        bitmap: Bitmap,
        verticalOriginFraction: Float
    ): Boolean {
        return bitmapRepository.storeMaskRevealBitmap(animationId, bitmap, verticalOriginFraction)
    }

    /**
     * Retrieves mask reveal bitmap from the database.
     *
     * Retrieves a previously stored mask reveal bitmap for a given animation ID.
     * Returns null if no bitmap is found for the given ID.
     * 
     * @param animationId Unique identifier for the animation instance
     * @return The cached bitmap if found, null otherwise
     * 
     * Note: This is a suspend function and should be called from a coroutine context.
     */
    suspend fun getMaskRevealBitmap(animationId: String): Bitmap? {
        return bitmapRepository.getMaskRevealBitmap(animationId)
    }

    /**
     * Deletes an animation bitmap from the database.
     *
     * Removes the stored bitmap(s) for a specific animation ID to free up storage space.
     * Should be called when an animation configuration is no longer needed.
     * 
     * @param animationId Unique identifier for the animation instance to delete
     * @return true if deletion was successful, false otherwise
     * 
     * Note: This is a suspend function and should be called from a coroutine context.
     */
    suspend fun deleteAnimationBitmap(animationId: String): Boolean {
        return bitmapRepository.deleteAnimationBitmap(animationId)
    }

    /**
     * Clears all animation bitmaps from the database.
     * 
     * Removes all cached animation bitmaps. Useful for cleanup or when freeing up storage space.
     * 
     * @return Number of bitmap records deleted
     *
     * Note: This is a suspend function and should be called from a coroutine context.
     * Use with caution as this removes all cached bitmaps across all animation types.
     */
    suspend fun clearAllBitmaps(): Int {
        return bitmapRepository.clearAllBitmaps()
    }
}
