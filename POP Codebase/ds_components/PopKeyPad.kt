package com.pop.components.ds_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.pop.components.theme.IconSize
import com.pop.components.theme.IconStyle
import com.pop.components.theme.Icons
import com.pop.components.theme.PopIconConfig
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopShapes
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

/**
 * Key type for PopKeyPad
 */
sealed class KeyPadKey {
    data class Number(val value: String) : KeyPadKey()
    data object Decimal : KeyPadKey()
    data object Backspace : KeyPadKey()
    
    val displayValue: String
        get() = when (this) {
            is Number -> value
            is Decimal -> "."
            is Backspace -> ""
        }
}

/**
 * PopKeyPad - Custom numeric keypad component from POP Design System
 *
 * Figma: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=6424-9514&m=dev
 *
 * Features:
 * - 4x3 grid layout with numbers 1-9, decimal point, 0, and backspace
 * - Dark theme with rounded buttons
 * - Haptic feedback on key press
 * - Flexible width: Automatically fills available width and distributes buttons equally
 * - Exactly 3 items per row with equal spacing and equal button widths
 *
 * Width behavior:
 * - Component fills available width (can be constrained via modifier)
 * - Each row contains exactly 3 buttons
 * - Buttons are equally spaced using PopSpacing.Spacing8 (8dp)
 * - Each button width = min((available width - 2 * spacing) / 3, PopSpacing.Spacing120)
 * - Button height is fixed at PopSpacing.Spacing56 (56dp)
 * - Button maximum width is PopSpacing.Spacing120 (120dp)
 *
 * @param onKeyPress Callback invoked when a key is pressed, with the key value
 * @param modifier Modifier for the component. Note: Component will fill max width unless constrained
 */
@Composable
fun PopKeyPad(
    onKeyPress: (KeyPadKey) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val hapticFeedback = LocalHapticFeedback.current

    // Remember key layout to avoid recreating on every recomposition
    val keyLayout = remember {
        listOf(
            listOf(
                KeyPadKey.Number("1"),
                KeyPadKey.Number("2"),
                KeyPadKey.Number("3")
            ),
            listOf(
                KeyPadKey.Number("4"),
                KeyPadKey.Number("5"),
                KeyPadKey.Number("6")
            ),
            listOf(
                KeyPadKey.Number("7"),
                KeyPadKey.Number("8"),
                KeyPadKey.Number("9")
            ),
            listOf(
                KeyPadKey.Decimal,
                KeyPadKey.Number("0"),
                KeyPadKey.Backspace
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (enabled) 1f else 0.4f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing8)
    ) {
        keyLayout.forEachIndexed { rowIndex, rowKeys ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing8)
            ) {
                rowKeys.forEach { key ->
                    KeyPadButton(
                        key = key,
                        enabled = enabled,
                        modifier = Modifier
                            .weight(1f)
                            .widthIn(max = PopSpacing.Spacing120),
                        onClick = {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.VirtualKey)
                            onKeyPress(key)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun KeyPadButton(
    key: KeyPadKey,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val hapticFeedback = LocalHapticFeedback.current

    // Number buttons use SecondaryTransparent60, Delete and Dot buttons use PrimaryTransparent50
    val buttonBackgroundColor = remember(key) {
        when (key) {
            is KeyPadKey.Number -> SurfaceColor.SecondaryTransparent60
            is KeyPadKey.Decimal -> SurfaceColor.PrimaryTransparent50
            is KeyPadKey.Backspace -> SurfaceColor.PrimaryTransparent50
        }
    }

    val interactionSource = remember { MutableInteractionSource() }

    // State for backspace long press support
    // Only used for backspace, but must be declared unconditionally (Compose rules)
    var isPressed by remember { mutableStateOf(false) }

    // Track press/release via InteractionSource for backspace long-press
    // Uses clickable (same as number keys) so it doesn't steal focus from the amount field
    if (key is KeyPadKey.Backspace) {
        LaunchedEffect(interactionSource, enabled) {
            interactionSource.interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> {
                        isPressed = true
                        if (enabled) {
                            hapticFeedback.performHapticFeedback(HapticFeedbackType.VirtualKey)
                            onClick() // Immediate delete on press-down
                        }
                    }
                    is PressInteraction.Release, is PressInteraction.Cancel -> {
                        isPressed = false
                    }
                }
            }
        }

        // Continuous deletion while held
        LaunchedEffect(isPressed, enabled) {
            if (isPressed && enabled) {
                delay(500)
                while (isActive && isPressed) {
                    onClick()
                    delay(100)
                }
            }
        }
    }

    // Remember handleClick to avoid recreating on every recomposition
    val handleClick = remember(onClick, hapticFeedback) {
        {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.VirtualKey)
            onClick()
        }
    }

    Box(
        modifier = modifier
            .height(PopSpacing.Spacing56)
            .background(
                color = buttonBackgroundColor,
                shape = PopShapes.rounded(PopRadius.Medium)
            )
            .then(
                if (!enabled) {
                    Modifier // no interaction when disabled
                } else {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        // For backspace, tap is handled via InteractionSource press (immediate on down)
                        // For other keys, tap is handled here on click (on release)
                        onClick = if (key is KeyPadKey.Backspace) {{}} else handleClick
                    )
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        when (key) {
            is KeyPadKey.Backspace -> {
                PopVisualElement(
                    visualElement = PopIconConfig(
                        iconName = Icons.BackSpaceIcon,
                        style = IconStyle.Outline,
                        size = IconSize.Medium,
                        tint = TextColor.Primary,
                        contentDescription = "Backspace"
                    ).toVisualElement(),
                    contentDescription = "Backspace"
                )
            }
            else -> {
                Text(
                    text = key.displayValue,
                    style = PopTypography.figtreeStyles.headingLarge,
                    color = TextColor.Primary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0D) // Using SurfaceColor.Primary equivalent
@Composable
private fun PopKeyPadPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PopSpacing.Spacing16),
        contentAlignment = Alignment.Center
    ) {
        PopKeyPad(
            onKeyPress = {}
        )
    }
}
