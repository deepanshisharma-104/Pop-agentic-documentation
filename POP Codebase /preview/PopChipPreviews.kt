package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.PopChip
import com.pop.components.ds_components.PopChipMode
import com.pop.components.ds_components.PopChipVariant
import com.pop.components.models.PopChipConfig
import com.pop.components.theme.IconSize
import com.pop.components.theme.IconStyle
import com.pop.components.theme.Icons
import com.pop.components.theme.IconColor
import com.pop.components.theme.PopIconConfig
import com.pop.components.theme.SurfaceColor

/**
 * Preview showing all PopChip variants in a grid layout matching Figma design
 * 
 * Grid layout: 3 rows × 4 columns
 * - Row 1: Basic chips (enabled, disabled, enabled, disabled)
 * - Row 2: Chips with close icon (enabled, disabled, enabled with close, disabled with close)
 * - Row 3: Chips with dropdown (enabled, disabled, enabled, disabled)
 */
@Preview(
    name = "PopChip All Variants",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipAllVariants() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Row 1: Basic chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PopChip(
                config = PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.Basic,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            )
            PopChip(
                config = PopChipConfig(
                    text = "Label",
                    enabled = false,
                    variant = PopChipVariant.Basic,
                    mode = PopChipMode.Static
                )
            )
            PopChip(
                config = PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.Basic,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            )
            PopChip(
                config = PopChipConfig(
                    text = "Label",
                    enabled = false,
                    variant = PopChipVariant.Basic,
                    mode = PopChipMode.Static
                )
            )
        }
        
        // Row 2: Chips with close icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PopChip(
                config = PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.Basic,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            )
            PopChip(
                config = PopChipConfig(
                    text = "Label",
                    enabled = false,
                    variant = PopChipVariant.Basic,
                    mode = PopChipMode.Static
                )
            )
            PopChip(
                config = PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithClose,
                    mode = PopChipMode.Static,
                    onClick = {},
                    onCloseClick = {}
                )
            )
            PopChip(
                config = PopChipConfig(
                    text = "Label",
                    enabled = false,
                    variant = PopChipVariant.WithClose,
                    mode = PopChipMode.Static
                )
            )
        }
        
        // Row 3: Chips with dropdown
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PopChip(
                config = PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            )
            PopChip(
                config = PopChipConfig(
                    text = "Label",
                    enabled = false,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static
                )
            )
            PopChip(
                config = PopChipConfig(
                    text = "Label",
                    enabled = true,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static,
                    onClick = {}
                )
            )
            PopChip(
                config = PopChipConfig(
                    text = "Label",
                    enabled = false,
                    variant = PopChipVariant.WithDropdown,
                    mode = PopChipMode.Static
                )
            )
        }
    }
}

/**
 * Preview showing enabled basic chip
 */
@Preview(
    name = "PopChip Basic Enabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipBasicEnabled() {
    PopChip(
        config = PopChipConfig(
            text = "Label",
            enabled = true,
            variant = PopChipVariant.Basic,
            mode = PopChipMode.Static,
            onClick = {}
        ),
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp)
    )
}

/**
 * Preview showing disabled basic chip
 */
@Preview(
    name = "PopChip Basic Disabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipBasicDisabled() {
    PopChip(
        config = PopChipConfig(
            text = "Label",
            enabled = false,
            variant = PopChipVariant.Basic,
            mode = PopChipMode.Static
        ),
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp)
    )
}

/**
 * Preview showing enabled chip with close icon
 */
@Preview(
    name = "PopChip WithClose Enabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipWithCloseEnabled() {
    PopChip(
        config = PopChipConfig(
            text = "Label",
            enabled = true,
            variant = PopChipVariant.WithClose,
            mode = PopChipMode.Static,
            onClick = {},
            onCloseClick = {}
        ),
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp)
    )
}

/**
 * Preview showing disabled chip with close icon
 */
@Preview(
    name = "PopChip WithClose Disabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipWithCloseDisabled() {
    PopChip(
        config = PopChipConfig(
            text = "Label",
            enabled = false,
            variant = PopChipVariant.WithClose,
            mode = PopChipMode.Static
        ),
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp)
    )
}

/**
 * Preview showing enabled chip with dropdown icon
 */
@Preview(
    name = "PopChip WithDropdown Enabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipWithDropdownEnabled() {
    PopChip(
        config = PopChipConfig(
            text = "Label",
            enabled = true,
            variant = PopChipVariant.WithDropdown,
            mode = PopChipMode.Static,
            onClick = {}
        ),
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp)
    )
}

/**
 * Preview showing disabled chip with dropdown icon
 */
@Preview(
    name = "PopChip WithDropdown Disabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipWithDropdownDisabled() {
    PopChip(
        config = PopChipConfig(
            text = "Label",
            enabled = false,
            variant = PopChipVariant.WithDropdown,
            mode = PopChipMode.Static
        ),
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp)
    )
}


/**
 * Preview showing active/toggled chip with border
 */
@Preview(
    name = "PopChip Active State",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipActive() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopChip(
            config = PopChipConfig(
                text = "Active Chip",
                enabled = true,
                isActive = true,
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Static,
                onClick = {}
            )
        )
        PopChip(
            config = PopChipConfig(
                text = "Inactive Chip",
                enabled = true,
                isActive = false,
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Static,
                onClick = {}
            )
        )
        PopChip(
            config = PopChipConfig(
                text = "Active with Close",
                enabled = true,
                isActive = true,
                variant = PopChipVariant.WithClose,
                mode = PopChipMode.Static,
                onClick = {},
                onCloseClick = {}
            )
        )
        PopChip(
            config = PopChipConfig(
                text = "Active with Dropdown",
                enabled = true,
                isActive = true,
                variant = PopChipVariant.WithDropdown,
                mode = PopChipMode.Static,
                onClick = {}
            )
        )
    }
}

/**
 * Preview showing chip with leading icon
 */
@Preview(
    name = "PopChip With Leading Icon",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopChipWithLeadingIcon() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopChip(
            config = PopChipConfig(
                text = "Label",
                enabled = true,
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Static,
                leadingIcon = PopIconConfig(
                    iconName = Icons.Share06,
                    style = IconStyle.Outline,
                    size = IconSize.Small,
                    tint = IconColor.Primary
                ),
                onClick = {}
            )
        )
        PopChip(
            config = PopChipConfig(
                text = "Label",
                enabled = true,
                isActive = true,
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Static,
                leadingIcon = PopIconConfig(
                    iconName = Icons.Share06,
                    style = IconStyle.Outline,
                    size = IconSize.Small,
                    tint = IconColor.Primary
                ),
                onClick = {}
            )
        )
        PopChip(
            config = PopChipConfig(
                text = "Label",
                enabled = false,
                variant = PopChipVariant.Basic,
                mode = PopChipMode.Static,
                leadingIcon = PopIconConfig(
                    iconName = Icons.Share06,
                    style = IconStyle.Outline,
                    size = IconSize.Small,
                    tint = IconColor.Primary
                )
            )
        )
    }
}

