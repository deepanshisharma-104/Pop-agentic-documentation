package com.pop.components.ds_components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.pop.components.models.VisualElement
import com.pop.components.theme.IconName
import com.pop.components.theme.IconStyle
import com.pop.components.theme.BorderColor
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopGradient
import com.pop.components.theme.PopRadius
import com.pop.components.theme.PopSpacing
import com.pop.components.theme.PopStroke
import com.pop.components.theme.PopTypography
import com.pop.components.theme.SurfaceColor
import com.pop.components.theme.TextColor
import com.pop.components.utils.bottomPrimaryGradientWithDither
import com.pop.components.utils.intenseGlowEffect
import com.pop.components.utils.popBackground

/**
 * Represents a single navigation item in the bottom bar.
 *
 * @param iconName Type-safe icon name for the icon
 * @param label Text label for the item
 * @param id Optional identifier for the item
 */
data class BottomBarItem(
    val iconName: IconName,
    val label: String,
    val id: String = label
)

/**
 * Represents the end sticky item (rightmost button) that is always visible.
 *
 * @param iconName Type-safe icon name for the icon
 * @param contentDescription Content description for accessibility
 */
data class EndStickyItem(
    val iconName: IconName,
    val contentDescription: String
)

/**
 * Color configuration for the bottom bar component.
 * Uses PopColor design system colors and gradients.
 */
@Immutable
data class BottomBarColors(
    val selectedTextColor: Color,
    val selectedIconColor: Color,
    val unselectedTextColor: Color,
    val unselectedIconColor: Color,
    val selectedItemBackground: PopGradient,
    val barBackground: PopGradient,
    val barBorderColor: Color,
    val barBorderWidth: Dp,
    val endStickyItemBackground: Color,
    val endStickyItemIconColor: Color
)

/**
 * Default values for PopBottomBar component.
 */
object BottomBarDefaults {
    /**
     * Creates default color configuration for the bottom bar.
     * Uses PopColor design system colors and gradients.
     */
    @Composable
    fun colors(): BottomBarColors {
        return BottomBarColors(
            selectedTextColor = TextColor.PrimaryFromTokens, // White from design system
            selectedIconColor = TextColor.PrimaryFromTokens, // White from design system
            unselectedTextColor = TextColor.Tertiary, // Grey from design system
            unselectedIconColor = TextColor.Tertiary, // Grey from design system
            selectedItemBackground = PopGradient.Solid(
                color = PopColor.WhiteBlack.White.copy(alpha = 0.05f) // Flat 5% white
            ),
            barBackground = PopGradient.Radial(
                colors = listOf(
                    SurfaceColor.Gradient.SecondaryHighlighted.Start,
                    SurfaceColor.Gradient.SecondaryHighlighted.Mid,
                    SurfaceColor.Gradient.SecondaryHighlighted.End
                ),
                stops = listOf(0f, 0.46f, 1f),
                alignment = Alignment.TopCenter,
                radiusMultiplier = 1.7f,
                constrainRadiusToBoundsY = true,
                constrainRadiusBoundsModeY = PopGradient.RadiusBoundsMode.FarthestEdge
            ),
            barBorderColor = BorderColor.BottomBarBorder, // Border color from design system
            barBorderWidth = PopStroke.Default, // 0.5dp border width
            endStickyItemBackground = PopColor.WhiteBlack.White, // White for gradient start
            endStickyItemIconColor = PopColor.Grey.Grey1000 // Dark icon on white
        )
    }

    // Dimensions from Figma
    val ItemIconSize: Dp = 16.67.dp
    val ItemVerticalPadding: Dp = PopSpacing.Spacing6 // 4dp
    val ItemHorizontalPadding: Dp = PopSpacing.Spacing8 // 8dp
    val ItemGap: Dp = PopSpacing.Spacing4 // 4dp
    val EndStickyItemSize: Dp = 56.dp
    val EndStickyItemIconSize: Dp = 24.dp
    val BarEndStickyItemGap: Dp = PopSpacing.Spacing12 // 12dp gap between bar and endStickyItem
    val SelectedItemBackgroundRadius: Dp = 50.dp // Pill shape
    val AnimationDurationMillis: Int = 200
}

/**
 * Custom collapsible bottom navigation bar component.
 *
 * Features:
 * - Supports up to 5 navigation items
 * - Separate endStickyItem (always visible, not part of the bar)
 * - Collapsible behavior: when collapsed, shows only the selected item on the left
 * - Manual state control via `isExpanded` parameter
 * - Smooth animations for expand/collapse transitions
 *
 * @param items List of navigation items (max 5)
 * @param selectedItemIndex Index of the currently selected item
 * @param isExpanded Whether the bar is expanded (true) or collapsed (false)
 * @param endStickyItem Optional end sticky item (rightmost button)
 * @param onItemSelected Callback when an item is selected
 * @param onEndStickyItemClick Callback when end sticky item is clicked
 * @param onExpand Callback to expand the bar when collapsed selected item is clicked
 * @param scrollToTop Callback to scroll to top when expanded selected item is clicked
 * @param modifier Modifier for the component
 * @param colors Color configuration
 */
@Composable
fun PopBottomBar(
    items: List<BottomBarItem>,
    selectedItemIndex: Int,
    isExpanded: Boolean,
    endStickyItem: EndStickyItem?,
    onItemSelected: (Int) -> Unit,
    onEndStickyItemClick: () -> Unit,
    onExpand: () -> Unit,
    scrollToTop: () -> Unit,
    modifier: Modifier = Modifier,
    colors: BottomBarColors = BottomBarDefaults.colors()
) {
    // Validate items count
    require(items.size <= 5) { "PopBottomBar supports maximum 5 items" }
    require(selectedItemIndex in items.indices) { "selectedItemIndex must be valid" }

    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        val (topGradientBackground, bottomBarRow, endFloatingItem) = createRefs()
        // Track container width for background animation
        val containerWidth = remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current
        var suppressEntranceAnimation by remember { mutableStateOf(true) }
        LaunchedEffect(containerWidth.value) {
            if (suppressEntranceAnimation && containerWidth.value > 0.dp) {
                suppressEntranceAnimation = false
            }
        }
        val barDpAnimationSpec: FiniteAnimationSpec<Dp> = if (suppressEntranceAnimation) {
            snap()
        } else {
            tween(BottomBarDefaults.AnimationDurationMillis, easing = FastOutSlowInEasing)
        }
        val barFloatAnimationSpec: FiniteAnimationSpec<Float> = if (suppressEntranceAnimation) {
            snap()
        } else {
            tween(BottomBarDefaults.AnimationDurationMillis, easing = FastOutSlowInEasing)
        }

        // Full-width gradient behind the bar area:
        // starts at the top of the nav container and ends at the top of the rounded bar.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topGradientBackground) {
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(bottomBarRow.top)
                    bottom.linkTo(parent.bottom)
                }
                .bottomPrimaryGradientWithDither()
        )

        // Bottom bar items container - spans full width
        Box(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    containerWidth.value = with(density) {
                        coordinates.size.width.toDp()
                    }
                }
                .constrainAs(bottomBarRow) {
                    width = Dimension.fillToConstraints
                    start.linkTo(parent.start, 12.dp)
                    bottom.linkTo(parent.bottom, 12.dp)
                    end.linkTo(endFloatingItem.start, BottomBarDefaults.BarEndStickyItemGap)
                },
            contentAlignment = Alignment.CenterStart
        ) {

            // Track positions and widths of all items for sliding indicator
            val itemPositions = remember { mutableStateOf(List(items.size) { 0.dp }) } // left positions
            val itemWidths = remember { mutableStateOf(List(items.size) { 0.dp }) } // widths
            val itemHeights = remember { mutableStateOf(List(items.size) { 0.dp }) } // heights

            // Calculate selected item position, width, and height for sliding indicator
            val selectedItemLeft = itemPositions.value.getOrElse(selectedItemIndex) { 0.dp }
            val selectedItemWidth = itemWidths.value.getOrElse(selectedItemIndex) { 0.dp }
            val selectedItemHeight = itemHeights.value.getOrElse(selectedItemIndex) { 0.dp }

            // Calculate offset for indicator when collapsed (same as selected item offset)
            val indicatorOffsetX = if (!isExpanded) {
                // Calculate sum of widths of items before selected item + gaps
                val sumOfWidthsBefore = with(density) {
                    itemWidths.value
                        .take(selectedItemIndex)
                        .fold(0f) { acc, width -> acc + width.toPx() }
                }
                val sumOfGaps = with(density) {
                    BottomBarDefaults.ItemGap.toPx() * selectedItemIndex
                }
                with(density) { -(sumOfWidthsBefore + sumOfGaps).toDp() }
            } else {
                0.dp
            }

            // Animate indicator position, width, and height
            val animatedIndicatorLeft by animateDpAsState(
                targetValue = selectedItemLeft + indicatorOffsetX,
                animationSpec = barDpAnimationSpec,
                label = "indicatorLeft"
            )
            val animatedIndicatorWidth by animateDpAsState(
                targetValue = selectedItemWidth,
                animationSpec = barDpAnimationSpec,
                label = "indicatorWidth"
            )
            val animatedIndicatorHeight by animateDpAsState(
                targetValue = selectedItemHeight,
                animationSpec = barDpAnimationSpec,
                label = "indicatorHeight"
            )

            // Calculate target background dimensions
            // When expanded: use container width, when collapsed: use indicator width + padding
            val targetBackgroundWidth = if (isExpanded) {
                containerWidth.value
            } else {
                animatedIndicatorWidth + BottomBarDefaults.ItemGap * 2
            }

            val animatedBackgroundHeight by animateDpAsState(
                targetValue = maxOf(
                    animatedIndicatorHeight + BottomBarDefaults.ItemGap * 2,
                    BottomBarDefaults.EndStickyItemSize
                ),
                animationSpec = barDpAnimationSpec,
                label = "backgroundHeight"
            )

            // Animate background width - smooth transition between expanded and collapsed
            val animatedBackgroundWidth by animateDpAsState(
                targetValue = targetBackgroundWidth,
                animationSpec = barDpAnimationSpec,
                label = "backgroundWidth"
            )

            // Bottom Bar Background - always anchored at left (x=0), shrinks from right
            if (animatedBackgroundHeight > 0.dp && animatedBackgroundWidth > 0.dp) {
                Box(
                    modifier = Modifier
                        .width(animatedBackgroundWidth)
                        .height(animatedBackgroundHeight)
                        .clip(RoundedCornerShape(50.dp))
                        .popBackground(
                            gradient = colors.barBackground,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .border(
                            width = colors.barBorderWidth,
                            color = colors.barBorderColor,
                            shape = RoundedCornerShape(50.dp)
                        )
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .onGloballyPositioned { coordinates ->
                        // Register for coach marks - Bottom navigation bar with pill shape
                        val rect = coordinates.boundsInWindow()
                        com.pop.components.coachmarks.CoachMarkRegistry.registerBounds(
                            key = "bottom_nav_bar",
                            bounds = rect,
                            cornerRadiusDp = 50f // Pill shape matches RoundedCornerShape(50.dp)
                        )
                    }
                    .drawWithContent {
                        // Clip content to the animated background area so items
                        // outside the shrinking bar are not visible during collapse
                        val clipWidthPx = animatedBackgroundWidth.toPx()
                        if (clipWidthPx > 0f && size.height > 0f) {
                            val path = Path().apply {
                                addRoundRect(
                                    RoundRect(
                                        left = 0f,
                                        top = 0f,
                                        right = clipWidthPx,
                                        bottom = size.height,
                                        cornerRadius = CornerRadius(50.dp.toPx())
                                    )
                                )
                            }
                            clipPath(path) {
                                this@drawWithContent.drawContent()
                            }
                        } else {
                            drawContent()
                        }
                    }
                    .padding(vertical = BottomBarDefaults.ItemGap, horizontal = BottomBarDefaults.ItemGap)
            ) {
                // Sliding background indicator - positioned behind items
                if (animatedIndicatorWidth > 0.dp && animatedIndicatorHeight > 0.dp) {
                    Box(
                        modifier = Modifier
                            .offset(x = animatedIndicatorLeft)
                            .width(animatedIndicatorWidth)
                            .height(animatedIndicatorHeight)
                            .clip(RoundedCornerShape(BottomBarDefaults.SelectedItemBackgroundRadius))
                            .popBackground(
                                gradient = colors.selectedItemBackground,
                                shape = RoundedCornerShape(BottomBarDefaults.SelectedItemBackgroundRadius)
                            )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(BottomBarDefaults.ItemGap)
                ) {
                    items.forEachIndexed { index, item ->
                        val isSelected = index == selectedItemIndex
                        val shouldShow = isExpanded || isSelected

                        // Calculate offset for slide animation
                        // When collapsed, move selected item to the left by offsetting by sum of widths before it
                        // Inactive items also slide slightly to the left for a cohesive animation
                        val targetOffsetX = if (!isExpanded) {
                            if (isSelected) {
                                // Calculate sum of widths of items before selected item + gaps
                                val sumOfWidthsBefore = with(density) {
                                    itemWidths.value
                                        .take(index)
                                        .fold(0f) { acc, width -> acc + width.toPx() }
                                }
                                val sumOfGaps = with(density) {
                                    BottomBarDefaults.ItemGap.toPx() * index
                                }
                                -(sumOfWidthsBefore + sumOfGaps)
                            } else {
                                // Inactive items slide a little to the left
                                with(density) { -24.dp.toPx() }
                            }
                        } else {
                            0f
                        }

                        val offsetX by animateFloatAsState(
                            targetValue = targetOffsetX,
                            animationSpec = barFloatAnimationSpec,
                            label = "itemOffsetX"
                        )

                        // Animate alpha for visibility
                        val alpha by animateFloatAsState(
                            targetValue = if (shouldShow) 1f else 0f,
                            animationSpec = barFloatAnimationSpec,
                            label = "itemAlpha"
                        )

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .onGloballyPositioned { coordinates ->
                                    // Track item position, width, and height for sliding indicator
                                    val left = with(density) { coordinates.boundsInParent().left.toDp() }
                                    val width = with(density) { coordinates.size.width.toDp() }
                                    val height = with(density) { coordinates.size.height.toDp() }
                                    itemPositions.value = itemPositions.value.toMutableList().apply {
                                        this[index] = left
                                    }
                                    itemWidths.value = itemWidths.value.toMutableList().apply {
                                        this[index] = width
                                    }
                                    itemHeights.value = itemHeights.value.toMutableList().apply {
                                        this[index] = height
                                    }
                                }
                                .offset { IntOffset(offsetX.toInt(), 0) }
                                .alpha(alpha)
                        ) {
                            BottomBarItem(
                                modifier = Modifier.fillMaxWidth(),
                                item = item,
                                isSelected = isSelected,
                                enabled = shouldShow,
                                onClick = {
                                    if (isSelected) {
                                        // If clicking the selected item
                                        if (isExpanded) {
                                            // If expanded, scroll to top
                                            scrollToTop()
                                        } else {
                                            // If collapsed, expand the bar
                                            onExpand()
                                        }
                                    } else {
                                        // If clicking a different item, select it
                                        onItemSelected(index)
                                    }
                                },
                                colors = colors,
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(end = BottomBarDefaults.BarEndStickyItemGap)
                .constrainAs(endFloatingItem) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 12.dp)
                }
        ) {
            // End sticky item (always visible) - positioned absolutely on the right
            endStickyItem?.let { stickyItem ->
                EndStickyItemButton(
                    item = stickyItem,
                    onClick = onEndStickyItemClick,
                    colors = colors,
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            // Register for coach marks - QR scanner button with circular shape
                            val rect = coordinates.boundsInWindow()
                            // Use width/2 as radius for perfect circle (56dp button = 28dp radius)
                            val radiusDp = with(density) { (rect.width / 2f).toDp().value }
                            com.pop.components.coachmarks.CoachMarkRegistry.registerBounds(
                                key = "qr_scanner_button",
                                bounds = rect,
                                cornerRadiusDp = radiusDp
                            )
                        }
                )
            }
        }
    }
}

@Composable
private fun BottomBarItem(
    modifier: Modifier = Modifier,
    item: BottomBarItem,
    isSelected: Boolean,
    enabled: Boolean = true,
    onClick: () -> Unit,
    colors: BottomBarColors,
) {
    val textColor = if (isSelected) colors.selectedTextColor else colors.unselectedTextColor
    val iconColor = if (isSelected) colors.selectedIconColor else colors.unselectedIconColor
    val fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick)
            .padding(
                horizontal = BottomBarDefaults.ItemHorizontalPadding,
                vertical = BottomBarDefaults.ItemVerticalPadding
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(BottomBarDefaults.ItemGap)
    ) {
        PopVisualElement(
            visualElement = remember(item.iconName, iconColor) {
                VisualElement.buildFrom(
                    iconName = item.iconName,
                    style = IconStyle.Outline,
                    tintColor = iconColor,
                    bgColor = null
                )
            },
            modifier = Modifier.size(BottomBarDefaults.ItemIconSize),
            contentDescription = item.label
        )

        Text(
            text = item.label,
            style = PopTypography.figtreeStyles.labelXSmall.copy(
                fontWeight = fontWeight
            ),
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun EndStickyItemButton(
    item: EndStickyItem,
    onClick: () -> Unit,
    colors: BottomBarColors,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(BottomBarDefaults.EndStickyItemSize)
            .graphicsLayer { clip = false }
            .intenseGlowEffect(
                glowRadius = 8.dp,
                spread = 1.dp,
                color = Color.White,
                layers = 1,
                shape = CircleShape
            )
            .clip(CircleShape)
            .popBackground(
                gradient = com.pop.components.theme.PopGradient.Linear(
                    colors = listOf(
                        colors.endStickyItemBackground, // White
                        PopColor.SimpleGray.SimpleGray300 // Grey from design system
                    ),
                    angleInDegrees = 180f // Top to bottom
                ),
                shape = CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        PopVisualElement(
            visualElement = remember(item.iconName, colors.endStickyItemIconColor) {
                VisualElement.buildFrom(
                    iconName = item.iconName,
                    style = IconStyle.Outline,
                    tintColor = colors.endStickyItemIconColor,
                    bgColor = null
                )
            },
            modifier = Modifier.size(BottomBarDefaults.EndStickyItemIconSize),
            contentDescription = item.contentDescription
        )
    }
}

