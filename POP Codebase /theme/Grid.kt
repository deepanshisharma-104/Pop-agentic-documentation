package com.pop.components.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

object FlashGrid {
    object MobileGrid {
        val horizontalPadding = 16.dp
        val verticalGutter = 27.dp
        val contentGutter = 20.dp
        val rowOffset = 43.dp
        
        val contentPadding = PaddingValues(
            horizontal = horizontalPadding,
            vertical = verticalGutter
        )
        
        val itemSpacing = 20.dp
    }
}

// Grid system modifiers and extensions
object GridDefaults {
    val DefaultHorizontalPadding = FlashGrid.MobileGrid.horizontalPadding
    val DefaultVerticalGutter = FlashGrid.MobileGrid.verticalGutter
    val DefaultContentGutter = FlashGrid.MobileGrid.contentGutter
    val DefaultRowOffset = FlashGrid.MobileGrid.rowOffset
    
    val DefaultContentPadding = FlashGrid.MobileGrid.contentPadding
    val DefaultItemSpacing = FlashGrid.MobileGrid.itemSpacing
} 