package com.pop.components.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pop.compose_components.R


object FlashTypography {
    val figtree = FontFamily(
        Font(R.font.figtree_medium, FontWeight.Normal),
        Font(R.font.figtree_medium, FontWeight.Medium),
        Font(R.font.figtree_semibold, FontWeight.SemiBold),
        Font(R.font.figtree_bold, FontWeight.Bold)
    )

    private val oswald = FontFamily(
        Font(R.font.oswald_regular, FontWeight.Normal)
    )

    // Headings
    val h0 = TextStyle(
        fontFamily = figtree,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.04).sp,
        lineHeight = 28.sp
    )

    val h1 = TextStyle(
        fontFamily = figtree,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.04).sp,
        lineHeight = 24.sp
    )

    val h2 = TextStyle(
        fontFamily = figtree,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.02).sp,
        lineHeight = 20.sp
    )

    val h3 = TextStyle(
        fontFamily = figtree,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.02).sp,
        lineHeight = 18.sp
    )

    val h4 = TextStyle(
        fontFamily = figtree,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.02).sp,
        lineHeight = 15.sp
    )

    val h5 = TextStyle(
        fontFamily = figtree,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = (-0.02).sp,
        lineHeight = 13.sp
    )

    // Body text
    val body1 = TextStyle(
        fontFamily = figtree,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (-0.02).sp,
        lineHeight = 18.sp
    )

    val body2 = TextStyle(
        fontFamily = figtree,
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (-0.02).sp,
        lineHeight = 15.sp
    )

    val body3 = TextStyle(
        fontFamily = figtree,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (-0.01).sp,
        lineHeight = 13.sp
    )

    // Labels
    val label1 = TextStyle(
        fontFamily = figtree,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (-0.01).sp,
        lineHeight = 10.sp
    )
    val label2 = TextStyle(
        fontFamily = figtree,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = (-0.01).sp,
        lineHeight = 10.sp
    )

    // Misc styles
    val upiNumber = TextStyle(
        fontFamily = figtree,
        fontSize = 64.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.sp,
        lineHeight = 64.sp
    )

    val upiRupeeSymbol = TextStyle(
        fontFamily = oswald,
        fontSize = 60.sp,
        fontWeight = FontWeight.Normal,
        letterSpacing = 0.sp,
        lineHeight = 60.sp
    )
}

val LocalFlashTypography = staticCompositionLocalOf { FlashTypography }

internal val MaterialTypography = Typography(
    headlineLarge = FlashTypography.h0,
    headlineMedium = FlashTypography.h1,
    headlineSmall = FlashTypography.h2,
    titleLarge = FlashTypography.h3,
    titleMedium = FlashTypography.h4,
    titleSmall = FlashTypography.h5,
    bodyLarge = FlashTypography.body1,
    bodyMedium = FlashTypography.body2,
    bodySmall = FlashTypography.body3,
    labelSmall = FlashTypography.label1
) 