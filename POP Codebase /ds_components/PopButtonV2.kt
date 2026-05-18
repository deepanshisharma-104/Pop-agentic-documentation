package com.pop.components.ds_components

import android.os.SystemClock
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.models.VisualElement
import com.pop.components.theme.BorderColor
import com.pop.components.theme.IconColor
import com.pop.components.theme.Icons
import com.pop.components.theme.IconStyle
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopTypography
import com.pop.components.theme.PrimaryColor
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import com.pop.components.utils.NoisePresets
import com.pop.components.utils.intenseGlowEffect
import com.pop.components.utils.noiseOverlay
import com.pop.components.utils.popBackground
import com.pop.components.utils.popBorder
import com.pop.compose_components.R

/**
 * PopButton V2 - Label button with optional left and right icons
 *
 * Supports variants: Primary, Secondary, Tertiary, BrandPrimary, White, Ghost, BrandGhost
 * Supports states: Active, Disabled, Loading (with LoadingState), Destructive
 *
 * For loading states, use ButtonState.Loading with loadingState parameter:
 * - LoadingState.Default: Shows spinner
 * - LoadingState.Success: Shows success icon (checkmark)
 * - LoadingState.Destructive: Shows destructive icon (cross)
 * - LoadingState.Intermediate: For future use
 *
 * Width is controlled via [widthType] parameter:
 * - [ButtonWidthType.Wrap]: Button wraps content width (default)
 * - [ButtonWidthType.Fill]: Button fills available width (full clickable area)
 *
 * Disabled button clicks can be handled via [onDisabledClick] parameter.
 * When provided, disabled buttons will still be clickable and trigger this callback.
 *
 * Corner radius is fully round (height/2) for pill shape.
 * Icons are placed next to label with no gap, then horizontal padding is applied.
 */
@Composable
fun PopButtonV2(
    text: CharSequence,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Primary,
    state: ButtonState = ButtonState.Active,
    buttonLoadingState: ButtonLoadingState? = null, // Only used when state == Loading
    size: ButtonSize = ButtonSize.Large,
    leftIcon: VisualElement? = null,
    rightIcon: VisualElement? = null,
    widthType: ButtonWidthType = ButtonWidthType.Wrap, // Controls button width behavior
    onDisabledClick: (() -> Unit)? = null, // Optional callback for disabled button clicks
    onClick: () -> Unit
) {
    val safeOnClick = rememberDebouncedClick(onClick = onClick)
    val safeOnDisabledClick = onDisabledClick?.let {
        rememberDebouncedClick(onClick = it)
    }

    // Determine enabled and loading states from ButtonState
    val enabled = state != ButtonState.Disabled
    val isLoading = state == ButtonState.Loading
    val effectiveButtonLoadingState = buttonLoadingState ?: ButtonLoadingState.Default // Default to Default if not specified
    val isSpinnerLoading = isLoading && effectiveButtonLoadingState == ButtonLoadingState.Default // Only show spinner for Default loading state

    // Determine if we should show success/error icons based on loading state
    val showSuccessIcon = isLoading && effectiveButtonLoadingState == ButtonLoadingState.Success
    val showErrorIcon = isLoading && effectiveButtonLoadingState == ButtonLoadingState.Destructive

    // Handle loading state icons - show check/cross icons for loading success/destructive
    val actualLeftIcon = leftIcon
    val actualRightIcon = when {
        showSuccessIcon -> {
            VisualElement.buildFrom(Icons.Check, IconStyle.Outline, fallbackResId = R.drawable.ic_check)
        }
        showErrorIcon -> {
            VisualElement.buildFrom(Icons.Cross, IconStyle.Outline, fallbackResId = R.drawable.ic_close)
        }
        else -> rightIcon
    }

    // Exact dimensions from Figma
    val buttonHeight = when (size) {
        ButtonSize.XSmall -> ButtonSpecs.Height.XSmall
        ButtonSize.Small -> ButtonSpecs.Height.Small
        ButtonSize.Medium -> ButtonSpecs.Height.Medium
        ButtonSize.Large -> ButtonSpecs.Height.Large
    }

    val horizontalPadding = when (size) {
        ButtonSize.XSmall -> ButtonSpecs.Padding.XSmall
        ButtonSize.Small -> ButtonSpecs.Padding.Small
        ButtonSize.Medium -> ButtonSpecs.Padding.Medium
        ButtonSize.Large -> ButtonSpecs.Padding.Large
    }

    // Exact font styles from Figma
    val (fontSize, fontWeight, lineHeight) = when (size) {
        ButtonSize.XSmall -> Triple(
            ButtonSpecs.FontSize.XSmall,
            ButtonSpecs.FontWeight.Medium,
            ButtonSpecs.LineHeight.XSmall
        )
        ButtonSize.Small -> Triple(
            ButtonSpecs.FontSize.Small,
            ButtonSpecs.FontWeight.Medium,
            ButtonSpecs.LineHeight.Small
        )
        ButtonSize.Medium -> Triple(
            ButtonSpecs.FontSize.Medium,
            ButtonSpecs.FontWeight.Medium,
            ButtonSpecs.LineHeight.Medium
        )
        ButtonSize.Large -> Triple(
            ButtonSpecs.FontSize.Large,
            ButtonSpecs.FontWeight.SemiBold,
            ButtonSpecs.LineHeight.Large
        )
    }

    // Determine if button should be outlined (Ghost variants)
    val isOutlined = variant == ButtonVariant.Ghost || variant == ButtonVariant.BrandGhost

    val buttonConfig = getButtonConfig(variant, state, effectiveButtonLoadingState, isOutlined)

    // Corner radius is fully round (height/2) for pill shape
    val cornerRadius = buttonHeight / 2.dp
    val buttonShape = RoundedCornerShape(cornerRadius.dp)

    val inlineContentMap = mapOf(
        "coin" to InlineTextContent(
            Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_popcoin_white_spiral_orange_background),
                modifier = Modifier.fillMaxSize(),
                contentDescription = ""
            )
        }
    )

    // Determine if button should fill width
    val shouldFillWidth = widthType == ButtonWidthType.Fill

    // Determine if we need to handle disabled clicks
    val isDisabled = state == ButtonState.Disabled
    val shouldHandleDisabledClick = isDisabled && safeOnDisabledClick != null

    val baseModifier = modifier
        .height(buttonHeight)
        .then(if (shouldFillWidth) Modifier.fillMaxWidth() else Modifier)
        .then(
            if (shouldHandleDisabledClick) {
                Modifier.clickable(enabled = true, onClick = { safeOnDisabledClick?.invoke() })
            } else Modifier
        )

    // Border modifier for outlined variants (applied to TextButton)
    val outlinedBorderModifier = if (isOutlined && buttonConfig.borderColor != null) {
        Modifier.popBorder(
            width = ButtonSpecs.BorderWidth,
            gradient = PopGradient.Solid(buttonConfig.borderColor),
            shape = buttonShape
        )
    } else Modifier

    // Border modifier for filled BrandPrimary buttons only (applied to Box wrapper)
    val brandPrimaryBorderModifier = if (!isOutlined &&
        variant == ButtonVariant.BrandPrimary &&
        (state == ButtonState.Active || (state == ButtonState.Loading && effectiveButtonLoadingState == ButtonLoadingState.Default)) &&
        buttonConfig.borderColor != null) {
        Modifier.popBorder(
            width = ButtonSpecs.BorderWidth,
            gradient = PopGradient.Solid(buttonConfig.borderColor),
            shape = buttonShape
        )
    } else Modifier

    // Border modifier for filled Secondary buttons (applied to Box wrapper)
    // Applies to Active, Disabled, Loading (Default), and Destructive states
    val secondaryBorderModifier = if (!isOutlined &&
        variant == ButtonVariant.Secondary &&
        (state == ButtonState.Active ||
                state == ButtonState.Disabled ||
                (state == ButtonState.Loading && effectiveButtonLoadingState == ButtonLoadingState.Default) ||
                state == ButtonState.Destructive) &&
        buttonConfig.borderColor != null) {
        Modifier.popBorder(
            width = ButtonSpecs.BorderWidth,
            gradient = PopGradient.Solid(buttonConfig.borderColor),
            shape = buttonShape
        )
    } else Modifier

    // Determine if noise effect should be applied
    // ONLY apply to BrandPrimary buttons in Active or Loading states (NOT Primary)
    val shouldApplyNoise = buttonConfig.gradient != null && !isOutlined &&
            variant == ButtonVariant.BrandPrimary &&
            (state == ButtonState.Active || (state == ButtonState.Loading && effectiveButtonLoadingState == ButtonLoadingState.Default))

    // Determine if glow effect should be applied
    // Apply to Primary buttons (white glow) and BrandPrimary (brand glow) in Active/Loading states
    val shouldApplyPrimaryGlow = !isOutlined &&
            variant == ButtonVariant.Primary &&
            (state == ButtonState.Active || (state == ButtonState.Loading && effectiveButtonLoadingState == ButtonLoadingState.Default))
    val shouldApplyBrandGlow = !isOutlined &&
            variant == ButtonVariant.BrandPrimary &&
            (state == ButtonState.Active || (state == ButtonState.Loading && effectiveButtonLoadingState == ButtonLoadingState.Default))


    // Calculate padding for consistent button dimensions
    // This padding ensures all buttons (with or without glow) have the same outer dimensions
    // Glow uses blurRadius (16.dp) + spreadRadius (1.dp) = 17.dp, so we use 16.dp padding on all sides
    val buttonPadding = 8.dp

    // Wrap button in Box for gradient background support
    // Note: Don't clip the outer Box - glow effect needs space outside bounds
    // Always apply padding to outer Box for consistent dimensions across all button variants
    Box(
        modifier = modifier.padding(buttonPadding)
    ) {
        Box(
            modifier = baseModifier
                .then(
                    when {
                        shouldApplyBrandGlow -> Modifier
                            .graphicsLayer { clip = false }
                            .intenseGlowEffect(
                                glowRadius = 8.dp,
                                spread = 0.5.dp,
                                color = PopColor.Brand.Brand500.copy(alpha = 0.001f),
                                layers = 1,
                                shape = buttonShape
                            )
                        shouldApplyPrimaryGlow -> Modifier
                            .graphicsLayer { clip = false }
                            .intenseGlowEffect(
                                glowRadius = 8.dp,
                                spread = 1.dp,
                                color = Color.White.copy(alpha = 0.01f),
                                layers = 1,
                                shape = buttonShape
                            )
                        else -> Modifier
                    }
                )
                .clip(buttonShape) // Clip all content to rounded corners
                .then(
                    if (buttonConfig.gradient != null && !isOutlined) {
                        // Avoid double anti-aliasing: parent is already clipped.
                        Modifier.popBackground(gradient = buttonConfig.gradient)
                    } else Modifier
                )
                .then(
                    if (shouldApplyNoise) {
                        Modifier.noiseOverlay(NoisePresets.BrandButton)
                    } else Modifier
                )
                .then(brandPrimaryBorderModifier) // Border for filled BrandPrimary buttons
                .then(secondaryBorderModifier) // Border for filled Secondary buttons
        ) {
            // Use TextButton for outlined variants, regular Button for filled
            if (isOutlined) {
                TextButton(
                    onClick = safeOnClick,
                    modifier = Modifier
                        .then(outlinedBorderModifier)
                        .then(if (shouldFillWidth) Modifier.fillMaxWidth() else Modifier)
                        .then(if (shouldFillWidth) Modifier else Modifier.align(Alignment.Center))
                        .fillMaxHeight(),
                    enabled = enabled && !isLoading,
                    shape = buttonShape,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = buttonConfig.textColor,
                        disabledContentColor = buttonConfig.disabledTextColor
                    ),
                    contentPadding = PaddingValues(horizontal = horizontalPadding)
                ) {
                    ButtonContent(
                        text = text,
                        leftIcon = actualLeftIcon,
                        rightIcon = actualRightIcon,
                        isLoading = isSpinnerLoading,
                        size = size,
                        fontSize = fontSize,
                        fontWeight = fontWeight,
                        lineHeight = lineHeight,
                        textColor = if(enabled) buttonConfig.textColor else buttonConfig.disabledTextColor,
                        inlineContentMap = inlineContentMap,
                        isSuccessOrErrorIcon = showSuccessIcon || showErrorIcon // Indicates if rightIcon is success/error icon (should show before text)
                    )
                }
            } else {
                Button(
                    onClick = safeOnClick,
                    modifier = Modifier
                        .then(if (shouldFillWidth) Modifier.fillMaxWidth() else Modifier)
                        .then(if (shouldFillWidth) Modifier else Modifier.align(Alignment.Center))
                        .fillMaxHeight(),
                    enabled = enabled && !isLoading,
                    shape = buttonShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (buttonConfig.gradient == null) buttonConfig.backgroundColor else Color.Transparent,
                        contentColor = if(enabled) buttonConfig.textColor else buttonConfig.disabledTextColor,
                        disabledContainerColor = if (buttonConfig.gradient == null) buttonConfig.disabledBackgroundColor else Color.Transparent,
                        disabledContentColor = buttonConfig.disabledTextColor
                    ),
                    contentPadding = PaddingValues(horizontal = horizontalPadding)
                ) {
                    ButtonContent(
                        text = text,
                        leftIcon = actualLeftIcon,
                        rightIcon = actualRightIcon,
                        isLoading = isSpinnerLoading,
                        size = size,
                        fontSize = fontSize,
                        fontWeight = fontWeight,
                        lineHeight = lineHeight,
                        textColor = if(enabled) buttonConfig.textColor else buttonConfig.disabledTextColor,
                        inlineContentMap = inlineContentMap,
                        isSuccessOrErrorIcon = showSuccessIcon || showErrorIcon // Indicates if rightIcon is success/error icon (should show before text)
                    )
                }
            }
        }
    }
}

/**
 * Button content composable (shared between Button and TextButton)
 */
@Composable
private fun ButtonContent(
    text: CharSequence,
    leftIcon: VisualElement?,
    rightIcon: VisualElement?,
    isLoading: Boolean,
    size: ButtonSize,
    fontSize: TextUnit,
    fontWeight: FontWeight,
    lineHeight: TextUnit,
    textColor: Color,
    inlineContentMap: Map<String, InlineTextContent>,
    isSuccessOrErrorIcon: Boolean = false // If true, rightIcon is success/error icon and should be shown before text
) {
    val iconSize = when (size) {
        ButtonSize.XSmall -> ButtonSpecs.IconSize.XSmall
        ButtonSize.Small -> ButtonSpecs.IconSize.Small
        ButtonSize.Medium -> ButtonSpecs.IconSize.Medium
        ButtonSize.Large -> ButtonSpecs.IconSize.Large
    }

    Box(
        modifier = Modifier.wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Show spinner if loading (Default loading state), or show success/error icon for LoadingSuccess/LoadingDestructive
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(iconSize),
                        color = textColor,
                        strokeWidth = 2.dp
                    )
                }
                isSuccessOrErrorIcon && rightIcon != null -> {
                    // For LoadingSuccess/LoadingDestructive: show icon (check/cross) before text
                    PopVisualElement(
                        visualElement = rightIcon,
                        modifier = Modifier.size(iconSize),
                        contentDescription = "Icon"
                    )
                }
                else -> {
                    // Left icon - next to label with no gap (only when not loading and no rightIcon)
                    leftIcon?.let {
                        PopVisualElement(
                            visualElement = it,
                            modifier = Modifier.size(iconSize),
                            contentDescription = "Left Icon"
                        )
                    }
                }
            }
            // Text - always show
            when (text) {
                is AnnotatedString -> Text(
                    text = text,
                    inlineContent = inlineContentMap,
                    fontFamily = PopTypography.figtree,
                    fontWeight = fontWeight,
                    fontSize = fontSize,
                    lineHeight = lineHeight,
                    letterSpacing = 0.sp,
                    textAlign = TextAlign.Center,
                    color = textColor
                )
                else -> Text(
                    text = text.toString(),
                    fontFamily = PopTypography.figtree,
                    fontWeight = fontWeight,
                    fontSize = fontSize,
                    lineHeight = lineHeight,
                    letterSpacing = 0.sp,
                    textAlign = TextAlign.Center,
                    color = textColor
                )
            }
            // Right icon - next to label with no gap (only when not loading and not a success/error icon)
            // Note: For LoadingSuccess/LoadingDestructive, rightIcon is shown before text, so don't show it again here
            if (!isLoading && !isSuccessOrErrorIcon) {
                rightIcon?.let {
                    PopVisualElement(
                        visualElement = it,
                        modifier = Modifier.size(iconSize),
                        contentDescription = "Right Icon"
                    )
                }
            }
        }
    }
}

/**
 * Button configuration data class
 */
private data class ButtonConfig(
    val backgroundColor: Color,
    val disabledBackgroundColor: Color,
    val textColor: Color,
    val disabledTextColor: Color,
    val borderColor: Color? = null,
    val gradient: PopGradient? = null
)

/**
 * Get button configuration based on variant and state
 */
@Composable
private fun getButtonConfig(
    variant: ButtonVariant,
    state: ButtonState,
    buttonLoadingState: ButtonLoadingState,
    isOutlined: Boolean
): ButtonConfig {
    val isDisabled = state == ButtonState.Disabled
    val isLoading = state == ButtonState.Loading
    val isDestructive = state == ButtonState.Destructive

    // Handle loading states first - they override normal variant behavior
    if (isLoading && !isOutlined) {
        return when (buttonLoadingState) {
            ButtonLoadingState.Default -> {
                // Loading state: White gradient for BrandPrimary only, otherwise use variant's normal gradient
                when (variant) {
                    ButtonVariant.BrandPrimary -> ButtonConfig(
                        backgroundColor = Color.Transparent,
                        disabledBackgroundColor = Color.Transparent,
                        textColor = TextColor.Primary, // White text/icon (using Error token which is white)
                        disabledTextColor = TextColor.Disabled,
                        gradient = ButtonSpecs.Gradients.WhiteLarge
                    )
                    ButtonVariant.Secondary -> {
                        // For Secondary, use normal config which includes border
                        val baseConfig = getBaseButtonConfig(variant, isDisabled, isOutlined, false)
                        baseConfig // borderColor is already set in base config
                    }
                    else -> {
                        // For other variants (including Primary), use their normal config but keep loading behavior
                        getBaseButtonConfig(variant, isDisabled, isOutlined, false)
                    }
                }
            }
            ButtonLoadingState.Success -> ButtonConfig(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent,
                textColor = TextColor.Primary, // White checkmark (using Error token which is white)
                disabledTextColor = TextColor.Disabled,
                gradient = ButtonSpecs.Gradients.SuccessLarge
            )
            ButtonLoadingState.Destructive -> ButtonConfig(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent,
                textColor = TextColor.Primary, // White X icon (using Error token which is white)
                disabledTextColor = TextColor.Disabled,
                gradient = ButtonSpecs.Gradients.DestructiveLarge
            )
            ButtonLoadingState.Intermediate -> {
                // For future use - currently treated as Default
                getBaseButtonConfig(variant, isDisabled, isOutlined, false)
            }
        }
    }

    // Handle Primary Destructive state - should use red background with white text
    if (variant == ButtonVariant.Primary && isDestructive && !isOutlined && !isDisabled) {
        return ButtonConfig(
            backgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent,
            textColor = TextColor.Error, // White text/icon for destructive
            disabledTextColor = TextColor.Disabled,
            gradient = ButtonSpecs.Gradients.DestructiveLarge // Red gradient background
        )
    }

    return when {
        isOutlined -> {
            when (variant) {
                ButtonVariant.Primary -> ButtonConfig(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,
                    textColor = if (isDisabled) TextColor.Disabled else TextColor.PrimaryInvert,
                    disabledTextColor = TextColor.Disabled,
                    borderColor = if (isDisabled) BorderColor.PrimaryTransparent30 else BorderColor.PrimaryFromTokens
                )
                ButtonVariant.Secondary -> ButtonConfig(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,
                    textColor = if (isDisabled) TextColor.Disabled else TextColor.Primary,
                    disabledTextColor = TextColor.Disabled,
                    borderColor = if (isDisabled) BorderColor.PrimaryTransparent30 else BorderColor.SecondaryFromTokens
                )
                ButtonVariant.Tertiary -> ButtonConfig(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,
                    textColor = if (isDisabled) TextColor.Disabled else TextColor.PrimaryFromTokens,
                    disabledTextColor = TextColor.Disabled,
                    borderColor = if (isDisabled) BorderColor.PrimaryTransparent30 else BorderColor.TertiaryFromTokens
                )
                ButtonVariant.BrandPrimary -> ButtonConfig(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,
                    textColor = if (isDisabled) IconColor.BrandDisabledFromTokens else IconColor.BrandFromTokens,
                    disabledTextColor = IconColor.BrandDisabledFromTokens,
                    borderColor = if (isDisabled) BorderColor.BrandDisabled else BorderColor.BrandFromTokens
                )
                ButtonVariant.White -> ButtonConfig(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,
                    textColor = if (isDisabled) TextColor.Disabled else TextColor.PrimaryInvert,
                    disabledTextColor = TextColor.Disabled,
                    borderColor = if (isDisabled) BorderColor.PrimaryTransparent30 else BorderColor.PrimaryInvert
                )
                ButtonVariant.Ghost -> ButtonConfig(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,
                    textColor = if (isDisabled) TextColor.Disabled else TextColor.PrimaryFromTokens,
                    disabledTextColor = TextColor.Disabled,
                    borderColor = null // Ghost buttons have no border in Figma
                )
                ButtonVariant.BrandGhost -> ButtonConfig(
                    backgroundColor = Color.Transparent,
                    disabledBackgroundColor = Color.Transparent,
                    textColor = if (isDisabled) IconColor.BrandDisabledFromTokens else IconColor.BrandFromTokens,
                    disabledTextColor = IconColor.BrandDisabledFromTokens,
                    borderColor = if (isDisabled) BorderColor.BrandDisabled else BorderColor.BrandFromTokens
                )
            }
        }
        else -> {
            getBaseButtonConfig(variant, isDisabled, isOutlined, isDestructive)
        }
    }
}

/**
 * Get base button configuration (without loading state overrides)
 */
@Composable
private fun getBaseButtonConfig(
    variant: ButtonVariant,
    isDisabled: Boolean,
    isOutlined: Boolean,
    isDestructive: Boolean = false
): ButtonConfig {
    return when (variant) {
        ButtonVariant.Primary -> ButtonConfig(
            backgroundColor = SurfaceColor.PrimaryInvert, // Light grey/white for Primary
            disabledBackgroundColor = Color.Transparent, // Transparent when using gradient
            textColor = TextColor.PrimaryInvert, // Black text for Primary
            disabledTextColor = TextColor.Disabled, // #4D4D4D as per Figma
            borderColor = null, // No border for Primary filled buttons
            gradient = if (isDisabled) {
                // Pre-blended disabled gradient (white->grey with 90% dark overlay baked in)
                ButtonSpecs.Gradients.PrimaryDisabled
            } else {
                ButtonSpecs.Gradients.PrimaryInvertLarge
            }
        )
        ButtonVariant.Secondary -> ButtonConfig(
            backgroundColor = SurfaceColor.SecondaryFromTokens, // Dark gray for Secondary (from tokens)
            disabledBackgroundColor = SurfaceColor.PrimaryDisabled70,
            // For destructive state, use red text; otherwise use white text
            textColor = if (isDestructive && !isDisabled) TextColor.DestructiveFromTokens else TextColor.PrimaryFromTokens,
            disabledTextColor = TextColor.Disabled,
            borderColor = Color(0xFF333333), // #333333 border for Secondary buttons
            gradient = if (!isDisabled) ButtonSpecs.Gradients.SecondaryLarge else null
        )
        ButtonVariant.Tertiary -> ButtonConfig(
            backgroundColor = PrimaryColor.Primary00, // Pure black for Tertiary (from tokens)
            disabledBackgroundColor = SurfaceColor.PrimaryDisabled70,
            // For destructive state, use red text; otherwise use primary text
            textColor = if (isDestructive && !isDisabled) TextColor.DestructiveFromTokens else TextColor.PrimaryFromTokens,
            disabledTextColor = TextColor.Disabled,
            gradient = null // Tertiary uses solid black, no gradient
        )
        ButtonVariant.BrandPrimary -> ButtonConfig(
            backgroundColor = PopColor.Brand.Brand600,
            disabledBackgroundColor = Color.Transparent,
            textColor = TextColor.PrimaryFromTokens,
            disabledTextColor = TextColor.PrimaryTransparent40, // Text/Primary-40% per Figma
            // Add border for Active and Loading states only (no border when disabled)
            borderColor = if (!isDisabled) {
                BorderColor.PrimaryInvert.copy(alpha = 0.1f) // Brand border for BrandPrimary buttons
            } else null,
            gradient = if (isDisabled) ButtonSpecs.Gradients.BrandPrimaryDisabled else ButtonSpecs.Gradients.BrandPrimaryLarge
        )
        ButtonVariant.White -> ButtonConfig(
            backgroundColor = SurfaceColor.PrimaryInvert,
            disabledBackgroundColor = SurfaceColor.PrimaryDisabled70,
            textColor = TextColor.PrimaryInvert,
            disabledTextColor = TextColor.Disabled,
            gradient = if (!isDisabled) ButtonSpecs.Gradients.PrimaryInvertLarge else null
        )
        ButtonVariant.Ghost -> ButtonConfig(
            backgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent,
            textColor = if (isDisabled) TextColor.Disabled else TextColor.PrimaryFromTokens,
            disabledTextColor = TextColor.Disabled,
            borderColor = null // Ghost buttons have no border in Figma
        )
        ButtonVariant.BrandGhost -> ButtonConfig(
            backgroundColor = Color.Transparent,
            disabledBackgroundColor = Color.Transparent,
            textColor = if (isDisabled) IconColor.BrandDisabledFromTokens else IconColor.BrandFromTokens,
            disabledTextColor = IconColor.BrandDisabledFromTokens,
            borderColor = if (isDisabled) BorderColor.BrandDisabled else BorderColor.BrandFromTokens
        )
    }
}

/**
 * PopIconButton V2 - Circular icon-only button
 *
 * Always circular shape. Supports same variants and states as PopButtonV2.
 * Width/size is controlled via modifier.
 */
@Composable
fun PopIconButtonV2(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    iconRes: VisualElement? = null,
    variant: ButtonVariant = ButtonVariant.Primary,
    state: ButtonState = ButtonState.Active,
    buttonLoadingState: ButtonLoadingState? = null, // Only used when state == Loading
    size: ButtonSize = ButtonSize.Large,
    onDisabledClick: (() -> Unit)? = null // Optional callback for disabled button clicks
) {
    val safeOnClick = rememberDebouncedClick(onClick = onClick)
    val safeOnDisabledClick = onDisabledClick?.let {
        rememberDebouncedClick(onClick = it)
    }

    val enabled = state != ButtonState.Disabled
    val isDisabled = state == ButtonState.Disabled
    val isLoading = state == ButtonState.Loading
    val effectiveButtonLoadingState = buttonLoadingState ?: ButtonLoadingState.Default // Default to Default if not specified
    val isSpinnerLoading = isLoading && effectiveButtonLoadingState == ButtonLoadingState.Default // Only show spinner for Default loading state

    val showSuccessIcon = isLoading && effectiveButtonLoadingState == ButtonLoadingState.Success
    val showErrorIcon = isLoading && effectiveButtonLoadingState == ButtonLoadingState.Destructive

    val actualIconRes = when {
        showSuccessIcon -> {
            VisualElement.buildFrom(Icons.Check, IconStyle.Outline, fallbackResId = R.drawable.ic_check)
        }
        showErrorIcon -> {
            VisualElement.buildFrom(Icons.Cross, IconStyle.Outline, fallbackResId = R.drawable.ic_close)
        }
        else -> iconRes ?: VisualElement.buildFrom(R.drawable.ic_button_arrow)
    }

    // Exact dimensions from Figma
    val buttonSize = when (size) {
        ButtonSize.XSmall -> ButtonSpecs.Height.XSmall
        ButtonSize.Small -> ButtonSpecs.Height.Small
        ButtonSize.Medium -> ButtonSpecs.Height.Medium
        ButtonSize.Large -> ButtonSpecs.Height.Large
    }

    val iconSize = when (size) {
        ButtonSize.XSmall -> ButtonSpecs.IconSize.XSmall
        ButtonSize.Small -> ButtonSpecs.IconSize.Small
        ButtonSize.Medium -> ButtonSpecs.IconSize.Medium
        ButtonSize.Large -> ButtonSpecs.IconSize.Large
    }

    val buttonConfig = getButtonConfig(variant, state, effectiveButtonLoadingState, isOutlined = false)

    // Determine if we need to handle disabled clicks
    val shouldHandleDisabledClick = isDisabled && safeOnDisabledClick != null

    val baseModifier = modifier
        .size(buttonSize)
        .aspectRatio(1f)
        .then(
            if (shouldHandleDisabledClick) {
                Modifier.clickable(enabled = true, onClick = { safeOnDisabledClick?.invoke() })
            } else Modifier
        )

    // Determine if noise effect should be applied
    // ONLY apply to BrandPrimary buttons in Active or Loading states
    val shouldApplyNoise = buttonConfig.gradient != null &&
            variant == ButtonVariant.BrandPrimary &&
            (state == ButtonState.Active || (state == ButtonState.Loading && effectiveButtonLoadingState == ButtonLoadingState.Default))

    // Determine if glow effect should be applied
    // Apply to Primary buttons (white glow) and BrandPrimary (brand glow) in Active/Loading states
    val shouldApplyPrimaryGlow = variant == ButtonVariant.Primary &&
            (state == ButtonState.Active || (state == ButtonState.Loading && effectiveButtonLoadingState == ButtonLoadingState.Default))
    val shouldApplyBrandGlow = variant == ButtonVariant.BrandPrimary &&
            (state == ButtonState.Active || (state == ButtonState.Loading && effectiveButtonLoadingState == ButtonLoadingState.Default))

    // Wrap button in Box for gradient background support
    Box(
        modifier = baseModifier
            .then(
                when {
                    shouldApplyBrandGlow -> Modifier
                        .graphicsLayer { clip = false }
                        .intenseGlowEffect(
                            glowRadius = 10.dp,
                            spread = 2.dp,
                            color = PopColor.Brand.Brand500,
                            layers = 2,
                            shape = CircleShape
                        )
                    shouldApplyPrimaryGlow -> Modifier
                        .graphicsLayer { clip = false }
                        .intenseGlowEffect(
                            glowRadius = 8.dp,
                            spread = 1.dp,
                            color = Color.White,
                            layers = 1,
                            shape = CircleShape
                        )
                    else -> Modifier
                }
            )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .then(
                    if (buttonConfig.gradient != null) {
                        Modifier.popBackground(
                            gradient = buttonConfig.gradient,
                            shape = CircleShape
                        )
                    } else Modifier
                )
                .then(
                    if (shouldApplyNoise) {
                        Modifier.noiseOverlay(NoisePresets.BrandButton)
                    } else Modifier
                )
        ) {
        Button(
            onClick = safeOnClick,
            modifier = Modifier.fillMaxSize().align(Alignment.Center),
            enabled = enabled && !isLoading,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (buttonConfig.gradient == null) buttonConfig.backgroundColor else Color.Transparent,
                contentColor = buttonConfig.textColor,
                disabledContainerColor = if (buttonConfig.gradient == null) buttonConfig.disabledBackgroundColor else Color.Transparent,
                disabledContentColor = buttonConfig.disabledTextColor
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                if (isSpinnerLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(iconSize),
                        color = buttonConfig.textColor,
                        strokeWidth = 2.dp
                    )
                } else {
                    PopVisualElement(
                        visualElement = actualIconRes,
                        modifier = Modifier.size(iconSize),
                        contentDescription = contentDescription
                    )
                }
            }
        }
        }
    }
}


/**
 * Button size enum matching Figma design system
 */
enum class ButtonSize {
    XSmall,
    Small,
    Medium,
    Large
}

/**
 * Button width behavior
 */
enum class ButtonWidthType {
    Wrap,  // Default - button wraps content width
    Fill   // Button fills available width
}

@Composable
private fun rememberDebouncedClick(
    debounceTimeMs: Long = 600L,
    onClick: () -> Unit
): () -> Unit {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    return remember(onClick, debounceTimeMs) {
        {
            val now = SystemClock.uptimeMillis()
            if (now - lastClickTime >= debounceTimeMs) {
                lastClickTime = now
                onClick()
            }
        }
    }
}
