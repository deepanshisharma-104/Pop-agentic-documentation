package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.compose_components.R
import com.pop.components.ds_components.AvatarIconFill
import com.pop.components.ds_components.AvatarSize
import com.pop.components.ds_components.AvatarType
import com.pop.components.ds_components.PopAvatar
import com.pop.components.ds_components.PopDot
import com.pop.components.models.VisualElement
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.Icons
import com.pop.components.theme.PopDotColor
import com.pop.components.theme.SurfaceColor

@Preview(name = "Avatar - People (Initials)", showBackground = true)
@Composable
fun PreviewPersonAvatarInitials() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AvatarSize.values().forEach { size ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PopAvatar(
                    type = AvatarType.Favorite(name = "John Doe"),
                    size = size
                )
                PopAvatar(
                    type = AvatarType.Favorite(name = "Jane"),
                    size = size
                )
            }
        }
    }
}

@Preview(name = "Avatar - Brand", showBackground = true)
@Composable
fun PreviewNonPersonAvatar() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AvatarSize.entries.forEach { size ->
            PopAvatar(
                type = AvatarType.Brand(imageModel = VisualElement.buildFrom(R.drawable.ic_airtel_operator_logo)),
                size = size
            )
        }
    }
}

@Preview(name = "Avatar - Icon fills", showBackground = true)
@Composable
fun PreviewAvatarIconFills() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val fills = listOf(
            AvatarIconFill.PrimaryHighlighted,
            AvatarIconFill.SecondaryHighlighted,
            AvatarIconFill.TertiaryTransparent,
            AvatarIconFill.OnlyIcon,
            AvatarIconFill.Primary50Percent,
        )

        AvatarSize.values().forEach { avatarSize ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                fills.forEach { fill ->
                    PopAvatar(
                        type = AvatarType.Icon(
                            fill = fill,
                            icon = Icons.Check
                        ),
                        size = avatarSize
                    )
                }
            }
        }
    }
}

@Preview(name = "Avatar - Illustration", showBackground = true)
@Composable
fun PreviewAvatarIllustration() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AvatarSize.values().forEach { size ->
            PopAvatar(
                type = AvatarType.Illustration(imageModel = VisualElement.buildFrom(R.drawable.ic_cart_white)),
                size = size
            )
        }
    }
}

@Preview(name = "Avatar - Disabled", showBackground = true)
@Composable
fun PreviewAvatarDisabled() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            PopAvatar(type = AvatarType.People(name = "NJ"), size = AvatarSize.XLarge, isDisabled = true,  topRightComposable = { PopDot(color = PopDotColor.Blue) })
            PopAvatar(
                type = AvatarType.Brand(imageModel = VisualElement.buildFrom(R.drawable.ic_aadhar_yes_bank)),
                size = AvatarSize.XLarge,
                isDisabled = true,
                topRightComposable = { PopDot(color = PopDotColor.Blue) }
            )
            PopAvatar(
                type = AvatarType.Icon(fill = AvatarIconFill.PrimaryHighlighted, icon = Icons.Check),
                size = AvatarSize.XLarge,
                isDisabled = true,
                topRightComposable = { PopDot() }
            )
            PopAvatar(
                type = AvatarType.Illustration(imageModel = VisualElement.buildFrom(R.drawable.ic_map_marker_orange)),
                size = AvatarSize.XLarge,
                isDisabled = true,
                topRightComposable = { PopDot(modifier = Modifier.size(10.dp)) }
            )
        }
    }
}

@Preview(name = "Avatar - People Background Colors", showBackground = true)
@Composable
fun PreviewPeopleAvatarBackgroundColors() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Row 1: All 8 avatar person gradients (XLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PopAvatar(
                type = AvatarType.People(
                    name = "Pink",
                    background = GradientPreset.AvatarPerson1.gradient
                ),
                size = AvatarSize.XLarge
            )
            PopAvatar(
                type = AvatarType.People(
                    name = "Sea Green",
                    background = GradientPreset.AvatarPerson2.gradient
                ),
                size = AvatarSize.XLarge
            )
            PopAvatar(
                type = AvatarType.People(
                    name = "Purple",
                    background = GradientPreset.AvatarPerson3.gradient
                ),
                size = AvatarSize.XLarge
            )
            PopAvatar(
                type = AvatarType.People(
                    name = "Maroon",
                    background = GradientPreset.AvatarPerson4.gradient
                ),
                size = AvatarSize.XLarge
            )
        }
        // Row 2: Remaining 4 avatar person gradients (XLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PopAvatar(
                type = AvatarType.People(
                    name = "Violet",
                    background = GradientPreset.AvatarPerson5.gradient
                ),
                size = AvatarSize.XLarge
            )
            PopAvatar(
                type = AvatarType.People(
                    name = "Orange",
                    background = GradientPreset.AvatarPerson6.gradient
                ),
                size = AvatarSize.XLarge
            )
            PopAvatar(
                type = AvatarType.People(
                    name = "Green",
                    background = GradientPreset.AvatarPerson7.gradient
                ),
                size = AvatarSize.XLarge
            )
            PopAvatar(
                type = AvatarType.People(
                    name = "Red",
                    background = GradientPreset.AvatarPerson8.gradient
                ),
                size = AvatarSize.XLarge
            )
        }
    }
}

@Preview(name = "Avatar - People with Dynamic Background", showBackground = true)
@Composable
fun PreviewPeopleAvatarDynamicBackground() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Shows how the same name always gets the same color
        val names = listOf("Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry")
        
        // Row 1: Medium size with dynamic gradient based on name
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            names.take(4).forEach { name ->
                PopAvatar(
                    type = AvatarType.People(
                        name = name,
                        background = GradientPreset.getAvatarGradientForName(name)
                    ),
                    size = AvatarSize.Medium
                )
            }
        }
        // Row 2: Medium size with dynamic gradient based on name
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            names.drop(4).forEach { name ->
                PopAvatar(
                    type = AvatarType.People(
                        name = name,
                        background = GradientPreset.getAvatarGradientForName(name)
                    ),
                    size = AvatarSize.Medium
                )
            }
        }
    }
}

@Preview(name = "Avatar - Favorite Background Colors", showBackground = true)
@Composable
fun PreviewFavoriteAvatarBackgroundColors() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Favorites with different background colors
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PopAvatar(
                type = AvatarType.Favorite(
                    name = "Mom",
                    background = GradientPreset.getAvatarGradientForName("Mom")
                ),
                size = AvatarSize.XLarge
            )
            PopAvatar(
                type = AvatarType.Favorite(
                    name = "Dad",
                    background = GradientPreset.getAvatarGradientForName("Dad")
                ),
                size = AvatarSize.XLarge
            )
            PopAvatar(
                type = AvatarType.Favorite(
                    name = "Best Friend",
                    background = GradientPreset.getAvatarGradientForName("Best Friend")
                ),
                size = AvatarSize.XLarge
            )
            PopAvatar(
                type = AvatarType.Favorite(
                    name = "Work",
                    background = GradientPreset.getAvatarGradientForName("Work")
                ),
                size = AvatarSize.XLarge
            )
        }
    }
}

@Preview(name = "Avatar - People All Sizes with Colors", showBackground = true)
@Composable
fun PreviewPeopleAvatarAllSizesWithColors() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Show each size with a different gradient
        AvatarSize.entries.forEachIndexed { index, size ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PopAvatar(
                    type = AvatarType.People(
                        name = "John Doe",
                        background = GradientPreset.getAvatarGradientByIndex(index)
                    ),
                    size = size
                )
                PopAvatar(
                    type = AvatarType.People(
                        name = "Jane Smith",
                        background = GradientPreset.getAvatarGradientByIndex(index + 1)
                    ),
                    size = size
                )
            }
        }
    }
}
