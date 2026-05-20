package com.pop.components.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.pop.components.ds_components.PopTopBarScrimType
import com.pop.components.theme.IconName
import com.pop.components.theme.Icons

/**
 * Configuration data class for PopAppBar component
 *
 * @property title Title text for the app bar (nullable)
 * @property navigationIcon Navigation icon (typically back button) - nullable
 * @property onNavigationClick Callback when navigation icon is clicked
 * @property showNavigationIcon Whether to show the navigation icon (default: true)
 * @property navigationIconTint Optional tint color for navigation icon (null uses default contentColor)
 * @property iconSlots Container for 4 optional icon slots with animations
 * @property backgroundColor Background color of the app bar (default: transparent)
 * @property contentColor Color for icons and text (default: white)
 * @property scrollScrimType Single-layer gradient scrim style when app bar scroll scrim is visible
 * @property showDivider Whether to show a bottom divider when app bar has background/scrim
 */
@Immutable
data class PopAppBarConfig(
    val title: String? = null,
    val navigationIcon: IconName? = null,
    val onNavigationClick: (() -> Unit)? = null,
    val showNavigationIcon: Boolean = false,
    val navigationIconTint: Color? = null,
    val iconSlots: AppBarIconSlots = AppBarIconSlots(),
    val backgroundColor: Color = Color.Transparent,
    val contentColor: Color = Color.White,
    val scrollScrimType: PopTopBarScrimType = PopTopBarScrimType.Solid90,
    val showDivider: Boolean = true
) {
    companion object {
        /**
         * Creates a default PopAppBarConfig for preview purposes
         */
        fun default(
            title: String = "",
            navigationIcon: IconName? = null
        ) = PopAppBarConfig(
            title = title,
            navigationIcon = navigationIcon
        )
    }
}


