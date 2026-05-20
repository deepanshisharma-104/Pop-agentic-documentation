package com.pop.components.coachmarks.compose

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import com.pop.components.ds_components.ButtonSize
import com.pop.components.ds_components.ButtonState
import com.pop.components.ds_components.ButtonVariant
import com.pop.components.ds_components.PopButtonV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.coachmarks.DialogAlignment
import com.pop.components.coachmarks.DialogPosition

/**
 * Coach mark dialog that shows title, subtitle, pagination, and navigation buttons.
 * Positioned relative to the highlighted target area.
 *
 * @param title Title text for the coach mark
 * @param subtitle Optional subtitle text
 * @param currentStep Current step number (1-indexed)
 * @param totalSteps Total number of steps
 * @param targetRect Rectangle of the highlighted target area
 * @param dialogPosition Preferred position (TOP or BOTTOM)
 * @param dialogAlignment Preferred horizontal alignment
 * @param onPrevious Callback for previous button click
 * @param onNext Callback for next button click
 * @param onSkip Callback for skip button click
 * @param showPrevious Whether to show the previous button
 * @param isLastStep Whether this is the last step
 */
@Composable
fun CoachMarkDialog(
    title: String,
    subtitle: String?,
    currentStep: Int,
    totalSteps: Int,
    targetRect: Rect?,
    dialogPosition: DialogPosition,
    dialogAlignment: DialogAlignment,
    onNext: () -> Unit,
    onSkip: () -> Unit,
    isLastStep: Boolean = false,
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    
    val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }
    
    val dialogWidth = 220.dp
    val dialogWidthPx = with(density) { dialogWidth.toPx() }
    val dialogPadding = 12.dp
    val dialogPaddingPx = with(density) { dialogPadding.toPx() }
    
    // Calculate dialog position
    val dialogOffset = if (targetRect != null) {
        calculateDialogOffset(
            targetRect = targetRect,
            dialogWidthPx = dialogWidthPx,
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            dialogPosition = dialogPosition,
            dialogAlignment = dialogAlignment,
            dialogPaddingPx = dialogPaddingPx,
            density = density
        )
    } else {
        // Center on screen if no target
        IntOffset(
            x = ((screenWidth - dialogWidthPx) / 2).toInt(),
            y = (screenHeight / 2).toInt()
        )
    }
    
    val animatedDialogOffset by animateIntOffsetAsState(
        targetValue = dialogOffset,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "dialogOffset"
    )

    Box(
        modifier = Modifier
            .offset { animatedDialogOffset }
            .width(dialogWidth)
            .shadow(
                elevation = 9.dp,
                shape = RoundedCornerShape(10.dp),
                spotColor = Color.White.copy(alpha = 0.42f)
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFF999999)
                    )
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top section: Title and subtitle
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 20.sp,
                    color = Color(0xFF0D0D0D),
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.sp,
                        color = Color(0xFF4D4D4D),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            
            // Bottom section: Pagination and buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Pagination
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$currentStep",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp,
                        color = Color(0xFF0D0D0D)
                    )
                    Text(
                        text = "/",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.sp,
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                    Text(
                        text = "$totalSteps",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 16.sp,
                        color = Color(0xFF666666)
                    )
                }
                
                // Buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Skip button — shown on all steps except the last
                    if (!isLastStep) {
                        TextButton(
                            onClick = onSkip,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color(0xFF0D0D0D)
                            ),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Text(
                                text = "Skip",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    // Next/Done button - using design system PopButtonV2
                    PopButtonV2(
                        text = if (isLastStep) "Done" else "Next",
                        onClick = onNext,
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.XSmall,
                        state = ButtonState.Active
                    )
                }
            }
        }
    }
}

/**
 * Calculate the offset for the dialog based on target rect and preferences.
 */
private fun calculateDialogOffset(
    targetRect: Rect,
    dialogWidthPx: Float,
    screenWidth: Float,
    screenHeight: Float,
    dialogPosition: DialogPosition,
    dialogAlignment: DialogAlignment,
    dialogPaddingPx: Float,
    density: androidx.compose.ui.unit.Density
): IntOffset {
    val dialogHeightPx = with(density) { 120.dp.toPx() } // Approximate dialog height
    
    // Determine vertical position (top or bottom)
    val targetCenterY = targetRect.top + (targetRect.height / 2)
    val spaceAbove = targetRect.top
    val spaceBelow = screenHeight - targetRect.bottom
    
    val yPosition = when (dialogPosition) {
        DialogPosition.TOP -> {
            if (spaceAbove >= dialogHeightPx + dialogPaddingPx) {
                // Position above target
                (targetRect.top - dialogHeightPx - dialogPaddingPx).toInt()
            } else {
                // Not enough space above, position below
                (targetRect.bottom + dialogPaddingPx).toInt()
            }
        }
        DialogPosition.BOTTOM -> {
            if (spaceBelow >= dialogHeightPx + dialogPaddingPx) {
                // Position below target
                (targetRect.bottom + dialogPaddingPx).toInt()
            } else {
                // Not enough space below, position above
                (targetRect.top - dialogHeightPx - dialogPaddingPx).toInt()
            }
        }
        DialogPosition.AUTO -> {
            // Choose position based on available space
            if (spaceAbove >= spaceBelow && spaceAbove >= dialogHeightPx + dialogPaddingPx) {
                (targetRect.top - dialogHeightPx - dialogPaddingPx).toInt()
            } else if (spaceBelow >= dialogHeightPx + dialogPaddingPx) {
                (targetRect.bottom + dialogPaddingPx).toInt()
            } else {
                // Center vertically if not enough space either side
                ((screenHeight - dialogHeightPx) / 2).toInt()
            }
        }
    }
    
    // Determine horizontal position
    val targetCenterX = targetRect.left + (targetRect.width / 2)
    val dialogCenterX = dialogWidthPx / 2
    
    val xPosition = when (dialogAlignment) {
        DialogAlignment.CENTER -> {
            // Center-align with target
            val centered = targetCenterX - dialogCenterX
            centered.coerceIn(dialogPaddingPx, screenWidth - dialogWidthPx - dialogPaddingPx).toInt()
        }
        DialogAlignment.START -> {
            // Align to start of target
            val aligned = targetRect.left
            aligned.coerceIn(dialogPaddingPx, screenWidth - dialogWidthPx - dialogPaddingPx).toInt()
        }
        DialogAlignment.END -> {
            // Align to end of target
            val aligned = targetRect.right - dialogWidthPx
            aligned.coerceIn(dialogPaddingPx, screenWidth - dialogWidthPx - dialogPaddingPx).toInt()
        }
        DialogAlignment.AUTO -> {
            // Try center first, then adjust if needed
            val centered = targetCenterX - dialogCenterX
            when {
                centered < dialogPaddingPx -> {
                    // Too far left, align to start
                    targetRect.left.coerceAtLeast(dialogPaddingPx).toInt()
                }
                centered + dialogWidthPx > screenWidth - dialogPaddingPx -> {
                    // Too far right, align to end
                    (targetRect.right - dialogWidthPx)
                        .coerceIn(dialogPaddingPx, screenWidth - dialogWidthPx - dialogPaddingPx)
                        .toInt()
                }
                else -> {
                    // Center works
                    centered.toInt()
                }
            }
        }
    }
    
    return IntOffset(xPosition, yPosition)
}
