package com.pop.components.theme

import androidx.compose.ui.graphics.Color
import com.pop.components.theme.BorderColor.TertiaryTransparent40

/**
 * PopColor - Design System Colors from Figma
 * Source: POP-DS Design System
 * https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS
 * 
 * NOTE: These colors are defined in colors.xml as the single source of truth.
 * The hex values here are kept in sync with colors.xml for use in Compose.
 * For XML views, reference colors directly from colors.xml (e.g., @color/brand_600)
 * 
 * Usage:
 * - In Compose: Use these static color objects (e.g., PopColor.Brand.Brand600, TextColor.Primary)
 * - In XML: Reference @color/brand_600, @color/text_primary directly from colors.xml
 */
object PopColor {
    
    // White & Black
    object WhiteBlack {
        val White = Color(0xFFFFFFFF) // XML: @color/ds_white
        val Black = Color(0xFF000000) // XML: @color/ds_black
    }
    
    // Neutral (Grey) colors - 100 to 1000
    object Grey {
        val Grey100 = Color(0xFFFFFFFF) // XML: @color/grey_100
        val Grey200 = Color(0xFFE6E6E6) // XML: @color/grey_200
        val Grey300 = Color(0xFFB3B3B3) // XML: @color/grey_300
        val Grey400 = Color(0xFF808080) // XML: @color/grey_400
        val Grey500 = Color(0xFF666666) // XML: @color/grey_500
        val Grey600 = Color(0xFF4D4D4D) // XML: @color/grey_600
        val Grey700 = Color(0xFF333333) // XML: @color/grey_700
        val Grey800 = Color(0xFF262626) // XML: @color/grey_800
        val Grey900 = Color(0xFF1F1F1F) // XML: @color/grey_900
        val Grey1000 = Color(0xFF0D0D0D) // XML: @color/grey_1000
    }
    
    // Simple-Gray colors - 100 to 1000 (from Figma)
    object SimpleGray {
        val SimpleGray100 = Color(0xFFF4F4F4) // XML: @color/simple_gray_100
        val SimpleGray200 = Color(0xFFD8D8D8) // XML: @color/simple_gray_200
        val SimpleGray300 = Color(0xFF9E9E9E) // XML: @color/simple_gray_300
        val SimpleGray400 = Color(0xFF7B7B7B) // XML: @color/simple_gray_400
        val SimpleGray500 = Color(0xFF616161) // XML: @color/simple_gray_500
        val SimpleGray600 = Color(0xFF4C4C4C) // XML: @color/simple_gray_600
        val SimpleGray700 = Color(0xFF3A3A3A) // XML: @color/simple_gray_700
        val SimpleGray800 = Color(0xFF2A2A2A) // XML: @color/simple_gray_800
        val SimpleGray900 = Color(0xFF1F1F1F) // XML: @color/simple_gray_900
        val SimpleGray1000 = Color(0xFF0D0D0D) // XML: @color/simple_gray_1000
    }
    
    // Destructive (Red) colors - 100 to 1000
    object Destructive {
        val Destructive100 = Color(0xFFFDEDEB) // XML: @color/destructive_100
        val Destructive200 = Color(0xFFFAC8C1) // XML: @color/destructive_200
        val Destructive300 = Color(0xFFF7ADA3) // XML: @color/destructive_300
        val Destructive400 = Color(0xFFF48879) // XML: @color/destructive_400
        val Destructive500 = Color(0xFFF1715F) // XML: @color/destructive_500 (Main)
        val Destructive600 = Color(0xFFEE4D37) // XML: @color/destructive_600
        val Destructive700 = Color(0xFFC33A28) // XML: @color/destructive_700
        val Destructive800 = Color(0xFFA93727) // XML: @color/destructive_800
        val Destructive900 = Color(0xFF832A1E) // XML: @color/destructive_900
        val Destructive1000 = Color(0xFF642017) // XML: @color/destructive_1000
    }
    
    // Success (Green) colors - 100 to 1000
    object Success {
        val Success100 = Color(0xFFE7F8EE) // XML: @color/success_100
        val Success200 = Color(0xFFB4E8C9) // XML: @color/success_200
        val Success300 = Color(0xFF90DDAF) // XML: @color/success_300
        val Success400 = Color(0xFF5DCE8A) // XML: @color/success_400
        val Success500 = Color(0xFF3DC574) // XML: @color/success_500
        val Success600 = Color(0xFF0DB651) // XML: @color/success_600
        val Success700 = Color(0xFF0CA64A) // XML: @color/success_700
        val Success800 = Color(0xFF09813A) // XML: @color/success_800
        val Success900 = Color(0xFF07642D) // XML: @color/success_900
        val Success1000 = Color(0xFF054C22) // XML: @color/success_1000
    }
    
    // Warning (Yellow/Orange) colors - 100 to 1000
    object Warning {
        val Warning100 = Color(0xFFFFF8E6) // XML: @color/warning_100
        val Warning200 = Color(0xFFFEEAB3) // XML: @color/warning_200
        val Warning300 = Color(0xFFFDE08E) // XML: @color/warning_300
        val Warning400 = Color(0xFFFCD25A) // XML: @color/warning_400
        val Warning500 = Color(0xFFFCC93A) // XML: @color/warning_500
        val Warning600 = Color(0xFFFBBC09) // XML: @color/warning_600
        val Warning700 = Color(0xFFE4AB08) // XML: @color/warning_700
        val Warning800 = Color(0xFFB28506) // XML: @color/warning_800
        val Warning900 = Color(0xFF8A6705) // XML: @color/warning_900
        val Warning1000 = Color(0xFF694F04) // XML: @color/warning_1000
    }
    
    // Brand (Orange) colors - 100 to 1000
    object Brand {
        val Brand100 = Color(0xFFFFEEE6) // XML: @color/brand_100
        val Brand200 = Color(0xFFFFC9B0) // XML: @color/brand_200
        val Brand300 = Color(0xFFFFAF8A) // XML: @color/brand_300
        val Brand400 = Color(0xFFFF8B54) // XML: @color/brand_400
        val Brand500 = Color(0xFFFF7533) // XML: @color/brand_500
        val Brand600 = Color(0xFFFF5200) // XML: @color/brand_600 (Main)
        val Brand700 = Color(0xFFE84B00) // XML: @color/brand_700
        val Brand800 = Color(0xFFB53A00) // XML: @color/brand_800
        val Brand900 = Color(0xFF8C2D00) // XML: @color/brand_900
        val Brand1000 = Color(0xFF6B2200) // XML: @color/brand_1000
    }

    object BrandPrimaryButton {
        val Color1 = Color(0XFFFF813B)
        val Color2 = Color(0XFFFF5200)
        val Color3 = Color(0XFFCD3401)
        val Color4 = Color(0XFFB32A01)
    }

    object AvatarPerson {
        val PinkStart = Color(0xFFBC1AA1)
        val PinkEnd = Color(0xFF45163F)

        val SeaGreenStart = Color(0xFF007171)
        val SeaGreenEnd = Color(0xFF093838)

        val PurpleStart = Color(0xFF6B2DBB)
        val PurpleEnd = Color(0xFF331F4D)

        val MaroonStart = Color(0xFFAB1D62)
        val MaroonEnd = Color(0xFF48192F)

        val VioletStart = Color(0XFF4040CE)
        val VioletEnd = Color(0xFF242452)

        val OrangeStart = Color(0XFFA63C19)
        val OrangeEnd = Color(0xFF462417)

        val GreenStart = Color(0XFF007C3B)
        val GreenEnd = Color(0xFF0B3A22)

        val RedStart = Color(0XFFAE202E)
        val RedEnd = Color(0xFF491A1B)
    }
    
    // Brand-Brown colors - 50, 100 to 900 (from Figma)
    object BrandBrown {
        val BrandBrown50 = Color(0xFFFFEBCE) // XML: @color/brand_brown_50
        val BrandBrown100 = Color(0xFFFFD3AF) // XML: @color/brand_brown_100
        val BrandBrown200 = Color(0xFFFFBD8E) // XML: @color/brand_brown_200
        val BrandBrown300 = Color(0xFFFFA86E) // XML: @color/brand_brown_300
        val BrandBrown400 = Color(0xFFFF954E) // XML: @color/brand_brown_400
        val BrandBrown500 = Color(0xFFD77B3C) // XML: @color/brand_brown_500
        val BrandBrown600 = Color(0xFFAE642B) // XML: @color/brand_brown_600
        val BrandBrown700 = Color(0xFF8A4F1D) // XML: @color/brand_brown_700
        val BrandBrown800 = Color(0xFF683E12) // XML: @color/brand_brown_800
        val BrandBrown900 = Color(0xFF4D2D09) // XML: @color/brand_brown_900
    }
    
    // General-Green colors - 100 to 900 (from Figma)
    object GeneralGreen {
        val GeneralGreen100 = Color(0xFFF2FFF2) // XML: @color/general_green_100
        val GeneralGreen200 = Color(0xFFCFFECF) // XML: @color/general_green_200
        val GeneralGreen300 = Color(0xFF9DFE9D) // XML: @color/general_green_300
        val GeneralGreen400 = Color(0xFF6BFD6B) // XML: @color/general_green_400
        val GeneralGreen500 = Color(0xFF3FEF3F) // XML: @color/general_green_500
        val GeneralGreen600 = Color(0xFF2CBE2C) // XML: @color/general_green_600
        val GeneralGreen700 = Color(0xFF1B8A1B) // XML: @color/general_green_700
        val GeneralGreen800 = Color(0xFF106610) // XML: @color/general_green_800
        val GeneralGreen900 = Color(0xFF0A4D0A) // XML: @color/general_green_900
    }
    
    // Destructive-Red colors - 50, 100 to 900 (from Figma)
    object DestructiveRed {
        val DestructiveRed50 = Color(0xFFFFF2F2) // XML: @color/destructive_red_50
        val DestructiveRed100 = Color(0xFFFFE7E7) // XML: @color/destructive_red_100
        val DestructiveRed200 = Color(0xFFFFC2C2) // XML: @color/destructive_red_200
        val DestructiveRed300 = Color(0xFFFF9C9C) // XML: @color/destructive_red_300
        val DestructiveRed400 = Color(0xFFFF7B7B) // XML: @color/destructive_red_400
        val DestructiveRed500 = Color(0xFFF85D5D) // XML: @color/destructive_red_500
        val DestructiveRed600 = Color(0xFFD44747) // XML: @color/destructive_red_600
        val DestructiveRed700 = Color(0xFFAE3232) // XML: @color/destructive_red_700
        val DestructiveRed800 = Color(0xFF8A1F1F) // XML: @color/destructive_red_800
        val DestructiveRed900 = Color(0xFF661010) // XML: @color/destructive_red_900
    }
    
    // Warning-Yellow colors - 50, 100 to 900 (from Figma)
    object WarningYellow {
        val WarningYellow50 = Color(0xFFFFFBF2) // XML: @color/warning_yellow_50
        val WarningYellow100 = Color(0xFFFFF7E7) // XML: @color/warning_yellow_100
        val WarningYellow200 = Color(0xFFFFEAC2) // XML: @color/warning_yellow_200
        val WarningYellow300 = Color(0xFFFFDC9C) // XML: @color/warning_yellow_300
        val WarningYellow400 = Color(0xFFFFCE7B) // XML: @color/warning_yellow_400
        val WarningYellow500 = Color(0xFFF8B45D) // XML: @color/warning_yellow_500
        val WarningYellow600 = Color(0xFFD49847) // XML: @color/warning_yellow_600
        val WarningYellow700 = Color(0xFFAE7D32) // XML: @color/warning_yellow_700
        val WarningYellow800 = Color(0xFF8A631F) // XML: @color/warning_yellow_800
        val WarningYellow900 = Color(0xFF664B10) // XML: @color/warning_yellow_900
    }
    
    // Accent colors - 10 to 100 (from Figma Color Tokens)
    object Accent {
        val Accent10 = Color(0xFF1E8626) // XML: @color/accent_10
        val Accent20 = Color(0xFF26D526) // XML: @color/accent_20
        val Accent30 = Color(0xFF2ED23A) // XML: @color/accent_30
        val Accent40 = Color(0xFF015001) // XML: @color/accent_40
        val Accent50 = Color(0xFF059505) // XML: @color/accent_50
        val Accent60 = Color(0xFF00CD00) // XML: @color/accent_60
        val Accent70 = Color(0xFF1E8626) // XML: @color/accent_70
        val Accent80 = Color(0xFF015001) // XML: @color/accent_80
        val Accent90 = Color(0xFF059505) // XML: @color/accent_90
        val Accent100 = Color(0xFF26D526) // XML: @color/accent_100
    }
}

// Primary/Secondary/Tertiary color tokens (from Figma Color Tokens)
object PrimaryColor {
    val Primary00 = Color(0xFF0D0D0D) // XML: @color/primary_00
    val Primary100 = Color(0xFFFFFFFF) // XML: @color/primary_100
}

object SecondaryColor {
    val Secondary00 = Color(0xFF1E1E1E) // XML: @color/secondary_00
    val Secondary100 = Color(0xFFA0A0A0) // XML: @color/secondary_100
}

object TertiaryColor {
    val Tertiary00 = Color(0xFF2A2A2A) // XML: @color/tertiary_00
    val Tertiary100 = Color(0xFF909090) // XML: @color/tertiary_100
}

// Brand Primary color tokens (from Figma Color Tokens)
object BrandPrimaryColor {
    val BrandPrimary05Percent = Color(0xFF141414) // XML: @color/brand_primary_05_percent
    val BrandPrimaryDisabled10Percent = Color(0xFF0D0D0D) // XML: @color/brand_primary_disabled_10_percent
    val BrandPrimaryDisabled20Percent = Color(0xFF1A1A1A) // XML: @color/brand_primary_disabled_20_percent
    val BrandPrimary80Percent = Color(0xFF7D7D7D) // XML: @color/brand_primary_80_percent
    val BrandPrimary90Percent = Color(0xFF7F7F7F) // XML: @color/brand_primary_90_percent
    val BrandPrimaryLight = Color(0xFFE0E0E0) // XML: @color/brand_primary_light
}

// Brand/Success/Warning/Destructive/Error color tokens (from Figma Color Tokens)
object BrandColorToken {
    val Brand100 = Color(0xFFFC7833) // XML: @color/brand_token_100
    val BrandDisabled = Color(0xFF994A20) // XML: @color/brand_token_disabled
}

object SuccessColorToken {
    val Success100 = Color(0xFF2ED23A) // XML: @color/success_token_100
    val SuccessDisabled = Color(0xFF1E8626) // XML: @color/success_token_disabled
    val SuccessHover = Color(0xFF00CD00) // XML: @color/success_token_hover
}

object WarningColorToken {
    val Warning100 = Color(0xFFFFD43B) // XML: @color/warning_token_100
    val WarningHover = Color(0xFFA08C20) // XML: @color/warning_token_hover
}

object DestructiveColorToken {
    val Destructive100 = Color(0xFFE63D3D) // XML: @color/destructive_token_100
    val DestructiveHover = Color(0xFF902020) // XML: @color/destructive_token_hover
}

object ErrorColorToken {
    val Error100 = Color(0xFFFFFFFF) // XML: @color/error_token_100
}

// Semantic Token Colors - Surface
object SurfaceColor {
    val Transparent = Color(0x00000000)
    val Primary = Color(0xFF0D0D0D) // XML: @color/surface_primary
    val PrimaryTransparent50 = Color(0x800D0D0D) // XML: @color/surface_primary_transparent_50
    val PrimaryTransparent10 = Color(0x1A0D0D0D) // XML: @color/surface_primary_transparent_10 (from Figma)
    val PrimaryDisabled70 = Color(0xB30D0D0D) // XML: @color/surface_primary_disabled_70 (70% opacity)
    val Secondary = Color(0xFF1F1F1F) // XML: @color/surface_secondary
    val SecondaryFromTokens = Color(0xFF1E1E1E) // XML: @color/surface_secondary_tokens (from Figma tokens)
    val SecondaryTransparent60 = Color(0x991F1F1F) // XML: @color/surface_secondary_transparent_60
    val SecondaryTransparent10 = Color(0x1A1E1E1E) // XML: @color/surface_secondary_transparent_10 (from Figma)
    val SecondaryDisabled = Color(0xFF262626) // XML: @color/surface_secondary_disabled
    val Tertiary = Color(0xFF4D4D4D) // XML: @color/surface_tertiary
    val TertiaryFromTokens = Color(0xFF2A2A2A) // XML: @color/surface_tertiary_tokens (from Figma tokens)
    val PrimaryInvert = Color(0xFFFFFFFF) // XML: @color/surface_primary_invert
    
    // Brand Primary variants (from Figma tokens)
    val BrandPrimary05Percent = Color(0xFF141414) // XML: @color/surface_brand_primary_05_percent
    val BrandPrimaryDisabled10Percent = Color(0xFF0D0D0D) // XML: @color/surface_brand_primary_disabled_10_percent
    val BrandPrimaryDisabled20Percent = Color(0xFF1A1A1A) // XML: @color/surface_brand_primary_disabled_20_percent
    val BrandPrimaryDisabled21Percent = Color(0xFF212121) // XML: @color/surface_brand_primary_disabled_20_percent
    val BrandPrimaryLight = Color(0xFFE0E0E0) // XML: @color/surface_brand_primary_light
    
    // Gradients (define as color stops)
    object Gradient {
        object Primary {
            val Start = Color(0xFFFFFFFF) // XML: @color/surface_gradient_primary_start
            val End = Color(0xFF999999) // XML: @color/surface_gradient_primary_end
        }
        object Secondary {
            val Start = Color(0xFF1F1F1F) // XML: @color/surface_gradient_secondary_start
            val End = Color(0xFF262626) // XML: @color/surface_gradient_secondary_end
        }
        object SecondaryHighlighted {
            val Start = Color(0xFF323232)
            val Mid = Color(0xFF1C1C1C)
            val End = Color(0xFF0D0D0D)
        }
        object SecondaryButton {
            val Start = Color(0xFF464646) // XML: @color/surface_gradient_secondary_start
            val End = Color(0xFF262525) // XML: @color/surface_gradient_secondary_end
        }
        object Brand {
            val Start = Color(0xFFFF7533) // XML: @color/surface_gradient_brand_start (orange)
            val End = Color(0xFFE84B00) // XML: @color/surface_gradient_brand_end (deep red)
        }
        object Destructive {
            val Start = Color(0xFFEE4D37) // XML: @color/surface_gradient_destructive_start (bright red)
            val End = Color(0xFFC33A28) // XML: @color/surface_gradient_destructive_end (darker red)
        }
        object Success {
            val Start = Color(0xFF26D526) // XML: @color/surface_gradient_success_start (bright green)
            val End = Color(0xFF015001) // XML: @color/surface_gradient_success_end (dark green)
        }
    }
}

// Semantic Token Colors - Text
object TextColor {
    val Primary = Color(0xFFE6E6E6) // XML: @color/text_primary
    val PrimaryFromTokens = Color(0xFFFFFFFF) // XML: @color/text_primary_tokens (from Figma tokens)
    val PrimaryTransparent40 = Color(0x66E6E6E6) // XML: @color/text_primary_transparent_40 (40% opacity)
    val PrimaryTransparent60 = Color(0x99E6E6E6) // XML: @color/text_primary_transparent_60 (60% opacity)
    val PrimaryTransparent10 = Color(0x1AFFFFFF) // XML: @color/text_primary_transparent_10 (from Figma)
    val PrimaryInactive = Color(0xFF7F7F7F) // XML: @color/text_primary_inactive (from Figma tokens)
    val Secondary = Color(0xFFB3B3B3) // XML: @color/text_secondary
    val SecondaryFromTokens = Color(0xFFA0A0A0) // XML: @color/text_secondary_tokens (from Figma tokens)
    val SecondaryDisabled = Color(0xFF333333) // XML: @color/text_secondary_disabled
    val Tertiary = Color(0xFF808080) // XML: @color/text_tertiary

    val Neutral = Color(0xFFB5B3AD) // XML: @color/text_neutral
    val TertiaryFromTokens = Color(0xFF909090) // XML: @color/text_tertiary_tokens (from Figma tokens)
    val PrimaryInvert = Color(0xFF0D0D0D) // XML: @color/text_primary_invert
    val SecondaryInvert = Color(0xFF666666) // XML: @color/text_secondary_invert
    val Disabled = Color(0xFF4D4D4D) // XML: @color/text_disabled
    val Brand = Color(0xFFFF7533) // XML: @color/text_brand
    val BrandFromTokens = Color(0xFFFC7833) // XML: @color/text_brand_tokens (from Figma tokens)
    val BrandDisabled = Color(0xFF6B2200) // XML: @color/text_brand_disabled
    val BrandDisabledFromTokens = Color(0xFF994A20) // XML: @color/text_brand_disabled_tokens (from Figma tokens)
    val BrandPrimary05Percent = Color(0xFF7D7D7D) // XML: @color/text_brand_primary_05_percent
    val BrandPrimaryDisabled10Percent = Color(0xFF404040) // XML: @color/text_brand_primary_disabled_10_percent
    val BrandPrimaryDisabled20Percent = Color(0xFF303030) // XML: @color/text_brand_primary_disabled_20_percent
    val Success = Color(0xFF3DC574) // XML: @color/text_success
    val SuccessFromTokens = Color(0xFF2ED23A) // XML: @color/text_success_tokens (from Figma tokens)
    val SuccessDisabled = Color(0xFF1E8626) // XML: @color/text_success_disabled (from Figma tokens)
    val SuccessHover = Color(0xFF00CD00) // XML: @color/text_success_hover (from Figma tokens)
    val SuccessInvert = Color(0xFF09813A) // XML: @color/text_success_invert
    val Warning = Color(0xFFFCC93A) // XML: @color/text_warning
    val WarningFromTokens = Color(0xFFFFD43B) // XML: @color/text_warning_tokens (from Figma tokens)
    val WarningHover = Color(0xFFA08C20) // XML: @color/text_warning_hover (from Figma tokens)
    val WarningInvert = Color(0xFF8A6705) // XML: @color/text_warning_invert
    val Destructive = Color(0xFFEE4D37) // XML: @color/text_destructive
    val DestructiveFromTokens = Color(0xFFE63D3D) // XML: @color/text_destructive_tokens (from Figma tokens)
    val DestructiveHover = Color(0xFF902020) // XML: @color/text_destructive_hover (from Figma tokens)
    val DestructiveInvert = Color(0xFFC33A28) // XML: @color/text_destructive_invert
    val Error = Color(0xFFFFFFFF) // XML: @color/text_error (from Figma tokens)
    val Color = Color(0xFFFFFFFF) // XML: @color/text_color
}

// Semantic Token Colors - Icons
object IconColor {
    val Primary = Color(0xFFE6E6E6) // XML: @color/icon_primary
    val PrimaryFromTokens = Color(0xFF0D0D0D) // XML: @color/icon_primary_tokens (from Figma tokens)
    val PrimaryTransparent40 = Color(0x66E6E6E6) // XML: @color/icon_primary_transparent_40 (40% opacity)
    val PrimaryTransparent60 = Color(0x99E6E6E6) // XML: @color/icon_primary_transparent_60 (60% opacity)
    val PrimaryTransparent10 = Color(0x1A0D0D0D) // XML: @color/icon_primary_transparent_10 (from Figma)
    val Secondary = Color(0xFFB3B3B3) // XML: @color/icon_secondary
    val SecondaryFromTokens = Color(0xFFA0A0A0) // XML: @color/icon_secondary_tokens (from Figma tokens)
    val SecondaryDisabled = Color(0xFF262626) // XML: @color/icon_secondary_disabled
    val Tertiary = Color(0xFF808080) // XML: @color/icon_tertiary
    val TertiaryFromTokens = Color(0xFF909090) // XML: @color/icon_tertiary_tokens (from Figma tokens)
    val PrimaryInvert = Color(0xFF0D0D0D) // XML: @color/icon_primary_invert
    val Disabled = Color(0xFF4D4D4D) // XML: @color/icon_disabled
    val Brand = Color(0xFFFF7533) // XML: @color/icon_brand
    val BrandFromTokens = Color(0xFFFC7833) // XML: @color/icon_brand_tokens (from Figma tokens)
    val BrandDisabled = Color(0xFF6B2200) // XML: @color/icon_brand_disabled
    val BrandDisabledFromTokens = Color(0xFF994A20) // XML: @color/icon_brand_disabled_tokens (from Figma tokens)
    val BrandPrimary05Percent = Color(0xFF7D7D7D) // XML: @color/icon_brand_primary_05_percent
    val BrandPrimaryDisabled10Percent = Color(0xFF404040) // XML: @color/icon_brand_primary_disabled_10_percent
    val BrandPrimaryDisabled20Percent = Color(0xFF1A1A1A) // XML: @color/icon_brand_primary_disabled_20_percent
    val Success = Color(0xFF5DCE8A) // XML: @color/icon_success
    val SuccessFromTokens = Color(0xFF2ED23A) // XML: @color/icon_success_tokens (from Figma tokens)
    val SuccessDisabled = Color(0xFF1E8626) // XML: @color/icon_success_disabled (from Figma tokens)
    val SuccessHover = Color(0xFF00CD00) // XML: @color/icon_success_hover (from Figma tokens)
    val SuccessInvert = Color(0xFF0CA64A) // XML: @color/icon_success_invert
    val Warning = Color(0xFFFCD25A) // XML: @color/icon_warning
    val WarningFromTokens = Color(0xFFFFD43B) // XML: @color/icon_warning_tokens (from Figma tokens)
    val WarningHover = Color(0xFFA08C20) // XML: @color/icon_warning_hover (from Figma tokens)
    val WarningInvert = Color(0xFFE4AB08) // XML: @color/icon_warning_invert
    val Destructive = Color(0xFFEE4D37) // XML: @color/icon_destructive
    val DestructiveFromTokens = Color(0xFFE63D3D) // XML: @color/icon_destructive_tokens (from Figma tokens)
    val DestructiveHover = Color(0xFF902020) // XML: @color/icon_destructive_hover (from Figma tokens)
    val Error = Color(0xFFFFFFFF) // XML: @color/icon_error (from Figma tokens)
}

// Semantic Token Colors - Border
object BorderColor {
    val Primary = Color(0xFF262626) // XML: @color/border_primary (updated from #1F1F1F to match tokens)
    val PrimaryFromTokens = Color(0xFF0D0D0D) // XML: @color/border_primary_tokens (from Figma tokens)
    val Secondary = Color(0xFF333333) // XML: @color/border_secondary
    val BottomBarBorder = Color(0x4D7A7A7A) // XML: @color/border_secondary
    val SecondaryFromTokens = Color(0xFF1E1E1E) // XML: @color/border_secondary_tokens (from Figma tokens)
    val Tertiary = Color(0xFF4D4D4D) // XML: @color/border_tertiary
    val TertiaryFromTokens = Color(0xFF2A2A2A) // XML: @color/border_tertiary_tokens (from Figma tokens)
    val TertiaryTransparent40 = Color(0x664D4D4D) // XML: @color/border_tertiary_transparent_40 (rgba(77,77,77,0.4))
    val PrimaryTransparent30 = Color(0x4D1F1F1F) // XML: @color/border_primary_transparent_30
    val PrimaryInvert = Color(0xFFE6E6E6) // XML: @color/border_primary_invert
    val PrimaryInvertTransparent10 = Color(0x33E6E6E6) // XML: @color/border_primary_invert_transparent_10 (20% opacity, not 10%)
    val Brand = Color(0xFFFF5200) // XML: @color/border_brand
    val BrandFromTokens = Color(0xFFFC7833) // XML: @color/border_brand_tokens (from Figma tokens)
    val BrandDisabled = Color(0xFF994A20) // XML: @color/border_brand_disabled (from Figma tokens)
    val BrandTransparent40 = Color(0x66FF5200) // XML: @color/border_brand_transparent_40 (40% opacity)
    val BrandPrimary05Percent = Color(0xFF141414) // XML: @color/border_brand_primary_05_percent
    val BrandPrimaryDisabled10Percent = Color(0xFF0D0D0D) // XML: @color/border_brand_primary_disabled_10_percent
    val BrandPrimaryDisabled20Percent = Color(0xFF1A1A1A) // XML: @color/border_brand_primary_disabled_20_percent
    val Success = Color(0xFF3DC574) // XML: @color/border_success
    val SuccessFromTokens = Color(0xFF2ED23A) // XML: @color/border_success_tokens (from Figma tokens)
    val SuccessDisabled = Color(0xFF1E8626) // XML: @color/border_success_disabled (from Figma tokens)
    val SuccessHover = Color(0xFF00CD00) // XML: @color/border_success_hover (from Figma tokens)
    val SuccessInvert = Color(0xFF90DDAF) // XML: @color/border_success_invert
    val Destructive = Color(0xFFEE4D37) // XML: @color/border_destructive
    val DestructiveFromTokens = Color(0xFFE63D3D) // XML: @color/border_destructive_tokens (from Figma tokens)
    val DestructiveHover = Color(0xFF902020) // XML: @color/border_destructive_hover (from Figma tokens)
    val Warning = Color(0xFFFCC93A) // XML: @color/border_warning
    val WarningFromTokens = Color(0xFFFFD43B) // XML: @color/border_warning_tokens (from Figma tokens)
    val WarningHover = Color(0xFFA08C20) // XML: @color/border_warning_hover (from Figma tokens)
    val Error = Color(0xFFFFFFFF) // XML: @color/border_error (from Figma tokens)
}

// Overflow colors for scrollable content
object OverflowColor {
    object Gradient {
        val Start = Color(0xFF0D0D0D) // XML: @color/overflow_start
        val End = Color(0x000D0D0D) // XML: @color/overflow_end (Transparent)
    }
}

// Semantic Token Colors - Avatar
object AvatarColor {
    val PersonBorder = TertiaryTransparent40 // Border/Tertiary-Transparent-40% from Figma
    val NonPersonBackground = PopColor.WhiteBlack.White
    val NonPersonBorder = TertiaryTransparent40
    val Text = PopColor.WhiteBlack.White
    // Note: Avatar gradient is defined in GradientPreset.AvatarBrand
}

// TSS (Transaction Status Screen) Colors
object TssColor {
    // Timer/Countdown colors (kept for backward compatibility, but timer is not shown to user)
    object Timer {
        val Background = Color(0xFFD4A745) // Golden/amber background for timer circle
        val BackgroundPending = Color(0xFFD4A745) // Same golden for pending
        val Text = Color(0xFF0D0D0D) // Dark text on timer
        val TrackBackground = Color(0xFF333333) // Track background for progress
    }
    
    // Status-specific colors
    object Success {
        val AccentGreen = Color(0xFF2ED23A) // Bright green for success checkmark
        val TextSuccess = Color(0xFF2ED23A) // Success text color
    }
    
    object Pending {
        val AccentYellow = Color(0xFFFFD43B) // Yellow/warning accent
        val TextPending = Color(0xFFFFD43B) // Pending text color
    }
    
    object Failure {
        val AccentRed = Color(0xFFE63D3D) // Red for failure
        val TextError = Color(0xFFE63D3D) // Error/failure text color
        val RemarkError = Color(0xFFFF7B7B) // Lighter red for remarks text
    }
    
    // Table/Card colors
    object Table {
        val Background = Color(0xFF1F1F1F) // Card/table background
        val Border = Color(0xFF333333) // Card border
        val DividerColor = Color(0xFF333333) // Divider between rows
    }
    
    // Verified badge colors
    object Badge {
        val VerifiedGreen = Color(0xFF2ED23A) // Green checkmark for verified
        val VerifiedBackground = Color(0xFF1E8626) // Darker green background
    }
    
    // Background gradient colors for TSS states (from Figma design)
    object Background {
        // Success - Green gradient from top
        object Success {
            val GradientStart = Color(0xFF2ED23A) // Bright green at top
            val GradientMid = Color(0xFF1E8626) // Medium green
            val GradientEnd = Color(0xFF0D0D0D) // Fades to dark background
        }
        
        // Pending - Yellow/Golden gradient from top
        object Pending {
            val GradientStart = Color(0xFFFFD43B) // Bright yellow at top
            val GradientMid = Color(0xFFA08C20) // Medium golden
            val GradientEnd = Color(0xFF0D0D0D) // Fades to dark background
        }
        
        // Failure - Red gradient from top
        object Failure {
            val GradientStart = Color(0xFFE63D3D) // Bright red at top
            val GradientMid = Color(0xFF902020) // Medium red
            val GradientEnd = Color(0xFF0D0D0D) // Fades to dark background
        }
        
        // Processing - Orange/Brand gradient from top
        object Processing {
            val GradientStart = Color(0xFFFC7833) // Brand orange at top
            val GradientMid = Color(0xFF994A20) // Medium orange
            val GradientEnd = Color(0xFF0D0D0D) // Fades to dark background
        }
    }
}
