package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.ButtonSize
import com.pop.components.ds_components.ButtonState
import com.pop.components.ds_components.ButtonVariant
import com.pop.components.ds_components.ButtonLoadingState
import com.pop.components.ds_components.ButtonWidthType
import com.pop.components.ds_components.PopButtonV2
import com.pop.components.ds_components.PopIconButtonV2
import com.pop.components.models.VisualElement
import com.pop.components.theme.PopColors
import com.pop.components.theme.PopTheme
import com.pop.components.theme.TextColors
import com.pop.compose_components.R

// ============================================================================
// Previews for all button types from Figma
// ============================================================================

@Preview(name = "Primary - Large")
@Composable
fun PopButtonPrimaryLargePreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Primary,
        state = ButtonState.Active,
        size = ButtonSize.Large
    )
}

@Preview(name = "Primary - Medium")
@Composable
fun PopButtonPrimaryMediumPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Primary,
        state = ButtonState.Active,
        size = ButtonSize.Medium
    )
}

@Preview(name = "Primary - Small")
@Composable
fun PopButtonPrimarySmallPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Primary,
        state = ButtonState.Active,
        size = ButtonSize.Small
    )
}

@Preview(name = "Primary - XSmall")
@Composable
fun PopButtonPrimaryXSmallPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Primary,
        state = ButtonState.Active,
        size = ButtonSize.XSmall
    )
}

@Preview(name = "Primary - With Leading Icon")
@Composable
fun PopButtonPrimaryLeadingIconPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Primary,
        state = ButtonState.Active,
        leftIcon = VisualElement.buildFrom(R.drawable.ic_button_arrow)
    )
}

@Preview(name = "Primary - With Trailing Icon")
@Composable
fun PopButtonPrimaryTrailingIconPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Primary,
        state = ButtonState.Active,
        rightIcon = VisualElement.buildFrom(R.drawable.ic_button_arrow)
    )
}

@Preview(name = "Primary - Loading")
@Composable
fun PopButtonPrimaryLoadingPreview() {
    PopButtonV2(
        text = "",
        variant = ButtonVariant.Primary,
        state = ButtonState.Loading,
        buttonLoadingState = ButtonLoadingState.Default
    ){}
}

@Preview(name = "Primary - Loading Success")
@Composable
fun PopButtonPrimaryLoadingSuccessPreview() {
    PopButtonV2(
        text = "",
        variant = ButtonVariant.Primary,
        state = ButtonState.Loading,
        buttonLoadingState = ButtonLoadingState.Success
    ){}
}

@Preview(name = "Primary - Loading Destructive")
@Composable
fun PopButtonPrimaryLoadingDestructivePreview() {
    PopButtonV2(
        text = "Label",
        variant = ButtonVariant.Primary,
        state = ButtonState.Loading,
        buttonLoadingState = ButtonLoadingState.Destructive
    ){}
}

@Preview(name = "Primary - Disabled", showBackground = true)
@Composable
fun PopButtonPrimaryDisabledPreview() {
    PopButtonV2(
        text = "Label",
        variant = ButtonVariant.Primary,
        state = ButtonState.Disabled
    ){}
}

@Preview(name = "Primary - Destructive")
@Composable
fun PopButtonPrimaryDestructivePreview() {
    PopButtonV2(
        text = "Label",
        variant = ButtonVariant.Primary,
        state = ButtonState.Destructive
    ){}
}

@Preview(name = "Secondary - Large")
@Composable
fun PopButtonSecondaryLargePreview() {
    PopButtonV2(
        text = "Label",
        variant = ButtonVariant.Secondary,
        state = ButtonState.Active,
        size = ButtonSize.Large
    ){}
}

@Preview(name = "Secondary - XSmall")
@Composable
fun PopButtonSecondaryXSmallPreview() {
    PopButtonV2(
        text = "Transfer",
        onClick = {},
        variant = ButtonVariant.Secondary,
        state = ButtonState.Active,
        size = ButtonSize.XSmall
    )
}

@Preview(name = "Secondary - Small")
@Composable
fun PopButtonSecondarySmallPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Secondary,
        state = ButtonState.Active,
        size = ButtonSize.Small
    )
}

@Preview(name = "Secondary - With Icons")
@Composable
fun PopButtonSecondaryWithIconsPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Secondary,
        state = ButtonState.Active,
        size = ButtonSize.Medium,
        leftIcon = VisualElement.buildFrom(R.drawable.ic_button_arrow),
        rightIcon = VisualElement.buildFrom(R.drawable.ic_button_arrow)
    )
}

@Preview(name = "Secondary - Loading")
@Composable
fun PopButtonSecondaryLoadingPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Secondary,
        state = ButtonState.Loading,
        buttonLoadingState = ButtonLoadingState.Default
    )
}

@Preview(name = "Secondary - Disabled")
@Composable
fun PopButtonSecondaryDisabledPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Secondary,
        state = ButtonState.Disabled
    )
}

@Preview(name = "Tertiary - Large")
@Composable
fun PopButtonTertiaryLargePreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Tertiary,
        state = ButtonState.Active,
        size = ButtonSize.Large
    )
}

@Preview(name = "Tertiary - Small")
@Composable
fun PopButtonTertiarySmallPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Tertiary,
        state = ButtonState.Active,
        size = ButtonSize.Small
    )
}

@Preview(name = "Tertiary - Disabled")
@Composable
fun PopButtonTertiaryDisabledPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Tertiary,
        state = ButtonState.Disabled
    )
}

// Brand Primary Variant
@Preview(name = "Brand Primary - Large Fill")
@Composable
fun PopButtonBrandPrimaryLargeFillPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        widthType = ButtonWidthType.Fill,
        variant = ButtonVariant.BrandPrimary,
        state = ButtonState.Active,
        size = ButtonSize.Large
    )
}

// Brand Primary Variant
@Preview(name = "Brand Primary - Large")
@Composable
fun PopButtonBrandPrimaryLargePreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.BrandPrimary,
        state = ButtonState.Active,
        size = ButtonSize.Large
    )
}

// White Variant
@Preview(name = "White - Large")
@Composable
fun PopButtonWhiteLargePreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.White,
        state = ButtonState.Active,
        size = ButtonSize.Large
    )
}

// Ghost Variants
@Preview(name = "Ghost - Large")
@Composable
fun PopButtonGhostLargePreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Ghost,
        state = ButtonState.Active,
        size = ButtonSize.Large
    )
}

@Preview(name = "Ghost + R Icon")
@Composable
fun PopButtonGhostRIconPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Ghost,
        state = ButtonState.Active,
        rightIcon = VisualElement.buildFrom(R.drawable.ic_button_arrow)
    )
}

@Preview(name = "Ghost + L Icon")
@Composable
fun PopButtonGhostLIconPreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.Ghost,
        state = ButtonState.Active,
        leftIcon = VisualElement.buildFrom(R.drawable.ic_button_arrow)
    )
}

@Preview(name = "Brand Ghost - Large")
@Composable
fun PopButtonBrandGhostLargePreview() {
    PopButtonV2(
        text = "Label",
        onClick = {},
        variant = ButtonVariant.BrandGhost,
        state = ButtonState.Active,
        size = ButtonSize.Large
    )
}

// Icon Button Previews
@Preview(name = "Icon Button - Primary Large")
@Composable
fun PopIconButtonPrimaryLargePreview() {
    PopIconButtonV2(
        onClick = {},
        contentDescription = "Primary icon button",
        variant = ButtonVariant.Primary,
        state = ButtonState.Active,
        size = ButtonSize.Large,
    )
}

@Preview(name = "Icon Button - Primary Medium")
@Composable
fun PopIconButtonPrimaryMediumPreview() {
    PopIconButtonV2(
        onClick = {},
        contentDescription = "Primary icon button",
        variant = ButtonVariant.Primary,
        state = ButtonState.Active,
        size = ButtonSize.Medium,
    )
}

@Preview(name = "Icon Button - Primary Small")
@Composable
fun PopIconButtonPrimarySmallPreview() {
    PopIconButtonV2(
        onClick = {},
        contentDescription = "Primary icon button",
        variant = ButtonVariant.Primary,
        state = ButtonState.Active,
        size = ButtonSize.Small,
    )
}

@Preview(name = "Icon Button - Primary XSmall")
@Composable
fun PopIconButtonPrimaryXSmallPreview() {
    PopIconButtonV2(
        onClick = {},
        contentDescription = "Primary icon button",
        variant = ButtonVariant.Primary,
        state = ButtonState.Active,
        size = ButtonSize.XSmall,
    )
}

@Preview(name = "Icon Button - Secondary Large")
@Composable
fun PopIconButtonSecondaryLargePreview() {
    PopIconButtonV2(
        onClick = {},
        contentDescription = "Secondary icon button",
        variant = ButtonVariant.Secondary,
        state = ButtonState.Active,
        size = ButtonSize.Large,
    )
}

@Preview(name = "Icon Button - Secondary Small")
@Composable
fun PopIconButtonSecondarySmallPreview() {
    PopIconButtonV2(
        onClick = {},
        contentDescription = "Secondary icon button",
        variant = ButtonVariant.Secondary,
        state = ButtonState.Active,
        size = ButtonSize.Small,
    )
}

@Preview(name = "Icon Button - Tertiary Large")
@Composable
fun PopIconButtonTertiaryLargePreview() {
    PopIconButtonV2(
        onClick = {},
        contentDescription = "Tertiary icon button",
        variant = ButtonVariant.Tertiary,
        state = ButtonState.Active,
        size = ButtonSize.Large,
    )
}

@Preview(name = "Icon Button - Primary Disabled")
@Composable
fun PopIconButtonPrimaryDisabledPreview() {
    PopIconButtonV2(
        onClick = {},
        contentDescription = "Primary icon button",
        variant = ButtonVariant.Primary,
        state = ButtonState.Disabled,
    )
}

@Preview(name = "Icon Button - Secondary Disabled")
@Composable
fun PopIconButtonSecondaryDisabledPreview() {
    PopIconButtonV2(
        onClick = {},
        contentDescription = "Secondary icon button",
        variant = ButtonVariant.Secondary,
        state = ButtonState.Disabled,
    )
}

@Preview(name = "Icon Button - Primary Loading")
@Composable
fun PopIconButtonPrimaryLoadingPreview() {
    PopIconButtonV2(
        onClick = {},
        contentDescription = "Primary icon button",
        variant = ButtonVariant.Primary,
        state = ButtonState.Loading,
        buttonLoadingState = ButtonLoadingState.Default
    )
}

@Preview(name = "Icon Button - Primary Destructive")
@Composable
fun PopIconButtonPrimaryDestructivePreview() {
    PopIconButtonV2(
        onClick = {},
        contentDescription = "Primary destructive icon button",
        variant = ButtonVariant.Primary,
        state = ButtonState.Destructive
    )
}

@Preview(name = "Icon Button - Primary Loading Success")
@Composable
fun PopIconButtonPrimaryLoadingSuccessPreview() {
    PopIconButtonV2(
        onClick = {},
        contentDescription = "Primary loading success icon button",
        variant = ButtonVariant.Primary,
        state = ButtonState.Loading,
        buttonLoadingState = ButtonLoadingState.Success
    )
}

// Helper functions for comprehensive button previews (matching EverythingUpi.kt)
@Composable
private fun ButtonVariantRows(
    variant: ButtonVariant,
    size: ButtonSize,
    modifier: Modifier = Modifier
) {
    val variantName = when (variant) {
        ButtonVariant.Primary -> "Primary"
        ButtonVariant.Secondary -> "Secondary"
        ButtonVariant.Tertiary -> "Tertiary"
        ButtonVariant.BrandPrimary -> "Brand Primary"
        ButtonVariant.White -> "White"
        ButtonVariant.Ghost -> "Ghost"
        ButtonVariant.BrandGhost -> "Brand Ghost"
        else -> variant.name
    }
    
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Row 1: Active - Label + Icon
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "$variantName - Active",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = variant,
                    state = ButtonState.Active,
                    size = size
                )
                PopIconButtonV2(
                    onClick = {},
                    variant = variant,
                    state = ButtonState.Active,
                    size = size
                )
            }
        }
        
        // Row 2: Disabled - Label + Icon
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "$variantName - Disabled",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = variant,
                    state = ButtonState.Disabled,
                    size = size
                )
                PopIconButtonV2(
                    onClick = {},
                    variant = variant,
                    state = ButtonState.Disabled,
                    size = size
                )
            }
        }
        
        // Row 3: Loading
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "$variantName - Loading",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = variant,
                    state = ButtonState.Loading,
                    buttonLoadingState = ButtonLoadingState.Default,
                    size = size
                )
            }
        }
        
        // Row 4: Loading Success
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "$variantName - Loading Success",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = variant,
                    state = ButtonState.Loading,
                    buttonLoadingState = ButtonLoadingState.Success,
                    size = size
                )
            }
        }
        
        // Row 5: Loading Destructive
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "$variantName - Loading Destructive",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = variant,
                    state = ButtonState.Loading,
                    buttonLoadingState = ButtonLoadingState.Destructive,
                    size = size
                )
            }
        }
        
        // Row 6: Destructive - Label + Icon
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "$variantName - Destructive",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = variant,
                    state = ButtonState.Destructive,
                    size = size
                )
                PopIconButtonV2(
                    onClick = {},
                    variant = variant,
                    state = ButtonState.Destructive,
                    size = size
                )
            }
        }
    }
}

@Composable
private fun GhostRIconRows(
    size: ButtonSize,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Row 1: Active - Label + Icon
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Ghost + R Icon - Active",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Active,
                    size = size,
                    rightIcon = VisualElement.buildFrom(R.drawable.ic_button_arrow)
                )
                PopIconButtonV2(
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Active,
                    size = size
                )
            }
        }
        
        // Row 2: Disabled - Label + Icon
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Ghost + R Icon - Disabled",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Disabled,
                    size = size,
                    rightIcon = VisualElement.buildFrom(R.drawable.ic_button_arrow)
                )
                PopIconButtonV2(
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Disabled,
                    size = size
                )
            }
        }
        
        // Row 3: Loading
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Ghost + R Icon - Loading",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Loading,
                    buttonLoadingState = ButtonLoadingState.Default,
                    size = size,
                    rightIcon = VisualElement.buildFrom(R.drawable.ic_button_arrow)
                )
            }
        }
        
        // Row 4: Loading Success
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Ghost + R Icon - Loading Success",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Loading,
                    buttonLoadingState = ButtonLoadingState.Success,
                    size = size
                )
            }
        }
        
        // Row 5: Loading Destructive
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Ghost + R Icon - Loading Destructive",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Loading,
                    buttonLoadingState = ButtonLoadingState.Destructive,
                    size = size
                )
            }
        }
    }
}

@Composable
private fun GhostLIconRows(
    size: ButtonSize,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Row 1: Active - Label + Icon
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Ghost + L Icon - Active",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Active,
                    size = size,
                    leftIcon = VisualElement.buildFrom(R.drawable.ic_button_arrow)
                )
                PopIconButtonV2(
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Active,
                    size = size
                )
            }
        }
        
        // Row 2: Disabled - Label + Icon
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Ghost + L Icon - Disabled",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Disabled,
                    size = size,
                    leftIcon = VisualElement.buildFrom(R.drawable.ic_button_arrow)
                )
                PopIconButtonV2(
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Disabled,
                    size = size
                )
            }
        }
        
        // Row 3: Loading
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Ghost + L Icon - Loading",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Loading,
                    buttonLoadingState = ButtonLoadingState.Default,
                    size = size,
                    leftIcon = VisualElement.buildFrom(R.drawable.ic_button_arrow)
                )
            }
        }
        
        // Row 4: Loading Success
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Ghost + L Icon - Loading Success",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Loading,
                    buttonLoadingState = ButtonLoadingState.Success,
                    size = size
                )
            }
        }
        
        // Row 5: Loading Destructive
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "Ghost + L Icon - Loading Destructive",
                style = MaterialTheme.typography.bodySmall,
                color = TextColors.Secondary.Default
            )
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                PopButtonV2(
                    text = "Label",
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                    state = ButtonState.Loading,
                    buttonLoadingState = ButtonLoadingState.Destructive,
                    size = size
                )
            }
        }
    }
}

// Comprehensive Preview - All Buttons Grid (Figma Style)
// Large canvas so the preview pane allows scrolling through all rows
@Preview(
    name = "All Buttons - Figma Grid",
    showBackground = true,
    backgroundColor = 0xFF111110,
    heightDp = 2000,
    widthDp = 420
)
@Composable
fun AllButtonsFigmaGridPreview() {
    PopTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PopColors.Neutral.N1)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Large Size
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Large Size",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ButtonVariantRows(ButtonVariant.Primary, ButtonSize.Large)
                ButtonVariantRows(ButtonVariant.Secondary, ButtonSize.Large)
                ButtonVariantRows(ButtonVariant.Tertiary, ButtonSize.Large)
                ButtonVariantRows(ButtonVariant.BrandPrimary, ButtonSize.Large)
                ButtonVariantRows(ButtonVariant.White, ButtonSize.Large)
                GhostRIconRows(ButtonSize.Large)
                GhostLIconRows(ButtonSize.Large)
            }

            // Medium Size
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Medium Size",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ButtonVariantRows(ButtonVariant.Primary, ButtonSize.Medium)
                ButtonVariantRows(ButtonVariant.Secondary, ButtonSize.Medium)
                ButtonVariantRows(ButtonVariant.Tertiary, ButtonSize.Medium)
                ButtonVariantRows(ButtonVariant.BrandPrimary, ButtonSize.Medium)
                ButtonVariantRows(ButtonVariant.White, ButtonSize.Medium)
                GhostRIconRows(ButtonSize.Medium)
                GhostLIconRows(ButtonSize.Medium)
            }

            // Small Size
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Small Size",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ButtonVariantRows(ButtonVariant.Primary, ButtonSize.Small)
                ButtonVariantRows(ButtonVariant.Secondary, ButtonSize.Small)
                ButtonVariantRows(ButtonVariant.Tertiary, ButtonSize.Small)
                ButtonVariantRows(ButtonVariant.BrandPrimary, ButtonSize.Small)
                ButtonVariantRows(ButtonVariant.White, ButtonSize.Small)
                GhostRIconRows(ButtonSize.Small)
                GhostLIconRows(ButtonSize.Small)
            }

            // XSmall Size
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "XSmall Size",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ButtonVariantRows(ButtonVariant.Primary, ButtonSize.XSmall)
                ButtonVariantRows(ButtonVariant.Secondary, ButtonSize.XSmall)
                ButtonVariantRows(ButtonVariant.Tertiary, ButtonSize.XSmall)
                ButtonVariantRows(ButtonVariant.BrandPrimary, ButtonSize.XSmall)
                ButtonVariantRows(ButtonVariant.White, ButtonSize.XSmall)
                GhostRIconRows(ButtonSize.XSmall)
                GhostLIconRows(ButtonSize.XSmall)
            }
        }
    }
}

