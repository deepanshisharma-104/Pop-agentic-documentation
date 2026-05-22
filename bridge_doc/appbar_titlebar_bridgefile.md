# App Bar & Title Bar — Design-to-Code Bridge

**Design source:** `molecules.md` — App bar (section 2) and Title bar (section 3)
**Codebase sources:** `PopAppBar.kt` · `PopAppBarDsl.kt` · `PopAppBarManager.kt` · `PopTitleBar.kt`

---

## A: App Bar Property & Type Mapping

### A. App Bar Type Mapping [molecules.md → PopAppBar.kt / PopAppBarManager.kt]

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | `Type: Default` | Standard nav icon on left via `showNavigationIcon = true` + `navigationIcon = Icons.*` | PopAppBarDsl.kt | `ConfigureAppBar(navigationIcon = Icons.ChevronLeft, onNavigationClick = onBack, showNavigationIcon = true)` |
| molecules.md | `Type: Profile` | Left nav icon replaced by avatar composable via `leftSlot` — `ConfigureAppBarLeftSlot { PopAvatar(...) }` | PopAppBarDsl.kt | `ConfigureAppBarLeftSlot { PopAvatar(type = AvatarType.People(...), size = AvatarSize.Small) }` |
| molecules.md | `R-slot: True` | `rightSlot: (@Composable () -> Unit)?` param on `PopAppBar` — place any composable | PopAppBar.kt | `ConfigureAppBarRightSlot { SearchBar(...) }` |
| molecules.md | `R-slot: False` | `rightSlot = null` (default) | PopAppBar.kt | Omit `rightSlot` param |
| molecules.md | `R-icon: True` | `ConfigureAppBarIcons { slot1(...) ... slot4(...) }` DSL | PopAppBarDsl.kt | `ConfigureAppBarIcons { slot1(Icons.Bell01) { onNotif() } }` |
| molecules.md | `R-icon: False` | No icon slots configured — slots are `null` by default | PopAppBarDsl.kt | Omit `ConfigureAppBarIcons` block |

> **`Type=Profile` rule:** `leftSlot` and `showNavigationIcon` are mutually exclusive. When `leftSlot` is provided, the navigation icon is hidden regardless of `showNavigationIcon`. Use `ConfigureAppBarLeftSlot { PopAvatar(...) }` for Profile type — never set both simultaneously.

---

### B. App Bar Icon Slot Mapping [molecules.md → PopAppBarDsl.kt]

> Design `Icon button` sub-component (`Count` property) maps to `AppBarIconSlotsBuilder` slots (slot1–slot4).
> **Left nav icon is always 24dp. Right action slots are always 20dp. Never override these sizes.**

| Design System File | Design Property | Codebase DSL Method | Codebase File | Icon Size | Code Pattern |
|---|---|---|---|---|---|
| molecules.md | `Count (L-icon): 1` — nav icon visible | `showNavigationIcon = true` + `navigationIcon = Icons.*` in `ConfigureAppBar` | PopAppBarDsl.kt | 24dp | `ConfigureAppBar(navigationIcon = Icons.ChevronLeft, onNavigationClick = onBack, showNavigationIcon = true)` |
| molecules.md | `Count (L-icon): 0` — nav icon hidden | `showNavigationIcon = false` or `HideAppBarNavigation()` | PopAppBarDsl.kt | — | `HideAppBarNavigation()` |
| molecules.md | `Count (R-icon): 1` | `slot1(icon) { onClick() }` inside `ConfigureAppBarIcons { }` | PopAppBarDsl.kt | 20dp | `ConfigureAppBarIcons { slot1(Icons.Search01) { onSearch() } }` |
| molecules.md | `Count (R-icon): 2` | `slot1(...)` + `slot2(...)` inside `ConfigureAppBarIcons { }` | PopAppBarDsl.kt | 20dp each | `ConfigureAppBarIcons { slot1(Icons.Share05) { share() }; slot2(Icons.Filter) { filter() } }` |
| molecules.md | `Count (R-icon): 3` | `slot1(...)` + `slot2(...)` + `slot3(...)` inside `ConfigureAppBarIcons { }` | PopAppBarDsl.kt | 20dp each | `ConfigureAppBarIcons { slot1(Icons.Search01) { }; slot2(Icons.Share05) { }; slot3(Icons.MoreHorizontal) { } }` |
| molecules.md | `Count (R-icon): 4` | `slot1(...)` + `slot2(...)` + `slot3(...)` + `slot4(...)` inside `ConfigureAppBarIcons { }` | PopAppBarDsl.kt | 20dp each | `ConfigureAppBarIcons { slot1(Icons.Search01) { }; slot2(Icons.Share05) { }; slot3(Icons.Filter) { }; slot4(Icons.MoreHorizontal) { } }` |

> **Note:** Left nav icon `Count` in design always means 0 (hidden) or 1 (visible) — it is a single icon only. Map to `showNavigationIcon: Boolean`. It is never 2–4.

---

### C. DSL Helper Selection Guide [PopAppBarDsl.kt]

| Design Scenario | DSL to Use | Codebase File | Notes |
|---|---|---|---|
| Need to configure BOTH nav icon AND right icon slots | `ConfigureAppBar(navigationIcon, onNavigationClick, showNavigationIcon) { slot1(...) }` | PopAppBarDsl.kt | Most common — one call handles everything |
| Need to configure icon slots ONLY (nav already set) | `ConfigureAppBarIcons { slot1(...) slot2(...) }` | PopAppBarDsl.kt | Preserves existing navigation config |
| Simple icon + click pairs, no animation config needed | `SetAppBarIcons(icon1 = Icons.X to { }, icon2 = Icons.Y to { })` | PopAppBarDsl.kt | Convenience wrapper over `ConfigureAppBarIcons` |
| Need a custom composable in the right slot | `ConfigureAppBarRightSlot { MyComposable() }` | PopAppBarDsl.kt | For non-icon right-side content |
| `Type=Profile` — avatar in left slot | `ConfigureAppBarLeftSlot { PopAvatar(...) }` | PopAppBarDsl.kt | Replaces nav icon with any composable |
| Remove all right icons | `HideAppBarIcons()` | PopAppBarDsl.kt | Preserves navigation config |
| Hide nav icon only | `HideAppBarNavigation()` or `HideNavigationIcon()` | PopAppBarDsl.kt | Use for home/root screens |
| Scroll scrim (shadow on scroll) | `ConfigureAppBarScrollScrim(lazyListState = listState)` | PopAppBarDsl.kt | Toggles top scrim when content scrolls > 60px |

---

## B: Title Bar Property Mapping

### D. Title Bar Property Mapping [molecules.md → PopTitleBar.kt / PopTitleBarConfig]

| Design System File | Design Property | Codebase Field / Param | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | `Title text: "My Screen"` | `PopTitleBarConfig.titleText: String` — always required, never leave default "Title" | PopTitleBar.kt | `PopTitleBarConfig(titleText = "Send Money")` |
| molecules.md | `Body: True` + `Body text: "..."` | `PopTitleBarConfig.bodyText: String?` — non-null enables body row | PopTitleBar.kt | `PopTitleBarConfig(titleText = "...", bodyText = "john@upi")` |
| molecules.md | `Body: False` | `bodyText = null` (default) — body row not rendered | PopTitleBar.kt | `PopTitleBarConfig(titleText = "...", bodyText = null)` |
| molecules.md | `L-slot: True` | `leftSlot: (@Composable () -> Unit)?` param on `PopTitleBar()` — place back arrow or close icon | PopTitleBar.kt | `PopTitleBar(config = config, leftSlot = { PopIcon(Icons.ChevronLeft) })` |
| molecules.md | `L-slot: False` | `leftSlot = null` (default) | PopTitleBar.kt | Omit `leftSlot` param |
| molecules.md | `L-icon - Title: True` | `PopTitleBarConfig.titleLeftIcon: IconName?` — icon rendered left of title text | PopTitleBar.kt | `PopTitleBarConfig(titleText = "...", titleLeftIcon = Icons.User01)` |
| molecules.md | `L-icon - Title: False` | `titleLeftIcon = null` (default) | PopTitleBar.kt | Omit `titleLeftIcon` |
| molecules.md | `R-icon - Title: True` | `PopTitleBarConfig.titleRightIcon: IconName?` — icon rendered right of title text | PopTitleBar.kt | `PopTitleBarConfig(titleText = "...", titleRightIcon = Icons.Edit01)` |
| molecules.md | `R-icon - Title: False` | `titleRightIcon = null` (default) | PopTitleBar.kt | Omit `titleRightIcon` |
| molecules.md | `L-icon - Body: True` | `PopTitleBarConfig.bodyLeftIcon: IconName?` — icon rendered left of body text | PopTitleBar.kt | `PopTitleBarConfig(bodyText = "...", bodyLeftIcon = Icons.CheckVerified01)` |
| molecules.md | `L-icon - Body: False` | `bodyLeftIcon = null` (default) | PopTitleBar.kt | Omit `bodyLeftIcon` |
| molecules.md | `R-icon - Body: True` | `PopTitleBarConfig.bodyRightIcon: IconName?` — rendered right of body; tap wired via `onBodyRightIconClick` | PopTitleBar.kt | `PopTitleBarConfig(bodyText = "...", bodyRightIcon = Icons.ChevronDown)` + `onBodyRightIconClick = { }` |
| molecules.md | `R-icon - Body: False` | `bodyRightIcon = null` (default) | PopTitleBar.kt | Omit `bodyRightIcon` |
| molecules.md | `R-slot: True` | `rightSlot: (@Composable () -> Unit)?` param on `PopTitleBar()` — any composable (CTA, avatar, icon button) | PopTitleBar.kt | `PopTitleBar(config = config, rightSlot = { TextButton("Done") { onDone() } })` |
| molecules.md | `R-slot: False` | `rightSlot = null` (default) | PopTitleBar.kt | Omit `rightSlot` param |
| molecules.md | `R-overflow: True` | `PopTitleBarConfig.gradientRight: Boolean = true` — activates right gradient fade for long/marquee title | PopTitleBar.kt | `PopTitleBarConfig(titleText = "...", gradientRight = true)` |
| molecules.md | `R-overflow: False` | `gradientRight = false` (default) | PopTitleBar.kt | Omit `gradientRight` |

---

### E. Title Bar Advanced Config [PopTitleBarConfig]

| Config Field | Type | Default | Purpose |
|---|---|---|---|
| `titleText` | `String` | — | Required title string |
| `bodyText` | `String?` | `null` | Subtitle; null = body row hidden |
| `titleLeftIcon` | `IconName?` | `null` | Icon left of title |
| `titleRightIcon` | `IconName?` | `null` | Icon right of title |
| `bodyLeftIcon` | `IconName?` | `null` | Icon left of body |
| `bodyRightIcon` | `IconName?` | `null` | Icon right of body — tap via `onBodyRightIconClick` |
| `gradientRight` | `Boolean` | `false` | Right fade overlay for long text — maps to `R-overflow=True` |
| `gradientLeft` | `Boolean` | `false` | Left fade overlay (marquee scroll lead-in) |
| `enableMarquee` | `Boolean` | `false` | Enables auto-scroll marquee on title overflow |
| `titleTextStyle` | `TextStyle?` | `headingLarge` | Override title font style |
| `bodyTextStyle` | `TextStyle?` | `labelXSmall` | Override body font style |
| `titleTextColor` | `Color` | `TextColor.Primary` | Title text color |
| `bodyTextColor` | `Color` | `TextColor.Secondary` | Body text color |
| `paddingDp` | `Int` | — | Outer padding of the title bar container |
| `titleBodyGapDp` | `Int` | — | Gap between title row and body row |
| `titleIconSizeDp` | `Int` | — | Size of icons in title row |
| `bodyIconSizeDp` | `Int` | — | Size of icons in body row |

---

## C: PopAppBarManager Reference

### F. PopAppBarManager [PopAppBarManager.kt]

| Concern | API | Notes |
|---|---|---|
| Access manager from Compose | `val manager = LocalPopAppBarManager.current` or `getPopAppBarManager()` | Always access via CompositionLocal — never instantiate directly in a screen |
| App bar height constant | `GlobalPopAppBarContentHeight = 56.dp` | Use for sticky component positioning (e.g., sticky tab bar below app bar) |
| Dynamic app bar height | `LocalPopAppBarHeight.current` | Provided via `ProvidePopAppBarManager(appBarHeight = ...)` at activity level |
| Update nav icon only | `manager.updateNavigation(navigationIcon, onNavigationClick, showNavigationIcon)` | Preserves icon slots and right slot |
| Update icon slots only | `manager.updateIconSlots(iconSlots)` | Preserves navigation config |
| Update right slot only | `manager.updateRightSlot(rightSlot = { })` | Preserves navigation and icon slots |
| Update left slot only | `manager.updateLeftSlot(leftSlot = { })` | Preserves all other config |
| Clear icon slots | `manager.clearIconSlots()` | Does NOT clear navigation |
| Hide entire app bar | `manager.hideAppBar()` | For full-screen experiences (video, image viewer) |
| Scroll scrim | `manager.updateScrollScrimVisibility(isVisible = true)` | Prefer `ConfigureAppBarScrollScrim` DSL instead |

> ⚠️ **Never call `manager` methods directly from screen UI code.** Always use the DSL composables (`ConfigureAppBar`, `ConfigureAppBarIcons`, `ConfigureAppBarRightSlot`, `ConfigureAppBarLeftSlot`) — they are lifecycle-aware and handle cleanup automatically.

---

## D: Lifecycle Awareness

### G. DSL Lifecycle Behaviour

| Lifecycle Event | DSL Behaviour |
|---|---|
| `ON_RESUME` | Re-applies the config — ensures the correct screen's bar is shown after pop animations |
| `ON_STOP` | Clears icon slots and navigation ownership — prevents disposed screens from leaking config |
| `onDispose` | Removes observer + clears slots and ownership — automatic cleanup on screen removal |
| Before `STARTED` | Config updates are **ignored** — prevents race conditions during navigation transitions |

> This means every `ConfigureAppBar` / `ConfigureAppBarIcons` call is safe to place at the top level of any `@Composable` screen function — no manual lifecycle management needed.

---

## E: Full Code Patterns

### App Bar — Type=Default, 1 nav icon + 2 right icons

```kotlin
@Composable
fun MyScreen(onBack: () -> Unit) {
    ConfigureAppBar(
        navigationIcon = Icons.ChevronLeft,
        onNavigationClick = onBack,
        showNavigationIcon = true
    ) {
        slot1(Icons.Search01) { onSearch() }
        slot2(Icons.MoreHorizontal) { onMore() }
    }

    // Screen content...
}
```

---

### App Bar — Type=Default, 3 right icons, no nav icon (Home screen)

```kotlin
@Composable
fun HomeScreen() {
    HideAppBarNavigation()
    ConfigureAppBarIcons {
        slot1(Icons.Search01) { onSearch() }
        slot2(Icons.Bell01) { onNotifications() }
        slot3(Icons.Settings01) { onSettings() }
    }

    // Screen content...
}
```

---

### App Bar — Type=Profile (avatar left slot + right icon)

```kotlin
@Composable
fun AccountScreen(user: User, onBack: () -> Unit) {
    ConfigureAppBarLeftSlot {
        PopAvatar(
            type = AvatarType.People(initials = user.initials),
            size = AvatarSize.Small
        )
    }
    ConfigureAppBarIcons {
        slot1(Icons.Settings01) { onSettings() }
    }

    // Screen content...
}
```

---

### App Bar — R-slot (custom composable right side)

```kotlin
@Composable
fun SearchScreen(onBack: () -> Unit) {
    ConfigureAppBar(
        navigationIcon = Icons.ChevronLeft,
        onNavigationClick = onBack,
        showNavigationIcon = true
    )
    ConfigureAppBarRightSlot {
        TextButton(onClick = { onFilter() }) {
            Text("Filter", color = TextColors.Primary.Default)
        }
    }

    // Screen content...
}
```

---

### App Bar — Scroll scrim

```kotlin
@Composable
fun ContentScreen(onBack: () -> Unit) {
    val listState = rememberLazyListState()

    ConfigureAppBar(
        navigationIcon = Icons.ChevronLeft,
        onNavigationClick = onBack,
        showNavigationIcon = true
    )
    ConfigureAppBarScrollScrim(lazyListState = listState)

    LazyColumn(state = listState) {
        // items...
    }
}
```

---

### Title Bar — Full config (body + icons + slots)

```kotlin
PopTitleBar(
    config = PopTitleBarConfig(
        titleText = "Send Money",
        bodyText = "john@okaxis",
        titleRightIcon = Icons.Edit01,
        bodyRightIcon = Icons.ChevronDown,
        gradientRight = false
    ),
    leftSlot = {
        PopIcon(
            icon = Icons.ChevronLeft,
            onClick = onBack
        )
    },
    rightSlot = {
        TextButton(onClick = onDone) {
            Text("Done")
        }
    },
    onBodyRightIconClick = { onChangeUpi() }
)
```

---

### Title Bar — Simple (title only, no body, no icons)

```kotlin
PopTitleBar(
    config = PopTitleBarConfig(
        titleText = "Payment Details"
    ),
    leftSlot = {
        PopIcon(icon = Icons.ArrowLeft, onClick = onBack)
    }
)
```

---

### Title Bar — Long title with R-overflow gradient

```kotlin
PopTitleBar(
    config = PopTitleBarConfig(
        titleText = "Bombay Shaving Company Power Trimmer",
        gradientRight = true,
        enableMarquee = true
    )
)
```

---

## Quick Reference

| Design Property | Codebase Equivalent | File |
|---|---|---|
| `Type=Default` | `ConfigureAppBar(showNavigationIcon = true, navigationIcon = Icons.ChevronLeft, ...)` | PopAppBarDsl.kt |
| `Type=Profile` | `ConfigureAppBarLeftSlot { PopAvatar(...) }` | PopAppBarDsl.kt |
| `R-icon, Count=1` | `ConfigureAppBarIcons { slot1(Icons.X) { } }` | PopAppBarDsl.kt |
| `R-icon, Count=2–4` | `ConfigureAppBarIcons { slot1(...); slot2(...); ... }` | PopAppBarDsl.kt |
| `R-slot=True` | `ConfigureAppBarRightSlot { MyComposable() }` | PopAppBarDsl.kt |
| App bar height | `GlobalPopAppBarContentHeight = 56.dp` / `LocalPopAppBarHeight.current` | PopAppBarManager.kt |
| `Title text` | `PopTitleBarConfig.titleText` | PopTitleBar.kt |
| `Body=True` | `PopTitleBarConfig.bodyText = "..."` | PopTitleBar.kt |
| `L-slot=True` | `leftSlot = { }` param on `PopTitleBar()` | PopTitleBar.kt |
| `R-slot=True` | `rightSlot = { }` param on `PopTitleBar()` | PopTitleBar.kt |
| `R-icon - Body` tap | `onBodyRightIconClick = { }` param on `PopTitleBar()` | PopTitleBar.kt |
| `R-overflow=True` | `PopTitleBarConfig.gradientRight = true` | PopTitleBar.kt |

> **Rule:** `PopTitleBar` is a building block molecule — it is embedded inside the `PopTopBar` organism in final screens. Never place `PopTitleBar` directly on a screen. Document standalone usage for bridge reference only.

> **Rule:** Never call `PopAppBarManager` methods directly from screen UI code — always use the DSL composables which are lifecycle-aware.
