package com.pop.components.models

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.pop.components.theme.PopIconConfig

/**
 * Base sealed class for PopInputFieldV2 configuration.
 * 
 * This provides a type-safe, structured way to configure all aspects of input fields,
 * following the design system's configuration pattern.
 * 
 * Each input field type has its own config class that extends this base class,
 * ensuring compile-time type safety for required and optional parameters.
 * 
 * **Performance Note**: All config classes are marked with [@Immutable] to prevent
 * unnecessary recompositions when config objects are passed as parameters.
 * 
 * **Example usage**:
 * ```
 * val config = UnderlineNakedLargeConfig(
 *     value = amount,
 *     onValueChange = { amount = it },
 *     placeholder = "0",
 *     useCustomKeypad = true
 * )
 * 
 * PopInputFieldV2(config = config)
 * ```
 */
@Immutable
sealed class PopInputFieldConfig {
    /** The current text value */
    abstract val value: String
    
    /** Callback when text changes */
    abstract val onValueChange: (String) -> Unit
    
    /** Whether the field is enabled */
    abstract val enabled: Boolean
    
    /** Whether the field is read-only */
    abstract val readOnly: Boolean
    
    /** The keyboard type */
    abstract val keyboardType: KeyboardType
    
    /** The IME action */
    abstract val imeAction: ImeAction
    
    /** Visual transformation for the text */
    abstract val visualTransformation: VisualTransformation
    
    /** Callback when IME action is triggered */
    abstract val onImeAction: () -> Unit
    
    /** Status type for validation/feedback */
    abstract val status: InputFieldStatus?
    
    /** Status message to display below the field */
    abstract val statusMessage: String?
    
    /** When true, hides system keyboard and enables custom keypad mode */
    abstract val useCustomKeypad: Boolean
    
    /** Placeholder text shown when field is empty */
    abstract val placeholder: String?
}

/**
 * Configuration for UnderlineNakedSmall input field type.
 * 
 * @param value The current text value
 * @param onValueChange Callback when text changes
 * @param title The title label shown above the input (required)
 * @param placeholder Placeholder text shown when field is empty
 * @param status Status type for validation/feedback
 * @param statusMessage Status message to display below the field
 * @param enabled Whether the field is enabled
 * @param readOnly Whether the field is read-only
 * @param editable Whether the field is editable (affects read-only behavior)
 * @param keyboardType The keyboard type
 * @param imeAction The IME action
 * @param visualTransformation Visual transformation for the text
 * @param onImeAction Callback when IME action is triggered
 */
@Immutable
data class UnderlineNakedSmallConfig(
    override val value: String,
    override val onValueChange: (String) -> Unit,
    val title: String,
    override val placeholder: String? = null,
    override val status: InputFieldStatus? = null,
    override val statusMessage: String? = null,
    override val enabled: Boolean = true,
    override val readOnly: Boolean = false,
    val editable: Boolean = false,
    val startIcon: PopIconConfig? = null,
    val endIcon: PopIconConfig? = null,
    val endSlot: (@Composable () -> Unit)? = null,
    override val keyboardType: KeyboardType = KeyboardType.Text,
    override val imeAction: ImeAction = ImeAction.Next,
    override val visualTransformation: VisualTransformation = VisualTransformation.None,
    override val onImeAction: () -> Unit = {},
    override val useCustomKeypad: Boolean = false // Not supported for this variant
) : PopInputFieldConfig()

/**
 * Configuration for UnderlineNakedLarge input field type.
 * 
 * @param value The current text value
 * @param onValueChange Callback when text changes
 * @param placeholder Placeholder text shown when field is empty
 * @param status Status type for validation/feedback
 * @param statusMessage Status message to display below the field
 * @param enabled Whether the field is enabled
 * @param readOnly Whether the field is read-only
 * @param editable Whether the field is editable (affects read-only behavior)
 * @param keyboardType The keyboard type
 * @param imeAction The IME action
 * @param visualTransformation Visual transformation for the text
 * @param onImeAction Callback when IME action is triggered
 * @param useCustomKeypad When true, hides system keyboard and enables custom keypad mode
 * @param onFocusReceived Callback when focus is requested/received by the field
 * @param onFocusChanged Callback when focus changes (true when focused, false when unfocused e.g. back press or outside touch)
 */
@Immutable
data class UnderlineNakedLargeConfig(
    override val value: String,
    override val onValueChange: (String) -> Unit,
    override val placeholder: String? = null,
    override val status: InputFieldStatus? = null,
    override val statusMessage: String? = null,
    override val enabled: Boolean = true,
    override val readOnly: Boolean = false,
    val editable: Boolean = false,
    override val keyboardType: KeyboardType = KeyboardType.Text,
    override val imeAction: ImeAction = ImeAction.Next,
    override val visualTransformation: VisualTransformation = VisualTransformation.None,
    override val onImeAction: () -> Unit = {},
    override val useCustomKeypad: Boolean = false,
    val shouldRequestFocus: Boolean = false,
    val onFocusReceived: () -> Unit = {}
) : PopInputFieldConfig()

/**
 * Configuration for MobileInputField input field type.
 * 
 * @param value The current text value
 * @param onValueChange Callback when text changes
 * @param title The title label shown above the input (required)
 * @param placeholder Placeholder text shown when field is empty
 * @param status Status type for validation/feedback
 * @param statusMessage Status message to display below the field
 * @param enabled Whether the field is enabled
 * @param readOnly Whether the field is read-only
 * @param keyboardType The keyboard type (default: KeyboardType.Phone)
 * @param imeAction The IME action
 * @param visualTransformation Visual transformation for the text
 * @param onImeAction Callback when IME action is triggered
 * @param onFocusReceived Callback when focus is requested/received by the field
 * @param onFocusChanged Callback when focus changes (true when focused, false when unfocused e.g. back press or outside touch)
 * @param useCustomKeypad Not supported for this variant (always false)
 */
@Immutable
data class MobileInputFieldConfig(
    override val value: String,
    override val onValueChange: (String) -> Unit,
    val title: String,
    override val placeholder: String? = null,
    override val status: InputFieldStatus? = null,
    override val statusMessage: String? = null,
    override val enabled: Boolean = true,
    override val readOnly: Boolean = false,
    override val keyboardType: KeyboardType = KeyboardType.Phone,
    override val imeAction: ImeAction = ImeAction.Next,
    override val visualTransformation: VisualTransformation = VisualTransformation.None,
    val onFocusReceived: () -> Unit = {},
    val onFocusChanged: (Boolean) -> Unit = {},
    override val onImeAction: () -> Unit = {},
    override val useCustomKeypad: Boolean = false // Not supported for this variant
) : PopInputFieldConfig()

/**
 * Configuration for Basic input field type.
 * 
 * @param value The current text value
 * @param onValueChange Callback when text changes
 * @param hintText Hint text that animates to top when field is active
 * @param placeholder Placeholder text shown when field is empty and active
 * @param startIcon Start icon config
 * @param endIcon End icon config
 * @param endSlot End slot composable
 * @param status Status type for validation/feedback
 * @param statusMessage Status message to display below the field
 */
@Immutable
data class BasicInputFieldConfig(
    override val value: String,
    override val onValueChange: (String) -> Unit,
    val hintText: String = "",
    override val placeholder: String? = null,
    val startIcon: PopIconConfig? = null,
    val endIcon: PopIconConfig? = null,
    val endSlot: (@Composable () -> Unit)? = null,
    val paddingStart: Dp? = null,
    val paddingEnd: Dp? = null,
    override val status: InputFieldStatus? = null,
    override val statusMessage: String? = null,
    override val enabled: Boolean = true,
    override val readOnly: Boolean = false,
    override val keyboardType: KeyboardType = KeyboardType.Text,
    override val imeAction: ImeAction = ImeAction.Next,
    override val visualTransformation: VisualTransformation = VisualTransformation.None,
    override val onImeAction: () -> Unit = {},
    override val useCustomKeypad: Boolean = false // Not supported for this variant
) : PopInputFieldConfig()

/**
 * Configuration for Basic input field type without horizontal padding.
 * Identical to [BasicInputFieldConfig] but removes horizontal padding from the container.
 */
data class BasicInputFieldConfigNoPadding(
    override val value: String,
    override val onValueChange: (String) -> Unit,
    val hintText: String = "",
    override val placeholder: String? = null,
    val startIcon: PopIconConfig? = null,
    val endIcon: PopIconConfig? = null,
    val endSlot: (@Composable () -> Unit)? = null,
    override val status: InputFieldStatus? = null,
    override val statusMessage: String? = null,
    override val enabled: Boolean = true,
    override val readOnly: Boolean = false,
    override val keyboardType: KeyboardType = KeyboardType.Text,
    override val imeAction: ImeAction = ImeAction.Next,
    override val visualTransformation: VisualTransformation = VisualTransformation.None,
    override val onImeAction: () -> Unit = {},
    override val useCustomKeypad: Boolean = false // Not supported for this variant
) : PopInputFieldConfig()

/**
 * Configuration for Search input field type.
 * 
 * @param value The current text value
 * @param onValueChange Callback when text changes
 * @param placeholder Placeholder text shown when field is empty
 * @param searchBorderStyle Border style (default: Subtle)
 * @param endSlot End slot composable for trailing chip/filter component
 * @param onClearClick Custom clear button handler (default: clears input)
 * @param status Status type for validation/feedback
 * @param statusMessage Status message to display below the field
 * @param enabled Whether the field is enabled
 * @param readOnly Whether the field is read-only
 * @param keyboardType The keyboard type
 * @param imeAction The IME action (default: ImeAction.Search)
 * @param visualTransformation Visual transformation for the text
 * @param onImeAction Callback when IME action is triggered
 * @param onFocusChanged Callback when focus changes (receives true when focused, false when unfocused)
 */
@Immutable
data class SearchInputFieldConfig(
    override val value: String,
    override val onValueChange: (String) -> Unit,
    override val placeholder: String? = null,
    val animatedPlaceholder: (@Composable () -> Unit)? = null,
    val searchBorderStyle: SearchBorderStyle = SearchBorderStyle.Subtle,
    val endSlot: (@Composable () -> Unit)? = null,
    val onClearClick: (() -> Unit)? = null,
    override val status: InputFieldStatus? = null,
    override val statusMessage: String? = null,
    override val enabled: Boolean = true,
    override val readOnly: Boolean = false,
    override val keyboardType: KeyboardType = KeyboardType.Text,
    override val imeAction: ImeAction = ImeAction.Search,
    override val visualTransformation: VisualTransformation = VisualTransformation.None,
    override val onImeAction: () -> Unit = {},
    override val useCustomKeypad: Boolean = false, // Not supported for this variant
    val onFocusChanged: (Boolean) -> Unit = {}
) : PopInputFieldConfig()

/**
 * Configuration for Small input field type.
 * 
 * @param value The current text value
 * @param onValueChange Callback when text changes
 * @param title Title text shown when field is inactive and empty
 * @param placeholder Placeholder text shown when field is empty
 * @param status Status type for validation/feedback
 * @param statusMessage Status message to display below the field
 * @param enabled Whether the field is enabled
 * @param readOnly Whether the field is read-only
 * @param keyboardType The keyboard type
 * @param imeAction The IME action
 * @param visualTransformation Visual transformation for the text
 * @param onImeAction Callback when IME action is triggered
 */
@Immutable
data class SmallInputFieldConfig(
    override val value: String,
    override val onValueChange: (String) -> Unit,
    val title: String? = null,
    override val placeholder: String? = null,
    override val status: InputFieldStatus? = null,
    override val statusMessage: String? = null,
    override val enabled: Boolean = true,
    override val readOnly: Boolean = false,
    override val keyboardType: KeyboardType = KeyboardType.Text,
    override val imeAction: ImeAction = ImeAction.Next,
    override val visualTransformation: VisualTransformation = VisualTransformation.None,
    override val onImeAction: () -> Unit = {},
    override val useCustomKeypad: Boolean = false // Not supported for this variant
) : PopInputFieldConfig()

