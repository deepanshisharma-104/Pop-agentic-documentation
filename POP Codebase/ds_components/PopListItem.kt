package com.pop.components.ds_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pop.components.compose_components.PopDivider
import com.pop.components.compose_components.PopDividerStyle
import com.pop.components.models.VisualElement
import com.pop.components.theme.BorderColor
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor

/**
 * PopListItem - A generic list item component that supports optional leading avatar,
 * headline, supporting text, and trailing content.
 *
 * Figma link: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=405-10994&t=nWbCETTLsSvZSiAY-4
 *
 * Based on typical Design System patterns and Figma specifications.
 *
 * @param headlineText The primary text of the list item
 * @param modifier Modifier to be applied to the list item
 * @param supportingText Optional secondary text below the headline
 * @param leadingAvatar Optional avatar to start (left) of the text
 * @param trailingContent Optional content to end (right) of the list item (e.g. Icon, Switch, Checkbox)
 * @param onClick Optional callback when the item is clicked
 * @param onLongClick Optional callback when the item is long pressed
 * @param onHeadlineTextClick Optional callback when the headline text is clicked (takes precedence over onClick)
 * @param onSupportingTextClick Optional callback when the supporting text is clicked (takes precedence over onClick)
 * @param enabled Whether the item is enabled (interactive)
 * @param headlineColor Color for the headline text
 * @param supportingTextColor Color for the supporting text
 * @param headlineTextStyle Text style for the headline (defaults to DS labelLarge)
 * @param supportingTextStyle Text style for the supporting text (defaults to DS paragraphSmall)
 * @param enableHeadlineMarquee Whether to enable marquee for headline text (default: false)
 * @param enableSupportingTextMarquee Whether to enable marquee for supporting text (default: false)
 * @param marqueeSpeedPxPerSecond Speed of marquee scroll in pixels per second (default: 60f)
 * @param marqueeDelayMillis Delay before starting marquee in milliseconds (default: 1500L)
 * @param marqueeLoopCount Number of marquee loops (null for infinite, default: null)
 * @param marqueeGradientWidth Width of gradient fade at edges (default: 56.dp)
 * @param showMarqueeLeftGradient Whether to show left gradient fade on both headline and supporting text (default: false)
 * @param showMarqueeRightGradient Whether to show right gradient fade on both headline and supporting text (default: false)
 * @param showHeadlineLeftGradient Whether to show left gradient fade on headline text when scrolling (default: false)
 * @param showHeadlineRightGradient Whether to show right gradient fade on headline text end (default: false)
 * @param headlineTextFillMaxWidth When true, headline text takes full available width with gradient at far right (default: false)
 * @param headlineLeftIcon Optional left icon next to headline text (VisualElement)
 * @param headlineRightIcon Optional right icon next to headline text (VisualElement)
 * @param supportingTextLeftIcon Optional left icon next to supporting text (VisualElement)
 * @param supportingTextRightIcon Optional right icon next to supporting text (VisualElement)
 * @param headlineIconSizeDp Size of headline icons in dp (default: 16.dp)
 * @param supportingTextIconSizeDp Size of supporting text icons in dp (default: 12.dp)
 * @param iconGapDp Gap between icon and text in dp (default: 6.dp)
 * @param showDivider Whether to show the divider at the bottom of the list item (default: true)
 * @param dividerColor Color for the divider - can be BorderColor.Secondary or BorderColor.Tertiary (default: BorderColor.Tertiary)
 * @param verticalPadding Vertical padding for the inner content of the list item (default: 12.dp)
 * @param showHeadlineUnderline Whether to show an underline below the headline text (default: false)
 * @param showSupportingTextUnderline Whether to show an underline below the supporting text (default: false)
 * @param headlineUnderlineStyle Style of the headline underline - [PopDividerStyle.Solid] or [PopDividerStyle.Dashed] (default: PopDividerStyle.Solid)
 * @param supportingTextUnderlineStyle Style of the supporting text underline - [PopDividerStyle.Solid], [PopDividerStyle.Dashed], or [PopDividerStyle.Dotted] (default: PopDividerStyle.Solid)
 * @param headlineUnderlineColor Color of the headline underline (defaults to headlineColor)
 * @param supportingTextUnderlineColor Color of the supporting text underline (defaults to supportingTextColor)
 * @param headlineUnderlineDashLength Length of each dash segment for headline underline (only used for dashed style, default: 4.dp)
 * @param headlineUnderlineGapLength Length of gap between dashes for headline underline (only used for dashed style, default: 3.dp)
 * @param headlineUnderlineThickness Thickness of the headline underline (default: 1.dp)
 * @param supportingTextUnderlineDashLength Length of each dash segment for supporting text underline (only used for dashed style, default: 4.dp)
 * @param supportingTextUnderlineGapLength Length of gap between dashes for supporting text underline (only used for dashed style, default: 3.dp)
 * @param supportingTextUnderlineThickness Thickness of the supporting text underline (default: 1.dp)
 * @param supportingTextUnderlineDotDiameter Diameter of each dot for supporting text underline (only used for dotted style, default: 2.5.dp)
 * @param supportingTextUnderlineDotGap Gap between dots for supporting text underline (only used for dotted style, default: 3.dp)
 * @param showHeadlineLoader Whether to show a shimmer loader instead of headline text (default: false)
 * @param showSupportingTextLoader Whether to show a shimmer loader instead of supporting text (default: false)
 * @param showTrailingContentLoader Whether to show a shimmer loader instead of trailing content (default: false)
 * @param headlineLoaderWidth Width of the headline loader shimmer box (default: 120.dp)
 * @param headlineLoaderHeight Height of the headline loader shimmer box (default: 20.dp)
 * @param supportingTextMaxLines Maximum number of lines for the supporting text (default: 1). Set to higher value for multi-line support.
 * @param supportingTextLoaderWidth Width of the supporting text loader shimmer box (default: 100.dp)
 * @param supportingTextLoaderHeight Height of the supporting text loader shimmer box (default: 16.dp)
 * @param trailingContentLoaderWidth Width of the trailing content loader shimmer box (default: 104.dp)
 * @param trailingContentLoaderHeight Height of the trailing content loader shimmer box (default: 42.dp)
 * @param trailingContentLoaderShape Shape of the trailing content loader shimmer box (default: RoundedCornerShape(24.dp))
 */
@Composable
fun PopListItem(
    headlineText: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    leadingAvatar: PopListItemAvatar? = null,
    trailingContent: (@Composable () -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    onHeadlineTextClick: (() -> Unit)? = null,
    onHeadlineRightIconClick: (() -> Unit)? = null,
    onSupportingTextClick: (() -> Unit)? = null,
    onSupportingRightIconClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    headlineColor: Color = TextColor.Primary,
    supportingTextColor: Color = TextColor.Tertiary,
    headlineTextStyle: TextStyle = PopTypography.labelLarge,
    supportingTextStyle: TextStyle = PopTypography.paragraphSmall,
    enableHeadlineMarquee: Boolean = false,
    enableSupportingTextMarquee: Boolean = false,
    marqueeSpeedPxPerSecond: Float = 60f,
    marqueeDelayMillis: Long = 1500L,
    marqueeLoopCount: Int? = null,
    marqueeGradientWidth: Dp = 56.dp,
    showMarqueeLeftGradient: Boolean = false,
    showMarqueeRightGradient: Boolean = false,
    showHeadlineLeftGradient: Boolean = false,
    showHeadlineRightGradient: Boolean = false,
    headlineTextFillMaxWidth: Boolean = false,
    headlineLeftIcon: VisualElement? = null,
    headlineRightIcon: VisualElement? = null,
    supportingTextLeftIcon: VisualElement? = null,
    supportingTextRightIcon: VisualElement? = null,
    headlineIconSizeDp: Dp = 16.dp,
    supportingTextIconSizeDp: Dp = 12.dp,
    iconGapDp: Dp = 6.dp,
    showDivider: Boolean = true,
    dividerColor: Color = BorderColor.Tertiary,
    verticalPadding: Dp = 12.dp,
    horizontalPadding: Dp = 16.dp,
    showHeadlineUnderline: Boolean = false,
    showSupportingTextUnderline: Boolean = false,
    headlineUnderlineStyle: PopDividerStyle = PopDividerStyle.Solid,
    supportingTextUnderlineStyle: PopDividerStyle = PopDividerStyle.Solid,
    headlineUnderlineColor: Color? = null,
    supportingTextUnderlineColor: Color? = null,
    headlineUnderlineDashLength: Dp = 4.dp,
    headlineUnderlineGapLength: Dp = 3.dp,
    headlineUnderlineThickness: Dp = 1.dp,
    supportingTextUnderlineDashLength: Dp = 4.dp,
    supportingTextUnderlineGapLength: Dp = 3.dp,
    supportingTextUnderlineThickness: Dp = 1.dp,
    supportingTextUnderlineDotDiameter: Dp = 2.5.dp,
    supportingTextUnderlineDotGap: Dp = 3.dp,
    supportingTextMaxLines: Int = 1,
    showHeadlineLoader: Boolean = false,
    showSupportingTextLoader: Boolean = false,
    showTrailingContentLoader: Boolean = false,
    headlineLoaderWidth: Dp = 120.dp,
    headlineLoaderHeight: Dp = 20.dp,
    supportingTextLoaderWidth: Dp = 100.dp,
    supportingTextLoaderHeight: Dp = 16.dp,
    trailingContentLoaderWidth: Dp = 104.dp,
    trailingContentLoaderHeight: Dp = 42.dp,
    trailingContentLoaderShape: Shape = RoundedCornerShape(24.dp)
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if ((onClick != null || onLongClick != null) && enabled) {
                    Modifier.combinedClickable(
                        interactionSource = interactionSource,
                        indication = rememberRipple(color = SurfaceColor.Secondary),
                        onClick = onClick ?: {},
                        onLongClick = onLongClick,
                        role = Role.Button
                    )
                } else {
                    Modifier
                }
            )
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Leading Content
        if (leadingAvatar != null) {
            Box(modifier = leadingAvatar.modifier) {
                PopAvatar(
                    type = leadingAvatar.type,
                    size = leadingAvatar.size,
                    modifier = Modifier,
                    contentDescription = leadingAvatar.contentDescription,
                    isDisabled = !enabled,
                    topRightComposable = leadingAvatar.topRightComposable
                )
            }
        }

        // Text Content
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            // Headline row with optional icons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Headline left icon
                headlineLeftIcon?.let { icon ->
                    PopVisualElement(
                        modifier = Modifier.size(headlineIconSizeDp),
                        visualElement = icon,
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.width(iconGapDp))
                }

                // Headline text or loader
                if (showHeadlineLoader) {
                    PopShimmerBox(
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .width(headlineLoaderWidth)
                            .height(headlineLoaderHeight),
                        shape = RoundedCornerShape(4.dp)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .then(
                                if (onHeadlineTextClick != null && enabled) {
                                    Modifier.clickable(
                                        onClick = onHeadlineTextClick,
                                        indication = null, // No ripple for headline text click
                                        interactionSource = remember { MutableInteractionSource() }
                                    )
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        PopMarqueeText(
                            modifier = if (headlineTextFillMaxWidth) Modifier.fillMaxWidth() else Modifier,
                            fillMaxWidth = headlineTextFillMaxWidth,
                            text = headlineText,
                            style = headlineTextStyle,
                            color = if (enabled) headlineColor else TextColor.Disabled,
                            shouldAnimate = enableHeadlineMarquee,
                            speedPxPerSecond = marqueeSpeedPxPerSecond,
                            delayMillis = marqueeDelayMillis,
                            loopCount = marqueeLoopCount,
                            gradientWidth = marqueeGradientWidth,
                            showLeftGradient = showMarqueeLeftGradient || showHeadlineLeftGradient,
                            showRightGradient = showMarqueeRightGradient || showHeadlineRightGradient,
                            textOverflows = showHeadlineRightGradient || showMarqueeRightGradient || showHeadlineLeftGradient,
                            showUnderline = showHeadlineUnderline,
                            underlineStyle = headlineUnderlineStyle,
                            underlineColor = headlineUnderlineColor,
                            underlineDashLength = headlineUnderlineDashLength,
                            underlineGapLength = headlineUnderlineGapLength,
                            underlineThickness = headlineUnderlineThickness
                        )
                    }
                }

                // Headline right icon
                headlineRightIcon?.let { icon ->
                    Spacer(modifier = Modifier.width(iconGapDp))
                    PopVisualElement(
                        modifier = Modifier
                            .size(headlineIconSizeDp)
                            .then(
                                if (onHeadlineRightIconClick != null && enabled) {
                                    Modifier.clickable(
                                        onClick = onHeadlineRightIconClick,
                                        indication = rememberRipple(bounded = false, radius = headlineIconSizeDp),
                                        interactionSource = remember { MutableInteractionSource() }
                                    )
                                } else {
                                    Modifier
                                }
                            ),
                        visualElement = icon,
                        contentScale = ContentScale.Fit
                    )
                }
            }

            if (supportingText != null) {
                Spacer(modifier = Modifier.height(4.dp))

                // Supporting text row with optional icons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Supporting text left icon
                    supportingTextLeftIcon?.let { icon ->
                        PopVisualElement(
                            modifier = Modifier.size(supportingTextIconSizeDp),
                            visualElement = icon,
                            contentScale = ContentScale.Fit
                        )
                        Spacer(modifier = Modifier.width(iconGapDp))
                    }

                    // Supporting text or loader
                    if (showSupportingTextLoader) {
                        PopShimmerBox(
                            modifier = Modifier
                                .weight(1f, fill = false)
                                .width(supportingTextLoaderWidth)
                                .height(supportingTextLoaderHeight),
                            shape = RoundedCornerShape(4.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .weight(1f, fill = false)
                                .then(
                                    if (onSupportingTextClick != null && enabled && !showSupportingTextUnderline) {
                                        Modifier.clickable(
                                            onClick = onSupportingTextClick,
                                            indication = null, // No ripple for supporting text click
                                            interactionSource = remember { MutableInteractionSource() }
                                        )
                                    } else {
                                        Modifier
                                    }
                                )
                        ) {
                            PopMarqueeText(
                                text = supportingText,
                                style = supportingTextStyle,
                                color = if (enabled) supportingTextColor else TextColor.Disabled,
                                shouldAnimate = enableSupportingTextMarquee,
                                speedPxPerSecond = marqueeSpeedPxPerSecond,
                                delayMillis = marqueeDelayMillis,
                                loopCount = marqueeLoopCount,
                                maxLines = supportingTextMaxLines,
                                gradientWidth = marqueeGradientWidth,
                                showLeftGradient = showMarqueeLeftGradient,
                                showRightGradient = showMarqueeRightGradient,
                                showUnderline = showSupportingTextUnderline,
                                underlineStyle = supportingTextUnderlineStyle,
                                underlineColor = supportingTextUnderlineColor,
                                underlineDashLength = supportingTextUnderlineDashLength,
                                underlineGapLength = supportingTextUnderlineGapLength,
                                underlineDotDiameter = supportingTextUnderlineDotDiameter,
                                underlineDotGap = supportingTextUnderlineDotGap,
                                underlineThickness = supportingTextUnderlineThickness,
                                onClick = if (enabled && showSupportingTextUnderline) onSupportingTextClick else null
                            )
                        }
                    }

                    // Supporting text right icon
                    supportingTextRightIcon?.let { icon ->
                        Spacer(modifier = Modifier.width(iconGapDp))
                        PopVisualElement(
                            modifier = Modifier
                                .size(supportingTextIconSizeDp)
                                .then(
                                    if (onSupportingRightIconClick != null && enabled) {
                                        Modifier.clickable(
                                            onClick = onSupportingRightIconClick,
                                            indication = rememberRipple(bounded = false, radius = supportingTextIconSizeDp),
                                            interactionSource = remember { MutableInteractionSource() }
                                        )
                                    } else {
                                        Modifier
                                    }
                                ),
                            visualElement = icon,
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }

        // Trailing Content
        if (trailingContent != null || showTrailingContentLoader) {
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .alpha(
                        if (!enabled) {
                            0.5f
                        } else 1f
                    ),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (showTrailingContentLoader) {
                    PopShimmerBox(
                        modifier = Modifier
                            .width(trailingContentLoaderWidth)
                            .height(trailingContentLoaderHeight),
                        shape = trailingContentLoaderShape
                    )
                } else {
                    trailingContent?.invoke()
                }
            }
        }
    }

    // Divider
    if (showDivider) {
        PopDivider(
            color = dividerColor, modifier = Modifier
                .fillMaxWidth().height(0.5.dp)
                .padding(horizontal = horizontalPadding)
        )
    }
}

data class PopListItemAvatar(
    val type: AvatarType,
    val size: AvatarSize = AvatarSize.Medium,
    val modifier: Modifier = Modifier.padding(end = 16.dp),
    val contentDescription: String? = null,
    val topRightComposable: (@Composable () -> Unit)? = null
)