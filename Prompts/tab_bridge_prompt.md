# Master Prompt: Inside "bridge_doc" folder add a file named "ctab_bridgefile.md"

## Objective
Create a comprehensive bridge documentation file that maps the **POP Design System Agentic Documentation - "molecules.md"** (Tab sections 9a and 9b — Tab Unit and Tab Stack only) with the **POP Codebase Agentic Documentation - POP Codebase/ds_components/PopTabLayout.kt** (Kotlin/Jetpack Compose implementation). This bridge enables seamless design-to-code translation workflows.

> **Scope:** Map only `Tab Unit` (9a) and `Tab Stack` (9b) — the underline-style tab bar that `PopTabLayout.kt` implements. Sections 9c–9g (Pill, Visual, Fancy variants) are out of scope as they do not map to `PopTabLayout.kt`.

---

## A: Tab Item Property Mapping Tables

##### A. Tab Unit Property Mapping [molecules.md → PopTabLayout.kt]

Map every design property of the `Tab Unit` (9a) to its field on `PopTabItem`.

Columns:
- Design System File (e.g., molecules.md)
- Design Property Name (e.g., `Is active`, `Is disabled`, `Title text`, `L-icon - Title`, `R-icon - Title`)
- Property Type (Variant / Boolean / Text)
- Codebase Field (e.g., `PopTabItem.label`, `PopTabItem.enabled`, `PopTabItem.leftIcon`)
- Default Value
- Codebase File Name
- Notes
- Code Pattern

Include mappings for:
- `Title text` (Text) → `PopTabItem.label: String` — always required; never leave the default `"Label • 0"` placeholder in production
- `Is active=True` → controlled via `selectedTabId: String` on `PopTabLayout` — the tab whose `PopTabItem.id == selectedTabId` is treated as selected; individual `PopTabItem` has no `isActive` boolean field
- `Is active=False` → tab whose `id != selectedTabId`; text color uses `colors.unselectedTextColor` (#808080)
- `Is disabled=True` → `PopTabItem.enabled = false` — tab is non-clickable; text color uses `colors.disabledTextColor` (#4D4D4D)
- `Is disabled=False` → `PopTabItem.enabled = true` (default)
- `L-icon - Title=True` → `PopTabItem.leftIcon: Int?` — pass a `@DrawableRes Int` resource ID; always `20dp` (`PopTabLayoutDefaults.IconSize`); tint follows tab text color
- `L-icon - Title=False` → `PopTabItem.leftIcon = null` (default)
- `R-icon - Title=True` → `PopTabItem.rightIcon: Int?` — pass a `@DrawableRes Int` resource ID; always `20dp`; tint follows tab text color
- `R-icon - Title=False` → `PopTabItem.rightIcon = null` (default)

Note: `PopTabItem.id` is a caller-supplied unique string used to drive selection state — it has no design property counterpart but is mandatory in code. Use stable, meaningful values (e.g. `"overview"`, `"transactions"`) — never use positional indices as IDs.

---

##### B. Tab Stack Width Mode Mapping [molecules.md → PopTabLayout.kt]

Map design layout behaviour to `PopTabLayoutWidth`.

Columns:
- Design System File
- Design Context / Behaviour
- Codebase Enum Value (e.g., `PopTabLayoutWidth.Equal`, `PopTabLayoutWidth.Wrap`)
- Underlying Material3 Component
- Codebase File Name
- Notes
- Code Pattern

Include mappings for:
- Fixed equal-width tabs (all tabs share equal width, no scroll) → `PopTabLayoutWidth.Equal` → Material3 `TabRow` — use when `Count` is small (2–5) and tabs fit within the screen width without scrolling
- Scrollable/wrapping tabs (`Count=Plenty` or tabs that overflow the screen) → `PopTabLayoutWidth.Wrap` → Material3 `ScrollableTabRow` — each tab wraps its own content width; edge padding is `6dp` (`PopTabLayoutDefaults.EdgePadding`)
- `Count=2/3/4/5` → typically `PopTabLayoutWidth.Equal` for standard layouts; switch to `Wrap` if label text is long
- `Count=Plenty` → always `PopTabLayoutWidth.Wrap` — tab count is dynamic or unknown; horizontal scroll available

---

##### C. Tab Stack Property Mapping [molecules.md → PopTabLayout.kt]

Map every design property of `Tab - Stack` (9b) to its `PopTabLayout` parameter.

Columns:
- Design System File
- Design Property (e.g., `Count`, `R-overflow`, `L-overflow`, `Switch tab - Page swipe`)
- Property Type (Variant / Boolean)
- Codebase Parameter
- Default Value
- Codebase File Name
- Notes
- Code Pattern

Include mappings for:
- `Count=2/3/4/5` → `tabs = listOf(...)` with the corresponding number of `PopTabItem` entries; use `PopTabLayoutWidth.Equal`
- `Count=Plenty` → `tabs = listOf(...)` with a dynamic-length list; use `PopTabLayoutWidth.Wrap` — `ScrollableTabRow` handles horizontal scroll automatically; no explicit overflow gradient param exists (unlike `PopChipStack`)
- `R-overflow=True` / `L-overflow=True` → ⚠️ `PopTabLayout` does not expose explicit overflow gradient params — scroll overflow is handled automatically by `ScrollableTabRow` when `widthMode = PopTabLayoutWidth.Wrap`; document this design-to-code gap clearly
- `Switch tab - Page swipe=True` → `enableSwipe = true` (default) — swipe left/right gesture switches tabs; threshold controlled by `swipeThreshold: Float` (default `50f` px); swipe right = previous tab, swipe left = next tab
- `Switch tab - Page swipe=False` → `enableSwipe = false` — disables swipe gesture; tab change only via tap
- `Divider` (below tabs) → `showDivider: Boolean` (default `false`) — renders `PopDivider(Horizontal)` directly below the tab row when `true`

---

## B: Colour Token Mapping Table

##### D. Tab Colour Token Mapping [molecules.md → PopTabLayout.kt]

Map all colour tokens used by `PopTabLayout` to design system equivalents.

Columns:
- Design System File
- Tab State / UI Element
- Codebase Color Value (from `PopTabLayoutDefaults.colors()`)
- Hex Value
- Codebase File Name
- How to Override
- Notes
- Code Pattern

Include mappings for:
- Selected tab text → `colors.selectedTextColor` = `Color(0xFFE6E6E6)` — `#E6E6E6`
- Unselected tab text → `colors.unselectedTextColor` = `Color(0xFF808080)` — `#808080`
- Disabled tab text → `colors.disabledTextColor` = `Color(0xFF4D4D4D)` — `#4D4D4D`
- Active underline indicator → `colors.indicatorColor` = `Color(0xFFE6E6E6)` — `#E6E6E6`; height is always `1.dp`; width matches the tab width with `6dp` horizontal padding applied

Note: All four colour values are provided via `PopTabLayoutColors` returned by `PopTabLayoutDefaults.colors()`. Override any token by passing a custom `colors` instance to `PopTabLayout`:
```
PopTabLayout(
    ...,
    colors = PopTabLayoutDefaults.colors(
        selectedTextColor = Color(0xFFFFFFFF),
        indicatorColor = Color(0xFFFFFFFF)
    )
)
```

---

## C: Spacing & Dimension Token Mapping Table

##### E. Tab Dimension Mapping [molecules.md → PopTabLayoutDefaults]

Columns:
- Design Element
- Codebase Token (e.g., `PopTabLayoutDefaults.TabHeight`)
- Resolved Dp Value
- Codebase File Name
- Notes

Include:
- Tab row height → `PopTabLayoutDefaults.TabHeight` = `56.dp`
- Horizontal padding inside each tab → `PopTabLayoutDefaults.HorizontalPadding` = `6.dp`
- Vertical padding inside each tab → `PopTabLayoutDefaults.VerticalPadding` = `16.dp`
- Text horizontal padding (between icon and label) → `PopTabLayoutDefaults.TextHorizontalPadding` = `8.dp`
- Minimum text width → `PopTabLayoutDefaults.TextMinWidth` = `30.dp`
- Minimum tab width (Wrap mode) → `PopTabLayoutDefaults.MinTabWidth` = `30.dp`
- Icon size → `PopTabLayoutDefaults.IconSize` = `20.dp` — fixed; never override with raw `Modifier.size(...)`
- Edge padding (Wrap/Scrollable mode only) → `PopTabLayoutDefaults.EdgePadding` = `6.dp`
- Underline indicator height → `1.dp` (hardcoded in indicator composable — not a named token)

---

##### F. Tab Typography Mapping [molecules.md → PopTabLayout.kt]

Columns:
- Design Element
- Codebase TextStyle
- Font Weight
- Font Size
- Line Height
- Letter Spacing
- Can Override
- Code Pattern

Include:
- Tab label text → `PopTypography.labelLarge` with overrides: `FontWeight.SemiBold`, `16.sp`, `24.sp` lineHeight, `0.sp` letterSpacing — applied uniformly to selected, unselected, and disabled states; only color changes between states

---

## D: Full Variant Mapping Tables

##### G. Tab Unit — All State Variants [molecules.md → PopTabLayout.kt]

Map every Tab Unit design variant to its `PopTabItem` definition.

Columns:
- Design Variant Name (e.g., `Is active=True, Is disabled=False, L-icon=False, R-icon=False`)
- `label`
- `enabled`
- `leftIcon`
- `rightIcon`
- Selection controlled by
- Full Code Pattern

Include:
1. `Is active=True, Is disabled=False, L-icon=False, R-icon=False` — default selected tab
2. `Is active=False, Is disabled=False, L-icon=False, R-icon=False` — unselected tab
3. `Is active=False, Is disabled=True, L-icon=False, R-icon=False` — disabled tab
4. `Is active=True, Is disabled=False, L-icon=True, R-icon=False` — selected tab with left icon
5. `Is active=False, Is disabled=False, L-icon=False, R-icon=True` — unselected tab with right icon
6. `Is active=True, Is disabled=False, L-icon=True, R-icon=True` — selected tab with both icons

---

##### H. Tab Stack — All Variants [molecules.md → PopTabLayout.kt]

Map every named Tab Stack variant to its complete `PopTabLayout(...)` call.

Columns:
- Design Variant Name (e.g., `Count=3, Switch tab - Page swipe=False`)
- `tabs` (list size)
- `widthMode`
- `enableSwipe`
- `showDivider`
- Full Code Pattern

Include representative variants:
1. Count=2, Switch tab - Page swipe=False — static 2-tab equal layout
2. Count=3, Switch tab - Page swipe=True — swipeable 3-tab layout
3. Count=4, Switch tab - Page swipe=True, with Divider — 4-tab + bottom divider
4. Count=5, Switch tab - Page swipe=False — 5-tab equal layout, no swipe
5. Count=Plenty, R-overflow=True, Switch tab - Page swipe=True — scrollable dynamic tab bar with swipe
6. Count=3, Is disabled=True on one tab — mixed enabled/disabled state
7. Count=2, L-icon on all tabs — icon tabs, equal width

---

## Detailed Mapping Instructions

### For Each Mapping Table:

1. **Extract from Design System File (molecules.md):**
   - Read sections 9a (Tab Unit) and 9b (Tab Stack) only
   - Extract all property names, variant values, state options, layout modes, and rules
   - Note which properties belong to the individual tab unit (`PopTabItem`) vs. the stack container (`PopTabLayout`)

2. **Match to Codebase (`PopTabLayout.kt`):**
   - **Tab layout composable**: `PopTabLayout(tabs, selectedTabId, onTabSelected, modifier, widthMode, colors, enableSwipe, swipeThreshold, showDivider)`
   - **Tab item data class**: `PopTabItem(id, label, enabled, leftIcon, rightIcon)`
   - **Width mode enum**: `PopTabLayoutWidth` — `Equal` (fixed-width `TabRow`) / `Wrap` (scrollable `ScrollableTabRow`)
   - **Colours**: `PopTabLayoutColors` via `PopTabLayoutDefaults.colors(indicatorColor, selectedTextColor, unselectedTextColor, disabledTextColor)`
   - **Dimension constants**: `PopTabLayoutDefaults` — `TabHeight`, `HorizontalPadding`, `VerticalPadding`, `TextHorizontalPadding`, `IconSize`, `EdgePadding`

3. **Provide Code Pattern:**
   - Show actual Kotlin/Jetpack Compose usage
   - Always use `PopTabLayout(tabs = listOf(...), selectedTabId = ..., onTabSelected = ...)` — never use raw Material3 `TabRow` or `ScrollableTabRow` directly
   - Every tab must be a `PopTabItem` — never a raw `Text` or custom composable in a `Row`
   - Always supply a unique `id` per `PopTabItem` — never use index as ID
   - Use `PopTabLayoutDefaults.colors(...)` to override colours — never pass raw `Color(...)` directly to `PopTabLayout` outside of `colors`
   - Use `PopTabLayoutDefaults.*` constants for dimension reference — never hardcode `56.dp`, `20.dp`, etc.
   - Show real code examples, not pseudocode

### Mapping Priorities:

1. **MUST include 1:1 mappings** showing exact design-to-code translation for every property
2. **MUST show code patterns** for every variant row
3. **MUST document `PopTabLayoutWidth.Equal` vs `PopTabLayoutWidth.Wrap`** — Equal uses `TabRow` (fixed widths); Wrap uses `ScrollableTabRow` (content-width tabs, scrollable); choosing the wrong mode causes layout bugs
4. **MUST document selection model** — selection is driven by `selectedTabId: String` on the layout composable, not a boolean on `PopTabItem`; always manage `selectedTabId` in the caller's state
5. **MUST document `Switch tab - Page swipe` mapping** — maps to `enableSwipe = true`; must be paired with a `HorizontalPager` or equivalent in the screen to keep the tab selection and the paged content in sync
6. **MUST document `L-overflow` / `R-overflow` gap** — design shows explicit overflow fade indicators; `PopTabLayout` does not expose gradient params — overflow is implicit in `ScrollableTabRow`; flag this gap clearly
7. **MUST document `Is disabled` model** — `PopTabItem.enabled = false` prevents clicks and applies disabled color; clicking a disabled tab does nothing; a disabled tab cannot be the `selectedTabId`
8. **MUST document icon resource type** — `leftIcon` / `rightIcon` are `@DrawableRes Int?` (not `IconName`); pass `R.drawable.*` resource IDs
9. **MUST document `showDivider`** — renders `PopDivider(Horizontal)` below tabs; defaults to `false`; do not add a manual divider when using `showDivider = true`
10. **MUST document `NoRippleIndicationNodeFactory`** — tap ripple is suppressed on all tabs; the underline indicator is the only active-state visual feedback

---

## Quality Standards

✓ **Accuracy:** All mappings verifiable against molecules.md (9a, 9b) and PopTabLayout.kt
✓ **Completeness:** All Tab Unit states + all Tab Stack variants + all design properties mapped
✓ **Clarity:** Design property → codebase param chains always shown; colour hex values included
✓ **Usability:** Tables searchable by design property name and by codebase param/composable
✓ **Practical:** Every row includes a runnable `PopTabLayout(...)` / `PopTabItem(...)` code pattern
✓ **Consistency:** Same column names and ordering throughout all tables
✓ **Gap documentation:** `L-overflow`/`R-overflow` gradient and icon resource type (`@DrawableRes Int`) gaps clearly flagged

---

## Output Format

- **File Type:** Markdown (.md)
- **Structure:** Hierarchical with clear sections — A (Tab item / stack properties), B (Colour tokens), C (Dimensions / Typography), D (Full variants)
- **Tables:** Markdown table format for all mappings
- **Code blocks:** Kotlin code blocks (```kotlin) for all multi-line patterns
- **Length:** Comprehensive but organized — one table per mapping concern

---

## End of Master Prompt

**Generated bridge file should be named:** `ctab_bridgefile.md`
