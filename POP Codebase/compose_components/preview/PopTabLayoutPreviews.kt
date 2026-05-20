package com.pop.components.compose_components.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pop.components.ds_components.PopTabItem
import com.pop.components.ds_components.PopTabLayout
import com.pop.components.ds_components.PopTabLayoutWidth
import com.pop.compose_components.R

private val DefaultTabs = listOf(
    PopTabItem("overview", "Overview"),
    PopTabItem("transactions", "Transactions"),
    PopTabItem("benefits", "Benefits")
)

private val DisabledTabs = listOf(
    PopTabItem("billpay", "Bill Pay"),
    PopTabItem("investments", "Investments", enabled = false),
    PopTabItem("cards", "Cards")
)

private val LongLabelTabs = listOf(
    PopTabItem("daily", "Daily Snapshot"),
    PopTabItem("weekly", "Weekly Insights"),
    PopTabItem("monthly", "Monthly Statement"),
    PopTabItem("quarterly", "Quarterly Report")
)

@Composable
private fun PreviewSurface(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        content()
    }
}

// ========== Equal Width Mode ==========

@Preview(name = "Equal • Default", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
fun PopTabLayoutEqualPreview() {
    var selected by remember { mutableStateOf(DefaultTabs.first().id) }
    PreviewSurface {
        PopTabLayout(
            tabs = DefaultTabs,
            selectedTabId = selected,
            onTabSelected = { selected = it },
            widthMode = PopTabLayoutWidth.Equal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(name = "Equal • Second Tab Selected", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
fun PopTabLayoutEqualSecondSelectedPreview() {
    var selected by remember { mutableStateOf(DefaultTabs[1].id) }
    PreviewSurface {
        PopTabLayout(
            tabs = DefaultTabs,
            selectedTabId = selected,
            onTabSelected = { selected = it },
            widthMode = PopTabLayoutWidth.Equal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(name = "Equal • With Disabled Tab", showBackground = true)
@Composable
fun PopTabLayoutEqualDisabledPreview() {
    var selected by remember { mutableStateOf(DisabledTabs.first().id) }
    PreviewSurface {
        PopTabLayout(
            tabs = DisabledTabs,
            selectedTabId = selected,
            onTabSelected = { selected = it },
            widthMode = PopTabLayoutWidth.Equal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// ========== Wrap Width Mode (Scrollable) ==========

@Preview(name = "Wrap • Long Labels", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
fun PopTabLayoutWrapPreview() {
    var selected by remember { mutableStateOf(LongLabelTabs[1].id) }
    PreviewSurface {
        PopTabLayout(
            tabs = LongLabelTabs,
            selectedTabId = selected,
            onTabSelected = { selected = it },
            widthMode = PopTabLayoutWidth.Wrap,
            modifier = Modifier.width(320.dp)
        )
    }
}

@Preview(name = "Wrap • Default Tabs", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
fun PopTabLayoutWrapDefaultPreview() {
    var selected by remember { mutableStateOf(DefaultTabs.first().id) }
    PreviewSurface {
        PopTabLayout(
            tabs = DefaultTabs,
            selectedTabId = selected,
            onTabSelected = { selected = it },
            widthMode = PopTabLayoutWidth.Wrap,
            modifier = Modifier.width(320.dp)
        )
    }
}

// ========== With Icons ==========

@Preview(name = "Equal • With Left Icons", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
fun PopTabLayoutWithLeftIconsPreview() {
    val tabsWithIcons = listOf(
        PopTabItem("home", "Home", leftIcon = R.drawable.ic_map_target_white),
        PopTabItem("search", "Search", leftIcon = R.drawable.ic_search),
        PopTabItem("profile", "Profile", leftIcon = R.drawable.ic_info_white)
    )
    var selected by remember { mutableStateOf(tabsWithIcons.first().id) }
    PreviewSurface {
        PopTabLayout(
            tabs = tabsWithIcons,
            selectedTabId = selected,
            onTabSelected = { selected = it },
            widthMode = PopTabLayoutWidth.Equal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(name = "Equal • With Right Icons", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
fun PopTabLayoutWithRightIconsPreview() {
    val tabsWithIcons = listOf(
        PopTabItem("home", "Home", rightIcon = R.drawable.ic_info_white),
        PopTabItem("search", "Search", rightIcon = R.drawable.ic_search),
        PopTabItem("profile", "Profile", rightIcon = R.drawable.ic_info_white)
    )
    var selected by remember { mutableStateOf(tabsWithIcons[1].id) }
    PreviewSurface {
        PopTabLayout(
            tabs = tabsWithIcons,
            selectedTabId = selected,
            onTabSelected = { selected = it },
            widthMode = PopTabLayoutWidth.Equal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(name = "Equal • With Both Icons", showBackground = true, backgroundColor = 0xFF0D0D0D)
@Composable
fun PopTabLayoutWithBothIconsPreview() {
    val tabsWithIcons = listOf(
        PopTabItem(
            "home",
            "Home",
            leftIcon = R.drawable.ic_info_white,
            rightIcon = R.drawable.cross
        ),
        PopTabItem(
            "search",
            "Search",
            leftIcon = R.drawable.ic_search,
            rightIcon = R.drawable.ic_close
        )
    )
    var selected by remember { mutableStateOf(tabsWithIcons.first().id) }
    PreviewSurface {
        PopTabLayout(
            tabs = tabsWithIcons,
            selectedTabId = selected,
            onTabSelected = { selected = it },
            widthMode = PopTabLayoutWidth.Equal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// ========== All States ==========

@Preview(name = "States • Selected, Unselected, Disabled", showBackground = true, backgroundColor = 0xFF0D0D0D, heightDp = 400)
@Composable
fun PopTabLayoutAllStatesPreview() {
    PreviewSurface {
        Column {
            // Selected
            var selected1 by remember { mutableStateOf("tab1") }
            PopTabLayout(
                tabs = listOf(PopTabItem("tab1", "Selected")),
                selectedTabId = selected1,
                onTabSelected = { selected1 = it },
                widthMode = PopTabLayoutWidth.Equal,
                modifier = Modifier.fillMaxWidth()
            )

            // Not selected
            var selected2 by remember { mutableStateOf("other") }
            PopTabLayout(
                tabs = listOf(PopTabItem("tab2", "Not Selected"), PopTabItem("other", "Other")),
                selectedTabId = selected2,
                onTabSelected = { selected2 = it },
                widthMode = PopTabLayoutWidth.Equal,
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
            )

            // Disabled
            var selected3 by remember { mutableStateOf("tab4") }
            PopTabLayout(
                tabs = listOf(
                    PopTabItem("tab4", "Enabled"),
                    PopTabItem("tab5", "Disabled", enabled = false)
                ),
                selectedTabId = selected3,
                onTabSelected = { selected3 = it },
                widthMode = PopTabLayoutWidth.Equal,
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
            )
        }
    }
}
