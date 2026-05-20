package com.pop.components.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.pop.components.theme.IconName

/**
 * Animation type for app bar icon slots
 */
enum class IconAnimationType {
    /** Fade in/out animation */
    Dissolve,
    /** 90-degree rotation animation */
    Rotate90,
    /** Combined rotation and dissolve */
    RotateDissolve
}

/**
 * Represents a single icon slot in the app bar
 *
 * @property icon The icon to display
 * @property onClick Callback when the icon is clicked
 * @property contentDescription Content description for accessibility
 * @property animationType Type of animation when showing/hiding
 * @property iconTint Optional tint color for the icon (null uses default contentColor)
 */
@Immutable
data class AppBarIconSlot(
    val icon: IconName,
    val onClick: () -> Unit,
    val contentDescription: String? = null,
    val animationType: IconAnimationType = IconAnimationType.Dissolve,
    val iconTint: Color? = null
)

/**
 * Container for 4 optional icon slots in the app bar
 * Icons are displayed from left to right before the composable slot
 *
 * @property slot1 First icon slot (leftmost)
 * @property slot2 Second icon slot
 * @property slot3 Third icon slot
 * @property slot4 Fourth icon slot (rightmost, before composable slot)
 */
@Immutable
data class AppBarIconSlots(
    val slot1: AppBarIconSlot? = null,
    val slot2: AppBarIconSlot? = null,
    val slot3: AppBarIconSlot? = null,
    val slot4: AppBarIconSlot? = null
)
