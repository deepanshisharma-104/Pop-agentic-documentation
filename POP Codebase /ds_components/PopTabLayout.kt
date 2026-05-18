package com.pop.components.ds_components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.node.DelegatingNode
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pop.components.compose_components.PopDivider
import com.pop.components.theme.PopColor
import com.pop.components.theme.PopTypography

/**
 * Underline-style tab layout for POP Design System.
 *
 * Features:
 * - Underline indicator on selected tab (bottom border)
 * - Optional left/right icons
 * - Two layout modes: Equal (distributed) or Wrap (scrollable)
 * - Three states: Selected, Not selected, Disabled
 * - Swipeable: Swipe left/right to switch tabs
 *
 * Based on: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=3018-1630
 * Specs: https://www.figma.com/design/yDf8UqgkvEJofpxyCYAmIC/POP-DS?node-id=3716-11900
 */
@Composable
fun PopTabLayout(
    tabs: List<PopTabItem>,
    selectedTabId: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    widthMode: PopTabLayoutWidth = PopTabLayoutWidth.Wrap,
    colors: PopTabLayoutColors = PopTabLayoutDefaults.colors(),
    enableSwipe: Boolean = true,
    swipeThreshold: Float = 50f,
    showDivider: Boolean = false
) {
    if (tabs.isEmpty()) return

    val selectedIndex = tabs.indexOfFirst { it.id == selectedTabId }.takeIf { it >= 0 } ?: 0
    var dragStartX by remember { mutableStateOf(0f) }
    var totalDragX by remember { mutableStateOf(0f) }

    // Swipe gesture modifier
    val swipeModifier = if (enableSwipe && tabs.size > 1) {
        Modifier.pointerInput(Unit) {
            detectHorizontalDragGestures(
                onDragStart = { offset ->
                    dragStartX = offset.x
                    totalDragX = 0f
                },
                onHorizontalDrag = { _, dragAmount ->
                    totalDragX += dragAmount
                },
                onDragEnd = {
                    // Determine swipe direction and switch tab
                    if (kotlin.math.abs(totalDragX) >= swipeThreshold) {
                        if (totalDragX > 0 && selectedIndex > 0) {
                            // Swipe right - go to previous tab
                            onTabSelected(tabs[selectedIndex - 1].id)
                        } else if (totalDragX < 0 && selectedIndex < tabs.size - 1) {
                            // Swipe left - go to next tab
                            onTabSelected(tabs[selectedIndex + 1].id)
                        }
                    }
                    totalDragX = 0f
                },
                onDragCancel = {
                    totalDragX = 0f
                }
            )
        }
    } else {
        Modifier
    }

    CompositionLocalProvider(LocalIndication provides NoRippleIndicationNodeFactory) {
        Column(modifier = modifier.fillMaxWidth()) {
            when (widthMode) {
                PopTabLayoutWidth.Equal -> {
                    TabRow(
                        selectedTabIndex = selectedIndex,
                        modifier = Modifier.fillMaxWidth().then(swipeModifier),
                        containerColor = Color.Transparent,
                        indicator = { tabPositions ->
                            if (selectedIndex < tabPositions.size) {
                                Box(
                                    modifier = Modifier
                                        .tabIndicatorOffset(tabPositions[selectedIndex])
                                        .height(1.dp)
                                        .background(colors.indicatorColor)
                                )
                            }
                        },
                        divider = {}
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            PopTab(
                                tab = tab,
                                isSelected = index == selectedIndex,
                                onTabSelected = onTabSelected,
                                colors = colors,
                                widthMode = widthMode
                            )
                        }
                    }
                }

                PopTabLayoutWidth.Wrap -> {
                    ScrollableTabRow(
                        selectedTabIndex = selectedIndex,
                        modifier = Modifier.fillMaxWidth().then(swipeModifier),
                        containerColor = Color.Transparent,
                        edgePadding = PopTabLayoutDefaults.EdgePadding,
                        indicator = { tabPositions ->
                            if (selectedIndex < tabPositions.size) {
                                Box(
                                    modifier = Modifier
                                        .tabIndicatorOffset(tabPositions[selectedIndex])
                                        .height(1.dp)
                                        .background(colors.indicatorColor)
                                )
                            }
                        },
                        divider = {}
                    ) {
                        tabs.forEachIndexed { index, tab ->
                            PopTab(
                                tab = tab,
                                isSelected = index == selectedIndex,
                                onTabSelected = onTabSelected,
                                colors = colors,
                                widthMode = widthMode
                            )
                        }
                    }
                }
            }

            if (showDivider) {
                PopDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = PopColor.Grey.Grey900
                )
            }
        }
    }
}

@Composable
private fun PopTab(
    tab: PopTabItem,
    isSelected: Boolean,
    onTabSelected: (String) -> Unit,
    colors: PopTabLayoutColors,
    widthMode: PopTabLayoutWidth
) {
    val textColor = when {
        !tab.enabled -> colors.disabledTextColor
        isSelected -> colors.selectedTextColor
        else -> colors.unselectedTextColor
    }

    val tabModifier = when (widthMode) {
        PopTabLayoutWidth.Equal -> Modifier.fillMaxWidth()
        PopTabLayoutWidth.Wrap -> Modifier.defaultMinSize(minWidth = PopTabLayoutDefaults.MinTabWidth)
    }

    Box(
        modifier = tabModifier
            .height(PopTabLayoutDefaults.TabHeight)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = tab.enabled,
                onClick = {
                    if (tab.enabled && !isSelected) {
                        onTabSelected(tab.id)
                    }
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .height(PopTabLayoutDefaults.TabHeight)
                .padding(
                    horizontal = PopTabLayoutDefaults.HorizontalPadding,
                    vertical = PopTabLayoutDefaults.VerticalPadding
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left icon
            tab.leftIcon?.let { iconRes ->
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(PopTabLayoutDefaults.IconSize),
                    tint = textColor
                )
            }

            // Text with horizontal padding
            Box(
                modifier = Modifier.padding(horizontal = PopTabLayoutDefaults.TextHorizontalPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tab.label,
                    style = PopTypography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        letterSpacing = 0.sp
                    ),
                    color = textColor,
                    modifier = Modifier.defaultMinSize(minWidth = PopTabLayoutDefaults.TextMinWidth)
                )
            }

            // Right icon
            tab.rightIcon?.let { iconRes ->
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(PopTabLayoutDefaults.IconSize),
                    tint = textColor
                )
            }
        }
    }
}

/**
 * Defines a single tab entry.
 */
data class PopTabItem(
    val id: String,
    val label: String,
    val enabled: Boolean = true,
    @DrawableRes val leftIcon: Int? = null,
    @DrawableRes val rightIcon: Int? = null
)

enum class PopTabLayoutWidth {
    Equal,
    Wrap
}

@Immutable
class PopTabLayoutColors internal constructor(
    val indicatorColor: Color,
    val selectedTextColor: Color,
    val unselectedTextColor: Color,
    val disabledTextColor: Color
)

object PopTabLayoutDefaults {
    // Dimensions from Figma specs
    val TabHeight = 56.dp
    val HorizontalPadding = 6.dp
    val VerticalPadding = 16.dp
    val TextHorizontalPadding = 8.dp
    val TextMinWidth = 30.dp
    val MinTabWidth = 30.dp
    val IconSize = 20.dp
    val EdgePadding = 6.dp

    @Composable
    fun colors(
        indicatorColor: Color = Color(0xFFE6E6E6), // #E6E6E6 from Figma
        selectedTextColor: Color = Color(0xFFE6E6E6), // #E6E6E6
        unselectedTextColor: Color = Color(0xFF808080), // #808080
        disabledTextColor: Color = Color(0xFF4D4D4D) // #4D4D4D
    ) = PopTabLayoutColors(
        indicatorColor = indicatorColor,
        selectedTextColor = selectedTextColor,
        unselectedTextColor = unselectedTextColor,
        disabledTextColor = disabledTextColor
    )
}


// Helper extension for tab indicator offset
private fun Modifier.tabIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier = this
    .wrapContentSize(Alignment.BottomStart)
    .offset(x = currentTabPosition.left)
    .width(currentTabPosition.width)
    .padding(horizontal = PopTabLayoutDefaults.HorizontalPadding)

object NoRippleIndicationNodeFactory : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatingNode {
        return object : DelegatingNode() {}
    }

    override fun hashCode(): Int = -1
    override fun equals(other: Any?) = other === this
}
