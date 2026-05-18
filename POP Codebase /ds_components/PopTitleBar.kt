package com.pop.components.ds_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pop.components.models.GradientWidth
import com.pop.components.models.PopTitleBarConfig
import com.pop.components.theme.IconName
import com.pop.components.theme.IconStyle
import com.pop.components.theme.PopIconResources
import com.pop.components.theme.PopTypography

/**
 * PopTitleBar component with title, body, icons, and optional marquee with gradients
 *
 * Figma: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=90-7176&m=dev
 *
 * Features:
 * - Title text with optional left and right icons
 * - Body text below title with optional left and right icons
 * - Custom slot composable at center-right
 * - Optional marquee effect when text overflows (uses Modifier.basicMarquee)
 * - Gradient overlays (left/right/both) when marquee is enabled and needed
 * - All elements are nullable and optional
 *
 * @param modifier Modifier for the component
 * @param config Configuration for the title bar
 * @param rightSlot Optional composable slot at center-right position
 */
@Composable
fun PopTitleBar(
    modifier: Modifier = Modifier,
    config: PopTitleBarConfig,
    rightSlot: (@Composable () -> Unit)? = null,
    onBodyRightIconClick: (() -> Unit)? = null
) {
    // Get colors directly from config
    val titleTextColor = config.titleTextColor
    val bodyTextColor = config.bodyTextColor
    
    // Density for dp to px conversion
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    // Text styles
    val titleStyle = config.titleTextStyle ?: PopTypography.figtreeStyles.headingLarge
    val bodyStyle = config.bodyTextStyle ?: PopTypography.labelXSmall

    // Calculate text widths eagerly for Gradient visibility logic
    val titleTextWidth = remember(config.titleText, titleStyle) {
        textMeasurer.measure(
            text = AnnotatedString(config.titleText),
            style = titleStyle,
            softWrap = false,
            maxLines = 1
        ).size.width.toFloat()
    }
    
    val bodyTextWidth = remember(config.bodyText, bodyStyle) {
        if (config.bodyText != null) {
            textMeasurer.measure(
                text = AnnotatedString(config.bodyText),
                style = bodyStyle,
                softWrap = false,
                maxLines = 1
            ).size.width.toFloat()
        } else {
            0f
        }
    }

    // Calculate total content widths
    val titleContentWidth = remember(titleTextWidth, config.titleLeftIcon, config.titleRightIcon, config.iconGapDp, config.titleIconSizeDp) {
        val iconGapPx = with(density) { config.iconGapDp.dp.toPx() }
        val iconSizePx = with(density) { config.titleIconSizeDp.dp.toPx() }
        
        val leftIconW = if (config.titleLeftIcon != null) iconSizePx else 0f
        val rightIconW = if (config.titleRightIcon != null) iconSizePx else 0f
        
        val leftGap = if (config.titleLeftIcon != null) iconGapPx else 0f
        val rightGap = if (config.titleRightIcon != null) iconGapPx else 0f
        
        titleTextWidth + leftIconW + rightIconW + leftGap + rightGap
    }
    
    val bodyContentWidth = remember(bodyTextWidth, config.bodyLeftIcon, config.bodyRightIcon, config.iconGapDp, config.bodyIconSizeDp) {
        val iconGapPx = with(density) { config.iconGapDp.dp.toPx() }
        val iconSizePx = with(density) { config.bodyIconSizeDp.dp.toPx() }
        
        val leftIconW = if (config.bodyLeftIcon != null) iconSizePx else 0f
        val rightIconW = if (config.bodyRightIcon != null) iconSizePx else 0f
        
        val leftGap = if (config.bodyLeftIcon != null) iconGapPx else 0f
        val rightGap = if (config.bodyRightIcon != null) iconGapPx else 0f
        
        bodyTextWidth + leftIconW + rightIconW + leftGap + rightGap
    }

    // Container width state
    var containerWidth by remember { mutableFloatStateOf(0f) }

    // Calculate available width (subtract slot width if present)
    val availableWidth = containerWidth

    // We only need containerWidth to be valid
    val measurementsReady = containerWidth > 0f

    // Determine if text overflows (for gradient visibility)
    val titleOverflows = measurementsReady && titleContentWidth > availableWidth
    val bodyOverflows = measurementsReady && config.bodyText != null && bodyContentWidth > availableWidth

    // Determine if marquee animation should be active (only for title, body doesn't animate)
    val titleNeedsMarquee = config.enableMarquee && titleOverflows

    // Calculate overflow for title (needed for scroll duration calculation)
    val titleIconSpacePx = (titleContentWidth - titleTextWidth).coerceAtLeast(0f)
    val titleAvailableTextWidth = (availableWidth - titleIconSpacePx).coerceAtLeast(0f)
    val titleOverflowPx = (titleTextWidth - titleAvailableTextWidth).coerceAtLeast(0f)

    // Calculate gradient width for title (auto-select based on text width if not specified)
    val titleGradientWidth = remember(config.gradientWidth, titleTextWidth) {
        config.gradientWidth?.widthDp ?: GradientWidth.fromTextWidth(titleTextWidth).widthDp
    }
    
    // Calculate gradient width for body (auto-select based on text width if not specified)
    val bodyGradientWidth = remember(config.gradientWidth, bodyTextWidth) {
        config.gradientWidth?.widthDp ?: GradientWidth.fromTextWidth(bodyTextWidth).widthDp
    }
    
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(config.paddingDp.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Main content area (title + body)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .onSizeChanged { size ->
                        containerWidth = size.width.toFloat()
                    }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalArrangement = Arrangement.spacedBy(config.titleBodyGapDp.dp)
                ) {
                    // Title row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Title left icon
                        config.titleLeftIcon?.let { iconName ->
                            IconWithSize(
                                iconName = iconName,
                                sizeDp = config.titleIconSizeDp.dp,
                                tint = titleTextColor
                            )
                            Spacer(modifier = Modifier.width(config.iconGapDp.dp))
                        }
                        
                        // Title text
                        PopMarqueeText(
                            modifier = Modifier.weight(1f, fill = false),
                            text = config.titleText,
                            style = titleStyle,
                            color = titleTextColor,
                            shouldAnimate = titleNeedsMarquee,
                            textOverflows = titleOverflows,
                            speedPxPerSecond = config.marqueeSpeedPxPerSecond,
                            delayMillis = config.marqueeStayDurationMs,
                            loopCount = config.marqueeLoopCount,
                            loopDelayMillis = config.marqueeStayDurationMs,
                            maxLines = 1,
                            gradientWidth = titleGradientWidth,
                            showLeftGradient = config.gradientLeft,
                            showRightGradient = config.gradientRight,
                            overflowDistancePx = titleOverflowPx
                        )
                        
                        // Title right icon
                        config.titleRightIcon?.let { iconName ->
                            Spacer(modifier = Modifier.width(config.iconGapDp.dp))
                            IconWithSize(
                                iconName = iconName,
                                sizeDp = config.titleIconSizeDp.dp,
                                tint = titleTextColor
                            )
                        }
                    }
                    
                    // Body row (if body text exists)
                    config.bodyText?.let { body ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Body left icon
                            config.bodyLeftIcon?.let { iconName ->
                                IconWithSize(
                                    iconName = iconName,
                                    sizeDp = config.bodyIconSizeDp.dp,
                                    tint = bodyTextColor
                                )
                                Spacer(modifier = Modifier.width(config.iconGapDp.dp))
                            }
                            
                            // Body text - no animation, supports maxLines and right gradient when overflowing
                            PopMarqueeText(
                                modifier = Modifier.weight(1f, fill = false),
                                text = body,
                                style = bodyStyle,
                                color = bodyTextColor,
                                shouldAnimate = false, // Body text doesn't animate
                                textOverflows = bodyOverflows,
                                maxLines = config.bodyMaxLines,
                                gradientWidth = bodyGradientWidth,
                                showLeftGradient = false, // Body doesn't animate, so no left gradient
                                showRightGradient = config.gradientRight
                            )
                            
                            // Body right icon
                            config.bodyRightIcon?.let { iconName ->
                                Spacer(modifier = Modifier.width(config.iconGapDp.dp))
                                IconWithSize(
                                    iconName = iconName,
                                    sizeDp = config.bodyIconSizeDp.dp,
                                    tint = bodyTextColor,
                                    onClick = onBodyRightIconClick
                                )
                            }
                        }
                    }
                }
            }
            
            // Center-right slot
            rightSlot?.invoke()
        }
    }
}

/**
 * Helper composable to render icon with custom size
 */
@Composable
private fun IconWithSize(
    iconName: IconName,
    sizeDp: Dp,
    tint: Color,
    onClick: (() -> Unit)? = null
) {
    val resourceId = PopIconResources.getIconResourceId(iconName, IconStyle.Outline)
    resourceId?.let { id ->
        Image(
            painter = painterResource(id = id),
            contentDescription = null,
            modifier = Modifier
                .size(sizeDp)
                .then(
                    if (onClick != null) {
                        Modifier.clickable { onClick() }
                    } else {
                        Modifier
                    }
                ),
            colorFilter = ColorFilter.tint(tint),
            contentScale = ContentScale.Fit
        )
    }
}

