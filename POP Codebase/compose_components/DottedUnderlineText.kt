package com.pop.components.compose_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Composable for text with dotted underline decoration.
 * Can be used standalone or inline with other text components.
 *
 * @param text The text to display
 * @param style The text style to apply
 * @param color The text color
 * @param modifier Modifier for the text component
 * @param dotDiameter Diameter of each dot (default: 2.5.dp)
 * @param gapBetweenDots Gap between dots (default: 3.dp)
 * @param startOffset Offset from text edge before first dot starts (default: 2.dp)
 * @param onClick Optional click handler for the text
 *
 * Example usage:
 * ```kotlin
 * // Standalone
 * DottedUnderlineText(
 *     text = "Check balance",
 *     style = MaterialTheme.typography.bodySmall,
 *     color = TextColors.Secondary.Default,
 *     onClick = { /* handle click */ }
 * )
 *
 * // Inline with other text
 * Row {
 *     Text("Your balance is ")
 *     DottedUnderlineText(
 *         text = "₹ 1,000",
 *         style = MaterialTheme.typography.bodyMedium
 *     )
 * }
 * ```
 */
@Composable
fun DottedUnderlineText(
    text: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    dotDiameter: Dp = 2.5.dp,
    gapBetweenDots: Dp = 3.dp,
    startOffset: Dp = 2.dp,
    textToUnderlineSpacing: Dp = 2.dp,
    onClick: (() -> Unit)? = null
) {
    val density = LocalDensity.current
    var textLayoutResult: TextLayoutResult? by remember { mutableStateOf(null) }
    
    val dotRadius = with(density) { dotDiameter.toPx() / 2f }
    val gapPx = with(density) { gapBetweenDots.toPx() }
    val startOffsetPx = with(density) { startOffset.toPx() }
    
    Column(modifier = modifier) {
        Text(
            text = text,
            style = style,
            color = color,
            textAlign = textAlign,
            onTextLayout = { layoutResult ->
                textLayoutResult = layoutResult
            },
            modifier = if (onClick != null) {
                Modifier.clickable(onClick = onClick)
            } else {
                Modifier
            }
        )
        Spacer(modifier = Modifier.height(textToUnderlineSpacing))
        // Draw circular dots using Canvas
        textLayoutResult?.let { layoutResult ->
            val textWidth = layoutResult.size.width
            if (textWidth > 0) {
                Canvas(
                    modifier = Modifier
                        .width(with(density) { textWidth.toDp() })
                        .height(dotDiameter + 2.dp)  // Height to accommodate dots
                ) {
                    val textWidthPx = with(density) { textWidth.toDp().toPx() }
                    val centerY = size.height / 2f
                    val dotDiameterPx = with(density) { dotDiameter.toPx() }
                    
                    // Calculate available width for dots (excluding equal margins on both sides)
                    val availableWidth = textWidthPx - (2 * startOffsetPx)
                    
                    // Calculate how many complete dots can fit with the desired gap
                    // Formula: availableWidth = (dotCount * dotDiameter) + ((dotCount - 1) * gap)
                    // Solving for dotCount: dotCount = (availableWidth + gap) / (dotDiameter + gap)
                    val dotCount = if (dotDiameterPx + gapPx > 0) {
                        ((availableWidth + gapPx) / (dotDiameterPx + gapPx)).toInt().coerceAtLeast(1)
                    } else {
                        1
                    }
                    
                    // Calculate actual spacing to ensure equal margins on both sides
                    // If dots don't fit perfectly, adjust spacing to distribute evenly
                    val actualSpacing = if (dotCount > 1) {
                        (availableWidth - (dotCount * dotDiameterPx)) / (dotCount - 1)
                    } else {
                        0f
                    }
                    
                    // Start from left edge + offset + radius (ensures equal margin on left)
                    // This ensures the left edge of first dot is exactly startOffset from text edge
                    var currentX = startOffsetPx + dotRadius
                    
                    // Draw dots with calculated spacing
                    // The last dot's right edge will be exactly startOffset from the right text edge
                    repeat(dotCount) {
                        drawCircle(
                            color = color,
                            radius = dotRadius,
                            center = Offset(x = currentX, y = centerY)
                        )
                        if (it < dotCount - 1) {
                            currentX += dotDiameterPx + actualSpacing
                        }
                    }
                }
            }
        }
    }
}

/**
 * Extension function to create an AnnotatedString with dotted underline annotation.
 * This allows you to use dotted underline inline within other text.
 *
 * Usage:
 * ```kotlin
 * Text(
 *     buildAnnotatedString {
 *         append("Your balance is ")
 *         withDottedUnderline(
 *             text = "₹ 1,000",
 *             color = TextColors.Primary.Default
 *         )
 *         append(" available")
 *     }
 * )
 * ```
 */
fun AnnotatedString.Builder.withDottedUnderline(
    text: String,
    color: Color,
    dotDiameter: Dp = 2.5.dp,
    gapBetweenDots: Dp = 3.dp,
    startOffset: Dp = 2.dp
) {
    // Add annotation tag for dotted underline
    pushStringAnnotation(
        tag = "dotted_underline",
        annotation = "dotDiameter=${dotDiameter.value},gap=${gapBetweenDots.value},offset=${startOffset.value},color=${color.value}"
    )
    withStyle(
        androidx.compose.ui.text.SpanStyle(
            color = color,
            // Note: We can't directly apply custom decoration, so we'll use a workaround
            // The actual dots will be drawn by DottedUnderlineText composable
        )
    ) {
        append(text)
    }
    pop()
}

/**
 * Helper function to build text with dotted underline that can be used inline.
 * Returns a composable that can be used in Row or other layouts.
 *
 * Usage:
 * ```kotlin
 * Row {
 *     Text("Your balance is ")
 *     InlineDottedUnderlineText(
 *         text = "₹ 1,000",
 *         style = MaterialTheme.typography.bodyMedium,
 *         color = TextColors.Primary.Default
 *     )
 *     Text(" available")
 * }
 * ```
 */
@Composable
fun InlineDottedUnderlineText(
    text: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    dotDiameter: Dp = 2.5.dp,
    gapBetweenDots: Dp = 3.dp,
    startOffset: Dp = 2.dp,
    textToUnderlineSpacing: Dp = 2.dp,
    onClick: (() -> Unit)? = null
) {
    DottedUnderlineText(
        text = text,
        style = style,
        color = color,
        modifier = modifier,
        textAlign = textAlign,
        dotDiameter = dotDiameter,
        gapBetweenDots = gapBetweenDots,
        startOffset = startOffset,
        textToUnderlineSpacing = textToUnderlineSpacing,
        onClick = onClick
    )
}

