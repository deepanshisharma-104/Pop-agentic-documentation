package com.pop.components.compose_components

import android.R.attr.fontFamily
import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.pop.components.theme.FlashTypography
import com.pop.components.theme.Radius
import com.pop.components.theme.PopColors
import com.pop.components.theme.TextColors

enum class SearchBarSize {
    Small, Big
}

@Composable
fun PopSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onClear: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    placeholder: String = "Send money to Mobile Number or UPI ID",
    flippingHint:Boolean = false,
    flippingTexts:List<String> = listOf(),
    enabled: Boolean = true,
    size: SearchBarSize = SearchBarSize.Small,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    var isFocused by remember { mutableStateOf(false) }
    
    val verticalPadding = when(size) {
        SearchBarSize.Small -> 12.dp
        SearchBarSize.Big -> 16.dp
    }
    
    val iconSize = when(size) {
        SearchBarSize.Small -> 20.dp
        SearchBarSize.Big -> 24.dp
    }
    
    val fontSize = when(size) {
        SearchBarSize.Small -> 16.sp
        SearchBarSize.Big -> 18.sp
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PopColors.Neutral.N2,
                shape = RoundedCornerShape(Radius.Full)
            )
            .border(
                width = 1.dp,
                color = if (isFocused) TextColors.Secondary.Default else Color.Transparent,
                shape = RoundedCornerShape(Radius.Full)
            )
            .padding(horizontal = 16.dp, vertical = verticalPadding)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = if (value.isNotEmpty()) TextColors.Primary.Default else PopColors.Neutral.N7,
                modifier = Modifier.size(iconSize)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(modifier = Modifier.weight(1f)) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { isFocused = it.isFocused },
                    textStyle = TextStyle(
                        fontFamily = FlashTypography.figtree,
                        fontWeight = FontWeight.Normal,
                        fontSize = fontSize,
                        letterSpacing = (-0.01).sp,
                        color = TextColors.Primary.Default
                    ),
                    enabled = enabled,
                    singleLine = true,
                    cursorBrush = SolidColor(TextColors.Secondary.Default),
                    keyboardOptions = keyboardOptions
                )

                if (value.isEmpty() && !isFocused) {
                    if(flippingHint) {
                        ScrollingTextEffect(
                            fixedTextStart = placeholder,
                            scrollingTexts = flippingTexts,
                            fixedTextEnd = "",
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextColors.Secondary.Default)
                        )
//                        FlippingTextEffect(
//                            fixedTextStart = placeholder,
//                            flippingTexts = flippingTexts,
//                            fixedTextEnd = "",
//                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = TextColors.Secondary.Default)
//                        )
                    }else{
                        Text(
                            text = placeholder,
                            color = PopColors.Neutral.N7,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            if (value.isNotEmpty()) {
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    tint = TextColors.Primary.Default,
                    modifier = Modifier
                        .size(iconSize).clickable(enabled = enabled,
                            onClick = { onClear?.invoke() },
                            indication = rememberRipple(bounded = true),
                            interactionSource = remember { MutableInteractionSource() })
                )
            }

            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                trailingIcon()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FlashSearchBarPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PopColors.Neutral.N1)
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Small Search Bar",
            style = TextStyle(
                fontFamily = FlashTypography.figtree,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = TextColors.Primary.Default
            )
        )
        
        // Empty state - Small
        var emptyTextSmall by remember { mutableStateOf("") }
        PopSearchBar(
            value = emptyTextSmall,
            onValueChange = { emptyTextSmall = it },
            size = SearchBarSize.Small
        )

        // With text - Small
        var filledTextSmall by remember { mutableStateOf("John Doe") }
        PopSearchBar(
            value = filledTextSmall,
            onValueChange = { filledTextSmall = it },
            size = SearchBarSize.Small
        )

        Text(
            text = "Big Search Bar",
            style = TextStyle(
                fontFamily = FlashTypography.figtree,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = TextColors.Primary.Default
            )
        )
        
        // Empty state - Big
        var emptyTextBig by remember { mutableStateOf("") }
        PopSearchBar(
            value = emptyTextBig,
            onValueChange = { emptyTextBig = it },
            size = SearchBarSize.Big
        )

        // With text - Big
        var filledTextBig by remember { mutableStateOf("John Doe") }
        PopSearchBar(
            value = filledTextBig,
            onValueChange = { filledTextBig = it },
            size = SearchBarSize.Big
        )

        // Disabled state
        Text(
            text = "Disabled States",
            style = TextStyle(
                fontFamily = FlashTypography.figtree,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = TextColors.Primary.Default
            )
        )
        
        PopSearchBar(
            value = "",
            onValueChange = {},
            enabled = false,
            size = SearchBarSize.Small
        )
        
        PopSearchBar(
            value = "",
            onValueChange = {},
            enabled = false,
            size = SearchBarSize.Big
        )
    }
}