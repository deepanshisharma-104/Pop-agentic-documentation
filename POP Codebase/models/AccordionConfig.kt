package com.pop.components.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.pop.components.theme.PopColors

/**
 * Separator style for Accordion
 */
enum class AccordionSeparatorStyle {
    /** Full width solid separator */
    FullWidth,
    
    /** Dashed separator */
    Dashed
}

/**
 * Separator color options for Accordion
 */
enum class AccordionSeparatorColor {
    /** Primary color (default) */
    Primary,
    
    /** Secondary color */
    Secondary,
    
    /** Custom color (specified via separatorColor) */
    Custom
}

/**
 * Configuration data class for Accordion component
 *
 * @property titleBarConfig Configuration for the PopTitleBar in the header
 * @property setBg Whether to show background color (default: false)
 * @property backgroundColor Background color (default: PopColors.Neutral.N3)
 * @property showBorder Whether to show border around the entire accordion (default: false)
 * @property borderColor Border color (default: PopColors.Neutral.N5)
 * @property borderWidthDp Border width in dp (default: 0.5dp)
 * @property borderRadiusDp Border radius in dp (default: 12dp)
 * @property showSeparator Whether to show separator between header and content (default: true)
 * @property separatorStyle Style of separator - FullWidth or Dashed (default: FullWidth)
 * @property separatorColor Color option for separator - Primary, Secondary, or Custom (default: Secondary)
 * @property customSeparatorColor Custom separator color (only used when separatorColor is Custom)
 * @property isInitiallyExpanded Whether accordion should be initially expanded (default: false)
 * @property animationDurationMillis Duration of expand/collapse animation in milliseconds (default: 300)
 */
@Immutable
data class AccordionConfig(
    val titleBarConfig: PopTitleBarConfig,
    val setBg: Boolean = false,
    val backgroundColor: Color = PopColors.Neutral.N3,
    val showBorder: Boolean = false,
    val borderColor: Color = PopColors.Neutral.N5,
    val borderWidthDp: Float = 0.5f,
    val borderRadiusDp: Float = 12f,
    val showSeparator: Boolean = true,
    val separatorStyle: AccordionSeparatorStyle = AccordionSeparatorStyle.FullWidth,
    val separatorColor: AccordionSeparatorColor = AccordionSeparatorColor.Secondary,
    val customSeparatorColor: Color? = null,
    val isInitiallyExpanded: Boolean = false,
    val animationDurationMillis: Int = 300
) {
    companion object {
        /**
         * Creates a default AccordionConfig for preview purposes
         */
        fun default(
            title: String = "Title",
            body: String? = null,
            showBorder: Boolean = false,
            separatorStyle: AccordionSeparatorStyle = AccordionSeparatorStyle.FullWidth
        ) = AccordionConfig(
            titleBarConfig = PopTitleBarConfig.default(title, body),
            showBorder = showBorder,
            separatorStyle = separatorStyle
        )
    }
}

