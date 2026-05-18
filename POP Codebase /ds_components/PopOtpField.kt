package com.pop.components.ds_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import android.os.Build
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.pop.components.models.DiscreteConfig
import com.pop.components.models.DiscreteNakedConfig
import com.pop.components.models.InputFieldStatus
import com.pop.components.models.PopOtpFieldConfig
import com.pop.components.theme.BorderColor
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.PopStroke
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.math.max

/**
 * Type of OTP field variant.
 *
 * @property Discrete Discrete style with individual boxes for each digit (from Figma design)
 * @property DiscreteNaked Discrete style with transparent background (from Figma design)
 */
enum class PopOtpFieldType {
    /** Discrete style - individual distinct boxes for each digit (from Figma design: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=4235-17681) */
    Discrete,
    /** DiscreteNaked style - individual distinct boxes with transparent background (from Figma design: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=4453-16354) */
    DiscreteNaked
}

// Constants
private val NUMBER_REGEX = Regex("[^0-9]") // Only allow digits 0-9

/**
 * PopOtpField - Config-based overload for better performance and type safety.
 *
 * This overload accepts a [PopOtpFieldConfig] object, which provides:
 * - Better performance: Config objects are [@Immutable], reducing unnecessary recompositions
 * - Type safety: Each config class enforces required fields at compile time
 * - Cleaner API: Single config parameter instead of many individual parameters
 * - Consistent with InputFieldStatus pattern: Use status enum instead of boolean flags
 *
 * **Performance Note**: Using config-based approach is recommended when:
 * - You need to pass the same configuration to multiple fields
 * - You want to reduce parameter count and improve readability
 * - You need better recomposition performance (config objects are stable)
 *
 * **Example usage**:
 * ```
 * val config = DiscreteConfig(
 *     value = otpValue,
 *     onValueChange = { text, isComplete -> otpValue = text },
 *     status = InputFieldStatus.Error,
 *     statusMessage = "Incorrect OTP"
 * )
 *
 * PopOtpField(config = config)
 * ```
 *
 * @param config The configuration object for the OTP field
 * @param modifier Modifier to be applied to the component
 */
@Composable
fun PopOtpField(
    config: PopOtpFieldConfig,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        require(config.otpCount in listOf(4, 6)) {
            "otpCount must be one of: 4, 6. Current value: ${config.otpCount}"
        }
    }

    // Do not throw on value length; avoid composition crash when state is briefly out of sync (e.g. restore from background)
    // Parent is responsible for keeping value.length <= otpCount

    when (config) {
        is DiscreteConfig -> {
            PopOtpFieldDiscrete(
                modifier = modifier,
                value = config.value,
                onValueChange = config.onValueChange,
                otpCount = config.otpCount,
                enabled = config.enabled,
                status = config.status,
                statusMessage = config.statusMessage,
                onDone = config.onDone,
                onResendOtp = config.onResendOtp,
                onEditClick = config.onEditClick,
                resendText = config.resendText,
                onErrorCleared = config.onErrorCleared,
                autoFocus = config.autoFocus,
                autoSubmit = config.autoSubmit,
                isLoading = config.isLoading,
                clearOnError = config.clearOnError,
                focusable = config.focusable,
                resendCountdownSeconds = config.resendCountdownSeconds,
                editUnderlineSpacing = config.editUnderlineSpacing,
            )
        }
        is DiscreteNakedConfig -> {
            PopOtpFieldDiscreteNaked(
                modifier = modifier,
                value = config.value,
                onValueChange = config.onValueChange,
                otpCount = config.otpCount,
                enabled = config.enabled,
                status = config.status,
                statusMessage = config.statusMessage,
                onDone = config.onDone,
                onResendOtp = config.onResendOtp,
                onEditClick = config.onEditClick,
                resendText = config.resendText,
                onErrorCleared = config.onErrorCleared,
                autoFocus = config.autoFocus,
                autoSubmit = config.autoSubmit,
                clearOnError = config.clearOnError,
                focusable = config.focusable,
                resendCountdownSeconds = config.resendCountdownSeconds,
                editUnderlineSpacing = config.editUnderlineSpacing,
            )
        }
    }
}

@Composable
private fun PopOtpFieldDiscrete(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String, Boolean) -> Unit,
    otpCount: Int,
    enabled: Boolean,
    status: InputFieldStatus?,
    statusMessage: String?,
    onDone: () -> Unit,
    onResendOtp: (() -> Unit)?,
    onEditClick: (() -> Unit)?,
    resendText: String,
    onErrorCleared: (() -> Unit)?,
    autoFocus: Boolean,
    autoSubmit: Boolean,
    isLoading: Boolean,
    clearOnError: Boolean,
    focusable: Boolean,
    resendCountdownSeconds: Int?,
    editUnderlineSpacing: Dp,
) {
    val focusRequester = remember { FocusRequester() }
    
    // Optimize derived state calculations
    val hasError by remember(status) { derivedStateOf { status == InputFieldStatus.Error } }
    val focusedIndex by remember(value) { derivedStateOf { value.length } }
    
    // Remember box dimensions at parent level
    val (boxWidth, boxHeight) = remember(otpCount) { getOtpBoxDimensions(otpCount) }
    
    // Optimize TextFieldValue creation
    val textFieldValue by remember(value) {
        derivedStateOf { TextFieldValue(value, selection = TextRange(value.length)) }
    }
    
    // Remember KeyboardOptions and KeyboardActions
    val keyboardOptions = remember {
        KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    }
    val keyboardActions = remember(onDone) {
        KeyboardActions(onDone = { onDone() })
    }

    // Auto-focus on first appearance only when focusable (e.g. not when using external keypad)
    LaunchedEffect(Unit) {
        if (focusable && autoFocus && enabled && value.isEmpty()) {
            focusRequester.requestFocus()
        }
    }

    // Clear field on error if requested
    LaunchedEffect(hasError) {
        if (hasError && clearOnError && value.isNotEmpty()) {
            onValueChange("", false)
        }
    }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = "OTP input field, enter $otpCount digit code" },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            modifier = Modifier
                .wrapContentWidth()
                // clearAndSetSemantics removes this composable from the autofill virtual structure,
                // which is what causes the yellow/green overlay when text is pasted.
                // importantForAutofill on the AndroidComposeView is reset by Compose itself on every
                // recomposition, so the semantics layer is the correct place to opt out.
                .clearAndSetSemantics { }
                .focusProperties { canFocus = focusable }
                .focusRequester(focusRequester),
            value = textFieldValue,
            onValueChange = { newValue ->
                var enteredText = newValue.text.replace(NUMBER_REGEX, "")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.getSystemService(android.view.autofill.AutofillManager::class.java)?.cancel()
                }

                if (enteredText.length > otpCount) {
                    enteredText = enteredText.take(otpCount)
                }
                if (enteredText.length <= otpCount) {
                    if (hasError && enteredText.isNotEmpty() && value.isEmpty()) {
                        onErrorCleared?.invoke()
                    }
                    val isComplete = enteredText.length == otpCount
                    onValueChange(enteredText, isComplete)
                    if (isComplete && autoSubmit) {
                        onDone()
                    }
                }
            },
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing12)
                ) {
                    repeat(otpCount) { index ->
                        DiscreteOtpBox(
                            index = index,
                            text = value,
                            isFocused = focusedIndex == index,
                            enabled = enabled,
                            status = status,
                            isLoading = isLoading,
                            boxWidth = boxWidth,
                            boxHeight = boxHeight
                        )
                    }
                }
                // Hide the actual text field off-screen, we render it manually in the boxes
                Box(modifier = Modifier.size(0.dp)) {
                    innerTextField()
                }
            }
        )

        // First text line: Status message
        OtpFieldMessageSection(
            status = status,
            statusMessage = statusMessage,
            onEditClick = onEditClick,
            editUnderlineSpacing = editUnderlineSpacing
        )

        // Second text line: Resend OTP countdown or link
        if (onResendOtp != null) {
            OtpFieldResendSection(
                resendCountdownSeconds = resendCountdownSeconds,
                resendText = resendText,
                onResendOtp = onResendOtp
            )
        }
    }
}

@Composable
private fun PopOtpFieldDiscreteNaked(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String, Boolean) -> Unit,
    otpCount: Int,
    enabled: Boolean,
    status: InputFieldStatus?,
    statusMessage: String?,
    onDone: () -> Unit,
    onResendOtp: (() -> Unit)?,
    onEditClick: (() -> Unit)?,
    resendText: String,
    onErrorCleared: (() -> Unit)?,
    autoFocus: Boolean,
    autoSubmit: Boolean,
    clearOnError: Boolean,
    focusable: Boolean,
    resendCountdownSeconds: Int?,
    editUnderlineSpacing: Dp = 2.dp,
) {
    val focusRequester = remember { FocusRequester() }
    
    // Optimize derived state calculations
    val hasError by remember(status) { derivedStateOf { status == InputFieldStatus.Error } }
    val focusedIndex by remember(value) { derivedStateOf { value.length } }
    
    // Remember box dimensions at parent level
    val (boxWidth, boxHeight) = remember(otpCount) { getOtpBoxDimensions(otpCount) }
    
    // Optimize TextFieldValue creation
    val textFieldValue by remember(value) {
        derivedStateOf { TextFieldValue(value, selection = TextRange(value.length)) }
    }
    
    // Remember KeyboardOptions and KeyboardActions
    val keyboardOptions = remember {
        KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    }
    val keyboardActions = remember(onDone) {
        KeyboardActions(onDone = { onDone() })
    }

    // Auto-focus on first appearance only when focusable
    LaunchedEffect(Unit) {
        if (focusable && autoFocus && enabled && value.isEmpty()) {
            focusRequester.requestFocus()
        }
    }

    // Clear field on error if requested
    LaunchedEffect(hasError) {
        if (hasError && clearOnError && value.isNotEmpty()) {
            onValueChange("", false)
        }
    }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = "OTP input field, enter $otpCount digit code" },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicTextField(
            modifier = Modifier
                .wrapContentWidth()
                .clearAndSetSemantics { }
                .focusProperties { canFocus = focusable }
                .focusRequester(focusRequester),
            value = textFieldValue,
            onValueChange = { newValue ->
                var enteredText = newValue.text.replace(NUMBER_REGEX, "")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.getSystemService(android.view.autofill.AutofillManager::class.java)?.cancel()
                }

                if (enteredText.length > otpCount) {
                    enteredText = enteredText.take(otpCount)
                }
                if (enteredText.length <= otpCount) {
                    if (hasError && enteredText.isNotEmpty() && value.isEmpty()) {
                        onErrorCleared?.invoke()
                    }
                    val isComplete = enteredText.length == otpCount
                    onValueChange(enteredText, isComplete)
                    if (isComplete && autoSubmit) {
                        onDone()
                    }
                }
            },
            enabled = enabled,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.spacedBy(PopSpacing.Spacing12)
                ) {
                    repeat(otpCount) { index ->
                        DiscreteNakedOtpBox(
                            index = index,
                            text = value,
                            isFocused = focusedIndex == index,
                            enabled = enabled,
                            boxWidth = boxWidth,
                            boxHeight = boxHeight
                        )
                    }
                }
                // Hide the actual text field off-screen, we render it manually in the boxes
                Box(modifier = Modifier.size(0.dp)) {
                    innerTextField()
                }
            }
        )

        // First text line: Status message
        OtpFieldMessageSection(
            status = status,
            statusMessage = statusMessage,
            onEditClick = onEditClick,
            editUnderlineSpacing = editUnderlineSpacing
        )

        // Second text line: Resend OTP countdown or link
        if (onResendOtp != null) {
            OtpFieldResendSection(
                resendCountdownSeconds = resendCountdownSeconds,
                resendText = resendText,
                onResendOtp = onResendOtp
            )
        }
    }
}

/**
 * Shared composable for displaying OTP field message section using StatusMessage composable
 */
@Composable
private fun OtpFieldMessageSection(
    status: InputFieldStatus?,
    statusMessage: String?,
    onEditClick: (() -> Unit)? = null,
    editUnderlineSpacing: Dp = 2.dp
) {
    Spacer(modifier = Modifier.height(PopSpacing.Spacing12))
    if (status != null && statusMessage != null) {
        StatusMessage(
            status = status,
            message = statusMessage,
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.Center,
            showIcon = false,
            textStyle = PopTypography.figtreeStyles.paragraphMedium,
            onEditClick = onEditClick,
            editUnderlineSpacing = editUnderlineSpacing
        )
    }
}

/**
 * Shared composable for displaying resend OTP section with countdown timer.
 * When [resendCountdownSeconds] is set, a countdown runs and the remaining seconds are shown;
 * when it reaches 0, the resend text becomes clickable.
 *
 * Uses an absolute end timestamp (currentTimeMillis + initialSeconds) so the timer stays correct
 * when the app is backgrounded: when the user returns, remaining time is derived from the current
 * time. Updates only run while [Lifecycle.State.RESUMED] to avoid blank-screen issues.
 */
@Composable
private fun OtpFieldResendSection(
    resendCountdownSeconds: Int?,
    resendText: String,
    onResendOtp: () -> Unit
) {
    Spacer(modifier = Modifier.height(PopSpacing.Spacing12))

    val initialSeconds = resendCountdownSeconds ?: 0
    val endTimeMs = remember(initialSeconds) {
        mutableStateOf(
            if (initialSeconds > 0) System.currentTimeMillis() + initialSeconds * 1000L else 0L
        )
    }
    LaunchedEffect(initialSeconds) {
        endTimeMs.value = if (initialSeconds > 0) {
            System.currentTimeMillis() + initialSeconds * 1000L
        } else {
            0L
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    var tick by remember { mutableStateOf(0) }
    // repeatOnLifecycle cancels the block as soon as lifecycle goes below RESUMED (e.g. app to background)
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            while (true) {
                delay(1000L)
                tick++
            }
        }
    }

    // Recompute remaining time from current clock when tick or endTimeMs changes; no key() to avoid composition churn
    val remainingSeconds = remember(tick, endTimeMs.value) {
        if (endTimeMs.value == 0L) 0
        else max(0, ((endTimeMs.value - System.currentTimeMillis()) / 1000).toInt())
    }
    val isCountdownActive = remainingSeconds > 0
    val displayText = if (isCountdownActive) {
        val minutes = remainingSeconds / 60
        val secs = remainingSeconds % 60
        "Resend in ${String.format(Locale.getDefault(), "%02d:%02d", minutes, secs)}"
    } else {
        resendText
    }
    val isClickable = !isCountdownActive

    Text(
        text = displayText,
        style = PopTypography.figtreeStyles.paragraphMedium.copy(
            color = if (isClickable) {
                TextColor.Brand
            } else {
                TextColor.TertiaryFromTokens
            }
        ),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .clickable(enabled = isClickable) {
                if (isClickable) {
                    onResendOtp()
                }
            }
    )
}

/**
 * Shared helper function to calculate box dimensions based on OTP count
 */
private fun getOtpBoxDimensions(otpCount: Int): Pair<Dp, Dp> {
    val boxWidth = if (otpCount == 4) {
        PopSpacing.Spacing56 // Square for 4 digits
    } else {
        PopSpacing.Spacing44 // Narrower for 6+ digits
    }
    val boxHeight = PopSpacing.Spacing56 // Always 56dp height
    return boxWidth to boxHeight
}

/**
 * Shared helper function to get text style for OTP digits
 */
private fun getOtpDigitTextStyle() = PopTypography.figtreeStyles.paragraphLarge.copy(
    fontSize = 22.sp,
    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
)

@Composable
private fun DiscreteOtpBox(
    index: Int,
    text: String,
    isFocused: Boolean,
    enabled: Boolean,
    status: InputFieldStatus?,
    isLoading: Boolean,
    boxWidth: Dp,
    boxHeight: Dp
) {
    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }

    // Optimize color calculations with remember
    val backgroundColor = remember(enabled) {
        if (enabled) {
            SurfaceColor.SecondaryFromTokens // Dark gray background from new design system
        } else {
            SurfaceColor.SecondaryDisabled // Disabled background from new design system
        }
    }

    val borderColor = remember(enabled, isLoading, status, isFocused) {
        when {
            !enabled -> BorderColor.PrimaryInvert.copy(alpha = 0.3f) // Faded border when disabled
            isLoading -> BorderColor.PrimaryInvert.copy(alpha = 0.7f) // Slightly faded when loading
            status == InputFieldStatus.Success -> TextColor.SuccessFromTokens // Green border for success
            status == InputFieldStatus.Error -> TextColor.DestructiveFromTokens // Reddish-orange border for error - takes priority over focus
            isFocused -> BorderColor.PrimaryInvert // Thicker, brighter border when focused (only if no error)
            else -> BorderColor.PrimaryInvert.copy(alpha = 0.5f) // Thin, light gray border
        }
    }

    val borderWidth = PopStroke.Default // Border width for Discrete variant

    val textColor = remember(enabled) {
        if (enabled) {
            TextColor.Primary // White/light gray text
        } else {
            TextColor.Primary.copy(alpha = 0.5f) // Faded text when disabled
        }
    }

    // Remember text style
    val textStyle = remember { getOtpDigitTextStyle() }

    val squircleShape = remember { SquircleShape(cornerRadius = PopRadius.Medium, smoothing = 1f) }

    // Blinking cursor when focused and empty
    var cursorVisible by remember { mutableStateOf(true) }
    LaunchedEffect(isFocused && enabled && char.isEmpty()) {
        cursorVisible = true
        while (true) {
            delay(500L)
            cursorVisible = !cursorVisible
        }
    }
    
    Box(
        modifier = Modifier
            .width(boxWidth)
            .height(boxHeight)
            .background(
                color = backgroundColor,
                shape = squircleShape
            )
            .border(
                width = borderWidth,
                color = borderColor,
                shape = squircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isFocused && enabled && char.isEmpty()) {
            // Blinking cursor when focused and empty
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(24.dp)
                    .graphicsLayer { alpha = if (cursorVisible) 1f else 0f }
                    .background(color = TextColor.Primary)
            )
        } else if (char.isNotEmpty()) {
            Text(
                text = char,
                color = textColor,
                textAlign = TextAlign.Center,
                style = textStyle
            )
        }
    }
}

@Composable
private fun DiscreteNakedOtpBox(
    index: Int,
    text: String,
    isFocused: Boolean,
    enabled: Boolean,
    boxWidth: Dp,
    boxHeight: Dp
) {
    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }

    // Colors based on state - DiscreteNaked type has transparent background and no border
    val backgroundColor = Color.Transparent // Transparent background for Naked variant

    // Optimize color calculations with remember
    val textColor = remember(enabled) {
        if (enabled) {
            TextColor.Primary // White/light gray text
        } else {
            TextColor.Primary.copy(alpha = 0.5f) // Faded text when disabled
        }
    }

    // Remember text style
    val textStyle = remember { getOtpDigitTextStyle() }

    val squircleShape = remember { SquircleShape(cornerRadius = PopRadius.Medium, smoothing = 1f) }

    // Blinking cursor when focused and empty
    var cursorVisible by remember { mutableStateOf(true) }
    LaunchedEffect(isFocused && enabled && char.isEmpty()) {
        cursorVisible = true
        while (true) {
            delay(500L)
            cursorVisible = !cursorVisible
        }
    }
    
    Box(
        modifier = Modifier
            .width(boxWidth)
            .height(boxHeight)
            .background(
                color = backgroundColor,
                shape = squircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isFocused && enabled && char.isEmpty()) {
            // Blinking cursor when focused and empty
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .height(24.dp)
                    .graphicsLayer { alpha = if (cursorVisible) 1f else 0f }
                    .background(color = TextColor.Primary)
            )
        } else if (char.isNotEmpty()) {
            Text(
                text = char,
                color = textColor,
                textAlign = TextAlign.Center,
                style = textStyle
            )
        }
    }
}
