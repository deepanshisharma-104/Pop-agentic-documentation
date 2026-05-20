package com.pop.components.ds_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.theme.BorderColor
import com.pop.components.theme.GradientPreset
import com.pop.components.theme.IconStyle
import com.pop.components.theme.Icons
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopIconResources
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopStroke
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import com.pop.components.utils.glowEffect
import com.pop.components.utils.popBackground

/**
 * Figma: Checkbox control (node `114:2926`) + "Checkox With Text" (node `5345:1987`).
 *
 * NOTE: This is the V2 checkbox implementation. Keep `PopCheckbox.kt` unchanged.
 */

enum class PopCheckBoxAlign {
    Left,
    Right,
}

enum class PopCheckBoxState {
    Selected,
    Unselected,
    Indeterminate,
}

/**
 * Control-only checkbox (20dp) used by [PopCheckBoxWithTextV2].
 */
@Composable
fun PopCheckBoxV2(
    state: PopCheckBoxState,
    onStateChange: (PopCheckBoxState) -> Unit,
    modifier: Modifier = Modifier,
    promoted: Boolean = false,
    enabled: Boolean = true,
) {
    val canToggle = enabled && state != PopCheckBoxState.Indeterminate
    fun handleToggleClick() {
        if (!canToggle) return
        val finalState = when (state) {
            PopCheckBoxState.Selected -> PopCheckBoxState.Unselected
            PopCheckBoxState.Unselected -> PopCheckBoxState.Selected
            PopCheckBoxState.Indeterminate -> return
        }
        onStateChange(finalState)
    }

    PopCheckBoxV2Content(
        state = state,
        promoted = promoted,
        enabled = enabled,
        modifier = modifier.then(if (canToggle) Modifier.clickable { handleToggleClick() } else Modifier)
    )
}

@Composable
private fun PopCheckBoxV2Content(
    state: PopCheckBoxState,
    promoted: Boolean,
    enabled: Boolean,
    modifier: Modifier,
) {
    // Figma spec: 8dp glyph, 6dp padding -> 20dp overall
    val outerSize = 20.dp
    val indicatorShape = RoundedCornerShape(PopRadius.ExtraSmall) // 4dp

    val isSelected = state != PopCheckBoxState.Unselected
    val isIndeterminate = state == PopCheckBoxState.Indeterminate

    val borderColor = when {
        !enabled -> BorderColor.Tertiary
        promoted -> BorderColor.Brand
        else -> BorderColor.PrimaryInvert
    }

    val backgroundGradient: PopGradient? = when {
        isSelected && enabled && promoted -> PopGradient.Linear(
            colors = listOf(SurfaceColor.Gradient.Brand.Start, SurfaceColor.Gradient.Brand.End),
            angleInDegrees = 180f
        )

        isSelected && enabled -> GradientPreset.SurfacePrimary.gradient // #FFF -> #999
        else -> null
    }

    val backgroundColor: Color? = when {
        isSelected && !enabled -> SurfaceColor.SecondaryDisabled // #262626
        else -> null
    }

    // Figma effects:
    // - Glow-Primary-Invert/Small: #FFFFFF6B blur 9
    // - Glow-Brand Primary/Small: #FF500B9E blur 9
    val glow: Color? = when {
        isSelected && enabled && promoted -> Color(0x9EFF500B)
        isSelected && enabled && !isIndeterminate -> Color(0x6BFFFFFF)
        else -> null
    }

    // Padding for glow effect visibility
    // Glow uses blurRadius (9.dp), so we need padding to ensure it's fully visible
    // Apply padding to all checkboxes for consistent dimensions
    val checkboxPadding = 0.dp

    // Wrap checkbox in outer Box with padding for glow effect visibility
    Box(
        modifier = modifier.padding(checkboxPadding)
    ) {
        Box(
            modifier = Modifier
                .size(outerSize)
                .then(
                    if (glow != null) {
                        Modifier.glowEffect(
                            blurRadius = 9.dp,
                            spreadRadius = 0.dp,
                            color = glow,
                            shape = indicatorShape
                        )
                    } else {
                        Modifier.clip(indicatorShape) // Only clip when no glow
                    }
                )
                .then(
                    when {
                        backgroundGradient != null -> Modifier.popBackground(
                            gradient = backgroundGradient,
                            shape = indicatorShape
                        )

                        backgroundColor != null -> Modifier.background(
                            color = backgroundColor,
                            shape = indicatorShape
                        )

                        else -> Modifier
                    }
                )
                .then(
                    if (!isSelected) {
                        Modifier.border(
                            width = PopStroke.Default,
                            color = borderColor,
                            shape = indicatorShape
                        )
                    } else {
                        Modifier
                    }
                )
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            val iconName = when (state) {
                PopCheckBoxState.Selected -> Icons.CheckBoxSelected
                PopCheckBoxState.Indeterminate -> Icons.CheckBoxIndeterminate
                PopCheckBoxState.Unselected -> null
            }

            val resId = iconName?.let { PopIconResources.getIconResourceId(it, IconStyle.Filled) }
            if (resId != null) {
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = null,
                    modifier = Modifier.size(8.dp),
                    colorFilter = ColorFilter.tint(SurfaceColor.Primary)
                )
            }
        }
    }
}

/**
 * Checkbox with title and optional body text.
 *
 * Figma layout:
 * - Row gap: 12dp
 * - Text column gap: 2dp
 * - Title typography: 14sp / 500 / lineHeight 20
 * - Body typography: 12sp / 400 / lineHeight 20
 */
@Composable
fun PopCheckBoxWithTextV2(
    state: PopCheckBoxState,
    onStateChange: (PopCheckBoxState) -> Unit,
    title: AnnotatedString,
    body: String?,
    modifier: Modifier = Modifier,
    promoted: Boolean = false,
    enabled: Boolean = true,
    align: PopCheckBoxAlign = PopCheckBoxAlign.Left,
    onTitleClick: ((Int) -> Unit)? = null,
) {
    val canToggle = enabled && state != PopCheckBoxState.Indeterminate
    fun handleToggleClick() {
        if (!canToggle) return
        val finalState = when (state) {
            PopCheckBoxState.Selected -> PopCheckBoxState.Unselected
            PopCheckBoxState.Unselected -> PopCheckBoxState.Selected
            PopCheckBoxState.Indeterminate -> return
        }
        onStateChange(finalState)
    }

    val titleColor = if (enabled) TextColor.Primary else TextColor.PrimaryTransparent40
    val bodyColor = when {
        !enabled -> TextColor.BrandDisabledFromTokens
        state == PopCheckBoxState.Selected -> TextColor.SuccessFromTokens
        promoted -> TextColor.BrandFromTokens
        else -> TextColor.SecondaryFromTokens
    }

    val checkboxComposable: @Composable () -> Unit = {
        PopCheckBoxV2Content(
            state = state,
            promoted = promoted,
            enabled = enabled,
            modifier = Modifier
        )
    }

    val textComposable: @Composable RowScope.() -> Unit = {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            if (onTitleClick != null) {
                ClickableText(
                    text = title,
                    style = TextStyle(
                        fontFamily = PopTypography.figtree,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        lineHeight = 20.sp,
                        color = titleColor,
                        textAlign = TextAlign.Start
                    ),
                    onClick = onTitleClick
                )
            } else {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = PopTypography.figtree,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(500),
                        lineHeight = 20.sp,
                        color = titleColor,
                        textAlign = TextAlign.Start
                    )
                )
            }

            if (body != null) {
                Text(
                    text = body,
                    style = TextStyle(
                        fontFamily = PopTypography.figtree,
                        fontSize = 12.sp,
                        fontWeight = FontWeight(400),
                        lineHeight = 20.sp,
                        color = bodyColor,
                        textAlign = TextAlign.Start
                    )
                )
            }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(if (canToggle) Modifier.clickable { handleToggleClick() } else Modifier),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (align) {
            PopCheckBoxAlign.Left -> {
                checkboxComposable()
                textComposable()
            }

            PopCheckBoxAlign.Right -> {
                textComposable()
                checkboxComposable()
            }
        }
    }
}


