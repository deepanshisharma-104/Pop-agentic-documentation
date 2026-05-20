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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.OfferType
import com.pop.components.ds_components.PopOfferToggle
import com.pop.components.theme.PopTheme
import com.pop.components.theme.SurfaceColor

@Preview(name = "PopOfferToggle - All Types Enabled", showBackground = true)
@Composable
fun PreviewPopOfferToggleAllTypes() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Offer Types - Enabled")
            
            var offerChecked by remember { mutableStateOf(true) }
            var cashChecked by remember { mutableStateOf(false) }
            var payIn3Checked by remember { mutableStateOf(true) }
            var popCoinChecked by remember { mutableStateOf(false) }
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.Offer,
                checked = offerChecked,
                onCheckedChange = { offerChecked = it },
                enabled = true,
                onInfoClick = {}
            )
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.Cash,
                checked = cashChecked,
                onCheckedChange = { cashChecked = it },
                enabled = true,
                onInfoClick = {}
            )
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.PayIn3,
                checked = payIn3Checked,
                onCheckedChange = { payIn3Checked = it },
                enabled = true,
                onInfoClick = {}
            )
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.PopCoin,
                checked = popCoinChecked,
                onCheckedChange = { popCoinChecked = it },
                enabled = true,
                onInfoClick = {}
            )
        }
    }
}

@Preview(name = "PopOfferToggle - All Types Disabled", showBackground = true)
@Composable
fun PreviewPopOfferToggleDisabled() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Offer Types - Disabled")
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.Offer,
                checked = false,
                onCheckedChange = {},
                enabled = false,
                onInfoClick = {}
            )
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.Cash,
                checked = false,
                onCheckedChange = {},
                enabled = false,
                onInfoClick = {}
            )
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.PayIn3,
                checked = false,
                onCheckedChange = {},
                enabled = false,
                onInfoClick = {}
            )
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.PopCoin,
                checked = false,
                onCheckedChange = {},
                enabled = false,
                onInfoClick = {}
            )
        }
    }
}

@Preview(name = "PopOfferToggle - Toggle States", showBackground = true)
@Composable
fun PreviewPopOfferToggleStates() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Toggle States - ON vs OFF")
            
            var checked1 by remember { mutableStateOf(true) }
            var checked2 by remember { mutableStateOf(false) }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PopOfferToggle(
                    title = "Label",
                    bodyText = "Label Label",
                    type = OfferType.Offer,
                    checked = checked1,
                    onCheckedChange = { checked1 = it },
                    enabled = true,
                    onInfoClick = {},
                    modifier = Modifier.weight(1f)
                )
                
                PopOfferToggle(
                    title = "Label",
                    bodyText = "Label Label",
                    type = OfferType.Offer,
                    checked = checked2,
                    onCheckedChange = { checked2 = it },
                    enabled = true,
                    onInfoClick = {},
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Preview(name = "PopOfferToggle - Without Info Button", showBackground = true)
@Composable
fun PreviewPopOfferToggleNoInfo() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Without Info Button")
            
            var checked by remember { mutableStateOf(true) }
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.Offer,
                checked = checked,
                onCheckedChange = { checked = it },
                enabled = true,
                onInfoClick = null
            )
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.Cash,
                checked = checked,
                onCheckedChange = { checked = it },
                enabled = true,
                onInfoClick = null
            )
        }
    }
}

@Preview(name = "PopOfferToggle - Long Text", showBackground = true)
@Composable
fun PreviewPopOfferToggleLongText() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SectionTitle("Long Text (Ellipsis)")
            
            var checked by remember { mutableStateOf(true) }
            
            PopOfferToggle(
                title = "This is a very long title that should be truncated with ellipsis",
                bodyText = "This is a very long body text that should also be truncated",
                type = OfferType.Offer,
                checked = checked,
                onCheckedChange = { checked = it },
                enabled = true,
                onInfoClick = {}
            )
        }
    }
}

@Preview(name = "PopOfferToggle - All Variants Grid", showBackground = true, heightDp = 1200)
@Composable
fun PreviewPopOfferToggleAllVariants() {
    PopTheme {
        Column(
            modifier = Modifier
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SectionTitle("All Variants")
            
            // Offer - Enabled ON
            var offerOn by remember { mutableStateOf(true) }
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.Offer,
                checked = offerOn,
                onCheckedChange = { offerOn = it },
                enabled = true,
                onInfoClick = {}
            )
            
            // Offer - Enabled OFF
            var offerOff by remember { mutableStateOf(false) }
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.Offer,
                checked = offerOff,
                onCheckedChange = { offerOff = it },
                enabled = true,
                onInfoClick = {}
            )
            
            // Cash - Enabled ON
            var cashOn by remember { mutableStateOf(true) }
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.Cash,
                checked = cashOn,
                onCheckedChange = { cashOn = it },
                enabled = true,
                onInfoClick = {}
            )
            
            // Cash - Enabled OFF
            var cashOff by remember { mutableStateOf(false) }
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.Cash,
                checked = cashOff,
                onCheckedChange = { cashOff = it },
                enabled = true,
                onInfoClick = {}
            )
            
            // PayIn3 - Enabled ON
            var payIn3On by remember { mutableStateOf(true) }
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.PayIn3,
                checked = payIn3On,
                onCheckedChange = { payIn3On = it },
                enabled = true,
                onInfoClick = {}
            )
            
            // PayIn3 - Enabled OFF
            var payIn3Off by remember { mutableStateOf(false) }
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.PayIn3,
                checked = payIn3Off,
                onCheckedChange = { payIn3Off = it },
                enabled = true,
                onInfoClick = {}
            )
            
            // PopCoin - Enabled ON
            var popCoinOn by remember { mutableStateOf(true) }
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.PopCoin,
                checked = popCoinOn,
                onCheckedChange = { popCoinOn = it },
                enabled = true,
                onInfoClick = {}
            )
            
            // PopCoin - Enabled OFF
            var popCoinOff by remember { mutableStateOf(false) }
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.PopCoin,
                checked = popCoinOff,
                onCheckedChange = { popCoinOff = it },
                enabled = true,
                onInfoClick = {}
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            SectionTitle("Disabled States")
            
            // Disabled variants
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.Offer,
                checked = false,
                onCheckedChange = {},
                enabled = false,
            )
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.Cash,
                checked = false,
                onCheckedChange = {},
                enabled = false,
                onInfoClick = {}
            )
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.PayIn3,
                checked = false,
                onCheckedChange = {},
                enabled = false,
                onInfoClick = {}
            )
            
            PopOfferToggle(
                title = "Label",
                bodyText = "Label Label",
                type = OfferType.PopCoin,
                checked = false,
                onCheckedChange = {},
                enabled = false,
                onInfoClick = {}
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = com.pop.components.theme.PopTypography.labelLarge,
        color = com.pop.components.theme.TextColor.Primary,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

