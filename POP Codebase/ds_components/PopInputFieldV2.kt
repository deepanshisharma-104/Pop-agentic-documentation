package com.pop.components.ds_components

import android.graphics.BlurMaskFilter
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.pop.components.models.BasicInputFieldConfig
import com.pop.components.models.BasicInputFieldConfigNoPadding
import com.pop.components.models.InputFieldStatus
import com.pop.components.models.InputFieldStatus.Error
import com.pop.components.models.InputFieldStatus.Info
import com.pop.components.models.InputFieldStatus.Success
import com.pop.components.models.InputFieldStatus.Warning
import com.pop.components.models.MobileInputFieldConfig
import com.pop.components.models.PopInputFieldConfig
import com.pop.components.models.PopInputFieldType
import com.pop.components.models.PopInputFieldType.UnderlineNakedLarge
import com.pop.components.models.PopInputFieldType.UnderlineNakedSmall
import com.pop.components.models.SearchBorderStyle
import com.pop.components.models.SearchBorderStyle.DefinedThin
import com.pop.components.models.SearchBorderStyle.Subtle
import com.pop.components.models.SearchInputFieldConfig
import com.pop.components.models.SmallInputFieldConfig
import com.pop.components.models.UnderlineNakedLargeConfig
import com.pop.components.models.UnderlineNakedSmallConfig
import com.pop.components.theme.BorderColor
import com.pop.components.theme.IconColor
import com.pop.components.theme.IconSize
import com.pop.components.theme.IconStyle
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopIconConfig
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.PopStroke
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import com.pop.components.theme.TextColors
import com.pop.components.theme.toDp
import com.pop.components.utils.glowEffect
import com.pop.components.utils.horizontalGradientFade
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons as MaterialIcons
import com.pop.components.theme.Icons as PopIcons
import com.pop.components.theme.PopIcons as PopIconsSizes

// Helper data class for combined color calculations
@Immutable
private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)

/**
 * PopInputFieldV2 - A composable input field component with support for multiple types.
 *
 * **⚠️ DEPRECATED: This parameter-based overload is deprecated. Use the config-based overload instead.**
 *
 * **Migration Guide:**
 *
 * **Step 1: Identify your input field type**
 * - `UnderlineNakedSmall` → Use [UnderlineNakedSmallConfig]
 * - `UnderlineNakedLarge` → Use [UnderlineNakedLargeConfig]
 * - `MobileInputField` → Use [MobileInputFieldConfig]
 * - `Basic` → Use [BasicInputFieldConfig]
 * - `Search` → Use [SearchInputFieldConfig]
 * - `Small` → Use [SmallInputFieldConfig]
 *
 * **Step 2: Create a config object**
 * ```
 * // Before (deprecated)
 * PopInputFieldV2(
 *     value = amount,
 *     onValueChange = { amount = it },
 *     type = PopInputFieldType.UnderlineNakedLarge,
 *     placeholder = "0",
 *     useCustomKeypad = true
 * )
 *
 * // After (recommended)
 * val config = UnderlineNakedLargeConfig(
 *     value = amount,
 *     onValueChange = { amount = it },
 *     placeholder = "0",
 *     useCustomKeypad = true
 * )
 * PopInputFieldV2(config = config)
 * ```
 *
 * **Step 3: Benefits of migration**
 * - ✅ Better performance: Config objects are [@Immutable], reducing unnecessary recompositions
 * - ✅ Type safety: Required fields enforced at compile time
 * - ✅ Cleaner API: Single config parameter instead of many individual parameters
 * - ✅ Easier to reuse: Create config once, use multiple times
 *
 * **Migration Examples:**
 *
 * **Example 1: UnderlineNakedSmall**
 * ```
 * // Before
 * PopInputFieldV2(
 *     value = name,
 *     onValueChange = { name = it },
 *     type = PopInputFieldType.UnderlineNakedSmall,
 *     title = "Name",
 *     placeholder = "Enter name",
 *     status = InputFieldStatus.Error,
 *     statusMessage = "Name is required"
 * )
 *
 * // After
 * val config = UnderlineNakedSmallConfig(
 *     value = name,
 *     onValueChange = { name = it },
 *     title = "Name",
 *     placeholder = "Enter name",
 *     status = InputFieldStatus.Error,
 *     statusMessage = "Name is required"
 * )
 * PopInputFieldV2(config = config)
 * ```
 *
 * **Example 2: Search with trailing chip**
 * ```
 * // Before
 * PopInputFieldV2(
 *     value = searchText,
 *     onValueChange = { searchText = it },
 *     type = PopInputFieldType.Search,
 *     placeholder = "Search",
 *     searchBorderStyle = SearchBorderStyle.DefinedThin,
 *     endSlot = { /* chip content */ }
 * )
 *
 * // After
 * val config = SearchInputFieldConfig(
 *     value = searchText,
 *     onValueChange = { searchText = it },
 *     placeholder = "Search",
 *     searchBorderStyle = SearchBorderStyle.DefinedThin,
 *     endSlot = { /* chip content */ }
 * )
 * PopInputFieldV2(config = config)
 * ```
 *
 * **Example 3: Basic with icons**
 * ```
 * // Before
 * PopInputFieldV2(
 *     value = email,
 *     onValueChange = { email = it },
 *     type = PopInputFieldType.Basic,
 *     hint = "Email",
 *     startIcon = startIconConfig,
 *     endIcon = endIconConfig
 * )
 *
 * // After
 * val config = BasicInputFieldConfig(
 *     value = email,
 *     onValueChange = { email = it },
 *     hintText = "Email",
 *     startIcon = startIconConfig,
 *     endIcon = endIconConfig
 * )
 * PopInputFieldV2(config = config)
 * ```
 *
 * Currently supports:
 * - UnderlineNakedSmall: Naked small underline style with title, error, and success states
 * - UnderlineNakedLarge: Naked large underline style without title, error, and success states
 * - MobileInputField: Mobile number input with rounded rectangle, country code prefix, and phone icon
 * - InputField: Standard input with rounded rectangle, title above, and status messages
 *
 * Based on Figma design:
 * - Small: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=3825-5885
 * - Large: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=4354-21420
 * - Mobile: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=4235-14047
 * - InputField: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=3821-4239
 *
 * @param modifier Modifier to be applied to the component
 * @param value The current text value
 * @param onValueChange Callback when text changes
 * @param type The type of input field (default: UnderlineNakedSmall)
 * @param title The title label shown above the input (required for UnderlineNakedSmall, MobileInputField, and InputField types, optional for UnderlineNakedLarge)
 * @param placeholder Placeholder text shown when field is empty
 * @param status Status type for validation/feedback (Success, Error, Info, Warning)
 * @param statusMessage Status message to display below the field (shown when status is not null)
 * @param enabled Whether the field is enabled
 * @param readOnly Whether the field is read-only
 * @param keyboardType The keyboard type
 * @param imeAction The IME action
 * @param visualTransformation Visual transformation for the text
 * @param onImeAction Callback when IME action is triggered
 * @param startIcon Start icon config (for Basic type only)
 * @param endIcon End icon config (for Basic type only)
 * @param endSlot End slot composable (for Basic type only)
 * @param searchBorderStyle Border style for Search type (default: Subtle)
 * @param trailingChip Trailing chip/filter component for Search type (deprecated, use endSlot)
 * @param onClearClick Custom clear button handler for Search type (default: clears input)
 * @param useCustomKeypad When true, makes the field read-only and hides the system keyboard to work with PopKeyPad.
 *                       Use [handleKeyPadInput] to process keypad key presses. The components remain decoupled -
 *                       render PopKeyPad separately and connect them via value/onValueChange.
 *                       Note: This parameter is deprecated. Use [useCustomKeypad] in config-based API instead.
 *
 * @see PopInputFieldV2 for the recommended config-based overload
 */
@Deprecated(
    message = "Use the config-based overload instead: PopInputFieldV2(config = YourConfig(...)). " +
            "See migration guide in KDoc for details.",
    level = DeprecationLevel.WARNING
)
@Composable
fun PopInputFieldV2(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    type: PopInputFieldType = UnderlineNakedSmall,
    title: String? = null,
    hint: String? = null,
    placeholder: String? = null,
    animatedPlaceholder: (@Composable () -> Unit)? = null,
    status: InputFieldStatus? = null,
    statusMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    editable: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: () -> Unit = {},
    startIcon: PopIconConfig? = null,
    endIcon: PopIconConfig? = null,
    endSlot: (@Composable () -> Unit)? = null,
    searchBorderStyle: SearchBorderStyle? = null,
    trailingChip: (@Composable () -> Unit)? = null,
    onClearClick: (() -> Unit)? = null,
    useCustomKeypad: Boolean = false,
    paddingStart: Dp? = null,
    paddingEnd: Dp? = null,
    onFocusReceived: () -> Unit = {}
) {
    when (type) {
        UnderlineNakedSmall -> {
            require(title != null) { "Title is required for UnderlineNakedSmall type" }
            PopInputFieldUnderlineNakedSmall(
                value = value,
                onValueChange = onValueChange,
                title = title,
                modifier = modifier,
                placeholder = placeholder,
                status = status,
                statusMessage = statusMessage,
                enabled = enabled,
                readOnly = readOnly,
                editable = editable,
                keyboardType = keyboardType,
                imeAction = imeAction,
                visualTransformation = visualTransformation,
                onImeAction = onImeAction
            )
        }

        UnderlineNakedLarge -> {
            PopInputFieldUnderlineNakedLarge(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                placeholder = placeholder,
                status = status,
                statusMessage = statusMessage,
                enabled = enabled,
                readOnly = readOnly,
                editable = editable,
                keyboardType = keyboardType,
                imeAction = imeAction,
                visualTransformation = visualTransformation,
                onImeAction = onImeAction,
                useCustomKeypad = useCustomKeypad,
                shouldRequestFocus = false,
                onFocusReceived = {}
            )
        }

        PopInputFieldType.MobileInputField -> {
            require(title != null) { "Title is required for MobileInputField type" }
            PopInputFieldMobile(
                value = value,
                onValueChange = onValueChange,
                title = title,
                modifier = modifier,
                placeholder = placeholder,
                status = status,
                statusMessage = statusMessage,
                enabled = enabled,
                readOnly = readOnly,
                keyboardType = keyboardType,
                imeAction = imeAction,
                visualTransformation = visualTransformation,
                onImeAction = onImeAction
            )
        }

        PopInputFieldType.Basic -> {
            PopInputFieldBasic(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                hintText = hint ?: "",
                placeholder = placeholder,
                startIcon = startIcon,
                endIcon = endIcon,
                endSlot = endSlot,
                status = status,
                statusMessage = statusMessage,
                paddingEnd = paddingEnd,
                paddingStart = paddingStart,
            )
        }

        PopInputFieldType.Search -> {
            PopInputFieldSearch(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                placeholder = placeholder,
                animatedPlaceholder = animatedPlaceholder,
                searchBorderStyle = searchBorderStyle ?: Subtle,
                endSlot = trailingChip,
                onClearClick = onClearClick ?: { onValueChange("") },
                status = status,
                statusMessage = statusMessage,
                enabled = enabled,
                readOnly = readOnly,
                keyboardType = keyboardType,
                imeAction = if (imeAction == ImeAction.Next) ImeAction.Search else imeAction,
                visualTransformation = visualTransformation,
                onImeAction = onImeAction,
                onFocusReceived = onFocusReceived
            )
        }

        PopInputFieldType.Small -> {
            PopInputFieldSmall(
                value = value,
                onValueChange = onValueChange,
                modifier = modifier,
                title = title,
                placeholder = placeholder,
                status = status,
                statusMessage = statusMessage,
                enabled = enabled,
                readOnly = readOnly,
                keyboardType = keyboardType,
                imeAction = imeAction,
                visualTransformation = visualTransformation,
                onImeAction = onImeAction
            )
        }
    }
}

/**
 * PopInputFieldV2 - Config-based overload for better performance and type safety.
 *
 * This overload accepts a [PopInputFieldConfig] object, which provides:
 * - Better performance: Config objects are [@Immutable], reducing unnecessary recompositions
 * - Type safety: Each config class enforces required fields at compile time
 * - Cleaner API: Single config parameter instead of many individual parameters
 *
 * **Performance Note**: Using config-based approach is recommended when:
 * - You need to pass the same configuration to multiple fields
 * - You want to reduce parameter count and improve readability
 * - You need better recomposition performance (config objects are stable)
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
 *
 * **Migration from parameter-based to config-based**:
 * ```
 * // Before
 * PopInputFieldV2(
 *     value = text,
 *     onValueChange = { text = it },
 *     type = PopInputFieldType.UnderlineNakedSmall,
 *     title = "Name",
 *     placeholder = "Enter name"
 * )
 *
 * // After
 * val config = UnderlineNakedSmallConfig(
 *     value = text,
 *     onValueChange = { text = it },
 *     title = "Name",
 *     placeholder = "Enter name"
 * )
 * PopInputFieldV2(config = config)
 * ```
 *
 * @param config The configuration object for the input field
 * @param modifier Modifier to be applied to the component
 */
@Composable
fun PopInputFieldV2(
    config: PopInputFieldConfig,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester? = null
) {
    when (config) {
        is UnderlineNakedSmallConfig -> {
            PopInputFieldUnderlineNakedSmall(
                value = config.value,
                onValueChange = config.onValueChange,
                title = config.title,
                modifier = modifier,
                placeholder = config.placeholder,
                status = config.status,
                startIcon = config.startIcon,
                endIcon = config.endIcon,
                endSlot = config.endSlot,
                statusMessage = config.statusMessage,
                enabled = config.enabled,
                readOnly = config.readOnly,
                editable = config.editable,
                keyboardType = config.keyboardType,
                imeAction = config.imeAction,
                visualTransformation = config.visualTransformation,
                onImeAction = config.onImeAction
            )
        }

        is UnderlineNakedLargeConfig -> {
            PopInputFieldUnderlineNakedLarge(
                value = config.value,
                onValueChange = config.onValueChange,
                modifier = modifier,
                placeholder = config.placeholder,
                status = config.status,
                statusMessage = config.statusMessage,
                enabled = config.enabled,
                readOnly = config.readOnly,
                editable = config.editable,
                keyboardType = config.keyboardType,
                imeAction = config.imeAction,
                visualTransformation = config.visualTransformation,
                onImeAction = config.onImeAction,
                useCustomKeypad = config.useCustomKeypad,
                shouldRequestFocus = config.shouldRequestFocus,
                onFocusReceived = config.onFocusReceived
            )
        }

        is MobileInputFieldConfig -> {
            PopInputFieldMobile(
                value = config.value,
                onValueChange = config.onValueChange,
                title = config.title,
                modifier = modifier,
                placeholder = config.placeholder,
                status = config.status,
                statusMessage = config.statusMessage,
                enabled = config.enabled,
                readOnly = config.readOnly,
                keyboardType = config.keyboardType,
                imeAction = config.imeAction,
                visualTransformation = config.visualTransformation,
                onImeAction = config.onImeAction,
                onFocusReceived = config.onFocusReceived,
                onFocusChanged = config.onFocusChanged,
                focusRequester = focusRequester
            )
        }

        is BasicInputFieldConfig -> {
            PopInputFieldBasic(
                value = config.value,
                onValueChange = config.onValueChange,
                modifier = modifier,
                hintText = config.hintText,
                placeholder = config.placeholder,
                startIcon = config.startIcon,
                endIcon = config.endIcon,
                endSlot = config.endSlot,
                status = config.status,
                statusMessage = config.statusMessage,
                enabled = config.enabled,
                readOnly = config.readOnly,
                keyboardType = config.keyboardType,
                imeAction = config.imeAction,
                visualTransformation = config.visualTransformation,
                onImeAction = config.onImeAction,
                paddingStart = config.paddingStart,
                paddingEnd = config.paddingEnd,
            )
        }

        is BasicInputFieldConfigNoPadding -> {
            PopInputFieldBasicNoPadding(
                value = config.value,
                onValueChange = config.onValueChange,
                modifier = modifier,
                hintText = config.hintText,
                placeholder = config.placeholder,
                startIcon = config.startIcon,
                endIcon = config.endIcon,
                endSlot = config.endSlot,
                status = config.status,
                statusMessage = config.statusMessage,
                enabled = config.enabled,
                readOnly = config.readOnly,
                keyboardType = config.keyboardType,
                imeAction = config.imeAction,
                visualTransformation = config.visualTransformation,
                onImeAction = config.onImeAction
            )
        }

        is SearchInputFieldConfig -> {
            PopInputFieldSearch(
                value = config.value,
                onValueChange = config.onValueChange,
                modifier = modifier,
                placeholder = config.placeholder,
                animatedPlaceholder = config.animatedPlaceholder,
                searchBorderStyle = config.searchBorderStyle,
                endSlot = config.endSlot,
                onClearClick = config.onClearClick ?: { config.onValueChange("") },
                status = config.status,
                statusMessage = config.statusMessage,
                enabled = config.enabled,
                readOnly = config.readOnly,
                keyboardType = config.keyboardType,
                imeAction = config.imeAction,
                visualTransformation = config.visualTransformation,
                onImeAction = config.onImeAction,
                onFocusChanged = config.onFocusChanged
            )
        }

        is SmallInputFieldConfig -> {
            PopInputFieldSmall(
                value = config.value,
                onValueChange = config.onValueChange,
                modifier = modifier,
                title = config.title,
                placeholder = config.placeholder,
                status = config.status,
                statusMessage = config.statusMessage,
                enabled = config.enabled,
                readOnly = config.readOnly,
                keyboardType = config.keyboardType,
                imeAction = config.imeAction,
                visualTransformation = config.visualTransformation,
                onImeAction = config.onImeAction
            )
        }
    }
}

/**
 * Underline type implementation - bottom-line indicator style.
 */
@Composable
private fun PopInputFieldUnderlineNakedSmall(
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    status: InputFieldStatus? = null,
    statusMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    editable: Boolean = false,
    startIcon: PopIconConfig? = null,
    endIcon: PopIconConfig? = null,
    endSlot: (@Composable () -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: () -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }

    // Combined color and style calculations to reduce remember calls
    val (titleColor, textColor, placeholderColor, cursorBrush) = remember(enabled, isFocused, value) {
        val textColorValue = if (!enabled) TextColors.Primary.Default.copy(alpha = 0.5f) else TextColors.Primary.Default
        val titleColorValue = when {
            !enabled -> TextColor.Secondary.copy(alpha = 0.5f) // Dimmed when disabled
            else -> TextColor.Tertiary // Always gray (medium grey) when enabled, regardless of focus/filled state
        }
        val placeholderColorValue = if (!enabled) TextColor.Secondary.copy(alpha = 0.5f) else TextColor.Secondary
        val cursorBrushValue = SolidColor(if (enabled) PopColor.WhiteBlack.White else PopColor.WhiteBlack.White.copy(alpha = 0.5f))
        Quadruple(titleColorValue, textColorValue, placeholderColorValue, cursorBrushValue)
    }

    val keyboardActions = remember(onImeAction) { createKeyboardActions(onImeAction) }

    // Use separate modifier for Column to avoid conflicts with ConstraintLayout modifier
    // Column will be constrained, so we don't need to pass the original modifier to it

    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (startIconRef, endIconRef, endSlotRef, inputFieldRef) = createRefs()
        // End Slot - only added if provided (create first for reference)
        if (endSlot != null) {
            Box(
                modifier = Modifier.constrainAs(endSlotRef) {
                    end.linkTo(parent.end, margin = PopSpacing.Spacing16)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.wrapContent
                }
            ) {
                endSlot()
            }
        } else {
            // Invisible placeholder to maintain constraint structure
            Box(
                modifier = Modifier
                    .size(PopSpacing.Spacing0)
                    .constrainAs(endSlotRef) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
        // End Icon - only added if provided
        if (endIcon != null) {
            Box(
                modifier = Modifier.constrainAs(endIconRef) {
                    end.linkTo(endSlotRef.start, margin = PopSpacing.Spacing16)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                PopVisualElement(
                    visualElement = remember(endIcon) { endIcon.toVisualElement() },
                    modifier = Modifier.size(endIcon.size.toDp()),
                    contentDescription = endIcon.contentDescription
                )
            }
        } else {
            // Invisible placeholder to maintain constraint structure
            Box(
                modifier = Modifier
                    .size(PopSpacing.Spacing0)
                    .constrainAs(endIconRef) {
                        end.linkTo(endSlotRef.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
        // Start Icon - only added if provided
        if (startIcon != null) {
            Box(
                modifier = Modifier.constrainAs(startIconRef) {
                    start.linkTo(parent.start, margin = PopSpacing.Spacing16)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                PopVisualElement(
                    visualElement = remember(startIcon) { startIcon.toVisualElement() },
                    modifier = Modifier.size(startIcon.size.toDp()),
                    contentDescription = startIcon.contentDescription
                )
            }
        } else {
            // Invisible placeholder to maintain constraint structure
            Box(
                modifier = Modifier
                    .size(PopSpacing.Spacing0)
                    .constrainAs(startIconRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
        Column(
            modifier = Modifier.constrainAs(inputFieldRef) {
                width = Dimension.fillToConstraints
                height = Dimension.wrapContent
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(
                    startIconRef.end, margin = if (startIcon != null) {
                        PopSpacing.Spacing12
                    } else {
                        0.dp
                    }
                )
                end.linkTo(
                    endIconRef.start, margin = if (endIcon != null || endSlot != null) {
                        PopSpacing.Spacing12
                    } else {
                        0.dp
                    }
                )
            }
        ) {
            // Title label - always visible
            Text(
                text = title,
                style = TitleTextStyle.copy(color = titleColor),
                modifier = Modifier.padding(bottom = PopSpacing.Spacing8)
            )

            // Input field
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = PopSpacing.Spacing8)
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { isFocused = it.isFocused },
                    textStyle = PopTypography.figtreeStyles.paragraphLarge.copy(color = textColor),
                    enabled = enabled,
                    readOnly = readOnly || editable,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = imeAction
                    ),
                    keyboardActions = keyboardActions,
                    visualTransformation = visualTransformation,
                    singleLine = true,
                    cursorBrush = cursorBrush
                )

                // Placeholder
                if (value.isEmpty() && placeholder != null) {
                    Text(
                        text = placeholder,
                        style = PopTypography.figtreeStyles.paragraphLarge.copy(color = placeholderColor),
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
            }

            // Status message
            if (status != null && statusMessage != null) {
                StatusMessage(status = status, message = statusMessage)
            }
        }
    }
}

/**
 * UnderlineNakedLarge type implementation - large text without title.
 */
@Composable
private fun PopInputFieldUnderlineNakedLarge(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    status: InputFieldStatus? = null,
    statusMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    editable: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: () -> Unit = {},
    useCustomKeypad: Boolean = false,
    shouldRequestFocus: Boolean = false,
    onFocusReceived: () -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }
    // FocusRequester only needed for non-custom-keypad mode
    // In custom keypad mode, we never focus the BasicTextField to avoid triggering
    // the system keyboard (which causes a flash when switching from notes field)
    val focusRequester = remember(useCustomKeypad) {
        if (!useCustomKeypad) FocusRequester() else null
    }

    // In custom keypad mode, use TextFieldValue to track cursor position
    // Only create TextFieldValue state when keypad mode is enabled to avoid unnecessary state
    var textFieldValue by remember(useCustomKeypad) {
        mutableStateOf(
            if (useCustomKeypad) {
                TextFieldValue(text = value, selection = TextRange(value.length))
            } else {
                TextFieldValue() // Dummy value when not in keypad mode
            }
        )
    }

    // Sync TextFieldValue with value when value changes externally (e.g., from keypad)
    // Only sync when in keypad mode to avoid unnecessary updates
    LaunchedEffect(value, useCustomKeypad) {
        if (useCustomKeypad) {
            // Always move cursor to end when value changes from keypad
            // This ensures backspace works correctly by always deleting from the end
            textFieldValue = TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            )
        }
    }

    // In custom keypad mode, notify when the field becomes "active" (shouldRequestFocus = true)
    // We don't actually focus the BasicTextField to avoid triggering the system keyboard
    if (useCustomKeypad) {
        LaunchedEffect(shouldRequestFocus) {
            if (shouldRequestFocus) {
                onFocusReceived()
            }
        }
    }

    // Cursor blinking state
    // In custom keypad mode, cursor visibility is driven by shouldRequestFocus (not isFocused)
    // because we don't actually focus the BasicTextField (to prevent system keyboard flash)
    var showCursor by remember { mutableStateOf(true) }
    val cursorActive = if (useCustomKeypad) shouldRequestFocus && editable else isFocused

    LaunchedEffect(cursorActive) {
        if (cursorActive) {
            showCursor = true
            while (true) {
                delay(500)
                showCursor = !showCursor
            }
        } else {
            showCursor = false
        }
    }

    // Combined color calculations to reduce remember calls
    val (textColor, rupeeSymbolColor, cursorBrush) = remember(enabled, cursorActive, value, readOnly, editable) {
        val textColorValue = if (!enabled) TextColors.Primary.Default.copy(alpha = 0.5f) else TextColors.Primary.Default
        val rupeeSymbolColorValue = when {
            !enabled -> textColorValue
            cursorActive -> PopColor.WhiteBlack.White // White when active
            value.isNotEmpty() -> textColorValue // Same as text color when has text
            else -> TextColor.Secondary // Gray when empty and inactive
        }
        // In custom keypad mode, readOnly is always true (to suppress system keyboard)
        // but the field is still editable via the keypad — use editable to decide cursor visibility
        val effectiveReadOnly = readOnly && !editable
        val cursorBrushValue = if (effectiveReadOnly) {
            SolidColor(Color.Transparent) // Hide cursor when truly read-only
        } else {
            SolidColor(if (enabled) PopColor.WhiteBlack.White else PopColor.WhiteBlack.White.copy(alpha = 0.5f))
        }
        Triple(textColorValue, rupeeSymbolColorValue, cursorBrushValue)
    }

    val keyboardActions = remember(onImeAction) { createKeyboardActions(onImeAction) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Input field - large text, no title, with rupee prefix
        Box(
            modifier = modifier
                .padding(bottom = PopSpacing.Spacing8)
        ) {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Rupee symbol prefix - always visible, non-editable
                Text(
                    text = "₹",
                    style = PopTypography.figtreeStyles.displayLarge.copy(color = rupeeSymbolColor)
                )

                // Text input field for the amount
                if (useCustomKeypad) {
                    Box(contentAlignment = Alignment.Center) {
                        BasicTextField(
                            value = textFieldValue,
                            onValueChange = { newValue ->
                                // Keep cursor at end to prevent text drag/scroll in read-only keypad mode
                                val updatedValue = newValue.copy(selection = TextRange(newValue.text.length))
                                textFieldValue = updatedValue
                                onValueChange(updatedValue.text)
                            },
                            modifier = Modifier
                                .defaultMinSize(minWidth = 2.dp),
                            // Disabled + forced text color: the BasicTextField is never focused
                            // in custom keypad mode (input comes from PopKeyPad callbacks, not IME).
                            // Keeping it disabled prevents any tap from triggering the system keyboard.
                            textStyle = PopTypography.figtreeStyles.displayLarge.copy(color = TextColors.Primary.Default),
                            enabled = false,
                            readOnly = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = keyboardType,
                                imeAction = imeAction
                            ),
                            keyboardActions = keyboardActions,
                            visualTransformation = visualTransformation,
                            singleLine = false,
                            maxLines = 1,
                            cursorBrush = cursorBrush,
                        )
                        // Custom blinking cursor - show when keypad is active and field is editable
                        // Driven by cursorActive (= shouldRequestFocus && editable) since
                        // the BasicTextField is never focused in custom keypad mode
                        if (cursorActive && showCursor) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .height(48.dp)
                                    .width(2.dp)
                                    .background(PopColor.WhiteBlack.White)
                            )
                        }
                    }
                } else {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .defaultMinSize(minWidth = 2.dp)
                            .onFocusChanged { focusState ->
                                val wasFocused = isFocused
                                isFocused = focusState.isFocused
                                // Call onFocusReceived when focus is gained
                                if (focusState.isFocused && !wasFocused) {
                                    onFocusReceived()
                                }
                            },
                        textStyle = PopTypography.figtreeStyles.displayLarge.copy(color = textColor),
                        enabled = enabled,
                        readOnly = readOnly || editable,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = keyboardType,
                            imeAction = imeAction
                        ),
                        keyboardActions = keyboardActions,
                        visualTransformation = visualTransformation,
                        singleLine = true,
                        cursorBrush = cursorBrush
                    )
                }
            }
        }

        // Status message - center aligned for UnderlineNakedLarge variant
        if (status != null && statusMessage != null) {
            StatusMessage(
                status = status,
                message = statusMessage,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            )
        }
    }
}

/**
 * Basic type implementation - rounded rectangle input field with animated hint text and optional icons.
 */
@Composable
private fun PopInputFieldBasic(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    hintText: String = "",
    paddingStart: Dp? = null,
    paddingEnd: Dp? = null,
    placeholder: String? = null,
    startIcon: PopIconConfig? = null,
    endIcon: PopIconConfig? = null,
    endSlot: (@Composable () -> Unit)? = null,
    status: InputFieldStatus? = null,
    statusMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: () -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }

    // Active when focused OR when there's text (stays active even if focus is lost but text exists)
    // Becomes inactive when focus is lost AND value is empty
    val isActive = isFocused || value.isNotEmpty()

    // Animation for hint text position
    // When inactive: centered vertically
    // When active: moved to top
    val hintScale by animateFloatAsState(
        targetValue = if (isActive) 0.85f else 1f,
        label = "hintScale"
    )

    val squircleShape = remember { SquircleShape(cornerRadius = PopSpacing.Spacing16, smoothing = 1f) }

    // Determine border color based on state
    val borderColor = remember(enabled, status, isActive) {
        when {
            !enabled -> BorderColor.TertiaryTransparent40
            status == Error -> PopColor.Destructive.Destructive600
            status == Success -> PopColor.Success.Success600
            status == Warning -> PopColor.Warning.Warning600
            status == Info -> PopColor.Grey.Grey400
            isActive -> PopColor.WhiteBlack.White // White glow when active
            else -> BorderColor.TertiaryTransparent40
        }
    }

    // Background color: SurfaceColor.Secondary when inactive, same when active
    val backgroundColor = remember(isActive) {
        SurfaceColor.Secondary
    }

    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val inputFieldItem = createRef()
        val statusContainer = createRef()
        ConstraintLayout(
            modifier = Modifier
                .height(PopSpacing.Spacing56)
                .background(backgroundColor, squircleShape)
                .then(
                    if (enabled && isFocused) {
                        // Active state: border with glow effect (only when focused)
                        Modifier
                            .border(
                                PopStroke.Default,
                                borderColor.copy(alpha = 0.9f),
                                squircleShape
                            )
                            .glowEffect(
                                spreadRadius = 0.dp,
                                blurRadius = 8.dp,
                                color = borderColor.copy(alpha = 0.9f),
                                shape = squircleShape,
                                blurStyle = BlurMaskFilter.Blur.OUTER
                            )
                    } else if (borderColor != Color.Transparent) {
                        // Disabled or inactive state: border without glow
                        Modifier.border(
                            PopStroke.Default,
                            borderColor,
                            squircleShape
                        )
                    } else {
                        Modifier
                    }
                )
                .constrainAs(inputFieldItem) {
                    width = Dimension.fillToConstraints
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = paddingStart ?: PopSpacing.Spacing16)
                    end.linkTo(parent.end, margin = paddingEnd ?: PopSpacing.Spacing16)
                }
        ) {
            val (inputField, hintTextRef, startIconRef, endIconRef, endSlotRef) = createRefs()
            // End Slot - only added if provided (create first for reference)
            if (endSlot != null) {
                Box(
                    modifier = Modifier.constrainAs(endSlotRef) {
                        end.linkTo(parent.end, margin = PopSpacing.Spacing16)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.wrapContent
                    }
                ) {
                    endSlot()
                }
            } else {
                // Invisible placeholder to maintain constraint structure
                Box(
                    modifier = Modifier
                        .size(PopSpacing.Spacing0)
                        .constrainAs(endSlotRef) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
            // End Icon - only added if provided
            if (endIcon != null) {
                Box(
                    modifier = Modifier.constrainAs(endIconRef) {
                        end.linkTo(endSlotRef.start, margin = PopSpacing.Spacing16)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    PopVisualElement(
                        visualElement = remember(endIcon) { endIcon.toVisualElement() },
                        modifier = Modifier.size(endIcon.size.toDp()),
                        contentDescription = endIcon.contentDescription
                    )
                }
            } else {
                // Invisible placeholder to maintain constraint structure
                Box(
                    modifier = Modifier
                        .size(PopSpacing.Spacing0)
                        .constrainAs(endIconRef) {
                            end.linkTo(endSlotRef.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
            // Start Icon - only added if provided
            if (startIcon != null) {
                Box(
                    modifier = Modifier.constrainAs(startIconRef) {
                        start.linkTo(parent.start, margin = PopSpacing.Spacing16)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    PopVisualElement(
                        visualElement = remember(startIcon) { startIcon.toVisualElement() },
                        modifier = Modifier.size(startIcon.size.toDp()),
                        contentDescription = startIcon.contentDescription
                    )
                }
            } else {
                // Invisible placeholder to maintain constraint structure
                Box(
                    modifier = Modifier
                        .size(PopSpacing.Spacing0)
                        .constrainAs(startIconRef) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
            // Hint Text - animated position (centered when inactive, top when active)
            if (hintText.isNotEmpty()) {
                val hintTextColor = remember(isActive) {
                    if (isActive) TextColor.Tertiary else TextColor.Secondary
                }
                Text(
                    text = hintText,
                    color = hintTextColor,
                    style = PopTypography.figtreeStyles.paragraphMedium.copy(fontSize = (PopTypography.figtreeStyles.paragraphMedium.fontSize * hintScale)),
                    modifier = Modifier
                        .constrainAs(hintTextRef) {
                            start.linkTo(startIconRef.end, margin = PopSpacing.Spacing12)
                            if (isActive) {
                                top.linkTo(parent.top, margin = PopSpacing.Spacing8)
                            } else {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                            width = Dimension.wrapContent
                        }
                )
            } else {
                // Invisible placeholder
                Box(
                    modifier = Modifier
                        .size(PopSpacing.Spacing0)
                        .constrainAs(hintTextRef) {
                            start.linkTo(startIconRef.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
            // Input Field - always present but invisible when inactive, between startIcon and endIcon
            Box(
                modifier = Modifier.constrainAs(inputField) {
                    start.linkTo(startIconRef.end, margin = PopSpacing.Spacing12)
                    end.linkTo(endIconRef.start, margin = PopSpacing.Spacing12)
                    if (hintText.isNotEmpty() && isActive) {
                        top.linkTo(hintTextRef.bottom, margin = -PopSpacing.Spacing4)
                    } else {
                        top.linkTo(parent.top)
                    }
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
            ) {
                val keyboardActions = remember(onImeAction) { createKeyboardActions(onImeAction) }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .onFocusChanged { focusState -> isFocused = focusState.isFocused }
                        .fillMaxWidth()
                        .alpha(if (isActive) 1f else 0f), // Make invisible when inactive but keep in composition
                    textStyle = BodyTextStyle.copy(color = TextColor.Primary),
                    cursorBrush = remember { SolidColor(TextColor.Primary) },
                    enabled = enabled,
                    readOnly = readOnly,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = imeAction
                    ),
                    keyboardActions = keyboardActions,
                    visualTransformation = visualTransformation,
                    singleLine = true
                )

                // Placeholder - only visible when active (focused or has text) and empty
                // When inactive, the hint text serves as the placeholder
                if (isActive && value.isEmpty() && placeholder != null) {
                    Text(
                        text = placeholder,
                        style = BodyTextStyle.copy(color = TextColor.Disabled),
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
            }
        }
        // Status message container - only take space when status is present
        if (status != null && statusMessage != null) {
            Box(
                modifier = Modifier.constrainAs(statusContainer) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    top.linkTo(inputFieldItem.bottom, margin = PopSpacing.Spacing8)
                    start.linkTo(inputFieldItem.start)
                    end.linkTo(inputFieldItem.end)
                }
            ) {
                StatusMessage(status = status, message = statusMessage)
            }
        } else {
            // Invisible placeholder to maintain constraint structure when no status
            Box(
                modifier = Modifier
                    .size(PopSpacing.Spacing0)
                    .constrainAs(statusContainer) {
                        width = Dimension.fillToConstraints
                        height = Dimension.value(0.dp)
                        top.linkTo(inputFieldItem.bottom)
                        start.linkTo(inputFieldItem.start)
                        end.linkTo(inputFieldItem.end)
                    }
            )
        }
    }
}

/** Max length for mobile number input (digits only). */
private const val MOBILE_INPUT_MAX_LENGTH = 10

/**
 * MobileInputField type implementation - rounded rectangle with floating label, country code prefix and phone icon.
 */
@Composable
private fun PopInputFieldMobile(
    value: String,
    onValueChange: (String) -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    status: InputFieldStatus? = null,
    statusMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Phone,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onFocusReceived: () -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {},
    onImeAction: () -> Unit = {},
    focusRequester: FocusRequester? = null
) {
    var isFocused by remember { mutableStateOf(false) }

    // Country code prefix
    val countryCodePrefix = remember { "+91" }

    // Filter input to only accept numbers and limit to MOBILE_INPUT_MAX_LENGTH digits
    val filteredOnValueChange = remember(onValueChange) {
        { newValue: String ->
            val digitsOnly = newValue.filter { it.isDigit() }
            val limitedValue = digitsOnly.take(MOBILE_INPUT_MAX_LENGTH)
            onValueChange(limitedValue)
        }
    }

    // Normalize value to max length for display (e.g. when set from Google phone hint)
    val displayValue = remember(value) {
        value.filter { it.isDigit() }.take(MOBILE_INPUT_MAX_LENGTH)
    }

    // Use TextFieldValue so we can place cursor at end when value is set externally (e.g. from Google phone hint)
    var textFieldValue by remember { mutableStateOf(TextFieldValue(displayValue, TextRange(displayValue.length))) }
    LaunchedEffect(displayValue) {
        if (textFieldValue.text != displayValue) {
            textFieldValue = TextFieldValue(displayValue, TextRange(displayValue.length))
        }
    }

    // Determine if label should be at top (focused or has text) or center (inactive)
    val isLabelAtTop = isFocused || value.isNotEmpty()

    // Animate label font size (16sp when center, 12sp when top)
    val labelFontSize by animateFloatAsState(
        targetValue = if (isLabelAtTop) 12f else 16f,
        animationSpec = tween(DEFAULT_ANIMATION_DURATION),
        label = "labelFontSize"
    )

    // Target border color based on state — animateColorAsState interpolates between states
    val targetBorderColor = when {
        !enabled -> BorderColor.Tertiary
        status == Error -> PopColor.Destructive.Destructive600
        status == Success -> PopColor.Success.Success600
        status == Warning -> PopColor.Warning.Warning600
        status == Info -> PopColor.Grey.Grey400
        isFocused -> PopColor.WhiteBlack.White
        else -> BorderColor.Tertiary
    }
    val borderColor by animateColorAsState(
        targetValue = targetBorderColor,
        animationSpec = tween(DEFAULT_ANIMATION_DURATION, easing = FastOutSlowInEasing),
        label = "borderColor"
    )

    // Glow intensity: fades in to 0.9 on focus, fades out to 0 on blur
    val glowAlpha by animateFloatAsState(
        targetValue = if (enabled && isFocused) 0.9f else 0f,
        animationSpec = tween(DEFAULT_ANIMATION_DURATION + 50, easing = FastOutSlowInEasing),
        label = "glowAlpha"
    )

    // Border width: Strokes/x (0.5) from Figma node 4235-14035
    val borderWidth = PopStroke.Default

    // Determine background color based on state
    val backgroundColor = remember(enabled) {
        if (!enabled) {
            SurfaceColor.SecondaryDisabled // Slightly lighter dark grey for disabled
        } else {
            SurfaceColor.Secondary
        }
    }

    // Determine label color based on state
    val labelColor = remember(enabled, isLabelAtTop) {
        when {
            !enabled -> TextColor.Secondary.copy(alpha = 0.5f) // Dimmed
            isLabelAtTop -> TextColor.Tertiary // Tertiary when active (at top)
            else -> TextColor.Secondary // Secondary when inactive (at center)
        }
    }

    // Determine text color based on state - Primary when user enters text
    val textColor = remember(enabled, value) {
        when {
            !enabled -> TextColor.Secondary.copy(alpha = 0.5f) // Dimmed
            else -> TextColor.Primary // Primary when enabled
        }
    }

    // Determine icon color based on state
    val iconColor = remember(enabled, isLabelAtTop) {
        when {
            !enabled -> IconColor.Tertiary.copy(alpha = 0.5f) // Dimmed
            isLabelAtTop -> IconColor.Primary // Primary when active
            else -> IconColor.Tertiary // Tertiary when inactive
        }
    }

    // Determine country code prefix color based on state
    val prefixColor = remember(enabled, value, isFocused) {
        when {
            !enabled -> TextColor.Secondary.copy(alpha = 0.5f)
            value.isNotEmpty() -> PopColor.WhiteBlack.White // White when has text
            isFocused -> PopColor.WhiteBlack.White // White when focused
            else -> TextColor.Secondary // Light grey when empty and unfocused
        }
    }

    // Same shape as Search variant: SquircleShape with PopRadius.Medium (consistent Active/Inactive)
    val fieldShape = remember { SquircleShape(cornerRadius = PopRadius.Medium, smoothing = 1f) }
    val cursorBrush = remember(enabled) {
        SolidColor(if (enabled) TextColor.Primary else TextColor.Primary.copy(alpha = 0.5f))
    }
    val keyboardActions = remember(onImeAction) { createKeyboardActions(onImeAction) }

    // Label animation values
    val topPadding = remember { PopSpacing.Spacing8.value }
    val centerPadding = remember { 18f } // Center accounting for text height
    val animatedTopPadding by animateFloatAsState(
        targetValue = if (isLabelAtTop) topPadding else centerPadding,
        animationSpec = tween(DEFAULT_ANIMATION_DURATION),
        label = "labelTopPadding"
    )

    val inputRowTopPadding by animateFloatAsState(
        // 16f = visual center of the 56dp box for paragraphMedium (24dp line-height): (56-24)/2 = 16
        // 26f = final position when label has floated to the top
        targetValue = if (isLabelAtTop) 26f else 16f,
        animationSpec = tween(DEFAULT_ANIMATION_DURATION),
        label = "inputRowTopPadding"
    )

    // Drives the cursor's slide-up entrance: progress=1 → fully below clip, progress=0 → in place
    val innerFieldTranslationProgress by animateFloatAsState(
        targetValue = if (isLabelAtTop) 0f else 1f,
        animationSpec = tween(DEFAULT_ANIMATION_DURATION),
        label = "innerFieldTranslationProgress"
    )
    val innerFieldAlpha by animateFloatAsState(
        targetValue = if (isLabelAtTop) 1f else 0f,
        animationSpec = tween(DEFAULT_ANIMATION_DURATION),
        label = "innerFieldAlpha"
    )

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Input field with squircle background (same shape as Search variant; consistent Active/Inactive)
        // clickable on whole Box so tapping the upper part (label area) also focuses the field
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(PopSpacing.Spacing56)
                .clickable(
                    enabled = enabled,
                    onClick = { focusRequester?.requestFocus() },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .background(color = backgroundColor, shape = fieldShape)
                // Border always present — color interpolates via animateColorAsState
                .border(borderWidth, borderColor, fieldShape)
                // Glow fades in/out via glowAlpha; skip the draw call entirely once fully faded
                .then(
                    if (glowAlpha > 0.01f) {
                        Modifier.glowEffect(
                            spreadRadius = 0.dp,
                            blurRadius = 8.dp,
                            color = borderColor.copy(alpha = glowAlpha),
                            shape = fieldShape,
                            blurStyle = BlurMaskFilter.Blur.OUTER
                        )
                    } else Modifier
                )
                .padding(horizontal = PopSpacing.Spacing16)
        ) {
            // Label that animates from center to top inside the container (acts as placeholder when inactive)
            // Clicking the placeholder/label requests focus so keyboard appears
            Text(
                text = title,
                style = if (isLabelAtTop) {
                    PopTypography.figtreeStyles.labelSmall
                } else {
                    PopTypography.figtreeStyles.paragraphMedium.copy(fontWeight = FontWeight.Normal)
                }.copy(
                    fontSize = labelFontSize.sp,
                    color = labelColor
                ),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = animatedTopPadding.dp)
                    .then(
                        if (enabled) Modifier.clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { focusRequester?.requestFocus() }
                        else Modifier
                    )
            )

            // Input content row - always anchored to TopStart; position driven by animated padding
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .padding(
                        top = inputRowTopPadding.dp,
                        end = (PopSpacing.Spacing16.value + PopSpacing.Spacing8.value + PopSpacing.Spacing16.value).dp // Icon (16) + spacing (8) + end padding (16)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Country code prefix - starts fully below the clip boundary and slides up, matching the label's upward direction
                androidx.compose.animation.AnimatedVisibility(
                    visible = isLabelAtTop,
                    enter = fadeIn(tween(DEFAULT_ANIMATION_DURATION)) +
                            slideInVertically(tween(DEFAULT_ANIMATION_DURATION)) { it },
                    exit = fadeOut(tween(DEFAULT_ANIMATION_DURATION)) +
                            slideOutVertically(tween(DEFAULT_ANIMATION_DURATION)) { it }
                ) {
                    Text(
                        text = "$countryCodePrefix ",
                        style = BodyTextStyle.copy(color = prefixColor)
                    )
                }

                // Text input field for the phone number
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = { newValue ->
                        val digitsOnly = newValue.text.filter { it.isDigit() }
                        val limited = digitsOnly.take(MOBILE_INPUT_MAX_LENGTH)
                        val clampedValue = TextFieldValue(
                            text = limited,
                            selection = TextRange(
                                newValue.selection.start.coerceIn(0, limited.length),
                                newValue.selection.end.coerceIn(0, limited.length)
                            )
                        )
                        textFieldValue = clampedValue
                        filteredOnValueChange(limited)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .then(focusRequester?.let { Modifier.focusRequester(it) } ?: Modifier)
                        .onFocusChanged { focusState ->
                            val wasFocused = isFocused
                            isFocused = focusState.isFocused
                            onFocusChanged(focusState.isFocused)
                            if (focusState.isFocused && !wasFocused) {
                                onFocusReceived()
                            }
                        },
                    textStyle = BodyTextStyle.copy(color = textColor),
                    enabled = enabled,
                    readOnly = readOnly,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = imeAction
                    ),
                    keyboardActions = keyboardActions,
                    visualTransformation = visualTransformation,
                    singleLine = true,
                    cursorBrush = cursorBrush,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            // Placeholder drawn first (behind) so the cursor from innerTextField renders on top.
                            // Starts fully below the clip boundary and slides up, matching the label's upward direction.
                            androidx.compose.animation.AnimatedVisibility(
                                visible = isLabelAtTop && value.isEmpty() && placeholder != null,
                                enter = fadeIn(tween(DEFAULT_ANIMATION_DURATION)) +
                                        slideInVertically(tween(DEFAULT_ANIMATION_DURATION)) { it },
                                exit = fadeOut(tween(DEFAULT_ANIMATION_DURATION)) +
                                        slideOutVertically(tween(DEFAULT_ANIMATION_DURATION)) { it }
                            ) {
                                if (placeholder != null) {
                                    Text(
                                        text = placeholder,
                                        style = BodyTextStyle.copy(color = TextColor.Disabled)
                                    )
                                }
                            }
                            // Cursor (and typed text) slide up from below, matching the placeholder entrance.
                            // clip=true keeps the cursor hidden below the boundary until it slides in.
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .graphicsLayer {
                                        alpha = innerFieldAlpha
                                        translationY = innerFieldTranslationProgress * 24.dp.toPx()
                                        clip = true
                                    }
                            ) {
                                innerTextField()
                            }
                        }
                    }
                )
            }

            // Trailing phone icon - fixed position, always vertically centered
            val mobileIconVisualElement = remember(iconColor) {
                com.pop.components.models.VisualElement.buildFrom(
                    iconName = PopIcons.Mobile,
                    style = IconStyle.Filled,
                    tintColor = iconColor,
                    heightDp = IconSize.Small.toDp().value.toInt(),
                    widthDp = IconSize.Small.toDp().value.toInt()
                )
            }
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = PopSpacing.Spacing8)
            ) {
                PopVisualElement(
                    visualElement = mobileIconVisualElement,
                    modifier = Modifier.size(IconSize.Small.toDp()),
                    contentDescription = "Phone"
                )
            }
        }

        // Status message
        if (status != null && statusMessage != null) {
            StatusMessage(status = status, message = statusMessage)
        }
    }
}

/**
 * Basic type implementation without horizontal padding - identical to PopInputFieldBasic but with 0 horizontal margins.
 */
@Composable
private fun PopInputFieldBasicNoPadding(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    hintText: String = "",
    placeholder: String? = null,
    startIcon: PopIconConfig? = null,
    endIcon: PopIconConfig? = null,
    endSlot: (@Composable () -> Unit)? = null,
    status: InputFieldStatus? = null,
    statusMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: () -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }

    // Active when focused OR when there's text (stays active even if focus is lost but text exists)
    // Becomes inactive when focus is lost AND value is empty
    val isActive = isFocused || value.isNotEmpty()

    // Animation for hint text position
    // When inactive: centered vertically
    // When active: moved to top
    val hintScale by animateFloatAsState(
        targetValue = if (isActive) 0.85f else 1f,
        label = "hintScale"
    )

    val squircleShape = remember { SquircleShape(cornerRadius = 12.dp, smoothing = 1f) }

    // Determine border color based on state
    val borderColor = remember(enabled, status, isActive) {
        when {
            !enabled -> BorderColor.TertiaryTransparent40
            status == Error -> PopColor.Destructive.Destructive600
            status == Success -> PopColor.Success.Success600
            status == Warning -> PopColor.Warning.Warning600
            status == Info -> PopColor.Grey.Grey400
            isActive -> PopColor.WhiteBlack.White // White glow when active
            else -> BorderColor.TertiaryTransparent40
        }
    }

    // Background color: SurfaceColor.Secondary when inactive, same when active
    val backgroundColor = remember(isActive) {
        SurfaceColor.Secondary
    }

    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val inputFieldItem = createRef()
        val statusContainer = createRef()
        ConstraintLayout(
            modifier = Modifier
                .height(PopSpacing.Spacing56)
                .background(backgroundColor, squircleShape)
                .then(
                    if (enabled && isFocused) {
                        // Active state: border with glow effect (only when focused)
                        Modifier
                            .border(
                                PopStroke.Default,
                                borderColor.copy(alpha = 0.9f),
                                squircleShape
                            )
                            .glowEffect(
                                spreadRadius = 0.dp,
                                blurRadius = 8.dp,
                                color = borderColor.copy(alpha = 0.9f),
                                shape = squircleShape,
                                blurStyle = BlurMaskFilter.Blur.OUTER
                            )
                    } else if (borderColor != Color.Transparent) {
                        // Disabled or inactive state: border without glow
                        Modifier.border(
                            PopStroke.Default,
                            borderColor,
                            squircleShape
                        )
                    } else {
                        Modifier
                    }
                )
                .constrainAs(inputFieldItem) {
                    width = Dimension.fillToConstraints
                    top.linkTo(parent.top)
                    start.linkTo(parent.start) // No horizontal margin
                    end.linkTo(parent.end) // No horizontal margin
                }
        ) {
            val (inputField, hintTextRef, startIconRef, endIconRef, endSlotRef) = createRefs()
            // End Slot - only added if provided (create first for reference)
            if (endSlot != null) {
                Box(
                    modifier = Modifier.constrainAs(endSlotRef) {
                        end.linkTo(parent.end, margin = PopSpacing.Spacing16)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.wrapContent
                    }
                ) {
                    endSlot()
                }
            } else {
                // Invisible placeholder to maintain constraint structure
                Box(
                    modifier = Modifier
                        .size(PopSpacing.Spacing0)
                        .constrainAs(endSlotRef) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
            // End Icon - only added if provided
            if (endIcon != null) {
                Box(
                    modifier = Modifier.constrainAs(endIconRef) {
                        end.linkTo(endSlotRef.start, margin = PopSpacing.Spacing16)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    PopVisualElement(
                        visualElement = remember(endIcon) { endIcon.toVisualElement() },
                        modifier = Modifier.size(endIcon.size.toDp()),
                        contentDescription = endIcon.contentDescription
                    )
                }
            } else {
                // Invisible placeholder to maintain constraint structure
                Box(
                    modifier = Modifier
                        .size(PopSpacing.Spacing0)
                        .constrainAs(endIconRef) {
                            end.linkTo(endSlotRef.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
            // Start Icon - only added if provided
            if (startIcon != null) {
                Box(
                    modifier = Modifier.constrainAs(startIconRef) {
                        start.linkTo(parent.start, margin = PopSpacing.Spacing16)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    PopVisualElement(
                        visualElement = remember(startIcon) { startIcon.toVisualElement() },
                        modifier = Modifier.size(startIcon.size.toDp()),
                        contentDescription = startIcon.contentDescription
                    )
                }
            } else {
                // Invisible placeholder to maintain constraint structure
                Box(
                    modifier = Modifier
                        .size(PopSpacing.Spacing0)
                        .constrainAs(startIconRef) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
            // Hint Text - animated position (centered when inactive, top when active)
            if (hintText.isNotEmpty()) {
                val hintTextColor = remember(isActive) {
                    if (isActive) TextColor.Tertiary else TextColor.Secondary
                }
                Text(
                    text = hintText,
                    color = hintTextColor,
                    style = PopTypography.figtreeStyles.paragraphMedium.copy(fontSize = (PopTypography.figtreeStyles.paragraphMedium.fontSize * hintScale)),
                    modifier = Modifier
                        .constrainAs(hintTextRef) {
                            start.linkTo(startIconRef.end, margin = PopSpacing.Spacing12)
                            if (isActive) {
                                top.linkTo(parent.top, margin = PopSpacing.Spacing8)
                            } else {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                            width = Dimension.wrapContent
                        }
                )
            } else {
                // Invisible placeholder
                Box(
                    modifier = Modifier
                        .size(PopSpacing.Spacing0)
                        .constrainAs(hintTextRef) {
                            start.linkTo(startIconRef.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                )
            }
            // Input Field - always present but invisible when inactive, between startIcon and endIcon
            Box(
                modifier = Modifier.constrainAs(inputField) {
                    start.linkTo(startIconRef.end, margin = PopSpacing.Spacing12)
                    end.linkTo(endIconRef.start, margin = PopSpacing.Spacing12)
                    if (hintText.isNotEmpty() && isActive) {
                        top.linkTo(hintTextRef.bottom, margin = -PopSpacing.Spacing4)
                    } else {
                        top.linkTo(parent.top)
                    }
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
            ) {
                val keyboardActions = remember(onImeAction) { createKeyboardActions(onImeAction) }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .onFocusChanged { focusState -> isFocused = focusState.isFocused }
                        .fillMaxWidth()
                        .alpha(if (isActive) 1f else 0f), // Make invisible when inactive but keep in composition
                    textStyle = BodyTextStyle.copy(color = TextColor.Primary),
                    cursorBrush = remember { SolidColor(TextColor.Primary) },
                    enabled = enabled,
                    readOnly = readOnly,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = imeAction
                    ),
                    keyboardActions = keyboardActions,
                    visualTransformation = visualTransformation,
                    singleLine = true
                )

                // Placeholder - only visible when active (focused or has text) and empty
                // When inactive, the hint text serves as the placeholder
                if (isActive && value.isEmpty() && placeholder != null) {
                    Text(
                        text = placeholder,
                        style = BodyTextStyle.copy(color = TextColor.Disabled),
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
            }
        }
        // Status message container - only take space when status is present
        if (status != null && statusMessage != null) {
            Box(
                modifier = Modifier.constrainAs(statusContainer) {
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                    top.linkTo(inputFieldItem.bottom, margin = PopSpacing.Spacing8)
                    start.linkTo(inputFieldItem.start)
                    end.linkTo(inputFieldItem.end)
                }
            ) {
                StatusMessage(status = status, message = statusMessage)
            }
        } else {
            // Invisible placeholder to maintain constraint structure when no status
            Box(
                modifier = Modifier
                    .size(PopSpacing.Spacing0)
                    .constrainAs(statusContainer) {
                        width = Dimension.fillToConstraints
                        height = Dimension.value(0.dp)
                        top.linkTo(inputFieldItem.bottom)
                        start.linkTo(inputFieldItem.start)
                        end.linkTo(inputFieldItem.end)
                    }
            )
        }
    }
}

/**
 * Search type implementation - rounded rectangle input field with search icon, clear button, and optional trailing chip.
 * Based on Figma design: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=4536-12367
 */
@Composable
private fun PopInputFieldSearch(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    animatedPlaceholder: (@Composable () -> Unit)? = null,
    searchBorderStyle: SearchBorderStyle = Subtle,
    endSlot: (@Composable () -> Unit)? = null,
    onClearClick: () -> Unit = {},
    status: InputFieldStatus? = null,
    statusMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Search,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: () -> Unit = {},
    onFocusReceived: () -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    // Active when focused OR when there's text (stays active even if focus is lost but text exists)
    // Becomes inactive when focus is lost AND value is empty
    val isActive = isFocused || value.isNotEmpty()

    // SquircleShape with 12dp corner radius (Radius/12 from Figma)
    val squircleShape = remember { SquircleShape(cornerRadius = PopRadius.Medium, smoothing = 1f) }
    // Use RoundedCornerShape for glow effect to match border outline
    val glowShape = remember { RoundedCornerShape(PopRadius.Medium) }

    // Determine border color based on state and border style
    val borderColor = remember(enabled, status, isActive, searchBorderStyle) {
        when {
            !enabled -> BorderColor.TertiaryTransparent40
            status == Error -> PopColor.Destructive.Destructive600
            status == Success -> PopColor.Success.Success600
            status == Warning -> PopColor.Warning.Warning600
            status == Info -> PopColor.Grey.Grey400
            isActive -> PopColor.WhiteBlack.White // White glow when active
            searchBorderStyle == DefinedThin -> BorderColor.Secondary // Border/Secondary (#333333) - always visible
            else -> Color.Transparent // Subtle - invisible in default
        }
    }

    // Border width: 0.5dp (Strokes/x from Figma)
    val borderWidth = if (borderColor == Color.Transparent) PopSpacing.Spacing0 else PopStroke.Default

    // Background color
    val backgroundColor = remember(enabled) {
        if (!enabled) {
            SurfaceColor.Secondary
        } else {
            SurfaceColor.Secondary
        }
    }

    // Text color
    val textColor = remember(enabled) {
        if (!enabled) {
            TextColor.Disabled // Text/Primary-Disabled: #4d4d4d
        } else {
            TextColor.Primary
        }
    }

    // Placeholder color
    val placeholderColor = remember(enabled) {
        if (!enabled) {
            TextColor.Secondary // Text/Secondary
        } else {
            TextColor.Secondary // Text/Secondary: #b3b3b3
        }
    }

    // Search icon color
    val searchIconColor = remember(enabled, isFocused, value) {
        when {
            !enabled -> IconColor.Tertiary // IconColor.Tertiary
            isFocused || value.isNotEmpty() -> IconColor.Primary // Icons/Primary: #e6e6e6
            else -> IconColor.Tertiary // Icons/Tertiary: #808080
        }
    }

    // Clear icon color
    val clearIconColor = remember(enabled) {
        if (!enabled) {
            IconColor.SecondaryDisabled // Icons/Secondary-Disabled: #262626
        } else {
            TextColor.Primary // Matches text color when enabled
        }
    }

    val cursorBrush = remember(enabled) {
        SolidColor(if (enabled) TextColor.Primary else TextColor.Disabled)
    }

    val keyboardActions = remember(onImeAction) {
        KeyboardActions(
            onSearch = { onImeAction() },
            onDone = { onImeAction() },
            onNext = { onImeAction() },
            onGo = { onImeAction() }
        )
    }

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // Input field container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(PopSpacing.Spacing56) // Standard search field height
                .clickable(
                    enabled = enabled,
                    onClick = {
                        focusRequester.requestFocus()
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
                .background(color = backgroundColor, shape = squircleShape)
                .then(
                    if (enabled && isFocused) {
                        // Use glow effect for enabled focused state (only when cursor is active)
                        // Apply border and glow to match Basic type behavior
                        Modifier
                            .border(
                                PopStroke.Default,
                                borderColor.copy(alpha = 0.9f),
                                squircleShape
                            )
                            .glowEffect(
                                spreadRadius = 0.dp,
                                blurRadius = 8.dp,
                                color = borderColor.copy(alpha = 0.9f),
                                shape = squircleShape,
                                blurStyle = BlurMaskFilter.Blur.OUTER,
                            )
                    } else if (borderWidth > PopSpacing.Spacing0) {
                        // Use regular border when inactive but border should be visible (e.g., DefinedThin style)
                        Modifier.border(borderWidth, borderColor, squircleShape)
                    } else {
                        Modifier
                    }
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = PopSpacing.Spacing12), // Spacing/12 from Figma
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                // Search icon - always visible on the left
                val searchIconVisualElement = remember(searchIconColor) {
                    com.pop.components.models.VisualElement.buildFrom(
                        iconName = PopIcons.Search,
                        style = IconStyle.Filled,
                        tintColor = searchIconColor,
                        heightDp = IconSize.Small.toDp().value.toInt(),
                        widthDp = IconSize.Small.toDp().value.toInt()
                    )
                }
                PopVisualElement(
                    visualElement = searchIconVisualElement,
                    modifier = Modifier
                        .size(IconSize.Small.toDp())
                        .clickable(
                            enabled = enabled,
                            onClick = {
                                focusRequester.requestFocus()
                            },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    contentDescription = "Search"
                )

                Spacer(modifier = Modifier.width(PopSpacing.Spacing12)) // Spacing between icon and text

                // Text input field
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                val wasFocused = isFocused
                                isFocused = focusState.isFocused
                                onFocusChanged(focusState.isFocused)
                                // Call onFocusReceived when focus is gained
                                if (focusState.isFocused && !wasFocused) {
                                    onFocusReceived()
                                }
                            },
                        textStyle = PopTypography.figtreeStyles.labelMedium.copy(
                            color = textColor
                        ),
                        enabled = enabled,
                        readOnly = readOnly,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = keyboardType,
                            imeAction = imeAction
                        ),
                        keyboardActions = keyboardActions,
                        visualTransformation = visualTransformation,
                        singleLine = true,
                        cursorBrush = cursorBrush
                    )

                    // Placeholder text (static or animated)
                    if (value.isEmpty()) {
                        when {
                            animatedPlaceholder != null -> {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.CenterStart)
                                        .graphicsLayer { clip = false }
                                ) {
                                    animatedPlaceholder()
                                }
                            }
                            placeholder != null -> {
                                Text(
                                    text = placeholder,
                                    style = PopTypography.figtreeStyles.labelMedium.copy(
                                        color = placeholderColor
                                    ),
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        }
                    }
                }

                // Clear button - only visible when text is not empty
                if (value.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(PopSpacing.Spacing12))
                    Icon(
                        imageVector = MaterialIcons.Filled.Close,
                        contentDescription = "Clear",
                        tint = clearIconColor,
                        modifier = Modifier
                            .size(PopIconsSizes.sizeSmall)
                            .clickable(
                                enabled = enabled && !readOnly,
                                onClick = onClearClick,
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            )
                    )
                }

                // Trailing chip - positioned after clear button (or at end if no clear button)
                if (endSlot != null) {
                    if (value.isNotEmpty()) {
                        Spacer(modifier = Modifier.width(PopSpacing.Spacing8))
                    } else {
                        Spacer(modifier = Modifier.width(PopSpacing.Spacing12))
                    }
                    endSlot()
                }
            }
        }

        // Status message
        if (status != null && statusMessage != null) {
            StatusMessage(status = status, message = statusMessage)
        }
    }
}

/**
 * Small type implementation - compact input field with 28dp height, 8dp border radius, and 12px text.
 * Based on Figma design: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=8220-47609
 */
@Composable
private fun PopInputFieldSmall(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    placeholder: String? = null,
    status: InputFieldStatus? = null,
    statusMessage: String? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onImeAction: () -> Unit = {}
) {
    var isFocused by remember { mutableStateOf(false) }
    val isFilled = value.isNotEmpty()
    // Active when focused OR when there's text (stays active even if focus is lost but text exists)
    val isActive = isFocused || value.isNotEmpty()

    // SquircleShape with 8dp corner radius (Radius/8 from Figma)
    val squircleShape = remember { SquircleShape(cornerRadius = PopRadius.Small, smoothing = 1f) }

    // Determine border color based on state
    val borderColor = remember(enabled, status, isActive) {
        when {
            !enabled -> BorderColor.Tertiary.copy(alpha = 0.4f)
            status == Error -> PopColor.Destructive.Destructive600
            status == Success -> PopColor.Success.Success600
            status == Warning -> PopColor.Warning.Warning600
            status == Info -> PopColor.Grey.Grey400
            isActive -> PopColor.WhiteBlack.White.copy(alpha = 0.9f) // White glow when active
            else -> BorderColor.Tertiary.copy(alpha = 0.4f) // Default subtle border
        }
    }

    // Border width: 0.5dp (Strokes/x from Figma)
    val borderWidth = PopStroke.Default

    // Background color - Secondary by default
    val backgroundColor = remember(enabled) {
        if (!enabled) {
            SurfaceColor.Secondary.copy(alpha = 0.6f)
        } else {
            SurfaceColor.Secondary
        }
    }

    // Text color
    val textColor = remember(enabled) {
        if (!enabled) {
            TextColor.Primary.copy(alpha = 0.5f) // Text/Primary-Disabled: #4d4d4d
        } else {
            TextColor.Primary
        }
    }

    // Placeholder color
    val placeholderColor = remember(enabled) {
        if (!enabled) {
            TextColor.Primary.copy(alpha = 0.5f) // Text/Primary-Disabled: #4d4d4d
        } else {
            TextColor.Primary.copy(alpha = 0.5f) // Text/Primary-Disabled when enabled but empty
        }
    }

    // Title color (shown when inactive and empty)
    val titleColor = remember(enabled) {
        if (!enabled) {
            TextColor.Secondary.copy(alpha = 0.5f) // Dimmed when disabled
        } else {
            TextColor.Secondary // Text/Secondary: #b3b3b3
        }
    }

    val cursorBrush = remember(enabled) {
        SolidColor(if (enabled) TextColor.Primary else TextColor.Primary.copy(alpha = 0.5f))
    }

    val keyboardActions = remember(onImeAction) { createKeyboardActions(onImeAction) }

    // Text measurer for detecting overflow
    val textMeasurer = rememberTextMeasurer()
    val textStyle = PopTypography.figtreeStyles.paragraphXSmall.copy(color = textColor)

    // Container width state for overflow detection
    var containerWidthPx by remember { mutableFloatStateOf(0f) }

    // Text width state - measured asynchronously to avoid blocking recomposition
    var textWidth by remember { mutableFloatStateOf(0f) }

    // Measure text width asynchronously when value or container size changes
    LaunchedEffect(value, containerWidthPx, textStyle) {
        textWidth = if (value.isNotEmpty() && containerWidthPx > 0f) {
            textMeasurer.measure(
                text = AnnotatedString(value),
                style = textStyle,
                softWrap = false,
                maxLines = 1
            ).size.width.toFloat()
        } else {
            0f
        }
    }

    // Focus manager for clearing focus on outside click
    val focusManager = LocalFocusManager.current

    // Use Column with fillMaxWidth so status message can fill available width
    // Input field is centered, status message spans full width
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Wrap input field in Row with IntrinsicSize.Min to constrain width to content
        // This prevents the input field from expanding when focused but empty
        Row(
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {
            // Input field container - apply external modifier here for width constraint
            Box(
                modifier = modifier
                    .height(PopSpacing.Spacing28) // Height from Figma
                    .background(color = backgroundColor, shape = squircleShape)
                    .then(
                        if (enabled && isFocused) {
                            Modifier
                                .border(
                                    PopStroke.Default,
                                    borderColor.copy(alpha = 0.9f),
                                    squircleShape
                                )
                                .glowEffect(
                                    spreadRadius = 0.dp,
                                    blurRadius = 8.dp,
                                    color = borderColor.copy(alpha = 0.9f),
                                    shape = squircleShape,
                                    blurStyle = BlurMaskFilter.Blur.OUTER,
                                )
                        } else {
                            Modifier.border(borderWidth, borderColor, squircleShape)
                        }
                    )
            ) {
                // Inner Box with 8dp horizontal padding
                Box(
                    modifier = Modifier
                        // .fillMaxWidth() removed
                        .fillMaxHeight()
                        .padding(horizontal = PopSpacing.Spacing8) // 8dp horizontal padding
                        .onSizeChanged { size ->
                            // Available width is the size width (already accounts for padding)
                            containerWidthPx = size.width.toFloat()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    // Calculate if text overflows - only show marquee when NOT focused and text exceeds container
                    val textOverflows = value.isNotEmpty() &&
                            containerWidthPx > 0f &&
                            textWidth > 0f &&
                            textWidth > containerWidthPx &&
                            !isFocused

                    // Box with right gradient fade when text overflows and not focused
                    Box(
                        modifier = Modifier
                            // .fillMaxWidth() removed
                            .then(
                                if (textOverflows) {
                                    Modifier.horizontalGradientFade(
                                        gradientWidth = 72.dp, // Standard gradient width from PopMarqueeText
                                        shouldShowLeftGradient = false,
                                        shouldShowRightGradient = true,
                                        leftGradientAlpha = 1f
                                    )
                                } else {
                                    Modifier
                                }
                            )
                    ) {
                        // Input field - always present but invisible when inactive to allow clicks
                        BasicTextField(
                            value = value,
                            onValueChange = onValueChange,
                            modifier = Modifier
                                // .fillMaxWidth() removed
                                .alpha(if (isActive || isFilled) 1f else 0f) // Invisible when inactive but keeps in composition for clicks
                                .onFocusChanged { isFocused = it.isFocused },
                            textStyle = textStyle.copy(
                                textAlign = TextAlign.Center,
                                color = textColor
                            ),
                            enabled = enabled,
                            readOnly = readOnly,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = keyboardType,
                                imeAction = imeAction
                            ),
                            keyboardActions = keyboardActions,
                            visualTransformation = visualTransformation,
                            singleLine = true,
                            cursorBrush = cursorBrush
                        )

                        // Placeholder text - visible when empty (both active and inactive states)
                        if (value.isEmpty() && placeholder != null) {
                            Text(
                                text = placeholder,
                                style = PopTypography.figtreeStyles.paragraphXSmall.copy(
                                    color = placeholderColor,
                                    textAlign = TextAlign.Center
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    // Title text (hint) - shown when inactive and empty, only if placeholder is not provided
                    // When placeholder is provided, it takes precedence over title
                    if (!isActive && !isFilled && title != null && placeholder == null) {
                        Text(
                            text = title,
                            style = PopTypography.figtreeStyles.labelXSmall.copy(
                                color = titleColor,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }

        // Status message (Info/Error/Success) - center aligned for Small type
        // Small variant has special behavior: clicking status message clears focus
        if (status != null && statusMessage != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = PopSpacing.Spacing8)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = {
                            // Clear focus when clicking on status message
                            focusManager.clearFocus()
                        }
                    )
            ) {
                StatusMessage(
                    status = status,
                    message = statusMessage,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
            }
        }
    }
}

