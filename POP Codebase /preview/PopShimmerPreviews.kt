package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.LoaderSize
import com.pop.components.ds_components.LoaderType
import com.pop.components.ds_components.PopShimmer
import com.pop.components.theme.PopSpacing

/**
 * Preview functions for PopShimmer component
 * 
 * This file contains all preview functions with various permutations
 * and combinations to showcase all features of the PopShimmer component.
 */

// ============================================================================
// People Loader Previews
// ============================================================================

@Preview(
    name = "People - Small 28",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerPeopleSmall28() {
    PopShimmer(
        type = LoaderType.People,
        size = LoaderSize.Small28
    )
}

@Preview(
    name = "People - Small 36",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerPeopleSmall36() {
    PopShimmer(
        type = LoaderType.People,
        size = LoaderSize.Small36
    )
}

@Preview(
    name = "People - Small 56",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerPeopleSmall56() {
    PopShimmer(
        type = LoaderType.People,
        size = LoaderSize.Small56
    )
}

@Preview(
    name = "People - Small 64",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerPeopleSmall64() {
    PopShimmer(
        type = LoaderType.People,
        size = LoaderSize.Small64
    )
}

// ============================================================================
// Brand Loader Previews
// ============================================================================

@Preview(
    name = "Brand - Small 16",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerBrandSmall16() {
    PopShimmer(
        type = LoaderType.Brand,
        size = LoaderSize.Small16
    )
}

@Preview(
    name = "Brand - Small 28",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerBrandSmall28() {
    PopShimmer(
        type = LoaderType.Brand,
        size = LoaderSize.Small28
    )
}

@Preview(
    name = "Brand - Small 36",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerBrandSmall36() {
    PopShimmer(
        type = LoaderType.Brand,
        size = LoaderSize.Small36
    )
}

@Preview(
    name = "Brand - Small 56",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerBrandSmall56() {
    PopShimmer(
        type = LoaderType.Brand,
        size = LoaderSize.Small56
    )
}

@Preview(
    name = "Brand - Small 64",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerBrandSmall64() {
    PopShimmer(
        type = LoaderType.Brand,
        size = LoaderSize.Small64
    )
}

// ============================================================================
// List Loader Previews
// ============================================================================

@Preview(
    name = "List - Small",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerListSmall() {
    PopShimmer(
        type = LoaderType.List,
        size = LoaderSize.Small
    )
}

@Preview(
    name = "List - Medium",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerListMedium() {
    PopShimmer(
        type = LoaderType.List,
        size = LoaderSize.Med
    )
}

@Preview(
    name = "List - Large",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerListLarge() {
    PopShimmer(
        type = LoaderType.List,
        size = LoaderSize.Large
    )
}

// ============================================================================
// Payment List Loader Previews
// ============================================================================

@Preview(
    name = "Payment List - Medium",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerPaymentListMedium() {
    PopShimmer(
        type = LoaderType.PaymentList,
        size = LoaderSize.Med
    )
}

@Preview(
    name = "Payment List - Large",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerPaymentListLarge() {
    PopShimmer(
        type = LoaderType.PaymentList,
        size = LoaderSize.Large
    )
}

@Preview(
    name = "Payment List Left - Small",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerPaymentListLeft() {
    PopShimmer(
        type = LoaderType.PaymentListLeft,
        size = LoaderSize.Small
    )
}

@Preview(
    name = "Payment List Right - Small",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerPaymentListRight() {
    PopShimmer(
        type = LoaderType.PaymentListRight,
        size = LoaderSize.Small
    )
}

// ============================================================================
// Body Loader Previews
// ============================================================================

@Preview(
    name = "Body",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerBody() {
    PopShimmer(
        type = LoaderType.Body,
        size = LoaderSize.Body
    )
}

// ============================================================================
// Title Loader Previews
// ============================================================================

@Preview(
    name = "Title - Medium",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerTitleMedium() {
    PopShimmer(
        type = LoaderType.Title,
        size = LoaderSize.Med
    )
}

@Preview(
    name = "Title - Large",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerTitleLarge() {
    PopShimmer(
        type = LoaderType.Title,
        size = LoaderSize.Large
    )
}

// ============================================================================
// Table Item Loader Previews
// ============================================================================

@Preview(
    name = "Table Item - Large",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerTableItem() {
    PopShimmer(
        type = LoaderType.TableItem,
        size = LoaderSize.Large
    )
}

// ============================================================================
// Card Loader Previews
// ============================================================================

@Preview(
    name = "Card - Medium",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerCard() {
    PopShimmer(
        type = LoaderType.Card,
        size = LoaderSize.Med
    )
}

// ============================================================================
// Combined Preview Functions
// ============================================================================

@Preview(
    name = "All People Sizes",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewAllPeopleSizes() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
            .padding(PopSpacing.Spacing16),
        verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing16)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing16)
        ) {
            PreviewPopShimmerPeopleSmall28()
            PreviewPopShimmerPeopleSmall36()
            PreviewPopShimmerPeopleSmall56()
            PreviewPopShimmerPeopleSmall64()
        }
    }
}

@Preview(
    name = "All Brand Sizes",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewAllBrandSizes() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
            .padding(PopSpacing.Spacing16),
        verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing16)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing16)
        ) {
            PreviewPopShimmerBrandSmall16()
            PreviewPopShimmerBrandSmall28()
            PreviewPopShimmerBrandSmall36()
            PreviewPopShimmerBrandSmall56()
            PreviewPopShimmerBrandSmall64()
        }
    }
}

@Preview(
    name = "All List Sizes",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewAllListSizes() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
            .padding(PopSpacing.Spacing16),
        verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing16)
    ) {
        PreviewPopShimmerListSmall()
        PreviewPopShimmerListMedium()
        PreviewPopShimmerListLarge()
    }
}

@Preview(
    name = "All Payment List Variants",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewAllPaymentListVariants() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
            .padding(PopSpacing.Spacing16),
        verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing16)
    ) {
        PreviewPopShimmerPaymentListMedium()
        PreviewPopShimmerPaymentListLarge()
        PreviewPopShimmerPaymentListLeft()
        PreviewPopShimmerPaymentListRight()
    }
}

@Preview(
    name = "All Title Sizes",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewAllTitleSizes() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
            .padding(PopSpacing.Spacing16),
        verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing16)
    ) {
        PreviewPopShimmerTitleMedium()
        PreviewPopShimmerTitleLarge()
    }
}

@Preview(
    name = "All Shimmer Types",
    showBackground = true,
    backgroundColor = 0xFF191918,
    heightDp = 2000
)
@Composable
fun PreviewAllShimmerTypes() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
            .padding(PopSpacing.Spacing16),
        verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing24)
    ) {
        // People
        Row(
            horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing16)
        ) {
            PreviewPopShimmerPeopleSmall28()
            PreviewPopShimmerPeopleSmall36()
            PreviewPopShimmerPeopleSmall56()
            PreviewPopShimmerPeopleSmall64()
        }
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing8))
        
        // Brand
        Row(
            horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing16)
        ) {
            PreviewPopShimmerBrandSmall16()
            PreviewPopShimmerBrandSmall28()
            PreviewPopShimmerBrandSmall36()
            PreviewPopShimmerBrandSmall56()
            PreviewPopShimmerBrandSmall64()
        }
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing8))
        
        // List
        PreviewPopShimmerListSmall()
        PreviewPopShimmerListMedium()
        PreviewPopShimmerListLarge()
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing8))
        
        // Payment List
        PreviewPopShimmerPaymentListMedium()
        PreviewPopShimmerPaymentListLarge()
        PreviewPopShimmerPaymentListLeft()
        PreviewPopShimmerPaymentListRight()
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing8))
        
        // Body
        PreviewPopShimmerBody()
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing8))
        
        // Title
        PreviewPopShimmerTitleMedium()
        PreviewPopShimmerTitleLarge()
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing8))
        
        // Table Item
        PreviewPopShimmerTableItem()
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing8))
        
        // Card
        PreviewPopShimmerCard()
    }
}

