package com.pop.components.ds_components

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import kotlin.math.PI
import kotlin.math.cos
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pop.components.compose_components.PopDivider
import com.pop.components.compose_components.PopDividerStyle
import com.pop.components.models.AccordionConfig
import com.pop.components.models.AccordionSeparatorColor
import com.pop.components.models.AccordionSeparatorStyle
import com.pop.components.theme.PopColors
import com.pop.compose_components.R

/**
 * Custom easing function that creates a bounce effect AFTER full expansion.
 * The animation has two phases:
 * 1. Expansion phase: Quickly expands to full size (with overshoot)
 * 2. Bounce phase: Oscillates around the final position with decreasing amplitude
 *
 * @param bounces Number of bounce oscillations after expansion (default: 1)
 * @param amplitude Maximum overshoot as a fraction (0.05 = 5% overshoot, default: 0.05)
 * @param expansionPoint When to complete expansion (0.35 = 35% of animation time, default: 0.35)
 */
class BounceEasing(
    private val bounces: Int = 1,
    private val amplitude: Float = 0.05f,
    private val expansionPoint: Float = 0.35f
) : Easing {
    override fun transform(fraction: Float): Float {
        if (fraction <= 0f) return 0f
        if (fraction >= 1f) return 1f
        
        return if (fraction < expansionPoint) {
            // Phase 1: Quick expansion from 0 to (1 + amplitude)
            // Uses ease-out curve for natural deceleration
            val t = fraction / expansionPoint
            val easeOut = 1 - (1 - t) * (1 - t)
            easeOut * (1 + amplitude)
        } else {
            // Phase 2: Bounce around 1.0 with decaying amplitude
            val bounceProgress = (fraction - expansionPoint) / (1 - expansionPoint)
            // Decay factor: starts at 1, ends at 0
            val decayFactor = 1 - bounceProgress
            // Cosine oscillation: starts at 1 (matching overshoot), ends near 0
            val oscillation = cos(bounces * PI * bounceProgress).toFloat()
            1 + (amplitude * decayFactor * oscillation)
        }
    }
}

/**
 * Accordion component with PopTitleBar header, optional border, and separator
 * Figma link: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=2945-1879&m=dev
 *
 * Features:
 * - Header section with PopTitleBar and arrow icon
 * - Expandable content section
 * - Optional border around entire accordion
 * - Separator (dashed or full width) with color options
 * - Two states: closed (collapsed) and open (expanded)
 * - Supports both controlled and uncontrolled modes
 * - Optional left avatar (mutually exclusive with titleLeftIcon in PopTitleBarConfig)
 *
 * @param config Configuration for the accordion
 * @param content Composable content to display when expanded
 * @param modifier Modifier for the component
 * @param expanded Optional controlled expanded state. When provided (non-null), the component
 *        operates in controlled mode and `onExpandStateChanged` must be used to update state.
 *        When null (default), the component manages its own state using `config.isInitiallyExpanded`.
 * @param arrowIconExpanded Drawable resource for arrow when expanded (default: up arrow)
 * @param arrowIconCollapsed Drawable resource for arrow when collapsed (default: down arrow)
 * @param arrowIconSize Size of the arrow icon (default: 24.dp)
 * @param titleBarRightSlot Optional composable slot for title bar center-right position
 * @param leftAvatar Optional composable slot for left avatar (takes precedence over titleLeftIcon)
 * @param onExpandStateChanged Callback triggered when expanded state changes
 */
@Composable
fun PopAccordion(
    config: AccordionConfig,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean? = null,
    @DrawableRes arrowIconExpanded: Int = R.drawable.up_chevron,
    @DrawableRes arrowIconCollapsed: Int = R.drawable.down_chevron,
    arrowIconSize: Dp = 24.dp,
    titleBarRightSlot: (@Composable () -> Unit)? = null,
    leftAvatar: (@Composable () -> Unit)? = null,
    onExpandStateChanged: (Boolean) -> Unit = {},
    onBodyRightIconClick: (() -> Unit)? = null
) {
    // Internal state for uncontrolled mode
    var internalExpanded by remember { mutableStateOf(config.isInitiallyExpanded) }
    
    // Determine if we're in controlled mode (expanded parameter is provided)
    val isControlled = expanded != null
    
    // Use external state if controlled, otherwise use internal state
    val isExpanded = expanded ?: internalExpanded
    
    val safeOnClick: () -> Unit = {
        val newExpandedState = !isExpanded
        if (!isControlled) {
            // Only update internal state in uncontrolled mode
            internalExpanded = newExpandedState
        }
        // Always notify parent of state change
        onExpandStateChanged(newExpandedState)
    }

    // Determine separator color
    val separatorColor = when (config.separatorColor) {
        AccordionSeparatorColor.Primary -> PopColors.Neutral.N5
        AccordionSeparatorColor.Secondary -> PopColors.Neutral.N5
        AccordionSeparatorColor.Custom -> config.customSeparatorColor ?: PopColors.Neutral.N5
    }
    
    // Main container with optional border and padding
    // Padding: 12dp when border or background is present, 0dp otherwise (per Figma design)
    val containerPadding = if (config.showBorder || config.setBg) 12.dp else 0.dp
    
    
    var boxSize by remember { mutableStateOf(Size.Zero) }
    
    // Create radial gradient background matching Figma: 
    // radial-gradient(107.02% 106.01% at 30.35% 21.05%, #1D1D1D 0%, #121212 100%)
    val gradientBrush = remember(boxSize) {
        if (config.setBg && boxSize.width > 0 && boxSize.height > 0) {
            // Calculate center position: 30.35% from left, 21.05% from top
            val centerX = boxSize.width * 0.3035f
            val centerY = boxSize.height * 0.2105f
            // Calculate radius: 107.02% of width (using larger dimension for coverage)
            val radius = boxSize.width.coerceAtLeast(boxSize.height) * 1.0702f
            
            Brush.radialGradient(
                colors = listOf(
                    Color(0xFF1D1D1D), // #1D1D1D at 0%
                    Color(0xFF121212)  // #121212 at 100%
                ),
                center = Offset(centerX, centerY),
                radius = radius
            )
        } else {
            null
        }
    }
    
    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                boxSize = Size(
                    coordinates.size.width.toFloat(),
                    coordinates.size.height.toFloat()
                )
            }
            .then(
                if (config.setBg && gradientBrush != null) {
                    Modifier.background(
                        brush = gradientBrush,
                        shape = SquircleShape(config.borderRadiusDp.dp, 1f)
                    )
                } else if (config.setBg) {
                    // Fallback to solid color if gradient not ready
                    Modifier.background(
                        color = config.backgroundColor,
                        shape = SquircleShape(config.borderRadiusDp.dp, 1f)
                    )
                } else {
                    Modifier
                }
            )
            .then(
                if (config.showBorder) {
                    Modifier.border(
                        width = config.borderWidthDp.dp,
                        color = config.borderColor,
                        shape = SquircleShape(config.borderRadiusDp.dp, 1f)
                    )
                } else {
                    Modifier
                }
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(containerPadding)
        ) {
            // Header section with PopTitleBar and arrow
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = safeOnClick)
                    .padding(bottom = if(isExpanded && config.showSeparator) 0.dp else if(isExpanded) 12.dp else 0.dp), // Conditional padding
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left avatar (if provided) - takes precedence over titleLeftIcon
                if (leftAvatar != null) {
                    leftAvatar()
                    Spacer(modifier = Modifier.width(12.dp))
                }
                
                // PopTitleBar takes remaining space
                // Note: PopTitleBar has its own padding, so we need to remove it
                // or set paddingDp to 0 in the config when used in Accordion
                // If leftAvatar is provided, remove titleLeftIcon to avoid duplicate icons
                PopTitleBar(
                    modifier = Modifier.weight(1f).padding(vertical = 10.dp),
                    config = if (leftAvatar != null) {
                        config.titleBarConfig.copy(paddingDp = 0, titleLeftIcon = null)
                    } else {
                        config.titleBarConfig.copy(paddingDp = 0)
                    },
                    rightSlot = titleBarRightSlot,
                    onBodyRightIconClick = onBodyRightIconClick
                )
                
                // Arrow icon on the right with 8dp gap (spacing/8 from Figma)
                Spacer(modifier = Modifier.width(8.dp))
                
                Image(
                    painter = painterResource(
                        id = if (isExpanded) arrowIconExpanded else arrowIconCollapsed
                    ),
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    modifier = Modifier.size(arrowIconSize)
                )
            }
            
            // Wrap separator and content in AnimatedVisibility
            // Custom bounce easing: expands fully first, then bounces once
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = BounceEasing(
                            bounces = 1,
                            amplitude = 0.03f,
                            expansionPoint = 0.4f
                        )
                    ),
                    expandFrom = Alignment.Top,
                    clip = false
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 150)
                ) + shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = BounceEasing(
                            bounces = 1,
                            amplitude = 0.02f,
                            expansionPoint = 0.4f
                        )
                    ),
                    shrinkTowards = Alignment.Top,
                    clip = false
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Separator (if enabled)
                    if (config.showSeparator) {
                        Spacer(modifier = Modifier.height(12.dp))
                        PopDivider(
                            modifier = Modifier.fillMaxWidth(),
                            style = when (config.separatorStyle) {
                                AccordionSeparatorStyle.FullWidth -> PopDividerStyle.Solid
                                AccordionSeparatorStyle.Dashed -> PopDividerStyle.Dashed
                            },
                            color = separatorColor
                        )
                    }
                    
                    // Content - stays in place, container border bounces
                    content()
                }
            }
        }
    }
}
