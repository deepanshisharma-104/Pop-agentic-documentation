package com.pop.components.coachmarks

import android.util.Log
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.pop.components.theme.PopTheme
import com.pop.components.coachmarks.compose.CoachMarkDisplay

/**
 * Manager for showing Compose-based coach marks from any context.
 * Creates a temporary ComposeView overlay to display coach marks.
 * 
 * Similar to IntegrityDialogManager but for coach marks.
 */
object CoachMarkManager {
    
    private var currentComposeView: ComposeView? = null
    private var showCoachMarks by mutableStateOf(false)
    private var currentConfig: CoachMarkConfig? = null
    private var currentActivity: FragmentActivity? = null
    
    /**
     * Session-level flag that tracks if coach marks logic has been processed in this session.
     * Set to true when shouldShow() is evaluated (regardless of result).
     * Used as gatekeeper: other dialogs should wait until CoachMarks logic has run first.
     */
    private var hasBeenProcessed = false
    
    /**
     * Session-level flag that tracks if coach marks have been shown in this app session.
     * This persists even after coach marks are dismissed, until the app process restarts.
     * Used for dialog priority: if coach marks showed in this session, other lower-priority
     * dialogs (like app icon change) should wait until next app launch.
     */
    private var hasShownInThisSession = false
    
    /**
     * Reset session-level flags for a new app launch.
     * Call this in POPMainActivity.onCreate() to ensure each launch starts fresh.
     * 
     * Note: finishAffinity() doesn't kill the process, so singleton state persists.
     * This method ensures clean state for each new session.
     */
    fun resetForNewSession() {
        hasBeenProcessed = false
        hasShownInThisSession = false
        Log.d("CoachMarkManager", "Session flags reset for new app launch")
    }
    
    /**
     * Show coach marks with the given configuration.
     * 
     * @param activity The activity to show coach marks in
     * @param config Configuration for the coach marks
     */
    fun showCoachMarks(
        activity: FragmentActivity,
        config: CoachMarkConfig
    ) {
        Log.d("CoachMarkManager", "=== showCoachMarks called ===")
        Log.d("CoachMarkManager", "Activity finishing: ${activity.isFinishing}")
        Log.d("CoachMarkManager", "Activity destroyed: ${activity.isDestroyed}")
        
        if (activity.isFinishing || activity.isDestroyed) {
            Log.e("CoachMarkManager", "Activity is finishing or destroyed, cannot show coach marks")
            return
        }
        
        currentActivity = activity
        currentConfig = config
        
        // IMPORTANT: Set this BEFORE creating the overlay so Compose sees it as true
        showCoachMarks = true
        hasShownInThisSession = true  // Also set when actually showing (in case markAsProcessed wasn't called)
        Log.d("CoachMarkManager", "showCoachMarks state set to: $showCoachMarks, hasShownInThisSession: true")
        
        Log.d("CoachMarkManager", "Calling showComposeOverlay()")
        showComposeOverlay(activity, config)
    }
    
    /**
     * Check if coach marks are currently being displayed.
     * 
     * @return true if coach marks are showing, false otherwise
     */
    fun isShowing(): Boolean = showCoachMarks
    
    /**
     * Check if coach marks have been shown at any point in this app session.
     * This remains true even after dismissal, until app process restarts.
     * 
     * Used for dialog priority: if coach marks showed in this session,
     * lower-priority dialogs should wait until next app launch.
     * 
     * @return true if coach marks were shown in this session
     */
    fun hasShownInSession(): Boolean = hasShownInThisSession
    
    /**
     * Mark that coach marks logic has been processed (evaluated) in this session.
     * Call this after shouldShow() is evaluated, regardless of the result.
     * This acts as a gatekeeper for other dialogs.
     * 
     * @param willShow If true, also marks that coach marks will show (blocks other dialogs for session)
     */
    fun markAsProcessed(willShow: Boolean = false) {
        hasBeenProcessed = true
        if (willShow) {
            hasShownInThisSession = true
            Log.d("CoachMarkManager", "Coach marks marked as processed + will show in session")
        } else {
            Log.d("CoachMarkManager", "Coach marks marked as processed (won't show)")
        }
    }
    
    /**
     * Check if coach marks logic has been processed yet in this session.
     * This is the gatekeeper - other dialogs should wait until this is true.
     * 
     * @return true if coach marks logic has been evaluated
     */
    fun hasBeenProcessed(): Boolean = hasBeenProcessed
    
    /**
     * Dismiss any currently showing coach marks.
     */
    fun dismiss() {
        Log.d("CoachMarkManager", "=== dismiss() called ===")
        showCoachMarks = false
        hideComposeOverlay()
        currentConfig?.onDismiss?.invoke()
    }
    
    /**
     * Create and attach a ComposeView overlay to show coach marks.
     */
    private fun showComposeOverlay(
        activity: FragmentActivity,
        config: CoachMarkConfig
    ) {
        Log.d("CoachMarkManager", "=== showComposeOverlay called ===")
        
        // Clean up existing overlay if any
        hideComposeOverlay()
        Log.d("CoachMarkManager", "Cleaned up existing overlay")
        
        // Create new ComposeView
        Log.d("CoachMarkManager", "Creating new ComposeView")
        val composeView = ComposeView(activity).apply {
            // Set up view tree owners for proper lifecycle
            setViewTreeLifecycleOwner(activity)
            setViewTreeViewModelStoreOwner(activity)
            setViewTreeSavedStateRegistryOwner(activity)
            
            Log.d("CoachMarkManager", "Setting content with showCoachMarks=$showCoachMarks")
            
            // Set content
            setContent {
                PopTheme {
                    Log.d("CoachMarkManager", "Inside PopTheme, showCoachMarks=$showCoachMarks")
                    if (showCoachMarks) {
                        Log.d("CoachMarkManager", "Rendering CoachMarkDisplay")
                        CoachMarkDisplay(
                            config = config,
                            onDismiss = {
                                Log.d("CoachMarkManager", "CoachMarkDisplay onDismiss called")
                                dismiss()
                            }
                        )
                    } else {
                        Log.d("CoachMarkManager", "showCoachMarks is false, not rendering")
                    }
                }
            }
        }
        
        // Add to activity's root view
        val rootView = activity.window.decorView as? ViewGroup
        Log.d("CoachMarkManager", "Root view: $rootView")
        
        if (rootView == null) {
            Log.e("CoachMarkManager", "Root view is null! Cannot add coach marks overlay")
            return
        }
        
        Log.d("CoachMarkManager", "Adding ComposeView to root view")
        rootView.addView(
            composeView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        
        currentComposeView = composeView
        Log.d("CoachMarkManager", "ComposeView added successfully")
        Log.d("CoachMarkManager", "Root view child count: ${rootView.childCount}")
    }
    
    /**
     * Remove the ComposeView overlay.
     */
    private fun hideComposeOverlay() {
        Log.d("CoachMarkManager", "hideComposeOverlay() called")
        currentComposeView?.let { view ->
            Log.d("CoachMarkManager", "Removing current ComposeView from parent")
            (view.parent as? ViewGroup)?.removeView(view)
        }
        currentComposeView = null
        currentConfig = null
        currentActivity = null
        Log.d("CoachMarkManager", "Overlay cleanup complete")
    }
}
