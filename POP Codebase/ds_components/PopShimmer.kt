package com.pop.components.ds_components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.pop.components.ds_components.SquircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopSpacing

/**
 * PopShimmer - Design System Shimmer Loader Component
 *
 * Based on Figma Design System: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=8220-35278
 *
 * Provides various shimmer loader types for inline loading states:
 * - People: Circular avatars (sizes: 28, 36, 56, 64)
 * - Brand: Square brand logos (sizes: 16, 28, 36, 56, 64)
 * - List: List item placeholders (sizes: Small, Med, Large)
 * - Payment list: Payment list items (sizes: Med, Large)
 * - Payment list - Left: Left-aligned payment items (size: Small)
 * - Payment list - Right: Right-aligned payment items (size: Small)
 * - Body: Body text placeholders (size: Body)
 * - Title: Title text placeholders (sizes: Med, Large)
 * - Table item: Table row placeholders (size: Large)
 * - Card: Card placeholders (size: Med)
 * - CartContent: Full cart screen layout (address card, offer row, cart items, wishlist, summary)
 * - AddressItem: Address list item (label + postcode line, address line, optional options icon)
 *
 * @param type The type of loader (People, Brand, List, Payment list, etc.)
 * @param size The size variant (Small, Med, Large, or specific pixel sizes)
 * @param modifier Optional modifier for the loader
 */
@Composable
fun PopShimmer(
    modifier: Modifier = Modifier,
    type: LoaderType = LoaderType.People,
    size: LoaderSize = LoaderSize.Small28,
) {
    when (type) {
        LoaderType.People -> {
            PeopleLoader(size = size, modifier = modifier)
        }
        LoaderType.Brand -> {
            BrandLoader(size = size, modifier = modifier)
        }
        LoaderType.List -> {
            ListLoader(size = size, modifier = modifier)
        }
        LoaderType.PaymentList -> {
            PaymentListLoader(size = size, modifier = modifier)
        }
        LoaderType.PaymentListLeft -> {
            PaymentListLeftLoader(size = size, modifier = modifier)
        }
        LoaderType.PaymentListRight -> {
            PaymentListRightLoader(size = size, modifier = modifier)
        }
        LoaderType.Body -> {
            BodyLoader(size = size, modifier = modifier)
        }
        LoaderType.Title -> {
            TitleLoader(size = size, modifier = modifier)
        }
        LoaderType.TableItem -> {
            TableItemLoader(size = size, modifier = modifier)
        }
        LoaderType.Card -> {
            CardLoader(size = size, modifier = modifier)
        }
        LoaderType.CartContent -> {
            CartContentLoader(modifier = modifier)
        }
        LoaderType.AddressItem -> {
            AddressItemLoader(modifier = modifier)
        }
        LoaderType.CLP -> {
            ClpLoader(modifier = modifier)
        }
        LoaderType.CashbackLedger -> {
            CashbackLedgerLoader(modifier = modifier)
        }
    }
}

/**
 * Loader types matching Figma design system
 */
enum class LoaderType {
    People,
    Brand,
    List,
    PaymentList,
    PaymentListLeft,
    PaymentListRight,
    Body,
    Title,
    TableItem,
    Card,
    /** Full cart screen layout: address card, offer row, cart items, wishlist, price summary */
    CartContent,
    /** Address list item: label + postcode line, address line, optional options icon */
    AddressItem,
    /** CLP (Category Listing Page): section title row, horizontal category circles with labels, horizontal product cards */
    CLP,
    /** Cashback Ledger header: claimable cashback capsule, inner panel, amount, claim button, lifetime earned / claimed cashback */
    CashbackLedger
}

/**
 * Loader sizes matching Figma design system
 */
enum class LoaderSize {
    Small,      // For List, Payment list variants
    Med,        // For List, Payment list, Title, Card
    Large,      // For List, Payment list, Title, Table item
    Small28,    // For People (default)
    Small36,    // For People, Brand
    Small56,    // For People, Brand
    Small64,    // For People, Brand
    Small16,    // For Brand
    Body        // For Body text
}

/**
 * People loader - Circular avatar placeholders
 */
@Composable
private fun PeopleLoader(
    size: LoaderSize,
    modifier: Modifier
) {
    val sizeDp = when (size) {
        LoaderSize.Small28 -> 28.dp
        LoaderSize.Small36 -> 36.dp
        LoaderSize.Small56 -> 56.dp
        LoaderSize.Small64 -> 64.dp
        else -> 28.dp // Default
    }

    ShimmerBox(
        modifier = modifier.size(sizeDp),
        shape = CircleShape
    )
}

/**
 * Brand loader - Square brand logo placeholders
 */
@Composable
private fun BrandLoader(
    size: LoaderSize,
    modifier: Modifier
) {
    val sizeDp = when (size) {
        LoaderSize.Small16 -> 16.dp
        LoaderSize.Small28 -> 28.dp
        LoaderSize.Small36 -> 36.dp
        LoaderSize.Small56 -> 56.dp
        LoaderSize.Small64 -> 64.dp
        else -> 28.dp // Default
    }

    ShimmerBox(
        modifier = modifier.size(sizeDp),
        shape = SquircleShape()
    )
}

/**
 * List loader - List item placeholders
 */
@Composable
private fun ListLoader(
    size: LoaderSize,
    modifier: Modifier
) {
    when (size) {
        LoaderSize.Small -> {
            // Small list: 20px height, 115px width with circle on left
            Row(modifier = modifier
                .width(115.dp)
                .height(20.dp)) {
                ShimmerBox(
                    modifier = Modifier.size(20.dp),
                    shape = CircleShape
                )
                Spacer(modifier = Modifier.width(PopSpacing.Spacing8))
                ShimmerBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
            }
        }

        LoaderSize.Med -> {
            // Med list: 38px height, 369px width with circle on left
            Row(modifier = modifier
                .width(369.dp)
                .height(38.dp)) {
                ShimmerBox(
                    modifier = Modifier.size(38.dp),
                    shape = CircleShape
                )
                Spacer(modifier = Modifier.width(PopSpacing.Spacing12))
                ShimmerBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
            }
        }

        LoaderSize.Large -> {
            // Large list: 44px height, 369px width with circle on left
            Row(modifier = modifier
                .width(369.dp)
                .height(44.dp)) {
                ShimmerBox(
                    modifier = Modifier.size(44.dp),
                    shape = CircleShape
                )
                Spacer(modifier = Modifier.width(PopSpacing.Spacing12))
                ShimmerBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
            }
        }

        else -> {
            // Default to Small
            Row(modifier = modifier
                .width(115.dp)
                .height(20.dp)) {
                ShimmerBox(
                    modifier = Modifier.size(20.dp),
                    shape = CircleShape
                )
                Spacer(modifier = Modifier.width(PopSpacing.Spacing8))
                ShimmerBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(20.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
            }
        }
    }
}

/**
 * Payment list loader - Payment list item placeholders
 * Has circle at start, rounded rectangle in middle, and vertical rounded rectangle at end
 */
@Composable
private fun PaymentListLoader(
    size: LoaderSize,
    modifier: Modifier
) {
    when (size) {
        LoaderSize.Med -> {
            // Med payment list: 38px height, 369px width
            // Circle (left) + Rounded rectangle (middle) + Vertical rounded rectangle (right)
            Row(modifier = modifier
                .width(369.dp)
                .height(38.dp)) {
                // Left: Circle
                ShimmerBox(
                    modifier = Modifier.size(38.dp),
                    shape = CircleShape
                )
                Spacer(modifier = Modifier.width(PopSpacing.Spacing12)) // 12dp spacing between 1st and 2nd
                // Middle: Rounded rectangle (flexible width)
                ShimmerBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
                Spacer(modifier = Modifier.width(40.dp)) // 40dp spacing between 2nd and 3rd
                // Right: Vertical rounded rectangle (oval) - 54dp wide
                ShimmerBox(
                    modifier = Modifier
                        .width(54.dp)
                        .height(38.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
            }
        }

        LoaderSize.Large -> {
            // Large payment list: 44px height, 369px width
            // Circle (left) + Rounded rectangle (middle) + Vertical rounded rectangle (right)
            Row(modifier = modifier
                .width(369.dp)
                .height(44.dp)) {
                // Left: Circle
                ShimmerBox(
                    modifier = Modifier.size(44.dp),
                    shape = CircleShape
                )
                Spacer(modifier = Modifier.width(PopSpacing.Spacing12)) // 12dp spacing between 1st and 2nd
                // Middle: Rounded rectangle (flexible width)
                ShimmerBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
                Spacer(modifier = Modifier.width(32.dp)) // 32dp spacing between 2nd and 3rd
                // Right: Vertical rounded rectangle (oval) - 54dp wide
                ShimmerBox(
                    modifier = Modifier
                        .width(54.dp)
                        .height(44.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
            }
        }

        else -> {
            // Default to Med
            Row(modifier = modifier
                .width(369.dp)
                .height(38.dp)) {
                ShimmerBox(
                    modifier = Modifier.size(38.dp),
                    shape = CircleShape
                )
                Spacer(modifier = Modifier.width(PopSpacing.Spacing12)) // 12dp spacing between 1st and 2nd
                ShimmerBox(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
                Spacer(modifier = Modifier.width(40.dp)) // 40dp spacing between 2nd and 3rd
                ShimmerBox(
                    modifier = Modifier
                        .width(54.dp)
                        .height(38.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
            }
        }
    }
}

/**
 * Payment list - Left loader - Left-aligned payment items
 * Has circle on left and rounded rectangle on right
 */
@Composable
private fun PaymentListLeftLoader(
    size: LoaderSize,
    modifier: Modifier
) {
    // Small payment list - Left: 20px height, 115px width
    // Circle (left) + Rounded rectangle (right)
    Row(modifier = modifier
        .width(115.dp)
        .height(20.dp)) {
        // Left: Circle
        ShimmerBox(
            modifier = Modifier.size(20.dp),
            shape = CircleShape
        )
        Spacer(modifier = Modifier.width(PopSpacing.Spacing8))
        // Right: Rounded rectangle (flexible width)
        ShimmerBox(
            modifier = Modifier
                .weight(1f)
                .height(20.dp),
            shape = RoundedCornerShape(PopRadius.XLLarge)
        )
    }
}

/**
 * Payment list - Right loader - Right-aligned payment items
 * Has rounded rectangle on left and circle on right
 */
@Composable
private fun PaymentListRightLoader(
    size: LoaderSize,
    modifier: Modifier
) {
    // Small payment list - Right: 20px height, 115px width
    // Rounded rectangle (left) + Circle (right)
    Row(modifier = modifier
        .width(115.dp)
        .height(20.dp)) {
        // Left: Rounded rectangle (flexible width)
        ShimmerBox(
            modifier = Modifier
                .weight(1f)
                .height(20.dp),
            shape = RoundedCornerShape(PopRadius.XLLarge)
        )
        Spacer(modifier = Modifier.width(PopSpacing.Spacing8))
        // Right: Circle
        ShimmerBox(
            modifier = Modifier.size(20.dp),
            shape = CircleShape
        )
    }
}

/**
 * Body loader - Body text placeholders
 */
@Composable
private fun BodyLoader(
    size: LoaderSize,
    modifier: Modifier
) {
    // Body: 16px height, 91px width
    ShimmerBox(
        modifier = modifier
            .width(91.dp)
            .height(16.dp),
        shape = RoundedCornerShape(PopRadius.XLLarge)
    )
}

/**
 * Title loader - Title text placeholders
 */
@Composable
private fun TitleLoader(
    size: LoaderSize,
    modifier: Modifier
) {
    when (size) {
        LoaderSize.Med -> {
            // Med title: 20px height, 91px width
            ShimmerBox(
                modifier = modifier
                    .width(91.dp)
                    .height(20.dp),
                shape = RoundedCornerShape(PopRadius.XLLarge)
            )
        }

        LoaderSize.Large -> {
            // Large title: 24px height, 91px width
            ShimmerBox(
                modifier = modifier
                    .width(91.dp)
                    .height(24.dp),
                shape = RoundedCornerShape(PopRadius.XLLarge)
            )
        }

        else -> {
            // Default to Med
            ShimmerBox(
                modifier = modifier
                    .width(91.dp)
                    .height(20.dp),
                shape = RoundedCornerShape(PopRadius.XLLarge)
            )
        }
    }
}

/**
 * Table item loader - Table row placeholders
 */
@Composable
private fun TableItemLoader(
    size: LoaderSize,
    modifier: Modifier
) {
    // Table item: 42px height, 91px width
    ShimmerBox(
        modifier = modifier
            .width(91.dp)
            .height(42.dp),
        shape = RoundedCornerShape(PopRadius.XLLarge)
    )
}

/**
 * Card loader - Card placeholders
 */
@Composable
private fun CardLoader(
    size: LoaderSize,
    modifier: Modifier
) {
    // Card: 128px height, 369px width
    ShimmerBox(
        modifier = modifier
            .width(369.dp)
            .height(128.dp),
        shape = RoundedCornerShape(PopRadius.Medium)
    )
}

/**
 * Cart content loader - Matches [CartContent] layout in CartComposables:
 * Delivery address card, savings/offer row, cart item rows (image + details), wishlist row, price summary.
 */
@Composable
private fun CartContentLoader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Delivery address card - full width card with label + address lines
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(PopRadius.Medium)
        )

        // Savings / offer toggle row
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(PopRadius.Medium)
        )

        // Cart items container (2 item rows) - each row: 78x108 image + details
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Cart item row 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ShimmerBox(
                    modifier = Modifier.size(78.dp, 108.dp),
                    shape = RoundedCornerShape(PopRadius.Medium)
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(14.dp),
                        shape = RoundedCornerShape(PopRadius.XLLarge)
                    )
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .height(14.dp),
                        shape = RoundedCornerShape(PopRadius.XLLarge)
                    )
                    ShimmerBox(
                        modifier = Modifier
                            .width(72.dp)
                            .height(12.dp),
                        shape = RoundedCornerShape(PopRadius.XLLarge)
                    )
                }
            }

            // Cart item row 2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ShimmerBox(
                    modifier = Modifier.size(78.dp, 108.dp),
                    shape = RoundedCornerShape(PopRadius.Medium)
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(14.dp),
                        shape = RoundedCornerShape(PopRadius.XLLarge)
                    )
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .height(14.dp),
                        shape = RoundedCornerShape(PopRadius.XLLarge)
                    )
                    ShimmerBox(
                        modifier = Modifier
                            .width(64.dp)
                            .height(12.dp),
                        shape = RoundedCornerShape(PopRadius.XLLarge)
                    )
                }
            }
        }

        // Wishlist section row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing12)
        ) {
            ShimmerBox(
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                shape = RoundedCornerShape(PopRadius.XLLarge)
            )
            ShimmerBox(
                modifier = Modifier
                    .width(120.dp)
                    .height(36.dp),
                shape = RoundedCornerShape(PopRadius.Medium)
            )
        }

        // Price summary section
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp),
                shape = RoundedCornerShape(PopRadius.XLLarge)
            )
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(16.dp),
                shape = RoundedCornerShape(PopRadius.XLLarge)
            )
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(16.dp),
                shape = RoundedCornerShape(PopRadius.XLLarge)
            )
            ShimmerBox(
                modifier = Modifier
                    .width(80.dp)
                    .height(18.dp),
                shape = RoundedCornerShape(PopRadius.XLLarge)
            )
        }

        Spacer(modifier = Modifier.height(120.dp))
    }
}

/**
 * Address item loader - Matches [AddressItem] layout: card with title line, address line, optional icon.
 * Use in address selection list when loading.
 */
@Composable
private fun AddressItemLoader(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .height(16.dp),
                shape = RoundedCornerShape(PopRadius.XLLarge)
            )
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(14.dp),
                shape = RoundedCornerShape(PopRadius.XLLarge)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        ShimmerBox(
            modifier = Modifier.size(24.dp),
            shape = RoundedCornerShape(PopRadius.Small)
        )
    }
}

/**
 * CLP (Category Listing Page) loader - Matches CLP layout:
 * Section title row (title + "Updates >"), horizontal category circles with labels, horizontal product cards.
 * Layout reference: section title, circular category selector, product cards with image, rating, title, price, chip.
 * Repeats two section blocks to fill the screen.
 */
@Composable
private fun ClpLoader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        ShimmerBox(
            modifier = Modifier.fillMaxWidth().height(400.dp),
            shape = RoundedCornerShape(16.dp)
        )
        ShimmerBox(
            modifier = Modifier.fillMaxWidth().height(100.dp).padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp)
        )
        repeat(2) {
            ClpSectionShimmer()
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun ClpSectionShimmer() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // Horizontal category row: circular items with label below
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ShimmerBox(
                modifier = Modifier
                    .width(120.dp)
                    .height(20.dp),
                shape = RoundedCornerShape(PopRadius.XLLarge)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                repeat(4) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ShimmerBox(
                            modifier = Modifier.size(80.dp),
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }
            }
        }


        // Horizontal product cards row
//        LazyRow(
//            horizontalArrangement = Arrangement.spacedBy(12.dp),
//            userScrollEnabled = false
//        ) {
//            items(3) {
//                ClpProductCardShimmer()
//            }
//        }
    }
}

/**
 * Cashback Ledger loader - Matches Cashback Ledger header layout from design:
 * Central capsule with inner panel (Claimable cashback + icon, amount, Claim now button),
 * bottom row (Lifetime earned / Claimed cashback with amounts).
 */
@Composable
private fun CashbackLedgerLoader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Main outer capsule (pill-shaped) + inner panel + content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)
                .height(280.dp)
        ) {
            // Outer capsule (pill)
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(280.dp),
                shape = RoundedCornerShape(140.dp)
            )
        }
        // Bottom row: Lifetime earned (left) | Claimed cashback (right)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.Start
            ) {
                ShimmerBox(
                    modifier = Modifier
                        .width(100.dp)
                        .height(12.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
                ShimmerBox(
                    modifier = Modifier
                        .width(48.dp)
                        .height(18.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp),
                horizontalAlignment = Alignment.End
            ) {
                ShimmerBox(
                    modifier = Modifier
                        .width(110.dp)
                        .height(12.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
                ShimmerBox(
                    modifier = Modifier
                        .width(48.dp)
                        .height(18.dp),
                    shape = RoundedCornerShape(PopRadius.XLLarge)
                )
            }
        }
    }
}

@Preview
@Composable
fun CashbackLoaderPreview(){
    CashbackLedgerLoader()
}

/**
 * Single product card placeholder for CLP shimmer: image (with badge), rating, title (2 lines), price, chip.
 */
@Composable
private fun ClpProductCardShimmer() {
    Column(
        modifier = Modifier.width(152.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Image area with small badge at top-left
        Box(modifier = Modifier.fillMaxWidth()) {
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp),
                shape = RoundedCornerShape(PopRadius.Medium)
            )
            ShimmerBox(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
                    .width(56.dp)
                    .height(20.dp),
                shape = RoundedCornerShape(PopRadius.Small)
            )
        }
        // Rating line
        ShimmerBox(
            modifier = Modifier
                .width(48.dp)
                .height(12.dp),
            shape = RoundedCornerShape(PopRadius.XLLarge)
        )
        // Product name (2 lines)
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(14.dp),
            shape = RoundedCornerShape(PopRadius.XLLarge)
        )
        ShimmerBox(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(14.dp),
            shape = RoundedCornerShape(PopRadius.XLLarge)
        )
        // Price lines
        ShimmerBox(
            modifier = Modifier
                .width(56.dp)
                .height(14.dp),
            shape = RoundedCornerShape(PopRadius.XLLarge)
        )
        ShimmerBox(
            modifier = Modifier
                .width(72.dp)
                .height(12.dp),
            shape = RoundedCornerShape(PopRadius.XLLarge)
        )
        // Bottom chip
        ShimmerBox(
            modifier = Modifier
                .width(100.dp)
                .height(28.dp),
            shape = RoundedCornerShape(PopRadius.Medium)
        )
    }
}

/**
 * Base shimmer box with animated gradient effect
 *
 * Creates a shimmer effect using a gradient brush that animates across the content.
 * Uses design system colors for the shimmer effect.
 */
@Composable
private fun ShimmerBox(
    modifier: Modifier,
    shape: Shape
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    var size by remember { mutableStateOf(Size.Zero) }

    // Animate the gradient offset from -1 to 1 (left to right)
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerOffset"
    )

    // Shimmer colors: base color and highlight
    // Using grey colors from design system for shimmer effect
    val baseColor = PopColor.Grey.Grey800 // Dark base
    val highlightColor = PopColor.Grey.Grey600 // Lighter highlight

    // Calculate gradient width (3x the content width for smooth sweep)
    val gradientWidth = if (size.width > 0) size.width * 3 else 1000f
    val gradientCenter = shimmerOffset * gradientWidth

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                size = coordinates.size.toSize()
            }
            .clip(shape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        baseColor,
                        baseColor,
                        highlightColor,
                        highlightColor,
                        baseColor,
                        baseColor
                    ),
                    start = Offset(
                        x = gradientCenter - gradientWidth / 3,
                        y = 0f
                    ),
                    end = Offset(
                        x = gradientCenter + gradientWidth / 3,
                        y = 0f
                    )
                )
            )
    )
}

@Preview
@Composable
fun CLPLoadingPreview(){
    ClpLoader()
}

