package com.pop.components.compose_components.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.BottomBarItem
import com.pop.components.ds_components.EndStickyItem
import com.pop.components.ds_components.PopBottomBar
import com.pop.components.theme.Icons
import com.pop.components.theme.PopTheme
import com.pop.components.theme.SurfaceColor

// Sample items for previews
private val sampleItems3 = listOf(
    BottomBarItem(
        iconName = Icons.HomeBottomNav,
        label = "Home",
        id = "home"
    ),
    BottomBarItem(
        iconName = Icons.ShopBottomNav,
        label = "Shop",
        id = "shop"
    ),
    BottomBarItem(
        iconName = Icons.CardBottomNav,
        label = "Card",
        id = "card"
    )
)

private val sampleItems5 = listOf(
    BottomBarItem(
        iconName = Icons.HomeBottomNav,
        label = "Home",
        id = "home"
    ),
    BottomBarItem(
        iconName = Icons.ShopBottomNav,
        label = "Shop",
        id = "shop"
    ),
    BottomBarItem(
        iconName = Icons.CardBottomNav,
        label = "Card",
        id = "card"
    ),
    BottomBarItem(
        iconName = Icons.User01,
        label = "Profile",
        id = "profile"
    ),
    BottomBarItem(
        iconName = Icons.InfoSquare,
        label = "More",
        id = "more"
    )
)

private val sampleEndStickyItem = EndStickyItem(
    iconName = Icons.QR, // QR code icon
    contentDescription = "QR Code"
)

@Preview(
    name = "PopBottomBar - Expanded",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D
)
@Composable
fun PopBottomBarExpandedPreview() {
    PopTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            var selectedIndex by remember { mutableStateOf(0) }
            var isExpanded by remember { mutableStateOf(true) }

            PopBottomBar(
                items = sampleItems3,
                selectedItemIndex = selectedIndex,
                isExpanded = isExpanded,
                endStickyItem = sampleEndStickyItem,
                onItemSelected = { selectedIndex = it },
                onEndStickyItemClick = {},
                onExpand = { isExpanded = true },
                scrollToTop = {}
            )
        }
    }
}

@Preview(
    name = "PopBottomBar - Collapsed",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D
)
@Composable
fun PopBottomBarCollapsedPreview() {
    PopTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            var selectedIndex by remember { mutableStateOf(0) }
            var isExpanded by remember { mutableStateOf(false) }

            PopBottomBar(
                items = sampleItems3,
                selectedItemIndex = selectedIndex,
                isExpanded = isExpanded,
                endStickyItem = sampleEndStickyItem,
                onItemSelected = { selectedIndex = it },
                onEndStickyItemClick = {},
                onExpand = { isExpanded = true },
                scrollToTop = {}
            )
        }
    }
}

@Preview(
    name = "PopBottomBar - 5 Items",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D
)
@Composable
fun PopBottomBarWith5ItemsPreview() {
    PopTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            var selectedIndex by remember { mutableStateOf(2) }
            var isExpanded by remember { mutableStateOf(true) }

            PopBottomBar(
                items = sampleItems5,
                selectedItemIndex = selectedIndex,
                isExpanded = isExpanded,
                endStickyItem = sampleEndStickyItem,
                onItemSelected = { selectedIndex = it },
                onEndStickyItemClick = {},
                onExpand = { isExpanded = true },
                scrollToTop = {}
            )
        }
    }
}

@Preview(
    name = "PopBottomBar - Without EndStickyItem",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D
)
@Composable
fun PopBottomBarWithoutEndStickyItemPreview() {
    PopTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            var selectedIndex by remember { mutableStateOf(1) }
            var isExpanded by remember { mutableStateOf(true) }

            PopBottomBar(
                items = sampleItems3,
                selectedItemIndex = selectedIndex,
                isExpanded = isExpanded,
                endStickyItem = null,
                onItemSelected = { selectedIndex = it },
                onEndStickyItemClick = {},
                onExpand = { isExpanded = true },
                scrollToTop = {}
            )
        }
    }
}

@Preview(
    name = "PopBottomBar - All States",
    showBackground = true,
    backgroundColor = 0xFF0D0D0D
)
@Composable
fun PopBottomBarAllStatesPreview() {
    PopTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceColor.Primary)
                .padding(16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(24.dp)
        ) {
            // Expanded state
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                var selectedIndex1 by remember { mutableStateOf(0) }
                PopBottomBar(
                    items = sampleItems3,
                    selectedItemIndex = selectedIndex1,
                    isExpanded = true,
                    endStickyItem = sampleEndStickyItem,
                    onItemSelected = { selectedIndex1 = it },
                    onEndStickyItemClick = {},
                    onExpand = { },
                    scrollToTop = {}
                )
            }

            // Collapsed state
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                var selectedIndex2 by remember { mutableStateOf(0) }
                PopBottomBar(
                    items = sampleItems3,
                    selectedItemIndex = selectedIndex2,
                    isExpanded = false,
                    endStickyItem = sampleEndStickyItem,
                    onItemSelected = { selectedIndex2 = it },
                    onEndStickyItemClick = {},
                    onExpand = { },
                    scrollToTop = {}
                )
            }
        }
    }
}

