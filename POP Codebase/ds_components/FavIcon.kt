package com.pop.components.ds_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.pop.components.theme.FavIconColor
import com.pop.components.theme.FavIconResources
import com.pop.components.theme.FavIconSize
import com.pop.components.theme.toIconDp

/**
 * Reusable FavIcon composable that displays fav icons based on enum values and parameters.
 * Based on Figma design specifications.
 *
 * The icon is wrapped in a container with border and background styling based on the state.
 * p
 * @param color The fav icon color - Orange, White, Green, or Blue (default: Orange)
 * @param size The fav icon size - Large (14dp) or Med (10dp) (default: Large)
 * @param isActive Whether the icon is in active state (default: true)
 * @param modifier Optional modifier for additional styling
 * @param contentDescription Optional content description for accessibility
 *
 * @example
 * ```
 * // Basic usage with default values
 * FavIcon()
 *
 * // Orange, Large, Active
 * FavIcon(
 *     color = FavIconColor.Orange,
 *     size = FavIconSize.Large,
 *     isActive = true
 * )
 *
 * // White, Medium, Inactive
 * FavIcon(
 *     color = FavIconColor.White,
 *     size = FavIconSize.Med,
 *     isActive = false
 * )
 *
 * // Green, Large, Active
 * FavIcon(
 *     color = FavIconColor.Green,
 *     size = FavIconSize.Large,
 *     isActive = true
 * )
 *
 * // Blue, Large, Active
 * FavIcon(
 *     color = FavIconColor.Blue,
 *     size = FavIconSize.Large,
 *     isActive = true
 * )
 * ```
 */
@Composable
fun FavIcon(
    modifier: Modifier = Modifier.Companion,
    color: FavIconColor = FavIconColor.Orange,
    size: FavIconSize = FavIconSize.Large,
    isActive: Boolean = true,
    contentDescription: String? = null,
) {
    val resourceId = FavIconResources.getFavIconResourceId(color, size, isActive)
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Companion.Center
    ) {
        resourceId?.let { id ->
            Image(
                painter = painterResource(id = id),
                contentDescription = contentDescription,
                modifier = Modifier.Companion.size(size.toIconDp()),
                contentScale = ContentScale.Companion.Fit
            )
        }
    }
}

@Preview
@Composable
fun FavIconPreview() {
    FavIcon()
}