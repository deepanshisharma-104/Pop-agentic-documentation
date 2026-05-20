package com.pop.components.compose_components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset // For translation
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds // To clip content within the Box
import androidx.compose.ui.layout.onGloballyPositioned // To get the height for translation
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset // For offset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun ScrollingTextEffect(
    modifier: Modifier = Modifier,
    fixedTextStart: String,
    scrollingTexts: List<String>,
    fixedTextEnd: String,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    scrollDurationMillis: Int = 500,
    delayBetweenScrollsMillis: Long = 1500
) {
    if (scrollingTexts.isEmpty()) {
        Row(modifier = modifier) {
            if (fixedTextStart.isNotEmpty()) Text(text = fixedTextStart, style = textStyle)
            if (fixedTextEnd.isNotEmpty()) Text(text = fixedTextEnd, style = textStyle)
        }
        return
    }

    var currentIndex by remember { mutableStateOf(0) }
    val currentText = scrollingTexts[currentIndex]
    val nextText = scrollingTexts[(currentIndex + 1) % scrollingTexts.size]

    val density = LocalDensity.current
    var textHeightPx by remember { mutableStateOf(0) }

    // Animatable for the Y offset.
    // Positive offset will move text down, negative will move text up.
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(key1 = scrollingTexts, key2 = delayBetweenScrollsMillis, key3 = textHeightPx) {
        if (textHeightPx == 0) return@LaunchedEffect // Wait until height is measured

        while (true) {
            // Scroll current text up (out of view)
            // Simultaneously, scroll next text from bottom to center
            offsetY.animateTo(
                targetValue = -textHeightPx.toFloat(), // Move current text completely up
                animationSpec = tween(durationMillis = scrollDurationMillis, easing = FastOutSlowInEasing)
            )

            // After animation: Update current text, snap offset back for the new "current"
            currentIndex = (currentIndex + 1) % scrollingTexts.size
            offsetY.snapTo(0f) // The new "current" text is now at 0 offset

            delay(delayBetweenScrollsMillis)
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (fixedTextStart.isNotEmpty()) {
            Text(text = fixedTextStart, style = textStyle)
        }

        Box(
            modifier = Modifier
                .clipToBounds() // Important: Clips the children to the Box's bounds
                .onGloballyPositioned { coordinates ->
                    if (textHeightPx == 0) { // Measure only once or if it changes
                        textHeightPx = coordinates.size.height
                    }
                }
        ) {
            // Current text, moving up
            Text(
                text = currentText,
                style = textStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .offset { IntOffset(0, offsetY.value.roundToInt()) }
                    .onGloballyPositioned { coordinates ->
                        // This specific onGloballyPositioned on the Text ensures we get an
                        // accurate height of the actual text content.
                        // We use the Box's onGloballyPositioned for the initial setup
                        // and animation target, but this could refine it.
                        // For simplicity in this animation, the Box's height is primary.
                        if (textHeightPx == 0 && fixedTextStart.isEmpty() && fixedTextEnd.isEmpty()) {
                            // If this is the only element, its height defines the scroll distance
                            textHeightPx = coordinates.size.height
                        }
                    }
            )

            // Next text, moving in from bottom
            Text(
                text = nextText,
                style = textStyle,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .offset { IntOffset(0, (offsetY.value + textHeightPx).roundToInt()) }
            )
        }

        if (fixedTextEnd.isNotEmpty()) {
            Text(text = fixedTextEnd, style = textStyle)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ScrollingTextEffectPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ScrollingTextEffect(
                fixedTextStart = "Welcome to ",
                scrollingTexts = listOf("POPclub", "the Future", "our App!"),
                fixedTextEnd = " 🎉"
            )

            Spacer(Modifier.height(30.dp))

            ScrollingTextEffect(
                fixedTextStart = "Current status: ",
                scrollingTexts = listOf("Loading...", "Processing...", "Complete!"),
                fixedTextEnd = "",
                textStyle = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(30.dp))

            ScrollingTextEffect(
                fixedTextStart = "",
                scrollingTexts = listOf("Item 1", "Item 2", "Item 3", "Item 4"),
                fixedTextEnd = " selected",
                textStyle = MaterialTheme.typography.bodyLarge,
                scrollDurationMillis = 300,
                delayBetweenScrollsMillis = 1000
            )
        }
    }
}
