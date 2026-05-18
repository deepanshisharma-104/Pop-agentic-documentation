package com.pop.components.ds_components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay

/**
 * A composable that displays a prefix followed by a rotating word that cycles through a list
 * with a slide animation. Goes forward (e.g. friends -> mobile number -> UPI ID -> UPI Number),
 * then cycles backwards to the first word at [delayMillisBackward], then repeats.
 *
 * @param prefix Static text shown before the rotating word (e.g. "Search ")
 * @param words List of words to cycle through (first word is the start, e.g. "friends")
 * @param style Text style for both prefix and rotating word
 * @param color Text color
 * @param delayMillis Delay between word transitions when going forward, in milliseconds
 * @param delayMillisBackward Delay when cycling backwards (typically faster)
 * @param modifier Modifier for the root Row
 */
@Composable
fun RotatingText(
    prefix: String,
    words: List<String>,
    style: TextStyle,
    color: Color,
    delayMillis: Long = 1000L,
    delayMillisBackward: Long = 125L,
    modifier: Modifier = Modifier
) {
    require(words.isNotEmpty()) { "words must not be empty" }
    var currentIndex by remember { mutableIntStateOf(0) }
    var goingForward by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            val delay = if (goingForward) delayMillis else delayMillisBackward
            delay(delay)
            if (goingForward) {
                if (currentIndex == words.size - 1) {
                    goingForward = false
                    currentIndex = (currentIndex - 1).coerceAtLeast(0)
                } else {
                    currentIndex++
                }
            } else {
                if (currentIndex <= 0) {
                    goingForward = true
                    currentIndex = 0
                } else {
                    currentIndex--
                }
            }
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = prefix,
            style = style,
            color = color
        )
        AnimatedContent(
            targetState = words[currentIndex],
            transitionSpec = {
                if (goingForward) {
                    slideInVertically(
                        animationSpec = tween(300),
                        initialOffsetY = { it }
                    ) togetherWith slideOutVertically(
                        animationSpec = tween(300),
                        targetOffsetY = { -it }
                    )
                } else {
                    slideInVertically(
                        animationSpec = tween(300),
                        initialOffsetY = { -it }
                    ) togetherWith slideOutVertically(
                        animationSpec = tween(300),
                        targetOffsetY = { it }
                    )
                }
            },
            label = "rotating_text"
        ) { word ->
            Text(
                text = word,
                style = style,
                color = color
            )
        }
    }
}
