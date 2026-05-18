package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.AvatarIconFill
import com.pop.components.ds_components.AvatarType
import com.pop.components.ds_components.PopVerticalButton
import com.pop.components.ds_components.VerticalButtonSize
import com.pop.components.models.VisualElement
import com.pop.components.theme.Icons
import com.pop.components.theme.SurfaceColor
import com.pop.compose_components.R

@Preview(name = "Vertical Button - Large Size, All Types", showBackground = true)
@Composable
fun PreviewVerticalButtonLargeAllTypes() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Single line titles
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // Icon type
            PopVerticalButton(
                avatarType = AvatarType.Icon(
                    fill = AvatarIconFill.PrimaryHighlighted,
                    icon = Icons.Check
                ),
                title = "Label",
                onClick = {},
                size = VerticalButtonSize.Large
            )
            // Icon disabled
            PopVerticalButton(
                avatarType = AvatarType.Icon(
                    fill = AvatarIconFill.PrimaryHighlighted,
                    icon = Icons.Check
                ),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Large,
                isDisabled = true
            )
        }
        
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // People type
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Large
            )
            // People disabled
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Large,
                isDisabled = true
            )
        }
        
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // Brand type
            PopVerticalButton(
                avatarType = AvatarType.Brand(
                    imageModel = VisualElement.buildFrom(R.drawable.ic_aadhar_yes_bank)
                ),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Large
            )
            // Brand disabled
            PopVerticalButton(
                avatarType = AvatarType.Brand(
                    imageModel = VisualElement.buildFrom(R.drawable.ic_aadhar_yes_bank)
                ),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Large,
                isDisabled = true
            )
        }
        
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            // Illustration type
            PopVerticalButton(
                avatarType = AvatarType.Illustration(
                    imageModel = VisualElement.buildFrom(R.drawable.ic_cart_white)
                ),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Large
            )
            // Illustration disabled
            PopVerticalButton(
                avatarType = AvatarType.Illustration(
                    imageModel = VisualElement.buildFrom(R.drawable.ic_cart_white)
                ),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Large,
                isDisabled = true
            )
        }
    }
}

@Preview(name = "Vertical Button - Medium Size, All Types", showBackground = true)
@Composable
fun PreviewVerticalButtonMediumAllTypes() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PopVerticalButton(
                avatarType = AvatarType.Icon(
                    fill = AvatarIconFill.PrimaryHighlighted,
                    icon = Icons.Check
                ),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Medium
            )
            PopVerticalButton(
                avatarType = AvatarType.Icon(
                    fill = AvatarIconFill.PrimaryHighlighted,
                    icon = Icons.Check
                ),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Medium,
                isDisabled = true
            )
        }
        
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Medium
            )
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Medium,
                isDisabled = true
            )
        }
    }
}

@Preview(name = "Vertical Button - Two Line Labels", showBackground = true)
@Composable
fun PreviewVerticalButtonTwoLines() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Large size with 2-line labels
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PopVerticalButton(
                avatarType = AvatarType.Icon(
                    fill = AvatarIconFill.PrimaryHighlighted,
                    icon = Icons.Check
                ),
                title = "2 line label goes here",
                onClick = {},
                size = VerticalButtonSize.Large,
                maxLines = 2
            )
            PopVerticalButton(
                avatarType = AvatarType.Icon(
                    fill = AvatarIconFill.PrimaryHighlighted,
                    icon = Icons.Check
                ),
                title = "2 line label goes here",
                onClick = {},
                size = VerticalButtonSize.Large,
                maxLines = 2,
                isDisabled = true
            )
        }
        
        // Medium size with 2-line labels
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "2 line label goes here",
                onClick = {},
                size = VerticalButtonSize.Medium,
                maxLines = 2
            )
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "2 line label goes here",
                onClick = {},
                size = VerticalButtonSize.Medium,
                maxLines = 2,
                isDisabled = true
            )
        }
    }
}

@Preview(name = "Vertical Button - All Variants Grid", showBackground = true)
@Composable
fun PreviewVerticalButtonAllVariants() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Large - 1 line
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PopVerticalButton(
                avatarType = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Check),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Large
            )
            PopVerticalButton(
                avatarType = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Check),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Large,
                isDisabled = true
            )
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Large
            )
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Large,
                isDisabled = true
            )

            PopVerticalButton(
                avatarType = AvatarType.Icon(fill = AvatarIconFill.SecondaryHighlighted, icon = Icons.Check),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Large,
                isDisabled = true
            )
        }
        
        // Large - 2 lines
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PopVerticalButton(
                avatarType = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Check),
                title = "2 line label goes here",
                onClick = {},
                size = VerticalButtonSize.Large,
                maxLines = 2
            )
            PopVerticalButton(
                avatarType = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Check),
                title = "2 line label goes here",
                onClick = {},
                size = VerticalButtonSize.Large,
                maxLines = 2,
                isDisabled = true
            )
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "2 line label goes here",
                onClick = {},
                size = VerticalButtonSize.Large,
                maxLines = 2
            )
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "2 line label goes here",
                onClick = {},
                size = VerticalButtonSize.Large,
                maxLines = 2,
                isDisabled = true
            )
        }
        
        // Medium - 1 line
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PopVerticalButton(
                avatarType = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Check),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Medium
            )
            PopVerticalButton(
                avatarType = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Check),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Medium,
                isDisabled = true
            )
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Medium
            )
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "Label here",
                onClick = {},
                size = VerticalButtonSize.Medium,
                isDisabled = true
            )
        }
        
        // Medium - 2 lines
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PopVerticalButton(
                avatarType = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Check),
                title = "2 line label goes here",
                onClick = {},
                size = VerticalButtonSize.Medium,
                maxLines = 2
            )
            PopVerticalButton(
                avatarType = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Check),
                title = "2 line label goes here",
                onClick = {},
                size = VerticalButtonSize.Medium,
                maxLines = 2,
                isDisabled = true
            )
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "2 line label goes here",
                onClick = {},
                size = VerticalButtonSize.Medium,
                maxLines = 2
            )
            PopVerticalButton(
                avatarType = AvatarType.People(name = "NJ"),
                title = "2 line label goes here",
                onClick = {},
                size = VerticalButtonSize.Medium,
                maxLines = 2,
                isDisabled = true
            )
        }
    }
}
