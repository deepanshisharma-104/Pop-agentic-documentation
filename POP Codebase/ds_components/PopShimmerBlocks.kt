package com.pop.components.ds_components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopSpacing

/**
 * PopShimmerBlocks - Design System Shimmer Loader Blocks Component
 * 
 * Based on Figma Design System: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=8244-51274
 * 
 * Provides stacked shimmer loader blocks for full-screen loading states:
 * - List stack: Stacked list items (652px height, 369px width)
 * - Payment list: Stacked payment list items (652px height, 369px width)
 * - Card stack: Stacked card items (704px height, 369px width)
 * 
 * @param type The type of loader block (List stack, Payment list, Card stack)
 * @param modifier Optional modifier for the loader
 */
@Composable
fun PopShimmerBlocks(
    modifier: Modifier = Modifier,
    type: LoaderBlockType = LoaderBlockType.ListStack,
    itemCount: Int = 5,
) {
    when (type) {
        LoaderBlockType.ListStack -> {
            ListStackLoader(modifier = modifier, itemCount = itemCount)
        }
        LoaderBlockType.PaymentList -> {
            PaymentListStackLoader(modifier = modifier, itemCount = itemCount)
        }
        LoaderBlockType.CardStack -> {
            CardStackLoader(modifier = modifier, itemCount = itemCount)
        }
    }
}

/**
 * Loader block types matching Figma design system
 */
enum class LoaderBlockType {
    ListStack,
    PaymentList,
    CardStack
}

/**
 * List stack loader - Stacked list items
 * 652px height, 369px width
 * 9 list items, each with circle on left and rounded rectangle on right
 */
@Composable
private fun ListStackLoader(
    modifier: Modifier,
    itemCount: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing12)
    ) {
        repeat(itemCount) {
            Row {
                // Circle on left
                PopShimmerBox(
                    modifier = Modifier.size(PopSpacing.Spacing44),
                    shape = CircleShape
                )
                Spacer(modifier = Modifier.width(PopSpacing.Spacing12))
                // Rounded rectangle on right
                PopShimmerBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(PopSpacing.Spacing44),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
            }
        }
    }
}

/**
 * Payment list stack loader - Stacked payment list items
 * 652px height, 369px width
 * 9 payment list items, each with circle on left, rounded rectangle in middle, and smaller rounded rectangle on right
 */
@Composable
private fun PaymentListStackLoader(
    modifier: Modifier,
    itemCount: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing12)
    ) {
        repeat(itemCount) {
            Row {
                // Circle on left
                PopShimmerBox(
                    modifier = Modifier.size(PopSpacing.Spacing44),
                    shape = CircleShape
                )
                Spacer(modifier = Modifier.width(PopSpacing.Spacing12))
                // Rounded rectangle in middle
                PopShimmerBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(PopSpacing.Spacing44),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
                Spacer(modifier = Modifier.width(PopSpacing.Spacing32))
                // Smaller rounded rectangle on right
                PopShimmerBox(
                    modifier = Modifier
                        .width(PopSpacing.Spacing56)
                        .height(PopSpacing.Spacing44),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
            }
        }
    }
}

/**
 * Card stack loader - Stacked card items
 * 704px height, 369px width
 * 5 large card blocks stacked vertically
 */
@Composable
private fun CardStackLoader(
    modifier: Modifier,
    itemCount: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing16)
    ) {
        repeat(itemCount) {
            PopShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp),
                shape = RoundedCornerShape(PopRadius.Medium)
            )
        }
    }
}

/**
 * Base shimmer box with animated gradient effect
 * 
 * Creates a shimmer effect using a gradient brush that animates across the content.
 * Uses design system colors for the shimmer effect.
 * 
 * @param modifier Modifier for the shimmer box
 * @param shape Shape of the shimmer box (default: RoundedCornerShape)
 */
@Composable
fun PopShimmerBox(
    modifier: Modifier,
    shape: Shape
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    var size by remember { mutableStateOf(Size.Zero) }
    
    // Animate the gradient offset from -1 to 1 (left to right)
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )
    
    // Shimmer colors: base color and highlight
    // Using grey colors from design system for shimmer effect
    val baseColor = PopColor.Grey.Grey800 // Dark base
    val highlightColor = PopColor.Grey.Grey600 // Lighter highlight
    
    // Calculate gradient width (3x the content width for smooth sweep)
    val gradientWidth = if (size.width > 0) size.width * 3 else 1000f
    val gradientCenter = shimmerOffset * gradientWidth
    
    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                size = coordinates.size.toSize()
            }
            .clip(shape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        baseColor,
                        baseColor,
                        highlightColor,
                        highlightColor,
                        baseColor,
                        baseColor
                    ),
                    start = Offset(
                        x = gradientCenter - gradientWidth / 3,
                        y = 0f
                    ),
                    end = Offset(
                        x = gradientCenter + gradientWidth / 3,
                        y = 0f
                    )
                )
            )
    )
}

