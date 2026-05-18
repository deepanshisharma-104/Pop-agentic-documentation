package com.pop.components.ds_components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pop.compose_components.R
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextColor
import com.pop.components.utils.popBackground
import com.pop.components.utils.toIndianFormat

/**
 * Size options for PopOfferHighlight
 * Based on Figma design: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=6877-3501
 */
enum class OfferHighlightSize {
    Small,   // 20dp height
    Medium,  // 24dp height
    Large    // 32dp height
}

/**
 * Background style options for PopOfferHighlight
 */
enum class OfferHighlightBackground {
    /** Blue filled background */
    Blue,
    /** Transparent/No background */
    Transparent
}

/**
 * Icon position options - where to place the percentage badge
 */
enum class OfferHighlightIconPosition {
    /** Percentage badge on left side of content */
    Left,
    /** Percentage badge on right side of content */
    Right
}

/**
 * Parsed result from an offer template string like "₹1000 + <img src=''></img> 500".
 * [amount] is 0 when the template contains only a bonus value.
 */
data class OfferHighlightData(val amount: Int, val bonus: Int)

private val IMG_TAG_REGEX = Regex("<img[^>]*></img>")

/**
 * Parses a config template string into structured amount/bonus values.
 *
 * Supported formats:
 * - "₹1000 + <img src=''></img> 500" -> amount=1000, bonus=500
 * - "<img src=''></img> 500"          -> amount=0, bonus=500
 * - "500"                              -> amount=0, bonus=500
 */
fun parseOfferTemplate(template: String): OfferHighlightData {
    val cleaned = template.replace(IMG_TAG_REGEX, "").trim()

    if (cleaned.contains("+")) {
        val parts = cleaned.split("+").map { it.trim() }
        val amountStr = parts.getOrNull(0)?.replace("₹", "")?.replace(",", "")?.trim()
        val bonusStr = parts.getOrNull(1)?.replace(",", "")?.trim()
        return OfferHighlightData(
            amount = amountStr?.toIntOrNull() ?: 0,
            bonus = bonusStr?.toIntOrNull() ?: 0
        )
    }

    val number = cleaned.replace("₹", "").replace(",", "").trim().toIntOrNull() ?: 0
    return OfferHighlightData(amount = 0, bonus = number)
}

/**
 * PopOfferHighlight - A reusable offer highlight/badge component that displays
 * an amount with bonus (coin icon) and optional percentage badge icon.
 *
 * Figma link: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=6877-3501
 *
 * @param amount The main amount to display (e.g., 1000 for ₹1,000). Ignored if showAmount is false.
 * @param bonus The bonus amount to display with coin icon (e.g., 234)
 * @param modifier Modifier to be applied to the component
 * @param size Size of the highlight (Small, Medium, Large)
 * @param background Background style (Blue filled or Transparent)
 * @param iconPosition Position of the percentage badge icon (Left or Right)
 * @param showPercentageBadge Whether to show the percentage badge icon
 * @param showAmount Whether to show the amount with currency symbol. If false, only bonus is displayed.
 * @param percentageBadgeRes Optional drawable resource for the percentage badge (placeholder if null)
 * @param coinIconRes Optional drawable resource for the coin icon (placeholder if null)
 * @param currencySymbol Currency symbol to display (default: "₹")
 * @param horizontalAlignment Content horizontal alignment (Center, Start, or End)
 */
@Composable
fun PopOfferHighlight(
    modifier: Modifier = Modifier,
    amount: Number = 0,
    bonus: Number,
    size: OfferHighlightSize = OfferHighlightSize.Medium,
    background: OfferHighlightBackground = OfferHighlightBackground.Blue,
    iconPosition: OfferHighlightIconPosition = OfferHighlightIconPosition.Right,
    showPercentageBadge: Boolean = true,
    showAmount: Boolean = true,
    @DrawableRes percentageBadgeRes: Int? = null,
    @DrawableRes coinIconRes: Int? = null,
    currencySymbol: String = "₹",
    horizontalAlignment: Arrangement.Horizontal = Arrangement.Center
) {
    // Size specifications based on Figma design
    val height: Dp = when (size) {
        OfferHighlightSize.Small -> 20.dp
        OfferHighlightSize.Medium -> 24.dp
        OfferHighlightSize.Large -> 32.dp
    }

    val horizontalPadding: Dp = when (size) {
        OfferHighlightSize.Small -> 6.dp
        OfferHighlightSize.Medium -> 8.dp
        OfferHighlightSize.Large -> 10.dp
    }

    val textStyle = when (size) {
        OfferHighlightSize.Small -> PopTypography.labelXSmall
        OfferHighlightSize.Medium -> PopTypography.labelSmall
        OfferHighlightSize.Large -> PopTypography.labelMedium
    }

    val coinIconSize: Dp = when (size) {
        OfferHighlightSize.Small -> 10.dp
        OfferHighlightSize.Medium -> 12.dp
        OfferHighlightSize.Large -> 16.dp
    }

    val percentageBadgeSize: Dp = when (size) {
        OfferHighlightSize.Small -> 14.dp
        OfferHighlightSize.Medium -> 18.dp
        OfferHighlightSize.Large -> 24.dp
    }

    val elementSpacing: Dp = when (size) {
        OfferHighlightSize.Small -> 2.dp
        OfferHighlightSize.Medium -> 3.dp
        OfferHighlightSize.Large -> 4.dp
    }

    // Subtle rounded rectangle (not pill-shaped)
    val cornerRadius: Dp = when (size) {
        OfferHighlightSize.Small -> 4.dp
        OfferHighlightSize.Medium -> 6.dp
        OfferHighlightSize.Large -> 8.dp
    }
    val shape = RoundedCornerShape(cornerRadius)

    val textColor = TextColor.Primary

    // Format amounts using Indian numbering system
    val formattedAmount = amount.toIndianFormat()
    val formattedBonus = bonus.toIndianFormat()

    Row(
        modifier = modifier
            .height(height)
            .then(
                if (background == OfferHighlightBackground.Blue) {
                    Modifier
                        .clip(shape)
                        .popBackground(
                            gradient = GradientPreset.PopOfferGradient.gradient,
                            shape = shape
                        )
                        .padding(horizontal = horizontalPadding)
                } else {
                    Modifier // No background/border for transparent variant
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalAlignment
    ) {
        // Percentage badge on left (if iconPosition is Left and showPercentageBadge is true)
        if (showPercentageBadge && iconPosition == OfferHighlightIconPosition.Left) {
            PercentageBadge(
                iconRes = percentageBadgeRes,
                size = percentageBadgeSize
            )
            Spacer(modifier = Modifier.width(elementSpacing))
        }

        // Amount text (only if showAmount is true)
        if (showAmount) {
            Text(
                text = "$currencySymbol$formattedAmount",
                style = textStyle,
                color = textColor
            )

            Spacer(modifier = Modifier.width(elementSpacing))

            // Plus sign
            Text(
                text = "+",
                style = textStyle,
                color = textColor
            )

            Spacer(modifier = Modifier.width(elementSpacing))
        }

        // Coin icon
        CoinIcon(
            iconRes = coinIconRes,
            size = coinIconSize
        )

        Spacer(modifier = Modifier.width(elementSpacing))

        // Bonus amount
        Text(
            text = formattedBonus,
            style = textStyle,
            color = textColor
        )

        // Percentage badge on right (if iconPosition is Right and showPercentageBadge is true)
        if (showPercentageBadge && iconPosition == OfferHighlightIconPosition.Right) {
            Spacer(modifier = Modifier.width(elementSpacing))
            PercentageBadge(
                iconRes = percentageBadgeRes,
                size = percentageBadgeSize
            )
        }
    }
}

/**
 * Coin icon component - displays the POPcoin icon
 */
@Composable
private fun CoinIcon(
    @DrawableRes iconRes: Int?,
    size: Dp
) {
    if (iconRes != null) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "Coin",
            modifier = Modifier.size(size),
            contentScale = ContentScale.Fit
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.pop_coin_glowing),
            contentDescription = "Coin",
            modifier = Modifier.size(size),
            contentScale = ContentScale.Fit
        )
    }
}

/**
 * Percentage badge component - blue badge with % symbol
 */
@Composable
private fun PercentageBadge(
    @DrawableRes iconRes: Int?,
    size: Dp
) {
    if (iconRes != null) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "Offer",
            modifier = Modifier.size(size),
            contentScale = ContentScale.Fit
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.img_offfer_star),
            contentDescription = "Offer",
            modifier = Modifier.size(size),
            contentScale = ContentScale.Fit
        )
    }
}

/**
 * String-template overload of [PopOfferHighlight].
 * Accepts a raw config string like "₹1000 + <img src=''></img> 500" and
 * renders it with the popcoin image replacing the img placeholder.
 *
 * @param templateString Raw offer template from app config
 */
@Composable
fun PopOfferHighlight(
    templateString: String,
    modifier: Modifier = Modifier,
    size: OfferHighlightSize = OfferHighlightSize.Medium,
    background: OfferHighlightBackground = OfferHighlightBackground.Blue,
    iconPosition: OfferHighlightIconPosition = OfferHighlightIconPosition.Right,
    showPercentageBadge: Boolean = true,
    @DrawableRes percentageBadgeRes: Int? = null,
    @DrawableRes coinIconRes: Int? = null,
    horizontalAlignment: Arrangement.Horizontal = Arrangement.Center
) {
    val data = parseOfferTemplate(templateString)
    if (data.amount <= 0 && data.bonus <= 0) return
    PopOfferHighlight(
        modifier = modifier,
        amount = data.amount,
        bonus = data.bonus,
        size = size,
        background = background,
        iconPosition = OfferHighlightIconPosition.Left,
        showPercentageBadge = showPercentageBadge,
        showAmount = data.amount > 0,
        percentageBadgeRes = percentageBadgeRes,
        coinIconRes = coinIconRes,
        horizontalAlignment = horizontalAlignment
    )
}

// ==================== PREVIEWS ====================

@Preview(name = "Large - Blue - Icon Right", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PreviewLargeBlueIconRight() {
    PopOfferHighlight(
        amount = 1000,
        bonus = 234,
        size = OfferHighlightSize.Large,
        background = OfferHighlightBackground.Blue,
        iconPosition = OfferHighlightIconPosition.Right
    )
}

@Preview(name = "Large - Blue - Icon Left", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PreviewLargeBlueIconLeft() {
    PopOfferHighlight(
        amount = 1000,
        bonus = 234,
        size = OfferHighlightSize.Large,
        background = OfferHighlightBackground.Blue,
        iconPosition = OfferHighlightIconPosition.Left
    )
}

@Preview(name = "Large - Transparent - Icon Right", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PreviewLargeTransparentIconRight() {
    PopOfferHighlight(
        amount = 1000,
        bonus = 234,
        size = OfferHighlightSize.Large,
        background = OfferHighlightBackground.Transparent,
        iconPosition = OfferHighlightIconPosition.Right
    )
}

@Preview(name = "Large - Transparent - Icon Left", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PreviewLargeTransparentIconLeft() {
    PopOfferHighlight(
        amount = 1000,
        bonus = 234,
        size = OfferHighlightSize.Large,
        background = OfferHighlightBackground.Transparent,
        iconPosition = OfferHighlightIconPosition.Left
    )
}

@Preview(name = "Medium - Blue - Icon Right", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PreviewMediumBlueIconRight() {
    PopOfferHighlight(
        amount = 1000,
        bonus = 234,
        size = OfferHighlightSize.Medium,
        background = OfferHighlightBackground.Blue,
        iconPosition = OfferHighlightIconPosition.Right
    )
}

@Preview(name = "Medium - Blue - Icon Left", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PreviewMediumBlueIconLeft() {
    PopOfferHighlight(
        amount = 1000,
        bonus = 234,
        size = OfferHighlightSize.Medium,
        background = OfferHighlightBackground.Blue,
        iconPosition = OfferHighlightIconPosition.Left
    )
}

@Preview(name = "Small - Blue - Icon Right", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PreviewSmallBlueIconRight() {
    PopOfferHighlight(
        amount = 1000,
        bonus = 234,
        size = OfferHighlightSize.Small,
        background = OfferHighlightBackground.Blue,
        iconPosition = OfferHighlightIconPosition.Right
    )
}

@Preview(name = "Small - Transparent - Icon Right", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PreviewSmallTransparentIconRight() {
    PopOfferHighlight(
        amount = 1000,
        bonus = 234,
        size = OfferHighlightSize.Small,
        background = OfferHighlightBackground.Transparent,
        iconPosition = OfferHighlightIconPosition.Right
    )
}

@Preview(name = "Bonus Only - Blue", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PreviewBonusOnlyBlue() {
    PopOfferHighlight(
        bonus = 234,
        size = OfferHighlightSize.Medium,
        background = OfferHighlightBackground.Blue,
        showPercentageBadge = true,
        showAmount = false
    )
}

@Preview(name = "Bonus Only - Small", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PreviewBonusOnlySmall() {
    PopOfferHighlight(
        bonus = 50,
        size = OfferHighlightSize.Small,
        background = OfferHighlightBackground.Blue,
        showPercentageBadge = false,
        showAmount = false
    )
}

@Preview(name = "All Sizes - Blue - Icon Right", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PreviewAllSizesBlueIconRight() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        PopOfferHighlight(
            amount = 1000, bonus = 234,
            size = OfferHighlightSize.Large,
            background = OfferHighlightBackground.Blue,
            iconPosition = OfferHighlightIconPosition.Right
        )
        PopOfferHighlight(
            amount = 1000, bonus = 234,
            size = OfferHighlightSize.Medium,
            background = OfferHighlightBackground.Blue,
            iconPosition = OfferHighlightIconPosition.Right
        )
        PopOfferHighlight(
            amount = 1000, bonus = 234,
            size = OfferHighlightSize.Small,
            background = OfferHighlightBackground.Blue,
            iconPosition = OfferHighlightIconPosition.Right
        )
    }
}

@Preview(name = "All Sizes - Transparent - Icon Left", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
private fun PreviewAllSizesTransparentIconLeft() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        PopOfferHighlight(
            amount = 1000, bonus = 234,
            size = OfferHighlightSize.Large,
            background = OfferHighlightBackground.Transparent,
            iconPosition = OfferHighlightIconPosition.Left
        )
        PopOfferHighlight(
            amount = 1000, bonus = 234,
            size = OfferHighlightSize.Medium,
            background = OfferHighlightBackground.Transparent,
            iconPosition = OfferHighlightIconPosition.Left
        )
        PopOfferHighlight(
            amount = 1000, bonus = 234,
            size = OfferHighlightSize.Small,
            background = OfferHighlightBackground.Transparent,
            iconPosition = OfferHighlightIconPosition.Left
        )
    }
}
