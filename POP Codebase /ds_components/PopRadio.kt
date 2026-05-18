package com.pop.components.ds_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pop.components.theme.BorderColor
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopStroke
import com.pop.components.theme.SurfaceColor
import com.pop.components.utils.glowEffect
import com.pop.components.utils.popBackground

/**
 * Figma: `Radio` (node `5345:2830`)
 *
 * A small control-only radio button (no label), with support for:
 * - Promoted (orange outline / orange glow)
 * - Disabled
 */
@Composable
fun PopRadio(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    promoted: Boolean = false,
    enabled: Boolean = true,
) {
    // Figma specs:
    // - Outer size: 20dp (8dp icon + 6dp padding each side)
    // - Dot size: 8dp
    val outerSize = 20.dp
    val dotSize = 8.dp

    val borderColor = when {
        !enabled -> BorderColor.Tertiary
        promoted -> BorderColor.Brand
        else -> BorderColor.PrimaryInvert
    }

    val backgroundGradient: PopGradient? = when {
        selected && enabled && promoted -> PopGradient.Linear(
            colors = listOf(SurfaceColor.Gradient.Brand.Start, SurfaceColor.Gradient.Brand.End),
            angleInDegrees = 180f
        )
        selected && enabled -> GradientPreset.SurfacePrimary.gradient // #FFF -> #999
        else -> null
    }

    val backgroundColor: Color? = when {
        selected && !enabled -> SurfaceColor.SecondaryDisabled // #262626
        else -> null
    }

    val glow = when {
        selected && enabled && promoted -> Triple(2.dp, 1.dp, Color(0x9EFF500B)) // #FF500B9E
        selected && enabled -> Triple(2.dp, 1.dp, Color(0x6BFFFFFF)) // #FFFFFF6B
        else -> null
    }

    Box(
        modifier = modifier
            .size(outerSize)
            .clip(CircleShape)
            .padding(2.dp)
            .then(
                when {
                    glow != null -> Modifier.glowEffect(
                        blurRadius = glow.first,
                        spreadRadius = glow.second,
                        color = glow.third,
                        shape = CircleShape
                    )

                    else -> Modifier
                }
            )
            .then(
                when {
                    backgroundGradient != null -> Modifier.popBackground(
                        gradient = backgroundGradient,
                        shape = CircleShape
                    )

                    backgroundColor != null -> Modifier.background(
                        color = backgroundColor,
                        shape = CircleShape
                    )

                    else -> Modifier
                }
            )
            .then(
                if (!selected) {
                    Modifier.border(
                        width = PopStroke.Default,
                        color = borderColor,
                        shape = CircleShape
                    )
                } else {
                    Modifier
                }
            )
            .then(if (enabled) Modifier.clickable(onClick = onClick) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .background(color = SurfaceColor.Primary, shape = CircleShape)
            )
        }
    }
}