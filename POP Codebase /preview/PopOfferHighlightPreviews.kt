package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.OfferHighlightBackground
import com.pop.components.ds_components.OfferHighlightIconPosition
import com.pop.components.ds_components.OfferHighlightSize
import com.pop.components.ds_components.PopOfferHighlight
import com.pop.components.theme.PopTheme
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor

@Preview(name = "PopOfferHighlight - All Sizes", showBackground = true)
@Composable
fun PreviewPopOfferHighlightAllSizes() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("All Sizes - Blue Background")
            
            PopOfferHighlight(
                amount = 1000,
                bonus = 234,
                size = OfferHighlightSize.Large,
                background = OfferHighlightBackground.Blue,
                iconPosition = OfferHighlightIconPosition.Right
            )
            
            PopOfferHighlight(
                amount = 1000,
                bonus = 234,
                size = OfferHighlightSize.Medium,
                background = OfferHighlightBackground.Blue,
                iconPosition = OfferHighlightIconPosition.Right
            )
            
            PopOfferHighlight(
                amount = 1000,
                bonus = 234,
                size = OfferHighlightSize.Small,
                background = OfferHighlightBackground.Blue,
                iconPosition = OfferHighlightIconPosition.Right
            )
        }
    }
}

@Preview(name = "PopOfferHighlight - Background Variants", showBackground = true)
@Composable
fun PreviewPopOfferHighlightBackgrounds() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Background Variants - Medium Size")
            
            SectionSubtitle("Blue Background")
            PopOfferHighlight(
                amount = 1000,
                bonus = 234,
                size = OfferHighlightSize.Medium,
                background = OfferHighlightBackground.Blue,
                iconPosition = OfferHighlightIconPosition.Right
            )
            
            SectionSubtitle("Transparent Background")
            PopOfferHighlight(
                amount = 1000,
                bonus = 234,
                size = OfferHighlightSize.Medium,
                background = OfferHighlightBackground.Transparent,
                iconPosition = OfferHighlightIconPosition.Right
            )
        }
    }
}

@Preview(name = "PopOfferHighlight - Icon Positions", showBackground = true)
@Composable
fun PreviewPopOfferHighlightIconPositions() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Icon Positions")
            
            SectionSubtitle("Icon on Right")
            PopOfferHighlight(
                amount = 1000,
                bonus = 234,
                size = OfferHighlightSize.Medium,
                background = OfferHighlightBackground.Blue,
                iconPosition = OfferHighlightIconPosition.Right
            )
            
            SectionSubtitle("Icon on Left")
            PopOfferHighlight(
                amount = 1000,
                bonus = 234,
                size = OfferHighlightSize.Medium,
                background = OfferHighlightBackground.Blue,
                iconPosition = OfferHighlightIconPosition.Left
            )
        }
    }
}

@Preview(name = "PopOfferHighlight - Without Percentage Badge", showBackground = true)
@Composable
fun PreviewPopOfferHighlightNoBadge() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Without Percentage Badge")
            
            PopOfferHighlight(
                amount = 1000,
                bonus = 234,
                size = OfferHighlightSize.Large,
                background = OfferHighlightBackground.Blue,
                showPercentageBadge = false
            )
            
            PopOfferHighlight(
                amount = 1000,
                bonus = 234,
                size = OfferHighlightSize.Medium,
                background = OfferHighlightBackground.Blue,
                showPercentageBadge = false
            )
            
            PopOfferHighlight(
                amount = 1000,
                bonus = 234,
                size = OfferHighlightSize.Small,
                background = OfferHighlightBackground.Blue,
                showPercentageBadge = false
            )
        }
    }
}

@Preview(name = "PopOfferHighlight - Bonus Only", showBackground = true)
@Composable
fun PreviewPopOfferHighlightBonusOnly() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Bonus Only (No Amount)")
            
            PopOfferHighlight(
                bonus = 234,
                size = OfferHighlightSize.Large,
                background = OfferHighlightBackground.Blue,
                showPercentageBadge = false,
                showAmount = false
            )
            
            PopOfferHighlight(
                bonus = 234,
                size = OfferHighlightSize.Medium,
                background = OfferHighlightBackground.Blue,
                showPercentageBadge = false,
                showAmount = false
            )
            
            PopOfferHighlight(
                bonus = 234,
                size = OfferHighlightSize.Small,
                background = OfferHighlightBackground.Blue,
                showPercentageBadge = false,
                showAmount = false
            )
        }
    }
}

@Preview(name = "PopOfferHighlight - Large Amounts", showBackground = true)
@Composable
fun PreviewPopOfferHighlightLargeAmounts() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Large Amounts (Indian Format)")
            
            PopOfferHighlight(
                amount = 123456,
                bonus = 5678,
                size = OfferHighlightSize.Medium,
                background = OfferHighlightBackground.Blue,
                iconPosition = OfferHighlightIconPosition.Right
            )
            
            PopOfferHighlight(
                amount = 1234567,
                bonus = 12345,
                size = OfferHighlightSize.Medium,
                background = OfferHighlightBackground.Blue,
                iconPosition = OfferHighlightIconPosition.Right
            )
            
            PopOfferHighlight(
                amount = 999999,
                bonus = 9999,
                size = OfferHighlightSize.Medium,
                background = OfferHighlightBackground.Blue,
                iconPosition = OfferHighlightIconPosition.Right
            )
        }
    }
}

@Preview(name = "PopOfferHighlight - All Variants Grid", showBackground = true, heightDp = 800)
@Composable
fun PreviewPopOfferHighlightAllVariants() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionTitle("All Variants Grid")
            
            // Blue Background - All sizes with icon positions
            SectionSubtitle("Blue Background")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Icon Right", style = PopTypography.labelXSmall, color = TextColor.Tertiary)
                    PopOfferHighlight(
                        amount = 1000, bonus = 234,
                        size = OfferHighlightSize.Large,
                        background = OfferHighlightBackground.Blue,
                        iconPosition = OfferHighlightIconPosition.Right
                    )
                    PopOfferHighlight(
                        amount = 1000, bonus = 234,
                        size = OfferHighlightSize.Medium,
                        background = OfferHighlightBackground.Blue,
                        iconPosition = OfferHighlightIconPosition.Right
                    )
                    PopOfferHighlight(
                        amount = 1000, bonus = 234,
                        size = OfferHighlightSize.Small,
                        background = OfferHighlightBackground.Blue,
                        iconPosition = OfferHighlightIconPosition.Right
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Icon Left", style = PopTypography.labelXSmall, color = TextColor.Tertiary)
                    PopOfferHighlight(
                        amount = 1000, bonus = 234,
                        size = OfferHighlightSize.Large,
                        background = OfferHighlightBackground.Blue,
                        iconPosition = OfferHighlightIconPosition.Left
                    )
                    PopOfferHighlight(
                        amount = 1000, bonus = 234,
                        size = OfferHighlightSize.Medium,
                        background = OfferHighlightBackground.Blue,
                        iconPosition = OfferHighlightIconPosition.Left
                    )
                    PopOfferHighlight(
                        amount = 1000, bonus = 234,
                        size = OfferHighlightSize.Small,
                        background = OfferHighlightBackground.Blue,
                        iconPosition = OfferHighlightIconPosition.Left
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Transparent Background - All sizes with icon positions
            SectionSubtitle("Transparent Background")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Icon Right", style = PopTypography.labelXSmall, color = TextColor.Tertiary)
                    PopOfferHighlight(
                        amount = 1000, bonus = 234,
                        size = OfferHighlightSize.Large,
                        background = OfferHighlightBackground.Transparent,
                        iconPosition = OfferHighlightIconPosition.Right
                    )
                    PopOfferHighlight(
                        amount = 1000, bonus = 234,
                        size = OfferHighlightSize.Medium,
                        background = OfferHighlightBackground.Transparent,
                        iconPosition = OfferHighlightIconPosition.Right
                    )
                    PopOfferHighlight(
                        amount = 1000, bonus = 234,
                        size = OfferHighlightSize.Small,
                        background = OfferHighlightBackground.Transparent,
                        iconPosition = OfferHighlightIconPosition.Right
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Icon Left", style = PopTypography.labelXSmall, color = TextColor.Tertiary)
                    PopOfferHighlight(
                        amount = 1000, bonus = 234,
                        size = OfferHighlightSize.Large,
                        background = OfferHighlightBackground.Transparent,
                        iconPosition = OfferHighlightIconPosition.Left
                    )
                    PopOfferHighlight(
                        amount = 1000, bonus = 234,
                        size = OfferHighlightSize.Medium,
                        background = OfferHighlightBackground.Transparent,
                        iconPosition = OfferHighlightIconPosition.Left
                    )
                    PopOfferHighlight(
                        amount = 1000, bonus = 234,
                        size = OfferHighlightSize.Small,
                        background = OfferHighlightBackground.Transparent,
                        iconPosition = OfferHighlightIconPosition.Left
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = PopTypography.labelLarge,
        color = TextColor.Primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun SectionSubtitle(text: String) {
    Text(
        text = text,
        style = PopTypography.labelSmall,
        color = TextColor.Secondary,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}
