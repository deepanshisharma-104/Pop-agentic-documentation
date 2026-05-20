package com.pop.components.ds_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.IconName
import com.pop.components.theme.IconStyle
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopIconResources
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextColor
import com.pop.components.utils.glowEffect
import com.pop.components.utils.popBackground

/**
 * Badge types based on Figma design node `5529-3928`.
 */
enum class BadgeType {
    /** Orange/Brand badge - supports bg+glow, bg only, or text only */
    Orange,
    /** Green/Success badge - supports bg+glow, bg only, or text only */
    Green,
    /** Red/Destructive badge - supports bg+glow, bg only, or text only */
    Red,
    /** White primary badge - supports bg+glow, bg only, or text only (inverted text when has bg) */
    WhitePrimary,
    /** White tertiary badge - text only (grey text) */
    WhiteTertiary,
    /** Warning badge - text only (yellow text) */
    Warning,
}

/**
 * PopBadge - Badge component from POP Design System.
 *
 * Based on Figma: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=5529-3928
 *
 * @param label The text to display in the badge.
 * @param type The badge type/color variant.
 * @param hasBg Whether to show background. Some types only support no-bg (WhiteTertiary, Warning).
 * @param hasGlow Whether to show glow effect. Only applies when hasBg is true.
 * @param leftIcon Optional icon to show on the left side.
 * @param rightIcon Optional icon to show on the right side.
 * @param modifier Modifier for the badge container.
 */
@Composable
fun PopBadge(
    modifier: Modifier = Modifier,
    label: String,
    type: BadgeType,
    hasBg: Boolean = true,
    hasGlow: Boolean = false,
    leftIcon: IconName? = null,
    rightIcon: IconName? = null,
) {
    // WhiteTertiary and Warning only support no-bg mode
    val effectiveHasBg = when (type) {
        BadgeType.WhiteTertiary, BadgeType.Warning -> false
        else -> hasBg
    }
    val effectiveHasGlow = effectiveHasBg && hasGlow

    val shape = SquircleShape() // Figma: radius/6

    // Background gradient based on type
    val bgGradient: PopGradient? = if (effectiveHasBg) {
        when (type) {
            BadgeType.Orange -> GradientPreset.GradientOrange.gradient
            BadgeType.Green -> GradientPreset.GradientGreen.gradient
            BadgeType.Red -> GradientPreset.GradientRed.gradient
            BadgeType.WhitePrimary -> GradientPreset.BadgeWhitePrimary.gradient
            else -> null
        }
    } else null

    // Text color based on type and bg state
    val textColor: Color = when {
        effectiveHasBg -> when (type) {
            BadgeType.WhitePrimary -> TextColor.PrimaryInvert // Dark text on white bg
            else -> TextColor.Primary // Light text on colored bg
        }
        else -> when (type) {
            BadgeType.Orange -> TextColor.Brand
            BadgeType.Green -> TextColor.Success
            BadgeType.Red -> TextColor.Destructive
            BadgeType.Warning -> TextColor.Warning
            BadgeType.WhitePrimary -> TextColor.Primary
            BadgeType.WhiteTertiary -> TextColor.Tertiary
        }
    }

    // Icon tint matches text color
    val iconTint = textColor

    // Glow color based on type
    val glowColor: Color? = if (effectiveHasGlow) {
        when (type) {
            BadgeType.Orange -> BadgeGlowColor.Orange
            BadgeType.Green -> BadgeGlowColor.Green
            BadgeType.Red -> BadgeGlowColor.Red
            BadgeType.WhitePrimary -> BadgeGlowColor.White
            else -> null
        }
    } else null

    // Horizontal padding: 8dp with bg, 0dp without
    val horizontalPadding = if (effectiveHasBg) 8.dp else 0.dp

    Box(
        modifier = modifier
            .then(
                if (glowColor != null) {
                    Modifier.glowEffect(
                        blurRadius = 9.dp,
                        spreadRadius = 0.dp,
                        color = glowColor,
                        shape = shape
                    )
                } else Modifier.Companion
            )
            .clip(shape)
            .then(
                if (bgGradient != null) {
                    Modifier.popBackground(gradient = bgGradient, shape = shape)
                } else Modifier.Companion
            )
            .padding(horizontal = horizontalPadding, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left icon
            if (leftIcon != null) {
                BadgeIcon(iconName = leftIcon, tint = iconTint)
            }

            // Label
            Text(
                text = label,
                fontFamily = PopTypography.figtree,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = textColor,
                maxLines = 1
            )

            // Right icon
            if (rightIcon != null) {
                BadgeIcon(iconName = rightIcon, tint = iconTint)
            }
        }
    }
}

@Composable
private fun BadgeIcon(
    iconName: IconName,
    tint: Color,
) {
    val resId = PopIconResources.getIconResourceId(iconName, IconStyle.Outline)
    if (resId != null) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = null,
            modifier = Modifier.size(6.dp),
            colorFilter = ColorFilter.tint(tint)
        )
    }
}

/**
 * Glow colors for badges per Figma design.
 */
private object BadgeGlowColor {
    val Orange = Color(0x9EFF500B) // rgba(255,80,11,0.62)
    val Green = Color(0x6621C321) // rgba(33,195,33,0.4)
    val Red = Color(0x9EE22626)   // rgba(226,38,38,0.62)
    val White = Color(0x6BFFFFFF) // rgba(255,255,255,0.42)
}