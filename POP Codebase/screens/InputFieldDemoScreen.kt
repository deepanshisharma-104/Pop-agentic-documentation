package com.pop.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.compose_components.PopInputField
import com.pop.components.compose_components.PopLabeledInputField
import com.pop.components.compose_components.PopPrefixInputField
import com.pop.components.compose_components.PopTabBar
import  com.pop.components.compose_components.ToggleOption
import com.pop.components.theme.PopColors
import com.pop.components.theme.PopTheme
import com.pop.components.theme.TextColors
import com.pop.components.compose_components.PopCheckbox
import com.pop.components.compose_components.PopRadioButton
import com.pop.components.theme.FlashTypography
import androidx.compose.ui.text.AnnotatedString

@Composable
fun InputFieldDemoScreen() {
    var emailValue by remember { mutableStateOf("") }
    var emailWithLabelValue by remember { mutableStateOf("") }
    var emailWithErrorValue by remember { mutableStateOf("") }
    var disabledEmailValue by remember { mutableStateOf("") }
    
    var labeledEmailValue by remember { mutableStateOf("") }
    var labeledEmailWithErrorValue by remember { mutableStateOf("") }
    var labeledDisabledEmailValue by remember { mutableStateOf("") }
    var labeledPasswordValue by remember { mutableStateOf("") }
    var labeledPhoneValue by remember { mutableStateOf("") }
    
    // New phone input states
    var phoneValue by remember { mutableStateOf("") }
    var phoneWithErrorValue by remember { mutableStateOf("") }
    var disabledPhoneValue by remember { mutableStateOf("") }

    var selectedOption by remember { mutableStateOf(ToggleOption.First) }
    
    // States for radio button and checkbox
    var isRadioSelected by remember { mutableStateOf(false) }
    var isCheckboxChecked by remember { mutableStateOf(false) }

    PopTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PopColors.Neutral.N1)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Standard Input Fields",
                style = TextStyle(
                    fontFamily = FlashTypography.figtree,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextColors.Primary.Default
                )
            )

            // Basic email input
            PopInputField(
                value = emailValue,
                onValueChange = { emailValue = it },
                placeholder = "Email Address",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            // Email with label
            PopInputField(
                value = emailWithLabelValue,
                onValueChange = { emailWithLabelValue = it },
                label = "Email Address",
                placeholder = "Enter your email",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            // Email with error
            PopInputField(
                value = emailWithErrorValue,
                onValueChange = { emailWithErrorValue = it },
                label = "Email Address",
                placeholder = "Enter your email",
                isError = true,
                errorMessage = "Please enter a valid email address",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            // Disabled email input
            PopInputField(
                value = disabledEmailValue,
                onValueChange = { disabledEmailValue = it },
                label = "Email Address",
                placeholder = "Enter your email",
                enabled = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Phone Input Fields",
                style = TextStyle(
                    fontFamily = FlashTypography.figtree,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextColors.Primary.Default
                )
            )

            // Basic phone input
            PopPrefixInputField(
                value = phoneValue,
                onValueChange = { phoneValue = it }
            )

            // Phone with error
            PopPrefixInputField(
                value = phoneWithErrorValue,
                onValueChange = { phoneWithErrorValue = it },
                isError = true,
                errorMessage = "Please enter a valid phone number"
            )

            // Disabled phone input
            PopPrefixInputField(
                value = disabledPhoneValue,
                onValueChange = { disabledPhoneValue = it },
                enabled = false
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Labeled Input Fields",
                style = TextStyle(
                    fontFamily = FlashTypography.figtree,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextColors.Primary.Default
                )
            )

            // Basic labeled email input
            PopLabeledInputField(
                value = labeledEmailValue,
                onValueChange = { labeledEmailValue = it },
                label = "Email Address",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            // Labeled password input
            PopLabeledInputField(
                value = labeledPasswordValue,
                onValueChange = { labeledPasswordValue = it },
                label = "Password",
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )

            // Labeled phone input
            PopLabeledInputField(
                value = labeledPhoneValue,
                onValueChange = { labeledPhoneValue = it },
                label = "Phone Number",
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            )

            // Labeled email with error
            PopLabeledInputField(
                value = labeledEmailWithErrorValue,
                onValueChange = { labeledEmailWithErrorValue = it },
                label = "Email Address",
                isError = true,
                errorMessage = "Please enter a valid email address",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            // Disabled labeled email input
            PopLabeledInputField(
                value = labeledDisabledEmailValue,
                onValueChange = { labeledDisabledEmailValue = it },
                label = "Email Address",
                enabled = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Selection Controls",
                style = TextStyle(
                    fontFamily = FlashTypography.figtree,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = TextColors.Primary.Default
                )
            )

            PopTabBar(
                firstOptionText = "Bank accounts",
                secondOptionText = "RuPay Credit Card",
                selectedOption = selectedOption,
                onOptionSelected = { selectedOption = it },
                modifier = Modifier.fillMaxWidth()
            )

            // Radio Button
            PopRadioButton(
                selected = isRadioSelected,
                onSelect = { isRadioSelected = true },
                label = "Radio Label",
                supportingText = "Optional supporting text",
                modifier = Modifier.fillMaxWidth()
            )

            PopRadioButton(
                selected = isRadioSelected,
                enabled = false,
                onSelect = { isRadioSelected = true },
                label = "Radio Label",
                supportingText = "Optional supporting text",
                modifier = Modifier.fillMaxWidth()
            )

            // Checkbox
            PopCheckbox(
                checked = isCheckboxChecked,
                onCheckedChange = { isCheckboxChecked = it },
                label = AnnotatedString("Checkbox Label"),
                supportingText = "Optional supporting text",
                modifier = Modifier.fillMaxWidth()
            )

            PopCheckbox(
                checked = isCheckboxChecked,
                enabled = false,
                onCheckedChange = { isCheckboxChecked = it },
                label = AnnotatedString("Checkbox Label"),
                supportingText = "Optional supporting text",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun InputFieldDemoScreenPreview() {
    InputFieldDemoScreen()
} 