# Chip — Design-to-Code Bridge

**Design source:** `molecules.md` — sections 8a (Chip Unit) and 8b (Chip Stack)
**Codebase sources:** `PopChip.kt` · `PopChipStack.kt` · `PopChipConfig.kt`

> Icon names used in code patterns are sourced from `bridge_doc/icon_files.md`. Always reference `Icons.*` — never hardcode resource IDs.

---

## A: Chip State → Variant & Mode Mapping

### A. Chip State → Variant & Mode [molecules.md → PopChip.kt / PopChipConfig.kt]

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | `State: Toggle` | `variant = PopChipVariant.Basic`, `mode = PopChipMode.Toggleable` | PopChipConfig.kt | `PopChipConfig(text = "Electronics", variant = PopChipVariant.Basic, mode = PopChipMode.Toggleable)` |
| molecules.md | `State: Cross` | `variant = PopChipVariant.WithClose`, `mode = PopChipMode.Toggleable` — wire both `onClick` and `onCloseClick` | PopChipConfig.kt | `PopChipConfig(text = "Electronics", variant = PopChipVariant.WithClose, onClick = { }, onCloseClick = { remove() })` |
| molecules.md | `State: Dropdown` | `variant = PopChipVariant.WithDropdown`, `mode = PopChipMode.Toggleable` | PopChipConfig.kt | `PopChipConfig(text = "Sort by", variant = PopChipVariant.WithDropdown, onClick = { showPicker() })` |
| molecules.md | `State: Switch` | ⚠️ **No `PopChipVariant.Switch` exists** — see Notes below | PopChipConfig.kt | See Notes — Section A |
| molecules.md | `Is active: True` | `isActive = true` — in `Toggleable` mode drives internal border animation | PopChipConfig.kt | `PopChipConfig(text = "Electronics", isActive = true)` |
| molecules.md | `Is active: False` | `isActive = false` (default) | PopChipConfig.kt | `PopChipConfig(text = "Electronics", isActive = false)` |
| molecules.md | `Is disabled: True` | `enabled = false` — applies `SurfaceColor.SecondaryDisabled` bg, `TextColor.Disabled`, no interactions | PopChipConfig.kt | `PopChipConfig(text = "Electronics", enabled = false)` |
| molecules.md | `Is disabled: False` | `enabled = true` (default) | PopChipConfig.kt | `PopChipConfig(text = "Electronics", enabled = true)` |
| molecules.md | `L-Icon: True` | `leadingIcon = PopIconConfig(icon = Icons.Star01)` — icon name from `bridge_doc/icon_files.md` | PopChipConfig.kt | `PopChipConfig(text = "Offers", leadingIcon = PopIconConfig(icon = Icons.Star01))` |
| molecules.md | `L-Icon: False` | `leadingIcon = null` (default) | PopChipConfig.kt | `PopChipConfig(text = "Electronics")` |

### Notes — Section A

**`PopChipMode.Toggleable` vs `PopChipMode.Static`:**
- `PopChipMode.Toggleable` (default) — internally tracks active state, toggles on each click, shows animated border when active
- `PopChipMode.Static` — always renders inactive UI regardless of `isActive` value; use when display-only or when selection is managed externally (e.g., driven from a ViewModel without chip self-toggling)

**⚠️ `State=Switch` gap:**
`PopChipVariant.Switch` does not exist in the codebase. The nearest workaround is to use `PopChipVariant.Basic` with `mode = PopChipMode.Static` and manage the active state externally, toggling `isActive` from your own logic. There is no trailing toggle indicator — the visual affordance differs from the design.

```kotlin
// ⚠️ Workaround for State=Switch — no native Switch variant
PopChipConfig(
    text = "Notifications",
    variant = PopChipVariant.Basic,
    mode = PopChipMode.Static,        // Disable internal toggling
    isActive = isSwitchOn,            // Drive from external state
    onClick = { isSwitchOn = !isSwitchOn }  // Toggle externally
)
```

**`State=Cross` click model:**
`WithClose` variant has two separate tap targets:
- `onClick` — fires when the **root chip** is tapped (any area outside the ✕ icon)
- `onCloseClick` — fires only when the **✕ icon** is tapped

Always wire both. Not wiring `onCloseClick` leaves the close icon visually present but non-functional.

**Active border animation spec (built-in — never replicate manually):**
- Duration: `1000ms`
- Border easing: `CubicBezierEasing(0.7f, 0f, 0.3f, 1f)` — slow start and end
- Fade easing: `CubicBezierEasing(0.4f, 0f, 0.2f, 1f)`
- Border color: `BorderColor.PrimaryInvert`
- Border width: `PopStroke.Default` when active → `0.dp` when inactive

---

## B: Chip Size Mapping

### B. Chip Size [molecules.md → PopChipStack.kt]

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | `Size: Large` | `size = PopChipStackSize.Large` — vertical padding `PopSpacing.Spacing16` (16dp) | PopChipStack.kt | `PopChipStack(chips = chips, size = PopChipStackSize.Large)` |
| molecules.md | `Size: Med` | `size = PopChipStackSize.Med` — vertical padding `PopSpacing.Spacing12` (12dp) | PopChipStack.kt | `PopChipStack(chips = chips, size = PopChipStackSize.Med)` |

### Notes — Section B

**⚠️ `PopChip` has no `size` parameter.**
Size is exclusively controlled through `PopChipStackSize` on `PopChipStack`. A standalone `PopChip` uses fixed internal spacing tokens:
- Horizontal padding: `PopSpacing.Spacing10` (10dp)
- Vertical padding: `PopSpacing.Spacing6` (6dp)
- Min height: `PopSpacing.Spacing28` (28dp)
- Icon size: `PopIcons.sizeSmall` (16dp)
- Gap between elements: `PopSpacing.Spacing8` (8dp)

There is no way to change the size of a standalone chip — always use `PopChipStack` when size control is needed.

---

## C: Chip Stack Property Mapping

### C. Chip Stack Properties [molecules.md → PopChipStack.kt]

| Design System File | Design Property | Codebase Value | Codebase File | Code Pattern |
|---|---|---|---|---|
| molecules.md | `Count: 2 / 3 / 4 / 5` | `chips = listOf(...)` with 2–5 `PopChipConfig` entries | PopChipStack.kt | `PopChipStack(chips = listOf(chip1, chip2, chip3))` |
| molecules.md | `Count: Plenty` | Dynamic `chips` list (no fixed count) — always pair with `showRightOverflow = true` at initial render | PopChipStack.kt | `PopChipStack(chips = dynamicChips, showRightOverflow = true)` |
| molecules.md | `Size: Large` | `size = PopChipStackSize.Large` (default) | PopChipStack.kt | `PopChipStack(chips = chips, size = PopChipStackSize.Large)` |
| molecules.md | `Size: Med` | `size = PopChipStackSize.Med` | PopChipStack.kt | `PopChipStack(chips = chips, size = PopChipStackSize.Med)` |
| molecules.md | `L-overflow: True` | `showLeftOverflow = true` | PopChipStack.kt | `PopChipStack(chips = chips, showLeftOverflow = true)` |
| molecules.md | `L-overflow: False` | `showLeftOverflow = false` (default) | PopChipStack.kt | Omit param |
| molecules.md | `R-overflow: True` | `showRightOverflow = true` | PopChipStack.kt | `PopChipStack(chips = chips, showRightOverflow = true)` |
| molecules.md | `R-overflow: False` | `showRightOverflow = false` (default) | PopChipStack.kt | Omit param |
| molecules.md | `Divider: True` | `showDivider = true` (default) — renders `PopDivider` below the chip row | PopChipStack.kt | `PopChipStack(chips = chips, showDivider = true)` |
| molecules.md | `Divider: False` | `showDivider = false` | PopChipStack.kt | `PopChipStack(chips = chips, showDivider = false)` |

### Notes — Section C

**`Count=Plenty` — scrollable overflow:**
Maps to a horizontally scrollable `chips` list with no fixed count. The stack is always scrollable internally (`horizontalScroll`). Always set `showRightOverflow = true` on initial render to communicate that more chips exist off-screen. Once the user scrolls, toggle `showLeftOverflow = true` / `showRightOverflow = false` based on scroll position if desired.

**Overflow gradient spec:**
- Overlay container width: `72dp`
- Actual gradient fade width: `56dp`
- Gradient background: `SurfaceColor.Primary` → `Color.Transparent` (left) or `Color.Transparent` → `SurfaceColor.Primary` (right)
- Never reconstruct this manually — it is built into `PopChipStack`

**Internal spacing (not design-configurable):**
- Gap between chips: `PopSpacing.Spacing8` (8dp)
- Horizontal container padding: `PopSpacing.Spacing12` (12dp)

---

### D. Chip Stack Selection Mode [molecules.md → PopChipStack.kt]

| Design Context / Use Case | Codebase Parameter | Codebase Value | Codebase File | Notes | Code Pattern |
|---|---|---|---|---|---|
| Sort options, mutually exclusive category filter — only one chip active at a time | `singleSelect` | `singleSelect = true` | PopChipStack.kt | Selecting a chip deselects all others. Tapping the already-selected chip deselects it (back to zero selection) | `PopChipStack(chips = chips, singleSelect = true)` |
| Multiple filters active simultaneously — each chip toggles independently | `singleSelect` | `singleSelect = false` (default) | PopChipStack.kt | Each chip toggles on/off independently; multiple selections allowed | `PopChipStack(chips = chips, singleSelect = false)` |
| React to chip selection in parent (e.g., update ViewModel, fire API) | `onChipSelected` | `onChipSelected = { index, config -> }` | PopChipStack.kt | Called after every tap with the updated `PopChipConfig` (`isActive` reflects new state) and its `index` in the list | `PopChipStack(chips = chips, onChipSelected = { i, c -> vm.onFilter(i, c) })` |

---

## D: Colour Token Reference

| Element | State | Token | Value |
|---|---|---|---|
| Background | Enabled | `SurfaceColor.Secondary` | `#1f1f1f` |
| Background | Disabled | `SurfaceColor.SecondaryDisabled` | dimmed secondary |
| Label text | Enabled | `TextColor.Primary` | `#e6e6e6` |
| Label text | Disabled | `TextColor.Disabled` | dimmed |
| Icon | Enabled | `IconColor.Primary` | `#e6e6e6` |
| Icon | Disabled | `IconColor.Disabled` | dimmed |
| Active border | Active + enabled | `BorderColor.PrimaryInvert` (animated) | invert primary border |
| Active border | Inactive or Static | `Color.Transparent` | no border |
| Overflow gradient | — | `SurfaceColor.Primary` | `#0d0d0d` |
| Stack divider | — | `PopColors.Neutral.N5` | `#31312E` |

---

## E: Full Variant Code Patterns

### State=Toggle — Basic chip, inactive

```kotlin
PopChip(
    config = PopChipConfig(
        text = "Electronics",
        variant = PopChipVariant.Basic,
        mode = PopChipMode.Toggleable,
        isActive = false,
        enabled = true
    )
)
```

### State=Toggle — Basic chip, active (with animated border)

```kotlin
PopChip(
    config = PopChipConfig(
        text = "Electronics",
        variant = PopChipVariant.Basic,
        mode = PopChipMode.Toggleable,
        isActive = true,
        enabled = true
    )
)
```

### State=Toggle — With leading icon

```kotlin
// Icon name sourced from bridge_doc/icon_files.md
PopChip(
    config = PopChipConfig(
        text = "Offers",
        variant = PopChipVariant.Basic,
        mode = PopChipMode.Toggleable,
        isActive = false,
        leadingIcon = PopIconConfig(icon = Icons.Star01)
    )
)
```

### State=Toggle — Disabled

```kotlin
PopChip(
    config = PopChipConfig(
        text = "Electronics",
        variant = PopChipVariant.Basic,
        enabled = false
    )
)
```

### State=Cross — Applied filter with remove

```kotlin
// Two separate callbacks: onClick (root tap) + onCloseClick (✕ tap)
PopChip(
    config = PopChipConfig(
        text = "Electronics",
        variant = PopChipVariant.WithClose,
        mode = PopChipMode.Toggleable,
        isActive = true,
        enabled = true,
        onClick = { /* optional root tap handler */ },
        onCloseClick = { removeFilter("Electronics") }
    )
)
```

### State=Dropdown — Opens picker

```kotlin
PopChip(
    config = PopChipConfig(
        text = "Sort by",
        variant = PopChipVariant.WithDropdown,
        mode = PopChipMode.Toggleable,
        isActive = false,
        enabled = true,
        onClick = { showSortPicker() }
    )
)
```

### State=Static — Display-only, selection managed externally

```kotlin
PopChip(
    config = PopChipConfig(
        text = "Featured",
        variant = PopChipVariant.Basic,
        mode = PopChipMode.Static,   // Always inactive UI — isActive has no visual effect
        isActive = true,             // Ignored in Static mode
        onClick = { /* handle externally */ }
    )
)
```

### Chip Stack — Multi-select filter bar (Count=5, Large, with divider)

```kotlin
var chips by remember {
    mutableStateOf(
        listOf(
            PopChipConfig(text = "All", isActive = true),
            PopChipConfig(text = "Electronics"),
            PopChipConfig(text = "Fashion"),
            PopChipConfig(text = "Home"),
            PopChipConfig(text = "Beauty")
        )
    )
}

PopChipStack(
    chips = chips,
    size = PopChipStackSize.Large,
    showDivider = true,
    singleSelect = false,
    onChipSelected = { index, updatedConfig ->
        chips = chips.mapIndexed { i, c ->
            if (i == index) updatedConfig else c
        }
    }
)
```

### Chip Stack — Single-select sort (Count=3, Med, no divider)

```kotlin
var sortChips by remember {
    mutableStateOf(
        listOf(
            PopChipConfig(text = "Relevance", isActive = true),
            PopChipConfig(text = "Price: Low to High"),
            PopChipConfig(text = "Price: High to Low")
        )
    )
}

PopChipStack(
    chips = sortChips,
    size = PopChipStackSize.Med,
    showDivider = false,
    singleSelect = true,
    onChipSelected = { index, updatedConfig ->
        sortChips = sortChips.mapIndexed { i, c ->
            c.copy(isActive = i == index && updatedConfig.isActive)
        }
        applySort(sortChips[index].text)
    }
)
```

### Chip Stack — Count=Plenty (scrollable, with overflow indicators)

```kotlin
PopChipStack(
    chips = categoryChips,          // Dynamic list, any length
    size = PopChipStackSize.Large,
    showDivider = true,
    showRightOverflow = true,        // Always set true on initial render
    showLeftOverflow = false,        // Set true once user scrolls right
    singleSelect = false,
    onChipSelected = { index, config ->
        viewModel.onCategorySelected(index, config)
    }
)
```

### Chip Stack — With L-overflow + R-overflow (mid-scroll state)

```kotlin
PopChipStack(
    chips = chips,
    size = PopChipStackSize.Large,
    showLeftOverflow = true,         // User has scrolled right — chips exist to the left
    showRightOverflow = true,        // More chips still exist to the right
    showDivider = true,
    singleSelect = false
)
```

---

## Quick Reference

| Design Property | Codebase Field | File |
|---|---|---|
| `State=Toggle` | `variant = PopChipVariant.Basic` + `mode = PopChipMode.Toggleable` | PopChipConfig.kt |
| `State=Cross` | `variant = PopChipVariant.WithClose` | PopChipConfig.kt |
| `State=Dropdown` | `variant = PopChipVariant.WithDropdown` | PopChipConfig.kt |
| `State=Switch` | ⚠️ Not supported — see Section A Notes | — |
| `Is active=True` | `isActive = true` | PopChipConfig.kt |
| `Is disabled=True` | `enabled = false` | PopChipConfig.kt |
| `L-Icon=True` | `leadingIcon = PopIconConfig(icon = Icons.*)` | PopChipConfig.kt |
| ✕ tap handler | `onCloseClick = { }` | PopChipConfig.kt |
| Root tap handler | `onClick = { }` | PopChipConfig.kt |
| `Size=Large` (stack) | `size = PopChipStackSize.Large` | PopChipStack.kt |
| `Size=Med` (stack) | `size = PopChipStackSize.Med` | PopChipStack.kt |
| `Divider=True` | `showDivider = true` | PopChipStack.kt |
| `L-overflow=True` | `showLeftOverflow = true` | PopChipStack.kt |
| `R-overflow=True` | `showRightOverflow = true` | PopChipStack.kt |
| `Count=Plenty` | Dynamic `chips` list + `showRightOverflow = true` | PopChipStack.kt |
| Single-select | `singleSelect = true` | PopChipStack.kt |
| Multi-select | `singleSelect = false` (default) | PopChipStack.kt |
| Selection callback | `onChipSelected = { index, config -> }` | PopChipStack.kt |

> **Rule:** Never place individual `PopChip` composables side-by-side manually — always use `PopChipStack`.
> **Rule:** Never hardcode spacing, padding, or border values — all tokens come from `PopSpacing`, `PopStroke`, `BorderColor`, `SurfaceColor`.
> **Rule:** Never replicate the active border animation — it is built into `PopChip` and fires automatically from `isActive` + `mode`.
