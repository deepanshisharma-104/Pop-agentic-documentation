package com.pop.components.compose_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.theme.FlashTypography
import com.pop.components.theme.PopColors
import com.pop.components.theme.TextColors
import kotlinx.coroutines.delay

@Composable
fun PopPrefixInputField(
    modifier: Modifier = Modifier,
    textfieldModifier:Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    prefixText: String = "+91 | ",
    placeholderText: String = "Drop your number here",
    isError: Boolean = false,
    errorMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    showCustomCursor: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }
    
    // Custom cursor blinking state
    var showCursor by remember { mutableStateOf(true) }
    
    // Blink cursor: in custom-cursor mode, keep blinking even if focus moves (e.g., to custom keyboard)
    LaunchedEffect(isFocused, showCustomCursor, readOnly) {
        val shouldBlink = if (showCustomCursor) {
            // For readOnly inputs with custom keyboard, keep cursor active regardless of focus
            true
        } else {
            isFocused
        }
        if (shouldBlink) {
            while (true) {
                delay(530)
                showCursor = !showCursor
            }
        } else {
            showCursor = false
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = PopColors.Neutral.N2,
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
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = prefixText,
                    style = TextStyle(
                        fontFamily = FlashTypography.figtree,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        letterSpacing = (-0.01).sp,
                        color = if(enabled) PopColors.Neutral.N12 else TextColors.Primary.Disabled
                    ),
                    modifier = Modifier.padding(end = 2.dp)
                )
                
                Box(modifier = Modifier.weight(1f)) {
                    if (showCustomCursor) {
                        val density = LocalDensity.current
                        val cursorHeight = with(density) { 16.sp.toDp() * 1.2f }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { isFocused = it.isFocused }
                                .then(textfieldModifier)
                        ) {
                            Row(modifier = Modifier.wrapContentWidth(), verticalAlignment = Alignment.CenterVertically) {
                                if (value.isEmpty()) {
                                    // Reserve cursor slot so placeholder doesn't shift while blinking
                                    Box(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .height(cursorHeight)
                                            .background(if (showCursor) Color.White else Color.Transparent)
                                    )
                                    Text(
                                        text = placeholderText,
                                        style = TextStyle(
                                            fontFamily = FlashTypography.figtree,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 16.sp,
                                            letterSpacing = (-0.01).sp,
                                            color = PopColors.Neutral.N7
                                        ),
                                        maxLines = 1,
                                        softWrap = false,
                                        overflow = TextOverflow.Clip
                                    )
                                } else {
                                    Text(
                                        text = value,
                                        style = TextStyle(
                                            fontFamily = FlashTypography.figtree,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 16.sp,
                                            letterSpacing = (-0.01).sp,
                                            color = TextColors.Primary.Default
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(2.dp))
                                    // Reserve cursor slot at end of text
                                    Box(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .height(cursorHeight)
                                            .background(if (showCursor) Color.White else Color.Transparent)
                                    )
                                }
                            }
                        }
                    } else {
                        BasicTextField(
                            value = value,
                            onValueChange = { newValue ->
                                if (newValue.all { it.isDigit() }) {
                                    onValueChange(newValue)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .onFocusChanged { isFocused = it.isFocused }
                                .then(textfieldModifier),
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
                                keyboardType = KeyboardType.Number,
                                imeAction = imeAction
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { onImeAction() },
                                onNext = { onImeAction() },
                                onGo = { onImeAction() }
                            ),
                            visualTransformation = VisualTransformation.None,
                            singleLine = true,
                            cursorBrush = SolidColor(TextColors.Primary.Default),
                            decorationBox = { innerTextField ->
                                innerTextField()
                            }
                        )
                        if (value.isEmpty() && !isFocused) {
                            Text(
                                text = placeholderText,
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