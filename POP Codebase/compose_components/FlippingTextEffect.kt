package com.pop.components.compose_components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.abs

@Composable
fun FlippingTextEffect(
    modifier: Modifier = Modifier,
    fixedTextStart: String,
    flippingTexts: List<String>,
    fixedTextEnd: String,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    flipDurationMillis: Int = 500, // Duration for one half of the flip (e.g., 0 to 90)
    delayBetweenFlipsMillis: Long = 1500 // Delay before starting the next flip cycle
) {
    var currentIndex by remember { mutableStateOf(0) }
    var currentTextToDisplay by remember { mutableStateOf(flippingTexts[currentIndex]) }
    val rotationX = remember { Animatable(0f) } // Using Animatable for more control

    LaunchedEffect(key1 = flippingTexts, key2 = delayBetweenFlipsMillis) {
        while (true) {
            // Animate out (current text flips up)
            rotationX.animateTo(
                targetValue = -90f,
                animationSpec = tween(durationMillis = flipDurationMillis, easing = FastOutSlowInEasing)
            )

            // Change text and reset rotation to flip in from the "other side"
            currentIndex = (currentIndex + 1) % flippingTexts.size
            currentTextToDisplay = flippingTexts[currentIndex]
            rotationX.snapTo(90f) // Snap to the other side without animation

            // Animate in (new text flips down)
            rotationX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = flipDurationMillis, easing = FastOutSlowInEasing)
            )

            delay(delayBetweenFlipsMillis)
        }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically // Or Baseline if text styles differ significantly
    ) {
        if (fixedTextStart.isNotEmpty()) {
            Text(text = fixedTextStart, style = textStyle)
        }

        Box(
            modifier = Modifier
                .graphicsLayer {
                    this.rotationX = rotationX.value
                    this.transformOrigin = TransformOrigin.Center // Rotate around the center
                    // Hide the text when it's perfectly on its edge during the flip
                    this.alpha = if (abs(rotationX.value) > 85f) 0f else 1f
                }
        ) {
            // Use a fixed width for the flipping text area if texts have different widths
            // to prevent the fixed text from jumping. You can find the max width needed.
            // For simplicity, this example doesn't implement dynamic width calculation.
            Text(
                text = currentTextToDisplay,
                style = textStyle,
                textAlign = TextAlign.Center // Optional: if texts have different widths
            )
        }

        if (fixedTextEnd.isNotEmpty()) {
            Text(text = fixedTextEnd, style = textStyle)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FlippingTextEffectPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FlippingTextEffect(
                fixedTextStart = "Pay with ",
                flippingTexts = listOf("Card", "UPI", "Wallet", "NetBanking"),
                fixedTextEnd = " securely"
            )

            Spacer(Modifier.height(30.dp))

            FlippingTextEffect(
                fixedTextStart = "Get ",
                flippingTexts = listOf("10%", "20%", "30%"),
                fixedTextEnd = " OFF!",
                textStyle = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(30.dp))

            FlippingTextEffect(
                fixedTextStart = "", // No start text
                flippingTexts = listOf("Loading...", "Processing...", "Finalizing..."),
                fixedTextEnd = "" // No end text
            )
        }
    }
}
