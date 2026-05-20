package com.pop.components.permissions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.AvatarIconFill
import com.pop.components.ds_components.AvatarSize
import com.pop.components.ds_components.AvatarType
import com.pop.components.ds_components.PopDot
import com.pop.components.ds_components.PopListItem
import com.pop.components.ds_components.PopListItemAvatar
import com.pop.components.theme.PopDotColor
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextColor

/**
 * Header component for the permission bottom sheet.
 */
@Composable
internal fun PermissionHeader(
    title: String,
    description: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = PopTypography.figtreeStyles.paragraphLarge,
            color = TextColor.Primary
        )
        Text(
            text = description,
            style = PopTypography.figtreeStyles.paragraphSmall,
            color = TextColor.Secondary
        )
    }
}

/**
 * Row component displaying a single permission with its status.
 */
@Composable
internal fun PermissionRow(
    permission: AppPermission,
    isGranted: Boolean,
    isDenied: Boolean,
    isLast: Boolean
) {
    PopListItem(
        headlineText = if (permission.isOptional) {
            "${permission.title} • (optional)"
        } else {
            permission.title
        },
        supportingText = permission.description,
        supportingTextMaxLines = 2,
        leadingAvatar = PopListItemAvatar(
            type = AvatarType.Icon(
                fill = AvatarIconFill.SecondaryHighlighted,
                icon = permission.icon
            ),
            size = AvatarSize.Small,
            topRightComposable = {
                when {
                    isGranted -> {
                        PopDot(
                            modifier = Modifier.size(10.dp),
                            color = PopDotColor.Green
                        )
                    }

                    isDenied -> {
                        PopDot(
                            modifier = Modifier.size(10.dp),
                            color = PopDotColor.Orange
                        )
                    }

                    else -> {
                        null
                    }
                }
            }
        ),
        horizontalPadding = 0.dp,
        verticalPadding = 16.dp,
        showDivider = !isLast
    )
}

