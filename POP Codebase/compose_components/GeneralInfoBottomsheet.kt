package com.pop.components.compose_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pop.components.ds_components.ButtonSize
import com.pop.components.ds_components.ButtonState
import com.pop.components.ds_components.ButtonVariant
import com.pop.components.ds_components.ButtonWidthType
import com.pop.components.ds_components.PopBottomSheet
import com.pop.components.ds_components.PopButtonV2
import com.pop.components.models.BottomSheetBackgroundType
import com.pop.components.models.BottomSheetGradientShape
import com.pop.components.models.PopBottomSheetConfig
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextColor

@Composable
fun GeneralInfoBottomSheet(
    showSheet: Boolean,
    title: String = "",
    description: String = "",
    leftButtonText: String = "",
    rightButtonText: String = "",
    onDismissRequest: () -> Unit,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
) {
    val config = remember {
        PopBottomSheetConfig(
            cornerRadius = PopRadius.XLarge,
            isDraggable = true,
            isCancellable = true,
            showFloatingCloseButton = true,
            backgroundType = BottomSheetBackgroundType.Gradient(
                shape = BottomSheetGradientShape.Rounded
            )
        )
    }

    PopBottomSheet(
        showSheet = showSheet,
        onDismissRequest = onDismissRequest,
        config = config
    ) {
        GeneralInfoBottomSheetContent(
            title = title,
            description = description,
            leftButtonText = leftButtonText,
            rightButtonText = rightButtonText,
            onLeftButtonClick = onLeftButtonClick,
            onRightButtonClick = onRightButtonClick
        )
    }
}

@Composable
fun NonDismissableGeneralInfoBottomSheet(
    showSheet: Boolean,
    title: String = "",
    description: String = "",
    leftButtonText: String = "",
    rightButtonText: String = "",
    onDismissRequest: () -> Unit,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit
) {
    val config = remember {
        PopBottomSheetConfig(
            cornerRadius = PopRadius.XLarge,
            isDraggable = false,
            isCancellable = false,
            showFloatingCloseButton = false,
            backgroundType = BottomSheetBackgroundType.Gradient(
                shape = BottomSheetGradientShape.Rounded
            )
        )
    }

    PopBottomSheet(
        showSheet = showSheet,
        onDismissRequest = onDismissRequest,
        config = config
    ) {
        GeneralInfoBottomSheetContent(
            title = title,
            description = description,
            leftButtonText = leftButtonText,
            rightButtonText = rightButtonText,
            onLeftButtonClick = onLeftButtonClick,
            onRightButtonClick = onRightButtonClick
        )
    }
}

@Composable
private fun GeneralInfoBottomSheetContent(
    title: String,
    description: String,
    leftButtonText: String,
    rightButtonText: String,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PopSpacing.Spacing16)
    ) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                color = TextColor.Primary,
                style = PopTypography.figtreeStyles.headingMedium
            )
            Spacer(modifier = Modifier.padding(PopSpacing.Spacing8))
        }

        if (description.isNotEmpty()) {
            Text(
                text = description,
                color = TextColor.Secondary,
                style = PopTypography.figtreeStyles.paragraphMedium
            )
            Spacer(modifier = Modifier.padding(PopSpacing.Spacing16))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing12)
        ) {
            if (leftButtonText.isNotEmpty()) {
                PopButtonV2(
                    text = leftButtonText,
                    onClick = onLeftButtonClick,
                    variant = ButtonVariant.Secondary,
                    state = ButtonState.Active,
                    size = ButtonSize.Medium,
                    widthType = ButtonWidthType.Fill,
                    modifier = Modifier.weight(1f)
                )
            }
            if (rightButtonText.isNotEmpty()) {
                PopButtonV2(
                    text = rightButtonText,
                    onClick = onRightButtonClick,
                    variant = ButtonVariant.Primary,
                    state = ButtonState.Active,
                    size = ButtonSize.Medium,
                    widthType = ButtonWidthType.Fill,
                    modifier = if (leftButtonText.isNotEmpty()) Modifier.weight(1f) else Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GeneralInfoBottomSheetContentPreview() {
    GeneralInfoBottomSheetContent(
        title = "General Info",
        description = "This is a general information bottom sheet.",
        leftButtonText = "Cancel",
        rightButtonText = "Confirm",
        onLeftButtonClick = {},
        onRightButtonClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun GeneralInfoBottomSheetContentSingleButtonPreview() {
    GeneralInfoBottomSheetContent(
        title = "General Info",
        description = "This is a general information bottom sheet.",
        leftButtonText = "",
        rightButtonText = "Got it",
        onLeftButtonClick = {},
        onRightButtonClick = {}
    )
}
