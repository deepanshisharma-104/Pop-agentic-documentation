package com.pop.components.animation.config

import timber.log.Timber
import java.util.ArrayDeque
import java.util.concurrent.ConcurrentHashMap

/**
 * Manages stacks of animation configurations for navigation.
 * 
 * This class maintains separate stacks of AnimationConfig instances for different owners
 * (fragments, activities, etc.), identified by unique owner IDs. The stack pattern is used
 * to track animation configurations as screens are navigated, allowing proper animation
 * handling when navigating back through the stack.
 * 
 * Use cases:
 * - Tracking animation configurations for nested navigation (e.g., split screen or mask reveal)
 * - Managing animation state across screen transitions
 * - Enabling proper cleanup when screens are detached (via removeNavigationAnimationConfigStack)
 * 
 * Thread-safety:
 * - Uses ConcurrentHashMap internally to ensure thread-safe access to stacks
 * - Safe to use from multiple threads without external synchronization
 * - Each stack is identified by a unique owner ID (typically the object ID of the owner)
 */
internal class AnimationConfigStackManager {

    /**
     * Private ConcurrentHashMap to store navigation animation config stacks.
     * Each stack is identified by a unique id. Which is the object id of the owner object (fragment, activity, etc.).
     */
    private val navigationAnimationConfigStackMap = ConcurrentHashMap<String, ArrayDeque<AnimationConfig>>()

    /**
     * Adds an animation configuration to the stack for the specified owner.
     *
     * @param ownerId Unique identifier for the owner (fragment, activity, etc.)
     * @param animationConfig The animation configuration to add
     * @return true if the config was added successfully, false if ownerId is blank
     */
    fun addNavigationAnimationConfigToStack(ownerId: String, animationConfig: AnimationConfig): Boolean {
        if (ownerId.isBlank()) {
            return false
        }
        val stack = getNavigationAnimationConfigStack(ownerId) ?: return false
        stack.push(animationConfig)
        val configType = animationConfig::class.simpleName ?: "Unknown"
        val animationId = when (animationConfig) {
            is SplitScreenAnimationConfig -> animationConfig.animationId
            is MaskRevealAnimationConfig -> animationConfig.animationId
            else -> "N/A"
        }
        Timber.tag("AnimationConfigStack").d("[$ownerId] ADD | Type: $configType | AnimationId: $animationId | StackSize: ${stack.size}")
        return true
    }

    /**
     * Gets the last animation configuration from the stack without removing it.
     *
     * @param ownerId Unique identifier for the owner
     * @return The last animation config, or null if stack is empty or ownerId is blank
     */
    fun getLastNavigationAnimationConfigFromStack(ownerId: String): AnimationConfig? {
        val stack = getNavigationAnimationConfigStack(ownerId)
        val config = stack?.peek()
        val configType = config?.let { it::class.simpleName ?: "Unknown" } ?: "null"
        val stackSize = stack?.size ?: 0
        Timber.tag("AnimationConfigStack").d("[$ownerId] GET | Type: $configType | StackSize: $stackSize")
        return config
    }

    /**
     * Removes and returns the last animation configuration from the stack.
     *
     * @param ownerId Unique identifier for the owner
     * @return The removed animation config, or null if stack is empty or ownerId is blank
     */
    fun removeLastNavigationAnimationConfigFromStack(ownerId: String): AnimationConfig? {
        val stack = getNavigationAnimationConfigStack(ownerId) ?: return null
        return if (stack.isNotEmpty()) {
            val config = stack.pop()
            val configType = config::class.simpleName ?: "Unknown"
            val animationId = when (config) {
                is SplitScreenAnimationConfig -> config.animationId
                is MaskRevealAnimationConfig -> config.animationId
                else -> "N/A"
            }
            Timber.tag("AnimationConfigStack").d("[$ownerId] REMOVE | Type: $configType | AnimationId: $animationId | StackSize: ${stack.size}")
            config
        } else {
            Timber.tag("AnimationConfigStack").w("[$ownerId] REMOVE | Stack is empty, nothing to remove")
            null
        }
    }

    /**
     * Removes a specific animation configuration from the stack.
     *
     * @param ownerId Unique identifier for the owner
     * @param animationConfig The animation configuration to remove
     * @return true if the config was removed, false otherwise
     */
    fun removeNavigationAnimationConfigFromStack(ownerId: String, animationConfig: AnimationConfig): Boolean {
        val stack = getNavigationAnimationConfigStack(ownerId) ?: return false
        return stack.remove(animationConfig)
    }

    /**
     * Checks if the animation configuration stack for the specified owner is empty.
     *
     * @param ownerId Unique identifier for the owner
     * @return true if the stack is empty or doesn't exist, false otherwise
     */
    fun isNavigationAnimationConfigStackEmpty(ownerId: String): Boolean {
        return getNavigationAnimationConfigStack(ownerId)?.isEmpty() ?: true
    }

    /**
     * Gets the size of the animation configuration stack for the specified owner.
     *
     * @param ownerId Unique identifier for the owner
     * @return The size of the stack, or 0 if the stack doesn't exist or is empty
     */
    fun getStackSize(ownerId: String): Int {
        return getNavigationAnimationConfigStack(ownerId)?.size ?: 0
    }

    /**
     * Logs the current state of the animation configuration stack for the specified owner.
     * Useful for debugging to see all configs in the stack.
     *
     * @param ownerId Unique identifier for the owner
     */
    fun logStackState(ownerId: String) {
        val stack = getNavigationAnimationConfigStack(ownerId)
        if (stack == null || stack.isEmpty()) {
            Timber.tag("AnimationConfigStack").d("[$ownerId] STACK_STATE | Empty or does not exist")
            return
        }
        val stackInfo = stack.mapIndexed { index, config ->
            val configType = config::class.simpleName ?: "Unknown"
            val animationId = when (config) {
                is SplitScreenAnimationConfig -> config.animationId
                is MaskRevealAnimationConfig -> config.animationId
                else -> "N/A"
            }
            "$index: $configType (id: $animationId)"
        }.joinToString(" | ")
        Timber.tag("AnimationConfigStack").d("[$ownerId] STACK_STATE | Size: ${stack.size} | Configs: [$stackInfo]")
    }

    /**
     * Removes the entire animation configuration stack for the specified owner.
     * Primarily designed to be called from the onDetach() of fragments when the fragment is removed/replaced.
     * Specifically for nested screens split screen and mask reveal animations
     *
     * @param ownerId Unique identifier for the owner
     */
    fun removeNavigationAnimationConfigStack(ownerId: String) {
        if (ownerId.isNotBlank()) {
            val stackSize = navigationAnimationConfigStackMap[ownerId]?.size ?: 0
            navigationAnimationConfigStackMap.remove(ownerId)
            Timber.tag("AnimationConfigStack").d("[$ownerId] CLEAR | Removed entire stack | PreviousStackSize: $stackSize")
        }
    }

    /**
     * Gets or creates the animation configuration stack for the specified owner.
     * 
     * This is an internal helper method that ensures a stack exists for the given ownerId.
     * If no stack exists, a new empty ArrayDeque is created and stored.
     * 
     * Thread Safety:
     * - Uses ConcurrentHashMap.getOrPut which is thread-safe
     * - Multiple threads can safely call this method concurrently
     * 
     * @param ownerId Unique identifier for the owner (fragment, activity, etc.)
     * @return The animation configuration stack for the owner, or null if ownerId is blank.
     *         A new empty stack is created if one doesn't exist.
     */
    private fun getNavigationAnimationConfigStack(ownerId: String): ArrayDeque<AnimationConfig>? {
        if (ownerId.isBlank()) {
            return null
        }
        return navigationAnimationConfigStackMap.getOrPut(ownerId) { ArrayDeque<AnimationConfig>() }
    }

    /**
     * Gets all stacks for serialization purposes.
     * 
     * This method is used internally by AnimationManager to serialize the entire
     * state of all animation configuration stacks. Useful for:
     * - Saving state across process death
     * - Debugging animation state
     * - Testing
     * 
     * The returned map is a snapshot - modifications to it won't affect the internal state.
     * 
     * @return Immutable map of owner IDs to their animation config stacks.
     *         Returns an empty map if no stacks exist.
     */
    internal fun getAllStacks(): Map<String, ArrayDeque<AnimationConfig>> {
        return navigationAnimationConfigStackMap.toMap()
    }

    /**
     * Clears all stacks and replaces with the provided map.
     * 
     * This method is used internally by AnimationManager to deserialize and restore
     * animation configuration stacks from a previously serialized state. Useful for:
     * - Restoring state after process death
     * - Testing
     * 
     * Warning: This completely replaces all existing stacks. Any stacks not in the
     * provided map will be lost.
     * 
     * @param stacks Map of owner IDs to their animation config stacks to restore.
     *               If null or empty, all stacks will be cleared.
     */
    internal fun setAllStacks(stacks: Map<String, ArrayDeque<AnimationConfig>>) {
        navigationAnimationConfigStackMap.clear()
        navigationAnimationConfigStackMap.putAll(stacks)
    }
}

