package com.pop.components.coachmarks.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import com.pop.components.coachmarks.CoachMarkConfig
import com.pop.components.coachmarks.CoachMarkRegistry
import com.pop.components.coachmarks.CoachMarkState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** TwoWayConverter so we can spring-animate a [Rect] as a single value. */
private val RectToVector = androidx.compose.animation.core.TwoWayConverter<Rect, AnimationVector4D>(
    convertToVector = { rect ->
        AnimationVector4D(rect.left, rect.top, rect.right, rect.bottom)
    },
    convertFromVector = { v ->
        Rect(v.v1, v.v2, v.v3, v.v4)
    }
)

private val boundsSpring = spring<Rect>(
    dampingRatio = Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessMedium
)

private val cornerSpring = spring<Float>(
    dampingRatio = Spring.DampingRatioNoBouncy,
    stiffness = Spring.StiffnessMedium
)

/**
 * Main composable for displaying coach marks.
 * Handles the full lifecycle of showing multiple coach mark steps.
 *
 * @param config Configuration for the coach marks
 * @param onDismiss Callback when coach marks are dismissed
 */
@Composable
fun CoachMarkDisplay(
    config: CoachMarkConfig,
    onDismiss: () -> Unit
) {
    var state by remember {
        mutableStateOf(
            CoachMarkState(
                config = config,
                currentStepIndex = 0,
                isShowing = true
            )
        )
    }

    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val screenHeightPx = with(density) { windowInfo.containerSize.height  }

    val currentStep = state.currentStep

    // Animatable instances persist for the lifetime of the composable.
    // We call snapTo() on first resolution so the highlight appears exactly
    // on the target with no animated journey from (0,0). On every subsequent
    // step change we call animateTo() for the smooth cross-step morph.
    val animatableRect = remember { Animatable(Rect.Zero, RectToVector) }
    val animatableCorner = remember { Animatable(8f) }
    // True after the very first bounds have been snapped into place.
    var hasFirstResolution by remember { mutableStateOf(false) }

    LaunchedEffect(currentStep) {
        val viewKey = currentStep?.targetViewKey

        // Scroll the target into the centre of the viewport if needed.
        // Priority:
        //   1. Explicit scrollAction on the step (full manual control)
        //   2. scrollToCenter registered in the registry (two-phase: jump + centre)
        //   3. BringIntoViewRequester (minimal-scroll fallback)
        when {
            currentStep?.scrollAction != null -> {
                currentStep.scrollAction.invoke()
                delay(300)
            }

            viewKey != null && CoachMarkRegistry.hasScrollToCenter(viewKey) -> {
                CoachMarkRegistry.scrollToCenter(viewKey)
                delay(300)
            }

            viewKey != null && CoachMarkRegistry.hasBringIntoView(viewKey) -> {
                CoachMarkRegistry.bringIntoView(viewKey)
                delay(300)
            }
        }

        fun resolvedBounds(): Pair<Rect, Float>? {
            if (viewKey == null) {
                val r = currentStep?.targetRect ?: return null
                return r to (currentStep.cornerRadiusDp ?: 8f)
            }
            val info = CoachMarkRegistry.getComponentInfo(viewKey) ?: return null
            if (info.bounds.width <= 0f || info.bounds.height <= 0f) return null
            if (info.bounds.top < 0f || info.bounds.bottom > screenHeightPx) return null
            return info.bounds to (info.cornerRadiusDp ?: 8f)
        }

        var attempts = 0
        var found: Pair<Rect, Float>? = null
        while (attempts < 20) {
            found = resolvedBounds()
            if (found != null) break
            delay(50)
            attempts++
        }

        if (found == null) {
            // Try the hardcoded fallback rect on the step itself.
            found = currentStep?.targetRect?.let { it to (currentStep.cornerRadiusDp ?: 8f) }
            if (found == null) {
                // No bounds at all — dismiss the entire coachmark flow so the user
                // is never left staring at a broken / invisible overlay.
                android.util.Log.w(
                    "CoachMarkDisplay",
                    "Target '$viewKey' not found after $attempts attempts — dismissing"
                )
                config.onSkip()
                onDismiss()
                return@LaunchedEffect
            }
            android.util.Log.w(
                "CoachMarkDisplay",
                "Bounds not found for $viewKey after $attempts attempts — using hardcoded fallback"
            )
        } else {
            android.util.Log.d(
                "CoachMarkDisplay",
                "Bounds found for $viewKey on attempt $attempts: ${found.first}"
            )
        }

        if (found != null) {
            val (rect, corner) = found
            if (!hasFirstResolution) {
                // Very first step — snap directly so highlight appears on the target
                // immediately with no animated journey from (0, 0).
                animatableRect.snapTo(rect)
                animatableCorner.snapTo(corner)
                hasFirstResolution = true
            } else {
                // Step change — animate from previous step's bounds to new ones.
                launch { animatableRect.animateTo(rect, boundsSpring) }
                launch { animatableCorner.animateTo(corner, cornerSpring) }
            }
        }

        currentStep?.onStepShown?.invoke()
    }

    // Drive the overlay from the Animatable values.
    // targetRect is null only before the very first step resolves (overlay stays hidden).
    val targetRect = if (hasFirstResolution) animatableRect.value else null
    val effectiveCornerRadius = if (hasFirstResolution) animatableCorner.value else null

    // Debug logging
    android.util.Log.d(
        "CoachMarkDisplay",
        "Step: ${currentStep?.id}, ViewKey: ${currentStep?.targetViewKey}, " +
                "CornerRadius: $effectiveCornerRadius, Style: ${currentStep?.highlightStyle}"
    )

    if (state.isShowing && currentStep != null) {
        Box(modifier = Modifier.fillMaxSize()) {

            // Overlay with dimming and animated highlight cutout
            CoachMarkOverlay(
                targetRect = targetRect,
                highlightStyle = currentStep.highlightStyle,
                cornerRadiusDp = effectiveCornerRadius,
                dimOpacity = currentStep.dimOpacity,
                onTapOutside = null
            )

            // Dialog — only shown once we have a real rect to anchor to
            if (targetRect != null || currentStep.targetRect != null) {
                CoachMarkDialog(
                    title = currentStep.title,
                    subtitle = currentStep.subtitle,
                    currentStep = state.currentStepIndex + 1,
                    totalSteps = state.totalSteps,
                    targetRect = targetRect ?: currentStep.targetRect,
                    dialogPosition = currentStep.dialogPosition,
                    dialogAlignment = currentStep.dialogAlignment,
                    onNext = {
                        if (state.isLastStep) {
                            config.onStepAction(currentStep.id, "Done")
                            config.onComplete()
                            onDismiss()
                        } else {
                            config.onStepAction(currentStep.id, "Next")
                            state = state.copy(currentStepIndex = state.currentStepIndex + 1)
                        }
                    },
                    onSkip = {
                        config.onStepAction(currentStep.id, "Skip")
                        config.onSkip()
                        onDismiss()
                    },
                    isLastStep = state.isLastStep
                )
            }
        }
    }
}

/**
 * Modifier to register a view for coach mark targeting.
 * Use this on composables that you want to highlight in coach marks.
 *
 * @param key Unique key to identify this view
 * @param onBoundsChanged Callback when view bounds change
 */
/**
 * Modifier to register a composable for coach mark targeting.
 * Automatically updates the CoachMarkRegistry when bounds change.
 *
 * @param key Unique key to identify this view
 */
@Composable
fun Modifier.coachMarkTarget(key: String): Modifier {
    return this.onGloballyPositioned { coordinates ->
        val rect = coordinates.boundsInWindow()
        CoachMarkRegistry.registerBounds(key, rect)
    }
}

/**
 * Legacy version that also calls a callback (for backwards compatibility).
 */
@Composable
fun Modifier.coachMarkTarget(
    key: String,
    onBoundsChanged: (Rect) -> Unit
): Modifier {
    return this.onGloballyPositioned { coordinates ->
        val rect = coordinates.boundsInWindow()
        CoachMarkRegistry.registerBounds(key, rect)
        onBoundsChanged(rect)
    }
}
