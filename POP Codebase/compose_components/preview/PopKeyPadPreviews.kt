package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.KeyPadKey
import com.pop.components.ds_components.PopKeyPad
import com.pop.components.theme.PopColors
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.PopTheme
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import com.pop.components.theme.PopTypography

/**
 * Preview showing the PopKeyPad component with interactive key press handling
 */
@Preview(
    name = "PopKeyPad - Default",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D
)
@Composable
fun PopKeyPadDefaultPreview() {
    PopTheme {
        var inputValue by remember { mutableStateOf("") }
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceColor.Primary)
                .padding(PopSpacing.Spacing16)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = SurfaceColor.Secondary,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                        )
                        .padding(PopSpacing.Spacing16)
                ) {
                    Text(
                        text = inputValue.ifEmpty { "Enter amount..." },
                        style = PopTypography.paragraphLarge,
                        color = if (inputValue.isEmpty()) {
                            TextColor.Secondary
                        } else {
                            TextColor.Primary
                        }
                    )
                }
                
                // Spacer
                Spacer(
                    modifier = Modifier.height(PopSpacing.Spacing24)
                )
                
                // KeyPad
                PopKeyPad(
                    onKeyPress = { key ->
                        inputValue = when (key) {
                            is KeyPadKey.Number -> inputValue + key.value
                            is KeyPadKey.Decimal -> {
                                if (!inputValue.contains(".")) {
                                    if (inputValue.isEmpty()) "0." else inputValue + "."
                                } else {
                                    inputValue
                                }
                            }
                            is KeyPadKey.Backspace -> {
                                if (inputValue.isNotEmpty()) {
                                    inputValue.dropLast(1)
                                } else {
                                    inputValue
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

/**
 * Preview showing the PopKeyPad component with constrained width
 */
@Preview(
    name = "PopKeyPad - Constrained Width",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    widthDp = 320
)
@Composable
fun PopKeyPadConstrainedWidthPreview() {
    PopTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceColor.Primary)
                .padding(PopSpacing.Spacing16)
        ) {
            PopKeyPad(
                onKeyPress = {}
            )
        }
    }
}

/**
 * Preview showing the PopKeyPad component in a bottom sheet context
 */
@Preview(
    name = "PopKeyPad - Bottom Sheet Context",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D
)
@Composable
fun PopKeyPadBottomSheetPreview() {
    PopTheme {
        var amount by remember { mutableStateOf("") }
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceColor.Primary)
        ) {
            // Simulate bottom sheet
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        color = SurfaceColor.Secondary,
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp
                        )
                    )
                    .padding(
                        top = PopSpacing.Spacing24,
                        start = PopSpacing.Spacing16,
                        end = PopSpacing.Spacing16,
                        bottom = PopSpacing.Spacing16
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Amount display
                    Text(
                        text = amount.ifEmpty { "₹0" },
                        style = PopTypography.headingLarge,
                        color = TextColor.Primary,
                        modifier = Modifier.padding(bottom = PopSpacing.Spacing24)
                    )
                    
                    // KeyPad
                    PopKeyPad(
                        onKeyPress = { key ->
                            amount = when (key) {
                                is KeyPadKey.Number -> {
                                    if (amount == "0") key.value else amount + key.value
                                }
                                is KeyPadKey.Decimal -> {
                                    if (!amount.contains(".")) {
                                        if (amount.isEmpty()) "0." else amount + "."
                                    } else {
                                        amount
                                    }
                                }
                                is KeyPadKey.Backspace -> {
                                    if (amount.isNotEmpty()) {
                                        amount.dropLast(1)
                                    } else {
                                        "0"
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

/**
 * Preview showing the PopKeyPad component with maximum width
 */
@Preview(
    name = "PopKeyPad - Maximum Width",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    widthDp = 600
)
@Composable
fun PopKeyPadMaximumWidthPreview() {
    PopTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceColor.Primary)
                .padding(PopSpacing.Spacing16)
        ) {
            PopKeyPad(
                onKeyPress = {}
            )
        }
    }
}
