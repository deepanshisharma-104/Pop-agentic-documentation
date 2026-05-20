package com.pop.components.ds_components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.pop.components.compose_components.PopDivider
import com.pop.components.compose_components.PopDividerOrientation
import com.pop.components.compose_components.PopDividerStyle
import com.pop.components.models.PopChipConfig
import com.pop.components.theme.PopColors
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.SurfaceColor

/**
 * Size options for PopChipStack
 */
enum class PopChipStackSize {
    /** Large size with 16dp vertical padding */
    Large,
    
    /** Medium size with 12dp padding */
    Med
}

/**
 * PopChipStack component - A horizontal stack of PopChip components
 *
 * Figma: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=5835-7001&m=dev
 *
 * Features:
 * - Displays multiple chips horizontally with consistent spacing
 * - Horizontally scrollable when content exceeds available width
 * - Optional overflow indicators (gradient overlays) on left/right edges
 * - Optional divider below the chips
 * - Support for different sizes (Large, Med)
 * - Proper spacing and padding using design tokens
 * - Selection management with single-select or multi-select modes
 * - Callback when a chip is selected
 *
 * @param chips List of PopChipConfig items to display
 * @param size Size of the chip stack - affects padding
 * @param showDivider Whether to show a divider below the chips
 * @param showLeftOverflow Whether to show a gradient overlay on the left edge
 * @param showRightOverflow Whether to show a gradient overlay on the right edge
 * @param singleSelect If true, selecting a chip will deselect all other chips. If false, multiple chips can be selected simultaneously.
 * @param onChipSelected Callback invoked when a chip is selected. Receives the chip index and the chip config.
 * @param modifier Modifier for the component
 */
@Composable
fun PopChipStack(
    modifier: Modifier = Modifier,
    chips: List<PopChipConfig>,
    size: PopChipStackSize = PopChipStackSize.Large,
    showDivider: Boolean = true,
    showLeftOverflow: Boolean = false,
    showRightOverflow: Boolean = false,
    singleSelect: Boolean = false,
    onChipSelected: ((index: Int, chipConfig: PopChipConfig) -> Unit)? = null,
) {
    // Spacing tokens from design system
    val chipGap = PopSpacing.Spacing8 // 8dp gap between chips
    val horizontalPadding = PopSpacing.Spacing12 // 12dp horizontal padding
    val verticalPadding = when (size) {
        PopChipStackSize.Large -> PopSpacing.Spacing16 // 16dp vertical padding for Large
        PopChipStackSize.Med -> PopSpacing.Spacing12 // 12dp vertical padding for Med
    }
    
    // Overflow gradient width (72dp from Figma, but gradient is 56dp)
    val overflowWidth = 72.dp
    val gradientWidth = 56.dp
    
    val density = LocalDensity.current
    val gradientWidthPx = with(density) { gradientWidth.toPx() }
    val scrollState = rememberScrollState()
    
    // Track selected chip indices - initialize from chips' isActive state
    var selectedIndices by remember {
        mutableStateOf(
            chips.mapIndexedNotNull { index, chip -> 
                if (chip.isActive) index else null 
            }.toSet()
        )
    }
    
    // Sync selection state when chips list changes (e.g., external updates to isActive)
    // Use a key based on the chips list to detect changes
    LaunchedEffect(chips) {
        val newSelectedIndices = chips.mapIndexedNotNull { index, chip ->
            if (chip.isActive) index else null
        }.toSet()
        // Only update if the selection state differs (to avoid unnecessary recompositions)
        if (newSelectedIndices != selectedIndices) {
            selectedIndices = newSelectedIndices
        }
    }
    
    // Create chip configs with updated selection state
    val chipsWithSelection = chips.mapIndexed { index, chipConfig ->
        chipConfig.copy(isActive = selectedIndices.contains(index))
    }
    
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Chips container with overflow indicators
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Chips row - horizontally scrollable
            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .padding(
                        horizontal = horizontalPadding,
                        vertical = verticalPadding
                    ),
                horizontalArrangement = Arrangement.spacedBy(chipGap)
            ) {
                chipsWithSelection.forEachIndexed { index, chipConfig ->
                    PopChip(
                        config = chipConfig.copy(
                            onClick = {
                                // Handle chip selection
                                val wasSelected = selectedIndices.contains(index)
                                val newSelectedIndices = if (singleSelect) {
                                    if (wasSelected) {
                                        emptySet() // Deselect if already selected in single-select mode
                                    } else {
                                        setOf(index) // Select only this chip
                                    }
                                } else {
                                    if (wasSelected) {
                                        selectedIndices - index // Toggle off
                                    } else {
                                        selectedIndices + index // Toggle on
                                    }
                                }
                                
                                selectedIndices = newSelectedIndices
                                
                                // Call the callback with the updated chip config
                                val updatedChipConfig = chipConfig.copy(isActive = newSelectedIndices.contains(index))
                                onChipSelected?.invoke(index, updatedChipConfig)
                                
                                // Also call the original onClick if it exists
                                chipConfig.onClick?.invoke()
                            }
                        ),
                        modifier = Modifier
                    )
                }
            }
            
            // Left overflow gradient overlay
            if (showLeftOverflow) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .width(overflowWidth)
                        .height(verticalPadding * 2 + PopSpacing.Spacing28) // Approximate chip height + padding
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    SurfaceColor.Primary, // Start with background color
                                    Color.Transparent // Fade to transparent
                                ),
                                startX = 0f,
                                endX = gradientWidthPx
                            )
                        )
                )
            }
            
            // Right overflow gradient overlay
            if (showRightOverflow) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(overflowWidth)
                        .height(verticalPadding * 2 + PopSpacing.Spacing28) // Approximate chip height + padding
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent, // Start transparent
                                    SurfaceColor.Primary // Fade to background color
                                ),
                                startX = 0f,
                                endX = gradientWidthPx
                            )
                        )
                )
            }
        }
        
        // Divider below chips
        if (showDivider) {
            PopDivider(
                modifier = Modifier.fillMaxWidth(),
                orientation = PopDividerOrientation.Horizontal,
                style = PopDividerStyle.Solid,
                color = PopColors.Neutral.N5 // Secondary color from Figma (using N5 as it's close to the divider color)
            )
        }
    }
}

