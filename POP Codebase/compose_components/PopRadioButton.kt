package com.pop.components.compose_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.theme.FlashTypography
import com.pop.components.theme.PopColors
import com.pop.components.theme.TextColors

@Composable
fun PopRadioButton(
    selected: Boolean,
    onSelect: () -> Unit,
    label: String? = null,
    supportingText: String? = null,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable(enabled = enabled) { onSelect() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Radio Button Circle
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = when {
                        !enabled -> PopColors.Neutral.N7.copy(alpha = 0.3f)
                        selected -> TextColors.Primary.Default
                        else -> PopColors.Neutral.N7
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(
                            if (enabled) TextColors.Primary.Default
                            else TextColors.Primary.Default.copy(alpha = 0.3f)
                        )
                )
            }
        }

        // Label and Supporting Text
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            if (label != null) {
                Text(
                    text = label,
                    style = TextStyle(
                        fontFamily = FlashTypography.figtree,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        letterSpacing = (-0.01).sp,
                        color = if (enabled) TextColors.Primary.Default else TextColors.Primary.Default.copy(
                            alpha = 0.3f
                        )
                    )
                )
            }
            if (supportingText != null) {
                Text(
                    text = supportingText,
                    style = TextStyle(
                        fontFamily = FlashTypography.figtree,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        letterSpacing = (-0.01).sp,
                        color = if (enabled) PopColors.Neutral.N7 else PopColors.Neutral.N7.copy(alpha = 0.3f)
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FlashRadioButtonPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PopColors.Neutral.N1)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Radio Buttons",
            style = TextStyle(
                fontFamily = FlashTypography.figtree,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = TextColors.Primary.Default
            )
        )

        // Selected with supporting text
        PopRadioButton(
            selected = true,
            onSelect = { },
            label = "Radio Label",
            supportingText = "supporting info",
            modifier = Modifier.fillMaxWidth()
        )

        // Unselected with supporting text
        PopRadioButton(
            selected = false,
            onSelect = { },
            label = "Radio Label",
            supportingText = "supporting info",
            modifier = Modifier.fillMaxWidth()
        )

        // Disabled with supporting text
        PopRadioButton(
            selected = false,
            onSelect = { },
            label = "Radio Label",
            supportingText = "supporting info",
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )
    }
} 