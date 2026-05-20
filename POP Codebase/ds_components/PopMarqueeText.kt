package com.pop.components.ds_components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.basicMarquee
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import com.pop.components.compose_components.PopDividerStyle
import com.pop.components.compose_components.InlineDottedUnderlineText
import com.pop.components.utils.horizontalGradientFade
import kotlinx.coroutines.delay

/**
 * A text component that supports marquee scrolling with gradient fade-out edges.
 *
 * figma link https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=1519-2670&t=Y8uwvlibJIvHPKNf-4
 * and https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=1519-3474&t=Y8uwvlibJIvHPKNf-4
 *
 * @param text The text to display
 * @param style The style to apply to the text
 * @param color The color of the text
 * @param modifier Modifier to be applied to the text
 * @param fillMaxWidth Whether the internal container should fill maximum width (default: false)
 * @param shouldAnimate Whether the marquee animation should be active (controls text movement)
 * @param textOverflows Whether the text width exceeds the available area (controls right gradient visibility)
 * @param speedPxPerSecond Speed of the marquee scroll in pixels per second (default: 100f)
 * @param delayMillis Delay in milliseconds before starting/restarting scroll (default: 1500)
 * @param loopCount Number of loops: 0 when shouldAnimate is false, 1 for single loop, null for infinite
 * @param loopDelayMillis Delay in milliseconds between each loop - the pause when text returns to start (default: 1000)
 * @param maxLines Maximum number of lines for the text (default: 1)
 * @param textAlign Text alignment within the container (default: TextAlign.Start). Most useful when shouldAnimate is false or text doesn't overflow.
 * @param gradientWidth Width of the gradient fade at edges
 * @param showLeftGradient Whether to show the fade on the left edge (only visible when animating)
 * @param showRightGradient Whether to show the fade on the right edge (always visible when textOverflows is true)
 * @param overflowDistancePx Pre-computed overflow distance in px (optional but improves sync accuracy)
 * @param loopDurationOverrideMs Override for scroll duration to sync with sibling marquee loops
 * @param showUnderline Whether to show an underline below the text (default: false)
 * @param underlineStyle Style of the underline - [PopDividerStyle.Solid], [PopDividerStyle.Dashed], or [PopDividerStyle.Dotted] (default: PopDividerStyle.Solid)
 * @param underlineColor Color of the underline (defaults to text color)
 * @param underlineDashLength Length of each dash segment (only used for dashed style, default: 4.dp)
 * @param underlineGapLength Length of gap between dashes (only used for dashed style, default: 3.dp)
 * @param underlineDotDiameter Diameter of each dot (only used for dotted style, default: 2.5.dp)
 * @param underlineDotGap Gap between dots (only used for dotted style, default: 3.dp)
 * @param underlineThickness Thickness of the underline (default: 1.dp)
 * @param onClick Optional click handler for the text (only used when showUnderline is true)
 */

// Duration for gradient fade animations
private const val GRADIENT_FADE_DURATION_MS = 300

@Composable
fun PopMarqueeText(
    text: String,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    fillMaxWidth: Boolean = false,
    shouldAnimate: Boolean = true,
    textOverflows: Boolean = false,
    speedPxPerSecond: Float = 100f,
    delayMillis: Long = 1500L,
    loopCount: Int? = null,
    loopDelayMillis: Long = 1000L,
    maxLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    gradientWidth: Dp = 56.dp,
    showLeftGradient: Boolean = false,
    showRightGradient: Boolean = false,
    overflowDistancePx: Float? = null,
    loopDurationOverrideMs: Long? = null,
    showUnderline: Boolean = false,
    underlineStyle: PopDividerStyle = PopDividerStyle.Solid,
    underlineColor: Color? = null,
    underlineDashLength: Dp = 4.dp,
    underlineGapLength: Dp = 3.dp,
    underlineDotDiameter: Dp = 2.5.dp,
    underlineDotGap: Dp = 3.dp,
    underlineThickness: Dp = 1.dp,
    onClick: (() -> Unit)? = null
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    
    // Measure text width to calculate scroll duration
    val textWidthPx = remember(text, style) {
        textMeasurer.measure(
            text = AnnotatedString(text),
            style = style,
            softWrap = false,
            maxLines = 1
        ).size.width
    }

    // Track container width to determine actual overflow
    var containerWidthPx by remember { mutableIntStateOf(0) }
    
    // Determine if text actually overflows the container (for basicMarquee to scroll)
    // This is needed because basicMarquee only scrolls when text > container
    val actuallyOverflows = containerWidthPx > 0 && textWidthPx > containerWidthPx

    // Determine how far the text needs to travel (px). Use provided overflow if available.
    // Calculate actual overflow distance: text width - container width
    val scrollDistancePx = remember(textWidthPx, containerWidthPx, overflowDistancePx) {
        val actualOverflowDistance = if (containerWidthPx > 0) {
            (textWidthPx - containerWidthPx).toFloat().coerceAtLeast(0f)
        } else {
            textWidthPx.toFloat().coerceAtLeast(0f)
        }
        (overflowDistancePx ?: actualOverflowDistance).coerceAtLeast(0f)
    }

    // Calculate scroll duration based on text width and speed
    val scrollDurationMs = remember(scrollDistancePx, speedPxPerSecond) {
        if (scrollDistancePx > 0f && speedPxPerSecond > 0f) {
            ((scrollDistancePx / speedPxPerSecond) * 1000f).toLong()
        } else {
            0L
        }
    }
    
    // If loopDurationOverrideMs is provided, calculate additional delay needed to sync with sibling
    // The shorter scroll should wait for the longer one to finish
    // The total delay should be: loopDelayMillis + (longerDuration - shorterDuration)
    // This ensures the shorter one doesn't restart until the longer one finishes
    val additionalRepeatDelayMs = remember(scrollDurationMs, loopDurationOverrideMs, loopDelayMillis) {
        if (loopDurationOverrideMs != null && scrollDurationMs < loopDurationOverrideMs) {
            // Calculate how long to wait: longer duration - shorter duration
            // This ensures both finish at the same time and restart together
            val waitTime = loopDurationOverrideMs - scrollDurationMs
            waitTime.coerceAtLeast(0L)
        } else {
            0L
        }
    }
    
    // Use the original speed - no adjustment needed
    val velocityDp = with(density) { speedPxPerSecond.toDp() }
    
    // Effective repeat delay includes both the configured delay and any sync delay
    // This ensures the shorter scroll waits for the longer one to finish
    val effectiveRepeatDelayMs = (loopDelayMillis + additionalRepeatDelayMs).toInt().coerceAtLeast(0)

    // Track when the marquee should show the left gradient
    // We can only reliably track the initial delay - after that, keep gradient visible
    // because we cannot perfectly sync with basicMarquee's internal animation state
    var isScrolling by remember(text, shouldAnimate) {
        mutableStateOf(false)
    }
    
    // Reset isScrolling when actuallyOverflows changes to false
    LaunchedEffect(actuallyOverflows) {
        if (!actuallyOverflows) {
            isScrolling = false
        }
    }

    // Simple approach: show gradient after initial delay, keep it visible while marquee is active
    // We cannot reliably track individual scroll cycles without desyncing over time
    LaunchedEffect(text, shouldAnimate, actuallyOverflows, delayMillis) {
        isScrolling = false
        
        // Only show gradient if text actually overflows the container
        if (shouldAnimate && actuallyOverflows) {
            val fadeTime = GRADIENT_FADE_DURATION_MS.toLong()
            val initialDelay = delayMillis.coerceAtLeast(0L)
            
            // Wait for initial delay (minus fade time so gradient is ready when scroll starts)
            if (initialDelay > fadeTime) {
                delay(initialDelay - fadeTime)
            }
            
            // Show gradient - it will stay visible as long as marquee is active
            isScrolling = true
        }
    }

    // Smooth fade animation for left gradient
    // Fade-in starts slightly before text movement, fade-out as text returns to start
    // IMPORTANT: Only animate if text actually overflows (actuallyOverflows check is critical)
    val leftGradientAlpha by animateFloatAsState(
        targetValue = if (isScrolling && showLeftGradient && actuallyOverflows) 1f else 0f,
        animationSpec = tween(durationMillis = GRADIENT_FADE_DURATION_MS),
        label = "leftGradientAlpha"
    )

    // Determine gradient visibility:
    // - Right gradient: only when text ACTUALLY overflows the measured container
    // - Left gradient: only when text ACTUALLY overflows (measured), alpha controls fade intensity
    // NOTE: Keep the textOverflows param as an opt-in switch, but never show gradient when text fits.
    val shouldShowRightGradient = textOverflows && showRightGradient && actuallyOverflows
    // Left gradient zone is set up when text actually overflows - leftGradientAlpha controls visibility
    val shouldShowLeftGradient = showLeftGradient && actuallyOverflows
    // Apply gradient modifier whenever we need ANY gradient (left or right)
    val shouldShowGradients = shouldShowLeftGradient || shouldShowRightGradient

    // Determine loop count: 0 when not animating, otherwise use provided value
    // 1 = single loop, null = infinite
    val effectiveLoopCount = if (!shouldAnimate) 0 else (loopCount ?: Int.MAX_VALUE)

    // We need a parent Layout/Box to apply the gradient mask *stationary* relative to the container,
    // while the Text inside moves.
    // Modifier.basicMarquee applies the scrolling transform to the layout node it's attached to.
    // If we attach drawWithContent(gradient) to the same node, the gradient moves with the text.
    // We need: [ Parent (Gradient Mask) ] -> [ Child (Marquee Text) ]
    
    // Using a Box to contain the text and apply the gradient mask on the Box layer
    val textContent = @Composable {
        Box(
            modifier = Modifier
                .then(if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier)
                .onGloballyPositioned { coordinates ->
                    // Measure container width to determine actual text overflow
                    containerWidthPx = coordinates.size.width
                }
                .then(
                    if (shouldShowGradients) {
                        Modifier.horizontalGradientFade(
                            gradientWidth = gradientWidth,
                            shouldShowLeftGradient = shouldShowLeftGradient,
                            shouldShowRightGradient = shouldShowRightGradient,
                            leftGradientAlpha = leftGradientAlpha
                        )
                    } else {
                        Modifier
                    }
                )
        ) {
            if (showUnderline) {
                // Use InlineDottedUnderlineText when underline is enabled
                InlineDottedUnderlineText(
                    text = text,
                    style = style,
                    color = underlineColor ?: color,
                    modifier = Modifier
                        .then(if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier)
                        .then(
                            if (shouldAnimate && effectiveLoopCount > 0) {
                                Modifier
                                    .basicMarquee(
                                        iterations = effectiveLoopCount,
                                        repeatDelayMillis = effectiveRepeatDelayMs,
                                        initialDelayMillis = delayMillis.toInt(),
                                        velocity = velocityDp
                                    )
                            } else {
                                Modifier
                            }
                        ),
                    textAlign = textAlign,
                    dotDiameter = when (underlineStyle) {
                        PopDividerStyle.Dashed -> underlineDashLength
                        PopDividerStyle.Dotted -> underlineDotDiameter
                        else -> 1.25.dp
                    },
                    gapBetweenDots = when (underlineStyle) {
                        PopDividerStyle.Dashed -> underlineGapLength
                        PopDividerStyle.Dotted -> underlineDotGap
                        else -> 1.5.dp
                    },
                    startOffset = 2.dp,
                    onClick = onClick
                )
            } else {
                Text(
                    modifier = Modifier
                        .then(if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier)
                        .then(
                            if (shouldAnimate && effectiveLoopCount > 0) {
                                Modifier
                                    .basicMarquee(
                                        iterations = effectiveLoopCount,
                                        repeatDelayMillis = effectiveRepeatDelayMs,
                                        initialDelayMillis = delayMillis.toInt(),
                                        velocity = velocityDp
                                    )
                            } else {
                                Modifier
                            }
                        ),
                    text = text,
                    style = style,
                    color = color,
                    textAlign = textAlign,
                    maxLines = maxLines,
                    overflow = if (maxLines > 1) TextOverflow.Ellipsis else TextOverflow.Clip,
                    softWrap = maxLines > 1
                )
            }
        }
    }

    Box(modifier = modifier) {
        textContent()
    }
}

