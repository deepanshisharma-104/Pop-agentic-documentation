package com.pop.components.compose_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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

/**
 * Figma link:
 * https://www.figma.com/design/l1MwQMIjvvJerOUdh4pNpK/Shop-2025?node-id=8917-14407&t=DJzwp2PwBukdeAwc-4
 */
@Composable
fun PopLabeledInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: () -> Unit = {},
    onAutoValidateAfterInteraction: (String) -> Unit = {},
    ) {
    var isFocused by remember { mutableStateOf(false) }
    val showLabel = value.isNotEmpty() || isFocused
    var hasInteracted by remember { mutableStateOf(false) }
    val shouldShowError = isError && hasInteracted

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = SurfaceColors.Tertiary,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = when {
                        isError -> TextColors.Accent.Default
                        isFocused -> TextColors.Secondary.Default
                        else -> Color.Transparent
                    },
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp)
        ) {
            Column {
                if (showLabel) {
                    Text(
                        text = label,
                        style = TextStyle(
                            fontFamily = FlashTypography.figtree,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            letterSpacing = (-0.01).sp,
                            color = if (shouldShowError) TextColors.Accent.Default
                                   else if (isFocused) TextColors.Secondary.Default
                                   else PopColors.Neutral.N7
                        ),
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = if (showLabel) 4.dp else 16.dp,
                            bottom = 16.dp
                        )
                ) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged {
                                val wasFocused = isFocused
                                isFocused = it.isFocused
                                if (wasFocused && !it.isFocused) {
                                    // user just left the field → mark as interacted
                                    hasInteracted = true
                                    onAutoValidateAfterInteraction.invoke(value)
                                }
                            },
                        textStyle = TextStyle(
                            fontFamily = FlashTypography.figtree,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            letterSpacing = (-0.01).sp,
                            color = TextColors.Primary.Default
                        ),
                        enabled = enabled,
                        readOnly = readOnly,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = keyboardType,
                            imeAction = imeAction
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                hasInteracted = true
                                onImeAction()
                            },
                            onNext = {
                                hasInteracted = true
                                onImeAction()
                            },
                            onGo = {
                                hasInteracted = true
                                onImeAction()
                            }
                        ),
                        visualTransformation = visualTransformation,
                        singleLine = true,
                        cursorBrush = SolidColor(TextColors.Secondary.Default)
                    )
                    
                    if (value.isEmpty() && !isFocused) {
                        Text(
                            text = label,
                            style = TextStyle(
                                fontFamily = FlashTypography.figtree,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                letterSpacing = (-0.01).sp,
                                color = PopColors.Neutral.N7
                            )
                        )
                    }
                }
            }
        }

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
                modifier = Modifier.padding(top = 4.dp, start = 16.dp)
            )
        }
    }
} 