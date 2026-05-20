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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.theme.FlashTypography
import com.pop.components.theme.PopColors
import com.pop.components.theme.SurfaceColors
import com.pop.components.theme.TextColors

@Deprecated("old Design System", replaceWith = ReplaceWith("PopCheckBoxV2"))

enum class FlashCheckboxVariant {
    Default, Orange
}

@Composable
fun PopCheckbox(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: AnnotatedString,
    supportingText: String? = null,
    enabled: Boolean = true,
    variant: FlashCheckboxVariant = FlashCheckboxVariant.Default,
    onLabelClick: ((Int) -> Unit)? = null
) {
    val checkboxColor = when (variant) {
        FlashCheckboxVariant.Default -> TextColors.Primary.Default
        FlashCheckboxVariant.Orange -> PopColors.Orange.O9
    }

    Row(
        modifier = modifier
            .clickable(enabled = enabled) { onCheckedChange(!checked) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Checkbox
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    when {
                        !enabled && variant == FlashCheckboxVariant.Orange -> checkboxColor.copy(alpha = 0.3f)
                        !enabled && checked -> checkboxColor.copy(alpha = 0.3f)
                        checked -> checkboxColor
                        variant == FlashCheckboxVariant.Orange -> PopColors.Orange.O3
                        else -> SurfaceColors.Tertiary
                    }
                )
                .let { modifier ->
                    if (!enabled && variant == FlashCheckboxVariant.Orange) {
                        modifier
                    } else {
                        modifier.border(
                            width = 2.dp,
                            color = when {
                                !enabled -> PopColors.Neutral.N7.copy(alpha = 0.3f)
                                checked -> checkboxColor
                                variant == FlashCheckboxVariant.Orange -> PopColors.Orange.O9
                                else -> PopColors.Neutral.N7
                            },
                            shape = RoundedCornerShape(4.dp)
                        )
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Checked",
                    tint = when (variant) {
                        FlashCheckboxVariant.Orange -> PopColors.Neutral.N12
                        FlashCheckboxVariant.Default -> if (!enabled) TextColors.Primary.Disabled else PopColors.Neutral.N2
                    },
                    modifier = Modifier.size(16.dp)
                )
            } else if (variant == FlashCheckboxVariant.Orange) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            color = SurfaceColors.Accent.Disabled,
                            shape = RoundedCornerShape(2.dp)
                        )
                )
            }
        }

        // Label and Supporting Text
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            if (onLabelClick != null) {
                ClickableText(
                    text = label,
                    style = TextStyle(
                        fontFamily = FlashTypography.figtree,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        letterSpacing = (-0.01).sp,
                        color = if (enabled) TextColors.Primary.Default else TextColors.Primary.Disabled
                    ),
                    onClick = onLabelClick
                )
            } else {
                Text(
                    text = label,
                    style = TextStyle(
                        fontFamily = FlashTypography.figtree,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        letterSpacing = (-0.01).sp,
                        color = if (enabled) TextColors.Primary.Default else TextColors.Primary.Disabled
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
private fun FlashCheckboxPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PopColors.Neutral.N1)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Checkboxes",
            style = TextStyle(
                fontFamily = FlashTypography.figtree,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = TextColors.Primary.Default
            )
        )

        // Default variant - checked
        PopCheckbox(
            checked = true,
            onCheckedChange = { },
            label = AnnotatedString("Default Checkbox - Checked"),
            supportingText = "supporting info",
            modifier = Modifier.fillMaxWidth()
        )

        // Default variant - unchecked
        PopCheckbox(
            checked = false,
            onCheckedChange = { },
            label = AnnotatedString("Default Checkbox - Unchecked"),
            supportingText = "supporting info",
            modifier = Modifier.fillMaxWidth()
        )

        // Orange variant - checked
        PopCheckbox(
            checked = true,
            onCheckedChange = { },
            label = AnnotatedString("Orange Checkbox - Checked"),
            supportingText = "supporting info",
            variant = FlashCheckboxVariant.Orange,
            modifier = Modifier.fillMaxWidth()
        )

        // Orange variant - unchecked
        PopCheckbox(
            checked = false,
            onCheckedChange = { },
            label = AnnotatedString("Orange Checkbox - Unchecked"),
            supportingText = "supporting info",
            variant = FlashCheckboxVariant.Orange,
            modifier = Modifier.fillMaxWidth()
        )

        // Disabled default - checked
        PopCheckbox(
            checked = true,
            onCheckedChange = { },
            label = AnnotatedString("Disabled Default - Checked"),
            supportingText = "supporting info",
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        // Disabled default - unchecked
        PopCheckbox(
            checked = false,
            onCheckedChange = { },
            label = AnnotatedString("Disabled Default - Unchecked"),
            supportingText = "supporting info",
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        // Disabled orange - checked
        PopCheckbox(
            checked = true,
            onCheckedChange = { },
            label = AnnotatedString("Disabled Orange - Checked"),
            supportingText = "supporting info",
            enabled = false,
            variant = FlashCheckboxVariant.Orange,
            modifier = Modifier.fillMaxWidth()
        )

        // Disabled orange - unchecked
        PopCheckbox(
            checked = false,
            onCheckedChange = { },
            label = AnnotatedString("Disabled Orange - Unchecked"),
            supportingText = "supporting info",
            enabled = false,
            variant = FlashCheckboxVariant.Orange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}