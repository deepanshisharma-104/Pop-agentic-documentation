package com.pop.components.ds_components

/**
 * Loading state substates for buttons
 * 
 * Used when ButtonState is Loading to specify the type of loading indicator.
 * This enum allows for easy extension with new loading states (e.g., Intermediate).
 */
enum class ButtonLoadingState {
    /**
     * Default loading state - shows spinner
     */
    Default,
    
    /**
     * Success loading state - shows success icon (checkmark)
     */
    Success,
    
    /**
     * Destructive loading state - shows destructive/error icon (cross)
     */
    Destructive,
    
    /**
     * Intermediate loading state - for future use (e.g., progress indicator)
     */
    Intermediate
}
