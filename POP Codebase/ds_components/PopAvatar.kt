package com.pop.components.ds_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pop.components.theme.BorderColor
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.IconColor
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopIconResources
import com.pop.components.theme.PopColor
import com.pop.components.theme.SurfaceColor
import com.pop.components.utils.popBackground
import com.pop.components.utils.popBorder
import com.pop.compose_components.R

/**
 * PopAvatar - Generic Avatar component for People and Non-People (Logos/Icons).
 * Based on Figma: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=856-171&m=dev
 */

@Composable
fun PopAvatar(
    modifier: Modifier = Modifier,
    type: AvatarType,
    size: AvatarSize,
    contentDescription: String? = null,
    isDisabled: Boolean = false,
    topRightComposable: @Composable (() -> Unit)? = null
) {
    when (type) {
        is AvatarType.Favorite -> {
            val shape = CircleShape
            val initials = getInitials(type.name)
            Box(
                modifier = modifier.size(size.size)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(shape)
                        .popBorder(
                            width = 1.dp,
                            gradient = if (type.showBorder) type.borderGradient else null,
                            shape = shape
                        )
                        .popBackground(
                            gradient = type.background,
                            shape = shape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Always show initials as fallback (visible if image fails to load)
                    Text(
                        text = initials,
                        style = size.initialsStyle,
                        color = type.textColor,
                        modifier = Modifier.widthIn(max = size.initialsMaxWidth)
                    )
                    
                    // Overlay image on top if provided (will cover initials when loaded successfully)
                    if (type.imageModel != null) {
                        PopVisualElement(
                            visualElement = type.imageModel,
                            modifier = Modifier.matchParentSize(),
                            contentScale = ContentScale.Crop,
                            contentDescription = contentDescription ?: type.name
                        )
                    }

                    if (isDisabled) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(color = SurfaceColor.PrimaryDisabled70, shape = shape)
                        )
                    }
                }
                Image(
                    modifier =
                        Modifier.align(Alignment.BottomEnd),
                    painter = painterResource(id = R.drawable.ic_fav_white_large_active),
                    contentDescription = "fav",
                )

                topRightComposable?.let { content ->
                    Box(modifier = Modifier.align(Alignment.TopEnd)) {
                        content()
                    }
                }
            }

        }
        is AvatarType.People -> {
            val shape = CircleShape
            val initials = getInitials(type.name)

            Box(
                modifier = modifier.size(size.size)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(shape)
                        .popBorder(
                            width = 1.dp,
                            gradient = if (type.showBorder) type.borderGradient else null,
                            shape = shape
                        )
                        .popBackground(
                            gradient = type.background,
                            shape = shape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Always show initials as fallback (visible if image fails to load)
                    Text(
                        text = initials,
                        style = size.initialsStyle,
                        color = type.textColor,
                        modifier = Modifier.widthIn(max = size.initialsMaxWidth)
                    )
                    
                    // Overlay image on top if provided (will cover initials when loaded successfully)
                    if (type.imageModel != null) {
                        PopVisualElement(
                            visualElement = type.imageModel,
                            modifier = Modifier.matchParentSize(),
                            contentScale = ContentScale.Crop,
                            contentDescription = contentDescription ?: type.name
                        )
                    }

                    if (isDisabled) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(color = SurfaceColor.PrimaryDisabled70, shape = shape)
                        )
                    }
                }

                topRightComposable?.let { content ->
                    Box(modifier = Modifier.align(Alignment.TopEnd)) {
                        content()
                    }
                }
            }
        }

        is AvatarType.Brand -> {
            // Brand avatars use Squircle shape (matches earlier NonPerson behavior).
            val shape = type.shape ?: SquircleShape(cornerRadius = 20.dp, smoothing = 1.8f)
            // Use custom padding if provided, otherwise use logoPadding for consistent icon sizing
            val iconPadding = type.iconPadding ?: 0.dp
            Box(
                modifier = modifier
                    .size(size.size)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(shape)
                        .popBorder(
                            width = 1.dp,
                            gradient = if (type.showBorder) type.borderGradient else null,
                            shape = shape
                        )
                        .popBackground(
                            gradient = type.background,
                            shape = shape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    PopVisualElement(
                        visualElement = type.imageModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(iconPadding)
                            .clip(shape),
                        contentScale = ContentScale.Fit,
                        contentDescription = contentDescription ?: "Avatar Brand"
                    )

                    if (isDisabled) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(color = SurfaceColor.PrimaryDisabled70, shape = shape)
                        )
                    }
                }

                topRightComposable?.let { content ->
                    Box(modifier = Modifier.align(Alignment.TopEnd)) {
                        content()
                    }
                }
            }
        }

        is AvatarType.Icon -> {
            val shape = type.shape ?: SquircleShape(cornerRadius = size.cornerRadius, smoothing = 1.2f)

            // Figma disabled variants for Icon are represented via different fills (e.g. Primary-50%),
            // so we avoid the global dark overlay and instead adjust fill/tint.
//            val effectiveFill = when {
//                isDisabled && type.fill == AvatarIconFill.PrimaryHighlighted -> AvatarIconFill.Primary50Percent
//                else -> type.fill
//            }

            val (bg, border) = when (type.fill) {
                AvatarIconFill.PrimaryHighlighted ->
                    PopGradient.Radial(
                        colors = listOf(SurfaceColor.BrandPrimaryDisabled20Percent, SurfaceColor.BrandPrimaryDisabled21Percent, SurfaceColor.BrandPrimaryDisabled10Percent),
                        stops = listOf(0.0f, 0.43f, 1f),
                        alignment = Alignment.TopCenter,
                        radius = size.size.value,
                        tileMode = TileMode.Decal
                    ) to BorderColor.Secondary
                AvatarIconFill.SecondaryHighlighted -> PopGradient.Radial(
                    colors = listOf(SurfaceColor.Gradient.SecondaryHighlighted.Start, SurfaceColor.Gradient.SecondaryHighlighted.Mid,  SurfaceColor.Gradient.SecondaryHighlighted.End),
                    stops = listOf(0f,0.46f,1f),
                    alignment = Alignment.TopCenter,
                    radius = 2 * size.size.value,
                    tileMode = TileMode.Decal
                ) to BorderColor.Secondary
                AvatarIconFill.TertiaryTransparent -> null to BorderColor.Tertiary
                AvatarIconFill.Primary50Percent -> PopGradient.Solid(Color(0x990D0D0D)) to null
                else -> null to null
            }

            val iconTintColor = type.iconTint ?: if (type.fill == AvatarIconFill.Primary50Percent) {
                IconColor.Primary
            } else {
                IconColor.Tertiary
            }

            Box(
                modifier = modifier
                    .size(size.size)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(shape)
                        .then(
                            if (bg != null) Modifier.popBackground(
                                gradient = bg,
                                shape = shape
                            ) else Modifier
                        )
                        .then(
                            when {
                                type.showBorder && border != null -> Modifier.border(
                                    width = 1.dp,
                                    color = border,
                                    shape = shape
                                )

                                else -> Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    val resId = type.icon?.let { PopIconResources.getIconResourceId(it, type.iconStyle) }
                    if (resId != null) {
                        Image(
                            painter = painterResource(id = resId),
                            contentDescription = contentDescription,
                            modifier = Modifier.size(size.iconSize),
                            colorFilter = ColorFilter.tint(iconTintColor)
                        )
                    }
                }

                topRightComposable?.let { content ->
                    Box(modifier = Modifier.align(Alignment.TopEnd)) {
                        content()
                    }
                }
            }
        }

        is AvatarType.Illustration -> {
            val shape = type.shape ?: RoundedCornerShape(size.cornerRadius)
            Box(
                modifier = modifier
                    .size(size.size)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(shape),
                    contentAlignment = Alignment.Center
                ) {
                    PopVisualElement(
                        visualElement = type.imageModel,
                        modifier = Modifier.matchParentSize().padding(size.logoPadding),
                        contentScale = ContentScale.Fit,
                        contentDescription = contentDescription ?: "Avatar Illustration"
                    )

                    if (isDisabled) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(color = SurfaceColor.PrimaryDisabled70, shape = shape)
                        )
                    }
                }

                topRightComposable?.let { content ->
                    Box(modifier = Modifier.align(Alignment.TopEnd)) {
                        content()
                    }
                }
            }
        }

        is AvatarType.Image -> {
            // Image type - edge-to-edge circular image with no padding, border, or background
            val shape = CircleShape
            Box(
                modifier = modifier
                    .size(size.size)
            ) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(shape),
                    contentAlignment = Alignment.Center
                ) {
                    // Use fillMaxSize() instead of matchParentSize() because PopVisualElement
                    // wraps the image in its own Box, so matchParentSize() won't work correctly
                    PopVisualElement(
                        visualElement = type.imageModel,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        contentDescription = contentDescription ?: "Avatar Image"
                    )

                    if (isDisabled) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .background(color = SurfaceColor.PrimaryDisabled70, shape = shape)
                        )
                    }
                }

                topRightComposable?.let { content ->
                    Box(modifier = Modifier.align(Alignment.TopEnd)) {
                        content()
                    }
                }
            }
        }

        is AvatarType.RawImage -> {
            Box(
                modifier = modifier.size(size.size)
            ) {
                PopVisualElement(
                    visualElement = type.imageModel,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit,
                    contentDescription = contentDescription ?: "Avatar Raw Image"
                )

                topRightComposable?.let { content ->
                    Box(modifier = Modifier.align(Alignment.TopEnd)) {
                        content()
                    }
                }
            }
        }
    }
}

private fun getInitials(name: String): String {
    if (name.isBlank()) return ""
    val initials = name.trim()
        .split("\\s+".toRegex())
        .take(2)
        .mapNotNull { word ->
            // Only take the first letter character from each word, ignoring numbers/symbols
            word.firstOrNull { it.isLetter() }?.uppercaseChar()
        }
        .joinToString("")
    
    // If no letter-based initials could be extracted (e.g., phone number without name), return "#"
    return initials.ifEmpty { "#" }
}

enum class AvatarIconFill {
    PrimaryHighlighted,
    SecondaryHighlighted,
    TertiaryTransparent,
    OnlyIcon,
    Primary50Percent,
}
