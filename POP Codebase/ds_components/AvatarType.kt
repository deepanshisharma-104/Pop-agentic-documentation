package com.pop.components.ds_components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.models.VisualElement
import com.pop.components.theme.AvatarColor
import com.pop.components.theme.BorderColor
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.IconName
import com.pop.components.theme.IconStyle
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextColor

sealed interface AvatarType {
    data class People(
        val name: String,
        val imageModel: VisualElement? = null,
        val background: PopGradient = GradientPreset.AvatarBrand.gradient,
        // Matches legacy Person behavior (and fixes missing border issue).
        val showBorder: Boolean = true,
        val borderGradient: PopGradient = PopGradient.Solid(AvatarColor.PersonBorder),
        val textColor: Color = TextColor.Primary.copy(alpha = 0.8f),
    ) : AvatarType

    data class Favorite(
        val name: String,
        val imageModel: VisualElement? = null,
        val background: PopGradient = GradientPreset.AvatarBrand.gradient,
        // Matches legacy Person behavior (and fixes missing border issue).
        val showBorder: Boolean = true,
        val borderGradient: PopGradient = PopGradient.Solid(AvatarColor.PersonBorder),
        val textColor: Color = TextColor.Primary.copy(alpha = 0.8f),
    ) : AvatarType

    data class Brand(
        val imageModel: VisualElement? = null,
        val background: PopGradient = PopGradient.Solid(AvatarColor.NonPersonBackground),
        val showBorder: Boolean = true,
        val borderGradient: PopGradient = PopGradient.Solid(AvatarColor.NonPersonBorder),
        // Defaults to SquircleShape if null
        val shape: Shape? = null,
        // Custom icon padding. If null, uses size.logoPadding. Use this to control icon size (e.g., 20.dp for 24dp icon in 64dp avatar).
        val iconPadding: Dp? = null,
    ) : AvatarType

    data class Icon(
        val fill: AvatarIconFill,
        val icon: IconName? = null,
        val iconStyle: IconStyle = IconStyle.Outline,
        val iconTint: Color? = null, // null => uses default tint as in Figma
        val showBorder: Boolean = true,
        // Border is driven by fill variant in PopAvatar; allow overriding if needed.
        val borderColor: Color = BorderColor.Tertiary,
        val shape: Shape? = null,
    ) : AvatarType

    data class Illustration(
        val imageModel: VisualElement,
        val shape: Shape? = null,
    ) : AvatarType

    /**
     * Image type - displays a visual element covering the entire circular avatar area
     * No padding, no border, no background - just the image cropped to circle
     * Perfect for profile pictures that need edge-to-edge display
     */
    data class Image(
        val imageModel: VisualElement,
    ) : AvatarType

    /**
     * Raw image type - renders the visual element as-is inside avatar bounds.
     * No shape, no border, no background and no forced clipping.
     * Size is still controlled by [AvatarSize].
     */
    data class RawImage(
        val imageModel: VisualElement,
    ) : AvatarType
}

/**
 * Avatar sizes per Figma node `5568:9341`.
 *
 * Sizes: 64, 56, 36, 28, 16
 * - Brand corner radius ≈ size/8 (64→8, 36→4.5)
 * - Icon sizes vary by avatar size (64/56→24, 36→10.667, 28→6)
 */
enum class AvatarSize(
    val size: Dp,
    val initialsStyle: TextStyle,
    val initialsMaxWidth: Dp,
    val logoPadding: Dp,
    val cornerRadius: Dp,
    val iconSize: Dp,
)
{
    XXLarge(
        size = 130.dp,
        initialsStyle = TextStyle(
            fontFamily = PopTypography.figtree,
            fontWeight = FontWeight.Bold,
            fontSize = 56.sp,
            lineHeight = 40.sp
        ),
        initialsMaxWidth = 120.dp,
        logoPadding = 16.dp,
        cornerRadius = 32.dp,
        iconSize = 48.dp
    ),
    XLarge(
        size = 64.dp,
        initialsStyle = TextStyle(
            fontFamily = PopTypography.figtree,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 40.sp
        ),
        initialsMaxWidth = 56.dp,
        // Brand/logo inset (keeps logo comfortably inside the squircle).
        logoPadding = 8.dp,
        cornerRadius = 16.dp,
        iconSize = 24.dp
    ),
    Large(
        size = 56.dp,
        initialsStyle = TextStyle(
            fontFamily = PopTypography.figtree,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp
        ),
        initialsMaxWidth = 48.dp,
        logoPadding = 7.dp,
        cornerRadius = 14.dp,
        iconSize = 20.dp
    ),
    Medium(
        size = 36.dp,
        initialsStyle = TextStyle(
            fontFamily = PopTypography.figtree,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 20.sp
        ),
        initialsMaxWidth = 32.dp,
        logoPadding = 4.5.dp,
        cornerRadius = 12.dp,
        iconSize = 16.dp
    ),
    Small(
        size = 28.dp,
        initialsStyle = TextStyle(
            fontFamily = PopTypography.figtree,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),
        initialsMaxWidth = 24.dp,
        logoPadding = 3.5.dp,
        cornerRadius = 10.dp,
        iconSize = 12.dp
    ),
    XSmall(
        size = 16.dp,
        initialsStyle = TextStyle(
            fontFamily = PopTypography.figtree,
            fontWeight = FontWeight.Bold,
            fontSize = 8.sp,
            lineHeight = 10.sp
        ),
        initialsMaxWidth = 16.dp,
        logoPadding = 2.dp,
        cornerRadius = 8.dp,
        iconSize = 8.dp
    ),
}