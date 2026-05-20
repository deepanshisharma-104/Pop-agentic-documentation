package com.pop.components.compose_components.preview

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.pop.components.compose_components.PopEnterAmountHeader
import com.pop.components.theme.PopTheme

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
fun PreviewPopEnterAmountHeaderUPI() {
    var amount by remember { mutableStateOf("") }
    PopTheme {
        PopEnterAmountHeader(
            name = "Verified name",
            subtitle = "9999999999@yespop",
            amount = amount,
            onAmountChange = { amount = it },
            onBackClick = {},
            onViewHistoryClick = {},
            onAddNoteClick = {},
            isVerified = true,
            content = {

            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
fun PreviewPopEnterAmountHeaderRCBP() {
    var amount by remember { mutableStateOf("389") }
    PopTheme {
        PopEnterAmountHeader(
            name = "Jio prepaid",
            subtitle = "Tanu • +918234036854",
            amount = amount,
            onAmountChange = { amount = it },
            onBackClick = {},
            isVerified = true,
            subtitleIcon = com.pop.compose_components.R.drawable.ic_check_verified, // Using verified as placeholder for PhonePe icon,
            content = {

            }
        )
    }
}
