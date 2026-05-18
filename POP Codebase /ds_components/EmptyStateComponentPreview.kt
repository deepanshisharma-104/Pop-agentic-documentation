package com.pop.components.ds_components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.pop.components.models.VisualElement
import com.pop.compose_components.R

/**
 * Preview file for EmptyStateComponent
 */
@Preview(name = "Empty State - With Button")
@Composable
private fun EmptyStateComponentPreview_WithButton() {
    EmptyStateComponent(
        imageResId = R.drawable.ic_rupee_green,
        title = "No saved payees yet",
        subtitle = "To transfer money, add a payee",
        buttonText = "Add payee and transfer",
        onButtonClick = {},
        buttonVariant = ButtonVariant.Secondary,
        buttonState = ButtonState.Active,
        buttonSize = ButtonSize.Medium,
        buttonIcon = VisualElement.Companion.buildFrom(R.drawable.ic_plus_15dp)
    )
}

@Preview(name = "Empty State - Without Button")
@Composable
private fun EmptyStateComponentPreview_WithoutButton() {
    EmptyStateComponent(
        imageResId = R.drawable.img_upi_logo,
        title = "No items found",
        subtitle = "There are no items to display at this time"
    )
}

@Preview(name = "Empty State - Without Subtitle")
@Composable
private fun EmptyStateComponentPreview_WithoutSubtitle() {
    EmptyStateComponent(
        imageResId = R.drawable.ic_map_marker_orange,
        title = "No items found",
        buttonText = "Add Item",
        onButtonClick = {}
    )
}

@Preview(name = "Empty State - Primary Button")
@Composable
private fun EmptyStateComponentPreview_PrimaryButton() {
    EmptyStateComponent(
        imageResId = R.drawable.ic_white_star,
        title = "No saved payees yet",
        subtitle = "To transfer money, add a payee",
        buttonText = "Add new payee",
        onButtonClick = {},
        buttonVariant = ButtonVariant.Primary,
        buttonState = ButtonState.Active,
        buttonSize = ButtonSize.Large
    )
}

