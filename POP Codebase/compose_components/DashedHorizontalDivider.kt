package com.pop.components.compose_components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Creates a horizontal dashed line divider.
 *
 * This Composable is similar in structure to DottedLineHorizontalDivider but is
 * configured for creating visible dashes rather than dots.
 *
 * @param modifier Modifier for this Composable.
 * @param color Color of the dashes.
 * @param thickness Thickness (height) of the dashes.
 * @param dashLength Length of each dash.
 * @param gapLength Length of the gap between dashes.
 * @param strokeCap Cap style for the ends of the dashes (e.g., StrokeCap.Butt, StrokeCap.Round, StrokeCap.Square).
 *                  StrokeCap.Butt is often preferred for standard dashes.
 */
@Composable
fun DashedHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    thickness: Dp = 1.dp,
    dashLength: Dp = 8.dp,
    gapLength: Dp = 4.dp,
    strokeCap: StrokeCap = StrokeCap.Butt // Default to Butt for sharper dash ends
) {
    val density = LocalDensity.current

    // Convert Dp values to Px for PathEffect
    val dashPx = with(density) { dashLength.toPx() }
    val gapPx = with(density) { gapLength.toPx() }
    val thicknessPx = with(density) { thickness.toPx() }

    // Ensure dashPx and gapPx are positive to avoid issues with PathEffect
    val safeDashPx = if (dashPx > 0) dashPx else 0.1f
    val safeGapPx = if (gapPx > 0) gapPx else 0.1f

    val pathEffect = PathEffect.dashPathEffect(
        intervals = floatArrayOf(safeDashPx, safeGapPx),
        phase = 0f
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(thickness) // The height of the canvas should accommodate the dashes
    ) {
        drawLine(
            color = color,
            start = Offset(x = 0f, y = size.height / 2f), // Centered vertically
            end = Offset(x = size.width, y = size.height / 2f),
            strokeWidth = thicknessPx,
            pathEffect = pathEffect,
            cap = strokeCap
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashedHorizontalDividerPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Content Above Dashed Divider")
        Spacer(Modifier.height(16.dp))

        DashedHorizontalDivider(
            color = Color.Black,
            thickness = 2.dp,
            dashLength = 10.dp,
            gapLength = 6.dp
        )

        Spacer(Modifier.height(32.dp))
        Text("Content Between Dividers")
        Spacer(Modifier.height(16.dp))

        DashedHorizontalDivider(
            color = Color.Red,
            thickness = 3.dp,
            dashLength = 15.dp,
            gapLength = 5.dp,
            strokeCap = StrokeCap.Round // Example with rounded dash ends
        )
        Spacer(Modifier.height(16.dp))
        Text("Content Below Dashed Divider")
    }
}
