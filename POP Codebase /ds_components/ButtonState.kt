package com.pop.components.ds_components

/**
 * Button states matching Figma design system
 * 
 * For loading states, use ButtonState.Loading with a LoadingState parameter
 * to specify the type of loading indicator (Default, Success, Destructive, etc.)
 */
enum class ButtonState {
    /**
     * Active/Default state - button is enabled and ready for interaction
     */
    Active,
    
    /**
     * Disabled state - button is disabled and non-interactive
     */
    Disabled,
    
    /**
     * Loading state - button is in loading state
     * Use LoadingState enum to specify the type of loading indicator
     */
    Loading,
    
    /**
     * Destructive state - button indicates a destructive action (e.g., delete)
     */
    Destructive
}
