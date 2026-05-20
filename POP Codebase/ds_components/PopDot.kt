package com.pop.components.ds_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopDotColor
import com.pop.components.theme.PopDotSize
import com.pop.components.theme.PopGradient
import com.pop.components.theme.toDotDp
import com.pop.components.utils.glowEffect
import com.pop.components.utils.popBackground

/**
 * PopDot composable that programmatically creates dots with gradients and glow effects.
 * Based on Figma design specifications.
 *
 * Uses existing modifier extensions:
 * - [popBackground] for radial gradients
 * - [glowEffect] for glow effects
 *
 * Creates circular dots with:
 * - Radial gradients based on color variant
 * - Glow effects for active state
 * - Proper sizing based on size enum and active state
 *
 * @param modifier Optional modifier for additional styling
 * @param color The dot color - Orange, White, Green, or Blue (default: Orange)
 * @param size The dot size - Large or Med (default: Large)
 * @param isActive Whether the dot is in active state (default: true)
 * @param contentDescription Optional content description for accessibility
 */
@Composable
fun PopDot(
    modifier: Modifier = Modifier,
    color: PopDotColor = PopDotColor.Orange,
    size: PopDotSize = PopDotSize.Large,
    isActive: Boolean = true,
    contentDescription: String? = null,
) {
    val dotSize = size.toDotDp()

    // Active dots are slightly larger and have glow
    val actualSize = if (isActive) {
        dotSize * 1.25f // Active dots are 25% larger
    } else {
        dotSize
    }

    // Get gradient for the dot
    val gradient = getGradient(color, isActive)

    // Get glow color for active dots
    val glowColor = if (isActive) {
        getGlowColor(color)
    } else {
        null
    }

    // Calculate padding needed for glow effect
    // Glow uses blurRadius (4.dp) + spreadRadius (1.dp) = 5.dp, so we need padding on all sides
    val glowPadding = if (isActive) {
        8.dp // Enough space for blur (4.dp) + spread (1.dp) + some extra
    } else {
        0.dp
    }

    // Total size includes padding for glow
    val totalSize = actualSize + (glowPadding * 2)

    Box(
        modifier = modifier
            .size(totalSize)
            .then(
                if (glowColor != null) {
                    Modifier.glowEffect(
                        blurRadius = 4.dp,
                        spreadRadius = 3.dp,
                        color = glowColor,
                        shape = CircleShape
                    )
                } else {
                    Modifier
                }
            )
            .clip(CircleShape)
            .popBackground(
                gradient = gradient,
                shape = CircleShape
            )
    )
}

/**
 * Get PopGradient based on color variant and active state.
 */
private fun getGradient(color: PopDotColor, isActive: Boolean): PopGradient {
    val alpha = if (isActive) 1f else 0.5f

    val colors = when (color) {
        PopDotColor.Orange -> listOf(
            PopColor.Brand.Brand400.copy(alpha = alpha), // Lighter orange
            PopColor.Brand.Brand600.copy(alpha = alpha), // Main orange
            PopColor.Brand.Brand700.copy(alpha = alpha)  // Darker orange
        )
        PopDotColor.White -> listOf(
            PopColor.WhiteBlack.White.copy(alpha = alpha), // Bright white
            PopColor.Grey.Grey200.copy(alpha = alpha),     // Light grey
            PopColor.Grey.Grey300.copy(alpha = alpha)      // Medium grey
        )
        PopDotColor.Green -> listOf(
            PopColor.Success.Success400.copy(alpha = alpha), // Lighter green
            PopColor.Success.Success600.copy(alpha = alpha), // Main green
            PopColor.Success.Success700.copy(alpha = alpha)  // Darker green
        )
        PopDotColor.Blue -> listOf(
            Color(0xFF4A90E2).copy(alpha = alpha), // Lighter blue
            Color(0xFF2E5CFF).copy(alpha = alpha), // Main blue
            Color(0xFF1E3A8A).copy(alpha = alpha)  // Darker blue
        )
    }

    return PopGradient.Radial(
        colors = colors,
        stops = listOf(0.0f, 0.6f, 1.0f),
        alignment = Alignment.Center
    )
}

/**
 * Get glow color for active dots based on color variant.
 */
private fun getGlowColor(color: PopDotColor): Color {
    return when (color) {
        PopDotColor.Orange -> PopColor.Brand.Brand600.copy(alpha = 0.3f)
        PopDotColor.White -> PopColor.WhiteBlack.White.copy(alpha = 0.3f)
        PopDotColor.Green -> PopColor.Success.Success600.copy(alpha = 0.3f)
        PopDotColor.Blue -> Color(0xFF2E5CFF).copy(alpha = 0.3f)
    }
}

@Preview(showBackground = true)
@Composable
private fun PopDotPreview() {
    Box(Modifier.padding(10.dp)){
        PopDot(color = PopDotColor.Blue)
    }
}

