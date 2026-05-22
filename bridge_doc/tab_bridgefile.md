# Tab — Design-to-Code Bridge

**Design source:** `molecules.md` — sections 9a (Tab Unit) and 9b (Tab Stack)
**Codebase source:** `PopTabLayout.kt`
**Scope:** Underline-style tab bar only (`PopTabLayout`). Sections 9c–9g (Pill, Visual, Fancy variants) are out of scope — they do not map to `PopTabLayout.kt`.

> ⚠️ `PopTabBar.kt` is a **separate** pill-style 2-option toggle (`ToggleOption.First / Second`). It does **not** implement the underline tab bar from 9a / 9b. Never use `PopTabBar` for `Tab - Stack` designs — always use `PopTabLayout`.

---

## A: Tab Unit Property Mapping

### A. Tab Unit Properties [molecules.md 9a → PopTabItem / PopTabLayout]

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | `Is active: True` | Selection is driven by `selectedTabId = tab.id` on `PopTabLayout` — there is no `isActive` field on `PopTabItem` | PopTabLayout.kt | `PopTabLayout(tabs = tabs, selectedTabId = "tab_home", onTabSelected = { id -> selectedId = id })` |
| molecules.md | `Is active: False` | Tab's `id` ≠ `selectedTabId` — tab renders as unselected automatically | PopTabLayout.kt | Any tab whose `id` is not the current `selectedTabId` |
| molecules.md | `Is disabled: True` | `PopTabItem(enabled = false)` — prevents click, applies `disabledTextColor`, cannot be set as `selectedTabId` | PopTabLayout.kt | `PopTabItem(id = "tab_1", label = "Offers", enabled = false)` |
| molecules.md | `Is disabled: False` | `PopTabItem(enabled = true)` (default) | PopTabLayout.kt | `PopTabItem(id = "tab_1", label = "Offers")` |
| molecules.md | `L-icon - Title: True` | `PopTabItem(leftIcon = R.drawable.your_icon)` — `@DrawableRes Int`, not `Icons.*` | PopTabLayout.kt | `PopTabItem(id = "tab_1", label = "Shop", leftIcon = R.drawable.ic_shop)` |
| molecules.md | `L-icon - Title: False` | `PopTabItem(leftIcon = null)` (default) | PopTabLayout.kt | `PopTabItem(id = "tab_1", label = "Shop")` |
| molecules.md | `R-icon - Title: True` | `PopTabItem(rightIcon = R.drawable.your_icon)` — `@DrawableRes Int`, not `Icons.*` | PopTabLayout.kt | `PopTabItem(id = "tab_1", label = "New", rightIcon = R.drawable.ic_badge_dot)` |
| molecules.md | `R-icon - Title: False` | `PopTabItem(rightIcon = null)` (default) | PopTabLayout.kt | `PopTabItem(id = "tab_1", label = "New")` |
| molecules.md | `Title text` | `PopTabItem(label = "Your Label")` — set per tab; never leave default | PopTabLayout.kt | `PopTabItem(id = "tab_home", label = "Home")` |

### Notes — Section A

**Selection model — `selectedTabId`, not `isActive`:**
Active state is **not a field on `PopTabItem`**. The active tab is determined entirely by matching `PopTabItem.id` against the `selectedTabId: String` passed to `PopTabLayout`. Manage `selectedTabId` in the caller's state (ViewModel or `remember`). Never set a boolean flag on the item itself.

```kotlin
var selectedTabId by remember { mutableStateOf("tab_all") }

PopTabLayout(
    tabs = tabs,
    selectedTabId = selectedTabId,
    onTabSelected = { id -> selectedTabId = id }
)
```

**`Is disabled` model:**
- `PopTabItem.enabled = false` prevents the click handler from firing — `onTabSelected` is never called for a disabled tab
- Disabled tabs cannot be the `selectedTabId` — setting a disabled tab's id as `selectedTabId` produces incorrect visual state
- Disabled tabs render with `disabledTextColor` (`#4D4D4D`) regardless of selected state

**Icon resource type — `@DrawableRes Int`, not `Icons.*`:**
`leftIcon` and `rightIcon` are `@DrawableRes Int?` — pass `R.drawable.*` resource IDs. These are **not** the same as `Icons.*` string references used in other POP components (`PopChip`, `PopIconConfig`). There is no automatic icon name resolution.

```kotlin
// Correct
PopTabItem(id = "tab_shop", label = "Shop", leftIcon = R.drawable.ic_shop_outline)

// Wrong — Icons.* string names are not accepted here
PopTabItem(id = "tab_shop", label = "Shop", leftIcon = Icons.Shop) // compile error
```

**Ripple suppression:**
Tap ripple is fully suppressed via `NoRippleIndicationNodeFactory` applied via `CompositionLocalProvider`. The animated underline indicator is the only active-state visual feedback — do not add manual ripple or press effects.

---

## B: Tab Stack Width Mode Mapping

### B. Tab Stack Properties [molecules.md 9b → PopTabLayoutWidth / PopTabLayout]

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | `Count: 2 / 3 / 4 / 5` (fixed count, fits screen) | `widthMode = PopTabLayoutWidth.Equal` — uses Material3 `TabRow`; tabs divide width equally | PopTabLayout.kt | `PopTabLayout(tabs = tabs, selectedTabId = id, widthMode = PopTabLayoutWidth.Equal, onTabSelected = { })` |
| molecules.md | `Count: Plenty` (dynamic / scrollable) | `widthMode = PopTabLayoutWidth.Wrap` (default) — uses `ScrollableTabRow`; tabs use content width, row scrolls | PopTabLayout.kt | `PopTabLayout(tabs = dynamicTabs, selectedTabId = id, widthMode = PopTabLayoutWidth.Wrap, onTabSelected = { })` |
| molecules.md | `R-overflow: True` | ⚠️ **No direct param** — overflow is implicit in `PopTabLayoutWidth.Wrap` (`ScrollableTabRow`). `PopTabLayout` does not expose a gradient overlay parameter. See Notes. | PopTabLayout.kt | Use `widthMode = PopTabLayoutWidth.Wrap` — right overflow fade is handled by `ScrollableTabRow` internally |
| molecules.md | `L-overflow: True` | ⚠️ **No direct param** — same as R-overflow; left fade appears automatically after user scrolls in `Wrap` mode | PopTabLayout.kt | Use `widthMode = PopTabLayoutWidth.Wrap` — left overflow is implicit once scroll position > 0 |
| molecules.md | `Switch tab - Page swipe: True` | `enableSwipe = true` (default) — detects horizontal drag ≥ `swipeThreshold` (default 50f) and calls `onTabSelected` with adjacent tab id | PopTabLayout.kt | `PopTabLayout(tabs = tabs, selectedTabId = id, enableSwipe = true, onTabSelected = { })` |
| molecules.md | `Switch tab - Page swipe: False` | `enableSwipe = false` — disables swipe gesture; tab changes only via direct tap | PopTabLayout.kt | `PopTabLayout(tabs = tabs, selectedTabId = id, enableSwipe = false, onTabSelected = { })` |
| molecules.md | `Divider` (implicit — below tab row) | `showDivider = true` — renders `PopDivider(Horizontal)` with `PopColor.Grey.Grey900` below the tab row | PopTabLayout.kt | `PopTabLayout(tabs = tabs, selectedTabId = id, showDivider = true, onTabSelected = { })` |

### Notes — Section B

**`PopTabLayoutWidth.Equal` vs `PopTabLayoutWidth.Wrap`:**

| Mode | Underlying component | When to use | Layout behaviour |
|---|---|---|---|
| `Equal` | Material3 `TabRow` | Fixed tab count (2–5) that always fits the screen | Tabs divide available width equally; no scrolling |
| `Wrap` (default) | Material3 `ScrollableTabRow` | Dynamic count (`Plenty`) or tabs with long labels that could overflow | Each tab uses its content width; row scrolls horizontally |

Choosing the wrong mode causes layout bugs: `Equal` with many long-label tabs results in clipped text; `Wrap` with 2 short tabs results in left-aligned tabs with empty right space.

**⚠️ `L-overflow` / `R-overflow` gradient gap:**
The design shows explicit gradient fade indicators on left/right edges when tabs scroll. `PopTabLayout` does **not** expose `showLeftOverflow` or `showRightOverflow` parameters. Overflow fading is handled implicitly by `ScrollableTabRow` — the native component provides a fade effect but its appearance is controlled by Material3 internals, not by `PopTabLayout` directly. If a custom gradient matching the design spec is required, wrap `PopTabLayout` in a `Box` and overlay gradient composables positioned at the left/right edges manually.

**`Switch tab - Page swipe` — pairing with `HorizontalPager`:**
`enableSwipe = true` causes `PopTabLayout` to call `onTabSelected` when a horizontal drag exceeds `swipeThreshold`. This only changes the selected tab — it does **not** move the page content. To keep the tab bar and page content in sync, also control the `HorizontalPager` (or equivalent) state from the same `selectedTabId` / index:

```kotlin
var selectedTabId by remember { mutableStateOf(tabs.first().id) }
val selectedIndex = tabs.indexOfFirst { it.id == selectedTabId }
val pagerState = rememberPagerState(initialPage = selectedIndex) { tabs.size }

// Sync pager → tab
LaunchedEffect(pagerState.currentPage) {
    selectedTabId = tabs[pagerState.currentPage].id
}

// Sync tab → pager
LaunchedEffect(selectedIndex) {
    pagerState.animateScrollToPage(selectedIndex)
}

PopTabLayout(
    tabs = tabs,
    selectedTabId = selectedTabId,
    enableSwipe = true,
    onTabSelected = { id -> selectedTabId = id }
)

HorizontalPager(state = pagerState) { page ->
    // page content
}
```

---

## C: Colour Token Reference

### C. Colour Tokens [PopTabLayoutDefaults.colors()]

| Element | State | Default Value (hex) | Override via |
|---|---|---|---|
| Underline indicator | Selected tab | `#E6E6E6` | `indicatorColor` |
| Tab label | Selected | `#E6E6E6` | `selectedTextColor` |
| Tab label | Unselected | `#808080` | `unselectedTextColor` |
| Tab label | Disabled | `#4D4D4D` | `disabledTextColor` |
| Icon tint | Selected | `selectedTextColor` — icon inherits label colour | — |
| Icon tint | Unselected | `unselectedTextColor` — icon inherits label colour | — |
| Icon tint | Disabled | `disabledTextColor` — icon inherits label colour | — |
| Divider (below tabs) | — | `PopColor.Grey.Grey900` | Not overridable via `PopTabLayoutColors` |
| Tab row container | — | `Color.Transparent` | Not overridable |

Override specific colours while keeping defaults for others:

```kotlin
PopTabLayout(
    tabs = tabs,
    selectedTabId = selectedTabId,
    onTabSelected = { id -> selectedTabId = id },
    colors = PopTabLayoutDefaults.colors(
        indicatorColor = Color(0xFFFF6B35),     // brand orange indicator
        selectedTextColor = Color(0xFFFF6B35)   // matching selected label
        // unselectedTextColor and disabledTextColor remain at defaults
    )
)
```

---

## D: Dimension Reference

### D. Dimensions [PopTabLayoutDefaults]

| Token | Value | What it controls |
|---|---|---|
| `PopTabLayoutDefaults.TabHeight` | `56.dp` | Height of each tab hit-target and the tab row |
| `PopTabLayoutDefaults.HorizontalPadding` | `6.dp` | Outer horizontal padding on each tab box + indicator padding |
| `PopTabLayoutDefaults.VerticalPadding` | `16.dp` | Vertical padding inside each tab row |
| `PopTabLayoutDefaults.TextHorizontalPadding` | `8.dp` | Horizontal padding around the label text (between icons and text) |
| `PopTabLayoutDefaults.TextMinWidth` | `30.dp` | Minimum width of the label text |
| `PopTabLayoutDefaults.MinTabWidth` | `30.dp` | Minimum tab width in `Wrap` mode |
| `PopTabLayoutDefaults.IconSize` | `20.dp` | Size of `leftIcon` and `rightIcon` |
| `PopTabLayoutDefaults.EdgePadding` | `6.dp` | Edge padding for `ScrollableTabRow` (`Wrap` mode) |

> Never hardcode these values (e.g., `56.dp`, `20.dp`) — always reference `PopTabLayoutDefaults.*` so changes propagate automatically.

**Typography (applied internally — not overridable via param):**
- Font: `PopTypography.labelLarge`
- Weight: `FontWeight.SemiBold`
- Size: `16.sp`
- Line height: `24.sp`
- Letter spacing: `0.sp`

---

## E: Full Variant Code Patterns

### Count=2, Equal width, no icons, swipe enabled

```kotlin
var selectedTabId by remember { mutableStateOf("tab_all") }

val tabs = listOf(
    PopTabItem(id = "tab_all", label = "All"),
    PopTabItem(id = "tab_offers", label = "Offers")
)

PopTabLayout(
    tabs = tabs,
    selectedTabId = selectedTabId,
    onTabSelected = { id -> selectedTabId = id },
    widthMode = PopTabLayoutWidth.Equal,
    enableSwipe = true
)
```

---

### Count=4, Equal width, with divider

```kotlin
var selectedTabId by remember { mutableStateOf("tab_home") }

val tabs = listOf(
    PopTabItem(id = "tab_home",     label = "Home"),
    PopTabItem(id = "tab_shop",     label = "Shop"),
    PopTabItem(id = "tab_offers",   label = "Offers"),
    PopTabItem(id = "tab_profile",  label = "Profile")
)

PopTabLayout(
    tabs = tabs,
    selectedTabId = selectedTabId,
    onTabSelected = { id -> selectedTabId = id },
    widthMode = PopTabLayoutWidth.Equal,
    showDivider = true
)
```

---

### Count=Plenty (Wrap mode, scrollable)

```kotlin
var selectedTabId by remember { mutableStateOf(categoryTabs.first().id) }

PopTabLayout(
    tabs = categoryTabs,     // Dynamic list — any length
    selectedTabId = selectedTabId,
    onTabSelected = { id -> selectedTabId = id },
    widthMode = PopTabLayoutWidth.Wrap,   // ScrollableTabRow
    showDivider = true
)
```

---

### With L-icon and R-icon

```kotlin
val tabs = listOf(
    PopTabItem(
        id = "tab_shop",
        label = "Shop",
        leftIcon = R.drawable.ic_shop_outline     // @DrawableRes Int
    ),
    PopTabItem(
        id = "tab_new",
        label = "New",
        rightIcon = R.drawable.ic_badge_dot       // @DrawableRes Int
    ),
    PopTabItem(
        id = "tab_sale",
        label = "Sale",
        leftIcon = R.drawable.ic_sale_tag,
        rightIcon = R.drawable.ic_arrow_right
    )
)
```

---

### With disabled tab

```kotlin
val tabs = listOf(
    PopTabItem(id = "tab_active",   label = "Active"),
    PopTabItem(id = "tab_inactive", label = "Coming Soon", enabled = false),  // disabled
    PopTabItem(id = "tab_past",     label = "Past")
)

// Never pass the disabled tab's id as selectedTabId
var selectedTabId by remember { mutableStateOf("tab_active") }
```

---

### Switch tab — Page swipe with HorizontalPager

```kotlin
val tabs = listOf(
    PopTabItem(id = "tab_feed",    label = "Feed"),
    PopTabItem(id = "tab_for_you", label = "For You"),
    PopTabItem(id = "tab_trending", label = "Trending")
)

var selectedTabId by remember { mutableStateOf(tabs.first().id) }
val selectedIndex = tabs.indexOfFirst { it.id == selectedTabId }
val pagerState = rememberPagerState(initialPage = selectedIndex) { tabs.size }

// Pager → Tab sync
LaunchedEffect(pagerState.currentPage) {
    selectedTabId = tabs[pagerState.currentPage].id
}

// Tab → Pager sync
LaunchedEffect(selectedIndex) {
    pagerState.animateScrollToPage(selectedIndex)
}

Column {
    PopTabLayout(
        tabs = tabs,
        selectedTabId = selectedTabId,
        onTabSelected = { id -> selectedTabId = id },
        widthMode = PopTabLayoutWidth.Equal,
        enableSwipe = true,          // Switch tab - Page swipe = True
        showDivider = true
    )

    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> FeedScreen()
            1 -> ForYouScreen()
            2 -> TrendingScreen()
        }
    }
}
```

---

### Custom colours

```kotlin
PopTabLayout(
    tabs = tabs,
    selectedTabId = selectedTabId,
    onTabSelected = { id -> selectedTabId = id },
    colors = PopTabLayoutDefaults.colors(
        indicatorColor = Color(0xFFFF6B35),
        selectedTextColor = Color(0xFFFF6B35),
        unselectedTextColor = Color(0xFF808080),
        disabledTextColor = Color(0xFF4D4D4D)
    )
)
```

---

### Switch tab — Swipe disabled (tap-only navigation)

```kotlin
PopTabLayout(
    tabs = tabs,
    selectedTabId = selectedTabId,
    onTabSelected = { id -> selectedTabId = id },
    widthMode = PopTabLayoutWidth.Equal,
    enableSwipe = false     // Switch tab - Page swipe = False
)
```

---

## Quick Reference

| Design Property | Codebase Field | File |
|---|---|---|
| `Is active: True` | `selectedTabId = tab.id` on `PopTabLayout` | PopTabLayout.kt |
| `Is active: False` | tab.id ≠ `selectedTabId` | PopTabLayout.kt |
| `Is disabled: True` | `PopTabItem(enabled = false)` | PopTabLayout.kt |
| `Is disabled: False` | `PopTabItem(enabled = true)` (default) | PopTabLayout.kt |
| `Title text` | `PopTabItem(label = "…")` | PopTabLayout.kt |
| `L-icon - Title: True` | `PopTabItem(leftIcon = R.drawable.*)` | PopTabLayout.kt |
| `R-icon - Title: True` | `PopTabItem(rightIcon = R.drawable.*)` | PopTabLayout.kt |
| `Count: 2–5` (fixed) | `widthMode = PopTabLayoutWidth.Equal` | PopTabLayout.kt |
| `Count: Plenty` | `widthMode = PopTabLayoutWidth.Wrap` | PopTabLayout.kt |
| `R-overflow: True` | ⚠️ Implicit in `Wrap` mode — no explicit param | PopTabLayout.kt |
| `L-overflow: True` | ⚠️ Implicit in `Wrap` mode — no explicit param | PopTabLayout.kt |
| `Switch tab - Page swipe: True` | `enableSwipe = true` (default) | PopTabLayout.kt |
| `Switch tab - Page swipe: False` | `enableSwipe = false` | PopTabLayout.kt |
| Divider below tabs | `showDivider = true` | PopTabLayout.kt |
| Indicator colour | `PopTabLayoutDefaults.colors(indicatorColor = …)` | PopTabLayout.kt |
| Selected text colour | `PopTabLayoutDefaults.colors(selectedTextColor = …)` | PopTabLayout.kt |
| Unselected text colour | `PopTabLayoutDefaults.colors(unselectedTextColor = …)` | PopTabLayout.kt |
| Disabled text colour | `PopTabLayoutDefaults.colors(disabledTextColor = …)` | PopTabLayout.kt |

---

## Rules

> **Never** use raw Material3 `TabRow` or `ScrollableTabRow` directly — always use `PopTabLayout`.
> **Never** place individual `PopTabItem` composables in a manual `Row` — always pass them as `tabs = listOf(…)` to `PopTabLayout`.
> **Never** use `PopTabBar` (pill-style toggle) for `Tab - Stack` designs — `PopTabBar` is a separate 2-option toggle component unrelated to underline tabs.
> **Never** hardcode `56.dp`, `20.dp`, or other dimension values — always reference `PopTabLayoutDefaults.*`.
> **Never** pass raw `Color(…)` values directly to `PopTabLayout` outside of `PopTabLayoutDefaults.colors(…)`.
> **Always** supply a unique `id` per `PopTabItem` — never use the list index as an ID.
> **Always** manage `selectedTabId` in the caller's state — `PopTabItem` has no `isActive` field.
> **Always** pair `enableSwipe = true` with a `HorizontalPager` (or equivalent) to keep tab bar and page content in sync.
> **Always** add a manual divider in code via `showDivider = true` — never place a `PopDivider` below a `PopTabLayout` manually.
> **Never** set a disabled tab's `id` as `selectedTabId` — disabled tabs cannot be the active tab.
