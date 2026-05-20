package com.pop.components.compose_components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.pop.components.theme.FlashTypography
import com.pop.components.theme.PopColors
import com.pop.components.theme.TextColors

enum class ToggleOption {
    First, Second
}

private object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(0.0f,0.0f,0.0f,0.0f)
}

// Custom modifier for animating tab indicator offset
fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    animationSpec: androidx.compose.animation.core.AnimationSpec<Dp> = tween(durationMillis = 300, easing = FastOutSlowInEasing)
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.width,
        animationSpec = animationSpec,
        label = "tabWidth"
    )
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = animationSpec,
        label = "indicatorOffset"
    )
    fillMaxSize()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}

@Composable
fun PopTabBar(
    firstOptionText: String,
    secondOptionText: String,
    selectedOption: ToggleOption,
    onOptionSelected: (ToggleOption) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(firstOptionText, secondOptionText)
    val selectedTabIndex = selectedOption.ordinal // First is 0, Second is 1
    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(PopColors.Neutral.N2)
    ) {
        CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.Transparent, // Transparent so the Box background shows
                indicator = { tabPositions ->
                    // Custom indicator that fills the selected tab
                    if (selectedTabIndex < tabPositions.size) {
                        Box(
                            modifier = Modifier
                                .customTabIndicatorOffset(tabPositions[selectedTabIndex])
                                .fillMaxSize() // Make indicator fill the tab
                                .clip(RoundedCornerShape(28.dp))
                                .background(PopColors.Misc.OnTop.Default)
                        )
                    }
                },
                divider = {} // No divider
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = selectedTabIndex == index
                    Tab(
                        selected = isSelected,
                        onClick = {
                            onOptionSelected(ToggleOption.values()[index])
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .zIndex(if (isSelected) 1f else 0f), // Ensure selected tab content is on top
                        selectedContentColor = TextColors.Primary.Default,
                        unselectedContentColor = TextColors.Primary.Default,
                        interactionSource = remember { MutableInteractionSource() },
                    )
                    {
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .padding(horizontal = 4.dp, vertical = 4.dp) // Padding for the text within the tab
                                .clip(RoundedCornerShape(24.dp))
                                .background(Color.Transparent),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = title,
                                style = TextStyle(
                                    fontFamily = FlashTypography.figtree,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
//                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    letterSpacing = (-0.01).sp,
                                    // Color is handled by selectedContentColor/unselectedContentColor
                                )
                            )
                        }
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun FlashToggleBarPreview() {
    var selectedOption by remember { mutableStateOf(ToggleOption.First) }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(PopColors.Neutral.N1)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "Toggle Bar",
            style = TextStyle(
                fontFamily = FlashTypography.figtree,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = TextColors.Primary.Default
            )
        )
        
        PopTabBar(
            firstOptionText = "Bank accounts",
            secondOptionText = "RuPay Credit Card",
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it },
            modifier = Modifier.fillMaxWidth()
        )
    }
} 