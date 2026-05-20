package com.pop.components.compose_components.preview

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
import com.pop.components.ds_components.PillSelection
import com.pop.components.ds_components.PopPills
import com.pop.components.theme.PopTheme
import com.pop.components.theme.TextColors

// ============================================================================
// PopPills Previews
// ============================================================================

@Preview(
    name = "Pills - Default Variant, 2 Pills",
    showBackground = true,
    backgroundColor = 0xFF111110
)
@Composable
fun PopPillsDefault2PillsPreview() {
    PopTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Left selected
            PopPills(
                modifier = Modifier,
                pillTexts = listOf("Left", "Right"),
                hasBg = true,
                selected = PillSelection.Left,
                isDisabled = false
            )
            
            // Right selected
            PopPills(
                pillTexts = listOf("Left", "Right"),
                hasBg = true,
                selected = PillSelection.Right,
                isDisabled = false
            )
        }
    }
}

@Preview(
    name = "Pills - Default Variant, 3 Pills",
    showBackground = true,
    backgroundColor = 0xFF111110
)
@Composable
fun PopPillsDefault3PillsPreview() {
    PopTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Left selected
            PopPills(
                modifier = Modifier,
                pillTexts = listOf("Left", "Center", "Right"),
                hasBg = true,
                selected = PillSelection.Left,
                isDisabled = false
            )
            
            // Center selected
            PopPills(
                modifier = Modifier,
                pillTexts = listOf("Left", "Center", "Right"),
                hasBg = true,
                selected = PillSelection.Center,
                isDisabled = false
            )
            
            // Right selected
            PopPills(
                modifier = Modifier,
                pillTexts = listOf("Left", "Center", "Right"),
                hasBg = true,
                selected = PillSelection.Right,
                isDisabled = false
            )
        }
    }
}

@Preview(
    name = "Pills - Subdued Variant",
    showBackground = true,
    backgroundColor = 0xFF111110
)
@Composable
fun PopPillsSubduedPreview() {
    PopTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 2 pills, Left selected
            PopPills(
                modifier = Modifier,
                pillTexts = listOf("Left", "Right"),
                hasBg = false,
                selected = PillSelection.Left,
                isDisabled = false
            )
            
            // 2 pills, Right selected
            PopPills(
                modifier = Modifier,
                pillTexts = listOf("Left", "Right"),
                hasBg = false,
                selected = PillSelection.Right,
                isDisabled = false
            )
            
            // 3 pills, Center selected
            PopPills(
                modifier = Modifier,
                pillTexts = listOf("Left", "Center", "Right"),
                hasBg = false,
                selected = PillSelection.Center,
                isDisabled = false
            )
        }
    }
}

@Preview(
    name = "Pills - Disabled State",
    showBackground = true,
    backgroundColor = 0xFF111110
)
@Composable
fun PopPillsDisabledPreview() {
    PopTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Default variant, disabled
            PopPills(
                modifier = Modifier,
                pillTexts = listOf("Left", "Right"),
                hasBg = true,
                selected = PillSelection.Left,
                isDisabled = true
            )
            
            // Subdued variant, disabled
            PopPills(
                modifier = Modifier,
                pillTexts = listOf("Left", "Right"),
                hasBg = false,
                selected = PillSelection.Right,
                isDisabled = true
            )
            
            // 3 pills, disabled
            PopPills(
                modifier = Modifier,
                pillTexts = listOf("Left", "Center", "Right"),
                hasBg = true,
                selected = PillSelection.Center,
                isDisabled = true
            )
        }
    }
}

@Preview(
    name = "Pills - All Variants",
    showBackground = true,
    backgroundColor = 0xFF111110,
    heightDp = 800
)
@Composable
fun AllPopPillsPreviews() {
    PopTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Default Variant - 2 Pills
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Default Variant - 2 Pills",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    PopPills(
                        modifier = Modifier,
                        pillTexts = listOf("Left", "Right"),
                        hasBg = true,
                        selected = PillSelection.Left,
                        isDisabled = false
                    )
                    PopPills(
                        modifier = Modifier,
                        pillTexts = listOf("Left", "Right"),
                        hasBg = true,
                        selected = PillSelection.Right,
                        isDisabled = false
                    )
                }
            }
            
            // Default Variant - 3 Pills
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Default Variant - 3 Pills",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    PopPills(
                        pillTexts = listOf("Left", "Center", "Right"),
                        hasBg = true,
                        selected = PillSelection.Left,
                        isDisabled = false
                    )
                    PopPills(
                        pillTexts = listOf("Left", "Center", "Right"),
                        hasBg = true,
                        selected = PillSelection.Center,
                        isDisabled = false
                    )
                    PopPills(
                        pillTexts = listOf("Left", "Center", "Right"),
                        hasBg = true,
                        selected = PillSelection.Right,
                        isDisabled = false
                    )
                }
            }
            
            // Subdued Variant - 2 Pills
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Subdued Variant - 2 Pills",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    PopPills(
                        modifier = Modifier,
                        pillTexts = listOf("Left", "Right"),
                        hasBg = false,
                        selected = PillSelection.Left,
                        isDisabled = false
                    )
                    PopPills(
                        pillTexts = listOf("Left", "Right"),
                        hasBg = false,
                        selected = PillSelection.Right,
                        isDisabled = false
                    )
                }
            }
            
            // Subdued Variant - 3 Pills
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Subdued Variant - 3 Pills",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    PopPills(
                        pillTexts = listOf("Left", "Center", "Right"),
                        hasBg = false,
                        selected = PillSelection.Left,
                        isDisabled = false
                    )
                    PopPills(
                        pillTexts = listOf("Left", "Center", "Right"),
                        hasBg = false,
                        selected = PillSelection.Center,
                        isDisabled = false
                    )
                    PopPills(
                        pillTexts = listOf("Left", "Center", "Right"),
                        hasBg = false,
                        selected = PillSelection.Right,
                        isDisabled = false
                    )
                }
            }
            
            // Disabled States
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Disabled States",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default
                )
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    PopPills(
                        pillTexts = listOf("Left", "Right"),
                        hasBg = true,
                        selected = PillSelection.Left,
                        isDisabled = true
                    )
                    PopPills(
                        pillTexts = listOf("Left", "Center", "Right"),
                        hasBg = false,
                        selected = PillSelection.Center,
                        isDisabled = true
                    )
                }
            }
        }
    }
}
