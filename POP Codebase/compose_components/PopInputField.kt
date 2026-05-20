package com.pop.components.compose_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.theme.FlashTypography
import com.pop.components.theme.PopColors
import com.pop.components.theme.SurfaceColors
import com.pop.components.theme.TextColors

@Composable
fun PopInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    focusedBorderColor: Color = TextColors.Secondary.Default,
    backgroundColor: Color = SurfaceColors.Tertiary,
    placeholderTextColor: Color = PopColors.Neutral.N7,
    labelColor:Color = PopColors.Neutral.N9,
    onImeAction: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (label != null) {
            Text(
                text = label,
                style = TextStyle(
                    fontFamily = FlashTypography.figtree,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    letterSpacing = (-0.01).sp,
                    color = labelColor
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SurfaceColors.Tertiary,
                    shape = RoundedCornerShape(16.dp)
                ),
            textStyle = TextStyle(
                fontFamily = FlashTypography.figtree,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = (-0.01).sp,
                color = TextColors.Primary.Default
            ),
            placeholder = placeholder?.let {
                {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontFamily = FlashTypography.figtree,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            letterSpacing = (-0.01).sp,
                            color = placeholderTextColor
                        )
                    )
                }
            },
            enabled = enabled,
            readOnly = readOnly,
            isError = isError,
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = { onImeAction() },
                onNext = { onImeAction() },
                onGo = { onImeAction() }
            ),
            visualTransformation = visualTransformation,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = focusedBorderColor,
                unfocusedBorderColor = Color.Transparent,
                errorBorderColor = TextColors.Accent.Default,
                disabledBorderColor = Color.Transparent,
                focusedContainerColor = backgroundColor,
                unfocusedContainerColor = backgroundColor,
                disabledContainerColor = PopColors.Neutral.N2,
                errorContainerColor = PopColors.Neutral.N2,
                cursorColor = TextColors.Secondary.Default
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                style = TextStyle(
                    fontFamily = FlashTypography.figtree,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    letterSpacing = (-0.01).sp,
                    color = TextColors.Accent.Default
                ),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
} 