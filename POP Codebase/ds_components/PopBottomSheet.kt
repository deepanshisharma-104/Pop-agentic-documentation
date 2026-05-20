package com.pop.components.ds_components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.compose.ui.zIndex
import androidx.core.view.doOnPreDraw
import com.pop.components.animation.utils.rootContentView
import com.pop.components.animation.utils.runResetScaleAnimation
import com.pop.components.animation.utils.runScaleDownAnimation
import com.pop.components.models.BottomSheetBackgroundType
import com.pop.components.models.BottomSheetGradientShape
import com.pop.components.models.PopBottomSheetConfig
import com.pop.components.models.VisualElement
import com.pop.components.theme.BorderColor
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.IconStyle
import com.pop.components.theme.Icons
import com.pop.components.theme.PopStroke
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import com.pop.components.utils.popBackground
import kotlinx.coroutines.delay

// ─── Constants ───────────────────────────────────────────────────────────────

object BottomSheetConstants {
    /** Sheet slides in from this offset below the screen on enter. */
    const val ENTER_SLIDE_OFFSET_DP = 100

    /** Sheet slides down to this offset on exit. */
    const val EXIT_SLIDE_OFFSET_DP = 50

    /** Bottom margin keeps a sliver of the background screen visible below the sheet. */
    const val BOTTOM_MARGIN_DP = 24

    /** Gap between the floating close button and the top edge of the sheet surface. */
    const val CLOSE_BUTTON_SPACING_DP = 28

    /** Diameter of the floating close button. */
    const val CLOSE_BUTTON_SIZE_DP = 40

    /** Window scale at the start of the enter animation (no transformation). */
    const val ENTER_SCALE_START = 1.0f

    /** Sheet content starts slightly zoomed in, then settles to VISIBLE_SCALE on enter. */
    const val SHEET_ENTER_SCALE_START = 1.1f

    /** The resting scale of the sheet content when fully open. */
    const val VISIBLE_SCALE = 0.99f

    /** Neutral scale — no transformation applied. */
    const val NORMAL_SCALE = 1f
}

// ─── Sheet lifecycle state machine ───────────────────────────────────────────

/**
 * Internal state machine that drives all enter/exit animations and guards against
 * race conditions between the caller's showSheet boolean and the animations.
 */
private sealed class BottomSheetState {
    /** Sheet is fully hidden and no animation is running. */
    object Hidden : BottomSheetState()

    /** Sheet is animating into view. */
    object Entering : BottomSheetState()

    /** Sheet is fully visible and stable. */
    object Visible : BottomSheetState()

    /**
     * Sheet is animating out of view.
     * @param dismissCallback invoked after the exit animation completes.
     * @param isDragDismiss true when the user initiated dismiss via a drag gesture;
     *   skips the timed exit delay since Material3 already animated the container away.
     */
    data class Exiting(
        val dismissCallback: () -> Unit,
        val isDragDismiss: Boolean = false,
    ) : BottomSheetState()
}

// ─── Public API ──────────────────────────────────────────────────────────────

/**
 * **⚠️ DEPRECATED** — use the [config]-based overload instead.
 *
 * Migration guide:
 * ```
 * // Before (deprecated)
 * PopBottomSheet(
 *     showSheet = showSheet,
 *     onDismissRequest = { showSheet = false },
 *     backgroundType = BottomSheetBackgroundType.Gradient(shape = BottomSheetGradientShape.Rounded),
 *     cornerRadius = 16.dp,
 *     showFloatingCloseButton = true
 * ) { … }
 *
 * // After (recommended)
 * val config = PopBottomSheetConfig(
 *     backgroundType = BottomSheetBackgroundType.Gradient(shape = BottomSheetGradientShape.Rounded),
 *     cornerRadius = 16.dp,
 *     showFloatingCloseButton = true
 * )
 * PopBottomSheet(showSheet, onDismissRequest = { showSheet = false }, config) { … }
 * ```
 */
@Deprecated(
    message = "Use the config-based overload instead. This provides better performance and type safety.",
    replaceWith = ReplaceWith(
        expression = "PopBottomSheet(showSheet, onDismissRequest, PopBottomSheetConfig(cornerRadius, isDraggable, isCancellable, showFloatingCloseButton, backgroundType), onCloseButtonClick, content)",
        imports = ["com.pop.components.models.PopBottomSheetConfig"]
    )
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopBottomSheet(
    showSheet: Boolean = false,
    onDismissRequest: () -> Unit,
    cornerRadius: Dp = 16.dp,
    isDraggable: Boolean = true,
    isCancellable: Boolean = true,
    showFloatingCloseButton: Boolean = false,
    onCloseButtonClick: (() -> Unit)? = null,
    backgroundType: BottomSheetBackgroundType = BottomSheetBackgroundType.Transparent,
    useGradientBackground: Boolean = false,
    content: @Composable () -> Unit,
) {
    val effectiveBackgroundType = if (backgroundType != BottomSheetBackgroundType.Transparent) {
        backgroundType
    } else if (useGradientBackground) {
        BottomSheetBackgroundType.Gradient(shape = BottomSheetGradientShape.Rounded)
    } else {
        BottomSheetBackgroundType.Transparent
    }

    PopBottomSheetInternal(
        showSheet = showSheet,
        onDismissRequest = onDismissRequest,
        config = PopBottomSheetConfig(
            cornerRadius = cornerRadius,
            isDraggable = isDraggable,
            isCancellable = isCancellable,
            showFloatingCloseButton = showFloatingCloseButton,
            backgroundType = effectiveBackgroundType
        ),
        onCloseButtonClick = onCloseButtonClick,
        content = content
    )
}

/**
 * Config-based PopBottomSheet — preferred entry point.
 *
 * Pass a [PopBottomSheetConfig] to control corner radius, drag, background type,
 * animation durations, scrim, and optional top visual element.
 *
 * @param showSheet Controls visibility. Set to `true` to open, `false` to close.
 *   The sheet always plays its full exit animation before [onDismissRequest] fires.
 * @param onDismissRequest Called after the exit animation completes. Use this to set
 *   your `showSheet` state back to `false`.
 * @param config Immutable configuration for appearance and behaviour.
 * @param onCloseButtonClick Optional override for the floating close button tap.
 *   Defaults to [onDismissRequest] when null.
 * @param content The composable content rendered inside the sheet surface.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopBottomSheet(
    showSheet: Boolean = false,
    onDismissRequest: () -> Unit,
    config: PopBottomSheetConfig,
    onCloseButtonClick: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    PopBottomSheetInternal(
        showSheet = showSheet,
        onDismissRequest = onDismissRequest,
        config = config,
        onCloseButtonClick = onCloseButtonClick,
        content = content
    )
}

// ─── Shape helper ────────────────────────────────────────────────────────────

/** Resolves the Compose [Shape] for the sheet surface based on [backgroundType]. */
private fun createShapeFromBackgroundType(
    backgroundType: BottomSheetBackgroundType,
    cornerRadius: Dp,
): Shape = when (backgroundType) {
    is BottomSheetBackgroundType.Gradient -> when (backgroundType.shape) {
        BottomSheetGradientShape.Rounded -> RoundedCornerShape(cornerRadius)
        BottomSheetGradientShape.Squircle -> SquircleShape(cornerRadius = cornerRadius, smoothing = 1f)
    }
    is BottomSheetBackgroundType.Transparent -> RoundedCornerShape(cornerRadius)
}

// ─── Internal implementation ─────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PopBottomSheetInternal(
    showSheet: Boolean,
    onDismissRequest: () -> Unit,
    config: PopBottomSheetConfig,
    onCloseButtonClick: (() -> Unit)?,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }

    val animationConfig = config.animationConfig
    val scrimConfig = config.scrimConfig
    val animationDuration = animationConfig.durationMs
    val animationDurationLong = animationConfig.durationMs.toLong()

    // ── Internal state machine ────────────────────────────────────────────────
    var sheetState by remember { mutableStateOf<BottomSheetState>(BottomSheetState.Hidden) }

    // Window scale: the activity's root view shrinks slightly while the sheet is open,
    // creating depth. Tracked as plain Float so the View animator can update it.
    var scale by remember { mutableFloatStateOf(BottomSheetConstants.NORMAL_SCALE) }

    // Sheet content scale: initialised at SHEET_ENTER_SCALE_START so the very first
    // rendered frame already has the correct starting value (LaunchedEffect fires after composition).
    val sheetScaleAnimatable = remember { Animatable(BottomSheetConstants.SHEET_ENTER_SCALE_START) }
    val sheetScale by remember { derivedStateOf { sheetScaleAnimatable.value } }

    // Slide offset drives the vertical enter/exit translation of the sheet content.
    // Stored in pixels internally, converted to Dp for Modifier.offset.
    val density = LocalDensity.current
    val slideOffsetAnimatable = remember(density) {
        Animatable(with(density) { BottomSheetConstants.EXIT_SLIDE_OFFSET_DP.dp.toPx() })
    }
    val slideOffset by remember {
        derivedStateOf { with(density) { slideOffsetAnimatable.value.toDp() } }
    }

    // Set to true when the user starts a drag-dismiss gesture so the Exiting state
    // knows to skip the timed delay (Material already animated the container away).
    var isDragDismissing by remember { mutableStateOf(false) }

    // After a drag-dismiss, Material3 internally sets modalSheetState.currentValue = Hidden.
    // We must reset it to Expanded before the next open so Material never plays its own
    // slide-up animation (which would conflict with the custom enter animation).
    //
    // This flag keeps ModalBottomSheet in the composition just long enough to call show().
    // It is set in the SAME Compose state batch as sheetState = Hidden, so both land in a
    // single recomposition — the dialog window is never destroyed and recreated, avoiding
    // a visible blink on the screen behind the sheet.
    var needsModalReset by remember { mutableStateOf(false) }

    // modalSheetState is the Material3 sheet state. We intentionally never call hide() on it:
    // keeping it Expanded at all times means show() is always a no-op on reopen, ensuring
    // the custom enter animation is the only transition the user ever sees.
    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { targetValue ->
            if (config.isDraggable) {
                if (targetValue == SheetValue.Hidden) {
                    isDragDismissing = true
                }
                true
            } else {
                false
            }
        }
    )

    // ── Show / hide trigger ───────────────────────────────────────────────────
    // Reacts to external showSheet changes and the isDraggable config flag.
    LaunchedEffect(showSheet, config.isDraggable) {
        when {
            showSheet && sheetState is BottomSheetState.Hidden -> {
                sheetState = BottomSheetState.Entering
                // On first open, modalSheetState.currentValue = Hidden so show() animates
                // the transparent container (content is alpha = 0f, so it's invisible).
                // On subsequent opens, currentValue = Expanded and show() is a no-op.
                modalSheetState.show()
            }

            !showSheet && sheetState !is BottomSheetState.Hidden -> {
                if (sheetState !is BottomSheetState.Exiting) {
                    animationConfig.onExitAnimationStart?.invoke()
                    sheetState = BottomSheetState.Exiting(onDismissRequest)
                }
            }
        }

        // Re-show when isDraggable is toggled while the sheet is open and somehow not Expanded.
        if (showSheet && !config.isDraggable && modalSheetState.currentValue != SheetValue.Expanded) {
            modalSheetState.show()
        }
    }

    // ── Drag-dismiss modal state reset ────────────────────────────────────────
    // After the exit animation fades everything to invisible, silently reset
    // modalSheetState back to Expanded so the next open plays only the custom animation.
    // The delay ensures the scrim and content have fully faded before the dialog window
    // is removed — preventing a visible activity repaint (flash).
    LaunchedEffect(sheetState) {
        if (sheetState is BottomSheetState.Hidden && needsModalReset) {
            try {
                delay(animationDurationLong)
                modalSheetState.show()
            } finally {
                needsModalReset = false
            }
        }
    }

    // ── Window scale + rounded corners animation ──────────────────────────────
    // On enter: the activity root view scales down slightly (depth illusion) and gets
    // rounded corners. On exit: both are restored. On dispose: always cleaned up.
    LaunchedEffect(sheetState, animationConfig.enableScale, density) {
        when (val currentState = sheetState) {
            is BottomSheetState.Entering -> {
                val rootView = activity?.rootContentView()
                rootView?.let {
                    applyTopRoundedCorners(it, with(density) { config.cornerRadius.toPx() })
                }

                if (animationConfig.enableScale) {
                    val rootViewForScale = activity?.rootContentView() ?: return@LaunchedEffect
                    scale = BottomSheetConstants.ENTER_SCALE_START
                    val onScaleProgress: ((Float) -> Unit) = { scale = it }

                    if (rootViewForScale.width == 0 || rootViewForScale.height == 0) {
                        rootViewForScale.doOnPreDraw {
                            rootViewForScale.runScaleDownAnimation(
                                targetScale = BottomSheetConstants.VISIBLE_SCALE,
                                duration = animationDurationLong,
                                onScaleProgress = onScaleProgress,
                                startScale = BottomSheetConstants.ENTER_SCALE_START
                            )
                        }
                    } else {
                        rootViewForScale.runScaleDownAnimation(
                            targetScale = BottomSheetConstants.VISIBLE_SCALE,
                            duration = animationDurationLong,
                            onScaleProgress = onScaleProgress,
                            startScale = BottomSheetConstants.ENTER_SCALE_START
                        )
                    }
                } else {
                    scale = BottomSheetConstants.NORMAL_SCALE
                }

                delay(animationDurationLong)
                if (sheetState is BottomSheetState.Entering) {
                    sheetState = BottomSheetState.Visible
                    animationConfig.onEnterAnimationComplete?.invoke()
                }
            }

            is BottomSheetState.Exiting -> {
                val exitDuration = if (currentState.isDragDismiss) 0L else animationDurationLong

                if (animationConfig.enableScale) {
                    val rootView = activity?.rootContentView() ?: return@LaunchedEffect

                    if (currentState.isDragDismiss ||
                        (rootView.scaleX == BottomSheetConstants.NORMAL_SCALE && scale == BottomSheetConstants.NORMAL_SCALE)
                    ) {
                        rootView.scaleX = BottomSheetConstants.NORMAL_SCALE
                        rootView.scaleY = BottomSheetConstants.NORMAL_SCALE
                        scale = BottomSheetConstants.NORMAL_SCALE
                        removeRoundedCorners(rootView)
                    } else {
                        val onScaleProgress: ((Float) -> Unit) = { scale = it }

                        if (rootView.width == 0 || rootView.height == 0) {
                            rootView.doOnPreDraw {
                                rootView.runResetScaleAnimation(
                                    duration = exitDuration,
                                    onScaleProgress = onScaleProgress,
                                    onScaleComplete = {
                                        scale = BottomSheetConstants.NORMAL_SCALE
                                        removeRoundedCorners(rootView)
                                    }
                                )
                            }
                        } else {
                            rootView.runResetScaleAnimation(
                                duration = exitDuration,
                                onScaleProgress = onScaleProgress,
                                onScaleComplete = {
                                    scale = BottomSheetConstants.NORMAL_SCALE
                                    removeRoundedCorners(rootView)
                                }
                            )
                        }
                    }
                } else {
                    scale = BottomSheetConstants.NORMAL_SCALE
                }

                if (!currentState.isDragDismiss) {
                    delay(animationDurationLong)
                }

                val rootView = activity?.rootContentView()
                rootView?.let { removeRoundedCorners(it) }

                // Set needsModalReset before sheetState = Hidden so both land in the same
                // Compose recomposition, keeping the dialog alive for the reset.
                if (modalSheetState.currentValue == SheetValue.Hidden) {
                    needsModalReset = true
                }
                sheetState = BottomSheetState.Hidden
                animationConfig.onExitAnimationComplete?.invoke()
                currentState.dismissCallback()
            }

            is BottomSheetState.Hidden -> scale = BottomSheetConstants.NORMAL_SCALE

            is BottomSheetState.Visible -> Unit
        }
    }

    // ── Slide animation ───────────────────────────────────────────────────────
    // Enter: snaps to EXIT_SLIDE_OFFSET_DP then animates to 0 (slides up into view).
    // Exit: animates from 0 to EXIT_SLIDE_OFFSET_DP (slides down out of view).
    // Drag dismiss snaps immediately since Material already moved the container away.
    LaunchedEffect(sheetState, animationConfig.enableSlide, density) {
        val offsetPx = with(density) { BottomSheetConstants.EXIT_SLIDE_OFFSET_DP.dp.toPx() }
        when (sheetState) {
            is BottomSheetState.Entering -> {
                if (animationConfig.enableSlide) {
                    slideOffsetAnimatable.snapTo(offsetPx)
                    slideOffsetAnimatable.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing)
                    )
                } else {
                    slideOffsetAnimatable.snapTo(0f)
                }
            }

            is BottomSheetState.Exiting -> {
                if (animationConfig.enableSlide && !(sheetState as BottomSheetState.Exiting).isDragDismiss) {
                    slideOffsetAnimatable.animateTo(
                        targetValue = offsetPx,
                        animationSpec = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing)
                    )
                } else {
                    slideOffsetAnimatable.snapTo(offsetPx)
                }
            }

            is BottomSheetState.Hidden -> slideOffsetAnimatable.snapTo(offsetPx)

            is BottomSheetState.Visible -> Unit
        }
    }

    // ── Fade animation (content alpha) ────────────────────────────────────────
    val alpha by animateFloatAsState(
        targetValue = when (sheetState) {
            is BottomSheetState.Entering, is BottomSheetState.Visible -> 1f
            else -> 0f
        },
        animationSpec = if (animationConfig.enableFade) {
            tween(durationMillis = animationDuration, easing = FastOutSlowInEasing)
        } else {
            tween(durationMillis = 0)
        },
        label = "bottomSheetFade"
    )

    // ── Scrim alpha ───────────────────────────────────────────────────────────
    // Always fades in/out smoothly regardless of dismiss type (including drag).
    // Previously the scrim was instantly removed on drag-dismiss, causing a flash.
    val scrimAlpha by animateFloatAsState(
        targetValue = when (sheetState) {
            is BottomSheetState.Entering, is BottomSheetState.Visible -> 1f
            else -> 0f
        },
        animationSpec = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing),
        label = "bottomSheetScrimFade"
    )

    // ── Sheet content scale animation ─────────────────────────────────────────
    // The sheet content zooms in slightly on enter (1.2 → 0.99) and out on exit.
    // Pre-set to SHEET_ENTER_SCALE_START while Hidden so the first rendered frame
    // of the next open already carries the correct starting scale.
    LaunchedEffect(sheetState, animationConfig.enableScale) {
        when (sheetState) {
            is BottomSheetState.Entering -> {
                if (animationConfig.enableScale) {
                    sheetScaleAnimatable.snapTo(BottomSheetConstants.SHEET_ENTER_SCALE_START)
                    sheetScaleAnimatable.animateTo(
                        targetValue = BottomSheetConstants.VISIBLE_SCALE,
                        animationSpec = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing)
                    )
                } else {
                    sheetScaleAnimatable.snapTo(BottomSheetConstants.NORMAL_SCALE)
                }
            }

            is BottomSheetState.Exiting -> {
                if (animationConfig.enableScale && !(sheetState as BottomSheetState.Exiting).isDragDismiss) {
                    sheetScaleAnimatable.animateTo(
                        targetValue = BottomSheetConstants.SHEET_ENTER_SCALE_START,
                        animationSpec = tween(durationMillis = animationDuration, easing = FastOutSlowInEasing)
                    )
                } else {
                    sheetScaleAnimatable.snapTo(BottomSheetConstants.SHEET_ENTER_SCALE_START)
                }
            }

            is BottomSheetState.Hidden ->
                sheetScaleAnimatable.snapTo(BottomSheetConstants.SHEET_ENTER_SCALE_START)

            is BottomSheetState.Visible -> Unit
        }
    }

    // ── Dismiss handlers ──────────────────────────────────────────────────────
    // Both handlers gate on the current state to prevent double-firing during animation.

    // Triggered when the user taps outside the sheet or presses back.
    val handleDismissRequest = remember(onDismissRequest) {
        {
            if (sheetState !is BottomSheetState.Exiting && sheetState !is BottomSheetState.Hidden) {
                animationConfig.onExitAnimationStart?.invoke()
                sheetState = BottomSheetState.Exiting(onDismissRequest)
            }
        }
    }

    // Triggered by the optional floating close button. Falls back to onDismissRequest
    // when no dedicated onCloseButtonClick callback is provided.
    val handleCloseButtonClick = remember(onCloseButtonClick, onDismissRequest) {
        {
            if (sheetState !is BottomSheetState.Exiting && sheetState !is BottomSheetState.Hidden) {
                animationConfig.onExitAnimationStart?.invoke()
                sheetState = BottomSheetState.Exiting(onCloseButtonClick ?: onDismissRequest)
            }
        }
    }

    // ── Cleanup on dispose ────────────────────────────────────────────────────
    // Restores window scale and rounded corners if the composable is removed from
    // the tree mid-animation (e.g. navigation pop while sheet is animating).
    DisposableEffect(Unit) {
        onDispose {
            val rootView = activity?.rootContentView()
            if (rootView != null) {
                if (rootView.scaleX != BottomSheetConstants.NORMAL_SCALE ||
                    rootView.scaleY != BottomSheetConstants.NORMAL_SCALE
                ) {
                    rootView.scaleX = BottomSheetConstants.NORMAL_SCALE
                    rootView.scaleY = BottomSheetConstants.NORMAL_SCALE
                }
                removeRoundedCorners(rootView)
            }
        }
    }

    // ── Rendering ─────────────────────────────────────────────────────────────
    // The dialog is conditionally rendered to avoid an always-open dialog window.
    // needsModalReset keeps it alive briefly after drag-dismiss (see above).
    // Compute the animated scrim color so it renders inside the ModalBottomSheet dialog
    // window (which is full-screen), rather than in the parent composable's bounds.
    val animatedScrimColor = if (scrimConfig.enabled) {
        Color(0xFF0D0D0D).copy(alpha = 0.8f * scrimAlpha)
    } else {
        Color.Transparent
    }

    if (sheetState !is BottomSheetState.Hidden || needsModalReset) {
        Box(Modifier.fillMaxSize()) {

            ModalBottomSheet(
                properties = ModalBottomSheetProperties(
                    // Disable touch/keyboard interception during the invisible reset phase
                    // so the screen behind the sheet remains fully interactive.
                    isFocusable = sheetState !is BottomSheetState.Hidden,
                    securePolicy = SecureFlagPolicy.Inherit,
                    shouldDismissOnBackPress = config.isCancellable && sheetState !is BottomSheetState.Hidden,
                ),
                onDismissRequest = {
                    // Called by Material3 after a drag-dismiss or back press.
                    //
                    // isCancellable gates tap-outside and back-press dismissals.
                    // isDragDismissing is true when Material already animated the sheet
                    // away via a drag gesture — we must always honour that regardless of
                    // isCancellable, otherwise the scrim and dialog window are left
                    // stranded on screen with no way to remove them.
                    if ((config.isCancellable || isDragDismissing) &&
                        sheetState !is BottomSheetState.Exiting &&
                        sheetState !is BottomSheetState.Hidden
                    ) {
                        animationConfig.onExitAnimationStart?.invoke()
                        sheetState = BottomSheetState.Exiting(
                            dismissCallback = onDismissRequest,
                            isDragDismiss = isDragDismissing
                        )
                        isDragDismissing = false
                    }
                },
                sheetState = modalSheetState,
                // Scrim renders inside the dialog window so it always covers the full screen,
                // even when the composable is placed inside a constrained parent (e.g. LazyColumn item).
                scrimColor = animatedScrimColor,
                containerColor = Color.Transparent,
                shape = RoundedCornerShape(0.dp),
                windowInsets = WindowInsets.ime,
                dragHandle = {},
            ) {
                // Full-size tap target that dismisses the sheet when tapped outside the content.
                Box(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .fillMaxSize()
                        .clickable(
                            enabled = config.isCancellable,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) { handleDismissRequest() }
                ) {
                    // Sheet content column — applies slide, scale, and fade animations.
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = false) {}  // Absorb touches so they don't reach the dismiss tap target
                            .align(Alignment.BottomCenter)
                            .offset(y = if (animationConfig.enableSlide) slideOffset else 0.dp)
                            .padding(bottom = BottomSheetConstants.BOTTOM_MARGIN_DP.dp)
                            .graphicsLayer(
                                alpha = alpha,
                                scaleX = if (animationConfig.enableScale) sheetScale else BottomSheetConstants.NORMAL_SCALE,
                                scaleY = if (animationConfig.enableScale) sheetScale else BottomSheetConstants.NORMAL_SCALE,
                                transformOrigin = TransformOrigin(0.5f, 1f)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var measuredHeight by remember { mutableStateOf(0.dp) }

                        // Optional floating close button, centred above the sheet surface.
                        if (config.showFloatingCloseButton) {
                            val closeIconVisualElement = remember {
                                VisualElement.buildFrom(
                                    iconName = Icons.Cross,
                                    style = IconStyle.Outline,
                                    tintColor = TextColor.Primary,
                                    heightDp = 16,
                                    widthDp = 16
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(BottomSheetConstants.CLOSE_BUTTON_SIZE_DP.dp)
                                    .clickable(
                                        enabled = config.isCancellable,
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) { handleDismissRequest() },
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(BottomSheetConstants.CLOSE_BUTTON_SIZE_DP.dp)
                                        .clip(CircleShape)
                                        .background(SurfaceColor.Secondary)
                                        .clickable { handleCloseButtonClick() },
                                    contentAlignment = Alignment.Center
                                ) {
                                    PopVisualElement(
                                        visualElement = closeIconVisualElement,
                                        contentDescription = "Close"
                                    )
                                }
                            }
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(BottomSheetConstants.CLOSE_BUTTON_SPACING_DP.dp + (measuredHeight / 2))
                                    .clickable(
                                        enabled = config.isCancellable,
                                        indication = null,
                                        interactionSource = remember { MutableInteractionSource() }
                                    ) { handleDismissRequest() }
                            )
                        }

                        // Optional top visual element that straddles the sheet's top edge —
                        // half above and half below. Uses a zero-height Layout so it doesn't
                        // push the surface down.
                        config.topVisualElement?.let { visualElement ->
                            val localDensity = LocalDensity.current
                            val offsetY = remember(measuredHeight, localDensity) {
                                if (measuredHeight > 0.dp) -measuredHeight / 2 else 0.dp
                            }

                            Layout(
                                content = {
                                    Box(
                                        modifier = Modifier.onGloballyPositioned { coordinates ->
                                            val newHeight = with(localDensity) { coordinates.size.height.toDp() }
                                            if (newHeight != measuredHeight) measuredHeight = newHeight
                                        },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        PopVisualElement(
                                            visualElement = visualElement,
                                            contentDescription = "Top visual element"
                                        )
                                    }
                                },
                                modifier = Modifier.zIndex(2f).fillMaxWidth()
                            ) { measurables, constraints ->
                                val placeable = measurables.first().measure(constraints)
                                layout(constraints.maxWidth, 0) {
                                    placeable.place(
                                        x = (constraints.maxWidth - placeable.width) / 2,
                                        y = with(localDensity) { offsetY.toPx().toInt() }
                                    )
                                }
                            }
                        }

                        val surfaceShape = createShapeFromBackgroundType(
                            backgroundType = config.backgroundType,
                            cornerRadius = config.cornerRadius
                        )

                        // Sheet surface: applies the gradient background and border when requested,
                        // or stays transparent for caller-controlled backgrounds.
                        Surface(
                            modifier = Modifier
                                .zIndex(1f)
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp)
                                .navigationBarsPadding()
                                .then(
                                    when (config.backgroundType) {
                                        is BottomSheetBackgroundType.Gradient -> Modifier
                                            .popBackground(
                                                gradient = GradientPreset.SurfacePrimaryLarge.gradient,
                                                shape = surfaceShape
                                            )
                                            .border(
                                                width = PopStroke.Default,
                                                color = BorderColor.Tertiary,
                                                shape = surfaceShape
                                            )
                                        is BottomSheetBackgroundType.Transparent -> Modifier
                                    }
                                ),
                            shape = surfaceShape,
                            color = Color.Transparent
                        ) {
                            Box(modifier = Modifier.padding(top = (measuredHeight / 2))) {
                                content()
                            }
                        }
                    }
                }
            }
        }
    }
}

// ─── Private helpers ──────────────────────────────────────────────────────────

/** Walks the Context chain to find the host Activity, or returns null. */
private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

/**
 * Clips the root view to rounded corners so the background shows the sheet shape
 * while the window is scaled down during the open animation.
 *
 * Uses [android.graphics.Outline.setRoundRect] (all corners) rather than a custom
 * clip path because custom paths on ViewGroups can cause rendering distortion.
 */
private fun applyTopRoundedCorners(rootView: View, cornerRadiusPx: Float) {
    val applyCorners = {
        if (rootView.width > 0 && rootView.height > 0) {
            try {
                rootView.outlineProvider = object : android.view.ViewOutlineProvider() {
                    override fun getOutline(view: View, outline: android.graphics.Outline) {
                        val radius = cornerRadiusPx
                            .coerceAtMost(view.width / 2f)
                            .coerceAtMost(view.height / 2f)
                        outline.setRoundRect(0, 0, view.width, view.height, radius)
                    }
                }
                rootView.invalidateOutline()
                rootView.clipToOutline = true
            } catch (_: Exception) {
                // If outline setup fails, skip clipping to avoid distortion.
                rootView.clipToOutline = false
                rootView.outlineProvider = null
            }
        }
    }

    if (rootView.width == 0 || rootView.height == 0) {
        rootView.post(applyCorners)
    } else {
        applyCorners()
    }
}

/** Restores the root view to its default (no clipping) state. */
private fun removeRoundedCorners(rootView: View) {
    rootView.clipToOutline = false
    rootView.outlineProvider = null
}

/** Draws a flat 80 % opaque #0D0D0D overlay that acts as the sheet's background scrim. */
private fun Modifier.bottomSheetScrim(): Modifier =
    this.background(Color(0xFF0D0D0D).copy(alpha = 0.8f))
