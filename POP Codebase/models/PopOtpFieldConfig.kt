package com.pop.components.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Base sealed class for PopOtpField configuration.
 * 
 * This provides a type-safe, structured way to configure all aspects of OTP fields,
 * following the design system's configuration pattern.
 * 
 * Each OTP field type has its own config class that extends this base class,
 * ensuring compile-time type safety for required and optional parameters.
 * 
 * **Performance Note**: All config classes are marked with [@Immutable] to prevent
 * unnecessary recompositions when config objects are passed as parameters.
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
 */
@Immutable
sealed class PopOtpFieldConfig {
    /** The current OTP text value */
    abstract val value: String
    
    /** Callback when OTP text changes, provides (text, isComplete) tuple */
    abstract val onValueChange: (String, Boolean) -> Unit
    
    /** Number of OTP digits (supports 4 or 6) */
    abstract val otpCount: Int
    
    /** Whether the field is enabled */
    abstract val enabled: Boolean
    
    /** Status type for validation/feedback */
    abstract val status: InputFieldStatus?
    
    /** Status message to display below the field */
    abstract val statusMessage: String?
    
    /** Whether the OTP is currently being verified (shows loading state) */
    abstract val isLoading: Boolean
    
    /** Callback when OTP input is complete (all digits entered) */
    abstract val onDone: () -> Unit
    
    /** Optional callback when user wants to resend OTP. If provided, a resend option will be shown. */
    abstract val onResendOtp: (() -> Unit)?
    abstract val onEditClick: (() -> Unit)?

    /** Text to display for resend option */
    abstract val resendText: String
    
    /** Optional callback when user starts typing after an error. Use this to clear the error state. */
    abstract val onErrorCleared: (() -> Unit)?
    
    /** Whether to auto-focus the field when it first appears */
    abstract val autoFocus: Boolean
    
    /** Whether to automatically call onDone when all digits are entered */
    abstract val autoSubmit: Boolean
    
    /** Whether to clear the field when an error occurs */
    abstract val clearOnError: Boolean
    
    /** When false, the field does not take focus (e.g. when using an external keypad). Prevents device keyboard from showing. */
    abstract val focusable: Boolean
    
    /** Current countdown timer in seconds for resend. When > 0, shows "Resend in 00:XX" format (MM:SS) and disables resend. When 0 or null, enables resend and shows "Resend OTP" link. */
    abstract val resendCountdownSeconds: Int?

    /** Spacing between the Edit text and its dotted underline (only when onEditClick is provided). Default 2.dp. */
    abstract val editUnderlineSpacing: Dp

    /** Optional override for each box's width. When null, the DS default (44.dp for 6-digit, 56.dp for 4-digit) is used. */
    abstract val boxWidth: Dp?

    /** Optional override for each box's height. When null, the DS default (56.dp) is used. */
    abstract val boxHeight: Dp?

    /** Optional override for the spacing between boxes. When null, the DS default (12.dp) is used. */
    abstract val boxSpacing: Dp?
}

/**
 * Configuration for Discrete OTP field type.
 * 
 * Discrete style with individual boxes for each digit (from Figma design: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=4235-17681)
 * 
 * @param value The current OTP text value
 * @param onValueChange Callback when OTP text changes, provides (text, isComplete) tuple
 * @param otpCount Number of OTP digits (default: 6, supports 4 or 6)
 * @param enabled Whether the field is enabled
 * @param status Status type for validation/feedback
 * @param statusMessage Status message to display below the field
 * @param isLoading Whether the OTP is currently being verified (shows loading state)
 * @param onDone Callback when OTP input is complete (all digits entered)
 * @param onResendOtp Optional callback when user wants to resend OTP. If provided, a resend option will be shown.
 * @param resendText Text to display for resend option (default: "Resend OTP")
 * @param onErrorCleared Optional callback when user starts typing after an error. Use this to clear the error state.
 * @param autoFocus Whether to auto-focus the field when it first appears (default: true)
 * @param autoSubmit Whether to automatically call onDone when all digits are entered (default: false)
 * @param clearOnError Whether to clear the field when an error occurs (default: false)
 * @param focusable When false, field does not take focus (e.g. external keypad); prevents device keyboard (default: true)
 * @param resendCountdownSeconds Current countdown timer in seconds for resend. When > 0, shows "Resend in 00:XX" format (MM:SS) and disables resend. When 0 or null, enables resend and shows "Resend OTP" link.
 */
@Immutable
data class DiscreteConfig(
    override val value: String,
    override val onValueChange: (String, Boolean) -> Unit,
    override val otpCount: Int = 6,
    override val enabled: Boolean = true,
    override val status: InputFieldStatus? = null,
    override val statusMessage: String? = null,
    override val isLoading: Boolean = false,
    override val onDone: () -> Unit = {},
    override val onResendOtp: (() -> Unit)? = null,
    override val resendText: String = "Resend OTP",
    override val onErrorCleared: (() -> Unit)? = null,
    override val autoFocus: Boolean = true,
    override val autoSubmit: Boolean = false,
    override val clearOnError: Boolean = false,
    override val focusable: Boolean = true,
    override val resendCountdownSeconds: Int? = null,
    override val onEditClick: (() -> Unit)? = null,
    override val editUnderlineSpacing: Dp = 2.dp,
    override val boxWidth: Dp? = null,
    override val boxHeight: Dp? = null,
    override val boxSpacing: Dp? = null,
) : PopOtpFieldConfig()

/**
 * Configuration for DiscreteNaked OTP field type.
 * 
 * DiscreteNaked style with individual boxes with transparent background (from Figma design: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=4453-16354)
 * 
 * @param value The current OTP text value
 * @param onValueChange Callback when OTP text changes, provides (text, isComplete) tuple
 * @param otpCount Number of OTP digits (default: 6, supports 4 or 6)
 * @param enabled Whether the field is enabled
 * @param status Status type for validation/feedback
 * @param statusMessage Status message to display below the field
 * @param isLoading Whether the OTP is currently being verified (shows loading state)
 * @param onDone Callback when OTP input is complete (all digits entered)
 * @param onResendOtp Optional callback when user wants to resend OTP. If provided, a resend option will be shown.
 * @param resendText Text to display for resend option (default: "Resend OTP")
 * @param onErrorCleared Optional callback when user starts typing after an error. Use this to clear the error state.
 * @param autoFocus Whether to auto-focus the field when it first appears (default: true)
 * @param autoSubmit Whether to automatically call onDone when all digits are entered (default: false)
 * @param clearOnError Whether to clear the field when an error occurs (default: false)
 * @param focusable When false, field does not take focus (e.g. external keypad); prevents device keyboard (default: true)
 * @param resendCountdownSeconds Current countdown timer in seconds for resend. When > 0, shows "Resend in 00:XX" format (MM:SS) and disables resend. When 0 or null, enables resend and shows "Resend OTP" link.
 * @param resendCountdownDuration Initial countdown duration in seconds when OTP is sent (default: 30). This is the duration to use when initializing the countdown.
 */
@Immutable
data class DiscreteNakedConfig(
    override val value: String,
    override val onValueChange: (String, Boolean) -> Unit,
    override val otpCount: Int = 6,
    override val enabled: Boolean = true,
    override val status: InputFieldStatus? = null,
    override val statusMessage: String? = null,
    override val isLoading: Boolean = false,
    override val onDone: () -> Unit = {},
    override val onResendOtp: (() -> Unit)? = null,
    override val onEditClick: (() -> Unit)? = null,
    override val resendText: String = "Resend OTP",
    override val onErrorCleared: (() -> Unit)? = null,
    override val autoFocus: Boolean = true,
    override val autoSubmit: Boolean = false,
    override val clearOnError: Boolean = false,
    override val focusable: Boolean = true,
    override val resendCountdownSeconds: Int? = null,
    override val editUnderlineSpacing: Dp = 2.dp,
    override val boxWidth: Dp? = null,
    override val boxHeight: Dp? = null,
    override val boxSpacing: Dp? = null,
) : PopOtpFieldConfig()

