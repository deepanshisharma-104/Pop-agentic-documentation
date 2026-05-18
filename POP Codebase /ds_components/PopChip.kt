package com.pop.components.ds_components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pop.components.models.PopChipConfig
import com.pop.components.models.VisualElement
import com.pop.components.theme.BorderColor
import com.pop.components.theme.IconColor
import com.pop.components.theme.IconSize
import com.pop.components.theme.IconStyle
import com.pop.components.theme.Icons
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.PopStroke
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import com.pop.compose_components.R
import com.pop.components.theme.PopIcons

/**
 * Variants for the PopChip component
 */
enum class PopChipVariant {
    /** Basic chip with label text only */
    Basic,
    
    /** Chip with dismissible close icon */
    WithClose,
    
    /** Chip with dropdown chevron icon */
    WithDropdown
}

/**
 * Mode for PopChip behavior
 */
enum class PopChipMode {
    /** Toggleable mode - changes active/inactive state on click */
    Toggleable,
    
    /** Static mode - always shows inactive UI, no state change on click */
    Static
}

/**
 * PopChip component - A customizable chip component with multiple variants
 *
 * Figma: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=1526-12162&m=dev
 *
 * Features:
 * - Multiple variants: Basic, WithClose, WithDropdown
 * - Two modes: Toggleable (changes active/inactive state on click) and Static (always inactive UI)
 * - Optional leading icon slot at the start of the chip
 * - Enabled/disabled states with proper color tokens
 * - Toggle functionality: Active state shows a border (only in Toggleable mode)
 * - Clickable chip with onClick applied to root element
 * - Pill-shaped design with proper spacing using SquircleShape
 *
 * @param config Configuration for the chip component
 * @param modifier Modifier for the component
 */
@Composable
fun PopChip(
    modifier: Modifier = Modifier,
    config: PopChipConfig,
) {
    // Handle toggle state for Toggleable mode
    var internalActiveState by remember { mutableStateOf(config.isActive) }
    
    // Sync internal state with config changes (for external control)
    LaunchedEffect(config.isActive) {
        if (config.mode == PopChipMode.Toggleable) {
            internalActiveState = config.isActive
        }
    }
    
    val effectiveActiveState = when (config.mode) {
        PopChipMode.Toggleable -> internalActiveState
        PopChipMode.Static -> false // Always inactive in Static mode
    }
    
    // Handle click - toggle state if Toggleable mode, then call onClick
    val handleClick: () -> Unit = {
        if (config.mode == PopChipMode.Toggleable && config.enabled) {
            internalActiveState = !internalActiveState
        }
        config.onClick?.invoke()
    }
    
    // Color tokens based on state
    val backgroundColor = if (config.enabled) {
        SurfaceColor.Secondary
    } else {
        SurfaceColor.SecondaryDisabled
    }
    
    val textColor = if (config.enabled) {
        TextColor.Primary
    } else {
        TextColor.Disabled
    }
    
    val iconColor = if (config.enabled) {
        IconColor.Primary
    } else {
        IconColor.Disabled
    }
    
    // Spacing tokens from design system
    val horizontalPadding = PopSpacing.Spacing10 // 10dp
    val verticalPadding = PopSpacing.Spacing6 // 6dp
    val iconSize = PopIcons.sizeSmall // 16dp
    val gapBetweenElements = PopSpacing.Spacing8 // 8dp
    
    val chipShape = SquircleShape(cornerRadius = null, smoothing = 1.0f) // Pill-shaped design
    val shouldShowActiveBorder = effectiveActiveState && config.enabled
    val chipAnimationDuration = 1000
    // Strong ease-in-out for very slow start and end.
    val chipSizeEasing = CubicBezierEasing(0.7f, 0f, 0.3f, 1f)
    val chipFadeEasing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)
    val animatedBorderWidth by animateDpAsState(
        targetValue = if (shouldShowActiveBorder) PopStroke.Default else 0.dp,
        animationSpec = tween(durationMillis = chipAnimationDuration, easing = chipSizeEasing),
        label = "chip_border_width"
    )
    val animatedBorderAlpha by animateFloatAsState(
        targetValue = if (shouldShowActiveBorder) 1f else 0f,
        animationSpec = tween(durationMillis = chipAnimationDuration, easing = chipFadeEasing),
        label = "chip_border_alpha"
    )
    val borderColorTarget = if (shouldShowActiveBorder) {
        BorderColor.PrimaryInvert.copy(alpha = animatedBorderAlpha)
    } else {
        Color.Transparent
    }
    val animatedBorderColor by animateColorAsState(
        targetValue = borderColorTarget,
        animationSpec = tween(durationMillis = chipAnimationDuration, easing = chipFadeEasing),
        label = "chip_border_color"
    )
    val closeIconStartGap by animateDpAsState(
        targetValue = if (config.variant == PopChipVariant.WithClose) gapBetweenElements else 0.dp,
        animationSpec = tween(durationMillis = chipAnimationDuration, easing = chipSizeEasing),
        label = "chip_close_icon_gap"
    )
    val closeIconSlotWidth by animateDpAsState(
        targetValue = if (config.variant == PopChipVariant.WithClose) iconSize else 0.dp,
        animationSpec = tween(durationMillis = chipAnimationDuration, easing = chipSizeEasing),
        label = "chip_close_icon_width"
    )
    val closeIconAlpha by animateFloatAsState(
        targetValue = if (config.variant == PopChipVariant.WithClose) 1f else 0f,
        animationSpec = if (config.variant == PopChipVariant.WithClose) {
            tween(durationMillis = 700, delayMillis = 140, easing = chipFadeEasing)
        } else {
            tween(durationMillis = 450, easing = chipFadeEasing)
        },
        label = "chip_close_icon_alpha"
    )
    
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = chipShape
            )
            .border(
                width = animatedBorderWidth,
                color = animatedBorderColor,
                shape = chipShape
            )
            .then(
                if ((config.onClick != null || config.mode == PopChipMode.Toggleable) && config.enabled) {
                    Modifier.clickable(onClick = handleClick)
                } else {
                    Modifier
                }
            )
            .defaultMinSize(minHeight = PopSpacing.Spacing28) // Minimum height for proper appearance
            .animateContentSize(
                animationSpec = tween(durationMillis = chipAnimationDuration, easing = chipSizeEasing)
            )
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Leading icon (if provided)
            config.leadingIcon?.let { iconConfig ->
                PopVisualElement(
                    visualElement = iconConfig.copy(
                        tint = iconColor,
                        size = IconSize.Small
                    ).toVisualElement(),
                    contentDescription = iconConfig.contentDescription
                )
                Spacer(modifier = Modifier.size(gapBetweenElements))
            }
            
            // Label text
            Text(
                text = config.text,
                style = PopTypography.figtreeStyles.labelXSmall,
                color = textColor
            )
            
            // Close icon slot width animates smoothly to avoid sudden layout jumps.
            Box(
                modifier = Modifier.size(
                    width = closeIconStartGap + closeIconSlotWidth,
                    height = iconSize
                ).clipToBounds(),
                contentAlignment = Alignment.CenterEnd
            ) {
                val closeIconVisualElement = VisualElement.buildFrom(
                    iconName = Icons.Cross,
                    style = IconStyle.Outline,
                    tintColor = iconColor,
                    heightDp = iconSize.value.toInt(),
                    widthDp = iconSize.value.toInt()
                )
                if (config.enabled && config.onCloseClick != null && config.variant == PopChipVariant.WithClose) {
                    Box(
                        modifier = Modifier
                            .size(iconSize)
                            .clickable(onClick = config.onCloseClick)
                            .alpha(closeIconAlpha),
                        contentAlignment = Alignment.Center
                    ) {
                        PopVisualElement(
                            visualElement = closeIconVisualElement,
                            modifier = Modifier
                                .size(iconSize)
                                .alpha(closeIconAlpha),
                            contentDescription = "Close"
                        )
                    }
                } else {
                    PopVisualElement(
                        visualElement = closeIconVisualElement,
                        modifier = Modifier
                            .size(iconSize)
                            .alpha(closeIconAlpha),
                        contentDescription = "Close"
                    )
                }
            }
            
            // Dropdown icon (for WithDropdown variant)
            // Note: onClick is handled at root level, no individual click handler needed
            if (config.variant == PopChipVariant.WithDropdown) {
                Spacer(modifier = Modifier.size(gapBetweenElements))
                val dropdownIconVisualElement = VisualElement.buildFrom(
                    iconName = Icons.ChevronDown16,
                    style = IconStyle.Outline,
                    fallbackResId = R.drawable.down_chevron,
                    tintColor = iconColor,
                    heightDp = iconSize.value.toInt(),
                    widthDp = iconSize.value.toInt()
                )
                
                PopVisualElement(
                    visualElement = dropdownIconVisualElement,
                    modifier = Modifier.size(iconSize),
                    contentDescription = "Dropdown"
                )
            }
        }
    }
}

