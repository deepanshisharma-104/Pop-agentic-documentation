package com.pop.components.ds_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.theme.PopDotColor
import com.pop.components.theme.PopDotSize
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextColor

/**
 * Vertical button sizes based on Figma design node `4858-1047`.
 */
enum class VerticalButtonSize(
    val width: Dp,
    val cornerRadius: Dp,
    val avatarSize: AvatarSize,
) {
    /** Large: 64dp width, maps to AvatarSize.XLarge (64dp) */
    Large(
        width = 64.dp,
        cornerRadius = PopRadius.Medium, // 12dp
        avatarSize = AvatarSize.XLarge
    ),
    /** Medium: 56dp width, maps to AvatarSize.Large (56dp) */
    Medium(
        width = 56.dp,
        cornerRadius = PopRadius.Medium, // 12dp
        avatarSize = AvatarSize.Large
    ),
}

/**
 * PopVerticalButton - A vertical button with an avatar/icon and text label below.
 *
 * Based on Figma: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=4858-1047
 *
 * The button consists of:
 * - Avatar container with border and gradient background
 * - Title text below the avatar (1 or 2 lines)
 *
 * @param avatarType The type of avatar to display (Icon, People, Brand, or Illustration).
 * @param title The text label to display below the avatar.
 * @param onClick Callback when the button is clicked.
 * @param modifier Modifier for the button container.
 * @param size Button size (Large or Medium).
 * @param maxLines Maximum number of lines for the title (1 or 2).
 * @param isDisabled Whether the button is disabled.
 * @param enableMarquee Whether to enable marquee scrolling for single-line titles that overflow.
 */
@Composable
fun PopVerticalButton(
    avatarType: AvatarType,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: VerticalButtonSize = VerticalButtonSize.Large,
    maxLines: Int = 1,
    isDisabled: Boolean = false,
    enableMarquee: Boolean = true,
    showCornerIndicator: Boolean? = false,
) {
    val textColor = if (isDisabled) TextColor.Disabled else TextColor.Primary
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    Column(
        modifier = modifier
            .width(size.width)
            .then(
                if (!isDisabled) {
                    Modifier.clickable(onClick = onClick)
                } else Modifier
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar with border and gradient background styling
        if (showCornerIndicator != true)
            PopAvatar(
                type = avatarType,
                size = size.avatarSize,
                isDisabled = isDisabled
            )

        if (showCornerIndicator == true) {
            Box(
                Modifier
                    .size(size.avatarSize.size + 3.dp)
                    .padding(top = 6.dp, end = 3.dp)
            ) {
                PopAvatar(
                    type = avatarType,
                    size = size.avatarSize,
                    isDisabled = isDisabled
                )
                PopDot(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(10.dp)
                        .offset(x = (2).dp, y = (-2).dp),
                    color = PopDotColor.Orange,
                    size = PopDotSize.Med,
                    isActive = true
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // Title text
        if (maxLines == 1 && enableMarquee) {
            val availableWidthPx = with(density) { size.width.toPx() }
            val textWidthPx = remember(title, size.width) {
                textMeasurer.measure(
                    text = AnnotatedString(title),
                    style = VerticalButtonTextStyle,
                    softWrap = false,
                    maxLines = 1
                ).size.width.toFloat()
            }
            val doesOverflow = textWidthPx > availableWidthPx
            if (doesOverflow) {
                // Single line with marquee support (and gradient) for overflow only
                PopMarqueeText(
                    text = title,
                    style = VerticalButtonTextStyle,
                    color = textColor,
                    modifier = Modifier.fillMaxWidth(),
                    fillMaxWidth = true,
                    shouldAnimate = false, //!Disabled
                    textOverflows = true,
                    maxLines = 1,
                    showRightGradient = true,
                    gradientWidth = 12.dp
                )
            } else {
                // Short text: render centered without marquee/gradient
                Text(
                    text = title,
                    fontFamily = PopTypography.figtree,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            // Multi-line with ellipsis
            Text(
                text = title,
                fontFamily = PopTypography.figtree,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = textColor,
                textAlign = TextAlign.Center,
                maxLines = maxLines.coerceIn(1, 2),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Text style for vertical button labels.
 * Figtree Medium, 12sp, lineHeight 16sp.
 */
val VerticalButtonTextStyle = TextStyle(
    fontFamily = PopTypography.figtree,
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    textAlign = TextAlign.Center
)
