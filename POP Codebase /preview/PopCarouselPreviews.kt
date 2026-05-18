package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.PopCarousel
import com.pop.components.ds_components.PopCarouselAutoScrollConfig
import com.pop.components.ds_components.PopCarouselConfig
import com.pop.components.ds_components.PopCarouselHeaderConfig
import com.pop.components.ds_components.PopCarouselPaginationConfig
import com.pop.components.theme.BorderColor
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import com.pop.components.utils.popBackground
import kotlin.let

/**
 * Preview demonstrations for PopCarousel component.
 *
 * Shows various configurations based on Figma designs.
 */

@Preview(
    name = "PopCarousel - Count=1 (Single Item)",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    widthDp = 393,
    heightDp = 250
)
@Composable
private fun PopCarouselSingleItemPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PopSpacing.Spacing12)
    ) {
        PopCarousel(
            itemCount = 1,
            headerConfig = PopCarouselHeaderConfig(
                title = "Title"
            ),
            paginationConfig = PopCarouselPaginationConfig(
                showPagination = false // Single item doesn't need pagination
            ),
            carouselConfig = PopCarouselConfig(
                itemWidthFraction = 1f // Full width for single item
            )
        ) { _ ->
            SampleBannerItem(
                title = "This is a title and it can span 2 lines",
                showCta = true
            )
        }
    }
}

@Preview(
    name = "PopCarousel - Count=2",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    widthDp = 393,
    heightDp = 250
)
@Composable
private fun PopCarouselTwoItemsPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PopSpacing.Spacing12)
    ) {
        PopCarousel(
            itemCount = 2,
            headerConfig = PopCarouselHeaderConfig(
                title = "Title"
            ),
            paginationConfig = PopCarouselPaginationConfig(
                showPagination = true
            ),
            carouselConfig = PopCarouselConfig(
                itemWidthFraction = 0.86f
            )
        ) { page ->
            SampleBannerItem(
                title = "This is a title and it can span 2 lines",
                showCta = true
            )
        }
    }
}

@Preview(
    name = "PopCarousel - Count=3",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    widthDp = 393,
    heightDp = 250
)
@Composable
private fun PopCarouselThreeItemsPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PopSpacing.Spacing12)
    ) {
        PopCarousel(
            itemCount = 3,
            headerConfig = PopCarouselHeaderConfig(
                title = "Title"
            ),
            paginationConfig = PopCarouselPaginationConfig(
                showPagination = true
            )
        ) { page ->
            SampleBannerItem(
                title = "This is a title and it can span 2 lines",
                showCta = true
            )
        }
    }
}

@Preview(
    name = "PopCarousel - Count=Plenty (Many Items)",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    widthDp = 393,
    heightDp = 250
)
@Composable
private fun PopCarouselManyItemsPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PopSpacing.Spacing12)
    ) {
        PopCarousel(
            itemCount = 8,
            headerConfig = PopCarouselHeaderConfig(
                title = "Title"
            ),
            paginationConfig = PopCarouselPaginationConfig(
                showPagination = true,
                maxVisibleDots = 5
            )
        ) { page ->
            SampleBannerItem(
                title = "Banner ${page + 1}",
                showCta = true
            )
        }
    }
}

@Preview(
    name = "PopCarousel - With Body Text",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    widthDp = 393,
    heightDp = 280
)
@Composable
private fun PopCarouselWithBodyPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PopSpacing.Spacing12)
    ) {
        PopCarousel(
            itemCount = 4,
            headerConfig = PopCarouselHeaderConfig(
                title = "Featured Offers",
                body = "Check out our latest deals"
            ),
            paginationConfig = PopCarouselPaginationConfig(
                showPagination = true
            )
        ) { page ->
            SampleBannerItem(
                title = "Special Offer ${page + 1}",
                subtitle = "Limited time only",
                showCta = true
            )
        }
    }
}

@Preview(
    name = "PopCarousel - No Header",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    widthDp = 393,
    heightDp = 200
)
@Composable
private fun PopCarouselNoHeaderPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PopSpacing.Spacing12, vertical = PopSpacing.Spacing16)
    ) {
        PopCarousel(
            itemCount = 3,
            headerConfig = null, // No header
            paginationConfig = PopCarouselPaginationConfig(
                showPagination = true
            )
        ) { page ->
            SampleBannerItem(
                title = "Banner ${page + 1}",
                showCta = false
            )
        }
    }
}

@Preview(
    name = "PopCarousel - With Overflow Gradient",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    widthDp = 393,
    heightDp = 250
)
@Composable
private fun PopCarouselWithOverflowPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PopSpacing.Spacing12)
    ) {
        PopCarousel(
            itemCount = 5,
            headerConfig = PopCarouselHeaderConfig(
                title = "Title"
            ),
            carouselConfig = PopCarouselConfig(
                showOverflowGradient = true,
                overflowGradientWidth = 12.dp
            )
        ) { page ->
            SampleBannerItem(
                title = "Item ${page + 1}",
                showCta = true
            )
        }
    }
}

@Preview(
    name = "PopCarousel - With Auto-Scroll",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    widthDp = 393,
    heightDp = 250
)
@Composable
private fun PopCarouselAutoScrollPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PopSpacing.Spacing12)
    ) {
        PopCarousel(
            itemCount = 5,
            headerConfig = PopCarouselHeaderConfig(
                title = "Auto-Scroll Carousel",
                body = "Slides automatically every 3 seconds"
            ),
            paginationConfig = PopCarouselPaginationConfig(
                showPagination = true
            ),
            autoScrollConfig = PopCarouselAutoScrollConfig(
                enabled = true,
                delayMillis = 3000L,
                loop = true,
                showProgressIndicator = true,
                resumeAfterInteraction = true,
                resumeDelayMillis = 2000L
            )
        ) { page ->
            SampleBannerItem(
                title = "Slide ${page + 1}",
                subtitle = "Auto-advances to next slide",
                showCta = true
            )
        }
    }
}

@Preview(
    name = "PopCarousel - Auto-Scroll No Loop",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    widthDp = 393,
    heightDp = 250
)
@Composable
private fun PopCarouselAutoScrollNoLoopPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = PopSpacing.Spacing12)
    ) {
        PopCarousel(
            itemCount = 3,
            headerConfig = PopCarouselHeaderConfig(
                title = "Non-Looping Carousel",
                body = "Stops at the last slide"
            ),
            autoScrollConfig = PopCarouselAutoScrollConfig(
                enabled = true,
                delayMillis = 2000L,
                loop = false, // Stops at last page
                showProgressIndicator = true
            )
        ) { page ->
            SampleBannerItem(
                title = "Page ${page + 1} of 3",
                showCta = false
            )
        }
    }
}

@Preview(
    name = "PopCarousel - All Variants",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D,
    widthDp = 393,
    heightDp = 800
)
@Composable
private fun PopCarouselAllVariantsPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = PopSpacing.Spacing12, vertical = PopSpacing.Spacing16)
    ) {
        Text(
            text = "Count = 1 (Single)",
            style = PopTypography.labelXSmall,
            color = TextColor.Tertiary
        )
        Spacer(modifier = Modifier.height(8.dp))
        PopCarousel(
            itemCount = 1,
            headerConfig = PopCarouselHeaderConfig(title = "Title"),
            paginationConfig = PopCarouselPaginationConfig(showPagination = false),
            carouselConfig = PopCarouselConfig(itemWidthFraction = 1f)
        ) { SampleBannerItem(title = "Single Item", showCta = true) }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Count = 2",
            style = PopTypography.labelXSmall,
            color = TextColor.Tertiary
        )
        Spacer(modifier = Modifier.height(8.dp))
        PopCarousel(
            itemCount = 2,
            headerConfig = PopCarouselHeaderConfig(title = "Title")
        ) { page -> SampleBannerItem(title = "Item ${page + 1}", showCta = true) }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Count = 3",
            style = PopTypography.labelXSmall,
            color = TextColor.Tertiary
        )
        Spacer(modifier = Modifier.height(8.dp))
        PopCarousel(
            itemCount = 3,
            headerConfig = PopCarouselHeaderConfig(title = "Title")
        ) { page -> SampleBannerItem(title = "Item ${page + 1}", showCta = true) }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Count = Plenty (5+)",
            style = PopTypography.labelXSmall,
            color = TextColor.Tertiary
        )
        Spacer(modifier = Modifier.height(8.dp))
        PopCarousel(
            itemCount = 6,
            headerConfig = PopCarouselHeaderConfig(title = "Title"),
            paginationConfig = PopCarouselPaginationConfig(maxVisibleDots = 5)
        ) { page -> SampleBannerItem(title = "Item ${page + 1}", showCta = true) }
    }
}

/**
 * Sample banner item matching Figma design
 */
@Composable
private fun SampleBannerItem(
    title: String,
    subtitle: String? = null,
    showCta: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .popBackground(
                gradient = GradientPreset.CardBackground.gradient,
                shape = RoundedCornerShape(PopRadius.Medium)
            )
            .border(
                width = 0.5.dp,
                color = BorderColor.Secondary,
                shape = RoundedCornerShape(PopRadius.Medium)
            )
            .padding(PopSpacing.Spacing12)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing6)
            ) {
                Text(
                    text = title,
                    style = PopTypography.labelLarge,
                    color = TextColor.Primary,
                    maxLines = 2
                )

                subtitle?.let {
                    Text(
                        text = it,
                        style = PopTypography.labelXSmall,
                        color = TextColor.Tertiary
                    )
                }

                if (showCta) {
                    Spacer(modifier = Modifier.height(PopSpacing.Spacing6))
                    Box(
                        modifier = Modifier
                            .popBackground(
                                gradient = GradientPreset.ButtonBrandPrimaryLarge.gradient,
                                shape = RoundedCornerShape(999.dp)
                            )
                            .padding(horizontal = PopSpacing.Spacing8, vertical = PopSpacing.Spacing6)
                    ) {
                        Text(
                            text = "Label",
                            style = PopTypography.labelXSmall,
                            color = TextColor.Primary
                        )
                    }
                }
            }

            // Placeholder for icon/image
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = SurfaceColor.Tertiary,
                        shape = RoundedCornerShape(PopRadius.Small)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "%",
                    style = PopTypography.headingLarge,
                    color = TextColor.Brand
                )
            }
        }
    }
}

















