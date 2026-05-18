package com.pop.components.theme


/**
 * Design System Icon Resources
 * 
 * This file provides utilities for resolving icon resources from type-safe icon names.
 * All icon names are defined in the [IconName] sealed interface and implemented as
 * objects in the [Icons] namespace (e.g., Icons.ArrowDown, Icons.Heart).
 * 
 * Resources are now embedded directly in icon objects, eliminating the need for
 * a separate resource map. This means adding a new icon only requires:
 * 1. Adding the drawable files
 * 2. Adding the icon object in the [Icons] namespace with its resource IDs
 */
object PopIconResources {
    
    /**
     * Get drawable resource ID for a type-safe icon name with style.
     * Returns null if the resource doesn't exist.
     * 
     * Resources are accessed directly from the icon object properties in the [Icons] namespace.
     */
    fun getIconResourceId(iconName: IconName, style: IconStyle): Int? {
        return when (style) {
            IconStyle.Outline -> iconName.outlineRes
            IconStyle.Filled -> iconName.filledRes
        }
    }
    
    /**
     * Check if an icon resource exists for the given type-safe icon name and style.
     */
    fun hasIcon(iconName: IconName, style: IconStyle): Boolean {
        return getIconResourceId(iconName, style) != null
    }
    
    /**
     * Internal helper to get drawable resource name for an icon with style.
     * Used internally for XML attribute parsing.
     */
    internal fun getIconResourceName(iconName: String, style: IconStyle): String {
        val suffix = when (style) {
            IconStyle.Outline -> "_outline"
            IconStyle.Filled -> "_filled"
        }
        return "$iconName$suffix"
    }
    
    /**
     * Internal helper to get drawable resource ID from string.
     * Used internally for XML attribute parsing.
     * 
     * Note: This requires finding the icon object by value string.
     * For better performance, use the type-safe icon objects from the [Icons] namespace directly.
     */
    internal fun getIconResourceIdFromString(iconName: String, style: IconStyle): Int? {
        // Find the icon object by value from the Icons namespace
        val iconNameObj = findIconNameByValue(iconName)
        return iconNameObj?.let { getIconResourceId(it, style) }
    }
    
    /**
     * Find an icon object by its value string.
     * This is used for XML attribute parsing where we only have the string.
     * 
     * Note: This is a fallback for XML parsing. For better performance and type safety,
     * use the icon objects from the [Icons] namespace directly in code (e.g., Icons.ArrowDown).
     */
    private fun findIconNameByValue(value: String): IconName? {
        // Since icon objects are in Icons namespace, we reference them through Icons
        return when (value) {
            Icons.Share05.value -> Icons.Share05
            Icons.Share06.value -> Icons.Share06
            // Add more as needed for XML compatibility
            else -> null
        }
    }
}
