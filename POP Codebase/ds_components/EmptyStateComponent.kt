package com.pop.components.ds_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pop.components.models.VisualElement
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextColor

/**
 * Reusable empty state component that displays an image, title, subtitle, and optional action button.
 * 
 * @param imageResId Resource ID for the empty state image
 * @param imageSize Size of the image (default: 84.dp)
 * @param title Main title text displayed in the empty state
 * @param subtitle Optional subtitle text displayed below the title
 * @param buttonText Optional text for the action button. If null, no button is shown.
 * @param onButtonClick Optional click handler for the action button
 * @param buttonVariant Variant of the button (default: ButtonVariant.Secondary)
 * @param buttonState State of the button (default: ButtonState.Active)
 * @param buttonSize Size of the button (default: ButtonSize.Medium)
 * @param buttonIcon Optional icon for the button (left icon)
 * @param modifier Modifier for the root Column
 * @param verticalPadding Vertical padding for the component (default: 156.dp)
 * @param horizontalPadding Horizontal padding for the component (default: 20.dp)
 * @param imageContentDescription Content description for the image (default: "Empty state")
 */
@Composable
fun EmptyStateComponent(
    imageResId: Int,
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    buttonText: String? = null,
    onButtonClick: (() -> Unit)? = null,
    buttonVariant: ButtonVariant = ButtonVariant.Secondary,
    buttonState: ButtonState = ButtonState.Active,
    buttonSize: ButtonSize = ButtonSize.Medium,
    buttonIcon: VisualElement? = null,
    verticalPadding: Dp = 50.dp,
    horizontalPadding: Dp = 20.dp,
    imageContentDescription: String = "Empty state"
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = verticalPadding, horizontal = horizontalPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageResId),
            contentDescription = imageContentDescription,
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = PopTypography.paragraphLarge.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = TextColor.Primary,
                textAlign = TextAlign.Center
            )

            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = PopTypography.paragraphSmall,
                    color = TextColor.Secondary,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(2.dp))
        }

        if (buttonText != null && onButtonClick != null) {
            PopButtonV2(
                text = buttonText,
                onClick = onButtonClick,
                variant = buttonVariant,
                state = buttonState,
                size = buttonSize,
                leftIcon = buttonIcon
            )
        }
    }
}

