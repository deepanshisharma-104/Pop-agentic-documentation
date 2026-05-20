package com.pop.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.compose_components.PopCheckbox
import com.pop.components.compose_components.FlashCheckboxVariant
import com.pop.components.theme.PopColors
import com.pop.components.theme.TextColors
import com.pop.components.theme.FlashTypography

@Composable
fun CheckboxDemoScreen() {
    var defaultChecked by remember { mutableStateOf(false) }
    var orangeChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PopColors.Neutral.N1)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        // Section title
        Text(
            text = "Checkbox Variants",
            style = TextStyle(
                fontFamily = FlashTypography.figtree,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = TextColors.Primary.Default
            )
        )

        // Default Variant Section
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Default Variant",
                style = TextStyle(
                    fontFamily = FlashTypography.figtree,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = TextColors.Primary.Default
                )
            )

            // Interactive Default
            PopCheckbox(
                checked = defaultChecked,
                onCheckedChange = { defaultChecked = it },
                label = AnnotatedString("Interactive Default"),
                supportingText = "Click to toggle state",
                modifier = Modifier.fillMaxWidth()
            )

            // Default - Checked
            PopCheckbox(
                checked = true,
                onCheckedChange = { },
                label = AnnotatedString("Checked State"),
                supportingText = "Non-interactive example",
                modifier = Modifier.fillMaxWidth()
            )

            // Default - Unchecked
            PopCheckbox(
                checked = false,
                onCheckedChange = { },
                label = AnnotatedString("Unchecked State"),
                supportingText = "Non-interactive example",
                modifier = Modifier.fillMaxWidth()
            )

            // Default - Disabled Checked
            PopCheckbox(
                checked = true,
                onCheckedChange = { },
                label = AnnotatedString("Disabled Checked"),
                supportingText = "Disabled state example",
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )

            // Default - Disabled Unchecked
            PopCheckbox(
                checked = false,
                onCheckedChange = { },
                label = AnnotatedString("Disabled Unchecked"),
                supportingText = "Disabled state example",
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Orange Variant Section
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Orange Variant",
                style = TextStyle(
                    fontFamily = FlashTypography.figtree,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = TextColors.Primary.Default
                )
            )

            // Interactive Orange
            PopCheckbox(
                checked = orangeChecked,
                onCheckedChange = { orangeChecked = it },
                label = AnnotatedString("Interactive Orange"),
                supportingText = "Click to toggle state",
                variant = FlashCheckboxVariant.Orange,
                modifier = Modifier.fillMaxWidth()
            )

            // Orange - Checked
            PopCheckbox(
                checked = true,
                onCheckedChange = { },
                label = AnnotatedString("Checked State"),
                supportingText = "Non-interactive example",
                variant = FlashCheckboxVariant.Orange,
                modifier = Modifier.fillMaxWidth()
            )

            // Orange - Unchecked
            PopCheckbox(
                checked = false,
                onCheckedChange = { },
                label = AnnotatedString("Unchecked State"),
                supportingText = "Non-interactive example",
                variant = FlashCheckboxVariant.Orange,
                modifier = Modifier.fillMaxWidth()
            )

            // Orange - Disabled Checked
            PopCheckbox(
                checked = true,
                onCheckedChange = { },
                label = AnnotatedString("Disabled Checked"),
                supportingText = "Disabled state example",
                variant = FlashCheckboxVariant.Orange,
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )

            // Orange - Disabled Unchecked
            PopCheckbox(
                checked = false,
                onCheckedChange = { },
                label = AnnotatedString("Disabled Unchecked"),
                supportingText = "Disabled state example",
                variant = FlashCheckboxVariant.Orange,
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckboxDemoScreenPreview() {
    CheckboxDemoScreen()
}