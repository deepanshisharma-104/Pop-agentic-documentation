package com.pop.components.compose_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DottedHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    thickness: Dp = 1.dp,
    dotInterval: Float = 10f, // Spacing between the start of each dot/dash
    dotRadius: Float = 2f // Radius of each dot, or half the length of a dash
) {
    val pathEffect = PathEffect.dashPathEffect(
        intervals = floatArrayOf(dotRadius * 2, dotInterval - (dotRadius * 2)), // on, off
        phase = 0f
    )
    // For actual dots, the 'on' and 'off' intervals are tricky.
    // A small 'on' interval will look like a dot if strokeCap is Round.
    // Or, you can draw individual circles as in Option 2.

    // This setup is more for dashed lines that can look like dots if 'on' is small.
    // For perfect circles, option 2 is better.
    // Let's adjust for a "dotted" look using dashPathEffect:
    // If you want actual dots, intervals should be small "on" and larger "off"
    // e.g., floatArrayOf(2f, 8f) means 2px on (dot), 8px off.
    val dotPathEffect = PathEffect.dashPathEffect(
        // intervals: first value is "on" (dot size), second is "off" (space)
        intervals = floatArrayOf(thickness.value * 2, dotInterval), // Adjust dot size based on thickness
        phase = 0f
    )


    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness)
    ) {
        drawLine(
            color = color,
            start = Offset(0f, center.y),
            end = Offset(size.width, center.y),
            strokeWidth = thickness.toPx(),
            pathEffect = dotPathEffect // Using the specifically tuned dotPathEffect
        )
    }
}

// More refined version specifically for "dots" by making the "on" part of dashPathEffect very small
@Composable
fun DottedLineHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    thickness: Dp = 2.dp, // Thickness of the "dots"
    dotSpacing: Dp = 8.dp  // Spacing FROM THE START of one dot TO THE START of the next
) {
    val density = LocalDensity.current
    val dotDiameter = with(density){thickness.toPx()}
    val gap = with(density){(dotSpacing - thickness).toPx()}
    val pathEffect = PathEffect.dashPathEffect(
    floatArrayOf(dotDiameter, if (gap > 0) gap else 0.1f),phase = 0f
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness) // The height of the canvas should accommodate the dots
    ) {
        drawLine(
            color = color,
            start = Offset(0f, size.height / 2f), // Centered vertically
            end = Offset(size.width, size.height / 2f),
            strokeWidth = thickness.toPx(), // This becomes the dot diameter
            pathEffect = pathEffect,
            // cap = StrokeCap.Round // Makes the ends of the dashes round, thus forming dots
            // This is crucial for the "dotted" effect with dashPathEffect
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DottedDividerPreview() {
    Column {
        Text("Content Above")
        DottedHorizontalDivider(
            color = Color.Red,
            thickness = 2.dp,
            dotInterval = 12f, // Corresponds to dotInterval in the first version
            dotRadius = 2f      // Corresponds to dotRadius in the first version (diameter = 4f)
        )
        Spacer(Modifier.height(16.dp))
        DottedLineHorizontalDivider(
            color = Color.Blue,
            thickness = 3.dp, // This will be the dot diameter
            dotSpacing = 10.dp // Spacing between the start of each dot
        )
        Text("Content Below")
    }
}

