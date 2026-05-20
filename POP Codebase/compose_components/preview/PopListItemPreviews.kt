package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.AvatarSize
import com.pop.components.ds_components.AvatarType
import com.pop.components.ds_components.PopListItem
import com.pop.components.ds_components.PopVisualElement
import com.pop.components.ds_components.PopListItemAvatar
import com.pop.components.compose_components.PopDividerStyle
import com.pop.components.ds_components.PopDot
import com.pop.components.ds_components.OverlappingImageStack
import com.pop.components.ds_components.toVisualElement
import com.pop.compose_components.R
import com.pop.components.theme.IconSize
import com.pop.components.theme.IconStyle
import com.pop.components.theme.Icons
import com.pop.components.models.VisualElement
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopDotColor
import com.pop.components.theme.PopIconConfig
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.PopTypography

@Preview(name = "PopListItem Variations", showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewPopListItem() {
    PopListItemDemoList(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(vertical = 16.dp)
    )
}

@Composable
fun PopListItemDemoList(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        PopListItem(
            headlineText = "Settings",
            onClick = {}
        )

        PopListItem(
            verticalPadding = 0.dp,
            enabled = false,
            headlineText = "Account",
            leadingAvatar = PopListItemAvatar(
                type = AvatarType.People(name = "Account Holder"),
                size = AvatarSize.Small
            ),
            trailingContent = {
                PopVisualElement(
                    visualElement = PopIconConfig(
                        iconName = Icons.ChevronLeft,
                        style = IconStyle.Outline,
                        size = IconSize.Medium,
                        tint = PopColor.Grey.Grey500
                    ).toVisualElement(),
                    contentDescription = null
                )
            },
            onClick = {}
        )

        PopListItem(
            headlineText = "John Doe",
            supportingText = "View profile details",
            leadingAvatar = PopListItemAvatar(
                type = AvatarType.People(name = "John Doe"),
                size = AvatarSize.Medium,
                topRightComposable = { PopDot(modifier = Modifier.size(10.dp), color = PopDotColor.Blue) }
            ),
            onClick = {}
        )

        PopListItem(
            headlineText = "ID",
            headlineLeftIcon = VisualElement.buildFrom(resId = R.drawable.ic_map_marker_orange),
            supportingText = "Axis Bank • Savings • 1234 5678 9012",
            supportingTextRightIcon = VisualElement.buildFrom(resId = R.drawable.ic_map_marker_orange),
            headlineRightIcon = VisualElement.buildFrom(resId = R.drawable.ic_map_marker_orange),
            supportingTextLeftIcon = VisualElement.buildFrom(resId = R.drawable.ic_map_marker_orange),
            leadingAvatar = PopListItemAvatar(
                type = AvatarType.Brand(
                    imageModel = VisualElement.buildFrom(R.drawable.ic_pop_logo_big)
                ),
                size = AvatarSize.Small,
                topRightComposable = { PopDot(modifier = Modifier.size(10.dp), color = PopDotColor.Blue) }
            ),
            onClick = {}
        )

        PopListItem(
            headlineText = "UPI Lite balance",
            supportingText = "₹25,000 daily limit remaining",
            headlineTextStyle = PopTypography.paragraphSmall,
            supportingTextStyle = PopTypography.headingMedium,
            leadingAvatar = PopListItemAvatar(
                type = AvatarType.People(name = "Lite"),
                size = AvatarSize.Small,
                topRightComposable = { PopDot(modifier = Modifier.size(10.dp), color = PopDotColor.Blue) }
            ),
            onClick = {}
        )

        PopListItem(
            headlineText = "Plain List Item",
            supportingText = "No avatar or icons on this row",
            onClick = {}
        )

        PopListItem(
            headlineText = "Version",
            trailingContent = {
                Text(
                    text = "1.0.0",
                    style = PopTypography.labelMedium,
                    color = PopColor.Grey.Grey500
                )
            },
            onClick = {}
        )

        // Underline examples
        PopListItem(
            headlineText = "Check balance",
            showHeadlineUnderline = true,
            headlineUnderlineStyle = PopDividerStyle.Solid,
            onClick = {}
        )

        PopListItem(
            horizontalPadding = 50.dp,
            headlineText = "Check balance",
            showHeadlineUnderline = true,
            headlineUnderlineStyle = PopDividerStyle.Dashed,
            headlineUnderlineDashLength = 4.dp,
            headlineUnderlineGapLength = 3.dp,
            onClick = {}
        )

        PopListItem(
            headlineText = "Account Name",
            supportingText = "Refresh",
            showSupportingTextUnderline = true,
            supportingTextUnderlineStyle = PopDividerStyle.Solid,
            onClick = {}
        )

        PopListItem(
            headlineText = "Account Name",
            supportingText = "Refresh",
            showSupportingTextUnderline = true,
            supportingTextUnderlineStyle = PopDividerStyle.Dashed,
            supportingTextUnderlineDashLength = 4.dp,
            supportingTextUnderlineGapLength = 3.dp,
            onClick = {}
        )

        PopListItem(
            headlineText = "Bank Account",
            supportingText = "Check balance",
            showHeadlineUnderline = true,
            headlineUnderlineStyle = PopDividerStyle.Solid,
            showSupportingTextUnderline = true,
            supportingTextUnderlineStyle = PopDividerStyle.Dashed,
            supportingTextUnderlineDashLength = 4.dp,
            supportingTextUnderlineGapLength = 3.dp,
            leadingAvatar = PopListItemAvatar(
                type = AvatarType.Brand(
                    imageModel = VisualElement.buildFrom(R.drawable.ic_pop_logo_big)
                ),
                size = AvatarSize.Small
            ),
            trailingContent = {
                OverlappingImageStack(listOf
                    (
                    R.drawable.ic_map_marker_orange,
                    R.drawable.ic_map_marker_orange,
                    R.drawable.ic_map_marker_orange,
                    R.drawable.ic_map_marker_orange,
                    R.drawable.ic_map_marker_orange,
                    )
                )
            },
            onClick = {}
        )
    }
}
