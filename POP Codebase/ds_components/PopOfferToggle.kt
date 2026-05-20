package com.pop.components.ds_components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.pop.components.compose_components.TicketShape
import com.pop.components.theme.BorderColor
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopStroke
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import com.pop.components.utils.popBackground

/**
 * Offer type for PopOfferToggle component
 */
enum class OfferType {
    /** Offer type with blue gradient */
    Offer,
    /** Cash type with green gradient */
    Cash,
    /** Pay in 3 type with purple gradient */
    PayIn3,
    /** Pop coin type with orange gradient */
    PopCoin
}

/**
 * PopOfferToggle - A reusable offer toggle component with gradient backgrounds,
 * icons, text content, info button, and toggle switch.
 *
 * Figma link: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=8095-32712
 *
 * @param title The main title text
 * @param bodyText The body/subtitle text (e.g., amount with coin icon)
 * @param type The offer type (Offer, Cash, Pay in 3, Pop coin)
 * @param checked Whether the toggle is checked/on
 * @param onCheckedChange Callback when toggle state changes
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether the component is enabled (affects opacity and interactions)
 * @param onInfoClick Optional callback when info icon is clicked
 * @param iconRes Optional drawable resource for the left icon (placeholder if null)
 * @param infoIconRes Optional drawable resource for the info icon (placeholder if null)
 */
@Composable
fun PopOfferToggle(
    title: String,
    bodyText: String,
    type: OfferType,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onInfoClick: (() -> Unit)? = null,
    @DrawableRes iconRes: Int? = null,
    @DrawableRes infoIconRes: Int? = null,
    bodyAnnotatedText: AnnotatedString? = null,
    bodyInlineContent: Map<String, InlineTextContent>? = null
) {
    val gradient = when {
        !enabled -> when (type) {
            OfferType.Offer -> GradientPreset.OfferGradientDisabled.gradient
            OfferType.Cash -> GradientPreset.CashGradientDisabled.gradient
            OfferType.PayIn3 -> GradientPreset.PayIn3GradientDisabled.gradient
            OfferType.PopCoin -> GradientPreset.PopCoinGradientDisabled.gradient
        }
        else -> when (type) {
            OfferType.Offer -> GradientPreset.OfferGradient.gradient
            OfferType.Cash -> GradientPreset.CashGradient.gradient
            OfferType.PayIn3 -> GradientPreset.PayIn3Gradient.gradient
            OfferType.PopCoin -> GradientPreset.PopCoinGradient.gradient
        }
    }

    val borderColor = if (enabled) BorderColor.Secondary else BorderColor.Primary
    val ticketShape = TicketShape(
        cornerRadius = PopRadius.XLarge,
        cutoutRadius = 10.dp,
        topCutoutOffset = 10.dp,
        bottomCutoutOffset = 10.dp,
        rightEdgeOffset = 94.dp,
        cornerSmoothing = 0.9f
    )

    // Use the same shape instance for clip, background, and border to ensure consistent radius
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .then(
                Modifier
                    .clip(ticketShape)
                    .border(width = PopStroke.Default, color = borderColor, shape = ticketShape)
            )
    ) {
        // Gradient overlay on left side (187dp width)
        // Use RectangleShape - the parent Box is already clipped with ticketShape,
        // so this will be automatically clipped to match the left corners exactly
        Box(
            modifier = Modifier
                .width(187.dp)
                .height(72.dp)
                .popBackground(gradient = gradient, shape = RectangleShape)
        )

        // Content
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(start = 6.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left content
            Row(
                modifier = Modifier
                    .weight(1f)
                    .then(if (!enabled) Modifier else Modifier),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Icon placeholder
                Box(
                    modifier = Modifier.size(44.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (iconRes != null) {
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(44.dp),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        // Placeholder box
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(
                                    color = Color.White.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(PopRadius.Small)
                                )
                        )
                    }
                }

                // Text content
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = title,
                        style = PopTypography.labelMedium,
                        color = if (enabled) TextColor.Primary else TextColor.Primary.copy(alpha = 0.4f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (bodyAnnotatedText != null && bodyInlineContent != null) {
                        Text(
                            text = bodyAnnotatedText,
                            inlineContent = bodyInlineContent,
                            style = PopTypography.labelXSmall,
                            color = if (enabled) TextColor.Secondary else TextColor.Secondary.copy(alpha = 0.4f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        Text(
                            text = bodyText,
                            style = PopTypography.labelXSmall,
                            color = if (enabled) TextColor.Secondary else TextColor.Secondary.copy(alpha = 0.4f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            // Right content
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Info icon button
                if (onInfoClick != null) {
                    IconButton(
                        onClick = onInfoClick,
                        enabled = enabled,
                        modifier = Modifier.size(24.dp)
                    ) {
                        if (infoIconRes != null) {
                            Image(
                                painter = painterResource(id = infoIconRes),
                                contentDescription = "Info",
                                modifier = Modifier.size(10.667.dp),
                                contentScale = ContentScale.Fit
                            )
                        } else {
                            // Placeholder
                            Box(
                                modifier = Modifier
                                    .size(10.667.dp)
                                    .background(
                                        color = TextColor.Secondary.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(2.dp)
                                    )
                            )
                        }
                    }
                }

                // Toggle switch
                if(enabled)
                    PopToggle(
                        checked = checked,
                        onCheckedChange = onCheckedChange,
                        enabled = enabled,
                        size = PopToggleSize.Medium,
                        modifier = Modifier
                    )
            }
        }
    }
}

