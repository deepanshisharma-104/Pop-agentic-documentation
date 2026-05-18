package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.pop.components.ds_components.LoaderBlockType
import com.pop.components.ds_components.PopShimmerBlocks
import com.pop.components.theme.PopSpacing

/**
 * Preview functions for PopShimmerBlocks component
 * 
 * This file contains all preview functions to showcase all features
 * of the PopShimmerBlocks component.
 */

// ============================================================================
// Individual Block Type Previews
// ============================================================================

@Preview(
    name = "List Stack",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerBlocksListStack() {
    PopShimmerBlocks(
        type = LoaderBlockType.ListStack
    )
}

@Preview(
    name = "Payment List Stack",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerBlocksPaymentList() {
    PopShimmerBlocks(
        type = LoaderBlockType.PaymentList
    )
}

@Preview(
    name = "Card Stack",
    showBackground = true,
    backgroundColor = 0xFF191918
)
@Composable
fun PreviewPopShimmerBlocksCardStack() {
    PopShimmerBlocks(
        type = LoaderBlockType.CardStack
    )
}

// ============================================================================
// Combined Preview Functions
// ============================================================================

@Preview(
    name = "All Block Types",
    showBackground = true,
    backgroundColor = 0xFF191918,
    heightDp = 2500
)
@Composable
fun PreviewAllShimmerBlocks() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191918))
            .padding(PopSpacing.Spacing16),
        verticalArrangement = Arrangement.spacedBy(PopSpacing.Spacing24)
    ) {
        PreviewPopShimmerBlocksListStack()
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing16))
        
        PreviewPopShimmerBlocksPaymentList()
        
        Spacer(modifier = Modifier.height(PopSpacing.Spacing16))
        
        PreviewPopShimmerBlocksCardStack()
    }
}

