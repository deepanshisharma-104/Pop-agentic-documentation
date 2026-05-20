package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.theme.PopColors
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopTheme
import com.pop.components.theme.TextColors
import com.pop.components.utils.backgroundWithEnclosedBorder
import com.pop.components.utils.borderEnclosedNoBg

// ============================================================================
// Card Style Previews
// ============================================================================

@Preview(
    name = "Card - Background With Enclosed Border",
    showBackground = true,
    backgroundColor = 0xFF111110
)
@Composable
fun CardBackgroundWithEnclosedBorderPreview() {
    PopTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .backgroundWithEnclosedBorder()
        ) {
            Text(
                text = "Content goes here",
                style = MaterialTheme.typography.bodyLarge,
                color = TextColors.Primary.Default,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(
    name = "Card - Border Enclosed No Background",
    showBackground = true,
    backgroundColor = 0xFF111110
)
@Composable
fun CardBorderEnclosedNoBgPreview() {
    PopTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .borderEnclosedNoBg()
        ) {
            Text(
                text = "Content with transparent background",
                style = MaterialTheme.typography.bodyLarge,
                color = TextColors.Primary.Default,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview(
    name = "Card - All Variants",
    showBackground = true,
    backgroundColor = 0xFF111110,
    heightDp = 800
)
@Composable
fun AllCardPreviews() {
    PopTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PopColors.Neutral.N1)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(24.dp)
        ) {
            // Background With Enclosed Border
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "backgroundWithEnclosedBorder()",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .backgroundWithEnclosedBorder()
                ) {
                    Text(
                        text = "Content with inner black background",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextColors.Primary.Default,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Border Enclosed No Background
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "borderEnclosedNoBg()",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .borderEnclosedNoBg()
                ) {
                    Text(
                        text = "Content with transparent background",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextColors.Primary.Default,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Custom corner radius example
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "backgroundWithEnclosedBorder() - Custom Radius",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .backgroundWithEnclosedBorder(
                            cornerRadius = PopRadius.XLarge
                        )
                ) {
                    Text(
                        text = "Content with larger corner radius",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextColors.Primary.Default,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Custom padding example
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "borderEnclosedNoBg() - Custom Padding",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextColors.Primary.Default
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .borderEnclosedNoBg(
                            innerPadding = PaddingValues(24.dp)
                        )
                ) {
                    Text(
                        text = "Content with larger inner padding",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextColors.Primary.Default,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
