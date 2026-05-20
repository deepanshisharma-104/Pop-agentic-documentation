package com.pop.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.compose_components.PopButton
import com.pop.components.compose_components.FlashButtonSize
import com.pop.components.compose_components.FlashButtonVariant
import com.pop.components.theme.PopColors
import com.pop.components.theme.PopTheme

@Composable
fun ButtonDemoScreen() {
    PopTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PopColors.Neutral.N1)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Primary Buttons
            // Large
            PopButton(
                text = "Primary Large",
                onClick = {},
                variant = FlashButtonVariant.Primary,
                size = FlashButtonSize.Large
            )
            
            // Medium
            PopButton(
                text = "Primary Medium",
                onClick = {},
                variant = FlashButtonVariant.Primary,
                size = FlashButtonSize.Medium
            )
            
            // Small
            PopButton(
                text = "Primary Small",
                onClick = {},
                variant = FlashButtonVariant.Primary,
                size = FlashButtonSize.Small
            )
            
            // Loading
            PopButton(
                text = "Primary Loading",
                onClick = {},
                isLoading = true,
                variant = FlashButtonVariant.Primary
            )
            
            // Disabled
            PopButton(
                text = "Primary Disabled",
                onClick = {},
                enabled = false,
                variant = FlashButtonVariant.Primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Secondary Buttons
            // Large
            PopButton(
                text = "Secondary Large",
                onClick = {},
                variant = FlashButtonVariant.Secondary,
                size = FlashButtonSize.Large
            )
            
            // Medium
            PopButton(
                text = "Secondary Medium",
                onClick = {},
                variant = FlashButtonVariant.Secondary,
                size = FlashButtonSize.Medium
            )
            
            // Small
            PopButton(
                text = "Secondary Small",
                onClick = {},
                variant = FlashButtonVariant.Secondary,
                size = FlashButtonSize.Small
            )
            
            // Loading
            PopButton(
                text = "Secondary Loading",
                onClick = {},
                isLoading = true,
                variant = FlashButtonVariant.Secondary
            )
            
            // Disabled
            PopButton(
                text = "Secondary Disabled",
                onClick = {},
                enabled = false,
                variant = FlashButtonVariant.Secondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Tertiary Buttons
            // Large
            PopButton(
                text = "Tertiary Large",
                onClick = {},
                variant = FlashButtonVariant.Tertiary,
                size = FlashButtonSize.Large
            )
            
            // Medium
            PopButton(
                text = "Tertiary Medium",
                onClick = {},
                variant = FlashButtonVariant.Tertiary,
                size = FlashButtonSize.Medium
            )
            
            // Small
            PopButton(
                text = "Tertiary Small",
                onClick = {},
                variant = FlashButtonVariant.Tertiary,
                size = FlashButtonSize.Small
            )
            
            // Loading
            PopButton(
                text = "Tertiary Loading",
                onClick = {},
                isLoading = true,
                variant = FlashButtonVariant.Tertiary
            )
            
            // Disabled
            PopButton(
                text = "Tertiary Disabled",
                onClick = {},
                enabled = false,
                variant = FlashButtonVariant.Tertiary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ButtonDemoScreenPreview() {
    ButtonDemoScreen()
}