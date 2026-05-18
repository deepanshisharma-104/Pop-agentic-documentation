package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.BadgeType
import com.pop.components.ds_components.PopBadge
import com.pop.components.theme.Icons
import com.pop.components.theme.SurfaceColor

@Preview(name = "Badge - All Types with Background + Glow", showBackground = true)
@Composable
fun PreviewBadgeWithBgAndGlow() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PopBadge(label = "Label", type = BadgeType.Orange, hasBg = true, hasGlow = true)
            PopBadge(label = "Label", type = BadgeType.Green, hasBg = true, hasGlow = true)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PopBadge(label = "Label", type = BadgeType.Red, hasBg = true, hasGlow = true)
            PopBadge(label = "Label", type = BadgeType.WhitePrimary, hasBg = true, hasGlow = true)
        }
    }
}

@Preview(name = "Badge - All Types with Background, No Glow", showBackground = true)
@Composable
fun PreviewBadgeWithBgNoGlow() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PopBadge(label = "Label", type = BadgeType.Orange, hasBg = true, hasGlow = false)
            PopBadge(label = "Label", type = BadgeType.Green, hasBg = true, hasGlow = false)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PopBadge(label = "Label", type = BadgeType.Red, hasBg = true, hasGlow = false)
            PopBadge(label = "Label", type = BadgeType.WhitePrimary, hasBg = true, hasGlow = false)
        }
    }
}

@Preview(name = "Badge - Text Only (No Background)", showBackground = true)
@Composable
fun PreviewBadgeTextOnly() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            PopBadge(label = "Label", type = BadgeType.Orange, hasBg = false)
            PopBadge(label = "Label", type = BadgeType.Green, hasBg = false)
            PopBadge(label = "Label", type = BadgeType.Red, hasBg = false)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            PopBadge(label = "Label", type = BadgeType.Warning, hasBg = false)
            PopBadge(label = "Label", type = BadgeType.WhitePrimary, hasBg = false)
            PopBadge(label = "Label", type = BadgeType.WhiteTertiary, hasBg = false)
        }
    }
}

@Preview(name = "Badge - With Icons", showBackground = true)
@Composable
fun PreviewBadgeWithIcons() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // With background and icons
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PopBadge(
                label = "New",
                type = BadgeType.Orange,
                hasBg = true,
                hasGlow = true,
                leftIcon = Icons.Share06
            )
            PopBadge(
                label = "Success",
                type = BadgeType.Green,
                hasBg = true,
                hasGlow = true,
                rightIcon = Icons.Check
            )
        }
        // Text only with icons
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PopBadge(
                label = "Error",
                type = BadgeType.Red,
                hasBg = false,
                leftIcon = Icons.Cross
            )
            PopBadge(
                label = "Info",
                type = BadgeType.Warning,
                hasBg = false,
                leftIcon = Icons.Share05
            )
        }
    }
}

@Preview(name = "Badge - All Variants Grid", showBackground = true)
@Composable
fun PreviewBadgeAllVariants() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Orange row
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PopBadge(label = "Label", type = BadgeType.Orange, hasBg = true, hasGlow = true)
            PopBadge(label = "Label", type = BadgeType.Orange, hasBg = true, hasGlow = false)
            PopBadge(label = "Label", type = BadgeType.Orange, hasBg = false)
        }
        // Green row
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PopBadge(label = "Label", type = BadgeType.Green, hasBg = true, hasGlow = true)
            PopBadge(label = "Label", type = BadgeType.Green, hasBg = true, hasGlow = false)
            PopBadge(label = "Label", type = BadgeType.Green, hasBg = false)
        }
        // Red row
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PopBadge(label = "Label", type = BadgeType.Red, hasBg = true, hasGlow = true)
            PopBadge(label = "Label", type = BadgeType.Red, hasBg = true, hasGlow = false)
            PopBadge(label = "Label", type = BadgeType.Red, hasBg = false)
        }
        // White Primary row
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PopBadge(label = "Label", type = BadgeType.WhitePrimary, hasBg = true, hasGlow = true)
            PopBadge(label = "Label", type = BadgeType.WhitePrimary, hasBg = true, hasGlow = false)
            PopBadge(label = "Label", type = BadgeType.WhitePrimary, hasBg = false)
        }
        // Text-only types
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PopBadge(label = "Label", type = BadgeType.Warning)
            PopBadge(label = "Label", type = BadgeType.WhiteTertiary)
        }
    }
}
