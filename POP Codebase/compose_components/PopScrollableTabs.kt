package com.pop.components.compose_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ScrollableTabRow // Changed from TabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset // Still useful
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import com.pop.components.theme.PopTheme
import com.pop.components.theme.PopColors

// Define colors based on the image if not already in your theme
object TabColors {
    val selectedContainerColor = Color.White // Or a light gray like #F0F0F0
    val selectedContentColor = Color.Black
    val unselectedContainerColor = Color(0xFF3A3A3A) // Dark gray from image
    val unselectedContentColor = Color(0xFFE0E0E0) // Light gray text for unselected
    val tabRowContainerBackground = Color.Transparent // Or Color(0xFF121212) if the TabRow itself has a bg
}


data class TabItem(
    val title: String,
    val showIndicator: Boolean = false
)

@Composable
fun PopScrollableTabs( // Renamed for clarity
    tabs: List<TabItem>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabHeight = 40.dp // Defines the overall height of the TabRow and the selected tab indicator

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier.height(tabHeight),
        containerColor = TabColors.tabRowContainerBackground, // Background for the whole ScrollableTabRow
        edgePadding = 0.dp, // No extra padding at the start/end of the tab list
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .fillMaxHeight()
                        // The indicator itself is the selected tab's background
                        // So, we draw it within the Tab composable now for individual pill shapes
                        // This Box can be minimal or removed if the Tab's background handles everything
                        // For the pill shape effect on the selected tab only, this indicator might be
                        // used to draw *just the pill shape* if tabs themselves are not clipped.
                        // However, the image suggests individual tabs are pill-shaped.
                        // Let's make this indicator transparent and handle shape in Tab itself for more control
                        .background(
                            color = Color.Transparent, // Indicator is now part of the Tab's background
                            shape = CircleShape
                        )
                )
            }
        },
        divider = {} // No divider needed, as per the image
    ) {
        tabs.forEachIndexed { index, tab ->
            val isSelected = selectedTabIndex == index
            val tabBackgroundColor = if (isSelected) {
                TabColors.selectedContainerColor
            } else {
                TabColors.unselectedContainerColor
            }
            val contentColor = if (isSelected) {
                TabColors.selectedContentColor
            } else {
                TabColors.unselectedContentColor
            }

            Tab(
                selected = isSelected,
                onClick = { onTabSelected(index) },
                modifier = Modifier
                    .padding(horizontal = 4.dp) // Space between tabs
                    .height(tabHeight) // Ensure tab takes full height defined for row
                    .background(tabBackgroundColor, CircleShape) // Pill shape for each tab
                    .clip(CircleShape), // Clip content to the pill shape
                // We are handling colors via the modifier background, so these might not be needed
                // selectedContentColor = contentColor,
                // unselectedContentColor = contentColor,
                text = {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Text(
                            text = tab.title,
                            color = contentColor, // Apply text color directly
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp, // Adjust as needed
                            textAlign = TextAlign.Center
                        )
                        if (tab.showIndicator) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(PopColors.Orange.O11)
                            )
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212) // Dark background for preview
@Composable
private fun PopScrollableTabsPreview_FewItems() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabItems = listOf(
        TabItem("Active"),
        TabItem("Pending"),
        TabItem("Expired"),
        TabItem("Closed")
    )

    PopTheme { // Assuming PopTheme sets up MaterialTheme
        Box(modifier = Modifier.padding(16.dp).fillMaxWidth().background(Color(0xFF121212))) {
            PopScrollableTabs(
                tabs = tabItems,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun PopScrollableTabsPreview_ManyItems() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabItems = listOf(
        TabItem("Active"),
        TabItem("Pending"),
        TabItem("Awaiting Payment"),
        TabItem("Shipped"),
        TabItem("Delivered"),
        TabItem("Cancelled"),
        TabItem("Returned"),
        TabItem("Refunded"),
        TabItem("Expired"),
        TabItem("Closed"),
        TabItem("On Hold")
    )

    PopTheme {
        Box(modifier = Modifier.padding(16.dp).fillMaxWidth().background(Color(0xFF121212))) {
            PopScrollableTabs(
                tabs = tabItems,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
