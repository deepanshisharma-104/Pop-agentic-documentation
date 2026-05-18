package com.pop.components.ds_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import kotlin.math.abs
import com.pop.components.theme.BorderColor
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopStroke
import com.pop.components.theme.SurfaceColor
import com.pop.components.utils.glowEffect
import com.pop.components.utils.popBackground
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Figma: `Toggle` (node `5345:4180`)
 */
enum class PopToggleSize {
    Large,
    Medium,
}

enum class PopToggleState {
    On,
    Off,
    Indeterminate,
}

private const val TOGGLE_INDETERMINATE_FLASH_MS: Long = 100L
private const val TOGGLE_TO_CENTER_MS: Int = 70
private const val TOGGLE_TO_EDGE_MS: Int = 180

/**
 * A small control-only toggle (switch) with support for:
 * - Size (Large / Medium)
 * - Promoted (orange border/glow/gradient when ON)
 * - Disabled
 * - Indeterminate (pill-shaped smaller indicator centered)
 */
@Composable
fun PopToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    size: PopToggleSize = PopToggleSize.Large,
    promoted: Boolean = false,
    enabled: Boolean = true,
    indeterminate: Boolean = false,
) {
    val state = when {
        indeterminate -> PopToggleState.Indeterminate
        checked -> PopToggleState.On
        else -> PopToggleState.Off
    }

    PopToggle(
        state = state,
        onStateChange = { newState ->
            when (newState) {
                PopToggleState.On -> onCheckedChange(true)
                PopToggleState.Off -> onCheckedChange(false)
                PopToggleState.Indeterminate -> Unit
            }
        },
        modifier = modifier,
        size = size,
        promoted = promoted,
        enabled = enabled,
    )
}

@Composable
fun PopToggle(
    state: PopToggleState,
    onStateChange: (PopToggleState) -> Unit,
    modifier: Modifier = Modifier,
    size: PopToggleSize = PopToggleSize.Large,
    promoted: Boolean = false,
    enabled: Boolean = true,
) {
    val scope = rememberCoroutineScope()
    var animJob: Job? by remember { mutableStateOf(null) }
    var isTransitioning by remember { mutableStateOf(false) }
    var transitionTarget: PopToggleState? by remember { mutableStateOf(null) }
    val thumbAnim = remember {
        Animatable(
            when (state) {
                PopToggleState.Off -> 0f
                PopToggleState.On -> 1f
                PopToggleState.Indeterminate -> 0.5f
            }
        )
    }

    val canToggle = enabled && state != PopToggleState.Indeterminate && !isTransitioning

    // If state changes externally, snap thumb to match.
    LaunchedEffect(state) {
        if (!isTransitioning) {
            transitionTarget = null
            thumbAnim.snapTo(
                when (state) {
                PopToggleState.Off -> 0f
                PopToggleState.On -> 1f
                PopToggleState.Indeterminate -> 0.5f
            }
            )
        }
    }

    fun handleToggleClick() {
        if (!canToggle) return

        val finalState = when (state) {
            PopToggleState.On -> PopToggleState.Off
            PopToggleState.Off -> PopToggleState.On
            PopToggleState.Indeterminate -> return
        }

        val target = if (finalState == PopToggleState.On) 1f else 0f
        animJob?.cancel()
        animJob = scope.launch {
            isTransitioning = true
            transitionTarget = finalState
            try {
                // Two-step animation: move to center (indeterminate) briefly, then to target.
                thumbAnim.animateTo(
                    targetValue = 0.5f,
                    animationSpec = tween(durationMillis = TOGGLE_TO_CENTER_MS, easing = FastOutSlowInEasing)
                )
                delay(TOGGLE_INDETERMINATE_FLASH_MS.coerceAtMost(80L))

                thumbAnim.animateTo(
                    targetValue = target,
                    animationSpec = tween(durationMillis = TOGGLE_TO_EDGE_MS, easing = FastOutSlowInEasing)
                )
            } catch (_: CancellationException) {
                // ignore
            } finally {
                isTransitioning = false
                transitionTarget = null
            }
        }

        // Callback only with final state (never Indeterminate).
        onStateChange(finalState)
    }

    val thumbFraction = thumbAnim.value
    val baseVisualState = transitionTarget ?: state

    val containerHeight = if (size == PopToggleSize.Large) 28.dp else 24.dp
    val padding = 4.dp
    val trackWidth = if (size == PopToggleSize.Large) 40.dp else 32.dp
    val knobWidth = if (size == PopToggleSize.Large) 24.dp else 20.dp

    val baseKnobHeight = containerHeight - padding * 2
    val indeterminateHeight = if (size == PopToggleSize.Large) 12.dp else 10.dp
    val centerProximity = if (isTransitioning) {
        val dist = abs(thumbFraction - 0.5f)
        (1f - (dist / 0.25f)).coerceIn(0f, 1f)
    } else 0f
    val knobHeight: Dp = lerp(baseKnobHeight, indeterminateHeight, centerProximity)

    val shape = RoundedCornerShape(PopRadius.XLLarge)

    val containerGradient: PopGradient? =
        when {
            !enabled -> null
            baseVisualState == PopToggleState.Off -> PopGradient.Linear(
                colors = listOf(SurfaceColor.Gradient.Secondary.Start, SurfaceColor.Gradient.Secondary.End),
                angleInDegrees = 180f
            )
            promoted -> PopGradient.Linear(
                colors = listOf(SurfaceColor.Gradient.Brand.Start, SurfaceColor.Gradient.Brand.End),
                angleInDegrees = 180f
            )
            else -> GradientPreset.SurfacePrimary.gradient
        }

    val containerBackgroundColor: Color? = when {
        enabled -> null
        baseVisualState != PopToggleState.Off -> SurfaceColor.SecondaryDisabled
        else -> null // OFF disabled has no bg
    }

    val containerBorderColor: Color? = when {
        enabled && baseVisualState != PopToggleState.Off && promoted -> PopColor.Brand.Brand500 // #FF7533 (brand-500)
        enabled && baseVisualState != PopToggleState.Off -> BorderColor.Secondary // #333
        else -> BorderColor.Tertiary // #4D4D4D
    }

    val containerGlow: Triple<Dp, Dp, Color>? = when {
        !enabled -> null
        baseVisualState != PopToggleState.Off && promoted -> Triple(9.dp, 0.dp, Color(0x9EFF500B)) // #FF500B9E
        baseVisualState != PopToggleState.Off -> Triple(9.dp, 0.dp, Color(0x6BFFFFFF)) // #FFFFFF6B
        else -> null
    }

    // Knob styling:
    // - OFF enabled: white gradient knob (with optional strong glow when promoted)
    // - OFF disabled: secondary-disabled knob
    // - ON states: black knob (or black centered pill for indeterminate)
    val knobGradient: PopGradient? = when {
        enabled && baseVisualState == PopToggleState.Off -> GradientPreset.SurfacePrimary.gradient
        else -> null
    }

    val knobColor: Color? = when {
        !enabled && baseVisualState == PopToggleState.Off -> SurfaceColor.SecondaryDisabled
        baseVisualState != PopToggleState.Off -> SurfaceColor.Primary
        else -> null
    }

    val knobGlow = when {
        enabled && promoted && baseVisualState == PopToggleState.Off -> Triple(32.dp, 2.dp, Color(0x40FFFFFF)) // rgba(255,255,255,0.25)
        else -> null
    }

    Box(
        modifier = modifier
            .size(width = trackWidth + padding * 2, height = containerHeight)
            .clip(shape)
            .then(
                when {
                    containerGlow != null -> Modifier.glowEffect(
                        blurRadius = containerGlow.first,
                        spreadRadius = containerGlow.second,
                        color = containerGlow.third,
                        shape = shape
                    )
                    else -> Modifier
                }
            )
            .then(
                when {
                    containerGradient != null -> Modifier.popBackground(gradient = containerGradient, shape = shape)
                    containerBackgroundColor != null -> Modifier.background(color = containerBackgroundColor, shape = shape)
                    else -> Modifier
                }
            )
            .border(width = PopStroke.Default, color = containerBorderColor ?: BorderColor.Tertiary, shape = shape)
            .then(if (canToggle) Modifier.clickable { handleToggleClick() } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(width = trackWidth, height = containerHeight - padding * 2)
                .padding(0.dp)
        ) {
            val knobOffsetX = (trackWidth - knobWidth) * thumbFraction
            val knobOffsetY = (baseKnobHeight - knobHeight) / 2

            Box(
                modifier = Modifier
                    .size(width = knobWidth, height = knobHeight)
                    .offset(x = knobOffsetX, y = knobOffsetY)
                    .clip(shape)
                    .then(
                        when {
                            knobGlow != null -> Modifier.glowEffect(
                                blurRadius = knobGlow.first,
                                spreadRadius = knobGlow.second,
                                color = knobGlow.third,
                                shape = shape
                            )
                            else -> Modifier
                        }
                    )
                    .then(
                        when {
                            knobGradient != null -> Modifier.popBackground(gradient = knobGradient, shape = shape)
                            knobColor != null -> Modifier.background(color = knobColor, shape = shape)
                            else -> Modifier
                        }
                    )
            )
        }
    }
}


