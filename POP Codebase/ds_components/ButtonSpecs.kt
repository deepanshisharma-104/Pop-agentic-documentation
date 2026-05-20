package com.pop.components.ds_components

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.theme.BorderColor
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.IconColor
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopStroke
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor

/**
 * Exact button specifications from Figma design system
 * Figma link: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=1248-823&m=dev
 */
object ButtonSpecs {
    // Exact spacing values from Figma
    object Spacing {
        val S0: Dp = 0.dp
        val S4: Dp = 4.dp
        val S6: Dp = 6.dp
        val S8: Dp = 8.dp
        val S10: Dp = 10.dp
        val S12: Dp = 12.dp
        val S16: Dp = 16.dp
        val S28: Dp = 28.dp
        val S36: Dp = 36.dp
        val S44: Dp = 44.dp
        val S56: Dp = 56.dp
    }

    // Exact button heights from Figma
    object Height {
        val XSmall: Dp = 32.dp  // Based on Spacing/36 but adjusted for XSmall
        val Small: Dp = 36.dp   // Spacing/36
        val Medium: Dp = 44.dp  // Spacing/44
        val Large: Dp = 56.dp   // Spacing/56
    }

    // Exact horizontal padding from Figma
    object Padding {
        val XSmall: Dp = 12.dp  // Spacing/12
        val Small: Dp = 16.dp   // Spacing/16
        val Medium: Dp = 20.dp  // Spacing/20 (or equivalent)
        val Large: Dp = 28.dp   // Spacing/28
    }

    // Exact font sizes from Figma (Label styles)
    object FontSize {
        val XSmall: TextUnit = 12.sp  // Label/XX Small-500
        val Small: TextUnit = 14.sp   // Label/X Small-500
        val Medium: TextUnit = 16.sp  // Label/Medium-500
        val Large: TextUnit = 18.sp   // Label/Large-600 (18px for disabled text)
    }

    // Exact font weights from Figma
    object FontWeight {
        val Medium = androidx.compose.ui.text.font.FontWeight(500)  // 500
        val SemiBold = androidx.compose.ui.text.font.FontWeight(600) // 600
    }

    // Exact line heights from Figma
    object LineHeight {
        val XSmall: TextUnit = 16.sp  // Label/XX Small-500
        val Small: TextUnit = 20.sp   // Label/X Small-500
        val Medium: TextUnit = 24.sp  // Label/Medium-500
        val Large: TextUnit = 24.sp   // Label/Large-600 (24px, 133.333% of 18px)
    }

    // Exact icon sizes from Figma
    object IconSize {
        val XSmall: Dp = 16.dp
        val Small: Dp = 18.dp
        val Medium: Dp = 20.dp
        val Large: Dp = 24.dp
    }

    // Exact gap between icon and text
    val IconTextGap: Dp = 6.dp  // Spacing/6

    // Border width - using PopStroke from PopSpacing.kt
    val BorderWidth: Dp = PopStroke.Default  // 0.5.dp from PopStroke

    // Corner radius values - using PopRadius from PopSpacing.kt where applicable
    // Note: Button corner radii may differ from standard PopRadius tokens
    // Using closest matching values or custom values if needed
    object CornerRadius {
        val XSmall: Dp = PopRadius.Large  // 16.dp - matches PopRadius.Large
        val Small: Dp = 18.dp   // Custom value (between PopRadius.Large 16dp and XLarge 20dp)
        val Medium: Dp = 22.dp  // Custom value (between PopRadius.XLarge 20dp and next)
        val Large: Dp = 28.dp   // Custom value (larger than PopRadius.XLarge 20dp)
    }

    // Colors from Figma variables
    // Note: Most colors now reference PopColor.kt for consistency
    // Keeping some legacy references for backward compatibility
    object Colors {
        // Text colors - using PopColor.kt definitions
        val TextPrimary = TextColor.PrimaryFromTokens  // Text/Primary (from tokens)
        val TextPrimaryInvert = TextColor.PrimaryInvert  // Text/Primary-Invert
        val TextPrimaryDisabled = TextColor.Disabled  // Text/Primary-Disabled
        val TextPrimary40Percent = TextColor.PrimaryTransparent40  // Text/Primary-40%
        val TextBrand = TextColor.BrandFromTokens  // Text/Brand (from tokens)
        val TextBrandDisabled = TextColor.BrandDisabledFromTokens  // Text/Brand-Disabled (from tokens)
        val TextDestructive = TextColor.DestructiveFromTokens  // Text/Destructive (from tokens)

        // Surface colors - using PopColor.kt definitions
        val SurfacePrimary = SurfaceColor.Primary  // Surface/Primary
        val SurfacePrimaryInvert = SurfaceColor.PrimaryInvert  // Surface/Primary-Invert
        val SurfacePrimaryDisabled = SurfaceColor.PrimaryDisabled70  // Surface/Primary-Disabled-70%
        val SurfacePrimary50Percent = SurfaceColor.PrimaryTransparent50  // Surface/Primary-50%
        val SurfaceSecondary = SurfaceColor.SecondaryFromTokens  // Surface/Secondary (from tokens)

        // Icon colors - using PopColor.kt definitions
        val IconPrimary = IconColor.PrimaryFromTokens  // Icons/Primary (from tokens)
        val IconPrimaryInvert = IconColor.PrimaryInvert  // Icons/Primary-Invert
        val IconPrimaryDisabled = IconColor.Disabled  // Icons/Primary-Disabled
        val IconPrimary40Percent = IconColor.PrimaryTransparent40  // Icons/Primary-40%
        val IconBrand = IconColor.BrandFromTokens  // Icons/Brand (from tokens)
        val IconBrandDisabled = IconColor.BrandDisabledFromTokens  // Icons/Brand-Disabled (from tokens)
        val IconDestructive = IconColor.DestructiveFromTokens  // Icons/Destructive (from tokens)
        val IconSuccess = IconColor.SuccessFromTokens  // Icons/Success (from tokens)

        // Border colors - using PopColor.kt definitions
        val BorderSecondary = BorderColor.SecondaryFromTokens  // Border/Secondary (from tokens)
        val BorderPrimaryInvert10Percent = BorderColor.PrimaryInvertTransparent10  // Border/Primary-Invert-10%
        val BorderPrimary30Percent = BorderColor.PrimaryTransparent30  // Border/Primary-30%
    }

    // Gradients from PopGradient.kt GradientPreset enum
    object Gradients {
        // Brand Primary Gradient - Large
        val BrandPrimaryLarge: PopGradient = GradientPreset.ButtonBrandPrimaryLargeHorizontal.gradient
        // Primary Invert Gradient - Large
        val PrimaryInvertLarge: PopGradient = GradientPreset.ButtonPrimaryInvertLarge.gradient

        // Secondary Gradient - Large
        val SecondaryLarge: PopGradient = GradientPreset.ButtonSecondaryLargeHorizontal.gradient

        // Success Gradient - Large
        val SuccessLarge: PopGradient = GradientPreset.ButtonSuccessLarge.gradient

        // Destructive Gradient - Large
        val DestructiveLarge: PopGradient = GradientPreset.ButtonDestructiveLarge.gradient

        // White Gradient - Large (for BrandPrimary Loading state)
        val WhiteLarge: PopGradient = GradientPreset.ButtonWhiteLarge.gradient

        // Primary Disabled Gradient (white-to-grey with dark overlay)
        val PrimaryDisabled: PopGradient = GradientPreset.ButtonPrimaryDisabled.gradient

        // Brand Primary Disabled Gradient (dark desaturated reddish-brown, Figma node 1698-18426)
        val BrandPrimaryDisabled: PopGradient = GradientPreset.ButtonBrandPrimaryDisabled.gradient
    }
}

