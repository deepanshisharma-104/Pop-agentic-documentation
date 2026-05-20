package com.pop.components.ds_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.theme.BorderColor
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopStroke
import com.pop.components.theme.PopRadius
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import com.pop.components.utils.popBackground

/**
 * PopPills - Pill-shaped selectable components
 * 
 * Displays a group of pills (2 or 3) that can be selected individually.
 *
 * figma link: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=4575-15065&m=dev
 *
 * @param pillTexts Labels for each pill; provide 2 or 3 items (count is derived)
 * @param hasBg Whether to show background variant (true = Default, false = Subdued)
 * @param selected Initial selection (Left, Center, Right). Taps update selection when enabled.
 * @param isDisabled Whether the pills are disabled
 * @param modifier Modifier for the container
 * @param onPillClick Callback when selection changes, receives index (0 for Left, 1 for Center, 2 for Right)
 * 
 * @sample
 * ```
 * PopPills(
 *     pillTexts = listOf("A", "B"),
 *     hasBg = true,
 *     selected = PillSelection.Left,
 *     onPillClick = { index -> /* handle selection */ }
 * )
 * ```
 */
@Composable
fun PopPills(
    modifier: Modifier = Modifier,
    pillTexts: List<String>,
    hasBg: Boolean,
    selected: PillSelection,
    isDisabled: Boolean = false,
    onPillClick: (Int) -> Unit = {}
) {
    require(pillTexts.size in 2..3) { "Provide 2 or 3 pillTexts" }

    var currentSelection by rememberSaveable(selected) { mutableStateOf(selected) }

    // Figma specs:
    // - Outer container: height 28dp, padding 4dp, radius 999dp, 0.5dp border
    // - Units: 32dp width, inner height 20dp (= 28 - 4*2), radius 999dp
    val containerPadding = 4.dp
    val pillShape = RoundedCornerShape(PopRadius.XLLarge)

    // disabled pills don't have bg
    val effectiveHasBg = hasBg && !isDisabled

    // Use provided labels (count derives from labels size).
    val count = pillTexts.size

    val containerGradient: PopGradient? = if (effectiveHasBg) {
        PopGradient.Linear(
            colors = listOf(
                SurfaceColor.Gradient.Secondary.Start,
                SurfaceColor.Gradient.Secondary.End
            ),
            angleInDegrees = 180f
        )
    } else {
        null
    }

    // Per Figma: disabled container has a solid Surface/Primary background.
    val containerBackgroundColor: Color? = if (isDisabled) SurfaceColor.Primary else null

    Box(
        modifier = modifier
            .wrapContentHeight()
            .then(
                when {
                    containerGradient != null -> Modifier.popBackground(gradient = containerGradient, shape = pillShape)
                    containerBackgroundColor != null -> Modifier.background(
                        color = containerBackgroundColor,
                        shape = pillShape
                    )
                    else -> Modifier
                }
            )
            .border(width = PopStroke.Default, color = BorderColor.Tertiary, shape = pillShape)
            .padding(containerPadding),
        contentAlignment = Alignment.Center
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
            repeat(count) { index ->
                val selectionForIndex = when (index) {
                    0 -> PillSelection.Left
                    1 -> if (count == 3) PillSelection.Center else PillSelection.Right
                    2 -> PillSelection.Right
                    else -> currentSelection
                }
                val isSelected = currentSelection == selectionForIndex

                PopPillUnit(
                    text = pillTexts[index],
                    isSelected = isSelected,
                    isDisabled = isDisabled,
                    onClick = {
                        if (!isDisabled && currentSelection != selectionForIndex) {
                            currentSelection = selectionForIndex
                            onPillClick(index)
                        }
                    },
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
    }
}

/**
 * Individual pill unit (Figma "Units" item).
 */
@Composable
private fun PopPillUnit(
    text: String,
    isSelected: Boolean,
    isDisabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val unitShape = RoundedCornerShape(PopRadius.XLLarge)

    val unitGradient: PopGradient? = when {
        !isDisabled && isSelected -> GradientPreset.SurfacePrimary.gradient // #FFF -> #999
        else -> null
    }

    val unitBackgroundColor: Color? = when {
        isDisabled && isSelected -> SurfaceColor.SecondaryDisabled // #262626
        else -> null // Unselected stays transparent to show container bg
    }

    val textColor = when {
        !isDisabled && isSelected -> TextColor.SecondaryDisabled // #333
        !isDisabled && !isSelected -> TextColor.PrimaryTransparent40 // rgba(230,230,230,0.4)
        isDisabled && isSelected -> TextColor.PrimaryInvert // #0D0D0D
        else -> TextColor.SecondaryDisabled // matches figma "not active + disabled"
    }

    Box(
        modifier = modifier
            .then(
                when {
                    unitGradient != null -> Modifier.popBackground(gradient = unitGradient, shape = unitShape)
                    unitBackgroundColor != null -> Modifier.background(color = unitBackgroundColor, shape = unitShape)
                    else -> Modifier
                }
            )
            .then(if (!isDisabled) Modifier.clickable(onClick = onClick) else Modifier)
            // Figma: px 8, py 6
            .padding(horizontal = 8.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontFamily = PopTypography.figtree,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 14.sp,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Selection position for pills
 */
enum class PillSelection {
    Left,
    Center,
    Right
}
