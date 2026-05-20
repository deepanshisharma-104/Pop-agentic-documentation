package com.pop.components.compose_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.theme.PopColors

@Composable
fun OTPTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit,
    onDone: () -> Unit
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }
    val charRegex = Regex("[., -]")
    BasicTextField(
        modifier = modifier,
        value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
        onValueChange = {
            var enteredText = it.text.replace(charRegex, "")
            if (enteredText.length <= otpCount) {
                onOtpTextChange.invoke(enteredText, enteredText.length == otpCount)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {
                repeat(otpCount) { index ->
                    Box(modifier = Modifier.weight(1f)) {
                        CharView(
                            index = index,
                            text = otpText
                        )
                    }
                    if (index != otpCount - 1) {
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
    )
}

@Composable
private fun CharView(
    index: Int,
    text: String
) {
    val isFocused = text.length == index
    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }
    val focusedModifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .background(color = PopColors.Neutral.N9.copy(alpha = 0.1f))
        .border(
            1.dp, PopColors.Neutral.N11, RoundedCornerShape(16.dp)
        )
    val defaultModifier = Modifier
        .fillMaxWidth()
        .height(56.dp)
        .background(color = PopColors.Neutral.N9.copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp))
    Box(modifier = if(isFocused) focusedModifier else defaultModifier, contentAlignment = Alignment.Center){
        Text(
            text = char,
            color =  PopColors.Neutral.N12,
            textAlign = TextAlign.Center,
            fontSize = 22.sp
        )
    }
}