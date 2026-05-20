package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons as MaterialIcons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pop.components.ds_components.PopInputFieldV2
import com.pop.components.models.SearchBorderStyle
import com.pop.components.models.InputFieldStatus
import com.pop.components.models.UnderlineNakedSmallConfig
import com.pop.components.models.UnderlineNakedLargeConfig
import com.pop.components.models.MobileInputFieldConfig
import com.pop.components.models.BasicInputFieldConfig
import com.pop.components.models.SearchInputFieldConfig
import com.pop.components.models.SmallInputFieldConfig
import com.pop.components.ds_components.PopChip
import com.pop.components.ds_components.PopChipVariant
import com.pop.components.models.PopChipConfig
import com.pop.components.theme.IconSize
import com.pop.components.theme.IconStyle
import com.pop.components.theme.Icons
import com.pop.components.theme.PopIconConfig
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor

/**
 * Preview showing all PopInputField states in linear layout
 */
@Preview(
    name = "PopInputField All States",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldAllStates() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // No message states
        var value1 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value1,
                onValueChange = { value1 = it },
                title = "Title",
                placeholder = "Placeholder - Unfocused Empty"
            )
        )

        var value2 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value2,
                onValueChange = { value2 = it },
                title = "Title",
                placeholder = "Placeholder - Focused Empty"
            )
        )

        var value3 by remember { mutableStateOf("Filled") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value3,
                onValueChange = { value3 = it },
                title = "Title",
                placeholder = "Placeholder - Focused Filled"
            )
        )

        var value4 by remember { mutableStateOf("Filled") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value4,
                onValueChange = { value4 = it },
                title = "Title",
                placeholder = "Placeholder - Unfocused Filled"
            )
        )

        // Error message states
        var value5 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value5,
                onValueChange = { value5 = it },
                title = "Title",
                placeholder = "Placeholder",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"
            )
        )

        var value6 by remember { mutableStateOf("Filled") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value6,
                onValueChange = { value6 = it },
                title = "Title",
                placeholder = "Placeholder",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"
            )
        )

        // Success message states
        var value7 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value7,
                onValueChange = { value7 = it },
                title = "Title",
                placeholder = "Placeholder",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"
            )
        )

        var value8 by remember { mutableStateOf("Filled") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value8,
                onValueChange = { value8 = it },
                title = "Title",
                placeholder = "Placeholder",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"
            )
        )

        // Disabled states
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = "",
                onValueChange = {},
                title = "Title",
                placeholder = "Placeholder",
                enabled = false
            )
        )

        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = "Filled",
                onValueChange = {},
                title = "Title",
                placeholder = "Placeholder",
                enabled = false
            )
        )
    }
}

/**
 * Preview showing single PopInputField with error state
 */
@Preview(
    name = "PopInputField Error",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldError() {
    var value by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value,
                onValueChange = { value = it },
                title = "Title",
                placeholder = "Placeholder",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"
            )
        )
    }
}

/**
 * Preview showing single PopInputField with success state
 */
@Preview(
    name = "PopInputField Success",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldSuccess() {
    var value by remember { mutableStateOf("Filled") }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value,
                onValueChange = { value = it },
                title = "Title",
                placeholder = "Placeholder",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"
            )
        )
    }
}

/**
 * Preview showing single PopInputField disabled state
 */
@Preview(
    name = "PopInputField Disabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldDisabled() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = "Filled",
                onValueChange = {},
                title = "Title",
                placeholder = "Placeholder",
                enabled = false
            )
        )
    }
}

/**
 * Preview showing PopInputFieldV2 UnderlineNakedSmall with start icon variants
 */
@Preview(
    name = "PopInputField UnderlineNakedSmall Start Icon",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldUnderlineNakedSmallStartIcon() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Check icon
        var value1 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value1,
                onValueChange = { value1 = it },
                title = "Email",
                placeholder = "Enter your email",
                startIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Check"
                )
            )
        )

        // Share icon
        var value2 by remember { mutableStateOf("John Doe") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value2,
                onValueChange = { value2 = it },
                title = "Name",
                placeholder = "Enter your name",
                startIcon = PopIconConfig(
                    iconName = Icons.Share05,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Share"
                )
            )
        )

        // ChevronLeft icon
        var value3 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value3,
                onValueChange = { value3 = it },
                title = "Password",
                placeholder = "Enter password",
                startIcon = PopIconConfig(
                    iconName = Icons.ChevronLeft,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Back"
                )
            )
        )

        // Cross icon
        var value4 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value4,
                onValueChange = { value4 = it },
                title = "Search",
                placeholder = "Search...",
                startIcon = PopIconConfig(
                    iconName = Icons.Cross,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Clear"
                )
            )
        )

        // With error status message
        var value5 by remember { mutableStateOf("invalid@email") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value5,
                onValueChange = { value5 = it },
                title = "Email",
                placeholder = "Enter your email",
                startIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Check"
                ),
                status = InputFieldStatus.Error,
                statusMessage = "Invalid email format"
            )
        )

        // With success status message
        var value6 by remember { mutableStateOf("user@example.com") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value6,
                onValueChange = { value6 = it },
                title = "Email",
                placeholder = "Enter your email",
                startIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Check"
                ),
                status = InputFieldStatus.Success,
                statusMessage = "Email verified"
            )
        )
    }
}

/**
 * Preview showing PopInputFieldV2 UnderlineNakedSmall with end icon variants
 */
@Preview(
    name = "PopInputField UnderlineNakedSmall End Icon",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldUnderlineNakedSmallEndIcon() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Check icon at end
        var value1 by remember { mutableStateOf("password123") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value1,
                onValueChange = { value1 = it },
                title = "Password",
                placeholder = "Enter password",
                endIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Check"
                )
            )
        )

        // Cross icon at end
        var value2 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value2,
                onValueChange = { value2 = it },
                title = "Search",
                placeholder = "Search...",
                endIcon = PopIconConfig(
                    iconName = Icons.Cross,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Clear"
                )
            )
        )

        // With error status message
        var value3 by remember { mutableStateOf("weak") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value3,
                onValueChange = { value3 = it },
                title = "Password",
                placeholder = "Enter password",
                endIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Check"
                ),
                status = InputFieldStatus.Error,
                statusMessage = "Password must be at least 8 characters"
            )
        )

        // With success status message
        var value4 by remember { mutableStateOf("strongpassword123") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value4,
                onValueChange = { value4 = it },
                title = "Password",
                placeholder = "Enter password",
                endIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Check"
                ),
                status = InputFieldStatus.Success,
                statusMessage = "Password is strong"
            )
        )
    }
}

/**
 * Preview showing PopInputFieldV2 UnderlineNakedSmall with end slot variants
 */
@Preview(
    name = "PopInputField UnderlineNakedSmall End Slot",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldUnderlineNakedSmallEndSlot() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // With end slot button
        var value1 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value1,
                onValueChange = { value1 = it },
                title = "Enter code",
                placeholder = "Enter verification code",
                endSlot = {
                    TextButton(
                        onClick = { /* Handle action */ }
                    ) {
                        Text(
                            text = "Send",
                            color = TextColor.Primary
                        )
                    }
                }
            )
        )

        // With start icon and end slot
        var value2 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value2,
                onValueChange = { value2 = it },
                title = "Search",
                placeholder = "Search...",
                startIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Search"
                ),
                endSlot = {
                    Icon(
                        imageVector = MaterialIcons.Filled.CheckCircle,
                        contentDescription = "Apply",
                        tint = TextColor.Primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
        )

        // With end icon and end slot
        var value3 by remember { mutableStateOf("John Doe") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value3,
                onValueChange = { value3 = it },
                title = "Name",
                placeholder = "Enter your name",
                endIcon = PopIconConfig(
                    iconName = Icons.Cross,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Clear"
                ),
                endSlot = {
                    TextButton(
                        onClick = { /* Handle action */ }
                    ) {
                        Text(
                            text = "Save",
                            color = TextColor.Primary
                        )
                    }
                }
            )
        )

        // With both start icon, end icon, and end slot
        var value4 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value4,
                onValueChange = { value4 = it },
                title = "Email",
                placeholder = "Enter your email",
                startIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Email"
                ),
                endIcon = PopIconConfig(
                    iconName = Icons.Cross,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Clear"
                ),
                endSlot = {
                    Icon(
                        imageVector = MaterialIcons.Filled.CheckCircle,
                        contentDescription = "Submit",
                        tint = TextColor.Primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
        )

        // With error status message
        var value5 by remember { mutableStateOf("123") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value5,
                onValueChange = { value5 = it },
                title = "Enter code",
                placeholder = "Enter verification code",
                endSlot = {
                    TextButton(
                        onClick = { /* Handle action */ }
                    ) {
                        Text(
                            text = "Send",
                            color = TextColor.Primary
                        )
                    }
                },
                status = InputFieldStatus.Error,
                statusMessage = "Invalid verification code"
            )
        )

        // With success status message
        var value6 by remember { mutableStateOf("123456") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value6,
                onValueChange = { value6 = it },
                title = "Enter code",
                placeholder = "Enter verification code",
                endSlot = {
                    TextButton(
                        onClick = { /* Handle action */ }
                    ) {
                        Text(
                            text = "Send",
                            color = TextColor.Primary
                        )
                    }
                },
                status = InputFieldStatus.Success,
                statusMessage = "Code verified successfully"
            )
        )

        // With start icon, end slot, and error status
        var value7 by remember { mutableStateOf("invalid") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value7,
                onValueChange = { value7 = it },
                title = "Search",
                placeholder = "Search...",
                startIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Search"
                ),
                endSlot = {
                    Icon(
                        imageVector = MaterialIcons.Filled.CheckCircle,
                        contentDescription = "Apply",
                        tint = TextColor.Primary,
                        modifier = Modifier.size(20.dp)
                    )
                },
                status = InputFieldStatus.Error,
                statusMessage = "No results found"
            )
        )

        // With start icon, end slot, and success status
        var value8 by remember { mutableStateOf("found") }
        PopInputFieldV2(
            config = UnderlineNakedSmallConfig(
                value = value8,
                onValueChange = { value8 = it },
                title = "Search",
                placeholder = "Search...",
                startIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Search"
                ),
                endSlot = {
                    Icon(
                        imageVector = MaterialIcons.Filled.CheckCircle,
                        contentDescription = "Apply",
                        tint = TextColor.Primary,
                        modifier = Modifier.size(20.dp)
                    )
                },
                status = InputFieldStatus.Success,
                statusMessage = "Search completed"
            )
        )
    }
}

/**
 * Preview showing all PopInputFieldV2 UnderlineNakedLarge states in linear layout
 */
@Preview(
    name = "PopInputField Large All States",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldLargeAllStates() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // No message states
        var value1 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = value1,
                onValueChange = { value1 = it },
                placeholder = "Placeholder - Unfocused Empty"
            )
        )

        var value2 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = value2,
                onValueChange = { value2 = it },
                placeholder = "Placeholder - Focused Empty"
            )
        )

        var value3 by remember { mutableStateOf("?1,234") }
        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = value3,
                onValueChange = { value3 = it },
                placeholder = "Placeholder - Focused Filled"
            )
        )

        var value4 by remember { mutableStateOf("?1,234") }
        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = value4,
                onValueChange = { value4 = it },
                placeholder = "Placeholder - Unfocused Filled"
            )
        )

        // Error message states
        var value5 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = value5,
                onValueChange = { value5 = it },
                placeholder = "Placeholder",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"
            )
        )

        var value6 by remember { mutableStateOf("?1,234") }
        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = value6,
                onValueChange = { value6 = it },
                placeholder = "Placeholder",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"
            )
        )

        // Success message states
        var value7 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = value7,
                onValueChange = { value7 = it },
                placeholder = "Placeholder",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"
            )
        )

        var value8 by remember { mutableStateOf("?1,234") }
        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = value8,
                onValueChange = { value8 = it },
                placeholder = "Placeholder",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"
            )
        )

        // Disabled states
        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = "",
                onValueChange = {},
                placeholder = "Placeholder",
                enabled = false
            )
        )

        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = "?1,234",
                onValueChange = {},
                placeholder = "Placeholder",
                enabled = false
            )
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Large with error state
 */
@Preview(
    name = "PopInputField Large Error",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldLargeError() {
    var value by remember { mutableStateOf("?1,234") }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = value,
                onValueChange = { value = it },
                placeholder = "Placeholder",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"
            )
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Large with success state
 */
@Preview(
    name = "PopInputField Large Success",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldLargeSuccess() {
    var value by remember { mutableStateOf("?1,234") }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = value,
                onValueChange = { value = it },
                placeholder = "Placeholder",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"
            )
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Large disabled state
 */
@Preview(
    name = "PopInputField Large Disabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldLargeDisabled() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = UnderlineNakedLargeConfig(
                value = "?1,234",
                onValueChange = {},
                placeholder = "Placeholder",
                enabled = false
            )
        )
    }
}

/**
 * Preview showing all PopInputFieldV2 MobileInputField states in linear layout
 */
@Preview(
    name = "PopInputField Mobile All States",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldMobileAllStates() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // No message states
        var value1 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = value1,
                onValueChange = { value1 = it },
                title = "Mobile number",
                placeholder = "99999 99999"
            )
        )

        var value2 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = value2,
                onValueChange = { value2 = it },
                title = "Mobile number",
                placeholder = "99999 99999"
            )
        )

        var value3 by remember { mutableStateOf("99999 99999") }
        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = value3,
                onValueChange = { value3 = it },
                title = "Mobile number",
                placeholder = "99999 99999"
            )
        )

        var value4 by remember { mutableStateOf("88888 88888") }
        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = value4,
                onValueChange = { value4 = it },
                title = "Mobile number",
                placeholder = "99999 99999"
            )
        )

        // Error message states
        var value5 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = value5,
                onValueChange = { value5 = it },
                title = "Mobile number",
                placeholder = "99999 99999",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"
            )
        )

        var value6 by remember { mutableStateOf("99999 99999") }
        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = value6,
                onValueChange = { value6 = it },
                title = "Mobile number",
                placeholder = "99999 99999",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"
            )
        )

        // Success message states
        var value7 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = value7,
                onValueChange = { value7 = it },
                title = "Mobile number",
                placeholder = "99999 99999",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"
            )
        )

        var value8 by remember { mutableStateOf("88888 88888") }
        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = value8,
                onValueChange = { value8 = it },
                title = "Mobile number",
                placeholder = "99999 99999",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"
            )
        )

        // Disabled states
        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = "",
                onValueChange = {},
                title = "Mobile number",
                placeholder = "99999 99999",
                enabled = false
            )
        )

        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = "88888 88888",
                onValueChange = {},
                title = "Mobile number",
                placeholder = "99999 99999",
                enabled = false
            )
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Mobile with error state
 */
@Preview(
    name = "PopInputField Mobile Error",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldMobileError() {
    var value by remember { mutableStateOf("99999 99999") }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = value,
                onValueChange = { value = it },
                title = "Mobile number",
                placeholder = "99999 99999",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"
            )
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Mobile with success state
 */
@Preview(
    name = "PopInputField Mobile Success",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldMobileSuccess() {
    var value by remember { mutableStateOf("88888 88888") }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = value,
                onValueChange = { value = it },
                title = "Mobile number",
                placeholder = "99999 99999",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"
            )
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Mobile disabled state
 */
@Preview(
    name = "PopInputField Mobile Disabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldMobileDisabled() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = MobileInputFieldConfig(
                value = "88888 88888",
                onValueChange = {},
                title = "Mobile number",
                placeholder = "99999 99999",
                enabled = false
            )
        )
    }
}

/**
 * Preview showing all PopInputFieldV2 Basic states in linear layout
 */
@Preview(
    name = "PopInputField Basic All States",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldBasicAllStates() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // No message states
        var value1 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value1,
                onValueChange = { value1 = it },
                hintText = "Title",
                placeholder = "Placeholder"
            )
        )

        var value2 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value2,
                onValueChange = { value2 = it },
                hintText = "Title",
                placeholder = "Placeholder"
            )
        )

        var value3 by remember { mutableStateOf("Filled") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value3,
                onValueChange = { value3 = it },
                hintText = "Title",
                placeholder = "Placeholder"
            )
        )

        var value4 by remember { mutableStateOf("Filled") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value4,
                onValueChange = { value4 = it },
                hintText = "Title",
                placeholder = "Placeholder"
            )
        )

        // Error message states
        var value5 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value5,
                onValueChange = { value5 = it },
                hintText = "Title",
                placeholder = "Placeholder",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"

            )
        )

        var value6 by remember { mutableStateOf("Filled") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value6,
                onValueChange = { value6 = it },
                hintText = "Title",
                placeholder = "Placeholder",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"

            )
        )

        // Success message states
        var value7 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value7,
                onValueChange = { value7 = it },
                hintText = "Title",
                placeholder = "Placeholder",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"

            )
        )

        var value8 by remember { mutableStateOf("Filled") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value8,
                onValueChange = { value8 = it },
                hintText = "Title",
                placeholder = "Placeholder",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"

            )
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Basic with error state
 */
@Preview(
    name = "PopInputField Basic Error",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldBasicError() {
    var value by remember { mutableStateOf("Filled") }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value,
                onValueChange = { value = it },
                placeholder = "Hint text",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"
            )
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Basic with success state
 */
@Preview(
    name = "PopInputField Basic Success",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldBasicSuccess() {
    var value by remember { mutableStateOf("Filled") }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value,
                onValueChange = { value = it },
                placeholder = "Hint text",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"
            )
        )
    }
}

/**
 * Preview showing PopInputFieldV2 Basic with start icon variants
 */
@Preview(
    name = "PopInputField Basic Start Icon",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldBasicStartIcon() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Check icon
        var value1 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value1,
                onValueChange = { value1 = it },
                hintText = "Email",
                placeholder = "Enter your email",
                startIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Check"

                )
            )
        )

        // Share icon
        var value2 by remember { mutableStateOf("John Doe") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value2,
                onValueChange = { value2 = it },
                hintText = "Name",
                placeholder = "Enter your name",
                startIcon = PopIconConfig(
                    iconName = Icons.Share05,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Share"

                )
            )
        )

        // ChevronLeft icon
        var value3 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value3,
                onValueChange = { value3 = it },
                hintText = "Password",
                placeholder = "Enter password",
                startIcon = PopIconConfig(
                    iconName = Icons.ChevronLeft,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Back"

                )
            )
        )

        // Cross icon
        var value4 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value4,
                onValueChange = { value4 = it },
                hintText = "Search",
                placeholder = "Search...",
                startIcon = PopIconConfig(
                    iconName = Icons.Cross,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Clear"

                )
            )
        )
    }
}

/**
 * Preview showing PopInputFieldV2 Basic with end icon variants
 */
@Preview(
    name = "PopInputField Basic End Icon",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldBasicEndIcon() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Check icon at end
        var value1 by remember { mutableStateOf("password123") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value1,
                onValueChange = { value1 = it },
                hintText = "Password",
                placeholder = "Enter password",
                endIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Check"

                )
            )
        )

        // Cross icon at end
        var value2 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value2,
                onValueChange = { value2 = it },
                hintText = "Search",
                placeholder = "Search...",
                endIcon = PopIconConfig(
                    iconName = Icons.Cross,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Clear"

                )
            )
        )
    }
}

/**
 * Preview showing PopInputFieldV2 Basic with both start and end icons
 */
@Preview(
    name = "PopInputField Basic Both Icons",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldBasicBothIcons() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Check with Cross icon
        var value1 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value1,
                onValueChange = { value1 = it },
                hintText = "Email",
                placeholder = "Enter your email",
                startIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Check"
                ),
                endIcon = PopIconConfig(
                    iconName = Icons.Cross,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Clear"
                )
            )
        )

        // Share with Check icon
        var value2 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value2,
                onValueChange = { value2 = it },
                hintText = "Password",
                placeholder = "Enter password",
                startIcon = PopIconConfig(
                    iconName = Icons.Share05,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Share"
                ),
                endIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Check"
                )
            )
        )

        // ChevronLeft with Cross
        var value3 by remember { mutableStateOf("John Doe") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value3,
                onValueChange = { value3 = it },
                hintText = "Name",
                placeholder = "Enter your name",
                startIcon = PopIconConfig(
                    iconName = Icons.ChevronLeft,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Back"
                ),
                endIcon = PopIconConfig(
                    iconName = Icons.Cross,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Clear"
                )
            )
        )
    }
}

/**
 * Preview showing PopInputFieldV2 Basic with end slot variants
 */
@Preview(
    name = "PopInputField Basic End Slot",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldBasicEndSlot() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // With end slot button
        var value1 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value1,
                onValueChange = { value1 = it },
                hintText = "Enter code",
                placeholder = "Enter verification code",
                endSlot = {
                    TextButton(
                        onClick = { /* Handle action */ }
                    ) {
                        Text(
                            text = "Send",
                            color = TextColor.Primary
                        )
                    }
                }
            )
        )

        // With start icon and end slot
        var value2 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value2,
                onValueChange = { value2 = it },
                hintText = "Search",
                placeholder = "Search...",
                startIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Search"
                ),
                endSlot = {
                    Icon(
                        imageVector = MaterialIcons.Filled.CheckCircle,
                        contentDescription = "Apply",
                        tint = TextColor.Primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
        )

        // With end icon and end slot
        var value3 by remember { mutableStateOf("John Doe") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value3,
                onValueChange = { value3 = it },
                hintText = "Name",
                placeholder = "Enter your name",
                endIcon = PopIconConfig(
                    iconName = Icons.Cross,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Clear"
                ),
                endSlot = {
                    TextButton(
                        onClick = { /* Handle action */ }
                    ) {
                        Text(
                            text = "Save",
                            color = TextColor.Primary
                        )
                    }
                }
            )
        )

        // With both start icon, end icon, and end slot
        var value4 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = BasicInputFieldConfig(
                value = value4,
                onValueChange = { value4 = it },
                hintText = "Email",
                placeholder = "Enter your email",
                startIcon = PopIconConfig(
                    iconName = Icons.Check,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Email"
                ),
                endIcon = PopIconConfig(
                    iconName = Icons.Cross,
                    style = IconStyle.Filled,
                    size = IconSize.Small,
                    tint = TextColor.Secondary,
                    contentDescription = "Clear"
                ),
                endSlot = {
                    Icon(
                        imageVector = MaterialIcons.Filled.CheckCircle,
                        contentDescription = "Submit",
                        tint = TextColor.Primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            )
        )
    }
}

/**
 * Preview showing all PopInputFieldV2 Search states in linear layout
 */
@Preview(
    name = "PopInputField Search All States",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldSearchAllStates() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Subtle Border Style - No message states
        var value1 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value1,
                onValueChange = { value1 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.Subtle

            )
        )

        var value2 by remember { mutableStateOf("Search item!") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value2,
                onValueChange = { value2 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.Subtle

            )
        )

        // Subtle Border Style - Error states
        var value3 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value3,
                onValueChange = { value3 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.Subtle,
                status = InputFieldStatus.Error,
                statusMessage = "Error message"

            )
        )

        var value4 by remember { mutableStateOf("Search item!") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value4,
                onValueChange = { value4 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.Subtle,
                status = InputFieldStatus.Error,
                statusMessage = "Error message"

            )
        )

        // Subtle Border Style - Success states
        var value5 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value5,
                onValueChange = { value5 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.Subtle,
                status = InputFieldStatus.Success,
                statusMessage = "Success message"

            )
        )

        var value6 by remember { mutableStateOf("Search item!") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value6,
                onValueChange = { value6 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.Subtle,
                status = InputFieldStatus.Success,
                statusMessage = "Success message"

            )
        )

        // Subtle Border Style - Disabled states
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = "",
                onValueChange = {},
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.Subtle,
                enabled = false

            )
        )

        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = "Search item!",
                onValueChange = {},
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.Subtle,
                enabled = false

            )
        )

        // DefinedThin Border Style - No message states
        var value7 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value7,
                onValueChange = { value7 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.DefinedThin

            )
        )

        var value8 by remember { mutableStateOf("Search item!") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value8,
                onValueChange = { value8 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.DefinedThin

            )
        )

        // DefinedThin Border Style - Error states
        var value9 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value9,
                onValueChange = { value9 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.DefinedThin,
                status = InputFieldStatus.Error,
                statusMessage = "Error message"

            )
        )

        var value10 by remember { mutableStateOf("Search item!") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value10,
                onValueChange = { value10 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.DefinedThin,
                status = InputFieldStatus.Error,
                statusMessage = "Error message"

            )
        )

        // DefinedThin Border Style - Success states
        var value11 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value11,
                onValueChange = { value11 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.DefinedThin,
                status = InputFieldStatus.Success,
                statusMessage = "Success message"

            )
        )

        var value12 by remember { mutableStateOf("Search item!") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value12,
                onValueChange = { value12 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.DefinedThin,
                status = InputFieldStatus.Success,
                statusMessage = "Success message"

            )
        )

        // DefinedThin Border Style - Disabled states
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = "",
                onValueChange = {},
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.DefinedThin,
                enabled = false

            )
        )

        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = "Search item!",
                onValueChange = {},
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.DefinedThin,
                enabled = false

            )
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Search with error state
 */
@Preview(
    name = "PopInputField Search Error",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldSearchError() {
    var value by remember { mutableStateOf("Search item!") }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value,
                onValueChange = { value = it },
                placeholder = "Search",
                status = InputFieldStatus.Error,
                statusMessage = "Error message"

            )
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Search with success state
 */
@Preview(
    name = "PopInputField Search Success",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldSearchSuccess() {
    var value by remember { mutableStateOf("Search item!") }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value,
                onValueChange = { value = it },
                placeholder = "Search",
                status = InputFieldStatus.Success,
                statusMessage = "Success message"

            )
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Search disabled state
 */
@Preview(
    name = "PopInputField Search Disabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldSearchDisabled() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = "Search item!",
                onValueChange = {},
                placeholder = "Search",
                enabled = false

            )
        )
    }
}

/**
 * Preview showing PopInputFieldV2 Search with border style variants
 */
@Preview(
    name = "PopInputField Search Border Styles",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldSearchBorderStyles() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Subtle border style
        var value1 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value1,
                onValueChange = { value1 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.Subtle

            )
        )

        var value2 by remember { mutableStateOf("Search item!") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value2,
                onValueChange = { value2 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.Subtle

            )
        )

        // DefinedThin border style
        var value3 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value3,
                onValueChange = { value3 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.DefinedThin

            )
        )

        var value4 by remember { mutableStateOf("Search item!") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value4,
                onValueChange = { value4 = it },
                placeholder = "Search",
                searchBorderStyle = SearchBorderStyle.DefinedThin

            )
        )
    }
}

/**
 * Preview showing PopInputFieldV2 Search with trailing chip
 */
@Preview(
    name = "PopInputField Search With End Slot",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldSearchWithEndSlot() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Without text, with chip
        var value1 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value1,
                onValueChange = { value1 = it },
                placeholder = "Search",
                endSlot = {
                    PopChip(
                        config = PopChipConfig(
                            text = "123",
                            variant = PopChipVariant.WithDropdown,
                            onClick = {}
                        )
                    )
                }
            )
        )

        // With text and clear button, with chip
        var value2 by remember { mutableStateOf("Search item!") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value2,
                onValueChange = { value2 = it },
                placeholder = "Search",
                endSlot = {
                    PopChip(
                        config = PopChipConfig(
                            text = "123",
                            variant = PopChipVariant.WithDropdown,
                            onClick = {}
                        )
                    )
                }
            )
        )

        // With error state and chip
        var value3 by remember { mutableStateOf("Search item!") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value3,
                onValueChange = { value3 = it },
                placeholder = "Search",
                status = InputFieldStatus.Error,
                statusMessage = "Error message",
                endSlot = {
                    PopChip(
                        config = PopChipConfig(
                            text = "123",
                            variant = PopChipVariant.WithDropdown,
                            onClick = {}
                        )
                    )
                }
            )
        )

        // With success state and chip
        var value4 by remember { mutableStateOf("Search item!") }
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = value4,
                onValueChange = { value4 = it },
                placeholder = "Search",
                status = InputFieldStatus.Success,
                statusMessage = "Success message",
                endSlot = {
                    PopChip(
                        config = PopChipConfig(
                            text = "123",
                            variant = PopChipVariant.WithDropdown,
                            onClick = {}
                        )
                    )
                }
            )
        )

        // Disabled with chip
        PopInputFieldV2(
            config = SearchInputFieldConfig(
                value = "Search item!",
                onValueChange = {},
                placeholder = "Search",
                enabled = false,
                endSlot = {
                    PopChip(
                        config = PopChipConfig(
                            text = "123",
                            variant = PopChipVariant.WithDropdown,
                            enabled = false
                        )
                    )
                }
            )
        )
    }
}

/**
 * Preview showing all PopInputFieldV2 Small states in linear layout
 */
@Preview(
    name = "PopInputField Small All States",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldSmallAllStates() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Default state - inactive empty (shows title)
        PopInputFieldV2(
            config = SmallInputFieldConfig(
                value = "",
                onValueChange = {},
                title = "Add note",
                placeholder = "Eg. Lunch"
            ),
            modifier = Modifier.width(100.dp)
        )

        // Default state - active empty (shows placeholder)
        var value2 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = SmallInputFieldConfig(
                value = value2,
                onValueChange = { value2 = it },
                title = "Add note",
                placeholder = "Eg. Lunch"
            ),
            modifier = Modifier.width(100.dp)
        )

        // Default state - active filled
        var value3 by remember { mutableStateOf("Dinner") }
        PopInputFieldV2(
            config = SmallInputFieldConfig(
                value = value3,
                onValueChange = { value3 = it },
                title = "Add note",
                placeholder = "Eg. Lunch"
            ),
            modifier = Modifier.width(100.dp)
        )

        // Default state - inactive filled
        var value4 by remember { mutableStateOf("Dinner") }
        PopInputFieldV2(
            config = SmallInputFieldConfig(
                value = value4,
                onValueChange = { value4 = it },
                title = "Add note",
                placeholder = "Eg. Lunch"
            ),
            modifier = Modifier.width(100.dp)
        )

        // Error state - empty
        var value5 by remember { mutableStateOf("") }
        PopInputFieldV2(
            config = SmallInputFieldConfig(
                value = value5,
                onValueChange = { value5 = it },
                title = "Add note",
                placeholder = "Eg. Lunch",
                status = InputFieldStatus.Error,
                statusMessage = "'.' not allowed"
            ),
            modifier = Modifier.width(100.dp)
        )

        // Error state - filled
        var value6 by remember { mutableStateOf("Dinner") }
        PopInputFieldV2(
            config = SmallInputFieldConfig(
                value = value6,
                onValueChange = { value6 = it },
                title = "Add note",
                placeholder = "Eg. Lunch",
                status = InputFieldStatus.Error,
                statusMessage = "'.' not allowed"
            ),
            modifier = Modifier.width(100.dp)
        )

        // Success state - filled
        var value7 by remember { mutableStateOf("Dinner") }
        PopInputFieldV2(
            config = SmallInputFieldConfig(
                value = value7,
                onValueChange = { value7 = it },
                title = "Add note",
                placeholder = "Eg. Lunch",
                status = InputFieldStatus.Success,
                statusMessage = "Promo applied"
            ),
            modifier = Modifier.width(100.dp)
        )

        // Disabled state - empty
        PopInputFieldV2(
            config = SmallInputFieldConfig(
                value = "",
                onValueChange = {},
                title = "Add note",
                placeholder = "Eg. Lunch",
                enabled = false
            ),
            modifier = Modifier.width(100.dp)
        )

        // Disabled state - filled
        PopInputFieldV2(
            config = SmallInputFieldConfig(
                value = "Dinner",
                onValueChange = {},
                title = "Add note",
                placeholder = "Eg. Lunch",
                enabled = false
            ),
            modifier = Modifier.width(100.dp)
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Small with error state
 */
@Preview(
    name = "PopInputField Small Error",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldSmallError() {
    var value by remember { mutableStateOf("Dinner") }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PopInputFieldV2(
            config = SmallInputFieldConfig(
                value = value,
                onValueChange = { value = it },
                title = "Add note",
                placeholder = "Eg. Lunch",
                status = InputFieldStatus.Error,
                statusMessage = "'.' not allowed"
            ),
            modifier = Modifier.width(100.dp)
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Small with success state
 */
@Preview(
    name = "PopInputField Small Success",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldSmallSuccess() {
    var value by remember { mutableStateOf("Dinner") }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PopInputFieldV2(
            config = SmallInputFieldConfig(
                value = value,
                onValueChange = { value = it },
                title = "Add note",
                placeholder = "Eg. Lunch",
                status = InputFieldStatus.Success,
                statusMessage = "Promo applied"
            ),
            modifier = Modifier.width(100.dp)
        )
    }
}

/**
 * Preview showing single PopInputFieldV2 Small disabled state
 */
@Preview(
    name = "PopInputField Small Disabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopInputFieldSmallDisabled() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PopInputFieldV2(
            config = SmallInputFieldConfig(
                value = "Dinner",
                onValueChange = {},
                title = "Add note",
                placeholder = "Eg. Lunch",
                enabled = false
            ),
            modifier = Modifier.width(100.dp)
        )
    }
}
