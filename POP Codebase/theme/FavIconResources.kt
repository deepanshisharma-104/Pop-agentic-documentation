package com.pop.components.theme

import com.pop.compose_components.R

/**
 * Fav icon resource mapping utility.
 * Maps FavIconColor, FavIconSize, and isActive state to the correct drawable resource.
 * 
 * Based on Figma design specifications:
 * - Different drawable resources for each color/size/active combination
 * - Resource naming convention: ic_fav_{color}_{size}_{active|inactive}
 * 
 * Drawable resources in res/drawable/ with the following naming:
 * - ic_fav_orange_large_active.xml
 * - ic_fav_orange_med_active.xml
 * - ic_fav_white_large_active.xml
 * - ic_fav_white_large_inactive.xml
 * - ic_fav_white_med_active.xml
 * - ic_fav_white_med_inactive.xml
 * - ic_fav_green_large_active.xml
 * - ic_fav_green_med_active.xml
 * - ic_fav_blue_large_active.xml
 */
object FavIconResources {
    
    /**
     * Get drawable resource ID for a fav icon based on color, size, and active state.
     * Returns null if the resource doesn't exist.
     * 
     * @param color The fav icon color (Orange, White, Green, Blue)
     * @param size The fav icon size (Large, Med)
     * @param isActive Whether the icon is in active state
     * @return Drawable resource ID or null if not found
     */
    fun getFavIconResourceId(
        color: FavIconColor,
        size: FavIconSize,
        isActive: Boolean
    ): Int? {
        return when {
            // Orange variants
            color == FavIconColor.Orange && size == FavIconSize.Large && isActive -> 
                getResourceId("ic_fav_orange_large_active")
            color == FavIconColor.Orange && size == FavIconSize.Med && isActive -> 
                getResourceId("ic_fav_orange_med_active")
            
            // White variants
            color == FavIconColor.White && size == FavIconSize.Large && isActive -> 
                getResourceId("ic_fav_white_large_active")
            color == FavIconColor.White && size == FavIconSize.Large && !isActive -> 
                getResourceId("ic_fav_white_large_inactive")
            color == FavIconColor.White && size == FavIconSize.Med && isActive -> 
                getResourceId("ic_fav_white_med_active")
            color == FavIconColor.White && size == FavIconSize.Med && !isActive -> 
                getResourceId("ic_fav_white_med_inactive")
            
            // Green variants
            color == FavIconColor.Green && size == FavIconSize.Large && isActive -> 
                getResourceId("ic_fav_green_large_active")
            color == FavIconColor.Green && size == FavIconSize.Med && isActive -> 
                getResourceId("ic_fav_green_med_active")
            
            // Blue variants
            color == FavIconColor.Blue && size == FavIconSize.Large && isActive -> 
                getResourceId("ic_fav_blue_large_active")
            
            else -> null
        }
    }
    
    /**
     * Helper function to safely get resource ID by name.
     */
    private fun getResourceId(resourceName: String): Int? {
        return try {
            val field = R.drawable::class.java.getField(resourceName)
            field.get(null) as? Int
        } catch (e: Exception) {
            null
        }
    }
    
    /**
     * Check if a fav icon resource exists for the given parameters.
     */
    fun hasFavIcon(
        color: FavIconColor,
        size: FavIconSize,
        isActive: Boolean
    ): Boolean {
        return getFavIconResourceId(color, size, isActive) != null
    }
    
    /**
     * Get the resource name string for a fav icon (for debugging/logging).
     */
    fun getFavIconResourceName(
        color: FavIconColor,
        size: FavIconSize,
        isActive: Boolean
    ): String {
        val colorName = color.name.lowercase()
        val sizeName = size.name.lowercase()
        val stateName = if (isActive) "active" else "inactive"
        return "ic_fav_${colorName}_${sizeName}_${stateName}"
    }
}

