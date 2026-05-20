package com.pop.components.coachmarks

import android.util.Log
import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.ui.geometry.Rect
import java.lang.ref.WeakReference

/**
 * Data class to hold both bounds and shape information for a component.
 */
data class ComponentInfo(
    val bounds: Rect,
    val cornerRadiusDp: Float? = null
)

/**
 * Global registry for tracking view bounds and shape information for coach marks.
 * This allows automatic discovery of UI components without hardcoded coordinates.
 * 
 * Views register themselves with a key, and coach marks reference them by that key.
 * Bounds and shape info are updated automatically when views are laid out or change.
 */
object CoachMarkRegistry {
    
    private val componentInfo = mutableMapOf<String, ComponentInfo>()
    private val viewReferences = mutableMapOf<String, WeakReference<View>>()
    @OptIn(ExperimentalFoundationApi::class)
    private val bringIntoViewRequesters = mutableMapOf<String, BringIntoViewRequester>()

    /**
     * Suspend lambdas that scroll a specific target to the centre of the viewport.
     * Higher fidelity than [BringIntoViewRequester]: the caller captures [LazyListState]
     * (or another scroll state) and performs a two-phase scroll — first to the item index,
     * then an animated correction to centre it on screen.
     */
    private val scrollToCenterActions = mutableMapOf<String, suspend () -> Unit>()

    /**
     * Register a [BringIntoViewRequester] for a key so the coachmark system can
     * scroll the target into the centre of the viewport before showing the highlight.
     * Call this from the same composable that attaches [bringIntoViewRequester] modifier.
     */
    @OptIn(ExperimentalFoundationApi::class)
    fun registerBringIntoView(key: String, requester: BringIntoViewRequester) {
        bringIntoViewRequesters[key] = requester
    }

    /** Scroll the registered target into view, centred if possible. No-op if not registered. */
    @OptIn(ExperimentalFoundationApi::class)
    suspend fun bringIntoView(key: String) {
        bringIntoViewRequesters[key]?.bringIntoView()
    }

    /** Returns true if a [BringIntoViewRequester] has been registered for [key]. */
    fun hasBringIntoView(key: String): Boolean = bringIntoViewRequesters.containsKey(key)

    /**
     * Register a scroll-to-center action for [key].
     * The [action] should scroll the target so it is centred in the visible viewport.
     * Typically registered from the parent composable that owns the scroll state
     * (e.g. inside [itemsIndexed] in a [LazyColumn]).
     */
    fun registerScrollToCenter(key: String, action: suspend () -> Unit) {
        scrollToCenterActions[key] = action
    }

    /**
     * Execute the registered scroll-to-center action for [key].
     * No-op if nothing has been registered.
     */
    suspend fun scrollToCenter(key: String) {
        scrollToCenterActions[key]?.invoke()
    }

    /** Returns true if a scroll-to-center action has been registered for [key]. */
    fun hasScrollToCenter(key: String): Boolean = scrollToCenterActions.containsKey(key)
    
    /**
     * Register a view with a key for coach mark targeting.
     * The view's bounds will be tracked automatically.
     * 
     * @param key Unique identifier for this view
     * @param view The view to track
     * @param cornerRadiusDp Optional corner radius of the view in dp
     */
    fun registerView(key: String, view: View, cornerRadiusDp: Float? = null) {
        viewReferences[key] = WeakReference(view)
        updateViewBounds(key, view, cornerRadiusDp)
        
        // Add layout listener to update bounds when view changes
        view.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            if (left != oldLeft || top != oldTop || right != oldRight || bottom != oldBottom) {
                updateViewBounds(key, v, cornerRadiusDp)
            }
        }
        
        Log.d("CoachMarkRegistry", "Registered view: $key with cornerRadius: $cornerRadiusDp")
    }
    
    /**
     * Register bounds directly (for Compose views using coachMarkTarget modifier).
     * 
     * @param key Unique identifier
     * @param bounds The view bounds
     * @param cornerRadiusDp Optional corner radius in dp
     */
    fun registerBounds(key: String, bounds: Rect, cornerRadiusDp: Float? = null) {
        componentInfo[key] = ComponentInfo(bounds, cornerRadiusDp)
        Log.d("CoachMarkRegistry", "Registered bounds: $key -> $bounds with cornerRadius: $cornerRadiusDp")
    }
    
    /**
     * Get the current bounds for a registered view/key.
     * Returns null if not found or view is no longer valid.
     * 
     * @param key The view identifier
     * @return Current bounds or null
     */
    fun getBounds(key: String): Rect? {
        return getComponentInfo(key)?.bounds
    }
    
    /**
     * Get complete component information (bounds + shape) for a registered view/key.
     * Returns null if not found or view is no longer valid.
     * 
     * @param key The view identifier
     * @return ComponentInfo or null
     */
    fun getComponentInfo(key: String): ComponentInfo? {
        // First check if we have direct component info (from Compose)
        componentInfo[key]?.let { return it }
        
        // Otherwise try to get from view reference
        val viewRef = viewReferences[key]
        val view = viewRef?.get()
        
        if (view == null) {
            Log.w("CoachMarkRegistry", "View not found for key: $key")
            viewReferences.remove(key)
            return null
        }
        
        return updateViewBounds(key, view, null)?.let { ComponentInfo(it) }
    }
    
    /**
     * Update bounds for a view.
     */
    private fun updateViewBounds(key: String, view: View, cornerRadiusDp: Float?): Rect? {
        if (!view.isLaidOut) {
            Log.d("CoachMarkRegistry", "View $key not yet laid out")
            return null
        }
        
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        
        val bounds = Rect(
            left = location[0].toFloat(),
            top = location[1].toFloat(),
            right = (location[0] + view.width).toFloat(),
            bottom = (location[1] + view.height).toFloat()
        )
        
        componentInfo[key] = ComponentInfo(bounds, cornerRadiusDp)
        Log.d("CoachMarkRegistry", "Updated bounds for $key: $bounds with cornerRadius: $cornerRadiusDp")
        return bounds
    }
    
    /**
     * Unregister a view (cleanup).
     * 
     * @param key The view identifier
     */
    fun unregister(key: String) {
        viewReferences.remove(key)
        componentInfo.remove(key)
        Log.d("CoachMarkRegistry", "Unregistered: $key")
    }
    
    /**
     * Clear all registered views (useful for testing or cleanup).
     */
    @OptIn(ExperimentalFoundationApi::class)
    fun clear() {
        viewReferences.clear()
        componentInfo.clear()
        bringIntoViewRequesters.clear()
        scrollToCenterActions.clear()
        Log.d("CoachMarkRegistry", "Cleared all registrations")
    }
    
    /**
     * Get all currently registered keys (for debugging).
     */
    fun getRegisteredKeys(): Set<String> {
        return (viewReferences.keys + componentInfo.keys).toSet()
    }
    
    /**
     * Wait for a view to be laid out, then return its bounds.
     * Useful when you need bounds immediately after view creation.
     * 
     * @param key The view identifier
     * @param timeout Maximum time to wait in milliseconds
     * @param callback Called with bounds when available (or null if timeout)
     */
    fun waitForBounds(key: String, timeout: Long = 5000, callback: (Rect?) -> Unit) {
        val startTime = System.currentTimeMillis()
        
        fun checkBounds() {
            val bounds = getBounds(key)
            if (bounds != null) {
                callback(bounds)
            } else if (System.currentTimeMillis() - startTime < timeout) {
                // Try again after a short delay
                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                    checkBounds()
                }, 50)
            } else {
                Log.w("CoachMarkRegistry", "Timeout waiting for bounds: $key")
                callback(null)
            }
        }
        
        checkBounds()
    }
}

/**
 * Extension function to register a view for coach marks.
 * 
 * Usage:
 * ```
 * qrScannerButton.registerForCoachMark("qr_scanner", cornerRadiusDp = 28f)
 * ```
 * 
 * @param key Unique identifier for this view
 * @param cornerRadiusDp Optional corner radius in dp to match the view's actual shape
 */
fun View.registerForCoachMark(key: String, cornerRadiusDp: Float? = null) {
    CoachMarkRegistry.registerView(key, this, cornerRadiusDp)
}

/**
 * Extension function to unregister a view.
 */
fun View.unregisterForCoachMark(key: String) {
    CoachMarkRegistry.unregister(key)
}
