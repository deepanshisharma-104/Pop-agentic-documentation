package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.PopChipMode
import com.pop.components.ds_components.PopChipStack
import com.pop.components.ds_components.PopChipStackSize
import com.pop.components.ds_components.PopChipVariant
import com.pop.components.models.PopChipConfig
import com.pop.components.theme.SurfaceColor

/**
 * Preview showing PopChipStack with 2 chips (Large size)
 */
@Preview(
    name = "PopChipStack - 2 Chips (Large)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStack2ChipsLarge() {
    PopChipStack(
        chips = listOf(
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            )
        ),
        size = PopChipStackSize.Large,
        showDivider = true,
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack with 3 chips (Large size)
 */
@Preview(
    name = "PopChipStack - 3 Chips (Large)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStack3ChipsLarge() {
    PopChipStack(
        chips = listOf(
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            )
        ),
        size = PopChipStackSize.Large,
        showDivider = true,
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack with 4 chips (Large size)
 */
@Preview(
    name = "PopChipStack - 4 Chips (Large)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStack4ChipsLarge() {
    PopChipStack(
        chips = listOf(
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            )
        ),
        size = PopChipStackSize.Large,
        showDivider = true,
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack with 5 chips (Large size)
 */
@Preview(
    name = "PopChipStack - 5 Chips (Large)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStack5ChipsLarge() {
    PopChipStack(
        chips = listOf(
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            )
        ),
        size = PopChipStackSize.Large,
        showDivider = true,
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack with many chips and overflow indicators (Large size)
 */
@Preview(
    name = "PopChipStack - Many Chips with Overflow (Large)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStackManyChipsWithOverflowLarge() {
    PopChipStack(
        chips = List(9) {
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            )
        },
        size = PopChipStackSize.Large,
        showDivider = true,
        showLeftOverflow = true,
        showRightOverflow = true,
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack with 2 chips (Med size)
 */
@Preview(
    name = "PopChipStack - 2 Chips (Med)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStack2ChipsMed() {
    PopChipStack(
        chips = listOf(
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            )
        ),
        size = PopChipStackSize.Med,
        showDivider = true,
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack with 3 chips (Med size)
 */
@Preview(
    name = "PopChipStack - 3 Chips (Med)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStack3ChipsMed() {
    PopChipStack(
        chips = listOf(
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            )
        ),
        size = PopChipStackSize.Med,
        showDivider = true,
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack with 4 chips (Med size)
 */
@Preview(
    name = "PopChipStack - 4 Chips (Med)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStack4ChipsMed() {
    PopChipStack(
        chips = listOf(
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            )
        ),
        size = PopChipStackSize.Med,
        showDivider = true,
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack with 5 chips (Med size)
 */
@Preview(
    name = "PopChipStack - 5 Chips (Med)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStack5ChipsMed() {
    PopChipStack(
        chips = listOf(
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            )
        ),
        size = PopChipStackSize.Med,
        showDivider = true,
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack with many chips and overflow indicators (Med size)
 */
@Preview(
    name = "PopChipStack - Many Chips with Overflow (Med)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStackManyChipsWithOverflowMed() {
    PopChipStack(
        chips = List(9) {
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            )
        },
        size = PopChipStackSize.Med,
        showDivider = true,
        showLeftOverflow = true,
        showRightOverflow = true,
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack without divider
 */
@Preview(
    name = "PopChipStack - No Divider",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStackNoDivider() {
    PopChipStack(
        chips = listOf(
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            ),
            PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            )
        ),
        size = PopChipStackSize.Large,
        showDivider = false,
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing all PopChipStack variants in a column
 */
@Preview(
    name = "PopChipStack - All Variants",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStackAllVariants() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 2 chips Large
        PopChipStack(
            chips = listOf(
                PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                ),
                PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            ),
            size = PopChipStackSize.Large,
            showDivider = true
        )
        
        // 3 chips Large
        PopChipStack(
            chips = List(3) {
                PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            },
            size = PopChipStackSize.Large,
            showDivider = true
        )
        
        // 4 chips Large
        PopChipStack(
            chips = List(4) {
                PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            },
            size = PopChipStackSize.Large,
            showDivider = true
        )
        
        // 5 chips Large
        PopChipStack(
            chips = List(5) {
                PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            },
            size = PopChipStackSize.Large,
            showDivider = true
        )
        
        // Many chips with overflow Large
        PopChipStack(
            chips = List(9) {
                PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            },
            size = PopChipStackSize.Large,
            showDivider = true,
            showLeftOverflow = true,
            showRightOverflow = true
        )
        
        // 2 chips Med
        PopChipStack(
            chips = List(2) {
                PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            },
            size = PopChipStackSize.Med,
            showDivider = true
        )
        
        // 3 chips Med
        PopChipStack(
            chips = List(3) {
                PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            },
            size = PopChipStackSize.Med,
            showDivider = true
        )
        
        // 4 chips Med
        PopChipStack(
            chips = List(4) {
                PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            },
            size = PopChipStackSize.Med,
            showDivider = true
        )
        
        // 5 chips Med
        PopChipStack(
            chips = List(5) {
                PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            },
            size = PopChipStackSize.Med,
            showDivider = true
        )
        
        // Many chips with overflow Med
        PopChipStack(
            chips = List(9) {
                PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            },
            size = PopChipStackSize.Med,
            showDivider = true,
            showLeftOverflow = true,
            showRightOverflow = true
        )
    }
}

/**
 * Preview showing PopChipStack with single-select mode
 * Only one chip can be selected at a time
 */
@Preview(
    name = "PopChipStack - Single Select",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStackSingleSelect() {
    PopChipStack(
        chips = listOf(
            PopChipConfig(
                text = "Option 1",
                enabled = true,
                isActive = true, // Pre-selected
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Toggleable,
                onClick = {}
            ),
            PopChipConfig(
                text = "Option 2",
                enabled = true,
                isActive = false,
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Toggleable,
                onClick = {}
            ),
            PopChipConfig(
                text = "Option 3",
                enabled = true,
                isActive = false,
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Toggleable,
                onClick = {}
            ),
            PopChipConfig(
                text = "Option 4",
                enabled = true,
                isActive = false,
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Toggleable,
                onClick = {}
            )
        ),
        size = PopChipStackSize.Large,
        showDivider = true,
        singleSelect = true,
        onChipSelected = { index, chipConfig ->
            // Selection callback - in preview this is a no-op
        },
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack with multi-select mode
 * Multiple chips can be selected simultaneously
 */
@Preview(
    name = "PopChipStack - Multi Select",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStackMultiSelect() {
    PopChipStack(
        chips = listOf(
            PopChipConfig(
                text = "Filter 1",
                enabled = true,
                isActive = true, // Pre-selected
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Toggleable,
                onClick = {}
            ),
            PopChipConfig(
                text = "Filter 2",
                enabled = true,
                isActive = true, // Pre-selected
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Toggleable,
                onClick = {}
            ),
            PopChipConfig(
                text = "Filter 3",
                enabled = true,
                isActive = false,
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Toggleable,
                onClick = {}
            ),
            PopChipConfig(
                text = "Filter 4",
                enabled = true,
                isActive = false,
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Toggleable,
                onClick = {}
            ),
            PopChipConfig(
                text = "Filter 5",
                enabled = true,
                isActive = false,
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Toggleable,
                onClick = {}
            )
        ),
        size = PopChipStackSize.Large,
        showDivider = true,
        singleSelect = false, // Multi-select mode
        onChipSelected = { index, chipConfig ->
            // Selection callback - in preview this is a no-op
        },
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack with single-select mode and different variants
 */
@Preview(
    name = "PopChipStack - Single Select with Variants",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStackSingleSelectWithVariants() {
    PopChipStack(
        chips = listOf(
            PopChipConfig(
                text = "Basic",
                enabled = true,
                isActive = true,
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Toggleable,
                onClick = {}
            ),
            PopChipConfig(
                text = "With Close",
                enabled = true,
                isActive = false,
                variant = PopChipVariant.WithClose,
                mode = PopChipMode.Toggleable,
                onClick = {},
                onCloseClick = {}
            ),
            PopChipConfig(
                text = "Dropdown",
                enabled = true,
                isActive = false,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Toggleable,
                onClick = {}
            )
        ),
        size = PopChipStackSize.Large,
        showDivider = true,
        singleSelect = true,
        onChipSelected = { index, chipConfig ->
            // Selection callback
        },
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .fillMaxWidth()
    )
}

/**
 * Preview showing PopChipStack selection states side by side
 */
@Preview(
    name = "PopChipStack - Selection Comparison",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipStackSelectionComparison() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Single-select mode
        PopChipStack(
            chips = listOf(
                PopChipConfig(
                    text = "Single 1",
                    enabled = true,
                    isActive = true,
                    variant = PopChipVariant.Basic,
                    mode = PopChipMode.Toggleable,
                    onClick = {}
                ),
                PopChipConfig(
                    text = "Single 2",
                    enabled = true,
                    isActive = false,
                    variant = PopChipVariant.Basic,
                    mode = PopChipMode.Toggleable,
                    onClick = {}
                ),
                PopChipConfig(
                    text = "Single 3",
                    enabled = true,
                    isActive = false,
                    variant = PopChipVariant.Basic,
                    mode = PopChipMode.Toggleable,
                    onClick = {}
                )
            ),
            size = PopChipStackSize.Large,
            showDivider = true,
            singleSelect = true,
            onChipSelected = { _, _ -> }
        )
        
        // Multi-select mode
        PopChipStack(
            chips = listOf(
                PopChipConfig(
                    text = "Multi 1",
                    enabled = true,
                    isActive = true,
                    variant = PopChipVariant.Basic,
                    mode = PopChipMode.Toggleable,
                    onClick = {}
                ),
                PopChipConfig(
                    text = "Multi 2",
                    enabled = true,
                    isActive = true,
                    variant = PopChipVariant.Basic,
                    mode = PopChipMode.Toggleable,
                    onClick = {}
                ),
                PopChipConfig(
                    text = "Multi 3",
                    enabled = true,
                    isActive = false,
                    variant = PopChipVariant.Basic,
                    mode = PopChipMode.Toggleable,
                    onClick = {}
                )
            ),
            size = PopChipStackSize.Large,
            showDivider = true,
            singleSelect = false,
            onChipSelected = { _, _ -> }
        )
    }
}

