package com.pop.components.models

import androidx.compose.runtime.Immutable
import com.pop.components.ds_components.PopChipMode
import com.pop.components.ds_components.PopChipVariant
import com.pop.components.theme.PopIconConfig

/**
 * Configuration data class for PopChip component
 *
 * @property text The label text to display in the chip
 * @property enabled Whether the chip is enabled (affects colors and interactions)
 * @property isActive Whether the chip is in active/toggled state (shows border when true, only used in Toggleable mode)
 * @property variant The chip variant to display
 * @property mode The chip mode - Toggleable (changes state on click) or Static (always inactive UI)
 * @property leadingIcon Optional icon to display at the start of the chip
 * @property onClick Optional callback when the chip is clicked (applied to root element)
 * @property onCloseClick Optional callback when the close icon is clicked (used with WithClose variant)
 */
@Immutable
data class PopChipConfig(
    val text: String,
    val enabled: Boolean = true,
    val isActive: Boolean = false,
    val variant: PopChipVariant = PopChipVariant.Basic,
    val mode: PopChipMode = PopChipMode.Toggleable,
    val leadingIcon: PopIconConfig? = null,
    val onClick: (() -> Unit)? = null,
    val onCloseClick: (() -> Unit)? = null
) {
    companion object {
        /**
         * Creates a default PopChipConfig for preview purposes
         */
        fun default(
            text: String = "Label",
            variant: PopChipVariant = PopChipVariant.Basic
        ) = PopChipConfig(
            text = text,
            variant = variant
        )
    }
}

