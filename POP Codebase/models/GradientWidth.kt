package com.pop.components.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Enum for gradient width options
 */
@Immutable
enum class GradientWidth(val widthDp: Dp) {
    Small(12.dp),
    Big(56.dp);
    
    companion object {
        /**
         * Automatically selects gradient width based on text width in pixels
         * If text width < 160px -> Small (12dp), else Big (56dp)
         */
        fun fromTextWidth(textWidthPx: Float): GradientWidth {
            return if (textWidthPx < 160f) {
                Small
            } else {
                Big
            }
        }
    }
}

