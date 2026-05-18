package com.pop.components.ds_components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

/**
 * Shows two texts alternating with vertical slide: both texts move up together (A disappears, B takes center),
 * then both move down together (B disappears, A takes center). Repeats after [delayMillis].
 *
 * @param textA First text (e.g. "POP • Gpay • PhonePe • Paytm • UPI ")
 * @param textB Second text (e.g. "Pay to any QR code")
 * @param style Text style (e.g. Figtree Medium 12sp, line height 16sp)
 * @param colorA Color for textA (e.g. TextColor.Tertiary)
 * @param colorB Color for textB (e.g. TextColor.Secondary)
 * @param delayMillis Delay between alternations in milliseconds
 * @param modifier Modifier for the root
 */
@Composable
fun AlternatingSubtitleText(
    textA: String,
    textB: String,
    style: TextStyle,
    colorA: Color,
    colorB: Color,
    delayMillis: Long = 2300L,
    modifier: Modifier = Modifier
) {
    val offsetY = remember { Animatable(0f) }
    val textHeight = 50f // Approximate height for vertical spacing

    LaunchedEffect(Unit) {
        while (true) {
            delay(delayMillis){name}
            // Move up: A goes to -textHeight, B goes to 0
            offsetY.animateTo(-textHeight, animationSpec = tween(400))
            delay(delayMillis)
            // Move down: A goes back to 0, B goes to textHeight
            offsetY.animateTo(0f, animationSpec = tween(400))
        }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // Text A - starts at center, moves up to disappear, then comes back from top
        Text(
            text = textA,
            style = style,
            color = colorA,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset { IntOffset(0, offsetY.value.roundToInt()) }
                .alpha(
                    when {
                        offsetY.value > -textHeight / 2 -> 1f // Visible when near or at center
                        else -> 0f // Faded when moved up
                    }
                )
        )
        
        // Text B - starts below, moves up to center, then moves down to disappear
        Text(
            text = textB,
            style = style,
            color = colorB,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .offset { IntOffset(0, (offsetY.value + textHeight).roundToInt()) }
                .alpha(
                    when {
                        offsetY.value < -textHeight / 2 -> 1f // Visible when at center (offsetY is negative)
                        else -> 0f // Faded when below center
                    }
                )
        )
    }
}
