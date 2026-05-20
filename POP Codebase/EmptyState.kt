package com.pop.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.ButtonSize
import com.pop.components.ds_components.ButtonState
import com.pop.components.ds_components.ButtonVariant
import com.pop.components.ds_components.PopButtonV2
import com.pop.components.models.VisualElement
import com.pop.components.theme.PopTypography
import com.pop.components.theme.TextColor
import com.pop.compose_components.R

@Composable
fun EmptyState(modifier: Modifier = Modifier, imageRes: Int? = null, imageModifier: Modifier = Modifier, title:String = "", description:String = "", buttonText:String = "", buttonLeftIcon: VisualElement? = null, onClick:()->Unit = {}) {
    Column(
        modifier = Modifier
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        imageRes?.let { imgRes ->
            Image(
                modifier = imageModifier,
                painter = painterResource(imgRes),
                contentDescription = "",
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(title.isNotEmpty()) {
                Text(
                    text = title,
                    style = PopTypography.paragraphLarge.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = TextColor.Primary,
                )
            }

            Text(
                text = description,
                style = PopTypography.paragraphSmall,
                color = TextColor.Secondary,
            )

        }

        if(buttonText.isNotEmpty()) {
            PopButtonV2(
                text = buttonText,
                onClick = onClick,
                variant = ButtonVariant.Secondary,
                state = ButtonState.Active,
                size = ButtonSize.Medium,
                leftIcon = buttonLeftIcon
            )
        }
    }
}

@Preview
@Composable
fun EmptyStatePreview() {
    EmptyState(
        imageRes = R.drawable.no_contact,
        imageModifier = Modifier.fillMaxWidth(),
        title = "No saved contacts",
        description = "Use UPI ID or mobile number to send money",
        buttonText = "Enter UPI ID or mobile number"
    )
}