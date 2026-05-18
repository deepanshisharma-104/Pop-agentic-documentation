package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.PopOtpField
import com.pop.components.models.DiscreteConfig
import com.pop.components.models.DiscreteNakedConfig
import com.pop.components.models.InputFieldStatus
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import com.pop.components.theme.PopTypography

/**
 * Preview showing PopOtpField using config-based approach (recommended)
 */
@Preview(
    name = "PopOtpField Config-Based (Recommended)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldConfigBased() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Default state with info message
        var value1 by remember { mutableStateOf("") }
        val config1 = remember(value1) {
            DiscreteConfig(
                value = value1,
                onValueChange = { text, _ -> value1 = text },
                status = InputFieldStatus.Info,
                statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                onResendOtp = {},
                resendCountdownSeconds = 30
            )
        }
        PopOtpField(config = config1)

        // Error state
        var value2 by remember { mutableStateOf("123456") }
        val config2 = remember(value2) {
            DiscreteConfig(
                value = value2,
                onValueChange = { text, _ -> value2 = text },
                status = InputFieldStatus.Error,
                statusMessage = "Incorrect OTP",
                onResendOtp = {},
                resendCountdownSeconds = null
            )
        }
        PopOtpField(config = config2)

        // Success state
        var value3 by remember { mutableStateOf("123456") }
        val config3 = remember(value3) {
            DiscreteConfig(
                value = value3,
                onValueChange = { text, _ -> value3 = text },
                status = InputFieldStatus.Success,
                statusMessage = "OTP verified successfully",
                onResendOtp = null
            )
        }
        PopOtpField(config = config3)

        // DiscreteNaked with info message
        var value4 by remember { mutableStateOf("") }
        val config4 = remember(value4) {
            DiscreteNakedConfig(
                value = value4,
                onValueChange = { text, _ -> value4 = text },
                status = InputFieldStatus.Info,
                statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                onResendOtp = {},
                resendCountdownSeconds = 25
            )
        }
        PopOtpField(config = config4)
    }
}

/**
 * Preview showing all PopOtpField states with 6 digits
 */
@Preview(
    name = "PopOtpField All States (6 digits)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldAllStates() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Default state - empty
        var value1 by remember { mutableStateOf("") }
        PopOtpField(
            config = remember(value1) {
                DiscreteConfig(
                    value = value1,
                    onValueChange = { text, _ -> value1 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {},
                    resendCountdownSeconds = 30
                )
            }
        )

        // Partially filled
        var value2 by remember { mutableStateOf("123") }
        PopOtpField(
            config = remember(value2) {
                DiscreteConfig(
                    value = value2,
                    onValueChange = { text, _ -> value2 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {},
                    resendCountdownSeconds = 25
                )
            }
        )

        // Fully filled
        var value3 by remember { mutableStateOf("123456") }
        PopOtpField(
            config = remember(value3) {
                DiscreteConfig(
                    value = value3,
                    onValueChange = { text, _ -> value3 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {},
                    resendCountdownSeconds = 20
                )
            }
        )

        // Error state - empty
        var value4 by remember { mutableStateOf("") }
        PopOtpField(
            config = remember(value4) {
                DiscreteConfig(
                    value = value4,
                    onValueChange = { text, _ -> value4 = text },
                    status = InputFieldStatus.Error,
                    statusMessage = "Incorrect",
                    onResendOtp = {},
                    resendCountdownSeconds = null
                )
            }
        )

        // Error state - filled with incorrect OTP
        var value5 by remember { mutableStateOf("123456") }
        PopOtpField(
            config = remember(value5) {
                DiscreteConfig(
                    value = value5,
                    onValueChange = { text, _ -> value5 = text },
                    status = InputFieldStatus.Error,
                    statusMessage = "Incorrect",
                    onResendOtp = {},
                    resendCountdownSeconds = null
                )
            }
        )

        // Disabled state - empty
        PopOtpField(
            config = DiscreteConfig(
                value = "",
                onValueChange = { _, _ -> },
                enabled = false,
                status = InputFieldStatus.Info,
                statusMessage = "OTP sent on +91 XXXX XXXX 9999"
            )
        )

        // Disabled state - filled
        PopOtpField(
            config = DiscreteConfig(
                value = "123456",
                onValueChange = { _, _ -> },
                enabled = false,
                status = InputFieldStatus.Info,
                statusMessage = "OTP sent on +91 XXXX XXXX 9999"
            )
        )

        // With OTP sent message and resend countdown
        var value6 by remember { mutableStateOf("") }
        PopOtpField(
            config = remember(value6) {
                DiscreteConfig(
                    value = value6,
                    onValueChange = { text, _ -> value6 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {},
                    resendCountdownSeconds = 29
                )
            }
        )

        // With OTP sent message and resend enabled
        var value7 by remember { mutableStateOf("") }
        PopOtpField(
            config = remember(value7) {
                DiscreteConfig(
                    value = value7,
                    onValueChange = { text, _ -> value7 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {},
                    resendCountdownSeconds = null
                )
            }
        )
    }
}

/**
 * Preview showing PopOtpField with different digit counts
 */
@Preview(
    name = "PopOtpField Different Counts",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldDifferentCounts() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 4 digits
        var value4 by remember { mutableStateOf("1234") }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "4 digits",
                style = PopTypography.labelSmall,
                color = TextColor.Secondary
            )
            PopOtpField(
                config = remember(value4) {
                    DiscreteConfig(
                        value = value4,
                        onValueChange = { text, _ -> value4 = text },
                        otpCount = 4,
                        status = InputFieldStatus.Info,
                        statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                        onResendOtp = {},
                        resendCountdownSeconds = null
                    )
                }
            )
        }

        // 6 digits
        var value6 by remember { mutableStateOf("123456") }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "6 digits",
                style = PopTypography.labelSmall,
                color = TextColor.Secondary
            )
            PopOtpField(
                config = remember(value6) {
                    DiscreteConfig(
                        value = value6,
                        onValueChange = { text, _ -> value6 = text },
                        otpCount = 6,
                        status = InputFieldStatus.Info,
                        statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                        onResendOtp = {},
                        resendCountdownSeconds = null
                    )
                }
            )
        }
    }
}

/**
 * Preview showing single PopOtpField with error state
 */
@Preview(
    name = "PopOtpField Error",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldError() {
    var value by remember { mutableStateOf("123456") }
    var status by remember { mutableStateOf<InputFieldStatus?>(InputFieldStatus.Error) }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Error state with incorrect OTP
        PopOtpField(
            config = remember(value, status) {
                DiscreteConfig(
                    value = value,
                    onValueChange = { text, _ ->
                        value = text
                        if (status == InputFieldStatus.Error && text.isNotEmpty()) {
                            status = InputFieldStatus.Info
                        }
                    },
                    status = status,
                    statusMessage = if (status == InputFieldStatus.Error) "Incorrect" else "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {
                        status = InputFieldStatus.Info
                        value = ""
                    },
                    resendCountdownSeconds = null,
                    onErrorCleared = {
                        status = InputFieldStatus.Info
                    }
                )
            }
        )
    }
}

/**
 * Preview showing single PopOtpField disabled state
 */
@Preview(
    name = "PopOtpField Disabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldDisabled() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Disabled - empty
        PopOtpField(
            config = DiscreteConfig(
                value = "",
                onValueChange = { _, _ -> },
                enabled = false,
                status = InputFieldStatus.Info,
                statusMessage = "OTP sent on +91 XXXX XXXX 9999"
            )
        )

        // Disabled - filled
        PopOtpField(
            config = DiscreteConfig(
                value = "123456",
                onValueChange = { _, _ -> },
                enabled = false,
                status = InputFieldStatus.Info,
                statusMessage = "OTP sent on +91 XXXX XXXX 9999"
            )
        )
    }
}

/**
 * Preview showing PopOtpField with completion callback
 */
@Preview(
    name = "PopOtpField With Completion",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldWithCompletion() {
    var value by remember { mutableStateOf("") }
    var completionMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PopOtpField(
            config = remember(value) {
                DiscreteConfig(
                    value = value,
                    onValueChange = { text, isComplete ->
                        value = text
                        if (isComplete) {
                            completionMessage = "OTP Complete: $text"
                        } else {
                            completionMessage = ""
                        }
                    },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {
                        value = ""
                        completionMessage = "OTP resent!"
                    },
                    resendCountdownSeconds = null,
                    onDone = {
                        completionMessage = "Done callback triggered!"
                    }
                )
            }
        )

        if (completionMessage.isNotEmpty()) {
            Text(
                text = completionMessage,
                style = PopTypography.labelSmall,
                color = TextColor.Success,
                modifier = Modifier.padding(top = PopSpacing.Spacing8)
            )
        }
    }
}

/**
 * Preview showing PopOtpField with incorrect OTP scenarios
 */
@Preview(
    name = "PopOtpField Incorrect OTP",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldIncorrectOtp() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Incorrect OTP - empty field
        var value1 by remember { mutableStateOf("") }
        var status1 by remember { mutableStateOf<InputFieldStatus?>(InputFieldStatus.Error) }
        PopOtpField(
            config = remember(value1, status1) {
                DiscreteConfig(
                    value = value1,
                    onValueChange = { text, _ ->
                        value1 = text
                        if (status1 == InputFieldStatus.Error && text.isNotEmpty()) {
                            status1 = InputFieldStatus.Info
                        }
                    },
                    status = status1,
                    statusMessage = if (status1 == InputFieldStatus.Error) "Incorrect" else "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {
                        status1 = InputFieldStatus.Info
                        value1 = ""
                    },
                    resendCountdownSeconds = null,
                    onErrorCleared = {
                        status1 = InputFieldStatus.Info
                    }
                )
            }
        )

        // Incorrect OTP - filled field
        var value2 by remember { mutableStateOf("123456") }
        var status2 by remember { mutableStateOf<InputFieldStatus?>(InputFieldStatus.Error) }
        PopOtpField(
            config = remember(value2, status2) {
                DiscreteConfig(
                    value = value2,
                    onValueChange = { text, _ ->
                        value2 = text
                        if (status2 == InputFieldStatus.Error && text.isNotEmpty()) {
                            status2 = InputFieldStatus.Info
                        }
                    },
                    status = status2,
                    statusMessage = if (status2 == InputFieldStatus.Error) "Incorrect" else "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {
                        status2 = InputFieldStatus.Info
                        value2 = ""
                    },
                    resendCountdownSeconds = null,
                    onErrorCleared = {
                        status2 = InputFieldStatus.Info
                    }
                )
            }
        )

        // Incorrect OTP with clearOnError
        var value3 by remember { mutableStateOf("123456") }
        PopOtpField(
            config = remember(value3) {
                DiscreteConfig(
                    value = value3,
                    onValueChange = { text, _ -> value3 = text },
                    status = InputFieldStatus.Error,
                    statusMessage = "Incorrect",
                    clearOnError = true,
                    onResendOtp = { value3 = "" },
                    resendCountdownSeconds = null
                )
            }
        )
    }
}

/**
 * Preview showing PopOtpField with resend OTP scenarios
 */
@Preview(
    name = "PopOtpField Resend OTP",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldResendOtp() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Resend countdown active (29 seconds)
        var value1 by remember { mutableStateOf("") }
        PopOtpField(
            config = remember(value1) {
                DiscreteConfig(
                    value = value1,
                    onValueChange = { text, _ -> value1 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = { value1 = "" },
                    resendCountdownSeconds = 29
                )
            }
        )

        // Resend countdown active (1 minute 15 seconds)
        var value2 by remember { mutableStateOf("") }
        PopOtpField(
            config = remember(value2) {
                DiscreteConfig(
                    value = value2,
                    onValueChange = { text, _ -> value2 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = { value2 = "" },
                    resendCountdownSeconds = 75
                )
            }
        )

        // Resend enabled (countdown expired)
        var value3 by remember { mutableStateOf("") }
        PopOtpField(
            config = remember(value3) {
                DiscreteConfig(
                    value = value3,
                    onValueChange = { text, _ -> value3 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = { value3 = "" },
                    resendCountdownSeconds = null
                )
            }
        )

        // Resend enabled with filled OTP
        var value4 by remember { mutableStateOf("123456") }
        PopOtpField(
            config = remember(value4) {
                DiscreteConfig(
                    value = value4,
                    onValueChange = { text, _ -> value4 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = { value4 = "" },
                    resendCountdownSeconds = null
                )
            }
        )

        // Resend disabled (countdown active, resend not enabled)
        var value5 by remember { mutableStateOf("") }
        PopOtpField(
            config = remember(value5) {
                DiscreteConfig(
                    value = value5,
                    onValueChange = { text, _ -> value5 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = { value5 = "" },
                    resendCountdownSeconds = 45
                )
            }
        )
    }
}

/**
 * Preview showing PopOtpField with incorrect OTP and resend scenarios combined
 */
@Preview(
    name = "PopOtpField Error and Resend",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldErrorAndResend() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Incorrect OTP with resend countdown
        var value1 by remember { mutableStateOf("123456") }
        var status1 by remember { mutableStateOf<InputFieldStatus?>(InputFieldStatus.Error) }
        PopOtpField(
            config = remember(value1, status1) {
                DiscreteConfig(
                    value = value1,
                    onValueChange = { text, _ ->
                        value1 = text
                        if (status1 == InputFieldStatus.Error && text.isNotEmpty()) {
                            status1 = InputFieldStatus.Info
                        }
                    },
                    status = status1,
                    statusMessage = if (status1 == InputFieldStatus.Error) "Incorrect" else "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {
                        status1 = InputFieldStatus.Info
                        value1 = ""
                    },
                    resendCountdownSeconds = 30,
                    onErrorCleared = {
                        status1 = InputFieldStatus.Info
                    }
                )
            }
        )

        // Incorrect OTP with resend enabled
        var value2 by remember { mutableStateOf("123456") }
        var status2 by remember { mutableStateOf<InputFieldStatus?>(InputFieldStatus.Error) }
        PopOtpField(
            config = remember(value2, status2) {
                DiscreteConfig(
                    value = value2,
                    onValueChange = { text, _ ->
                        value2 = text
                        if (status2 == InputFieldStatus.Error && text.isNotEmpty()) {
                            status2 = InputFieldStatus.Info
                        }
                    },
                    status = status2,
                    statusMessage = if (status2 == InputFieldStatus.Error) "Incorrect" else "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {
                        status2 = InputFieldStatus.Info
                        value2 = ""
                    },
                    resendCountdownSeconds = null,
                    onErrorCleared = {
                        status2 = InputFieldStatus.Info
                    }
                )
            }
        )
    }
}

/**
 * Preview showing PopOtpField DiscreteNaked type - all states with 6 digits
 */
@Preview(
    name = "PopOtpField DiscreteNaked All States (6 digits)",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldDiscreteNakedAllStates() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Default state - empty
        var value1 by remember { mutableStateOf("") }
        PopOtpField(
            config = remember(value1) {
                DiscreteNakedConfig(
                    value = value1,
                    onValueChange = { text, _ -> value1 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {},
                    resendCountdownSeconds = 30
                )
            }
        )

        // Partially filled
        var value2 by remember { mutableStateOf("123") }
        PopOtpField(
            config = remember(value2) {
                DiscreteNakedConfig(
                    value = value2,
                    onValueChange = { text, _ -> value2 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {},
                    resendCountdownSeconds = 25
                )
            }
        )

        // Filled
        var value3 by remember { mutableStateOf("123456") }
        PopOtpField(
            config = remember(value3) {
                DiscreteNakedConfig(
                    value = value3,
                    onValueChange = { text, _ -> value3 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {},
                    resendCountdownSeconds = null
                )
            }
        )

        // With OTP sent message and resend countdown
        var value4 by remember { mutableStateOf("") }
        PopOtpField(
            config = remember(value4) {
                DiscreteNakedConfig(
                    value = value4,
                    onValueChange = { text, _ -> value4 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {},
                    resendCountdownSeconds = 29
                )
            }
        )

        // With OTP sent message and resend enabled
        var value5 by remember { mutableStateOf("") }
        PopOtpField(
            config = remember(value5) {
                DiscreteNakedConfig(
                    value = value5,
                    onValueChange = { text, _ -> value5 = text },
                    status = InputFieldStatus.Info,
                    statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {},
                    resendCountdownSeconds = null
                )
            }
        )
    }
}

/**
 * Preview showing PopOtpField DiscreteNaked type with different digit counts
 */
@Preview(
    name = "PopOtpField DiscreteNaked Different Counts",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldDiscreteNakedDifferentCounts() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 4 digits
        var value4 by remember { mutableStateOf("1234") }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "4 digits (DiscreteNaked)",
                style = PopTypography.labelSmall,
                color = TextColor.Secondary
            )
            PopOtpField(
                config = remember(value4) {
                    DiscreteNakedConfig(
                        value = value4,
                        onValueChange = { text, _ -> value4 = text },
                        otpCount = 4,
                        status = InputFieldStatus.Info,
                        statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                        onResendOtp = {},
                        resendCountdownSeconds = null
                    )
                }
            )
        }

        // 6 digits
        var value6 by remember { mutableStateOf("123456") }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "6 digits (DiscreteNaked)",
                style = PopTypography.labelSmall,
                color = TextColor.Secondary
            )
            PopOtpField(
                config = remember(value6) {
                    DiscreteNakedConfig(
                        value = value6,
                        onValueChange = { text, _ -> value6 = text },
                        otpCount = 6,
                        status = InputFieldStatus.Info,
                        statusMessage = "OTP sent on +91 XXXX XXXX 9999",
                        onResendOtp = {},
                        resendCountdownSeconds = null
                    )
                }
            )
        }
    }
}

/**
 * Preview showing PopOtpField DiscreteNaked type with error state
 */
@Preview(
    name = "PopOtpField DiscreteNaked Error",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldDiscreteNakedError() {
    var value by remember { mutableStateOf("123456") }
    var status by remember { mutableStateOf<InputFieldStatus?>(InputFieldStatus.Error) }
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Error state with incorrect OTP
        PopOtpField(
            config = remember(value, status) {
                DiscreteNakedConfig(
                    value = value,
                    onValueChange = { text, _ ->
                        value = text
                        if (status == InputFieldStatus.Error && text.isNotEmpty()) {
                            status = InputFieldStatus.Info
                        }
                    },
                    status = status,
                    statusMessage = if (status == InputFieldStatus.Error) "Incorrect" else "OTP sent on +91 XXXX XXXX 9999",
                    onResendOtp = {
                        status = InputFieldStatus.Info
                        value = ""
                    },
                    resendCountdownSeconds = null,
                    onErrorCleared = {
                        status = InputFieldStatus.Info
                    }
                )
            }
        )
    }
}

/**
 * Preview showing PopOtpField DiscreteNaked type disabled state
 */
@Preview(
    name = "PopOtpField DiscreteNaked Disabled",
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun PreviewPopOtpFieldDiscreteNakedDisabled() {
    Column(
        modifier = Modifier
            .background(SurfaceColor.Primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Disabled - empty
        PopOtpField(
            config = DiscreteNakedConfig(
                value = "",
                onValueChange = { _, _ -> },
                enabled = false,
                status = InputFieldStatus.Info,
                statusMessage = "OTP sent on +91 XXXX XXXX 9999"
            )
        )

        // Disabled - filled
        PopOtpField(
            config = DiscreteNakedConfig(
                value = "123456",
                onValueChange = { _, _ -> },
                enabled = false,
                status = InputFieldStatus.Info,
                statusMessage = "OTP sent on +91 XXXX XXXX 9999"
            )
        )
    }
}
