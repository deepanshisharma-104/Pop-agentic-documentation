package com.pop.components.compose_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import app.rive.runtime.kotlin.RiveAnimationView
import app.rive.runtime.kotlin.controllers.RiveFileController
import app.rive.runtime.kotlin.core.Fit
import app.rive.runtime.kotlin.core.Loop
import app.rive.runtime.kotlin.core.PlayableInstance
import com.pop.components.ds_components.AvatarSize
import com.pop.components.ds_components.AvatarType
import com.pop.components.ds_components.KeyPadKey
import com.pop.components.ds_components.PopAvatar
import com.pop.components.ds_components.PopInputFieldV2
import com.pop.components.ds_components.PopKeyPad
import com.pop.components.ds_components.PopShimmerBox
import com.pop.components.ds_components.handleKeyPadInput
import com.pop.components.models.InputFieldStatus
import com.pop.components.models.SmallInputFieldConfig
import com.pop.components.models.UnderlineNakedLargeConfig
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopIcons
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import com.pop.components.utils.toIndianFormat
import com.pop.compose_components.R
import kotlinx.coroutines.delay
import kotlin.math.max
import com.pop.components.theme.Icons as PopIconsList

private const val DELETE_KEY = "DELETE"

/**
 * PopEnterAmountHeader - A reusable header component for amount entry screens (UPI and RCBP).
 * Includes user info, large amount input field, and action buttons.
 *
 * @param name The name of the beneficiary/user
 * @param subtitle The subtitle (e.g., VPA or Account No)
 * @param amount The current amount value
 * @param onAmountChange Callback when the amount changes
 * @param onBackClick Callback when the back button is clicked
 * @param onViewHistoryClick Callback when the "View history" button is clicked
 * @param onAddNoteClick Callback when the "Add note" button is clicked
 * @param avatarType The type of avatar to display (People or Brand)
 * @param isVerified Whether to show the verified badge next to the name
 * @param showGlow Whether to show the circular background glow (as seen in UPI design)
 * @param infoText Optional text to show above the amount (e.g., "Your bill ₹399")
 * @param subtitleIcon Optional icon resource ID to show next to the subtitle
 * @param isAmountEditable Whether the amount input field is editable (default: true)
 * @param modifier Modifier for the container
 */
@Composable
fun PopEnterAmountHeader(
    modifier: Modifier = Modifier,
    name: String,
    subtitle: String,
    amount: String,
    onAmountChange: (String) -> Unit,
    onKeyPress: (String) -> Unit = {},
    onBackClick: () -> Unit,
    onViewHistoryClick: (() -> Unit)? = null,
    onAddNoteClick: (() -> Unit)? = null,
    notes: String = "",
    onNotesChange: ((String) -> Unit)? = null,
    isNoteFocused: Boolean = false,
    onNoteFocusChange: ((Boolean) -> Unit)? = null,
    onImeAction: (() -> Unit)? = null,
    avatarType: AvatarType = AvatarType.People(background = GradientPreset.getAvatarGradientForName(
        name
    ),name = name),
    isVerified: Boolean = false,
    verifiedIconRes: Int? = null, // Custom verified icon resource (e.g., SEBI icon). If null, uses default shield icon
    isNameLoading: Boolean = false,
    isSubtitleLoading: Boolean = false,
    isAmountLoading: Boolean = false,
    isInfoTextLoading: Boolean = false,
    infoText: AnnotatedString? = null,
    subtitleIcon: Int? = null,
    isAmountEditable: Boolean = true,
    isNotesEditable: Boolean = true,
    showKeypad: Boolean = false,
    allowDecimal: Boolean = true,
    onKeypadDismiss: (() -> Unit)? = null,
    onInputFieldClick: (() -> Unit)? = null,
    billDueText: String? = null,
    onBillDueTextClick: (() -> Unit)? = null,
    billDueTextColor: Color? = null,
    showBillDueInfoIcon: Boolean = false, // If true, shows info icon on left instead of dropdown on right
    useInfoIconForBillDueRight: Boolean = false, // If true, shows info icon on right instead of dropdown
    amountStatus: InputFieldStatus? = null,
    amountStatusMessage: String? = null,
    noteStatus: InputFieldStatus? = null,
    noteStatusMessage: String? = null,
    content: @Composable ColumnScope.() -> Unit = {},
    footer: @Composable BoxScope.() -> Unit = {},
    showConfettiAnimation: Boolean = false,
    confettiResourceId: Int? = null,
    onConfettiAnimationComplete: (() -> Unit)? = null,
    topSpacingBeforeAmountSection: Dp = PopSpacing.Spacing6,
    showBuiltInKeypad: Boolean = showKeypad, // Control built-in keypad separately from focus (defaults to showKeypad for backward compatibility)
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceColor.Primary)
            .navigationBarsPadding()
    ) {
        // 0. Background Image
        Image(
            painter = painterResource(id = R.drawable.ic_enter_amount_bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            // Scrollable Content area with dismissible overlay when keypad is shown
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    // Header Info Section
                    HeaderInfoSection(
                        name = name,
                        subtitle = subtitle,
                        amount = amount,
                        onAmountChange = onAmountChange,
                        onViewHistoryClick = onViewHistoryClick,
                        onImeAction = onImeAction,
                        onAddNoteClick = onAddNoteClick,
                        notes = notes,
                        onNotesChange = onNotesChange,
                        isNoteFocused = isNoteFocused,
                        onNoteFocusChange = onNoteFocusChange,
                        avatarType = avatarType,
                        isVerified = isVerified,
                        verifiedIconRes = verifiedIconRes,
                        isNameLoading = isNameLoading,
                        isSubtitleLoading = isSubtitleLoading,
                        isAmountLoading = isAmountLoading,
                        isInfoTextLoading = isInfoTextLoading,
                        infoText = infoText,
                        subtitleIcon = subtitleIcon,
                        isAmountEditable = isAmountEditable,
                        isNotesEditable = isNotesEditable,
                        amountStatus = amountStatus,
                        amountStatusMessage = amountStatusMessage,
                        noteStatus = noteStatus,
                        noteStatusMessage = noteStatusMessage,
                        onInputFieldClick = onInputFieldClick,
                        billDueText = billDueText,
                        billDueTextColor = billDueTextColor,
                        onBillDueTextClick = onBillDueTextClick,
                        showConfettiAnimation = showConfettiAnimation,
                        confettiResourceId = confettiResourceId,
                        onConfettiAnimationComplete = onConfettiAnimationComplete,
                        showBillDueInfoIcon = showBillDueInfoIcon,
                        useInfoIconForBillDueRight = useInfoIconForBillDueRight,
                        topSpacingBeforeAmountSection = topSpacingBeforeAmountSection,
                        showKeypad = showKeypad,
                        onFocusReceived = {
                            onInputFieldClick?.invoke()
                        }
                    )

                    // Custom Content
                    content()
                }

                // Dismissible overlay when keypad is shown
                if (showBuiltInKeypad && isAmountEditable) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(
                                onClick = { onKeypadDismiss?.invoke() },
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            )
                    )
                }
            }

            // Sticky Footer area
            Box(
                modifier = Modifier.fillMaxWidth(),
                content = footer
            )

            // Custom Keypad (shown when showBuiltInKeypad = true and isAmountEditable = true)
            if (showBuiltInKeypad && isAmountEditable) {
                // Use rememberUpdatedState to ensure lambda always reads current amount value
                val currentAmount by rememberUpdatedState(amount)
                Spacer(modifier = Modifier.height(PopSpacing.Spacing12))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceColor.Primary)
                        .navigationBarsPadding()
                        .padding(horizontal = PopSpacing.Spacing12)
                ) {
                    PopKeyPad(
                        modifier = Modifier.fillMaxWidth(),
                        onKeyPress = { key ->
                            val newAmount = handleKeyPadInput(
                                key = key,
                                currentValue = currentAmount,
                                allowDecimal = allowDecimal,
                                maxLength = 999999999
                            )
                            onAmountChange(newAmount)

                            val keyString = when (key) {
                                is KeyPadKey.Number -> key.value
                                is KeyPadKey.Decimal -> "."
                                is KeyPadKey.Backspace -> DELETE_KEY
                            }
                            onKeyPress(keyString)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderInfoSection(
    name: String,
    subtitle: String,
    amount: String,
    onAmountChange: (String) -> Unit,
    onViewHistoryClick: (() -> Unit)? = null,
    onAddNoteClick: (() -> Unit)? = null,
    notes: String = "",
    onNotesChange: ((String) -> Unit)? = null,
    isNoteFocused: Boolean = false,
    onNoteFocusChange: ((Boolean) -> Unit)? = null,
    onImeAction: (() -> Unit)? = null,
    avatarType: AvatarType,
    isVerified: Boolean,
    verifiedIconRes: Int? = null,
    isNameLoading: Boolean = false,
    isSubtitleLoading: Boolean = false,
    isAmountLoading: Boolean = false,
    isInfoTextLoading: Boolean = false,
    infoText: AnnotatedString?,
    subtitleIcon: Int?,
    isAmountEditable: Boolean,
    isNotesEditable: Boolean = true,
    amountStatus: InputFieldStatus? = null,
    amountStatusMessage: String? = null,
    noteStatus: InputFieldStatus? = null,
    noteStatusMessage: String? = null,
    onInputFieldClick: (() -> Unit)? = null,
    billDueText: String? = null,
    billDueTextColor: androidx.compose.ui.graphics.Color? = null,
    onBillDueTextClick: (() -> Unit)? = null,
    showConfettiAnimation: Boolean = false,
    confettiResourceId: Int? = null,
    onConfettiAnimationComplete: (() -> Unit)? = null,
    showBillDueInfoIcon: Boolean = false,
    useInfoIconForBillDueRight: Boolean = false,
    topSpacingBeforeAmountSection: Dp = PopSpacing.Spacing6,
    showKeypad: Boolean = false,
    onFocusReceived: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val view = LocalView.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = PopSpacing.Spacing16)
    ) {
        // 2. Top Bar
        // Hidden View history row but maintained spacing
        Spacer(modifier = Modifier.height(PopSpacing.Spacing56))
        Spacer(modifier = Modifier.height(PopSpacing.Spacing16))

        // 3. Avatar Section
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            PopAvatar(
                type = avatarType,
                size = AvatarSize.XLarge
            )

        }

        Spacer(modifier = Modifier.height(PopSpacing.Spacing16))

        // 4. User Info (Name & Subtitle)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Show shimmer when loading, otherwise show name with optional verified badge
            if (isNameLoading) {
                PopShimmerBox(
                    modifier = Modifier
                        .width(150.dp)
                        .height(28.dp),
                    shape = RoundedCornerShape(14.dp)
                )
            } else {
                // Use inline content to embed verified badge within text
                // This ensures the badge flows with the text when it wraps to multiple lines
                val verifiedIconId = "verified_icon"
                val nameWithBadge = buildAnnotatedString {
                    append(name)
                    if (isVerified) {
                        append(" ") // Space before icon
                        appendInlineContent(verifiedIconId, "[verified]")
                    }
                }

                val inlineContent = mapOf(
                    verifiedIconId to InlineTextContent(
                        placeholder = Placeholder(
                            width = PopIcons.sizeSmall.value.sp,
                            height = PopIcons.sizeSmall.value.sp,
                            placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                        )
                    ) {
                        Image(
                            painter = painterResource(id = verifiedIconRes ?: PopIconsList.VpaVerified.filledRes),
                            contentDescription = "Vpa Verified",
                            modifier = Modifier.size(PopIcons.sizeSmall)
                        )
                    }
                )

                Text(
                    text = nameWithBadge,
                    style = PopTypography.headingLarge.copy(color = PopColor.WhiteBlack.White).merge(
                        lineBreak = LineBreak.Heading.copy(
                        strategy = LineBreak.Strategy.Balanced
                    )),
                    textAlign = TextAlign.Center,
                    inlineContent = inlineContent,
                    modifier = Modifier.padding(horizontal = PopSpacing.Spacing16)
                )
            }

            Spacer(modifier = Modifier.height(PopSpacing.Spacing4))

            // Show shimmer when loading, otherwise show subtitle
            if (isSubtitleLoading) {
                PopShimmerBox(
                    modifier = Modifier
                        .width(120.dp)
                        .height(16.dp),
                    shape = RoundedCornerShape(8.dp)
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (subtitleIcon != null) {
                        Icon(
                            painter = painterResource(id = subtitleIcon),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(PopSpacing.Spacing4))
                    }
                    Text(
                        text = subtitle,
                        style = PopTypography.labelSmall.copy(color = TextColor.Secondary)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(topSpacingBeforeAmountSection))

        // Wrap infoText, amount, and billDueText in Box for confetti overlay coverage
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 4a. Info Text (e.g., "Your bill ₹399") with shimmer when loading
                if (isInfoTextLoading) {
                    PopShimmerBox(
                        modifier = Modifier
                            .width(150.dp)
                            .height(16.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(8.dp)
                    )
                    Spacer(modifier = Modifier.height(PopSpacing.Spacing8))
                } else if (infoText != null) {
                    Text(
                        text = infoText,
                        style = PopTypography.labelSmall.copy(color = TextColor.Tertiary),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(PopSpacing.Spacing8))
                }

                // 5. Amount Input Field with shimmer when loading
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isAmountLoading) {
                        PopShimmerBox(
                            modifier = Modifier
                                .width(200.dp)
                                .height(48.dp),
                            shape = RoundedCornerShape(8.dp)
                        )
                    } else {
                        // Display amount: formatted with Indian commas for display
                        val displayAmount = remember(amount) {
                            if (amount.isEmpty() || amount == "0") {
                                amount
                            } else {
                                try {
                                    // Handle decimal amounts
                                    if (amount.contains('.')) {
                                        val parts = amount.split('.')
                                        val integerPart = parts[0].toLongOrNull()?.toIndianFormat() ?: parts[0]
                                        val decimalPart = parts.getOrNull(1) ?: ""
                                        if (decimalPart.isNotEmpty()) {
                                            "$integerPart.$decimalPart"
                                        } else {
                                            "$integerPart."
                                        }
                                    } else {
                                        // Format as integer
                                        amount.toLongOrNull()?.toIndianFormat() ?: amount
                                    }
                                } catch (e: Exception) {
                                    amount
                                }
                            }
                        }
                        
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    enabled = isAmountEditable || onInputFieldClick != null,
                                    onClick = {
                                        ViewCompat.getWindowInsetsController(view)?.hide(WindowInsetsCompat.Type.ime())
                                        focusManager.clearFocus()
                                        onInputFieldClick?.invoke()
                                    },
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            PopInputFieldV2(
                                config = UnderlineNakedLargeConfig(
                                    value = displayAmount,
                                    onValueChange = { newValue ->
                                        // Strip commas and any formatting characters before updating state
                                        val cleanValue = newValue.replace(",", "")
                                        onAmountChange(cleanValue)
                                    },
                                    imeAction = ImeAction.Done,
                                    onImeAction = {
                                        onImeAction?.invoke()
                                    },
                                    keyboardType = KeyboardType.Number,
                                    useCustomKeypad = true,
                                    shouldRequestFocus = showKeypad,
                                    // Keep enabled=true always to maintain primary text color (not grayed out)
                                    enabled = true,
                                    // Use editable to control actual editability
                                    editable = isAmountEditable,
                                    readOnly = !isAmountEditable,
                                    status = amountStatus,
                                    statusMessage = amountStatusMessage,
                                    onFocusReceived = {
                                        onFocusReceived.invoke()
                                    }
                                ),
                                modifier = Modifier.width(IntrinsicSize.Min)
                            )
                            // Transparent overlay to intercept taps when notes are focused.
                            // Without this, taps on the BasicTextField text bypass the parent
                            // Box's clickable, causing the system keyboard to flash briefly
                            // as the amount field gains focus before keyboard can be hidden.
                            if (isNoteFocused) {
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() },
                                            onClick = {
                                                ViewCompat.getWindowInsetsController(view)?.hide(WindowInsetsCompat.Type.ime())
                                                focusManager.clearFocus()
                                                onInputFieldClick?.invoke()
                                            }
                                        )
                                )
                            }
                        }
                    }

                    // Bill Due Warning (only show if billDueText is not null or empty)
                    if (!billDueText.isNullOrEmpty()) {
                        Spacer(modifier = Modifier.height(PopSpacing.Spacing2))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    enabled = onBillDueTextClick != null,
                                    onClick = { onBillDueTextClick?.invoke() },
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Show info icon on left if showBillDueInfoIcon is true
                            if (showBillDueInfoIcon) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_info_square),
                                    contentDescription = null,
                                    tint = billDueTextColor ?: TextColor.Warning,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(PopSpacing.Spacing6))
                            }

                            Text(
                                text = billDueText ?: "",
                                style = PopTypography.labelXSmall.copy(
                                    color = billDueTextColor ?: TextColor.Warning
                                )
                            )

                            // Show info icon or dropdown on right (when showBillDueInfoIcon is false)
                            if (!showBillDueInfoIcon) {
                                Spacer(modifier = Modifier.width(PopSpacing.Spacing6))
                                Icon(
                                    painter = painterResource(
                                        id = if (useInfoIconForBillDueRight) R.drawable.ic_info_square else R.drawable.ic_drop_down
                                    ),
                                    contentDescription = null,
                                    tint = billDueTextColor ?: TextColor.Warning,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Confetti animation overlay - covers infoText, amount, and billDueText sections
            // Only show when toggle is ON; hidden when animation completes or toggle is OFF
            if (confettiResourceId != null && showConfettiAnimation) {
                val onComplete by rememberUpdatedState(onConfettiAnimationComplete)
                var animationStopped by remember { mutableStateOf(false) }
                val showTime = remember(showConfettiAnimation) {
                    if (showConfettiAnimation) System.currentTimeMillis() else 0L
                }

                // Reset when confetti is shown again
                LaunchedEffect(showConfettiAnimation) {
                    if (showConfettiAnimation) animationStopped = false
                }

                // Minimum display time so confetti is visible even if notifyStop fires early (e.g. load error)
                LaunchedEffect(animationStopped, showTime) {
                    if (animationStopped && showTime > 0L) {
                        val elapsed = System.currentTimeMillis() - showTime
                        delay(max(0, 2500 - elapsed))
                        onComplete?.invoke()
                    }
                }

                Box(
                    modifier = Modifier
                        .matchParentSize() // Match parent Box size without affecting layout
                        .zIndex(1f)
                        .pointerInteropFilter { _ -> false }, // Pass through touches so text/amount remain clickable
                    contentAlignment = Alignment.Center
                ) {
                    var riveView by remember { mutableStateOf<RiveAnimationView?>(null) }
                    val setAnimationStopped by rememberUpdatedState({ animationStopped = true })

                    // Listener to detect when animation completes and when it tries to loop
                    val listener = object : RiveFileController.Listener {
                        override fun notifyStop(animation: PlayableInstance) {
                            setAnimationStopped()
                        }

                        override fun notifyLoop(animation: PlayableInstance) {
                            // If animation tries to loop (shouldn't happen with ONESHOT, but safety check)
                            // Stop it immediately to prevent looping
                            riveView?.stop()
                        }

                        override fun notifyPlay(animation: PlayableInstance) {}
                        override fun notifyPause(animation: PlayableInstance) {}
                        override fun notifyStateChanged(
                            stateMachineName: String,
                            stateName: String
                        ) {
                        }
                    }

                    // Trigger animation when showConfettiAnimation becomes true
                    LaunchedEffect(showConfettiAnimation) {
                        riveView?.let { view ->
                            if (showConfettiAnimation) {
                                // When toggle is ON, reset and play animation once
                                view.reset()
                                view.play()
                            }
                        }
                    }

                    AndroidView(
                        factory = { context ->
                            RiveAnimationView(context).apply {
                                setRiveResource(
                                    resId = confettiResourceId,
                                    autoplay = false,
                                    fit = Fit.NONE,
                                    loop = Loop.ONESHOT // Play animation only once, no loop
                                )
                                registerListener(listener)
                                riveView = this
                                // Play animation when shown
                                play()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp), // Ensure Rive has visible area to draw confetti
                        update = { view ->
                            riveView = view
                            // Ensure listener is registered
                            view.registerListener(listener)
                        }
                    )
                }
            }
        }


        // Reduced spacing between amount input and content (chips) - was Spacing24, now Spacing12
        Spacer(modifier = Modifier.height(PopSpacing.Spacing12))

        // 6. Action Button (Add Note) or Note Input
        if (onAddNoteClick != null || onNotesChange != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                    // Note Input Field
                    if (onNotesChange != null) {
                        val focusRequester = remember { FocusRequester() }

                        LaunchedEffect(isNoteFocused) {
                            if (isNoteFocused) {
                                focusRequester.requestFocus()
                            }
                        }

                        // Use fillMaxWidth Column so status message can expand
                        PopInputFieldV2(
                            config = SmallInputFieldConfig(
                                value = notes,
                                onValueChange = onNotesChange,
                                title = "Note",
                                placeholder = "Add a note",
                                imeAction = ImeAction.Done,
                                onImeAction = {
                                    onImeAction?.invoke()
                                },
                                keyboardType = KeyboardType.Text,
                                status = noteStatus,
                                statusMessage = noteStatusMessage,
                                enabled = isNotesEditable,
                                readOnly = !isNotesEditable
                            ),
                            modifier = Modifier
                                .defaultMinSize(minWidth = 100.dp) // Minimum width to handle "Add a note" hint text
                                .focusRequester(focusRequester)
                                .onFocusChanged { focusState ->
                                    onNoteFocusChange?.invoke(focusState.isFocused)
                                }
                        )
                    }
            }
        }

        Spacer(modifier = Modifier.height(PopSpacing.Spacing32))
    }
}
