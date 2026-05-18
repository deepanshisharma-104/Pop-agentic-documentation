package com.pop.components.ds_components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.pop.components.compose_components.InlineDottedUnderlineText
import com.pop.components.models.InputFieldStatus
import com.pop.components.models.VisualElement
import com.pop.components.theme.IconSize
import com.pop.components.theme.IconStyle
import com.pop.components.theme.PopIconConfig
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextColor
import com.pop.components.theme.toDp

// Common constants for input fields
internal const val DEFAULT_ANIMATION_DURATION = 200
internal val STATUS_ICON_SIZE = IconSize.Small
internal val STATUS_ICON_SPACING = PopSpacing.Spacing4

// Common text styles using PopTypography
internal val TitleTextStyle = PopTypography.figtreeStyles.labelSmall // 14.sp, Medium
internal val BodyTextStyle = PopTypography.figtreeStyles.paragraphMedium // 16.sp, Medium
internal val StatusTextStyle = PopTypography.figtreeStyles.labelXSmall // 12.sp, Medium

/**
 * Status message component that displays an icon and message based on status type.
 * 
 * @param status The input field status (Success, Error, Info, Warning)
 * @param message The status message text to display
 * @param modifier Modifier to be applied to the status message
 * @param horizontalArrangement Arrangement for horizontal alignment (default: Start for left alignment, use Center for center alignment)
 * @param contentDescription Content description for the status icon (default: derived from status type)
 * @param showIcon Whether to show the status icon (default: true)
 * @param textStyle Typography for the status message text (default: BodyTextStyle / paragraphMedium)
 */
@Composable
internal fun StatusMessage(
    status: InputFieldStatus,
    message: String,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    contentDescription: String? = null,
    showIcon: Boolean = true,
    textStyle: TextStyle = BodyTextStyle,
    onEditClick:(() -> Unit)?=null,
    editUnderlineSpacing: Dp = 2.dp
) {
    val (iconName, textColor) = when (status) {
        InputFieldStatus.Success -> {
            com.pop.components.theme.Icons.CheckVerified to TextColor.Success
        }
        InputFieldStatus.Error -> {
            com.pop.components.theme.Icons.AlertHexagon to TextColor.Destructive
        }
        InputFieldStatus.Info -> {
            com.pop.components.theme.Icons.InfoSquare to TextColor.Tertiary
        }
        InputFieldStatus.Warning -> {
            com.pop.components.theme.Icons.AlertCircle to TextColor.Warning
        }
    }

    val visualElement = remember(iconName, textColor) {
        VisualElement.buildFrom(
            iconName = iconName,
            style = IconStyle.Filled,
            tintColor = textColor,
            heightDp = STATUS_ICON_SIZE.toDp().value.toInt(),
            widthDp = STATUS_ICON_SIZE.toDp().value.toInt()
        )
    }

    // Generate content description from status if not provided
    val finalContentDescription = contentDescription ?: when (status) {
        InputFieldStatus.Success -> "Success"
        InputFieldStatus.Error -> "Error"
        InputFieldStatus.Info -> "Info"
        InputFieldStatus.Warning -> "Warning"
    }

    StatusMessageRow(
        message = message,
        visualElement = if (showIcon) visualElement else null,
        textColor = textColor,
        textStyle = textStyle,
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        contentDescription = finalContentDescription,
        status = status,
        onEditClick = onEditClick,
        editUnderlineSpacing = editUnderlineSpacing
    )
}

/**
 * Reusable status message row component
 * 
 * @param message The status message text
 * @param visualElement The icon visual element (null to hide the icon)
 * @param textColor The text color for the message
 * @param textStyle Typography for the message text (default: BodyTextStyle)
 * @param modifier Modifier to be applied to the row
 * @param horizontalArrangement Arrangement for horizontal alignment (default: Start for left alignment)
 * @param contentDescription Content description for accessibility (default: null, but should be provided for better accessibility)
 */
@Composable
internal fun StatusMessageRow(
    message: String,
    visualElement: VisualElement?,
    textColor: Color,
    textStyle: TextStyle = BodyTextStyle,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    contentDescription: String? = null,
    status: InputFieldStatus? = null,
    onEditClick:(()->Unit?)? = null,
    editUnderlineSpacing: Dp = 2.dp
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = PopSpacing.Spacing4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement
    ) {
        if (visualElement != null) {
            PopVisualElement(
                visualElement = visualElement,
                modifier = Modifier.size(STATUS_ICON_SIZE.toDp()),
                contentDescription = contentDescription
            )
            Spacer(modifier = Modifier.width(STATUS_ICON_SPACING))
        }
        Text(
            text = message,
            style = textStyle.copy(color = textColor),
            modifier = Modifier.alignBy(FirstBaseline)
        )
        Spacer(modifier = Modifier.width(STATUS_ICON_SPACING))
        if (onEditClick != null) {
            InlineDottedUnderlineText(
                modifier = Modifier
                    .alignBy(FirstBaseline)
                    .clickable {
                        onEditClick.invoke()
                    },
                text = "Edit",
                style = textStyle.copy(fontWeight = FontWeight.SemiBold),
                color = textColor,
                textToUnderlineSpacing = editUnderlineSpacing
            )
        }

    }
}

/**
 * Creates keyboard actions from IME action callback
 */
internal fun createKeyboardActions(onImeAction: () -> Unit) = KeyboardActions(
    onDone = { onImeAction() },
    onNext = { onImeAction() },
    onGo = { onImeAction() }
)

/**
 * Helper function to convert PopIconConfig to VisualElement
 */
fun PopIconConfig.toVisualElement(): VisualElement {
    return VisualElement.buildFrom(
        iconName = iconName,
        style = style,
        tintColor = tint,
        heightDp = size.toDp().value.toInt(),
        widthDp = size.toDp().value.toInt()
    )
}